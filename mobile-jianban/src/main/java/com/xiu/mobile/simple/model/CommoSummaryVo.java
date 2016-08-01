package com.xiu.mobile.simple.model;

/**
 * @ClassName: CommoSummaryVo
 * @Description: 商品摘要信息
 * @author: Hualong
 * @date 2013-5-3 10:44:36
 */
public class CommoSummaryVo {

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

}
