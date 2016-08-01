package com.xiu.mobile.brand.web.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xiu.common.web.contants.NumberConstant;
import com.xiu.mobile.brand.web.bo.CatalogBo;
import com.xiu.mobile.brand.web.cache.CatalogTreeCache;
import com.xiu.mobile.brand.web.constants.ItemShowTypeEnum;
import com.xiu.mobile.brand.web.constants.MktTypeEnum;
import com.xiu.mobile.brand.web.model.CatalogModel;
import com.xiu.mobile.brand.web.service.ICatalogService;
import com.xiu.mobile.brand.web.util.Constants;

/**
 * <p>
 * **************************************************************
 * 
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-9 下午4:04:52
 *       ***************************************************************
 *       </p>
 */
@Service("catalogService")
public class CatalogServiceImpl implements ICatalogService {
	private static final Logger logger = LoggerFactory.getLogger(CatalogServiceImpl.class);
	
	@Override
	public CatalogBo getSelectedCatalogFromSelectedCatalogTree(
			CatalogBo selectedCatalogTree) {
		if (selectedCatalogTree == null)
			return null;
		CatalogBo ret = selectedCatalogTree;
		CatalogBo retTemp;
		if (selectedCatalogTree.isSelected()
				&& selectedCatalogTree.getChildCatalog() != null
				&& selectedCatalogTree.getChildCatalog().size() > 0) {
			for (CatalogBo bo : selectedCatalogTree.getChildCatalog()) {
				
				if (bo.isSelected()) {
					ret = bo;
					retTemp = getSelectedCatalogFromSelectedCatalogTree(bo);
					
					if (null != retTemp) {
						return retTemp;
					}
				}
			}
		}
		return ret;
	}

	public List<CatalogBo> parsePlaneSelectCatalogBo(
			List<CatalogBo> catalogBoList) {
		List<CatalogBo> list = new ArrayList<CatalogBo>();
		if (catalogBoList != null)
			for (CatalogBo cat : catalogBoList) {
				if (cat.isSelected()) {
					list.add(cat);
					list.addAll(parsePlaneSelectCatalogBo(cat.getChildCatalog()));
					break;
				}
			}
		return list;
	}

	@Override
	public void filterCatalogTreeDeleteUnSelectedSiblingsItem(
			List<CatalogBo> catalogBoList) {
		if (CollectionUtils.isEmpty(catalogBoList))
			return;
		int len = catalogBoList.size();
		CatalogBo bo;
		while (len-- > 0) {
			bo = catalogBoList.get(len);
			if (bo.isSelected()) {
				catalogBoList.clear();
				catalogBoList.add(bo);
				if (CollectionUtils.isNotEmpty(bo.getChildCatalog()))
					this.filterCatalogTreeDeleteUnSelectedSiblingsItem(bo
							.getChildCatalog());
				return;
			}

		}

	}

	@Override
	public CatalogBo fetchCatalogBoTreeById(Integer catId) {
		return this.fetchCatalogBoByIdAndMktTypeAndShowType(catId, null, null);
	}

	@Override
	public CatalogBo fetchCatalogBoTreeByIdForXiu(Integer catId,
			ItemShowTypeEnum type) {
		return this.fetchCatalogBoByIdAndMktTypeAndShowType(catId,
				MktTypeEnum.XIU, type);
	}

	@Override
	public CatalogBo fetchCatalogBoTreeByIdForEbay(Integer catId) {
		return this.fetchCatalogBoByIdAndMktTypeAndShowType(catId,
				MktTypeEnum.EBAY, null);
	}

	public CatalogBo fetchCatalogBoTreeByIdFromDisylay12(Integer catId,
			MktTypeEnum mktType, ItemShowTypeEnum showType) {
		return this.fetchCatalogBoByIdAndMktTypeAndShowType(catId, mktType,
				showType);
	}

	@Override
	public List<CatalogBo> fetchCatalogBoTreeListForEbay(FacetField facetField,
			Integer catId) {
		return this.fetchCatalogBoListByCatalogFacetFieldAndCatalogId(
				facetField, catId, MktTypeEnum.EBAY);
	}

