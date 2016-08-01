package com.xiu.mobile.portal.facade.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: OrderSummaryOutParam
 * @Description: 订单列表查询，中订单摘要信息出参
 * @author: Hualong
 * @date 2013-5-3 12:03:21
 */
public class OrderSummaryOutParam {

	private String orderId;// 订单ID
	private String orderNo;// 订单业务编号
	private String price;// 价格
	private String when;// 订单发生时间
	private String status;// 订单状态
	private String lpStatus;// 订单物流状态
	private String receiver;// 收件人
	private String address;// 收件人地址
	private String deliver;// 送货方式
	private String totalPrice;//总价格
	private String paymentMethod;// 支付方式描述信息
	private String payType;// 支付方式代码
	private String payStatus;// 支付状态代码
	private String payStatusDesc;// 支付状态描述

	private List<CommoSummaryOutParam> goodsList;// 商品列表

	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getWhen() {
		return this.when;
	}

	public void setWhen(String when) {
		this.when = when;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLpStatus() {
		return this.lpStatus;
	}

	public void setLpStatus(String lpStatus) {
		this.lpStatus = lpStatus;
	}

	public String getReceiver() {
		return this.receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDeliver() {
		return this.deliver;
	}

	public void setDeliver(String deliver) {
		this.deliver = deliver;
	}

	public String getPaymentMethod() {
		return this.paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayStatus() {
		return this.payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayStatusDesc() {
		return this.payStatusDesc;
	}

	public void setPayStatusDesc(String payStatusDesc) {
		this.payStatusDesc = payStatusDesc;
	}

	public List<CommoSummaryOutParam> getGoodsList() {
		if (goodsList==null) {
			goodsList=new ArrayList<CommoSummaryOutParam>();
		}
		return this.goodsList;
	}

	public void setGoodsList(List<CommoSummaryOutParam> goodsList) {
		this.goodsList = goodsList;
	}

	public String getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

}
