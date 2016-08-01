package com.xiu.mobile.core.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Before;
import org.junit.Test;

public class TestGetAllBrands {
	private static final Logger logger =Logger.getLogger(TestGetAllBrands.class);
	
	private CommonsHttpSolrServer solrServer;
	
	@Before
	public void init(){
		String solrServerUrl="http://10.0.0.161:3736/solr2/xiu";
		try {
			solrServer = new CommonsHttpSolrServer(solrServerUrl);
			solrServer.setConnectionTimeout(5000);
			solrServer.setFollowRedirects(false);
			solrServer.setMaxRetries(3);
			solrServer.setMaxTotalConnections(30);
			solrServer.setDefaultMaxConnectionsPerHost(15);
			try {
				solrServer.ping();
				System.out.println("链接success");
				logger.info("链接success");
			} catch (SolrServerException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			logger.error("获取solr链接异常",e);
			e.printStackTrace();
		}
	}
	
	@Test
	public void getBrandIds(){
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("*:*");
		//设置分组
		solrQuery.setFacet(true);
		solrQuery.addFacetField("brandId");
		solrQuery.setStart(0);
		solrQuery.setFacetLimit(Integer.MAX_VALUE);
		try {
			 QueryResponse response = solrServer.query(solrQuery);
			 List<Count> countList = response.getFacetField("brandId").getValues();
			 for(Count c:countList){
				 System.out.println("品牌ID:"+c.getName());
				 System.out.println("该"+c.getName()+"品牌ID下品牌个数:"+c.getCount());
			 }
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
	}
}
