package com.xiu.mobile.brand.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xiu.common.web.contants.NumberConstant;
import com.xiu.mobile.brand.web.bo.SearchBo;
import com.xiu.mobile.brand.web.constants.ItemShowTypeEnum;
import com.xiu.mobile.brand.web.constants.MktTypeEnum;
import com.xiu.mobile.brand.web.controller.params.SearchParam;
import com.xiu.mobile.brand.web.model.CatalogModel;
import com.xiu.mobile.brand.web.service.ICatalogService;
import com.xiu.mobile.brand.web.service.ISearchService;
import com.xiu.mobile.brand.web.util.CommonUtil;
import com.xiu.mobile.core.constants.RequestInletEnum;
import com.xiu.mobile.core.param.SearchFatParam;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(分类商品列表页) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-10-29 下午5:18:18 
 * ***************************************************************
 * </p>
 */
@Controller
@RequestMapping
public class CatalogForPageController {
	
	private static final Logger LOG = LoggerFactory.getLogger(CatalogController.class);
	private static final String V="2.0";
	@Autowired
	private ICatalogService catalogService;
	@Autowired
	private ISearchService searchService;
     /**
      * 提供m.xiu.com分类第一页数据
      * @param catId
      * @param v
      * @param model
      * @param request
      * @return
      */
	@RequestMapping(value = "list-{catId}")
	public String catGoodsList(@PathVariable Integer catId, String v, Model model,HttpServletRequest request) {
		SearchParam param = new SearchParam();
		//param.setV("2.0");//m.xiu.com 第一页筛选项增加发货地
		param.setCatId(catId);
		SearchFatParam fatParam = CommonUtil.change(param, RequestInletEnum.LIST_PAGE);
		fatParam.setNeedCatalogFlag(false);
		SearchBo searchBo = null;
		try {
			String firstCatalogName = "";
			CatalogModel catalog = catalogService.fetchCatalogModelNoteById(catId, MktTypeEnum.XIU, ItemShowTypeEnum.DSP12);
			model.addAttribute("catalog", catalog);
			if (catalog == null) {
				return "/404_error";
			}
			
			if (catalog.getParentCatalogId() > NumberConstant.NUM_0) {
				CatalogModel tempModel;
				boolean getFirst = false;
				int firstId = catalog.getParentCatalogId();
				while (!getFirst) {
					tempModel = catalogService.fetchCatalogModelNoteById(firstId, MktTypeEnum.XIU, ItemShowTypeEnum.DSP12);
					if (tempModel != null && tempModel.getParentCatalogId() == NumberConstant.NUM_0) {
						getFirst = true;
						firstCatalogName = tempModel.getCatalogName();
					}
					if (tempModel != null) {
						firstId = tempModel.getParentCatalogId();
					}
				}
			}else {
				firstCatalogName = catalog.getCatalogName();
			}
			
			model.addAttribute("firstCatalogName", firstCatalogName);
			searchBo = searchService.searchBySearchFatParam(fatParam);
			
			// 根据API版本信息，过滤“发货地”筛选项
			CommonUtil.filterDeliverInfo(V, searchBo.getAttrFacets());//硬性添加发货地
			model.addAttribute("attrFacets", searchBo.getAttrFacets());
			model.addAttribute("goodsItems", searchBo.getGoodsItems());
			model.addAttribute("sortId", fatParam.getSort().getSortOrder());
			model.addAttribute("page", searchBo.getPage());
			model.addAttribute("channel", request.getParameter("channel"));
		} catch (Exception e) {
			LOG.error("SearchFalParam:" + fatParam.toString(), e);
			return "/404_error";
		}
		model.addAttribute("catalogId", catId);
		return "pages/goodslist_cat";
	}
}
