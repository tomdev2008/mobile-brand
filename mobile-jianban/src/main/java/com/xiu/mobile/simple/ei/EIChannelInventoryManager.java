package com.xiu.mobile.simple.ei;


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
	Integer inventoryQueryBySkuCodeAndChannelCode(
			String channelCode, String skuCode);
}
