package com.xiu.mobile.portal.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: CommoSummaryOutParam
 * @Description: 商品摘要信息
 * @author: Hualong
 * @date 2013-5-3 12:02:46
 */
@SuppressWarnings("serial")
public class CommoSummaryOutParam implements Serializable{

	private String goodsId; // goodId
	private String skuCode;// sku码
	private String goodsSn; // goodsSn
	private String goodsName; // 货品名称
	private String goodsAmount; // 货品数量
	private String zsPrice; // 走秀价
	private String price;// 订单价
	private String discountPrice;// 折后价
	private String goodsImg; // 商品图片
	private int orderDetailId; // 商品详情Id
	private String goodsProperties;// 商品属性
	private int refundableQuantity;// 可退换货商品数量
	private int refundRecord;//退换货商品记录
	private Date deliveryDate;// 商品出库时间(如果一个SKU多次出库取最后一个出库时间为准)
	private int deliverType;// 发送方式 0:普通商品 1:直发 2:全球速递 3:香港速递4:供应商直发 5:海外直发
	private String brandName; //品牌名

	public int getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(int orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

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

	public int getRefundableQuantity() {
		return refundableQuantity;
	}

	public void setRefundableQuantity(int refundableQuantity) {
		this.refundableQuantity = refundableQuantity;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public int getDeliverType() {
		return deliverType;
	}

	public void setDeliverType(int deliverType) {
		this.deliverType = deliverType;
	}

	public int getRefundRecord() {
		return refundRecord;
	}

	public void setRefundRecord(int refundRecord) {
		this.refundRecord = refundRecord;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	@Override
	public String toString() {
		return "CommoSummaryOutParam [goodsId=" + goodsId + ", skuCode=" + skuCode + ", goodsSn=" + goodsSn 
				+ ", goodsName=" + goodsName + ", goodsAmount=" + goodsAmount + ", zsPrice=" + zsPrice + ", price=" + price 
				+ ", discountPrice=" + discountPrice + ", goodsImg=" + goodsImg + ", orderDetailId=" + orderDetailId 
				+ ", goodsProperties=" + goodsProperties + ", refundableQuantity=" + refundableQuantity + ", refundRecord=" + refundRecord 
				+ ", deliveryDate=" + deliveryDate + ", deliverType=" + deliverType + "]";
	}

}
