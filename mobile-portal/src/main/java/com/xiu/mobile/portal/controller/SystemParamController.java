package com.xiu.mobile.portal.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.commerce.hessian.model.Product;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.constants.GoodsConstant;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.ImageServiceConvertor;
import com.xiu.mobile.portal.common.util.Tools;
import com.xiu.mobile.portal.model.SysParamsVo;
import com.xiu.mobile.portal.model.Topic;
import com.xiu.mobile.portal.model.XiuWordDataVo;
import com.xiu.mobile.portal.service.IActivityNoregularService;
import com.xiu.mobile.portal.service.IGoodsService;
import com.xiu.mobile.portal.service.ISysParamService;

/**
 * 
* @Description: TODO(系统参数相关) 
* @author haidong.luo@xiu.com
* @date 2016年6月16日 上午10:50:17 
*
 */
@Controller
@RequestMapping("/sysParam")
public class SystemParamController extends BaseController {
	private Logger logger = Logger.getLogger(SystemParamController.class);

	@Autowired
	private ISysParamService sysParamService;

	/**
	 * 获取系统参数列表
	 * @param request
	 * @param jsoncallback
	 * @param xiuWord
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getSysParamList", produces = "text/html;charset=UTF-8")
	public String getSysParamList(HttpServletRequest request,String paramKey,
			String jsoncallback ) {
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		Map<String, Object>  paramMap=new HashMap<String, Object> ();
		paramMap.put("paramKey", paramKey);
		try {
			    List<SysParamsVo> ps=sysParamService.getAppSysParamsList(paramMap);
				resultMap.put("result", true);
				resultMap.put("sysParams", ps);
				resultMap.put("errorCode",ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg",ErrorCode.Success.getErrorMsg());

		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取系统参数列表发生异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
}
