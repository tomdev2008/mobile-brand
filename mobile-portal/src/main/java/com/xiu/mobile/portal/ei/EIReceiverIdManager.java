package com.xiu.mobile.portal.ei;

import java.util.List;

import com.xiu.uuc.facade.dto.IdentityInfoDTO;
import com.xiu.uuc.facade.dto.IdentityInfoQueryDTO;
import com.xiu.uuc.facade.dto.Result;
import com.xiu.uuc.facade.dto.UserIdentityDTO;
import com.xiu.uuc.facade.dto.UserQueryIdentityDTO;

/***
 * 收货人身份证审核信息接口
 * @author hejianxiong
 *
 */
public interface EIReceiverIdManager {
	
	/**
	 * 新增收货人-顾客身份证报关 信息
	 * @Title: insertUserIdentity 
	 * @param  userIdentityDto 收货人-顾客身份证报关 信息
	 * @return Result    返回类型 
	 * @throws
	 */
	public Result insertUserIdentity(UserIdentityDTO userIdentityDto);
	
	/**
	 * 通过地址ID 删除 收货人-顾客身份证报关 信息
	 * @Title: deleteByAddressId 
	 * @param  addressId 地址ID
	 * @return Result    返回类型 
	 * @throws
	 */
	public Result deleteByAddressId(Long addressId);

	
	/**
	 * 得到收货人-顾客身份证报关 信息列表 多表查询
	 * @Title: getUserIdentityListByObject
	 * @param  userQueryIdentityDTO 
	 * @return Result    返回类型 
	 * @throws
	 */
	public Result getUserIdentityListByObject(UserQueryIdentityDTO userQueryIdentityDTO);

	/**
	 * 得到收货人-顾客身份证报关 信息  单表查询
	 * @Title: getUserIdentity 
	 * @param  userIdentityDto 收货人-顾客身份证报关 信息
	 * @return Result    返回类型 
	 * @throws
	 */
	public Result getUserIdentity(UserIdentityDTO userIdentityDto);

	/**
	 * 修改收货人-顾客身份证报关 信息列表
	 * @Title: modifyUserIdentity 
	 * @param  userIdentityDto 收货人-顾客身份证报关 信息
	 * @return Result    返回类型 
	 * @throws
	 */
	public Result modifyUserIdentity(UserIdentityDTO userIdentityDto);
	
	/**
	 * 通过身份ID查询一条身份证信息记录
	 * @param identityId
	 * @return
	 * @time	2015-2-2
	 */
	public Result queryIdentityInfoById(Long identityId);
	
	/**
	 * 根据用户ID和（收货人名称或身份证姓名）查询用户信息记录，有多条记录则返回第一条
	 * @param userId
	 * @param rNameOrIdName
	 * @return
	 * @time	2015-2-2
	 */
	public Result queryIdentityInfoByUserIdAndName(Long userId, String rNameOrIdName);
	
	/**
	 * 根据条件查询身份信息记录
	 * @param logonName
	 * @return
	 * @time	2015-2-2
	 */
	public Result queryIdentityInfoList(IdentityInfoQueryDTO identityInfoQueryDTO);
	
	/**
	 * 更新一条身份信息记录
	 * @param identityInfoDTO
	 * @return
	 * @time	2015-2-2
	 */
	public Result updateIdentityInfo(IdentityInfoDTO identityInfoDTO);
	
	/**
	 * 根据集合删除多条身份信息记录
	 * @param indetityIds
	 * @return
	 * @time	2015-2-2
	 */
	public Result deleteIdentityInfoListByIds(List<Long> indetityIds);
	
	/**
	 * 删除一条身份信息记录
	 * @param indetityId
	 * @return
	 * @time	2015-2-2
	 */
	public Result deleteIdentityInfoById(Long indetityId);
	
	/**
	 * 新增身份信息记录
	 * @param identityInfoDTO
	 * @return
	 * @time	2015-2-2
	 */
	public Result insertIdentityInfo(IdentityInfoDTO identityInfoDTO);

}
