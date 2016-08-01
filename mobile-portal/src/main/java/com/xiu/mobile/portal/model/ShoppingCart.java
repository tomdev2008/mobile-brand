package com.xiu.mobile.portal.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ShoppingCart implements Serializable {

	private String id; // Id
	private Long userId;
	private String goodsSn;
	private String goodsSku;
	private int quantity; // 数目
	private Date createDate;
	private int platform; // 1: mobile 2: www
	private String checked = "Y";//Y选中， N没选
	private String goodsSource;//商品来源
	private String changed= "N";//Y已改，N没改
	private boolean supportPackaging; //是否支持礼品包装
	private String packagingPrice; //礼品包装价格
	private Integer limitSaleNum; //商品限购数量
	private boolean isCustoms; //是否海外商品
	private String referrerPageId;//加入购物车来源pageId

	/**
	 * @return the changed
	 */
	public synchronized String getChanged() {
		return changed;
	}

	/**
	 * @param changed the changed to set
	 */
	public synchronized void setChanged(String changed) {
		this.changed = changed;
	}

	/**
	 * @return the goodsSource
	 */
	public synchronized String getGoodsSource() {
		return goodsSource;
	}

	/**
	 * @param goodsSource the goodsSource to set
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getPlatform() {
		return platform;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}

	public boolean getSupportPackaging() {
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
	
	public Integer getLimitSaleNum() {
		return limitSaleNum;
	}

	public void setLimitSaleNum(Integer limitSaleNum) {
		this.limitSaleNum = limitSaleNum;
	}
	
	public boolean isCustoms() {
		return isCustoms;
	}

	public void setCustoms(boolean isCustoms) {
		this.isCustoms = isCustoms;
	}

	public String getReferrerPageId() {
		return referrerPageId;
	}

	public void setReferrerPageId(String referrerPageId) {
		this.referrerPageId = referrerPageId;
	}

	@Override
	public String toString() {
		return "ShoppingCart [id=" + id + ", userId=" + userId + ", goodsSn="
				+ goodsSn + ", goodsSku=" + goodsSku + ", quantity=" + quantity
				+ ", createDate=" + createDate + ", platform=" + platform
				+ ",supportPackaging=" + supportPackaging + ",packagingPrice=" + packagingPrice
				+ ",limitSaleNum=" + limitSaleNum + "]";
	}

}
