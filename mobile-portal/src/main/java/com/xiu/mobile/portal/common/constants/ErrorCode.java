package com.xiu.mobile.portal.common.constants;

/**
 * 错误代码枚举类，用来标识错误描述
 * 
 * @author wangzhenjiang
 *
 */
public enum ErrorCode {

	/** 抱歉，系统出错了，请联系在线客服或稍后再试试 {errorCode:-1} */
	SystemError(-1, "抱歉，系统出错了，请联系在线客服或稍后再试试"),

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
	/** 用户名未注册 {errorCode:2010} */
	LogonNameIsNotExist(2010, "用户名未注册 "),
	/** 验证码输入不正确 {errorCode:2011} */
	ValidateCodeError(2011, "验证码输入不正确 "),
	/** 旧密码验证失败 {errorCode:2012} */
	ValidateOldPwdError(2012, "您输入的旧密码错误，请重新输入 "),
	/** 手机号重新绑定验证失败 {errorCode:2013} */
	ValidateNewPhoneError(2013, "您输入的手机号已被其它帐号验证，请重新输入！ "),
	/** 两次绑定的手机号码不能一致 {errorCode:2014} */
	PhoneValidateIsSame(2014, "两次绑定的手机号码不能一致 "),
	/** 用户不存在{errorCode:2015} */
	UserNotExists(2015, "用户不存在 "),
	/** 用户绑定出错{errorCode:2016} */
	UserBindError(2016, "用户绑定出错"),
	/** 用户密码为空{errorCode:2017} */
	UserPasswordIsEmpty(2017, "用户密码为空"),
	UserPetNameExists(2018, "用户昵称已存在"),
	UserUpdatePetNameFaild(2019, "更新用户名称失败"),
	UserUnBindError(2020, "用户解绑出错"),
	SendVoiceCodeFailed(2021, "发送语音验证码失败"),
	UserHasAsset(2022,"用户有资产"),
	VerifyCodeHasTimeOut(2023, "验证码失效"),
	VerifyCodeHasFailed(2024, "校验验证码失败"),
	
	EmailNotRegister(2025,"邮箱没有注册"),
	EmailNotBinding(2026,"邮箱没有绑定"),
	EmailHasBinding(2027,"邮箱已绑定"),
	EmailIsSame(2028,"绑定邮箱不能相同"),
	
	ThirdPasswordError(2030,"第三次密码错误"),
	UserPhoneHadBind(2031,"手机号已经被绑定"),
	UserThirdPartyHadBind(2032,"第三方账号已经被绑定"),

	/** 用户session失效 {errorCode:4001} */
	SessionTimeOut(4001, "您的账户已退出，请重新登录"),
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
	/** 此订单的收货地址已被删除，请联系客服上传身份证  {errorCode:6011}*/
	GetAddressInfoFailed(6011, "此订单的收货地址已被删除，请联系客服上传身份证"),
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
	/**代付失败  {errorCode:7009}*/
	PayForOthersFailed(7009,"代付失败"),
	/**抱歉，此订单已失效（已被支付或被取消） {errorCode:7010}*/
	PayForOthersInvalid(7010,"抱歉，此订单已失效（已被支付或被取消）"),
	/**多笔支付时，系统有每次支付最小金额限制*/
	MinPayAmountError(7011, "本次支付金额小于最小支付金额"),
	
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
	/**用户已关注商品提示{errorCode:10008}*/
	CheckAddIitemVoFailed(10008,"已经关注了此商品"),
	/**用户没关注商品提示{errorCode:10009}*/
	WithoutAddIitemVo(10009,"未关注此商品"),
	
	/**用户已关注品牌提示{errorCode:10006}*/
	CheckAddBrandFailed(10006,"已经关注了此品牌"),
	/**用户没关注品牌提示{errorCode:10007}*/
	WithoutAddBrand(10007,"未关注此品牌"),
	/**用户已赞了此商品提示{errorCode:10004}*/
	WithAddLoveGoods(10004,"用户已赞了此商品"),
	/**用户未点赞此商品提示{errorCode:10005}*/
	WithoutAddLoveGoods(10005,"用户未点赞此商品"),
	/**当前版本是最新版或者未查出改版本{errorCode:10010}*/
	NewestAppVersion(10010,"当前版本是最新版或者未查出改版本"),
	
