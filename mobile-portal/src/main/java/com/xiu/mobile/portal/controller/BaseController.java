package com.xiu.mobile.portal.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.xiu.mobile.core.exception.EIBusinessException;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.constants.GlobalConstants;
import com.xiu.mobile.portal.facade.utils.HttpUtil;
import com.xiu.mobile.portal.service.ILoginService;

@Controller
public abstract class BaseController {
	@Autowired
	private ILoginService loginService;
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
		map.put("remoteIpAddr", HttpUtil.getRemoteIpAddr(request));
		return map;
	}
	
	
	// 检查登陆
	protected boolean checkLogin(HttpServletRequest request)throws Exception{
		try{
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		String tokenId = SessionUtil.getTokenId(request);
		params.put("tokenId", tokenId);
		params.putAll(getDeviceParams(request));
		// 添加IP信息，登录，获取用户状态等接口需要。
		params.put(GlobalConstants.KEY_REMOTE_IP,
				HttpUtil.getRemoteIpAddr(request));
		// 检查登陆
		if (!StringUtils.isEmpty(tokenId)
				&& loginService.checkOnlineStatus(params)) {
			return true;
		} else {
			return false;
		}
	}
	catch(EIBusinessException e){
		return false;
	}
	
	}
	
	/**
	 * 获取系统错误提示结果
	 * @param resultMap
	 * @return
	 */
	protected Map<String, Object> getErroResultMap(Map<String, Object> resultMap) {
		resultMap.put("result", false);
		resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
		resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		return resultMap;
	}
	/**
	 * 获取返回结果
	 * @param resultMap
	 * @return
	 */
	protected Map<String, Object> getResultMap(Map<String, Object> resultMap,Boolean reusult,ErrorCode code) {
		resultMap.put("result", reusult);
		resultMap.put("errorCode",code.getErrorCode());
		resultMap.put("errorMsg", code.getErrorMsg());
		return resultMap;
	}
	
}
