package com.xiu.mobile.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.common.lang.StringUtil;
import com.xiu.mobile.core.exception.EIRuntimeException;
import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.portal.common.util.AESCipher;
import com.xiu.mobile.portal.constants.ErrConstants;
import com.xiu.mobile.portal.constants.GlobalConstants;
import com.xiu.mobile.portal.dao.LoginDao;
import com.xiu.mobile.portal.ei.EISSOManager;
import com.xiu.mobile.portal.ei.EIUUCManager;
import com.xiu.mobile.portal.model.BindUserInfo;
import com.xiu.mobile.portal.model.LoginResVo;
import com.xiu.mobile.portal.service.ILoginService;
import com.xiu.sso.server.dto.UserDO;
import com.xiu.uuc.facade.dto.DeviceInfoDTO;
import com.xiu.uuc.facade.dto.ModifyUserAuthenDTO;
import com.xiu.uuc.facade.dto.RegisterUserDTO;
import com.xiu.uuc.facade.dto.Result;
import com.xiu.uuc.facade.dto.UnionInfoDTO;
import com.xiu.uuc.facade.dto.UnionUserBindingDTO;
import com.xiu.uuc.facade.dto.UserBaseDTO;
import com.xiu.uuc.facade.dto.UserOperateLogInfoDTO;
import com.xiu.uuc.facade.util.TypeEnum;

@Service("loginService")
public class LoginServiceImpl implements ILoginService {
	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
	@Autowired
	private EISSOManager eissoManager;
	@Autowired
	private EIUUCManager eiuucManager;
	@Autowired
	private LoginDao loginDao;
	
	@Override
	public LoginResVo login(Map<String, Object> params) {
		String logonName = (String) params.get("logonName");
		String password = (String) params.get("password");
		String ip = (String) params.get(GlobalConstants.KEY_REMOTE_IP);
		String channel = GlobalConstants.CHANNEL_ID;
		String logoutTime = GlobalConstants.ONLINE_EXPIRED_TIME; // 最大时间336小时
		String loginChannel=(String) params.get("loginChannel");
		//userSource:用户注册来源(秀官网注册：110,IOS APP注册：130,Android APP注册：140,手机WAP注册：150)
		//登录渠道(pc-web:官网;mobile-wap:手机wap;android android应用;ios-app IOS应用)
		try {
			if (loginChannel == null || loginChannel.trim().equals("")) {

				Object userSource = params.get("userSource");
				if (null == userSource) {
					loginChannel = "mobile-wap";
				} else if ("110".equals(userSource.toString())) {
					loginChannel = "pc-web";
				} else if ("130".equals(userSource.toString())) {
					loginChannel = "ios-app";
				} else if ("140".equals(userSource.toString())) {
					loginChannel = "android";
				} else if ("150".equals(userSource.toString())) {
					loginChannel = "mobile-wap";
				} else {
					loginChannel = "mobile-wap";
				}
			}
		} catch (Exception e) {
			logger.error("用户注册来源数据转换异常：" + e.getMessage());
		}
		
		// 解密密码
		password = this.decodePassword(password); 
		
		UserDO user = eissoManager.logon(logonName, password, channel, ip, logoutTime,loginChannel);
		
		LoginResVo loginResVo = new LoginResVo();
		loginResVo.setRetCode(GlobalConstants.RET_CODE_SUCESSS);
		loginResVo.setTokenId(user.getTokenId());
		loginResVo.setUserId(user.getUserId());
		loginResVo.setLogonName(user.getLoginName());
		return loginResVo;
	}
	

	@Override
	public boolean checkOnlineStatus(Map<String, Object> params) {
		String tokenId = (String) params.get("tokenId");
		String channelId = GlobalConstants.CHANNEL_ID;
		String ip = (String) params.get(GlobalConstants.KEY_REMOTE_IP);
		
		return eissoManager.checkOnlineStatus(tokenId, channelId, ip);
	}
	
	@Override
	public boolean checkOnlineStatus(String tokenId, String userId, String ip) {
		String channelId = GlobalConstants.CHANNEL_ID;
		return eissoManager.checkOnlineStatus(tokenId, channelId, ip);
	}

	@Override
	public boolean logOut(Map<String, Object> pmap) {
		String tokenId = (String) pmap.get("tokenId");
		String channelId = GlobalConstants.CHANNEL_ID;
		
		UserDO user = eissoManager.logout(tokenId, channelId);
		return user.getIsSuccess();
	}


