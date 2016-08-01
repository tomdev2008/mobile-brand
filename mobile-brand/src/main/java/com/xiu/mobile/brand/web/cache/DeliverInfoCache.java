package com.xiu.mobile.brand.web.cache;

import com.xiu.mobile.brand.web.cache.impl.DeliverInfoCacheImpl;
import com.xiu.mobile.brand.web.dao.model.DeliverModel;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2015-4-2 下午6:01:25 
 * ***************************************************************
 * </p>
 */

public abstract class DeliverInfoCache {
	
	private static DeliverInfoCache instance = new DeliverInfoCacheImpl();

	public static DeliverInfoCache getInstance() {
		return instance;
	}
	/**
	 * 初始化数据
	 */
	public abstract void init();
	/**
	 * 重新加载数据
	 */
	public abstract void reLoad();
	
	/**
	 * 通过code获取发货地信息
	 * @param code
	 * @return
	 */
	public abstract DeliverModel getDeliverModel(Integer code);
	
}
