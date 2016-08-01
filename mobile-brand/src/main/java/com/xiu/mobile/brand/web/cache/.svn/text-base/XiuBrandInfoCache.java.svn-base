package com.xiu.mobile.brand.web.cache;

import java.util.HashMap;

import com.xiu.mobile.brand.web.cache.impl.XiuBrandInfoCacheImpl;
import com.xiu.mobile.brand.web.dao.model.XBrandModel;

public abstract class XiuBrandInfoCache {
	
	private static XiuBrandInfoCache instance;
	
	public static XiuBrandInfoCache getInstance(){
		if(instance == null) {
			instance = new XiuBrandInfoCacheImpl();
		}
			
		return instance;
	}
	
	protected abstract void init();
	
	/**
	 * 通过ID获取品牌信息
	 * @param brandId
	 * @return
	 */
	public abstract XBrandModel getBrandById(Long brandId);
	
	/**
	 * 通过名称获取名牌信息
	 * @param name
	 *        名牌中文名 or 品牌英文名
	 * @return
	 */
	public abstract XBrandModel getBrandHasGoodsByName(String name);
	
	/**
	 * 所有xiu_goods品牌
	 * @return
	 */
	public abstract HashMap<Long, XBrandModel> getBrandIDs();
}
