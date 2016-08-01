/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-17 下午5:20:16 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.mobile.portal.service;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.portal.model.CouponBo;
import com.xiu.mobile.portal.model.OrderGoodsVO;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO 优惠券业务实现接口
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-17 下午5:20:16 
 * ***************************************************************
 * </p>
 */

public interface ICouponService {

	/**
	 * 激活优惠券
	 * @param cardCode   优惠券编码
	 * @param activeCode 优惠券密码
	 * @param userId     用户ID 
	 * @param loginName  用户名
	 * @return
	 */
	String activeCoupon(String cardCode, String activeCode,
			String userId, String loginName);
	
	/**
	 * 激活优惠券和优惠券卡包
	 * @param cardCode   优惠券编码
	 * @param activeCode 优惠券密码
	 * @param userId     用户ID 
	 * @param loginName  用户名
	 * @return
	 * @time	2015-2-28	
	 */
	Map activeCouponOrCardCode(String cardCode, String activeCode, String userId, String loginName);
	
	/**
	 * 查询优惠券，封装BO对象
	 * @param userId
	 * @param listValid
	 * @param page
	 * @return
	 */
	CouponBo findCouponBoByParam(String userId, Integer listValid, Page<?> page,
			Integer terminal,Integer orderField,Integer orderType,Integer isCanUseInt);
	
	/***
	 * 验证优惠券是否可用
	 * @param cardCode
	 * @param userId
	 * @param goodsSn
	 * @param channelID
	 * @param number 商品数量
	 * @return
	 */
	public Map<String, Object> validateCardCoupon(String cardCode,long userId,String goodsSn,String channelID,int number);
	
	/***
	 * 多商品验证优惠券是否可用
	 * @param cardCode
	 * @param userId
	 * @param channelID
	 * @param goodsVOList
	 * @return
	 */
	public Map<String, Object> validateCardCoupon(String cardCode,long userId,String channelID,List<OrderGoodsVO> goodsVOList);
	
	/***
	 * 验证优惠券是否可用
	 * @param cardCode
	 * @param userId
	 * @param goodsSn
	 * @param channelID
	 * @param number
	 * @return
	 */
	public Map<String, Object> validateCoupon(String cardCode,long userId,String goodsSn,String channelID,int number);
	
	
	/***
	 * 验证优惠券是否满足多商品使用规则
	 * @param cardCode
	 * @param userId
	 * @param channelID
	 * @param goodsVOList
	 * @return
	 */
	public Map<String, Object> validateCoupon(String cardCode,long userId,String channelID,List<OrderGoodsVO> goodsVOList);
	
}
