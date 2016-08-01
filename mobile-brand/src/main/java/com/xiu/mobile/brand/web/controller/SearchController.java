package com.xiu.mobile.brand.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.mobile.brand.web.bo.SearchBo;
import com.xiu.mobile.brand.web.controller.params.SearchParam;
import com.xiu.mobile.brand.web.service.ISearchService;
import com.xiu.mobile.brand.web.util.CommonUtil;
import com.xiu.mobile.brand.web.util.JsonWrapper;
import com.xiu.mobile.brand.web.util.JsonWrapper.CodeEnum;
import com.xiu.mobile.core.constants.RequestInletEnum;
import com.xiu.mobile.core.param.SearchFatParam;

/**
 * <p>
 * **************************************************************
 * @Description: TODO(搜索页面控制器)
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-8-21 下午6:02:11
 * ***************************************************************
 * </p>
 */
@Controller
@RequestMapping("search")
public class SearchController extends BaseController {

	private static final Logger LOGGER = Logger.getLogger(SearchController.class);

	@Autowired
	private ISearchService searchService;
    /**
     * 关键词搜索商品列表
     * @param param
     * @param jsoncallback
     * @return
     */
	@RequestMapping(value = "goodsList", produces = "text/html;charset=UTF-8" )
	public @ResponseBody String goodsList(SearchParam param, String jsoncallback) {
		JsonWrapper jsonWrapper = new JsonWrapper();
		
		// 参数校验
		if (StringUtils.isEmpty(param.getKw())) {
			jsonWrapper.getHead().setCodeEnum(CodeEnum.LACK_PARAM);
			return jsonWrapper.render(jsoncallback);
		}
				
		// 设置默认排序值
		if(param.getSort() == null) {
			param.setSort(9);
		}
		
		// 构造查询参数
		SearchFatParam fatParam = CommonUtil.change(param, RequestInletEnum.SEARCH_PAGE);
		fatParam.setNeedCatalogFlag(false);
		fatParam.setNeedAttrFlag(false);
		
		SearchBo searchBo = null;
		try {
			searchBo = searchService.searchBySearchFatParam(fatParam);
			jsonWrapper.getData().put("keyWord", param.getKw());
			jsonWrapper.getData().put("goodsItems", searchBo.getGoodsItems());
			jsonWrapper.getData().put("page", searchBo.getPage());
		} catch (Exception e) {
			jsonWrapper.getHead().setCodeEnum(CodeEnum.ERROR);
			LOGGER.error("SearchFalParam:" + fatParam.toString(), e);
		}
		
		return jsonWrapper.render(jsoncallback);
	}
	/**
	 * 搜索分类
	 * @param param
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping(value = "catList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String catalogList(SearchParam param, String jsoncallback) {
		JsonWrapper jsonWrapper = new JsonWrapper();
		
		// 参数校验
		if (StringUtils.isEmpty(param.getKw())) {
			jsonWrapper.getHead().setCodeEnum(CodeEnum.LACK_PARAM);
			return jsonWrapper.render(jsoncallback);
		}
				
		// 构造查询参数
		SearchFatParam fatParam = CommonUtil.change(param, RequestInletEnum.SEARCH_PAGE);
		fatParam.setNeedGoodsFlag(false);
		fatParam.setNeedAttrFlag(false);
		
		SearchBo searchBo = null;
		try {
			Map<String, String[]> beanExclusivePros = new HashMap<String, String[]>();
			beanExclusivePros.put(JsonWrapper.CATALOG_BO_FILTER, new String[]{"img"});
			jsonWrapper.addFilters(beanExclusivePros);
			
			searchBo = searchService.searchBySearchFatParam(fatParam);
			jsonWrapper.getData().put("keyWord", param.getKw());
			jsonWrapper.getData().put("catalogs", searchBo.getCatalogs());
		} catch (Exception e) {
			jsonWrapper.getHead().setCodeEnum(CodeEnum.ERROR);
			LOGGER.error("SearchFalParam:" + fatParam.toString(), e);
		}

		return jsonWrapper.render(jsoncallback);
	}
	/**
	 * 搜索根据分类返回筛选项
	 * @param param
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping(value = "attrList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String attrList(SearchParam param, String jsoncallback) {
		JsonWrapper jsonWrapper = new JsonWrapper();
		
		// 参数校验
		if(StringUtils.isEmpty(param.getKw()) || param.getCatId() == null) {
			jsonWrapper.getHead().setCodeEnum(CodeEnum.LACK_PARAM);
			return jsonWrapper.render(jsoncallback);
		}
		
		// 构造查询参数
		SearchFatParam fatParam = CommonUtil.change(param, RequestInletEnum.SEARCH_PAGE);
		fatParam.setNeedCatalogFlag(false);
		fatParam.setNeedGoodsFlag(false);
		
		SearchBo searchBo = null;
		try {
			searchBo = searchService.searchBySearchFatParam(fatParam);
			
			// 根据API版本信息，过滤“发货地”筛选项
			CommonUtil.filterDeliverInfo(param.getV(), searchBo.getAttrFacets());
			
			jsonWrapper.getData().put("keyWord", param.getKw());
			jsonWrapper.getData().put("attrFacets", searchBo.getAttrFacets());
		} catch (Exception e) {
			jsonWrapper.getHead().setCodeEnum(CodeEnum.ERROR);
			LOGGER.error("SearchFalParam:" + fatParam.toString(), e);
		}

		return jsonWrapper.render(jsoncallback);
	}

}
