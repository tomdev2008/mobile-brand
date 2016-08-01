package com.xiu.mobile.portal.model;

import java.util.List;



public class OrderSummaryVo {

	private String orderId;// 订单ID
	private String orderNo;// 订单号
	private String price;// 订单金额
	private String when;// 下单时间
	private Long left;//订单未支付时还剩多少毫秒撤销
	private String payType;// 支付方式代码
	private String lpStatus;// 物流状态代码
	private String imgUrl;// 商品图片地址
	
	private String receiver;// 收件人
	private String address;// 收件人地址
	private String deliver;// 送货方式
	
	private String goodsId;
	private String skuCode;
	private List<CommoSummaryOutParam> goodsList;//goodsList
	private int statusCode;// 订单状态编码-旧
	private String status;// 订单状态-旧
	private int newStatusCode;// 订单状态编码-新
	private String newStatus;// 订单状态-新
	private Integer showStatusCode; //订单显示状态CODE   -最新
	private String showStatusName; //订单显示状态描述	-最新
    //是否禁止评论  0 不禁止  1 禁止
	private int forbidComment ;
	
	private boolean useProductPackaging; //使用商品包装服务
	
	//多笔支付 start
	private int isMutilPay; //是否选择多笔支付
	private Double minMutilPayAmount; //订单多笔支付每次支付最小金额
	private String confirmPaidFee; //已确认支付金额,这个是需要显示的，所以用string
	private String notAmount; //未支付金额
	private String limitPayAmount; //本次只能支付多少金额
	//多笔支付 end
	
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

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
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

	public String getWhen() {
		return this.when;
	}

	public void setWhen(String when) {
		this.when = when;
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

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public List<CommoSummaryOutParam> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<CommoSummaryOutParam> goodsList) {
		this.goodsList = goodsList;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDeliver() {
		return deliver;
	}

	public void setDeliver(String deliver) {
		this.deliver = deliver;
	}

	public boolean getUseProductPackaging() {
		return useProductPackaging;
	}

	public void setUseProductPackaging(boolean useProductPackaging) {
		this.useProductPackaging = useProductPackaging;
	}

	public Long getLeft() {
		return left;
	}

	public void setLeft(Long left) {
		this.left = left;
	}

	public int getIsMutilPay() {
		return isMutilPay;
	}

	public void setIsMutilPay(int isMutilPay) {
		this.isMutilPay = isMutilPay;
	}

	public Double getMinMutilPayAmount() {
		return minMutilPayAmount;
	}

	public void setMinMutilPayAmount(Double minMutilPayAmount) {
		this.minMutilPayAmount = minMutilPayAmount;
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

	public String getLimitPayAmount() {
		return limitPayAmount;
	}

	public void setLimitPayAmount(String limitPayAmount) {
		this.limitPayAmount = limitPayAmount;
	}
	
}
