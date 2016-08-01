/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-22 下午3:34:50 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.mobile.portal.ei.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.util.ParseProperties;
import com.xiu.mobile.portal.constants.ErrConstants;
import com.xiu.mobile.portal.constants.GlobalConstants;
import com.xiu.mobile.portal.ei.EIMobileSalesManager;
import com.xiu.mobile.portal.ei.EIUUCManager;
import com.xiu.mobile.portal.facade.utils.DateUtil;
import com.xiu.mobile.portal.model.IntegralVo;
import com.xiu.uuc.facade.AcctChangeFacade;
import com.xiu.uuc.facade.AcctItemFacade;
import com.xiu.uuc.facade.AppDeviceFacade;
import com.xiu.uuc.facade.BankAcctFacade;
import com.xiu.uuc.facade.UserManageFacade;
import com.xiu.uuc.facade.dto.AcctChangeDTO;
import com.xiu.uuc.facade.dto.AcctChangeExtDTO;
import com.xiu.uuc.facade.dto.AcctItemDTO;
import com.xiu.uuc.facade.dto.AppDeviceDTO;
import com.xiu.uuc.facade.dto.BankAcctDTO;
import com.xiu.uuc.facade.dto.DeviceInfoDTO;
import com.xiu.uuc.facade.dto.IntegeralRuleDTO;
import com.xiu.uuc.facade.dto.ModifyUserAuthenDTO;
import com.xiu.uuc.facade.dto.RegisterUserDTO;
import com.xiu.uuc.facade.dto.Result;
import com.xiu.uuc.facade.dto.UnionInfoDTO;
import com.xiu.uuc.facade.dto.UnionUserBindingDTO;
import com.xiu.uuc.facade.dto.UserAcctDTO;
import com.xiu.uuc.facade.dto.UserBaseDTO;
import com.xiu.uuc.facade.dto.UserDetailDTO;
import com.xiu.uuc.facade.dto.UserOperateLogInfoDTO;
import com.xiu.uuc.facade.dto.VirtualAcctItemDTO;
import com.xiu.uuc.facade.util.ErrorCodeConstant;
import com.xiu.uuc.facade.util.FacadeConstant;
import com.xiu.uuc.facade.util.Page;

/** 
 * <p>
 * ************************************************************** 
 * @Description: 用户中心
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-22 下午3:34:50 
 * ***************************************************************
 * </p>
 */
@Service("eiuucManager")
public class EIUUCManagerImpl implements EIUUCManager {
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(EIUUCManagerImpl.class);
	
	@Autowired
	private UserManageFacade userManageFacade;
	@Autowired
	private BankAcctFacade bankAcctFacade;
	@Autowired
	private AcctItemFacade acctItemFacade;
	@Autowired
	private AcctChangeFacade acctChangeFacade;
	@Autowired
	private EIMobileSalesManager eiMobileSalesManager;
	
	@Autowired
	private AppDeviceFacade appDeviceFacade ;
	/**
	 * 判断登录名是否存在
	 * @param logonName
	 * @param userSource
	 * @return
	 */
	@Override
	public Result isLogonNameExist(String logonName) {
		return isLogonNameExist(logonName, null);
	}
	
	/**
	 * 判断登录名是否存在（联合登陆）
	 * @param logonName
	 * @param userSource
	 * @return
	 */
	@Override
	public Result isPetNameExist(String petName) {
		Assert.notNull(petName, "petName should be not null.");

		Result result = null;
		try {
			// 判断昵称是否存在
			result = userManageFacade.isPetNameExist(petName);
		} catch (Exception e) {
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, e);
		}
		
