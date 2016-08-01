package com.xiu.mobile.portal.ei;

import java.util.Map;

/**
 * 夺宝商品详情
 * @author Administrator
 *
 */
public interface EIRaiderGoodManager {
	/**
	 * 他们都在玩
	 */
	public Map<String,Object> findParticipateUser(String raiderId);
	/**
	 * 本场活动参与的用户记录
	 */
	public Map<String,Object> findParticipateByRaiders(String raiderId,String page,String pageSize);
	
	/**
	 * 夺宝分享
	 */
	public Map<String,Object> spreadSendRaiders(String raiderId,String participateId,
			boolean checkLogin,String xiuUserId,Integer personCount,String content);

	/**
	 * 夺宝分享详情
	 */
	public Map<String,Object> accessRaidersSharaIndex(String raiderId,String participateId,String code);
	/**
	 * 领取夺宝号码
	 */
	public Map<String,Object> supportSendRaiders(String raiderId,String participateId,
			String code,String mobile,String ip,String ipAdr);
	/**
	 * 活动详情计算规则
	 */
	public Map<String,Object> getLotteryRule(String raiderId);
	/**
	 * 根据orderId查询参与记录信息
	 */
	public Map<String,Object> getParticipateByOrderId(String orderId);
	/**
	 * 根据participateId查询购买次数
	 */
	public Map<String,Object> findSharaNum(String participateId);
}
