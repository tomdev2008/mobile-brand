package com.xiu.mobile.brand.web.model;

import java.io.Serializable;
import java.util.Set;


public class CatAndSizeModel implements Comparable<CatAndSizeModel>, Serializable {

	private static final long serialVersionUID = 3555603321626424543L;
    /**
     * 商品id
     */
	private String goodsId;
	/**
	 * 走秀码
	 */
	private String goodsSn;
	/**
	 * 一级分类ID
	 */
	private String categoryId;
	/**
	 * 一级分类名称
	 */
	private String categoryName;
	/**
	 * 商品尺码集合
	 */
	private Set<String> sizeList;
	
	
	
	public String getGoodsId() {
		return goodsId;
	}



	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}



	public String getGoodsSn() {
		return goodsSn;
	}



	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}



	public String getCategoryId() {
		return categoryId;
	}



	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}



	public String getCategoryName() {
		return categoryName;
	}



	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}



	public Set<String> getSizeList() {
		return sizeList;
	}



	public void setSizeList(Set<String> sizeList) {
		this.sizeList = sizeList;
	}


	@Override
	public String toString() {
		return "CatAndSizeModel [goodsId=" + goodsId + ", goodsSn=" + goodsSn
				+ ", categoryId=" + categoryId + ", categoryName="
				+ categoryName + ", sizeList=" + sizeList + "]";
	}

	@Override
	public int compareTo(CatAndSizeModel o) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	
}
