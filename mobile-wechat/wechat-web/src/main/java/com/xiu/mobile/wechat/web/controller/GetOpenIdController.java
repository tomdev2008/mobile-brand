package com.xiu.mobile.wechat.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xiu.mobile.wechat.core.constants.Constants;
import com.xiu.mobile.wechat.core.utils.WeChatApiUtil;
import com.xiu.mobile.wechat.web.utils.WebUtils;

/**
 * 获取openid
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/auth")
public class GetOpenIdController {
	private static final Logger logger = LoggerFactory.getLogger(GetOpenIdController.class);
	
	/**
	 * 微信静默授权，获取CODE，并设置微信回调url
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/setOpenId")
	public ModelAndView setWxCallbackUrl(HttpServletRequest request, HttpServletResponse response){
		String targetUrl = request.getParameter("targetUrl");
		String redirectUrl = String.format(Constants.GET_OPENID, WebUtils.getURLEncoderVal(targetUrl, "utf-8"));
		String oauthUrl = String.format(Constants.AUTH_BASE_URL, Constants.WEB_APPID, WebUtils.getURLEncoderVal(redirectUrl, "utf-8"));
		return new ModelAndView("redirect:" + oauthUrl);
	}

	/**
	 * 获取用户openid
	 * 
	 * 微信静默授权，根据微信返回的CODE，获取openid
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getOpenId")
	public ModelAndView getOpenid(HttpServletRequest request, HttpServletResponse response) {
		String code = request.getParameter("code");
		String appId = Constants.WEB_APPID;
		String appSecret = Constants.WEB_APPSECRET;
		
		String openId = null;
		try {
			JSONObject jsonObj = WeChatApiUtil.getAccessInfoByCode(appId, appSecret, code);
			openId = jsonObj.getString("openid");
			logger.info("静默授权，获取用户openid: " + openId);
		} catch (IOException e) {
			logger.error("静默授权，根据code获取openid异常", e);
		}
		
		String targetUrl = request.getParameter("targetUrl");
		if(StringUtils.isNotBlank(targetUrl)){
			if(targetUrl.indexOf("?") > -1){
				targetUrl += "&openId=" + openId;
			}else{
				targetUrl += "?openId=" + openId;
			}
		}
		
		return new ModelAndView("redirect:" + targetUrl);
	}
	
}
