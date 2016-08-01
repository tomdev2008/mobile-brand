package com.xiu.mobile.brand.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xiu.mobile.brand.web.bo.MutilProductBo;
import com.xiu.mobile.brand.web.service.ImutilProductService;
import com.xiu.mobile.core.model.GoodsSolrModel;
import com.xiu.search.solrj.service.GenericSolrService;
import com.xiu.search.solrj.service.SearchResult;
/**
 * 走秀码查询一品多商信息服务
 * @author rian.luo@xiu.com
 *
 * 2016-1-29
 */
@Service("mutilProductService")
public class MutilProductServiceImpl implements ImutilProductService {
	
    private static Logger logger = LoggerFactory.getLogger(MutilProductServiceImpl.class);
    
	@Autowired
	private GenericSolrService genericSolrService;
	
	@Autowired
	private CommonsHttpSolrServer xiuAllSorlServer;
	
	@Value("${xiu.all.solr.url}")
	private String solrUrl;
	
	private static final String UPLOAD="/upload";
	
	/**
	 * 批量走秀码获取solr信息
	 */
	public List<MutilProductBo> queryBySn(String sn,boolean temp) {
		if (StringUtils.isEmpty(sn))
			return null;
		Long startTime = System.currentTimeMillis();
		List<MutilProductBo> mutilProductList = new ArrayList<MutilProductBo>();
		
		SearchResult<GoodsSolrModel> searchResult = null;
		BooleanQuery query = null;
		SolrQuery solrQuery = null;
		
		String[] productSns = sn.split(",");
		int size = productSns.length;
		for(int j=0;j<size;j++){
			List<GoodsSolrModel> goodsSolrModel =null;
			String productSn = productSns[j];
		    solrQuery = new SolrQuery();
		    query = new BooleanQuery();
			query.add(buildQuery("productSns", productSn), Occur.SHOULD);
			query.add(buildQuery("partNumber", productSn), Occur.SHOULD);
			solrQuery.setQuery(query.toString());
			solrQuery.setFields("priceMkt","priceFinal","priceXiu","itemId","brandName","brandNameEn","itemName","stateOnsale","imgUrl","partNumber");
			try {
				if(temp){
					//CommonsHttpSolrServer solrServer = SolrHttpService.getInstance().getSolrServer(solrUrl);
					QueryResponse response = xiuAllSorlServer.query(solrQuery);
					goodsSolrModel= response.getBeans(GoodsSolrModel.class);
				}else {
					searchResult = genericSolrService.findAll(GoodsSolrModel.class,solrQuery);
					goodsSolrModel = searchResult.getBeanList();
				}
			} catch (SolrServerException e) {
				if(logger.isErrorEnabled())
				logger.error("SN:"+productSn+",solr查询异常：", e);
			}
			
			if(CollectionUtils.isNotEmpty(goodsSolrModel)){
				int  count = goodsSolrModel.size();
				for (int i = 0; i < count; i++) {
					GoodsSolrModel model = goodsSolrModel.get(i);
					if (model != null) {
						MutilProductBo mutilProduct = new MutilProductBo();
						mutilProduct.setGoodsId(model.getItemId());
						mutilProduct.setGoodsSn(model.getPartNumber());
						mutilProduct.setGoodName(model.getItemName());
						mutilProduct.setBrandNameCn(model.getBrandName());
						mutilProduct.setBrandNameEn(model.getBrandNameEn());
						mutilProduct.setImg(UPLOAD+model.getImgUrl());
						mutilProduct.setMkPrice(model.getPriceMkt());
						mutilProduct.setOnSale(model.getOnsale());
						mutilProduct.setXiuPrice(model.getPriceXiu());
						mutilProduct.setFinalPrice(model.getPriceFinal());
						mutilProductList.add(mutilProduct);
					}
				}
			}
		}
		Long endTime = System.currentTimeMillis();
		logger.warn("此批走秀码查询共花费："+(endTime-startTime)+"毫秒。");
		//System.out.println("此批走秀码耗时："+(endTime-startTime));
		return mutilProductList;
	}
	
	private Query buildQuery(String field, Object value) {
		return new TermQuery(new Term(field, value.toString()));
	}
}