	GetDrawApplyListFailed(10011,"查询申请提现信息列表失败"),
	GetWithDrawInfoFailed(10012,"查询申请提现信息详情失败"),
	DelWithDrawInfoFailed(10013,"取消提现失败"),
	DelWithDrawInfoError(10014,"当申请单为待审核状态时，用户可以进行【取消】操作"),
	GetWithDrawBankInfoFailed(10015,"查询用户银行账户信息详情失败"),
	DelWithDrawBankInfoFailed(10016,"删除用户银行账户失败"),
	AddDrawApplyBankFailed(10017,"添加用户银行账户失败"),
	UpdateDrawApplyBankFailed(10018,"修改用户银行账户失败"),
	AddDrawApplyInfoFailed(10019,"添加用户申请提现信息失败"),
	GetWithDrawAmountInfoFailed(10020,"查询用户虚拟账户详细信息失败"),
	AddDrawApplyInfoError(10021,"当前用户账户被冻结或申请提现金额大于可提现金额"),
	DelWithDrawBankInfoError(10022,"当前用户没有此银行账户"),
	GetAccountChangeListFailed(10023,"查询用户虚拟账户的变更明细失败"),
	GetAccountChangeInfoFailed(10024,"查询用户虚拟账户的变更明细详情失败"),
	UpdateTradeEndStatusFailed(10025,"更新已发货订单为已完结状态失败"),
	NoShoppingCartGoods(10026,"您的购物车中暂无商品"),
	OverLimitNumber(10027,"已达到限购数量"),
	CanNotContinueBuy(10028,"您不能再购买了"),
	OverGoodsLimitSaleNum(10029,"已达到限购数量"),
	
	ShakeAndShakeFailed(10030, "用户摇一摇没有中奖"),
	ShakeNoTime(10031, "摇奖机会已经用完"),
	
	DeleteOrderFailed(10041,"删除订单失败"),
	
	NotChooseSex(10050, "您还没选择玩家身份"),
	ChooseGoodsNotFinish(10051, "您的题还没答完"),
	
	WithoutBrowseGoods(10060,"没有此商品浏览记录"),
	AddBrowseGoodsFailed(10061,"添加商品浏览记录失败"),
	DeleteBrowseGoodsFailed(10062,"删除商品浏览记录失败"),
	DeleteAllBrowseGoodsFailed(10063,"清空商品浏览记录失败"),
	getBrowseGoodsFailed(10064,"获取商品浏览记录失败"),
	
	getSMSOverLimitTimes(10065,"获取短信验证码超过限制次数"),
	getVoiceOverLimitTimes(10066,"获取语音验证码超过限制次数"),
	
	AddAskBuyInfoFailed(202001,"求购申请失败"),
	DeleteAskBuyOrder(202002,"删除求购订单失败"),
	AskBuyOrderNotExists(202003,"求购订单不存在"),
	PictureMoreThanSize(202004,"文件大小不能超过10MB"),
	PictureFormatLimit(202005,"限制上传文件格式为jpg,jepg,png,bmp"),
	AskBuyGoodsNotSupport(202006,"商品不支持求购"),
	
	
	RemoveTopicCacheFailed(203001,"移除卖场的分类尺码缓存失败"),
	
	/**专题相关**/
	SubjectNotExists(204001,"专题不存在"),
	SubjectNotLists(204005,"没有专题列表信息"),
	SubjectCommentLenghFormat(204002,"评论长度限制在1-1000字符以内"),
	SubjectCommentSensitiveWordExists(204003,"抱歉，您输入的内容包含不良信息，请修改后重新发送"),
	SubjectCommentNotExists(204004,"评论不存在"),
	SubjectCommentNotAuthority(204005,"用户无权限评论"),
	
	/**我的关注相关**/
	FavorNotSoldOut(205001,"您关注的售罄商品已全部清空"),
	
	
	/**创客相关**/
	OldUserByMaker(1002, "此用户已经加入走秀，再试试其他朋友"),
	OldUserByUser(1003, "您已经加入走秀，无需重新注册"),
	
	
	/**夺宝项目相关 3开头*/
	
	RaiderNoStock(30100,"该夺宝活动已售完"),
	RaiderShoppingSaleOut(30102,"购物车所有商品都已经售完"),
	RaiderCreateCartOrderFaile(30101,"创建购物车订单失败"),
	
	/**积分签到相关 4开头**/
	

	/**积分相关 4开头*/
	IntegralError(40100,"查询积分异常"),
	IntegralNotCardCode(40101,"该优惠卷不存在"),
	IntegralLack(40102,"对不起，积分不够"),
	SignCreditStatusNull(40101,"签到状态不能为空"),
	IntegralDeductNotSeccess(40103,"积分扣除不成功"),
	;
	
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
	
	/**
	 * 根据错误编码获取错误信息
	 * @param errorCode
	 * @return
	 */
	public static String getErrorMsgByCode(int errorCode) {
		ErrorCode[] errors = ErrorCode.values();
		for(ErrorCode error : errors) {
			if(error.getErrorCode() == errorCode) {
				return error.getErrorMsg();
			}
		}
		return "";
	}
}
