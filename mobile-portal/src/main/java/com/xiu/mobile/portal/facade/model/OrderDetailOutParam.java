package com.xiu.mobile.portal.facade.model;

import java.util.List;

import com.xiu.mobile.portal.common.model.BaseBackDataVo;

/**
 * @ClassName: OrderDetailOutParam
 * @Description:
 * @author: Hualong
 * @date 2013-5-3 02:35:04
 */
public class OrderDetailOutParam extends BaseBackDataVo {

	private static final long serialVersionUID = 1L;

	private String tokenId;// 认证凭据
	private String orderId;// 订单ID
	private String orderNo;// 订单业务编号
	private String totalPrice;// 总价（走秀价）
	private String price;// 总价格
	private String when;// 下单时间
	private String status;// 订单状态
	private String receiver;// 收件人
	private String mobile;// 手机号码
	private String phone;// 家庭电话号码
	private String postCode;// 邮政编码
	private String province;// 省
	private String city;// 市
	private String area;// 区/县
	private String address;// 地址
	private String deliver;// 送货方式
	private String paymentMethod;// 支付方式描述
	private String invoice;// 是否发票
	private String invoiceType;// 发票类型
	private String invoiceHeading;// 发票抬头
	private String invoiceContent;// 发票内容
	private String payStatus;
	private String payType;// 支付方式
	private String lpStatus;// 物流状态
	private List<CommoSummaryOutParam> goodsList;// 商品列表

	public String getTokenId() {
		return this.tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

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

	public String getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
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

	public String getReceiver() {
		return this.receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
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

	public String getInvoice() {
		return this.invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public String getInvoiceType() {
		return this.invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceHeading() {
		return this.invoiceHeading;
	}

	public void setInvoiceHeading(String invoiceHeading) {
		this.invoiceHeading = invoiceHeading;
	}

	public String getInvoiceContent() {
		return this.invoiceContent;
	}

	public void setInvoiceContent(String invoiceContent) {
		this.invoiceContent = invoiceContent;
	}

	public List<CommoSummaryOutParam> getGoodsList() {
		return this.goodsList;
	}

	public void setGoodsList(List<CommoSummaryOutParam> goodsList) {
		this.goodsList = goodsList;
	}

	public String getPayStatus() {
		return this.payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getLpStatus() {
		return this.lpStatus;
	}

	public void setLpStatus(String lpStatus) {
		this.lpStatus = lpStatus;
	}

}
