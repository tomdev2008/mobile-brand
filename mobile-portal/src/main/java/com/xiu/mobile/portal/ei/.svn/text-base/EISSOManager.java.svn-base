package com.xiu.mobile.portal.ei;

import com.xiu.sso.server.dto.UserDO;


/** 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 对接SSO接口
 * @AUTHOR : mike@xiu.com 
 * @DATE :2014-5-15 下午12:10:53
 * </p>
 **************************************************************** 
 */
public interface EISSOManager {

	/**
	 * 登录
	 * @param logonName
	 * @param password
	 * @param channelId
	 * @param remoteIpAddr
	 * @return
	 */
	UserDO logon(String logonName, String password, String channelId, String remoteIpAddr, String logoutTime,String loginChannel);
	
	/**
	 * 退出登录
	 * @param tokenId
	 * @param channelId
	 * @return
	 */
	UserDO logout(String tokenId, String channelId);
	
	/**
	 * 检查登录状态
	 * @param tokenId
	 * @param channelId
	 * @param remoteIpAddr
	 * @return
	 */
	boolean checkOnlineStatus(String tokenId, String channelId, String remoteIpAddr);
	
	/**
	 * 自动登录
	 * @param userId
	 * @param channelId
	 * @param clientIp
	 * @return
	 */
	UserDO proxyLogin(String userId, String channelId, String clientIp, String logoutTime);
	
	/**
	 * 自动登录，增加登录渠道
	 * @param userId
	 * @param channelId
	 * @param clientIp
	 * @param logoutTime
	 * @param loginChannel
	 * @return
	 */
	UserDO proxyLogin(String userId, String channelId, String clientIp, String logoutTime, String loginChannel);

}
