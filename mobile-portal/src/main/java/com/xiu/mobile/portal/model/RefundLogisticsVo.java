package com.xiu.mobile.portal.model;

import java.io.Serializable;
import java.util.Date;

/***
 * 退换货物流信息
 * 
 * @author hejianxiong
 * @date 2014-08-29
 */
@SuppressWarnings("serial")
public class RefundLogisticsVo implements Serializable {

	private String refundOrderId; // 退换货申请单据Id
	private String orderId; // 订单Id
	private String companyName; // 公司名称
	private String logisticsCode; // 快递单号
	private double postFee; // 物流运费
	private String createDate; // 创建物流信息日期

	public String getRefundOrderId() {
		return refundOrderId;
	}

	public void setRefundOrderId(String refundOrderId) {
		this.refundOrderId = refundOrderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getLogisticsCode() {
		return logisticsCode;
	}

	public void setLogisticsCode(String logisticsCode) {
		this.logisticsCode = logisticsCode;
	}

	public double getPostFee() {
		return postFee;
	}

	public void setPostFee(double postFee) {
		this.postFee = postFee;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "RefundLogisticsVo [refundOrderId=" + refundOrderId
				+ ", orderId=" + orderId + ", companyName=" + companyName
				+ ", logisticsCode=" + logisticsCode + ", postFee=" + postFee
				+ ", createDate=" + createDate + "]";
	}

}
