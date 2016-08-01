package com.xiu.mobile.core.model;

import java.util.Date;

public class WellChosenBrandBo {
	
	private Long id;
	/**
	 * 品牌id
	 */
	private Long brandId;
	/**
	 * 品牌代码
	 */
	private String brandCode;
	/**
	 * 显示序号
	 */
	private Long orderSequence;
	/**
	 * 精选日期
	 */
	private Date createDate;
	/**
	 * 是否删除-Y N
	 */
	private String deleted;
	/**
	 * 是否替换-Y N
	 */
	private String replaced;
	/**
	 * 添加人 同userId
	 */
	private Long createBy;
	/**
	 * 品牌商品列表banner图
	 */
	private String bannerPic;
	
	private Long onlineGoods;	//在线商品数量
	
	private Integer followNum;	//粉丝数目
	
	
	
	
	public Long getOnlineGoods() {
		return onlineGoods;
	}
	public void setOnlineGoods(Long onlineGoods) {
		this.onlineGoods = onlineGoods;
	}
	public String getBannerPic() {
		return bannerPic;
	}
	public void setBannerPic(String bannerPic) {
		this.bannerPic = bannerPic;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBrandId() {
		return brandId;
	}
	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public Long getOrderSequence() {
		return orderSequence;
	}
	public void setOrderSequence(Long orderSequence) {
		this.orderSequence = orderSequence;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getReplaced() {
		return replaced;
	}
	public void setReplaced(String replaced) {
		this.replaced = replaced;
	}
	public Integer getFollowNum() {
		return followNum;
	}
	public void setFollowNum(Integer followNum) {
		this.followNum = followNum;
	}
	

}
