package com.xiu.mobile.core.test;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

public class TestQueryNewProducts {
	private static String[] CATS_NORMAL = {"100001107","100001227"};
//											"100000173","100001107","100000334","100000119","100001227",
//											"100000346","100000176","100000089","100000146"};
	private static String[] CATS_MAN ={"100004294","100000586"};
//										"100000398","100000586","100000063","100004294"};
	private static String[] BRAND_IDS = {};
	private static String[] filters = {"-5667","-5998"};
//	public CommonsHttpSolrServer getSolrServer(){
//		try {
//			CommonsHttpSolrServer httpSolrServer = new CommonsHttpSolrServer("http://10.0.0.161:3736/solr2/xiu/");
//			httpSolrServer.setSoTimeout(30000);
//			httpSolrServer.setMaxTotalConnections(5);
//			httpSolrServer.setConnectionTimeout(30000);
//			httpSolrServer.setDefaultMaxConnectionsPerHost(3);
//			return httpSolrServer;
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	@Test
//	public void queryTest(){
//		Map<String,Collection<Object>> subPartNumbers = new HashMap<String, Collection<Object>>();
//		Map<String,Collection<Object>> allPartNumbers = new HashMap<String, Collection<Object>>();
//		Map<String,Collection<Object>> filterdPartNumbers = new HashMap<String, Collection<Object>>();
//		BooleanQuery mainQuery = new BooleanQuery();
//		BooleanQuery manCatalogQuery = new BooleanQuery();
//		for (int i = 0; i < CATS_MAN.length; i++) {
//			manCatalogQuery.add(new TermQuery(new Term("oclassIds",CATS_MAN[i])), Occur.SHOULD);
//		}
//		BooleanQuery normalCatalogQuery = new BooleanQuery();
//		BooleanQuery normalCatalog = new BooleanQuery();
//		for (int i = 0; i < CATS_NORMAL.length; i++) {
//			normalCatalog.add(new TermQuery(new Term("oclassIds",CATS_NORMAL[i])),Occur.SHOULD);
//		}
//		normalCatalogQuery.add(normalCatalog,Occur.MUST);
//		normalCatalogQuery.add(new TermQuery(new Term("itemName","女")),Occur.MUST_NOT);
//		mainQuery.add(manCatalogQuery, Occur.MUST);
//		mainQuery.add(normalCatalogQuery, Occur.SHOULD);
//		if (BRAND_IDS.length>0) {
//			BooleanQuery brandQuery = new BooleanQuery();
//			for (int i = 0; i < BRAND_IDS.length; i++) {
//				brandQuery.add(new TermQuery(new Term("brandId",BRAND_IDS[i])),Occur.SHOULD);
//			}
//			mainQuery.add(brandQuery, Occur.MUST);
//		}
//		if (filters.length>0) {
//			BooleanQuery specificQuery = new BooleanQuery();
//			for (int i = 0; i < filters.length; i++) {
//				specificQuery.add(new TermQuery(new Term("attr_7000000000000027250",QueryParser.escape(filters[i]))),Occur.SHOULD);
//			}
//			mainQuery.add(specificQuery, Occur.MUST);
//		}
//		SolrQuery solrQuery = new SolrQuery();
//		solrQuery.setQuery(mainQuery.toString());
//		solrQuery.setFields("partNumber","oclassIds");
//		solrQuery.setSortField("onsaleTime", ORDER.desc);
//		solrQuery.setStart(0);
//		solrQuery.setRows(Integer.MAX_VALUE);
//		try {
//			long start = System.currentTimeMillis();
//			CommonsHttpSolrServer httpSolrServer = getSolrServer();
//			System.out.println("solrQuery:"+solrQuery);
//			QueryResponse response = httpSolrServer.query(solrQuery, METHOD.POST);
//			SolrDocumentList documentList = response.getResults();
//			for (Iterator<SolrDocument> iter = documentList.iterator();iter.hasNext();) {
//				SolrDocument document = iter.next();
//				subPartNumbers.put((String)document.getFieldValue("partNumber"),document.getFieldValues("oclassIds"));
//			}
//			System.out.println("total Hits:"+response.getResults().getNumFound());
//			long end = System.currentTimeMillis();
//			System.out.println("查询共计耗时："+(end-start)+"毫秒");
//			allPartNumbers = getAllProductMap();
//			for (Iterator<String> iter = allPartNumbers.keySet().iterator();iter.hasNext();) {
//				String key = iter.next();
//				if (!subPartNumbers.containsKey(key)) {
//					filterdPartNumbers.put(key, allPartNumbers.get(key));
//				}
//			}
//			for (Iterator<Map.Entry<String, Collection<Object>>> iterator = filterdPartNumbers.entrySet().iterator();iterator.hasNext();) {
//				Entry<String, Collection<Object>> entry = iterator.next();
//				String key = entry.getKey();
//				List<Object> values = new ArrayList<Object>(entry.getValue());
//				List<String> catNorms = Arrays.asList(CATS_MAN);
//				//System.out.println("find filter ---->partNumber:"+key);
//				for (int i = 0; i < values.size(); i++) {
//					if (catNorms.contains((String)values.get(i))) {
//						System.out.println("find filter ---->partNumber:"+key+",class:"+values.get(i));
//						break;
//					}
//				}
//			}
//		} catch (SolrServerException e) {
//			e.printStackTrace();
//		}
//		
//	}
//	private Map<String, Collection<Object>> getAllProductMap(){
//		Map<String,Collection<Object>> allPartNumbers = new HashMap<String, Collection<Object>>();
//		BooleanQuery mainQuery = new BooleanQuery();
//		BooleanQuery manCatalogQuery = new BooleanQuery();
//		for (int i = 0; i < CATS_MAN.length; i++) {
//			manCatalogQuery.add(new TermQuery(new Term("oclassIds",CATS_MAN[i])), Occur.SHOULD);
//		}
//		BooleanQuery normalCatalogQuery = new BooleanQuery();
//		BooleanQuery normalCatalog = new BooleanQuery();
//		for (int i = 0; i < CATS_NORMAL.length; i++) {
//			normalCatalog.add(new TermQuery(new Term("oclassIds",CATS_NORMAL[i])),Occur.SHOULD);
//		}
//		normalCatalogQuery.add(normalCatalog,Occur.MUST);
//		//normalCatalogQuery.add(new TermQuery(new Term("itemName","女")),Occur.MUST_NOT);
//		mainQuery.add(manCatalogQuery, Occur.MUST);
//		mainQuery.add(normalCatalogQuery, Occur.SHOULD);
//		if (BRAND_IDS.length>0) {
//			BooleanQuery brandQuery = new BooleanQuery();
//			for (int i = 0; i < BRAND_IDS.length; i++) {
//				brandQuery.add(new TermQuery(new Term("brandId",BRAND_IDS[i])),Occur.SHOULD);
//			}
//			mainQuery.add(brandQuery, Occur.MUST);
//		}
//		if (filters.length>0) {
//			BooleanQuery specificQuery = new BooleanQuery();
//			for (int i = 0; i < filters.length; i++) {
//				specificQuery.add(new TermQuery(new Term("attr_7000000000000027250",QueryParser.escape(filters[i]))),Occur.SHOULD);
//			}
//			mainQuery.add(specificQuery, Occur.MUST);
//		}
//		SolrQuery solrQuery = new SolrQuery();
//		solrQuery.setQuery(mainQuery.toString());
//		solrQuery.setFields("partNumber","oclassIds");
//		solrQuery.setSortField("onsaleTime", ORDER.desc);
//		solrQuery.setStart(0);
//		solrQuery.setRows(Integer.MAX_VALUE);
//
//		try {
//			long start = System.currentTimeMillis();
//			CommonsHttpSolrServer httpSolrServer = getSolrServer();
//			System.out.println("solrQuery:"+solrQuery);
//			QueryResponse response = httpSolrServer.query(solrQuery, METHOD.POST);
//			SolrDocumentList documentList = response.getResults();
//			for (Iterator<SolrDocument> iter = documentList.iterator();iter.hasNext();) {
//				SolrDocument document = iter.next();
//				allPartNumbers.put((String)document.getFieldValue("partNumber"),document.getFieldValues("oclassIds"));
//			}
//			System.out.println("total Hits:"+response.getResults().getNumFound());
//			long end = System.currentTimeMillis();
//			System.out.println("查询共计耗时："+(end-start)+"毫秒");
//		} catch (SolrServerException e) {
//			e.printStackTrace();
//		}
//		return allPartNumbers;
//	}
}
