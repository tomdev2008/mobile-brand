package com.xiu.mobile.portal.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.mobile.core.model.DataBrandBo;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.model.DataBrandVo;
import com.xiu.mobile.portal.model.WellChosenBrandVo;
import com.xiu.mobile.portal.service.IWellChosenBrandService;

/**
 * <p>
 * **************************************************************
 * 
 * @Description: 精选品牌控制层
 * @AUTHOR wangchengqun
 * @DATE 2014-6-5
 ****************************************************************
 *</p>
 */
@Controller
@RequestMapping("/wellChosenBrand") 
public class WellChosenBrandControll extends BaseController {
	private Logger logger = Logger.getLogger(WellChosenBrandControll.class);

	@Autowired
	private IWellChosenBrandService wellChosenBrandManager;

	/**
	 * 分页查询精选品牌列表
	 * @param request
	 * @param jsoncallback
	 * @param pageNum
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getwellChosenBrandList", produces = "text/html;charset=UTF-8")
	public String getwellChosenBrandList(String jsoncallback){
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		 
		try {
			List<DataBrandBo> wellChosenBrandBoList=wellChosenBrandManager.getWellChosenBrandList();
			List<Object> wellChosenBrandList=new ArrayList<Object>();
			WellChosenBrandVo wcb=new WellChosenBrandVo();
			if(null!=wellChosenBrandBoList&&wellChosenBrandBoList.size()>0){
				wcb=wellChosenBrandManager.getSortList(wellChosenBrandBoList);
				wellChosenBrandList=wellChosenBrandManager.getResultList(wcb);
				resultMap.put("result", true);
				resultMap.put("wellChosenBrandList", wellChosenBrandList);
			 
			}else{
				resultMap.put("result", true);
				resultMap.put("wellChosenBrandList",new ArrayList<DataBrandVo>());
			}
		}catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("分页查询精选品牌列表时发生异常：" + e.getMessage(),e);
		}

		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	/**
	 * 全部品牌
	 */
	@ResponseBody
	@RequestMapping(value = "/allBrands", produces = "text/html;charset=UTF-8")
	public String allBrands(String jsoncallback){
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			List<Object> wellChosenBrandBoList=wellChosenBrandManager.findAllBrands();
			if(null!=wellChosenBrandBoList&&wellChosenBrandBoList.size()>0){
				resultMap.put("result", true);
				resultMap.put("allBrandList", wellChosenBrandBoList);
			}else{
				resultMap.put("result", true);
				resultMap.put("allBrandList",new ArrayList<DataBrandVo>());
			}
		}catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询全部品牌列表时发生异常：" + e.getMessage(),e);
			e.printStackTrace();
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
}
