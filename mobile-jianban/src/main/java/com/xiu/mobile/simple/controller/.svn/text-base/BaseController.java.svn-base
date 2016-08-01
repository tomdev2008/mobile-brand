package com.xiu.mobile.simple.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

@Controller
public abstract class BaseController {

	@InitBinder
	protected void ininBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}

	protected Map<String, String> getDeviceParams(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String ua = request.getHeader("User-Agent");
		String deviceType = "1";// 设备类型1：iphone 2：ipad 接口为2才能调
		String deviceSn = "unknown";// 设备号
		String appVersion = "wap 1.0";// 非应用哪来应用端版本号
		String iosVersion = "unknown";
		String imei = "unknown";// 手机内部15位码，无法获取
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
		map.put("deviceType", deviceType);
		map.put("deviceSn", deviceSn);
		map.put("imei", imei);
		map.put("appVersion", appVersion);
		map.put("iosVersion", iosVersion);
		map.put("marketingFlag", "1");
		map.put("remoteIpAddr", request.getRemoteAddr());
		return map;
	}
}
