package com.xiu.mobile.simple.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xiu.mobile.core.exception.BusinessException;
import com.xiu.mobile.core.exception.EIBusinessException;
import com.xiu.mobile.simple.common.constants.ErrorCode;
import com.xiu.mobile.simple.common.constants.XiuConstant;
import com.xiu.mobile.simple.common.json.JsonUtils;
import com.xiu.mobile.simple.common.util.AESCipher;
import com.xiu.mobile.simple.common.util.CookieUtil;
import com.xiu.mobile.simple.common.util.SessionUtil;
import com.xiu.mobile.simple.constants.GlobalConstants;
import com.xiu.mobile.simple.facade.utils.HttpUtil;
import com.xiu.mobile.simple.model.LoginResVo;
import com.xiu.mobile.simple.service.ILoginService;

/**
 * <p>
 * ************************************************************** 
 * @Description: 登陆控制器
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-15 上午10:50:20 
 * ***************************************************************
 * </p>
 */
@Controller
@RequestMapping("/loginReg")
public class LoginController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private ILoginService loginService;

	/**
	 * 登陆
	 * 
	 * @param request
	 * @return JSON
	 */
	@ResponseBody
	@RequestMapping(value = "requestSubmitLogin", produces = "text/html;charset=UTF-8")
	public String requestSubmitLogin(String jsoncallback,
			HttpServletRequest request, HttpServletResponse response) {
//		String regType = request.getParameter("memberVo.regType");
		String logonName = request.getParameter("memberVo.logonName");
		String password = request.getParameter("memberVo.password");
//		String isRemember = request.getParameter("memberVo.isRemember");
		//登录渠道(pc-web:官网;mobile-wap:手机wap;android-app android应用;ios-app IOS应用)
		String loginChannel=request.getParameter("loginChannel");
		if("".equals(loginChannel)||null==loginChannel){
			loginChannel="mobile-wap";
		}
		Map<String, Object> model = new HashMap<String, Object>();
		
		if(StringUtils.isEmpty(logonName) 
				|| StringUtils.isEmpty(password)) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.ParamsError.getErrorCode());
			model.put("errorMsg", ErrorCode.ParamsError.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, model);
		}
		
		try {
			password = new AESCipher(XiuConstant.SAFE_CODE).encrypt(password);
			Map<String, Object> pmap = new HashMap<String, Object>();
			pmap.put("logonName", logonName);
			pmap.put("password", password);
			pmap.putAll(getDeviceParams(request));// 设备信息
			// 添加IP信息，登录，获取用户状态等接口需要。
			String remoteIp = HttpUtil.getRemoteIpAddr(request);
			pmap.put(GlobalConstants.KEY_REMOTE_IP, remoteIp);
			pmap.put("loginChannel", loginChannel);
			LoginResVo lrv = loginService.login(pmap);
			
			// 登录成功后，将登录后的用户信息放入到session中
			lrv.setLogonName(logonName);
			SessionUtil.addLoginCookie(response, lrv);
			model.put("result", true);
			model.put("errorCode", ErrorCode.Success.getErrorCode());
			model.put("errorMsg", ErrorCode.Success.getErrorMsg());
		} 
		catch (EIBusinessException e) {
			model.put("result", false);
			model.put("retCode", GlobalConstants.RET_CODE_OTHER_MSG);
			model.put("errorMsg", e.getExtMessage());
			model.put("wurl", request.getContextPath() + "/loginReg/toLogin.shtml");// 登录失败后
			logger.error("RequestSubmitLogin error：{errorCode：" + e.getErrCode()
					+ ",errorMsg：" + e.getExtMessage() + "}"); // 跳转到登录页面
		}
		catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("RequestSubmitLogin system error{}", e);
		} 
		
		return JsonUtils.bean2jsonP(jsoncallback, model);
	}

	/**
	 * 联合登陆
	 * @param request
	 * @return JSON
	 */
	@ResponseBody
	@RequestMapping(value = "/submitFederateLogin", produces = "text/html;charset=UTF-8")
	public String unionLogin(String jsoncallback,
			HttpServletRequest request, HttpServletResponse response) {
		String logonName = request.getParameter("memberVo.logonName");
		String userSource = request.getParameter("userSource");
		String partnerId = request.getParameter("partnerId");
		
		// 存储返回结果值
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(StringUtils.isEmpty(logonName)) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.ParamsError.getErrorCode());
			result.put("errorMsg", ErrorCode.ParamsError.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, result);
		}
		
		try {
			Map<String, Object> pmap = new HashMap<String, Object>();
			pmap.put("retAddressAndCoupon", "0");
			pmap.put("partnerId", partnerId);
			pmap.put("logonName", logonName);
			/*
			 * 联合登陆用户来源 ：
			 * tencent_qq(QQ),alipay(支付宝),sina_weibo(新浪微博),netEase(网易)
			 * ,139(139邮箱),51fanli(返利网)
			 */
			pmap.put("userSource", userSource);
			pmap.putAll(getDeviceParams(request));// 设备信息
			LoginResVo lrv = loginService.submitFederateLogin(pmap);
			
			// 登录成功后，将登录后的用户信息放入到session中
			lrv.setLogonName(logonName);
			// 登录成功后，将登录后的用户信息放入到cookie中
			SessionUtil.addLoginCookie(response, lrv);

			String wurl = CookieUtil.getInstance().readCookieValue(request, "wurl");
				
			if (StringUtils.isEmpty(wurl)) {
				wurl = request.getContextPath() + "/cx/index.shtml";// 默认首页地址;
			}
				
			result.put("result", true);
			result.put("wurl", wurl);	
		} 
		catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorMsg", e.getExtMessage());
			result.put("retCode", GlobalConstants.RET_CODE_OTHER_MSG);
			result.put("wurl", request.getContextPath() + "/loginReg/toLogin.shtml");// 登录失败后
			logger.error("SubmitFederateLogin error：{errorCode：" + e.getErrCode()
					+ ",errorMsg：" + e.getExtMessage() + "}"); // 跳转到登录页面
		}
		catch (BusinessException e) {
			result.put("result", false);
			result.put("retCode", e.getErrCode());
			result.put("errorMsg", e.getMessage());
			result.put("wurl", request.getContextPath() + "/loginReg/toLogin.shtml");
			logger.error("SubmitFederateLogin error：{errorCode：" + e.getErrCode()
					+ ",errorMsg：" + e.getMessage() + "}"); 
		}
		catch (Exception e) {
			result.put("result", false);
			result.put("errorMsg", "系统服务异常，请与管理员或客服联系");
			logger.error("requestSubmitLogin() ==> " + e.getMessage());
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}

	/**
	 * 检查是否登录
	 * 
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "/checkLogin", produces = "text/html;charset=UTF-8")
	public String checkLoginStatus(String jsoncallback, HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		try {
			Map<String, Object> params = new LinkedHashMap<String, Object>();
			String tokenId = SessionUtil.getTokenId(request);
			params.put("tokenId", tokenId);
			params.putAll(getDeviceParams(request));
			// 添加IP信息，登录，获取用户状态等接口需要。
			params.put(GlobalConstants.KEY_REMOTE_IP, HttpUtil.getRemoteIpAddr(request));
			
			// 检查登陆
			if (!StringUtils.isEmpty(tokenId) && loginService.checkOnlineStatus(params)) {
				model.put("result", true);
				model.put("errorCode", ErrorCode.UserIsOnline.getErrorCode());
				model.put("errorMsg", ErrorCode.UserIsOnline.getErrorMsg());
			} else {
				model.put("result", false);
				model.put("errorCode", ErrorCode.SessionTimeOut.getErrorCode());
				model.put("errorMsg", ErrorCode.SessionTimeOut.getErrorMsg());
			}
		} catch (Exception e) {
			model.put("result", true);
			model.put("retCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("检查登陆状态发生异常：", e);
		}

		return JsonUtils.bean2jsonP(jsoncallback, model);
	}

	/**
	 * 获取当前用户
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserInfoRmote", produces = "text/html;charset=UTF-8")
	public String getUserInfo(String jsoncallback, HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		try {
			String tokenId = SessionUtil.getTokenId(request);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("tokenId", tokenId);
			// 添加IP信息，登录，获取用户状态等接口需要。
			String remoteIp = HttpUtil.getRemoteIpAddr(request);
			params.put(GlobalConstants.KEY_REMOTE_IP, remoteIp);
			params.putAll(getDeviceParams(request));
			
			if (!StringUtils.isEmpty(tokenId) && loginService.checkOnlineStatus(params)) {
				model.put("result", true);
				model.put("errorCode", ErrorCode.Success.getErrorCode());
				model.put("errorMsg", ErrorCode.Success.getErrorMsg());
				model.put("userName", SessionUtil.getUserName(request));
			} else {
				model.put("result", false);
				model.put("errorCode", ErrorCode.SessionTimeOut.getErrorCode());
				model.put("errorMsg", ErrorCode.SessionTimeOut.getErrorMsg());
			}
		} 
		catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取用户信息发生异常：", e);
		}

		logger.info("获取用户信息接口返回结果：" + JSON.toJSONString(model));
		return JsonUtils.bean2jsonP(jsoncallback, model);
	}

	/**
	 * 设置需要写入cookie中的url
	 * 
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "/setRedirectUrl", produces = "text/html;charset=UTF-8")
	public String setRedirectUrl(String jsoncallback, HttpServletRequest request, 
			HttpServletResponse response) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		
		try {
			String urlKey = request.getParameter("uKey");
			String urlValue = request.getParameter("uValue");
			urlKey = StringUtils.isEmpty(urlKey) ? "wurl" : urlKey;
			urlValue = StringUtils.isEmpty(urlValue) 
					? ("http://m.xiu.com") : urlValue;
			// 将对应的url写入cookie
			CookieUtil.getInstance().addCookie(response, urlKey, urlValue);
			result.put("result", true);
			result.put("retCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
		} catch (Exception e) {
			result.put("result", true);
			result.put("retCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.info("设置转发URL发生异常：" + JSON.toJSONString(result));
		}

		return JsonUtils.bean2jsonP(jsoncallback, result);
	}

	/**
	 * 获取cookie中uKey对应的url
	 * 
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "/getRedirectUrl", produces = "text/html;charset=UTF-8")
	public String getRedirectUrl(String jsoncallback, HttpServletRequest request, 
			HttpServletResponse response) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		
		try {
			String urlKey = request.getParameter("uKey");
			urlKey = StringUtils.isEmpty(urlKey) ? "wurl" : urlKey;
			CookieUtil cookieUtil = CookieUtil.getInstance();
			String urlValue = cookieUtil.readCookieValue(request, urlKey);
			urlValue = StringUtils.isEmpty(urlValue) ? ("http://m.xiu.com") : urlValue;
			result.put("result", true);
			result.put("retCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			result.put(urlKey, urlValue);
			// 使用完毕删除该cookie
			cookieUtil.deleteCookie(response, urlKey);
		} catch (Exception e) {
			result.put("result", true);
			result.put("retCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.info("设置转发URL发生异常：" + e.getMessage());
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}

	/**
	 * 退出登录
	 * @param request
	 * @param response
	 * @param wurl
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/logoutRemote", produces = "text/html;charset=UTF-8")
	public String logOut(String jsoncallback, 
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 从cookie中获取TOKENID
		String tokenId = SessionUtil.getTokenId(request);
		
		try {
			if (!StringUtils.isEmpty(tokenId)) {// 如果页面因长时间停留已经回话超时，则返回 SUCCESS
				Map<String, Object> pmap = new HashMap<String, Object>();
				pmap.put("tokenId", tokenId);
				pmap.putAll(getDeviceParams(request));// 设备信息
				
				loginService.logOut(pmap);
				// 退出登录成功
				SessionUtil.deleteLoginCookie(response);
				model.put("result", true);
				model.put("errorCode", ErrorCode.Success.getErrorCode());
				model.put("errorMsg", ErrorCode.Success.getErrorMsg());				
			} else {// tokenId为null,长时间视为已退出登录
				model.put("result", true);
				model.put("errorCode", ErrorCode.Success.getErrorCode());
				model.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}
		} 
		catch (EIBusinessException e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.LogoutAccountFailed.getErrorCode());
			model.put("errorMsg", ErrorCode.LogoutAccountFailed.getErrorMsg());
			logger.error("logOut() ==> ", e);
		}
		catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("logOut() ==> ", e);
		}
		logger.info("退出登录接口返回结果：" + JSON.toJSONString(model));
		return JsonUtils.bean2jsonP(jsoncallback, model);
	}
	
	

}
