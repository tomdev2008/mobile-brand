package com.xiu.mobile.portal.ei.impl;

import java.util.List;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.portal.constants.ErrConstants;
import com.xiu.mobile.portal.ei.EIAddressManager;
import com.xiu.uuc.facade.AddressManageFacade;
import com.xiu.uuc.facade.dto.RcvAddressDTO;
import com.xiu.uuc.facade.dto.Result;

/**
 * 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 收货地址外部接口实现  UUC
 * @AUTHOR : chenghong.ding@xiu.com 
 * @DATE :2014年5月28日 下午3:34:14
 * </p>
 ****************************************************************
 */
@Service("eiAddressManager")
public class EIAddressManagerImpl implements EIAddressManager {
	
	private static final XLogger logger = XLoggerFactory.getXLogger(EIAddressManagerImpl.class);
	
	@Autowired
    private AddressManageFacade addressManageFacade;
	
	

	@Override
	public Result addAddress(RcvAddressDTO rcvAddressDTO) {
        Result result = null;
        try{
        	result = this.addressManageFacade.addRcvAddressInfo(rcvAddressDTO);
        }catch(Exception e){
        	logger.error("{}.addRcvAddressInfo 调用UUC接口添加收货地址出现异常  | message={}",
        			new Object[]{addressManageFacade.getClass(),e.getMessage()});
        	throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_ADDRESS_BIZ_ERR, e);
        }
        if (!"1".equals(result.getSuccess())) {// 用户中心接口定义返回1为成功
        	logger.error("{}.addRcvAddressInfo 调用UUC接口添加收货地址失败 | errCode={} | errMessage={}",
        			new Object[]{addressManageFacade.getClass().getName(),result.getErrorCode(),result.getErrorMessage()});
            throw ExceptionFactory.buildEIBusinessException(
            		ErrConstants.EIErrorCode.EI_UUC_ADDRESS_ADD_FAILED_ERR,
            		result.getErrorCode(),
                    result.getErrorMessage());
        }
        return result;
	}

	@Override
	public Result updateAddress(RcvAddressDTO rcvAddressDTO) {
        Result result = null;
        try{
        	result = this.addressManageFacade.modifyRcvAddressInfo(rcvAddressDTO);
        }catch(Exception e){
        	logger.error("{}.modifyRcvAddressInfo 调用UUC接口修改收货地址异常 | message={}",
        			new Object[]{addressManageFacade.getClass(),e.getMessage()});
        	throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_ADDRESS_MODIFY_BIZ_ERR, e);
        }
        if (!result.getSuccess().equals("1")) {// 用户中心接口定义返回1为成功
        	logger.error("{}.modifyRcvAddressInfo 调用UUC接口修改收货地址失败  | errCode={} | errMessage={}",
        			new Object[]{addressManageFacade.getClass().getName(),result.getErrorCode(),result.getErrorMessage()});
            throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_ADDRESS_MODIFY_FAILED_ERR, result.getErrorCode(),
                    result.getErrorMessage());
        }
        return result;
	}
	

	@Override
	public RcvAddressDTO getRcvAddressInfoById(Long addressId) {
		Result result = null;
		try{
			result = this.addressManageFacade.getRcvAddressInfoById(Long.parseLong(addressId.toString()));
		}catch(Exception e){
			logger.error("{}.getRcvAddressInfoById 调用UUC接口获取收货地址异常 | message={}",
					new Object[]{addressManageFacade.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_ADDRESS_BIZ_ERR, e);
		}
		if(!"1".equals(result.getSuccess())){ // 用户中心接口定义返回1为成功
			logger.error("{}.getRcvAddressInfoById 调用UUC接口获取收货地址失败 | errCode={} | errMessage={}",
        			new Object[]{addressManageFacade.getClass().getName(),result.getErrorCode(),result.getErrorMessage()});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_ADDRESS_INFO_ERR, result.getErrorCode(), result.getErrorMessage());
		}
		RcvAddressDTO dto = (RcvAddressDTO) result.getData();
		return dto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RcvAddressDTO> getRcvAddressListByUserId(Long userId) {
		Assert.notNull(userId, "收货地址用户userId不能为空.");
		Result addressResult = null;
		try{
			addressResult = this.addressManageFacade.getRcvAddressListByUserId(userId);
		}catch(Exception e){
			logger.error("{}.getRcvAddressListByUserId 调用UUC接口取得用户收货地址列表异常 | message={},userId is :" + userId ,
					new Object[]{addressManageFacade.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_ADDRESS_LIST_BIZ_ERR, e);
		}
		if(!"1".equals(addressResult.getSuccess())){ // 用户中心接口定义返回1为成功
			logger.error("{}.getRcvAddressListByUserId 调用UUC接口取得用户收货地址列表失败 | errCode={} | errMessage={},userId is :" + userId ,
        			new Object[]{addressManageFacade.getClass().getName(),addressResult.getErrorCode(),addressResult.getErrorMessage()});
			throw ExceptionFactory.buildEIBusinessException(
					ErrConstants.EIErrorCode.EI_UUC_ADDRESS_LIST_FAILED_ERR, 
					addressResult.getErrorCode(), 
					addressResult.getErrorMessage());
		}
		List<RcvAddressDTO> list_rcv = (List<RcvAddressDTO>) addressResult.getData();
		return list_rcv;
	}

	@Override
	public Result delAddress(Long addressId) {
        Result result = null;
        try{
        	result = this.addressManageFacade.deleteRcvAddressInfo(addressId);
        }catch(Exception e){
        	logger.error("{}.deleteRcvAddressInfo调用接口删除收货地址异常 | message={}",
        			new Object[]{addressManageFacade.getClass(),e.getMessage()});
        	throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_ADDRESS_DELETE_BIZ_ERR, e);
        }
        if (!"1".equals(result.getSuccess())) {// 用户中心接口定义返回1为成功
        	logger.error("{}.deleteRcvAddressInfo调用接口删除收货地址失败 | errCode={} | errMessage={}",
        			new Object[]{addressManageFacade.getClass().getName(),result.getErrorCode(),result.getErrorMessage()});
            throw ExceptionFactory.buildEIBusinessException(
            		ErrConstants.EIErrorCode.EI_UUC_ADDRESS_DELETE_FAILED_ERR,
            		result.getErrorCode(),
                    result.getErrorMessage());
        }
        return result;
	}

}
