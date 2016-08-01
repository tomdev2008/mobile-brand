package com.xiu.mobile.brand.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.brand.web.bo.PriceQueryBo;
import com.xiu.mobile.brand.web.service.IPriceQueryService;
import com.xiu.mobile.core.model.GoodsSolrModel;
import com.xiu.search.solrj.service.GenericSolrService;
import com.xiu.search.solrj.service.SearchResult;
/**
 * 通过走秀码获取最终价，市场价，走秀价
 * @author rian.luo@xiu.com
 *
 * 2016-1-27
 */
@Service("priceQueryService")
public class PriceQueryServiceImpl implements IPriceQueryService {

	@Autowired
	private GenericSolrService genericSolrService;
	
	@Override
	public List<PriceQueryBo> getPriceMap(String prosn) throws Exception {
		if (StringUtils.isEmpty(prosn))
			return null;
		List<PriceQueryBo> priceQuerybo = new ArrayList<PriceQueryBo>();
		String[] productSns = prosn.split(",");
		SearchResult<GoodsSolrModel> searchResult = null;

		SolrQuery solrQuery = new SolrQuery();
		for (String productSn : productSns) {
			//一品多商添加
			BooleanQuery query = new BooleanQuery();
		//	query.add(new TermQuery(new Term("productSns", productSn)), Occur.SHOULD);
			query.add(buildQuery("productSns", productSn), Occur.SHOULD);
		//	query.add(new TermQuery(new Term("partNumber", productSn)), Occur.SHOULD);
			query.add(buildQuery("partNumber", productSn), Occur.SHOULD);
			solrQuery.setQuery(query.toString());
			solrQuery.setFields("priceFinal", "priceMkt", "priceXiu");
			searchResult = genericSolrService.findAll(GoodsSolrModel.class,
					solrQuery);
			List<GoodsSolrModel> goodsSolrModel = searchResult.getBeanList();
			for (int i = 0; i < goodsSolrModel.size(); i++) {
				GoodsSolrModel model = goodsSolrModel.get(i);
				if (model != null) {
					PriceQueryBo priceQueryBo = new PriceQueryBo();
					priceQueryBo.setProductSn(productSn);
					priceQueryBo.setFinalPrice(model.getPriceFinal());
					priceQueryBo.setMktPrice(model.getPriceMkt());
					priceQueryBo.setXiuPrice(model.getPriceXiu());
					priceQuerybo.add(priceQueryBo);
				}
			}
		}
		return priceQuerybo;
	}
	
	private Query buildQuery(String field, Object value) {
		return new TermQuery(new Term(field, value.toString()));
	}
}
