package com.xiu.mobile.portal.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.util.AppVersionUtils;
import com.xiu.mobile.portal.model.AppVo;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.service.IIndexService;

/**
 * 
* @Description: TODO(首页接口) 
* @author haidong.luo@xiu.com
* @date 2015年9月25日 下午2:54:56 
*
 */
@Controller
@RequestMapping("/index")
public class IndexController extends BaseController {
	private Logger logger = Logger.getLogger(IndexController.class);

	@Autowired
	private IIndexService indexService;

	/**
	 * 首页信息获取接口
	 * @param request
	 * @param jsoncallback
	 * @param pageNum
	 * @param dataType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getIndexInfo", produces = "text/html;charset=UTF-8")
	public String getIndexInfo(HttpServletRequest request,
			String jsoncallback) {
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		Map<String,Object> params=new HashMap<String,Object>();
		try {

			AppVo appVo = AppVersionUtils.parseAppVo(request);
			params.put("appVo", appVo);

			Map<String,Object> returnMap=indexService.getIndexInfo(params);
			Boolean isSuccess=(Boolean)returnMap.get("status");
			resultMap.put("result", isSuccess);
			if(isSuccess){
				resultMap.put("advList", returnMap.get("advList"));
				resultMap.put("secondAdvList", returnMap.get("middelAdvList"));
				resultMap.put("indexGuideIconList", returnMap.get("indexGuideIconList"));
				resultMap.put("rowSubjectList", returnMap.get("rowSubjectList"));
				resultMap.put("crossSubjectList", returnMap.get("crossSubjectList"));
				resultMap.put("goodsRecommendListModel", returnMap.get("goodsRecommendListModel"));
				
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				resultMap.put("result", false);
				resultMap.put("errorCode", "errorCode");
				resultMap.put("errorMsg",returnMap.get("msg"));
			}

		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("首页查询发生异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}

	/**
	 * 首页信息获取接口（新）
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getNewIndexInfo", produces = "text/html;charset=UTF-8")
	public String getNewIndexInfo(HttpServletRequest request,
							   String jsoncallback) {
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		Map<String,Object> params=new HashMap<String,Object>();
		try {

			AppVo appVo = AppVersionUtils.parseAppVo(request);
			params.put("appVo", appVo);

			Map<String,Object> returnMap=indexService.getNewIndexInfo(params);
			Boolean isSuccess=(Boolean)returnMap.get("status");
			resultMap.put("result", isSuccess);
			if(isSuccess){
				resultMap.put("advList", returnMap.get("advList"));
				resultMap.put("secondAdvList", returnMap.get("middelAdvList"));
				resultMap.put("indexGuideIconList", returnMap.get("indexGuideIconList"));
				resultMap.put("rowSubjectList", returnMap.get("rowSubjectList"));
				resultMap.put("crossSubjectList", returnMap.get("crossSubjectList"));
				resultMap.put("goodsRecommendListModel", returnMap.get("goodsRecommendListModel"));

				resultMap.put("moreSubjectUrl", XiuConstant.MORE_SUBJECT_URL);
				resultMap.put("seasonHotUrl", returnMap.get("seasonHotUrl"));
				resultMap.put("recommendGoodsTags", returnMap.get("recommendGoodsTags"));

				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				resultMap.put("result", false);
				resultMap.put("errorCode", "errorCode");
				resultMap.put("errorMsg",returnMap.get("msg"));
			}

		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("首页查询发生异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}

	
}
