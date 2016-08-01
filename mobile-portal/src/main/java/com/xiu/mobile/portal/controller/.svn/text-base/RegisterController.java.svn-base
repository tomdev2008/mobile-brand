package com.xiu.mobile.portal.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xiu.mobile.core.exception.EIBusinessException;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.constants.LoginRegConstant;
import com.xiu.mobile.portal.common.constants.MobileCommonConstant;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.AESCipher;
import com.xiu.mobile.portal.common.util.EncryptUtils;
import com.xiu.mobile.portal.common.util.SMSTemplateUtil;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.constants.GlobalConstants;
import com.xiu.mobile.portal.facade.utils.DateUtil;
import com.xiu.mobile.portal.facade.utils.HttpUtil;
import com.xiu.mobile.portal.model.LoginResVo;
import com.xiu.mobile.portal.model.MessageCode;
import com.xiu.mobile.portal.model.RecommendRegBo;
import com.xiu.mobile.portal.service.IEmailService;
import com.xiu.mobile.portal.service.IMessageCodeService;
import com.xiu.mobile.portal.service.IRegisterService;
import com.xiu.mobile.portal.service.ISMSService;
/**
 * <p>
 * ************************************************************** 
 * @Description: 用户注册控制器
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-13 下午7:39:54 
 * ***************************************************************
 * </p>
 */
