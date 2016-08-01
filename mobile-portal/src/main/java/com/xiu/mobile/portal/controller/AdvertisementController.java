package com.xiu.mobile.portal.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.facade.utils.HttpUtil;
import com.xiu.mobile.portal.model.AdvertisementVo;
import com.xiu.mobile.portal.service.IAdvService;

/**
 * 
 * @author wangchengqun
 * @date 20140701
 * 
 */
@Controller
@RequestMapping("/adv")
public class AdvertisementController extends BaseController {
	private Logger logger = Logger.getLogger(AdvertisementController.class);

	@Autowired
	private IAdvService advertisementService;

	/**
	 * 查询广告列表
	 * @param request
	 * @param jsoncallback
	 * @param advPlaceCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAdvList", produces = "text/html;charset=UTF-8")
	public String getAdvList(HttpServletRequest request,
			String jsoncallback, String advPlaceCode ) {
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		List<AdvertisementVo> advs=null;
		try {
			if (null != advPlaceCode && !"".equals(advPlaceCode)) {
				Map params=new HashMap();
				params.put("code", advPlaceCode);
				advs=advertisementService.getAdvListByType(params);
				resultMap.put("advList", advs);
				resultMap.put("defaultMainMenuIndex", 0); //0默认首页 1促销
				resultMap.put("result", true);
				resultMap.put("errorCode",ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg",ErrorCode.Success.getErrorMsg());
			} 
			//参数不对
			else {
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			}

		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询最新版本的App时发生异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}

//	@ResponseBody
//	@RequestMapping(value="/clickAdv",produces="test/html;charset=UTF-8")
//	public String clickAdv(HttpServletRequest request,String jsoncallback,String advId,String deviceId){
//		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
//		//参数不对
//		if (null == advId ||"".equals(advId)||null == deviceId || "".equals(deviceId)) {
//			resultMap.put("result", false);
//			resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
//			resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
//		}
//		Map params=new HashMap();
//		params.put("advId", advId);
//		params.put("deviceId", deviceId);
//		params.put("ip", HttpUtil.getRemoteIpAddr(request));
//		try {
//			advertisementService.addAdvLog(params);
//		}catch (Exception e) {
//			resultMap.put("result", false);
//			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
//			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
//			logger.error("记录广告点击异常:" + e.getMessage(),e);
//		}
//		
//		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
//	}
	
}
