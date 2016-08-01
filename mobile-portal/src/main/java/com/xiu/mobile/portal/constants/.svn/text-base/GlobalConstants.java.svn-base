package com.xiu.mobile.portal.constants;

import javax.crypto.SecretKey;

import com.xiu.mobile.portal.common.util.ConfInTomcat;
import com.xiu.mobile.portal.common.util.EncryptUtils;
import com.xiu.mobile.portal.common.util.ParseProperties;


public interface GlobalConstants {

	// 与App客户端约定好的安全密钥串
	public static final String SAFE_CODE = "$#%^&abcdeF99913245678898978989qwertyuioplkjhgfdsazxcvbnm";
	// 缓存公共密钥对象
	public static final SecretKey SECRET_KEY = EncryptUtils.getSecretKey(SAFE_CODE);

	// 订单的来自无线的代码
	public static final String ORDER_FROM_CODE_MOBILE = "2";

	// 渠道标识
	public static final String CHANNEL_ID = ParseProperties.getPropertiesValue("CHANNEL_ID");
	public static final String STORE_ID = ParseProperties.getPropertiesValue("STORE_ID");
	// 优惠券业务使用的渠道标识

	public static final String COUPON_CHANNEL_ID = ParseProperties.getPropertiesValue("COUPON_CHANNEL_ID");

	/**在线时长*/
	public static final String ONLINE_EXPIRED_TIME = ParseProperties.getPropertiesValue("ONLINE_EXPIRED_TIME");

	public static final String KEY_REMOTE_IP = "remoteIpAddr";
	public static final String KEY_SESSION_USER = "sessionUser";

	// 输入参数的key： 详细数据
	public static final String PARAM_KEY_DETAIL = "detail";
	// 输入参数的key： 版本号
	public static final String PARAM_KEY_VERSION = "v";
	// 输入参数的key： 身份校验码
	public static final String PARAM_KEY_IDENTITY = "identity";

	// 返回码的对应的key
	public static final String RET_KEY_RET_CODE = "retCode";
	// 出错提示
	public static final String RET_KEY_ERROR_MESSAGE = "errorMsg";

	// 返回成功标志到客户端
	public static final String RET_CODE_SUCESSS = "000";

	// 系统出错
	public static final String RET_CODE_SYS_ERROR = "001";

	// 输入JSON不合法
	public static final String RET_CODE_JSON_ERROR = "002";

	// 缺乏必填字段
	public static final String RET_CODE_MISSING_REQUIRED_FIELD = "003";

	// 会话超时或未登录
	public static final String RET_CODE_SESSION_TIMEOUT = "004";

	// 不支持的接口
	public static final String RET_CODE_API_NOT_FOUND = "005";

	// 其他消息
	public static final String RET_CODE_OTHER_MSG = "006";

	// 没有待push的消息
	public static final String RET_CODE_NO_PUSH_MESSAGE = "007";

	public static final String RET_CODE_ORDER_COUPON_ERROR = "100";

	// 注册用户失败：已存在用户名
	public static final String RET_CODE_REGISTER_FAILED = "102";

	// 用户名不存在或密码错误
	public static final String RET_CODE_LOGON_FAILED = "103";

	// 页码参数
	public static final String PAGE_NUM = "pageNum";
	public static final int DEFAULT_PAGE_NUM = 1;

	// 页码大小参数
	public static final String PAGE_SIZE = "pageSize";
	public static final int DEFAULT_PAGE_SIZE = 50;
	public static final int DEFAULT_PAGE_SIZE_2 = 8;

	public static final String START_POS = "startPos";
	public static final String END_POS = "end_pos";

	public static final String RET_CODE_NO_GOODS_SN = "401";
	public static final String RET_CODE_NO_GOODS_SN_MSG = "商品SN不存在";

