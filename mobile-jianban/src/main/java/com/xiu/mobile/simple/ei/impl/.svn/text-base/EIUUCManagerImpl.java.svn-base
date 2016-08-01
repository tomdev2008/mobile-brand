/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-22 下午3:34:50 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.mobile.simple.ei.impl;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.simple.constants.ErrConstants;
import com.xiu.mobile.simple.ei.EIUUCManager;
import com.xiu.uuc.facade.UserManageFacade;
import com.xiu.uuc.facade.dto.RegisterUserDTO;
import com.xiu.uuc.facade.dto.Result;
import com.xiu.uuc.facade.dto.UserBaseDTO;
import com.xiu.uuc.facade.dto.UserDetailDTO;
import com.xiu.uuc.facade.util.ErrorCodeConstant;
import com.xiu.uuc.facade.util.FacadeConstant;

/** 
 * <p>
 * ************************************************************** 
 * @Description: 用户中心
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-22 下午3:34:50 
 * ***************************************************************
 * </p>
 */
@Service
public class EIUUCManagerImpl implements EIUUCManager {
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(EIUUCManagerImpl.class);
	
	@Autowired
	private UserManageFacade userManageFacade;
	
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
    public Result modifyUserPassword(Long userId, String oldPassword, String newPassword) {
        Assert.notNull(userId);
        Assert.notNull(oldPassword);
        Assert.notNull(newPassword);
        LOGGER.entry(userId,oldPassword,newPassword);
        
        Result result = null;
        try {
            //接口调用
            result = userManageFacade.modifyUserPassword(userId, oldPassword, newPassword);
        } catch (Throwable e) {
            throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, e);
        }

        Assert.notNull(result);
        if (FacadeConstant.FALSE.equals(result.isSuccess())) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("{}.modifyUserPassword  isSuccess={}, errorCode={}, errorMessage={}",
                        new Object[] { this.getClass(), result.isSuccess(), result.getErrorCode(), result.getErrorMessage() });
            }
            if(!ErrorCodeConstant.SYSTEM_ERROR_CODE.equals(result.getErrorCode())){
                throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_BIZ_ERR, result.getErrorCode(), result.getErrorMessage());
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
	public UserDetailDTO getUserBasicInfoByLogonName(String logonName,Integer channelId) {
		// 调用用户中心接口查询用户详细信息
		Assert.notNull(logonName, "用户logonName不能为空.");
		Assert.notNull(channelId, "用户channelId不能为空.");
		com.xiu.uuc.facade.dto.Result result = null;
		try{
			result = userManageFacade.getUserDetailInfoByLogonName(logonName, channelId);
		}catch(Exception ex){
			LOGGER.error("{}.getUserDetailInfoByLogonName 调用UUC接口获取用户信息异常 | message={}",
					new Object[]{userManageFacade.getClass(),ex.getMessage()});
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
        } catch (Throwable e) {
            throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, e);
        }

        Assert.notNull(result);
        if (FacadeConstant.FALSE.equals(result.isSuccess())) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("{}.resetUserPassword  isSuccess={}, errorCode={}, errorMessage={}",
                        new Object[] { this.getClass(), result.isSuccess(), result.getErrorCode(), result.getErrorMessage() });
            }
            if(!ErrorCodeConstant.SYSTEM_ERROR_CODE.equals(result.getErrorCode())){
                throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_BIZ_ERR, result.getErrorCode(), result.getErrorMessage());
            }else{
                throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_GENERAL_ERR, result.getErrorCode(), result.getErrorMessage());
            }           
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("{}.resetUserPassword  isSuccess={}", new Object[] { this.getClass(), result.isSuccess() });
        }   
        
        return result;
    }
}
