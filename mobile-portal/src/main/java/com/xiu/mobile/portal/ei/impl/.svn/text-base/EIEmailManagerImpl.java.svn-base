package com.xiu.mobile.portal.ei.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.xiu.email.bean.EmailBean;
import com.xiu.email.hessian.IEmailHessianService;
import com.xiu.email.hessian.SendingResult;
import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.portal.constants.ErrConstants;
import com.xiu.mobile.portal.ei.EIEmailManager;

/** 
 * <p>
 * ************************************************************** 
 * @Description: 发送邮件服务接口
 * @AUTHOR hejianxiong
 * @DATE 2014-08-20 下午5:39:55 
 * ***************************************************************
 * </p>
 */
@Service("eiEmailManager")
public class EIEmailManagerImpl implements EIEmailManager{
	private static final Logger logger = Logger.getLogger(EISmsManagerImpl.class);
	
	@Autowired
	private IEmailHessianService iEmailHessianService;

	@Override
	public SendingResult sendEmail(EmailBean emailBean) {
		Assert.notNull(emailBean);
		Assert.notNull(emailBean.getReceiverMail());
		Assert.notNull(emailBean.getBody());
		
		logger.info("邮件发送参数：creator="+emailBean.getCreator()+",emailAddr="+emailBean.getReceiverMail()
				+",sendName="+emailBean.getSenderName()+",subject="+emailBean.getSubject()+",body="+emailBean.getBody());
		
		SendingResult result = null;
		
		try {
			result = iEmailHessianService.sendEmail(emailBean);
		} catch (Exception e) {
			throw ExceptionFactory.buildEIRuntimeException(
					ErrConstants.EIErrorCode.EI_EMAIL_GENERAL_ERR, e);
		}
		
		logger.info("invoke remote interface [email] iEmailHessianService.sendEmail");
		logger.info("result.isSuccess():" + result.isSuccess());
		logger.info("result.getComment():" + result.getComment());
		
		if(!result.isSuccess()) {
			logger.error("invoke remote interface [email] iEmailHessianService.sendEmail error.");
			logger.error("result.isSuccess():" + result.isSuccess());
			logger.error("result.getComment():" + result.getComment());
			
			throw ExceptionFactory.buildEIBusinessException(
					ErrConstants.EIErrorCode.EI_EMAIL_BIZ_ERR, null, result.getComment());
		}
		
		return result;
	}

}
