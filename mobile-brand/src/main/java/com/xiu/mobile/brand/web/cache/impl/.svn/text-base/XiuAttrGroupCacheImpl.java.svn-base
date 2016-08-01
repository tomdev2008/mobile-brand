/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-9 上午11:07:01 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.mobile.brand.web.cache.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.xiu.mobile.brand.web.cache.CatalogTreeCache;
import com.xiu.mobile.brand.web.cache.XiuAttrGroupCache;
import com.xiu.mobile.brand.web.cache.XiuAttrGroupInfoCache;
import com.xiu.mobile.brand.web.dao.SalesCatalogCondDao;
import com.xiu.mobile.brand.web.dao.model.XSalesCatalogCond;
import com.xiu.mobile.brand.web.model.AttrGroupJsonModel;
import com.xiu.mobile.brand.web.model.AttrGroupJsonModel.AttrGroupTypeEnum;
import com.xiu.mobile.brand.web.model.CatalogModel;
import com.xiu.mobile.brand.web.util.ConfigUtil;
import com.xiu.mobile.core.constants.GoodsIndexFieldEnum;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-9 上午11:07:01 
 * ***************************************************************
 * </p>
 */

public class XiuAttrGroupCacheImpl extends XiuAttrGroupCache {
	
	private static final Logger LOG = Logger.getLogger(XiuAttrGroupCacheImpl.class);

	private volatile Map<Long,Map<String,AttrGroupJsonModel>> attrGroupMap = new HashMap<Long,Map<String,AttrGroupJsonModel>>();
	private volatile Map<Long,Integer> attrValueSeqMap = new HashMap<Long,Integer>();
	
	private static String[] EMPTY_STRING_ARRAY = new String[0];
	
	private final static String COLOR_ID="7000000000000027249";//官网规范颜色的属性项ID
	private final static String SIZE_ID="7000000000000027250";//官网规范尺码的属性项ID
	
	private SalesCatalogCondDao salesCatalogCondDao;
	
	/**
	 * 初始化属性项数据
	 */
	@Override
	public void init(){
		LOG.debug("初始化XiuAttrGroupCacheImpl，执行init()");
		this.reLoadAttr();
		this.reLoadAttrValue();
		this.reloadTimer();
	}
	
