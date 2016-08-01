package com.xiu.mobile.wechat.web.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.xiu.mobile.wechat.web.constants.WebContants;
import com.xiu.mobile.wechat.web.service.ILoginService;
import com.xiu.sso.server.SsoServer;
import com.xiu.sso.server.dto.UserDO;
import com.xiu.uuc.facade.UserManageFacade;
import com.xiu.uuc.facade.dto.RegisterUserDTO;
import com.xiu.uuc.facade.dto.Result;
import com.xiu.uuc.facade.dto.UserBaseDTO;
import com.xiu.uuc.facade.util.TypeEnum;

@Service("loginService")
public class LoginServiceImpl implements ILoginService {
	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Value("${logout.time}")
	private String logoutTime;

	@Value("${logon.channel}")
	private String logonChannel;

	@Resource(name = "userManageFacade")
	private UserManageFacade userManageFacade;

	@Resource(name = "ssoServer")
	private SsoServer ssoServer;

	@Override
	public boolean checkOnlineStatus(String tokenId, String channelId, String clientIp) {
		Assert.notNull(tokenId);
		Assert.notNull(clientIp);
		UserDO userDO = null;
		try {
			userDO = ssoServer.checkOnlineState(tokenId, channelId, clientIp);
		} catch (Exception e) {
			logger.error("检查登陆状态时发生异常.tokenId:{},channelId:{},clientIp:{}", tokenId, channelId, clientIp, e);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("检查用户登录状态(ssoServer.checkOnlineState)---------BEGIN-----------");
			logger.debug("tokenId: {}", tokenId);
			logger.debug("channelId: {}", channelId);
			logger.debug("clientIp: {}", clientIp);
			logger.debug("userDO : {}", ToStringBuilder.reflectionToString(userDO));
			logger.debug("检查用户登录状态(ssoServer.checkOnlineState)----------END------------");
		}
		return userDO != null && userDO.getIsSuccess();
	}

	@Override
	public UserDO xiuLogin(String logonName, String password, String channelId, String clientIp) {
		Assert.notNull(logonName);
		Assert.notNull(password);
		Assert.notNull(channelId);
		Assert.notNull(clientIp);

		UserDO userDO = null;
		try {
			userDO = ssoServer.login(logonName, password, channelId, clientIp, logoutTime, logonChannel);
		} catch (Exception e) {
			logger.error("走秀账户登录时发生异常.logonName:{},password:{},channelId:{},clientIp:{}", logonName, password, channelId, clientIp, e);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("走秀账户登录结果(ssoServer.login)---------BEGIN-----------");
			logger.debug("userId: {}", logonName);
			logger.debug("password: {}", password);
			logger.debug("channelId: {}", channelId);
			logger.debug("clientIp: {}", clientIp);
			logger.debug("userDO : {}", ToStringBuilder.reflectionToString(userDO));
			logger.debug("走秀账户登录结果(ssoServer.login)----------END------------");
		}
		return userDO;
	}

