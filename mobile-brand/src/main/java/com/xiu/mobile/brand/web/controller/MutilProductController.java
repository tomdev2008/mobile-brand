package com.xiu.mobile.brand.web.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.mobile.brand.web.bo.MutilProductBo;
import com.xiu.mobile.brand.web.service.ImutilProductService;
import com.xiu.mobile.brand.web.util.JsonWrapper;
import com.xiu.mobile.brand.web.util.JsonWrapper.CodeEnum;

/**
 * 走秀码查询一品多商信息
 * @author rian.luo@xiu.com
 *
 * 2016-1-29
 */
@Controller
@RequestMapping(value="/mutilPro")
public class MutilProductController {
    private static Logger logger = LoggerFactory.getLogger(MutilProductController.class);
    
    @Autowired
    private ImutilProductService muImutilProductService;
    
	@ResponseBody
	@RequestMapping(value = "query", produces = "application/json;charset=UTF-8")
	public String queryMutilProBySn(String sn,String jsoncallback){
		JsonWrapper jsonWrapper = new JsonWrapper();
		if(StringUtils.isEmpty(sn)){
			if(logger.isInfoEnabled())
				logger.info("参数为空：", sn);
			jsonWrapper.getHead().setCodeEnum(CodeEnum.LACK_PARAM);
			return jsonWrapper.render(jsoncallback);
		}
		try {
			List<MutilProductBo> data = muImutilProductService.queryBySn(sn,false);
			if(data.size()==0){
				if(logger.isInfoEnabled())
					logger.info("参数{%s}没有查询到数据", sn);
				jsonWrapper.getHead().setCodeEnum(CodeEnum.NOT_FOUND);
			}else{
				jsonWrapper.getData().put("data", data);
			}
		} catch (Exception e) {
			if(logger.isErrorEnabled())
				logger.error("通过走秀码查询异常：", e);
			jsonWrapper.getHead().setCodeEnum(CodeEnum.ERROR);
		}
		return jsonWrapper.render(jsoncallback);
	}
	
	@ResponseBody
	@RequestMapping(value = "queryAll", produces = "application/json;charset=UTF-8")
	public String queryMutilProBySnAll(String sn,String jsoncallback){
		JsonWrapper jsonWrapper = new JsonWrapper();
		if(StringUtils.isEmpty(sn)){
			if(logger.isInfoEnabled())
				logger.info("参数为空：", sn);
			jsonWrapper.getHead().setCodeEnum(CodeEnum.LACK_PARAM);
			return jsonWrapper.render(jsoncallback);
		}
		try {
			List<MutilProductBo> data = muImutilProductService.queryBySn(sn,true);
			if(data.size()==0){
				if(logger.isInfoEnabled())
					logger.info("参数{%s}没有查询到数据", sn);
				jsonWrapper.getHead().setCodeEnum(CodeEnum.NOT_FOUND);
			}else{
				jsonWrapper.getData().put("data", data);
			}
		} catch (Exception e) {
			if(logger.isErrorEnabled())
				logger.error("通过走秀码查询异常：", e);
			jsonWrapper.getHead().setCodeEnum(CodeEnum.ERROR);
		}
		return jsonWrapper.render(jsoncallback);
	}
}
