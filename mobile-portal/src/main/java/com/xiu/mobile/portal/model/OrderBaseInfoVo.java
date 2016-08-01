package com.xiu.mobile.portal.model;

import java.util.List;

/**
 * 
 * @ClassName: OrderBaseInfoVo
 * @Description: 订单详情中基础信息
 * @author: Hualong
 * @date 2013-5-3 09:31:12
 * 
 */
public class OrderBaseInfoVo {

	private String orderId;// 订单ID
	private String orderNo;// 订单业务编号
	private String when;// 下单时间
	private String paymentMethod;// 支付方式
	private String payType;
	private String payStatus;// 支付状态
	private String goodsTotalPrice;// 商品总金额
	private String orderPrice;// 订单总金额 (商品总金额+运费)
	private String payPrice;// 实际支付金额(订单总金额-优惠金额-（虚拟账户的钱【用户选择虚拟账户支付】）)
	private String logisticsCost;// 物流费用
	private int goodsCount;// 商品总数
	private int hasLogisticsInfo; //是否有物流信息
	private String promoAmount;//优惠金额（订单总金额-实际支付金额）
	private String vpayAmount;//虚拟账户支付金额
	
	//mportal旧的订单状态
	private int statusCode;
	private String status;// 订单状态̬
		
	//新的订单状态osc获得
	private int newStatusCode;
	private String newStatus;
	
	//最新的订单状态
	private Integer showStatusCode; //订单显示状态CODE   -最新
	private String showStatusName; //订单显示状态描述	-最新
	
    //是否禁止评论  0 不禁止  1 禁止
	private int forbidComment ;
	
	private boolean useProductPackaging; //使用商品包装服务
	private String packagingPrice; //商品包装价格
	
	private String lpStatus;// 物流状态

	private Long left;//订单未支付时还剩多少毫秒撤销
	
	private String leftPayAmount;//待支付金额
	
	private String confirmPaidFee; //已支付金额
	
	private int isMutilPay;//是否为多次支付订单
	
	private Double minMutilPayAmount; //每笔支付最小金额
	
	private String limitPayAmount; //多笔支付，本次只能支付多少金额

	private List<OrderPayInfo> orderPayList; //支付成功信息
	
	public String getVpayAmount() {
		return vpayAmount;
	}

	public void setVpayAmount(String vpayAmount) {
		this.vpayAmount = vpayAmount;
	}

	public int getForbidComment() {
		return forbidComment;
	}

	public void setForbidComment(int forbidComment) {
		this.forbidComment = forbidComment;
	}

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

	
	public int getNewStatusCode() {
		return newStatusCode;
	}

	public void setNewStatusCode(int newStatusCode) {
		this.newStatusCode = newStatusCode;
	}

	public String getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(String newStatus) {
		this.newStatus = newStatus;
	}

	public String getPromoAmount() {
		return promoAmount;
	}

	public void setPromoAmount(String promoAmount) {
		this.promoAmount = promoAmount;
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

	public String getWhen() {
		return this.when;
	}

	public void setWhen(String when) {
		this.when = when;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentMethod() {
		return this.paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPayType() {
		return payType;
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

	public String getGoodsTotalPrice() {
		return goodsTotalPrice;
	}

	public void setGoodsTotalPrice(String goodsTotalPrice) {
		this.goodsTotalPrice = goodsTotalPrice;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(String payPrice) {
		this.payPrice = payPrice;
	}

	public int getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(int goodsCount) {
		this.goodsCount = goodsCount;
	}

	public int getHasLogisticsInfo() {
		return hasLogisticsInfo;
	}

	public void setHasLogisticsInfo(int hasLogisticsInfo) {
		this.hasLogisticsInfo = hasLogisticsInfo;
	}

	public String getLogisticsCost() {
		return logisticsCost;
	}

	public void setLogisticsCost(String logisticsCost) {
		this.logisticsCost = logisticsCost;
	}

	public boolean getUseProductPackaging() {
		return useProductPackaging;
	}

	public void setUseProductPackaging(boolean useProductPackaging) {
		this.useProductPackaging = useProductPackaging;
	}

	public String getPackagingPrice() {
		return packagingPrice;
	}

	public void setPackagingPrice(String packagingPrice) {
		this.packagingPrice = packagingPrice;
	}

	public String getLpStatus() {
		return lpStatus;
	}

	public void setLpStatus(String lpStatus) {
		this.lpStatus = lpStatus;
	}

	public Long getLeft() {
		return left;
	}

	public void setLeft(Long left) {
		this.left = left;
	}

	public String getLeftPayAmount() {
		return leftPayAmount;
	}

	public void setLeftPayAmount(String leftPayAmount) {
		this.leftPayAmount = leftPayAmount;
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

	public Double getMinMutilPayAmount() {
		return minMutilPayAmount;
	}

	public void setMinMutilPayAmount(Double minMutilPayAmount) {
		this.minMutilPayAmount = minMutilPayAmount;
	}

	public List<OrderPayInfo> getOrderPayList() {
		return orderPayList;
	}

	public void setOrderPayList(List<OrderPayInfo> orderPayList) {
		this.orderPayList = orderPayList;
	}

	public String getLimitPayAmount() {
		return limitPayAmount;
	}

	public void setLimitPayAmount(String limitPayAmount) {
		this.limitPayAmount = limitPayAmount;
	}
	
	
	

}
