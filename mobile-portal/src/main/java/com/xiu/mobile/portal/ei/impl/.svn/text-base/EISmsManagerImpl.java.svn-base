/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-22 下午2:41:59 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.mobile.portal.ei.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.portal.constants.ErrConstants;
import com.xiu.mobile.portal.ei.EISmsManager;
import com.xiu.sms.bean.Message;
import com.xiu.sms.hessian.ISMSHessianService;
import com.xiu.sms.hessian.SendingResult;

/** 
 * <p>
 * ************************************************************** 
 * @Description: 短信服务
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-22 下午2:41:59 
 * ***************************************************************
 * </p>
 */
@Service
public class EISmsManagerImpl implements EISmsManager {
	private static final Logger LOGGER = Logger.getLogger(EISmsManagerImpl.class);
	
	@Autowired
	private ISMSHessianService iSMSHessianService;

	@Override
	public SendingResult sendSMS(Message message) {
		Assert.notNull(message);
		Assert.notNull(message.getTelephone());
		Assert.notNull(message.getBody());
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("invoke remote interface [sms] iSMSHessianService.sendSimpleMessage");
			LOGGER.debug("message.getTelephone():" + message.getTelephone());
			LOGGER.debug("message.getBody():" + message.getBody());
		}
		
		SendingResult result = null;
		
		try {
			result = iSMSHessianService.sendSimpleMessage(message);
		} catch (Exception e) {
			throw ExceptionFactory.buildEIRuntimeException(
					ErrConstants.EIErrorCode.EI_SMS_GENERAL_ERR, e);
		}
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("invoke remote interface [sms] iSMSHessianService.sendSimpleMessage");
			LOGGER.debug("result.isSuccess():" + result.isSuccess());
			LOGGER.debug("result.getComment():" + result.getComment());
		}
		
		if(!result.isSuccess()) {
			LOGGER.error("invoke remote interface [sms] iSMSHessianService.sendSimpleMessage error.");
			LOGGER.error("result.isSuccess():" + result.isSuccess());
			LOGGER.error("result.getComment():" + result.getComment());
			
			throw ExceptionFactory.buildEIBusinessException(
					ErrConstants.EIErrorCode.EI_SMS_BIZ_SSM_ERR, null, result.getComment());
		}
		
		return result;
	}
	
}
