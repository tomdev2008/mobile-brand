package com.xiu.mobile.portal.ei;

import java.util.Map;

import com.xiu.pcs.opms.facade.dto.MutilPartnerInfo;

public interface EIPcsOpmsManager {
	/**
	 * 获取一品多商信息
	 * @param goodSn
	 * @param channel
	 * @return
	 */
	public MutilPartnerInfo getMutilPartnerInfo(String goodSn);
	
	/**
	 * 实时判断该SKU是否有库存
	 * @param skuCustomInfoVo
	 * @return
	 */
	public Map<String, Object> getSkuRealtime(String goodsSn, String goodsSku);
}
