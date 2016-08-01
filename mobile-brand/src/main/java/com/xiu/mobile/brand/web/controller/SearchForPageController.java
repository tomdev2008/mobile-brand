package com.xiu.mobile.brand.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xiu.mobile.brand.web.bo.SearchBo;
import com.xiu.mobile.brand.web.controller.params.SearchParam;
import com.xiu.mobile.brand.web.service.ISearchService;
import com.xiu.mobile.brand.web.util.CommonUtil;
import com.xiu.mobile.core.constants.RequestInletEnum;
import com.xiu.mobile.core.param.SearchFatParam;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(搜索商品列表页控制器) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-9-11 下午4:25:03 
 * ***************************************************************
 * </p>
 */
@Controller
@RequestMapping
public class SearchForPageController {
	
	private static final Logger LOGGER = Logger.getLogger(SearchForPageController.class);
	
	@Autowired
	private ISearchService searchService;
	/**
	 * 提供m.xiu.com 搜索第一页数据
	 * @param kw
	 * @param catId
	 * @param model
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "kw-{kw}")
    public String searchGoodsList(@PathVariable String kw, Integer catId, Model model,HttpServletRequest request) {
		if(StringUtils.isEmpty(kw)) {
			return "404_error";
		}
		
		SearchParam params = new SearchParam();
		params.setKw(kw);
		params.setSort(9);
		params.setCatId(catId);
		
		try {
			SearchFatParam fatParam = CommonUtil.change(params, RequestInletEnum.SEARCH_PAGE);
			SearchBo searchBo = searchService.searchBySearchFatParam(fatParam);
			if(searchBo == null) {
				return "404_error";
			}
			
			//返回参数
			model.addAttribute("keyWord", params.getKw());
			model.addAllAttributes(new BeanMap(searchBo));
			model.addAttribute("channel", request.getParameter("channel"));
		} catch (Exception e) {
			LOGGER.error("search goodlist page error.keyWord={" + params.getKw() + "}", e);
			return "404_error";
		}
	    return "pages/goodslist_search";
    }
}
