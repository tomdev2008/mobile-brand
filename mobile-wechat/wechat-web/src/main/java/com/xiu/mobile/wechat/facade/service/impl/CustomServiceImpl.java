package com.xiu.mobile.wechat.facade.service.impl;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xiu.mobile.wechat.core.constants.Constants;
import com.xiu.mobile.wechat.core.utils.HttpUtil;
import com.xiu.mobile.wechat.core.utils.WeChatApiUtil;
import com.xiu.mobile.wechat.facade.constants.FacadeContants;
import com.xiu.mobile.wechat.facade.model.MessageInfo;
import com.xiu.mobile.wechat.facade.model.NotifyResult;
import com.xiu.mobile.wechat.facade.model.TextInfo;
import com.xiu.mobile.wechat.facade.service.ICustomService;
import com.xiu.mobile.wechat.facade.utils.JSONUtil;
import com.xiu.mobile.wechat.web.service.IFeedbackCfgService;

@Service("customService")
public class CustomServiceImpl implements ICustomService {
	private static final Logger logger = LoggerFactory.getLogger(CustomServiceImpl.class);

	@Resource
	private IFeedbackCfgService feedbackCfgService;

	@Override
	public NotifyResult sendMessage(String openId, String message) {
		String result = "";
		try {
			String access = WeChatApiUtil.getAccessToken(Constants.WEB_APPID, Constants.WEB_APPSECRET,Constants.TYPE_WEB);
			String url = FacadeContants.CUSTOMER_SERVICE_MSG_URL.replace("ACCESS_TOKEN", access);

			Map<String, Object> params = new LinkedHashMap<String, Object>();
			params.put("touser", openId);
			params.put("msgtype", "text");
			params.put("text", new TextInfo(message));
			JSONObject paramsJson = new JSONObject();
			paramsJson.putAll(params);
			// 发送模板消息， 请求返回结果 {"errcode":0,"errmsg":"ok","msgid":200241615}
			result = HttpUtil.requestPost(url, paramsJson.toString(), "utf-8");
			logger.info("调用客服消息接口返回结果：" + result);
		} catch (IOException e) {
			logger.error("调用客服消息接口时发生异常：" + e.getMessage());
		}
		return JSONUtil.convertToNotifyResult(result);
	}

	@Override
	public NotifyResult sendMessage(String openId, MessageInfo info) {
		String result = "";
		try {
			String access = WeChatApiUtil.getAccessToken(Constants.APP_APPID, Constants.APP_APPSECRET,Constants.TYPE_WEB);
			String url = FacadeContants.CUSTOMER_SERVICE_MSG_URL.replace("ACCESS_TOKEN", access);

			Map<String, Object> params = new LinkedHashMap<String, Object>();
			params.put("touser", openId);
			params.put("msgtype", info.getMsgType());
			params.put(info.getMsgType(), getMsg(info));

			JSONObject paramsJson = new JSONObject();
			paramsJson.putAll(params);
			// 发送模板消息， 请求返回结果 {"errcode":0,"errmsg":"ok","msgid":200241615}
			result = HttpUtil.requestPost(url, paramsJson.toString(), "utf-8");

			if (logger.isDebugEnabled()) {
				logger.debug("调用客服消息接口:------------");
				logger.debug("openId:{}", openId);
				logger.debug("message:{}", info.toString());
				logger.debug("result:{}", result);
			}
		} catch (IOException e) {
			logger.error("调用客服消息接口时发生异常：" + e.getMessage());
		}
		return JSONUtil.convertToNotifyResult(result);
	}

	private Object getMsg(MessageInfo info) {
		// 发送文本信息
		if ("text".equals(info.getMsgType())) {
			return info.getText();
		}
		// 发送图片信息
		if ("image".equals(info.getMsgType())) {
			return info.getImage();
		}
		// 发送语音信息
		if ("voice".equals(info.getMsgType())) {
			return info.getVoice();
		}
		// 发送视频信息
		if ("video".equals(info.getMsgType())) {
			return info.getVideo();
		}
		// 发送音乐信息
		if ("music".equals(info.getMsgType())) {
			return info.getMusic();
		}
		// 发送图文信息
		if ("news".equals(info.getMsgType())) {
			return info.getNews();
		}
		return null;
	}

}
