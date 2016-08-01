package com.xiu.mobile.brand.web.cache.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.params.SolrParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiu.mobile.brand.web.cache.CatalogTreeCache;
import com.xiu.mobile.brand.web.constants.ItemShowTypeEnum;
import com.xiu.mobile.brand.web.dao.CatalogDao;
import com.xiu.mobile.brand.web.dao.model.XiuCatalogDBModel;
import com.xiu.mobile.brand.web.model.CatalogModel;
import com.xiu.mobile.brand.web.util.CommonUtil;
import com.xiu.mobile.brand.web.util.ConfigUtil;
import com.xiu.mobile.brand.web.util.Constants;
import com.xiu.mobile.core.model.GoodsSolrModel;
import com.xiu.search.solrj.service.SolrHttpService;

/**
 * <p>
 * **************************************************************
 * 
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-9 上午11:08:57
 *       ***************************************************************
 *       </p>
 */

public class CatalogTreeCacheImpl extends CatalogTreeCache {

	private static final Logger LOGGER = LoggerFactory.getLogger(CatalogTreeCacheImpl.class);

	private volatile boolean isRunning;

	// 官网,display=1
	private volatile Map<String, CatalogModel> treeMap = null;

	// 官网,display=1,2
	private volatile Map<String, CatalogModel> treeMap_0 = null;

	private CatalogDao catalogDao;

	@Override
	protected void init() {
		this.reload();
		this.reloadTimer();
	}

	@Override
	protected void reload() {

		if (isRunning) {
			return;
		}

		LOGGER.info("【XiuCatalogTreeCacheImpl】 开始从数据库中读取运营分类数据: ");

		// 1. 查询数据库中运营分类数据
		List<XiuCatalogDBModel> cList = this.getCatalogFromDB();
		if (cList == null || cList.size() == 0) {
			isRunning = false;
			return;
		}

		// 2.1 构建官网树
		Map<String, CatalogModel> cMap = this.getCatalogBosByDb(cList, null);
		if (cMap == null || cMap.size() == 0) {
			LOGGER.info("【XiuCatalogTreeCacheImpl】官网树  中不包含display=1的运营分类");
			isRunning = false;
			return;
		} else {
			treeMap = cMap;
		}
		// 2.2 构建官网树 display=1,2
		cMap = this.getCatalogBosByDb(cList, null, true);
		if (cMap == null || cMap.size() == 0) {
			LOGGER.info("【XiuCatalogTreeCacheImpl】官网树  中不包含display=1,2的运营分类");
			isRunning = false;
			return;
		} else {
			treeMap_0 = cMap;
		}

		LOGGER.info("【XiuCatalogTreeCacheImpl】 从数据库中读取运营分类数据完成，共有["
				+ cList.size() + "]条数据");
		// 清空临时变量
		cList.clear();
		cList = null;
		cMap = null;
		isRunning = false;

	}

	/**
	 * 生成Tree，只包含display=1
	 */
	private Map<String, CatalogModel> getCatalogBosByDb(
			List<XiuCatalogDBModel> xcList, String itemShowType) {
		return this.getCatalogBosByDb(xcList, itemShowType, false);
	}
	
