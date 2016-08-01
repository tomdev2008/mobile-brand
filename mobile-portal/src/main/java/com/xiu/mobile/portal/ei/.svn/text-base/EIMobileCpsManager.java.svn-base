package com.xiu.mobile.portal.ei;

import java.util.Map;

public interface EIMobileCpsManager {

	/**
	 * 获取个人礼包CPS首页信息
	 * @param xiuUserId
	 * @return
	 */
	public Map<String, Object> queryPersonCpsCenterInfo(Long xiuUserId, String userPhoneUrl);
	
	/**
	 * 分享礼包后，回调通知接口
	 * @param xiuUserId
	 * @return
	 */
	public Map<String, Object> shareCallback(Long xiuUserId, String mobile);
	
	/**
	 * 获取返现明细接口
	 * @param xiuUserId
	 * @param status
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> queryRebateDetail(Long xiuUserId, String status, int page, int pageSize);
	
	/**
	 * 获取已领取礼包明细接口
	 * @param xiuUserId
	 * @param status
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> queryGiftbagDetail(Long xiuUserId, String status, int page, int pageSize);
	
	
}
