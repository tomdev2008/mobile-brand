package com.xiu.mobile.portal.model;

import java.util.Date;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(商品浏览记录) 
 * @AUTHOR coco.long@xiu.com
 * @DATE 2014-12-19 
 * ***************************************************************
 * </p>
 */
public class BrowseGoodsModel {
	/**
	 * 商品浏览记录Id
	 */
	private Long id;

	/**
	 * 用户Id
	 */
	private Long userId;
	
	/**
	 * 商品走秀码
	 */
	private String goodsSn;
	
	/**
	 * 创建时间
	 */
	private Date createDate;
	
	/**
	 * 创建日期
	 */
	private String createDay;
	
	/**
	 * 来源方式： 3:m-web 4:and-app 5:iphone-app 6:ipad-app 
	 */
	private int terminal;
	
	/**
	 * 商品名称
	 */
	private String goodsName;
	
	/**
	 * 商品主图片地址
	 */
	private String goodsImgUrl;
	
	/**
	 * 商品价格：走秀价
	 */
	private Double zsPrice;
	
	/**
	 * 商品是否售罄
	 */
	private int stock;
	
	/**
	 * 商品ID
	 */
	private String goodsId;
	
	/**
	 * 品牌名称
	 */
	private String brandName;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getTerminal() {
		return terminal;
	}

	public void setTerminal(int terminal) {
		this.terminal = terminal;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsImgUrl() {
		return goodsImgUrl;
	}

	public void setGoodsImgUrl(String goodsImgUrl) {
		this.goodsImgUrl = goodsImgUrl;
	}

	public Double getZsPrice() {
		return zsPrice;
	}

	public void setZsPrice(Double zsPrice) {
		this.zsPrice = zsPrice;
	}
	
	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCreateDay() {
		return createDay;
	}

	public void setCreateDay(String createDay) {
		this.createDay = createDay;
	}

	public String toString() {
		return "BrowseGoodsModel [id=" + id + ", userId=" + userId
				+ ", goodsSn=" + goodsSn + ", createDate=" + createDate
				+ ", terminal=" + terminal + ", goodsName=" + goodsName
				+ ", goodsImgUrl=" + goodsImgUrl + ", zsPrice=" + zsPrice
				+ ", stock=" + stock + ", goodsId=" + goodsId + "]";
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((goodsId == null) ? 0 : goodsId.hashCode());
		result = prime * result
				+ ((goodsImgUrl == null) ? 0 : goodsImgUrl.hashCode());
		result = prime * result
				+ ((goodsName == null) ? 0 : goodsName.hashCode());
		result = prime * result + ((goodsSn == null) ? 0 : goodsSn.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + stock;
		result = prime * result + terminal;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((zsPrice == null) ? 0 : zsPrice.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BrowseGoodsModel other = (BrowseGoodsModel) obj;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (goodsId == null) {
			if (other.goodsId != null)
				return false;
		} else if (!goodsId.equals(other.goodsId))
			return false;
		if (goodsImgUrl == null) {
			if (other.goodsImgUrl != null)
				return false;
		} else if (!goodsImgUrl.equals(other.goodsImgUrl))
			return false;
		if (goodsName == null) {
			if (other.goodsName != null)
				return false;
		} else if (!goodsName.equals(other.goodsName))
			return false;
		if (goodsSn == null) {
			if (other.goodsSn != null)
				return false;
		} else if (!goodsSn.equals(other.goodsSn))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (stock != other.stock)
			return false;
		if (terminal != other.terminal)
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (zsPrice == null) {
			if (other.zsPrice != null)
				return false;
		} else if (!zsPrice.equals(other.zsPrice))
			return false;
		return true;
	}

}
