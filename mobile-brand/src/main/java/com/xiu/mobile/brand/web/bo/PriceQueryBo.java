package com.xiu.mobile.brand.web.bo;

public class PriceQueryBo {
	
	//走秀码
	private String productSn;
	
	//走秀价
	private double xiuPrice;
	
	//市场价
	private double mktPrice;

	//最终价
	private double finalPrice;
	

	public String getProductSn() {
		return productSn;
	}

	public void setProductSn(String productSn) {
		this.productSn = productSn;
	}

	public double getXiuPrice() {
		return xiuPrice;
	}

	public void setXiuPrice(double xiuPrice) {
		this.xiuPrice = xiuPrice;
	}

	public double getMktPrice() {
		return mktPrice;
	}

	public void setMktPrice(double mktPrice) {
		this.mktPrice = mktPrice;
	}

	public double getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(double finalPrice) {
		this.finalPrice = finalPrice;
	}
}
