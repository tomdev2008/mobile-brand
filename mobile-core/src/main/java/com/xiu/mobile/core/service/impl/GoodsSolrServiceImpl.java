package com.xiu.mobile.core.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.core.constants.GoodsIndexFieldEnum;
import com.xiu.mobile.core.constants.RequestInletEnum;
import com.xiu.mobile.core.model.GoodsSolrModel;
import com.xiu.mobile.core.param.SearchFatParam;
import com.xiu.mobile.core.service.IGoodsSolrService;
import com.xiu.mobile.core.utils.GoodsSolrConditionBuilder;
import com.xiu.search.solrj.query.QueryFieldCondition;
import com.xiu.search.solrj.query.QueryFieldFacetCondition;
import com.xiu.search.solrj.query.QueryFieldSortCondition;
import com.xiu.search.solrj.query.SolrQueryBuilder;
import com.xiu.search.solrj.service.GenericSolrService;
import com.xiu.search.solrj.service.SearchResult;

/**
 * <p>
 * ************************************************************** 
 * @Description: 商品Solr操作业务逻辑实现类 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-9 下午3:10:39
 * ***************************************************************
 * </p>
 */
@Service("goodsSolrService")
public class GoodsSolrServiceImpl implements IGoodsSolrService {
	
	private static final Logger LOGGER = Logger.getLogger(GoodsSolrServiceImpl.class);
	
	@Autowired
	private GenericSolrService genericSolrService;
	
	@Override
	public SearchResult<GoodsSolrModel> search(SearchFatParam solrParams, 
			List<String> attrFacetFields, List<String> extFacetFields) {
		SolrQueryBuilder builder = new SolrQueryBuilder();
		
		// 构建主参数
		List<QueryFieldCondition> mainConds = GoodsSolrConditionBuilder.createMainCondition(solrParams);
		for(QueryFieldCondition cond : mainConds) {
			builder.addMainQueryClauses(cond);
		}
		
		// 构建筛选项分组
		if(solrParams.getCatalogId() != null && solrParams.isNeedAttrFlag()) {
			// 品牌页不反查品牌
			boolean brandFacetFlag = solrParams.getRequestInlet() != RequestInletEnum.BRAND_PAGE;
			List<QueryFieldFacetCondition> facetConds = GoodsSolrConditionBuilder
					.createFilterFacetCondition(brandFacetFlag, true, true, attrFacetFields);
			
			for(QueryFieldFacetCondition cond : facetConds) {
				builder.addFacetQueryClauses(cond);
			}
		}
		
		// 构建扩展分组
		if (null != extFacetFields) {
			for(int i = 0; i < extFacetFields.size(); i++) {
				if(GoodsIndexFieldEnum.OCLASS_IDS.fieldName().equals(extFacetFields.get(i))) {
					if(!solrParams.isNeedCatalogFlag()) {
						extFacetFields.remove(i);
						i--;
					}
				}
			}
			
			QueryFieldFacetCondition facetCond = GoodsSolrConditionBuilder.createExtFacetCondition(extFacetFields);
			if (null != facetCond) {
				builder.addFacetQueryClauses(facetCond);
			}
		}
		
		// 排序
		if(null != solrParams.getSort()) {
			List<QueryFieldSortCondition> sortConds = GoodsSolrConditionBuilder.createSortCondition(solrParams);
			
			for(QueryFieldSortCondition sortCond : sortConds) {
				builder.addSortQueryClauses(sortCond);
			}
		}
		
		// 翻页
		if(solrParams.isNeedGoodsFlag() && null != solrParams.getPage()) {
			builder.setStart(solrParams.getPage().getFirstRecord() - 1);
			builder.setRows(solrParams.getPage().getPageSize());
		}
		
		// 查询Solr
		SolrQuery solrQuery = builder.solrQueryBuilder();
		SearchResult<GoodsSolrModel> searchResult = null;
		try {
			searchResult = genericSolrService.findAll(GoodsSolrModel.class, solrQuery);
		} catch (SolrServerException e) {
			LOGGER.error("Query goods solr error. solrQuery:" + solrQuery.toString(), e);
		}
		
		return searchResult;
	}

	@Override
	public SearchResult<GoodsSolrModel> search(SearchFatParam solrParams) {
		return search(solrParams, null, null);
	}

	@Override
	public SearchResult<GoodsSolrModel> findCurrentCatalogOtherAttrCount(SearchFatParam params, List<Long> attrIds) {
		if (params == null || params.getCatalogId()==null) {
			return null;
		}
		SolrQueryBuilder builder = new SolrQueryBuilder();
		SearchResult<GoodsSolrModel> results = null;
		try {
			// 构建主参数
			List<QueryFieldCondition> mainConds = GoodsSolrConditionBuilder.createMainCondition(params);
			if(null == mainConds || mainConds.size()==0){
				LOGGER.error("查询solr主参数为空！");
				return null;
			}
			for (QueryFieldCondition cond : mainConds) {
				builder.addMainQueryClauses(cond);
			}
			
			SolrQuery solrQuery = builder.solrQueryBuilder();
			solrQuery.setFacet(true);
			solrQuery.setFacetMinCount(1);
			solrQuery.addFacetField("attrs");
			solrQuery.setRows(0);
			if (attrIds != null && !attrIds.isEmpty()) {
				for (int i = 0; i < attrIds.size(); i++) {
					if (attrIds.get(i) != null) {
						solrQuery.addFacetQuery("attrs:"+params.getCatalogId()+""+attrIds.get(i));
					}
				}
				solrQuery.setFacetLimit(1);
			}else {
				solrQuery.addFacetQuery("attrs:"+params.getCatalogId()+"*");
				solrQuery.setFacetLimit(Integer.MAX_VALUE);
			}
			results = genericSolrService.findAll(GoodsSolrModel.class, solrQuery);
		} catch (SolrServerException e) {
			LOGGER.error("获取分类筛选项异常----->",e);
			e.printStackTrace();
		}
		return results;
	}
	
}
