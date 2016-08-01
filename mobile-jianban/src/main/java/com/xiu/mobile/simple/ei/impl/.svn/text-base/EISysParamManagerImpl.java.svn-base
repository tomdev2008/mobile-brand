package com.xiu.mobile.simple.ei.impl;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.csp.facade.SysParamFacade;
import com.xiu.csp.facade.dto.CspResult;
import com.xiu.csp.facade.util.SysParamUtil;
import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.simple.constants.ErrConstants;
import com.xiu.mobile.simple.ei.EISysParamManager;

/**
 * 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 系统参数外部接口实现
 * @AUTHOR : chenghong.ding@xiu.com 
 * @DATE :2014年5月28日 下午5:01:51
 * </p>
 ****************************************************************
 */
@Service("eiSysParamManager")
public class EISysParamManagerImpl implements EISysParamManager {
	
	private static final XLogger logger = XLoggerFactory.getXLogger(EISysParamManagerImpl.class);
	
	@Autowired
    private SysParamFacade sysParamFacade;

	@Override
	public CspResult getListByParamTypeAndParentCode(String paramType,
			String parentCode) {
		CspResult result = null;
		try{
			result = this.sysParamFacade.getListByParamTypeAndParentCode(paramType, parentCode);
		}catch(Exception e){
			logger.error("{}.getListByParamTypeAndParentCode 调用CSP接口异常 | paramType={} | parentCode={} | message={}",
					new Object[]{sysParamFacade.getClass(),paramType,parentCode,e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_CSP_PRO_CITY_REGION_BIZ_ERR, e);
		}
		if(!"1".equals(result.getSuccess())){
			logger.error("{}.getListByParamTypeAndParentCode 调用CSP接口失败 | paramType={} | parentCode={} | errCode={} | errMessage={}",
					new Object[]{sysParamFacade.getClass(),paramType,parentCode,result.getErrorCode(),result.getErrorMessage()});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_CSP_BIZ_ERR, result.getErrorCode(), result.getErrorMessage());
		}
		return result;
	}

	@Override
	public CspResult getListByParamType(String paramType) {
		CspResult result = null;
		try{
			result = this.sysParamFacade.getListByParamType(paramType);
		}catch(Exception e){
			logger.error("{}.getListByParamType 调用CSP接口异常 | message",
					new Object[]{sysParamFacade.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_CSP_PRO_CITY_REGION_BIZ_ERR, e);
		}
		if(!"1".equals(result.getSuccess())){
			logger.error("{}.getListByParamType 调用CSP接口失败 | paramType={} | errCode={} | errMessage={}",
					new Object[]{sysParamFacade.getClass(),paramType,result.getErrorCode(),result.getErrorMessage()});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_CSP_BIZ_ERR, result.getErrorCode(), result.getErrorMessage());
		}
		return result;
	}

	private static SysParamUtil sysParamUtil;
	
	@Override
	public String getAddressDescByCode(String code) {
		try {
			if (sysParamUtil == null) {
				sysParamUtil = SysParamUtil.getInstance(this.sysParamFacade);
			}
			return sysParamUtil.getParamDescByCode(code);
		} catch (Exception e) {
			logger.error("SysParamUtil.getParamDescByCode 出现异常 | code={}",
					new Object[]{code});
			return "";
		}
	}
	
	

}
