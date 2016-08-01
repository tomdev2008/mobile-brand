package com.xiu.mobile.brand.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.common.web.utils.JSONObjectUtils;
import com.xiu.mobile.brand.web.bo.BrandBo;
import com.xiu.mobile.brand.web.bo.SearchBo;
import com.xiu.mobile.brand.web.controller.params.SearchParam;
import com.xiu.mobile.brand.web.dao.model.XBrandModel;
import com.xiu.mobile.brand.web.service.IBrandService;
import com.xiu.mobile.brand.web.service.ISearchService;
import com.xiu.mobile.brand.web.util.CommonUtil;
import com.xiu.mobile.brand.web.util.JsonWrapper;
import com.xiu.mobile.brand.web.util.JsonWrapper.CodeEnum;
import com.xiu.mobile.core.constants.RequestInletEnum;
import com.xiu.mobile.core.param.SearchFatParam;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO 品牌搜索接口控制器
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-4 下午3:11:21 
 * ***************************************************************
 * </p>
 */
@Controller
@RequestMapping("/brand")
public class BrandController {
	
	private static final Logger LOG = Logger.getLogger(BaseController.class);
	
	@Autowired
	private IBrandService brandService;
	
	@Autowired
	private ISearchService searchService;
	
	/**(目前乜有调用)
	 * 返回品牌分类信息、刷选项信息、品牌下商品列表信息
	 * @param params
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "brandSearchByParams", produces = "text/html;charset=UTF-8")
	public String brandSearchByParams(SearchParam params, String jsoncallback) {
		// 构造查询参数
		SearchFatParam fatParam = CommonUtil.change(params, RequestInletEnum.BRAND_PAGE);
		BrandBo brandBo = getBrandBoByParam(fatParam, params.getV());
		return JSONObjectUtils.bean2jsonP(jsoncallback, brandBo);
	}
	
	/**
	 * 返回品牌的分类信息
	 * @param params
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "brandCatalogsSearchByParams", produces = "text/html;charset=UTF-8")
	public String brandCatalogsSearchByParams(SearchParam params, String jsoncallback) {
		// 构造查询参数
		SearchFatParam fatParam = CommonUtil.change(params, RequestInletEnum.BRAND_PAGE);
		fatParam.setNeedAttrFlag(false);
		fatParam.setNeedGoodsFlag(false);
		BrandBo brandBo = getBrandBoByParam(fatParam, params.getV());
		return JSONObjectUtils.bean2jsonP(jsoncallback, brandBo);
	}
	/**
	 * 返回品牌的筛选项信息
	 * @param params
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "brandAttrsSearchByParams", produces = "text/html;charset=UTF-8")
	public String brandAttrsSearchByParams(SearchParam params, String jsoncallback) {
		// 构造查询参数
		SearchFatParam fatParam = CommonUtil.change(params, RequestInletEnum.BRAND_PAGE);
		fatParam.setNeedCatalogFlag(false);
		fatParam.setNeedGoodsFlag(false);
		BrandBo brandBo = getBrandBoByParam(fatParam, params.getV());
		return JSONObjectUtils.bean2jsonP(jsoncallback, brandBo);
	}
	
	/**
	 * 返回品牌商品列表信息
	 * 品牌页第二页数据
	 * @param params
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "brandGoodsSearchByParams", produces = "text/html;charset=UTF-8")
	public String brandGoodsSearchByParams(SearchParam params, String jsoncallback) {
		// 构造查询参数
		SearchFatParam fatParam = CommonUtil.change(params, RequestInletEnum.BRAND_PAGE);
		fatParam.setNeedCatalogFlag(false);
		fatParam.setNeedAttrFlag(false);
		BrandBo brandBo = getBrandBoByParam(fatParam, params.getV());
		return JSONObjectUtils.bean2jsonP(jsoncallback, brandBo);
	}
	
	private BrandBo getBrandBoByParam(SearchFatParam fatParam, String apiVersion) {
		BrandBo brandBo = new BrandBo();
		if(fatParam.getBrandIds() == null || fatParam.getBrandIds().length != 1) {
			brandBo.setCode(CodeEnum.ILLEGAL_PARAM.getCode());
			brandBo.setMsg(CodeEnum.ILLEGAL_PARAM.getDesc());
			return brandBo;
		}
		try {
			SearchBo searchBo = searchService.searchBySearchFatParam(fatParam);
			// 根据API版本信息，过滤“发货地”筛选项
			CommonUtil.filterDeliverInfo(apiVersion, searchBo.getAttrFacets());
			brandBo.setBrandId(fatParam.getBrandIds()[0]);
			brandBo.setAttrFacets(searchBo.getAttrFacets());//属性
			brandBo.setCatalogs(searchBo.getCatalogs());//分类
			brandBo.setGoodsItems(searchBo.getGoodsItems());//商品列表
			brandBo.setPage(searchBo.getPage());
			brandBo.setCode("0");
			brandBo.setMsg("成功");
		} catch (Exception e) {
			brandBo.setCode("-1");
			brandBo.setMsg("系统错误");
			LOG.error("SearchFalParam:" + fatParam.toString(), e);
		}
		return brandBo;
	}
	/**
	 * 得到该品牌信息
	 * @param bId
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getBrandInfo", produces = "text/html;charset=UTF-8")
	public String getBrandInfo(long bId, String jsoncallback) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			XBrandModel xBrand = brandService.getBrandInfoById(bId);
			if(xBrand == null) {
				map.put("code", 1);
				map.put("msg", "品牌不存在");
			} else {
				map.put("code", 0);
				map.put("msg", "成功");
				map.put("brand", xBrand);
			}
		} catch (Exception e) {
			map.put("code", -1);
			map.put("msg", "系统错误");
			LOG.error("get brand{" + bId + "} info error.", e);
		}
		return JSONObjectUtils.bean2jsonP(jsoncallback, map);
	}
	/**
	 * 获取某品牌在售商品数
	 * @param params
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "goodscount", produces = "text/html;charset=UTF-8")
	public String getBrandGoodsCount(SearchParam params, String jsoncallback) {
		JsonWrapper jsonWrapper = new JsonWrapper();
		// 构造查询参数
		params.setpSize(0);
		SearchFatParam fatParam = CommonUtil.change(params, RequestInletEnum.BRAND_PAGE);
		fatParam.setNeedCatalogFlag(false);
		fatParam.setNeedAttrFlag(false);
		BrandBo brandBo = null;
		try {
			brandBo = getBrandBoByParam(fatParam, params.getV());
			jsonWrapper.getData().put("data", brandBo.getPage().getRecordCount());
		} catch (Exception e) {
			jsonWrapper.getHead().setCodeEnum(CodeEnum.ERROR);
		}
		return jsonWrapper.render(jsoncallback);
	}
	/**
	 * 获取所有在售的品牌数
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "AllOnLineBrands", produces = "text/html;charset=UTF-8")
	public String getAllOnLineBrands(@RequestParam(required = false)String jsoncallback) {
		JsonWrapper wrapper = new JsonWrapper();
		List<XBrandModel> brandList = brandService.getBrandInfo(); 
		if (brandList ==null || brandList.isEmpty()) {
			wrapper.getHead().setCodeEnum(CodeEnum.CATA_NOT_FOUND);
		}else {
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			if (brandList != null) {
				for (int i = 0; i < brandList.size(); i++) {
					HashMap<String, Object> catMap = new LinkedHashMap<String, Object>();
					catMap.put("brandId", brandList.get(i).getBrandId());
					catMap.put("brandName", brandList.get(i).getBrandName());
					catMap.put("enName", brandList.get(i).getEnName());
					resultList.add(catMap);
				}
			}
			LOG.info("组装成JSON 的个数：======"+resultList.size()+"==========");
			wrapper.getData().put("brands", resultList);
		}
		return wrapper.render(jsoncallback);
	}
}
