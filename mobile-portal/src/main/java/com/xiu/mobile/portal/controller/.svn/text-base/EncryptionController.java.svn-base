package com.xiu.mobile.portal.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.EncryptUtils;
import com.xiu.mobile.portal.service.ILoginService;

/**
 * 
 * @author wangchengqun
 * @date 20140701
 * 
 */
@Controller
@RequestMapping("/test")
public class EncryptionController extends BaseController {
	private Logger logger = Logger.getLogger(EncryptionController.class);

	@Autowired
	private ILoginService loginService;
	/**
	 * 获取mportal加密密钥
	 * 
	 * @param request
	 * @param jsoncallback
	 * @param type
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/testtimeout", produces = "text/html;charset=UTF-8")
	public String getAesKey(HttpServletRequest request,String jsoncallback) {
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

		try {
			String aesKey = EncryptUtils.getAESKeyShare();
			resultMap.put("testMessage", aesKey);
			resultMap.put("result", true);
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取mportal加密密钥时发生异常：" + e.getMessage());
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	
	/**
	 * Aes的加密串用于orderId,addresId,bankAccountId等
	 * 
	 * @param request
	 * @param jsoncallback
	 * @param type
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/testselfout", produces = "text/html;charset=UTF-8")
	public String getAesKeySelf(HttpServletRequest request,String jsoncallback) {
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

		try {
			String aesKey = EncryptUtils.getAESKeySelf();
			resultMap.put("testMessage", aesKey);
			resultMap.put("result", true);
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取Aes的加密串用于orderId,addresId,bankAccountId等时发生异常：" + e.getMessage());
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	/**
	 * Aes的加密串 代支付加密key
	 * 
	 * @param request
	 * @param jsoncallback
	 * @param type
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/testpayother", produces = "text/html;charset=UTF-8")
	public String getAesKeyPayOther(HttpServletRequest request,String jsoncallback) {
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

		try {
			String aesKey = EncryptUtils.getAESKeyPayForOthers();
			resultMap.put("testMessage", aesKey);
			resultMap.put("result", true);
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取Aes的加密串 代支付加密key时发生异常：" + e.getMessage());
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	/**
	 * 获取手机验证码
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMobileVerifyCode", produces = "text/html;charset=UTF-8")
	public String getMobileVerifyCode(HttpServletRequest request,String jsoncallback) {
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		String mobile = request.getParameter("mobile");
		
		if(StringUtils.isBlank(mobile)) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		try {
			String result = loginService.getMobileVerifyCode(mobile);
			resultMap.put("result", true);
			resultMap.put("verifyCode", result);
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取手机验证码时发生异常：" + e.getMessage());
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
}
