package com.xiu.mobile.simple.ei;

import com.xiu.uuc.facade.dto.RegisterUserDTO;
import com.xiu.uuc.facade.dto.Result;
import com.xiu.uuc.facade.dto.UserBaseDTO;
import com.xiu.uuc.facade.dto.UserDetailDTO;


/** 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 
 * @AUTHOR : mike@xiu.com 
 * @DATE :2014-5-15 下午12:12:59
 * </p>
 **************************************************************** 
 */
public interface EIUUCManager {

	/**
	 * 判断登录名是否存在
	 * @param logonName
	 * @return
	 */
	Result isLogonNameExist(String logonName);
	
	/**
	 * 判断登录名是否存在（联合登录）
	 * @param logonName
	 * @param userSource
	 * @return
	 */
	Result isLogonNameExist(String logonName, Integer iPartnerChannelId);
	
	/**
	 * 注册用户
	 * @param partnerId
	 * @param logonName
	 * @param clientIp
	 * @param iPartnerChannelId
	 * @param userSource
	 * @return
	 */
	Result registerUser(RegisterUserDTO registerUserDTO);
	
	/**
	 * 取得用户基本信息
	 * @return
	 */
	UserBaseDTO getUserBasicInfoByUserId(Long userId);
	
	/***
	 * 更改用户密码
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	public Result modifyUserPassword(Long userId, String oldPassword, String newPassword);
	
	/**
	 * 通过用户名、channelId取得用户基本信息
	 * @return
	 */
	UserDetailDTO getUserBasicInfoByLogonName(String logonName,Integer channelId);
	
	/***
	 * 重置用户密码
	 * @param userId
	 * @param newPassword
	 * @return
	 */
	public Result resetUserPassword(Long userId,String newPassword);

}
