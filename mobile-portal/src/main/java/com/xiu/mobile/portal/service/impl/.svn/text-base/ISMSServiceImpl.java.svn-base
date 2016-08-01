package com.xiu.mobile.portal.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.portal.ei.EISmsManager;
import com.xiu.mobile.portal.service.ISMSService;
import com.xiu.msc.facade.voiceverify.dto.VoiceVerifyHessianMessage;
import com.xiu.msc.facade.voiceverify.dto.VoiceVerifyHessianResult;
import com.xiu.msc.facade.voiceverify.service.IVoiceVerifyHessianService;
import com.xiu.sms.bean.Message;

/**
 * <p>
 * ************************************************************** 
 * @Description: 短信服务业务逻辑实现类
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-14 下午4:07:21 
 * ***************************************************************
 * </p>
 */
@Service("iSMSService")
public class ISMSServiceImpl implements ISMSService {
	
	private static final Logger logger = LoggerFactory.getLogger(ISMSServiceImpl.class);
	
	@Autowired
	private EISmsManager eiSmsManager;
	@Autowired
	private IVoiceVerifyHessianService voiceVerifyService;

	@Override
	public boolean sendSMS(String smsBody, String phoneNum, String subject,
			String creator) {
		Message me = new Message();
		me.setBody(smsBody);
		me.setTelephone(phoneNum);
		me.setSubject(subject);
		me.setCreator(creator);
		return sendSMS(me);
	}

	@Override
	public boolean sendSMS(Message message) {
		return eiSmsManager.sendSMS(message).isSuccess();
	}

	/**
	 * 发送语音验证码
	 */
	public boolean sendVoiceVerifyCode(String mobile, String verifyCode) {
		boolean result = false;
		try {
			VoiceVerifyHessianMessage voiceVerifyHessianMessage = new VoiceVerifyHessianMessage();
			voiceVerifyHessianMessage.setPszMobis(mobile);
			voiceVerifyHessianMessage.setPszMsg(verifyCode);
//			voiceVerifyHessianMessage.setPszSubPort("");
//			voiceVerifyHessianMessage.setPtTmpiid("100092");
//			voiceVerifyHessianMessage.setiMobiCount("1");
//			voiceVerifyHessianMessage.setMsgId("");
//			voiceVerifyHessianMessage.setMsgType("1");
			VoiceVerifyHessianResult voiceResult = voiceVerifyService.sendVoiceVerifyCode(voiceVerifyHessianMessage);
			if(voiceResult != null && voiceResult.isSuccess()) {
				result = true;
			}
		} catch (Exception e) {
			logger.error("调用语音短信验证码接口异常，手机号：" + mobile + "，验证码：" + verifyCode, e);
		}
		
		return result;
	}
	
}
