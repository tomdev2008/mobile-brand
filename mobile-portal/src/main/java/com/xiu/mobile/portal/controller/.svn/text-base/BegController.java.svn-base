package com.xiu.mobile.portal.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.mobile.core.exception.EIBusinessException;
import com.xiu.mobile.portal.common.constants.BegDescConstant;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.service.IBegSerivce;
import com.xiu.mobile.sales.dointerface.activity.vo.WeiXinActivityJoinResponse;
import com.xiu.mobile.sales.dointerface.activity.vo.WeiXinActivityVirtualPayResponse;
import com.xiu.mobile.sales.dointerface.vo.PageView;
import com.xiu.mobile.sales.dointerface.vo.WeiXinActivityResponse;
import com.xiu.mobile.sales.dointerface.vo.WeiXinSpreadResponse;
import com.xiu.mobile.sales.dointerface.vo.WeiXinSpreadRewardResult;
import com.xiu.mobile.sales.dointerface.vo.WeiXinSupportResponse;

/**
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/beg")
public class BegController extends BaseController{
	private final static Logger logger = Logger.getLogger(BegController.class);
	
	@Autowired
	private IBegSerivce begSerivce;
	
	/***
	 * 进入微信活动页获取活动传播者讨赏和针对该传播者的支持者信息
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getSupportInfo", produces = "text/html;charset=UTF-8")
	public String getSupportInfo(HttpServletRequest request, HttpServletResponse response, String jsoncallback) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		try {
			logger.info("进入微信活动页获取活动传播者的支持者信息相关参数：params="+request.getQueryString());
			// 获取传递相关参数
			String activityId = request.getParameter("activityId"); // 微信活动ID
			String unionId = request.getParameter("unionId"); // 微信用户unionId
			String code = request.getParameter("code"); // 防止篡改签名
			String spreadUnionId = request.getParameter("spreadUnionId");
			if (StringUtils.isBlank(activityId) || StringUtils.isBlank(unionId) || StringUtils.isBlank(code)) {
				result.put("result", false);
				result.put("errorCode", BegDescConstant.INPUT_PARAMES_IS_NULL);
				result.put("errorMsg", BegDescConstant.getMessageByCode(BegDescConstant.INPUT_PARAMES_IS_NULL));
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			// 查询微信活动传播支持者信息
			WeiXinActivityResponse weiXinActivityResponse = begSerivce.getWeiXinActivitySpreadAndSupportInfo(activityId, unionId, code, spreadUnionId);
			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			result.put("weiXinActivityResponse", weiXinActivityResponse);
		} catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", e.getExtErrCode());
			String errorCode = e.getExtErrCode();
			String message = e.getExtMessage();
			Map<String, String> begDescMap = BegDescConstant.getDescList();
			// 如果讨赏奖励描述信息包含key
			if (StringUtils.isNotBlank(errorCode) && begDescMap.containsKey(errorCode)) {
				message = begDescMap.get(errorCode);
			}
			result.put("errorMsg", message);
			logger.error("入微信活动页获取活动传播者的支持者信息发生异常：", e);
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", BegDescConstant.SYSTEM_INNER_ERROR);
			result.put("errorMsg", BegDescConstant.getMessageByCode(BegDescConstant.SYSTEM_INNER_ERROR));
			logger.error("入微信活动页获取活动传播者的支持者信息发生异常：" + e.getMessage());
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/***
	 * 微信活动传播者获取礼品
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/gainGift", produces = "text/html;charset=UTF-8")
	public String gainGift(HttpServletRequest request, HttpServletResponse response, String jsoncallback) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		try {
			logger.info("微信活动传播者获取礼品相关参数：params="+request.getQueryString());
			// 获取传递相关参数
			String activityId = request.getParameter("activityId"); // 微信活动ID
			String unionId = request.getParameter("unionId"); // 微信用户unionId
			String supportUnionId = request.getParameter("supportUnionId"); // 微信支持者UnionId
			String code = request.getParameter("code"); // 防止篡改签名
			
			if (StringUtils.isBlank(activityId) || StringUtils.isBlank(unionId) || StringUtils.isBlank(code)) {
				result.put("result", false);
				result.put("errorCode", BegDescConstant.INPUT_PARAMES_IS_NULL);
				result.put("errorMsg", BegDescConstant.getMessageByCode(BegDescConstant.INPUT_PARAMES_IS_NULL));
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			boolean status = false;
			// 如果支持者unitId不为空 则调用支持者获取礼品接口
			if (StringUtils.isNotBlank(supportUnionId)) {
				// 微信活动传播支持者打赏成功后获取礼品
				status = begSerivce.supportUserGiveGift(activityId, unionId, code, supportUnionId);
			}else{
				// 微信活动传播者(讨满一定金额)后获取礼品
				status = begSerivce.spreadUserGiveGift(activityId, unionId, code);
			}
			
			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			result.put("status", status);
		} catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", e.getExtErrCode());
			String errorCode = e.getExtErrCode();
			String message = e.getExtMessage();
			Map<String, String> begDescMap = BegDescConstant.getDescList();
			// 如果讨赏奖励描述信息包含key
			if (StringUtils.isNotBlank(errorCode) && begDescMap.containsKey(errorCode)) {
				message = begDescMap.get(errorCode);
			}
			result.put("errorMsg", message);
			logger.error("微信活动传播者获取礼品发生异常：", e);
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", BegDescConstant.SYSTEM_INNER_ERROR);
			result.put("errorMsg", BegDescConstant.getMessageByCode(BegDescConstant.SYSTEM_INNER_ERROR));
			logger.error("微信活动传播者获取礼品发生异常：" + e.getMessage());
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/***
	 * 活动传播者发出讨赏接口
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sendBeg", produces = "text/html;charset=UTF-8")
	public String sendBeg(HttpServletRequest request, HttpServletResponse response, String jsoncallback) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		try {
			logger.info("活动传播者发出讨赏接口相关参数：params="+request.getQueryString());
			// 获取传递相关参数
			String activityId = request.getParameter("activityId"); // 微信活动ID
			String unionId = request.getParameter("unionId"); // 微信用户unionId
			String code = request.getParameter("code"); // 防止篡改签名
			String nickName = request.getParameter("nickName"); // 微信传播者微信昵称
			String userPhoneUrl = request.getParameter("userPhoneUrl"); // 传播者图片地址
			String xiuUserName = request.getParameter("xiuUserName"); // 传播者走秀账号
			String xiuUserId = request.getParameter("xiuUserId");
			if (StringUtils.isBlank(activityId) || StringUtils.isBlank(unionId) || StringUtils.isBlank(code) || StringUtils.isBlank(nickName)
					|| StringUtils.isBlank(userPhoneUrl) || StringUtils.isBlank(xiuUserName) || StringUtils.isBlank(xiuUserId)) {
				result.put("result", false);
				result.put("errorCode", BegDescConstant.INPUT_PARAMES_IS_NULL);
				result.put("errorMsg", BegDescConstant.getMessageByCode(BegDescConstant.INPUT_PARAMES_IS_NULL));
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			// 活动传播者发出讨赏
			WeiXinSpreadResponse weiXinSpreadResponse = begSerivce.spreadSendReward(activityId, unionId, code, nickName, userPhoneUrl, xiuUserName, xiuUserId);
			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			result.put("weiXinSpreadResponse", weiXinSpreadResponse);
		} catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", e.getExtErrCode());
			String errorCode = e.getExtErrCode();
			String message = e.getExtMessage();
			Map<String, String> begDescMap = BegDescConstant.getDescList();
			// 如果讨赏奖励描述信息包含key
			if (StringUtils.isNotBlank(errorCode) && begDescMap.containsKey(errorCode)) {
				message = begDescMap.get(errorCode);
			}
			result.put("errorMsg", message);
			logger.error("活动传播者发出讨赏发生异常：", e);
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", BegDescConstant.SYSTEM_INNER_ERROR);
			result.put("errorMsg", BegDescConstant.getMessageByCode(BegDescConstant.SYSTEM_INNER_ERROR));
			logger.error("活动传播者发出讨赏发生异常：" + e.getMessage());
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/***
	 * 支持者为微信传播者打赏接口
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/respondBeg", produces = "text/html;charset=UTF-8")
	public String respondBeg(HttpServletRequest request, HttpServletResponse response, String jsoncallback) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		logger.info("支持者为微信传播者打赏接口相关参数：params="+request.getQueryString());
		// 获取传递相关参数
		String activityId = request.getParameter("activityId"); // 微信活动ID
		String unionId = request.getParameter("unionId"); // 微信用户unionId
		String code = request.getParameter("code"); // 防止篡改签名
		String nickName = request.getParameter("nickName"); // 微信传播者微信昵称
		String supportUnionId = request.getParameter("supportUnionId"); // 微信支持者UnionId
		String supportSay = request.getParameter("supportSay"); //支持者对传播者打赏是发表的说说
		String supportPhoneUrl = request.getParameter("supportPhoneUrl"); // 支持者微信图像Url
		String xiuUserName = request.getParameter("xiuUserName"); // 支持者走秀账号
		String xiuUserId = request.getParameter("xiuUserId"); // 支持者走秀用户ID
		if (StringUtils.isBlank(activityId) || StringUtils.isBlank(unionId) || StringUtils.isBlank(code) || StringUtils.isBlank(nickName)
				|| StringUtils.isBlank(supportUnionId) || StringUtils.isBlank(supportSay) || StringUtils.isBlank(supportPhoneUrl)
				|| StringUtils.isBlank(xiuUserName) || StringUtils.isBlank(xiuUserId)) {
			result.put("result", false);
			result.put("errorCode", BegDescConstant.INPUT_PARAMES_IS_NULL);
			result.put("errorMsg", BegDescConstant.getMessageByCode(BegDescConstant.INPUT_PARAMES_IS_NULL));
			return JsonUtils.bean2jsonP(jsoncallback, result);
		}
		
		// 打赏微信传播者
		result = begSerivce.supportSendReward(activityId, unionId, code, nickName, supportUnionId, supportSay, supportPhoneUrl, xiuUserName, xiuUserId);
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/***
	 * 微信活动传播获奖名单
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getGiftInfoList", produces = "text/html;charset=UTF-8")
	public String getGiftInfoList(HttpServletRequest request, HttpServletResponse response, String jsoncallback) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		try {
			logger.info("微信活动传播获奖名单接口相关参数：params="+request.getQueryString());
			// 获取传递相关参数
			String activityId = request.getParameter("activityId"); // 微信活动ID
			String activityType = request.getParameter("activityType"); // 微信活动类型
			int currentPage = NumberUtils.toInt(request.getParameter("currentPage"), 1); // 当前页(默认为1)
			int pageSize = NumberUtils.toInt(request.getParameter("pageSize"), 30); // 每页显示记录数(默认为30)
			
			// 微信活动传播获奖名单
			PageView<WeiXinSpreadRewardResult> pageView = begSerivce.queryWeiXinSpreadRewardResult(activityId, activityType, currentPage, pageSize);
			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			result.put("pageView", pageView);
		} catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", e.getExtErrCode());
			String errorCode = e.getExtErrCode();
			String message = e.getExtMessage();
			Map<String, String> begDescMap = BegDescConstant.getDescList();
			// 如果讨赏奖励描述信息包含key
			if (StringUtils.isNotBlank(errorCode) && begDescMap.containsKey(errorCode)) {
				message = begDescMap.get(errorCode);
			}
			result.put("errorMsg", message);
			logger.error("微信活动传播获奖名单信息发生异常：", e);
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", BegDescConstant.SYSTEM_INNER_ERROR);
			result.put("errorMsg", BegDescConstant.getMessageByCode(BegDescConstant.SYSTEM_INNER_ERROR));
			logger.error("微信活动传播获奖名单信息发生异常：" + e.getMessage());
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/***
	 * 进入微信活动页获取活动传播者讨赏和针对该传播者的支持者信息 新接口
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getSpreaderAndSupporterInfo", produces = "text/html;charset=UTF-8")
	public String getSpreaderAndSupporterInfo(HttpServletRequest request, HttpServletResponse response, String jsoncallback) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		try {
			logger.info("进入微信活动页获取活动传播者的支持者信息相关参数：params="+request.getQueryString());
			// 获取传递相关参数
			String activityId = request.getParameter("activityId"); // 微信活动ID
			String unionId = request.getParameter("unionId"); // 微信用户unionId
			String code = request.getParameter("code"); // 防止篡改签名
			String spreadUnionId = request.getParameter("spreadUnionId");	//微信传播者UnionId
			String nickName = request.getParameter("nickName");		//微信支持者微信昵称
			String supportSay = request.getParameter("supportSay");	//支持者对传播者打赏是发表的说说
			String supportPhoneUrl = request.getParameter("supportPhoneUrl");	//支持者微信图像Url
			String xiuUserName = request.getParameter("xiuUserName");	//支持者走秀账号
			String xiuUserId = request.getParameter("xiuUserId");		//支持者走秀用户ID
			if (StringUtils.isBlank(activityId) || StringUtils.isBlank(unionId) || StringUtils.isBlank(code) || StringUtils.isBlank(nickName) 
					|| StringUtils.isBlank(supportSay) || StringUtils.isBlank(supportPhoneUrl) || StringUtils.isBlank(xiuUserName) || StringUtils.isBlank(xiuUserId)) {
				result.put("result", false);
				result.put("errorCode", BegDescConstant.INPUT_PARAMES_IS_NULL);
				result.put("errorMsg", BegDescConstant.getMessageByCode(BegDescConstant.INPUT_PARAMES_IS_NULL));
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			// 查询微信活动传播支持者信息
			//WeiXinActivityVirtualPayResponse activityVirtualPayResponse = begSerivce.weiXinActivityVirtaulPayHomePage(activityId, unionId, code, spreadUnionId, nickName, supportSay, supportPhoneUrl, xiuUserName, xiuUserId);
			Map resultMap = begSerivce.weiXinActivityVirtaulPayHomePageNew(activityId, unionId, code, spreadUnionId, nickName, supportSay, supportPhoneUrl, xiuUserName, xiuUserId);
			WeiXinActivityVirtualPayResponse activityVirtualPayResponse = null;
			String errorCode = "";
			String errorMsg = "";
			
			Object responseObj = resultMap.get("weiXinActivityResponse");
			if(responseObj != null && !responseObj.toString().equals("null")) {
				activityVirtualPayResponse = (WeiXinActivityVirtualPayResponse) responseObj;
			}
			Object resultCodeObj = resultMap.get("resultCode");
			if(resultCodeObj != null && !resultCodeObj.toString().equals("null")) {
				errorCode = resultCodeObj.toString();
				Map tmpMap = (Map) resultMap.get("errorMessages");
				if(tmpMap != null) {
					errorMsg = (String) tmpMap.get(errorCode);
				}
				Map<String, String> begDescMap = BegDescConstant.getDescList();
				if (StringUtils.isNotBlank(errorCode) && begDescMap.containsKey(errorCode)) {
					errorMsg = begDescMap.get(errorCode);
				}
			}
			result.put("result", true);
			result.put("errorCode", errorCode);
			result.put("errorMsg", errorMsg);
			result.put("activityVirtualPayResponse", activityVirtualPayResponse);
		} catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", e.getExtErrCode());
			String errorCode = e.getExtErrCode();
			String message = e.getExtMessage();
			Map<String, String> begDescMap = BegDescConstant.getDescList();
			if (StringUtils.isNotBlank(errorCode) && begDescMap.containsKey(errorCode)) {
				message = begDescMap.get(errorCode);
			}
			result.put("errorMsg", message);
			logger.error("进入微信活动页获取活动传播者的支持者信息发生异常：", e);
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", BegDescConstant.SYSTEM_INNER_ERROR);
			result.put("errorMsg", BegDescConstant.getMessageByCode(BegDescConstant.SYSTEM_INNER_ERROR));
			logger.error("进入微信活动页获取活动传播者的支持者信息发生异常：" + e.getMessage());
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/***
	 * 活动传播者发出讨赏接口
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/spreaderSendReward", produces = "text/html;charset=UTF-8")
	public String spreaderSendReward(HttpServletRequest request, HttpServletResponse response, String jsoncallback) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		try {
			logger.info("活动传播者发出讨赏接口相关参数：params="+request.getQueryString());
			// 获取传递相关参数
			String activityId = request.getParameter("activityId"); // 微信活动ID
			String unionId = request.getParameter("unionId"); // 微信用户unionId
			String code = request.getParameter("code"); // 防止篡改签名
			String nickName = request.getParameter("nickName"); // 微信传播者微信昵称
			String userPhoneUrl = request.getParameter("userPhoneUrl"); // 传播者图片地址
			String xiuUserName = request.getParameter("xiuUserName"); // 传播者走秀账号
			String xiuUserId = request.getParameter("xiuUserId");
			if (StringUtils.isBlank(activityId) || StringUtils.isBlank(unionId) || StringUtils.isBlank(code) || StringUtils.isBlank(nickName)
					|| StringUtils.isBlank(userPhoneUrl) || StringUtils.isBlank(xiuUserName) || StringUtils.isBlank(xiuUserId)) {
				result.put("result", false);
				result.put("errorCode", BegDescConstant.INPUT_PARAMES_IS_NULL);
				result.put("errorMsg", BegDescConstant.getMessageByCode(BegDescConstant.INPUT_PARAMES_IS_NULL));
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			// 活动传播者发出讨赏
			WeiXinSpreadResponse weiXinSpreadResponse = begSerivce.spreadSendReward(activityId, unionId, code, nickName, userPhoneUrl, xiuUserName, xiuUserId);
			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			result.put("weiXinSpreadResponse", weiXinSpreadResponse);
		} catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", e.getExtErrCode());
			String errorCode = e.getExtErrCode();
			String message = e.getExtMessage();
			Map<String, String> begDescMap = BegDescConstant.getDescList();
			// 如果讨赏奖励描述信息包含key
			if (StringUtils.isNotBlank(errorCode) && begDescMap.containsKey(errorCode)) {
				message = begDescMap.get(errorCode);
			}
			result.put("errorMsg", message);
			logger.error("活动传播者发出讨赏发生异常：", e);
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", BegDescConstant.SYSTEM_INNER_ERROR);
			result.put("errorMsg", BegDescConstant.getMessageByCode(BegDescConstant.SYSTEM_INNER_ERROR));
			logger.error("活动传播者发出讨赏发生异常：" + e.getMessage());
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/***
	 * 获取微信获取参与人数,与送出的年终奖信息
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getWeiXinJoinInfo", produces = "text/html;charset=UTF-8")
	public String getWeiXinJoinInfo(HttpServletRequest request, HttpServletResponse response, String jsoncallback) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		try {
			logger.info("获取微信获取参与人数,与送出的年终奖信息相关参数：params="+request.getQueryString());
			// 获取传递相关参数
			String activityId = request.getParameter("activityId"); // 微信活动ID
			if (StringUtils.isBlank(activityId)) {
				result.put("result", false);
				result.put("errorCode", BegDescConstant.INPUT_PARAMES_IS_NULL);
				result.put("errorMsg", BegDescConstant.getMessageByCode(BegDescConstant.INPUT_PARAMES_IS_NULL));
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			//获取微信活动参与信息
			WeiXinActivityJoinResponse weiXinJoinResponse = begSerivce.queryWeiXinJoinInfo(activityId);
			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			result.put("weiXinJoinResponse", weiXinJoinResponse);
		} catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", e.getExtErrCode());
			String errorCode = e.getExtErrCode();
			String message = e.getExtMessage();
			Map<String, String> begDescMap = BegDescConstant.getDescList();
			if (StringUtils.isNotBlank(errorCode) && begDescMap.containsKey(errorCode)) {
				message = begDescMap.get(errorCode);
			}
			result.put("errorMsg", message);
			logger.error("获取微信获取参与人数,与送出的年终奖信息发生异常：", e);
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", BegDescConstant.SYSTEM_INNER_ERROR);
			result.put("errorMsg", BegDescConstant.getMessageByCode(BegDescConstant.SYSTEM_INNER_ERROR));
			logger.error("获取微信获取参与人数,与送出的年终奖信息发生异常：" + e.getMessage());
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
}
