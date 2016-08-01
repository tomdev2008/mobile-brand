package com.xiu.mobile.portal.model;

import java.io.Serializable;

public class GoodItemVO implements Serializable { 

	private static final long serialVersionUID = 4241994748951981570L;

	private Long goodId;	//商品ID
	
	private String goodName; // 品名 当类型为3商品时候 该字段才有值
	
	private String brand; // 商品品牌 当类型为3商品时候 该字段才有值

	private String price; // 价格 当类型为3商品时候 该字段才有值

	public Long getGoodId() {
		return goodId;
	}

	public void setGoodId(Long goodId) {
		this.goodId = goodId;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
}
