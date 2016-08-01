package com.xiu.mobile.portal.ei;

import java.util.List;
import java.util.Map;


/**
 * @ClassName: EIChannelInventoryManager
 * @Description: 渠道中心
 * @author toney.li
 * @date 2012-4-25 下午8:54:20
 * 
 */
public interface EIChannelInventoryManager {
	
	/**
	 * 按SKU查询.
	 * @param channelCode 渠道号
	 * @param skuCode
	 * @return
	 */
	Integer inventoryQueryBySkuCodeAndChannelCode(String channelCode, String skuCode);
	
	/***
	 * 批量查询sku库存数目
	 * @param channelCode
	 * @param skuCodeList
	 * @return
	 */
	public Map<String, Integer> inventoryQueryBySkuCodeList(String channelCode, List<String> skuCodeList);
}
