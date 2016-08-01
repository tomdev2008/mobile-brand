package com.xiu.mobile.portal.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.danga.MemCached.MemCachedClient;
import com.xiu.commerce.hessian.model.Product;
import com.xiu.csp.facade.dto.SysParamDTO;
import com.xiu.mobile.core.model.DataBrandBo;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.ei.EIUUCManager;
import com.xiu.mobile.portal.model.GoodsCategoryVO;
import com.xiu.mobile.portal.model.OrderInvoiceVO;
import com.xiu.mobile.portal.model.Topic;
import com.xiu.mobile.portal.model.TopicCategoryVO;
import com.xiu.mobile.portal.service.IMemcachedService;
import com.xiu.uuc.facade.dto.UserDetailDTO;

/**
 * Memcached缓存Service
 * @author coco.long
 * @time	2015-08-20
 */
@Transactional()
@Service("memcachedService")
public class MemcachedServiceImpl implements IMemcachedService {

	private Logger logger = Logger.getLogger(MemcachedServiceImpl.class);
	
	@Resource(name="memcachedClient")
	private MemCachedClient memcachedClient;
	
	
	@Autowired
	private EIUUCManager eiuucManager;
	
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
	 * 获取所有省市区参数信息缓存
	 */
	public List<SysParamDTO> getAllRegionParamInfo() {
		List<SysParamDTO> list = (List<SysParamDTO>) loadObject(XiuConstant.MOBILE_MEMCACHED_ALL_REGIONPARAM);
		return list;
	}

	/**
	 * 添加所有省市区参数信息缓存
	 */
	public void addAllRegionParamCache(Map map) {
		String key = XiuConstant.MOBILE_MEMCACHED_ALL_REGIONPARAM;
		Object value = map.get(key);
		long time = System.currentTimeMillis() + CACHE_EXP_YEAR;		//1年后失效
		
		//缓存对象
		cacheObject(key, value, time);
	}
	//获取全部品牌信息缓存
	public List<Object> findAllBrands(){
		List<Object> list = (List<Object>) loadObject(XiuConstant.BRAND_ALL);
		return list;
	}
	//添加全部品牌缓存
	public void addAllBrands(Map map){
		String key = XiuConstant.BRAND_ALL;
		Object value = map.get(key);
		long time = System.currentTimeMillis() + CACHE_EXP_DAY;		//1天后失效
		
		//缓存对象
		cacheObject(key, value, time);
	}
	/**
	 * 获取卖场缓存
	 */
	public Topic getTopicCached(String key) {
		Topic topic = (Topic) loadObject(key);
		return topic;
	}

	/**
	 * 获取商品分类缓存
	 */
	public List<TopicCategoryVO> getTopicCategoryList(String key) {
		List<TopicCategoryVO> list = (List<TopicCategoryVO>) loadObject(key);
		return list;
	}

	/**
	 * 获取卖场下的商品
	 */
	public List<GoodsCategoryVO> getGoodsCategoryList(String key) {
		List<GoodsCategoryVO> list = (List<GoodsCategoryVO>) loadObject(key);
		return list;
	}

	/**
	 * 添加卖场分类尺码缓存
	 */
	public void addTopicCategoryCache(Map map) {
		String key = (String) map.get("key");
		Object value = map.get(key);
		long time = System.currentTimeMillis() + CACHE_EXP_HOUR;	//1小时后失效
		
		//缓存对象
		cacheObject(key, value, time);
	}

	/**
	 * 添加卖场下商品的分类尺码缓存
	 */
	public void addGoodsCategoryCache(Map map) {
		String key = (String) map.get("key");
		Object value = map.get(key);
		long time = System.currentTimeMillis() + CACHE_EXP_HOUR;	//1小时后失效
		
		//缓存对象
		cacheObject(key, value, time);
	}

	/**
	 * 删除卖场下的分类尺码和卖场下商品的分类尺码缓存
	 */
	public void deleteTopicAndGoodsCategoryCache(Map map) {
		String topicKey = (String) map.get("topicKey");
		String goodsKey = (String) map.get("goodsKey");
		
		//删除缓存
		deleteObject(topicKey);
		deleteObject(goodsKey);
	}

	/**
	 * 添加发票类型列表
	 */
	public void addInvoiceTypesCache(Map map) {
		String key = (String) map.get("key");
		Object value = map.get("value");
		long time = System.currentTimeMillis() + CACHE_EXP_HOUR;	//1小时后失效
		
		//缓存对象
		cacheObject(key, value, time);
	}
	/**
	 * 获取发票类型列表
	 */
	public List<OrderInvoiceVO> getInvoiceTypesCache(String key) {
		List<OrderInvoiceVO> list = (List<OrderInvoiceVO>) loadObject(key);
		return list;
	}
	
	/**
	 * 添加用户明细缓存
	 * @param userId
	 */
	public void addUserDetail(Long userId){
		UserDetailDTO userDetailDTO =eiuucManager.getUserDetailDTOByUserId(userId);
		String key=XiuConstant.USER_DETAIL_CACHE_KEY+ userId;
        long time = System.currentTimeMillis() + CACHE_EXP_HOUR;	//1小时后失效
		//缓存对象
		cacheObject(key, userDetailDTO, time);
	}
	/**
	 * 添加用户明细缓存
	 * @param userId
	 */
	public void getUserDetail(Long userId){
		UserDetailDTO userDetailDTO =eiuucManager.getUserDetailDTOByUserId(userId);
		String key=XiuConstant.USER_DETAIL_CACHE_KEY+ userId;
		long time = System.currentTimeMillis() + CACHE_EXP_HOUR;	//1小时后失效
		//缓存对象
		cacheObject(key, userDetailDTO, time);
	}
	
	
	/**
	 * 添加用户明细
	 * @param map
	 */
	public void addUserDetailCache(Map map) {
		String key = (String) map.get("key");
		Object value = map.get("value");
		long time = System.currentTimeMillis() + CACHE_EXP_HOUR;	//1小时后失效
		//缓存对象
		cacheObject(key, value, time);
	}
	/**
	 * 获取用户明细
	 * @param key
	 * @return
	 */
	public UserDetailDTO getUserDetailCache(String key) {
		UserDetailDTO userDto = (UserDetailDTO) loadObject(key);
		return userDto;
	}
	
	

	/**
	 * 添加商品明细
	 * @param map
	 */
	public void addProductCache(Product pro) {
//		String key = XiuConstant.CACHE_PRODUCT_CACHE_KEY+pro.getXiuSN();
//		long time = System.currentTimeMillis() + 10*CACHE_EXP_HOUR;	//10分钟后
//		//缓存对象
//		cacheObject(key, pro, time);
	}
	/**
	 * 获取商品明细
	 * @param key
	 * @return
	 */
	public Product getProductCache(String goodSn) {
//		String key= XiuConstant.CACHE_PRODUCT_CACHE_KEY+goodSn;
//		Product productDto = (Product) loadObject(key);
//		return productDto;
		return null;
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

	@Override
	public String getH5ModuleHtml(String key) {
		
		return (String) loadObject(key);
	}
	
	
	
}
