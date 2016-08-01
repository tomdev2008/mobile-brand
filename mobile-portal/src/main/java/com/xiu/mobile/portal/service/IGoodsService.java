package com.xiu.mobile.portal.service;

import java.util.List;
import java.util.Map;

import com.xiu.commerce.hessian.model.Product;
import com.xiu.mobile.portal.model.GoodsDetailBo;
import com.xiu.mobile.portal.model.GoodsDetailVo;
import com.xiu.mobile.portal.model.GoodsDetailsVO;
import com.xiu.mobile.portal.model.GoodsSkuItem;
import com.xiu.mobile.portal.model.GoodsVo;
import com.xiu.mobile.portal.model.OrderGoodsVO;
import com.xiu.mobile.portal.model.ShoppingCartGoodsVo;


public interface IGoodsService {
	/**
	 * 加载走秀商品详情
	 * @return
	 * @throws Exception
	 */
	GoodsDetailBo loadXiuGoodsDetail(String goodsSn, Map<String, String> params) throws Exception;
	
	/**
	 * 加载走秀商品详情列表
	 * @param goodsSn
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<GoodsDetailBo> getGoodsDetailBoList(String goodsSnStr, Map<String, String> params) throws Exception;
	
	/**
	 * 加载走秀商品详情列表
	 * @param goodsSn
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<GoodsDetailBo> getShoppingCartGoodsDetailBoList(String goodsSnStr, Map<String, String> params) throws Exception;

	/**
	 * 查看商品详情
	 * @param goodsSn
	 * @param params
	 * @return
	 * @throws Exception
	 */
	GoodsDetailVo viewGoodsDetail(String goodsSn, Map<String, String> params) throws Exception;
	
	/**
	 * 获取商品详情列表
	 * @param goodsSn
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<GoodsDetailVo> getGoodsDetailList(String goodsSnStr, Map<String, String> params) throws Exception;
	
	/**
	 * 参数转换
	 * @param goodsSn
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<GoodsDetailVo> getGoodsDetailList(List<GoodsDetailBo> goodsDetailBoList) throws Exception;

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
	//String getGoodsSkuJson(String goodsId, String sku, Map<String, String> params) throws Exception;

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
	
	/***
	 * 通过goodsSn查询sku、颜色尺寸相关列表信息
	 * @param goodsSn
	 * @return
	 */
	public List<GoodsSkuItem> getSkuItemsByGoodsSn(String goodsSn);
	
	/***
	 * 通过goodsSn,goodsSku查询sku、颜色尺寸详细信息
	 * @param goodsSn
	 * @param goodsSku
	 * @return
	 */
	public GoodsSkuItem getSkuItemByGoodsSnAndSku(String goodsSn,String goodsSku);
	/**
	 * 查询购物车商品信息
	 * @param shoppingCartGoodsList
	 * @param map
	 * @return
	 */
	public List<OrderGoodsVO> getShoppingCartGoodsList(List<ShoppingCartGoodsVo> shoppingCartGoodsList, Map<String, String> map)throws Exception;

	
	/**
	 * 构造购物车订单商品信息
	 * @param shoppingCartGoodsList
	 * @param goodsDetailBoList
	 * @return
	 */
	public List<OrderGoodsVO> getOrderGoodsList(List<ShoppingCartGoodsVo> shoppingCartGoodsList, List<GoodsDetailBo> goodsDetailBoList)throws Exception;

	/**
	 * 查询供应商显示名
	 * @param supplierCode
	 * @return
	 */
	public String getSupplierDisplayName(String supplierCode)throws Exception;
	
	/**
	 * 查询是否海外商品
	 * @param goodsSn
	 * @return
	 */
	public boolean isGoodsCustoms(String goodsSn);
	
	/**
	 * 查询商品限购数量，-1表示不限购
	 * @param goodsSn
	 * @return
	 */
	public int getGoodsLimitSaleNum(String goodsSn);
	
	/**
	 * 查询商品限购数量，-1表示不限购，查询用户的历史订单
	 * @param goodsSn
	 * @param userId
	 * @return
	 */
	public int getGoodsLimitSaleNum(String goodsSn, String userId);
	
	/**
	 * 查询商品的限购信息
	 * @param map
	 * @return
	 */
	public Map getOrderLimitSaleInfo(Map map);
	
	/**
	 * 查询商品限购信息
	 * @param goodsSn
	 * @return
	 */
	public Map getGoodsLimitSaleInfo(String goodsSn);
	
	/**
	 * 查询商品是否是礼品包装商品
	 * @param goodsId
	 * @return
	 */
	public boolean isPackagingProduct(String goodsId);
	
	/**
	 * 查询商品是否支持礼品包装
	 * @param goodsSn
	 * @return
	 */
	public boolean isProductSupportWrap(String goodsSn);
	
	/**
	 * 查询礼品包装价格
	 * @return
	 */
	public String getProductPackagingPrice();
	
	/**
	 * 查询礼品包装商品信息：商品ID、商品走秀码、sku码
	 * @return
	 */
	public Map getProductPackagingGoods();
	
	/**
	 * 查询商品详情
	 * @param map
	 * @return
	 */
	public Map<String, Object> getGoodsDetail(Map<String, Object> map);
	
	
	/**
	 * 查询商品
	 * @param goodsSn
	 * @return
	 */
	public OrderGoodsVO getOrderGoods(String goodsSn, Map map);
	
	/**
	 * 查询商品上传身份证状态：0.必须 1.需要但是不强制 2.不需要
	 * @param goodsId
	 * @return
	 */
	public int getGoodsUploadIdCardByGoodsId(String goodsId);
	
	/**
	 * 查询商品上传身份证状态：0.必须 1.需要但是不强制2.不需要
	 * @param goodsSn
	 * @return
	 */
	public int getGoodsUploadIdCardByGoodsSn(String goodsSn);
	
	/**
	 * 添加商品求购信息
	 * @param map
	 * @return
	 */
	public Map<String, Object> addAskBuyInfo(Map map);
	
	/**
	 * 一品多商：加购物车或立即购买时判断是否有库存
	 * @param goodsSn
	 * @param goodsSku
	 * @return
	 */
	public Map<String, Object> checkStock(String goodsSn, String goodsSku);
	
	
	
	/**
	 * 查询商品信息（少字段）
	 */
	public Product getGoodsInfoSimpleByGoodSn(String goodsSn);
	/**
	 * 获取商品价格，调用一品多商接口
	 */
	public GoodsVo getGoodsPrice(String goodsSn);
	
	
	/**
	 * 批量查询商品是否支持礼品包装
	 * @param goodsSn
	 * @return
	 */
	public Map<String,Boolean> isProductSupportWrapBySnList(List goodsSns);
	
}
