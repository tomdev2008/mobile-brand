package com.xiu.mobile.portal.model;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车商品信息，促销信息，
 * 
 * @author wangchengqun
 * 
 */
public class ShoppingCartGoodsVo implements Serializable {

	/** 商品标识 */
	private String goodsSn;
	/** 商品id */
	private String goodsId;
	/** sku码 */
	private String goodsSku;
	/**
	 * 上架状态：0:下架,1:上架
	 */
	private String stateOnsale;
	/** 商品主图URL */
	private String goodsImgUrl;
	/** 商品名称 */
	private String goodsName;
	/** 商品颜色 */
	private String color;
	/** 商品尺码 */
	private String size;
	/** 商品购买数量 */
	private Integer quantity;
	/** 商品库存量 */
	private Integer inventory;
	/** 品牌ID */
	private Long brandId;
	/** 品牌编码 **/
	private String brandCode;
	/** 购买价 */
	private Double purchasePrice;
	/** 走秀价 **/
	private Double price;
	/** 小计 */
	private String amount;
	/** 促销活动信息 */
	private List<MktActInfoVo> activityItemList;
	/** 商品对应购物车对应的id */
	private String shoppingCartId;
	private String checked;// Y选中， N没选
	private String goodsSource;// 商品来源
	private boolean canUseCoupon;// 是否使用优惠券
	private boolean supportPackaging; //是否支持礼品包装
	private String packagingPrice; //礼品包装价格
	private String packagingGoodsId; //礼品包装商品ID
	private Integer limitSaleNum; //商品限购数量
	
	private boolean isCustoms; //是否海外商品
	
	private String brandEnName; //品牌英文名
	private String brandCNName; //品牌中文名
	
	private String referrerPageId;//加入购物车的来源pageId
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * @return the goodsId
	 */
	public synchronized String getGoodsId() {
		return goodsId;
	}

	/**
	 * @param goodsId
	 *            the goodsId to set
	 */
	public synchronized void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	/**
	 * @return the canUseCoupon
	 */
	public synchronized boolean isCanUseCoupon() {
		return canUseCoupon;
	}

	/**
	 * @param canUseCoupon
	 *            the canUseCoupon to set
	 */
	public synchronized void setCanUseCoupon(boolean canUseCoupon) {
		this.canUseCoupon = canUseCoupon;
	}

	/**
	 * @return the goodsSource
	 */
	public synchronized String getGoodsSource() {
		return goodsSource;
	}

	/**
	 * @param goodsSource
	 *            the goodsSource to set
	 */
	public synchronized void setGoodsSource(String goodsSource) {
		this.goodsSource = goodsSource;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getShoppingCartId() {
		return shoppingCartId;
	}

	public void setShoppingCartId(String shoppingCartId) {
		this.shoppingCartId = shoppingCartId;
	}

	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public String getGoodsSku() {
		return goodsSku;
	}

	public void setGoodsSku(String goodsSku) {
		this.goodsSku = goodsSku;
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

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getInventory() {
		return inventory;
	}

	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public Double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public List<MktActInfoVo> getActivityItemList() {
		return activityItemList;
	}

	public void setActivityItemList(List<MktActInfoVo> activityItemList) {
		this.activityItemList = activityItemList;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public boolean isSupportPackaging() {
		return supportPackaging;
	}

	public void setSupportPackaging(boolean supportPackaging) {
		this.supportPackaging = supportPackaging;
	}

	public String getPackagingPrice() {
		return packagingPrice;
	}

	public void setPackagingPrice(String packagingPrice) {
		this.packagingPrice = packagingPrice;
	}
	
	public String getPackagingGoodsId() {
		return packagingGoodsId;
	}

	public void setPackagingGoodsId(String packagingGoodsId) {
		this.packagingGoodsId = packagingGoodsId;
	}

	public Integer getLimitSaleNum() {
		return limitSaleNum;
	}

	public void setLimitSaleNum(Integer limitSaleNum) {
		this.limitSaleNum = limitSaleNum;
	}
	
	public boolean getIsCustoms() {
		return isCustoms;
	}

	public void setIsCustoms(boolean isCustoms) {
		this.isCustoms = isCustoms;
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
	
	public String getReferrerPageId() {
		return referrerPageId;
	}

	public void setReferrerPageId(String referrerPageId) {
		this.referrerPageId = referrerPageId;
	}

	@Override
	public String toString() {
		return "ShoppingCartGoodsVo [goodsSn=" + goodsSn + ", goodsId=" + goodsId + ", goodsSku=" + goodsSku 
				+ ", stateOnsale=" + stateOnsale + ", goodsImgUrl=" + goodsImgUrl + ", goodsName=" + goodsName 
				+ ", color=" + color + ", size=" + size + ", quantity=" + quantity + ", inventory=" + inventory
				+ ", brandId=" + brandId + ", brandCode=" + brandCode + ", purchasePrice=" + purchasePrice 
				+ ", price=" + price + ", amount=" + amount + ", activityItemList=" + activityItemList + ", shoppingCartId=" 
				+ shoppingCartId + ", checked=" + checked + ", goodsSource=" + goodsSource
				+ ", canUseCoupon=" + canUseCoupon + ", isCustoms=" + isCustoms +", brandCNName=" +brandCNName + ", brandEnName=" + brandEnName +"]";
	}

	
}
