package com.xiu.mobile.portal.model;

import java.io.Serializable;
import java.util.List;

import com.xiu.sales.common.balance.dataobject.ProdSettlementDO;

public class OrderGoodsVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * sku码
	 */
	private String skuCode;

	/**
	 * 商品Sn
	 */
	private String goodsSn;

	/**
	 *商品数量 
	 */
	private int goodsQuantity;

	/**
	 * 商品走秀价
	 */
	private String zsPrice;

	/**
	 * 商品市单价
	 */
	private String price;

	/**
	 * 商品购买价
	 */
	private String discountPrice;

	/**
	 * 商品活动
	 */
//	private String[] activityList;
	private List<String> activityList;

	/**
	 * 商品名称
	 */
	private String goodsName;

	/**
	 * 是否支持货到付款
	 */
	private String codFlag;

	/**
	 * 商品主图URL
	 */
	private String goodsImgUrl;

	/**
	 * 商品名称详情URL
	 */
	private String goodsUrl;
	/**
	 * 商品报关、国际转运
	 */
	private ProdSettlementDO prodSettlementDO; 
	//商品小计
	private long goodsAmt;
	// 商品来源
	private String goodsSource;
	//礼品包装标识 0.未使用礼品包装 1.礼品包装商品 2.非礼品包装商品
	private String giftPackType; 
	
	public String getGoodsSource() {
		return goodsSource;
	}

	public void setGoodsSource(String goodsSource) {
		this.goodsSource = goodsSource;
	}

	public long getGoodsAmt() {
		return goodsAmt;
	}

	public void setGoodsAmt(long goodsAmt) {
		this.goodsAmt = goodsAmt;
	}

	public ProdSettlementDO getProdSettlementDO() {
		return prodSettlementDO;
	}

	public void setProdSettlementDO(ProdSettlementDO prodSettlementDO) {
		this.prodSettlementDO = prodSettlementDO;
	}

	public String getGoodsUrl() {
		return goodsUrl;
	}

	public void setGoodsUrl(String goodsUrl) {
		this.goodsUrl = goodsUrl;
	}

	public String getGoodsImgUrl() {
		return goodsImgUrl;
	}

	public void setGoodsImgUrl(String goodsImgUrl) {
		this.goodsImgUrl = goodsImgUrl;
	}

	public String getCodFlag() {
		return codFlag;
	}

	public void setCodFlag(String codFlag) {
		this.codFlag = codFlag;
	}

	public String getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(String discountPrice) {
		this.discountPrice = discountPrice;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public int getGoodsQuantity() {
		return goodsQuantity;
	}

	public void setGoodsQuantity(int goodsQuantity) {
		this.goodsQuantity = goodsQuantity;
	}

	public String getZsPrice() {
		return zsPrice;
	}

	public void setZsPrice(String zsPrice) {
		this.zsPrice = zsPrice;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	
	/*public String[] getActivityList() {
		return activityList;
	}

	public void setActivityList(String[] activityList) {
		this.activityList = activityList;
	}*/

	public List<String> getActivityList() {
		return activityList;
	}

	public void setActivityList(List<String> activityList) {
		this.activityList = activityList;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGiftPackType() {
		return giftPackType;
	}

	public void setGiftPackType(String giftPackType) {
		this.giftPackType = giftPackType;
	}
	
}
