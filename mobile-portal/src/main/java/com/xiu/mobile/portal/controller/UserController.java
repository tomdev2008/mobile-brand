package com.xiu.mobile.portal.controller;

import java.io.File;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiu.common.lang.StringUtil;
import com.xiu.mobile.core.exception.EIBusinessException;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.constants.LoginRegConstant;
import com.xiu.mobile.portal.common.constants.MobileCommonConstant;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.ConfInTomcat;
import com.xiu.mobile.portal.common.util.EmailTemplateUtil;
import com.xiu.mobile.portal.common.util.EncryptUtils;
import com.xiu.mobile.portal.common.util.SMSTemplateUtil;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.common.util.Tools;
import com.xiu.mobile.portal.common.util.UploadUtil;
import com.xiu.mobile.portal.constants.GlobalConstants;
import com.xiu.mobile.portal.ei.EIMobileCpsManager;
import com.xiu.mobile.portal.ei.EIXiuMakerService;
import com.xiu.mobile.portal.facade.utils.DateUtil;
import com.xiu.mobile.portal.facade.utils.HttpUtil;
import com.xiu.mobile.portal.model.AddressListQueryInParam;
import com.xiu.mobile.portal.model.BindUserInfo;
import com.xiu.mobile.portal.model.MessageCode;
import com.xiu.mobile.portal.model.QueryUserAddressListOutParam;
import com.xiu.mobile.portal.model.ShowUserInfoVo;
import com.xiu.mobile.portal.model.UserInfo;
import com.xiu.mobile.portal.service.IAddressService;
import com.xiu.mobile.portal.service.IEmailService;
import com.xiu.mobile.portal.service.ILoginService;
import com.xiu.mobile.portal.service.IMessageCodeService;
import com.xiu.mobile.portal.service.ISMSService;
import com.xiu.mobile.portal.service.IUserService;
import com.xiu.uuc.facade.dto.ModifyUserAuthenDTO;
import com.xiu.uuc.facade.dto.UserBaseDTO;
import com.xiu.uuc.facade.dto.UserDetailDTO;
import com.xiu.uuc.facade.dto.UserOperateLogInfoDTO;

