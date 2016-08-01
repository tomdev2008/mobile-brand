package com.xiu.mobile.portal.service;

import java.util.List;
import java.util.Map;

import com.xiu.commerce.hessian.model.Product;
import com.xiu.csp.facade.dto.SysParamDTO;
import com.xiu.mobile.core.model.DataBrandBo;
import com.xiu.mobile.portal.model.GoodsCategoryVO;
import com.xiu.mobile.portal.model.OrderInvoiceVO;
import com.xiu.mobile.portal.model.Topic;
import com.xiu.mobile.portal.model.TopicCategoryVO;
import com.xiu.uuc.facade.dto.UserDetailDTO;

/**
 * Memcached缓存Service
 * @author coco.long
 * @time	2015-08-20
 */
public interface IMemcachedService {

	//获取所有省市区参数信息缓存
	public List<SysParamDTO> getAllRegionParamInfo();
	
	//添加所有省市区参数信息缓存
	public void addAllRegionParamCache(Map map);
	
	//获取全部品牌信息缓存
	public List<Object> findAllBrands();
	//添加全部品牌缓存
	public void addAllBrands(Map map);
	//获取卖场缓存
	public Topic getTopicCached(String key);
	
	//获取卖场下的分类尺码缓存
	public List<TopicCategoryVO> getTopicCategoryList(String key); 
	
	//获取卖场下商品的分类尺码缓存
	public List<GoodsCategoryVO> getGoodsCategoryList(String key);
	
	//添加卖场下的分类尺码缓存
	public void addTopicCategoryCache(Map map);
	
	//添加卖场下商品的分类尺码缓存
	public void addGoodsCategoryCache(Map map);
	
	//删除卖场的分类尺码和卖场下商品的分类尺码缓存
	public void deleteTopicAndGoodsCategoryCache(Map map);
	
	//添加发票类型列表
	public void addInvoiceTypesCache(Map map);
	
	//获取商品发票类型
	public List<OrderInvoiceVO> getInvoiceTypesCache(String key);
	
	/**
	 * 添加用户明细
	 * @param map
	 */
	public void addUserDetailCache(Map map);
	/**
	 * 获取用户明细
	 * @param key
	 * @return
	 */
	public UserDetailDTO getUserDetailCache(String key);
	
	
	/**
	 * 添加商品明细
	 * @param map
	 */
	public void addProductCache(Product pro);
	/**
	 * 获取商品明细
	 * @param key
	 * @return
	 */
	public Product getProductCache(String goodSn);
	
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
	
	
	public String getH5ModuleHtml(String key);
}
