package com.xiu.mobile.wechat.facade.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xiu.mobile.wechat.core.utils.WeChatApiUtil;
import com.xiu.mobile.wechat.facade.service.IWechatCommonService;

@Service("wechatCommonService")
public class WechatCommonServiceImpl implements IWechatCommonService {
	private static final Logger logger = LoggerFactory.getLogger(WechatCommonServiceImpl.class);

	@Override
	public String getAccessToken(String appId, String appSecret, String type) {
		logger.info("获取access_token。 appId：" + appId + ",appSecret:" + appSecret + ",type:" + type);
		try {
			return WeChatApiUtil.getAccessToken(appId, appSecret, type);
		} catch (Exception e) {
			logger.error("调用获取access_token接口时发生异常.", e);
		}
		return null;
	}

	@Override
	public String getAccessToken(String type) {
		logger.info("获取access_token。type:" + type);
		try {
			return WeChatApiUtil.getAccessToken(type);
		} catch (Exception e) {
			logger.error("调用获取access_token接口时发生异常.", e);
		}
		return null;
	}

}
