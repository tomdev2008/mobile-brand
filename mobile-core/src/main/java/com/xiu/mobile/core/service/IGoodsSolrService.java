package com.xiu.mobile.core.service;

import java.util.List;

import com.xiu.mobile.core.model.GoodsSolrModel;
import com.xiu.mobile.core.param.SearchFatParam;
import com.xiu.search.solrj.service.SearchResult;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(商品索引查询业务逻辑接口) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-30 下午2:38:39 
 * ***************************************************************
 * </p>
 */

public interface IGoodsSolrService {
	
	/**
	 * 查询商品索引中商品信息
	 * @param solrParams
	 *        查询参数
	 * @param attrFacetFields
	 *        筛选项分组
	 * @param extFacetFields
	 *        拓展项分组
	 * @return
	 */
	SearchResult<GoodsSolrModel> search(SearchFatParam solrParams, 
			List<String> attrFacetFields, List<String> extFacetFields);
	
	/**
	 * 查询商品索引中商品信息
	 * @param solrParams
	 *        查询参数
	 * @return
	 */
	SearchResult<GoodsSolrModel> search(SearchFatParam solrParams);

	SearchResult<GoodsSolrModel> findCurrentCatalogOtherAttrCount(SearchFatParam params, List<Long> attrIds);
	/**
	 * 通过走秀码过滤一品多商商品
	 * @param goodSns 走秀码
	 * @return 走秀码列表
	 */
	/*public List<String> filterGoodsOpmsSolr(List<String> goodSns);*/

}
