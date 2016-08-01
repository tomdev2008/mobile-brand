package com.xiu.mobile.brand.web.cache;

import com.xiu.mobile.brand.web.cache.impl.XiuAttrGroupInfoCacheImpl;


public abstract class XiuAttrGroupInfoCache {

	private static XiuAttrGroupInfoCache instance;
	
	public static XiuAttrGroupInfoCache getInstance(){
		if(instance==null){
			instance = new XiuAttrGroupInfoCacheImpl();
		}
		return instance;
	}
	
	/**
	 * 初始化属性项名称数据
	 */
	protected abstract void init();
	/**
	 * 根据属性项ID获取数属项名称数据
	 */
	public abstract String getAttrGrouNameByID(String id);
	
	/**
	 * @Description: 获取属性项名称缓存大小
	 * @return    
	 * @return int
	 */
	protected abstract int getCacheSize();
}