		if(!"1".equals(result.getSuccess())) {
			LOGGER.error("invoke remote interface [uuc] userManageFacade.isPetNameExist error.");
			LOGGER.error("ErrorCode:" + result.getErrorCode());
			LOGGER.error("ErrorMessage:" + result.getErrorMessage());
			
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, result.getErrorCode(), result.getErrorMessage());
		}
		
		return result;
	}
	
	/**
	 * 判断登录名是否存在（联合登陆）
	 * @param logonName
	 * @param userSource
	 * @return
	 */
	@Override
	public Result isLogonNameExist(String logonName, Integer iPartnerChannelId) {
		Assert.notNull(logonName, "logonName should be not null.");
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("invoke remote interface [uuc] userManageFacade.isLogonNameExist");
			LOGGER.debug("logonName ： " + logonName);
			LOGGER.debug("iPartnerChannelId ： " + iPartnerChannelId);
		}
		
		Result result = null;
		try {
			if (iPartnerChannelId != null) { // 联合登陆渠道ID
				result = userManageFacade.isLogonNameExist(logonName, iPartnerChannelId);
			} else {
				result = userManageFacade.isLogonNameExist(logonName);
			}
		} catch (Exception e) {
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, e);
		}
		
		if(!"1".equals(result.getSuccess())) {
			LOGGER.error("invoke remote interface [uuc] userManageFacade.isLogonNameExist error.");
			LOGGER.error("ErrorCode:" + result.getErrorCode());
			LOGGER.error("ErrorMessage:" + result.getErrorMessage());
			
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_BIZ_ILNE_ERR, 
					result.getErrorCode(), result.getErrorMessage());
		}
		
		return result;
	}
	
	/***
	 * 判断登陆名是否能注册
	 * @param logonName
	 * @return
	 */
	@Override
	public Result isLogonNameCanRegister(String logonName) {
		return isLogonNameCanRegister(logonName, GlobalConstants.UUC_USERNAME_PHONE);
	}
	
	/***
	 * 判断登陆名是否能注册
	 * @param logonName
	 * @param type(1: 邮箱, 2:手机, 3:呢称)
	 * @return
	 */
	@Override
	public Result isLogonNameCanRegister(String logonName,String type) {
		Assert.notNull(logonName, "logonName should be not null.");
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("invoke remote interface [uuc] userManageFacade.isLogonNameCanRegister");
			LOGGER.debug("logonName ： " + logonName);
		}
		
		Result result = null;
		try {
			result = userManageFacade.isLogonNameCanRegister(logonName, type);
		} catch (Exception e) {
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, e);
		}
		
		if(!"1".equals(result.getSuccess())) {
			LOGGER.error("invoke remote interface [uuc] userManageFacade.isLogonNameCanRegister error.");
			LOGGER.error("ErrorCode:" + result.getErrorCode());
			LOGGER.error("ErrorMessage:" + result.getErrorMessage());
			
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_BIZ_ILNE_ERR, 
					result.getErrorCode(), result.getErrorMessage());
		}
		
		return result;
	}
	
	/**
	 * 注册用户
	 * @return
	 */
	public Result registerUser(RegisterUserDTO registerUserDTO) {
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("invoke remote interface [uuc] userManageFacade.registerUser");
			LOGGER.debug("partnerId ： " + registerUserDTO.getPartnerId());
			LOGGER.debug("logonName ： " + registerUserDTO.getLogonName());
			LOGGER.debug("clientIp ： " + registerUserDTO.getRegisterIp());
			LOGGER.debug("iPartnerChannelId ： " + registerUserDTO.getPartnerChannelId());
			LOGGER.debug("userSource： " + registerUserDTO.getUserSource());
		}
		
		Result result = null;
		try {
			result = userManageFacade.registerUser(registerUserDTO);
		} catch (Exception e) {
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, e);
		}
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("invoke remote interface [uuc] userManageFacade.registerUser");
			LOGGER.debug("result.success: " + result.getSuccess());
			LOGGER.debug("result.errorCode: " + result.getErrorCode());
			LOGGER.debug("result.errorMessage: " + result.getErrorMessage());
			LOGGER.debug("result.data: " + result.getData());
		}
		
		if (!"1".equals(result.getSuccess())) {
			LOGGER.error("invoke remote interface [uuc] userManageFacade.registerUser error.");
			LOGGER.error("ErrorCode:" + result.getErrorCode());
			LOGGER.error("ErrorMessage:" + result.getErrorMessage());
			
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_BIZ_REU_ERR, 
					result.getErrorCode(), result.getErrorMessage());
		}else{
			//注册成功后，赠送新用户礼包
			String isRegisterSendCoupon = ParseProperties
			.getPropertiesValue(GlobalConstants.IS_REGISTER_SEND_COUPON);

			if (isRegisterSendCoupon != null && isRegisterSendCoupon.equals("TRUE")) {
				try {
					eiMobileSalesManager.userRegisterGiveCoupon("register", "mobile", 
							Long.parseLong(result.getData().toString()), registerUserDTO.getLogonName());
				} catch (Exception ex) {
					LOGGER.error("注册送券失败,用户名：" + registerUserDTO.getLogonName());
				}
			}
		}
		return result;
	}

	@Override
	public UserBaseDTO getUserBasicInfoByUserId(Long userId) {
		// 调用用户中心接口查询用户详细信息
		Assert.notNull(userId, "用户userId不能为空.");
		com.xiu.uuc.facade.dto.Result result = null;
		try{
			result = this.userManageFacade.getUserBasicInfoByUserId(userId);
		}catch(Exception ex){
			LOGGER.error("{}.getUserBasicInfoByUserId 调用UUC接口获取用户信息异常 | message={}",
					new Object[]{userManageFacade.getClass(),ex.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_BIZ_ERR, ex);
		}
		
		// 查询用户基本信息出错
		if ("0".equals(result.isSuccess())) {
			LOGGER.error("{}.getUserBasicInfoByUserId 调用UUC接口获取用户信息失败 | errCode={} | errMessage={}",
					new Object[]{userManageFacade.getClass(),result.getErrorCode(),result.getErrorMessage()});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_USER_INFO_ERR, result.getErrorCode(), result.getErrorMessage());
		}
		UserBaseDTO user = (UserBaseDTO) result.getData();
		return user;
	}
	
	@Override
	public UserDetailDTO getUserDetailDTOByUserId(Long userId) {
		// 调用用户中心接口查询用户详细信息
		Assert.notNull(userId, "用户userId不能为空.");
		com.xiu.uuc.facade.dto.Result result = null;
		try{
			result = this.userManageFacade.getUserDetailInfoByUserId(userId);
		}catch(Exception ex){
			LOGGER.error("{}.getUserDetailInfoByUserId 调用UUC接口获取用户信息异常 | message={}",
					new Object[]{userManageFacade.getClass(),ex.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_BIZ_ERR, ex);
		}
		
		// 查询用户详细信息出错
		if ("0".equals(result.isSuccess())) {
			LOGGER.error("{}.getUserDetailInfoByUserId 调用UUC接口获取用户信息失败 | errCode={} | errMessage={}",
					new Object[]{userManageFacade.getClass(),result.getErrorCode(),result.getErrorMessage()});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_USER_INFO_ERR, result.getErrorCode(), result.getErrorMessage());
		}
		UserDetailDTO user = (UserDetailDTO) result.getData();
		return user;
	}
	
	@Override
	public String getPasswordByLogonName(String logonName) {
		// 调用用户中心接口查询登录密码
		Assert.notNull(logonName, "用户logonName不能为空.");
		com.xiu.uuc.facade.dto.Result result = null;
		try{
			String channelId = GlobalConstants.CHANNEL_ID;
			result = this.userManageFacade.getPasswordByLogonNameAndChannelId(logonName, Integer.parseInt(channelId));
		}catch(Exception ex){
			LOGGER.error("{}.getPasswordByLogonNameAndChannelId 调用UUC接口获取用户密码异常 | message={}",
					new Object[]{userManageFacade.getClass(),ex.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_BIZ_ERR, ex);
		}
		
		// 查询用户基本信息出错
		if ("0".equals(result.isSuccess())) {
			LOGGER.error("{}.getPasswordByLogonNameAndChannelId 调用UUC接口获取用户密码失败 | errCode={} | errMessage={}",
					new Object[]{userManageFacade.getClass(),result.getErrorCode(),result.getErrorMessage()});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_USER_INFO_ERR, result.getErrorCode(), result.getErrorMessage());
		}
		String password = (String) result.getData();
		return password;
	}
	
	@Override
    public Result modifyUserPassword(Long userId, String oldPassword, String newPassword) {
        Assert.notNull(userId);
        Assert.notNull(oldPassword);
        Assert.notNull(newPassword);
        LOGGER.entry(userId,oldPassword,newPassword);
        
        Result result = null;
        try {
            //接口调用
            result = userManageFacade.modifyUserPassword(userId, oldPassword, newPassword);
        } catch (Exception e) {
            throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, e);
        }

        Assert.notNull(result);
        if (FacadeConstant.FALSE.equals(result.isSuccess())) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("{}.modifyUserPassword  isSuccess={}, errorCode={}, errorMessage={}",
                        new Object[] { this.getClass(), result.isSuccess(), result.getErrorCode(), result.getErrorMessage() });
            }
            if(!ErrorCodeConstant.SYSTEM_ERROR_CODE.equals(result.getErrorCode())){
                throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, result.getErrorCode(), result.getErrorMessage());
            }else{
                throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, result.getErrorCode(), result.getErrorMessage());
            }           
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("{}.modifyUserPassword  isSuccess={}", new Object[] { this.getClass(), result.isSuccess() });
        }   
        
        return result;
    }
	
	@Override
    public Result modifyUserBaseInfo(UserBaseDTO userBaseDTO) {
        Assert.notNull(userBaseDTO);
        Result result = null;
        try {
            //接口调用
            result = userManageFacade.modifyUserBaseInfo(userBaseDTO);
        } catch (Exception e) {
        	if(result == null) {
        		LOGGER.error("{}.modifyUserBaseInfo", new Object[] { this.getClass() });
        	} else {
        		LOGGER.error("{}.modifyUserBaseInfo  isSuccess={}, errorCode={}, errorMessage={}",
                        new Object[] { this.getClass(), result.isSuccess(), result.getErrorCode(), result.getErrorMessage() });
        	}
        	
            throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, e);
        }

        Assert.notNull(result);
        if (FacadeConstant.FALSE.equals(result.isSuccess())) {
            if (LOGGER.isErrorEnabled()) {
                	LOGGER.error("{}.modifyUserBaseInfo  isSuccess={}, errorCode={}, errorMessage={}",
                            new Object[] { this.getClass(), result.isSuccess(), result.getErrorCode(), result.getErrorMessage() });
            }     
            if(!ErrorCodeConstant.SYSTEM_ERROR_CODE.equals(result.getErrorCode())){
                throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, result.getErrorCode(), result.getErrorMessage());
            }else{
                throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, result.getErrorCode(), result.getErrorMessage());
            } 
        }
         
        return result;
    }
	
	public Result modifyUserBaseInfo(UserOperateLogInfoDTO userOperateLogInfoDTO) {
		Assert.notNull(userOperateLogInfoDTO);
        Result result = null;
        try {
            //接口调用
            result = userManageFacade.modifyUserBaseInfo(userOperateLogInfoDTO);
        } catch (Exception e) {
        	if(result == null) {
        		LOGGER.error("{}.modifyUserBaseInfo", new Object[] { this.getClass() });
        	} else {
        		LOGGER.error("{}.modifyUserBaseInfo  isSuccess={}, errorCode={}, errorMessage={}",
                        new Object[] { this.getClass(), result.isSuccess(), result.getErrorCode(), result.getErrorMessage() });
        	}
        	
            throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, e);
        }

        Assert.notNull(result);
        if (FacadeConstant.FALSE.equals(result.isSuccess())) {
            if (LOGGER.isErrorEnabled()) {
                	LOGGER.error("{}.modifyUserBaseInfo  isSuccess={}, errorCode={}, errorMessage={}",
                            new Object[] { this.getClass(), result.isSuccess(), result.getErrorCode(), result.getErrorMessage() });
            }     
            if(!ErrorCodeConstant.SYSTEM_ERROR_CODE.equals(result.getErrorCode())){
                throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, result.getErrorCode(), result.getErrorMessage());
            }else{
                throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, result.getErrorCode(), result.getErrorMessage());
            } 
        }
         
        return result;
	}
	
	@Override
    public Result bindUser(UnionUserBindingDTO unionUserBindingDTO) {
        Assert.notNull(unionUserBindingDTO);
        Result result = null;
        try {
            //接口调用
            result = userManageFacade.bindingUser(unionUserBindingDTO);
        } catch (Exception e) {
        	if(result == null) {
        		LOGGER.error("{}.bindingUser ", new Object[] { this.getClass() });
        	} else {
        		LOGGER.error("{}.bindingUser  isSuccess={}, errorCode={}, errorMessage={}",
                        new Object[] { this.getClass(), result.isSuccess(), result.getErrorCode(), result.getErrorMessage() });
        	}
        	
            throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, e);
        }

        Assert.notNull(result);
        if (FacadeConstant.FALSE.equals(result.isSuccess())) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("{}.bindingUser  isSuccess={}, errorCode={}, errorMessage={}",new Object[] { this.getClass(), result.isSuccess(), result.getErrorCode(), result.getErrorMessage() });
            }     
            if(!ErrorCodeConstant.SYSTEM_ERROR_CODE.equals(result.getErrorCode())){
                throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, result.getErrorCode(), result.getErrorMessage());
            }else{
                throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, result.getErrorCode(), result.getErrorMessage());
            } 
        }
         
        return result;
    }
	
	public Result bindUser(UserOperateLogInfoDTO userOperateLogInfoDTO) {
		Assert.notNull(userOperateLogInfoDTO);
        Result result = null;
        try {
            //接口调用
            result = userManageFacade.bindingUser(userOperateLogInfoDTO);
        } catch (Exception e) {
        	if(result == null) {
        		LOGGER.error("{}.bindingUser ", new Object[] { this.getClass() });
        	} else {
        		LOGGER.error("{}.bindingUser  isSuccess={}, errorCode={}, errorMessage={}",
                        new Object[] { this.getClass(), result.isSuccess(), result.getErrorCode(), result.getErrorMessage() });
        	}
        	
            throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, e);
        }

        Assert.notNull(result);
        if (FacadeConstant.FALSE.equals(result.isSuccess())) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("{}.bindingUser  isSuccess={}, errorCode={}, errorMessage={}",new Object[] { this.getClass(), result.isSuccess(), result.getErrorCode(), result.getErrorMessage() });
            }     
            if(!ErrorCodeConstant.SYSTEM_ERROR_CODE.equals(result.getErrorCode())){
                throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, result.getErrorCode(), result.getErrorMessage());
            }else{
                throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, result.getErrorCode(), result.getErrorMessage());
            } 
        }
         
        return result;
	}
	
	@Override
	public boolean unBindUser(UnionUserBindingDTO unionUserBindingDTO) {
		// TODO Auto-generated method stub
		Assert.notNull(unionUserBindingDTO);
        Result result = null;
        try {
            //接口调用
            result = userManageFacade.unbindUser(unionUserBindingDTO.getUserId(), unionUserBindingDTO.getPartnerId(), unionUserBindingDTO.getPartnerChannelId());
        } catch (Exception e) {
        	if(result == null) {
        		LOGGER.error("{}.unbindUser", new Object[] { this.getClass() });
        	} else {
        		LOGGER.error("{}.unbindUser  isSuccess={}, errorCode={}, errorMessage={}",
                        new Object[] { this.getClass(), result.isSuccess(), result.getErrorCode(), result.getErrorMessage() });
        	}
        	
            throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, e);
        }

        Assert.notNull(result);
        if (FacadeConstant.FALSE.equals(result.isSuccess())) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("{}.unbindUser  isSuccess={}, errorCode={}, errorMessage={}",new Object[] { this.getClass(), result.isSuccess(), result.getErrorCode(), result.getErrorMessage() });
            }     
            if(!ErrorCodeConstant.SYSTEM_ERROR_CODE.equals(result.getErrorCode())){
                throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, result.getErrorCode(), result.getErrorMessage());
            }else{
                throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, result.getErrorCode(), result.getErrorMessage());
            } 
        }
         
        boolean status = Boolean.parseBoolean(result.getData().toString());
        return status;
	}
	
	public boolean unBindUser(UserOperateLogInfoDTO userOperateLogInfoDTO) {
		Assert.notNull(userOperateLogInfoDTO);
        Result result = null;
        try {
            //接口调用
            result = userManageFacade.unbindUser(userOperateLogInfoDTO);
        } catch (Exception e) {
        	if(result == null) {
        		LOGGER.error("{}.unbindUser", new Object[] { this.getClass() });
        	} else {
        		LOGGER.error("{}.unbindUser  isSuccess={}, errorCode={}, errorMessage={}",
                        new Object[] { this.getClass(), result.isSuccess(), result.getErrorCode(), result.getErrorMessage() });
        	}
        	
            throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, e);
        }

        Assert.notNull(result);
        if (FacadeConstant.FALSE.equals(result.isSuccess())) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("{}.unbindUser  isSuccess={}, errorCode={}, errorMessage={}",new Object[] { this.getClass(), result.isSuccess(), result.getErrorCode(), result.getErrorMessage() });
            }     
            if(!ErrorCodeConstant.SYSTEM_ERROR_CODE.equals(result.getErrorCode())){
                throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, result.getErrorCode(), result.getErrorMessage());
            }else{
                throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, result.getErrorCode(), result.getErrorMessage());
            } 
        }
         
        boolean status = Boolean.parseBoolean(result.getData().toString());
        return status;
	}
	
	@Override
	public boolean isEmptyPassword(Long userId) {
		// TODO Auto-generated method stub
		Assert.notNull(userId);
        Result result = null;
        try {
            //接口调用
            result = userManageFacade.isEmptyPassword(userId);
        } catch (Exception e) {
        	if(result == null) {
        		LOGGER.error("{}.isEmptyPassword", new Object[] { this.getClass() });
        	} else {
        		LOGGER.error("{}.isEmptyPassword  isSuccess={}, errorCode={}, errorMessage={}",
                        new Object[] { this.getClass(), result.isSuccess(), result.getErrorCode(), result.getErrorMessage() });
        	} 
        	
            throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, e);
        }

        Assert.notNull(result);
        if (FacadeConstant.FALSE.equals(result.isSuccess())) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("{}.isEmptyPassword  isSuccess={}, errorCode={}, errorMessage={}",new Object[] { this.getClass(), result.isSuccess(), result.getErrorCode(), result.getErrorMessage() });
            }     
            if(!ErrorCodeConstant.SYSTEM_ERROR_CODE.equals(result.getErrorCode())){
                throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, result.getErrorCode(), result.getErrorMessage());
            }else{
                throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, result.getErrorCode(), result.getErrorMessage());
            } 
        }
         
        boolean status = Boolean.parseBoolean(result.getData().toString());
        return status;
	}
	
	@Override
    public Result modifyUserDetailDTO(UserDetailDTO userDetailDTO) {
        Assert.notNull(userDetailDTO);
        Result result = null;
        try {
        	LOGGER.info("userManageFacade.modifyUserDetailInfo：userDetailDTO="+userDetailDTO);
            //接口调用
            result = userManageFacade.modifyUserDetailInfo(userDetailDTO);
        } catch (Exception e) {
        	if(result == null) {
        		LOGGER.error("{}.modifyUserDetailInfo", new Object[] { this.getClass() });
        	} else {
        		LOGGER.error("{}.modifyUserDetailInfo  isSuccess={}, errorCode={}, errorMessage={}",
                        new Object[] { this.getClass(), result.isSuccess(), result.getErrorCode(), result.getErrorMessage() });
        	}
            
            throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, e);
        }

        Assert.notNull(result);
        if (FacadeConstant.FALSE.equals(result.isSuccess())) {
            if (LOGGER.isErrorEnabled()) {
            	LOGGER.error("{}.modifyUserDetailInfo  isSuccess={}, errorCode={}, errorMessage={}",
                        new Object[] { this.getClass(), result.isSuccess(), result.getErrorCode(), result.getErrorMessage() });
            }    
            if(!ErrorCodeConstant.SYSTEM_ERROR_CODE.equals(result.getErrorCode())){
                throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, result.getErrorCode(), result.getErrorMessage());
            }else{
                throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, result.getErrorCode(), result.getErrorMessage());
            } 
        }
         
        return result;
    }
	
	public Result modifyUserDetailDTO(UserOperateLogInfoDTO userOperateLogInfoDTO) {
		Assert.notNull(userOperateLogInfoDTO);
        Result result = null;
        try {
        	LOGGER.info("userManageFacade.modifyUserDetailInfo：userOperateLogInfoDTO="+userOperateLogInfoDTO);
            //接口调用
            result = userManageFacade.modifyUserDetailInfo(userOperateLogInfoDTO);
        } catch (Exception e) {
        	if(result == null) {
        		LOGGER.error("{}.modifyUserDetailInfo", new Object[] { this.getClass() });
        	} else {
        		LOGGER.error("{}.modifyUserDetailInfo  isSuccess={}, errorCode={}, errorMessage={}",
                        new Object[] { this.getClass(), result.isSuccess(), result.getErrorCode(), result.getErrorMessage() });
        	}
            
            throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, e);
        }

        Assert.notNull(result);
        if (FacadeConstant.FALSE.equals(result.isSuccess())) {
            if (LOGGER.isErrorEnabled()) {
            	LOGGER.error("{}.modifyUserDetailInfo  isSuccess={}, errorCode={}, errorMessage={}",
                        new Object[] { this.getClass(), result.isSuccess(), result.getErrorCode(), result.getErrorMessage() });
            }    
            if(!ErrorCodeConstant.SYSTEM_ERROR_CODE.equals(result.getErrorCode())){
                throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, result.getErrorCode(), result.getErrorMessage());
            }else{
                throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, result.getErrorCode(), result.getErrorMessage());
            } 
        }
         
        return result;
	}
	
	@Override
	public UserDetailDTO getUserBasicInfoByLogonName(String logonName,Integer channelId) {
		// 调用用户中心接口查询用户详细信息
		Assert.notNull(logonName, "用户logonName不能为空.");
		Assert.notNull(channelId, "用户channelId不能为空.");
		com.xiu.uuc.facade.dto.Result result = null;
		try{
			result = userManageFacade.getUserDetailInfoByLogonName(logonName, channelId);
		}catch(Exception ex){
			if(result == null) {
				LOGGER.error("{}.getUserDetailInfoByLogonName",new Object[] { this.getClass() });
			} else {
				LOGGER.error("{}.getUserDetailInfoByLogonName  isSuccess={}, errorCode={}, errorMessage={}",
	                    new Object[] { this.getClass(), result.isSuccess(), result.getErrorCode(), result.getErrorMessage() });
			}
			
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_BIZ_ERR, ex);
		}
		
		// 查询用户基本信息出错
		if ("0".equals(result.isSuccess())) {
			LOGGER.error("{}.getUserDetailInfoByLogonName 调用UUC接口获取用户信息失败 | errCode={} | errMessage={}",
					new Object[]{userManageFacade.getClass(),result.getErrorCode(),result.getErrorMessage()});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_USER_INFO_ERR, result.getErrorCode(), result.getErrorMessage());
		}
		UserDetailDTO user = (UserDetailDTO) result.getData();
		return user;
	}

	@Override
    public Result resetUserPassword(Long userId,String newPassword) {
        Assert.notNull(userId);
        Assert.notNull(newPassword);
        LOGGER.entry(userId,newPassword);
        
        Result result = null;
        try {
            //接口调用   参数分别是 userId,newPassword,optUserId,remark
            result = userManageFacade.resetUserPassword(userId, newPassword, userId.toString(), "");
        } catch(Exception e){
        	if(result == null) {
        		LOGGER.error("{}.resetUserPassword", new Object[] { this.getClass() });
        	} else {
        		LOGGER.error("{}.resetUserPassword  isSuccess={}, errorCode={}, errorMessage={}",
                        new Object[] { this.getClass(), result.isSuccess(), result.getErrorCode(), result.getErrorMessage() });
        	}
			
            throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, e);
        }

        Assert.notNull(result);
        if (FacadeConstant.FALSE.equals(result.isSuccess())) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("{}.resetUserPassword  isSuccess={}, errorCode={}, errorMessage={}",
                        new Object[] { this.getClass(), result.isSuccess(), result.getErrorCode(), result.getErrorMessage() });
            }
            if(!ErrorCodeConstant.SYSTEM_ERROR_CODE.equals(result.getErrorCode())){
                throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, result.getErrorCode(), result.getErrorMessage());
            }else{
                throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, result.getErrorCode(), result.getErrorMessage());
            }           
        }
        
        return result;
    }
	@Override
	public List<BankAcctDTO> getBankAcctListInfo(BankAcctDTO bankAcctDTO) {
		Result result = null;
		try {
			result = bankAcctFacade.getBankAcctListInfo(bankAcctDTO);
		} catch (Exception e) {
			LOGGER.error("调用UUC接口查询用户已有的提现账号列表异常exception:",e);
			LOGGER.error("{}.bankAcctFacade 查询用户已有的提现账号列表异常 | message={}",
					new Object[]{bankAcctFacade.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_BANKS_ERR, e);
		}
		if("0".equals(result.isSuccess())){
			LOGGER.error("{}.bankAcctFacade  调用UUC接口查询用户已有的提现账号列表失败 | errCode={} | errMessage={}",
					new Object[]{bankAcctFacade.getClass(),result.getErrorCode(),result.getErrorMessage()});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_BANKS_FAILED_ERR, result.getErrorCode(),"调用UUC接口查询用户已有的提现账号列表失败");
		}
		List<BankAcctDTO> bankAcctList=(List<BankAcctDTO>)result.getData();
		return bankAcctList;
	}

	@Override
	public BankAcctDTO findBankAcctDetailInfo(BankAcctDTO bankAcctDTO) {
		Result result = null;
		try {
			result = bankAcctFacade.findBankAcctDetailInfo(bankAcctDTO);
		} catch (Exception e) {
			LOGGER.error("调用UUC接口查询特定提现银行账号详情异常exception:",e);
			LOGGER.error("{}.bankAcctFacade 查询特定提现银行账号详情异常 | message={}",
					new Object[]{bankAcctFacade.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_BANKS_INFO_ERR, e);
		}
		if("0".equals(result.isSuccess())){
			LOGGER.error("{}.bankAcctFacade  调用UUC接口查询特定提现银行账号详情失败 | errCode={} | errMessage={}",
					new Object[]{bankAcctFacade.getClass(),result.getErrorCode(),result.getErrorMessage()});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_BANKS_INFO_FAILED_ERR, result.getErrorCode(),"调用UUC接口查询特定提现银行账号详情失败");
		}
		 BankAcctDTO bankAcct=(BankAcctDTO)result.getData();
		 return bankAcct;
	}
	
	@Override
	public Result addBankAcctInfo(BankAcctDTO bankAcctDTO) {
		Result result = null;
		try {
			result = bankAcctFacade.addBankAcctInfo(bankAcctDTO);
		} catch (Exception e) {
			LOGGER.error("调用UUC接口新增用户提现账号信息异常exception:",e);
			LOGGER.error("{}.bankAcctFacade 新增用户提现账号信息异常 | message={}",
					new Object[]{bankAcctFacade.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_BANKS_ADD_ERR, e);
		}
		if("0".equals(result.isSuccess())){
			LOGGER.error("{}.bankAcctFacade  调用UUC接口新增用户提现账号信息失败 | errCode={} | errMessage={}",
					new Object[]{bankAcctFacade.getClass(),result.getErrorCode(),result.getErrorMessage()});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_BANKS_ADD_FAILED_ERR, result.getErrorCode(),"调用UUC接口新增用户提现账号信息失败");
		}
		return result;
	}

	@Override
	public Result updateBankAcctInfo(BankAcctDTO bankAcctDTO) {
		Result result = null;
		try {
			result = bankAcctFacade.updateBankAcctInfo(bankAcctDTO);
		} catch (Exception e) {
			LOGGER.error("调用UUC接口编辑用户提现帐号信息异常exception:",e);
			LOGGER.error("{}.bankAcctFacade 编辑用户提现帐号信息 | message={}",
					new Object[]{bankAcctFacade.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_BANKS_UPD_ERR, e);
		}
		if("0".equals(result.isSuccess())){
			LOGGER.error("{}.bankAcctFacade  调用UUC接口编辑用户提现帐号信息失败 | errCode={} | errMessage={}",
					new Object[]{bankAcctFacade.getClass(),result.getErrorCode(),result.getErrorMessage()});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_BANKS_UPD_FAILED_ERR, result.getErrorCode(),"调用UUC接口编辑用户提现帐号信息失败");
		}
		return result;
	}

	@Override
	public Result deleteBankAcctInfo(BankAcctDTO bankAcctDTO) {
		Result result = null;
		try {
			result = bankAcctFacade.deleteBankAcctInfo(bankAcctDTO);
		} catch (Exception e) {
			LOGGER.error("调用UUC接口删除用户提现账号信息异常exception:",e);
			LOGGER.error("{}.bankAcctFacade 删除用户提现账号信息异常 | message={}",
					new Object[]{bankAcctFacade.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_BANKS_DEL_ERR, e);
		}
		if("0".equals(result.isSuccess())){
			LOGGER.error("{}.bankAcctFacade  调用UUC接口删除用户提现账号信息失败 | errCode={} | errMessage={}",
					new Object[]{bankAcctFacade.getClass(),result.getErrorCode(),result.getErrorMessage()});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_BANKS_DEL_FAILED_ERR, result.getErrorCode(),"调用UUC接口删除用户提现账号信息失败");
		}
		return result;
	}

	@Override
	public Result getVirtualAccountInfo(VirtualAcctItemDTO virtualAcctItemDTO) {
		Result result = null;
		try {
			result = acctItemFacade.getVirtualAccountInfo(virtualAcctItemDTO);
		} catch (Exception e) {
			LOGGER.error("调用UUC接口查询用户虚拟账户信息异常exception:",e);
			LOGGER.error("{}.acctItemFacade 查询用户虚拟账户信息异常 | message={}",
					new Object[]{acctItemFacade.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_VIRTUAL_ACCOUNT_ERR, e);
		}
		if("0".equals(result.isSuccess())){
			LOGGER.error("{}.acctItemFacade  调用UUC接口查询用户虚拟账户信息失败 | errCode={} | errMessage={}",
					new Object[]{acctItemFacade.getClass(),result.getErrorCode(),result.getErrorMessage()});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_VIRTUAL_ACCOUNT_FAILED_ERR, result.getErrorCode(),"调用UUC接口查询用户虚拟账户信息失败");
		}
		return result;
	}
	
	@Override
	public String checkIsFreezeUserAcct(UserAcctDTO userAcctDTO) {
		Result result = null;
		try {
			 result = acctChangeFacade.checkIsFreezeUserAcct(userAcctDTO);
		} catch (Exception e) {
			LOGGER.error("调用UUC接口校验当前用户账户是否被冻结异常exception:",e);// true表示被冻结 false表示未被冻结
			LOGGER.error("{}.acctChangeFacade 校验当前用户账户是否被冻结异常 | message={}",
					new Object[]{acctChangeFacade.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_CHECK_FREEZE_ERR, e);
		}
		if("0".equals(result.isSuccess())){
			LOGGER.error("{}.acctChangeFacade  调用UUC接口校验当前用户账户是否被冻结失败 | errCode={} | errMessage={}",
					new Object[]{acctChangeFacade.getClass(),result.getErrorCode(),result.getErrorMessage()});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_CHECK_FREEZE_FAILED_ERR, result.getErrorCode(),"调用UUC接口校验当前用户账户是否被冻结失败");
		}
		return (String) result.getData();
	}
	
	@Override
	public Result getVirtualChangeList(AcctChangeExtDTO acctChangeDTO) {
		Result result = null;
		try {
			 result = acctChangeFacade.getVirtualChangeListExt(acctChangeDTO);
		} catch (Exception e) {
			LOGGER.error("调用UUC接口获取用户的账户变动记录异常exception:",e);// true表示被冻结 false表示未被冻结
			LOGGER.error("{}.acctChangeFacade获取用户的账户变动记录异常 | message={}",
					new Object[]{acctChangeFacade.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_VIRTUAL_LIST_ERR, e);
		}
		if("0".equals(result.isSuccess())){
			LOGGER.error("{}.acctChangeFacade  调用UUC接口获取用户的账户变动记录失败 | errCode={} | errMessage={}",
					new Object[]{acctChangeFacade.getClass(),result.getErrorCode(),result.getErrorMessage()});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_CHECK_VIRTUAL_LIST_ERR, result.getErrorCode(),"调用UUC接口校验当前用户账户是否被冻结失败");
		}
		return result;
	}

	@Override
	public UnionInfoDTO getUnionInfoDTOByUserId(Long userId,Integer channelId) {
		Result result = null;
		try {
			 result = userManageFacade.getUnionInfoByUserIdAndPartnerChannelId(userId, channelId);
		} catch (Exception e) {
			LOGGER.error("调用UUC接口获取获取联合用户信息异常exception:",e);
			LOGGER.error("{}.getUnionInfoByUserIdAndPartnerChannelId获取联合用户信息异常 | message={}",
					new Object[]{userManageFacade.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_BIZ_ERR, e);
		}
		if("0".equals(result.isSuccess())){
			LOGGER.error("{}.getUnionInfoByUserIdAndPartnerChannelId 调用UUC接口获取获取联合用户信息失败 | errCode={} | errMessage={}",
					new Object[]{userManageFacade.getClass(),result.getErrorCode(),result.getErrorMessage()});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_BIZ_ERR, result.getErrorCode(),"调用UUC接口获取获取联合用户信息失败");
		}
		// 数据转换
		UnionInfoDTO unionInfoDTO = (UnionInfoDTO) result.getData();
		return unionInfoDTO;
	}

	@Override
	public List<UnionInfoDTO> getUnionInfoDTOListByUserId(Long userId) {
		Result result = null;
		try {
			 result = userManageFacade.getUnionInfoByUserId(userId);
		} catch (Exception e) {
			LOGGER.error("调用UUC接口获取获取联合用户列表信息异常exception:",e);
			LOGGER.error("{}.getUnionInfoByUserId获取联合用户列表信息异常 | message={}",
					new Object[]{userManageFacade.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_BIZ_ERR, e);
		}
		if("0".equals(result.isSuccess())){
			LOGGER.error("{}.getUnionInfoByUserId 调用UUC接口获取获取联合用户列表信息失败 | errCode={} | errMessage={}",
					new Object[]{userManageFacade.getClass(),result.getErrorCode(),result.getErrorMessage()});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_BIZ_ERR, result.getErrorCode(),"调用UUC接口获取获取联合用户列表信息失败");
		}
		
		if (result.getData() != null) {
			// 数据转换
			List<UnionInfoDTO> unionInfoDTOList = (List<UnionInfoDTO>) result.getData();
			return unionInfoDTOList;
		}else{
			return new ArrayList<UnionInfoDTO>();
		}
	}

	@Override
	public List<UnionInfoDTO> getUnionInfoByCondition(UnionInfoDTO unionInfoDTO) {
		Result result = null;
		try {
			 result = userManageFacade.getUnionInfoByCondition(unionInfoDTO);
		} catch (Exception e) {
			LOGGER.error("调用UUC接口获取获取联合用户信息异常exception:",e);
			LOGGER.error("{}.getUnionInfoByCondition获取联合用户信息异常 | message={}",
					new Object[]{userManageFacade.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_BIZ_ERR, e);
		}
		if("0".equals(result.isSuccess())){
			LOGGER.error("{}.getUnionInfoByCondition 调用UUC接口获取获取联合用户信息失败 | errCode={} | errMessage={}",
					new Object[]{userManageFacade.getClass(),result.getErrorCode(),result.getErrorMessage()});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_BIZ_ERR, result.getErrorCode(),"调用UUC接口获取获取联合用户信息失败");
		}
		// 数据转换
		if (result.getData() != null) {
			List<UnionInfoDTO> unionInfoDTOList = (List<UnionInfoDTO>) result.getData();
			return unionInfoDTOList;
		}else{
			return new ArrayList<UnionInfoDTO>();
		}
	}

	public Result modifyUserAuthen(ModifyUserAuthenDTO modityUserAuthenDTO) {
		Result result = null;
		try {
			 result = userManageFacade.modifyUserAuthen(modityUserAuthenDTO);
		} catch (Exception e) {
			LOGGER.error("调用UUC接口更新用户认证状态异常exception:",e);
			LOGGER.error("{}.userManageFacad更新用户认证状态异常 | message={}",
					new Object[]{userManageFacade.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_MODITY_USER_AUTHEN_ERR, e);
		}
		if("0".equals(result.isSuccess())){
			LOGGER.error("{}.userManageFacad 调用UUC接口更新用户认证状态失败 | errCode={} | errMessage={}",
					new Object[]{userManageFacade.getClass(),result.getErrorCode(),result.getErrorMessage()});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_MODITY_USER_AUTHEN_ERR, result.getErrorCode(),"调用UUC接口更新用户认证状态失败");
		}
		return result;
	}
	
	public Result modifyUserAuthen(UserOperateLogInfoDTO userOperateLogInfoDTO) {
		Result result = null;
		try {
			 result = userManageFacade.modifyUserAuthen(userOperateLogInfoDTO);
		} catch (Exception e) {
			LOGGER.error("调用UUC接口更新用户认证状态异常exception:",e);
			LOGGER.error("{}.userManageFacad更新用户认证状态异常 | message={}",
					new Object[]{userManageFacade.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_MODITY_USER_AUTHEN_ERR, e);
		}
		if("0".equals(result.isSuccess())){
			LOGGER.error("{}.userManageFacad 调用UUC接口更新用户认证状态失败 | errCode={} | errMessage={}",
					new Object[]{userManageFacade.getClass(),result.getErrorCode(),result.getErrorMessage()});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_MODITY_USER_AUTHEN_ERR, result.getErrorCode(),"调用UUC接口更新用户认证状态失败");
		}
		return result;
	}

	@Override
	public Result bindXiuUserToBaiduPushDevice(DeviceInfoDTO deviceInfoDTO) {
		Result result = null;
		try {
			 result = userManageFacade.bindBaiduDeviceInfo(deviceInfoDTO);
		} catch (Exception e) {
			LOGGER.error("绑定百度推送节点信息exception:",e);
			LOGGER.error("{}.userManageFacad绑定百度推送节点信息异常 | message={}",
					new Object[]{userManageFacade.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_BIND_BAIDU_DEVICE_ERROR, e);
		}
		return result;
	}

	@Override
	public Result setDefaultUserInfoFromTrd(long userId, int trdPtySource,
			String partnerId, String picURL, String trdName) {
		Result result = null;
		try {
			 result = userManageFacade.setDefaultUserInfoFromTrd(userId, trdPtySource, partnerId, picURL, trdName);
		} catch (Exception e) {
			LOGGER.error("处置默认第三方信息exception:",e);
			LOGGER.error("{}.userManageFacad处置默认第三方信息异常 | message={}",
					new Object[]{userManageFacade.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_SET_DEFAULT_FERDRATE_INFO_ERROR, e);
		}
		return result;
	}

	public Result deleteUserAuthen(ModifyUserAuthenDTO modifyUserAuthenDTO) {
		Result result = null;
		try {
			UserOperateLogInfoDTO operateInfo = new UserOperateLogInfoDTO();
			operateInfo.setModifyUserAuthenDTO(modifyUserAuthenDTO);
			result = userManageFacade.delUserAuthen(operateInfo);
		} catch (Exception e) {
			LOGGER.error("删除用户认证信息exception:",e);
			LOGGER.error("{}.userManageFacad删除用户认证信息异常 | message={}",
					new Object[]{userManageFacade.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_DELETE_USER_AUTHEN_ERR, e);
		}
		return result;
	}
	public Map<String,Object> getVirtualIntegralInfo(Long userId){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		AcctItemDTO acctItemDTO = new AcctItemDTO();
		try{
			acctItemDTO.setUserId(userId);
			Result result=acctItemFacade.getVirtualIntegralInfo(acctItemDTO);
			List<AcctItemDTO> acctItemList=(List<AcctItemDTO>)result.getData();
			String acctTypeCode=acctItemList.get(0).getAcctTypeCode();
			Long integral=0L;
			if(acctTypeCode.equals("03")){//03是积分
				integral=acctItemList.get(0).getTotalAmount();
			}
			resultMap.put("status", true);
			resultMap.put("totalIntegral", integral);//积分
			LOGGER.info(userId+"的总积分为："+integral);
		}catch(Exception e){
			resultMap.put("status", false);
			LOGGER.error("调用积分接口查询总积分异常："+e);
		}
		return resultMap; 
	}
	//积分明细
	public Map<String,Object> findIntegralInfoList(Long userId,int currPage,int pageSize){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		AcctChangeDTO acctChangeDTO = new AcctChangeDTO();
		List<IntegralVo> listIntegra=new ArrayList<IntegralVo>();
		try{
			acctChangeDTO.setUserId(userId);
			acctChangeDTO.setAcctTypeCode("03");
			acctChangeDTO.setCurrentPage(currPage);
			acctChangeDTO.setPageSize(pageSize);
			Result rs =acctChangeFacade.getVirtualChangeList(acctChangeDTO);
			if(rs.getSuccess().equals("1")){
				List<AcctChangeDTO> list=(List<AcctChangeDTO>)rs.getData();
				for(AcctChangeDTO dto:list){
					IntegralVo integralVo=new IntegralVo();
					if(dto.getIoTypeDesc().equals("进账")){
						integralVo.setType(1);
					}else{
						integralVo.setType(2);
					}
					integralVo.setCreateTime(DateUtil.formatTimesTampDate(dto.getCreateTime()));
					integralVo.setOrderCode(dto.getRltCode());
					integralVo.setIntegra(dto.getIoAmount());
					integralVo.setIntegraDesc(dto.getBusiTypeDesc());
					listIntegra.add(integralVo);
				}
				Page page=rs.getPage();
				resultMap.put("status", true);
				resultMap.put("listIntegra", listIntegra);
				resultMap.put("page", page);
			}else{
				resultMap.put("status", false);
				resultMap.put("errorCode", rs.getErrorCode());
				resultMap.put("errorMsg", rs.getErrorMessage());
			}
		}catch(Exception e){
			resultMap.put("status", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorCode());
			LOGGER.error("调用积分接口查询总积分异常："+e);
		}
		return resultMap; 
	}
	//虚拟账户积分充值（积分增加）
	public Map<String,Object> addVirtualAccountIntegral(Long userId,Long rltId,
			Long integral,String busiType){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		AcctItemDTO acctItemDTO = new AcctItemDTO();
		acctItemDTO.setUserId(userId);//用户Id
		acctItemDTO.setTotalAmount(integral);//充值积分
		AcctChangeDTO acctChangeDTO = new AcctChangeDTO();
		acctChangeDTO.setRltId(rltId); //导致帐目变化的业务流水ID
		acctChangeDTO.setBusiType(busiType);//业务类型
		acctChangeDTO.setIoAmount(integral);//出入数量
		acctChangeDTO.setRltChannelId("1");//业务来源渠道标识 1.官网 2.秀团 3.团货
        acctChangeDTO.setOperId("99999");//操作人员ID
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("p1", 0);
        map.put("p2", 0);
        IntegeralRuleDTO integeralRuleDTO = new IntegeralRuleDTO(busiType,map);
        try{
        	 Result result = acctItemFacade.addVirtualAccountIntegral(acctItemDTO, acctChangeDTO, integeralRuleDTO);
             if(result.getSuccess().equals("1")){
            	 resultMap.put("status", true);
				 LOGGER.info("调用虚拟账户积分充值成功,用户ID："+userId+"兑换积分："+integral);
             }else{
            	 resultMap.put("status", false);
             }
        }catch(Exception e){
        	resultMap.put("status", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorCode());
			LOGGER.error("调用虚拟账户积分充值异常："+e);
        }
		return resultMap;
	}
	//虚拟账户积分支付（积分减少）
	public Map<String,Object> decVirtualAccountIntegral(Long userId,Long rltId,
			Long integral,String busiType){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		AcctItemDTO acctItemDTO = new AcctItemDTO();
		acctItemDTO.setUserId(userId);//用户ID
		acctItemDTO.setTotalAmount(integral);//支付积分
		AcctChangeDTO acctChangeDTO = new AcctChangeDTO();
		 acctChangeDTO.setRltId(rltId);//导致帐目变化的业务流水ID
		 acctChangeDTO.setBusiType(busiType);//业务类型
		 acctChangeDTO.setIoAmount(integral);//支付积分
		 acctChangeDTO.setRltChannelId("1");//业务来源渠道标识 10751.官网 2.秀团 3.团货
		 acctChangeDTO.setOperId("99999");//操作人员ID
		 try{
			 Result rs =acctItemFacade.decVirtualAccountIntegral(acctItemDTO, acctChangeDTO, null);
			 if(rs.getSuccess().equals("1")){
				 resultMap.put("status", true);
				 LOGGER.info("调用虚拟账户积分支付成功,用户ID："+userId+"兑换积分："+integral);
			 }else{
				 resultMap.put("status", false);
			 }
		 }catch(Exception e){
			resultMap.put("status", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorCode());
			LOGGER.error("调用虚拟账户积分支付异常："+e);
		 }
		return resultMap;
	}

	public void insertOrUpdateAppInfo(String deviceId, String deviceOs,
			String deviceVersion, String source,String userId, String version, String idfa) {
		try {
			LOGGER.info("insertOrUpdateAppInfo更新用户信息开始:deviceId="+deviceId+",deviceOs="+deviceOs+",deviceVersion="+deviceVersion+
					 ",source="+source+",userId="+userId+",version="+version+",idfa="+idfa);
			AppDeviceDTO appDeviceDTO = new AppDeviceDTO();
			appDeviceDTO.setDeviceNumber(deviceId);
			appDeviceDTO.setDeviceType(deviceOs);
			appDeviceDTO.setDeviceSystemVersion(deviceVersion);
			appDeviceDTO.setAppVersion(version);
			appDeviceDTO.setAppSource(source);
			appDeviceDTO.setUserId(userId!=null ? Long.valueOf(userId) : null);//可选
			appDeviceDTO.setIdfa(idfa);//可选：只有苹果才有
			appDeviceFacade.logAppDevice(appDeviceDTO);
			 LOGGER.info("调用UUC接口insertOrUpdateAppInfo成功,deviceId="+deviceId+",deviceOs="+deviceOs+",deviceVersion="+deviceVersion+
					 ",source="+source+",userId="+userId+",version="+version+",idfa="+idfa);
		} catch (Exception e) {
			 LOGGER.info("调用UUC更新用户APP信息异常",e);
		}
	}
}
