package com.xiu.mobile.brand.web.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.brand.web.bo.LabelSearchBo;
import com.xiu.mobile.brand.web.bo.SuggestBo;
import com.xiu.mobile.brand.web.bo.TypeSuggestBo;
import com.xiu.mobile.brand.web.dao.model.XBrandModel;
import com.xiu.mobile.brand.web.service.IBrandService;
import com.xiu.mobile.brand.web.service.ICatalogService;
import com.xiu.mobile.brand.web.service.ILianXiangService;
import com.xiu.mobile.brand.web.util.Page;
import com.xiu.mobile.core.utils.XiuSearchHisMapUtil;
import com.xiu.mobile.core.utils.XiuSynonymsUtil;
import com.xiu.search.solrj.query.QueryFieldCondition;
import com.xiu.search.solrj.query.SolrQueryBuilder;
import com.xiu.search.solrj.service.SolrHttpService;
import com.xiu.solr.lexicon.client.common.MktTypeEnum;
import com.xiu.solr.lexicon.client.model.LexiconBaseModel;
import com.xiu.solr.lexicon.client.model.SuggestModel;
import com.xiu.solr.lexicon.client.util.LexiconDataUtil;
import com.xiu.solr.lexicon.client.util.LexiconStringUtils;

/**
 * 继承联想词类实现分页查询
 * @author Administrator
 *
 */
@Service("lianXiangService")
public class LianXiangServiceImpl extends com.xiu.solr.lexicon.client.service.SuggestServiceImpl implements ILianXiangService{

	@Autowired
	private IBrandService brandService;
	@Autowired
	private ICatalogService catalogService;
	
