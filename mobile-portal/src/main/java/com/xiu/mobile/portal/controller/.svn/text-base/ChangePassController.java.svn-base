package com.xiu.mobile.portal.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xiu.common.lang.StringUtil;
import com.xiu.mobile.core.exception.EIBusinessException;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.constants.LoginRegConstant;
import com.xiu.mobile.portal.common.constants.MobileCommonConstant;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.CookieUtil;
import com.xiu.mobile.portal.common.util.EmailTemplateUtil;
import com.xiu.mobile.portal.common.util.EncryptUtils;
import com.xiu.mobile.portal.common.util.SMSTemplateUtil;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.constants.GlobalConstants;
import com.xiu.mobile.portal.facade.utils.DateUtil;
import com.xiu.mobile.portal.model.MessageCode;
import com.xiu.mobile.portal.service.IEmailService;
import com.xiu.mobile.portal.service.ILoginService;
import com.xiu.mobile.portal.service.IMessageCodeService;
import com.xiu.mobile.portal.service.IRegisterService;
import com.xiu.mobile.portal.service.ISMSService;
import com.xiu.mobile.portal.service.IUserService;
import com.xiu.uuc.facade.dto.UserDetailDTO;

/**
 * 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 手机app密码管理
 * @AUTHOR : hejianxiong
 * @DATE :2014年6月19日 下午4:31:42
 * </p>
 ****************************************************************
 */
@Controller
@RequestMapping("/changePassword")
public class ChangePassController extends BaseController {
	private final long DISSECOND = LoginRegConstant.SMS_DIS_SECOND;// 验证码失效时间
	private Logger logger = Logger.getLogger(ChangePassController.class);
	@Autowired
	private ILoginService loginService;
	@Autowired
	private IRegisterService registerService;
	@Autowired
	private IUserService userService;
	@Autowired
	private ISMSService iSMSService;
	@Autowired
	private IEmailService emailService;
	@Autowired
	private IMessageCodeService messageCodeService;
	
