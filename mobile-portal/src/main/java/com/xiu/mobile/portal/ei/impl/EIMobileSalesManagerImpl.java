package com.xiu.mobile.portal.ei.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.sales.dointerface.vo.GiftCardCode;
import com.xiu.common.command.result.Result;
import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.constants.ErrConstants;
import com.xiu.mobile.portal.ei.EIMobileSalesManager;
import com.xiu.mobile.sales.dointerface.activity.vo.WeiXinActivityJoinRequest;
import com.xiu.mobile.sales.dointerface.activity.vo.WeiXinActivityJoinResponse;
import com.xiu.mobile.sales.dointerface.activity.vo.WeiXinActivityVirtualPayRequest;
import com.xiu.mobile.sales.dointerface.activity.vo.WeiXinActivityVirtualPayResponse;
import com.xiu.mobile.sales.dointerface.card.vo.CardInfoRequest;
import com.xiu.mobile.sales.dointerface.serivce.MobileSalesServiceFacade;
import com.xiu.mobile.sales.dointerface.vo.ActivityGiveCouponRequest;
import com.xiu.mobile.sales.dointerface.vo.ConsumeCardRequest;
import com.xiu.mobile.sales.dointerface.vo.DeviceActivityRequest;
import com.xiu.mobile.sales.dointerface.vo.FirstLoginRewardParamIn;
import com.xiu.mobile.sales.dointerface.vo.FirstOpenRecordParamIn;
import com.xiu.mobile.sales.dointerface.vo.PageView;
import com.xiu.mobile.sales.dointerface.vo.SpreadSupportInfoVo;
import com.xiu.mobile.sales.dointerface.vo.WeiXinActivityRequest;
import com.xiu.mobile.sales.dointerface.vo.WeiXinActivityResponse;
import com.xiu.mobile.sales.dointerface.vo.WeiXinSpreadResponse;
import com.xiu.mobile.sales.dointerface.vo.WeiXinSpreadRewardQueryParames;
import com.xiu.mobile.sales.dointerface.vo.WeiXinSpreadRewardResult;
import com.xiu.mobile.sales.dointerface.vo.WeiXinSupportRequest;
import com.xiu.mobile.sales.dointerface.vo.WeiXinSupportUserRequest;
import com.xiu.mobile.sales.dointerface.vo.WeiXinUserRequest;

/**
 * @ClassName: EIMobileSalesManagerImpl
 * @Description: 移动营销接口类
 * @author hejianxiong
 * @date 2014-7-8 11:21:05
 * 
 */
@Service("eiMobileSalesManager")
public class EIMobileSalesManagerImpl implements EIMobileSalesManager {
	private static final Logger logger = Logger.getLogger(EIMobileSalesManagerImpl.class);
	@Autowired
	private MobileSalesServiceFacade mSalesServiceFacade;

	@Override
	public boolean recordFirstOpen(String deviceId, String deviceOS,String source,String version,String idfa) {
		Result result = null;
		String paraLog = "记录第一次打开deviceId=" + deviceId + " , deviceOs="
				+ deviceOS + ",source=" + source + ",version=" + version;
		try {
			logger.info(paraLog);
			FirstOpenRecordParamIn paramIn = new FirstOpenRecordParamIn();
			paramIn.setDeviceId(deviceId);
			paramIn.setDeviceOS(deviceOS);
			paramIn.setSource(source);
			paramIn.setNowVersion(version);
			paramIn.setIdfa(idfa);
			result = mSalesServiceFacade.recordFirstOpen(paramIn);
			logger.info(paraLog + ",result=" + result);
			return result.isSuccess();
		} catch (Exception e) {
			logger.info(paraLog + ",exception ", e);
			return false;
		}
	}

	@Override
	public boolean awardFirstLogin(String deviceId, String deviceOS,String source,String version,
			String userId, String userName,String idfa) {
		Result result = null;
		String paraLog = "奖励第一次登录deviceId=" + deviceId + " , deviceOs="
				+ deviceOS + " , userId=" + userId + " , userName=" + userName;
		try {
			logger.info(paraLog);
			FirstLoginRewardParamIn paramIn = new FirstLoginRewardParamIn();
			paramIn.setDeviceId(deviceId);
			paramIn.setDeviceOS(deviceOS);
			paramIn.setSource(source);
			paramIn.setNowVersion(version);
			paramIn.setUserId(Long.parseLong(userId));
			paramIn.setUserName(userName);
			paramIn.setIdfa(idfa);
			result = mSalesServiceFacade.awardFirstLogin(paramIn);
			logger.info(paraLog + ",result=" + result);
			return result.isSuccess();
		} catch (Exception e) {
			logger.info(paraLog + ",exception", e);
			return false;
		}
	}
	