	/**
	 * 获取指定类别的联想词
	 * @param q
	 * @param type 1品牌 2分类
	 * @param size 条数
	 * @return
	 */
	public List<TypeSuggestBo> findSuggest(String q,String type,int size)
	{
		int oclassRows=2;
		MktTypeEnum mktTypeEnum=MktTypeEnum.XIU;
		//q="浪凡";
		//1品牌 2分类
		String search="";
		if("1".equals(type))
		{
			search="-brand_id:[* TO *]";
		}
		else if("2".equals(type))
		{
			search="-oclass_xiu:[* TO *]";
		}
		if(null == q)
		{
			q=search;
		}	
		else
			q=q+" AND "+search;
		if(LexiconStringUtils.isBlank(q))
			return null;
		List<SuggestModel> ret = null;
		SolrQueryBuilder sqb = new SolrQueryBuilder();
		// 添加主搜索 
		QueryFieldCondition condition = new QueryFieldCondition(q);
		sqb.addMainQueryClauses(condition);
		
		// 添加条数*7防止排重后数据量不足
		sqb.setRows(size*7);
		sqb.setStart(0);
		// 添加查询字段
		if(null == mktTypeEnum){
			sqb.addFields(new String[]{"name","py","s_py","type","brand_id"});
		}else{
			sqb.addFields(new String[]{"name","py","s_py","oclass_"+mktTypeEnum.name().toLowerCase(),"oclass_count_"+mktTypeEnum.name().toLowerCase(),"count_"+mktTypeEnum.name().toLowerCase(),"type","brand_id"});
		}
		
		SolrQuery solrQuery = sqb.solrQueryBuilder();
		if(null != mktTypeEnum)
			solrQuery.set("mktType", mktTypeEnum.name());
		System.out.println("查询命令为:"+solrQuery.toString());
		// 查询
		List<LexiconBaseModel> lexiconList = null;
		try {
			CommonsHttpSolrServer solrServer = SolrHttpService.getInstance().getSolrServer(LexiconBaseModel.class);
			QueryResponse response = solrServer.query(solrQuery, METHOD.POST);
			if (response == null || response.getResults() == null || response.getResults().size() <= 0) {
				return null;
			}
			lexiconList = response.getBeans(LexiconBaseModel.class);
		
		} catch (SolrServerException e) {
			LOGGER.error("访问词典库索引出错", e);
		}
		if(null == lexiconList || lexiconList.size()==0)
			return null;
		ret = new ArrayList<SuggestModel>();
		SuggestModel sm;		
	    // 循环,组装展示对象
		for (LexiconBaseModel lex : lexiconList) {
		
			for(String name:lex.getNameList())
			{
				sm = new SuggestModel();
				sm.setDisplay(name);
				sm.setCount(lex.getCount(mktTypeEnum));
				//1品牌 2分类
				if("1".equals(type))
				{
					sm.setType(1);
					//sm.setBrandId(lex.getBrandId());
					ret.add(sm);
				}
				else if("2".equals(type)){
					sm.setType(2);
					formatOclassSuggestItem(ret, sm,lex, oclassRows,mktTypeEnum);
				}
			}
			
		}
		List<TypeSuggestBo> result = change2TypeSuggestBo(ret);
		if(result.size()>size)
			return result.subList(0, size);
		else
			return result;
	}
	
	
	public LabelSearchBo findSuggest(String q,String type, int pSize,int p) {
		
		int oclassRows=2;
		MktTypeEnum mktTypeEnum=MktTypeEnum.XIU;
		//q="浪凡";
		//1品牌 2分类
		String search="";
		if("1".equals(type))
		{
			search="-brand_id:[* TO *]";
		}
		else if("2".equals(type))
		{
			search="-oclass_xiu:[* TO *]";
		}
		if(null == q)
		{
			q=search;
		}	
		else
			q=q+" AND "+search;
		if(LexiconStringUtils.isBlank(q))
			return null;
		LabelSearchBo result = new LabelSearchBo();
		List<SuggestModel> ret = null;
		SolrQueryBuilder sqb = new SolrQueryBuilder();
		// 添加主搜索 
		QueryFieldCondition condition = new QueryFieldCondition(q);
		sqb.addMainQueryClauses(condition);
		
		// 添加条数
		sqb.setRows(pSize);
		int start = pSize*(p-1);
		sqb.setRows(pSize);
		sqb.setStart(start);
		// 添加查询字段
		if(null == mktTypeEnum){
			sqb.addFields(new String[]{"name","py","s_py","type","brand_id"});
		}else{
			sqb.addFields(new String[]{"name","py","s_py","oclass_"+mktTypeEnum.name().toLowerCase(),"oclass_count_"+mktTypeEnum.name().toLowerCase(),"count_"+mktTypeEnum.name().toLowerCase(),"type","brand_id"});
		}
		
		SolrQuery solrQuery = sqb.solrQueryBuilder();
		// 设置访问类型 设置后无法过滤type ~
		//solrQuery.set("lexiconType", SUGGEST_LEXICON_TYPE);
//		if(q !=null)
//    	{
//    		solrQuery.set(CommonParams.Q, "name:"+q+"* AND type:"+type);
//    	}
//	    else
//	    {
//	    	solrQuery.set(CommonParams.Q, "type:"+type);
//	    }
		if(null != mktTypeEnum)
			solrQuery.set("mktType", mktTypeEnum.name());
		System.out.println("查询命令为:"+solrQuery.toString());
		// 查询
		List<LexiconBaseModel> lexiconList = null;
		try {
			CommonsHttpSolrServer solrServer = SolrHttpService.getInstance().getSolrServer(LexiconBaseModel.class);
			QueryResponse response = solrServer.query(solrQuery, METHOD.POST);
			if (response == null || response.getResults() == null || response.getResults().size() <= 0) {
				return null;
			}
			lexiconList = response.getBeans(LexiconBaseModel.class);
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
		
		} catch (SolrServerException e) {
			LOGGER.error("访问词典库索引出错", e);
		}
		if(null == lexiconList || lexiconList.size()==0)
			return null;
		ret = new ArrayList<SuggestModel>();
		SuggestModel sm;		
		// 处理转义
		//String parseQ = LexiconQueryUtil.trimLeft(LexiconQueryUtil.replaceBlankSpecialCharactorForSuggest(q)).toLowerCase();
		// 循环,组装展示对象
		for (LexiconBaseModel lex : lexiconList) {
		
			for(String name:lex.getNameList())
			{
				sm = new SuggestModel();
				sm.setDisplay(name);
				sm.setCount(lex.getCount(mktTypeEnum));
				//1品牌 2分类
				if("1".equals(type))
				{
					sm.setType(1);
					//sm.setBrandId(lex.getBrandId());
					ret.add(sm);
				}
				else if("2".equals(type)){
					sm.setType(2);
					formatOclassSuggestItem(ret, sm,lex, oclassRows,mktTypeEnum);
				}
			}
			
		}
		
		result.setTypeSuggestBos(change2TypeSuggestBo(ret));
		return result;
	}
	
