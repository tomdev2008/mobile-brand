package com.xiu.mobile.portal.ei.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.common.command.result.Result;
import com.xiu.mobile.core.exception.EIBusinessException;
import com.xiu.mobile.cps.dointerface.query.CommissionsOrderQueryParamIn;
import com.xiu.mobile.cps.dointerface.query.CommissionsOrderQueryParamOut;
import com.xiu.mobile.cps.dointerface.service.CpsManageFacade;
import com.xiu.mobile.cps.dointerface.vo.UserCommissionsAmountVo;
import com.xiu.mobile.cps.dointerface.vo.UserCommissionsOrderDetail;
import com.xiu.mobile.cps.dointerface.vo.UserCommissionsOrderInfo;
import com.xiu.mobile.portal.constants.ErrConstants;
import com.xiu.mobile.portal.ei.EICpsCommissonManager;

@Service
public class EICpsCommissonManagerImpl implements EICpsCommissonManager{
	private static final Logger logger = Logger.getLogger(EICpsCommissonManagerImpl.class);
	
	@Autowired
	private CpsManageFacade cpsManageFacade;

	@Override
	public UserCommissionsAmountVo queryUserCommissionsAmount(Long userId) {
		Result result = null;
		try {
			logger.info("查询用户userId="+userId+"返佣金额信息");
			result = cpsManageFacade.queryUserCommissionsAmount(userId);
			logger.info("查询用户userId="+userId+"返佣金额信息result="+result);
		} catch (Exception e) {
			logger.error("查询用户userId="+userId+"返佣金额信息异常exception",e);
			throw new EIBusinessException("查询用户返佣金额接口异常", ErrConstants.EIErrorCode.EI_PUC_BIZ_ERR, null, null);
		}
		if (!result.isSuccess()) {
			// 优先返回errorMessages中的错误信息
			if (result.getErrorMessages() != null && !result.getErrorMessages().isEmpty()) {
				// 只返回一个错误提示
				for (String error : result.getErrorMessages().values()) {
					logger.error("查询用户userId="+userId+"返佣金额信息出错：result.code="+result.getResultCode()+",result.errorMessage="+result.getErrorMessages());
					throw new EIBusinessException(error, result.getResultCode(), null, null);
				}
			}
		}
		// 用户返佣金额信息
		UserCommissionsAmountVo amountVos = (UserCommissionsAmountVo) result.getDefaultModel();
		return amountVos;
	}

	@Override
	public CommissionsOrderQueryParamOut queryUserCommissionsOrderList(CommissionsOrderQueryParamIn paramIn) {
		Result result = null;
		try {
			logger.info("查询用户返佣订单信息commissionsOrderQueryParamIn="+paramIn);
			result = cpsManageFacade.queryUserCommissionsOrderList(paramIn);
			logger.info("查询用户返佣订单信息result="+result);
		} catch (Exception e) {
			logger.error("查询用户返佣订单信息commissionsOrderQueryParamIn="+paramIn+"返佣金额信息异常exception",e);
			throw new EIBusinessException("查询用户返佣订单信息异常", ErrConstants.EIErrorCode.EI_PUC_BIZ_ERR, null, null);
		}
		if (!result.isSuccess()) {
			// 优先返回errorMessages中的错误信息
			if (result.getErrorMessages() != null && !result.getErrorMessages().isEmpty()) {
				// 只返回一个错误提示
				for (String error : result.getErrorMessages().values()) {
					logger.error("查询用户返佣订单信息commissionsOrderQueryParamIn="+paramIn+"出错：result.code="+result.getResultCode()+",result.errorMessage="+result.getErrorMessages());
					throw new EIBusinessException(error, result.getResultCode(), null, null);
				}
			}
		}
		// 用户返佣订单信息
		CommissionsOrderQueryParamOut commissionsOrderQueryParamOut = (CommissionsOrderQueryParamOut) result.getDefaultModel();
		return commissionsOrderQueryParamOut;
	}

	@Override
	public List<UserCommissionsOrderDetail> queryUserCommissionsOrderDetail(Long userId, Long orderCode) {
		Result result = null;
		try {
			logger.info("查询用户userId="+userId+",orderCode="+orderCode+"订单详细信息");
			result = cpsManageFacade.queryUserCommissionsOrderDetail(userId, orderCode);
			logger.info("查询用户userId="+userId+",orderCode="+orderCode+"订单详细信息result="+result);
		} catch (Exception e) {
			logger.error("查询用户userId="+userId+",orderCode="+orderCode+"订单详细信息异常exception",e);
			throw new EIBusinessException("查询用户返佣订单详细信息异常", ErrConstants.EIErrorCode.EI_PUC_BIZ_ERR, null, null);
		}
		if (!result.isSuccess()) {
			// 优先返回errorMessages中的错误信息
			if (result.getErrorMessages() != null && !result.getErrorMessages().isEmpty()) {
				// 只返回一个错误提示
				for (String error : result.getErrorMessages().values()) {
					logger.error("查询用户userId="+userId+",orderCode="+orderCode+"订单详细信息出错：result.code="+result.getResultCode()+",result.errorMessage="+result.getErrorMessages());
					throw new EIBusinessException(error, result.getResultCode(), null, null);
				}
			}
		}
		// 返佣订单详细信息
		List<UserCommissionsOrderDetail> commissionsOrderDetails = (List<UserCommissionsOrderDetail>) result.getDefaultModel();
		return commissionsOrderDetails;
	}

	@Override
	public UserCommissionsOrderInfo queryUserCommissionsOrderInfo(Long orderCode) {
		Result result = null;
		try {
			logger.info("查询用户返佣订单信息orderCode="+orderCode);
			result = cpsManageFacade.queryUserCommissionsOrderInfo(orderCode);
			logger.info("查询用户返佣订单信息result="+result);
		} catch (Exception e) {
			logger.error("查询用户返佣订单信息orderCode="+orderCode+"返佣金额信息异常exception",e);
			throw new EIBusinessException("查询用户返佣订单信息异常", ErrConstants.EIErrorCode.EI_PUC_BIZ_ERR, null, null);
		}
		if (!result.isSuccess()) {
			// 优先返回errorMessages中的错误信息
			if (result.getErrorMessages() != null && !result.getErrorMessages().isEmpty()) {
				// 只返回一个错误提示
				for (String error : result.getErrorMessages().values()) {
					logger.error("查询用户返佣订单信息orderCode="+orderCode+"出错：result.code="+result.getResultCode()+",result.errorMessage="+result.getErrorMessages());
					throw new EIBusinessException(error, result.getResultCode(), null, null);
				}
			}
		}
		// 用户返佣订单信息
		UserCommissionsOrderInfo userCommissionsOrderInfo = (UserCommissionsOrderInfo) result.getDefaultModel();
		return userCommissionsOrderInfo;
	}
	
}
