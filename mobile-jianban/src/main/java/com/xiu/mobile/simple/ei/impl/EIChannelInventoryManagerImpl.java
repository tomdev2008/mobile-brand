/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-28 下午12:30:36 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.mobile.simple.ei.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.xiu.channel.inventory.api.InventoryService;
import com.xiu.channel.inventory.api.dto.QueryInventoryRequest;
import com.xiu.channel.inventory.api.dto.QueryInventoryResponse;
import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.simple.constants.ErrConstants;
import com.xiu.mobile.simple.ei.EIChannelInventoryManager;

/** 
 * <p>
 * ************************************************************** 
 * @Description: 渠道中心
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-28 下午12:30:36 
 * ***************************************************************
 * </p>
 */
@Service
public class EIChannelInventoryManagerImpl implements EIChannelInventoryManager {
	
	private static final Logger LOGGER = Logger.getLogger(EIChannelInventoryManagerImpl.class);

	@Autowired
	private InventoryService inventoryService;
	
	@Override
	public Integer inventoryQueryBySkuCodeAndChannelCode(
			String channelCode, String skuCode) {
		Assert.notNull(channelCode);
		Assert.notNull(skuCode);
		
		QueryInventoryRequest queryInventoryReq = new QueryInventoryRequest();
		queryInventoryReq.setChannelCode(channelCode);
		queryInventoryReq.setSkuCode(skuCode);
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("invoke remote interface [channel] : inventoryService.inventoryQueryBySkuCodeAndChannelCode");
			LOGGER.debug("channelCode:" + channelCode);
			LOGGER.debug("skuCode:" + skuCode);
		}
		
		QueryInventoryResponse queryInventoryRes = null;
		try {
			queryInventoryRes = inventoryService
					.inventoryQueryBySkuCodeAndChannelCode(queryInventoryReq);
		} catch (Exception e) {
			throw ExceptionFactory.buildEIRuntimeException(
					ErrConstants.EIErrorCode.EI_INV_GENERAL_ERR, e);
		}

		if (1 != queryInventoryRes.getCode()) {
			LOGGER.debug("invoke remote interface [channel] : inventoryService.inventoryQueryBySkuCodeAndChannelCode error.");
			LOGGER.error("ErrorCode:" + queryInventoryRes.getCode());
			LOGGER.error("ErrorMessage:" + queryInventoryRes.getDesc());
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_INV_BIZ_QSUK_ERR, 
					queryInventoryRes.getCode().toString(), queryInventoryRes.getDesc());
		} 
		
		return queryInventoryRes.getQty();
	}


	
}
