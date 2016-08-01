package com.xiu.mobile.portal.model;

import java.util.List;

public class CommentListResult {

	private String brandId;
	private String brandCode;
	private String likeNum;
	private String likeNumOfGoods;
	private String normalLike;
	private String normalLikeOfGoods;
	private String pageNo;
	private String pageSize;
	private String prodId;
	private boolean result;
	private String total;
	private String totalCount;
	private String totalOfGoods;
	private String totalPage;
	private String unLike;
	private String unLikeOfGoods;
	private List<CommentBOResult> comBO;
	
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public String getLikeNum() {
		return likeNum;
	}
	public void setLikeNum(String likeNum) {
		this.likeNum = likeNum;
	}
	public String getLikeNumOfGoods() {
		return likeNumOfGoods;
	}
	public void setLikeNumOfGoods(String likeNumOfGoods) {
		this.likeNumOfGoods = likeNumOfGoods;
	}
	public String getNormalLike() {
		return normalLike;
	}
	public void setNormalLike(String normalLike) {
		this.normalLike = normalLike;
	}
	public String getNormalLikeOfGoods() {
		return normalLikeOfGoods;
	}
	public void setNormalLikeOfGoods(String normalLikeOfGoods) {
		this.normalLikeOfGoods = normalLikeOfGoods;
	}
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	public String getTotalOfGoods() {
		return totalOfGoods;
	}
	public void setTotalOfGoods(String totalOfGoods) {
		this.totalOfGoods = totalOfGoods;
	}
	public String getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
	}
	public String getUnLike() {
		return unLike;
	}
	public void setUnLike(String unLike) {
		this.unLike = unLike;
	}
	public String getUnLikeOfGoods() {
		return unLikeOfGoods;
	}
	public void setUnLikeOfGoods(String unLikeOfGoods) {
		this.unLikeOfGoods = unLikeOfGoods;
	}
	public List<CommentBOResult> getComBO() {
		return comBO;
	}
	public void setComBO(List<CommentBOResult> comBO) {
		this.comBO = comBO;
	}
	
}
