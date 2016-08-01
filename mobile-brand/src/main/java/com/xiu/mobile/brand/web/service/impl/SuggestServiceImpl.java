package com.xiu.mobile.brand.web.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.brand.web.bo.CatalogBo;
import com.xiu.mobile.brand.web.bo.LabelSearchBo;
import com.xiu.mobile.brand.web.bo.SearchHisMapBo;
import com.xiu.mobile.brand.web.bo.SuggestBo;
import com.xiu.mobile.brand.web.bo.TypeSuggestBo;
import com.xiu.mobile.brand.web.dao.model.XBrandModel;
import com.xiu.mobile.brand.web.model.CatalogModel;
import com.xiu.mobile.brand.web.search.XiuLabelSolrClient;
import com.xiu.mobile.brand.web.service.IBrandService;
import com.xiu.mobile.brand.web.service.ICatalogService;
import com.xiu.mobile.brand.web.service.ISuggestService;
import com.xiu.mobile.brand.web.service.params.SuggestFatParam;
import com.xiu.mobile.brand.web.util.Constants;
import com.xiu.mobile.brand.web.util.Page;
import com.xiu.mobile.core.model.XiuLabelInfoModel;
import com.xiu.mobile.core.utils.XiuSearchHisMapUtil;
import com.xiu.mobile.core.utils.XiuSynonymsUtil;
import com.xiu.search.solrj.query.QueryFieldCondition;
import com.xiu.search.solrj.query.SolrQueryBuilder;
import com.xiu.search.solrj.service.SolrHttpService;
import com.xiu.solr.lexicon.client.common.MktTypeEnum;
import com.xiu.solr.lexicon.client.model.LexiconBaseModel;
import com.xiu.solr.lexicon.client.model.SuggestModel;
import com.xiu.solr.lexicon.client.service.SuggestService;
import com.xiu.solr.lexicon.client.util.LexiconQueryUtil;
import com.xiu.solr.lexicon.client.util.LexiconStringUtils;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(关键字联想业务逻辑实现类) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-10-14 下午4:24:57 
 * ***************************************************************
 * </p>
 */
@Service("suggestService")
public class SuggestServiceImpl implements ISuggestService {

	@Autowired
	private IBrandService brandService;
	@Autowired
	private SuggestService suggestClient;
	@Autowired
	private ICatalogService catalogService;
	
	@Autowired
	private LianXiangServiceImpl lianXiangService;
	
	@Override
	public List<SuggestBo> suggest(SuggestFatParam param) {
		List<SuggestBo> suggestBos = new ArrayList<SuggestBo>();
		String keyWord = XiuSynonymsUtil.getValue(param.getKeyword());
		List<SuggestModel> suggestModels = 
				suggestClient.findSuggest(keyWord, param.getMaxRows(), 2, false, MktTypeEnum.XIU);
		
		if(CollectionUtils.isEmpty(suggestModels)) {
			return suggestBos;
		}
		
		for(SuggestModel sm : suggestModels) {
			SuggestBo suggest = new SuggestBo();
			String diplayName = XiuSearchHisMapUtil.transValue(sm.getDisplay());
			suggest.setDisplay(diplayName);
			suggest.setMatchValue(sm.getMatchValue());
			
			if(StringUtils.isNotBlank(sm.getCatalogId())) {
				suggest.setCatalogId(sm.getCatalogId());
				String secondCatalogName = catalogService.getSecondCatalogName(Integer.parseInt(sm.getCatalogId()));
				if(StringUtils.isNotBlank(secondCatalogName)){
					suggest.setCatalogName(secondCatalogName+" "+sm.getCatalogName());
				}else{
					suggest.setCatalogName(sm.getCatalogName());
				}
				suggest.setType(sm.getType());
			} else {
				XBrandModel brand = brandService.getBrandHasGoodsByName(sm.getDisplay());
				
				if(brand != null) {
					suggest.setBrandId(brand.getBrandId());
					suggest.setBrandName(brand.getMainName());
					suggest.setType(2);
				}
			}
			
			suggestBos.add(suggest);
		}
		
		// 排序，将品牌的联想放到前面
		Collections.sort(suggestBos, new Comparator<SuggestBo>() {
			@Override
			public int compare(SuggestBo o1, SuggestBo o2) {
				if(o1.getType() == 2 && o2.getType() != 2) {
					return -1;
				} else if(o1.getType() != 2 && o2.getType() == 2) {
					return 1;
				}
				return 0;
			}  
		});
		return suggestBos;
	}
	
	@Override
	public List<SearchHisMapBo> getSearchHistoryMap() {
		List<SearchHisMapBo> searchHisMapBos = new ArrayList<SearchHisMapBo>();
		for(String searchHiskey :XiuSearchHisMapUtil.getProperties().stringPropertyNames()){
			SearchHisMapBo searchHisMapBo = new SearchHisMapBo();
			searchHisMapBo.setKey(searchHiskey);
			searchHisMapBo.setValue(XiuSearchHisMapUtil.getValue(searchHiskey));
			searchHisMapBos.add(searchHisMapBo);
		}
		return searchHisMapBos;
	}

	@Override
	public Map<String, List<TypeSuggestBo>> getLabelList(String key, int size) {
		
		Map<String, List<TypeSuggestBo>> result = new HashMap<String, List<TypeSuggestBo>>();
		//从label搜索符合的标签项
		Map<String, List<XiuLabelInfoModel>> labelMap = XiuLabelSolrClient.searchLable(key, size);
		List<TypeSuggestBo> boList;
		TypeSuggestBo bo;
		List<XiuLabelInfoModel> labels;
		if(labelMap !=null)
		{
			Set<String> types = labelMap.keySet();
			for(String t :types)
			{
				labels = labelMap.get(t);
				boList = new ArrayList<TypeSuggestBo>();
				for(XiuLabelInfoModel l :labels)
				{
					bo = new TypeSuggestBo();
					bo.setAccessesAddress(l.getContent());
					bo.setDisplay(l.getName());
					bo.setType(l.getType());
					boList.add(bo);
				}
				result.put(t, boList);
			}
		}
		//从原有联想词获取标签
		result.put("1", lianXiangService.findSuggest(key, "1", size));
		result.put("2", lianXiangService.findSuggest(key, "2", size));
		return result;
	}

	@Override
	public LabelSearchBo getLabelListByType(String key, String type,
			int p, int pSize) {
		//type >2 走标签接口 其他走旧联想接口
		if(Integer.valueOf(type) >2)
		{
			return XiuLabelSolrClient.getLabelListByType(key, type, p, pSize);
		}
		//旧接口联想
		else
		{
			return lianXiangService.findSuggest(key, type, pSize, p);
		}
	}

}
