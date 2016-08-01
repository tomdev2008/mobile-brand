package com.xiu.mobile.core.service;

import java.util.Map;

/**
 * Memcached缓存Service
 * @author coco.long
 * @time	2015-08-20
 */
public interface IMemcachedMgtService {

	/**
	 * 获取卖场筛选字段名称
	 * @param key
	 * @return
	 */
	public Map getTopicFilterName(String key); 
	
	/**
	 * 增加卖场筛选字段名称缓存
	 * @param key
	 * @param time 单位小时
	 */
	public void addTopicFilterName(String key,Map nameMap,Integer time); 
	/**
	 * 获取卖场下的筛选缓存
	 * @param key
	 * @return
	 */
	public Map getTopicFilterList(String key); 
	
	/**
	 * 增加卖场下的筛选缓存
	 * @param key
	 * @param time 单位小时
	 */
	public void addTopicFilterList(String key,Map filterMap,Integer time); 
	

	//删除卖场下的筛选缓存
	public void deleteTopicFilterList(String key);
	
	
	
}
