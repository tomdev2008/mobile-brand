package com.xiu.mobile.simple.service;

import java.util.Map;

import com.xiu.uuc.facade.dto.UserBaseDTO;
import com.xiu.uuc.facade.dto.UserDetailDTO;

public interface IUserService {
	/**
	 * 判断登录名是否存在
	 * @param logonName
	 * @return
	 */
	boolean isLogonNameExist(String logonName);
	
	/**
	 * 判断登录名是否存在（联合登录）
	 * @param logonName
	 * @param userSource
	 * @return
	 */
	boolean isLogonNameExist(String logonName, Integer iPartnerChannelId);
	
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
	public Map<String, Object> modifyUserPassword(Long userId, String oldPassword, String newPassword);
	
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
	public Map<String, Object> resetUserPassword(Long userId,String newPassword);
}
