package com.xiu.mobile.portal.service;

import java.util.List;

import com.xiu.mobile.core.model.Page;
import com.xiu.uuc.facade.dto.IdentityInfoDTO;
import com.xiu.uuc.facade.dto.IdentityInfoQueryDTO;
import com.xiu.uuc.facade.dto.UserIdentityDTO;
import com.xiu.uuc.facade.dto.UserQueryIdentityDTO;

public interface IReceiverIdService {

	/**
	 * 新增收货人-顾客身份认证信息
	 * @Title: insertUserIdentity 
	 * @param  userIdentityDto 收货人-顾客身份证报关 信息
	 * @return long 收货地址Id 
	 * @throws
	 */
	public Long insertUserIdentity(UserIdentityDTO userIdentityDto);
	
	/**
	 * 通过地址ID 删除 收货人-顾客身份证报关 信息
	 * @Title: deleteByAddressId 
	 * @param  addressId 地址ID
	 * @return boolean 删除结果
	 * @throws
	 */
	public boolean deleteByAddressId(Long addressId);

	
	/**
	 * 得到收货人-顾客身份证报关 信息列表 多表查询
	 * @Title: getUserIdentityListByObject
	 * @param  userQueryIdentityDTO 
	 * @return Page<UserQueryIdentityDTO>    返回类型 
	 * @throws
	 */
	public Page<UserQueryIdentityDTO> getUserIdentityListByObject(UserQueryIdentityDTO userQueryIdentityDTO);

	/***
	 * 得到收货人-顾客身份证报关 信息  单表查询
	 * @param userId 用户Id
	 * @param addressId 地址Id
	 * @return
	 */
	public UserIdentityDTO getUserIdentity(String userId,String addressId);

	/**
	 * 修改收货人-顾客身份证报关 信息列表
	 * @Title: modifyUserIdentity 
	 * @param  userIdentityDto 收货人-顾客身份证报关 信息
	 * @return boolean  修改结果 
	 * @throws
	 */
	public boolean modifyUserIdentity(UserIdentityDTO userIdentityDto);
	
	/**
	 * 根据身份ID查询一条身份信息记录
	 * @param identityId
	 * @return
	 * @time 2015-2-2
	 */
	public IdentityInfoDTO queryIdentityInfoById(Long identityId);
	/**
	 * 新增身份信息记录
	 * @param identityInfoDTO
	 * @return
	 * @time	2015-2-2
	 */
	public Long insertIdentityInfo(IdentityInfoDTO identityInfoDTO);
	
	/**
	 * 更新身份信息记录
	 * @param identityInfoDTO
	 * @return
	 * @time	2015-2-2
	 */
	public Integer updateIdentityInfo(IdentityInfoDTO identityInfoDTO);
	
	/**
	 * 根据用户ID和（收货人名称或者身份证姓名）查询用户信息记录
	 * @param userId
	 * @param rNameOrIdName
	 * @return
	 * @time	2015-2-2
	 */
	public IdentityInfoDTO queryIdentityInfoByUserIdAndName(Long userId, String rNameOrIdName);
	
	/**
	 * 删除一条身份信息记录
	 * @param indetityId
	 * @return
	 */
	public Long deleteIdentityInfoById(Long indetityId);
	
}
