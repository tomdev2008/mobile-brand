package com.xiu.mobile.portal.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.portal.dao.UserPayPlatformDao;
import com.xiu.mobile.portal.model.UserPayPlatform;
import com.xiu.mobile.portal.service.IUserPayPlatformService;

@Service("userPayPlatform")
public class UserPayPlatformServiceImpl implements IUserPayPlatformService {

	@Autowired
	private UserPayPlatformDao userPayPlatformDao;
	
	@Override
	public UserPayPlatform get(String userId) {
		return userPayPlatformDao.get(userId);
	}

	@Override
	public void insert(UserPayPlatform userPayInfo) {
		userPayPlatformDao.insert(userPayInfo);
	}

	@Override
	public void update(UserPayPlatform userPayInfo) {
		userPayPlatformDao.update(userPayInfo);
	}

	@Override
	public void recordInfo(String userId, String paymentMethod) {
		UserPayPlatform userPayInfo = userPayPlatformDao.get(userId);
		if (userPayInfo != null) {
			userPayInfo.setUpdateDate(new Date());
			userPayInfo.setPayPlatform(paymentMethod);
			userPayPlatformDao.update(userPayInfo);
		} else {
			userPayInfo = new UserPayPlatform();
			userPayInfo.setUserId(userId);
			userPayInfo.setCreateDate(new Date());
			userPayInfo.setPayPlatform(paymentMethod);
			userPayPlatformDao.insert(userPayInfo);
		}
	}

}
