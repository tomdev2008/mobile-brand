package com.xiu.mobile.simple.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiu.mobile.simple.common.config.XiuInterface;
import com.xiu.mobile.simple.common.constants.XiuConstant;
import com.xiu.mobile.simple.model.DeviceVo;
import com.xiu.mobile.simple.model.LoginResVo;

public class SessionUtil {
	/**
	 * 写写cookie用
	 */
	private static final String LOGIN_TOKENID = "xiu.login.tokenId";
	private static final String LOGIN_USERID = "xiu.login.userId";
	private static final String LOGIN_USERNAME = "xiu.login.userName";
	private static final String REGISTER_FROM = "register_from";
	private static final String PRAISE_DEVICE_ID = "praise.device.id";

	/**
	 * 获取登录用户信息 包含tokenId addressList couponList
	 * 
	 * @Title: getLoginInfo
	 * @param request
	 * @return Map<String,Object>
	 * @author: jack.jia
	 * @date: 2013-5-6上午11:10:30
	 */
	public static LoginResVo getLoginInfo(HttpServletRequest request) {

		String token_id = getCookie(request, LOGIN_TOKENID);
		if (null == token_id || "".equals(token_id)) {
			return null;
		}

		LoginResVo lrv = new LoginResVo();
		lrv.setLogonName(getCookie(request, LOGIN_USERNAME));
		lrv.setUserId(getCookie(request, LOGIN_USERID));
		lrv.setTokenId(getCookie(request, LOGIN_TOKENID));

		return lrv;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public static LoginResVo getUser(HttpServletRequest request) {
		return getLoginInfo(request);
	}
	
	/**
	 * 
	 * @Title: getDeviceInfo
	 * @Description: 从session获取设备信息
	 * @param request
	 * @return
	 * @throws
	 * @author: Hualong
	 * @date: 2013-5-6下午04:13:08
	 */
	public static void setDeviceInfo(HttpServletRequest request, DeviceVo deviceVo) {
		if (null != deviceVo) {
			deviceVo.setAppVersion(XiuInterface.appVersion);
			deviceVo.setDeviceType(XiuInterface.deviceType);
			deviceVo.setImei(XiuInterface.imei);
			deviceVo.setMarketingFlag(XiuInterface.marketingFlag);
			// 客户端信息,只能得到deviceSn, iosVersion
			String ua = request.getHeader("User-Agent");
			String deviceSn = "unknow";// 设备号
			String iosVersion = "unknow";
			if (ua != null) {
				if (ua.indexOf("iPhone") > 0) {
					deviceSn = "iPhone";
					for (int v = 0; v < 10; v++) {// 取得iphone型号
						if (ua.indexOf("iPhone OS " + v + "_") >= 0) {
							iosVersion = String.valueOf(v);
							break;
						}
					}
				} else if (ua.indexOf("Android") > 0) {
					deviceSn = "Android";
				} else if (ua.indexOf("MSIE") > 0) {
					deviceSn = "winPhone";
				}
			}
			deviceVo.setDeviceSn(deviceSn);
			deviceVo.setIosVersion(iosVersion);
			deviceVo.setIp(request.getRemoteAddr());
			deviceVo.setUserId(getUserId(request));
		}
	}

	/**
	 * 
	 * @Title: getDeviceInfo
	 * @Description: 从Post提交中获取设备信息
	 * @param request
	 * @return
	 * @throws
	 * @author: Hualong
	 * @date: 2013-5-6下午04:13:08
	 */
	public static void setDeviceInfoByPost(HttpServletRequest request, DeviceVo deviceVo) {
		if (null != deviceVo) {
			deviceVo.setAppVersion(request.getParameter("appVersion"));
			deviceVo.setDeviceSn(request.getParameter("deviceSn"));
			deviceVo.setDeviceType((request.getParameter("deviceType")));
			deviceVo.setImei(request.getParameter("imei"));
			deviceVo.setIosVersion(request.getParameter("iosVersion"));
			deviceVo.setMarketingFlag(request.getParameter("marketingFlag"));
		}
	}

	/**
	 * 得到要登录后跳转的路径
	 * 
	 * @Title: getWurl
	 * @return Object
	 * @author: chengyuanhuan
	 * @date: 2013-5-9上午10:26:19
	 */

	public static String getWurl(HttpServletRequest request) {
		return (String) request.getSession().getAttribute(XiuConstant.WURL);
	}

	/**
	 * 设置登录cookie
	 * @param response
	 * @param lrv
	 */
	public static void addLoginCookie(HttpServletResponse response, LoginResVo lrv) {
		CookieUtil.getInstance().addCookie(response, LOGIN_TOKENID, lrv.getTokenId());
		CookieUtil.getInstance().addCookie(response, LOGIN_USERID, lrv.getUserId());
		CookieUtil.getInstance().addCookie(response, LOGIN_USERNAME, lrv.getLogonName());
	}

	/**
	 * 清除登录cookie
	 * @param response
	 */
	public static void deleteLoginCookie(HttpServletResponse response) {
		CookieUtil.getInstance().deleteCookie(response, LOGIN_TOKENID);
		CookieUtil.getInstance().deleteCookie(response, LOGIN_USERID);
		CookieUtil.getInstance().deleteCookie(response, LOGIN_USERNAME);
	}

	/**
	 * 获取xiu.com的cookie
	 * @param request
	 * @param cookieKey
	 * @return
	 */
	public static String getCookie(HttpServletRequest request, String cookieKey) {
		return CookieUtil.getInstance().getCookieValue(request, ".xiu.com", cookieKey);
	}

	/**
	 * 获取tokenId
	 * @param request
	 * @return
	 */
	public static String getTokenId(HttpServletRequest request) {
		return CookieUtil.getInstance().getCookieValue(request, ".xiu.com", LOGIN_TOKENID);
	}

	/**
	 * 获取userId
	 * @param request
	 * @return
	 */
	public static String getUserId(HttpServletRequest request) {
		return CookieUtil.getInstance().getCookieValue(request, ".xiu.com", LOGIN_USERID);
	}

	/**
	 * 获取UserName
	 * @param request
	 * @return
	 */
	public static String getUserName(HttpServletRequest request) {
		return CookieUtil.getInstance().getCookieValue(request, ".xiu.com", LOGIN_USERNAME);
	}
	/**
	 * 获取推荐注册
	 * @param request
	 * @return
	 */
	public static String getRegisterFrom(HttpServletRequest request) {
		return CookieUtil.getInstance().getCookieValue(request, ".xiu.com", REGISTER_FROM);
	}
	
	
	/**
	 * 设置点赞cookie
	 * @param response
	 * @param deviceId
	 */
	public static void addGoodPraiseCookie(HttpServletResponse response, String deviceId) {
		CookieUtil.getInstance().addCookie(response, PRAISE_DEVICE_ID, deviceId);
	}
	
	/**
	 * 获取点赞设备Id
	 * @param request
	 * @return
	 */
	public static String getPraiseDeviceId(HttpServletRequest request) {
		return CookieUtil.getInstance().getCookieValue(request, ".xiu.com", PRAISE_DEVICE_ID);
	}
	
}
