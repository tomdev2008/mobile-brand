package com.xiu.mobile.simple.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * 
 * Cookie工具类
 * 
 * @author wangzhenjiang
 *
 */
public final class CookieUtil {
	private static final Logger logger = LoggerFactory.getLogger(CookieUtil.class);

	private static final String COOKIE_IS_SECURE = ConfigUtil.getValue("cookie.isSecure");
	private static final String COOKIE_MAX_AGE = ConfigUtil.getValue("cookie.maxAge");
	private static final String COOKIE_PATH = ConfigUtil.getValue("cookie.path");
	private static final String COOKIE_DOMAIN = ConfigUtil.getValue("cookie.domain");

	static CookieUtil util = new CookieUtil();

	public static CookieUtil getInstance() {
		return util;
	}

	/**
	 * 获取cookie的值，包括从合并后的cookie中取值
	 * 
	 * @Title: readCookieValue
	 * @param request
	 * @param key
	 * @return String
	 */
	public String readCookieValue(HttpServletRequest request, String key) {
		String domain = StringUtils.isEmpty(COOKIE_DOMAIN) ? ".xiu.com" : COOKIE_DOMAIN;
		return getCookieValue(request, domain, key);
	}

	/**
	 * 获取Cookie的值
	 * 
	 * @param request
	 * @param domain
	 * @param name
	 * @return value
	 */
	public String getCookieValue(HttpServletRequest request, String domain, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (null != cookie && cookie.getName().equals(name)) {
					try {
						return URLDecoder.decode(cookie.getValue(), "utf-8");
					} catch (UnsupportedEncodingException e) {
						logger.error("对Cookie解码时发生异常：" + e.getMessage());
					}
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @Title: addCookie
	 * @Description: 增加cookie
	 * @param response
	 * @param key
	 * @param value
	 * @return void
	 * @throws
	 */
	public void addCookie(HttpServletResponse response, String key, String value) {

		try {
			value = URLEncoder.encode(value, "utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("URLEncoder.encode : " + e.getMessage());
		}

		Cookie cookie = new Cookie(key, value);

		int maxAge = Integer.parseInt(StringUtils.isEmpty(COOKIE_MAX_AGE) ? "-1" : COOKIE_MAX_AGE);
		Boolean isSecure = Boolean.valueOf(COOKIE_IS_SECURE);

		cookie.setDomain(StringUtils.isEmpty(COOKIE_DOMAIN) ? ".xiu.com" : COOKIE_DOMAIN);
		cookie.setPath(StringUtils.isEmpty(COOKIE_PATH) ? "/" : COOKIE_PATH);
		cookie.setMaxAge(maxAge);
		cookie.setSecure(isSecure);
		response.setCharacterEncoding("UTF-8");
		response.addCookie(cookie);
	}

	public void deleteCookie(HttpServletResponse response, String key) {
		Cookie cookie = new Cookie(key, null);
		cookie.setDomain(StringUtils.isEmpty(COOKIE_DOMAIN) ? ".xiu.com" : COOKIE_DOMAIN);
		cookie.setPath(StringUtils.isEmpty(COOKIE_PATH) ? "/" : COOKIE_PATH);
		cookie.setMaxAge(0);

		response.addCookie(cookie);
	}
}
