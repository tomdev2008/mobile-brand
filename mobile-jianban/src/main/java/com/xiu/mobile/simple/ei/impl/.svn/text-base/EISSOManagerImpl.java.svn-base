/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-15 下午3:22:04 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.mobile.simple.ei.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.simple.constants.ErrConstants;
import com.xiu.mobile.simple.ei.EISSOManager;
import com.xiu.sso.facade.result.SsoResult;
import com.xiu.sso.server.SsoServer;
import com.xiu.sso.server.dto.UserDO;

/** 
 * <p>
 * ************************************************************** 
 * @Description: 对接SSO实现类 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-15 下午3:22:04 
 * ***************************************************************
 * </p>
 */
@Service
public class EISSOManagerImpl implements EISSOManager {
	private static final Logger LOGGER = Logger.getLogger(EISSOManagerImpl.class);
	
	@Autowired
	private SsoServer ssoServer;
	
	/**
	 * Key：SSO返回错误码  | Value：中文描述
	 */
	private static Map<Object, Object> ssoResultMap = new HashMap<Object, Object>();
	
	static {
		try{
			for (SsoResult.SsoCodeEnum ssoCodeEnum : SsoResult.SsoCodeEnum.values()) {
				ssoResultMap.put(ssoCodeEnum.getCode(), ssoCodeEnum.getDescribe());
			}
		}catch(Exception e){
			LOGGER.error("转换SSO登录检查信息发生错误", e);
		}
	}
	
	@Override
	public UserDO logon(String logonName, String password, 
			String channelId, String remoteIpAddr, String logoutTime,String loginChannel) {
		Assert.notNull(logonName);
		Assert.notNull(password);
		Assert.notNull(channelId);
		Assert.notNull(remoteIpAddr);
		Assert.notNull(loginChannel);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("invoke remote interface [sso] ssoServer.login()");
			LOGGER.debug("logonName: " + logonName);
			LOGGER.debug("password: " + password);
			LOGGER.debug("remoteIpAddr: " + remoteIpAddr);
			LOGGER.debug("channelId: " + channelId);
			LOGGER.debug("logoutTime: " + logoutTime);
			LOGGER.debug("loginChannel: " +loginChannel);
		}

		UserDO user = null;
		try {
			user = ssoServer.login(logonName, password, channelId, 
					remoteIpAddr, logoutTime,loginChannel);
		} catch (Exception e) {
			throw ExceptionFactory.buildEIRuntimeException(
					ErrConstants.EIErrorCode.EI_SSO_GENERAL_ERR, e);
		}

		if (!user.getIsSuccess()) {
			LOGGER.error("invoke remote interface [sso] ssoServer.login() error.");
			LOGGER.error("ErrorCode:" + user.getErrorCode());
			LOGGER.error("ErrorMessage:" + getSSOErrInfo(user.getErrorCode()));
			
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_SSO_BIZ_LOGIN_ERR, 
					user.getErrorCode(), getSSOErrInfo(user.getErrorCode()));
		}
		
		return user;
	}

	@Override
	public UserDO logout(String tokenId, String channelId) {
		Assert.notNull(tokenId, "tokenId should be not null.");
		Assert.notNull(channelId, "channelId should be not null.");
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("invoke remote interface [sso] ssoServer.logout");
			LOGGER.debug("tokenId: " + tokenId);
			LOGGER.debug("channelId: " + channelId);
		}
		
		UserDO user = null;
		try {
			user = ssoServer.logout(tokenId, channelId);
		} catch (Exception e) {
			throw ExceptionFactory.buildEIRuntimeException(
					ErrConstants.EIErrorCode.EI_SSO_GENERAL_ERR, e);
		}
		
		if(!user.getIsSuccess()) {
			LOGGER.error("invoke remote interface [sso] ssoServer.logout() error.");
			LOGGER.error("ErrorCode:" + user.getErrorCode());
			LOGGER.error("ErrorMessage:" + getSSOErrInfo(user.getErrorCode()));
			
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_SSO_BIZ_LOGOUT_ERR, 
					user.getErrorCode(), getSSOErrInfo(user.getErrorCode()));
		} 
		
		return user;
	}
	
	@Override
	public boolean checkOnlineStatus(String tokenId, String channelId, String remoteIpAddr) {
		Assert.notNull(tokenId);
		Assert.notNull(remoteIpAddr);
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("invoke remote interface [sso] ssoServer.checkOnlineStatus");
			LOGGER.debug("tokenId: " + tokenId);
			LOGGER.debug("channelId: " + channelId);
			LOGGER.debug("remoteIpAddr: " + remoteIpAddr);
		}

		UserDO user = null;
		try {
			user = ssoServer.checkOnlineState(tokenId, channelId, remoteIpAddr);
		} catch (Exception e) {
			throw ExceptionFactory.buildEIRuntimeException(
					ErrConstants.EIErrorCode.EI_SSO_GENERAL_ERR, e);
		}
			
		return user.getIsSuccess();
	}
	
	@Override
	public UserDO proxyLogin(String userId, String channelId, String clientIp, String logoutTime) {
		Assert.notNull(userId);
		Assert.notNull(channelId);
		Assert.notNull(clientIp);
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("invoke remote interface [sso] ssoServer.proxyLogin");
			LOGGER.debug("userId: " + userId);
			LOGGER.debug("channelId: " + channelId);
			LOGGER.debug("clientIp: " + clientIp);
		}
		
		UserDO user = null;
		try {
			user = ssoServer.proxyLogin(Integer.valueOf(userId), channelId, 
					clientIp, logoutTime);
		} catch (Exception e) {
			throw ExceptionFactory.buildEIRuntimeException(
					ErrConstants.EIErrorCode.EI_SSO_GENERAL_ERR, e);
		}
		
		if(!user.getIsSuccess()) {
			LOGGER.error("invoke remote interface [sso] ssoServer.proxyLogin() error.");
			LOGGER.error("ErrorCode:" + user.getErrorCode());
			LOGGER.error("ErrorMessage:" + getSSOErrInfo(user.getErrorCode()));
			
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_SSO_BIZ_PROXYLOGIN_ERR, 
					user.getErrorCode(), getSSOErrInfo(user.getErrorCode()));
		}
		
		return user;
	}
	
	/**
	 * 返回登录结果的具体中文含义
	 * @param errCode
	 * @return
	 */
	private static String getSSOErrInfo(String errCode) {
		return String.valueOf(ssoResultMap.get(errCode));
	}

}
