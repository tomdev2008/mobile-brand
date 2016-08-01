package com.xiu.mobile.portal.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PriceCompareVo implements Serializable {

	private String goodsSn;// 走秀码

	private boolean showStatus;// 控制比价显示状态

	private String goodsChannel;// 竞网名称

	private String goodsUrl;// 竞网地址

	private String goodsPrice;// 竞网价格

	private Double goodsRMBPrice;// 人民币价格

	private String rate;// 汇率

	private Double customsTax;// 关税

	private Double transportCost;// 国际运费

	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public boolean getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(boolean showStatus) {
		this.showStatus = showStatus;
	}

	public String getGoodsChannel() {
		return goodsChannel;
	}

	public void setGoodsChannel(String goodsChannel) {
		this.goodsChannel = goodsChannel;
	}

	public String getGoodsUrl() {
		return goodsUrl;
	}

	public void setGoodsUrl(String goodsUrl) {
		this.goodsUrl = goodsUrl;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public Double getCustomsTax() {
		return customsTax;
	}

	public void setCustomsTax(Double customsTax) {
		this.customsTax = customsTax;
	}

	public Double getTransportCost() {
		return transportCost;
	}

	public void setTransportCost(Double transportCost) {
		this.transportCost = transportCost;
	}

	public String getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(String goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public Double getGoodsRMBPrice() {
		return goodsRMBPrice;
	}

	public void setGoodsRMBPrice(Double goodsRMBPrice) {
		this.goodsRMBPrice = goodsRMBPrice;
	}

	@Override
	public String toString() {
		return "PriceCompareVo [goodsSn=" + goodsSn + ", showStatus=" + showStatus + ", goodsChannel=" + goodsChannel 
				+ ", goodsUrl=" + goodsUrl + ", goodsPrice=" + goodsPrice + ", goodsRMBPrice=" + goodsRMBPrice + ", rate=" + rate 
				+ ", customsTax=" + customsTax + ", transportCost=" + transportCost
				+ "]";
	}

}
