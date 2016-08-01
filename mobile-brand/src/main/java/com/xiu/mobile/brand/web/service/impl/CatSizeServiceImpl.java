package com.xiu.mobile.brand.web.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.brand.web.cache.CatalogTreeCache;
import com.xiu.mobile.brand.web.model.CatAndSizeModel;
import com.xiu.mobile.brand.web.model.CatalogModel;
import com.xiu.mobile.brand.web.service.ICatSizeService;
import com.xiu.mobile.core.model.GoodsSolrModel;
import com.xiu.mobile.core.model.XiuSKUIndexModel;
import com.xiu.search.solrj.service.GenericSolrService;
import com.xiu.search.solrj.service.SearchResult;
/**
 * 通过goodsID获取商品的一级分类及尺码
 * @author rian.luo@xiu.com
 *
 * 2015-9-14
 */
@Service
public class CatSizeServiceImpl implements ICatSizeService {
	@Autowired
	private GenericSolrService genericSolrService;
	
	private Logger LOGGER = Logger.getLogger(CatSizeServiceImpl.class);
	
	/**
	 * 获取商品的一级运营分类
	 * @param itemIdList
	 * @return
	 */
	private List<CatAndSizeModel> listCatSize1(List<String> itemIdList) {
 		BooleanQuery query = new BooleanQuery();
		for(int i=0;i<itemIdList.size();i++){
			query.add(new TermQuery(creatTerm(itemIdList.get(i))),Occur.SHOULD);//逻辑或
		}
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery(query.toString());
	    solrQuery.setFields("itemId","partNumber","oclassPath","oclassIds");//指定返回字段
	    solrQuery.setRows(Integer.MAX_VALUE);
		//分类
		SearchResult<GoodsSolrModel> searchResult = null;
		
		List<CatAndSizeModel> casList = new ArrayList<CatAndSizeModel>();
		CatAndSizeModel  casModel = new CatAndSizeModel();
		try {
			searchResult = genericSolrService.findAll(GoodsSolrModel.class, solrQuery);
			SolrDocumentList documentList = searchResult.getResults();
			for (Iterator<SolrDocument> iter = documentList.iterator();iter.hasNext();) {
				SolrDocument document = iter.next();
				casModel = new CatAndSizeModel();
				Collection<Object> coll = document.getFieldValues("itemId");
				String itemId = (String)coll.iterator().next();
				casModel.setGoodsId(itemId);
				Collection<Object> xiuSn = document.getFieldValues("partNumber");
				casModel.setGoodsSn((String)xiuSn.iterator().next());
				Collection<Object> oclassPath = document.getFieldValues("oclassPath");
				
				if(oclassPath!=null){
					String[] oclassPathArray = oclassPath.toString().split("\\|");
					String first = oclassPathArray[0];
					int index = first.indexOf(":");
					casModel.setCategoryId(first.substring(1, index));
					casModel.setCategoryName(first.substring(index+1));
				}else{
					Collection<Object> aclassId = document.getFieldValues("oclassIds");
					//遍历分类ID catModel的父分类为0表示该分类ID为一级分类 aclassId:运营分类中既包含一级分类二级分类三级分类
					if(aclassId!=null){
						for(Iterator<Object> iter3 = aclassId.iterator();iter3.hasNext();){
							String cat = (String)iter3.next();
							CatalogModel  catModel = CatalogTreeCache.getInstance().getTreeNodeByIdFromTreeMap0(cat);
							if(catModel == null){
								continue;
							}
							int parentId = catModel.getParentCatalogId();
							if(parentId==0){
								if(StringUtils.isNotBlank(catModel.getCatalogName())){
									casModel.setCategoryId(cat);
									//通过分类ID 去查询该分类的名称
									casModel.setCategoryName(catModel.getCatalogName());
								}
								break;
							}
							
						}
					}
				}
				casList.add(casModel);
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
			LOGGER.error("获取一级分类异常：", e);
		}
		return casList;
	}
	/**
	 * 分类与尺码
	 */
	public List<CatAndSizeModel> listCatSize(List<String> itemIdList){
		List<CatAndSizeModel> resultList = new ArrayList<CatAndSizeModel>();
		int num=0;
		int total = itemIdList.size();
		while(num<total){
			List<String> itemIdLis = new ArrayList<String>();
			itemIdLis = itemIdList.subList(num, (num+200)>total ? total:(num+200));
			num +=200;
			Map<String,Set<String>> resultMap = getSize(itemIdLis);
			List<CatAndSizeModel> list = listCatSize1(itemIdLis);
			for(int i=0;i<list.size();i++){
				String itemID = list.get(i).getGoodsId();
				list.get(i).setSizeList(resultMap.get(itemID));
			}
			resultList.addAll(list);
		}
		return resultList;
	}
	/**
	 * 根据itemId 商品id获取该商品的sku级别的尺码
	 * @param itemIdList itemId集合
	 * @return
	 */
	private Map<String,Set<String>> getSize(List<String> itemIdList){
		Map<String,Set<String>> resultMap = new HashMap<String, Set<String>>();
		SearchResult<XiuSKUIndexModel> searchResult = null;
		BooleanQuery query = new BooleanQuery();
		for(int i=0;i<itemIdList.size();i++){
			query.add(new TermQuery(creatTerm(itemIdList.get(i))),Occur.SHOULD);//逻辑或
		}
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery(query.toString());
		solrQuery.setRows(Integer.MAX_VALUE);
		solrQuery.setFields("skuSize","itemId");
		try {
			searchResult = genericSolrService.findAll(XiuSKUIndexModel.class, solrQuery);
			List<XiuSKUIndexModel> xiuSkUIndexModel = searchResult.getBeanList();
			Set<String> sizeList = null;
			XiuSKUIndexModel model = null;
			//商品可能对应多个尺码，所以尺码存入set集合中
			for(int i=0;i<xiuSkUIndexModel.size();i++){
				model = xiuSkUIndexModel.get(i);
				if(model != null){
					sizeList = resultMap.get(model.getItemId());
					if(sizeList == null){
						sizeList = new HashSet<String>();
						resultMap.put(model.getItemId(), sizeList);
					}
					sizeList.add(model.getSkuSize());
				}
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
			LOGGER.error("获取尺码异常：", e);
		}
		return resultMap;
	}
	
	/*private SolrDocumentList getSolrDocumentList(String itemId){
		SearchResult<XiuSKUIndexModel> searchResult2 = null;
		BooleanQuery query2 = new BooleanQuery();
		query2.add(new TermQuery(new Term("itemId",itemId)),Occur.MUST);
		SolrQuery solrQuery2 = new SolrQuery();
		solrQuery2.setQuery(query2.toString());
		solrQuery2.setRows(Integer.MAX_VALUE);
		try {
			searchResult2 = genericSolrService.findAll(XiuSKUIndexModel.class, solrQuery2);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return searchResult2.getResults();
	}*/
	
	private Term creatTerm(String key){
		if(StringUtils.isNotBlank(key)){
			return new Term("itemId",key.trim().toString());
		}else{
			return new Term(key);
		}
	}
	
}