	@Override
	public Result userRegisterGiveCoupon(String activityTypeName, String appTerminalTypeName, long userId, String userName) {
		
		ActivityGiveCouponRequest req = new ActivityGiveCouponRequest();
		req.setActivityTypeName(activityTypeName);
		req.setAppTerminalTypeName(appTerminalTypeName);
		req.setUserId(userId);
		req.setUserName(userName);
		Result result = null;
		
		String reqLog = "注册成功送券 UserName=" + req.getUserName() + " , UserId="
				+ req.getUserId() + " , ActivityTypeName=" + req.getActivityTypeName() + " , AppTerminalTypeName=" + req.getAppTerminalTypeName();
		try {
			logger.info(reqLog);
			result = mSalesServiceFacade.userRegisterGiveCoupon(req);
			logger.info(reqLog + ",result=" + result);
			return result;
		} catch (Exception e) {
			logger.info(reqLog + ",exception", e);
			return result;
		}
	}
	
	
	@Override
	public WeiXinActivityResponse getWeiXinActivitySpreadAndSupportInfo(String activityId, String unionId, String code,String spreadUnionId) {
		WeiXinActivityResponse weiXinActivityResponse = null;
		Result result = null;
		try {
			WeiXinActivityRequest weiXinActivityRequest = new WeiXinActivityRequest();
			weiXinActivityRequest.setActivityId(activityId);
			weiXinActivityRequest.setUnionId(unionId);
			weiXinActivityRequest.setCode(code);
			weiXinActivityRequest.setSpreadUnionId(spreadUnionId);
			result = mSalesServiceFacade.getWeiXinActivitySpreadAndSupportInfo(weiXinActivityRequest);
		} catch (Exception e) {
			logger.error("进入微信活动页获取活动传播者讨赏和针对该传播者的支持者信息异常" + e, e);
			throw ExceptionFactory.buildEIRuntimeException("获取活动传播者讨赏和针对该传播者的支持者信息异常", e);
		}
		
		if (result != null) {
			boolean isSuccess = result.isSuccess();
			logger.info("是否成功" + isSuccess + " ,结果码:" + result.getResultCode() + "错误消息:" + result.getErrorMessages());
			if (isSuccess) {
				// 活动有效,数据没有被篡改
				Object activityResponse = result.getModels().get("activityResponse");
				if (activityResponse != null && activityResponse instanceof WeiXinActivityResponse) {
					weiXinActivityResponse = (WeiXinActivityResponse) activityResponse;
				}
				if (weiXinActivityResponse != null) {
					int fullAmountOfSpreader = weiXinActivityResponse.getFullAmountOfSpreader();// 已讨满多少钱的金额   单位是分
					logger.info("Spreader已讨满多少钱的金额:" + fullAmountOfSpreader + "分"); 
					int fullAmountOfSupporter = weiXinActivityResponse.getFullAmountOfSupporter();// 已讨满多少钱的金额   单位是分
					logger.info("Supporter已讨满多少钱的金额:" + fullAmountOfSupporter + "分");
					boolean isSpreadIsGiveConpon = weiXinActivityResponse.isSpreadIsGiveConpon();// true:可以领礼品,false为不可以,控制前端领礼品按钮
					String spreadCode = weiXinActivityResponse.getCode();// 防止篡改的加密字符串,调用发起讨赏(传播时)方法:spreadSendReward
					logger.info("防止篡改的加密字符为:" + spreadCode);
					logger.info("是否可以领礼品:" + isSpreadIsGiveConpon);
					List<SpreadSupportInfoVo> spreadSupportInfoVos = weiXinActivityResponse.getSpreadSupportInfoVos();// 微信活动支持者对传播
					if (spreadSupportInfoVos != null && spreadSupportInfoVos.size() > 0) {
						for (SpreadSupportInfoVo spreadSupportInfoVo : spreadSupportInfoVos) {
							logger.info(spreadSupportInfoVo);
						}
					}
				}
			}else{
				StringBuilder builder = new StringBuilder();
				Set<String> keySet = result.getErrorMessages().keySet();
				for (String key : keySet) {
					builder.append(result.getErrorMessages().get(key)).append(" ");
				}
				throw ExceptionFactory.buildEIBusinessException(result.getResultCode(), result.getResultCode(), builder.toString());
			}
		}
		
		
		return weiXinActivityResponse;
	}

	@Override
	public WeiXinSpreadResponse spreadSendReward(String activityId, String unionId, String code, String nickName, String userPhoneUrl, String xiuUserName, String xiuUserId) {
		WeiXinSpreadResponse weiXinSpreadResponse = null;
		Result result = null;
		try {
			WeiXinUserRequest weiXinUserRequest = new WeiXinUserRequest();
			weiXinUserRequest.setActivityId(activityId);
			weiXinUserRequest.setUnionId(unionId);
			weiXinUserRequest.setCode(code);
			weiXinUserRequest.setNickName(nickName);
			weiXinUserRequest.setXiuUserId(Long.parseLong(xiuUserId));
			weiXinUserRequest.setXiuUserName(xiuUserName);
			weiXinUserRequest.setUserPhoneUrl(userPhoneUrl);
			result = mSalesServiceFacade.spreadSendReward(weiXinUserRequest);
		} catch (Exception e) {
			logger.error("活动传播者发出讨赏异常" + e, e);
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_TRADE_GENERAL_ERR, e);
		}
		
		if (result != null) {
			boolean isSuccess = result.isSuccess();
			logger.info("是否成功" + isSuccess + " ,结果码:" + result.getResultCode() + "错误消息:" + result.getErrorMessages());
			if (isSuccess) {
				// 活动有效,数据没有被篡改
				Object spreadResponse = result.getModels().get("spreadResponse");
				if (spreadResponse != null && spreadResponse instanceof WeiXinSpreadResponse) {
					weiXinSpreadResponse = (WeiXinSpreadResponse) spreadResponse;
				}
			}else{
				StringBuilder builder = new StringBuilder();
				Set<String> keySet = result.getErrorMessages().keySet();
				for (String key : keySet) {
					builder.append(result.getErrorMessages().get(key)).append(" ");
				}
				throw ExceptionFactory.buildEIBusinessException(result.getResultCode(), result.getResultCode(), builder.toString());
			}
		}

