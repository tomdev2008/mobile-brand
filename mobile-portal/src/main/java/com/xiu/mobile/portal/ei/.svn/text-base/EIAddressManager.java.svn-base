package com.xiu.mobile.portal.ei;

import java.util.List;

import com.xiu.uuc.facade.dto.RcvAddressDTO;
import com.xiu.uuc.facade.dto.Result;

/**
 * 
 *************************************************************** 
 * <p>
 * @DESCRIPTION ：收货地址管理外部接口 TC
 * @AUTHOR : chenghong.ding@xiu.com 
 * @DATE :2014年5月28日 上午11:51:55
 * </p>
 ****************************************************************
 */
public interface EIAddressManager {

	/**
	 * 新增收货地址
	 * @param addressInParam
	 * @return
	 */
	public Result addAddress(RcvAddressDTO rcvAddressDTO);
	
	/**
	 * 修改收货地址
	 * @param addressInParam
	 * @return
	 */
	public Result updateAddress(RcvAddressDTO rcvAddressDTO);
	
	/**
	 * 获取收货地址信息
	 * @param addressId
	 * @return
	 */
	public RcvAddressDTO getRcvAddressInfoById(Long addressId);
	
	/**
	 * 获取用户的收货地址
	 * @param userId
	 * @return
	 */
	public List<RcvAddressDTO> getRcvAddressListByUserId(Long userId);
	
	/**
	 * 删除收货地址
	 * @param addressInParam
	 * @return
	 */
	public Result delAddress(Long addressId);
	
	
}
