package com.xiu.mobile.brand.web.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.CommonParams;

import com.xiu.mobile.brand.web.bo.LabelSearchBo;
import com.xiu.mobile.brand.web.bo.TypeSuggestBo;
import com.xiu.mobile.brand.web.util.Page;
import com.xiu.mobile.core.model.XiuLabelInfoModel;
import com.xiu.search.solrj.service.SolrHttpService;

public class XiuLabelSolrClient{
	private Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * 获取所有分类对应分组
	 * @param key 搜索关键字
	 * @param size 每个分组返回条数
	 * @return
	 */
	public static LabelSearchBo getLabelListByType(String key,String type,int p,int pSize)
	{
		LabelSearchBo result = new LabelSearchBo();
		CommonsHttpSolrServer solrServer = SolrHttpService.getInstance().getSolrServer(XiuLabelInfoModel.class);
    	SolrQuery solrQuery= new SolrQuery();
    	int start = pSize*(p-1);
    	solrQuery.setRows(pSize);
		solrQuery.setStart(start);
		solrQuery.setSortField("sort_info",ORDER.desc);
		if(key !=null)
    	{
    		solrQuery.set(CommonParams.Q, "searchable:"+key+" AND type:"+type);
    	}
	    else
	    {
	    	solrQuery.set(CommonParams.Q, "type:"+type);
	    }
		
		try {
			QueryResponse response = solrServer.query(solrQuery, METHOD.POST);
			//未找到结果处理
			if (response == null || response.getResults() == null || response.getResults().size() <= 0) {
				return null;
			}
			List<XiuLabelInfoModel> labelList = response.getBeans(XiuLabelInfoModel.class);
			if(labelList !=null)
			{
				TypeSuggestBo bo;
				List<TypeSuggestBo> boList = new ArrayList<TypeSuggestBo>();
				for(XiuLabelInfoModel l :labelList)
				{
					bo = new TypeSuggestBo();
					bo.setAccessesAddress(l.getContent());
					bo.setDisplay(l.getName());
					bo.setType(l.getType());
					boList.add(bo);
				}
				result.setTypeSuggestBos(boList);
			}
			Integer totalCount = Integer.valueOf(response.getResults().getNumFound()+"");
			int pageCount=0;
			if(totalCount % pSize ==0)
			{
				pageCount = totalCount/pSize;
			}
			else
			{
				pageCount =totalCount/pSize+1;
			}
			Page page = new Page();
			page.setRecordCount(totalCount);
			page.setPageNo(p);
			page.setPageSize(pSize);
			page.setPageCount(pageCount);
			result.setPage(page);
			return result;
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取所有分类对应分组
	 * @param key 搜索关键字
	 * @param size 每个分组返回条数
	 * @return
	 */
	public static Map<String,List<XiuLabelInfoModel>> searchLable(String key,int size)
	{
		Map<String,List<XiuLabelInfoModel>> map = new HashMap<String,List<XiuLabelInfoModel>>();
		List<String> types = getGroupValueList(key,"type");
		if(types !=null)
		{
			CommonsHttpSolrServer solrServer = SolrHttpService.getInstance().getSolrServer(XiuLabelInfoModel.class);
	    	SolrQuery solrQuery;
			for(String type : types)
			{
				solrQuery	= new SolrQuery();
			    solrQuery.setRows(size);
				solrQuery.setStart(0);
				solrQuery.setSortField("sort_info",ORDER.desc);
			    if(key !=null)
		    	{
		    		solrQuery.set(CommonParams.Q, "searchable:"+key+" AND type:"+type);
		    	}
			    else
			    {
			    	solrQuery.set(CommonParams.Q, "type:"+type);
			    }
			    System.out.println(solrQuery.toString());
			    try {
					QueryResponse response = solrServer.query(solrQuery, METHOD.POST);
					if (response == null || response.getResults() == null || response.getResults().size() <= 0) {
						break;
					}
					List<XiuLabelInfoModel> labelList = response.getBeans(XiuLabelInfoModel.class);
					map.put(type, labelList);
				} catch (SolrServerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return map;
		}
		
		return null;
	}
	
	/**
	 * 获取该字段的对应的值列表
	 * @param key
	 * @return
	 */
	public static List<String>getGroupValueList(String key,String field)
	{
		CommonsHttpSolrServer solrServer = SolrHttpService.getInstance().getSolrServer(XiuLabelInfoModel.class);
    	SolrQuery solrQuery	= new SolrQuery();
    	if(key !=null)
    	{
    		solrQuery.set(CommonParams.Q, "searchable:"+key);
    	}
    	else
    	{
    		solrQuery.set(CommonParams.Q, "*:*");
    	}
    	solrQuery.set("fl", "type");
		solrQuery.setRows(1);
		solrQuery.setStart(0);
		solrQuery.setFacet(true);
		solrQuery.addFacetField(field);
		solrQuery.setFacetMinCount(1);
		System.out.println(solrQuery.toString());
		try {
			QueryResponse response = solrServer.query(solrQuery, METHOD.POST);
			FacetField typeField = response.getFacetField(field);
			if(typeField != null)
			{
				List<Count> count = typeField.getValues();
				if(count !=null)
				{
					List<String> reuslt = new ArrayList<String>();
					for(Count c :count )
					{
						reuslt.add(c.getName());
					}
					return reuslt;
				}
			}
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
