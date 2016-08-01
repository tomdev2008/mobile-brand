package com.xiu.mobile.portal.controller;

import java.util.HashMap;
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

import com.alibaba.fastjson.JSON;
import com.xiu.commerce.hessian.model.Product;
import com.xiu.mobile.core.exception.EIBusinessException;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.constants.GoodsConstant;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.EncryptUtils;
import com.xiu.mobile.portal.common.util.ImageServiceConvertor;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.common.util.Tools;
import com.xiu.mobile.portal.ei.EIShortUrlManager;
import com.xiu.mobile.portal.model.LoginResVo;
import com.xiu.mobile.portal.model.PayReqVO;
import com.xiu.mobile.portal.model.Topic;
import com.xiu.mobile.portal.model.XiuWordDataVo;
import com.xiu.mobile.portal.service.IActivityNoregularService;
import com.xiu.mobile.portal.service.IGoodsService;

/**
 * 
* @Description: TODO(工具类) 
* @author haidong.luo@xiu.com
* @date 2016年3月4日 上午10:42:04 
*
 */
@Controller
@RequestMapping("/util")
public class UtilController extends BaseController {
	private Logger logger = Logger.getLogger(UtilController.class);

	@Autowired
	private EIShortUrlManager shortUrlManager;
	/***
	 * 生成短链接
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getShortURL", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getShortURL(HttpServletRequest request, HttpServletResponse response, String jsoncallback) {
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			// 获取utl
			String url =request.getParameter("url");
			
			if (StringUtils.isBlank(url)) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				result.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
				//判断订单状态，不是待支付状态则链接无效
			 url=shortUrlManager.getShortLinkByThirdParty(url);
			 
			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			result.put("shortURL", url);
			logger.info("生成短链接成功url："+ url);
		} catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", e.getExtErrCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("生成短链接url发生异常：" + e.getMessageWithSupportCode(), e);
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.info("生成短链接url发生异常：" + e.getMessage(), e);
		}
		
		logger.info("生成短链接url返回结果：" + JSON.toJSONString(result));
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}

}