	@Override
	public List<CatalogBo> fetchCatalogBoTreeListForXiu(FacetField facetField, Integer catId) {
		List<CatalogBo> catalogs = this.fetchCatalogBoListByCatalogFacetFieldAndCatalogId(
				facetField, catId, MktTypeEnum.XIU);
		return catalogs;
	}

	/**
	 * @return
	 */
	private List<CatalogBo> fetchCatalogBoListByCatalogFacetFieldAndCatalogId(
			FacetField facetField, Integer catId, MktTypeEnum mktType) {
		List<Count> cList = facetField.getValues();
		Map<Integer, CatalogBo> map = new HashMap<Integer, CatalogBo>(); // 用于保存遍历过的节点
		List<CatalogBo> catalogBolist = new ArrayList<CatalogBo>();
		if (cList == null || cList.size() <= 0) {
			return null;
		}
		List<Integer> selectIds = null;
		if (catId != null) {
			// 取得当前被选中的运营分类
			CatalogModel cm = this.fetchCatalogModelNoteById(catId, mktType, ItemShowTypeEnum.DSP12);
			
			if (cm != null) {
				selectIds = new ArrayList<Integer>(3);
				selectIds.add(cm.getCatalogId());
				// 获得顶级分类Model，以及选中的分类树的ID
				while (cm.getParentCatalogId() > 0) {
					cm = this.fetchCatalogModelNoteById(
							cm.getParentCatalogId(), mktType,
							ItemShowTypeEnum.DSP12);
					if (cm == null) {
						selectIds = null;
						break;
					}
					selectIds.add(cm.getCatalogId());
				}
				cm = null;
			}
		}
		String countName;
		long itemCount;
		for (Count count : cList) {
			countName = count.getName();
			itemCount = count.getCount();
			Integer i = null;
			if (StringUtils.isNumeric(countName)) {
				i = Integer.parseInt(count.getName());
			}
			if (i == null || i.intValue() <= 0) {
				continue;
			}
			// 如果map 包含当前节点,则不作任何操作，继续下一个。
			if (map.containsKey(i)) {
				continue;
			}
			CatalogModel cm1 = this.fetchCatalogModelNoteById(i, mktType,
					ItemShowTypeEnum.DSP12);// this.fetchCatalogModelNoteById(i,mktType
											// ,null);
			if (cm1 == null) {
				continue;
			}
			CatalogBo boTemp1 = new CatalogBo();
			if (selectIds != null) {
				boTemp1.setSelected(selectIds.contains(i));
			}
			boTemp1.setCatalogId(cm1.getCatalogId());
			boTemp1.setCatalogName(cm1.getCatalogName());
			boTemp1.setImg(cm1.getImg());
			boTemp1.setItemCount(itemCount);
			boTemp1.setParentCatalogId(cm1.getParentCatalogId());
			boTemp1.setDisplay(cm1.getDisplay());
			// 当前map不包含当前节点
			map.put(boTemp1.getCatalogId(), boTemp1);

			// 如果该节点有父类节点，则继续取得父类
			if (cm1.getParentCatalogId() != 0) {
				// 如果当前的MAP中包含该集合的父类，则直接将该类加载在其父类下
				if (map.containsKey(cm1.getParentCatalogId())) {
					map.get(cm1.getParentCatalogId()).addChildCatalog(boTemp1);
					continue;
				}
				CatalogModel cm2 = this.fetchCatalogModelNoteById(
						cm1.getParentCatalogId(), mktType,
						ItemShowTypeEnum.DSP12);
				if (null == cm2) {
					continue;
				}
					
				CatalogBo boTemp2 = new CatalogBo();
				if (selectIds != null) {
					boTemp2.setSelected(selectIds.contains(i));
				}
				boTemp2.addChildCatalog(boTemp1);
				boTemp2.setCatalogId(cm2.getCatalogId());
				boTemp2.setCatalogName(cm2.getCatalogName());
				boTemp2.setImg(cm2.getImg());
				boTemp2.setDisplay(cm2.getDisplay());
				map.put(cm2.getCatalogId(), boTemp2);
				if (cm2.getParentCatalogId() != 0) {
					if (map.containsKey(cm2.getParentCatalogId())) {
						map.get(cm2.getParentCatalogId()).addChildCatalog(
								boTemp2);
						continue;
					}
					CatalogModel cm3 = this.fetchCatalogModelNoteById(
							cm2.getParentCatalogId(), mktType,
							ItemShowTypeEnum.DSP12);
					if (null == cm3)
						continue;
					CatalogBo boTemp3 = new CatalogBo();
					if (selectIds != null) {
						boTemp3.setSelected(selectIds.contains(i));
					}
					boTemp3.setCatalogId(cm3.getCatalogId());
					boTemp3.setCatalogName(cm3.getCatalogName());
					boTemp3.addChildCatalog(boTemp2);
					boTemp3.setImg(cm3.getImg());
					boTemp3.setDisplay(cm3.getDisplay());
					map.put(cm3.getCatalogId(), boTemp3);
					catalogBolist.add(boTemp3);
				} else {
					catalogBolist.add(boTemp2);
				}
			} else {
				catalogBolist.add(boTemp1);
			}
		}
		if (map != null) {
			map.clear();
			map = null;
		}
		return catalogBolist;

	}