	/**
	 * 加载属性项数据
	 */
	@Override
	public void reLoadAttr(){
		try{
			LOG.info("执行XiuAttrGroupCacheImpl.reLoadAttr()开始");
			List<XSalesCatalogCond> xSalesCatalogConds = 
					salesCatalogCondDao.selectAllForAttrGroup();
			Map<Long, String> map = new HashMap<Long, String>();//分类id：json
			for (XSalesCatalogCond xSalesCatalogCond2 : xSalesCatalogConds) {
				if (null != xSalesCatalogCond2
						&& xSalesCatalogCond2.getCatgroupId() != null)
					map.put(xSalesCatalogCond2.getCatgroupId(),
							xSalesCatalogCond2.getField2());
			}
			//key:分类ID value:map{key:筛选项id value:json数据}
			Map<Long,Map<String,AttrGroupJsonModel>> mapTemp = new HashMap<Long,Map<String,AttrGroupJsonModel>>();
	        List<CatalogModel> categoryList = CatalogTreeCache.getInstance().getTree12();
	        if(categoryList != null){
	        	Map<String,AttrGroupJsonModel> groupMapTemp;
	        	CatalogModel cat1,cat2;
	        	for (CatalogModel cat : categoryList) {
	        		if(cat.getParentCatalogId() > 0)
	        			continue;
	        		// 处理一级分类
	        		groupMapTemp = parseJsonToAttrCatalogLinkMap(map.get(Long.valueOf(cat.getCatalogId())));
	        		mapTemp.put((long)cat.getCatalogId(), groupMapTemp);
	        		if(null != cat.getChildIdList() && cat.getChildIdList().size()>0){//判断二级是否为空
	        			for (Integer catId1 : cat.getChildIdList()) {
	        				cat1 = CatalogTreeCache.getInstance().getTreeNodeById12(String.valueOf(catId1));
	        				if(cat1 == null)
	        					continue;
	        				// 处理二级
	        				groupMapTemp = parseJsonToAttrCatalogLinkMap(map.get(Long.valueOf(cat1.getCatalogId())));
	        				
	        				if(groupMapTemp == null || groupMapTemp.size() <= 0){
	        					groupMapTemp = mapTemp.get(Long.valueOf(cat1.getParentCatalogId()));
	        				}
	                		mapTemp.put((long)cat1.getCatalogId(), groupMapTemp);
	                		if(null != cat1.getChildIdList() && cat1.getChildIdList().size()>0){//判断三级是否为空
	                			for (Integer catId2 : cat1.getChildIdList()) {
	                				cat2 = CatalogTreeCache.getInstance().getTreeNodeById12(String.valueOf(catId2));
	                				if(cat2 == null)
	                					continue;
	                				// 处理三级
	                				groupMapTemp = parseJsonToAttrCatalogLinkMap(map.get(Long.valueOf(cat2.getCatalogId())));
	                				
	                				if(groupMapTemp == null || groupMapTemp.size() <= 0){
	                					groupMapTemp = mapTemp.get(Long.valueOf(cat2.getParentCatalogId()));
	                				}
	                				mapTemp.put((long)cat2.getCatalogId(), groupMapTemp);
	                			}
	                		}
	        			}
	        		}
				}
	        }
			attrGroupMap = mapTemp;
			mapTemp=null;
			map = null;
			LOG.info("执行XiuAttrGroupCacheImpl.reLoadAttr()结束");
		}catch(Exception e){
			LOG.error("执行XiuAttrGroupCacheImpl.reLoadAttr()发生异常", e);
		}
	}
	/**
	 * 加载属性项值顺序数据
	 */
	@Override
	public void reLoadAttrValue(){
		try{
			LOG.info("执行XiuAttrGroupCacheImpl.reLoadattrValue()开始");
			List<XSalesCatalogCond> xSalesCatalogCond = salesCatalogCondDao.selectAllForAttrValue();

			Map<Long, Integer> mapTemp = new HashMap<Long, Integer>();
			if(xSalesCatalogCond != null){
				for(XSalesCatalogCond xSalesCatalogCond2 : xSalesCatalogCond){
					if(xSalesCatalogCond2 != null && xSalesCatalogCond2.getAttrdictgrpId() != null){
						mapTemp.put(xSalesCatalogCond2.getAttrdictgrpId(), xSalesCatalogCond2.getOrderBy());
					}
				}	
				attrValueSeqMap = mapTemp;
			}
			else{
		    	LOG.info("【XiuAttrGroupCacheImpl】在数据库中没有查询到属性值的信息");
		    }
			LOG.info("执行XiuAttrGroupCacheImpl.reLoadattrValue()结束");
		}catch(Exception e){
			LOG.error("执行XiuAttrGroupCacheImpl.reLoadattrValue()发生异常", e);
		}
	}
	/**
	 * 根据属性项ID，取得对应的属性项
	 * @param attrGroupId
	 * @return
	 */
	@Override
	public Map<String,AttrGroupJsonModel>  selectByPrimaryKeyForJson(Long categoryId) {
		if(categoryId != null){
			return attrGroupMap.get(categoryId);
		}
		return null;
	}
	
	public Integer selectAttrValueForId(Long id){
		if(id != null){
			return attrValueSeqMap.get(id);
		}
		return null;
	}
	/**
	 * 定时器
	 */
	private void reloadTimer(){
		Timer t=new Timer(true);
		TimerTask tk=new TimerTask() {
			@Override
			public void run() {
				reLoadAttr();
				reLoadAttrValue();
			}
		};
		
		long updateTime  = 60000 * Long.valueOf(ConfigUtil.getValue("attr.update.time"));
		//5秒之后 ，每隔N分钟执行一次
		t.scheduleAtFixedRate(tk, 5000, updateTime);
	}
	
