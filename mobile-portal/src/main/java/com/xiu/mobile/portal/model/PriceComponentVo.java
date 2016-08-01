package com.xiu.mobile.portal.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PriceComponentVo implements Serializable {
	// 是否行邮
	private boolean isCustoms;
	// 价格组成名称
	private String priceComponentName;
	// 定价
	private Double totalPriceComponent;
	// 成交价
	private Double basePrice;
	// 实际关税(如果关税<=50时，关税=0)
	private Double realCustomsTax;
	// 预计关税(如果实际关税为0，显示预计关税)
	private Double evalCustomsTax;
	// 国际运费
	private Double transportCost;

	public Double getEvalCustomsTax() {
		return evalCustomsTax;
	}

	public void setEvalCustomsTax(Double evalCustomsTax) {
		this.evalCustomsTax = evalCustomsTax;
	}

	public String getPriceComponentName() {
		return priceComponentName;
	}

	public void setPriceComponentName(String priceComponentName) {
		this.priceComponentName = priceComponentName;
	}

	public Double getTotalPriceComponent() {
		return totalPriceComponent;
	}

	public void setTotalPriceComponent(Double totalPriceComponent) {
		this.totalPriceComponent = totalPriceComponent;
	}

	public Double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}

	public Double getRealCustomsTax() {
		return realCustomsTax;
	}

	public void setRealCustomsTax(Double realCustomsTax) {
		this.realCustomsTax = realCustomsTax;
	}

	public Double getTransportCost() {
		return transportCost;
	}

	public void setTransportCost(Double transportCost) {
		this.transportCost = transportCost;
	}

	public boolean getIsCustoms() {
		return isCustoms;
	}

	public void setIsCustoms(boolean isCustoms) {
		this.isCustoms = isCustoms;
	}

	public void setCustoms(boolean isCustoms) {
		this.isCustoms = isCustoms;
	}

	@Override
	public String toString() {
		return "PriceComponentVo [isCustoms=" + isCustoms + ", priceComponentName=" + priceComponentName 
				+ ", totalPriceComponent=" + totalPriceComponent + ", basePrice=" + basePrice + ", realCustomsTax=" + realCustomsTax 
				+ ", evalCustomsTax=" + evalCustomsTax + ", transportCost=" + transportCost
				+ "]";
	}

}
