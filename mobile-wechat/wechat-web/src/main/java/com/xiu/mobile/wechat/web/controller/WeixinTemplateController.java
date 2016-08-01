package com.xiu.mobile.wechat.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.common.lang.StringUtil;
import com.xiu.mobile.wechat.web.model.BindingCfgVo;
import com.xiu.mobile.wechat.web.service.IBindingCfgService;
import com.xiu.mobile.wechat.web.utils.WeixinTemplateUtil;

/**
 * 
 * @author Tomman.qiu
 *
 */
@Controller
@RequestMapping("/weixinmsg")
public class WeixinTemplateController {
	private static final Logger logger = LoggerFactory.getLogger(WeixinTemplateController.class);

	@Resource
	private IBindingCfgService bindingCfgService;
	
	@ResponseBody
	@RequestMapping(value = "sendTemplate")
	public Map<String,String> sendTemplate(HttpServletRequest request, HttpServletResponse response){
		
		String url = request.getParameter("url");
		String unionId = request.getParameter("unionId");
		String title = request.getParameter("title");
		String activityName = request.getParameter("activityName");
		String activityTime = request.getParameter("activityTime");
		String remark = request.getParameter("remark");
		String templateId = request.getParameter("templateId");
		Map<String,String> result = new HashMap<String,String>();
		if(StringUtil.isBlank(url) || StringUtil.isBlank(unionId)
				||StringUtil.isBlank(title)||StringUtil.isBlank(activityName)
				||StringUtil.isBlank(activityTime)||StringUtil.isBlank(remark))
		{
			result.put("code", "500");
			result.put("errMsg", "url,unionId,title,activityName,activityTime,remark are need");
			logger.error("url,unionId,title,activityName,activityTime,remark are need");
			return result;
		}
		//通过unionId获取openId
		BindingCfgVo bindInfo = bindingCfgService.getBindingCfgByUnionId(unionId);
		if(bindInfo == null)
		{
			result.put("code", "500");
			result.put("errMsg", "bindInfo is null");
			logger.error("bindInfo is null");
			return result;
		}
		
		try {
			String sendResult = WeixinTemplateUtil.sendInfo(bindInfo.getOpenId(),title,activityName,activityTime,remark,url,templateId);
		    if(sendResult !=null)
		    {
		    	result.put("code", "500");
				result.put("errMsg", sendResult);
				logger.error("sendResult is : "+sendResult);
				
		    }
		    else
		    {
		    	result.put("code", "200");
				result.put("errMsg", sendResult);
				logger.error("sendResult is : "+sendResult);
		    }
		    return result;
		} catch (IOException e1) {
			result.put("code", "500");
			result.put("errMsg", e1.getMessage());
			logger.error(e1.getMessage());
			e1.printStackTrace();
			return result;
		}
	}
}
