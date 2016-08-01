package com.xiu.mobile.portal.dao;

import com.xiu.mobile.portal.model.RecommendRegBo;

public interface LoginDao {

	/** 
	 * 记录推荐注册
	 * @param map
	 * @return LoginResVo    
	 */
	int insertRecommendRegister(RecommendRegBo bo);
	
	/**
	 * 获取手机验证码
	 * @param mobile
	 * @return
	 */
	String getMobileVerifyCode(String mobile);
	
}
