package com.xiu.mobile.portal.service;

import java.util.Map;

import com.xiu.mobile.portal.model.ShowUserInfoVo;
import com.xiu.uuc.facade.dto.ModifyUserAuthenDTO;
import com.xiu.uuc.facade.dto.UnionUserBindingDTO;
import com.xiu.uuc.facade.dto.UserBaseDTO;
import com.xiu.uuc.facade.dto.UserDetailDTO;
import com.xiu.uuc.facade.dto.UserOperateLogInfoDTO;

public interface IUserService {
	/**
	 * 判断登录名是否存在
	 * @param logonName
	 * @return
	 */
	boolean isLogonNameExist(String logonName);
	
	/**
	 * 判断登录用户昵称是否存在
	 * @param petName
	 * @return
	 */
	boolean isPetNameExist(String petName);
	
	/**
	 * 判断登录名是否存在（联合登录）
	 * @param logonName
	 * @param userSource
	 * @return
	 */
	boolean isLogonNameExist(String logonName, Integer iPartnerChannelId);
	
	/***
	 * 判断登陆名是否能注册
	 * @param logonName
	 * @return
	 */
	public boolean isLogonNameCanRegister(String logonName);
	
	/***
	 * 判断登陆名是否能注册
	 * @param logonName
	 * @param type(1: 邮箱, 2:手机, 3:呢称)
	 * @return
	 */
	public boolean isLogonNameCanRegister(String logonName,String type);
	
	/**
	 * 取得用户基本信息
	 * @return
	 */
	UserBaseDTO getUserBasicInfoByUserId(Long userId);
	
	/**
	 * 取得用户详细信息
	 * @return
	 */
	UserDetailDTO getUserDetailDTOByUserId(Long userId);
	
	/***
	 * 获取用户名密码信息
	 * @param logonName
	 * @return
	 */
	public String getPasswordByLogonName(String logonName);
	
	/***
	 * 更改用户密码
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	public boolean modifyUserPassword(Long userId, String oldPassword, String newPassword);
	
	/***
	 * 更新用户基本信息
	 * @param userBaseDTO
	 * @return
	 */
	public boolean modifyUserBaseInfo(UserBaseDTO userBaseDTO);
	
	/**
	 * 更新用户基本信息，增加日志记录
	 * @param userOperateLogInfoDTO
	 * @return
	 */
	public boolean modifyUserBaseInfo(UserOperateLogInfoDTO userOperateLogInfoDTO);
	
	/***
	 * 联合登录用户绑定走秀用户
	 * @param unionUserBindingDTO
	 * @return
	 */
	public boolean bindUser(UnionUserBindingDTO unionUserBindingDTO);
	
	/**
	 * 联合登录用户绑定走秀用户，增加日志记录
	 * @param userOperateLogInfoDTO
	 * @return
	 */
	public boolean bindUser(UserOperateLogInfoDTO userOperateLogInfoDTO);
	
	/***
	 * 更新用户详细信息
	 * @param userDetailDTO
	 * @return
	 */
	public boolean modifyUserDetailDTO(UserDetailDTO userDetailDTO);
	
	/**
	 * 更新用户详细信息，增加日志记录
	 * @param userOperateLogInfoDTO
	 * @return
	 */
	public boolean modifyUserDetailDTO(UserOperateLogInfoDTO userOperateLogInfoDTO);
	
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
	public boolean resetUserPassword(Long userId,String newPassword);
	
	/***
	 * 判断当前用户是否是空密码
	 * @param userId
	 * @return
	 */
	public boolean isEmptyPassword(Long userId);
	
	/**
	 * 获取秀客信息
	 * @param userId
	 * @return
	 */
	public ShowUserInfoVo getShowUserInfo(String userId);
	
	
	/***
	 * 判断当前用户是否是空密码
	 * @param userId
	 * @return
	 */
	public boolean getManagerAuthority(Map params);
	
	/**
	 * 查询用户手机是否绑定
	 * @param userId
	 * @return
	 */
	public boolean isMobileBinding(String userId);
	
	/**
	 * 修改用户认证状态
	 * @param modityUserAuthenDTO
	 * @return
	 */
	public boolean modifyUserAuthen(ModifyUserAuthenDTO modityUserAuthenDTO);
	
	/**
	 * 修改用户认证状态，增加日志参数
	 * @param serOperateLogInfoDTO
	 * @return
	 */
	public boolean modifyUserAuthen(UserOperateLogInfoDTO userOperateLogInfoDTO);
	
	/**
	 * 查询用户是否有资产
	 * @param userId
	 * @return
	 */
	public boolean isUserHasAsset(String userId);
	
	/**
	 * 删除用户认证信息
	 * @param userId
	 * @param authenType
	 * @param authenValue
	 * @param ipAddr
	 * @return
	 */
	public boolean deleteUserAuthen(Long userId, String authenType, String authenValue, String ipAddr);
	
	/**
	 * 更新用户昵称
	 * @param userId
	 * @param thirdName
	 * @return
	 */
	public boolean updateUserPetName(String userId, String thirdName);
	
	
}
