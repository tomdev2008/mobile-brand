package com.xiu.mobile.wechat.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.mobile.wechat.core.utils.WeixinSignUtil;
import com.xiu.mobile.wechat.facade.utils.JSONUtil;

/**
 * 
 * @author Tomman.qiu
 *
 */
@Controller
@RequestMapping("/weixinsign")
public class WeixinSignController {
	private static final Logger logger = LoggerFactory.getLogger(WeixinSignController.class);

	@ResponseBody
	@RequestMapping(value = "sign", produces = "text/html;charset=UTF-8")
	public String sign(HttpServletRequest request, HttpServletResponse response){
		
		String url = request.getParameter("url");
		String jsoncallback = request.getParameter("jsoncallback");
		if(jsoncallback == null)
		{
			jsoncallback="jsoncallback";
		}
		Map<String,String> result = new HashMap<String,String>();
		if(url == null || "".equals(url.trim()))
		{
			result.put("code", "500");
			result.put("errMsg", "url is need");
			logger.error("url is null");
			return bean2jsonP(jsoncallback,result);
		}
		
		try {
			result = WeixinSignUtil.signByUrl(url);
			result.put("code", "200");
		} catch (IOException e) {
			result.put("code", "500");
			result.put("errMsg", e.getMessage());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return bean2jsonP(jsoncallback,result);
	}
	
	/**
	 * jsonP跨域
	 * @param callback
	 * @param bean
	 * @return
	 */
	public String bean2jsonP(String callback, Object bean) {
		String str =JSONObject.fromObject(bean).toString();
		StringBuffer sb = new StringBuffer(str);
		if(callback != null) {
			sb.insert(0, callback + "(");
			sb.append(")");
		}
		return sb.toString();
	}

}
