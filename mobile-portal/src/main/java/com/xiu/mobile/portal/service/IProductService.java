package com.xiu.mobile.portal.service;

import java.util.List;
import java.util.Map;

import com.xiu.commerce.hessian.model.Product;

/**
 * 产品相关业务逻辑
 * 将远程调用商品中心、渠道中心的逻辑封装在此
 * 
 * @author zany@buzheng.org
 *
 */
public interface IProductService {
	/**
	 * 查询商品详情
	 * @param goodsSn 商品编号
	 * @return 商品中心的Product对象
	 */
	public Product loadProduct(String goodsSn);
	
	/**
	 * 批量查询商品详情
	 * @param goodsSnBag 多个商品编号,以逗号(,)分隔
	 * @return 商品中心的Product对象的列表
	 */
	public List<Product> batchLoadProducts(String goodsSnBag);
	
	/**
	 * 商品中心老的接口
	 */
	public List<Product> batchLoadProductsOld(String goodsSnBag);
	
	/**
	 * 批量查询商品详情        商品中心轻量级接口（缓存接口）
	 * @param goodsSnBag 多个商品编号,以逗号(,)分隔
	 * @return 商品中心的Product对象的列表
	 */
	public List<Product> batchLoadProductsLight(String goodsSnBag);
	
	
	/**
	 * 根据sku查询库存
	 * @param sku
	 * @return 库存信息，出错则返回-9999
	 */
	public int queryInventoryBySku(String sku);
	
	/**
	 * 根据sku列表查询库存
	 * @param skuCodeRequestList
	 * @return 库存信息，出错则返回-9999
	 */
	public Map<String,Integer> queryInventoryBySkuList(List<String> skuCodeRequestList);
	
	
	/**
	 * 根据code列表查询库存
	 * @param codeRequestList
	 * @return 库存信息，出错则返回-9999
	 */
	public Map<String, Integer> queryInventoryByCodeList(List<String> codeRequestList);
}
