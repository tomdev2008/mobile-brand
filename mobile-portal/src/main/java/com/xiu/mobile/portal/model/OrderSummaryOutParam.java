package com.xiu.mobile.portal.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xiu.tc.common.orders.domain.PayOrderDO;

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
	
	private String status;// 订单状态-旧
	private String newStatus;//订单状态-新
	
	private Integer showStatusCode; //订单显示状态CODE   -最新
	private String showStatusName; //订单显示状态描述	-最新
	
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
	private boolean useProductPackaging; //使用商品包装服务
	
	private int isMutilPay; //是否选择多笔支付
	private OrderPayConfig orderPayConfig; //订单多笔支付信息
	private String confirmPaidFee; //已确认支付金额,这个是需要显示的，所以用string
	private String notAmount; //未支付金额
	private List<PayOrderDO> payOrderList; //支付记录
	
	private Date delayTime; //延迟撤销时间

	public Integer getShowStatusCode() {
		return showStatusCode;
	}

	public void setShowStatusCode(Integer showStatusCode) {
		this.showStatusCode = showStatusCode;
	}

	public String getShowStatusName() {
		return showStatusName;
	}

	public void setShowStatusName(String showStatusName) {
		this.showStatusName = showStatusName;
	}
	public String getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(String newStatus) {
		this.newStatus = newStatus;
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

	public boolean getUseProductPackaging() {
		return useProductPackaging;
	}

	public void setUseProductPackaging(boolean useProductPackaging) {
		this.useProductPackaging = useProductPackaging;
	}

	public int getIsMutilPay() {
		return isMutilPay;
	}

	public void setIsMutilPay(int isMutilPay) {
		this.isMutilPay = isMutilPay;
	}

	public String getConfirmPaidFee() {
		return confirmPaidFee;
	}

	public void setConfirmPaidFee(String confirmPaidFee) {
		this.confirmPaidFee = confirmPaidFee;
	}

	public String getNotAmount() {
		return notAmount;
	}

	public void setNotAmount(String notAmount) {
		this.notAmount = notAmount;
	}

	public OrderPayConfig getOrderPayConfig() {
		return orderPayConfig;
	}

	public void setOrderPayConfig(OrderPayConfig orderPayConfig) {
		this.orderPayConfig = orderPayConfig;
	}

	public Date getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(Date delayTime) {
		this.delayTime = delayTime;
	}

	public List<PayOrderDO> getPayOrderList() {
		return payOrderList;
	}

	public void setPayOrderList(List<PayOrderDO> payOrderList) {
		this.payOrderList = payOrderList;
	}
	
}
