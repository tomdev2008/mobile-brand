package com.xiu.mobile.wechat.web.controller;

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

@Controller
@RequestMapping("/pcunion")
public class PCUnionLoginController {
	private static final Logger logger = LoggerFactory.getLogger(PCUnionLoginController.class);

	/**
	 * 获取微信用户信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getUserInfo")
	public ModelAndView getUserInfo(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 获取微信授权之后返回的授权CODE
			String code = request.getParameter("code");
			String targetUrl = request.getParameter("targetUrl");
			if (logger.isInfoEnabled()) {
				logger.info("PC联合登陆获取参数----BEGIN-------");
				logger.info("code：{}", code);
				logger.info("targetUrl：{}", targetUrl);
				logger.info("PC联合登陆获取参数-----END-------");
			}

			// 如果用户取消授权 获取 没有授权,则跳转至目标页面
			if (Constants.CANCEL_CODE.equals(code) || StringUtils.isBlank(code)) {
				response.sendRedirect(targetUrl);
			}

			// 调用微信接口，获取用户基本信息
			UserInfo userInfo = WeChatApiUtil.getWeiXinUserInfo(Constants.WEB_APPID, Constants.WEB_APPSECRET, code);

			// 如果用户没用关注服务号，则设置默认昵称
			if (Constants.NOT_SUBSCRIBE.equals(userInfo.getSubscribe())) {
				String nickName = StringUtils.isEmpty(userInfo.getNickName()) ? userInfo.getUnionId() + "@wechat" : userInfo.getNickName();
				userInfo.setNickName(nickName);
			}
			// 返回参数
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("targetUrl", targetUrl);
			model.put("nickName", userInfo.getNickName());
			model.put("openId", userInfo.getOpenId());
			model.put("unionId", userInfo.getUnionId());
			model.put("headImgUrl", userInfo.getHeadImgUrl());
			// 跳转到登录页面
			return new ModelAndView("union/pcunion_login", model);
		} catch (Exception e) {
			logger.error("PC联合登陆获取用户信息时发生异常.", e);
			return new ModelAndView("error/error");
		}
	}

}
