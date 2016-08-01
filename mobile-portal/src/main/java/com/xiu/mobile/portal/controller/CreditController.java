package com.xiu.mobile.portal.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.service.ICreditService;
import com.xiu.show.core.common.util.ObjectUtil;

/**
 * 积分签到
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/credit")
public class CreditController extends BaseController{
	private Logger logger=Logger.getLogger(CreditController.class);

	@Autowired
	
	private ICreditService creditService;
	
	
	
	/**
	 * 用户签到
	 */
	@ResponseBody
	@RequestMapping(value="/userCreditCenter",produces="text/html;charset=UTF-8")
	public String userCreditCenter(HttpServletRequest request,String jsoncallback){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		
		try{
			String xiuUserId =SessionUtil.getUserId(request);
			logger.info("xiuUserId签到用户信息:"+xiuUserId);
			if(StringUtils.isBlank(xiuUserId)){
				resultMap.put("result", false);
				resultMap.put("errorCode", "-1");
				resultMap.put("errorMsg", "提交参数不全");
			}else{
				resultMap=creditService.userCreditCenter(Long.valueOf(xiuUserId));
			}
			
		}catch(Exception e){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("签到发生异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	/**
	 * 积分补签
	 */
	@ResponseBody
	@RequestMapping(value="/doCredit",produces="text/html;charset=UTF-8")
	public String doCredit(HttpServletRequest request,String jsoncallback,
			String creditOldStatus){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		String xiuUserId = SessionUtil.getUserId(request);
		logger.info("积分补签,传入参数:xiuUserId:"+xiuUserId+",creditOldStatus:"+creditOldStatus);
		try{
			if(StringUtils.isBlank(creditOldStatus) || StringUtils.isBlank(xiuUserId)){
				resultMap.put("result", false);
				resultMap.put("errorCode", "-1");
				resultMap.put("errorMsg", "提交参数不全");
			}else{
				resultMap=creditService.doCredit(Long.parseLong(xiuUserId),creditOldStatus);
			}
		}catch(Exception e){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("积分补签异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
}
