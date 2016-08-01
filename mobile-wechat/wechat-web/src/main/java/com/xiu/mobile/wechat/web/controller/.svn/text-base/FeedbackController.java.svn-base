package com.xiu.mobile.wechat.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xiu.mobile.wechat.core.constants.Constants;
import com.xiu.mobile.wechat.web.model.FeedbackCfgVo;
import com.xiu.mobile.wechat.web.service.IFeedbackCfgService;
import com.xiu.mobile.wechat.web.utils.MessageUtil;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {
	private static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);

	@Resource
	private IFeedbackCfgService feedbackCfgService;

	private byte[] lock = new byte[0];

	@RequestMapping(value = "/submit")
	public void submit(HttpServletRequest request) {
		try {
			Map<String, String> parMap = MessageUtil.parseXml(request.getInputStream());
			if (logger.isDebugEnabled()) {
				logger.debug("打印维权信息：----------BEGIN-------------");
				logger.debug(parMap.toString());
				logger.debug("打印维权信息：-----------END--------------");
			}
			synchronized (lock) {
				FeedbackCfgVo objFeedbackCfg = convertToFeedbackCfg(parMap);
				/*
				 * 由于微信服务器会将一次维权信息每隔几秒钟post过来一次，我们只需要保存接收到其中一条即可。过滤方法：根据feedbackId、transId、state查询数据库中是否存在记录
				 * 如果存在，则判断当前进来的信息时间戳是否满足一下公式
				 * ：b+30(分钟)>a>b-30(分钟)(当前时间戳a,数据库已存在记录时间戳B)如果满足则视为当前记录与数据库中的记录为同一次记录，忽略不进行保存与发送
				 */
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("feedbackId", objFeedbackCfg.getFeedbackId());
				params.put("transId", objFeedbackCfg.getTransId());
				params.put("state", "N");
				List<FeedbackCfgVo> lstFds = feedbackCfgService.getFeedbackCfgList(params);
				boolean isFirst = true;
				if (null != lstFds && !lstFds.isEmpty()) {
					Calendar cNew = Calendar.getInstance();
					cNew.setTime(objFeedbackCfg.getCreateDate());
					for (FeedbackCfgVo oldFd : lstFds) {
						Calendar cOld = Calendar.getInstance();
						cOld.setTime(oldFd.getCreateDate());

						Calendar cOldAddHour = Calendar.getInstance();
						cOldAddHour.setTime(oldFd.getCreateDate());
						cOldAddHour.add(Calendar.MINUTE, 30);

						Calendar cOldSubHour = Calendar.getInstance();
						cOldSubHour.setTime(oldFd.getCreateDate());
						cOldSubHour.add(Calendar.MINUTE, -30);

						if (cNew.before(cOldAddHour) && cNew.after(cOldSubHour)) {
							isFirst = false;
						}
					}
				}
				if (isFirst) {
					if (Constants.FEEDBACK_MSG_TYPE_REQUEST.equals(objFeedbackCfg.getMsgType())) {
						// 把微信维权信息发给客服系统，如果发送成功，则保存消息
						feedbackCfgService.sendToCustomSys(objFeedbackCfg);
					}
				}
			}
		} catch (Exception e) {
			logger.error("接收微信维权信息发生异常!", e);
		}
	}

	private FeedbackCfgVo convertToFeedbackCfg(Map<String, String> parMap) {
		FeedbackCfgVo objFeedbackCfg = new FeedbackCfgVo();
		objFeedbackCfg.setOpenId(parMap.get("OpenId"));
		objFeedbackCfg.setAppId(parMap.get("AppId"));
		objFeedbackCfg.setMsgType(parMap.get("MsgType"));
		objFeedbackCfg.setFeedbackId(parMap.get("FeedBackId"));
		objFeedbackCfg.setTransId(parMap.get("TransId"));
		objFeedbackCfg.setReason(parMap.get("Reason"));
		objFeedbackCfg.setSolution(parMap.get("Solution"));
		objFeedbackCfg.setExtinfo(parMap.get("ExtInfo"));
		objFeedbackCfg.setAppsignature(parMap.get("AppSignature"));
		String picUrl = parMap.get("PicUrl");
		if (StringUtils.isNotBlank(picUrl)) {
			objFeedbackCfg.setPicUrls(picUrl.split(","));
		}
		objFeedbackCfg.setState("N");
		String timeStamp = parMap.get("TimeStamp");
		if (!StringUtils.isEmpty(timeStamp)) {
			// 将PHP的时间戳(10位)转换为Java可以使用的时间戳
			timeStamp = timeStamp.concat("000");
			objFeedbackCfg.setCreateDate(new Date(Long.valueOf(timeStamp)));
		}
		objFeedbackCfg.setCreatorId("007");
		objFeedbackCfg.setCreatorName("admin");
		return objFeedbackCfg;
	}

	/**
	 * 获得维权信息列表
	 */
	@ResponseBody
	@RequestMapping(value = "/queryFeedBackList")
	public ModelAndView queryFeedBackList(String userName) {
		if ("zoshow".equals(userName)) {
			List<FeedbackCfgVo> feedbackList = new ArrayList<FeedbackCfgVo>();
			Map<String, Object> params = new HashMap<String, Object>();
			Map<String, List<FeedbackCfgVo>> model = new HashMap<String, List<FeedbackCfgVo>>();
			feedbackList = feedbackCfgService.getFeedbackCfgList(params);
			model.put("feedbackList", feedbackList);
			return new ModelAndView("feedback/feedback", model);
		}
		return null;
	}
}
