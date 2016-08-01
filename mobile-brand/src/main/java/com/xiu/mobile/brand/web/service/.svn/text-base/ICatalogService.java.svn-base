/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-9 下午4:04:39 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.mobile.brand.web.service;

import java.util.List;

import org.apache.solr.client.solrj.response.FacetField;

import com.xiu.mobile.brand.web.bo.CatalogBo;
import com.xiu.mobile.brand.web.constants.ItemShowTypeEnum;
import com.xiu.mobile.brand.web.constants.MktTypeEnum;
import com.xiu.mobile.brand.web.model.CatalogModel;


/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-9 下午4:04:39 
 * ***************************************************************
 * </p>
 */

public interface ICatalogService {
	
	/**
	 * 从已选中的运营分类树中得到最末端选中的叶子节点
	 * 注意：selectedCatalogTree需要是已经选中树结构
	 * @see CatalogBo#isSelected()
	 * @param catalogBoList
	 * @return
	 */
	 CatalogBo getSelectedCatalogFromSelectedCatalogTree(CatalogBo selectedCatalogTree);
	
	/**
	 * 获取水平结构的选中运营分类
	 * List<CatalogBo> catalogBoList 是已经选中
	 * @see CatalogBo#isSelected()
	 * @param catalogBoList
	 * @return
	 */
	 List<CatalogBo> parsePlaneSelectCatalogBo(List<CatalogBo> catalogBoList);
	
	/**
	 * 删除已选中分类的 其他兄弟节点
	 * @param catalogBoList
	 */
	 void filterCatalogTreeDeleteUnSelectedSiblingsItem(List<CatalogBo> catalogBoList);
	
	/**
	 * Xiu走秀官网通过运营分类ID查询分类树结构
	 * 用于分类列表页获取一个分类树的业务逻辑
	 * @param id
	 * @return
	 */
	 CatalogBo fetchCatalogBoTreeByIdForXiu(Integer id,ItemShowTypeEnum type);
	
	/**
	 * EBAY通过运营分类ID查询分类树结构
	 * 用于分类列表页获取一个分类树的业务逻辑
	 * @param id
	 * @return
	 */
	 CatalogBo fetchCatalogBoTreeById(Integer id);
	
	/**
	 * EBAY通过运营分类ID查询分类树结构
	 * 用于分类列表页获取一个分类树的业务逻辑
	 * @param id
	 * @return
	 */
	 CatalogBo fetchCatalogBoTreeByIdForEbay(Integer id);
	
	/**
	 * 官网,EBAY通过运营分类ID查询分类树结构 display=1,2
	 * 用于分类列表页获取一个分类树的业务逻辑
	 * @param id
	 * @param mktType
	 * @param showType
	 * @return
	 */
	 CatalogBo fetchCatalogBoTreeByIdFromDisylay12(Integer id, MktTypeEnum mktType,ItemShowTypeEnum showType);
	
	/**
	 * XIU通过运营分类ID集合，获得运营分类
	 * 用于 搜索和品牌页面反查 分类树逻辑
	 */
	 List<CatalogBo> fetchCatalogBoTreeListForXiu(FacetField facetField,Integer selectedCatalogId);
	
	
	/**
	 * Ebay通过运营分类ID集合，获得运营分类
	 * 用于 搜索和品牌页面反查 分类树逻辑
	 */
	 List<CatalogBo> fetchCatalogBoTreeListForEbay(FacetField facetField,Integer selectedCatalogId);

	/**
	 * 清除不显示的运营分类树节点 
	 * 1.第三级节点 
	 * 2.没有子节点的一级节点 
	 * 3.促销分类ID=215269，100000220的节点
	 * @param catalogList
	 */
	void removeHiddenCatalog(List<CatalogBo> catalogList);
	 /**
	  * 查询一级分类的类目
	  * @return 一级分类集合
	  */
	List<CatalogModel> listFirstLevelCatlogs();
	/**
	 * 根据分类id查询分类对象
	 * @param catId
	 * @param mktType
	 * @param showType
	 * @return
	 */
	public CatalogModel fetchCatalogModelNoteById(Integer catId, MktTypeEnum mktType, ItemShowTypeEnum showType);
	
	/*
	 * 根据分类ID查询二级分类对象
	 * @param catId
	 */
	public CatalogModel fetchSecondCatalogNoteById(int catId);
	
	/*
	 * 根据分类id查询是否是三级分类
	 */
	public CatalogModel fetchThirdCatalogNoteById(int catId);
	
	/*
	 * 根据分类ID查询二级分类的名称
	 * @catId
	 */
	public String getSecondCatalogName(int catId);
}
