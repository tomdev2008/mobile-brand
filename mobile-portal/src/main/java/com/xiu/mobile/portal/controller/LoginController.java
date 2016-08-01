package com.xiu.mobile.portal.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xiu.common.lang.StringUtil;
import com.xiu.mobile.core.exception.BusinessException;
import com.xiu.mobile.core.exception.EIBusinessException;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.constants.LoginRegConstant;
import com.xiu.mobile.portal.common.constants.MobileCommonConstant;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.AESCipher;
import com.xiu.mobile.portal.common.util.AppVersionUtils;
import com.xiu.mobile.portal.common.util.CookieUtil;
import com.xiu.mobile.portal.common.util.EncryptUtils;
import com.xiu.mobile.portal.common.util.SMSTemplateUtil;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.common.util.Tools;
import com.xiu.mobile.portal.constants.GlobalConstants;
import com.xiu.mobile.portal.facade.utils.DateUtil;
import com.xiu.mobile.portal.facade.utils.HttpUtil;
import com.xiu.mobile.portal.model.AppVo;
import com.xiu.mobile.portal.model.BindUserInfo;
import com.xiu.mobile.portal.model.LoginResVo;
import com.xiu.mobile.portal.model.MessageCode;
import com.xiu.mobile.portal.model.RecommendRegBo;
import com.xiu.mobile.portal.model.UserInfo;
import com.xiu.mobile.portal.service.ILoginService;
import com.xiu.mobile.portal.service.IMessageCodeService;
import com.xiu.mobile.portal.service.IRegisterService;
import com.xiu.mobile.portal.service.ISMSService;
import com.xiu.mobile.portal.service.IUserService;
import com.xiu.show.core.common.util.ObjectUtil;
import com.xiu.uuc.facade.dto.ModifyUserAuthenDTO;
import com.xiu.uuc.facade.dto.UnionInfoDTO;
import com.xiu.uuc.facade.dto.UserBaseDTO;
import com.xiu.uuc.facade.dto.UserDetailDTO;
import com.xiu.uuc.facade.dto.UserOperateLogInfoDTO;

/**
 * <p>
 * ************************************************************** 
 * @Description: 登陆控制器
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-15 上午10:50:20 
 * ***************************************************************
 * </p>
 */
@Controller
@RequestMapping("/loginReg")
public class LoginController extends BaseController {
	
	private final long DISSECOND = LoginRegConstant.SMS_DIS_SECOND;// 验证码失效时间
	private final String prefixURL = "http://6.xiustatic.com/user_headphoto";
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private ILoginService loginService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IMessageCodeService messageCodeService;
	@Autowired
	private IRegisterService registerService;
	@Autowired
	private ISMSService iSMSService;

