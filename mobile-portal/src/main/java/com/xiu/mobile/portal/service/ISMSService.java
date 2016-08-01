package com.xiu.mobile.portal.service;

import com.xiu.sms.bean.Message;

/**
 * <p>
 * ************************************************************** 
 * @Description: 短信服务业务逻辑接口
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-14 上午11:23:07 
 * ***************************************************************
 * </p>
 */
public interface ISMSService {
	
	boolean sendSMS(String smsBody, String phoneNum, String subject, String creator);

	boolean sendSMS(Message message);
	
	//发送语音验证码
	boolean sendVoiceVerifyCode(String mobile, String verifyCode);
}
