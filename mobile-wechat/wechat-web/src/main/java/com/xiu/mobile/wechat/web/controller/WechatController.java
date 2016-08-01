package com.xiu.mobile.wechat.web.controller;

import java.util.Arrays;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiu.mobile.wechat.web.service.IWechatService;

@Controller
@RequestMapping("/wechat")
public class WechatController {
	private static final Logger logger = LoggerFactory.getLogger(WechatController.class);

	/**
	 * 微信验证token
	 */
	private final String TOKEN = "xiu_wechat";

	@Resource
	private IWechatService wechatService;

	/**
	 * 响应微信发送的Token验证
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(method = RequestMethod.GET)
	public void verifyWechatToken(HttpServletRequest request, HttpServletResponse response) {
		try {
			String signature = request.getParameter("signature");
			String timestamp = request.getParameter("timestamp");
			String nonce = request.getParameter("nonce");
			String echostr = request.getParameter("echostr");
			logger.info("signature:" + signature + " timestamp:" + timestamp + " nonce：" + nonce + " echostr:" + echostr);

			String[] params = { TOKEN, timestamp, nonce };
			Arrays.sort(params);
			StringBuilder sb = new StringBuilder();
			for (String param : params) {
				sb.append(param);
			}
			String dest = DigestUtils.shaHex(sb.toString());
			if (dest.equals(signature)) {
				if (!"".equals(echostr) && echostr != null)
					response.getWriter().write(echostr);
			}
		} catch (Exception e) {
			logger.error("验证微信Token发生异常：" + e.getMessage());
		}
	}

	/**
	 * 处理微信发送过来的请求并响应
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void processReqAndResp(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 处理接收信息 并响应
			String returnMsg = wechatService.processReq(request.getInputStream());
			logger.info("输出消息:[" + returnMsg + "]");
			response.getWriter().write(returnMsg);
		} catch (Exception e) {
			logger.error("处理微信消息发生异常：" + e.getMessage());
		}
	}

}