	/**
	 * 用于查询某个分类树的Bo； showType只作用于MKTType = Xiu的情况
	 * @param catId
	 * @param mktType
	 * @param showType
	 * @return
	 */
	private CatalogBo fetchCatalogBoByIdAndMktTypeAndShowType(Integer catId,
			MktTypeEnum mktType, ItemShowTypeEnum showType) {
		CatalogModel cm = this.fetchCatalogModelNoteById(catId, mktType,
				showType);
		if (cm == null)
			return null;
		List<Integer> selectIds = new ArrayList<Integer>(3);
		selectIds.add(cm.getCatalogId());
		// 获得顶级分类Model，以及选中的分类树的ID
		while (cm.getParentCatalogId() > 0) {
			cm = this.fetchCatalogModelNoteById(cm.getParentCatalogId(),
					mktType, showType);
			if (cm == null)
				return null;
			selectIds.add(cm.getCatalogId());
		}
		CatalogBo catalogBo = new CatalogBo();// 一级
		catalogBo.setCatalogId(cm.getCatalogId());
		catalogBo.setCatalogName(cm.getCatalogName());
		catalogBo.setImg(cm.getImg());
		catalogBo.setParentCatalogId(cm.getParentCatalogId());
		catalogBo.setItemCount(cm.getItemCount());
		catalogBo.setRank(cm.getSortNum());
		catalogBo.setSelected(true);
		catalogBo.setDisplay(cm.getDisplay());
		if (CollectionUtils.isNotEmpty(cm.getChildIdList())) {
			// 遍历二级分类
			CatalogBo boTemp2, boTemp3;
			CatalogModel cmTemp2, cmTemp3;
			for (Integer cId2 : cm.getChildIdList()) {
				cmTemp2 = this.fetchCatalogModelNoteById(cId2, mktType, showType);
				if (cmTemp2 == null) {
					continue;
				}
				
				boTemp2 = new CatalogBo();
				boTemp2.setCatalogId(cmTemp2.getCatalogId());
				boTemp2.setCatalogName(cmTemp2.getCatalogName());
				boTemp2.setSelected(selectIds.contains(cId2));
				boTemp2.setImg(cmTemp2.getImg());
				boTemp2.setItemCount(cmTemp2.getItemCount());
				boTemp2.setRank(cmTemp2.getSortNum());
				boTemp2.setDisplay(cmTemp2.getDisplay());
				catalogBo.addChildCatalog(boTemp2);
				if (CollectionUtils.isNotEmpty(cmTemp2.getChildIdList())) {
					// 遍历三级分类
					for (Integer cId3 : cmTemp2.getChildIdList()) {
						cmTemp3 = this.fetchCatalogModelNoteById(cId3, mktType,
								showType);
						if (cmTemp3 == null)
							continue;
						boTemp3 = new CatalogBo();
						boTemp3.setCatalogId(cmTemp3.getCatalogId());
						boTemp3.setCatalogName(cmTemp3.getCatalogName());
						boTemp3.setSelected(selectIds.contains(cId3));
						boTemp3.setImg(cmTemp3.getImg());
						boTemp3.setItemCount(cmTemp3.getItemCount());
						boTemp3.setRank(cmTemp3.getSortNum());
						boTemp3.setDisplay(cmTemp3.getDisplay());
						boTemp2.addChildCatalog(boTemp3);
					}
					Collections.sort(boTemp2.getChildCatalog());
				}
			}
			boTemp2 = null;
			boTemp3 = null;
			cmTemp2 = null;
			cmTemp3 = null;
			//对子级排序
			Collections.sort(catalogBo.getChildCatalog());
		}
		return catalogBo;
	}

