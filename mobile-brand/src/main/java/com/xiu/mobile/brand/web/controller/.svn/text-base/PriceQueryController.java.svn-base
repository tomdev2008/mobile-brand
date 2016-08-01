package com.xiu.mobile.brand.web.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.mobile.brand.web.bo.PriceQueryBo;
import com.xiu.mobile.brand.web.service.IPriceQueryService;
import com.xiu.mobile.brand.web.util.JsonWrapper;
import com.xiu.mobile.brand.web.util.JsonWrapper.CodeEnum;

/*
 * **************************************
 * @Description 根据SN号查询价格
 * @Author kanghuai.zou@xiu.com
 * @Date 2015-12-15
 * **************************************
 */
@Controller
@RequestMapping(value="query")
public class PriceQueryController {
	private static Logger logger = Logger.getLogger(PriceQueryController.class);
	@Autowired
	private IPriceQueryService priceQueryService;
	//restful
	@RequestMapping(value = "price/{productSn}", produces = "application/json;charset=UTF-8")
	public  @ResponseBody String QueryPrice(@PathVariable("productSn") String prosn,String jsoncallback) {
		JsonWrapper jsonWrapper = new JsonWrapper();
		if(StringUtils.isEmpty(prosn)){
			jsonWrapper.getHead().setCodeEnum(CodeEnum.LACK_PARAM);
			return jsonWrapper.render(jsoncallback);
		}
		try {
			List<PriceQueryBo> data = priceQueryService.getPriceMap(prosn);
			if(data.size()==0){
				jsonWrapper.getHead().setCodeEnum(CodeEnum.ILLEGAL_PARAM);
			}else{
				jsonWrapper.getData().put("data", data);
			}
		} catch (Exception e) {
			jsonWrapper.getHead().setCodeEnum(CodeEnum.ERROR);
			logger.error("走秀码："+prosn+"获取价格异常："+e.getMessage());
		}
		return jsonWrapper.render(jsoncallback);
	}
	
}
