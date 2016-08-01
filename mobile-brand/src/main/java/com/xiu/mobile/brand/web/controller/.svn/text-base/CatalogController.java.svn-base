package com.xiu.mobile.brand.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.mobile.brand.web.bo.CatalogBo;
import com.xiu.mobile.brand.web.bo.SearchBo;
import com.xiu.mobile.brand.web.constants.ItemShowTypeEnum;
import com.xiu.mobile.brand.web.constants.MktTypeEnum;
import com.xiu.mobile.brand.web.controller.params.SearchParam;
import com.xiu.mobile.brand.web.model.CatalogModel;
import com.xiu.mobile.brand.web.service.ICatalogService;
import com.xiu.mobile.brand.web.service.ISearchService;
import com.xiu.mobile.brand.web.util.CommonUtil;
import com.xiu.mobile.brand.web.util.JsonWrapper;
import com.xiu.mobile.brand.web.util.JsonWrapper.CodeEnum;
import com.xiu.mobile.core.constants.RequestInletEnum;
import com.xiu.mobile.core.param.SearchFatParam;
/**
 * 运营分类
 * @author rian.luo@xiu.com
 *
 * 2016-1-27
 */
@Controller
@RequestMapping("/list")
public class CatalogController extends BaseController{
	private static final Logger LOG = LoggerFactory.getLogger(CatalogController.class);
	@Autowired
	private ICatalogService catalogService;
	@Autowired
	private ISearchService searchService;
	/**
	 * APP获取一级分类接口
	 * @param jsoncallback 跨域
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/firstCatList", produces="text/html;charset=utf-8")
	public String listFirstLevel(@RequestParam(required = false) String jsoncallback){
		JsonWrapper wrapper = new JsonWrapper();
		List<CatalogModel> catalogModels = catalogService.listFirstLevelCatlogs();
		if (catalogModels ==null || catalogModels.isEmpty()) {
			wrapper.getHead().setCodeEnum(CodeEnum.CATA_NOT_FOUND);
		}else {
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			if (catalogModels != null) {
				for (int i = 0; i < catalogModels.size(); i++) {
					HashMap<String, Object> catMap = new LinkedHashMap<String, Object>();
					catMap.put("id", catalogModels.get(i).getCatalogId());
					catMap.put("name", catalogModels.get(i).getCatalogName());
					resultList.add(catMap);
				}
			}
			wrapper.getData().put("cats", resultList);
		}
		return wrapper.render(jsoncallback);
	}
	/**
	 * 通过一级分类ID获取旗下的所有分类信息
	 * @param pid
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/catListByPID", produces="text/html;charset=utf-8")
	public String listByPid(Integer pid, @RequestParam(required = false) String jsoncallback){
		JsonWrapper wrapper = new JsonWrapper();
		try {
			if (pid == null) {
				wrapper.getHead().setCodeEnum(CodeEnum.LACK_PARAM);
			}else {
				Map<String, String[]> beanExclusivePros = new HashMap<String, String[]>();
				beanExclusivePros.put(JsonWrapper.CATALOG_BO_FILTER, new String[]{"itemCount","selected","rank"});
				wrapper.addFilters(beanExclusivePros);
				CatalogBo catalog = catalogService.fetchCatalogBoTreeByIdForXiu(pid, ItemShowTypeEnum.DSP12);
				if (catalog == null) {
					wrapper.getHead().setCodeEnum(CodeEnum.CATA_NOT_FOUND);
				}else {
					wrapper.getData().put("cats", catalog.getChildCatalog());
				}
			}
		} catch (Exception e) {
			wrapper.getHead().setCodeEnum(CodeEnum.ERROR);
		}
		return wrapper.render(jsoncallback);
	}
	/**
	 * 分类页面商品
	 * @param params
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "goodsList", produces = "text/html;charset=UTF-8" )
	public String goodsList(SearchParam params, String jsoncallback) {
		JsonWrapper jsonWrapper = new JsonWrapper();
		// 构造查询参数
		SearchFatParam fatParam = CommonUtil.change(params, RequestInletEnum.LIST_PAGE);
		fatParam.setNeedCatalogFlag(false);
		fatParam.setNeedAttrFlag(false);
		SearchBo searchBo = null;
		try {
			if (fatParam.getCatalogId() ==null) {//分类id一定
				jsonWrapper.getHead().setCodeEnum(CodeEnum.LACK_PARAM);
			}else {
				CatalogModel catalog = catalogService.fetchCatalogModelNoteById(params.getCatId(), MktTypeEnum.XIU, ItemShowTypeEnum.DSP12);
				if (catalog == null) {
					jsonWrapper.getHead().setCodeEnum(CodeEnum.CATA_NOT_FOUND);
				}else {
					searchBo = searchService.searchBySearchFatParam(fatParam);
					jsonWrapper.getData().put("goodsItems", searchBo.getGoodsItems());
					jsonWrapper.getData().put("page", searchBo.getPage());
					jsonWrapper.getData().put("catName", catalog.getCatalogName());
					jsonWrapper.getData().put("catId", catalog.getCatalogId());
				}
			}
		} catch (Exception e) {
			jsonWrapper.getHead().setCodeEnum(CodeEnum.ERROR);
			LOG.error("SearchFalParam:" + fatParam.toString(), e);
		}
		return jsonWrapper.render(jsoncallback);
	}
	/**
	 * 分类筛选项
	 * @param params
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "filterListByCID", produces = "text/html;charset=UTF-8")
	public String attrList(SearchParam params, String jsoncallback) {
		JsonWrapper jsonWrapper = new JsonWrapper();
		// 构造查询参数
		SearchFatParam fatParam = CommonUtil.change(params, RequestInletEnum.LIST_PAGE);
		fatParam.setNeedCatalogFlag(true);//筛选项添加分类
		fatParam.setNeedGoodsFlag(false);
		SearchBo searchBo = null;
		try {
			if (fatParam.getCatalogId() ==null) {
				jsonWrapper.getHead().setCodeEnum(CodeEnum.LACK_PARAM);
			}else {
				searchBo = searchService.searchBySearchFatParam(fatParam);
				// 根据API版本信息，过滤“发货地”筛选项
				CommonUtil.filterDeliverInfo(params.getV(), searchBo.getAttrFacets());
				jsonWrapper.getData().put("attrFacets", searchBo.getAttrFacets());
				//判断该分类id是三级分类就不显示
				if(LOG.isInfoEnabled())
					LOG.info("分类筛选项分类id:"+params.getCatId());
				CatalogModel  catalogModel = catalogService.fetchThirdCatalogNoteById(params.getCatId());
				
				if(catalogModel!=null){
					jsonWrapper.getData().put("attrCats", searchBo.getCatalogs());
				}
			}
		} catch (Exception e) {
			jsonWrapper.getHead().setCodeEnum(CodeEnum.ERROR);
			LOG.error("SearchFalParam:" + fatParam.toString(), e);
		}
		return jsonWrapper.render(jsoncallback);
	}
}