	/**
	 * 生成Tree
	 */
	private Map<String, CatalogModel> getCatalogBosByDb(
			List<XiuCatalogDBModel> xcList, String itemShowType,
			boolean include2) {

		Map<String, CatalogModel> classMap = new HashMap<String, CatalogModel>();
		CatalogModel o = null, op1 = null, op2 = null;

		Map<Integer, Long> retMap = this.queryCounts(itemShowType);

		int mktType = 0;

		for (XiuCatalogDBModel xcb : xcList) {
			
			if (!include2 && Constants.CATALOG_DISPLAY_HIDDEN.equals(xcb.getDisplay1())) {
				continue;
			}
			if (Constants.PROVIDER_CODE_EBAY.equals(xcb.getCatProviderCode())) {
				mktType = Constants.XIUINDEX_MKTTYPE_EBAY;
			} else {
				mktType = Constants.XIUINDEX_MKTTYPE_XIU;
			}

			// 一级分类
			op1 = classMap.get(String.valueOf(xcb.getParCatId1()));
			
			if (op1 == null) {
				op1 = this.buildModel(xcb.getParCatId1(), xcb.getParCatName1(),
						0, xcb.getParCatRank1(), mktType,
						this.getItemCount(retMap, xcb.getParCatId1()),
						xcb.getDisplay1(),CommonUtil.wrapCatalogImg(xcb.getImg1()));
				if (op1 == null) {
					continue;
				}
				classMap.put(String.valueOf(xcb.getParCatId1()), op1);
			}
            
			// 二级分类
			if (!include2
					&& Constants.CATALOG_DISPLAY_HIDDEN.equals(xcb.getDisplay2())) {
				continue;
			}
			op2 = classMap.get(String.valueOf(xcb.getParCatId2()));
			if (op2 == null) {
				op2 = this.buildModel(xcb.getParCatId2(), xcb.getParCatName2(),
						xcb.getParCatId1(), xcb.getParCatRank2(), mktType,
						this.getItemCount(retMap, xcb.getParCatId2()),
						xcb.getDisplay2(),CommonUtil.wrapCatalogImg(xcb.getImg2()));
				if (op2 == null) {
					continue;
				}
				classMap.put(String.valueOf(xcb.getParCatId2()), op2);
			}

			// 把二级分类加到一级分类节点的childList中
			this.addChild(op2.getCatalogId(), op1);

			// 三级分类
			if (!include2 && Constants.CATALOG_DISPLAY_HIDDEN.equals(xcb.getDisplay3())) {
				continue;
			}
			o = this.buildModel(xcb.getCatId(), xcb.getCatName(),
					xcb.getParCatId2(), xcb.getCatRank(), mktType,
					this.getItemCount(retMap, xcb.getCatId()),
					xcb.getDisplay3(),CommonUtil.wrapCatalogImg(xcb.getImg()));
			if (o == null) {
				continue;
			}

			classMap.put(String.valueOf(o.getCatalogId()), o);
			op2.addChildId(o.getCatalogId());
		}
		retMap.clear();
		retMap = null;

		return classMap;
	}

	@Override
	public List<CatalogModel> getTree() {
		Map<String, CatalogModel> map = treeMap;
		if (map == null || map.size() == 0) {
			return null;
		}
		List<CatalogModel> retList = new ArrayList<CatalogModel>(map.size());
		Iterator<CatalogModel> itr = map.values().iterator();
		while (itr.hasNext()) {
			retList.add(itr.next());
		}
		itr = null;
		map = null;
		Collections.sort(retList);
		return retList;
	}
	
	public List<CatalogModel> getTree12() {
		Map<String, CatalogModel> map = treeMap_0;
		if (map == null || map.size() == 0) {
			return null;
		}
		List<CatalogModel> retList = new ArrayList<CatalogModel>(map.size());
		Iterator<CatalogModel> itr = map.values().iterator();
		while (itr.hasNext()) {
			retList.add(itr.next());
		}
		itr = null;
		map = null;
		Collections.sort(retList);
		return retList;
	}

	@Override
	public CatalogModel getTreeNodeById(String catalogID) {
		Map<String, CatalogModel> map = treeMap;
		if(map==null || map.size()==0){
			return null;
		}
		CatalogModel cm=map.get(catalogID);
		map = null;
		return cm;
	}

	@Override
	public CatalogModel getTreeNodeById12(String catalogID) {
		Map<String, CatalogModel> map = treeMap_0;
		if(map==null || map.size()==0){
			return null;
		}
		CatalogModel cm=map.get(catalogID);
		map = null;
		return cm;
	}
	
	@Override
	public CatalogModel getTreeNodeByIdFromTreeMap0(String catalogID) {
		Map<String, CatalogModel> map = treeMap_0;
		if(map==null || map.size()==0){
			return null;
		}
		CatalogModel cm=map.get(catalogID);
		map = null;
		return cm;
	}

	/**
	 * 从数据库中加载所有分类信息
	 * 
	 * @return
	 */
	private List<XiuCatalogDBModel> getCatalogFromDB() {
		List<XiuCatalogDBModel> xcList = null;
		/*
		 * 前端是否展示display为0的运营分类,true:显示,false:不显示 add Time: 2013-05-20 15:30
		 */
		try {
			xcList = catalogDao.selectAllLevel3CatalogListByDB();
			return xcList;
		} catch (Exception e) {
			LOGGER.error("【XiuCatalogTreeCacheImpl】 从数据库中读取运营分类数据出错: ", e);
			return null;
		}
	}

