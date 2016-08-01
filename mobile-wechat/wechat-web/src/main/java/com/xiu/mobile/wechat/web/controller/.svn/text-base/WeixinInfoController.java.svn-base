package com.xiu.mobile.wechat.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xiu.mobile.wechat.core.constants.Constants;
import com.xiu.mobile.wechat.core.model.UserInfo;
import com.xiu.mobile.wechat.core.utils.WeChatApiUtil;
import com.xiu.mobile.wechat.web.utils.WebUtils;

@Controller
@RequestMapping("/weixininfo")
public class WeixinInfoController {
	private static final Logger logger = LoggerFactory.getLogger(WeixinInfoController.class);

	
	@RequestMapping("toLogin")
	public ModelAndView toLogin(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
	
		String targetUrl = request.getParameter("targetUrl");
		if(StringUtils.isEmpty(targetUrl))
			targetUrl="http://m.xiu.com";
		String redirectUrl = String.format(Constants.USER_INFO, WebUtils.getURLEncoderVal(targetUrl, "utf-8"));
		String oauthUrl = String.format(Constants.AUTH_BASE_URL, Constants.WEB_APPID, WebUtils.getURLEncoderVal(redirectUrl, "utf-8"));
		logger.info("winxininfo login auth request redirect to weixin Oauth:"+oauthUrl);
		return new ModelAndView("redirect:" + oauthUrl);
	}
	
	
	
	/**
	 * 获取微信用户信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/userInfo")
	public ModelAndView getUserInfo(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 获取微信授权之后返回的授权CODE
			String code = request.getParameter("code");
			logger.info("获取微信信息授权回调 code：{}", code);
			String targetUrl = request.getParameter("targetUrl");
			// 如果用户取消授权 获取 没有授权,则跳转至目标页面
			if (Constants.CANCEL_CODE.equals(code) || StringUtils.isBlank(code)) {
				response.sendRedirect(targetUrl);
			}
			// 调用微信接口，获取用户基本信息
			UserInfo userInfo = WeChatApiUtil.getWeiXinUserInfo(Constants.WEB_APPID, Constants.WEB_APPSECRET, code);
			// 如果用户没有昵称，则设置默认昵称
			if (userInfo.getNickName() == null) {
				String nickName = StringUtils.isEmpty(userInfo.getNickName()) ? userInfo.getUnionId() + "@wechat" : userInfo.getNickName();
				userInfo.setNickName(nickName);
			}
			String openId = userInfo.getOpenId();
			String unionId = userInfo.getUnionId();
			logger.info("获取微信信息微信 targetUrl：{}", targetUrl);
			logger.info("获取微信信息微信 nickName：{}", userInfo.getNickName());
			logger.info("获取微信信息微信unionId：{}",  userInfo.getUnionId());
			logger.info("获取微信信息微信 headImgUrl：{}", userInfo.getHeadImgUrl());
			// 返回参数
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("nickName", userInfo.getNickName());
			model.put("openId", openId);
			model.put("unionId", unionId);
			model.put("headImgUrl", userInfo.getHeadImgUrl());
			// 跳转到登录页面
			return new ModelAndView("redirect:"+targetUrl, model);
		} catch (Exception e) {
			logger.error("获取微信信息微信登陆获取用户信息时发生异常.", e);
			return new ModelAndView("error/error");
		}
	}

}