	@Override
	//App联合登录旧接口,有则登录，无则创建默认帐号并登录
	public LoginResVo unionLoginOrReg(Map<String, Object> params) {
		// 获取联合登陆渠道类型 ：TENCENT(QQ)、ALIPAY(支付宝)、SINA_WEIBO(新浪微博)等
		String userSource = (String) params.get("userSource");
		// 联合登录唯一标识
		String partnerId = (String) params.get("partnerId");
		//weChatOpenId是微信公众号的id，暂时的id；partnerId 存放的是统一的unionId
		String weChatOpenId = (String) params.get("weChatOpenId");
		
		if(null!=userSource&&"alipay".equals(userSource)){
			partnerId =(String) params.get("partnerId")+"@alipay";
		}
		//第三方登录名
		String logonName = String.valueOf(params.get("logonName"));
		String clientIp = String.valueOf(params.get(GlobalConstants.KEY_REMOTE_IP));
		String channelId = GlobalConstants.CHANNEL_ID;
		String logoutTime = GlobalConstants.ONLINE_EXPIRED_TIME; // 最大时间336小时
		String loginChannel = String.valueOf(params.get("loginChannel"));
		
		String deviceId = (String) params.get("deviceId");		//设备ID
		String remoteIp = (String) params.get(GlobalConstants.KEY_REMOTE_IP);		//IP地址
		String sysName = (String) params.get("sysName");		//系统名称
		String sysVersion = (String) params.get("sysVersion");	//系统版本
		
		// 联合登陆渠道ID
		Integer iPartnerChannelId = getPartnerChannelId(userSource);
		
		String userId = null;
		Result resultExist = null;
		
		try{
		resultExist = eiuucManager.isLogonNameExist(partnerId, iPartnerChannelId);
		userId = resultExist.getData() == null ? null : resultExist.getData().toString();
		}catch (Exception e){
			logger.error("1调用isLogonNameExist uuc返回issuccess结果为0",e);
		}
		
		//unionId没有登录过检查openId是否有帐号
		if(StringUtils.isEmpty(userId) && "tencent_wechat".equals(userSource)&& !StringUtils.isEmpty(weChatOpenId)){
			try{
			resultExist = eiuucManager.isLogonNameExist(weChatOpenId, iPartnerChannelId);
			userId = resultExist.getData() == null ? null : resultExist.getData().toString();
			//如果opendId有帐号
			if(!StringUtils.isEmpty(userId))
			   try { 
			        // 当用户是openid登录时，需要把openid更换为unionId。 
			         UserBaseDTO baseDTO = eiuucManager.getUserBasicInfoByUserId(Long.parseLong(userId));
			         baseDTO.setPartnerId(partnerId); 
			         
			         UserOperateLogInfoDTO userOperateLogInfoDTO = new UserOperateLogInfoDTO();
			 		 userOperateLogInfoDTO.setDeviceNum(deviceId);
			 		 userOperateLogInfoDTO.setOperAddr(remoteIp);
			 		 userOperateLogInfoDTO.setSysName(sysName);
			 		 userOperateLogInfoDTO.setSysNum(sysVersion);
			 		 userOperateLogInfoDTO.setUserBaseDTO(baseDTO);
			 		 
			         eiuucManager.modifyUserBaseInfo(userOperateLogInfoDTO); 
			    } catch (Exception e) {// 屏蔽异常信息 
			        logger.error("微信联合登陆保存union_id原值时发生异常.union_id:" + partnerId, e); 
			    }
			
			}catch(Exception e) {
				logger.error("2调用isLogonNameExist uuc返回issuccess结果为0",e);
			}
		}
		
		String thirdName = (String) params.get("thirdName");
		String thirdPortraitURL = (String) params.get("thirdPortraitURL");
		
		if (StringUtils.isEmpty(userId)) { 
			
			// 构建联合登录的注册对象RegisterUserDTO
			RegisterUserDTO registerUserDTO = buildRegisterUserDTO(partnerId, 
					logonName, clientIp, iPartnerChannelId, userSource,thirdName,thirdPortraitURL);	
			
			Result result = eiuucManager.registerUser(registerUserDTO);
			userId = result.getData().toString();
			
			if (StringUtils.isEmpty(userId)) {
				throw ExceptionFactory.buildBusinessException(
						GlobalConstants.RET_CODE_LOGON_FAILED, "自动注册用户失败.");
			}
		}
		
		UserDO user = eissoManager.proxyLogin(userId, channelId, clientIp, logoutTime, loginChannel);
		try{
			if(null != thirdName){
				eiuucManager.setDefaultUserInfoFromTrd(Long.parseLong(userId), getPartnerChannelId(userSource) ,partnerId, thirdPortraitURL, thirdName);
			}
		}catch(Exception e){
			logger.error("setDefaultUserInfoFromTrd异常",e);	
		}
		
		// 登录成功
		LoginResVo loginResVo = new LoginResVo();
		loginResVo.setRetCode(GlobalConstants.RET_CODE_SUCESSS);
		loginResVo.setTokenId(user.getTokenId());
		loginResVo.setUserId(user.getUserId());
		loginResVo.setLogonName(getLogonName(user, logonName, userSource));
		return loginResVo;
	}
	
