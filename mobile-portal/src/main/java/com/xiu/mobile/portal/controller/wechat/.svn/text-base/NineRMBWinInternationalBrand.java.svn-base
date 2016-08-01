package com.xiu.mobile.portal.controller.wechat;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.mobile.core.exception.EIBusinessException;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.controller.BaseController;
import com.xiu.mobile.portal.ei.EIMobileSalesManager;
import com.xiu.mobile.portal.service.IUserService;
import com.xiu.mobile.sales.dointerface.vo.WeiXinActivityResponse;
import com.xiu.mobile.sales.dointerface.vo.WeiXinSupportResponse;
import com.xiu.uuc.facade.dto.UserBaseDTO;

@Controller
@RequestMapping("/winInternationalBrand")
public class NineRMBWinInternationalBrand extends BaseController {

	private final static Logger logger = Logger.getLogger(NineRMBWinInternationalBrand.class);
	
	@Autowired
	EIMobileSalesManager manager;
	
	@Autowired
	private IUserService userService;
	
	/**
	 * 获取活动信息
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getActivitySupportInfo", produces = "text/html;charset=UTF-8")
	public String getWeiXinActivitySupportInfo(HttpServletRequest request,
			HttpServletResponse response, String jsoncallback){
		
		logger.info("获取9元赢大牌活动相关参数：params="+request.getQueryString());
		
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		// 获取传递相关参数
		String activityId = request.getParameter("activityId"); // 微信活动ID
		String unionId = request.getParameter("unionId"); // 微信用户unionId
		String spreadUnionId = request.getParameter("spreadUnionId"); // 微信传播者
		String code = request.getParameter("code"); // 防止篡改签名
		String nickName = ""; // 微信用户微信昵称
		try {
			nickName = URLDecoder.decode(request.getParameter("nickName"), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			logger.error("解密nickname异常，nickname: " + request.getParameter("nickName"), e1);
		} 
		String userPhoneUrl = request.getParameter("userPhoneUrl"); // 微信用户图片地址
		

		if(StringUtils.isBlank(activityId) 
				|| StringUtils.isBlank(unionId) || StringUtils.isBlank(code)){
			result.put("result", false);
			result.put("errorCode", "-1");
			result.put("errorMsg", "参数不能为空，请检查传递的参数");
			return JsonUtils.bean2jsonP(jsoncallback, result);
		}
		Long xiuUserId = Long.parseLong(SessionUtil.getUserId(request)); //走秀用户id
		UserBaseDTO userBaseDTO = userService.getUserBasicInfoByUserId(xiuUserId);
		if(userBaseDTO == null || StringUtils.isBlank(userBaseDTO.getLogonName())){
			result.put("result", false);
			result.put("errorCode", "-1");
			result.put("errorMsg", "根据user_id获取不到用户信息");
			return JsonUtils.bean2jsonP(jsoncallback, result);
		}
		String xiuUserName = userBaseDTO.getLogonName(); //走秀用户名
		
		try{
			WeiXinActivityResponse weiXinActivityResponse = manager.getWeiXinActivitySupportInfo(activityId,
					unionId, spreadUnionId, code, xiuUserId, xiuUserName, nickName, userPhoneUrl, true, null, null, null);
			result.put("result", true);
			result.put("errorCode", "0");
			result.put("errorMsg", "成功");
			result.put("weiXinActivityResponse", weiXinActivityResponse);
			
			logger.info("获取9元赢大牌活动返回结果：" + weiXinActivityResponse);
			
		}catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", e.getExtErrCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("获取9元赢大牌活动信息发生异常：", e);
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", "-1");
			result.put("errorMsg", "系统异常");
			logger.error("获取9元赢大牌活动信息发生异常：", e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/**
	 * 获取支付链接
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/supportSendReward", produces = "text/html;charset=UTF-8")
	public String supportSendReward(HttpServletRequest request,
			HttpServletResponse response, String jsoncallback){
		
		logger.info("获取9元赢大牌活动支付链接参数：params="+request.getQueryString());
		
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		String activityId = request.getParameter("activityId"); // 微信活动ID
		String unionId = request.getParameter("unionId"); // 微信用户unionId
		String code = request.getParameter("code"); // 防止篡改签名
		String userPhoneUrl = request.getParameter("userPhoneUrl"); // 微信用户图片地址
		String spreadUnionId = request.getParameter("spreadUnionId"); // 传播者unionid
		String nickName = ""; // 微信用户微信昵称
		try {
			nickName = URLDecoder.decode(request.getParameter("nickName"), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			logger.error("解密nickname异常，nickname: " + request.getParameter("nickName"), e1);
		} 		
		String xiuUserId = SessionUtil.getUserId(request); //走秀用户id
		UserBaseDTO userBaseDTO = userService.getUserBasicInfoByUserId(Long.parseLong(xiuUserId));
		if(userBaseDTO == null || StringUtils.isBlank(userBaseDTO.getLogonName())){
			result.put("result", false);
			result.put("errorCode", "-1");
			result.put("errorMsg", "根据user_id获取不到用户信息");
			return JsonUtils.bean2jsonP(jsoncallback, result);
		}
		String xiuUserName = userBaseDTO.getLogonName(); //走秀用户名
		
		if(StringUtils.isBlank(unionId) 
				|| StringUtils.isBlank(nickName) || StringUtils.isBlank(userPhoneUrl)){
			result.put("result", false);
			result.put("errorCode", "-1");
			result.put("errorMsg", "参数不能为空，请检查传递的参数");
			return JsonUtils.bean2jsonP(jsoncallback, result);
		}
		
		result = manager.supportSendReward(activityId,
					unionId, code, nickName, unionId, "这个参数对这个活动无效", userPhoneUrl, xiuUserName, xiuUserId, spreadUnionId, true, null, null, null);
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
}
