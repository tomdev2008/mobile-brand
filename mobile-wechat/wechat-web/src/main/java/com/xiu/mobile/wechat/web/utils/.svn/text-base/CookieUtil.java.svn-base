package com.xiu.mobile.wechat.web.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * Cookie工具类
 * 
 * @author wangzhenjiang
 *
 */
public final class CookieUtil {
	/** Cookie 设置 */
	private final boolean COOKIE_IS_SECURE = false;
	private final Integer COOKIE_MAX_AGE = 2147483646;
	private final String COOKIE_PATH = "/";
	private final String COOKIE_DOMAIN = ".xiu.com";
	/** 登录Cookie：tokenId */
	private final String LOGIN_TOKENID = "xiu.login.tokenId";
	/** 登录Cookie：userId */
	private final String LOGIN_USERID = "xiu.login.userId";
	/** 登录Cookie：userName */
	private final String LOGIN_USERNAME = "xiu.login.userName";
	/** 登录Cookie：userName */
	private final String LOGIN_PETNAME = "xiu.login.petName";
	
	/** 登录Cookie：nickName 昵称 */
	private final String LOGIN_NICKNAME = "xiu.login.nickName";
	/** 登录Cookie：unionId 联合登陆id */
	private final String LOGIN_UNIONID = "xiu.login.unionId";
	/** 登录Cookie：headImgUrl 用户头像 */
	private final String LOGIN_HEADIMGURL = "xiu.login.headImgUrl";
	/**	登陆Cookie：activity是否经过活动授权 */
	public final String AOUTH_ACTIVITY ="xiu.login.activity";
	
	private static CookieUtil util = new CookieUtil();

	public static CookieUtil getInstance() {
		return util;
	}

	/**
	 * 获取Cookie的值
	 * 
	 * @param request
	 * @param name
	 * @return value
	 */
	public String readCookieValue(HttpServletRequest request, String domain, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (null != cookie && cookie.getName().equals(name)) {
					return WebUtils.getURLEncoderVal(cookie.getValue(), "utf-8");
				}
			}
		}
		return null;
	}

	/**
	 * 增加cookie
	 * 
	 * @param response
	 * @param key
	 * @param value
	 */
	public void addCookie(HttpServletResponse response, String key, String value) {
		Cookie cookie = new Cookie(key, WebUtils.getURLEncoderVal(value, "utf-8"));
		cookie.setDomain(COOKIE_DOMAIN);
		cookie.setPath(COOKIE_PATH);
		cookie.setMaxAge(COOKIE_MAX_AGE);
		cookie.setSecure(COOKIE_IS_SECURE);
		response.setCharacterEncoding("UTF-8");
		response.addCookie(cookie);
	}

	/**
	 * 删除Cookie
	 * 
	 * @param response
	 * @param key
	 */
	public void delCookie(HttpServletResponse response, String key) {
		Cookie cookie = new Cookie(key, null);
		cookie.setDomain(COOKIE_DOMAIN);
		cookie.setPath(COOKIE_PATH);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}

	/**
	 * 获取tokenId
	 * 
	 * @param request
	 * @return tokenId
	 */
	public String getTokenId(HttpServletRequest request) {
		return readCookieValue(request, ".xiu.com", LOGIN_TOKENID);
	}

	/**
	 * 设置登录cookie
	 * 
	 * @param response
	 * @param lrv
	 */
	public void addLoginCookie(HttpServletResponse response, String tokenId, String userId, String loginName) {
		addCookie(response, LOGIN_TOKENID, tokenId);
		addCookie(response, LOGIN_USERID, userId);
		addCookie(response, LOGIN_USERNAME, loginName);
	}
	
	/**
	 * 设置登录cookie
	 * @param response
	 * @param tokenId
	 * @param userId
	 * @param petName
	 * @param nickName
	 * @param headimgUrl
	 * @param unionId
	 */
	public void addWeixinLoginCookie(HttpServletResponse response, String tokenId, String userId, 
			String petName,String nickName,String headimgUrl,String unionId) {
		addCookie(response, LOGIN_TOKENID, tokenId);
		addCookie(response, LOGIN_USERID, userId);
		addCookie(response, LOGIN_PETNAME, petName);
		addCookie(response, LOGIN_NICKNAME, nickName);
		addCookie(response, LOGIN_HEADIMGURL, headimgUrl);
		addCookie(response, LOGIN_UNIONID, unionId);
		//是否活动授权，如果非活动授权在没有关注的情况下取不到头像等信息
		addCookie(response,AOUTH_ACTIVITY,"true");
	}
}