	private List<TypeSuggestBo> change2TypeSuggestBo(List<SuggestModel> suggestModels)
	{
		List<SuggestBo> list = this.change2SuggestBo(suggestModels);
		List<TypeSuggestBo> boList = new ArrayList<TypeSuggestBo>();
		//去重标识~
		Map<String,String> quchong = new HashMap<String,String>();
		TypeSuggestBo bo;
		for(SuggestBo s :list)
		{
			bo = new TypeSuggestBo();
			bo.setType(s.getType());
			if(s.getType()==1)
			{
				bo.setAccessesAddress(s.getBrandId()+"");
				bo.setDisplay(s.getBrandName());
				if(quchong.get("1_"+s.getBrandId())==null)
				{
					quchong.put("1_"+s.getBrandId(), "1");
					boList.add(bo);
				}
					
			}
			else if(s.getType()==2)
			{
				bo.setAccessesAddress(s.getCatalogId());
				bo.setDisplay(s.getCatalogName());
				if(quchong.get("2_"+s.getCatalogId())==null)
				{
					quchong.put("2_"+s.getCatalogId(), "1");
					boList.add(bo);
				}
			}
		}
		quchong.clear();
		return boList;
	}
	
	private List<SuggestBo> change2SuggestBo(List<SuggestModel> suggestModels)
	{
		List<SuggestBo> suggestBos = new ArrayList<SuggestBo>();
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
				//suggest.setCatalogName(sm.getCatalogName());
				suggest.setType(sm.getType());
				suggestBos.add(suggest);
			} else {
				String key = XiuSynonymsUtil.getValue(sm.getDisplay());
				XBrandModel brand = brandService.getBrandHasGoodsByName(key);
				if(brand != null) {
					suggest.setBrandId(brand.getBrandId());
					suggest.setBrandName(brand.getMainName());
					suggest.setType(sm.getType());
					suggestBos.add(suggest);
				}
				
			}
			
			
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
	
	/**
	 * 格式化带有OClass类型的SuggestModel
	 * @param smList
	 * @param lexiconModel
	 * @param oclassRows
	 */
	private void formatOclassSuggestItem(List<com.xiu.solr.lexicon.client.model.SuggestModel> smList,SuggestModel sm,LexiconBaseModel lexiconModel,int oclassRows,MktTypeEnum mktTypeEnum){
		if(oclassRows<=0 || null == lexiconModel.getOclassList(mktTypeEnum) || lexiconModel.getOclassList(mktTypeEnum).size()<=0) return;
		List<String> pathList = lexiconModel.getOclassList(mktTypeEnum);
		List<Integer> pathCountList = lexiconModel.getOclassCountList(mktTypeEnum);
		int len = pathList.size();
		String oId;
		String oPath;
		Integer pathCount;
		SuggestModel smT;
		for (int i = 0; i < len && i < oclassRows; i++) {
			smT  = sm.clone();
			if(smT == null){
				LOGGER.error("SuggestModel克隆失败");
				return;
			}
			oId = LexiconDataUtil.getOclassId(pathList.get(i));
			oPath = this.getSuggestOclassShowPath(pathList.get(i));
			if(null != pathCountList && i < pathList.size()){
				pathCount = pathCountList.get(i);
				smT.setCatalogItemCount(pathCount);
			}
			smT.setCatalogId(oId);
			smT.setCatalogName(oPath);
			smT.setType(sm.getType());
			smList.add(smT);
		}
	}
	
	/**
	 * 输入oclasspath路径得到显示路径
	 * @return
	 */
	private String getSuggestOclassShowPath(String path){
		try {
//			String[] arr = path.split("\\|");
			String[] arr = LexiconStringUtils.split(path, '|');
			StringBuffer sb = new StringBuffer(arr.length*2);
			for (int i = 0; i < arr.length; i++) {
				if(i > 0)sb.append("/");
//				sb.append(arr[i].split(":")[1]);
				sb.append(LexiconStringUtils.split(arr[i], ':')[1]);
			}
			return sb.toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
