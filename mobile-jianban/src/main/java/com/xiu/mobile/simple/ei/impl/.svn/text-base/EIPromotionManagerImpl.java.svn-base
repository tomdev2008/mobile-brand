/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-22 下午4:15:43 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.mobile.simple.ei.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.xiu.common.command.result.Result;
import com.xiu.common.lang.StringUtil;
import com.xiu.dispatch.common.dataobj.SendCardIntegDO;
import com.xiu.dispatch.integ.send.SendCardInteg;
import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.simple.constants.ErrConstants;
import com.xiu.mobile.simple.constants.GlobalConstants;
import com.xiu.mobile.simple.ei.EIPromotionManager;
import com.xiu.mobile.simple.service.IGoodsService;
import com.xiu.sales.common.balance.dataobject.BalanceActivityInfoDO;
import com.xiu.sales.common.balance.dataobject.BalanceOrderInfoDO;
import com.xiu.sales.common.card.dataobject.CardInputParamDO;
import com.xiu.sales.common.card.dataobject.CardOutParamDO;
import com.xiu.sales.common.card.dataobject.RuleGoodsRelationDO;
import com.xiu.sales.common.card.dointerface.CardService;
import com.xiu.sales.common.itemsettle.dataobject.ItemSettleResultDO;
import com.xiu.sales.common.settle.ItemListSettleService;
import com.xiu.sales.common.settle.ItemSettleService;
import com.xiu.sales.common.settle.OrderSettleService;

/** 
 * <p>
 * ************************************************************** 
 * @Description: 促销中心
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-22 下午4:15:43 
 * ***************************************************************
 * </p>
 */
@Service
public class EIPromotionManagerImpl implements EIPromotionManager {
	
	private static final Logger LOGGER = Logger.getLogger(EIPromotionManagerImpl.class);
	
	@Autowired
	private SendCardInteg sendCardIntegService;
	@Autowired
	private CardService cardService;
	@Autowired
	private ItemListSettleService itemListSettleService;
	@Autowired
	private ItemSettleService itemSettleService;
	@Autowired
	private OrderSettleService orderSettleService;
	@Autowired
	IGoodsService goodsService;

	@Override
	public Result sendCardByDeed(SendCardIntegDO sendCardIntegDO) {
		Assert.notNull(sendCardIntegDO);
		Assert.notNull(sendCardIntegDO.getUserId());
		Assert.notNull(sendCardIntegDO.getUserName());
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("invoke remote interface [Promotion] sendCardIntegService.sendCardByDeed");
			LOGGER.debug("sendCardIntegDO.getUserId():" + sendCardIntegDO.getUserId());
			LOGGER.debug("sendCardIntegDO.getUserName():" + sendCardIntegDO.getUserName());
			LOGGER.debug("sendCardIntegDO.getSendPoint():" + sendCardIntegDO.getSendPoint());
			LOGGER.debug("sendCardIntegDO.getMobile():" + sendCardIntegDO.getMobile());
		}
		
		Result result = null;
		try {
			result = sendCardIntegService.sendCardByDeed(sendCardIntegDO);
		} catch (Exception e) {
			LOGGER.error("优惠券发放sendCardByDeed异常：e="+e);
			throw ExceptionFactory.buildEIRuntimeException(
					ErrConstants.EIErrorCode.EI_PROMOTION_GENERAL_ERR, e);
		}
		
