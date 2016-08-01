package com.xiu.mobile.portal.model;

import java.io.Serializable;

public class OrderPayConfig implements Serializable{
	
	private static final long serialVersionUID = -4205340814965999818L;
	/**是否支持多次支付*/
	private boolean isSupportMutilPay = false;
	/**最低支付金额*/
	private double minMutilPayAmount;
	
	public boolean isSupportMutilPay() {
		return isSupportMutilPay;
	}
	public void setSupportMutilPay(boolean isSupportMutilPay) {
		this.isSupportMutilPay = isSupportMutilPay;
	}
	public double getMinMutilPayAmount() {
		return minMutilPayAmount;
	}
	public void setMinMutilPayAmount(double minMutilPayAmount) {
		this.minMutilPayAmount = minMutilPayAmount;
	}
}
