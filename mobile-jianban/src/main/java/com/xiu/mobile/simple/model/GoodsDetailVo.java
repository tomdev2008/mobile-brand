package com.xiu.mobile.simple.model;

import java.util.List;

/**
 * @author caichangliang
 * 
 */
public class GoodsDetailVo {
	/**
	 * 商品标识
	 */
	private String goodsSn;
	/**
	 * 上架状态：0:下架,1:上架
	 */
	private String stateOnsale;
	/**
	 * 内部ID
	 */
	private String goodsInnerId;
	/**
	 * 商品主图URL
	 */
	private String goodsImgUrl;
	/**
	 * 小图片列表
	 */
	private List<FlexibleItem> imgList;
	/**
	 * 大图片列表
	 */
	private List<FlexibleItem> imgListMax;
	/**
	 * 商品编号
	 */
	private String goodsNumber;
	/**
	 * 商品ID
	 */
	private String innerId;
	/**
	 * 商品名称
	 */
	private String goodsName;
	/**
	 * 走秀价
	 */
	private String zsPrice;
	/**
	 * 折扣率
	 */
	private String discount;
	/**
	 * 商品价格(市场价)
	 */
	private String price;
	/**
	 * 配送信息
	 */
	private String tranport;
	/**
	 * 剩余时间（/sec）
	 */
	private long leaveTime;
	/**
	 * 颜色列表
	 */
	private List<String> colors;
	/**
	 * 尺码列表
	 */
	private List<String> sizes;
	/**
	 * 规格矩阵,行序号为颜色，列序号为规格，数字为库存量
	 */
	private int[][] styleMatrix;
	/**
	 * 规格对应SKU码
	 */
	private List<FlexibleItem> styleSku;
	/**
	 * 主sku颜色索引
	 */
	private int primColor;
	/**
	 * 主sku尺寸索引
	 */
	private int primSize;
	/**
	 * 商品属性
	 */
	private List<FlexibleItem> goodsProperties;
	/**
	 * 商品描述
	 */
	private String description;
	/**
	 * 商品细节
	 */
	private String goodsDetail;

	/**
	 * 尺码对照图
	 */
	private String sizeUrl;
	/**
	 * 品牌ID
	 */
	private Long brandId;

	/**
	 * 品牌名称
	 */
	private String brandName;

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public String getStateOnsale() {
		return stateOnsale;
	}

	public void setStateOnsale(String stateOnsale) {
		this.stateOnsale = stateOnsale;
	}

	public String getGoodsImgUrl() {
		return goodsImgUrl;
	}

	public void setGoodsImgUrl(String goodsImgUrl) {
		this.goodsImgUrl = goodsImgUrl;
	}

	public List<FlexibleItem> getImgList() {
		return imgList;
	}

	public void setImgList(List<FlexibleItem> imgList) {
		this.imgList = imgList;
	}

	public String getGoodsNumber() {
		return goodsNumber;
	}

	public void setGoodsNumber(String goodsNumber) {
		this.goodsNumber = goodsNumber;
	}

	public String getInnerId() {
		return innerId;
	}

	public void setInnerId(String innerId) {
		this.innerId = innerId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getZsPrice() {
		return zsPrice;
	}

	public void setZsPrice(String zsPrice) {
		this.zsPrice = zsPrice;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getTranport() {
		return tranport;
	}

	public void setTranport(String tranport) {
		this.tranport = tranport;
	}

	public List<String> getColors() {
		return colors;
	}

	public void setColors(List<String> colors) {
		this.colors = colors;
	}

	public List<String> getSizes() {
		return sizes;
	}

	public void setSizes(List<String> sizes) {
		this.sizes = sizes;
	}

	public int[][] getStyleMatrix() {
		return styleMatrix;
	}

	public void setStyleMatrix(int[][] styleMatrix) {
		this.styleMatrix = styleMatrix;
	}

	public List<FlexibleItem> getGoodsProperties() {
		return goodsProperties;
	}

	public void setGoodsProperties(List<FlexibleItem> goodsProperties) {
		this.goodsProperties = goodsProperties;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGoodsDetail() {
		return goodsDetail;
	}

	public void setGoodsDetail(String goodsDetail) {
		this.goodsDetail = goodsDetail;
	}

	public String getSizeUrl() {
		return sizeUrl;
	}

	public void setSizeUrl(String sizeUrl) {
		this.sizeUrl = sizeUrl;
	}

	public long getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(long leaveTime) {
		this.leaveTime = leaveTime;
	}

	public List<FlexibleItem> getStyleSku() {
		return styleSku;
	}

	public void setStyleSku(List<FlexibleItem> styleSku) {
		this.styleSku = styleSku;
	}

	public int getPrimColor() {
		return primColor;
	}

	public void setPrimColor(int primColor) {
		this.primColor = primColor;
	}

	public int getPrimSize() {
		return primSize;
	}

	public void setPrimSize(int primSize) {
		this.primSize = primSize;
	}

	public String getGoodsInnerId() {
		return goodsInnerId;
	}

	public void setGoodsInnerId(String goodsInnerId) {
		this.goodsInnerId = goodsInnerId;
	}

	public List<FlexibleItem> getImgListMax() {
		return imgListMax;
	}

	public void setImgListMax(List<FlexibleItem> imgListMax) {
		this.imgListMax = imgListMax;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

}
