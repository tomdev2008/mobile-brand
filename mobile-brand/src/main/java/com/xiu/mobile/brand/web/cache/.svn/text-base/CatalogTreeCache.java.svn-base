/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-6 上午11:13:25 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.mobile.brand.web.cache;

import java.util.List;

import com.xiu.mobile.brand.web.cache.impl.CatalogTreeCacheImpl;
import com.xiu.mobile.brand.web.constants.ItemShowTypeEnum;
import com.xiu.mobile.brand.web.model.CatalogModel;

/** 
 * <p>
 * ************************************************************** 
 * @Description: 运营分类树缓存
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-6 上午11:13:25 
 * ***************************************************************
 * </p>
 */

public abstract class CatalogTreeCache {
	
	private static CatalogTreeCache instance;

	public static synchronized CatalogTreeCache getInstance() {
		if (instance == null) {
			instance = new CatalogTreeCacheImpl();
		}
		return instance;
	}
	
	/**
	 * 初始化数据，只有子类能调用
	 */
	protected abstract void init();
	
	/**
	 * 重载数据，只有子类能调用 
	 */
	protected abstract void reload();
	
	/**
	 * 从缓存中加载所有运营分类树
	 * @return
	 */
	public abstract List<CatalogModel> getTree();
	
	/**
	 * 从缓存中加载display12运营分类
	 * @return
	 */
	public abstract List<CatalogModel> getTree12();
	
	/**
	 * 根据运营分类ID从缓存中加载对应的运营分类对象
	 * @param catalogID
	 * @return
	 */
	public abstract CatalogModel getTreeNodeById(String catalogID);
	
	/**
	 * 根据运营分类ID从缓存中加载对应的运营分类对象(包括12)
	 * @param catalogID
	 * @return
	 */
	public abstract CatalogModel getTreeNodeById12(String catalogID);
	
	/**
	 * 根据运营分类ID从缓存中加载对应的运营分类对象  查询treeMap_0
	 * @param catalogID
	 * @return
	 */
	public abstract CatalogModel getTreeNodeByIdFromTreeMap0(String catalogID);
	
	/**
	 * 根据运营分类ID和ItemShowType从缓存中加载对应的运营分类对象
	 * @param catalogId
	 * @param itemShowTypeEnum
	 * @return
	 */
	public abstract CatalogModel getTreeNodeById(String catalogId,ItemShowTypeEnum itemShoTypeEnum);

}
