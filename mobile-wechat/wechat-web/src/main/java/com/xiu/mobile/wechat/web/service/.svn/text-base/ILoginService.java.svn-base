package com.xiu.mobile.wechat.web.service;

import com.xiu.sso.server.dto.UserDO;

public interface ILoginService {

	/**
	 * 检查用户登录状态
	 * 
	 * @param tokenId
	 * @param channelId
	 * @param clientIp
	 * @return 检查结果 true:登录状态有效 ; false:未登录或登录已超时
	 */
	boolean checkOnlineStatus(String tokenId, String channelId, String clientIp);

	/**
	 * 登录（走秀账号登录）
	 * 
	 * @param logonName
	 * @param password
	 * @param channelId
	 * @param clientIp
	 * @return 登录结果
	 */
	UserDO xiuLogin(String logonName, String password, String channelId, String clientIp);

	/**
	 * 联合登陆
	 * 
	 * @param openId
	 * @param unionId
	 * @param nickname
	 * @param clientIp
	 * @return
	 */
	UserDO unionLogin(String openId, String unionId, String nickname, String clientIp);

	/**
	 * 代客登录
	 * 
	 * @param userId
	 * @param channelId
	 * @param clientIp
	 * @return
	 */
	UserDO proxyLogin(Long userId, String channelId, String clientIp);

}
