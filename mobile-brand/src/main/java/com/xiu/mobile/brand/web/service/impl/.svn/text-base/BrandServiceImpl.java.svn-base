package com.xiu.mobile.brand.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.brand.web.cache.XiuBrandInfoCache;
import com.xiu.mobile.brand.web.dao.model.XBrandModel;
import com.xiu.mobile.brand.web.service.IBrandService;
import com.xiu.mobile.core.model.GoodsSolrModel;
import com.xiu.search.solrj.service.GenericSolrService;
import com.xiu.search.solrj.service.SearchResult;

/** 
 * <p>
 * ************************************************************** 
 * @Description: 品牌业务实现类
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-4 下午2:02:29 
 * ***************************************************************
 * </p>
 */
@Service("brandService")
public class BrandServiceImpl implements IBrandService {
	private static final Logger logger = Logger.getLogger(BrandServiceImpl.class);
	
	@Autowired
	private XiuBrandInfoCache xiuBrandInfoCache;
	@Autowired
	private GenericSolrService genericSolrService;
	
	@Override
	public XBrandModel getBrandInfoById(long brandId) {
		XBrandModel brandModel = xiuBrandInfoCache.getBrandById(brandId);
		return brandModel;
	}

	@Override
	public XBrandModel getBrandHasGoodsByName(String name) {
		XBrandModel brandModel = xiuBrandInfoCache.getBrandHasGoodsByName(name);
		return brandModel;
	}

	@Override
	public List<XBrandModel> getBrandInfo() {
		List<Long> brandIds = getBrandId();
		logger.info("搜索查询到品牌ID个数：========"+brandIds.size()+"========");
		List<XBrandModel> brandList = new ArrayList<XBrandModel>(brandIds.size());
		HashMap<Long, XBrandModel> map = xiuBrandInfoCache.getBrandIDs();
		XBrandModel xbrand =null;
		int count =0;
		for(int i=0;i<brandIds.size();i++){
			xbrand = map.get(brandIds.get(i));
			if(xbrand!=null){
				brandList.add(xbrand);
				count++;
			}
		}
        logger.info("缓存中实际匹配到存在品牌ID、品牌中文名、品牌英文名的个数：======="+count+"=======");
		return brandList;
	}
	/**
	 * 索引中获取所有品牌ID
	 * @return
	 */
	private List<Long> getBrandId(){
		List<Long> brandIds = new ArrayList<Long>();
		SolrQuery solrQuery = new SolrQuery();
		
		solrQuery.setQuery("stateOnsale:1");
		solrQuery.setRows(0);
		//品牌id分组
		solrQuery.setFacet(true);
		solrQuery.addFacetField("brandId");
		solrQuery.setFacetLimit(Integer.MAX_VALUE);
		SearchResult<GoodsSolrModel> result = null;
		List<Count> countList = null;
		Long start = 0l;
		Long end = 0l;
		try {
			start = System.currentTimeMillis();
			result = genericSolrService.findAll(GoodsSolrModel.class, solrQuery);
			end = System.currentTimeMillis();
			logger.info("搜索耗时："+(end-start)+"(秒)");
			countList = result.getFacetField("brandId").getValues();
			if(CollectionUtils.isNotEmpty(countList)){
				for(Count c :countList){
					brandIds.add(Long.parseLong(c.getName()));
				}
			}
		} catch (SolrServerException e) {
			logger.error("搜索异常"+e);
			e.printStackTrace();
		}
		return brandIds;
	}


}
