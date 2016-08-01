package com.xiu.mobile.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xiu.mobile.core.model.BrandInfoVO;
import com.xiu.mobile.core.model.DeliverInfo;
import com.xiu.mobile.core.model.Goods;


/**
 * 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 商品中心商品信息
 * @AUTHOR : chenghong.ding@xiu.com 
 * @DATE :2014年6月9日 下午7:04:20
 * </p>
 ****************************************************************
 */
public interface GoodsDao {
	
	/**
	 * 根据走秀码查询商品信息
	 * @param sn
	 * @return
	 */
	Goods getGoodsBySn(String sn);
	/**
	 * 根据商品ID查询商品信息
	 * @param productId
	 * @return
	 */
	Goods getGoodsById(Long productId);
	
	/**
	 * 去除商品中心表中不存在的记录
	 * @param goodsSn
	 * @return
	 */
	List<String> checkGoodsSn(@Param(value="goodsSn")List<String> goodsSn);
	
	String getGoodsSnByGoodsId(String goodsId) throws Exception;
	
	Long getBrandIdByGoodsSn(String goodsSn)throws Exception;

	String getSupplierDisplayName(String supplierCode)throws Exception;
	
	//根据品牌ID查询品牌信息
	BrandInfoVO getBrandInfoByBrandId(String brandId);
	
	//查询礼品包装商品列表
	Map getPackagingProductList();
	
	//查询商品信息：描述、尺码、创建时间
	Map getGoodsInfo(String goodsId);
	
	//查询关注商品数量
	int getFavorGoodsCount(Map map);
	
	//查询是否支持求购
	int getSupportAskBuy(Map map);
	
	//查询支持求购信息
	Map getSupportAskBuyInfo(Map map);
	
	//查询上架的商品走秀码列表
	List<String> getGoodsListOnSale();
	
	//插入商品价格降价记录All
	int addGoodsReducedPriceAll();
	//插入商品最终价
	int addGoodsFinalPrice(Map map);
	//更新商品最终价
	int updateGoodsFinalPrice();
	//更新商品差价
	int updateGoodsDiffPirce();
	//查询用户商品降价信息
	List getUserGoodsReducedPrice();
	//获取商品发货地
	DeliverInfo getDeliverInfoByProduct(Map map) throws Exception;
}