	@Override
	public UserDO unionLogin(String openId, String unionId, String nickname, String clientIp) {
		logger.info("联合登录请求参数：openId：" + openId + ", unionId:" + unionId + ", nickName:" + nickname + ", ip:" + clientIp);
		Result uucResult = userManageFacade.isLogonNameExist(unionId, TypeEnum.PartnerChannelType.WECHAT.getKey());
		if (null == uucResult || WebContants.FAILED.equals(uucResult.getSuccess())) {
			logger.error("检查用户名是否已注册失败.uucResutl:{}", uucResult);
			return null;
		}
		Long userId = (Long) uucResult.getData();

		// 微信openId数据迁移，为了兼容旧数据，当unionId用户不存在时，需要再次使用openId多判断一次。
		if (null == userId || 0L == userId) {
			uucResult = userManageFacade.isLogonNameExist(openId, TypeEnum.PartnerChannelType.WECHAT.getKey());
			userId = (Long) uucResult.getData();
			if (userId != null && 0L != userId) {
				try {
					// 当用户是openid登录时，需要把openid更换为unionId。
					UserBaseDTO baseDTO = (UserBaseDTO) userManageFacade.getUserBasicInfoByUserId(userId).getData();
					baseDTO.setPartnerId(unionId);
					userManageFacade.modifyUserBaseInfo(baseDTO);
				} catch (Exception e) {// 屏蔽异常信息
					logger.error("微信联合登陆保存union_id原值时发生异常.union_id:" + unionId, e);
				}
			}
		}

		if (null == userId) {
			Integer partnerChannelId = TypeEnum.PartnerChannelType.WECHAT.getKey();
			// 构建注册对象RegisterUserDTO
			RegisterUserDTO registerUserDTO = buildRegisterUserDTO(unionId, clientIp, partnerChannelId);
			Result result = userManageFacade.registerUser(registerUserDTO);
			if (null == result || WebContants.FAILED.equals(result.getSuccess())) {
				logger.error("微信联合登陆注册新用户失败----------BEGIN--------------");
				logger.error("(参数)unionId: {}", unionId);
				logger.error("(参数)nickname: {}", nickname);
				logger.error("(参数)clientIp: {}", clientIp);
				logger.error("(参数)partnerChannelId: {}", TypeEnum.PartnerChannelType.WECHAT.getKey());
				logger.error("(返回结果)result:{}", result);
				logger.error("微信联合登陆注册新用户失败-----------END---------------");
				return null;
			}
			userId = (Long) result.getData();
		}
		UserDO userDO = null;
		try {
			userDO = proxyLogin(userId, WebContants.CHANNEL_ID, clientIp);
		} catch (Exception e) {
			logger.error("微信联合登陆调用SSO进行登录时发生异常。", e);
		}
		if (null == userDO || !userDO.getIsSuccess()) {
			logger.error("微信联合登陆调用SSO进行登录时返回失败----------BEGIN--------------");
			logger.error("(参数)userId: {}", userId);
			logger.error("(参数)channelId: {}", WebContants.CHANNEL_ID);
			logger.error("(参数)clientIp: {}", clientIp);
			logger.error("(返回结果)userDO:{} ", ToStringBuilder.reflectionToString(userDO));
			logger.error("微信联合登陆调用SSO进行登录时返回失败-----------END---------------");
		}
		return userDO;
	}

	@Override
	public UserDO proxyLogin(Long userId, String channelId, String clientIp) {
		Assert.notNull(userId);
		Assert.notNull(channelId);
		Assert.notNull(clientIp);

		UserDO userDO = null;
		try {
			userDO = ssoServer.proxyLogin(userId.intValue(), channelId, clientIp, logoutTime);
		} catch (Exception e) {
			logger.error("检查登陆状态时发生异常.userId:{},channelId:{},clientIp:{}", userId, channelId, clientIp, e);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("代客登录成功(ssoServer.proxyLogin)---------BEGIN-----------");
			logger.debug("userId: {}", userId);
			logger.debug("channelId: {}", channelId);
			logger.debug("clientIp: {}", clientIp);
			logger.debug("userDO : {}", ToStringBuilder.reflectionToString(userDO));
			logger.debug("代客登录成功(ssoServer.proxyLogin)----------END------------");
		}
		return userDO;
	}

	/**
	 * @Description: 构建注册对象RegisterUserDTO
	 * @param partnerId
	 * @param xiuLoginName
	 * @param registerIp
	 * @param partnerChannelId
	 * @param flag
	 * @return RegisterUserDTO
	 */
	private RegisterUserDTO buildRegisterUserDTO(String partnerId, String registerIp, Integer partnerChannelId) {
		// 构建注册对象
		RegisterUserDTO registerUserDTO = new RegisterUserDTO();
		// 登录名,此处与union保持一致
		String logonName = partnerId + "@wechat";
		registerUserDTO.setLogonName(logonName);
		// 默认密码
		registerUserDTO.setPassword("");
		// 用户所在渠道
		registerUserDTO.setChannelId(11);
		// 注册类型
		registerUserDTO.setRegisterType(TypeEnum.RegisterType.PETNAME.getKey());
		// 用户ID
		registerUserDTO.setPartnerId(partnerId);
		// 来自那个渠道
		registerUserDTO.setPartnerChannelId(partnerChannelId);
		// 客户类型
		registerUserDTO.setCustType(TypeEnum.CustType.PARTNER.getKey());
		registerUserDTO.setIsNeedActivate("N");
		registerUserDTO.setRegisterIp(registerIp);
		// 用户来源
		String registerSource = TypeEnum.PartnerChannelType.getList().get(partnerChannelId).getValue();
		Integer userSource = TypeEnum.UserRegistSource.XIU.getKey();
		if (StringUtils.isNotBlank(registerSource)) {
			userSource = TypeEnum.UserRegistSource.getList().get(registerSource).getKey();
		}
		registerUserDTO.setUserSource(userSource);
		return registerUserDTO;
	}
}
