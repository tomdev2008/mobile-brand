package com.xiu.mobile.portal.service;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.cps.dointerface.query.CommissionsOrderQueryParamOut;
import com.xiu.mobile.cps.dointerface.vo.UserCommissionsAmountVo;
import com.xiu.mobile.cps.dointerface.vo.UserCommissionsOrderDetail;
import com.xiu.mobile.cps.dointerface.vo.UserCommissionsOrderInfo;

/**
 * @ClassName: ICpsCommissonManager
 * @Description: cps佣金管理
 * @author hejianxiong
 * @date 2014-7-8 11:21:05
 * 
 */
public interface ICpsCommissonManager {
	
	/**
	 * 根据用户ID查询用户返佣金额信息
	 * @param userId
	 * @return
	 */
	public UserCommissionsAmountVo queryUserCommissionsAmount(Long userId);
	
	/**
	 * 查询用户返佣订单列表信息
	 * @param paramIn
	 * @return
	 */
	public CommissionsOrderQueryParamOut queryUserCommissionsOrderList(Map<String, String> params);
	
	/**
	 * 查询用户返佣订单信息
	 * @param paramIn
	 * @return
	 */
	public UserCommissionsOrderInfo queryUserCommissionsOrderInfo(Long orderCode);
	
	/**
	 * 根据用户ID和订单号查询订单详细
	 * @param userId
	 * @param orderCode
	 * @return
	 */
	public List<UserCommissionsOrderDetail> queryUserCommissionsOrderDetail(Long userId,Long orderCode);

}
