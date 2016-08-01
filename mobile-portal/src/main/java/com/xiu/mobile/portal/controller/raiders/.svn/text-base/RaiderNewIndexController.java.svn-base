package com.xiu.mobile.portal.controller.raiders;

import java.util.HashMap;
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
import com.xiu.mobile.portal.controller.BaseController;
import com.xiu.mobile.portal.ei.EIRaiderNewIndexManager;
/**
 * 夺宝新接口
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/newRaiderIndex")
public class RaiderNewIndexController extends BaseController{
	private Logger logger=Logger.getLogger(RaiderIndexController.class);
	@Autowired
	private EIRaiderNewIndexManager raiderNewIndexManager;
	/**
	 * 鞋子列表接口
	 */
	@ResponseBody
    @RequestMapping(value="/getShoesList",produces="text/html;charset=UTF-8")
	public String getShoesList(HttpServletRequest request,String jsoncallback,
			String raiderMainId){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		if(StringUtils.isEmpty(raiderMainId)){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		try{
			Map<String,Object> result=raiderNewIndexManager.getShoesList(raiderMainId);
			Boolean isSuccess=(Boolean)result.get("status");
			if(isSuccess){
				logger.info("查询鞋子列表成功,raiderMainId="+raiderMainId);
				resultMap.put("raidersList", result.get("raidersList"));
				resultMap.put("time", result.get("time"));
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				resultMap.put("result", false);
				resultMap.put("errorCode",result.get("errorCode") );
				resultMap.put("errorMsg",result.get("errorMsg"));
			}
		}catch(Exception e){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询鞋子列表异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}

}