		return weiXinSpreadResponse;
	}

	@Override
	public boolean spreadUserGiveGift(String activityId, String unionId, String code) {
		boolean isSuccess = false;
		Result result = null;
		try {
			WeiXinActivityRequest weiXinActivityRequest = new WeiXinActivityRequest();
			weiXinActivityRequest.setActivityId(activityId);
			weiXinActivityRequest.setCode(code);
			weiXinActivityRequest.setUnionId(unionId);
			result = mSalesServiceFacade.spreadUserGiveGift(weiXinActivityRequest);
		} catch (Exception e) {
			logger.error("微信活动传播者(讨满一定金额)后获取礼品异常" + e,e);
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_TRADE_GENERAL_ERR, e);
		}
		
		if (result != null) {
			isSuccess = result.isSuccess();
			logger.info("是否成功" + isSuccess + " ,结果码:" + result.getResultCode() + "错误消息:" + result.getErrorMessages());
			if (!isSuccess) {
				StringBuilder builder = new StringBuilder();
				Set<String> keySet = result.getErrorMessages().keySet();
				for (String key : keySet) {
					builder.append(result.getErrorMessages().get(key)).append(" ");
				}
				throw ExceptionFactory.buildEIBusinessException(result.getResultCode(), result.getResultCode(), builder.toString());
			}
		}

		return isSuccess;
	}
	@Override
	public Map<String, Object> supportSendReward(String activityId,
			String unionId, String code, String nickName, String supportUnionId, String supportSay, String supportPhoneUrl, String xiuUserName, String xiuUserId, String spreadUnionId, boolean check, String extstring2, Double extnumber1, String telephone) {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		WeiXinSupportRequest supportUserRequest = new WeiXinSupportRequest();
		supportUserRequest.setActivityId(activityId);
		supportUserRequest.setCode(code);
		supportUserRequest.setUnionId(unionId);// 传播者的unionId
		supportUserRequest.setSupportUnionId(supportUnionId);
		supportUserRequest.setSpreadUnionId(spreadUnionId);//订单号
		supportUserRequest.setXiuUserId(StringUtils.isBlank(xiuUserId) ? null : Long.parseLong(xiuUserId));
		supportUserRequest.setXiuUserName(xiuUserName);
		supportUserRequest.setNickName(nickName);
		supportUserRequest.setSupportPhoneUrl(supportPhoneUrl);
		supportUserRequest.setSupportSay(supportSay);
		supportUserRequest.setCheck(check);
		supportUserRequest.setBizParam("extString2", extstring2);
		supportUserRequest.setBizParam("extNumber1", extnumber1);
		supportUserRequest.setBizParam("telephone", telephone);
		
		try{
			Result result = mSalesServiceFacade.supportSendReward(supportUserRequest);
			
			if (result != null) {
				boolean isSuccess = result.isSuccess();
				logger.info("是否成功" + isSuccess + " ,结果码:" + result.getResultCode() + "错误消息:" + result.getErrorMessages());
				if (isSuccess) {
					returnMap.put("result", true);
					returnMap.put("errorCode", "0");
					returnMap.put("errorMsg", "成功");
					returnMap.put("weiXinSupportResponse", result.getModels().get("supportResponse"));
					returnMap.put("supportList", result.getModels().get("supportList"));
					returnMap.put("giftbagAmount", result.getModels().get("giftbagAmount"));
					
				}else{
					returnMap.put("result", false);
					Set<String> keySet = result.getErrorMessages().keySet();
					for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
						String key = iterator.next();
						returnMap.put("errorCode", key);
						returnMap.put("errorMsg", result.getErrorMessages().get(key));
					}
				}
				logger.info("支持者为微信传播者打赏，返回结果：" + returnMap);
			}
		}catch(Exception e){
			logger.error("支持者为微信传播者打赏异常", e);
			returnMap.put("result", false);
			returnMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			returnMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return returnMap;
	}
	@Override
	public Map<String, Object> supportSendRewardNew(String activityId,
			String unionId, String code, String nickName, String supportUnionId, String supportSay, String supportPhoneUrl, String xiuUserName, String xiuUserId, String spreadUnionId, boolean check, String extstring2, Double extnumber1, String telephone) {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		WeiXinSupportRequest supportUserRequest = new WeiXinSupportRequest();
		supportUserRequest.setActivityId(activityId);
		supportUserRequest.setCode(code);
		supportUserRequest.setUnionId(unionId);// 传播者的unionId
		supportUserRequest.setSupportUnionId(supportUnionId);
		supportUserRequest.setSpreadUnionId(spreadUnionId);//订单号
		supportUserRequest.setXiuUserId(StringUtils.isBlank(xiuUserId) ? null : Long.parseLong(xiuUserId));
		supportUserRequest.setXiuUserName(xiuUserName);
		supportUserRequest.setNickName(nickName);
		supportUserRequest.setSupportPhoneUrl(supportPhoneUrl);
		supportUserRequest.setSupportSay(supportSay);
		supportUserRequest.setCheck(check);
		supportUserRequest.setBizParam("extString2", extstring2);
		supportUserRequest.setBizParam("extNumber1", extnumber1);
		supportUserRequest.setBizParam("telephone", telephone);
		
		try{
			Result result = mSalesServiceFacade.supportSendRewardNew(supportUserRequest);
			
			if (result != null) {
				boolean isSuccess = result.isSuccess();
				logger.info("是否成功" + isSuccess + " ,结果码:" + result.getResultCode() + "错误消息:" + result.getErrorMessages());
				if (isSuccess) {
					returnMap.put("result", true);
					returnMap.put("errorCode", "0");
					returnMap.put("errorMsg", "成功");
					returnMap.put("supportList", result.getModels().get("supportList"));
					returnMap.put("giftbagAmount", result.getModels().get("giftbagAmount"));
					returnMap.put("isNew", result.getModels().get("isNew"));
				}else{
					returnMap.put("result", false);
					Set<String> keySet = result.getErrorMessages().keySet();
					for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
						String key = iterator.next();
						returnMap.put("errorCode", key);
						returnMap.put("errorMsg", result.getErrorMessages().get(key));
					}
				}
				logger.info("支持者为微信传播者打赏，返回结果：" + returnMap);
			}
		}catch(Exception e){
			logger.error("支持者为微信传播者打赏异常", e);
			returnMap.put("result", false);
			returnMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			returnMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return returnMap;
	}

	@Override
	public boolean supportUserGiveGift(String activityId, String unionId, String code, String supportUnionId) {
		boolean isSuccess = false;
		Result result = null;
		try {
			WeiXinSupportUserRequest weiXinSupportUserRequest = new WeiXinSupportUserRequest();
			weiXinSupportUserRequest.setActivityId(activityId);
			weiXinSupportUserRequest.setUnionId(unionId);
			weiXinSupportUserRequest.setSupportUnionId(supportUnionId);
			weiXinSupportUserRequest.setCode(code);
			result = mSalesServiceFacade.supportUserGiveGift(weiXinSupportUserRequest);
		} catch (Exception e) {
			logger.error("微信活动支持者为微信活动传播者打赏成功后获取礼品异常" + e,e);
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_TRADE_GENERAL_ERR, e);
		}

		if (result != null) {
			isSuccess = result.isSuccess();
			logger.info("是否成功" + isSuccess + " ,结果码:" + result.getResultCode() + "错误消息:" + result.getErrorMessages());
			if (!isSuccess) {
				StringBuilder builder = new StringBuilder();
				Set<String> keySet = result.getErrorMessages().keySet();
				for (String key : keySet) {
					builder.append(result.getErrorMessages().get(key)).append(" ");
				}
				throw ExceptionFactory.buildEIBusinessException(result.getResultCode(), result.getResultCode(), builder.toString());
			}
		}
		return isSuccess;
	}

	@Override
	public PageView<WeiXinSpreadRewardResult> queryWeiXinSpreadRewardResult(String activityId, String activityType, int currentPage, int pageSize) {
		PageView<WeiXinSpreadRewardResult> pageView = null;
		Result result = null;
		try {
			WeiXinSpreadRewardQueryParames weiXinSpreadRewardQueryParames = new WeiXinSpreadRewardQueryParames();
			weiXinSpreadRewardQueryParames.setActivityId(activityId);
			int typeId = NumberUtils.toInt(activityType, 1);
			weiXinSpreadRewardQueryParames.setActivityType(typeId);
			weiXinSpreadRewardQueryParames.setPageSize(pageSize);
			weiXinSpreadRewardQueryParames.setCurrentPage(currentPage);
			result = mSalesServiceFacade.queryWeiXinSpreadRewardResult(weiXinSpreadRewardQueryParames);
		} catch (Exception e) {
			logger.error("微信活动支持者为微信活动传播者打赏成功后获取礼品异常" + e,e);
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_TRADE_GENERAL_ERR, e);
		}

		if (result != null) {
			boolean isSuccess = result.isSuccess();
			logger.info("是否成功" + isSuccess + " ,结果码:" + result.getResultCode() + "错误消息:" + result.getErrorMessages());
			if (isSuccess) {
				Object pageViewObject = result.getModels().get("pageView");
				if (pageViewObject != null && pageViewObject instanceof PageView) {
					pageView = (PageView<WeiXinSpreadRewardResult>) pageViewObject;
					logger.info("totalRecord：" + pageView.getTotalRecord());
					List<WeiXinSpreadRewardResult> weiXinSpreadRewardResult = pageView.getRecords();
					if (weiXinSpreadRewardResult != null && weiXinSpreadRewardResult.size() > 0) {
						for (WeiXinSpreadRewardResult weiXinSpreadReward : weiXinSpreadRewardResult) {
							logger.info("nickName:" + weiXinSpreadReward.getNickName() + "  spreadPhoneUrl:" + weiXinSpreadReward.getSpreadPhoneUrl() + " spreadPresentIllustration:" + weiXinSpreadReward.getSpreadPresentIllustration());
						}
					}
				}
			}else{
				StringBuilder builder = new StringBuilder();
				Set<String> keySet = result.getErrorMessages().keySet();
				for (String key : keySet) {
					builder.append(result.getErrorMessages().get(key)).append(" ");
				}
				throw ExceptionFactory.buildEIBusinessException(result.getResultCode(), result.getResultCode(), builder.toString());
			}
		}

		return pageView;
	}

	public WeiXinActivityVirtualPayResponse weiXinActivityVirtaulPayHomePage(String activityId, String unionId, String code, String spreadUnionId, String nickName, String supportSay, String supportPhoneUrl, String xiuUserName, String xiuUserId) {
		WeiXinActivityVirtualPayResponse weiXinActivityResponse = null;
		Result result = null;
		try {
			WeiXinActivityVirtualPayRequest weiXinActivityRequest = new WeiXinActivityVirtualPayRequest();
			weiXinActivityRequest.setActivityId(activityId);
			weiXinActivityRequest.setUnionId(unionId);
			weiXinActivityRequest.setCode(code);
			weiXinActivityRequest.setSpreadUnionId(spreadUnionId);
			weiXinActivityRequest.setNickName(nickName);
			weiXinActivityRequest.setSupportSay(supportSay);
			weiXinActivityRequest.setSupportPhoneUrl(supportPhoneUrl);
			weiXinActivityRequest.setXiuUserName(xiuUserName);
			weiXinActivityRequest.setXiuUserId(Long.parseLong(xiuUserId));
			result = mSalesServiceFacade.weiXinActivityVirtaulPayHomePage(weiXinActivityRequest);
		} catch (Exception e) {
			logger.error("进入微信活动页获取活动传播者讨赏和针对该传播者的支持者信息异常" + e, e);
			throw ExceptionFactory.buildEIRuntimeException("获取活动传播者讨赏和针对该传播者的支持者信息异常", e);
		}
		
		if (result != null) {
			boolean isSuccess = result.isSuccess();
			logger.info("是否成功" + isSuccess + " ,结果码:" + result.getResultCode() + "错误消息:" + result.getErrorMessages());
			if (isSuccess) {
				// 活动有效,数据没有被篡改
				Object activityResponse = result.getModels().get("activityVirtualPayResponse");
				if (activityResponse != null && activityResponse instanceof WeiXinActivityVirtualPayResponse) {
					weiXinActivityResponse = (WeiXinActivityVirtualPayResponse) activityResponse;
				}
			}else{
				StringBuilder builder = new StringBuilder();
				Set<String> keySet = result.getErrorMessages().keySet();
				for (String key : keySet) {
					builder.append(result.getErrorMessages().get(key)).append(" ");
				}
				throw ExceptionFactory.buildEIBusinessException(result.getResultCode(), result.getResultCode(), builder.toString());
			}
		}
		
		return weiXinActivityResponse;
	}
	
	public Map weiXinActivityVirtaulPayHomePageNew(String activityId, String unionId, String code, String spreadUnionId, String nickName, String supportSay, String supportPhoneUrl, String xiuUserName, String xiuUserId) {
		Map resultMap = new HashMap();
		WeiXinActivityVirtualPayResponse weiXinActivityResponse = null;
		Result result = null;
		try {
			WeiXinActivityVirtualPayRequest weiXinActivityRequest = new WeiXinActivityVirtualPayRequest();
			weiXinActivityRequest.setActivityId(activityId);
			weiXinActivityRequest.setUnionId(unionId);
			weiXinActivityRequest.setCode(code);
			weiXinActivityRequest.setSpreadUnionId(spreadUnionId);
			weiXinActivityRequest.setNickName(nickName);
			weiXinActivityRequest.setSupportSay(supportSay);
			weiXinActivityRequest.setSupportPhoneUrl(supportPhoneUrl);
			weiXinActivityRequest.setXiuUserName(xiuUserName);
			weiXinActivityRequest.setXiuUserId(Long.parseLong(xiuUserId));
			result = mSalesServiceFacade.weiXinActivityVirtaulPayHomePage(weiXinActivityRequest);
		} catch (Exception e) {
			logger.error("进入微信活动页获取活动传播者讨赏和针对该传播者的支持者信息异常" + e, e);
			throw ExceptionFactory.buildEIRuntimeException("获取活动传播者讨赏和针对该传播者的支持者信息异常", e);
		}
		
		if (result != null) {
			boolean isSuccess = result.isSuccess();
			logger.info("是否成功" + isSuccess + " ,结果码:" + result.getResultCode() + "错误消息:" + result.getErrorMessages());
			// 活动有效,数据没有被篡改
			Object activityResponse = result.getModels().get("activityVirtualPayResponse");
			if (activityResponse != null && activityResponse instanceof WeiXinActivityVirtualPayResponse) {
				weiXinActivityResponse = (WeiXinActivityVirtualPayResponse) activityResponse;
			}
			
			resultMap.put("weiXinActivityResponse", weiXinActivityResponse);  //返回结果对象
			
			resultMap.put("resultCode", result.getResultCode()); //返回错误代码
			resultMap.put("errorMessages", result.getErrorMessages()); //返回错误消息
		}
		
		return resultMap;
	}

	public WeiXinActivityJoinResponse queryWeiXinJoinInfo(String activityId) {
		WeiXinActivityJoinResponse weiXinActivityJoinResponse = null;
		Result result = null;
		try {
			WeiXinActivityJoinRequest weiXinActivityRequest = new WeiXinActivityJoinRequest();
			weiXinActivityRequest.setActivityId(activityId);
			result = mSalesServiceFacade.queryWeiXinJoinInfo(weiXinActivityRequest);
		} catch (Exception e) {
			logger.error("获取微信获取参与人数,与送出的年终奖信息异常" + e, e);
			throw ExceptionFactory.buildEIRuntimeException("获取微信获取参与人数,与送出的年终奖信息异常", e);
		}
		
		if (result != null) {
			boolean isSuccess = result.isSuccess();
			logger.info("是否成功" + isSuccess + " ,结果码:" + result.getResultCode() + "错误消息:" + result.getErrorMessages());
			if (isSuccess) {
				// 活动有效,数据没有被篡改
				Object activityResponse = result.getModels().get("activityJoinResponse");
				if (activityResponse != null && activityResponse instanceof WeiXinActivityJoinResponse) {
					weiXinActivityJoinResponse = (WeiXinActivityJoinResponse) activityResponse;
				}
			}else{
				StringBuilder builder = new StringBuilder();
				Set<String> keySet = result.getErrorMessages().keySet();
				for (String key : keySet) {
					builder.append(result.getErrorMessages().get(key)).append(" ");
				}
				throw ExceptionFactory.buildEIBusinessException(result.getResultCode(), result.getResultCode(), builder.toString());
			}
		}
		
		return weiXinActivityJoinResponse;
	}

	/**
	 * 获取微信活动信息
	 * @param request
	 * @return
	 */
	@Override
	public WeiXinActivityResponse getWeiXinActivitySupportInfo(String activityId,
			String unionId, String spreadUnionId, String code, Long xiuUserId, String xiuUserName, String nickName, String userPhoneUrl, boolean check, String extstring1, String extstring2, Double extnumber1) {
		WeiXinUserRequest request = new WeiXinUserRequest();
		request.setActivityId(activityId);
		request.setCode(code);
		request.setUnionId(unionId);
		request.setSpreadUnionId(spreadUnionId);
		request.setXiuUserId(xiuUserId);
		request.setXiuUserName(xiuUserName);
		request.setNickName(nickName);
		request.setUserPhoneUrl(userPhoneUrl);
		request.setCheck(check);
		request.setBizParam("extString1", extstring1);
		request.setBizParam("extString2", extstring2);
		request.setBizParam("extNumber1", extnumber1);
		
		WeiXinActivityResponse weiXinActivityResponse = null;
		Result result = mSalesServiceFacade.getWeiXinActivitySupportInfo(request);
		boolean isSuccess = result.isSuccess();
		logger.info("是否成功" + isSuccess + " ,结果码:" + result.getResultCode() + "错误消息:" + result.getErrorMessages());
		if (isSuccess) {
			// 活动有效,数据没有被篡改
			Object activityResponse = result.getModels().get("activityResponse");
			if (activityResponse != null && activityResponse instanceof WeiXinActivityResponse) {
				weiXinActivityResponse = (WeiXinActivityResponse) activityResponse;
			}
		}else{
			StringBuilder builder = new StringBuilder();
			Set<String> keySet = result.getErrorMessages().keySet();
			for (String key : keySet) {
				builder.append(result.getErrorMessages().get(key)).append(" ");
			}
			throw ExceptionFactory.buildEIBusinessException(result.getResultCode(), result.getResultCode(), builder.toString());
		}
		return weiXinActivityResponse;
	}
	@Override
	public Map<String, Object> getOrderRedpacketParentInfo(String activityId,
			String unionId, String spreadUnionId, String code, Long xiuUserId, String xiuUserName, boolean check) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		WeiXinUserRequest request = new WeiXinUserRequest();
		request.setActivityId(activityId);
		request.setCode(code);
		request.setUnionId(unionId);
		request.setSpreadUnionId(spreadUnionId);
		request.setXiuUserId(xiuUserId);
		request.setXiuUserName(xiuUserName);
		request.setCheck(check);
		try{
			Result result = mSalesServiceFacade.getWeiXinActivitySupportInfoNew(request);
			
			if(result!=null){
				if(result.isSuccess()){
					returnMap.put("result", true);
					returnMap.put("errorCode", "0");
					returnMap.put("errorMsg", "成功");
					returnMap.put("redPacketParent", result.getModels().get("redPacketParent"));
					returnMap.put("redpacketChildren", result.getModels().get("redpacketChildren"));
				}else{
					returnMap.put("result", false);
					Set<String> keySet = result.getErrorMessages().keySet();
					for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
						String key = iterator.next();
						returnMap.put("errorCode", key);
						returnMap.put("errorMsg", result.getErrorMessages().get(key));
					}
				}
			}
		}catch(Exception e){
			logger.error("获取活动信息返回结果发生异常：", e);
			returnMap.put("result", false);
			returnMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			returnMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return returnMap;
	}
	/**
	 * 微信扫码获取优惠礼包
	 * @param request
	 * @return
	 */
	@Override
	public WeiXinActivityResponse scanCodeWeiXinActivityPage(String activityId,
			String unionId, String spreadUnionId, String code, Long xiuUserId, String xiuUserName, String nickName, String userPhoneUrl) {
		WeiXinUserRequest request = new WeiXinUserRequest();
		request.setActivityId(activityId);
		request.setCode(code);
		request.setUnionId(unionId);
		request.setSpreadUnionId(spreadUnionId);
		request.setXiuUserId(xiuUserId);
		request.setXiuUserName(xiuUserName);
		request.setNickName(nickName);
		request.setUserPhoneUrl(userPhoneUrl);
		
		WeiXinActivityResponse weiXinActivityResponse = null;
		Result result = mSalesServiceFacade.scanCodeWeiXinActivityPage(request);
		boolean isSuccess = result.isSuccess();
		logger.info("是否成功" + isSuccess + " ,结果码:" + result.getResultCode() + "错误消息:" + result.getErrorMessages());
		if (isSuccess) {
			// 活动有效,数据没有被篡改
			Object activityResponse = result.getModels().get("activityResponse");
			if (activityResponse != null && activityResponse instanceof WeiXinActivityResponse) {
				weiXinActivityResponse = (WeiXinActivityResponse) activityResponse;
			}
		}else{
			StringBuilder builder = new StringBuilder();
			Set<String> keySet = result.getErrorMessages().keySet();
			for (String key : keySet) {
				builder.append(result.getErrorMessages().get(key)).append(" ");
			}
			throw ExceptionFactory.buildEIBusinessException(result.getResultCode(), result.getResultCode(), builder.toString());
		}
		return weiXinActivityResponse;
	}
	
	@Override
	public Result consumeWechatCard(Long xiuUserId, String xiuUserName,
			String unionId, String encryptCode, String cardId, String signature) {
		ConsumeCardRequest request = new ConsumeCardRequest();
		request.setXiuUserId(xiuUserId);
		request.setXiuUserName(xiuUserName);
		request.setUnionId(unionId);
		request.setEncryptCode(encryptCode);
		request.setCardId(cardId);
		request.setSignature(signature);
		
		logger.info("用户[" + unionId + "]调用核销卡券接口！" + request);

		return mSalesServiceFacade.consumeWechatCard(request);
	}
	
	@Override
	public Map<String, Object> userCustomReward(Long xiuUserId,String xiuUserName,String deviceId,String operType, String platform){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		DeviceActivityRequest request = new DeviceActivityRequest();
		request.setDeviceId(deviceId);
		request.setXiuUserId(String.valueOf(xiuUserId));
		request.setStatus(operType);
		request.setXiuUserName(xiuUserName);
		request.setPlatform(platform);
		
		try{
			Result result = mSalesServiceFacade.sendDeviceActivityCard(request);
			logger.info("设备：" + deviceId + "，用户：" + xiuUserId + "，专享送券返回结果：" + result.getResultCode());
			
			returnMap.put("result", true);
			returnMap.put("errorCode", "0");
			returnMap.put("errorMsg", "成功");
			returnMap.put("resultCode", result.getResultCode());
				
		}catch(Exception e){
			logger.error("设备专享送券异常", e);
			returnMap.put("result", false);
			returnMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			returnMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return returnMap;
	}
	
	public Map<String, Object> customRewardStatus(String xiuUserId, String deviceId){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		DeviceActivityRequest request = new DeviceActivityRequest();
		request.setDeviceId(deviceId);
		request.setXiuUserId(xiuUserId);
		
		try{
			Result result = mSalesServiceFacade.getDeviceCardStatus(request);
			logger.info("设备：" + deviceId + "，用户：" + xiuUserId + "，获取送券状态返回结果：" + result.getResultCode());
			
			returnMap.put("result", true);
			returnMap.put("errorCode", "0");
			returnMap.put("errorMsg", "成功");
			returnMap.put("resultCode", result.getResultCode());
				
		}catch(Exception e){
			logger.error("获取设备送券状态异常", e);
			returnMap.put("result", false);
			returnMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			returnMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> spreadSendRewardNew(String activityCode,
			String spreadUnionId, Long xiuUserId, String xiuUserName,
			String userPhoneUrl, String code, boolean check,String mobile,String deviceId) {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		WeiXinUserRequest request = new WeiXinUserRequest();
		//活动code，这里填写orderActivityCode
		request.setBizParam("activityCode", activityCode);
		request.setBizParam("mobile", mobile);
		request.setBizParam("deviceId", deviceId);
        //填写订单id
		request.setSpreadUnionId(spreadUnionId);
		request.setXiuUserId(xiuUserId);
		request.setNickName(xiuUserName);
		request.setUserPhoneUrl(userPhoneUrl);
		request.setCheck(check);
		request.setCode(code);
        
		try{
			Result result = mSalesServiceFacade.spreadSendRewardNew(request);
			logger.info("获取分享红包链接返回结果：" + result.isSuccess());
			
			if(result.isSuccess()){
				returnMap.put("result", true);
				returnMap.put("errorCode", "0");
				returnMap.put("errorMsg", "成功");
				returnMap.put("shareUrl", result.getModels().get("shareUrl"));
				returnMap.put("giftbagCount", result.getModels().get("totalCount"));
				returnMap.put("shareContent", result.getModels().get("shareContent"));
				returnMap.put("shareTitle", result.getModels().get("shareTitle"));
				logger.info("返回分享链接：" + result.getModels().get("shareUrl"));
			}else{
				returnMap.put("result", false);
				Set<String> keySet = result.getErrorMessages().keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					returnMap.put("errorCode", key);
					returnMap.put("errorMsg", result.getErrorMessages().get(key));
				}
			}
				
		}catch(Exception e){
			logger.error("获取分享红包链接异常", e);
			returnMap.put("result", false);
			returnMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			returnMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return returnMap;
	}
	@Override
	public Map<String, Object> spreadSendReward(String activityCode,
			String spreadUnionId, Long xiuUserId, String xiuUserName,
			String userPhoneUrl, String code, boolean check) {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		WeiXinUserRequest request = new WeiXinUserRequest();
		//活动code，这里填写orderActivityCode
		request.setBizParam("activityCode", activityCode);
        //填写订单id
		request.setSpreadUnionId(spreadUnionId);
		request.setXiuUserId(xiuUserId);
		request.setNickName(xiuUserName);
		request.setUserPhoneUrl(userPhoneUrl);
		request.setCheck(check);
		request.setCode(code);
        
		try{
			Result result = mSalesServiceFacade.spreadSendReward(request);
			logger.info("获取分享红包链接返回结果：" + result.isSuccess());
			
			if(result.isSuccess()){
				returnMap.put("result", true);
				returnMap.put("errorCode", "0");
				returnMap.put("errorMsg", "成功");
				returnMap.put("shareUrl", result.getModels().get("shareUrl"));
				returnMap.put("giftbagCount", result.getModels().get("totalCount"));
				returnMap.put("shareContent", result.getModels().get("shareContent"));
				returnMap.put("shareTitle", result.getModels().get("shareTitle"));
				logger.info("返回分享链接：" + result.getModels().get("shareUrl"));
			}else{
				returnMap.put("result", false);
				Set<String> keySet = result.getErrorMessages().keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					returnMap.put("errorCode", key);
					returnMap.put("errorMsg", result.getErrorMessages().get(key));
				}
			}
				
		}catch(Exception e){
			logger.error("获取分享红包链接异常", e);
			returnMap.put("result", false);
			returnMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			returnMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return returnMap;
	}
	@Override
	public Map<String,Object> spreadIsBlacklist(long userId,String mobile,String deviceId,String orderId){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		WeiXinUserRequest request = new WeiXinUserRequest();
		request.setBizParam("mobile", mobile);
		request.setBizParam("deviceId", deviceId);
		request.setXiuUserId(userId);
		request.setBizParam("orderId", orderId);
		try{
			Result result=mSalesServiceFacade.spreadIsBlacklist(request);
			logger.info("获取判断分享者是否是黑名单结果：" + result.isSuccess());
			if(result.isSuccess()){
				returnMap.put("result", true);
				returnMap.put("errorCode", "0");
				returnMap.put("errorMsg", "成功");
				returnMap.put("userId", result.getModels().get("userId"));
				returnMap.put("mobile", result.getModels().get("mobile"));
			}else{
				returnMap.put("result", false);
				Set<String> keySet = result.getErrorMessages().keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					returnMap.put("errorCode", key);
					returnMap.put("errorMsg", result.getErrorMessages().get(key));
				}
			}
		}catch(Exception e){
			logger.error("获取判断分享者是否是黑名单结果异常", e);
			returnMap.put("result", false);
			returnMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			returnMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return returnMap;
	}
	/**
	 * 下单发红包分享者黑名单处理
	 */
	public void findBlacklistByspread(String mobile,long userId,String deviceId){
		Map<String,Object> parems=new HashMap<String, Object>();
		parems.put("mobile", mobile);
		parems.put("userId", userId);
		parems.put("deviceId", deviceId);
		mSalesServiceFacade.findBlacklistByspread(parems);;
	}
	/**
	 * 下单发红包领取者黑名单处理
	 */
	public void findBlacklistBysupport(String mobile){
		Map<String,Object> parems=new HashMap<String, Object>();
		parems.put("mobile", mobile);
		mSalesServiceFacade.findBlacklistBysupport(parems);
	}
	public Map<String,Object> getOrderRedPacketNum(long orderId){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		try{
			Result result=mSalesServiceFacade.getOrderRedPacketNum(orderId);
			logger.info("查询下单发红包的红包个数结果：" + result.isSuccess());
			if(result.isSuccess()){
				resultMap.put("result", true);
				resultMap.put("errorCode", "0");
				resultMap.put("errorMsg", "成功");
				resultMap.put("total", result.getModels().get("total"));
			}else{
				resultMap.put("result", false);
				Set<String> keySet = result.getErrorMessages().keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					resultMap.put("errorCode", key);
					resultMap.put("errorMsg", result.getErrorMessages().get(key));
				}
			}
		}catch(Exception e){
			logger.error("web查询下单发红包的红包个数异常", e);
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return resultMap;
	}
	public Map<String,Object> findGiftMemcached(Long userId,String cardCode){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		try{
			Result result=mSalesServiceFacade.findGiftMemcached(userId,cardCode);
			if(result.isSuccess()){
				resultMap.put("result", true);
				GiftCardCode giftCardCodeVo=(GiftCardCode)result.getModels().get("giftCardCodeVo");
				resultMap.put("giftCardCodeVo",giftCardCodeVo);
			}else{
				resultMap.put("result", false);
				Set<String> keySet = result.getErrorMessages().keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					resultMap.put("errorCode", key);
					resultMap.put("errorMsg", result.getErrorMessages().get(key));
				}
			}
		}catch(Exception e){
			logger.error("根据优惠卷Id查询优惠代码异常", e);
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return resultMap;
	}
	//积分兑换优惠卷
	public Map<String,Object> activateConponsOrCardCode(CardInfoRequest request){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		try{
			Result result=mSalesServiceFacade.activateConponsOrCardCode(request);
			if(result.isSuccess()){
				resultMap.put("result", true);
			}else{
				resultMap.put("result", false);
				Set<String> keySet = result.getErrorMessages().keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					resultMap.put("errorCode", key);
					resultMap.put("errorMsg", result.getErrorMessages().get(key));
				}
			}
		}catch(Exception e){
			logger.error("优惠卷激活失败", e);
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return resultMap;
	}
	public Map<String,Object> integralChange(String userId, String userName,String cardCode){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		try{
			Result result=mSalesServiceFacade.integralChange(userId,userName,cardCode);
			if(result.isSuccess()){
				resultMap.put("result", true);
			}else{
				resultMap.put("result", false);
				Set<String> keySet = result.getErrorMessages().keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					resultMap.put("errorCode", key);
					resultMap.put("errorMsg", result.getErrorMessages().get(key));
				}
			}
		}catch(Exception e){
			logger.error("兑换优惠卷失败", e);
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return resultMap;
	}
	public Map<String,Object> getIntegralChangeList(Long userId,String page,String pageSize){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		try{
			Result result=mSalesServiceFacade.getIntegralChangeList(userId,page,pageSize);
			if(result.isSuccess()){
				resultMap.put("result", true);
			}else{
				resultMap.put("result", false);
				Set<String> keySet = result.getErrorMessages().keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					resultMap.put("errorCode", key);
					resultMap.put("errorMsg", result.getErrorMessages().get(key));
				}
			}
		}catch(Exception e){
			logger.error("查询积分兑换记录失败", e);
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return resultMap;
	}
}