	/**
	 * 搜索各运营分类对应的商品数量
	 * @return
	 */
	private Map<Integer, Long> queryCounts(String itemShowTypeValue) {
		Map<Integer, Long> retMap = new HashMap<Integer, Long>();
		CommonsHttpSolrServer solrServer = null;

		try {
			solrServer = SolrHttpService.getInstance().getSolrServer(
					GoodsSolrModel.class);
			Map<String, String[]> prms = new HashMap<String, String[]>();
			if (itemShowTypeValue == null) {
				prms.put("q", new String[] { "*:*" });
			} else {
				prms.put("q", new String[] { "itemShowType:"
						+ itemShowTypeValue });
			}

			prms.put("facet", new String[] { "true" });
			prms.put("facet.field", new String[] { "oclassIds" });
			prms.put("rows", new String[] { "0" });
			prms.put("facet.limit", new String[] { "1000000" });

			SolrParams sp = new ModifiableSolrParams(prms);
			QueryResponse response = solrServer.query(sp);

			List<FacetField> fList = response.getFacetFields();
			if (fList == null) {
				return retMap;
			}
			List<Count> cList = null;
			for (FacetField ff : fList) {
				cList = ff.getValues();
				if (cList == null || cList.size() == 0) {
					continue;
				}
				for (Count c : cList) {
					retMap.put(Integer.parseInt(c.getName()), c.getCount());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return retMap;
		} finally {
			solrServer = null;
		}
		return retMap;
	}

	private long getItemCount(Map<Integer, Long> iMap, Integer key) {
		Long ic = iMap.get(key);
		if (ic == null) {
			return 0;
		}
		return ic.longValue();
	}

	/**
	 * 创建节点
	 * @param id
	 * @param name
	 * @param parentId
	 * @param rank
	 * @param mktType
	 * @param itemCount
	 * @param display
	 * @return
	 */
	private CatalogModel buildModel(int id, String name, int parentId,
			Integer rank, int mktType, long itemCount, String display, String img) {

		if (itemCount < 1) {
			return null;
		}
		CatalogModel os = new CatalogModel(id, name, parentId,
				rank != null ? rank.intValue() : 0, itemCount, mktType, display, img);
		return os;
	}

	/**
	 * 把子节点加入父节点
	 * 
	 * @param sonId
	 * @param parent
	 */
	private void addChild(Integer sonId, CatalogModel parent) {
		boolean contains = false;
		List<Integer> sList = parent.getChildIdList();
		if (sList == null || sList.size() == 0) {
			parent.addChildId(sonId);
		} else {
			for (Integer os : sList) {
				if (os != null && os.intValue() == sonId.intValue()) {
					contains = true;
					break;
				}
			}
			if (!contains) {
				parent.addChildId(sonId);
			}
		}
	}
	
	/**
	 * 定时器
	 */
	private void reloadTimer() {
		Timer t = new Timer(true);
		TimerTask tk = new TimerTask() {
			@Override
			public void run() {
				reload();
			}
		};
		
		// 10秒之后 ，每隔N分钟执行一次
		long updateTime  = 60000 * Long.valueOf(ConfigUtil.getValue("catalog.update.time"));
		t.scheduleAtFixedRate(tk, 1000, updateTime);
	}
	
	/**
	 * @return the catalogDao
	 */
	public CatalogDao getCatalogDao() {
		return catalogDao;
	}

	/**
	 * @param catalogDao the catalogDao to set
	 */
	public void setCatalogDao(CatalogDao catalogDao) {
		this.catalogDao = catalogDao;
	}

	@Override
	public CatalogModel getTreeNodeById(String catalogId,
			ItemShowTypeEnum itemShoTypeEnum) {
		if(itemShoTypeEnum==null){
			return this.getTreeNodeById(catalogId);
		}
		CatalogModel cb=null;
		Map<String, CatalogModel> map = null;
		switch (itemShoTypeEnum) {
		case DSP12:
			map = treeMap_0;
			break;	
		}
		cb = this.retBoByMap(catalogId, map);
		map = null;
		return cb;
	}
	/**
	 * 从指定的Map中获取对象
	 * @param catalogId
	 * @param tMap
	 * @return
	 */
	private CatalogModel retBoByMap(String catalogId,Map<String, CatalogModel> tMap){
		if(tMap==null || tMap.size()==0){
			return null;
		}
		CatalogModel cm=tMap.get(catalogId);
		tMap = null;
		return cm;
	}
}