/**
 * <p>
 * ************************************************************** 
 * @Description: 用户信息操作
 * @AUTHOR hejianxiong
 * @DATE 2014-12-23 上午10:50:20 
 * ***************************************************************
 * </p>
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	
	private final long DISSECOND = LoginRegConstant.SMS_DIS_SECOND;// 验证码失效时间
	private final String prefixURL = "http://6.xiustatic.com/user_headphoto";
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private ILoginService loginService;
	@Autowired
	private IUserService userService;
	@Autowired
	private ISMSService iSMSService;
	@Autowired
	private IEmailService emailService;
	@Autowired
	private IMessageCodeService messageCodeService;
	@Autowired
	private EIXiuMakerService eiXiuMakerService;
	@Autowired
	private EIMobileCpsManager cpsManager;
	@Autowired
	private IAddressService addressService;

	/**
	 * 获取当前用户详细信息
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserInfo", produces = "text/html;charset=UTF-8")
	public String getUserInfo(String jsoncallback, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			String deviceId=request.getParameter("deviceId");
			
			String userId = SessionUtil.getUserId(request);
			UserDetailDTO userDetailDTO = userService.getUserDetailDTOByUserId(Long.parseLong(userId));
			logger.info("获取用户详细信息userDetailDTO="+userDetailDTO);
			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			UserInfo userInfo = getUserInfo(userDetailDTO);
			boolean isFirstChangeName = false;
			// 联合登陆用户名逻辑 >> 如果前缀以xiu_+user_id(当前用户的用户ID)开头的  表示联合登陆初始化用户
			String prefix = "xiu_".concat(userId);
			if (userInfo != null && userInfo.getLogonName() != null) {
				if (userInfo.getLogonName().startsWith(prefix)) {
					isFirstChangeName = true;
				} 
				
				// 正则表达式 字母开头 字母数字和下划线组成  
				Pattern pattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]{5,19}$");
				Matcher matcher = pattern.matcher(userInfo.getLogonName());
				if (!matcher.matches()) {
					isFirstChangeName = true;
				}
			}
			result.put("isFirstChangeName", isFirstChangeName);
			
			//查询是否是创客
			Map<String,Object> makerResult = eiXiuMakerService.queryMakerUserInfo(Long.parseLong(userId));
			if(makerResult != null) {
				boolean isMaker = (Boolean) makerResult.get("result");
				userInfo.setIsMaker(isMaker);
			}
			//返现金额
			Map<String, Object> resultMap = cpsManager.queryPersonCpsCenterInfo(Long.valueOf(userId), null);
			if(resultMap!=null&&(Boolean)resultMap.get("result")){
				String jsonStr=JSON.toJSONString(resultMap);
				JSONObject obj = JSONObject.parseObject(jsonStr);
				JSONObject indexVO=(JSONObject)obj.get("indexVO");
				userInfo.setTotalAmount(indexVO.get("totalAmount")+"");
				userInfo.setNotPayAmount(indexVO.get("notPayAmount")+"");
			}
			//查询秀客信息
			ShowUserInfoVo showUserInfo = userService.getShowUserInfo(userId);
			boolean showUserTalent=false;//达人标识
			boolean showUserManager=false;//前台管理员标识
			if(showUserInfo!=null){
				//查询是否秀客达人标识
				 showUserTalent =showUserInfo.getTalentFlag();
				//查询是否为秀客前台管理员
				 if(deviceId!=null&&!deviceId.equals("")){
					 Map<String,Object> managerParams=new HashMap<String,Object>();
					 managerParams.put("userId", userId);
					 managerParams.put("deviceId", deviceId);
					 showUserManager = userService.getManagerAuthority(managerParams);
				 }
			}
			userInfo.setIsShowUserTalent(showUserTalent);
			userInfo.setIsShowManager(showUserManager);
			
			result.put("userInfo", userInfo);
//			//查询用户绑定信息
			List<BindUserInfo> bindUserInfoList = loginService.getBindUserInfoListByUserId(Long.parseLong(userId));
			result.put("bindUserInfoList", bindUserInfoList);
//			//设置是否绑定微博
			for (BindUserInfo bindVo:bindUserInfoList) {
				if(bindVo.getThirdName()==null){
					bindVo.setThirdName("");
				}
			}
			//查询用户收货地址个数
			Boolean isHaveAddress=false;
			AddressListQueryInParam addressListQuery = new AddressListQueryInParam();
			SessionUtil.setDeviceInfo(request, addressListQuery);
			try{
				QueryUserAddressListOutParam queryAddress=addressService.listAddressNew(addressListQuery);
				if(queryAddress.getRetCode().equals(GlobalConstants.RET_CODE_SUCESSS)
						&&queryAddress.getAddressList()!=null
						&&queryAddress.getAddressList().size()>0){
					isHaveAddress=true;
				}
			}catch (Exception e) {
				logger.error("获取用户信息判断是否有地址异常：", e);
			}
			result.put("isHaveAddress", isHaveAddress);
			
		}catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", e.getExtErrCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("获取用户信息发生异常：", e);
		}catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取用户信息发生异常：", e);
		}

		logger.info("获取用户信息接口返回结果：" + JSON.toJSONString(result));
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/***
	 * 获取用户第三方的聚道列表信息
	 * @param jsoncallback
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBindInfoList", produces = "text/html;charset=UTF-8")
	public String getBindInfoList(String jsoncallback,HttpServletRequest request, HttpServletResponse response) {
		logger.info("获取用户第三方的聚道列表信息参数params="+request.getQueryString());
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String userId = SessionUtil.getUserId(request);
			//查询用户绑定信息
			List<BindUserInfo> bindUserInfoList = loginService.getBindUserInfoListByUserId(Long.parseLong(userId));
			
			//查询用户绑定手机信息
			UserDetailDTO userDetailDTO = userService.getUserDetailDTOByUserId(Long.parseLong(userId));
			if(userDetailDTO != null) {
				String mobile = userDetailDTO.getMobile();
				String mobileAuthenStatus = userDetailDTO.getMobileAuthenStatus();
				if(StringUtils.isNotBlank(mobile) && "1".equals(mobileAuthenStatus)) {
					//如果手机号码不为空，并且已验证
					result.put("mobile", mobile);
				}
			}
			
			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			result.put("bindUserInfoList", bindUserInfoList);
		}catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", e.getExtErrCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("获取用户第三方的聚道列表信息{errorCode：" + e.getExtErrCode() + ", errorMsg：" + e.getExtMessage() + "}");
		}catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取用户第三方的聚道列表信息发生异常：", e);
		}

		logger.info("获取用户第三方的聚道列表信息返回结果：" + JSON.toJSONString(result));
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	
	/**
	 * 发送验证码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sendVerifyCode", produces = "text/html;charset=UTF-8")
	public String sendSmsVerifyCode(String jsoncallback, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			String phone = request.getParameter("phone");
			if (StringUtils.isBlank(phone)) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				result.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			String deviceId = request.getParameter("deviceId"); 
			if(StringUtils.isNotBlank(deviceId)) {
				//如果设备号不为空，则根据设备号验证发送验证码次数
				Map<String,Object> paraMap = new HashMap<String,Object>();
				paraMap.put("mobile", phone);
				paraMap.put("deviceId", deviceId);
				paraMap.put("type", MobileCommonConstant.SMS_VALIDATE_TIMES_VALIDATE_PHONE);
				paraMap.put("limitTimes", MobileCommonConstant.SMS_LIMIT_TIMES);
				//验证获取短信验证码是否超过次数
				boolean limitStatus = messageCodeService.getSMSLimitStatus(paraMap);
				if(limitStatus) {
					result.put("result", false);
					result.put("errorCode", ErrorCode.getSMSOverLimitTimes.getErrorCode());
					result.put("errorMsg", "操作太频繁了，请1个小时后再试或联系客服");
					return JsonUtils.bean2jsonP(jsoncallback, result);
				}
			}
			
			// 获取验证码
			String smsCode =messageCodeService.getMessageCodeByPhone(phone);
			String smsBody = SMSTemplateUtil.getChangePhoneBody(smsCode);
			logger.info("用户号码："+ phone + ",信息内容：" + smsBody);
			boolean flag = iSMSService.sendSMS(smsBody, phone, "", phone);
			logger.info("短信发送状态：flag=" + flag);
			
			MessageCode messageCode = new MessageCode();
			messageCode.setMsgCode(smsCode);
			messageCode.setTimestamp(String.valueOf(System.currentTimeMillis()));
			messageCode.setMsgPhone(phone);
			messageCode.setTimeFlag("N");
			messageCode.setContentFlag("N");
				
			// 插入验证码到数据库中
			messageCodeService.saveMessageCode(messageCode);
			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
		}catch (EIBusinessException e) {
			result.put("result", false);
			result.put("retCode", ErrorCode.SendSMSCodeFailed.getErrorCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("发送短信验证码时发生异常：", e);
		}catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("发送短信验证码时发生异常：", e);
		}

		logger.info("发送验证码接口返回结果：" + JSON.toJSONString(result));
		return JsonUtils.bean2jsonP(jsoncallback, result);
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
		String remoteIp = HttpUtil.getRemoteIpAddr(request);	//IP地址
		String deviceId = request.getParameter("deviceId");		//设备ID
		String sysName = request.getParameter("sysName");		//系统名称
		String sysVersion = request.getParameter("sysVersion");	//系统版本
		try {
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
						result.put("errorCode", ErrorCode.VerifyCodeFailed.getErrorCode());
						result.put("errorMsg", ErrorCode.VerifyCodeFailed.getErrorMsg());
						logger.error("checkVerifyCode==>验证码为空，没有找到验证码记录");
					}
				} else {
					result.put("result", false);
					result.put("errorCode", ErrorCode.ValidateNewPhoneError.getErrorCode());
					result.put("errorMsg", ErrorCode.ValidateNewPhoneError.getErrorMsg());
				}
			}
		}catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", e.getExtErrCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("修改用户手机发生异常：", e);
		}catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("修改用户手机发生异常：", e);
		}

		logger.info("修改用户手机接口返回结果：" + JSON.toJSONString(result));
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
	public @ResponseBody
	String resetPassword(String jsoncallback, HttpServletRequest request, HttpServletResponse response) {
		// 存储返回结果值
		Map<String, Object> result = new HashMap<String, Object>();
		String newPassword = request.getParameter("newPassword");
		try {
			String userId = SessionUtil.getUserId(request);
			String channelId = GlobalConstants.CHANNEL_ID;
			String logonName = SessionUtil.getUserName(request);
			logger.info("logonName=" + logonName + ",channelId=" + channelId + "重置用户密码！");
			if (StringUtils.isNotBlank(newPassword)) {
				// password解密(AES)
				String aesKey = EncryptUtils.getAESKeyShare();
				newPassword = EncryptUtils.decryptPassByAES(newPassword, aesKey);

				// 验证密码修改密码的相关业务逻辑处理
				userService.resetUserPassword(Long.parseLong(userId), newPassword);
				result.put("result", true);
				result.put("errorCode", ErrorCode.Success.getErrorCode());
				result.put("errorMsg", ErrorCode.Success.getErrorMsg());
				logger.info("userId=" + userId + "重置密码结果result=" + result);
			} else {
				result.put("result", false);
				result.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				result.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			}
		} catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", e.getExtErrCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("重置密码异常:" + e.getMessageWithSupportCode(), e);
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("重置密码异常：e=" + e.getMessage(), e);
		}

		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/***
	 * 更改用户详细信息
	 * @param jsoncallback
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateInfo", produces = "text/html;charset=UTF-8")
	public String updateInfo(String jsoncallback, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String username = request.getParameter("username");
		String nickname = request.getParameter("nickname");
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		String birthday = request.getParameter("birthday");
		String addressInfo = request.getParameter("addressInfo"); 
		
		String remoteIp = HttpUtil.getRemoteIpAddr(request);	//IP地址
		String deviceId = request.getParameter("deviceId");		//设备ID
		String sysName = request.getParameter("sysName");		//系统名称
		String sysVersion = request.getParameter("sysVersion");	//系统版本
		try {
			String userId = SessionUtil.getUserId(request);
			// 查询当前用户登录信息  uuc logonName mobile email会冲突 故修改某个属性则上传某属性
			UserDetailDTO userDetailDTO = new UserDetailDTO();
			userDetailDTO.setUserId(Long.parseLong(userId));
			userDetailDTO.setLogonName(username);
			userDetailDTO.setPetName(nickname);
			userDetailDTO.setCustName(name);
			userDetailDTO.setSex(sex);
			if (StringUtils.isNotBlank(birthday)) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				userDetailDTO.setBirthday(dateFormat.parse(birthday));
			}
			userDetailDTO.setAddressInfo(addressInfo);
			
			UserOperateLogInfoDTO userOperateLogInfoDTO = new UserOperateLogInfoDTO();
			userOperateLogInfoDTO.setDeviceNum(deviceId);
			userOperateLogInfoDTO.setOperAddr(remoteIp);
			userOperateLogInfoDTO.setSysName(sysName);
			userOperateLogInfoDTO.setSysNum(sysVersion);
			userOperateLogInfoDTO.setUserDetailDTO(userDetailDTO);
			userService.modifyUserDetailDTO(userOperateLogInfoDTO);
			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
		}catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", e.getExtErrCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("更改用户信息发生异常：", e);
		}catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("更改用户信息发生异常：", e);
		}

		logger.info("更改用户信息接口返回结果：" + JSON.toJSONString(result));
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	
	/**
	 * 上传用户头像
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String uploadFile(MultipartHttpServletRequest request, HttpServletResponse response,String jsoncallback) {
		Map<String, Object> result = new HashMap<String, Object>();
		String remoteIp = HttpUtil.getRemoteIpAddr(request);	//IP地址
		String deviceId = request.getParameter("deviceId");		//设备ID
		String sysName = request.getParameter("sysName");		//系统名称
		String sysVersion = request.getParameter("sysVersion");	//系统版本
		try {
			String userId = SessionUtil.getUserId(request);
			
			// 转型为MultipartHttpRequest：     
	        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;     
	        // 获得文件：     
			MultipartFile patch = multipartRequest.getFile("imgFile");
			// 判断是否获取到文件
			if (patch.isEmpty() || patch.getSize() <= 0 || patch.getOriginalFilename() == null) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.SystemError.getErrorCode());
				result.put("errorMsg", ErrorCode.SystemError.getErrorMsg().concat(",没有接收到文件信息"));
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			String fileName = patch.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf("."));
			// 限制上传文件格式为jpg,jepg,png,bmp
			if (("*.jpg;*.jepg,*.png,*.bmp").indexOf(suffix.toLowerCase()) < 0) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.SystemError.getErrorCode());
				result.put("errorMsg", "限制上传文件格式为jpg,jepg,png,bmp");
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			// 文件大小 不能超过10MB
			if (patch.getSize()>10485760) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.SystemError.getErrorCode());
				result.put("errorMsg", ErrorCode.SystemError.getErrorMsg().concat(",文件大小不能超过10MB"));
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			String folderURL = ConfInTomcat.getValue("user.headphoto.upload.dir");
			// 头像名称 headPortrait
			String transFileName  =  "headPortrait.jpg";
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String imageSortName = UploadUtil.getImageSortName(userId);	//获取图片分类名称
			String imgURL = "/" + imageSortName + "/" + userId + "/" + dateFormat.format(new Date()) + "/" + transFileName;
			String fullPicFile = folderURL + imgURL;
			// 创建上级文件夹
			createFolder(fullPicFile);
			patch.transferTo(new File(fullPicFile));// 上传图片
			
			// 查询当前用户登录信息  uuc logonName mobile email会冲突 故修改某个属性则上传某属性
			UserDetailDTO userDetailDTO = new UserDetailDTO();
			userDetailDTO.setUserId(Long.parseLong(userId));
			userDetailDTO.setHeadPortrait(imgURL);
			
			UserOperateLogInfoDTO userOperateLogInfoDTO = new UserOperateLogInfoDTO();
			userOperateLogInfoDTO.setDeviceNum(deviceId);
			userOperateLogInfoDTO.setOperAddr(remoteIp);
			userOperateLogInfoDTO.setSysName(sysName);
			userOperateLogInfoDTO.setSysNum(sysVersion);
			userOperateLogInfoDTO.setUserDetailDTO(userDetailDTO);
			userService.modifyUserDetailDTO(userOperateLogInfoDTO);
			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			result.put("imgURL", prefixURL.concat(imgURL));

		}catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", e.getExtErrCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("用户更换头像发生异常：", e);
		}  catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("图片上传错误:", e);
		}
	
		logger.info("上传用户头像响应结果："+ JSON.toJSONString(result));
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/**
	 * 上传用户头像
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadHeadPortrait", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String uploadHeadPortrait(MultipartHttpServletRequest request, HttpServletResponse response,String jsoncallback) {
		Map<String, Object> result = new HashMap<String, Object>();
		String remoteIp = HttpUtil.getRemoteIpAddr(request);	//IP地址
		String deviceId = request.getParameter("deviceId");		//设备ID
		String sysName = request.getParameter("sysName");		//系统名称
		String sysVersion = request.getParameter("sysVersion");	//系统版本
		try {
			String userId = SessionUtil.getUserId(request);
			
			// 转型为MultipartHttpRequest：     
	        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;     
	        // 获得文件：     
			MultipartFile patch = multipartRequest.getFile("imgFile");
			// 判断是否获取到文件
			if (patch.isEmpty() || patch.getSize() <= 0 || patch.getOriginalFilename() == null) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.SystemError.getErrorCode());
				result.put("errorMsg", ErrorCode.SystemError.getErrorMsg().concat(",没有接收到文件信息"));
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			String fileName = patch.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf("."));
			// 限制上传文件格式为jpg,jepg,png,bmp
			if (("*.jpg;*.jepg,*.png,*.bmp").indexOf(suffix.toLowerCase()) < 0) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.SystemError.getErrorCode());
				result.put("errorMsg", "限制上传文件格式为jpg,jepg,png,bmp");
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			// 文件大小 不能超过10MB
			if (patch.getSize()>10485760) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.SystemError.getErrorCode());
				result.put("errorMsg", ErrorCode.SystemError.getErrorMsg().concat(",文件大小不能超过10MB"));
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			String folderURL = ConfInTomcat.getValue("user.headphoto.upload.dir");
			// 头像名称 headPortrait
			String transFileName  =  "headPortrait.jpg";
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String time = dateFormat.format(new Date()); //时间
			String imageSortName = UploadUtil.getImageSortName(userId);	//获取图片分类名称
			String imgURL = "/" + imageSortName + "/" + userId + "/" + time + "/" + transFileName;
			String fullPicFile = folderURL + imgURL;
			// 创建上级文件夹
			createFolder(fullPicFile);
			patch.transferTo(new File(fullPicFile));// 上传图片
			
			//生成缩略图
			String thumbnailPicUrl = "/" + imageSortName + "/" + userId + "/" + time + "/thumbnail_" + transFileName;
			Thumbnails.of(fullPicFile).size(120, 120).scale(0.25f).toFile(folderURL + thumbnailPicUrl);
			
			// 查询当前用户登录信息  uuc logonName mobile email会冲突 故修改某个属性则上传某属性
			UserDetailDTO userDetailDTO = new UserDetailDTO();
			userDetailDTO.setUserId(Long.parseLong(userId));
			userDetailDTO.setHeadPortrait(thumbnailPicUrl); //缩略图
			userDetailDTO.setBigHeadPortrait(imgURL); //原图
			
			UserOperateLogInfoDTO userOperateLogInfoDTO = new UserOperateLogInfoDTO();
			userOperateLogInfoDTO.setDeviceNum(deviceId);
			userOperateLogInfoDTO.setOperAddr(remoteIp);
			userOperateLogInfoDTO.setSysName(sysName);
			userOperateLogInfoDTO.setSysNum(sysVersion);
			userOperateLogInfoDTO.setUserDetailDTO(userDetailDTO);
			userService.modifyUserDetailDTO(userOperateLogInfoDTO);
			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			result.put("imgURL", prefixURL.concat(thumbnailPicUrl)); //缩略图
			result.put("bigImgURL", prefixURL.concat(thumbnailPicUrl)); //原图

		}catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", e.getExtErrCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("用户更换头像发生异常：", e);
		}  catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("图片上传错误:", e);
		}
	
		logger.info("上传用户头像响应结果："+ JSON.toJSONString(result));
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	
	/***
	 * 用户详细信息转换
	 * @param userDetailDTO
	 * @return
	 */
	public UserInfo getUserInfo(UserDetailDTO userDetailDTO){
		UserInfo userInfo = new UserInfo();
		
		if (userDetailDTO != null) {
			userInfo.setAddressInfo(userDetailDTO.getAddressInfo());
			if (userDetailDTO.getBirthday() != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				userInfo.setBirthday(dateFormat.format(userDetailDTO.getBirthday()));
			}
			userInfo.setCustName(userDetailDTO.getCustName());
			userInfo.setEbayUserAgreement(userDetailDTO.getEbayUserAgreement());
			userInfo.setEmail(userDetailDTO.getEmail());
			userInfo.setEmailAuthenStatus(userDetailDTO.getEmailAuthenStatus());
			if (StringUtils.isNotBlank(userDetailDTO.getHeadPortrait())) {
				userInfo.setHeadPortrait(prefixURL.concat(userDetailDTO.getHeadPortrait()));
			}
			if (StringUtils.isNotBlank(userDetailDTO.getBigHeadPortrait())) {
				userInfo.setBigHeadPortrait(prefixURL.concat(userDetailDTO.getBigHeadPortrait()));
			}
			userInfo.setLastLogonChannelId(userDetailDTO.getLastLogonChannelId());
			userInfo.setLastLogonIp(userDetailDTO.getLastLogonIp());
			userInfo.setLastLogonTime(userDetailDTO.getLastLogonTime());
			userInfo.setLogonName(userDetailDTO.getLogonName());
			userInfo.setMobile(userDetailDTO.getMobile());
			userInfo.setMobileAuthenStatus(userDetailDTO.getMobileAuthenStatus());
			userInfo.setPetName(userDetailDTO.getPetName());
			userInfo.setRegisterTime(userDetailDTO.getRegisterTime());
			userInfo.setRegisterType(userDetailDTO.getRegisterType());
			userInfo.setSex(userDetailDTO.getSex());
			userInfo.setSexDesc(userDetailDTO.getSexDesc());
			userInfo.setUserId(userDetailDTO.getUserId());
			userInfo.setUserLevel(Tools.getUserLevel(userDetailDTO.getUserLevel()));
			
			if(!StringUtils.isEmpty(userDetailDTO.getMobile()) && "1".equals(userDetailDTO.getMobileAuthenStatus())) {
				userInfo.setMobileBindStatus(true);
			} else {
				userInfo.setMobileBindStatus(false);
			}
			
			//设置等级说明页面url
			userInfo.setUserLevelUrl(XiuConstant.USER_LEVEL_INFO_URL);
			
			boolean isEmptyPassword = userService.isEmptyPassword(userDetailDTO.getUserId());
			userInfo.setIsEmptyPassword(isEmptyPassword);
			if (StringUtils.isNotBlank(userDetailDTO.getBigHeadPortrait())) {
				userInfo.setBigHeadPortrait(prefixURL.concat(userDetailDTO.getBigHeadPortrait()));
			}
		}
		
		return userInfo;
	}
	
	
	/***
	 * 检测当前用户名是否存在
	 * @param jsoncallback
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/loginNameIsExist", produces = "text/html;charset=UTF-8")
	public String loginNameIsExist(String jsoncallback, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			String oldLoginName = request.getParameter("oldLoginName");
			String newLoginName = request.getParameter("newLoginName");
			
			// 验证参数
			if (StringUtils.isBlank(newLoginName)) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				result.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			boolean isExist = false;
			if (!oldLoginName.equals(newLoginName)) {
				isExist = userService.isLogonNameExist(newLoginName);
			}
			result.put("result", true);
			result.put("isExist", isExist);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
		}catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", e.getExtErrCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("检测当前用户名是否存在发生异常：", e);
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("检测当前用户名是否存在发生异常：", e);
		}

		logger.info("检测当前用户名是否存在返回结果：" + JSON.toJSONString(result));
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	
	/***
	 * 检测用户昵称是否存在
	 * @param jsoncallback
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/petNameIsExist", produces = "text/html;charset=UTF-8")
	public String petNameIsExist(String jsoncallback, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			String oldPetName = request.getParameter("oldPetName");
			String newPetName = request.getParameter("newPetName");
			
			// 验证参数
			if (StringUtils.isBlank(newPetName)) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				result.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			boolean isExist = false;
			if (!oldPetName.equals(newPetName)) {
				isExist = userService.isPetNameExist(newPetName);
			}
			result.put("result", true);
			result.put("isExist", isExist);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
		}catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", e.getExtErrCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("检测当前用户昵称是否存在发生异常：", e);
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("检测当前用户昵称是否存在发生异常：", e);
		}

		logger.info("检测当前用户昵称是否存在返回结果：" + JSON.toJSONString(result));
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/**
	 * 更新用户昵称
	 * @param jsoncallback
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateUserPetName", produces = "text/html;charset=UTF-8")
	public String updateUserPetName(String jsoncallback, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String remoteIp = HttpUtil.getRemoteIpAddr(request);	//IP地址
		String deviceId = request.getParameter("deviceId");		//设备ID
		String sysName = request.getParameter("sysName");		//系统名称
		String sysVersion = request.getParameter("sysVersion");	//系统版本
		try {
			String oldPetName = request.getParameter("oldPetName");
			String newPetName = request.getParameter("newPetName");
			
			// 验证参数
			if (StringUtils.isBlank(oldPetName) || StringUtils.isBlank(newPetName)) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				result.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			oldPetName = URLDecoder.decode(oldPetName, "UTF-8");	//解码
			newPetName = URLDecoder.decode(newPetName, "UTF-8");	//解码
			
			// 检测昵称是否存在
			boolean isExist = false;
			if (!oldPetName.equals(newPetName)) {
				isExist = userService.isPetNameExist(newPetName);
			}
			
			boolean updateResult = false; //更新结果
			
			if(!isExist) {
				// 如果昵称不存在则更新
				String userId = SessionUtil.getUserId(request);
				
				UserDetailDTO userDetailDTO = new UserDetailDTO();
				userDetailDTO.setUserId(Long.parseLong(userId));
				userDetailDTO.setPetName(newPetName);
				
				UserOperateLogInfoDTO userOperateLogInfoDTO = new UserOperateLogInfoDTO();
				userOperateLogInfoDTO.setDeviceNum(deviceId);
				userOperateLogInfoDTO.setOperAddr(remoteIp);
				userOperateLogInfoDTO.setSysName(sysName);
				userOperateLogInfoDTO.setSysNum(sysVersion);
				userOperateLogInfoDTO.setUserDetailDTO(userDetailDTO);
				updateResult = userService.modifyUserDetailDTO(userOperateLogInfoDTO);
			} else {
				// 如果昵称已存在
				result.put("result", false);
				result.put("errorCode", ErrorCode.UserPetNameExists.getErrorCode());
				result.put("errorMsg", ErrorCode.UserPetNameExists.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			if(updateResult) {
				//更新成功
				result.put("result", updateResult);
				result.put("errorCode", ErrorCode.Success.getErrorCode());
				result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			} else {
				//更新失败
				result.put("result", updateResult);
				result.put("errorCode", ErrorCode.UserUpdatePetNameFaild.getErrorCode());
				result.put("errorMsg", ErrorCode.UserUpdatePetNameFaild.getErrorMsg());
			}
			
		}catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", e.getExtErrCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("更新用户昵称发生异常：", e);
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("更新用户昵称发生异常：", e);
		}

		logger.info("更新用户昵称返回结果：" + JSON.toJSONString(result));
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	
	/**
	 * 如果文件不存在就创建
	 * @param fullFileName
	 */
	public static void createFolder(String fullFileName) {
		File picFile = new File(fullFileName);
		String picParentPath = picFile.getParent();
		File picParentFile = new File(picParentPath);
		if (!picParentFile.exists()) {
			picParentFile.mkdirs();
		}
	}
	
	
	/**
	 * 绑定手机，如果手机号已绑定，则先解绑再绑定
	 * @param jsoncallback
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/bindMobile", produces = "text/html;charset=UTF-8" )
	public String bindMobile(String jsoncallback,HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		String mobile = request.getParameter("mobile"); //手机号
		String validateCode = request.getParameter("validateCode");
		String remoteIp = HttpUtil.getRemoteIpAddr(request);	//IP地址
		String deviceId = request.getParameter("deviceId");		//设备ID
		String sysName = request.getParameter("sysName");		//系统名称
		String sysVersion = request.getParameter("sysVersion");	//系统版本
		String userSource = request.getParameter("userSource");//

		//检验参数
		if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(validateCode)) {
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
								if(userSource==null||userSource.equals("")){//老版本
									result=bindMobileByChangeMobile(request, oldUserBaseDTO);
								}else{
									result=bindMobileByFederateLogin();
								}
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
	 * 修改手机号码的绑定处理逻辑
	 * @param request
	 * @param oldUserBaseDTO
	 * @return
	 */
	public Map bindMobileByChangeMobile(HttpServletRequest request,UserBaseDTO oldUserBaseDTO){
		String mobile = request.getParameter("mobile"); //手机号
		String remoteIp = HttpUtil.getRemoteIpAddr(request);	//IP地址
		String deviceId = request.getParameter("deviceId");		//设备ID
		String sysName = request.getParameter("sysName");		//系统名称
		String sysVersion = request.getParameter("sysVersion");	//系统版本
		Map<String, Object> result = new HashMap<String, Object>();
			//检测是否新手机
			boolean newMobile = userService.isLogonNameCanRegister(mobile);
			if(!newMobile) {
				//如果不是新手机，则解绑
				String channelId = GlobalConstants.CHANNEL_ID;
				//根据用户名查询用户信息
				UserDetailDTO user = userService.getUserBasicInfoByLogonName(mobile, Integer.parseInt(channelId));
				if(user != null) {
					//解绑
					userService.deleteUserAuthen(user.getUserId(), "1", mobile, remoteIp);
					
//					boolean hasAsset = userService.isUserHasAsset(String.valueOf(user.getUserId()));
//					if(hasAsset) {
						//检测账号是否是空密码
						boolean isEmptyPassword = userService.isEmptyPassword(user.getUserId());
						String password = "";
						if(isEmptyPassword) {
							//如果密码为空，则生成6位随机密码，发送短信
							password = Tools.getRandomPassword();
						}
						String account = getUserAccount(user);
						//3.发送短信
						String smsBody = "尊敬的走秀用户，您的手机号已与原账户解绑并绑定到当前账户，原账户可通过"
								+ account + "和密码"+password+"登录查看订单信息和余额信息，感谢您的支持！";
						logger.info("用户手机号码："+ mobile + ",信息内容：" + smsBody);
						boolean sendFlag = iSMSService.sendSMS(smsBody, mobile, "", mobile);
						logger.info("短信发送状态：sendFlag=" + sendFlag);
						logger.info("绑定手机短信发送内容：smsBody=" + smsBody);
						
						if(isEmptyPassword && sendFlag) {
							//如果生成了密码，则修改用户的密码
							userService.resetUserPassword(user.getUserId(), password);
						}
//					}
				}
			}
			
			//修改手机号和手机绑定状态
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
			
			userService.modifyUserAuthen(userOperateLogInfoDTO);
			
			//返回用户信息
			UserDetailDTO userDetailDTO = userService.getUserDetailDTOByUserId(oldUserBaseDTO.getUserId());
			result.put("userInfo", getUserInfo(userDetailDTO));
			
			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			
			return result;
	
	}
	
	/**
	 * 第三方手机绑定处理
	 * @return
	 */
	public Map bindMobileByFederateLogin(){
		Map<String, Object> result = new HashMap<String, Object>();

	  return result;
	}
	
	
	/**
	 * 获取用户账号
	 * @param userDetailDTO
	 * @return
	 */
	private String getUserAccount(UserDetailDTO userDetailDTO) {
		String account = "";
		
		String userEmail = userDetailDTO.getEmail();
		if(!StringUtils.isEmpty(userEmail)) {
			//邮箱不为空
			int len = userEmail.indexOf("@");
			if(len > 4) {
				account = userEmail.substring(0, len-4) + "****" + userEmail.substring(len, userEmail.length());
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
	 * 发送绑定邮箱邮件
	 * @param jsoncallback
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sendBindEmail", produces = "text/html;charset=UTF-8")
	public String sendBindEmail(String jsoncallback,HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		String email = request.getParameter("email"); //邮箱
		String deviceId = request.getParameter("deviceId"); //设备ID
		//检验参数
		if(StringUtils.isEmpty(email) || StringUtils.isEmpty(deviceId)) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			result.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, result);
		}
		
		try {
			//1.检测邮箱是否相同
			String userId = SessionUtil.getUserId(request);
			UserBaseDTO userBaseDTO = userService.getUserBasicInfoByUserId(Long.parseLong(userId));
			if(userBaseDTO != null && email.equals(userBaseDTO.getEmail())) {
				//邮箱相同
				result.put("result", false);
				result.put("errorCode", ErrorCode.EmailIsSame.getErrorCode());
				result.put("errorMsg", ErrorCode.EmailIsSame.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			//2.检测要绑定的邮箱是否已存在
			String channelId = GlobalConstants.CHANNEL_ID;
			UserDetailDTO user = userService.getUserBasicInfoByLogonName(email, Integer.parseInt(channelId));
			if(user != null) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.EmailHasBinding.getErrorCode());
				result.put("errorMsg", ErrorCode.EmailHasBinding.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			//3.发送绑定邮箱邮件
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("email", email);
			paraMap.put("deviceId", deviceId);
			paraMap.put("limitTimes", MobileCommonConstant.SMS_LIMIT_TIMES);
			paraMap.put("type", MobileCommonConstant.EMAIL_BINDING_EMAIL);
			
			String code = messageCodeService.getEmailVerifyCode(paraMap);	//获取邮箱验证码
			
			String creator = "user@xiu.com";
			String senderName = "走秀网";
			String subject = "走秀账号-修改邮箱身份验证";
			String body = EmailTemplateUtil.getBindingEmailBody(code);
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
			logger.error(" 发送绑定邮箱邮件时发生异常，邮箱："+email+"，错误信息：", e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/**
	 * 绑定邮箱
	 * @param jsoncallback
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/bindEmail", produces = "text/html;charset=UTF-8")
	public String bindEmail(String jsoncallback,HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		String email = request.getParameter("email"); 		//邮箱
		String code = request.getParameter("validateCode"); //验证码
		String remoteIp = HttpUtil.getRemoteIpAddr(request);	//IP地址
		String deviceId = request.getParameter("deviceId");		//设备ID
		String sysName = request.getParameter("sysName");		//系统名称
		String sysVersion = request.getParameter("sysVersion");	//系统版本
		
		//检验参数
		if(StringUtils.isEmpty(email) || StringUtils.isEmpty(code)) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			result.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, result);
		}
		
		try {
			String channelId = GlobalConstants.CHANNEL_ID;
			//检测要绑定的邮箱是否已存在
			UserDetailDTO user = userService.getUserBasicInfoByLogonName(email, Integer.parseInt(channelId));
			if(user != null) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.EmailHasBinding.getErrorCode());
				result.put("errorMsg", "该邮箱已绑定其他走秀账号，请更换邮箱重试");
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			//检验邮箱验证码
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("email", email);
			paraMap.put("code", code);
			paraMap.put("time", "1"); //查询有效时间
			paraMap.put("type", MobileCommonConstant.EMAIL_BINDING_EMAIL);
			//检测邮箱验证码是否存在
			boolean existsStatus = messageCodeService.isEmailVerifyCodeExists(paraMap);
			if(!existsStatus) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.VerifyCodeHasFailed.getErrorCode());
				result.put("errorMsg", ErrorCode.VerifyCodeHasFailed.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			String userId = SessionUtil.getUserId(request); //用户ID
			//修改邮箱
			UserOperateLogInfoDTO userOperateLogInfoDTO = new UserOperateLogInfoDTO();
			userOperateLogInfoDTO.setDeviceNum(deviceId);
			userOperateLogInfoDTO.setOperAddr(remoteIp);
			userOperateLogInfoDTO.setSysName(sysName);
			userOperateLogInfoDTO.setSysNum(sysVersion);
			
			ModifyUserAuthenDTO modifyUserAuthenDTO = new ModifyUserAuthenDTO();
			modifyUserAuthenDTO.setUserId(Long.parseLong(userId));
			modifyUserAuthenDTO.setAuthenType("0");
			modifyUserAuthenDTO.setAuthenValue(email);
			modifyUserAuthenDTO.setIpAddr(remoteIp);
			userOperateLogInfoDTO.setModifyUserAuthenDTO(modifyUserAuthenDTO);
			userService.modifyUserAuthen(userOperateLogInfoDTO);
			
			//修改成功，清除邮箱验证码
			paraMap.remove("code");
			paraMap.put("code", "");
			messageCodeService.updateEmailVerifyCode(paraMap);
			
			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("绑定邮箱时发生异常，邮箱："+email+"，错误信息：", e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/**
	 * 发送短信验证码-用户绑定时使用
	 * @param jsoncallback
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sendSmsVerifyCodeBinding", produces = "text/html;charset=UTF-8")
	public String sendLoginSMSVerifyCode(String jsoncallback, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String mobile = request.getParameter("mobile"); //手机号
		String deviceId = request.getParameter("deviceId");	//设备号
		String forceBinding = request.getParameter("forceBinding"); //强制绑定 Y.是 N.不是
		String userSource = request.getParameter("userSource");//如果有传说明是新用户，还没注册走秀
		
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
						resultMap=sendSMSNotifyCheck(request, mobile);
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
	 * 发送语音验证码-用户绑定时使用
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
						resultMap=sendSMSNotifyCheck(request, mobile);
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
	
	
	
	/**
	 * 发送短信验证码前的提示检查（最初版本）
	 * @param request
	 * @param mobile
	 * @return
	 */
	public Map sendSMSNotifyCheck(HttpServletRequest request,String mobile){
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
//		if(hasAsset) {
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
//		}
		return null;
	}
	
	
	
	
}
