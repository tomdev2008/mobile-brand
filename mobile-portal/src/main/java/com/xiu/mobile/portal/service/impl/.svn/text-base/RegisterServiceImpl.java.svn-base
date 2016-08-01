package com.xiu.mobile.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.dispatch.common.dataobj.SendCardIntegDO;
import com.xiu.mobile.core.exception.EIRuntimeException;
import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.portal.common.util.AESCipher;
import com.xiu.mobile.portal.common.util.ParseProperties;
import com.xiu.mobile.portal.constants.ErrConstants;
import com.xiu.mobile.portal.constants.GlobalConstants;
import com.xiu.mobile.portal.dao.LoginDao;
import com.xiu.mobile.portal.ei.EIMobileSalesManager;
import com.xiu.mobile.portal.ei.EIPromotionManager;
import com.xiu.mobile.portal.ei.EIUUCManager;
import com.xiu.mobile.portal.model.LoginResVo;
import com.xiu.mobile.portal.model.RecommendRegBo;
import com.xiu.mobile.portal.service.ILoginService;
import com.xiu.mobile.portal.service.IRegisterService;
import com.xiu.sales.common.card.dataobject.CardInputParamDO;
import com.xiu.uuc.facade.dto.RegisterUserDTO;
import com.xiu.uuc.facade.dto.Result;
import com.xiu.uuc.facade.util.TypeEnum;

/**
 * <p>
 * ************************************************************** 
 * @Description: 用户注册业务逻辑实现类
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-14 上午9:46:10 
 * ***************************************************************
 * </p>
 */
@Service("registerService")
public class RegisterServiceImpl implements IRegisterService {
	private static Logger logger = Logger.getLogger(RegisterServiceImpl.class);

	@Autowired
	private ILoginService loginService;
	
	@Autowired
	private EIUUCManager eiuucManager;
	@Autowired
	private EIPromotionManager eiPromotionManager;
	
	@Autowired
	private LoginDao loginDao;
	
	@Override
	public LoginResVo register(Map<String, Object> map) {
		RegisterUserDTO user = new RegisterUserDTO();
		user.setRegisterType((String) map.get("regType"));
		user.setLogonName((String) map.get("logonName"));
		user.setPassword((String) map.get("password"));
		user.setChannelId(Integer.parseInt(GlobalConstants.CHANNEL_ID));
		user.setCustType("01");
		user.setRegisterIp((String) map.get(GlobalConstants.KEY_REMOTE_IP));
		user.setIsNeedActivate("N"); // 注册时直接激活用户
		user.setUserSource((Integer) map.get("userSource"));
		// 解密密码
		user.setPassword(decodePassword(user.getPassword()));
		user.setTrdName((String)map.get("thirdName"));
		user.setPicUrl((String)map.get("thirdPortraitURL"));

		eiuucManager.registerUser(user);
		
		/** 注册送券统一移到eiuucManager.registerUser(user) 方法里，以免后续自己增加注册业务时，忘了送券 */
//		Result result = eiuucManager.registerUser(user);

//		String isRegisterSendCoupon = ParseProperties
//				.getPropertiesValue(GlobalConstants.IS_REGISTER_SEND_COUPON);

//		if (isRegisterSendCoupon != null && isRegisterSendCoupon.equals("TRUE")) {
//			
//			try {
//				userRegisterGiveCoupon("register","mobile", Long.parseLong(result.getData().toString()),user.getLogonName());
//			} catch (Exception ex) {
//				logger.error("注册送券失败,用户名：" + user.getLogonName());
//			}
//		}

		// 注册成功后直接登录SSO
		if (logger.isDebugEnabled()) {
			logger.debug("register successfully. try logon");
		}
		
		LoginResVo loginRes = loginService.login(map);
		
		return loginRes;	
	}
	
	@Override
	public Long registerForMMKT(Map<String, Object> map) {
		RegisterUserDTO user = new RegisterUserDTO();
		user.setRegisterType((String) map.get("regType"));
		user.setLogonName((String) map.get("logonName"));
		user.setPassword((String) map.get("password"));
		user.setChannelId(Integer.parseInt(GlobalConstants.CHANNEL_ID));
		user.setCustType("01");
		user.setRegisterIp((String) map.get(GlobalConstants.KEY_REMOTE_IP));
		user.setIsNeedActivate("N"); // 注册时直接激活用户
		user.setUserSource((Integer) map.get("userSource"));

		Result result = eiuucManager.registerUser(user);

		Long xiuUserId = Long.parseLong(result.getData().toString());
		
		/** 注册送券统一移到eiuucManager.registerUser(user) 方法里，以免后续自己增加注册业务时，忘了送券 */
//		String isRegisterSendCoupon = ParseProperties
//				.getPropertiesValue(GlobalConstants.IS_REGISTER_SEND_COUPON);

//		if (isRegisterSendCoupon != null && isRegisterSendCoupon.equals("TRUE")) {
//			
//			try {
//				userRegisterGiveCoupon("register","mobile", xiuUserId, user.getLogonName());
//			} catch (Exception ex) {
//				logger.error("注册送券失败,用户名：" + user.getLogonName());
//			}
//		}

		// 注册成功后直接登录SSO
		if (logger.isDebugEnabled()) {
			logger.debug("register successfully. try logon");
		}
		
		return xiuUserId;	
	}
	
