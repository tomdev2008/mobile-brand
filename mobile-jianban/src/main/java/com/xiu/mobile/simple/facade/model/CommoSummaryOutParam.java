package com.xiu.mobile.simple.facade.model;

/**
 * @ClassName: CommoSummaryOutParam
 * @Description: 商品摘要信息
 * @author: Hualong
 * @date 2013-5-3 12:02:46
 */
public class CommoSummaryOutParam {
	
	private String goodsId; //goodId
	private String skuCode;// sku码
	private String goodsSn; // goodsSn
	private String goodsName; // 货品名称
	private String goodsAmount; // 货品数量
	private String zsPrice; // 走秀价
	private String price;// 订单价
	private String discountPrice;// 折后价
	private String goodsImg; // 商品图片
	private String goodsProperties;// 商品属性

	
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getSkuCode() {
		return this.skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getGoodsSn() {
		return this.goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public String getGoodsName() {
		return this.goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsAmount() {
		return this.goodsAmount;
	}

	public void setGoodsAmount(String goodsAmount) {
		this.goodsAmount = goodsAmount;
	}

	public String getZsPrice() {
		return this.zsPrice;
	}

	public void setZsPrice(String zsPrice) {
		this.zsPrice = zsPrice;
	}

	public String getGoodsProperties() {
		return this.goodsProperties;
	}

	public void setGoodsProperties(String goodsProperties) {
		this.goodsProperties = goodsProperties;
	}

	public String getGoodsImg() {
		return this.goodsImg;
	}

	public void setGoodsImg(String goodsImg) {
		this.goodsImg = goodsImg;
	}

	public String getDiscountPrice() {
		return this.discountPrice;
	}

	public void setDiscountPrice(String discountPrice) {
		this.discountPrice = discountPrice;
	}

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}
