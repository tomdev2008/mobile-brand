package com.xiu.mobile.simple.service;

import java.util.Map;

import com.xiu.mobile.simple.model.LoginResVo;

public interface ILoginService {

	/**
	 * 用户登陆
	 * @param pmap
	 * @return
	 */
	LoginResVo login(Map<String, Object> pmap);
	
	/**
	 * 提交联合登陆
	 * @param valMap
	 * @return LoginResVo
	 */
	LoginResVo submitFederateLogin(Map<String, Object> valMap);
	
	/**
	 * 
	 * 检查用户登录状态是否有效
	 * 
	 * @param params{tokenId ...}
	 * @return true：token未失效,false：token失效
	 */
	boolean checkOnlineStatus(Map<String, Object> params);
	
	/**
	 * 检查用户登陆状态是否有效（校验tokenId与userId的一致性）
	 * @param tokenId 
	 * @param userId 
	 * @param ip
	 * @return
	 */
	boolean checkOnlineStatus(String tokenId, String userId, String ip);
	
	/** 
	 * 注销
	 * @Title: logOut 
	 * @Description: TODO
	 * @param @param pmap    
	 * @return void    
	 * @throws 
	 * @author: chengyuanhuan
	 * @date: 2013-5-7上午11:03:38
	 */
	boolean logOut(Map<String, Object> pmap);

}