	private Map<String,AttrGroupJsonModel> parseJsonToAttrCatalogLinkMap(String json){
	//伪造的运营分类数据	William.zhang	20130524
	//	json = "[{\"aliasName\":\"\",\"attrGroupValueId\":[],\"display\":\"1\",\"id\":\"7000000000000000091\",\"isAll\":\"1\",\"name\":\"图案样式\",\"order\":\"4\",\"type\":\"0\"},{\"aliasName\":\"\",\"attrGroupValueId\":[],\"display\":\"1\",\"id\":\"7000000000000000090\",\"isAll\":\"1\",\"name\":\"袖长\",\"order\":\"2\",\"type\":\"0\"},{\"aliasName\":\"\",\"attrGroupValueId\":[],\"display\":\"1\",\"id\":\"7000000000000019770\",\"isAll\":\"1\",\"name\":\"服装风格\",\"order\":\"3\",\"type\":\"0\"}]";
		Map<String, AttrGroupJsonModel> attrGroups=new LinkedHashMap<String, AttrGroupJsonModel>();
		List<AttrGroupJsonModel> attrGroupList = new ArrayList<AttrGroupJsonModel>();
		if(StringUtils.isNotBlank(json)){
			@SuppressWarnings("unchecked")
			List<Map<String,Object>> array=JSONArray.fromObject(json);
			Map<String,Object> _obj;
			AttrGroupJsonModel _attrModel;
			for (int i = 0,len=array.size(); i < len; i++) {
				_obj=(Map<String,Object>)array.get(i);
				_attrModel = this.transformJsonItemToModel(_obj);
				if(null != _attrModel && _attrModel.isDisplayFlag()){
					attrGroupList.add(_attrModel);
				}
			}
		}
		
		//处理规范颜色和规范尺码
		this.addColorAndSizeAttr(attrGroupList);
		
		//排序
		Collections.sort(attrGroupList,new Comparator<AttrGroupJsonModel>() {
			@Override
			public int compare(AttrGroupJsonModel o1, AttrGroupJsonModel o2) {
				return o2.getOrder() - o1.getOrder();
			}
		});
		for (AttrGroupJsonModel m : attrGroupList) {
			attrGroups.put(GoodsIndexFieldEnum.ATTRS_PREFIX.fieldName()+m.getId(), m);
		}
		return attrGroups;
	}
	
	private AttrGroupJsonModel transformJsonItemToModel(Map<String,Object> itemMap){
		if(null == itemMap)
			return null;
		String[] attrGroupValueIds = null;
		try {
			attrGroupValueIds = (String[]) ((JSONArray) itemMap.get("attrGroupValueId")).toArray(new String[]{});
		} catch (Exception e) {
			// ignore this exception
		}
		AttrGroupJsonModel ret = new AttrGroupJsonModel();
		ret.setAll("1".equals(itemMap.get("isAll")));
		if(!ret.isAll()
				 && (attrGroupValueIds == null || attrGroupValueIds.length == 0))
			return null;
		ret.setAttrGroupValueIds(attrGroupValueIds);
		AttrGroupTypeEnum type = AttrGroupTypeEnum.valueof(getValueForInteger(itemMap.get("type")));
		int order = getValueForInteger(itemMap.get("order"));
		boolean displayFlag = "1".equals(getValueForString(itemMap.get("display")));
		String id = getValueForString(itemMap.get("id"));
		String localName = getValueForString(itemMap.get("name"));
		/*if("7000000000000027249".equals(id)||"7000000000000027250".equals(id)){
			System.out.println("7000000000000027249或7000000000000027250=="+id);
		}*/
		if(LOG.isInfoEnabled()){
			LOG.info("-------筛选项id:"+id+"------------localName is ："+localName+"------------------------");
		}
		/*
		//	有别名的情况展示别名	William.zhang	20130507
		if(itemMap.get("aliasName") != null && !"".equals(itemMap.get("aliasName"))){
			name = getValueForString(itemMap.get("aliasName"));
		}*/
		
		String name = XiuAttrGroupInfoCache.getInstance().getAttrGrouNameByID(id);
		
		if(LOG.isInfoEnabled()){
			LOG.info("----------筛选项id:"+id+"-----------Name is ："+name+"------------------");
		}
		if(name==null){
			return null;
		}
		//	属性值是否全展示	William.zhang	20130507
		ret.setOrder(order);
		ret.setDisplayFlag(displayFlag);
		if(type == null)
			type = AttrGroupTypeEnum.ATTR;
		if(type == AttrGroupTypeEnum.ATTR){
			ret.setId(id);
			ret.setName(name);
		}else if(type == AttrGroupTypeEnum.BRAND){
			ret.setId("0");
			ret.setName(StringUtils.isBlank(name) ? "品牌类型" : name);
		}
		return ret;
	}
	/**
	 * 判断Map值的类型,避免出现Integer或null
	 * @param o
	 * @return
	 */
	private int getValueForInteger(Object o){
		if(null==o){
			return 0;
		}else if(o instanceof Integer){
			return ((Integer) o).intValue();
		}else if(o instanceof String){
			try {
				return Integer.valueOf(o.toString());
			} catch (Exception e) {
			}
		}
		return 0;
	}
	
	/**
	 * 判断Map值的类型,避免出现Integer或null
	 * @param o
	 * @return
	 */
	private String getValueForString(Object o){
		return null==o?"":o.toString();
	}
	