	@Override
	public boolean checkLogonNameIsExist(String logonName, String userSource) {
		// 联合登陆渠道ID
		Integer iPartnerChannelId = null;
		if (StringUtils.isNotBlank(userSource)) {
			iPartnerChannelId = getPartnerChannelId(userSource);
		} else {
			// 接口有问题，暂时这样处理
			iPartnerChannelId = Integer.valueOf(GlobalConstants.CHANNEL_ID);
		}
		
		Result result = eiuucManager.isLogonNameExist(logonName, iPartnerChannelId);
		if(result == null) {
			return false;
		}
		if(iPartnerChannelId != null) {
			return (null != (Long) result.getData());
		} else {
			return (Boolean) result.getData();
		}
	}
	
	/**
	 * 
	 * @param sendCardIntegDO
	 * @return
	 */
	private boolean sendCardByRegister(SendCardIntegDO sendCardIntegDO) {
		// 先取得券号
		com.xiu.common.command.result.Result sendResult = eiPromotionManager.sendCardByDeed(sendCardIntegDO);
		SendCardIntegDO retDo = (SendCardIntegDO) sendResult.getDefaultModel();
		String couponNo = retDo.getCouponNo();

		if (StringUtils.isNotEmpty(couponNo)) {
			CardInputParamDO par = new CardInputParamDO();
			// 销售渠道
			par.setChannelID(GlobalConstants.COUPON_CHANNEL_ID);
			// 终端用户类型
			par.setTerminalUser(GlobalConstants.TERMINAL_USER_TYPE);
			// 用户ID
			par.setUserId(Long.parseLong(sendCardIntegDO.getUserId()));
			// 用户名
			par.setUserName(sendCardIntegDO.getUserName());
			// 优惠券密码
			par.setCardPwd(retDo.getPwd());
			// 卡号
			par.setCardId(couponNo);

			for (int i = 1; i < 3; i++) {// 如果激活失败，将重试一次
				
				try {
					String result = eiPromotionManager.activeCardBody(par);

					if (result.equals("11")) {// 激活优惠券业务返回11表示成功
						logger.debug("第  " + i + "次注册送券成功！，用户名：" + sendCardIntegDO.getUserName() + "优惠券号：" + couponNo);
						return true;
					}
				} catch (Exception ex) {
					logger.error("第  " + i + "次注册送券失败，用户名："
									+ sendCardIntegDO.getUserName() + "优惠券号：" + couponNo);
				}
			}
		}

		return false;
	}
	
	/**
	 * 根据约定的规则解密密码
	 * @param encodedPassword
	 * @return
	 * @throws EIRuntimeException
	 */
	public String decodePassword(String encodedPassword) {
		encodedPassword = encodedPassword.replaceAll("<|>|\\s", "");
		AESCipher cipher = new AESCipher(GlobalConstants.SAFE_CODE);
		
		try {
			return cipher.decrypt(encodedPassword);
		} catch (Exception e) {
			throw ExceptionFactory.buildBusinessException(
					ErrConstants.GeneralErrorCode.BIZ_RET_CODE_ERROR, "解密密码错误");
		}
	}
	
	/**
	 * 根据联合登陆用户来源，获取对用渠道编码
	 * @param userSource
	 * @return iPartnerChannelId
	 */
	private Integer getPartnerChannelId(String userSource) {
		Integer iPartnerChannelId = null;
		
		if (GlobalConstants.TENCENT_QQ.equals(userSource)) {
			iPartnerChannelId = TypeEnum.PartnerChannelType.TENCENT_CAIBEI.getKey();
		} else if (GlobalConstants.ALIPAY.equals(userSource)) {
			iPartnerChannelId = TypeEnum.PartnerChannelType.ALIPAY.getKey();
		} else if (GlobalConstants.NETEASE.equals(userSource)) {
			iPartnerChannelId = TypeEnum.PartnerChannelType.NETEASE.getKey();
		} else if (GlobalConstants.MOBILE139.equals(userSource)) {
			iPartnerChannelId = TypeEnum.PartnerChannelType.MOBILE139.getKey();
		} else {
			// 默认
			iPartnerChannelId = Integer.valueOf(GlobalConstants.CHANNEL_ID);
		}
		
		return iPartnerChannelId;
	}

	@Override
	public int insertRecommendRegister(RecommendRegBo bo) {
		int result = 0;
		result = loginDao.insertRecommendRegister(bo)>=0?0:-1;
		return result;
	}
	
	/**
	 * 
	 * @param sendCardIntegDO
	 * @return
	 */
//	private boolean userRegisterGiveCoupon(String activityTypeName, String appTerminalTypeName, long userId, String userName) {
//		com.xiu.common.command.result.Result sendResult = eiMobileSalesManager.userRegisterGiveCoupon(activityTypeName, appTerminalTypeName, userId, userName);
//		return  null==sendResult?false: sendResult.isSuccess();
//	}
}
