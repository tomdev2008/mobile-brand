package com.xiu.mobile.brand.web.cache;

import java.util.Map;

import com.xiu.mobile.brand.web.cache.impl.XiuAttrGroupCacheImpl;
import com.xiu.mobile.brand.web.model.AttrGroupJsonModel;

/**
 * 属性项ID缓存类接口
 * @author Willian.zhang		20130705
 *
 */
public abstract class XiuAttrGroupCache {
	
	private static XiuAttrGroupCache instance;
	
	public static XiuAttrGroupCache getInstance() {
		if (instance == null) {
			instance = new XiuAttrGroupCacheImpl();
		}
		return instance;
	}
	/**
	 * 初始化属性项数据
	 */
	public abstract void init();
	/**
	 * 重新加载属性项数据
	 */
	public abstract void reLoadAttr();
	/**
	 * 重新加载属性项值顺序
	 */
	public abstract void reLoadAttrValue();
	/**
	 * 根据属性项ID，取得对应的属性项
	 * @param categoryId
	 * @return
	 */
	public abstract Map<String, AttrGroupJsonModel> selectByPrimaryKeyForJson(Long categoryId);
	/**
	 * 根据属性项ID，取得对应的属性项
	 * @param attrGroupId
	 * @return
	 */
	public abstract Integer selectAttrValueForId(Long id);
}