	private void addColorAndSizeAttr(List<AttrGroupJsonModel> attrGroupList){
		if(attrGroupList == null)
			return;
		List<String> attrIds = new ArrayList<String>(2);
		
		//添加日期：2014-07-30 17：00 lvshd  修改attrGroupList 不为空且没有规范颜色尺码的情况
		boolean hasColor=false,hasSize=false;
		for (AttrGroupJsonModel o : attrGroupList) {
			if(o!=null){
				attrIds.add(o.getId());
				if(!hasColor && COLOR_ID.equals(o.getId())){
					o.setOrder(Integer.MAX_VALUE-2);
					hasColor=true;
				}
				if(!hasSize && SIZE_ID.equals(o.getId())){
					o.setOrder(Integer.MAX_VALUE-1);
					hasSize=true;
				}
			}
		}
		AttrGroupJsonModel attr;
		
		//规范颜色	镜像环境颜色的ID为700000000000002 0 249，所以在镜像上面颜色存在不设置不显示的问题，上了线就正常了！
		if(!hasColor && (attrIds == null || !attrIds.contains(COLOR_ID))){//"7000000000000027249")){
			attr = new AttrGroupJsonModel();
			attr.setAliasName("颜色");
			attr.setAll(true);
			attr.setId(COLOR_ID);
			attr.setAttrGroupValueIds(EMPTY_STRING_ARRAY);
			attr.setName("颜色");
			attr.setOrder(Integer.MAX_VALUE-2);// 修改日期：2014-07-30 15：57
			attr.setType(0);
			attr.setDisplayFlag(true);
			attrGroupList.add(attr);
		}
		//规范尺码	镜像环境颜色的ID为700000000000002 0 250，所以在镜像上面颜色存在不设置不显示的问题，上了线就正常了！
		if(!hasSize && (attrIds == null || !attrIds.contains(SIZE_ID))){//"7000000000000027250")){
			attr = new AttrGroupJsonModel();
			attr.setAliasName("尺码");
			attr.setAll(true);
			attr.setId(SIZE_ID);
			attr.setName("尺码");
			attr.setOrder(Integer.MAX_VALUE-1);// 修改日期：2014-07-30 15：57
			attr.setAttrGroupValueIds(EMPTY_STRING_ARRAY);
			attr.setType(0);
			attr.setDisplayFlag(true);
			attrGroupList.add(attr);
		}
	}
	private void addColorAndSizeAttr2(List<AttrGroupJsonModel> attrGroupList){
		if(attrGroupList == null)
			return;
		List<String> attrIds = new ArrayList<String>(2);
		for (AttrGroupJsonModel o : attrGroupList) {
			if(o!=null)
			attrIds.add(o.getId());
		}
		AttrGroupJsonModel attr;
		//规范颜色	镜像环境颜色的ID为700000000000002 0 249，所以在镜像上面颜色存在不设置不显示的问题，上了线就正常了！
		if(attrIds == null || !attrIds.contains("7000000000000027249")){
			attr = new AttrGroupJsonModel();
			attr.setAliasName("颜色");
			attr.setAll(true);
			attr.setId("7000000000000027249");
			attr.setAttrGroupValueIds(EMPTY_STRING_ARRAY);
			attr.setName("颜色");
			attr.setOrder(-2);
			attr.setType(0);
			attr.setDisplayFlag(true);
			attrGroupList.add(attr);
		}
		//规范尺码	镜像环境颜色的ID为700000000000002 0 250，所以在镜像上面颜色存在不设置不显示的问题，上了线就正常了！
		if(attrIds == null || !attrIds.contains("7000000000000027250")){
			attr = new AttrGroupJsonModel();
			attr.setAliasName("尺码");
			attr.setAll(true);
			attr.setId("7000000000000027250");
			attr.setName("尺码");
			attr.setOrder(-1);
			attr.setAttrGroupValueIds(EMPTY_STRING_ARRAY);
			attr.setType(0);
			attr.setDisplayFlag(true);
			attrGroupList.add(attr);
		}
	}
	
	/**
	 * @return the salesCatalogCondDao
	 */
	public SalesCatalogCondDao getSalesCatalogCondDao() {
		return salesCatalogCondDao;
	}

	/**
	 * @param salesCatalogCondDao the salesCatalogCondDao to set
	 */
	public void setSalesCatalogCondDao(SalesCatalogCondDao salesCatalogCondDao) {
		this.salesCatalogCondDao = salesCatalogCondDao;
	}
	
	
}
