package com.xiu.mobile.wechat.web.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xiu.sso.facade.result.SsoResult;

public class WebUtils {

	/**
	 * Key：SSO返回错误码 | Value：中文描述
	 */
	private static Map<String, String> ssoResultMap = new HashMap<String, String>();

	static {
		try {
			for (SsoResult.SsoCodeEnum ssoCodeEnum : SsoResult.SsoCodeEnum.values()) {
				ssoResultMap.put(ssoCodeEnum.getCode(), ssoCodeEnum.getDescribe());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 返回远程访问者的IP地址
	public static String getRemoteIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Cluster-Client-Ip");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 获取URLEncoder编码之后的内容，如果发生异常，则返回源字符串
	 * 
	 * @param value
	 * @param charset
	 * @return String
	 */
	public static String getURLEncoderVal(String value, String charset) {
		try {
			return URLEncoder.encode(value, charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 获取登录返回信息
	 * 
	 * @param errorCode
	 * @return String
	 */
	public static String getErrorMsg(String errorCode) {
		return ssoResultMap.get(errorCode);
	}
}
