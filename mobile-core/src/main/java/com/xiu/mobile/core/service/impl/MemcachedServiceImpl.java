package com.xiu.mobile.core.service.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.danga.MemCached.MemCachedClient;
import com.xiu.mobile.core.service.IMemcachedMgtService;

/**
 * Memcached缓存Service
 * @author coco.long
 * @time	2015-08-20
 */
@Service("memcachedMgtService")
public class MemcachedServiceImpl implements IMemcachedMgtService {

	private Logger logger = Logger.getLogger(MemcachedServiceImpl.class);
	
	@Resource(name="memcachedClient")
	private MemCachedClient memcachedClient; 
	
	
	//缓存时效：1秒
	public static final int CACHE_EXP_SECOND = 1000;
	//缓存时效：1分钟
	public static final int CACHE_EXP_MINUTE = 60 * 1000;
	//缓存时效：半小时
	public static final int CACHE_EXP_HALF_HOUR = 1800 * 1000;
	//缓存时效：1小时
	public static final int CACHE_EXP_HOUR = 3600 * 1000;
	//缓存时效：1天
	public static final int CACHE_EXP_DAY = 24 * 3600 * 1000; 
	//缓存时效：1周
	public static final int CACHE_EXP_WEEK = 7 * 24 * 3600 * 1000;
	//缓存时效：1月
	public static final int CACHE_EXP_MONTH = 30 * 24 * 3600 * 1000; 
	//缓存时效：1年
	public static final int CACHE_EXP_YEAR = 365 * 24 * 3600 * 1000; 
	//缓存时效：永久
	public static final int CACHE_EXP_FOREVER = 0;
	
	/**
	 * 缓存对象
	 * @param key		缓存key
	 * @param value		缓存对象
	 * @param time		失效时间
	 */
	private void cacheObject(String key, Object value, long time) {
		try{
			memcachedClient.set(key, value, new Date(time));
		} catch (Exception e) {  
            logger.error(e.getMessage(), e);  
        }  
        logger.info("Cache Object: [" + key + "]"); 
	}
	
	/**
	 * 加载缓存对象
	 * @param key
	 * @return
	 */
	private Object loadObject(String key) {
		Object object = null;
		try {
			object = memcachedClient.get(key);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return object;
	}
	
	/**
	 * 删除缓存对象
	 * @param key
	 */
	private void deleteObject(String key) {
		try {
			memcachedClient.delete(key);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 获取卖场下的筛选缓存
	 * @param key
	 * @return
	 */
	public Map getTopicFilterList(String key){
		Map filterMap = (Map) loadObject(key);
		return filterMap;
	}
	
	/**
	 * 增加卖场下的筛选缓存
	 * @param key
	 * @param time 单位小时
	 */
	public void addTopicFilterList(String key,Map filterMap,Integer time){
		long timel = System.currentTimeMillis() + time*CACHE_EXP_HOUR;	//time小时后失效
		//缓存对象
		cacheObject(key, filterMap, timel);
	}
	
	//删除卖场下的筛选缓存
	public void deleteTopicFilterList(String key){
		deleteObject(key);
	}
	
	/**
	 * 获取卖场筛选字段名称
	 * @param key
	 * @return
	 */
	public Map getTopicFilterName(String key){
		Map filterMap = (Map) loadObject(key);
		return filterMap;
	}
	
	/**
	 * 增加卖场筛选字段名称缓存
	 * @param key
	 * @param time 单位小时
	 */
	public void addTopicFilterName(String key,Map nameMap,Integer time){
		long timel = System.currentTimeMillis() + time*CACHE_EXP_HOUR;	//time小时后失效
		//缓存对象
		cacheObject(key, nameMap, timel);
	}
	
	
	
}
