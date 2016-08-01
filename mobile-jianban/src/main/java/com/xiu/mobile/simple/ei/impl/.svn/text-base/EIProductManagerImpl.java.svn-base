/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-28 上午11:44:16 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.mobile.simple.ei.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.opensymphony.xwork2.interceptor.annotations.Allowed;
import com.xiu.commerce.hessian.model.ProductCommonParas;
import com.xiu.commerce.hessian.model.ProductSearchParas;
import com.xiu.commerce.hessian.server.GoodsCenterService;
import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.simple.constants.ErrConstants;
import com.xiu.mobile.simple.ei.EIProductManager;

/** 
 * <p>
 * ************************************************************** 
 * @Description: 商品中心EI实现类 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-28 上午11:44:16 
 * ***************************************************************
 * </p>
 */
@Service
public class EIProductManagerImpl implements EIProductManager {
	private static final Logger LOGGER = Logger.getLogger(EIProductManagerImpl.class);
	
	@Autowired
	private GoodsCenterService goodsCenterService;

	@Override
	public Map getProductLight(ProductCommonParas commonParas,
			ProductSearchParas searchParas) {
		Assert.notNull(commonParas);
		Assert.notNull(searchParas);
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("invoke remote interface [goods] : goodsCenterService.getProductLight");
			LOGGER.debug("searchParas.getXiuSnList:" + searchParas.getXiuSnList());
			LOGGER.debug("searchParas.getPageStep:" + searchParas.getPageStep());
		}
		
		Map result = null;
		try {
			result = goodsCenterService.getProductLight(commonParas, searchParas);
		} catch (Exception e) {
			throw ExceptionFactory.buildEIRuntimeException(
					ErrConstants.EIErrorCode.EI_PROD_GENERAL_ERR, e);
		}

		return result;
	}

	@Override
	public Map searchProduct(ProductCommonParas commonParas, ProductSearchParas searchParas) {
		Assert.notNull(commonParas);
		Assert.notNull(searchParas);
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("invoke remote interface [goods] : goodsCenterService.getProductLight");
			LOGGER.debug("searchParas.getXiuSnList:" + searchParas.getXiuSnList());
			LOGGER.debug("searchParas.getPageStep:" + searchParas.getPageStep());
		}

		Map result = null;
		try {
			result = goodsCenterService.searchProduct(commonParas, searchParas);
		} catch (Exception e) {
			throw ExceptionFactory.buildEIRuntimeException(
					ErrConstants.EIErrorCode.EI_PROD_GENERAL_ERR, e);
		}
		
		return result;
	}

}
