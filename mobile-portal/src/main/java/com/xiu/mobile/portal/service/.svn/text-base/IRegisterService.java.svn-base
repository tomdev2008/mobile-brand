package com.xiu.mobile.portal.service;

import java.util.Map;

import com.xiu.mobile.portal.model.LoginResVo;
import com.xiu.mobile.portal.model.RecommendRegBo;

/**
 * <p>
 * ************************************************************** 
 * @Description: 用户注册业务逻辑类接口
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-14 上午11:29:19 
 * ***************************************************************
 * </p>
 */
public interface IRegisterService {
	
	/**
	 * 检查用户名是否被注册
	 * @param logonName  登录名称（如果是联合登录，则为联合登陆的唯一标识）
	 * @param userSource 获取联合登陆渠道类型 ：TENCENT(QQ)、ALIPAY(支付宝)、SINA_WEIBO(新浪微博)等
	 * @return
	 */
	boolean checkLogonNameIsExist(String logonName, String userSource);
	
	/** 
	 * 提交注册
	 * @param map
	 * @return LoginResVo    
	 */

	LoginResVo register(Map<String, Object> map);
	
	/** 
	 * 记录推荐注册
	 * @param map
	 * @return LoginResVo    
	 */
	int insertRecommendRegister(RecommendRegBo bo);
	
	/** 
	 * 为微营销提供的注册用户接口
	 * @param map
	 * @return LoginResVo    
	 */

	Long registerForMMKT(Map<String, Object> map);
	
}
