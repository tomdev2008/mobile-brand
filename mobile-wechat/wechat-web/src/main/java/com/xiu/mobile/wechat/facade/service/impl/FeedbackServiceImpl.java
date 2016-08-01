package com.xiu.mobile.wechat.facade.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.xiu.mobile.wechat.core.constants.Constants;
import com.xiu.mobile.wechat.core.utils.HttpUtil;
import com.xiu.mobile.wechat.core.utils.WeChatApiUtil;
import com.xiu.mobile.wechat.facade.constants.FacadeContants;
import com.xiu.mobile.wechat.facade.model.NotifyResult;
import com.xiu.mobile.wechat.facade.service.IFeedbackService;
import com.xiu.mobile.wechat.facade.utils.JSONUtil;
import com.xiu.mobile.wechat.web.model.FeedbackCfgVo;
import com.xiu.mobile.wechat.web.service.IFeedbackCfgService;

/**
 * 维权服务接口实现
 *
 * @author wangzhenjiang
 *
 * @since 2014年6月17日
 */

@Service("feedbackService")
public class FeedbackServiceImpl implements IFeedbackService {
	private static final Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);

	@Resource(name = "feedbackCfgService")
	private IFeedbackCfgService feedbackCfgService;

	@Override
	public NotifyResult updateFeedbackStatus(String openId, String feedbackId) {
		NotifyResult result = new NotifyResult();
		try {
			String accessToken = WeChatApiUtil.getAccessToken(Constants.WEB_APPID, Constants.WEB_APPSECRET,Constants.TYPE_WEB);
			String url = String.format(FacadeContants.UPDATE_FEEDBACK_URL, accessToken, openId, feedbackId);
			String jsonResult = HttpUtil.requestGet(url, "utf-8");
			result = JSONUtil.convertToNotifyResult(jsonResult);
			// 通知成功后，更新微信维权记录表，将state 更改为 ：“Y”
			if (result.isSuccess()) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("openId", openId);
				params.put("feedbackId", feedbackId);
				params.put("state", "N");
				List<FeedbackCfgVo> lstVos = feedbackCfgService.getFeedbackCfgList(params);
				if (!CollectionUtils.isEmpty(lstVos)) {
					for (FeedbackCfgVo feedbackCfgVo : lstVos) {
						if (null == feedbackCfgVo) {
							lstVos.remove(feedbackCfgVo);
							continue;
						}
						feedbackCfgVo.setState("Y");
					}
					feedbackCfgService.updateFeedbackCfgList(lstVos);
				}
			}

			if (logger.isDebugEnabled()) {
				logger.debug("更新维权状态:------------");
				logger.debug("openId:{}", openId);
				logger.debug("feedbackId:{}", feedbackId);
				logger.debug("result:{}", result);
			}
		} catch (IOException e) {
			logger.error("更新维权状态发生异常", e);
		}
		return result;
	}
}
