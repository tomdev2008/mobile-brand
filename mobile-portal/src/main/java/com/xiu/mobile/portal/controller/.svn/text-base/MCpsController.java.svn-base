package com.xiu.mobile.portal.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.mobile.core.exception.EIBusinessException;
import com.xiu.mobile.cps.dointerface.query.CommissionsOrderQueryParamOut;
import com.xiu.mobile.cps.dointerface.vo.UserCommissionsAmountVo;
import com.xiu.mobile.cps.dointerface.vo.UserCommissionsOrderDetail;
import com.xiu.mobile.cps.dointerface.vo.UserCommissionsOrderInfo;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.model.LoginResVo;
import com.xiu.mobile.portal.service.ICpsCommissonManager;
import com.xiu.mobile.portal.service.ILoginService;

/**
 * <p>
 * ************************************************************** 
 * @Description: cps控制器
 * @AUTHOR hejianxiong
 * @DATE 2014-7-8 11:10:25
 * ***************************************************************
 * </p>
 */
@Controller
@RequestMapping("/cpsCommisson")
public class MCpsController extends BaseController 
{
	private final Logger logger = Logger.getLogger(MCpsController.class);
	
	@Autowired
	private ILoginService loginService;
	@Autowired
	private ICpsCommissonManager cpsCommissonManager;
	
	/***
	 * 获取用户返佣金额信息
	 * @param jsoncallback
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/cpsAmount", produces = "text/html;charset=UTF-8")
	public String getCpsAmount(String jsoncallback, HttpServletRequest request) {
		LoginResVo user = SessionUtil.getUser(request);
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			// 查询用户返佣金额信息
			UserCommissionsAmountVo commissionsAmountVo = cpsCommissonManager.queryUserCommissionsAmount(Long.parseLong(user.getUserId()));
			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			result.put("totalCommissionsAmount", commissionsAmountVo.getTotalCommissionsAmount());
			result.put("pastCommissionsAmount", commissionsAmountVo.getPastCommissionsAmount());
			result.put("futureCommissionsAmount", commissionsAmountVo.getFutureCommissionsAmount());
		}catch(EIBusinessException e){
			result.put("result", false);
			result.put("errorCode", e.getExtErrCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("用户返佣金额信息查询异常：exception" + e.getMessageWithSupportCode(),e);
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("用户返佣金额信息查询异常：exception",e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/***
	 * 获取用户返佣订单信息
	 * @param jsoncallback
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/cpsOrderList", produces = "text/html;charset=UTF-8")
	public String getCpsOrder(String jsoncallback, HttpServletRequest request) {
		LoginResVo user = SessionUtil.getUser(request);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 封装相关查询参数
			Map<String, String> params = new HashMap<String, String>();
			params.put("userId", user.getUserId());
			params.put("commissionsStatus", request.getParameter("commissionsStatus"));
			params.put("minOrderTime", request.getParameter("minOrderTime"));
			params.put("maxOrderTime", request.getParameter("maxOrderTime"));
			params.put("pageNo", StringUtils.isNotBlank(request.getParameter("pageNo"))?request.getParameter("pageNo"):"1");
			params.put("entrisPerPage", StringUtils.isNotBlank(request.getParameter("entrisPerPage"))?request.getParameter("entrisPerPage"):"10");
			
			CommissionsOrderQueryParamOut paramOut = cpsCommissonManager.queryUserCommissionsOrderList(params);
			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			result.put("orderInfoList", paramOut.getOrderInfos());
		}catch(EIBusinessException e){
			result.put("result", false);
			result.put("errorCode", e.getExtErrCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("获取用户返佣订单信息异常：exception" + e.getMessageWithSupportCode(),e);
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取用户返佣订单信息异常：exception",e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/***
	 * 获取用户返佣订单详细信息
	 * @param jsoncallback
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/cpsOrderGoodsList", produces = "text/html;charset=UTF-8")
	public String getCpsOrderDetail(String jsoncallback, HttpServletRequest request) {
		LoginResVo user = SessionUtil.getUser(request);
		Map<String, Object> result = new HashMap<String, Object>();
		String orderCode = request.getParameter("orderCode"); //订单号 
		try {
			// 查询用户返佣订单信息
			UserCommissionsOrderInfo userCommissionsOrderInfo = cpsCommissonManager.queryUserCommissionsOrderInfo(Long.parseLong(orderCode));
			// 查询用户返佣金额信息
			List<UserCommissionsOrderDetail> commissionsOrderDetailList = cpsCommissonManager.queryUserCommissionsOrderDetail(Long.parseLong(user.getUserId()),Long.parseLong(orderCode));
			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			result.put("userCommissionsOrderInfo", userCommissionsOrderInfo);
			result.put("orderGoodsList", commissionsOrderDetailList);
		}catch(EIBusinessException e){
			result.put("result", false);
			result.put("errorCode", e.getExtErrCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("获取用户返佣订单详细信息异常：exception" + e.getMessageWithSupportCode(),e);
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.info("获取用户返佣订单详细信息异常：exception=",e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	

}