	public CatalogModel fetchCatalogModelNoteById(Integer catId,
			MktTypeEnum mktType, ItemShowTypeEnum showType) {
		if (catId == null)
			return null;
		CatalogModel cm = null;
		// 从缓存中 获取 category model
		if (showType == null) {
			cm = CatalogTreeCache.getInstance().getTreeNodeById(catId.toString());
		} else {
			cm = CatalogTreeCache.getInstance().getTreeNodeById(catId.toString(),showType);
			//cm = CatalogTreeCache.getInstance().getTreeNodeById(catId.toString());
		}
		 
		if (cm == null) {
			return null;
		}
		// 判断MKT 类型
		if (mktType != null && cm.getMktType() != mktType.getType())
			return null;
		return cm;
	}
	
	/**
	 * 清除不显示的运营分类树节点
	 * 1.第三级节点（放开限制）
	 * 2.没有子节点的一级节点
	 * 3.促销分类ID=215269，100000220的节点
	 * @param catalogList
	 */
	@Override
	public void removeHiddenCatalog(List<CatalogBo> catalogList) {
		for(int i = 0; i < catalogList.size(); i++) {
			CatalogBo catalogOne = catalogList.get(i);
			
			// 1.去除促销分类ID
			// 2.没有子节点的一级节点
			if((215269 == catalogOne.getCatalogId() || 100000220 == catalogOne.getCatalogId()) || 
					(0 == catalogOne.getParentCatalogId() && CollectionUtils.isEmpty(catalogOne.getChildCatalog()))){
				catalogList.remove(catalogOne);
				i--;
				continue;
			}
			
			// 去除三级分类
			/* for (CatalogBo catalogTwo : catalogOne.getChildCatalog()) {
				catalogTwo.setChildCatalog(null);
			}*/
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CatalogModel> listFirstLevelCatlogs() {
		List<CatalogModel> catalogs = CatalogTreeCache.getInstance().getTree();
		if (catalogs == null) {
			return Collections.EMPTY_LIST;
		}
		List<CatalogModel> results = new ArrayList<CatalogModel>();
		for (Iterator<CatalogModel> iter = catalogs.iterator(); iter.hasNext();) {
			CatalogModel temp = iter.next();
			if (temp.getParentCatalogId() == NumberConstant.NUM_0 && temp.getMktType() == Constants.XIUINDEX_MKTTYPE_XIU) {
				results.add(temp);
			}
		}
		Collections.sort(results);
		return results;
	}

	@Override
	public CatalogModel fetchSecondCatalogNoteById(int catId) {
		CatalogModel cm = this.fetchCatalogModelNoteById(catId, null, null);
		if(cm == null)
			return null;
		while(cm.getParentCatalogId()>0){
			cm = this.fetchCatalogModelNoteById(cm.getParentCatalogId(), null, null);
		}
		return cm;
	}

	@Override
	public String getSecondCatalogName(int catId) {
		CatalogModel cm = this.fetchSecondCatalogNoteById(catId);
		if(cm == null)
			return null;
		return cm.getCatalogName();
	}

	@Override
	public CatalogModel fetchThirdCatalogNoteById(int catId) {
		
		CatalogModel cm = this.fetchCatalogModelNoteById(catId, null,ItemShowTypeEnum.DSP12 );
		int i=0;
		if(cm == null)
			return null;
		while(cm.getParentCatalogId()>0){
			i++;
			cm = this.fetchCatalogModelNoteById(cm.getParentCatalogId(),null,ItemShowTypeEnum.DSP12);
		    if(i==2){
		    	if(logger.isInfoEnabled())
		    		logger.info("分类id:"+catId+"是第三级分类");
		       return null;
		    }
		}
		return cm;
	}

}
