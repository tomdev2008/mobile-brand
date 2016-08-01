package com.xiu.mobile.brand.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.common.web.utils.JSONObjectUtils;
import com.xiu.mobile.brand.web.bo.SearchBo;
import com.xiu.mobile.brand.web.controller.params.SearchParam;
import com.xiu.mobile.brand.web.dao.model.XBrandModel;
import com.xiu.mobile.brand.web.service.IBrandService;
import com.xiu.mobile.brand.web.service.ISearchService;
import com.xiu.mobile.brand.web.service.ISeoService;
import com.xiu.mobile.brand.web.util.CommonUtil;
import com.xiu.mobile.core.constants.RequestInletEnum;
import com.xiu.mobile.core.param.SearchFatParam;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO 品牌列表页面控制器
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-13 下午2:42:42 
 * ***************************************************************
 * </p>
 */
@Controller
@RequestMapping("/brand")
public class BrandForPageController {
	
	private static final Logger LOGGER = Logger.getLogger(BrandForPageController.class);
	
	@Autowired
	private IBrandService brandService;
	@Autowired
	private ISearchService searchService;
	@Autowired
	private ISeoService seoService;
	/**
	 * 提供m.xiu.com品牌第一页数据
	 * @param bId
	 * @param sort
	 * @param model
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "goodlist/{bId}")
    public String brandGoodsList(@PathVariable String bId, Integer sort, Model model,HttpServletRequest request) {
		try {
			// 构造查询参数
			SearchParam params = new SearchParam();
			params.setbId(bId);
			params.setSort(sort);
			
			SearchFatParam fatParam = CommonUtil.change(params, RequestInletEnum.BRAND_PAGE);
			SearchBo searchBo = searchService.searchBySearchFatParam(fatParam);
			if(searchBo == null) {
				return "404_error";
			}
			
			XBrandModel xBrandModel = brandService.getBrandInfoById(Long.valueOf(bId));
			if(xBrandModel == null) {
				return "404_error";
			}
			
			// seo
			model.addAttribute("seoInfo", seoService.buildBrandPageSEOInfo(xBrandModel));
			//返回参数
			model.addAttribute("brandId", bId);
			model.addAllAttributes(new BeanMap(searchBo));
			model.addAttribute("brandInfo", xBrandModel);
			model.addAttribute("sortId", fatParam.getSort().getSortOrder());
			model.addAttribute("channel", request.getParameter("channel"));
		} catch (Exception e) {
			LOGGER.error("brand goodlist page error.brandId={" + bId + "}, sort={" + sort + "}", e);
			return "404_error";
		}
		
	    return "pages/goodslist";
    }
	
	@ResponseBody
	@RequestMapping(value = "brandStory/{bId}",produces = "text/plain;charset=UTF-8")
	public String brandGoodsSearchByParams(@PathVariable String bId, String jsoncallback) {
		// 构造查询参数
		XBrandModel xBrandModel = brandService.getBrandInfoById(Long.valueOf(bId));
		return JSONObjectUtils.bean2jsonP(jsoncallback, xBrandModel);
	}
	
}