	/**
	 * 检查第三方账户是否生成走秀账户
	 * @param params
	 * @return
	 */
	public Map  checkUnionIsXiuExist(Map params){
		Map result=new HashMap();
		boolean isSuccess=true;
		// 获取联合登陆渠道类型 ：TENCENT(QQ)、ALIPAY(支付宝)、SINA_WEIBO(新浪微博)等
		String userSource = (String) params.get("userSource");
		// 联合登录唯一标识
		String partnerId = (String) params.get("partnerId");
		//weChatOpenId是微信公众号的id，暂时的id；partnerId 存放的是统一的unionId
		String weChatOpenId = (String) params.get("weChatOpenId");
		
		if(null!=userSource&&"alipay".equals(userSource)){
			partnerId =(String) params.get("partnerId")+"@alipay";
		}
		// 联合登陆渠道ID
		Integer iPartnerChannelId = getPartnerChannelId(userSource);
		
		String userId = null;
		Result resultExist = null;
		Boolean isExist=false;
		try{
			resultExist = eiuucManager.isLogonNameExist(partnerId, iPartnerChannelId);
			userId = resultExist.getData() == null ? null : resultExist.getData().toString();
			if(!StringUtils.isEmpty(userId)){
				isExist=true;
			}
			}catch (Exception e){
				logger.error("1调用isLogonNameExist uuc返回issuccess结果为0",e);
				isSuccess=false;
			}
			
			//unionId没有登录过检查openId是否有帐号
			if(StringUtils.isEmpty(userId) && "tencent_wechat".equals(userSource)&& !StringUtils.isEmpty(weChatOpenId)){
				try{
					resultExist = eiuucManager.isLogonNameExist(weChatOpenId, iPartnerChannelId);
					userId = resultExist.getData() == null ? null : resultExist.getData().toString();
					//如果opendId有帐号
					if(!StringUtils.isEmpty(userId)){
						isExist= true;
					}
				}catch(Exception e) {
					logger.error("2调用isLogonNameExist uuc返回issuccess结果为0",e);
					isSuccess=false;
				}
			}
			result.put("isSuccess", isSuccess);
			result.put("isExist", isExist);
			result.put("userId", userId);
			return result;
	}
	
	
	@Override
	//仅代理登录不建默认帐号,返回联合登录的状态
	public LoginResVo federateLogin(Map<String, Object> params) {
		// 获取联合登陆渠道类型 ：TENCENT(QQ)、ALIPAY(支付宝)、SINA_WEIBO(新浪微博)等
		String userSource = (String) params.get("userSource");
		// 联合登录唯一标识
		String partnerId = (String) params.get("partnerId");
		String weChatOpenId = (String) params.get("weChatOpenId");

		if (null != userSource && "alipay".equals(userSource)) {
			partnerId = (String) params.get("partnerId") + "@alipay";
		}
		
		String clientIp = String.valueOf(params.get(GlobalConstants.KEY_REMOTE_IP));
		String channelId = GlobalConstants.CHANNEL_ID;
		String logoutTime = GlobalConstants.ONLINE_EXPIRED_TIME; // 最大时间336小时
		String loginChannel = String.valueOf(params.get("loginChannel"));
		
		String deviceId = (String) params.get("deviceId");		//设备ID
		String remoteIp = (String) params.get(GlobalConstants.KEY_REMOTE_IP);		//IP地址
		String sysName = (String) params.get("sysName");		//系统名称
		String sysVersion = (String) params.get("sysVersion");	//系统版本
		
		// 联合登陆渠道ID
		Integer iPartnerChannelId = getPartnerChannelId(userSource);
		
		String userId = null;
		Result resultExist = null;
		
		try {
			resultExist = eiuucManager.isLogonNameExist(partnerId, iPartnerChannelId);
			userId = resultExist.getData() == null ? null : resultExist.getData().toString();
		} catch (Exception e) {
			logger.error("1调用isLogonNameExist uuc返回issuccess结果为0", e);
		}

		// unionId没有登录过检查openId是否有帐号
		if (StringUtils.isEmpty(userId) && "tencent_wechat".equals(userSource) && !StringUtils.isEmpty(weChatOpenId)) {
			try {
				resultExist = eiuucManager.isLogonNameExist(weChatOpenId, iPartnerChannelId);
				userId = resultExist.getData() == null ? null : resultExist.getData().toString();
				// 如果opendId有帐号
				if (StringUtils.isNotBlank(userId)){
					try {
						// 当用户是openid登录时，需要把openid更换为unionId。
						UserBaseDTO baseDTO = eiuucManager.getUserBasicInfoByUserId(Long.parseLong(userId));
						baseDTO.setPartnerId(partnerId);
						
						UserOperateLogInfoDTO userOperateLogInfoDTO = new UserOperateLogInfoDTO();
				 		userOperateLogInfoDTO.setDeviceNum(deviceId);
				 		userOperateLogInfoDTO.setOperAddr(remoteIp);
				 		userOperateLogInfoDTO.setSysName(sysName);
				 		userOperateLogInfoDTO.setSysNum(sysVersion);
				 		userOperateLogInfoDTO.setUserBaseDTO(baseDTO);
				 		 
						eiuucManager.modifyUserBaseInfo(userOperateLogInfoDTO);
					} catch (Exception e) {// 屏蔽异常信息
						logger.error("微信联合登陆保存union_id原值时发生异常.union_id:" + partnerId, e);
					}
				}

			} catch (Exception e) {
				logger.error("2调用isLogonNameExist uuc返回issuccess结果为0", e);
			}
		}
		
		if (StringUtils.isNotBlank(userId)) { 
			UserDO user = eissoManager.proxyLogin(userId, channelId, clientIp, logoutTime, loginChannel);


			try {
				//处置第三方用户信息
				String thirdName = (String) params.get("thirdName");
				String thirdPortraitURL = (String) params.get("thirdPortraitURL");
				if(null!=thirdName){
					eiuucManager.setDefaultUserInfoFromTrd(Long.parseLong(userId), getPartnerChannelId(userSource), partnerId, thirdPortraitURL, thirdName);
				}
			}catch(Exception e){
				logger.error("setDefaultUserInfoFromTrd异常", e);	
			}
			// 登录成功
			LoginResVo loginResVo = new LoginResVo();
			loginResVo.setRetCode(GlobalConstants.RET_CODE_SUCESSS);
			loginResVo.setTokenId(user.getTokenId());
			loginResVo.setUserId(user.getUserId());
			loginResVo.setLogonName(user.getLoginName());
			return loginResVo;
		}else{
			return null;
		}
	}
	
	
	@Override
	public LoginResVo federateLoginAndBind(Map<String, Object> params) {
		// 获取联合登陆渠道类型 ：TENCENT(QQ)、ALIPAY(支付宝)、SINA_WEIBO(新浪微博)等
		String userSource = (String) params.get("userSource");
		// 联合登录唯一标识
		String partnerId = (String) params.get("partnerId");
		String logonName = (String) params.get("logonName");
		String password = (String) params.get("password");
		String ip = (String) params.get(GlobalConstants.KEY_REMOTE_IP);
		String channel = GlobalConstants.CHANNEL_ID;
		String logoutTime = GlobalConstants.ONLINE_EXPIRED_TIME; // 最大时间336小时
		String deviceId = (String) params.get("deviceId");		//设备ID
		String sysName = (String) params.get("sysName");		//系统名称
		String sysVersion = (String) params.get("sysVersion");	//系统版本
		
		//登录渠道(pc-web:官网;mobile-wap:手机wap;android android应用;ios-app IOS应用)
		String loginChannel=(String) params.get("loginChannel");
		if (null != userSource && "alipay".equals(userSource)) {
			partnerId = (String) params.get("partnerId") + "@alipay";
		}
		// 解密密码
		password = this.decodePassword(password); 
		// 用户登录
		UserDO user = eissoManager.logon(logonName, password, channel, ip, logoutTime,loginChannel);
		if (user != null ) {
			LoginResVo loginResVo = new LoginResVo();
			loginResVo.setRetCode(GlobalConstants.RET_CODE_SUCESSS);
			loginResVo.setTokenId(user.getTokenId());
			loginResVo.setUserId(user.getUserId());
			// 联合登陆渠道ID
			Integer iPartnerChannelId = getPartnerChannelId(userSource);
			// 联合用户绑定输入参数
			UserOperateLogInfoDTO userOperateLogInfoDTO = new UserOperateLogInfoDTO();
			userOperateLogInfoDTO.setDeviceNum(deviceId);
			userOperateLogInfoDTO.setOperAddr(ip);
			userOperateLogInfoDTO.setSysName(sysName);
			userOperateLogInfoDTO.setSysNum(sysVersion);
			
			UnionUserBindingDTO unionUserBindingDTO = new UnionUserBindingDTO();
			unionUserBindingDTO.setExtendInfo(partnerId);
			unionUserBindingDTO.setPartnerChannelId(iPartnerChannelId);
			unionUserBindingDTO.setPartnerId(partnerId);
			unionUserBindingDTO.setUserId(Long.parseLong(user.getUserId()));
			userOperateLogInfoDTO.setUnionUserBindingDTO(unionUserBindingDTO);
			
			// 存储返回结果值
			Result result = eiuucManager.bindUser(userOperateLogInfoDTO);
			if (result != null && "1".equals(result.getSuccess())) {
				return loginResVo;
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	@Override
	public boolean bindUser(Map<String, Object> params) {
		// 获取联合登陆渠道类型 ：TENCENT(QQ)、ALIPAY(支付宝)、SINA_WEIBO(新浪微博)等
		String userSource = (String) params.get("userSource");
		// 联合登录唯一标识
		String partnerId = (String) params.get("partnerId");
		String userId = (String)params.get("userId");
		if (null != userSource && "alipay".equals(userSource)) {
			partnerId = (String) params.get("partnerId") + "@alipay";
		}
		String deviceId = (String) params.get("deviceId");		//设备ID
		String remoteIp = (String) params.get(GlobalConstants.KEY_REMOTE_IP);		//IP地址
		String sysName = (String) params.get("sysName");		//系统名称
		String sysVersion = (String) params.get("sysVersion");	//系统版本
		
		UserOperateLogInfoDTO userOperateLogInfoDTO = new UserOperateLogInfoDTO();
		userOperateLogInfoDTO.setDeviceNum(deviceId);
		userOperateLogInfoDTO.setOperAddr(remoteIp);
		userOperateLogInfoDTO.setSysName(sysName);
		userOperateLogInfoDTO.setSysNum(sysVersion);
		
		// 联合用户绑定输入参数
		UnionUserBindingDTO unionUserBindingDTO = new UnionUserBindingDTO();
		unionUserBindingDTO.setExtendInfo(partnerId);
		// 联合登陆渠道ID
		Integer iPartnerChannelId = getPartnerChannelId(userSource);
		unionUserBindingDTO.setPartnerChannelId(iPartnerChannelId);
		unionUserBindingDTO.setPartnerId(partnerId);
		unionUserBindingDTO.setUserId(Long.parseLong(userId));
		userOperateLogInfoDTO.setUnionUserBindingDTO(unionUserBindingDTO);
		
		// 存储返回结果值
		boolean flag = false;
		Result result = eiuucManager.bindUser(userOperateLogInfoDTO);
		if (result != null && "1".equals(result.getSuccess())) {
			try{
				//处置第三方原名和头像
				String thirdName = (String) params.get("thirdName");
				String thirdPortraitURL = (String) params.get("thirdPortraitURL");
				if(null!= thirdName ){
					eiuucManager.setDefaultUserInfoFromTrd(Long.parseLong(userId), getPartnerChannelId(userSource), partnerId, thirdPortraitURL, thirdName);
				}
			}catch(Exception e){
				logger.error("setDefaultUserInfoFromTrd异常", e);	
			}
			flag = true;
		}
		return flag;
	}
	
	@Override
	public boolean unBindUser(Map<String, Object> params) {
		// 获取联合登陆渠道类型 ：TENCENT(QQ)、ALIPAY(支付宝)、SINA_WEIBO(新浪微博)等
		String userSource = (String) params.get("userSource");
		// 联合登录唯一标识
		String partnerId = (String) params.get("partnerId");
		String userId = (String)params.get("userId");
		String deviceId = (String) params.get("deviceId");		//设备ID
		String remoteIp = (String) params.get(GlobalConstants.KEY_REMOTE_IP);		//IP地址
		String sysName = (String) params.get("sysName");		//系统名称
		String sysVersion = (String) params.get("sysVersion");	//系统版本
		if (null != userSource && "alipay".equals(userSource)) {
			partnerId = (String) params.get("partnerId");
			// 如果没有增加@alipay后缀 则添加
			if (!partnerId.contains("@alipay")) {
				partnerId = partnerId.concat("@alipay");
			}
		}
		
		UserOperateLogInfoDTO userOperateLogInfoDTO = new UserOperateLogInfoDTO();
		userOperateLogInfoDTO.setDeviceNum(deviceId);
		userOperateLogInfoDTO.setOperAddr(remoteIp);
		userOperateLogInfoDTO.setSysName(sysName);
		userOperateLogInfoDTO.setSysNum(sysVersion);
		
		// 联合用户绑定输入参数
		UnionUserBindingDTO unionUserBindingDTO = new UnionUserBindingDTO();
		unionUserBindingDTO.setExtendInfo(partnerId);
		// 联合登陆渠道ID
		Integer iPartnerChannelId = getPartnerChannelId(userSource);
		unionUserBindingDTO.setPartnerChannelId(iPartnerChannelId);
		unionUserBindingDTO.setPartnerId(partnerId);
		unionUserBindingDTO.setUserId(Long.parseLong(userId));
		userOperateLogInfoDTO.setUnionUserBindingDTO(unionUserBindingDTO);
		
		// 存储返回结果值
		boolean flag = eiuucManager.unBindUser(userOperateLogInfoDTO);
		return flag;
	}
	
	/**
	 * 根据约定的规则解密密码
	 * @param encodedPassword
	 * @return
	 * @throws EIRuntimeException
	 */
	public String decodePassword(String encodedPassword) throws EIRuntimeException {
		encodedPassword = encodedPassword.replaceAll("<|>|\\s", "");
		AESCipher cipher = new AESCipher(GlobalConstants.SAFE_CODE);
		
		try {
			return cipher.decrypt(encodedPassword);
		} catch (Exception e) {
			throw ExceptionFactory.buildBusinessException(ErrConstants.GeneralErrorCode.BIZ_RET_CODE_ERROR
					, "Encoded Password:{"+encodedPassword+"} error.");
		}
	}
	
	/**
	 * 根据联合登陆用户来源，获取对用渠道编码
	 * 
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
		} else if (GlobalConstants.SINA_WEIBO.equals(userSource)) {
			iPartnerChannelId = TypeEnum.PartnerChannelType.SINA_WEIBO_XIU.getKey();
		} else if (GlobalConstants.WANLITONG.equals(userSource)) {
			iPartnerChannelId = TypeEnum.PartnerChannelType.WANLITONG.getKey();
		} else if (GlobalConstants.TENCENT_WECHAT.equals(userSource)) {
			iPartnerChannelId = GlobalConstants.PARTNER_CHANNEL_WECHAT;
		}
		
		return iPartnerChannelId;
	}
	
	/**
	 * 根据参数组装注册用户DTO
	 * 
	 * @param partnerId
	 * @param logonName
	 * @param clientIp
	 * @param iPartnerChannelId
	 * @param string
	 * @return RegisterUserDTO
	 */
	private RegisterUserDTO buildRegisterUserDTO(String partnerId, String logonName, 
			String clientIp, Integer iPartnerChannelId, String userSource,String thirdName,String  thirdPortraitURL) {
		// 构建注册对象
		RegisterUserDTO registerUserDTO = new RegisterUserDTO();
		registerUserDTO.setLogonName(
				assembleLogonName(partnerId, logonName, userSource)); // 登录名
		registerUserDTO.setPassword(""); // 联合登陆默认密码为空

		// 用户所在渠道
		Integer channelId = StringUtils.isNotEmpty(
				GlobalConstants.CHANNEL_ID) ? new Integer(GlobalConstants.CHANNEL_ID) : 11;
		registerUserDTO.setChannelId(channelId);

		registerUserDTO.setRegisterType(TypeEnum.RegisterType.PETNAME.getKey()); // 注册类型
		registerUserDTO.setPartnerId(partnerId); // 用户ID
		registerUserDTO.setPartnerChannelId(iPartnerChannelId); // 来自那个渠道
		registerUserDTO.setCustType(TypeEnum.CustType.PARTNER.getKey()); // 客户类型
		registerUserDTO.setIsNeedActivate("N");
		registerUserDTO.setRegisterIp(clientIp);
		registerUserDTO.setTrdName(thirdName);
		registerUserDTO.setPicUrl(thirdPortraitURL);
		
		return registerUserDTO;
	}
	
	/** 
	 * 
	 * 组长的用户登录名称
	 * 
	 * @param partnerId 用户联合登陆的唯一标识
	 * @param loginName 名称
	 * @param userSource  用户联合登陆来源
	 * @return logonName
	 */
	private String assembleLogonName(String partnerId, String loginName, String userSource) {
		String name;
		
		if (GlobalConstants.TENCENT_QQ.equals(userSource)) {
			name = partnerId + "@qq";
		} else if (GlobalConstants.ALIPAY.equals(userSource)) {
			name = loginName + getNineBitsRandom() + "@alipay";
		} else if (GlobalConstants.FANLI_51.equals(userSource)) {
			name = loginName + "@51fanli";
		} else if (GlobalConstants.TENCENT_WECHAT.equals(userSource)) {
			name = partnerId + "@wechat";
		} else {
			name = getNineBitsRandom() + loginName;
		}
		
		return name;
	}
	
	/**
	 * @Description: 返回9位随机数字串
	 * @return String 9位随机数字串
	 */
	private String getNineBitsRandom() {
		Long round = Math.round(Math.random() * 1000000000);
		String str = round.toString();
		int lenth = str.length();
		int j = 0;
		
		if (lenth < 9) {
			j = 9 - lenth;
			
			for (int k = 0; k < j; k++) {
				str = str + 0;
			}
		}
		return str;
	}
	
	/**
	 * 返回登录名称
	 * @param user
	 * @param logonName
	 * @param userSource
	 * @return
	 */
	private String getLogonName(UserDO user, String logonName, String userSource) {
		String userName = "";
		
		if (GlobalConstants.TENCENT_QQ.equals(userSource)) {
			userName = logonName;
		} else {
			if (StringUtil.isNotEmpty(user.getPetName())) {
				userName = user.getPetName();
			} else if (StringUtil.isNotEmpty(user.getEmail())) {
				userName = user.getEmail();
			} else if (StringUtil.isNotEmpty(user.getMobile())) {
				userName = user.getMobile();
			} else {
				userName = user.getLoginName();
			}
		}
		return userName;
	}


	public LoginResVo loginWithVerifyCode(Map<String, Object> paraMap, boolean isUserExists) {
		String channelId = GlobalConstants.CHANNEL_ID;
		String logoutTime = GlobalConstants.ONLINE_EXPIRED_TIME; // 最大时间336小时L
		String loginChannel = (String) paraMap.get("loginChannel");
		String clientIp = paraMap.get(GlobalConstants.KEY_REMOTE_IP).toString();
		String deviceId = (String) paraMap.get("deviceId");		//设备ID
		String sysName = (String) paraMap.get("sysName");		//系统名称
		String sysVersion = (String) paraMap.get("sysVersion");	//系统版本
		String userId = "";
		if(isUserExists) {
			//用户已注册
			userId = paraMap.get("userId").toString();
		} else {
			String logonName = paraMap.get("logonName").toString();
			int regSource = (Integer) paraMap.get("regSource");
			// 构建注册对象
			RegisterUserDTO registerUserDTO = new RegisterUserDTO();
			registerUserDTO.setMobile(logonName);
			registerUserDTO.setLogonName(assembleLogonName("",logonName,"")); // 登录名
			registerUserDTO.setPassword(""); // 联合登陆默认密码为空
			registerUserDTO.setChannelId(Integer.parseInt(channelId)); // 用户所在渠道
			registerUserDTO.setRegisterType(TypeEnum.RegisterType.PETNAME.getKey()); // 注册类型
			registerUserDTO.setCustType(TypeEnum.CustType.XIU.getKey()); // 客户类型
			registerUserDTO.setIsNeedActivate("N");
			registerUserDTO.setRegisterIp(clientIp);
			registerUserDTO.setUserSource(regSource);	//注册来源
			
			Result result = eiuucManager.registerUser(registerUserDTO);
			userId = result.getData().toString();
			
			if (StringUtils.isEmpty(userId)) {
				throw ExceptionFactory.buildBusinessException(GlobalConstants.RET_CODE_LOGON_FAILED, "自动注册用户失败.");
			}
			
			UserOperateLogInfoDTO userOperateLogInfoDTO = new UserOperateLogInfoDTO();
			userOperateLogInfoDTO.setDeviceNum(deviceId);
			userOperateLogInfoDTO.setOperAddr(clientIp);
			userOperateLogInfoDTO.setSysName(sysName);
			userOperateLogInfoDTO.setSysNum(sysVersion);
			
			//获取注册后的用户信息，然后更新，更新手机认证标识
			ModifyUserAuthenDTO modifyUserAuthenDTO = new ModifyUserAuthenDTO();
			modifyUserAuthenDTO.setUserId(Long.parseLong(userId));
			modifyUserAuthenDTO.setAuthenType("1");
			modifyUserAuthenDTO.setAuthenValue(logonName);
			modifyUserAuthenDTO.setIpAddr(clientIp);
			
			userOperateLogInfoDTO.setModifyUserAuthenDTO(modifyUserAuthenDTO);
			eiuucManager.modifyUserAuthen(userOperateLogInfoDTO);
		}
		
		UserDO user = eissoManager.proxyLogin(userId, channelId, clientIp, logoutTime, loginChannel);
		// 登录成功
		LoginResVo loginResVo = new LoginResVo();
		loginResVo.setRetCode(GlobalConstants.RET_CODE_SUCESSS);
		loginResVo.setTokenId(user.getTokenId());
		loginResVo.setUserId(user.getUserId());
		loginResVo.setLogonName(user.getLoginName());
		return loginResVo;
	}


	@Override
	public UnionInfoDTO getUnionInfoDTOByUserId(Long userId, String userSource) {
		int channelId = getPartnerChannelId(userSource);
		return eiuucManager.getUnionInfoDTOByUserId(userId, channelId);
	}


	@Override
	public List<BindUserInfo> getBindUserInfoListByUserId(Long userId) {
		List<BindUserInfo> bindUserInfoList = new ArrayList<BindUserInfo>();
		List<UnionInfoDTO> unionInfoDTOList = eiuucManager.getUnionInfoDTOListByUserId(userId);
		for (UnionInfoDTO unionInfoDTO : unionInfoDTOList) {
			BindUserInfo bindUserInfo = new BindUserInfo();
			bindUserInfo.setExtendInfo(unionInfoDTO.getExtendInfo());
			bindUserInfo.setPartnerId(unionInfoDTO.getPartnerId());
			bindUserInfo.setUnionId(unionInfoDTO.getUnionId());
			bindUserInfo.setUserId(unionInfoDTO.getUserId());
			String userSource=getUserSource(unionInfoDTO.getPartnerChannelId());
			bindUserInfo.setUserSource(userSource);
			
			try {
				bindUserInfo.setThirdName(unionInfoDTO.getTrdName());
			} catch (Exception e) {
				logger.error("UUC UnionInfoDTO.getTrdName()异常，该字段没找到 ");
			}
			if(userSource!=null){
				bindUserInfoList.add(bindUserInfo);
			}
		}
		return bindUserInfoList;
	}
	
	@Override
	public List<UnionInfoDTO> getUnionInfoByCondition(UnionInfoDTO unionInfoDTO) {
		return eiuucManager.getUnionInfoByCondition(unionInfoDTO);
	}

	
	/**
	 * 根据联合登陆用户对用渠道编码，获取来源
	 * 
	 * @param userSource
	 * @return iPartnerChannelId
	 */
	private String getUserSource(Integer iPartnerChannelId) {
		String userSource = null;
		
		if (TypeEnum.PartnerChannelType.TENCENT_CAIBEI.getKey().equals(iPartnerChannelId)) {
			userSource = GlobalConstants.TENCENT_QQ;
		} else if (TypeEnum.PartnerChannelType.ALIPAY.getKey().equals(iPartnerChannelId)) {
			userSource = GlobalConstants.ALIPAY;
		} else if (TypeEnum.PartnerChannelType.NETEASE.getKey().equals(iPartnerChannelId)) {
			userSource = GlobalConstants.NETEASE;
		} else if (TypeEnum.PartnerChannelType.MOBILE139.getKey().equals(iPartnerChannelId)) {
			userSource = GlobalConstants.MOBILE139;
		} else if (TypeEnum.PartnerChannelType.SINA_WEIBO_XIU.getKey().equals(iPartnerChannelId)) {
			userSource = GlobalConstants.SINA_WEIBO;
		} else if (GlobalConstants.PARTNER_CHANNEL_WECHAT.equals(iPartnerChannelId)) {
			userSource = GlobalConstants.TENCENT_WECHAT;
		}
		
		return userSource;
	}
	@Override
	public boolean bindXiuUserToBaiduPushDevice(Map<String, Object> params) {
		DeviceInfoDTO deviceInfoDTO = new DeviceInfoDTO();
		deviceInfoDTO.setUserId((Long)params.get("userId"));
		deviceInfoDTO.setDeviceId((String)params.get("deviceId"));
		deviceInfoDTO.setLoginChannelId((String)params.get("loginChannel"));
		deviceInfoDTO.setVersion((String)params.get("appVersion"));
		deviceInfoDTO.setBaiduChannelId((String)params.get("baiduChannelId"));
		deviceInfoDTO.setBaiduUserId((String)params.get("baiduUserId"));
		deviceInfoDTO.setBaiduDeviceType((String)params.get("baiduDeviceType"));
		
		Result result =  eiuucManager.bindXiuUserToBaiduPushDevice(deviceInfoDTO);
		return  "1".equals(result.isSuccess());
	}

	/**
	 * 获取手机验证码
	 */
	public String getMobileVerifyCode(String mobile) {
		String verifyCode = loginDao.getMobileVerifyCode(mobile);
		return verifyCode;
	}
}
