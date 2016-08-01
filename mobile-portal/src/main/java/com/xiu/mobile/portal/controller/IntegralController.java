package com.xiu.mobile.portal.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.ei.EIMobileSalesManager;
import com.xiu.mobile.portal.ei.EIUUCManager;

/**
 * 积分
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/integral")
public class IntegralController extends BaseController{
	private Logger logger = Logger.getLogger(IntegralController.class);
	@Autowired
	private EIUUCManager eiuucManager;
	
	@Autowired
	private EIMobileSalesManager eiMobileSalesManager;
	
	/**
	 * 查询总积分
	 */
	@ResponseBody
	@RequestMapping(value = "/findIntegralTotal", produces = "text/html;charset=UTF-8")
	public String findIntegralTotal(HttpServletRequest request,String jsoncallback){
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		//获取用户ID
		String userIdStr=SessionUtil.getUserId(request);//用户ID
		
	//	String userIdStr="1000079668";
		Long userId=Long.valueOf(userIdStr);
		try{
			Map<String,Object> params=eiuucManager.getVirtualIntegralInfo(userId);
			Boolean isSuccess=(Boolean)params.get("status");
			if(isSuccess){
				resultMap.put("result", true);
				resultMap.put("totalIntegral", params.get("totalIntegral"));
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				resultMap.put("result", false);
				resultMap.put("errorCode","2001");
				resultMap.put("errorMsg","信息异常");
			}
		}catch(Exception e){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询我的积分总数异常：" + e.getMessage(),e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	/**
	 * 积分明细
	 */
	@ResponseBody
	@RequestMapping(value = "/findIntegralInfoList", produces = "text/html;charset=UTF-8")
	public String findIntegralInfoList(HttpServletRequest request,String jsoncallback,
			String currentPage,String pageSize){
		int currPage = 1;
		int pageSizes=15;
		try {
			currPage = Integer.parseInt(currentPage);
			pageSizes=Integer.parseInt(pageSize);
		} catch (NumberFormatException e1) {
			logger.info("查询用户积分明细列表时页码参数错误，故使用默认第一页：" + e1.getMessage(), e1);
		}
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		//获取用户ID
		String userIdStr=SessionUtil.getUserId(request);//用户ID
	//	String userIdStr="2012464294";
		Long userId=Long.valueOf(userIdStr);
		try{
			Map<String,Object> params=eiuucManager.findIntegralInfoList(userId,currPage,pageSizes);
			Boolean isSuccess=(Boolean)params.get("status");
			if(isSuccess){
				resultMap.put("result", true);
				resultMap.put("page", params.get("page"));
				resultMap.put("listIntegra", params.get("listIntegra"));
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				resultMap.put("result", false);
				resultMap.put("errorCode",params.get("errorCode"));
				resultMap.put("errorMsg",params.get("errorMsg"));
			}
		}catch(Exception e){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询我的积分明细异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	/**
	 * 积分商城兑换
	 */
	@ResponseBody
	@RequestMapping(value = "/integralChange", produces = "text/html;charset=UTF-8")
	public String integralChange(HttpServletRequest request,String jsoncallback){
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		String userId=SessionUtil.getUserId(request);
	//	String userId="1000079668";
	//	String userName="zhuzhu";
		String userName=SessionUtil.getUserName(request);
		String cardCode=request.getParameter("cardCode");//优惠卷ID
		if(StringUtils.isBlank(cardCode)){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		try{
			Map<String,Object> result=eiMobileSalesManager.integralChange(userId, userName, cardCode);
			Boolean isSuccess=(Boolean)result.get("result");
			if(isSuccess){
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				resultMap.put("result", false);
				resultMap.put("errorCode",result.get("errorCode"));
				resultMap.put("errorMsg",result.get("errorMsg"));
			}
		}catch(Exception e){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询积分兑奖商品（优惠卷）异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	/**
	 * 积分兑换记录
	 */
	@ResponseBody
	@RequestMapping(value = "/getIntegralChangeList", produces = "text/html;charset=UTF-8")
	public String getIntegralChangeList(HttpServletRequest request,String jsoncallback,
			String page,String pageSize){
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		String userId=SessionUtil.getUserId(request);
	//	String userId="2012465815";
		if(page==null){
			page="1";
		}
		if(pageSize==null){
			pageSize="20";
		}
		Long userIdStr=Long.valueOf(userId);
		try{
			Map<String,Object> result=eiMobileSalesManager.getIntegralChangeList(userIdStr,page, pageSize);
			Boolean isSuccess=(Boolean)result.get("result");
			if(isSuccess){
				resultMap.put("result", true);
				resultMap.put("listIntegra", result.get("listIntegra"));
				resultMap.put("totalCount", result.get("totalCount"));
				resultMap.put("totalPage", result.get("totalPage"));
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				resultMap.put("result", false);
				resultMap.put("errorCode",result.get("errorCode"));
				resultMap.put("errorMsg",result.get("errorMsg"));
			}
		}catch(Exception e){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询积分兑换记录异常：" + e.getMessage(),e);
			e.printStackTrace();
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
}
