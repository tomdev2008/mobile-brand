package com.xiu.mobile.portal.ei.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.xiu.common.command.result.Result;
import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.portal.constants.ErrConstants;
import com.xiu.mobile.portal.ei.EIPCSManager;
import com.xiu.pcs.facade.OverseasProductHessianService;
import com.xiu.pcs.facade.dto.OverseasProductHessianSingleDTO;

@Service("pcsManager")
public class EIPCSManagerImpl implements EIPCSManager {
	
	private final static Logger logger = Logger.getLogger(EIPCSManagerImpl.class);
	
	@Autowired
	private OverseasProductHessianService overseasProductHessianService;

	@Override
	public OverseasProductHessianSingleDTO queryPortalOverseasProduct(String goodsSn) {
		Assert.notNull(goodsSn, "goodsSn should be not null.");

		Result result = null;
		try {
			// 通过走秀码查询商品海外供应商信息
			result = overseasProductHessianService.queryPortalOverseasProduct(goodsSn);
		} catch (Exception e) {
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.GENERAL_COMM_ERR_MSG, e);
		}
		
		if (!result.isSuccess()) {
			if (!"00000".equals(result.getResultCode()) && !"00001".equals(result.getResultCode())) {
				logger.error("invoke remote interface [pcs] overseasProductHessianService.queryPortalOverseasProduct error.");
				logger.error("ErrorCode:" + result.getResultCode());
				logger.error("ErrorMessage:" + result.getErrorMessages().get(result.getResultCode()));
				
				throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_TRADE_GENERAL_ERR, result.getResultCode(), result.getErrorMessages().get(result.getResultCode()));
			}
		}
		
		// 海外供应商信息
		OverseasProductHessianSingleDTO dto = (OverseasProductHessianSingleDTO) result.getDefaultModel();
		return dto;
	}

	@Override
	public Map<String, OverseasProductHessianSingleDTO> queryPortalOverseasProducts(List<String> goodsSns) {
		Assert.notNull(goodsSns, "goodsSn should be not null.");

		Result result = null;
		try {
			// 通过走秀码批量查询商品海外供应商信息
			result = overseasProductHessianService.queryPortalOverseasProducts(goodsSns);
		} catch (Exception e) {
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.GENERAL_COMM_ERR_MSG, e);
		}
		
		if (!result.isSuccess()) {
			if (!"00000".equals(result.getResultCode()) && !"00001".equals(result.getResultCode())) {
				logger.error("invoke remote interface [pcs] overseasProductHessianService.queryPortalOverseasProducts error.");
				logger.error("ErrorCode:" + result.getResultCode());
				logger.error("ErrorMessage:" + result.getErrorMessages().get(result.getResultCode()));
				
				throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_TRADE_GENERAL_ERR, result.getResultCode(), result.getErrorMessages().get(result.getResultCode()));
			}
		}
		
		// 海外供应商信息
		Map<String, OverseasProductHessianSingleDTO> dtoMap = (Map<String, OverseasProductHessianSingleDTO>) result.getDefaultModel();
		return dtoMap;
	}

}
