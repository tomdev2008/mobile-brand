package com.xiu.mobile.simple.common.constants;

/**
 * 错误代码枚举类，用来标识错误描述
 * 
 * @author wangzhenjiang
 *
 */
public enum ErrorCode {

	/** 系统错误 {errorCode:-1} */
	SystemError(-1, "系统错误"),

	/** 成功 {errorCode:0} */
	Success(0, "成功"),

	/** 登陆失败 {errorCode:1001} */
	LoginFailed(1001, "登陆失败"),

	/** 发送短信验证码失败 {errorCode:2001} */
	SendSMSCodeFailed(2001, "发送短信验证码失败"),
	/** 短信验证码失效 {errorCode:2002} */
	VerifyCodeTimeOut(2002, "短信验证码失效"),
	/** 校验短信验证码失败{errorCode:2003} */
	VerifyCodeFailed(2003, "校验短信验证码失败"),
	/** 注册账号失败 {errorCode:2005} */
	RegisterAccountFailed(2005, "注册账号失败"),
	/** 校验短信验证码时发生异常 {errorCode:2006} */
	CheckSMSCodeError(2006, "发送短信验证码时发生异常"),
	/** 用户名已注册 {errorCode:2007} */
	LogonNameIsExist(2007, "用户名已注册 "),
	/** 用户名为空{errorCode:2008} */
	LogonNameIsNull(2008, "用户名为空 "),
	/** 账号注销失败 {errorCode:2009} */
	LogoutAccountFailed(2009, "注销账号失败"),

	/** 用户session失效 {errorCode:4001} */
	SessionTimeOut(4001, "用户session失效"),
	/** 用户在线状态有效 {errorCode:4002} */
	UserIsOnline(4002, "用户在线状态有效"),

	/** 没有找到商品  {errorCode:5001}*/
	GoodsNotFound(5001, "没有找到该商品"),
	/** 商品已下架  {errorCode:5002}*/
	GoodsNotOnSale(5002, "商品已下架"),
	/** 库存不足  {errorCode:5003}*/
	StockShortage(5003, "库存不足"),
	/** 获取商品列表失败  {errorCode:5004}*/
	GetGoodsListFailed(5004, "获取商品列表失败"),

	/** 获取收货地址失败  {errorCode:6001}*/
	GetAddressListFailed(6001, "获取收货地址失败"),
	/** 收货地址重复  {errorCode:6002}*/
	AddressRepeated(6002, "收货地址重复"),
	/** 添加收货地址失败  {errorCode:6003}*/
	AddAddressFailed(6003, "添加收货地址失败"),
	/** 设置默认收货地址失败  {errorCode:6004}*/
	SetMasterAddressFailed(6004, "设置默认收货地址发生错误 "),
	/** 更新收货地址失败  {errorCode6005}*/
	UpdateAddressFailed(6005, "更新收货地址失败"),
	/** 删除收货地址失败  {errorCode:6006}*/
	DelAddressFailed(6006, "删除收货地址失败"),
	/** 初始化添加收货地址失败  {errorCode:6007}*/
	getAddAddressFailed(6007, "初始化添加收货地址失败"),
	/** 初始化更新收货地址失败  {errorCode:6008}*/
	getUpdateAddressFailed(6008, "初始化更新收货地址失败"),
	/** 获取省级联动失败  {errorCode:6009}*/
	getPrcVosListFailed(6009, "获取省级联动失败"),

	/** 支付失败  {errorCode:7001}*/
	PayFailed(7001, "支付失败"),
	/** 创建订单失败  {errorCode:7002}*/
	CreateOrderFailed(7002, "创建订单失败"),
	/** 订单详情获取失败 {errorCode:7004}*/
	GetOrderDetailFailed(7004, "订单详情获取失败"),
	/**获取订单数据失败{errorCode:7005}*/
	GetOrderDataFailed(7005, "获取订单数据失败"),
	/**查询的订单详情信息不属于当前用户{errorCode:111002}*/
	OrderNotBelongToUser(7006, "查询的订单详情不属于当前用户"),
	/** 修改支付方式失败  {errorCode:7007}*/
	UpdatePayMethodFailed(7007, "修改支付方式失败"),
	/** 不支持的支付方式  {errorCode:7008}*/
	UnsupportPayMethod(7008, "不支持的支付方式"),

	/**参数错误{errorCode:8001}*/
	ParamsError(8001, "参数错误"),
	/**缺少参数{errorCode:8002}*/
	MissingParams(8002, "缺少参数"),

	/**吐槽失败{errorCode:9001}*/
	VomitSayFailed(9001, "吐槽失败"),
	
	/**不需要更新{errorCode:10001}*/
	WithoutUpdate(10001, "不需要更新"),
	
	/**当前订单不是重复提交订单{errorCode:1{errorCode:10002}*/
	IsNotRepeatedOrder(10002, "不是重复订单，此订单可以提交"),
	
	/**撤销订单失败{errorCode:11001}*/
	CancelOrderFailed(11001, "撤销订单失败"),

	/**物流信息为空{errorCode:111001}*/
	CarryInfoIsNull(111001, "物流信息为空"),
	/**查询的订单物流信息不属于当前用户{errorCode:111002}*/
	CarryInfoNotBelongToUser(111002, "查询的订单物流信息不属于当前用户"),
	/**用户已收藏商品提示{errorCode:10008}*/
	CheckAddIitemVoFailed(10008,"已经收藏了此商品"),
	/**用户没收藏商品提示{errorCode:10009}*/
	WithoutAddIitemVo(10009,"未收藏此商品"),
	
	/**用户已收藏品牌提示{errorCode:10006}*/
	CheckAddBrandFailed(10006,"已经收藏了此品牌"),
	/**用户没收藏品牌提示{errorCode:10007}*/
	WithoutAddBrand(10007,"未收藏此品牌"),
	/**用户已赞了此商品提示{errorCode:10004}*/
	WithAddLoveGoods(10004,"用户已赞了此商品"),
	/**用户未点赞此商品提示{errorCode:10005}*/
	WithoutAddLoveGoods(10005,"用户未点赞此商品"),
	/**当前版本是最新版或者未查出改版本{errorCode:10010}*/
	NewestAppVersion(10010,"当前版本是最新版或者未查出改版本");
	
	private ErrorCode(int errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	/**
	 * 错误代码
	 */
	private int errorCode;
	/**
	 * 错误描述
	 */
	private String errorMsg;

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
}
