package com.xiu.mobile.portal.service;

import com.xiu.mobile.portal.model.UserPayPlatform;

public interface IUserPayPlatformService {
	
	/***
	 * 记录用户支付信息
	 * @param userId
	 * @param paymentMethod
	 */
	public void recordInfo(String userId,String paymentMethod);

	/***
	 * 获取用户支付信息
	 * 
	 * @param userId
	 * @return UserPayInfo
	 */
	public UserPayPlatform get(String userId);

	/***
	 * 新增用户支付信息
	 * 
	 * @param userPayInfo
	 */
	public void insert(UserPayPlatform userPayInfo);

	/***
	 * 更新用户支付信息
	 * 
	 * @param userPayInfo
	 */
	public void update(UserPayPlatform userPayInfo);
}
