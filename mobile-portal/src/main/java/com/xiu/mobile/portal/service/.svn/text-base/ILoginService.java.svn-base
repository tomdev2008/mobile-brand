package com.xiu.mobile.portal.service;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.portal.model.BindUserInfo;
import com.xiu.mobile.portal.model.LoginResVo;
import com.xiu.uuc.facade.dto.UnionInfoDTO;

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
	LoginResVo unionLoginOrReg(Map<String, Object> valMap);
	
	/**
	 * 检查第三方账户是否生成走秀账户
	 * @param params
	 * @return
	 */
	public Map  checkUnionIsXiuExist(Map params);
	
	/**
	 * 走秀联合登录
	 * @param valMap
	 * @return LoginResVo
	 */
	LoginResVo federateLogin(Map<String, Object> valMap);
	
	/***
	 * 联合登录用户绑定走秀账号
	 * @param params
	 * @return
	 */
	boolean bindUser(Map<String, Object> params);
	
	/***
	 * 联合登录用户解绑走秀账号
	 * @param params
	 * @return
	 */
	boolean unBindUser(Map<String, Object> params);
	
	/***
	 * 走秀联合登录并绑定账号
	 * @param params
	 * @return
	 */
	public LoginResVo federateLoginAndBind(Map<String, Object> params);
	
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

	/**
	 * 通过验证码登录
	 * @param paraMap
	 * @return
	 */
	LoginResVo loginWithVerifyCode(Map<String, Object> paraMap, boolean isUserExists);
	
	/**
	 * 通过userId,userSource获取联合用户信息
	 * @return
	 */
	UnionInfoDTO getUnionInfoDTOByUserId(Long userId,String userSource);
	
	/**
	 * 通过userId获取联合用户信息
	 * @return
	 */
	List<BindUserInfo> getBindUserInfoListByUserId(Long userId);
	
	/**
	 * 通过unionInfoDTO条件信息获取联合用户信息
	 * @return
	 */
	List<UnionInfoDTO> getUnionInfoByCondition(UnionInfoDTO unionInfoDTO);
	/**
	 * 绑定百度推送设备信息bindBaiduDevicePushInfo
	 * @return
	 */
	boolean bindXiuUserToBaiduPushDevice(Map<String, Object> params);
	
	//获取手机验证码
	public String getMobileVerifyCode(String mobile);
}
