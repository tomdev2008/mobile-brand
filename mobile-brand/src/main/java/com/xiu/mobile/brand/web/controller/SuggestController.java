package com.xiu.mobile.brand.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.mobile.brand.web.bo.LabelSearchBo;
import com.xiu.mobile.brand.web.bo.SearchHisMapBo;
import com.xiu.mobile.brand.web.bo.SuggestBo;
import com.xiu.mobile.brand.web.bo.TypeSuggestBo;
import com.xiu.mobile.brand.web.controller.params.SuggestParam;
import com.xiu.mobile.brand.web.search.XiuLabelSolrClient;
import com.xiu.mobile.brand.web.service.ISuggestService;
import com.xiu.mobile.brand.web.service.params.SuggestFatParam;
import com.xiu.mobile.brand.web.util.JsonWrapper;
import com.xiu.mobile.brand.web.util.JsonWrapper.CodeEnum;
import com.xiu.mobile.brand.web.util.Page;
import com.xiu.mobile.core.model.XiuLabelInfoModel;

/**
 * <p>
 * ************************************************************** 
 * @Description: TODO(关键字联想) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-10-14 下午4:12:22 
 * ***************************************************************
 * </p>
 */
@Controller
@RequestMapping(value="suggest")
public class SuggestController {
	
	private static final Logger LOGGER = Logger.getLogger(SuggestController.class);
	
	@Autowired
	private ISuggestService suggestService;
    /**
     * 搜索关键词联想
     * @param param
     * @param jsoncallback
     * @return
     */
	@RequestMapping(value = "execute", produces = "text/html;charset=UTF-8")
	public @ResponseBody String execute(SuggestParam param, String jsoncallback) {
		JsonWrapper jsonWrapper = new JsonWrapper();
		
		try {
			SuggestFatParam suggestFatParam = new SuggestFatParam(param);
			List<SuggestBo> data = suggestService.suggest(suggestFatParam);
			jsonWrapper.getData().put("data", data);
		} catch (Exception e) {
			jsonWrapper.getHead().setCodeEnum(CodeEnum.ERROR);
		}
		return jsonWrapper.render(jsoncallback);
	}
	/**
	 * 联想词大写
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping(value = "get", produces = "text/html;charset=UTF-8")
	public  @ResponseBody String get(String jsoncallback) {
		JsonWrapper jsonWrapper = new JsonWrapper();
		try {
			List<SearchHisMapBo> data = suggestService.getSearchHistoryMap();
			jsonWrapper.getData().put("data", data);
		} catch (Exception e) {
			jsonWrapper.getHead().setCodeEnum(CodeEnum.ERROR);
		}
		return jsonWrapper.render(jsoncallback);
	}
	
    /**
     * 分类搜索关键词联想
     * @param param
     * @param jsoncallback
     * @return
     */
	@RequestMapping(value = "typeKeySuggest", produces = "text/html;charset=UTF-8")
	public @ResponseBody String typeKeySuggest(SuggestParam param, String jsoncallback) {
		JsonWrapper jsonWrapper = new JsonWrapper();
		
//		param.setQ("女装");
		LOGGER.info("标签搜索，关键字为:"+param.getQ());
		// 参数校验
		if (StringUtils.isEmpty(param.getQ())) {
			jsonWrapper.getHead().setCodeEnum(CodeEnum.LACK_PARAM);
			return jsonWrapper.render(jsoncallback);
		}
		LOGGER.info("标签搜索，关键字为:"+param.getQ());
		try {
			
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			Map<String, List<TypeSuggestBo>> typeMap = suggestService.getLabelList(param.getQ(), 3);
			Set<String> typeSet = typeMap.keySet();
			Map<String,Object> t;
			for(String typeKey : typeSet)
			{
				if(typeMap.get(typeKey) !=null && typeMap.get(typeKey).size()>0)
				{
					t = new HashMap<String,Object>();
					t.put("type", typeKey);
					t.put("suggests", typeMap.get(typeKey));
					data.add(t);
				}
				
			}
			jsonWrapper.getData().put("data", data);
			//联想词
			param.setType("0");
			if(param.getLimit()==0)
				param.setLimit(10);
			jsonWrapper.getData().put("suggestWords", suggestWords(param));
		} catch (Exception e) {
			e.printStackTrace();
			jsonWrapper.getHead().setCodeEnum(CodeEnum.ERROR);
			LOGGER.error("分类搜索关键词联想异常", e);
		}
		return jsonWrapper.render(jsoncallback);
	}
	
	
	/**
     * 分类搜索关键词联想
     * @param param
     * @param jsoncallback
     * @return
     */
	@RequestMapping(value = "getLabelListByType", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getLabelListByType(SuggestParam param, String jsoncallback) {
		JsonWrapper jsonWrapper = new JsonWrapper();
		
		LOGGER.info("标签列表搜索，关键字为:"+param.getQ());
		// 参数校验
		if (StringUtils.isEmpty(param.getType())) {
			jsonWrapper.getHead().setCodeEnum(CodeEnum.LACK_PARAM);
			return jsonWrapper.render(jsoncallback);
		}
		try {
			LabelSearchBo page = suggestService.getLabelListByType(param.getQ(), param.getType(), param.getP(), param.getpSize());
			if(page !=null)
			{
				jsonWrapper.getData().put("suggests", page.getTypeSuggestBos());
				jsonWrapper.getData().put("page", page.getPage());
			}
			else
			{
				jsonWrapper.getData().put("suggests", new ArrayList<TypeSuggestBo>());
				jsonWrapper.getData().put("page", new Page());
			}
			//联想词
			param.setType("0");
			if(param.getLimit()==0)
				param.setLimit(10);
			
			jsonWrapper.getData().put("suggestWords", suggestWords(param));
		} catch (Exception e) {
			e.printStackTrace();
			jsonWrapper.getHead().setCodeEnum(CodeEnum.ERROR);
			LOGGER.error("分类搜索关键词联想列表异常", e);
		}
		return jsonWrapper.render(jsoncallback);
	}
	
	/**
	 * 获取联想词
	 * @return
	 */
	private List<String> suggestWords(SuggestParam param)
	{
		SuggestFatParam suggestFatParam = new SuggestFatParam(param);
		//suggestFatParam.setType("0");
		List<SuggestBo> words = suggestService.suggest(suggestFatParam);
		List<String> suggestWords=new ArrayList<String>();
		//去重~
		Map<String,String> quchong = new HashMap<String,String>();
		for(SuggestBo b : words)
		{
			//关键字一模一样不显示
			if(!b.getDisplay().equals(param.getQ()) && quchong.get(b.getDisplay()) == null)
			{
				quchong.put(b.getDisplay(), "");
				suggestWords.add(b.getDisplay());
			}
				
		}
		quchong.clear();
		return suggestWords;
	}
}
