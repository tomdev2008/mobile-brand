package com.xiu.mobile.simple.model;

/** 
 * @ClassName: BookmarkIitemBo 
 * @Description:xiu_goods.iitem  xiu_goods.x_data_prd_info
 * @author: wangchengqun
 * @date 2014-5-29
 *  
 */
public class BookmarkIitemVo {
	/**
	 * 商品ID
	 */
	private Long goodsId;
	 
	/**
	 * 商品的最终售价。
	 */
	private Double goodsPrice;
 
	/**
	 * 和SSO中的UserID保持一致
	 */
	private Long userId;
	/**
	 * 商品名称
	 */
	private String goodsName;
	/**
	 * 走秀码
	 */
	private String goodsSn;
	/**
	 * 商品图片路径
	 */
	private String goodsImgUrl;
 
	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public Double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public String getGoodsImgUrl() {
		return goodsImgUrl;
	}

	public void setGoodsImgUrl(String goodsImgUrl) {
		this.goodsImgUrl = goodsImgUrl;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
 

}
