package com.xiu.mobile.core.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DeliverInfo implements Serializable {

	private int code; // 发货编码
	private String priceLabel; // 发货价格说明
	private String city; // 发货城市
	private String deliveryTime; // 发货时间
	private String deliveryTimeInfo; //发货时间信息
	private String country; // 发货国家
	private String flowImgURL; // 流程图片地址
	
	public DeliverInfo(){
		
	}
	
	public DeliverInfo(int code, String priceLabel, String city,String deliveryTime, String country) {
		this.code = code;
		this.priceLabel = priceLabel;
		this.city = city;
		this.deliveryTime = deliveryTime;
		this.country = country;
	}

	public DeliverInfo(int code, String priceLabel, String city,String deliveryTime, String country, String flowImgURL) {
		this.code = code;
		this.priceLabel = priceLabel;
		this.city = city;
		this.deliveryTime = deliveryTime;
		this.country = country;
		this.flowImgURL = flowImgURL;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getPriceLabel() {
		return priceLabel;
	}

	public void setPriceLabel(String priceLabel) {
		this.priceLabel = priceLabel;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getDeliveryTimeInfo() {
		return deliveryTimeInfo;
	}

	public void setDeliveryTimeInfo(String deliveryTimeInfo) {
		this.deliveryTimeInfo = deliveryTimeInfo;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getFlowImgURL() {
		return flowImgURL;
	}

	public void setFlowImgURL(String flowImgURL) {
		this.flowImgURL = flowImgURL;
	}

	
}