	/**
	 * 发送验证码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sendVerifyCode", produces = "text/html;charset=UTF-8")
	public String sendVerifyCode(String jsoncallback, HttpServletRequest request) {
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
				String userSource = getDeviceParams(request).get("userSource");
				if (registerService.checkLogonNameIsExist(logonName, userSource)) {
					
					boolean limitStatus = false;
					
					if(!StringUtils.isEmpty(deviceId)) {
						Map paraMap = new HashMap();
						paraMap.put("deviceId", deviceId);
						paraMap.put("type", MobileCommonConstant.SMS_VALIDATE_TIMES_FORGET_PASSWORD);
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
						// 获取短信内容
						String smsBody = SMSTemplateUtil.getForgotPasswordBody(smsCode);
						logger.info("用户走秀网密码操作：telephone=" + logonName + ",smsBody=" + smsBody);
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
					map.put("errorCode", ErrorCode.LogonNameIsNotExist.getErrorCode());
					map.put("errorMsg", ErrorCode.LogonNameIsNotExist.getErrorMsg());
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
	 * 校验 验证码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkVerifyCode", produces = "text/html;charset=UTF-8")
	public String checkVerifyCode(String jsoncallback, String smsCode, 
			HttpServletRequest request, HttpServletResponse response) {
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
								
								//生成密串
								String aesKey = EncryptUtils.getAESKeyShare();
								String code = EncryptUtils.encryptPassByAES(logonName + XiuConstant.VERIFY_ENCRYPT_CONSTANT, aesKey);
								CookieUtil.getInstance().addCookie(response, XiuConstant.VERIFY_ENCRYPT_CODE, code);
								
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
			logger.error("校验短信验证码发生异常：" + e.getMessage(),e);
		}

		logger.info("校验短信验证码接口返回结果：" + JSON.toJSONString(resultMap));
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}

	/***
	 * 用户密码修改
	 * @param jsoncallback
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/modify/password", produces = "text/html;charset=UTF-8")
	public @ResponseBody String modifyPassword(String jsoncallback,HttpServletRequest request,HttpServletResponse response) {
		// 存储返回结果值
		Map<String, Object> result = new HashMap<String, Object>();
		String encryptFlag = request.getParameter("encryptFlag");
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		
		try {
			if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)) {
				// encryptFlag为Y
				if( "Y".equals(encryptFlag)){
					String aesKey=EncryptUtils.getAESKeyShare();
					// oldPassword解密(AES)
					if(!StringUtils.isEmpty(oldPassword)){
						//oldPassword=EncryptUtils.Encrypt(oldPassword, aesKey);
						oldPassword=EncryptUtils.decryptPassByAES(oldPassword, aesKey);
					}
					// newPassword解密(AES)
					if(!StringUtils.isEmpty(newPassword)){
						//newPassword=EncryptUtils.Encrypt(newPassword, aesKey);
						newPassword=EncryptUtils.decryptPassByAES(newPassword, aesKey);
					}							
				}
				
				// 获取userId
				String userId = SessionUtil.getUserId(request);
				// 验证密码修改密码的相关业务逻辑处理
				userService.modifyUserPassword(Long.parseLong(userId), oldPassword, newPassword);
				result.put("result", true);
				result.put("errorCode", ErrorCode.Success.getErrorCode());
				result.put("errorMsg", ErrorCode.Success.getErrorMsg());
				logger.info("userId="+userId+"修改密码结果result="+result.toString());
			}else{
				result.put("result", false);
				result.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				result.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			}
		}catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", e.getExtErrCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("修改密码异常：e=", e);
		}catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("修改密码异常：e="+e.getMessage(),e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/***
	 * 用户密码修改
	 * @param jsoncallback
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/reset/password", produces = "text/html;charset=UTF-8")
	public @ResponseBody String resetPassword(String jsoncallback,HttpServletRequest request,HttpServletResponse response) {
		// 存储返回结果值
		Map<String, Object> result = new HashMap<String, Object>();
		String logonName = request.getParameter("memberVo.logonName");
		String encryptFlag = request.getParameter("encryptFlag");
		String newPassword = request.getParameter("newPassword");
		String channelId = GlobalConstants.CHANNEL_ID;
		logger.info("memberVo.logonName="+logonName+",channelId="+channelId+"重置用户密码！");
		if (StringUtils.isNotBlank(logonName)&&StringUtils.isNotBlank(newPassword)&&StringUtils.isNotBlank(channelId)) {
			try {
				// 验证密码修改密码的相关业务逻辑处理
				UserDetailDTO user = userService.getUserBasicInfoByLogonName(logonName,Integer.parseInt(channelId));
				logger.info("查询到用户信息UserDetailDTO="+user);

				if (user!=null) {	
					try {
						// 密码不为空，encryptFlag为Y
						if(StringUtils.isNotEmpty(newPassword) && "Y".equals(encryptFlag)){
							// password解密(AES)
							String aesKey=EncryptUtils.getAESKeyShare();					
							//newPassword=EncryptUtils.Encrypt(newPassword, aesKey);
							newPassword=EncryptUtils.decryptPassByAES(newPassword, aesKey);
							
						}
					} catch (Exception e) {
						logger.error("重置密码时AES解密用户名密码出错" ,e);
					}
					
					// 检验常量
					String code = CookieUtil.getInstance().getCookieValue(request, ".xiu.com", XiuConstant.VERIFY_ENCRYPT_CODE);
					if(StringUtil.isNotBlank(code)) {
						String aesKey = EncryptUtils.getAESKeyShare();
						String verifyCode = EncryptUtils.encryptPassByAES(logonName + XiuConstant.VERIFY_ENCRYPT_CONSTANT, aesKey);
						if(!code.equals(verifyCode)) {
							//校验常量失败
							logger.error("重置密码失败，校验常量密串失败。参数：memberVo.logonName="+logonName+",encryptFlag="+encryptFlag+",newPassword="+newPassword);
							result.put("result", false);
							result.put("errorCode", ErrorCode.VerifyCodeHasFailed.getErrorCode());
							result.put("errorMsg", ErrorCode.VerifyCodeHasFailed.getErrorMsg());
							return JsonUtils.bean2jsonP(jsoncallback, result);
						}
					} else {
						logger.error("重置密码失败，缺少常量密串。参数：memberVo.logonName="+logonName+",encryptFlag="+encryptFlag+",newPassword="+newPassword);
						result.put("result", false);
						result.put("errorCode", ErrorCode.VerifyCodeHasFailed.getErrorCode());
						result.put("errorMsg", ErrorCode.VerifyCodeHasFailed.getErrorMsg());
						return JsonUtils.bean2jsonP(jsoncallback, result);
					}
					
					// 验证密码修改密码的相关业务逻辑处理
					userService.resetUserPassword(user.getUserId(),newPassword);
					result.put("result", true);
					result.put("errorCode", ErrorCode.Success.getErrorCode());
					result.put("errorMsg", ErrorCode.Success.getErrorMsg());
					logger.info("user="+user+"重置密码结果result="+result);
				}else{
					result.put("result", false);
					result.put("errorCode", ErrorCode.SystemError.getErrorCode());
					result.put("errorMsg", ErrorCode.SystemError.getErrorMsg().concat(",没有此用户名相关信息"));
				}
			}catch(EIBusinessException e){
				result.put("result", false);
				result.put("errorCode", e.getExtErrCode());
				result.put("errorMsg", e.getExtMessage());
				logger.error("重置密码异常:" + e.getMessageWithSupportCode(),e);
			}catch (Exception e) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.SystemError.getErrorCode());
				result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
				logger.error("重置密码异常：e="+e.getMessage(),e);
			}
		} else {
			result.put("result", false);
			result.put("errorCode", ErrorCode.MissingParams.getErrorCode());
	        result.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
	    }

		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/**
	 * 检测邮箱验证码是否存在
	 * @param jsoncallback
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkEmailVerifyCodeExists", produces = "text/html;charset=UTF-8")
	public String checkEmailVerifyCodeExists(String jsoncallback, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String email = request.getParameter("email"); //邮箱
		String type = request.getParameter("type"); //类型：1.找回密码 2.绑定邮箱
		if(StringUtils.isBlank(email) || StringUtils.isBlank(type)) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			result.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, result);
		}
		
		if(!"1".equals(type) && !"2".equals(type)) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.ParamsError.getErrorCode());
			result.put("errorMsg", ErrorCode.ParamsError.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, result);
		}
		
		try {
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("email", email);
			paraMap.put("time", "1"); 		 //查询有效时间
			paraMap.put("codeNotNull", "1"); //验证码不为空
			if("1".equals(type)) {
				paraMap.put("type", MobileCommonConstant.EMAIL_FORGET_PASSWORD);
			} else if("2".equals(type)) {
				paraMap.put("type", MobileCommonConstant.EMAIL_BINDING_EMAIL);
			}
			
			//检测邮箱验证码是否存在
			boolean existsStatus = messageCodeService.isEmailVerifyCodeExists(paraMap);
			
			result.put("existsStatus", existsStatus);
			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("检测邮箱验证码是否存在时发生异常：", e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/**
	 * 找回密码验证邮箱
	 * @param jsoncallback
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/validateEmail", produces = "text/html;charset=UTF-8")
	public String validateEmail(String jsoncallback, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		String email = request.getParameter("email"); //邮箱
		String code = request.getParameter("validateCode"); //验证码
		if(StringUtils.isBlank(email) || StringUtils.isBlank(code)) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			result.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, result);
		}
		
		try {
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("email", email);
			paraMap.put("code", code);
			paraMap.put("time", "1"); //查询有效时间
			paraMap.put("type", MobileCommonConstant.EMAIL_FORGET_PASSWORD);
			
			//检测邮箱验证码是否存在
			boolean existsStatus = messageCodeService.isEmailVerifyCodeExists(paraMap);
			
			if(existsStatus) {
				//验证成功，清除邮箱验证码
				paraMap.remove("code");
				paraMap.put("code", "");
				messageCodeService.updateEmailVerifyCode(paraMap);
				
				//生成密串
				String aesKey = EncryptUtils.getAESKeyShare();
				String verifyCode = EncryptUtils.encryptPassByAES(email + XiuConstant.VERIFY_ENCRYPT_CONSTANT, aesKey);
				CookieUtil.getInstance().addCookie(response, XiuConstant.VERIFY_ENCRYPT_CODE, verifyCode);
				
				result.put("result", true);
				result.put("errorCode", ErrorCode.Success.getErrorCode());
				result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			} else {
				result.put("result", false);
				result.put("errorCode", ErrorCode.VerifyCodeHasFailed.getErrorCode());
				result.put("errorMsg", ErrorCode.VerifyCodeHasFailed.getErrorMsg());
			}
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("找回密码验证邮箱时发生异常：", e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
   
	/**
	 * 发送找回密码邮件
	 * @param jsoncallback
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sendFindPasswordEmail", produces = "text/html;charset=UTF-8")
	public String sendFindPasswordEmail(String jsoncallback, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String email = request.getParameter("email"); //邮箱
		String deviceId = request.getParameter("deviceId"); //设备ID
		if(StringUtils.isBlank(email) || StringUtils.isBlank(deviceId)) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			result.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, result);
		}
		
		try {
			String channelId = GlobalConstants.CHANNEL_ID;
			//1.检测邮箱是否存在
			UserDetailDTO user = userService.getUserBasicInfoByLogonName(email, Integer.parseInt(channelId));
			if(user == null) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.EmailNotRegister.getErrorCode());
				result.put("errorMsg", ErrorCode.EmailNotRegister.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			//2.检测邮箱是否绑定
			String emailAuthenStatus = user.getEmailAuthenStatus();
			if(!"1".equals(emailAuthenStatus)) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.EmailNotBinding.getErrorCode());
				result.put("errorMsg", ErrorCode.EmailNotBinding.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			//3.发送邮件
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("email", email);
			paraMap.put("deviceId", deviceId);
			paraMap.put("type", MobileCommonConstant.EMAIL_FORGET_PASSWORD);
			paraMap.put("limitTimes", MobileCommonConstant.SMS_LIMIT_TIMES);
			
			String code = messageCodeService.getEmailVerifyCode(paraMap);	//获取邮箱验证码
			
			String body = EmailTemplateUtil.getBindingEmailBody(code);
			String creator = "user@xiu.com";
			String senderName = "走秀网";
			String subject = "走秀账号-找回密码身份验证";
			boolean flag = emailService.sendEmail(email, subject, body, creator, senderName);
			if(!flag) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.SystemError.getErrorCode());
				result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("发送找回密码邮件时发生异常：", e);
		}

		logger.info("发送找回密码邮件返回结果：" + JSON.toJSONString(result));
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
}