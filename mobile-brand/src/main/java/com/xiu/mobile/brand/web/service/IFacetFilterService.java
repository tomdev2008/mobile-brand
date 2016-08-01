package com.xiu.mobile.brand.web.service;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.response.FacetField;

import com.xiu.mobile.brand.web.bo.FacetFilterBo;
import com.xiu.mobile.brand.web.bo.FacetFilterBo.FacetTypeEnum;
import com.xiu.mobile.brand.web.model.AttrGroupJsonModel;

/**
 * 筛选区BO接口
 * @author Leon
 *
 */
 public interface IFacetFilterService {

	/**
	 * 解析品牌筛选结果<br>
	 * 索引分组查询结果的解析
	 * @param facetField
	 * @return
	 */
	 FacetFilterBo parseBrandFacetFilter(FacetField facetField);
	
	/**
	 * 解析价格查询QUERY结果<br>
	 * 索引分组查询结果的解析
	 * @param priceRange
	 * @return
	 */
	 FacetFilterBo parsePriceRangeFacetFilter(Map<String, Integer> priceRange);
	
	
	/**
	 * 解析属性筛选结果	支持多选		William.zhang	20130508<br>
	 * 索引分组查询结果的解析<br>
	 * 将名称通过attrIndexFieldNameMap进行初始化
	 * @param facetField
	 * @param oriAttrValIds 原始的查询参数数组
	 * @param attrIndexFieldNameMap 属性项的map，key为索引字段名，value为name
	 * @return
	 */
	 FacetFilterBo parseAttrFacetFilter(FacetField facetField,List<List<String>> attrValIdList,Map<String,AttrGroupJsonModel> attrIndexFieldNameMap);
	 
	 /**
	  * 解析发货地筛选结果<br>
	  * 将发货地封装成“国内发货”与“海外发货”两部分
	  * @param facetField
	  * @return
	  */
	 FacetFilterBo parseDeliverFacetFilter(FacetField facetField);
	
	/**
	 * 获得已选中的筛选项<br>
	 * 注：目前仅价格过滤可适用此方法<br>
	 * @param oriFacetFilter
	 * @param valueId
	 * @return
	 */
	 FacetFilterBo formatSelectFacetFilter(FacetTypeEnum type,long valueId);
}
