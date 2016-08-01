/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-28 下午3:31:46 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.mobile.portal.ei.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.xiu.image.biz.dto.GoodsInfoDTO;
import com.xiu.image.biz.hessian.interfaces.OriImageCheckHessianService;
import com.xiu.image.biz.hessian.interfaces.SkuImagesPair;
import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.portal.constants.ErrConstants;
import com.xiu.mobile.portal.ei.EImageManager;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-28 下午3:31:46 
 * ***************************************************************
 * </p>
 */

@Service
public class EImageManagerImpl implements EImageManager {
	private static final Logger LOGGER = Logger.getLogger(EImageManager.class);
	
	@Autowired
	private OriImageCheckHessianService oriImageCheckHessianService;

	@Override
	public List<SkuImagesPair> checkImageExists(GoodsInfoDTO goodsDTO) {
		Assert.notNull(goodsDTO);
		Assert.notNull(goodsDTO.getXiuCode());
		Assert.notNull(goodsDTO.getSkuCodes());
		Assert.notNull(goodsDTO.getLength());
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("invoke remote interface [image] oriImageCheckHessianService.checkImageExists()");
			LOGGER.debug("goodsDTO.getXiuCode: " + goodsDTO.getXiuCode());
			String[] skuCodes=goodsDTO.getSkuCodes();
			StringBuffer sb = new StringBuffer();
			for(int i = 0; i < skuCodes.length; i++){
				sb. append(skuCodes[i]);
			}
			LOGGER.debug("goodsDTO.getSkuCodes: " +sb.toString());
			LOGGER.debug("goodsDTO.getLength: " + goodsDTO.getLength());
		}
		
		List<SkuImagesPair> pairs = null;
		try {
			pairs = oriImageCheckHessianService.checkImageExists(goodsDTO);
		} catch (Exception e) {
			throw ExceptionFactory.buildEIRuntimeException(
					ErrConstants.EIErrorCode.EI_IMAGE_GENERAL_ERR, e);
		}
		
		return pairs;
	}

}