	/**
	 * 登陆
	 * 
	 * @param request
	 * @return JSON
	 */
	@ResponseBody
	@RequestMapping(value = "requestSubmitLogin", produces = "text/html;charset=UTF-8")
	public String login(String jsoncallback,HttpServletRequest request, HttpServletResponse response) {
		logger.info("用户登陆参数params="+request.getQueryString());
//		String regType = request.getParameter("memberVo.regType");
		String logonName = request.getParameter("memberVo.logonName");
		String encryptFlag = request.getParameter("encryptFlag");
		String password = request.getParameter("memberVo.password");
//		String isRemember = request.getParameter("memberVo.isRemember");
		//登录渠道(pc-web:官网;mobile-wap:手机wap;android-app android应用;ios-app IOS应用)
		String loginChannel=request.getParameter("loginChannel");
		if("".equals(loginChannel)||null==loginChannel){
			loginChannel="mobile-wap";
		}
		Map<String, Object> model = new HashMap<String, Object>();
		
		if(StringUtils.isEmpty(logonName) 
				|| StringUtils.isEmpty(password)) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.ParamsError.getErrorCode());
			model.put("errorMsg", ErrorCode.ParamsError.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, model);
		}
		try {
			//解码
			logonName = URLDecoder.decode(logonName, "UTF-8");
			// 密码不为空，encryptFlag为Y
			if(!StringUtils.isEmpty(password) && "Y".equals(encryptFlag)){
				// password解密(AES)
				String aesKey=EncryptUtils.getAESKeyShare();
				//password=EncryptUtils.Encrypt(password, aesKey);
				password=EncryptUtils.decryptPassByAES(password,aesKey);
			}
			
			password = new AESCipher(XiuConstant.SAFE_CODE).encrypt(password);
			Map<String, Object> pmap = new HashMap<String, Object>();
			pmap.put("logonName", logonName);
			pmap.put("password", password);
			pmap.putAll(getDeviceParams(request));// 设备信息
			// 添加IP信息，登录，获取用户状态等接口需要。
			String remoteIp = HttpUtil.getRemoteIpAddr(request);
			pmap.put(GlobalConstants.KEY_REMOTE_IP, remoteIp);
			pmap.put("loginChannel", loginChannel);
			LoginResVo lrv = loginService.login(pmap);
			
			// 登录成功后，将登录后的用户信息放入到session中
			//lrv.setLogonName(logonName);
			SessionUtil.addLoginCookie(response, lrv);
			SessionUtil.addLoginTypeCookie(response, XiuConstant.LOGIN_TYPE_ACCOUNT);//标示为账号登录

			//判断是否是手机号登录，如果不是，则检测是否绑定手机
			Pattern p = Pattern.compile("^1\\d{10}$");
			Matcher m = p.matcher(logonName);  
			if(!m.matches()) {
				//如果不是手机
				boolean isMobileBinding = userService.isMobileBinding(lrv.getUserId());
				model.put("mobileBindStatus", isMobileBinding);
			} else {
				//如果是手机号登录，默认是绑定状态
				model.put("mobileBindStatus", true);
			}
			
			model.put("result", true);
			model.put("errorCode", ErrorCode.Success.getErrorCode());
			model.put("errorMsg", ErrorCode.Success.getErrorMsg());
		} 
		catch (EIBusinessException e) {
			model.put("result", false);
			model.put("retCode", GlobalConstants.RET_CODE_OTHER_MSG);
			String errorCode = e.getExtErrCode();
			String errorMsg = e.getExtMessage();
			if(errorCode.indexOf("-") > -1) {
				if("202".equals(errorCode.substring(0, errorCode.indexOf("-")))) {
					//如果是密码错误
					String pwdErrorCount = errorCode.substring(errorCode.indexOf("-") + 1, errorCode.length());
					if("3".equals(pwdErrorCount)) {
						//如果是第3次密码错误
						errorCode = String.valueOf(ErrorCode.ThirdPasswordError.getErrorCode());
					} else {
						errorCode = "202";
					}
				}
			}
			model.put("errorMsg", errorMsg);
			model.put("errorCode", errorCode);
			model.put("wurl", request.getContextPath() + "/loginReg/toLogin.shtml");// 登录失败后
			
			logger.error("requestSubmitLogin EIBusinessException,LoginName is : " +logonName);
			logger.error("RequestSubmitLogin error：{errorCode：" + e.getErrCode()
					+ ",errorMsg：" + e.getExtMessage() + "}"); // 跳转到登录页面
		}
		catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("requestSubmitLogin error,LoginName is : " +logonName);
			logger.error("RequestSubmitLogin system error{}", e);
		} 
		
		return JsonUtils.bean2jsonP(jsoncallback, model);
	}
	
	/***
	 * 联合登录授权后查询发现未绑定走秀帐号，用户选择 已有走秀帐号绑定，会执行走秀帐号登录操作并绑定
	 * @param jsoncallback
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/federateLoginAndBind", produces = "text/html;charset=UTF-8")
	public String federateBindXiuAndLogin(String jsoncallback,HttpServletRequest request, HttpServletResponse response) {
		logger.info("用户登陆参数params=" + request.getQueryString());
		String userSource = request.getParameter("userSource");
		// QQ和微信的都是openId, 支付宝的是user_id，和userSource一起在走秀里标识出唯一的来自第三方的用户
		String partnerId = request.getParameter("partnerId");
		String weChatOpenId = request.getParameter("weChatOpenId");
		String logonName = request.getParameter("loginName");
		String password = request.getParameter("password");
		String thirdName = request.getParameter("thirdName");
		String thirdPortraitURL = request.getParameter("thirdPortraitURL");
		
		String deviceId = request.getParameter("deviceId");		//设备ID
		String sysName = request.getParameter("sysName");		//系统名称
		String sysVersion = request.getParameter("sysVersion");	//系统版本
		try {
			if (null != thirdName) {
				thirdName = URLDecoder.decode(thirdName, "UTF-8");
			}
			if (null != thirdPortraitURL) {
				thirdPortraitURL = URLDecoder.decode(thirdPortraitURL, "UTF-8");
			}
		} catch (UnsupportedEncodingException e1) {
			logger.error("decode第三方原名和头像异常：");
		}
		// String isRemember = request.getParameter("memberVo.isRemember");
		// 登录渠道(pc-web:官网;mobile-wap:手机wap;android-app android应用;ios-app IOS应用)
		String loginChannel = request.getParameter("loginChannel");
		if ("".equals(loginChannel) || null == loginChannel) {
			loginChannel = "mobile-wap";
		}
		Map<String, Object> model = new HashMap<String, Object>();

		if (StringUtils.isEmpty(logonName) || StringUtils.isEmpty(password) || StringUtils.isEmpty(partnerId)) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.ParamsError.getErrorCode());
			model.put("errorMsg", ErrorCode.ParamsError.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, model);
		}
		try {
			// 解码
			logonName = URLDecoder.decode(logonName, "UTF-8");
			// password解密(AES)
			String aesKey = EncryptUtils.getAESKeyShare();
			password = EncryptUtils.decryptPassByAES(password, aesKey);
			password = new AESCipher(XiuConstant.SAFE_CODE).encrypt(password);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("logonName", logonName);
			params.put("password", password);
			params.put("partnerId", partnerId);
			params.put("weChatOpenId", weChatOpenId);
			params.put("thirdName", thirdName);
			params.put("thirdPortraitURL", thirdPortraitURL);
			// 联合登陆用户来源 ：tencent_qq(QQ),alipay(支付宝),sina_weibo(新浪微博),netEase(网易) ,139(139邮箱),51fanli(返利网)
			params.put("userSource", userSource);
			params.putAll(getDeviceParams(request));// 设备信息
			// 添加IP信息，登录，获取用户状态等接口需要。
			params.put("loginChannel", loginChannel);
			params.put("deviceId", deviceId);
			params.put("sysName", sysName);
			params.put("sysVersion", sysVersion);
			
			// 获取用户绑定到的用户信息
			String channelId = GlobalConstants.CHANNEL_ID;
			UserDetailDTO user = userService.getUserBasicInfoByLogonName(logonName, Integer.parseInt(channelId));
			if (user != null) {//用户存在
				Long userId = user.getUserId();
				// 获取要绑定账号联合用户信息
				UnionInfoDTO unionInfoDTO = loginService.getUnionInfoDTOByUserId(userId, userSource);
				if (unionInfoDTO != null) {
					// 如果用户联合账号信息和当前绑定不一致  则提示用户已绑定账号
					//这个判断应该是多余的，已绑定关联的还可以再次关联？
					if (!unionInfoDTO.getPartnerId().equalsIgnoreCase(partnerId)) {
						model.put("result", false);
						model.put("errorCode", ErrorCode.UserBindError.getErrorCode());
						String userName= user.getPetName();
						if(userName!=null&&!userName.equals("")&&userName.indexOf("xiu_")!=0){
							userName="（"+userName+"）";
						}else{
							userName="";
						}
						String msg="是否解除与原账号"+userName+"的绑定，并绑定到当前账号上？";
						model.put("errorMsg", msg);
//						model.put("errorMsg", "该走秀账号已绑定了".concat(getPartnerChannel(userSource)).concat("账号" + (null == unionInfoDTO.getTrdName()?"":unionInfoDTO.getTrdName()) +"，请解绑后再绑定"));
						return JsonUtils.bean2jsonP(jsoncallback, model);
					}
				}
			}else{//用户名不存在
				model.put("result", false);
				model.put("errorCode", ErrorCode.UserNotExists.getErrorCode());
				model.put("errorMsg", ErrorCode.UserNotExists.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, model);
			}
			//若登录不成功则抛异常了，不会再绑定
			LoginResVo lrv = loginService.login(params);
			params.put("userId", lrv.getUserId());
			// 绑定用户,已在里面setDefaultFerationUserInfo了
			boolean flag = loginService.bindUser(params);
			SessionUtil.addLoginTypeCookie(response, XiuConstant.LOGIN_TYPE_FEDERATE);//标示为第三方登录
			if (flag) {
				// 登录成功后，将登录后的用户信息放入到session中
				//lrv.setLogonName(logonName);
				SessionUtil.addLoginCookie(response, lrv);
				//修改用户昵称
				userService.updateUserPetName(lrv.getUserId(), thirdName);
				model.put("result", true);
				UserDetailDTO userDetailDTO = userService.getUserDetailDTOByUserId(Long.parseLong(lrv.getUserId()));
				model.put("userInfo", getUserInfo(userDetailDTO));
				model.put("isFirstChangeName", getStatus(userDetailDTO));
				model.put("errorCode", ErrorCode.Success.getErrorCode());
				model.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				model.put("result", false);
				model.put("errorCode", ErrorCode.SystemError.getErrorCode());
				model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			}
		} catch (EIBusinessException e) {
			model.put("result", false);
			model.put("retCode", GlobalConstants.RET_CODE_OTHER_MSG);
			model.put("errorMsg", e.getExtMessage());
			model.put("errorCode", e.getExtErrCode());
			model.put("wurl", request.getContextPath() + "/loginReg/toLogin.shtml");// 登录失败后

			logger.error("requestSubmitLogin EIBusinessException,LoginName is : " + logonName);
			logger.error("RequestSubmitLogin error：{errorCode：" + e.getErrCode() + ",errorMsg：" + e.getExtMessage() + "}"); // 跳转到登录页面
		} catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("requestSubmitLogin error,LoginName is : " + logonName);
			logger.error("RequestSubmitLogin system error{}", e);
		}

		return JsonUtils.bean2jsonP(jsoncallback, model);
	}
	
	/***
	 * 登录用户绑定第三方联盟信息
	 * @param jsoncallback
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/federateBindUser", produces = "text/html;charset=UTF-8")
	public String xiuBindFederateUser(String jsoncallback,HttpServletRequest request, HttpServletResponse response) {
		logger.info("登录用户绑定第三方联盟信息参数params="+request.getQueryString());
		Map<String, Object> result = new HashMap<String, Object>();
		String userSource = request.getParameter("userSource");
		// QQ和微信的都是openId, 支付宝的是user_id，和userSource一起在走秀里标识出唯一的来自第三方的用户
		String partnerId = request.getParameter("partnerId");
		String weChatOpenId = request.getParameter("weChatOpenId");
		// 登录渠道(pc-web:官网;mobile-wap:手机wap;android-app android应用;ios-app IOS应用)
		String loginChannel = request.getParameter("loginChannel");
		String thirdName = request.getParameter("thirdName");
		String thirdPortraitURL = request.getParameter("thirdPortraitURL");
		
		String deviceId = request.getParameter("deviceId");		//设备ID
		String sysName = request.getParameter("sysName");		//系统名称
		String sysVersion = request.getParameter("sysVersion");	//系统版本
		try {
			if (null != thirdName) {
				thirdName = URLDecoder.decode(thirdName,"UTF-8");
			}
			if (null != thirdPortraitURL) {
				thirdPortraitURL = URLDecoder.decode(thirdPortraitURL, "UTF-8");
			}
		} catch (UnsupportedEncodingException e1) {
			logger.error("decode第三方原名和头像异常：");
		}
		
		if ("".equals(loginChannel) || null == loginChannel) {
			loginChannel = "mobile-wap";
		}

		if (StringUtils.isEmpty(userSource) || StringUtils.isEmpty(partnerId)) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.ParamsError.getErrorCode());
			result.put("errorMsg", ErrorCode.ParamsError.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, result);
		}
		try {
			String paramsId = partnerId;
			// 第三方条件查询  partnerId 支付宝和微信进行特殊处理
			if (null != userSource && "alipay".equals(userSource)) {
				// 如果没有增加@alipay后缀 则添加
				if (!paramsId.contains("@alipay")) {
					paramsId = paramsId.concat("@alipay");
				}
			}
			UnionInfoDTO unionInfoDTO = new UnionInfoDTO();
			unionInfoDTO.setPartnerId(paramsId);
			List<UnionInfoDTO> dtoList = loginService.getUnionInfoByCondition(unionInfoDTO);
			if (dtoList != null && dtoList.size() > 0) {
				// 如果用户联合账号信息和当前绑定不一致  则提示用户已绑定账号
				if (dtoList.get(0).getUserId().longValue() != Long.parseLong(SessionUtil.getUserId(request))) {
					result.put("result", false);
					result.put("errorCode", ErrorCode.UserBindError.getErrorCode());
					UserDetailDTO user = userService.getUserDetailDTOByUserId(dtoList.get(0).getUserId());
					String userName= user.getPetName();
					if(userName!=null&&!userName.equals("")&&userName.indexOf("xiu_")!=0){
						userName="（"+userName+"）";
					}else{
						userName="";
					}
					String msg="是否解除与原账号"+userName+"的绑定，并绑定到当前账号上？";
					result.put("errorMsg", msg);
					return JsonUtils.bean2jsonP(jsoncallback, result);
				}
			}
			
			Map<String, Object> params = new HashMap<String, Object>();
			String userId = SessionUtil.getUserId(request);
			params.put("partnerId", partnerId);
			params.put("weChatOpenId", weChatOpenId);
			// 联合登陆用户来源 ：tencent_qq(QQ),alipay(支付宝),sina_weibo(新浪微博),netEase(网易) ,139(139邮箱),51fanli(返利网)
			params.put("userSource", userSource);
			params.putAll(getDeviceParams(request));// 设备信息
			// 添加IP信息，登录，获取用户状态等接口需要。
			params.put("loginChannel", loginChannel);
			params.put("userId", userId);
			params.put("thirdName", thirdName);
			params.put("thirdPortraitURL", thirdPortraitURL);
			params.put("deviceId", deviceId);
			params.put("sysName", sysName);
			params.put("sysVersion", sysVersion);
			//里面已设置默认第三方原名和头像
			boolean flag = loginService.bindUser(params);
			//调用serviece的那个接口
			if (flag) {
				//修改用户昵称
				userService.updateUserPetName(userId, thirdName);
				result.put("result", true);
				result.put("errorCode", ErrorCode.Success.getErrorCode());
				result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				result.put("result", false);
				result.put("errorCode", ErrorCode.SystemError.getErrorCode());
				result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			}
		}catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", e.getExtErrCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("登录用户绑定第三方联盟信息信息{errorCode：" + e.getExtErrCode() + ", errorMsg：" + e.getExtMessage() + "}");
		}catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("登录用户绑定第三方联盟信息发生异常：", e);
		}

		logger.info("登录用户绑定第三方联盟信息返回结果：" + JSON.toJSONString(result));
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/**
	 * 绑定第三方账号，如果第三方已经绑定了其他账号，会解绑重新绑定当前账号
	 * @param jsoncallback
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/bindUser", produces = "text/html;charset=UTF-8")
	public String xiubindUser(String jsoncallback,HttpServletRequest request, HttpServletResponse response) {
		logger.info("登录用户绑定第三方账号信息参数params="+request.getQueryString());
		Map<String, Object> result = new HashMap<String, Object>();
		String userSource = request.getParameter("userSource");
		// QQ和微信的都是openId, 支付宝的是user_id，和userSource一起在走秀里标识出唯一的来自第三方的用户
		String partnerId = request.getParameter("partnerId");
		String weChatOpenId = request.getParameter("weChatOpenId");
		// 登录渠道(pc-web:官网;mobile-wap:手机wap;android-app android应用;ios-app IOS应用)
		String loginChannel = request.getParameter("loginChannel");
		String thirdName = request.getParameter("thirdName");
		String thirdPortraitURL = request.getParameter("thirdPortraitURL");
		String mobile = request.getParameter("mobile");
		
		String deviceId = request.getParameter("deviceId");		//设备ID
		String sysName = request.getParameter("sysName");		//系统名称
		String sysVersion = request.getParameter("sysVersion");	//系统版本
		try {
			if (null != thirdName) {
				thirdName = URLDecoder.decode(thirdName,"UTF-8");
			}
			if (null != thirdPortraitURL) {
				thirdPortraitURL = URLDecoder.decode(thirdPortraitURL, "UTF-8");
			}
		} catch (UnsupportedEncodingException e1) {
			logger.error("decode第三方原名和头像异常：");
		}
		
		if ("".equals(loginChannel) || null == loginChannel) {
			loginChannel = "mobile-wap";
		}

		if (StringUtils.isEmpty(userSource) || StringUtils.isEmpty(partnerId) || StringUtils.isEmpty(mobile)) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.ParamsError.getErrorCode());
			result.put("errorMsg", ErrorCode.ParamsError.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, result);
		}
		try {
			String paramsId = partnerId;
			// 第三方条件查询  partnerId 支付宝和微信进行特殊处理
			if (null != userSource && "alipay".equals(userSource)) {
				// 如果没有增加@alipay后缀 则添加
				if (!paramsId.contains("@alipay")) {
					paramsId = paramsId.concat("@alipay");
				}
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("partnerId", partnerId);
			params.put("weChatOpenId", weChatOpenId);
			// 联合登陆用户来源 ：tencent_qq(QQ),alipay(支付宝),sina_weibo(新浪微博),netEase(网易) ,139(139邮箱),51fanli(返利网)
			params.put("userSource", userSource);
			params.putAll(getDeviceParams(request));// 设备信息
			// 添加IP信息，登录，获取用户状态等接口需要。
			params.put("loginChannel", loginChannel);
			params.put("deviceId", deviceId);
			params.put("sysName", sysName);
			params.put("sysVersion", sysVersion);
			
			UnionInfoDTO unionInfoDTO = new UnionInfoDTO();
			unionInfoDTO.setPartnerId(paramsId);
			List<UnionInfoDTO> dtoList = loginService.getUnionInfoByCondition(unionInfoDTO);
			if (dtoList != null && dtoList.size() > 0) {
				// 如果用户联合账号信息和当前绑定不一致  则解绑
				if (dtoList.get(0).getUserId().longValue() != Long.parseLong(SessionUtil.getUserId(request))) {
					Long user_id = dtoList.get(0).getUserId();
					params.put("userId", String.valueOf(user_id));
					boolean flag = loginService.unBindUser(params);
					if(flag) {
						//解绑成功，发送短信
						UserDetailDTO userDetailDTO = userService.getUserDetailDTOByUserId(user_id);
						if(userDetailDTO != null) {
							//1.没有密码则生成密码返回，有则不显示密码
							String password = "";
							boolean isEmptyPassword = userService.isEmptyPassword(user_id);
							if(isEmptyPassword) {
								password = Tools.getRandomPassword();
							}
							
							//2.用户名显示顺序:手机、邮箱、用户名
							String account = getUserAccount(userDetailDTO);
							
							//3.发送短信
							String smsBody = "尊敬的走秀用户，您的第三方账号已与原账户解绑并绑定到当前账户，原账户可通过"
									+ account + "和密码"+password+"登录查看订单信息和余额信息，感谢您的支持！";
							logger.info("用户手机号码："+ mobile + ",信息内容：" + smsBody);
							boolean sendFlag = iSMSService.sendSMS(smsBody, mobile, "", mobile);
							logger.info("短信发送状态：sendFlag=" + sendFlag);
							logger.info("绑定第三方账号短信发送内容：smsBody=" + smsBody);
							
							if(isEmptyPassword && sendFlag) {
								//如果生成了密码，则修改用户的密码
								userService.resetUserPassword(user_id, password);
							}
						}
					} else {
						result.put("result", false);
						result.put("errorCode", ErrorCode.UserUnBindError.getErrorCode());
						result.put("errorMsg", ErrorCode.UserUnBindError.getErrorMsg());
						return JsonUtils.bean2jsonP(jsoncallback, result);
					}
				}
			}
			
			String userId = SessionUtil.getUserId(request);
			// 添加IP信息，登录，获取用户状态等接口需要。
			params.put("userId", userId);
			params.put("thirdName", thirdName);
			params.put("thirdPortraitURL", thirdPortraitURL);
			//里面已设置默认第三方原名和头像
			boolean flag = loginService.bindUser(params);
			//调用serviece的那个接口
			if (flag) {
				//修改用户昵称
				userService.updateUserPetName(userId, thirdName);
				result.put("result", true);
				result.put("errorCode", ErrorCode.Success.getErrorCode());
				result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				result.put("result", false);
				result.put("errorCode", ErrorCode.SystemError.getErrorCode());
				result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			}
		}catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", e.getExtErrCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("登录用户绑定第三方账号信息{errorCode：" + e.getExtErrCode() + ", errorMsg：" + e.getExtMessage() + "}");
		}catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("登录用户绑定第三方账号信息发生异常：", e);
		}

		logger.info("登录用户绑定第三方账号信息返回结果：" + JSON.toJSONString(result));
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	
	/***
	 * 用户解绑联合登陆信息
	 * @param jsoncallback
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/unBindUser", produces = "text/html;charset=UTF-8")
	public String xiuUnBindfederateUser(String jsoncallback,HttpServletRequest request, HttpServletResponse response) {
		logger.info("用户解绑联合登陆信息信息参数params="+request.getQueryString());
		Map<String, Object> result = new HashMap<String, Object>();
		String userSource = request.getParameter("userSource");
		// QQ和微信的都是openId, 支付宝的是user_id，和userSource一起在走秀里标识出唯一的来自第三方的用户
		String partnerId = request.getParameter("partnerId");
		String weChatOpenId = request.getParameter("weChatOpenId");
		// 登录渠道(pc-web:官网;mobile-wap:手机wap;android-app android应用;ios-app IOS应用)
		String loginChannel = request.getParameter("loginChannel");
		if ("".equals(loginChannel) || null == loginChannel) {
			loginChannel = "mobile-wap";
		}
		
		String deviceId = request.getParameter("deviceId");		//设备ID
		String sysName = request.getParameter("sysName");		//系统名称
		String sysVersion = request.getParameter("sysVersion");	//系统版本

		if (StringUtils.isEmpty(userSource) || StringUtils.isEmpty(partnerId)) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			result.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, result);
		}
		try {
			String userId = SessionUtil.getUserId(request);
			// 获取当前用户绑定联盟用户信息  如果是解绑最后一个账号是才判断密码为空
//			List<BindUserInfo> bindUserInfoList = loginService.getBindUserInfoListByUserId(Long.parseLong(userId));
//			if (bindUserInfoList.size() <= 1) {
//				boolean isEmptyPassword = userService.isEmptyPassword(Long.parseLong(userId));
//				if (isEmptyPassword) {
//					result.put("result", false);
//					result.put("errorCode", ErrorCode.UserPasswordIsEmpty.getErrorCode());
//					result.put("errorMsg", ErrorCode.UserPasswordIsEmpty.getErrorMsg());
//					return JsonUtils.bean2jsonP(jsoncallback, result);
//				}
//			}
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("partnerId", partnerId);
			params.put("weChatOpenId", weChatOpenId);
			// 联合登陆用户来源 ：tencent_qq(QQ),alipay(支付宝),sina_weibo(新浪微博),netEase(网易) ,139(139邮箱),51fanli(返利网)
			params.put("userSource", userSource);
			params.putAll(getDeviceParams(request));// 设备信息
			// 添加IP信息，登录，获取用户状态等接口需要。
			params.put("loginChannel", loginChannel);
			params.put("userId", userId);
			params.put("deviceId", deviceId);
			params.put("sysName", sysName);
			params.put("sysVersion", sysVersion);
			boolean flag = loginService.unBindUser(params);
			if (flag) {
				result.put("result", true);
				result.put("errorCode", ErrorCode.Success.getErrorCode());
				result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				result.put("result", false);
				result.put("errorCode", ErrorCode.SystemError.getErrorCode());
				result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			}
		}catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", e.getExtErrCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("用户解绑联合登陆{errorCode：" + e.getExtErrCode() + ", errorMsg：" + e.getExtMessage() + "}");
		}catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("用户解绑联合登陆信息发生异常：", e);
		}

		logger.info("用户解绑联合登陆信息返回结果：" + JSON.toJSONString(result));
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}

	
	/***
	 * 联合登录授权后查询发现未绑定走秀帐号，用户选择  注册走秀帐号并绑定
	 * @param jsoncallback
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/federateRegisterAndBind", produces = "text/html;charset=UTF-8")
	public String federateRegisterAndBindXiu(String jsoncallback,HttpServletRequest request, HttpServletResponse response) {
		logger.info("用户注册参数params="+request.getQueryString());
		// QQ和微信的都是openId, 支付宝的是user_id，和userSource一起在走秀里标识出唯一的来自第三方的用户
		String partnerId = request.getParameter("partnerId");
		String weChatOpenId = request.getParameter("weChatOpenId");
		//第三方登录名
		String logonName = request.getParameter("loginName");
		String password = request.getParameter("password");
		String regType = request.getParameter("regType");
		String thirdName = request.getParameter("thirdName");
		String thirdPortraitURL = request.getParameter("thirdPortraitURL");
		
		String deviceId = request.getParameter("deviceId");		//设备ID
		String remoteIp = HttpUtil.getRemoteIpAddr(request);	//IP地址
		String sysName = request.getParameter("sysName");		//系统名称
		String sysVersion = request.getParameter("sysVersion");	//系统版本
		try {
			if (null != thirdName) {
				thirdName = URLDecoder.decode(thirdName,"UTF-8");
			}
			if (null != thirdPortraitURL) {
				thirdPortraitURL = URLDecoder.decode(thirdPortraitURL, "UTF-8");
			}
		} catch (UnsupportedEncodingException e1) {
			logger.error("decode第三方原名和头像异常：");
		}
		
		int regSource = NumberUtils.toInt(request.getParameter("regSource"), 150);
		//添加用户注册来源标识
		String userSource=request.getParameter("userSource");
		String smsCode = request.getParameter("smsCode");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if(StringUtils.isEmpty(logonName) || StringUtils.isEmpty(password) || StringUtils.isEmpty(partnerId) || StringUtils.isEmpty(smsCode) ) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}

		try {
			
			// 短信验证码验证start
			MessageCode messageCode = messageCodeService.findMessageCodeByPhone(logonName);

			if (messageCode != null) {
				String oldSmsCode = messageCode.getMsgCode();
				String sendCodeTime = messageCode.getTimestamp();
				String timeFlag = messageCode.getTimeFlag();
				String contentFlag = messageCode.getContentFlag();

				// 不同的手机验证码不同
				if (!StringUtils.isEmpty(oldSmsCode) && !StringUtils.isEmpty(sendCodeTime) && timeFlag.equals("N") && contentFlag.equals("N")) {
					long dif = DateUtil.diffDateTime(new Date(), new Date(Long.valueOf(sendCodeTime)));

					// 时间差大于规定时间则验证码失效
					if (dif > this.DISSECOND) {
						// 时间失效
						messageCode.setTimeFlag("Y");
						messageCodeService.updateMessageCode(messageCode);
						resultMap.put("result", false);
						resultMap.put("errorCode", ErrorCode.VerifyCodeTimeOut.getErrorCode());
						resultMap.put("errorMsg", ErrorCode.VerifyCodeTimeOut.getErrorMsg());
						return JsonUtils.bean2jsonP(jsoncallback, resultMap);
					} else {
						// 验证验证码的正确性
						if (smsCode.equals(oldSmsCode)) {
							// 删除验证码
							messageCodeService.removeMessageCodeByPhone(logonName);
						} else {
							resultMap.put("result", false);
							resultMap.put("errorCode", ErrorCode.VerifyCodeFailed.getErrorCode());
							resultMap.put("errorMsg", "请输入正确的验证码");
							logger.info("校验验证码失败：{Tel: " + logonName + ", smsCode: " + smsCode + "}");
							return JsonUtils.bean2jsonP(jsoncallback, resultMap);
						}
					}
				}else {
					resultMap.put("result", false);
					resultMap.put("errorCode", ErrorCode.VerifyCodeFailed.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.VerifyCodeFailed.getErrorMsg());
					logger.error("checkVerifyCode==>验证码为空，没有找到验证码记录");
					return JsonUtils.bean2jsonP(jsoncallback, resultMap);
				}
			} else {
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.VerifyCodeFailed.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.VerifyCodeFailed.getErrorMsg());
				logger.error("checkVerifyCode==>验证码为空，没有找到验证码记录");
				return JsonUtils.bean2jsonP(jsoncallback, resultMap);
			}
			// 短信验证码验证end
			
			// password解密(AES)
			String aesKey = EncryptUtils.getAESKeyShare();
			// password=EncryptUtils.encryptByAES(password, aesKey);
			password = EncryptUtils.decryptPassByAES(password, aesKey);
				
			
			Map<String, Object> regParams = new HashMap<String, Object>();
			password = new AESCipher(XiuConstant.SAFE_CODE).encrypt(password);
			regParams.put("regType", regType);
			regParams.put("logonName", logonName);
			regParams.put("password", password);
			// 添加IP信息，登录，获取用户状态等接口需要。
			regParams.putAll(getDeviceParams(request));// 设备信息
			regParams.put("userSource", regSource);
			regParams.put("thirdName", thirdName);
			regParams.put("thirdPortraitURL", thirdPortraitURL);
			LoginResVo lrv = registerService.register(regParams);
			// 注册成功后账号绑定处理
			if (lrv != null) {
				// 用户绑定参数
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("partnerId", partnerId);
				params.put("weChatOpenId", weChatOpenId);
				// 联合登陆用户来源 ：tencent_qq(QQ),alipay(支付宝),sina_weibo(新浪微博),netEase(网易) ,139(139邮箱),51fanli(返利网)
				params.put("userSource", userSource);
				params.put("userId", lrv.getUserId());
				params.put("thirdName", thirdName);
				params.put(GlobalConstants.KEY_REMOTE_IP, remoteIp);
				params.put("deviceId", deviceId);
				params.put("sysName", sysName);
				params.put("sysVersion", sysVersion);
				// 绑定用户
				boolean flag = loginService.bindUser(params);
				SessionUtil.addLoginTypeCookie(response, XiuConstant.LOGIN_TYPE_FEDERATE);//标示为第三方登录
				if (flag) {
					// String tokenId = lrv.getTokenId();
					// add by gy for 登录成功后，将登录后的用户信息放入到cookie中
					//lrv.setLogonName(logonName);
					SessionUtil.addLoginCookie(response, lrv);
					//修改用户昵称
					userService.updateUserPetName(lrv.getUserId(), thirdName);
					//检查并插入推荐注册信息
					String fromId = SessionUtil.getRegisterFrom(request);
					try {
						if (!StringUtils.isEmpty(fromId)) {
							RecommendRegBo bo = new RecommendRegBo();
							bo.setCustomerId(Long.parseLong(fromId));
							bo.setRegSSOUserId(Long.parseLong(lrv.getUserId()));
							bo.setLogonName(logonName);
							int flagReg = registerService.insertRecommendRegister(bo);
							if ( flagReg == -1){
								logger.error("检查并插入推荐注册信息失败,fromId:  " +fromId +", userId:" + lrv.getUserId() );
							}
						}
					} catch (Exception e) {
						logger.error("检查并插入推荐注册信息异常,fromId:  " +fromId +", userId:" + lrv.getUserId() ,e);
					}

					String wurl = SessionUtil.getWurl(request);
					wurl = StringUtils.isEmpty(wurl) ? (request.getContextPath() + "/activity/toActivityIndex/.shtml") : wurl;
					resultMap.put("result", true);
					UserDetailDTO userDetailDTO = userService.getUserDetailDTOByUserId(Long.parseLong(lrv.getUserId()));
					resultMap.put("userInfo", getUserInfo(userDetailDTO));
					resultMap.put("isFirstChangeName", getStatus(userDetailDTO));
					resultMap.put("wurl", wurl);// 注册成功后，跳转到要跳转的页面
				}else{
					resultMap.put("result", false);
					resultMap.put("errorCode", ErrorCode.UserBindError.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.UserBindError.getErrorMsg());
					return JsonUtils.bean2jsonP(jsoncallback, resultMap);
				}
			} else {
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.RegisterAccountFailed.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.RegisterAccountFailed.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, resultMap);
			}
		}catch (EIBusinessException e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", e.getExtErrCode());
			resultMap.put("errorMsg", e.getExtMessage());
			resultMap.put("wurl", request.getContextPath() + "/loginReg/toReg.shtml");// 注册失败后
			logger.error("联合登录账号注册并绑定走秀账号{errorCode：" + e.getExtErrCode()
					+ ", errorMsg：" + e.getExtMessage() + "}");
		}catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("联合登录账号注册并绑定走秀账号时发生异常：", e);
		}

		logger.info("联合登录账号注册并绑定走秀账号接口返回结果：" + JSON.toJSONString(resultMap));
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	/**
	 * 联合登录旧接口，联合授权通过后，查询是否已绑定走秀帐号，若已绑定则代理登录，若未绑定则创建默认帐号绑定后并代理的登录
	 * 新的联合登录界面里"直接登录"也是调用的这个接口，创建默认的走秀帐号  亦即 强制联合接口
	 * @param request
	 * @return JSON
	 */
	@ResponseBody
	@RequestMapping(value = "/submitFederateLogin", produces = "text/html;charset=UTF-8")
	public String federateForceLoginXiu(String jsoncallback,
			HttpServletRequest request, HttpServletResponse response) {
		//第三方登录名
		String logonName = request.getParameter("memberVo.logonName");
		String userSource = request.getParameter("userSource");
		//QQ和微信的都是openId,  支付宝的是user_id，和userSource一起在走秀里标识出唯一的来自第三方的用户
		String partnerId = request.getParameter("partnerId");
		String weChatOpenId =  request.getParameter("weChatOpenId");
		String thirdName = request.getParameter("thirdName");
		String thirdPortraitURL = request.getParameter("thirdPortraitURL");
		// 登录渠道(pc-web:官网;mobile-wap:手机wap;android-app android应用;ios-app IOS应用)
		String loginChannel = request.getParameter("loginChannel");
		if ("".equals(loginChannel) || null == loginChannel) {
			loginChannel = "mobile-wap";
		}
		
		String deviceId = request.getParameter("deviceId");		//设备ID
		String sysName = request.getParameter("sysName");		//系统名称
		String sysVersion = request.getParameter("sysVersion");	//系统版本
		try {
			if (null != thirdName) {
				thirdName = URLDecoder.decode(thirdName,"UTF-8");
			}
			if (null != thirdPortraitURL) {
				thirdPortraitURL = URLDecoder.decode(thirdPortraitURL, "UTF-8");
			}
		} catch (UnsupportedEncodingException e1) {
			logger.error("decode第三方原名和头像异常：");
		}
		// 存储返回结果值
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(StringUtils.isEmpty(logonName)) {
			logonName = "xiu_"+UUID.randomUUID().toString();
			
//			result.put("result", false);
//			result.put("errorCode", ErrorCode.ParamsError.getErrorCode());
//			result.put("errorMsg", ErrorCode.ParamsError.getErrorMsg());
//			return JsonUtils.bean2jsonP(jsoncallback, result);
		}
		
		try {
			Map<String, Object> pmap = new HashMap<String, Object>();
			pmap.put("retAddressAndCoupon", "0");
			pmap.put("partnerId", partnerId);
			pmap.put("logonName", logonName);
			pmap.put("weChatOpenId", weChatOpenId);
			pmap.put("thirdName", thirdName);
			pmap.put("thirdPortraitURL", thirdPortraitURL);
			pmap.put("deviceId", deviceId);
			pmap.put("sysName", sysName);
			pmap.put("sysVersion", sysVersion);
			/*
			 * 联合登陆用户来源 ：
			 * tencent_qq(QQ),alipay(支付宝),sina_weibo(新浪微博),netEase(网易)
			 * ,139(139邮箱),51fanli(返利网)
			 */
			pmap.put("userSource", userSource);
			pmap.put("loginChannel", loginChannel);
			pmap.putAll(getDeviceParams(request));// 设备信息
			LoginResVo lrv = loginService.unionLoginOrReg(pmap);
			SessionUtil.addLoginTypeCookie(response, XiuConstant.LOGIN_TYPE_FEDERATE);//表示为第三方登录

			//登录成功后，查询用户信息返回 add by coco.long 2015-07-08
			if (lrv != null) {
				UserDetailDTO userDetailDTO = userService.getUserDetailDTOByUserId(Long.parseLong(lrv.getUserId()));
				result.put("userInfo", getUserInfo(userDetailDTO));
				
				// 登录成功后，将登录后的用户信息放入到session中
				//lrv.setLogonName(logonName);
				// 登录成功后，将登录后的用户信息放入到cookie中
				SessionUtil.addLoginCookie(response, lrv);
			}

			String wurl = CookieUtil.getInstance().readCookieValue(request, "wurl");
				
			if (StringUtils.isEmpty(wurl)) {
				wurl = request.getContextPath() + "/cx/index.shtml";// 默认首页地址;
			}
				
			result.put("result", true);
			result.put("wurl", wurl);	
		} 
		catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorMsg", e.getExtMessage());
			result.put("retCode", GlobalConstants.RET_CODE_OTHER_MSG);
			result.put("wurl", request.getContextPath() + "/loginReg/toLogin.shtml");// 登录失败后
			logger.error("SubmitFederateLogin error：{errorCode：" + e.getErrCode()
					+ ",errorMsg：" + e.getExtMessage() + "}"); // 跳转到登录页面
		}
		catch (BusinessException e) {
			result.put("result", false);
			result.put("retCode", e.getErrCode());
			result.put("errorMsg", e.getMessage());
			result.put("wurl", request.getContextPath() + "/loginReg/toLogin.shtml");
			logger.error("SubmitFederateLogin error：{errorCode：" + e.getErrCode()
					+ ",errorMsg：" + e.getMessage() + "}"); 
		}
		catch (Exception e) {
			result.put("result", false);
			result.put("errorMsg", "系统服务异常，请与管理员或客服联系");
			logger.error("requestSubmitLogin() ==> " + e.getMessage());
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/**
	 * 联合登陆新接口：点击"联合登录"按钮后，先进行第三方授权，完毕后调用这个接口，返回该第三方绑定走秀帐号的状况true 或false
	 * @param request
	 * @return JSON
	 */
	@ResponseBody
	@RequestMapping(value = "/federateLogin", produces = "text/html;charset=UTF-8")
	public String federateLoginEntry(String jsoncallback,HttpServletRequest request, HttpServletResponse response) {
		String userSource = request.getParameter("userSource");
		//QQ和微信的都是openId,  支付宝的是user_id，和userSource一起在走秀里标识出唯一的来自第三方的用户
		String partnerId = request.getParameter("partnerId");
		String weChatOpenId =  request.getParameter("weChatOpenId");
		String thirdName = request.getParameter("thirdName");
		String thirdPortraitURL = request.getParameter("thirdPortraitURL");
		//登录渠道(pc-web:官网;mobile-wap:手机wap;android-app android应用;ios-app IOS应用)
		String loginChannel=request.getParameter("loginChannel");
		if("".equals(loginChannel)||null==loginChannel){
			loginChannel="mobile-wap";
		}
				
		String deviceId = request.getParameter("deviceId");		//设备ID
		String sysName = request.getParameter("sysName");		//系统名称
		String sysVersion = request.getParameter("sysVersion");	//系统版本
		try {
			if (null != thirdName) {
				thirdName = URLDecoder.decode(thirdName,"UTF-8");
			}
			if (null != thirdPortraitURL) {
				thirdPortraitURL = URLDecoder.decode(thirdPortraitURL, "UTF-8");
			}
		} catch (UnsupportedEncodingException e1) {
			logger.error("decode第三方原名和头像异常：");
		}
		
		// 存储返回结果值
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("partnerId", partnerId);
			params.put("weChatOpenId", weChatOpenId);
			// 联合登陆用户来源 ：tencent_qq(QQ),alipay(支付宝),sina_weibo(新浪微博),netEase(网易) ,139(139邮箱),51fanli(返利网)
			params.put("userSource", userSource);
			params.put("loginChannel", loginChannel);
			params.putAll(getDeviceParams(request));// 设备信息
			params.put("thirdName", thirdName);
			params.put("thirdPortraitURL", thirdPortraitURL);
			params.put("deviceId", deviceId);
			params.put("sysName", sysName);
			params.put("sysVersion", sysVersion);
			//如有绑定过则登录
			LoginResVo loginResVo = loginService.federateLogin(params);
			result.put("result", true);
			//已绑定过
			if (loginResVo != null) {
				UserDetailDTO userDetailDTO = userService.getUserDetailDTOByUserId(Long.parseLong(loginResVo.getUserId()));
				result.put("bindStatus", true);
				result.put("userInfo", getUserInfo(userDetailDTO));
				result.put("isFirstChangeName", getStatus(userDetailDTO));
				// 登录成功后，将登录后的用户信息放入到cookie中
				SessionUtil.addLoginCookie(response, loginResVo);
				SessionUtil.addLoginTypeCookie(response, XiuConstant.LOGIN_TYPE_FEDERATE);//表示为第三方登录
				
				//返回用户是否绑定手机状态
				if(!StringUtils.isEmpty(userDetailDTO.getMobile()) && "1".equals(userDetailDTO.getMobileAuthenStatus())) {
					result.put("mobileBindStatus", true);
				} else {
					result.put("mobileBindStatus", false);
				}
			}else{
				result.put("bindStatus", false);
				result.put("mobileBindStatus", false);
			}
			// 用户访问足迹url
			String wurl = CookieUtil.getInstance().readCookieValue(request, "wurl");
			if (StringUtils.isEmpty(wurl)) {
				wurl = request.getContextPath() + "/cx/index.shtml";// 默认首页地址;
			}
			result.put("wurl", wurl);	
		} 
		catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorMsg", e.getExtMessage());
			result.put("retCode", GlobalConstants.RET_CODE_OTHER_MSG);
			result.put("wurl", request.getContextPath() + "/loginReg/toLogin.shtml");// 登录失败后
			logger.error("SubmitFederateLogin error：{errorCode：" + e.getErrCode()
					+ ",errorMsg：" + e.getExtMessage() + "}"); // 跳转到登录页面
		}
		catch (BusinessException e) {
			result.put("result", false);
			result.put("retCode", e.getErrCode());
			result.put("errorMsg", e.getMessage());
			result.put("wurl", request.getContextPath() + "/loginReg/toLogin.shtml");
			logger.error("SubmitFederateLogin error：{errorCode：" + e.getErrCode()
					+ ",errorMsg：" + e.getMessage() + "}"); 
		}
		catch (Exception e) {
			result.put("result", false);
			result.put("errorMsg", "系统服务异常，请与管理员或客服联系");
			logger.error("requestSubmitLogin() ==> " + e.getMessage());
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}

	/**
	 * 检查是否登录
	 * 
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "/checkLogin", produces = "text/html;charset=UTF-8")
	public String checkLoginStatus(String jsoncallback, HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		try {
			Map<String, Object> params = new LinkedHashMap<String, Object>();
			String tokenId = SessionUtil.getTokenId(request);
			params.put("tokenId", tokenId);
			params.putAll(getDeviceParams(request));
			// 添加IP信息，登录，获取用户状态等接口需要。
			params.put(GlobalConstants.KEY_REMOTE_IP, HttpUtil.getRemoteIpAddr(request));
			// 检查登陆
			if (!StringUtils.isEmpty(tokenId) && loginService.checkOnlineStatus(params)) {
				model.put("result", true);
				model.put("errorCode", ErrorCode.UserIsOnline.getErrorCode());
				model.put("errorMsg", ErrorCode.UserIsOnline.getErrorMsg());
			} else {
				model.put("result", false);
				model.put("errorCode", ErrorCode.SessionTimeOut.getErrorCode());
				model.put("errorMsg", ErrorCode.SessionTimeOut.getErrorMsg());
			}
		} catch (Exception e) {
			model.put("result", true);
			model.put("retCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("检查登陆状态发生异常：", e);
		}

		return JsonUtils.bean2jsonP(jsoncallback, model);
	}

	/**
	 * 获取当前用户
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserInfoRmote", produces = "text/html;charset=UTF-8")
	public String getUserInfo(String jsoncallback, HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		try {
			String tokenId = SessionUtil.getTokenId(request);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("tokenId", tokenId);
			// 添加IP信息，登录，获取用户状态等接口需要。
			String remoteIp = HttpUtil.getRemoteIpAddr(request);
			params.put(GlobalConstants.KEY_REMOTE_IP, remoteIp);
			params.putAll(getDeviceParams(request));
			
			if (!StringUtils.isEmpty(tokenId) && loginService.checkOnlineStatus(params)) {
				model.put("result", true);
				model.put("errorCode", ErrorCode.Success.getErrorCode());
				model.put("errorMsg", ErrorCode.Success.getErrorMsg());
				model.put("userName", SessionUtil.getUserName(request));
			} else {
				model.put("result", false);
				model.put("errorCode", ErrorCode.SessionTimeOut.getErrorCode());
				model.put("errorMsg", ErrorCode.SessionTimeOut.getErrorMsg());
			}
		} catch (EIBusinessException e) {
			model.put("result", false);
			model.put("errorCode", e.getExtErrCode());
			model.put("errorMsg", e.getExtMessage());
			logger.error("获取用户信息发生异常:" + e.getMessageWithSupportCode(), e);
		} catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取用户信息发生异常：", e);
		}

		logger.info("获取用户信息接口返回结果：" + JSON.toJSONString(model));
		return JsonUtils.bean2jsonP(jsoncallback, model);
	}
	
	/**
	 * 获取当前用户手机号码信息
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserPhone", produces = "text/html;charset=UTF-8")
	public String getUserPhone(String jsoncallback, HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		try {
			String tokenId = SessionUtil.getTokenId(request);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("tokenId", tokenId);
			// 添加IP信息，登录，获取用户状态等接口需要。
			String remoteIp = HttpUtil.getRemoteIpAddr(request);
			params.put(GlobalConstants.KEY_REMOTE_IP, remoteIp);
			params.putAll(getDeviceParams(request));
			
			if (!StringUtils.isEmpty(tokenId) && loginService.checkOnlineStatus(params)) {
				model.put("result", true);
				model.put("errorCode", ErrorCode.Success.getErrorCode());
				model.put("errorMsg", ErrorCode.Success.getErrorMsg());
				String userId = SessionUtil.getUserId(request);
				UserBaseDTO userBaseDTO = userService.getUserBasicInfoByUserId(Long.parseLong(userId));
				logger.info("获取用户基本信息userBaseDTO="+userBaseDTO);
				model.put("phone", userBaseDTO.getMobile());
				model.put("isEmptyPassword", userService.isEmptyPassword(userBaseDTO.getUserId()));
			} else {
				model.put("result", false);
				model.put("errorCode", ErrorCode.SessionTimeOut.getErrorCode());
				model.put("errorMsg", ErrorCode.SessionTimeOut.getErrorMsg());
			}
		} catch (EIBusinessException e) {
			model.put("result", false);
			model.put("errorCode", e.getExtErrCode());
			model.put("errorMsg", e.getExtMessage());
			logger.error("获取用户信息发生异常:" + e.getMessageWithSupportCode(), e);
		} catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取用户信息发生异常：", e);
		}

		logger.info("获取用户信息接口返回结果：" + JSON.toJSONString(model));
		return JsonUtils.beanjsonP(jsoncallback, model);
	}
	
	/***
	 * 用户更改手机号
	 * @param jsoncallback
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/changePhone", produces = "text/html;charset=UTF-8")
	public String changePhone(String jsoncallback, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String phone = request.getParameter("phone");
		String validateCode = request.getParameter("validateCode");
		String deviceId = request.getParameter("deviceId");		//设备ID
		String remoteIp = HttpUtil.getRemoteIpAddr(request);	//IP地址
		String sysName = request.getParameter("sysName");		//系统名称
		String sysVersion = request.getParameter("sysVersion");	//系统版本
		try {
			if (checkLogin(request)) {
				// 如果新手机为空
				if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(validateCode)) {
					result.put("result", false);
					result.put("errorCode", ErrorCode.MissingParams.getErrorCode());
					result.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
				} else {
					// 如果新号码未被绑定过
					if (userService.isLogonNameCanRegister(phone)) {
						MessageCode messageCode = messageCodeService.findMessageCodeByPhone(phone);
						if (messageCode != null) {
							String smsCode = messageCode.getMsgCode();
							String sendCodeTime = messageCode.getTimestamp();
							String timeFlag = messageCode.getTimeFlag();
							String contentFlag = messageCode.getContentFlag();

							// 不同的手机验证码不同
							if (!StringUtils.isEmpty(smsCode) && !StringUtils.isEmpty(sendCodeTime) && timeFlag.equals("N") && contentFlag.equals("N")) {
								long dif = DateUtil.diffDateTime(new Date(), new Date(Long.valueOf(sendCodeTime)));
								// 时间差大于规定时间则验证码失效
								if (dif > this.DISSECOND) {
									// 时间失效
									messageCode.setTimeFlag("Y");
									messageCodeService.updateMessageCode(messageCode);
									result.put("result", false);
									result.put("errorCode", ErrorCode.VerifyCodeTimeOut.getErrorCode());
									result.put("errorMsg", ErrorCode.VerifyCodeTimeOut.getErrorMsg());
								} else {
									// 验证验证码的正确性
									if (validateCode.equals(smsCode)) {
										// 内容已验证
										messageCode.setContentFlag("Y");
										messageCodeService.updateMessageCode(messageCode);
										// 获取userId
										String userId = SessionUtil.getUserId(request);
										// 查询当前用户登录信息
										UserBaseDTO userBaseDTO = userService.getUserBasicInfoByUserId(Long.parseLong(userId));
										// 如果当前用户电话为空 则直接绑定新号码
										if (userBaseDTO != null && userBaseDTO.getMobile()!=null && userBaseDTO.getMobile().equals(phone)) {
											result.put("result", false);
											result.put("errorCode", ErrorCode.PhoneValidateIsSame.getErrorCode());
											result.put("errorMsg", ErrorCode.PhoneValidateIsSame.getErrorMsg());
										} else {
											if(userBaseDTO != null) {
												userBaseDTO.setMobile(phone);
											}
											UserOperateLogInfoDTO userOperateLogInfoDTO = new UserOperateLogInfoDTO();
											userOperateLogInfoDTO.setUserBaseDTO(userBaseDTO);
											userOperateLogInfoDTO.setDeviceNum(deviceId);
											userOperateLogInfoDTO.setOperAddr(remoteIp);
											userOperateLogInfoDTO.setSysName(sysName);
											userOperateLogInfoDTO.setSysNum(sysVersion);
											userService.modifyUserBaseInfo(userOperateLogInfoDTO);
											result.put("result", true);
											result.put("errorCode", ErrorCode.Success.getErrorCode());
											result.put("errorMsg", ErrorCode.Success.getErrorMsg());
										}
									}else{
										result.put("result", false);
										result.put("errorCode", ErrorCode.VerifyCodeFailed.getErrorCode());
										result.put("errorMsg", ErrorCode.VerifyCodeFailed.getErrorMsg());
										logger.info("校验验证码失败：{Tel: " + phone + ", smsCode: " + validateCode + "}");
									}
								}
							}else{
								result.put("result", false);
								result.put("errorCode", ErrorCode.VerifyCodeFailed.getErrorCode());
								result.put("errorMsg", ErrorCode.VerifyCodeFailed.getErrorMsg());
								logger.info("校验验证码失败：{Tel: " + phone + ", smsCode: " + validateCode + "}");
							}
						} else {
							result.put("result", false);
							result.put("errorCode", ErrorCode.SystemError.getErrorCode());
							result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
							logger.error("checkVerifyCode==>验证码为空，没有找到验证码记录");
						}
					} else {
						result.put("result", false);
						result.put("errorCode", ErrorCode.ValidateNewPhoneError.getErrorCode());
						result.put("errorMsg", ErrorCode.ValidateNewPhoneError.getErrorMsg());
					}
				}
			} else {
				result.put("result", false);
				result.put("errorCode", ErrorCode.SessionTimeOut.getErrorCode());
				result.put("errorMsg", ErrorCode.SessionTimeOut.getErrorMsg());
			}
		} catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", e.getExtErrCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("用户更改手机号发生异常:" + e.getMessageWithSupportCode(), e);
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("用户更改手机号发生异常：", e);
		}

		logger.info("用户更改手机号接口返回结果：" + JSON.toJSONString(result));
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}

	/**
	 * 设置需要写入cookie中的url
	 * 
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "/setRedirectUrl", produces = "text/html;charset=UTF-8")
	public String setRedirectUrl(String jsoncallback, HttpServletRequest request, 
			HttpServletResponse response) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		
		try {
			String urlKey = request.getParameter("uKey");
			String urlValue = request.getParameter("uValue");
			urlKey = StringUtils.isEmpty(urlKey) ? "wurl" : urlKey;
			urlValue = StringUtils.isEmpty(urlValue) 
					? ("http://m.xiu.com") : urlValue;
			// 将对应的url写入cookie
			CookieUtil.getInstance().addCookie(response, urlKey, urlValue);
			result.put("result", true);
			result.put("retCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
		} catch (Exception e) {
			result.put("result", true);
			result.put("retCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.info("设置转发URL发生异常：" + JSON.toJSONString(result));
		}

		return JsonUtils.bean2jsonP(jsoncallback, result);
	}

	/**
	 * 获取cookie中uKey对应的url
	 * 
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "/getRedirectUrl", produces = "text/html;charset=UTF-8")
	public String getRedirectUrl(String jsoncallback, HttpServletRequest request, 
			HttpServletResponse response) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		
		try {
			String urlKey = request.getParameter("uKey");
			urlKey = StringUtils.isEmpty(urlKey) ? "wurl" : urlKey;
			CookieUtil cookieUtil = CookieUtil.getInstance();
			String urlValue = cookieUtil.readCookieValue(request, urlKey);
			urlValue = StringUtils.isEmpty(urlValue) ? ("http://m.xiu.com") : urlValue;
			result.put("result", true);
			result.put("retCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			result.put(urlKey, urlValue);
			// 使用完毕删除该cookie
			cookieUtil.deleteCookie(response, urlKey);
		} catch (Exception e) {
			result.put("result", true);
			result.put("retCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.info("设置转发URL发生异常：" + e.getMessage());
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}

	/**
	 * 退出登录
	 * @param request
	 * @param response
	 * @param wurl
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/logoutRemote", produces = "text/html;charset=UTF-8")
	public String logOut(String jsoncallback, 
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 从cookie中获取TOKENID
		String tokenId = SessionUtil.getTokenId(request);
		
		try {
			if (!StringUtils.isEmpty(tokenId)) {// 如果页面因长时间停留已经回话超时，则返回 SUCCESS
				Map<String, Object> pmap = new HashMap<String, Object>();
				pmap.put("tokenId", tokenId);
				pmap.putAll(getDeviceParams(request));// 设备信息
				loginService.logOut(pmap);
				// 退出登录成功
				SessionUtil.deleteLoginCookie(response);
				String userId = SessionUtil.getUserId(request);
				boolean isEmptyPassword = userService.isEmptyPassword(Long.parseLong(userId));
				model.put("result", true);
				model.put("errorCode", ErrorCode.Success.getErrorCode());
				model.put("errorMsg", ErrorCode.Success.getErrorMsg());		
				model.put("isEmptyPassword", isEmptyPassword);
			} else {// tokenId为null,长时间视为已退出登录
				model.put("result", true);
				model.put("errorCode", ErrorCode.Success.getErrorCode());
				model.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}
		} 
		catch (EIBusinessException e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.LogoutAccountFailed.getErrorCode());
			model.put("errorMsg", ErrorCode.LogoutAccountFailed.getErrorMsg());
			logger.error("logOut() ==> ", e);
		}
		catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("logOut() ==> ", e);
		}
		logger.info("退出登录接口返回结果：" + JSON.toJSONString(model));
		return JsonUtils.bean2jsonP(jsoncallback, model);
	}
	
	/***
	 * 用户详细信息转换
	 * @param userDetailDTO
	 * @return
	 */
	public UserInfo getUserInfo(UserDetailDTO userDetailDTO){
		UserInfo userInfo = new UserInfo();
		
		if (userDetailDTO != null) {
			String loginName = userDetailDTO.getLogonName();
			// loginName 如果用户loginName遵循先手机再邮箱再昵称
			if (userDetailDTO.getMobileAuthenStatus().equals("1") && !StringUtils.isEmpty(userDetailDTO.getMobile())) {
				loginName = userDetailDTO.getMobile();
				userInfo.setMobileBindStatus(true); //设置用户的手机绑定状态
			}else if (userDetailDTO.getEmailAuthenStatus().equals("1") && !StringUtils.isEmpty(userDetailDTO.getEmail())) {
				loginName = userDetailDTO.getEmail();
			}
			userInfo.setAddressInfo(userDetailDTO.getAddressInfo());
			if (userDetailDTO.getBirthday() != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				userInfo.setBirthday(dateFormat.format(userDetailDTO.getBirthday()));
			}
			userInfo.setCustName(userDetailDTO.getCustName());
			userInfo.setEbayUserAgreement(userDetailDTO.getEbayUserAgreement());
			userInfo.setEmail(userDetailDTO.getEmail());
			userInfo.setEmailAuthenStatus(userDetailDTO.getEmail());
			if (!StringUtils.isEmpty(userDetailDTO.getHeadPortrait())) {
				userInfo.setHeadPortrait(prefixURL.concat(userDetailDTO.getHeadPortrait()));
			}
			userInfo.setLastLogonChannelId(userDetailDTO.getLastLogonChannelId());
			userInfo.setLastLogonIp(userDetailDTO.getLastLogonIp());
			userInfo.setLastLogonTime(userDetailDTO.getLastLogonTime());
			userInfo.setLogonName(loginName);
			userInfo.setMobile(userDetailDTO.getMobile());
			userInfo.setMobileAuthenStatus(userDetailDTO.getMobileAuthenStatus());
			userInfo.setPetName(userDetailDTO.getPetName());
			userInfo.setRegisterTime(userDetailDTO.getRegisterTime());
			userInfo.setRegisterType(userDetailDTO.getRegisterType());
			userInfo.setSex(userDetailDTO.getSex());
			userInfo.setSexDesc(userDetailDTO.getSexDesc());
			userInfo.setUserId(userDetailDTO.getUserId());
			boolean isEmptyPassword = userService.isEmptyPassword(userDetailDTO.getUserId());
			userInfo.setIsEmptyPassword(isEmptyPassword);
		}
		
		return userInfo;
	}
	
	/***
	 * 登录名是否首次修改
	 * @param userInfo
	 * @return
	 */
	public boolean getStatus(UserDetailDTO userDetailDTO){
		boolean isFirstChangeName = false;
		if(userDetailDTO!=null){
			// 联合登陆用户名逻辑 >> 如果前缀以xiu_+user_id(当前用户的用户ID)开头的  表示联合登陆初始化用户
			String prefix ="";
			if(userDetailDTO.getUserId()!=null){
				prefix = "xiu_".concat(userDetailDTO.getUserId().toString());
			}
			if ( userDetailDTO.getLogonName() != null) {
				if (userDetailDTO.getLogonName().startsWith(prefix)) {
					isFirstChangeName = true;
				} 
				
				// 正则表达式 字母开头 字母数字和下划线组成  
				Pattern pattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]{5,19}$");
				Matcher matcher = pattern.matcher(userDetailDTO.getLogonName());
				if (!matcher.matches()) {
					isFirstChangeName = true;
				}
			}
			
			
			// loginName 如果用户loginName遵循先手机再邮箱再昵称
			if (userDetailDTO.getMobileAuthenStatus().equals("1") && !StringUtils.isEmpty(userDetailDTO.getMobile())) {
				isFirstChangeName = false;
			}else if (userDetailDTO.getEmailAuthenStatus().equals("1") && !StringUtils.isEmpty(userDetailDTO.getEmail())) {
				isFirstChangeName = false;
			}
		}

		return isFirstChangeName;
	}
	
	/**
	 * 发送验证码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sendLoginVerifyCode", produces = "text/html;charset=UTF-8")
	public String sendLoginSmsVerifyCode(String jsoncallback, HttpServletRequest request) {
		String logonName = request.getParameter("memberVo.telphone");
		Map<String, Object> map = new HashMap<String, Object>();
		String deviceId = request.getParameter("deviceId");	//设备号
		
		try {
			// 存储返回结果值
			if (!StringUtils.isEmpty(logonName)) {
				if(!StringUtils.isEmpty(deviceId)) {
					boolean limitStatus = false;
					
					Map<String, Object> paraMap = new HashMap<String, Object>();
					paraMap.put("mobile", logonName);
					paraMap.put("deviceId", deviceId);
					paraMap.put("type", MobileCommonConstant.SMS_VALIDATE_TIMES_LOGIN);
					paraMap.put("limitTimes", MobileCommonConstant.SMS_LIMIT_TIMES);
					//验证获取短信验证码是否超过次数
					limitStatus = messageCodeService.getSMSLimitStatus(paraMap);
					
					if(limitStatus) {
						map.put("result", false);
						map.put("errorCode", ErrorCode.getSMSOverLimitTimes.getErrorCode());
						map.put("errorMsg", "操作太频繁了，请1个小时后再试或联系客服");
					} else {
						// 获取验证码
						String smsCode =messageCodeService.getMessageCodeByPhone(logonName);
						// 获取通过手机号登录短信内容
						String smsBody = SMSTemplateUtil.getLoginBody(smsCode);
						logger.info("用户注册号码："+ logonName + ",信息内容：" + smsBody);
						boolean flag = iSMSService.sendSMS(smsBody, logonName, "", logonName);
						logger.info("短信发送状态：flag=" + flag);
						
						MessageCode messageCode = new MessageCode();
						messageCode.setMsgCode(smsCode);
						messageCode.setTimestamp(String.valueOf(System.currentTimeMillis()));
						messageCode.setMsgPhone(logonName);
						messageCode.setTimeFlag("N");
						messageCode.setContentFlag("N");
							
						// 插入验证码到数据库中
						messageCodeService.saveMessageCode(messageCode);
						
						//检测手机号是否存在
						boolean isUserExists = userService.isLogonNameExist(logonName);
						map.put("isUserExists", isUserExists);
						
						map.put("result", true);
						map.put("errorCode", ErrorCode.Success.getErrorCode());
						map.put("errorMsg", ErrorCode.Success.getErrorMsg());
					}
				} else {
					map.put("result", false);
					map.put("errorCode", ErrorCode.MissingParams.getErrorCode());
					map.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
				}
			} 
			else {
				map.put("result", false);
				map.put("errorCode", ErrorCode.LogonNameIsNull.getErrorCode());
				map.put("errorMsg", ErrorCode.LogonNameIsNull.getErrorMsg());
			}
		} 
		catch (EIBusinessException e) {
			map.put("result", false);
			map.put("retCode", ErrorCode.SendSMSCodeFailed.getErrorCode());
			map.put("errorMsg", e.getExtMessage());
			logger.error("发送短信验证码时发生异常：", e);
		}
		catch (Exception e) {
			map.put("result", false);
			map.put("errorCode", ErrorCode.SystemError.getErrorCode());
			map.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("发送短信验证码时发生异常：", e);
		}

		logger.info("发送验证码接口返回结果：" + JSON.toJSONString(map));
		return JsonUtils.bean2jsonP(jsoncallback, map);
	}

	
	/***
	 * 通过验证码登录
	 * @param jsoncallback
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/loginWithVerifyCode", produces = "text/html;charset=UTF-8")
	public String loginWithVerifyCode(String jsoncallback,HttpServletRequest request, HttpServletResponse response) {
		logger.info("用户通过验证码登录参数params="+request.getQueryString());
		String logonName = request.getParameter("loginName");
		String smsCode = request.getParameter("smsCode");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if(StringUtils.isEmpty(logonName) || StringUtils.isEmpty(smsCode) ) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		
		int regSource = NumberUtils.toInt(request.getParameter("regSource"), 150);	//注册来源
		//登录渠道(pc-web:官网;mobile-wap:手机wap;android-app android应用;ios-app IOS应用)
		String loginChannel = request.getParameter("loginChannel");
		if("".equals(loginChannel)||null==loginChannel){
			loginChannel="mobile-wap";
		}
				
		String deviceId = request.getParameter("deviceId");		//设备ID
		String remoteIp = HttpUtil.getRemoteIpAddr(request);	//IP地址
		String sysName = request.getParameter("sysName");		//系统名称
		String sysVersion = request.getParameter("sysVersion");	//系统版本
		try {
			// 短信验证码验证start
			MessageCode messageCode = messageCodeService.findMessageCodeByPhone(logonName);
			if (messageCode != null) {
				String oldSmsCode = messageCode.getMsgCode();
				String sendCodeTime = messageCode.getTimestamp();
				String timeFlag = messageCode.getTimeFlag();
				String contentFlag = messageCode.getContentFlag();

				// 不同的手机验证码不同
				if (!StringUtils.isEmpty(oldSmsCode) && !StringUtils.isEmpty(sendCodeTime) && timeFlag.equals("N") && contentFlag.equals("N")) {
					long dif = DateUtil.diffDateTime(new Date(), new Date(Long.valueOf(sendCodeTime)));

					// 时间差大于规定时间则验证码失效
					if (dif > this.DISSECOND) {
						// 时间失效
						messageCode.setTimeFlag("Y");
						messageCodeService.updateMessageCode(messageCode);
						resultMap.put("result", false);
						resultMap.put("errorCode", ErrorCode.VerifyCodeTimeOut.getErrorCode());
						resultMap.put("errorMsg", ErrorCode.VerifyCodeTimeOut.getErrorMsg());
						return JsonUtils.bean2jsonP(jsoncallback, resultMap);
					} else {
						// 验证验证码的正确性
						if (smsCode.equals(oldSmsCode)) {
							// 删除验证码
							messageCodeService.removeMessageCodeByPhone(logonName);
						} else {
							resultMap.put("result", false);
							resultMap.put("errorCode", ErrorCode.VerifyCodeFailed.getErrorCode());
							resultMap.put("errorMsg", "请输入正确的验证码");
							logger.info("校验验证码失败：{Tel: " + logonName + ", smsCode: " + smsCode + "}");
							return JsonUtils.bean2jsonP(jsoncallback, resultMap);
						}
					}
				}else {
					resultMap.put("result", false);
					resultMap.put("errorCode", ErrorCode.VerifyCodeFailed.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.VerifyCodeFailed.getErrorMsg());
					logger.error("checkVerifyCode==>验证码为空，没有找到验证码记录");
					return JsonUtils.bean2jsonP(jsoncallback, resultMap);
				}
			} else {
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.VerifyCodeFailed.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.VerifyCodeFailed.getErrorMsg());
				logger.error("checkVerifyCode==>验证码为空，没有找到验证码记录");
				return JsonUtils.bean2jsonP(jsoncallback, resultMap);
			}
			// 短信验证码验证end
			
			//检测用户手机号是否注册
			boolean isUserExists = userService.isLogonNameExist(logonName);
			Map<String, Object> pmap = new HashMap<String, Object>();
			if(isUserExists) {
				//存在则登录成功
				String channelId = GlobalConstants.CHANNEL_ID;
				UserDetailDTO user = userService.getUserBasicInfoByLogonName(logonName, Integer.parseInt(channelId));
				String userId = user.getUserId().toString();
				pmap.put("userId", userId);
			} else {
				pmap.put("regSource", regSource);
			}
			pmap.put("logonName", logonName);
			pmap.put("loginChannel", loginChannel);
			pmap.put("deviceId", deviceId);
			pmap.put("sysName", sysName);
			pmap.put("sysVersion", sysVersion);
			pmap.put(GlobalConstants.KEY_REMOTE_IP, remoteIp);
			
			LoginResVo lrv = loginService.loginWithVerifyCode(pmap, isUserExists);
			lrv.setLogonName(logonName);
			SessionUtil.addLoginCookie(response, lrv);  //把用户信息放入cookie
			SessionUtil.addLoginTypeCookie(response, XiuConstant.LOGIN_TYPE_ACCOUNT);//标示为账号登录

			resultMap.put("result", true);
			resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			
		}catch (EIBusinessException e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", e.getExtErrCode());
			resultMap.put("errorMsg", e.getExtMessage());
			resultMap.put("wurl", request.getContextPath() + "/loginReg/toLogin.shtml");// 登录失败后
			logger.error("用户通过验证码登录{errorCode：" + e.getExtErrCode() + ", errorMsg：" + e.getExtMessage() + "}");
		}catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("用户通过验证码登录时发生异常：", e);
		}

		logger.info("用户通过验证码登录接口返回结果：" + JSON.toJSONString(resultMap));
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	/**
	 * 根据联合登陆用户来源，获取对用渠道编码
	 * 
	 * @param userSource
	 * @return iPartnerChannelId
	 */
	private String getPartnerChannel(String userSource) {
		String channel = null;
		if (GlobalConstants.TENCENT_QQ.equals(userSource)) {
			channel = "QQ";
		} else if (GlobalConstants.ALIPAY.equals(userSource)) {
			channel = "支付宝";
		} else if (GlobalConstants.NETEASE.equals(userSource)) {
			channel = "网易";
		} else if (GlobalConstants.MOBILE139.equals(userSource)) {
			channel = "139邮箱";
		} else if (GlobalConstants.SINA_WEIBO.equals(userSource)) {
			channel = "新浪微博";
		} else if (GlobalConstants.TENCENT_WECHAT.equals(userSource)) {
			channel = "腾讯微信";
		}else if (GlobalConstants.WANLITONG.equals(userSource)) {
			channel = "万里通";
		}

		return channel;
	}
	
	
	/***
	 * 绑定百度推送节点信息
	 * 
	 * @param jsoncallback
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/bindXiuUserToBaiduPushDevice", produces = "text/html;charset=UTF-8")
	public String bindXiuUserToBaiduPushDevice(String jsoncallback,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (checkLogin(request)) {
				LoginResVo user = SessionUtil.getLoginInfo(request);

				Long userId = Long.parseLong(user.getUserId());
				String loginChannel = request.getParameter("loginChannel");
				String baiduChannelId = request.getParameter("baiduChannelId");
				String baiduUserId = request.getParameter("baiduUserId");
				String baiduDeviceType = request.getParameter("baiduDeviceType");
				//必填字段不允许为空
				if ( null != userId && null != loginChannel && null != baiduChannelId && null != baiduUserId ){
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("userId", userId);
				params.put("deviceId", request.getParameter("deviceId"));
				params.put("loginChannel", loginChannel);
				params.put("appVersion", request.getParameter("appVersion"));
				
				params.put("baiduChannelId", baiduChannelId);
				params.put("baiduUserId", baiduUserId);
				params.put("baiduDeviceType", baiduDeviceType);
				
				loginService.bindXiuUserToBaiduPushDevice(params);
				resultMap.put("result", true);
				
				}else{
					logger.error("调用bindBaiduDevicePushInfo 参数不合法");	
				}
			} else {
				logger.info("调用bindBaiduDevicePushInfo：用户未登录故不调用");
				resultMap.put("result", false);
			}

		} catch (Exception e) {
			resultMap.put("result", false);
			logger.error("调用UUC绑定百度推送设备信息异常", e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	/**
	 * 发送语音验证码
	 * @param jsoncallback
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sendVoiceVerifyCode", produces = "text/html;charset=UTF-8")
	public String sendVoiceVerifyCode(String jsoncallback, HttpServletRequest request) {
		String logonName = request.getParameter("mobile");
		Map<String, Object> map = new HashMap<String, Object>();
		String deviceId = request.getParameter("deviceId");	//设备号
		
		//检验参数
		if(StringUtils.isEmpty(logonName) || StringUtils.isEmpty(deviceId) ) {
			map.put("result", false);
			map.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			map.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, map);
		}
		
		//判断是否手机号
		Pattern p = Pattern.compile("^1\\d{10}$");
		Matcher m = p.matcher(logonName);  
		if(!m.matches()) {
			map.put("result", false);
			map.put("errorCode", ErrorCode.ParamsError.getErrorCode());
			map.put("errorMsg", ErrorCode.ParamsError.getErrorMsg() + "：必须是手机号");
			return JsonUtils.bean2jsonP(jsoncallback, map);
		}
		
		try {
			boolean limitStatus = false; 
			
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("mobile", logonName);
			paraMap.put("deviceId", deviceId);
			paraMap.put("limitTimes", MobileCommonConstant.SMS_LIMIT_TIMES);
			paraMap.put("type", MobileCommonConstant.VOICE_VALIDATE_TIMES_LOGIN);
			//验证获取语音验证码是否超过次数
			limitStatus = messageCodeService.getSMSLimitStatus(paraMap);
			
			if(limitStatus) {
				map.put("result", false);
				map.put("errorCode", ErrorCode.getVoiceOverLimitTimes.getErrorCode());
				map.put("errorMsg", "操作太频繁了，请1个小时后再试或联系客服");
			} else {
				// 获取验证码
				String smsCode =messageCodeService.getMessageCodeByPhone(logonName);
				
				// 发送语音验证码
				logger.info("用户注册号码："+ logonName + ",语音验证码：" + smsCode);
				boolean flag = iSMSService.sendVoiceVerifyCode(logonName, smsCode);
				logger.info("语音验证码发送状态：flag=" + flag);
				
				if(!flag) {
					map.put("result", false);
					map.put("errorCode", ErrorCode.SendVoiceCodeFailed.getErrorCode());
					map.put("errorMsg", ErrorCode.SendVoiceCodeFailed.getErrorMsg());
					return JsonUtils.bean2jsonP(jsoncallback, map);
				}
				
				MessageCode messageCode = new MessageCode();
				messageCode.setMsgCode(smsCode);
				messageCode.setTimestamp(String.valueOf(System.currentTimeMillis()));
				messageCode.setMsgPhone(logonName);
				messageCode.setTimeFlag("N");
				messageCode.setContentFlag("N");
					
				// 插入验证码到数据库中
				messageCodeService.saveMessageCode(messageCode);
				
				//检测手机号是否存在
				boolean isUserExists = userService.isLogonNameExist(logonName);
				map.put("isUserExists", isUserExists);
				
				map.put("result", true);
				map.put("errorCode", ErrorCode.Success.getErrorCode());
				map.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}
		} catch (EIBusinessException e) {
			map.put("result", false);
			map.put("retCode", ErrorCode.SendSMSCodeFailed.getErrorCode());
			map.put("errorMsg", e.getExtMessage());
			logger.error("发送语音验证码时发生异常：", e);
		}
		catch (Exception e) {
			map.put("result", false);
			map.put("errorCode", ErrorCode.SystemError.getErrorCode());
			map.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("发送语音验证码时发生异常：", e);
		}

		logger.info("发送语音验证码接口返回结果：" + JSON.toJSONString(map));
		return JsonUtils.bean2jsonP(jsoncallback, map);
	}
	
	/**
	 * 获取用户账号
	 * @param userDetailDTO
	 * @return
	 */
	private String getUserAccount(UserDetailDTO userDetailDTO) {
		String account = "";
		
		String userMobile = userDetailDTO.getMobile();
		String userEmail = userDetailDTO.getEmail();
		if(!StringUtils.isEmpty(userMobile)) {
			//手机不为空
			account = userMobile.substring(0, 3) + "****" + userMobile.substring(userMobile.length() - 4, userMobile.length());
			account = "用户名" + account;
		} else if(!StringUtils.isEmpty(userEmail)) {
			int len = userEmail.indexOf("@");
			if(len > 4) {
				account = userEmail.substring(0, len - 4) + "****" + userEmail.substring(len, userEmail.length());
			} else {
				StringBuffer star = new StringBuffer();
				int position = len;
				while(position > 1) {
					star = star.append("*");
					position--;
				}
				account = userEmail.charAt(0) + star.toString() + userEmail.substring(len, userEmail.length());
			}
			account = "邮箱" + account;
		} else {
			account = userDetailDTO.getLogonName();
			account = "用户名" + account;
		}
		
		return account;
	}
	
	/**
	 * 发送语音验证码
	 * @param jsoncallback
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sendVoiceVerifyCodeBinding", produces = "text/html;charset=UTF-8")
	public String sendLoginVoiceVerifyCode(String jsoncallback, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String mobile = request.getParameter("mobile"); //手机号
		String deviceId = request.getParameter("deviceId");	//设备号
		String forceBinding = request.getParameter("forceBinding"); //强制绑定 Y.是 N.不是
		String userSource = request.getParameter("userSource");//如果有传说明是新用户，还没注册走秀
		String vesrion = request.getParameter("vesrion");//版本
		AppVo appVo = AppVersionUtils.parseAppVo(request);
		//无版本获取比4.0版本小 默认为1.0低版本处理
		if(appVo==null||appVo.getAppVersion()==null||  "4.0.0".compareTo(appVo.getAppVersion()) > 0){
			vesrion="1.0";
		}
		//检验参数
		if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(deviceId) ) {
			map.put("result", false);
			map.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			map.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, map);
		}
		
		//判断是否手机号
		Pattern p = Pattern.compile("^1\\d{10}$");
		Matcher m = p.matcher(mobile);  
		if(!m.matches()) {
			map.put("result", false);
			map.put("errorCode", ErrorCode.ParamsError.getErrorCode());
			map.put("errorMsg", ErrorCode.ParamsError.getErrorMsg() + "：必须是手机号");
			return JsonUtils.bean2jsonP(jsoncallback, map);
		}
		
		if(StringUtils.isEmpty(forceBinding) || !"Y".equals(forceBinding)) {
			forceBinding = "N"; //默认不强制
		}
		
		try {
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("mobile", mobile);
			paraMap.put("deviceId", deviceId);
			paraMap.put("limitTimes", MobileCommonConstant.VOICE_LIMIT_TIMES);
			paraMap.put("type", MobileCommonConstant.VOICE_VALIDATE_TIMES_LOGIN);
			//验证获取语音验证码是否超过次数
			boolean limitStatus = messageCodeService.getSMSLimitStatus(paraMap);
			
			if(limitStatus) {
				map.put("result", false);
				map.put("errorCode", ErrorCode.getVoiceOverLimitTimes.getErrorCode());
				map.put("errorMsg", "操作太频繁了，请1个小时后再试或联系客服");
			} else {
				boolean newMobile = userService.isLogonNameCanRegister(mobile); 
				if(!newMobile && "N".equals(forceBinding)) {
					Map resultMap=null;
					if(vesrion.equals("1.0")){
						resultMap=sendSMSNotifyCheckV1(request, mobile);
					}else {
						resultMap=sendSMSnotifyCheckV400(request, mobile);
					}
					if(resultMap!=null){
						return JsonUtils.bean2jsonP(jsoncallback, resultMap);
					}
				
				} 
				
				// 获取验证码
				String smsCode =messageCodeService.getMessageCodeByPhone(mobile);
				
				//发送语音验证码
				logger.info("用户注册号码："+ mobile + ",语音验证码：" + smsCode);
				boolean flag = iSMSService.sendVoiceVerifyCode(mobile, smsCode);
				logger.info("语音验证码发送状态：flag=" + flag);
				if(!flag) {
					map.put("result", false);
					map.put("errorCode", ErrorCode.SendVoiceCodeFailed.getErrorCode());
					map.put("errorMsg", ErrorCode.SendVoiceCodeFailed.getErrorMsg());
					return JsonUtils.bean2jsonP(jsoncallback, map);
				}
				
				MessageCode messageCode = new MessageCode();
				messageCode.setMsgCode(smsCode);
				messageCode.setTimestamp(String.valueOf(System.currentTimeMillis()));
				messageCode.setMsgPhone(mobile);
				messageCode.setTimeFlag("N");
				messageCode.setContentFlag("N");
				//插入验证码到数据库中
				messageCodeService.saveMessageCode(messageCode);
				
				map.put("result", true);
				map.put("errorCode", ErrorCode.Success.getErrorCode());
				map.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}
		} catch (EIBusinessException e) {
			map.put("result", false);
			map.put("retCode", ErrorCode.SendVoiceCodeFailed.getErrorCode());
			map.put("errorMsg", e.getExtMessage());
			logger.error("发送语音验证码时发生异常：", e);
		}
		catch (Exception e) {
			map.put("result", false);
			map.put("errorCode", ErrorCode.SystemError.getErrorCode());
			map.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("发送语音验证码时发生异常：", e);
		}

		logger.info("发送语音验证码接口返回结果：" + JSON.toJSONString(map));
		return JsonUtils.bean2jsonP(jsoncallback, map);
	}
	
	@ResponseBody
	@RequestMapping(value = "/sendSmsVerifyCodeBinding", produces = "text/html;charset=UTF-8")
	public String sendLoginSMSVerifyCode(String jsoncallback, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String mobile = request.getParameter("mobile"); //手机号
		String deviceId = request.getParameter("deviceId");	//设备号
		String forceBinding = request.getParameter("forceBinding"); //强制绑定 Y.是 N.不是
		String userSource = request.getParameter("userSource");//如果有传说明是新用户，还没注册走秀
		AppVo appVo = AppVersionUtils.parseAppVo(request);
		String vesrion ="";
		//无版本获取比4.0版本小 默认为1.0低版本处理
		if(appVo==null||appVo.getAppVersion()==null||  "4.0.0".compareTo(appVo.getAppVersion()) > 0){
			vesrion="1.0";
		}
		
		//检验参数
		if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(deviceId)) {
			map.put("result", false);
			map.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			map.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, map);
		}
		
		//判断是否手机号
		Pattern p = Pattern.compile("^1\\d{10}$");
		Matcher m = p.matcher(mobile);  
		if(!m.matches()) {
			map.put("result", false);
			map.put("errorCode", ErrorCode.ParamsError.getErrorCode());
			map.put("errorMsg", ErrorCode.ParamsError.getErrorMsg() + "：必须是手机号");
			return JsonUtils.bean2jsonP(jsoncallback, map);
		}
		
		if(StringUtils.isEmpty(forceBinding) || !"Y".equals(forceBinding)) {
			forceBinding = "N"; //默认不强制
		}
		
		try {
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("mobile", mobile);
			paraMap.put("deviceId", deviceId);
			paraMap.put("limitTimes", MobileCommonConstant.SMS_LIMIT_TIMES);
			paraMap.put("type", MobileCommonConstant.SMS_VALIDATE_TIMES_LOGIN);
			//验证获取验证码是否超过次数
			boolean limitStatus = messageCodeService.getSMSLimitStatus(paraMap);
			
			if(limitStatus) {
				map.put("result", false);
				map.put("errorCode", ErrorCode.getSMSOverLimitTimes.getErrorCode());
				map.put("errorMsg", "操作太频繁了，请1个小时后再试或联系客服");
			} else {
				boolean newMobile = userService.isLogonNameCanRegister(mobile); 
				if(!newMobile && "N".equals(forceBinding)) {
					Map resultMap=null;
					if(vesrion.equals("1.0")){
						resultMap=sendSMSNotifyCheckV1(request, mobile);
					}else {
						resultMap=sendSMSnotifyCheckV400(request, mobile);
					}
					if(resultMap!=null){
						return JsonUtils.bean2jsonP(jsoncallback, resultMap);
					}
				} 
					
				// 获取验证码
				String smsCode =messageCodeService.getMessageCodeByPhone(mobile);
				//发送短信验证码
				String smsBody = SMSTemplateUtil.getLoginBody(smsCode);
				logger.info("用户注册号码："+ mobile + ",信息内容：" + smsBody);
				boolean flag = iSMSService.sendSMS(smsBody, mobile, "", mobile);
				logger.info("短信验证码发送状态：flag=" + flag);
				//如果是新生成的验证码，则插入到数据库
				MessageCode messageCode = new MessageCode();
				messageCode.setMsgCode(smsCode);
				messageCode.setTimestamp(String.valueOf(System.currentTimeMillis()));
				messageCode.setMsgPhone(mobile);
				messageCode.setTimeFlag("N");
				messageCode.setContentFlag("N");
				//插入验证码到数据库中
				messageCodeService.saveMessageCode(messageCode);
				
				map.put("result", true);
				map.put("errorCode", ErrorCode.Success.getErrorCode());
				map.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}
		} catch (EIBusinessException e) {
			map.put("result", false);
			map.put("retCode", ErrorCode.SendSMSCodeFailed.getErrorCode());
			map.put("errorMsg", e.getExtMessage());
			logger.error("发送短信验证码时发生异常：", e);
		}
		catch (Exception e) {
			map.put("result", false);
			map.put("errorCode", ErrorCode.SystemError.getErrorCode());
			map.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("发送短信验证码时发生异常：", e);
		}

		logger.info("发送短信验证码接口返回结果：" + JSON.toJSONString(map));
		return JsonUtils.bean2jsonP(jsoncallback, map);
	}
	
	/**
	 * 发送短信验证码前的提示检查（最初版本）
	 * @param request
	 * @param mobile
	 * @return
	 */
	public Map sendSMSNotifyCheckV1(HttpServletRequest request,String mobile){
		Map map=new HashMap();
		String userSource = request.getParameter("userSource");//如果有传说明是新用户，还没注册走秀
		//如果不是新手机，则检测是否有资产
		String channelId = GlobalConstants.CHANNEL_ID;
		//根据用户名查询用户信息
		UserDetailDTO user = userService.getUserBasicInfoByLogonName(mobile, Integer.parseInt(channelId));
		if(user == null) {
			map.put("result", false);
			map.put("errorCode", ErrorCode.SendSMSCodeFailed.getErrorCode());
			map.put("errorMsg", ErrorCode.SendSMSCodeFailed.getErrorMsg());
			return map;
		}
		
		boolean hasAsset = userService.isUserHasAsset(String.valueOf(user.getUserId()));
		if(hasAsset) {
			if(StringUtil.isNotBlank(userSource)) {
			} else {
				map.put("result", false);
				map.put("errorCode", ErrorCode.UserHasAsset.getErrorCode());
				String userName= user.getPetName();
				if(userName!=null&&!userName.equals("")&&userName.indexOf("xiu_")!=0){
					userName="（"+userName+"）";
				}else{
					userName="";
				}
				String msg="是否解除与原账号"+userName+"的绑定，并绑定到当前账号上？";
				map.put("errorMsg", msg);
				return map;
			}
		}
		return null;
	}
	
	/**
	 * 发送短信验证码前的提示检查（4.0版本以上需求变更）
	 * @param request
	 * @param mobile
	 * @return
	 */
	public Map sendSMSnotifyCheckV400(HttpServletRequest request,String mobile){
		Map map=new HashMap();
		// 获取当前登录userId
		String oldUserId = SessionUtil.getUserId(request);
		String loginType = SessionUtil.getLoginTypeCookie(request);
		//第三方是老账号
		if(oldUserId!=null&&!oldUserId.equals("")){
			String channelId = GlobalConstants.CHANNEL_ID;
			// 查询当前用户登录信息
			UserBaseDTO oldUserBaseDTO = userService.getUserBasicInfoByUserId(Long.parseLong(oldUserId));
			if(oldUserBaseDTO == null) {
				map.put("result", false);
				map.put("errorCode", ErrorCode.SystemError.getErrorCode());
				map.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
				return map;
			}
			//检查老账户是否有资产
			UserDetailDTO oldUser = userService.getUserBasicInfoByLogonName(oldUserBaseDTO.getLogonName(), Integer.parseInt(channelId));
			//如果不是新手机，则检测是否有资产
			//根据用户名查询用户信息
			UserDetailDTO user = userService.getUserBasicInfoByLogonName(mobile, Integer.parseInt(channelId));
			if(oldUser==null||user == null) {
				map.put("result", false);
				map.put("errorCode", ErrorCode.SendSMSCodeFailed.getErrorCode());
				map.put("errorMsg", ErrorCode.SendSMSCodeFailed.getErrorMsg());
				return map;
			}
			//登录用户是否有资产
			boolean oldUserHasAsset = userService.isUserHasAsset(String.valueOf(oldUser.getUserId()));
			//手机用户是否有资产
			boolean hasAsset = userService.isUserHasAsset(String.valueOf(user.getUserId()));
			
	        if(loginType!=null&&loginType.equals(XiuConstant.LOGIN_TYPE_FEDERATE)){//第三方登录处理情况
				//如果登录用户无资产
				//或者如果登录用户有资产且手机账户有资产
				//则提示第三方登录用户需要解绑且绑定到手机账户
				if(!oldUserHasAsset||(oldUserHasAsset&&hasAsset)){
					map.put("result", false);
					map.put("errorCode", ErrorCode.UserThirdPartyHadBind.getErrorCode());
					String userName= oldUserBaseDTO.getPetName();
					if(userName!=null&&!userName.equals("")&&userName.indexOf("xiu_")!=0){
						userName="（"+userName+"）";
					}else{
						userName="";
					}
					String msg="是否解除与原账号"+userName+"的绑定，并绑定到手机账号上？";
					map.put("errorMsg", msg);
					return map;
				}
				//如果登录用户有资产且手机账户无资产
				//则提示手机号需要解绑且绑定到当前登录账户
				else if(oldUserHasAsset&&!hasAsset){
					map.put("result", false);
					map.put("errorCode", ErrorCode.UserPhoneHadBind.getErrorCode());
					String userName= user.getPetName();
					if(userName!=null&&!userName.equals("")&&userName.indexOf("xiu_")!=0){
						userName="（"+userName+"）";
					}else{
						userName="";
					}
					String msg="是否解除与原账号"+userName+"的绑定，并绑定到当前账号上？";
					map.put("errorMsg", msg);
					return map;
				}
	        }
	        ////其他登录情况，当手机账户有资产则提示，
	        //则提示手机号需要解绑且绑定到当前登录账户
	        else {
				map.put("result", false);
				map.put("errorCode", ErrorCode.UserHasAsset.getErrorCode());
				String userName= user.getPetName();
				if(userName!=null&&!userName.equals("")&&userName.indexOf("xiu_")!=0){
					userName="（"+userName+"）";
				}else{
					userName="";
				}
				String msg="是否解除与原账号"+userName+"的绑定，并绑定到当前账号上？";
				map.put("errorMsg", msg);
				return map;
	        }
		}
		return null;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/unionRegisterAndBind", produces = "text/html;charset=UTF-8")
	public String unionRegisterAndBindXiu(String jsoncallback,HttpServletRequest request, HttpServletResponse response) {
		logger.info("用户注册参数params="+request.getQueryString());
		// QQ和微信的都是openId, 支付宝的是user_id，和userSource一起在走秀里标识出唯一的来自第三方的用户
		String partnerId = request.getParameter("partnerId");
		String weChatOpenId = request.getParameter("weChatOpenId");
		//第三方登录名
		String logonName = request.getParameter("loginName");
		String thirdName = request.getParameter("thirdName");
		String thirdPortraitURL = request.getParameter("thirdPortraitURL");
		String deviceId = request.getParameter("deviceId");		//设备ID
		String remoteIp = HttpUtil.getRemoteIpAddr(request);	//IP地址
		String sysName = request.getParameter("sysName");		//系统名称
		String sysVersion = request.getParameter("sysVersion");	//系统版本
		
		// 登录渠道(pc-web:官网;mobile-wap:手机wap;android-app android应用;ios-app IOS应用)
		String loginChannel = request.getParameter("loginChannel");
		if ("".equals(loginChannel) || null == loginChannel) {
			loginChannel = "mobile-wap";
		}
		try {
			if (null != thirdName) {
				thirdName = URLDecoder.decode(thirdName,"UTF-8");
			}
			if (null != thirdPortraitURL) {
				thirdPortraitURL = URLDecoder.decode(thirdPortraitURL, "UTF-8");
			}
		} catch (UnsupportedEncodingException e1) {
			logger.error("decode第三方原名和头像异常：");
		}
		
		String userSource = request.getParameter("userSource");
		String smsCode = request.getParameter("smsCode");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if(StringUtils.isEmpty(logonName) || StringUtils.isEmpty(partnerId) || StringUtils.isEmpty(smsCode) ) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}

		try {
			// 短信验证码验证start
			MessageCode messageCode = messageCodeService.findMessageCodeByPhone(logonName);
			if (messageCode != null) {
				String oldSmsCode = messageCode.getMsgCode();
				String sendCodeTime = messageCode.getTimestamp();
				String timeFlag = messageCode.getTimeFlag();
				String contentFlag = messageCode.getContentFlag();

				// 不同的手机验证码不同
				if (!StringUtils.isEmpty(oldSmsCode) && !StringUtils.isEmpty(sendCodeTime) && timeFlag.equals("N") && contentFlag.equals("N")) {
					long dif = DateUtil.diffDateTime(new Date(), new Date(Long.valueOf(sendCodeTime)));

					// 时间差大于规定时间则验证码失效
					if (dif > this.DISSECOND) {
						// 时间失效
						messageCode.setTimeFlag("Y");
						messageCodeService.updateMessageCode(messageCode);
						resultMap.put("result", false);
						resultMap.put("errorCode", ErrorCode.VerifyCodeTimeOut.getErrorCode());
						resultMap.put("errorMsg", ErrorCode.VerifyCodeTimeOut.getErrorMsg());
						return JsonUtils.bean2jsonP(jsoncallback, resultMap);
					} else {
						// 验证验证码的正确性
						if (smsCode.equals(oldSmsCode)) {
							// 删除验证码
							messageCodeService.removeMessageCodeByPhone(logonName);
						} else {
							resultMap.put("result", false);
							resultMap.put("errorCode", ErrorCode.VerifyCodeFailed.getErrorCode());
							resultMap.put("errorMsg", "请输入正确的验证码");
							logger.info("校验验证码失败：{Tel: " + logonName + ", smsCode: " + smsCode + "}");
							return JsonUtils.bean2jsonP(jsoncallback, resultMap);
						}
					}
				}else {
					resultMap.put("result", false);
					resultMap.put("errorCode", ErrorCode.VerifyCodeFailed.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.VerifyCodeFailed.getErrorMsg());
					logger.error("checkVerifyCode==>验证码为空，没有找到验证码记录");
					return JsonUtils.bean2jsonP(jsoncallback, resultMap);
				}
			} else {
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.VerifyCodeFailed.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.VerifyCodeFailed.getErrorMsg());
				logger.error("checkVerifyCode==>验证码为空，没有找到验证码记录");
				return JsonUtils.bean2jsonP(jsoncallback, resultMap);
			}
			// 短信验证码验证end
			
			Map<String, Object> params = new HashMap<String, Object>();
			//解绑原有第三方，绑定新的第三方
			// 联合登陆用户来源 ：tencent_qq(QQ),alipay(支付宝),sina_weibo(新浪微博),netEase(网易) ,139(139邮箱),51fanli(返利网)
			params.put("userSource", userSource);
			// 添加IP信息，登录，获取用户状态等接口需要。
			params.put("loginChannel", loginChannel);
			params.putAll(getDeviceParams(request));// 设备信息
			params.put("deviceId", deviceId);
			params.put("sysName", sysName);
			params.put("sysVersion", sysVersion);
			params.put("partnerId", partnerId);
			params.put("weChatOpenId", weChatOpenId);
			params.put("thirdName", thirdName);
			params.put("thirdPortraitURL", thirdPortraitURL);
			params.put("partnerId", partnerId);
			//验证手机是否有绑定
			boolean newMobile = userService.isLogonNameCanRegister(logonName);
			boolean sameThirdExists = false; //是否存在同类第三方标志
			if(!newMobile) {//如果手机已经是注册
				String channelId = GlobalConstants.CHANNEL_ID;
				//根据用户名查询用户信息
				UserDetailDTO user = userService.getUserBasicInfoByLogonName(logonName, Integer.parseInt(channelId));
				if(user != null) {
					//如果用户存在同类型的第三方渠道，则解绑原有第三方，绑定新的第三方
					List<BindUserInfo> bindUserInfoList = loginService.getBindUserInfoListByUserId(user.getUserId());
					BindUserInfo bindUserInfo = null;
					if(bindUserInfoList != null && bindUserInfoList.size() > 0 && StringUtil.isNotBlank(userSource)) {
						for(BindUserInfo bindInfo : bindUserInfoList) {
							if(userSource.equals(bindInfo.getUserSource())) {
								bindUserInfo = bindInfo;
								sameThirdExists = true;
								break;
							}
						}
					}
					params.put("userId", String.valueOf(user.getUserId()));
					
					if(sameThirdExists) {//解绑原有第三方，绑定新的第三方
						params.put("partnerId", bindUserInfo.getPartnerId());
						
						boolean flag = loginService.unBindUser(params); //解绑
						if(flag) {
							params.put("partnerId", partnerId);
							flag = loginService.bindUser(params); //绑定
							if(!flag) {
								resultMap.put("result", false);
								resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
								resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
								return JsonUtils.bean2jsonP(jsoncallback, resultMap);
							}
						} else {
							resultMap.put("result", false);
							resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
							resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
							return JsonUtils.bean2jsonP(jsoncallback, resultMap);
						}
					} else {//绑定新的第三方
						boolean flag = loginService.bindUser(params); //绑定
						if(!flag) {
							resultMap.put("result", false);
							resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
							resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
							return JsonUtils.bean2jsonP(jsoncallback, resultMap);
						}
					}
				}

				//联合登录
				LoginResVo loginResVo = loginService.federateLogin(params);
				SessionUtil.addLoginTypeCookie(response, XiuConstant.LOGIN_TYPE_FEDERATE);//表示为第三方登录

				resultMap.put("result", true);
				//已绑定过
				if (loginResVo != null) {
					// 登录成功后，将登录后的用户信息放入到cookie中
					SessionUtil.addLoginCookie(response, loginResVo);
					UserDetailDTO userDetailDTO = userService.getUserDetailDTOByUserId(Long.parseLong(loginResVo.getUserId()));
					resultMap.put("bindStatus", true);
					resultMap.put("userInfo", getUserInfo(userDetailDTO));
					resultMap.put("isFirstChangeName", getStatus(userDetailDTO));
					String wurl = SessionUtil.getWurl(request);
					wurl = StringUtils.isEmpty(wurl) ? (request.getContextPath() + "/activity/toActivityIndex/.shtml") : wurl;
					resultMap.put("wurl", wurl);// 注册成功后，跳转到要跳转的页面
				}
				
			}else{//(!newMobile) 如果手机没注册过
				Map<String, Object> pmap = new HashMap<String, Object>();
				pmap.put("retAddressAndCoupon", "0");
				pmap.put("partnerId", partnerId);
				pmap.put("logonName", logonName);
				pmap.put("weChatOpenId", weChatOpenId);
				pmap.put("thirdName", thirdName);
				pmap.put("thirdPortraitURL", thirdPortraitURL);
				pmap.put("userSource", userSource);
				pmap.put("loginChannel", loginChannel);
				pmap.put("deviceId", deviceId);
				pmap.put("sysName", sysName);
				pmap.put("sysVersion", sysVersion);
				pmap.putAll(getDeviceParams(request));// 设备信息
				//注册新账号
				LoginResVo lrv = loginService.unionLoginOrReg(pmap);
				SessionUtil.addLoginTypeCookie(response, XiuConstant.LOGIN_TYPE_FEDERATE);//表示为第三方登录

				// 注册成功后账号绑定处理
				if (lrv != null) {
					SessionUtil.addLoginCookie(response, lrv);
					pmap.put("userId", lrv.getUserId());
					//绑定手机
					UserOperateLogInfoDTO userOperateLogInfoDTO = new UserOperateLogInfoDTO();
					userOperateLogInfoDTO.setDeviceNum(deviceId);
					userOperateLogInfoDTO.setOperAddr(remoteIp);
					userOperateLogInfoDTO.setSysName(sysName);
					userOperateLogInfoDTO.setSysNum(sysVersion);
					
					ModifyUserAuthenDTO modifyUserAuthenDTO = new ModifyUserAuthenDTO();
					modifyUserAuthenDTO.setUserId(Long.parseLong(lrv.getUserId()));
					modifyUserAuthenDTO.setAuthenType("1");
					modifyUserAuthenDTO.setAuthenValue(logonName);
					modifyUserAuthenDTO.setIpAddr(remoteIp);
					userOperateLogInfoDTO.setModifyUserAuthenDTO(modifyUserAuthenDTO);
					
					userService.modifyUserAuthen(userOperateLogInfoDTO);
					
					// 绑定用户
					boolean flag = loginService.bindUser(pmap);
					if (flag) {
						//修改用户昵称
						userService.updateUserPetName(lrv.getUserId(), thirdName);
						//检查并插入推荐注册信息
						String fromId = SessionUtil.getRegisterFrom(request);
						try {
							if (!StringUtils.isEmpty(fromId)) {
								RecommendRegBo bo = new RecommendRegBo();
								bo.setCustomerId(Long.parseLong(fromId));
								bo.setRegSSOUserId(Long.parseLong(lrv.getUserId()));
								bo.setLogonName(logonName);
								int flagReg = registerService.insertRecommendRegister(bo);
								if ( flagReg == -1){
									logger.error("检查并插入推荐注册信息失败,fromId:  " +fromId +", userId:" + lrv.getUserId() );
								}
							}
						} catch (Exception e) {
							logger.error("检查并插入推荐注册信息异常,fromId:  " +fromId +", userId:" + lrv.getUserId() ,e);
						}

						String wurl = SessionUtil.getWurl(request);
						wurl = StringUtils.isEmpty(wurl) ? (request.getContextPath() + "/activity/toActivityIndex/.shtml") : wurl;
						resultMap.put("result", true);
						UserDetailDTO userDetailDTO = userService.getUserDetailDTOByUserId(Long.parseLong(lrv.getUserId()));
						resultMap.put("userInfo", getUserInfo(userDetailDTO));
						resultMap.put("isFirstChangeName", getStatus(userDetailDTO));
						resultMap.put("wurl", wurl);// 注册成功后，跳转到要跳转的页面
					}else{
						resultMap.put("result", false);
						resultMap.put("errorCode", ErrorCode.UserBindError.getErrorCode());
						resultMap.put("errorMsg", ErrorCode.UserBindError.getErrorMsg());
						return JsonUtils.bean2jsonP(jsoncallback, resultMap);
					}
				} else {
					resultMap.put("result", false);
					resultMap.put("errorCode", ErrorCode.RegisterAccountFailed.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.RegisterAccountFailed.getErrorMsg());
					return JsonUtils.bean2jsonP(jsoncallback, resultMap);
				}
			}
		}catch (EIBusinessException e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", e.getExtErrCode());
			resultMap.put("errorMsg", e.getExtMessage());
			resultMap.put("wurl", request.getContextPath() + "/loginReg/toReg.shtml");// 注册失败后
			logger.error("联合登录账号注册并绑定走秀账号{errorCode：" + e.getExtErrCode()
					+ ", errorMsg：" + e.getExtMessage() + "}");
		}catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("联合登录账号注册并绑定走秀账号时发生异常：", e);
		}

		logger.info("联合登录账号注册并绑定走秀账号接口返回结果：" + JSON.toJSONString(resultMap));
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	

	/**
	 * 第三方登录 绑定手机
	 * @param jsoncallback
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/federateLoginBindMobile", produces = "text/html;charset=UTF-8" )
	public String federateLoginBindMobile(String jsoncallback,HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		String mobile = request.getParameter("mobile"); //手机号
		String validateCode = request.getParameter("validateCode");
		
		String userSource = request.getParameter("userSource");
		// QQ和微信的都是openId, 支付宝的是user_id，和userSource一起在走秀里标识出唯一的来自第三方的用户
		String partnerId = request.getParameter("partnerId");
		String weChatOpenId = request.getParameter("weChatOpenId");
		// 登录渠道(pc-web:官网;mobile-wap:手机wap;android-app android应用;ios-app IOS应用)
		String loginChannel = request.getParameter("loginChannel");
		String remoteIp = HttpUtil.getRemoteIpAddr(request);
		String deviceId = request.getParameter("deviceId");		//设备ID
		String sysName = request.getParameter("sysName");		//系统名称
		String sysVersion = request.getParameter("sysVersion");	//系统版本
		String thirdName = request.getParameter("thirdName");	//第三方名称
		String thirdPortraitURL = request.getParameter("thirdPortraitURL");	//第三方头像

		//检验参数
		if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(validateCode)|| StringUtils.isEmpty(partnerId)) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			result.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, result);
		}
		
		try {
			MessageCode messageCode = messageCodeService.findMessageCodeByPhone(mobile);
			if (messageCode != null) {
				String smsCode = messageCode.getMsgCode();
				String sendCodeTime = messageCode.getTimestamp();
				String timeFlag = messageCode.getTimeFlag();
				String contentFlag = messageCode.getContentFlag();

				// 不同的手机验证码不同
				if (!StringUtils.isEmpty(smsCode) && !StringUtils.isEmpty(sendCodeTime) && timeFlag.equals("N") && contentFlag.equals("N")) {
					long dif = DateUtil.diffDateTime(new Date(), new Date(Long.valueOf(sendCodeTime)));
					// 时间差大于规定时间则验证码失效
					if (dif > this.DISSECOND) {
						// 时间失效
						messageCode.setTimeFlag("Y");
						messageCodeService.updateMessageCode(messageCode);
						result.put("result", false);
						result.put("errorCode", ErrorCode.VerifyCodeHasTimeOut.getErrorCode());
						result.put("errorMsg", ErrorCode.VerifyCodeHasTimeOut.getErrorMsg());
					} else {
						// 验证验证码的正确性
						if (validateCode.equals(smsCode)) {
							// 内容已验证
							messageCode.setContentFlag("Y");
							messageCodeService.updateMessageCode(messageCode);
							
							// 获取userId
							String oldUserId = SessionUtil.getUserId(request);
							// 查询当前用户登录信息
							UserBaseDTO oldUserBaseDTO = userService.getUserBasicInfoByUserId(Long.parseLong(oldUserId));
							if(oldUserBaseDTO == null) {
								result.put("result", false);
								result.put("errorCode", ErrorCode.SystemError.getErrorCode());
								result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
								return JsonUtils.bean2jsonP(jsoncallback, result);
							}
							// 如果当前用户电话为空 则直接绑定新号码
							if (oldUserBaseDTO != null && oldUserBaseDTO.getMobile()!=null && oldUserBaseDTO.getMobile().equals(mobile)) {
								result.put("result", false);
								result.put("errorCode", ErrorCode.PhoneValidateIsSame.getErrorCode());
								result.put("errorMsg", ErrorCode.PhoneValidateIsSame.getErrorMsg());
							} else {
									result=bindMobileByFederateLogin(request,response);
							}
						}else{
							result.put("result", false);
							result.put("errorCode", ErrorCode.VerifyCodeHasFailed.getErrorCode());
							result.put("errorMsg", ErrorCode.VerifyCodeHasFailed.getErrorMsg());
							logger.info("校验验证码失败：{Tel: " + mobile + ", smsCode: " + validateCode + "}");
						}
					}
				}else{
					result.put("result", false);
					result.put("errorCode", ErrorCode.VerifyCodeHasFailed.getErrorCode());
					result.put("errorMsg", ErrorCode.VerifyCodeHasFailed.getErrorMsg());
					logger.info("校验验证码失败：{Tel: " + mobile + ", smsCode: " + validateCode + "}");
				}
			} else {
				result.put("result", false);
				result.put("errorCode", ErrorCode.VerifyCodeHasFailed.getErrorCode());
				result.put("errorMsg", ErrorCode.VerifyCodeHasFailed.getErrorMsg());
				logger.error("验证码为空，没有找到验证码记录。手机号："+mobile);
			}
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("绑定手机时发生异常，手机号："+mobile+"，错误信息：", e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/**
	 * 第三方手机绑定处理
	 * @return
	 */
	public Map bindMobileByFederateLogin(HttpServletRequest request ,HttpServletResponse response){
		Map<String, Object> result = new HashMap<String, Object>();
		String mobile = request.getParameter("mobile");
		String userSource = request.getParameter("userSource");
		// QQ和微信的都是openId, 支付宝的是user_id，和userSource一起在走秀里标识出唯一的来自第三方的用户
		String partnerId = request.getParameter("partnerId");
		String weChatOpenId = request.getParameter("weChatOpenId");
		// 登录渠道(pc-web:官网;mobile-wap:手机wap;android-app android应用;ios-app IOS应用)
		String loginChannel = request.getParameter("loginChannel");
		String remoteIp = HttpUtil.getRemoteIpAddr(request);
		String deviceId = request.getParameter("deviceId");		//设备ID
		String sysName = request.getParameter("sysName");		//系统名称
		String sysVersion = request.getParameter("sysVersion");	//系统版本
		String thirdName = request.getParameter("thirdName");	//第三方名称
		String thirdPortraitURL = request.getParameter("thirdPortraitURL");	//第三方头像
		
		// 查询当前用户登录信息
		String oldUserId = SessionUtil.getUserId(request);
		Long oldUserIdL=ObjectUtil.getLong(oldUserId);
		UserDetailDTO oldUserBaseDTO = userService.getUserDetailDTOByUserId(oldUserIdL);
		Long loginUserId=oldUserIdL;//当前登录用户ID
		//检测是否新手机
		boolean newMobile = userService.isLogonNameCanRegister(mobile);
		//如果不是新手机
		if(!newMobile) {
			String channelId = GlobalConstants.CHANNEL_ID;
			//根据用户名查询手机账户用户信息
			UserDetailDTO mobileUser = userService.getUserBasicInfoByLogonName(mobile, Integer.parseInt(channelId));
			if(mobileUser != null) {
				//登录用户是否有资产
				boolean oldUserHasAsset = userService.isUserHasAsset(String.valueOf(oldUserBaseDTO.getUserId()));
				//手机用户是否有资产
				boolean hasAsset = userService.isUserHasAsset(String.valueOf(mobileUser.getUserId()));
				//如果登录用户无资产
				//或者如果登录用户有资产且手机账户有资产
				//则第三方登录用户需要解绑且绑定到手机账户,且短信提示
				if(!oldUserHasAsset||(oldUserHasAsset&&hasAsset)){
					//#登录账号解绑该第三方账号
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("partnerId", partnerId);
					params.put("weChatOpenId", weChatOpenId);
					// 联合登陆用户来源 ：tencent_qq(QQ),alipay(支付宝),sina_weibo(新浪微博),netEase(网易) ,139(139邮箱),51fanli(返利网)
					params.put("userSource", userSource);
					params.putAll(getDeviceParams(request));// 设备信息
					// 添加IP信息，登录，获取用户状态等接口需要。
					params.put("loginChannel", loginChannel);
					params.put("userId", oldUserBaseDTO.getUserId().toString());
					params.put("deviceId", deviceId);
					params.put("sysName", sysName);
					params.put("sysVersion", sysVersion);
					params.put("thirdName", thirdName);
					params.put("thirdPortraitURL", thirdPortraitURL);
					
					boolean flag = loginService.unBindUser(params);
					//#解绑成功，则绑定到手机账户且发短信提示
					if(flag) {
						//#绑定到手机账户
						params.put("userId", mobileUser.getUserId().toString());
						flag = loginService.bindUser(params); //绑定
						if(flag){
							//#发送短信
							//检测登录账号是否是空密码
							boolean isEmptyPassword = userService.isEmptyPassword(oldUserBaseDTO.getUserId());
							String password = "";
							if(isEmptyPassword) {
								//如果密码为空，则生成6位随机密码，发送短信
								password = Tools.getRandomPassword();
							}
							String thirdAccountTypeName=getPartnerChannel(userSource);
							String account = getUserAccount(oldUserBaseDTO);
							String smsBody = "亲爱的xiu.com用户，您的"+thirdAccountTypeName+"账号已绑定到当前手机账户，原账户可通过"
									+ account + "和密码"+password+"登录查询，感谢您的支持！";
							logger.info("用户手机号码："+ mobile + ",信息内容：" + smsBody);
							boolean sendFlag = iSMSService.sendSMS(smsBody, mobile, "", mobile);
							logger.info("短信发送状态：sendFlag=" + sendFlag);
							logger.info("绑定手机短信发送内容：smsBody=" + smsBody);
							
							if(isEmptyPassword && sendFlag) {
								//如果生成了密码，则修改用户的密码
								userService.resetUserPassword(oldUserBaseDTO.getUserId(), password);
							}
							
							//需要重新登录
							 params = new HashMap<String, Object>();
							//解绑原有第三方，绑定新的第三方
							// 联合登陆用户来源 ：tencent_qq(QQ),alipay(支付宝),sina_weibo(新浪微博),netEase(网易) ,139(139邮箱),51fanli(返利网)
							params.put("userSource", userSource);
							// 添加IP信息，登录，获取用户状态等接口需要。
							params.put("loginChannel", loginChannel);
							params.putAll(getDeviceParams(request));// 设备信息
							params.put("deviceId", deviceId);
							params.put("sysName", sysName);
							params.put("sysVersion", sysVersion);
							params.put("partnerId", partnerId);
							params.put("weChatOpenId", weChatOpenId);
							params.put("partnerId", partnerId);
							//联合登录
							LoginResVo loginResVo = loginService.federateLogin(params);
							result.put("result", true);
							//已绑定过
							if (loginResVo != null) {
								loginUserId=ObjectUtil.getLong(loginResVo.getUserId());//登录用户需要切换到手机用户
								SessionUtil.deleteLoginCookie(response);//先清除
								// 登录成功后，将登录后的用户信息放入到cookie中
								SessionUtil.addLoginCookie(response, loginResVo);
							}
						}
					}
					if(!flag){
						result.put("result", false);
						result.put("errorCode", ErrorCode.UserBindError.getErrorCode());
						result.put("errorMsg", ErrorCode.UserBindError.getErrorMsg());
						 return result;
					}
					
				}
				//如果登录用户有资产且手机账户无资产
				//则提示手机号需要解绑且绑定到当前登录账户
				else if(oldUserHasAsset&&!hasAsset){
					//#解绑手机账户
					Boolean flag=userService.deleteUserAuthen(mobileUser.getUserId(), "1", mobile, remoteIp);
					if(flag){
						//#绑定手机号到当前登录账户
						UserOperateLogInfoDTO userOperateLogInfoDTO = new UserOperateLogInfoDTO();
						userOperateLogInfoDTO.setDeviceNum(deviceId);
						userOperateLogInfoDTO.setOperAddr(remoteIp);
						userOperateLogInfoDTO.setSysName(sysName);
						userOperateLogInfoDTO.setSysNum(sysVersion);
						
						ModifyUserAuthenDTO modifyUserAuthenDTO = new ModifyUserAuthenDTO();
						modifyUserAuthenDTO.setUserId(oldUserBaseDTO.getUserId());
						modifyUserAuthenDTO.setAuthenType("1");
						modifyUserAuthenDTO.setAuthenValue(mobile);
						modifyUserAuthenDTO.setIpAddr(remoteIp);
						userOperateLogInfoDTO.setModifyUserAuthenDTO(modifyUserAuthenDTO);
						
						flag=userService.modifyUserAuthen(userOperateLogInfoDTO);
						if(flag){
							//#发短信通知
							//检测账号是否是空密码
							boolean isEmptyPassword = userService.isEmptyPassword(mobileUser.getUserId());
							String password = "";
							if(isEmptyPassword) {
								//如果密码为空，则生成6位随机密码，发送短信
								password = Tools.getRandomPassword();
							}
							mobileUser.setMobile(null);//手机用户的手机已经解绑
							String account = getUserAccount(mobileUser);
							//发送短信
							String smsBody = "亲爱的xiu.com用户，您的手机号已绑定到当前账户，原账户可通过"
									+ account + "和密码"+password+"登录查询，感谢您的支持！";
							logger.info("用户手机号码："+ mobile + ",信息内容：" + smsBody);
							boolean sendFlag = iSMSService.sendSMS(smsBody, mobile, "", mobile);
							logger.info("短信发送状态：sendFlag=" + sendFlag);
							logger.info("绑定手机短信发送内容：smsBody=" + smsBody);
							
							if(isEmptyPassword && sendFlag) {
								//如果生成了密码，则修改用户的密码
								userService.resetUserPassword(mobileUser.getUserId(), password);
							}
						}
					}
					if(!flag){
						result.put("result", false);
						result.put("errorCode", ErrorCode.UserBindError.getErrorCode());
						result.put("errorMsg", ErrorCode.UserBindError.getErrorMsg());
						return result;
					}
				}
			}
		}
		//新手机情况
		else{
			//#绑定手机号到当前登录账户
			UserOperateLogInfoDTO userOperateLogInfoDTO = new UserOperateLogInfoDTO();
			userOperateLogInfoDTO.setDeviceNum(deviceId);
			userOperateLogInfoDTO.setOperAddr(remoteIp);
			userOperateLogInfoDTO.setSysName(sysName);
			userOperateLogInfoDTO.setSysNum(sysVersion);
			
			ModifyUserAuthenDTO modifyUserAuthenDTO = new ModifyUserAuthenDTO();
			modifyUserAuthenDTO.setUserId(oldUserBaseDTO.getUserId());
			modifyUserAuthenDTO.setAuthenType("1");
			modifyUserAuthenDTO.setAuthenValue(mobile);
			modifyUserAuthenDTO.setIpAddr(remoteIp);
			userOperateLogInfoDTO.setModifyUserAuthenDTO(modifyUserAuthenDTO);
			Boolean flag= userService.modifyUserAuthen(userOperateLogInfoDTO);
			if(!flag){
				result.put("result", false);
				result.put("errorCode", ErrorCode.UserBindError.getErrorCode());
				result.put("errorMsg", ErrorCode.UserBindError.getErrorMsg());
				return result;
			}
		}
		
		
		//返回用户信息
		UserDetailDTO userDetailDTO = userService.getUserDetailDTOByUserId(loginUserId);
		result.put("userInfo", getUserInfo(userDetailDTO));
		
		result.put("result", true);
		result.put("errorCode", ErrorCode.Success.getErrorCode());
		result.put("errorMsg", ErrorCode.Success.getErrorMsg());
	  return result;
	}
	
	
	
}