@Controller
@RequestMapping("/loginReg")
public class RegisterController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
	
	@Autowired
	private IRegisterService registerService;
	@Autowired
	private ISMSService iSMSService;
	@Autowired
	private IEmailService emailService;
	@Autowired
	private IMessageCodeService messageCodeService;
	
	private final long DISSECOND = LoginRegConstant.SMS_DIS_SECOND;// 验证码失效时间
	
	private final long DISSECOND_LONG = 180;// 验证码失效时间
	
	/**
	 * 发送验证码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sendVerifyCode", produces = "text/html;charset=UTF-8")
	public String sendSmsVerifyCode(String jsoncallback, HttpServletRequest request) {
		String logonName = request.getParameter("memberVo.telphone");
		// 验证码 或 设备号
		String validateCode = request.getParameter("validateCode");
		Map<String, Object> map = new HashMap<String, Object>();
		String deviceId = request.getParameter("deviceId");	//设备号
		
		try {
			if (!StringUtils.isEmpty(validateCode)) {
				//通过图形验证码验证
				String validateImageCode = "";
				Object validateImage = request.getSession().getAttribute("validateImage");
				if (validateImage!=null) {
					validateImageCode = validateImage.toString();
				}
				
				if (!validateCode.equalsIgnoreCase(validateImageCode)) {
					map.put("result", false);
					map.put("errorCode", ErrorCode.ValidateCodeError.getErrorCode());
					map.put("errorMsg", ErrorCode.ValidateCodeError.getErrorMsg());
					return JsonUtils.bean2jsonP(jsoncallback, map);
				}
			} 
			
			// 存储返回结果值
			if (!StringUtils.isEmpty(logonName)) {
				String userSource = request.getParameter("userSource");
				if (!registerService.checkLogonNameIsExist(logonName, userSource)) {
					
					boolean limitStatus = false;
					
					if(!StringUtils.isEmpty(deviceId)) {
						Map<String,Object> paraMap = new HashMap<String,Object>();
						paraMap.put("mobile", logonName);
						paraMap.put("deviceId", deviceId);
						paraMap.put("type", MobileCommonConstant.SMS_VALIDATE_TIMES_REGISTER);
						paraMap.put("limitTimes", MobileCommonConstant.SMS_LIMIT_TIMES);
						//验证获取短信验证码是否超过次数
						limitStatus = messageCodeService.getSMSLimitStatus(paraMap);
					}
					
					if(limitStatus) {
						map.put("result", false);
						map.put("errorCode", ErrorCode.getSMSOverLimitTimes.getErrorCode());
						map.put("errorMsg", "操作太频繁了，请1个小时后再试或联系客服");
					} else {
						// 获取验证码
						String smsCode =messageCodeService.getMessageCodeByPhone(logonName);
						// 获取注册短信内容
						String smsBody = SMSTemplateUtil.getRegisterBody(smsCode);
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
						map.put("result", true);
						map.put("errorCode", ErrorCode.Success.getErrorCode());
						map.put("errorMsg", ErrorCode.Success.getErrorMsg());
					}
				} else {
					map.put("result", false);
					map.put("errorCode", ErrorCode.LogonNameIsExist.getErrorCode());
					map.put("errorMsg", ErrorCode.LogonNameIsExist.getErrorMsg());
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
	
	/**
	 * 发送验证邮箱
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sendVerifyEmail", produces = "text/html;charset=UTF-8")
	public String sendVerifyEmail(String jsoncallback, HttpServletRequest request) {
		String logonName = request.getParameter("memberVo.telphone");
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			// 存储返回结果值
			if (!StringUtils.isEmpty(logonName)) {
				String userSource = getDeviceParams(request).get("userSource");
				if (!registerService.checkLogonNameIsExist(logonName, userSource)) {
					String emailAddress = "951460685@qq.com";
					String subject = "密码找回测试";
					String body = "验证码为：1234";	
					String creator = "zouxiu";
					String senderName = "zouxiu";
					boolean flag = emailService.sendEmail(emailAddress, subject, body, creator, senderName);
					if (flag) {
						map.put("result", true);
						map.put("errorCode", ErrorCode.Success.getErrorCode());
						map.put("errorMsg", ErrorCode.Success.getErrorMsg());
					}else {
						map.put("result", false);
						map.put("errorCode", ErrorCode.SystemError.getErrorCode());
						map.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
					}
				} else {
					map.put("result", false);
					map.put("errorCode", ErrorCode.LogonNameIsExist.getErrorCode());
					map.put("errorMsg", ErrorCode.LogonNameIsExist.getErrorMsg());
				}
			}else {
				map.put("result", false);
				map.put("errorCode", ErrorCode.LogonNameIsNull.getErrorCode());
				map.put("errorMsg", ErrorCode.LogonNameIsNull.getErrorMsg());
			}
		}catch (Exception e) {
			map.put("result", false);
			map.put("errorCode", ErrorCode.SystemError.getErrorCode());
			map.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("发送邮箱验证时发生异常：", e);
		}

		logger.info("发送邮箱验证接口返回结果：" + JSON.toJSONString(map));
		return JsonUtils.bean2jsonP(jsoncallback, map);
	}
	
	/**
	 * 校验 验证码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkVerifyCode", produces = "text/html;charset=UTF-8")
	public String checkVerifyCode(String jsoncallback, String smsCode, 
			HttpServletRequest request) {
		String logonName = request.getParameter("memberVo.telphone");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			// 验证是否成功
			boolean iSuccess = false;
			
			if (!StringUtils.isEmpty(logonName) && !StringUtils.isEmpty(smsCode)) {
				MessageCode messageCode = messageCodeService.findMessageCodeByPhone(logonName);
				
				if (messageCode != null) {
					String oldSmsCode = messageCode.getMsgCode();
					String sendCodeTime = messageCode.getTimestamp();
					String timeFlag = messageCode.getTimeFlag();
					String contentFlag = messageCode.getContentFlag();

					// 不同的手机验证码不同
					if (!StringUtils.isEmpty(oldSmsCode)
							&& !StringUtils.isEmpty(sendCodeTime)
							&& timeFlag.equals("N") && contentFlag.equals("N")) {
						long dif = DateUtil.diffDateTime(new Date(), new Date(
								Long.valueOf(sendCodeTime)));
						
						// 时间差大于规定时间则验证码失效
						if (dif > this.DISSECOND) {
							// 时间失效
							messageCode.setTimeFlag("Y");
							messageCodeService.updateMessageCode(messageCode);
							resultMap.put("result", false);
							resultMap.put("errorCode", ErrorCode.VerifyCodeTimeOut.getErrorCode());
							resultMap.put("errorMsg", ErrorCode.VerifyCodeTimeOut.getErrorMsg());
						} else {
							// 验证验证码的正确性
							if (smsCode.equals(oldSmsCode)) {
								iSuccess = true;
								// 内容已验证
								messageCode.setContentFlag("Y");
								messageCodeService.updateMessageCode(messageCode);
								resultMap.put("result", true);
								resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
								resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
							} else {
								resultMap.put("result", false);
								resultMap.put("errorCode", ErrorCode.VerifyCodeFailed.getErrorCode());
								resultMap.put("errorMsg", "请输入正确的验证码");
								return JsonUtils.bean2jsonP(jsoncallback, resultMap);
							}
						}
					}
				} else {
					resultMap.put("result", false);
					resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
					logger.error("checkVerifyCode==>验证码为空，没有找到验证码记录");
				}
			}

			if (!iSuccess) {
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.VerifyCodeFailed.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.VerifyCodeFailed.getErrorMsg());
				logger.info("校验验证码失败：{Tel: " + logonName + ", smsCode: " + smsCode + "}");
			}

		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.CheckSMSCodeError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.CheckSMSCodeError.getErrorMsg());
			logger.error("校验短信验证码发生异常：" + e.getMessage());
		}

		logger.info("校验短信验证码接口返回结果：" + JSON.toJSONString(resultMap));
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}

	/**
	 * 用户注册
	 */
	@ResponseBody
	@RequestMapping(value = "/submitRegister", produces = "text/html;charset=UTF-8")
	public String submitRegister(String jsoncallback,HttpServletRequest request, HttpServletResponse response) {
		logger.info("用户注册参数params="+request.getQueryString());
		String logonName = request.getParameter("memberVo.telphone");
		String encryptFlag = request.getParameter("encryptFlag");
		String password = request.getParameter("memberVo.password");
		String regType = request.getParameter("memberVo.regType");
		//添加用户注册来源标识
		String userSour=request.getParameter("userSource") ;
		Integer userSource=150;
		if("".equals(userSour)||null==userSour){
			userSource=150;
		}else{
			userSource=Integer.parseInt(userSour);
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if(StringUtils.isEmpty(logonName) || StringUtils.isEmpty(password)) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.ParamsError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.ParamsError.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}

		try {
			// 加密验证码
			MessageCode messageCode = messageCodeService.findMessageCodeByPhone(logonName);
			if (messageCode != null) {
				String oldVerifyResult = messageCode.getMsgCode();
				String timeFlag = messageCode.getTimeFlag();
				String contentFlag = messageCode.getContentFlag();
				if (!StringUtils.isEmpty(oldVerifyResult) && timeFlag.equals("N") && contentFlag.equals("Y")) {
					// 删除验证码
					messageCodeService.removeMessageCodeByPhone(logonName);
				}

				// 密码不为空，encryptFlag为Y
				if(!StringUtils.isEmpty(password) && "Y".equals(encryptFlag)){
					// password解密(AES)
					String aesKey=EncryptUtils.getAESKeyShare();
					//password=EncryptUtils.encryptByAES(password, aesKey);
					password=EncryptUtils.decryptPassByAES(password, aesKey);
					
				}
				
				Map<String, Object> regParams = new HashMap<String, Object>();
				password = new AESCipher(XiuConstant.SAFE_CODE).encrypt(password);
				regParams.put("regType", regType);
				regParams.put("logonName", logonName);
				regParams.put("password", password);
				// 添加IP信息，登录，获取用户状态等接口需要。
				String remoteIp = HttpUtil.getRemoteIpAddr(request);
				regParams.put(GlobalConstants.KEY_REMOTE_IP, remoteIp);
				regParams.putAll(getDeviceParams(request));// 设备信息
				regParams.put("userSource",userSource);
				LoginResVo lrv = registerService.register(regParams);
				// String tokenId = lrv.getTokenId();
				// add by gy for 登录成功后，将登录后的用户信息放入到cookie中
				lrv.setLogonName(logonName);
				SessionUtil.addLoginCookie(response, lrv);
				
				
				//检查并插入推荐注册信息
				String fromId = SessionUtil.getRegisterFrom(request);
				try {
					if (!StringUtils.isEmpty(fromId)) {
						RecommendRegBo bo = new RecommendRegBo();
						bo.setCustomerId(Long.parseLong(fromId));
						bo.setRegSSOUserId(Long.parseLong(lrv.getUserId()));
						bo.setLogonName(logonName);
						int flag = registerService.insertRecommendRegister(bo);
						if ( flag == -1){
							logger.error("检查并插入推荐注册信息失败,fromId:  " +fromId +", userId:" + lrv.getUserId() );
						}
					}
				} catch (Exception e) {
					logger.error("检查并插入推荐注册信息异常,fromId:  " +fromId +", userId:" + lrv.getUserId() ,e);
				}

				String wurl = SessionUtil.getWurl(request);
				wurl = StringUtils.isEmpty(wurl) 
							? (request.getContextPath() + "/activity/toActivityIndex/.shtml") : wurl;
				resultMap.put("result", true);
				resultMap.put("wurl", wurl);// 注册成功后，跳转到要跳转的页面
			} else {
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.RegisterAccountFailed.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.RegisterAccountFailed.getErrorMsg());
			}
		}catch (EIBusinessException e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", e.getExtErrCode());
			resultMap.put("errorMsg", e.getExtMessage());
			resultMap.put("wurl", request.getContextPath() + "/loginReg/toReg.shtml");// 注册失败后
			logger.error("注册失败{errorCode：" + e.getExtErrCode()
					+ ", errorMsg：" + e.getExtMessage() + "}");
		}catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("注册账户时发生异常：", e);
		}

		logger.info("注册账户接口返回结果：" + JSON.toJSONString(resultMap));
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	/**
	 * 用户注册
	 * @param jsoncallback
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/registerUser", produces = "text/html;charset=UTF-8")
	public String registerUser(String jsoncallback,HttpServletRequest request, HttpServletResponse response) {
		logger.info("用户注册Simple参数params="+request.getQueryString());
		String logonName = request.getParameter("memberVo.telphone");
		String password = request.getParameter("memberVo.password");
		String encryptFlag = request.getParameter("encryptFlag");
		String regType = request.getParameter("memberVo.regType");
		String smsCode = request.getParameter("smsCode");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if(StringUtils.isEmpty(logonName) || StringUtils.isEmpty(password) || StringUtils.isEmpty(smsCode)) {
			//验证参数
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		
		try {
			boolean iSuccess = false; // 验证是否成功
			//1.校验验证码
			if (!StringUtils.isEmpty(logonName) && !StringUtils.isEmpty(smsCode)) {
				MessageCode messageCode = messageCodeService.findMessageCodeByPhone(logonName);
				
				if (messageCode != null) {
					String oldSmsCode = messageCode.getMsgCode();
					String sendCodeTime = messageCode.getTimestamp();
					String timeFlag = messageCode.getTimeFlag();
					String contentFlag = messageCode.getContentFlag();
	
					// 不同的手机验证码不同
					if (!StringUtils.isEmpty(oldSmsCode) && !StringUtils.isEmpty(sendCodeTime) && timeFlag.equals("N") && contentFlag.equals("N")) {
						long dif = DateUtil.diffDateTime(new Date(), new Date(Long.valueOf(sendCodeTime)));
						
						// 时间差大于规定时间则验证码失效 3分钟
						if (dif > this.DISSECOND_LONG) {
							// 时间失效
							messageCode.setTimeFlag("Y");
							messageCodeService.updateMessageCode(messageCode);
							resultMap.put("result", false);
							resultMap.put("errorCode", ErrorCode.VerifyCodeTimeOut.getErrorCode());
							resultMap.put("errorMsg", ErrorCode.VerifyCodeTimeOut.getErrorMsg());
							logger.error("用户注册->短信验证码失效");
							return JsonUtils.bean2jsonP(jsoncallback, resultMap);
						} else {
							// 验证验证码的正确性
							if (smsCode.equals(oldSmsCode)) {
								iSuccess = true;
								// 内容已验证
								messageCode.setContentFlag("Y");
								messageCodeService.updateMessageCode(messageCode);
							} else {
								resultMap.put("result", false);
								resultMap.put("errorCode", ErrorCode.VerifyCodeFailed.getErrorCode());
								resultMap.put("errorMsg", "请输入正确的短信验证码");
								logger.error("用户注册->短信验证码不正确");
								return JsonUtils.bean2jsonP(jsoncallback, resultMap);
							}
						}
					}
				} else {
					resultMap.put("result", false);
					resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
					logger.error("用户注册->验证码为空，没有找到验证码记录");
					return JsonUtils.bean2jsonP(jsoncallback, resultMap);
				}
			}
			
			if (!iSuccess) {
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.VerifyCodeFailed.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.VerifyCodeFailed.getErrorMsg());
				logger.info("用户注册->校验验证码失败：{Tel: " + logonName + ", smsCode: " + smsCode + "}");
				return JsonUtils.bean2jsonP(jsoncallback, resultMap);
			}
	
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.CheckSMSCodeError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.CheckSMSCodeError.getErrorMsg());
			logger.error("校验短信验证码发生异常：" + e.getMessage());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		
		//2.用户注册
		String userSour = request.getParameter("userSource") ; //用户注册来源标识
		Integer userSource = 150;
		if("".equals(userSour) || null == userSour){
			userSource = 150;
		} else{
			userSource = Integer.parseInt(userSour);
		}
		
		try {
			// 加密验证码
			MessageCode messageCode = messageCodeService.findMessageCodeByPhone(logonName);
			if (messageCode != null) {
				String oldVerifyResult = messageCode.getMsgCode();
				String timeFlag = messageCode.getTimeFlag();
				String contentFlag = messageCode.getContentFlag();
				if (!StringUtils.isEmpty(oldVerifyResult) && timeFlag.equals("N") && contentFlag.equals("Y")) {
					// 删除验证码
					messageCodeService.removeMessageCodeByPhone(logonName);
				}

				// 密码不为空，encryptFlag为Y
				if(!StringUtils.isEmpty(password) && "Y".equals(encryptFlag)){
					// password解密(AES)
					String aesKey=EncryptUtils.getAESKeyShare();
					//password=EncryptUtils.encryptByAES(password, aesKey);
					password=EncryptUtils.decryptPassByAES(password, aesKey);
					
				}
				
				Map<String, Object> regParams = new HashMap<String, Object>();
				password = new AESCipher(XiuConstant.SAFE_CODE).encrypt(password);
				regParams.put("regType", regType);
				regParams.put("logonName", logonName);
				regParams.put("password", password);
				// 添加IP信息，登录，获取用户状态等接口需要。
				String remoteIp = HttpUtil.getRemoteIpAddr(request);
				regParams.put(GlobalConstants.KEY_REMOTE_IP, remoteIp);
				regParams.putAll(getDeviceParams(request));// 设备信息
				regParams.put("userSource",userSource);
				LoginResVo lrv = registerService.register(regParams);
				// String tokenId = lrv.getTokenId();
				// add by gy for 登录成功后，将登录后的用户信息放入到cookie中
				lrv.setLogonName(logonName);
				SessionUtil.addLoginCookie(response, lrv);
				
				//检查并插入推荐注册信息
				String fromId = SessionUtil.getRegisterFrom(request);
				try {
					if (!StringUtils.isEmpty(fromId)) {
						RecommendRegBo bo = new RecommendRegBo();
						bo.setCustomerId(Long.parseLong(fromId));
						bo.setRegSSOUserId(Long.parseLong(lrv.getUserId()));
						bo.setLogonName(logonName);
						int flag = registerService.insertRecommendRegister(bo);
						if ( flag == -1){
							logger.error("检查并插入推荐注册信息失败,fromId:  " +fromId +", userId:" + lrv.getUserId() );
						}
					}
				} catch (Exception e) {
					logger.error("检查并插入推荐注册信息异常,fromId:  " +fromId +", userId:" + lrv.getUserId() ,e);
				}

				String wurl = SessionUtil.getWurl(request);
				wurl = StringUtils.isEmpty(wurl) ? (request.getContextPath() + "/activity/toActivityIndex/.shtml") : wurl;
				resultMap.put("result", true);
				resultMap.put("wurl", wurl);// 注册成功后，跳转到要跳转的页面
			} else {
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.RegisterAccountFailed.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.RegisterAccountFailed.getErrorMsg());
			}
		}catch (EIBusinessException e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", e.getExtErrCode());
			resultMap.put("errorMsg", e.getExtMessage());
			resultMap.put("wurl", request.getContextPath() + "/loginReg/toReg.shtml");// 注册失败后
			logger.error("用户注册{errorCode：" + e.getExtErrCode() + ", errorMsg：" + e.getExtMessage() + "}");
		}catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("用户注册时发生异常：", e);
		}

		logger.info("用户注册接口返回结果：" + JSON.toJSONString(resultMap));
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
}
