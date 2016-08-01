package com.xiu.mobile.wechat.core.constants;

import com.xiu.mobile.wechat.core.utils.ConfigUtil;

/**
 * 微信常量类
 *
 * @author wangzhenjiang
 * @since 2014年5月20日
 */
public class Constants {

	public static final String TYPE_WEB = "web";
	public static final String TYPE_APP = "app";

	/** --------------微信公众平台相关信息 BEGIN-------------------------- */
	/** 公众号appId */
	public static final String WEB_APPID = ConfigUtil.INSTANCE.getString("wechat.web.appId");
	/** 公众号appSecret */
	public static final String WEB_APPSECRET = ConfigUtil.INSTANCE.getString("wechat.web.appSecret");
	/** 公众号支付签名paysignkey */
	public static final String WEB_PAYSIGNKEY = ConfigUtil.INSTANCE.getString("wechat.web.paySignKey");

	/** 开放平台appId（app微信支付） */
	public static final String APP_APPID = ConfigUtil.INSTANCE.getString("wechat.app.appId");
	/** 开放平台appSecret（app微信支付） */
	public static final String APP_APPSECRET = ConfigUtil.INSTANCE.getString("wechat.app.appSecret");
	/** 开放平台支付签名paysignkey（app微信支付） */
	public static final String APP_PAYSIGNKEY = ConfigUtil.INSTANCE.getString("wechat.app.paySignKey");
	/** --------------微信公众平台相关信息 END---------------------------- */

	/** ---------------微信API BEGIN------------------------------- */
	/** 获取access_token令牌接口url */
	public static final String GET_TOKEN_URL = ConfigUtil.INSTANCE.getString("api.get.token");
	/** 授权获取code接口url（scope=snsapi_base,不弹出授权页面,只能获取到openid） */
	public static final String AUTH_BASE_URL = ConfigUtil.INSTANCE.getString("api.oauth.base");
	/** 授权获取code接口url(scope=snsapi_userinfo,弹出授权页面,可以通过 openid 拿到昵称、性别、所在地) */
	public static final String AUTH_USERINFO_URL = ConfigUtil.INSTANCE.getString("api.oauth.userinfo");
	/** 获取openId接口url */
	public static final String GET_OPENID_URL = ConfigUtil.INSTANCE.getString("api.get.openid");
	/** 获取用户信息接口url */
	public static final String GET_USERINFO_URL = ConfigUtil.INSTANCE.getString("api.get.userinfo");
	/** 未关注获取用户信息接口url */
	public static final String GET_WEIXIN_USERINFO_URL = ConfigUtil.INSTANCE.getString("api.get.weixin.userinfo");
	/** 未关注获取用户信息接口url */
	public static final String GET_WEIXIN_TICKET_URL = ConfigUtil.INSTANCE.getString("api.get.weixin.ticket");
	/** 未关注获取用户信息接口url */
	public static final String SEND_WEIXIN_TEMPLATEMSG_URL = ConfigUtil.INSTANCE.getString("api.send.weixin.templatemsg");
	/** 未关注获取用户信息接口url */
	public static final String GET_WEIXIN_REMOTETOKEN_URL = ConfigUtil.INSTANCE.getString("api.get.weixin.remotetoken");

	
	/** ---------------微信API END--------------------------------- */

	/** ----------微信API重定向相关配置BEGIN--------------------------- */
	/** 基础域名 */
	public static final String BASE_DOMAIN = ConfigUtil.INSTANCE.getString("base.domain");

	/** 检查绑定URL */
	public static final String REDIRECT_CHECK_BIND = BASE_DOMAIN + "/binding/checkBind?targetUrl=%s";

	/** 注册、登录获取用户信息URL */
	public static final String REDIRECT_USER_INFO = BASE_DOMAIN + "/weixinlogin/userInfo?targetUrl=%s";

	/** ----------微信API重定向相关配置END----------------------------- */

	/** ----------微信相关常量定义BEGIN------------------------------- */
	/** 用户关注服务号状态 0-未关注 */
	public static final String NOT_SUBSCRIBE = "0";
	/** 用户关注服务号状态 1-已关注 */
	public static final String HAS_SUBSCRIBED = "1";

	/** 用户取消授权时返回的CODE值 */
	public static final String CANCEL_CODE = "authdeny";

	/** 微信维权消息类型 requst - 用户提交投诉 */
	public static final String FEEDBACK_MSG_TYPE_REQUEST = "request";
	/** 微信维权消息类型 confirm - 用户确认消除投诉(已处理的投诉) */
	public static final String FEEDBACK_MSG_TYPE_CONFIRM = "confirm";
	/** ----------微信相关常量定义END--------------------------------- */

	/** ----------微信wap授权常量定义BEGIN------------------------------- */
	//微信wap回调路径
	public static final String WAP_USER_INFO = BASE_DOMAIN + "/wapunion/userInfo?targetUrl=%s";
	//微信wap登陆后跳转地址
	public static final String WAP_TARGET_URL = ConfigUtil.INSTANCE.getString("xiu.wechat.wap.targeturl");
	//未登陆地址
	public static final String WAP_NOLOGIN_URL = ConfigUtil.INSTANCE.getString("xiu.wechat.wap.nologinurl");
	/** ----------微信wap授权相关常量定义END--------------------------------- */
	
	/**
	 * 获取openid
	 */
	public static final String GET_OPENID = BASE_DOMAIN + "/auth/getOpenId?targetUrl=%s";
	
	//微信静默授权回调路径
	public static final String USER_INFO = BASE_DOMAIN + "/weixininfo/userInfo?targetUrl=%s";

}
