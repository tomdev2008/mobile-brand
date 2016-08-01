package com.xiu.mobile.portal.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.service.IFindGoodsApiService;

/**
* @Description: TODO( 单品推荐接口) 
* @author haidong.luo@xiu.com
* @date 2015年9月29日 上午10:04:52 
*
 */
@Controller
@RequestMapping("/recommendGoods")
public class RecommendGoodsController extends BaseController {
	private Logger logger = Logger.getLogger(RecommendGoodsController.class);

	@Autowired
	private IFindGoodsApiService findGoodsApiService;

	
	@ResponseBody
	@RequestMapping(value = "/getRecommendGoodsList", produces = "text/html;charset=UTF-8")
	public String getRecommendGoodsList(HttpServletRequest request,String jsoncallback, 
			String pageNum,String tagId){
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("pageNum",pageNum);
		params.put("tagId",request.getParameter("tagId"));
		try {
			Map<String, Object> returnMap=findGoodsApiService.getRecommendGoodsList(params);
			Boolean isSuccess=(Boolean)returnMap.get("status");
			if(isSuccess){
				resultMap.put("goodsRecommendList", returnMap.get("goodsRecommendList"));
				resultMap.put("totalCount", returnMap.get("totalCount"));
				resultMap.put("totalPage", returnMap.get("totalPage"));
				resultMap.put("tagId", tagId);
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
			logger.error("单品推荐查询发生异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);

	}
	
}
