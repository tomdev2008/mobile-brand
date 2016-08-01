package com.xiu.mobile.simple.common.util;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Strings;
import com.xiu.mobile.simple.common.model.CPSCookieVo;

public class CPSCookieUtils {
	private static CPSCookieUtils util = new CPSCookieUtils();

	private CPSCookieUtils() {
	}

	public static CPSCookieUtils getInstance() {
		return util;
	}
	protected String readCookieValueFromCookie(HttpServletRequest request,
			String key) {

		Cookie[] cookies = request.getCookies();
		String value = null;
		if (cookies != null && cookies.length != 0) {

			javax.servlet.http.Cookie c = null;

			for (int i = 0; i < cookies.length; i++) {
				c = cookies[i];
				if (c.getName().equalsIgnoreCase(key)) {
					value = c.getValue();
					break;
				}
			}
		}

		return value;
	}

	protected String getValueByMergeKey(String keyValue, String key) {
		try {
			keyValue = URLDecoder.decode(keyValue, "utf-8");
		} catch (Exception e) {
			return null;
		}
		String[] keyvalues = keyValue.split("\\^\\^");
		String startStr = key + CPSCookieVo.equalStr;
		String value = "";
		for (int i = 0; i < keyvalues.length; i++) {
			if (keyvalues[i].indexOf(startStr) == 0) {
				value = keyvalues[i].substring(startStr.length(), keyvalues[i].length());
				break;
			}
		}
		return value;
	}

	
	
	public CPSCookieVo readCookieValueToCPS(HttpServletRequest request) {
		CPSCookieVo bean = new CPSCookieVo();
		bean.setA_id(readCookieValue(request,CPSCookieVo.COOKIE_NAME_CPS_A_ID));
		bean.setAbid(readCookieValue(request,CPSCookieVo.COOKIE_NAME_CPS_ABID));
		bean.setBid(readCookieValue(request,CPSCookieVo.COOKIE_NAME_CPS_BID));
		bean.setChannel_name(readCookieValue(request,CPSCookieVo.COOKIE_NAME_CHANNEL_NAME));
		bean.setChannel_type(readCookieValue(request,CPSCookieVo.COOKIE_NAME_CHANNEL_TYPE));
		bean.setCid(readCookieValue(request,CPSCookieVo.COOKIE_NAME_CPS_CID));
		bean.setCps(readCookieValue(request, CPSCookieVo.COOKIE_NAME_CPS));
		bean.setCps_type(readCookieValue(request,CPSCookieVo.COOKIE_NAME_CPSTYPE));
		bean.setFromid(readCookieValue(request,CPSCookieVo.COOKIE_NAME_FROMID));
		bean.setU_id(readCookieValue(request,CPSCookieVo.COOKIE_NAME_CPS_U_ID));
		bean.setUid(readCookieValue(request,CPSCookieVo.COOKIE_NAME_CPS_UID));
		bean.setW_id(readCookieValue(request,CPSCookieVo.COOKIE_NAME_CPS_W_ID));
		bean.setL_id(readCookieValue(request,CPSCookieVo.COOKIE_NAME_CPS_L_ID));
		bean.setL_type1(readCookieValue(request,CPSCookieVo.COOKIE_NAME_CPS_L_TYPE1)); 
		bean.setQmail(readCookieValue(request,CPSCookieVo.COOKIE_NAME_CPS_QMAIL));
		bean.setQname(readCookieValue(request,CPSCookieVo.COOKIE_NAME_CPS_QNAME));
		bean.setShopId( readCookieValue(request,CPSCookieVo.COOKIE_NAME_CPS_SHOPID));
		bean.setUnionParams(readCookieValue(request,CPSCookieVo.COOKIE_NAME_CPS_UNIONPARAMS));
		bean.setUserId(readCookieValue(request,CPSCookieVo.COOKIE_NAME_CPS_USERID));
		bean.setValueid(readCookieValue(request,CPSCookieVo.COOKIE_NAME_CPS_VALUEID));
		bean.setChannelId(readCookieValue(request,CPSCookieVo.COOKIE_NAME_CPS_CHANNELID));
		bean.setA_uid(readCookieValue(request,CPSCookieVo.COOKIE_NAME_CPS_A_UID));
		bean.setC_id(readCookieValue(request,CPSCookieVo.COOKIE_NAME_CPS_C_ID));
		return bean;
	}

	public String readCookieValue(HttpServletRequest request, String key) {
		
		String xcps = readCookieValueFromCookie(request,CPSCookieVo.CPS_COOKIE);
		String value = getValueByMergeKey(xcps, key);

		if (!"".equals(value)&&value!=null) {// 按原来方式取cookie值
			return value;
		}
		value = readCookieValueFromCookie(request, key);
		return value;
	}

	protected Cookie createCookie(String key, String value, String domain,String path, Boolean isSecure, int expire) {
		Cookie cookie = new Cookie(key, value);
		if (!Strings.isNullOrEmpty(domain)) {
			cookie.setDomain(domain);
		}

		if (!Strings.isNullOrEmpty(path)) {
			cookie.setPath(path);
		}

		if (isSecure != null) {
			cookie.setSecure(new Boolean(isSecure));
		}

		if (!(expire <= 0)) {
			cookie.setMaxAge(expire);
		}

		return cookie;
	}

	public void addCPSToCookie(HttpServletResponse response, CPSCookieVo bean) {
		String keyValue = null;
		try {
			keyValue = URLEncoder.encode(bean.toString(), "utf-8");
		} catch (Exception e) {
		}
		// 加密后的userId
		Cookie cookie = createCookie(CPSCookieVo.CPS_COOKIE, keyValue,".xiu.com", "", true, 60 * 60 * 24 * 30);
		response.addCookie(cookie);
	}

}
