package com.xiu.mobile.core.model;

/**
 * 品牌信息VO
 * @author coco.long
 * @time	2015-09-28
 */
public class BrandInfoVO {

	//品牌ID
	private String brandId;
	//品牌名称
	private String brandName;
	//品牌logo地址
	private String logo;
	//商品数量
	private Integer goodsNum;
	//品牌秀数量
	private Integer showNum;
	//评论数量
	private Integer commentNum;
	//品牌中文名
	private String cnName;
	//品牌英文名	
	private String enName;

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Integer getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}

	public Integer getShowNum() {
		return showNum;
	}

	public void setShowNum(Integer showNum) {
		this.showNum = showNum;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}
	
}
