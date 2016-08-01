/**  
 * @Project: xiu
 * @Title: XiuConstant.java
 * @Package org.lazicats.xiu.common.constant
 * @Description: TODO
 * @author: chengyuanhuan
 * @date 2013-5-6 上午10:42:46
 * @Copyright: BroadenGate Software Services Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.xiu.mobile.simple.common.constants;

import com.xiu.mobile.simple.common.util.ConfigUtil;

/**
 * xiu平台
 * @ClassName: XiuConstant 
 * @Description: TODO
 * @author: chengyuanhuan
 * @date 2013-5-6 上午10:42:55
 *
 */
public class XiuConstant {
	public static String TOKENID = "tokenId";// tokenId
	public static String LOGIN_INFO = "loginInfo";// 登录人的一些信息，包含tokenId addressList couponList
	public static String USER_ID = "userId"; // 登录后的userId
	public static String WURL = "wurl";// 页面url，用来记录历史浏览
	public static String ACTIVITY_MAP = "activityParamsMap";// 记录页面频道活动页面传来的参数，防止数据丢失

	public static String RETCODE_SUCCESS = "000";// 成功返回码

	public static String SAFE_CODE = ConfigUtil.getValue("safe_code");// 密钥 xiu 提供

	public static String G1_IMG = "/g1_150_150.jpg";// SKU 生成图片

	public static String IMG2_PREFIX = "http://10.0.0.118";
	public static String IMG3_PREFIX = "http://3.xiustatic.com";// 第三台服务器 图片域名

	public static String XIU_INTERFACE_PREFIX = ConfigUtil.getValue("interface_url");// 接口URL前缀

	/**支付成功回调地址（旧版）*/
	public static String CALLBACK_URL_SUFFIX = "/order/paySuccess.shtml";

	/**支付成功回调地址*/
	public static String PAY_SUCCESS_CALLBACK_URL = ConfigUtil.getValue("pay.success.callback.url");

	/**支付中心签名密码*/
	public static String PAY_SIGN_KEY = ConfigUtil.getValue("pay.sign.key");

	/**支付中心URL*/
	public static String REMOTE_URL_PAY = ConfigUtil.getValue("remote.url.pay");

	/**支付中心定义无线代码*/
	public static String PAY_MERCHANT_ID = ConfigUtil.getValue("pay.merchant.id");

	public static String DEVICE_SESSION = "device_info_session";// 设备信息在httpsession的缓存名字
	public static String BRANDURL = "http://list.xiu.com/";
	public static String BRANDURL1 = "http://list.xiu.com/";
	public static String BRANDURL2 = "http://brand.xiu.com/";
	public static String BRANDURL3 = "http://search.xiu.com/";
	public static String BRANDURL4 = "http://www.xiu.com/cx/";

	/**
	 * 订单媒体
	*/
	public static final String MEDIA_URL = "http://media.xiu.com/zs_media_from.php?act=query&user_id=";

	public static String[] XIUSTATIC =    { "http://1.xiustatic.com", "http://2.xiustatic.com", "http://3.xiustatic.com"};
	public static String[] MOBILE_IMAGE = { "http://1.xiustatic.com", "http://2.xiustatic.com", "http://3.xiustatic.com"};

	// topic 首页每次条数
	public static int TOPIC_PAGE_SIZE = 20;

	
	//hession批量请求最大数量
	public static final int HESSION_BATCH_MAX_NUMBER = 30;

}
