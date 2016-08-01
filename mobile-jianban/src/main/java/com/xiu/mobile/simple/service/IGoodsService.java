package com.xiu.mobile.simple.service;

import java.util.List;
import java.util.Map;

import com.xiu.commerce.hessian.model.Product;
import com.xiu.mobile.simple.model.GoodsDetailBo;
import com.xiu.mobile.simple.model.GoodsDetailVo;
import com.xiu.mobile.simple.model.GoodsSkuItem;
import com.xiu.mobile.simple.model.OrderGoodsVO;
import com.xiu.sales.common.itemsettle.dataobject.ItemSettleResultDO;


public interface IGoodsService {
	/**
	 * 加载走秀商品详情
	 * @return
	 * @throws Exception
	 */
	GoodsDetailBo loadXiuGoodsDetail(String goodsSn, Map<String, String> params) throws Exception;

	/**
	 * 查看商品详情
	 * @param goodsSn
	 * @param params
	 * @return
	 * @throws Exception
	 */
	GoodsDetailVo viewGoodsDetail(String goodsSn, Map<String, String> params) throws Exception;

	/**
	 * 返回购物车的图片
	 * @param gd
	 * @param skuItem
	 * @return
	 */
	String getShopcartImg(GoodsDetailBo gd, GoodsSkuItem skuItem);

	/**
	 * 立刻购买
	 * @param goodsSn
	 * @param sku
	 * @param quantity
	 * @return
	 * @throws Exception
	 */
	List<OrderGoodsVO> buyDirect(String goodsSn, String sku, int quantity, Map<String, String> map) throws Exception;

	/**
	 * 获取订单商品列表
	 * @param goodsSn
	 * @param sku
	 * @param quantity
	 * @param map
	 * @return List<OrderGoodsVO>
	 * @throws Exception
	 */
	List<OrderGoodsVO> getOrderGoodsList(String goodsSn, String sku, int quantity, Map<String, String> map) throws Exception;

	/**
	 * 返回剩余时间结果
	 * @param goodsId
	 * @param goodsSn
	 * @return
	 */
	String getRestTime(String goodsId, String goodsSn);

	/**
	 * 获取SKU的JSON格式信息
	 * @param goodsId
	 * @param sku
	 * @return
	 * @throws Exception
	 */
	String getGoodsSkuJson(String goodsId, String sku, Map<String, String> params) throws Exception;

	/**
	 * 
	 * 根据goodsId查询goodsSn
	 * 
	 * @param goodsId
	 * @return goodsSn
	 * @throws Exception
	 */
	String getGoodsSnByGoodsId(String goodsId) throws Exception;
	/**
	 * 根据走秀码对应的商品价格
	 * @param goodsSn
	 * @return
	 */
	Double getZxPrice(String goodsSn)throws Exception;

	/**
	 * 更加走秀码获得品牌id
	 * @param goodsSn
	 * @return
	 */
	Long getBrandIdByGoodsSn(String goodsSn)throws Exception;
	
	/***
	 * 通过走秀码获取产品信息
	 */
	public List<Product> batchLoadProductsOld(String goodsSnBag) ;
	
	
	/**
	 * 查询库存信息
	 * @param sku
	 * @return
	 */
	public int queryInventoryBySku(String skuCode);
	
	
	/**
	 * 根据商品信息获取营销中心的商品价格
	 * @param product
	 * @return
	 */
	public Double getZxPrice(Product product);
}
