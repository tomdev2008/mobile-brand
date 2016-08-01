package com.xiu.mobile.simple.service.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.common.lang.StringUtil;
import com.xiu.mobile.core.exception.EIRuntimeException;
import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.simple.common.util.AESCipher;
import com.xiu.mobile.simple.constants.ErrConstants;
import com.xiu.mobile.simple.constants.GlobalConstants;
import com.xiu.mobile.simple.ei.EISSOManager;
import com.xiu.mobile.simple.ei.EIUUCManager;
import com.xiu.mobile.simple.model.LoginResVo;
import com.xiu.mobile.simple.service.ILoginService;
import com.xiu.sso.server.dto.UserDO;
import com.xiu.uuc.facade.dto.RegisterUserDTO;
import com.xiu.uuc.facade.dto.Result;
import com.xiu.uuc.facade.util.TypeEnum;

@Service("loginService")
public class LoginServiceImpl implements ILoginService {

	@Autowired
	private EISSOManager eissoManager;
	@Autowired
	private EIUUCManager eiuucManager;
	
	@Override
	public LoginResVo login(Map<String, Object> params) {
		String logonName = (String) params.get("logonName");
		String password = (String) params.get("password");
		String ip = (String) params.get(GlobalConstants.KEY_REMOTE_IP);
		String channel = GlobalConstants.CHANNEL_ID;
		String logoutTime = GlobalConstants.ONLINE_EXPIRED_TIME; // 最大时间336小时
		String loginChannel=(String) params.get("loginChannel");
		
		// 解密密码
		password = this.decodePassword(password); 
		
		UserDO user = eissoManager.logon(logonName, password, channel, ip, logoutTime,loginChannel);
		
		LoginResVo loginResVo = new LoginResVo();
		loginResVo.setRetCode(GlobalConstants.RET_CODE_SUCESSS);
		loginResVo.setTokenId(user.getTokenId());
		loginResVo.setUserId(user.getUserId());
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
	public LoginResVo submitFederateLogin(Map<String, Object> params) {
		// 获取联合登陆渠道类型 ：TENCENT(QQ)、ALIPAY(支付宝)、SINA_WEIBO(新浪微博)等
		String userSource = (String) params.get("userSource");
		// 联合登录唯一标识
		String partnerId = (String) params.get("partnerId");
		String logonName = String.valueOf(params.get("logonName"));
		String clientIp = String.valueOf(params.get(GlobalConstants.KEY_REMOTE_IP));
		String channelId = GlobalConstants.CHANNEL_ID;
		String logoutTime = GlobalConstants.ONLINE_EXPIRED_TIME; // 最大时间336小时
		
		// 联合登陆渠道ID
		Integer iPartnerChannelId = getPartnerChannelId(userSource);
		
		Result resultExist = eiuucManager.isLogonNameExist(partnerId, iPartnerChannelId);
		
		String userId = resultExist.getData() == null ? null : resultExist.getData().toString();
		if (StringUtils.isEmpty(userId)) { 
			
			// 构建注册对象RegisterUserDTO
			RegisterUserDTO registerUserDTO = buildRegisterUserDTO(partnerId, 
					logonName, clientIp, iPartnerChannelId, userSource);
			
			Result result = eiuucManager.registerUser(registerUserDTO);
			userId = result.getData().toString();
			
			if (StringUtils.isEmpty(userId)) {
				throw ExceptionFactory.buildBusinessException(
						GlobalConstants.RET_CODE_LOGON_FAILED, "自动注册用户失败.");
			}
		}
		
		UserDO user = eissoManager.proxyLogin(userId, channelId, clientIp, logoutTime);
		// 登录成功
		LoginResVo loginResVo = new LoginResVo();
		loginResVo.setRetCode(GlobalConstants.RET_CODE_SUCESSS);
		loginResVo.setTokenId(user.getTokenId());
		loginResVo.setUserId(user.getUserId());
		loginResVo.setLogonName(getLogonName(user, logonName, userSource));
		return loginResVo;
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
			String clientIp, Integer iPartnerChannelId, String userSource) {
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
	
}
