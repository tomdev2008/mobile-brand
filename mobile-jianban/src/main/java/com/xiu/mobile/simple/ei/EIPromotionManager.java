package com.xiu.mobile.simple.ei;

import java.util.List;
import java.util.Map;

import com.xiu.common.command.result.Result;
import com.xiu.dispatch.common.dataobj.SendCardIntegDO;
import com.xiu.sales.common.balance.dataobject.BalanceActivityInfoDO;
import com.xiu.sales.common.balance.dataobject.BalanceOrderInfoDO;
import com.xiu.sales.common.card.dataobject.CardInputParamDO;
import com.xiu.sales.common.card.dataobject.CardOutParamDO;
import com.xiu.sales.common.card.dataobject.RuleGoodsRelationDO;
import com.xiu.sales.common.itemsettle.dataobject.ItemSettleResultDO;


/** 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 促销中心
 * @AUTHOR : mike@xiu.com 
 * @DATE :2014-5-15 下午12:09:15
 * </p>
 **************************************************************** 
 */
public interface EIPromotionManager {

	/**
	 * 注册送优惠券
	 * @param sendCardIntegDO
	 * @return
	 */
	Result sendCardByDeed(SendCardIntegDO sendCardIntegDO);
	
	/**
	 * 激活优惠券
	 * @param par
	 * @return
	 */
	String activeCardBody(CardInputParamDO cardInput);
	
	/**
	 * @Description: 查询优惠卡、券
	 * @param cardInputParamDO
	 * @return CardOutParamDO
	 * @throws
	 */
	CardOutParamDO findCoupons(CardInputParamDO cardInputParamDO);
	
	/**
	 * @Title: findCouponCount
	 * @Description: 查询优惠卡、券总数
	 * @param cardInputParamDO
	 * @return CardOutParamDO
	 * @throws
	 */
	int findCouponCount(CardInputParamDO cardInputParamDO);
	
	/**
	 * 批量调用促销的接口
	 * @param itemSettleResults
	 * @return
	 */
	Result itemListSettleSrevice(List<ItemSettleResultDO> itemSettleResults);
	
	/**
	 * 
	 * @param param
	 * @return
	 */
	Result itemSettleForXmlRpc(ItemSettleResultDO param);
	
	/**
	 * 校验指定的优惠卷卡是否可用. "CardInputParamDO <br/>
	 * goodsInfo RuleGoodsRelationDO[] 商品信息<br/>
	 * cardId String 优惠券号码<br/>
	 * userId long 用户ID<br/>
	 * channelID String 销售渠道ID"<br/>
	 * amount    String 订单总金额
	 * 
	 * 返回 15表示卡可以使用.
	 * 
	 * @param cardCode
	 * @param activeCode
	 * @param userId
	 * @param userName
	 * @param storeId
	 * @throws Exception
	 */
	public CardOutParamDO validateCardCoupon(String cardCode,long userId, RuleGoodsRelationDO[] goodsInfo, String channelID,String amount) throws Exception;

	/***
	 * 验证优惠券
	 * @param balanceOrderInfoDO 结算订单信息
	 * @return
	 */
	public Map<String, Object> validateCoupon(BalanceOrderInfoDO balanceOrderInfoDO);
	
	/***
	 * 获取结算活动列表信息
	 * @param balanceOrderInfoDO 结算订单信息
	 * @return
	 */
	public List<BalanceActivityInfoDO> getBalanceActivityInfoDOList(BalanceOrderInfoDO balanceOrderInfoDO);
	
}