		if (!result.isSuccess()) {
			String errorMessage = produceErrorMes(result);
			
			LOGGER.debug("invoke remote interface [Promotion] sendCardIntegService.sendCardByDeed error.");
			LOGGER.error("ErrorCode:" + result.getResultCode());
			LOGGER.error("ErrorMessage:" + errorMessage);
			
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_PROMOTION_BIZ_SCD_ERR, 
					result.getResultCode(), errorMessage);
		}
		
		return result;
	}

	@Override
	public String activeCardBody(CardInputParamDO cardInput) {
		Assert.notNull(cardInput);
		Assert.notNull(cardInput.getUserId());
		Assert.notNull(cardInput.getUserName());
		Assert.notNull(cardInput.getCardId());
		Assert.notNull(cardInput.getCardPwd());
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("invoke remote interface [Promotion] cardService.activeCardBody");
			LOGGER.debug("cardInput.getUserId():" + cardInput.getUserId());
			LOGGER.debug("cardInput.getUserName():" + cardInput.getUserName());
			LOGGER.debug("cardInput.getCardId():" + cardInput.getCardId());
			LOGGER.debug("cardInput.getCardPwd():" + cardInput.getCardPwd());
			LOGGER.debug("cardInput.getTerminalUser():" + cardInput.getTerminalUser());
			LOGGER.debug("cardInput.getChannelID():" + cardInput.getChannelID());
		}
		
		CardOutParamDO cardOut = null;
		try {
			cardOut = cardService.activeCardBody(cardInput);
		} catch (Exception e) {
			LOGGER.equals("优惠券激活activeCardBody异常：e"+e);
			throw ExceptionFactory.buildEIRuntimeException(
					ErrConstants.EIErrorCode.EI_PROMOTION_GENERAL_ERR, e);
		}
		
		if(cardOut == null || StringUtil.isEmpty(cardOut.getStatus())) {
			LOGGER.debug("invoke remote interface [Promotion] cardService.activeCardBody error.");
			
			throw ExceptionFactory.buildEIBusinessException(
					ErrConstants.EIErrorCode.EI_PROMOTION_BIZ_ACB_ERR, cardOut.getStatus(), null);
		}
		
		return cardOut.getStatus();
	}

	@Override
	public Result itemListSettleSrevice(List<ItemSettleResultDO> itemSettleResults) {
		Assert.notNull(itemSettleResults);
		
		Result result = null;
		try {
			result = itemListSettleService.itemListSettleSrevice(itemSettleResults);
		} catch (Exception e) {
			LOGGER.error("优惠券itemListSettleSrevice异常：e"+e);
			throw ExceptionFactory.buildEIRuntimeException(
					ErrConstants.EIErrorCode.EI_PROMOTION_GENERAL_ERR, e);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("----------------------------------------------------------------------");
			LOGGER.debug("result.isSuccess() - " + result.isSuccess());
			LOGGER.debug("result.getDefaultModel() - " + result.getDefaultModel());
			LOGGER.debug("result.getError() - " + result.getError());
			LOGGER.debug("result.getDefaultModelKey() - " + result.getDefaultModelKey());
			LOGGER.debug("result.getErrorMessages() - " + result.getErrorMessages());
			LOGGER.debug("result.getModels() - " + result.getModels());
			LOGGER.debug("result.getResultCode() - " + result.getResultCode());

			LOGGER.debug(result);
		}

		if (!result.isSuccess()) {
			String errorMessage = produceErrorMes(result);
			
			LOGGER.error("invoke remote interface [Promotion] itemListSettleService.itemListSettleSrevice error.");
			LOGGER.error("ErrorCode:" + result.getResultCode());
			LOGGER.error("ErrorMessage:" + errorMessage);
			
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_PROMOTION_BIZ_ILSS_ERR, 
					result.getResultCode(), errorMessage);
		}
		
		return result;
	}

	@Override
	public Result itemSettleForXmlRpc(ItemSettleResultDO param) {
		Assert.notNull(param);
		
		Result result = null;
		try {
			result = itemSettleService.itemSettleForXmlRpc(param);
		} catch (Exception e) {
			LOGGER.error("优惠券itemSettleForXmlRpc异常：e"+e);
			throw ExceptionFactory.buildEIRuntimeException(
					ErrConstants.EIErrorCode.EI_PROMOTION_GENERAL_ERR, e);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("----------------------------------------------------------------------");
			LOGGER.debug("result.isSuccess():" + result.isSuccess());
			LOGGER.debug("result.getDefaultModel():"+ result.getDefaultModel());
			LOGGER.debug("result.getError():" + result.getError());
			LOGGER.debug("result.getDefaultModelKey():" + result.getDefaultModelKey());
			LOGGER.debug("result.getErrorMessages():" + result.getErrorMessages());
			LOGGER.debug("result.getModels():" + result.getModels());
			LOGGER.debug("result.getResultCode():" + result.getResultCode());
		}

		if (!result.isSuccess()) {
			String errorMessage = produceErrorMes(result);
			
			LOGGER.error("invoke remote interface [Promotion] itemSettleService.itemSettleForXmlRpc");
			LOGGER.error("ErrorCode:" + result.getResultCode());
			LOGGER.error("ErrorMessage:" + errorMessage);
			
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_PROMOTION_BIZ_ISFXR_ERR, 
					result.getResultCode(), errorMessage);
		}
		
		return result;
	}
	
	@Override
	public CardOutParamDO findCoupons(CardInputParamDO cardInputParamDO) {
		Assert.notNull(cardInputParamDO);
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("invoke remote interface [Promotion] cardService.userCardList");
			LOGGER.debug("sendCardIntegDO.getUserId():" + cardInputParamDO.getUserId());
		}
		
		CardOutParamDO outParam = null;
		try {
			outParam = cardService.userCardList(cardInputParamDO);
		} catch (Exception e) {
			LOGGER.error("优惠券列表查询userCardList异常：e="+e);
			throw ExceptionFactory.buildEIRuntimeException(
					ErrConstants.EIErrorCode.EI_PROMOTION_GENERAL_ERR, e);
		}
		
		if(null == outParam) {
			LOGGER.error("CardInputParamDO:" + JSONObject.fromObject(cardInputParamDO).toString());
			LOGGER.error("CardOutParamDO:" + JSONObject.fromObject(outParam).toString());

			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_PROMOTION_BIZ_UCL_ERR,
					"促销系统查询优惠卡、券接口调用", "促销系统查询优惠卡、券接口调用失败返回结果为:null");
		}
		
		return outParam;
	}
	
	@Override
	public int findCouponCount(CardInputParamDO cardInputParamDO) {
		Assert.notNull(cardInputParamDO.getUserId());
		
		CardOutParamDO outParam = findCoupons(cardInputParamDO);
		return outParam.getCount();
	}
	
	/**
	 * 处理返回错误信息
	 * @return
	 */
	private String produceErrorMes(Result result) {
		String errorMessage = null;
		
		// 优先返回errorMessages中的错误信息
		if (result.getErrorMessages() != null 
				&& !result.getErrorMessages().isEmpty()) { 
			// 只返回一个错误提示
			errorMessage = result.getErrorMessages().values().iterator().next();
		}

		// error中的错误
		else if (result.getError() != null 
				&& StringUtils.isNotBlank(result.getError().toString())) {
			errorMessage = result.getError().toString();
		}
		else {
			errorMessage = "系统出错[SALE]";
		}
		
		return errorMessage;
	}

	
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
	public CardOutParamDO validateCardCoupon(String cardCode,long userId, RuleGoodsRelationDO[] goodsInfo, String channelID,String amount)
			throws Exception {
		CardOutParamDO outParam = null;
		try {
			CardInputParamDO input = new CardInputParamDO();
			input.setCardId(cardCode);
			input.setUserId(userId);
			input.setGoodsInfo(goodsInfo);
			input.setChannelID(channelID);
			// 优惠券终端类型  1 移动端  2 pc端
			input.setTerminalUser(GlobalConstants.TERMINAL_USER_TYPE);
			input.setAmount(amount);
			outParam = cardService.validateCard(input);
		} catch (Exception e) {
			LOGGER.error("验证优惠券异常e:",e);
		}
		return outParam;
	}

	/**
	 * remoteValidateCardStatus
	 * 
	 * @param cardCode
	 * @param userId
	 * @param channelID
	 * @return
	 * @throws Exception
	 */
	public int validateCardStatus(String cardCode, long userId,
			String channelID) throws Exception {
		int result = 0;
		try {
			CardInputParamDO input = new CardInputParamDO();
			input.setCardId(cardCode);
			input.setUserId(userId);
			input.setChannelID(channelID);
			input.setTerminalUser("2");
			CardOutParamDO outParam = cardService.validateCardCondition(input);
			result = new Integer(outParam.getStatus()).intValue();
		} catch (Exception e) {
			LOGGER.error("优惠券验证validateCardCondition异常",e);
			throw new Exception(e);
		}
		return result;
	}

	public Map<String, Object> validateCoupon(BalanceOrderInfoDO balanceOrderInfoDO){
		Result result = null;
		boolean flag = false;
		try {
			// 计算订单 验证数据信息
			result = orderSettleService.orderBalanceService(balanceOrderInfoDO);
			LOGGER.info("优惠券验证响应信息：result="+result);
			LOGGER.info("优惠券验证响应信息result.isSuccess="+result.isSuccess());
			LOGGER.info("优惠券验证响应信息result.resultCode="+result.getResultCode());
			// 接收并解析响应数据
			BalanceOrderInfoDO balanceOrderInfo = (BalanceOrderInfoDO) result.getDefaultModel();
			LOGGER.info("验证响应信息：balanceOrderInfo="+balanceOrderInfo);
			List<BalanceActivityInfoDO> activityList = balanceOrderInfo.getActivityInfo();
			LOGGER.info("验证响应信息：activityList="+activityList);
			if (activityList != null) {
				for (BalanceActivityInfoDO activity : activityList) {
					if (activity.getActivityKind() == 2) {
						flag = true;
						break;
					}
				}
			}
			// 计算正常，但有一些注意项：比如优惠券没用
			if (result.getResultCode() != null && "80".equals(result.getResultCode())) {
				// 返回码为80表示传入优惠券，但是并不适合
				LOGGER.error("该订单不满足优惠券规则,优惠券无法使用，errorCode:" + result.getResultCode());
			}

			if (! result.isSuccess()) {
				// 计算错误的提示信息在这里
				List errors = balanceOrderInfo.getErrorInfo();
				if (errors != null && errors.size() > 0) {
					LOGGER.error("该订单不满足优惠券规则,优惠券无法使用，errorInfo:" + errors);				
				}
			}
			
			if(flag){
				LOGGER.info("该订单优惠券验证通过,可以使用");
			}else{
				LOGGER.error("该订单不满足优惠券规则,优惠券无法使用，errorCode:" + result.getResultCode());
			}
		} catch (Exception e) {
			LOGGER.error("优惠券验证响应信息异常",e);
		}
		
		// 封装响应数据信息
		Map<String, Object> validateMap = new HashMap<String, Object>();
		validateMap.put("result", flag);
		validateMap.put("status", result.getResultCode());

		return validateMap;
	}

	@Override
	public List<BalanceActivityInfoDO> getBalanceActivityInfoDOList(BalanceOrderInfoDO balanceOrderInfoDO) {
		List<BalanceActivityInfoDO> activityList = null;
		try {
			// 计算订单 验证数据信息
			Result result = orderSettleService.orderBalanceService(balanceOrderInfoDO);
			LOGGER.info("结算活动列表信息：result="+result);
			// 接收并解析响应数据
			BalanceOrderInfoDO balanceOrderInfo = (BalanceOrderInfoDO) result.getDefaultModel();
			LOGGER.info("结算活动列表：balanceOrderInfo="+balanceOrderInfo);
			activityList = balanceOrderInfo.getActivityInfo();
			LOGGER.info("结算活动列表响应信息：activityList="+activityList);
		} catch (Exception e) {
			LOGGER.info("获取结算活动列表信息异常", e);
		}

		return activityList;
	}
}
