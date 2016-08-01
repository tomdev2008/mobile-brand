package com.xiu.mobile.portal.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.mobile.core.service.IWapH5ListService;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.json.JsonUtils;

@Controller
@RequestMapping("/wapH5")
public class WapH5ListController extends BaseController{
	private Logger logger=Logger.getLogger(WapH5ListController.class);
	
    @Autowired
    private IWapH5ListService wapH5ListService;
	/**
	 * 查询wap-H5列表
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value="/getWapH5List",produces="text/html;charset=UTF-8")
	public String getWapH5List(HttpServletRequest request,String jsoncallback,
			String page,String pageSize){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		if(null == page || "".equals(page)){
			page="1";
		}
		if(null == pageSize || "".equals(pageSize)){
			pageSize="5";
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put(XiuConstant.PAGE_NUM, page);
		map.put(XiuConstant.PAGE_SIZE, pageSize);
		try{
			Map<String,Object> result=wapH5ListService.getWapH5List(map);
			Boolean isSuccess=(Boolean)result.get("status");
			if(isSuccess){
				logger.info("查询wapH5列表信息："+result);
				resultMap.put(XiuConstant.TOTAL_COUNT, result.get(XiuConstant.TOTAL_COUNT)); //总数
				resultMap.put(XiuConstant.TOTAL_PAGE, result.get(XiuConstant.TOTAL_PAGE)); //总页数
				resultMap.put("wapH5List", result.get("h5List"));
				resultMap.put("pageURL", result.get("pageURL"));
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
			logger.error("wapH5列表查询发生异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
}