	// 运营分类
	public static final String O_CAT_TYPE = "2";
	// 地址信息省类型
	public static final String ADDRESS_PARAM_TYPE_PROVINCE = "1";
	// 地址信息市类型
	public static final String ADDRESS_PARAM_TYPE_REGION = "2";
	// 地址信息县类型
	public static final String ADDRESS_PARAM_TYPE_CITY = "3";
	// 地址信息邮编
	public static final String ADDRESS_PARAM_TYPE_POST_CODE = "4";
	// 优惠券终端类型 XOP继承过来的
	public static final String TERMINAL_USER_TYPE = "1";
	// 配置文件名称
	public static final String PROPERTIES_FILE_NAME = "mobile.properties";
	// 跳转苹果应用
	public static final String DOWNLOAD_APP_URL = "DOWNLOAD_APP_URL";
	// 点击统计servlet操作标识符,跳转
	public static final String DOWN_CLICK_REDIRECT = "redirect";
	// 注册是否送券
	public static final String IS_REGISTER_SEND_COUPON = "IS_REGISTER_SEND_COUPON";
	// 设备类型iphone
	public static final String DEVICE_TYPE_IPHONE = "1";
	// 设备类型ipad
	public static final String DEVICE_TYPE_IPAD = "2";
	// 是否debug返回给客户端的json
	public static final String ENABLE_DEBUG_RESPONSE_JSON = "enable.debug.response.json";
	// 虚拟用户ID(在购物袋页面进行订单计算时，虚拟一个不存在用户ID)
	public static final String VIRTUAL_USER_ID = "-1";
	// 全球速递商品标识
	public static final int GLOBAL_FLAG = 2;

	public final static String APP_ID_IPHONE = "com.xiu.zoshow.iPhone";

	/***liweibiao ***/
	// 1代表广告
	public final static String AD_MEAN = "1";
	// 2代表全球新品
	public final static String GLOBAL_NEW_MEAN = "2";
	// 3代表限时特卖
	public final static String LIMITED_SELL_MEAN = "3";

	/** 联合登陆用户来源  TENCENT_QQ(腾讯QQ)*/
	public final static String TENCENT_QQ = "tencent_qq";
	/** 联合登陆用户来源  TENCENT_WECHAT(腾讯微信)*/
	public final static String TENCENT_WECHAT = "tencent_wechat";
	/** 联合登陆用户来源  ALIPAY(支付宝)*/
	public final static String ALIPAY = "alipay";
	/** 联合登陆用户来源  SINA_WEIBO(新浪微博)*/
	public final static String SINA_WEIBO = "sina_weibo";
	/** 联合登陆用户来源  NETEASE(网易)*/
	public final static String NETEASE = "netEase";
	/** 联合登陆用户来源  MOBILE_139(139邮箱)*/
	public final static String MOBILE139 = "139";
	/** 联合登陆用户来源  FANLI_51(返利网)*/
	public final static String FANLI_51 = "51fanli";
	/** 联合登陆用户来源 万里通*/
	public final static String WANLITONG = "wanlitong";

	/** 联合登陆用户渠道ID  PARTNER_CHANNEL_WECHAT(腾讯微信)*/
	public final static Integer PARTNER_CHANNEL_WECHAT = Integer.valueOf(ParseProperties.getPropertiesValue("PARTNER_CHANNEL_WECHAT"));
	
	/**
	 * 支付相关
	 */
	public final static String PAY_MERCHANT_ID = ParseProperties.getPropertiesValue("pay.merchant.id");
	public final static String REMOTE_URL_PAY = ParseProperties.getPropertiesValue("remote.url.pay");
	public final static String PAY_SIGN_KEY = ConfInTomcat.getValue("pay.sign.key");
	public final static String PAY_SUCCESS_CALLBACK_URL = ParseProperties.getPropertiesValue("pay.success.callback.url");

	/**
	 * 订单商品来源默认值
	 */
	public final static String GOODS_FROM_DEFAULT = "UC0000";
	//日期格式
	public final static String DATE_FROMAT = "yyyy-MM-dd hh:mm:ss";
	public final static String DATE_2013 = "2013-01-01 00:00:00";

	
	/***
	 * 用户名分类  type(1: 邮箱, 2:手机, 3:呢称)
	 */
	public final static String UUC_USERNAME_EMAIL = "1";
	public final static String UUC_USERNAME_PHONE = "2";
	public final static String UUC_USERNAME_NICKNAME = "3";
	
	//发现频道版本号
	public final static String XIU_MOBILE_FINDCHANNEL_VERSION = "xiu.mobile.findChannelVersion";
	
	// 根据品牌查询商品数量接口
	public static final String GET_GOODSNUM_BY_BRAND = ParseProperties.getPropertiesValue("remote.url.mbrand.goodscount");
	// 根据品牌查询品牌下的评论数量接口
	public static final String GET_COMMENTNUM_BY_BRAND = ParseProperties.getPropertiesValue("remote.url.comment.countOfBrand");
	// 根据商品查询商品下的评论数量接口
	public static final String GET_COMMENTNUM_BY_PROD = ParseProperties.getPropertiesValue("remote.url.comment.countOfOneGoods");
	
	//订单是否支持多笔支付，是
	public static final int MUTIL_PAY_YES = 1;
	//订单是否支持多笔支付，否
	public static final int MUTIL_PAY_NO = 0;
}
