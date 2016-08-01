package com.xiu.mobile.portal.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: CommoSummaryVo
 * @Description: 商品摘要信息
 * @author: Hualong
 * @date 2013-5-3 10:44:36
 */
@SuppressWarnings("serial")
public class CommoSummaryVo implements Serializable{

	private String goodsId; // 商品ID
	private String goodsSn; // 商品编号
	private String goodsName; // 货品名称
	private String goodsAmount; // 货品数量
	private String discountPrice; // 折后价
	private String goodsImg; // 商品图片
	private String color; // 颜色
	private String size;// 尺寸
	private String skuCode;
	private String totalAmount;// 小计
	private int orderDetailId; // 商品详情Id
	private int refundableQuantity;// 可退换货商品数量
	private int refundRecord;// 商品退换货记录数目
	private Date deliveryDate;// 商品出库时间(如果一个SKU多次出库取最后一个出库时间为准)
	private boolean refundFlag;// 退换货状态标识
	private int deliverType;// 发送方式 0:普通商品 1:直发 2:全球速递 3:香港速递4:供应商直发 5:海外直发
	private String brandEnName; //品牌英文名
	private String brandCNName; //品牌中文名
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

	public String getDiscountPrice() {
		return this.discountPrice;
	}

	public void setDiscountPrice(String discountPrice) {
		this.discountPrice = discountPrice;
	}

	public String getGoodsImg() {
		return this.goodsImg;
	}

	public void setGoodsImg(String goodsImg) {
		this.goodsImg = goodsImg;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return this.size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	/**
	 * @Title: getTotalAmount
	 * @Description: 小计
	 * @return
	 * @throws
	 * @author: Hualong
	 * @date: 2013-5-3 04:23:26
	 */
	public String getTotalAmount() {
		return this.totalAmount;
	}

	/**
	 * @Title: setTotalAmount
	 * @Description: 小计
	 * @param totalAmount
	 * @throws
	 * @author: Hualong
	 * @date: 2013-5-3 04:23:33
	 */
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
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

	public boolean getRefundFlag() {
		return refundFlag;
	}

	public void setRefundFlag(boolean refundFlag) {
		this.refundFlag = refundFlag;
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

	@Override
	public String toString() {
		return "CommoSummaryVo [goodsId=" + goodsId + ", goodsSn=" + goodsSn + ", goodsName=" + goodsName 
				+ ", goodsAmount=" + goodsAmount + ", discountPrice=" + discountPrice + ", goodsImg=" + goodsImg 
				+ ", color=" + color + ", size=" + size + ", skuCode=" + skuCode + ", totalAmount="
				+ totalAmount + ", orderDetailId=" + orderDetailId + ", refundableQuantity=" + refundableQuantity 
				+ ", refundRecord=" + refundRecord + ", deliveryDate=" + deliveryDate + ", refundFlag=" + refundFlag 
				+ ", deliverType=" + deliverType + ", brandEnName=" + brandEnName + ", brandCNName=" + brandCNName + "]";
	}

	public String getBrandEnName() {
		return brandEnName;
	}

	public void setBrandEnName(String brandEnName) {
		this.brandEnName = brandEnName;
	}

	public String getBrandCNName() {
		return brandCNName;
	}

	public void setBrandCNName(String brandCNName) {
		this.brandCNName = brandCNName;
	}
	
}
