package com.xiu.mobile.brand.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.solr.client.solrj.response.FacetField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.brand.web.bo.CatalogBo;
import com.xiu.mobile.brand.web.bo.FacetFilterBo;
import com.xiu.mobile.brand.web.bo.FacetFilterBo.FacetTypeEnum;
import com.xiu.mobile.brand.web.bo.FacetFilterValueBo;
import com.xiu.mobile.brand.web.bo.GoodsItemBo;
import com.xiu.mobile.brand.web.bo.SearchBo;
import com.xiu.mobile.brand.web.model.AttrGroupJsonModel;
import com.xiu.mobile.brand.web.service.IAttrGroupService;
import com.xiu.mobile.brand.web.service.ICatalogService;
import com.xiu.mobile.brand.web.service.IFacetFilterService;
import com.xiu.mobile.brand.web.service.ISearchService;
import com.xiu.mobile.brand.web.util.CommonUtil;
import com.xiu.mobile.brand.web.util.Page;
import com.xiu.mobile.core.constants.GoodsIndexFieldEnum;
import com.xiu.mobile.core.model.GoodsSolrModel;
import com.xiu.mobile.core.param.SearchFatParam;
import com.xiu.mobile.core.service.IGoodsSolrService;
import com.xiu.search.solrj.service.SearchResult;

/**
 * <p>
 * **************************************************************
 * @Description: TODO(搜索业务逻辑实现类)
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-8-22 上午11:23:15
 * ***************************************************************
 * </p>
 */

@Service("searchService")
public class SearchServiceImpl implements ISearchService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchServiceImpl.class);
	@Autowired
	private IGoodsSolrService goodsSolrService;

	@Autowired
	private ICatalogService catalogService;
	@Autowired
	private IAttrGroupService attrGroupService;
	@Autowired
	private IFacetFilterService facetFilterService;

	@Override
	public SearchBo searchBySearchFatParam(SearchFatParam fatParam) {
		SearchBo searchBo = new SearchBo();
		// 确认来源渠道对应的运营分类,默认为官网
		GoodsIndexFieldEnum oClassPath = GoodsIndexFieldEnum.OCLASS_IDS;
		
		// 筛选项分组
		List<String> attrFacetFields = null;
		// key的类型attr_7000000000000044255 value：AttrGroupJsonModel
		Map<String, AttrGroupJsonModel> attrFacetFieldMap = null;
		if(null != fatParam.getCatalogId()) {
			attrFacetFieldMap = attrGroupService.getAttrGroupIdNameListWithInherit(fatParam.getCatalogId().longValue());
			
			if(attrFacetFieldMap != null) {
				//筛选项id集合 [attr_7000000000000027250, attr_7000000000000027249]
				attrFacetFields = new ArrayList<String>(attrFacetFieldMap.keySet());
			}
		} 
		
		// 拓展分组
		List<String> extFacetFields = new ArrayList<String>();
		extFacetFields.add(oClassPath.fieldName());
		
		SearchResult<GoodsSolrModel> searchResult = goodsSolrService.search(
				fatParam, attrFacetFields, extFacetFields);
		
		// 商品信息
		// 分页
		if(fatParam.isNeedGoodsFlag()) {
			List<GoodsItemBo> goodsItemBoList = CommonUtil.transformXiuItemBo(searchResult.getBeanList());
			searchBo.setGoodsItems(goodsItemBoList);
			fatParam.getPage().setTotalCount((int) searchResult.getTotalHits());
			
			Page page = new Page();
			page.setPageNo(fatParam.getPage().getPageNo());
			page.setPageSize(fatParam.getPage().getPageSize());
			page.setRecordCount(fatParam.getPage().getTotalCount());
			searchBo.setPage(page);
		}
		
		// 反查域处理
		if(null != searchResult.getFacetFields()) {
			dealFacetFields(searchResult.getFacetFields(), oClassPath, fatParam, searchBo);
		}
		
		//通过各筛选结果，来判断是否硬性添加”其他“筛选项
		/*try {
			this.mergeOtherAttr(searchResult, searchBo, fatParam, attrFacetFieldMap);
			this.resortAttrList(searchBo, attrFacetFieldMap);
		} catch (Exception e) {
			LOGGER.error("通过各筛选结果，来判断是否硬性添加”其他“筛选项出现异常------------>>",e);
		}*/
		
		// 生成价格范围查询的筛选, 价格整合到筛选项list中，并排到最后
		FacetFilterBo facetFilterBo = facetFilterService
				.parsePriceRangeFacetFilter(searchResult.getFacetQuery());
//{priceFinal:[8000.01 TO *]=0, priceFinal:[3000.01 TO 5000.0]=0, priceFinal:[500.01 TO 1000.0]=8, priceFinal:[0.0 TO 500.0]=108, priceFinal:[1000.01 TO 3000.0]=7, priceFinal:[5000.01 TO 8000.0]=0}
		if(null != facetFilterBo && CollectionUtils.isNotEmpty(facetFilterBo.getFacetValues())) {
			searchBo.addAttrFacetBoList(facetFilterBo);
		}
		//对筛选项排序排序
		if (searchBo != null) {
			searchBo.sortAttrFacets();
		}
		return searchBo;
	}
	/**
	 * 按照attrFacetFieldMap的顺序对searchBo中的facetFilterBo排序
	 * @param searchBo
	 * @param attrFacetFieldMap
	 */
	private void resortAttrList(SearchBo searchBo, Map<String, AttrGroupJsonModel> attrFacetFieldMap) {
		if (searchBo == null || searchBo.getAttrFacets() == null || attrFacetFieldMap==null) {
			return ;
		}
		List<FacetFilterBo> tempList = new ArrayList<FacetFilterBo>();
		List<FacetFilterBo> filterBos = searchBo.getAttrFacets();
		for (Iterator<String> iter= attrFacetFieldMap.keySet().iterator();  iter.hasNext(); ) {
			FacetFilterBo tempBo = null;
			String key = iter.next().replaceFirst("attr_", "");
			for (Iterator<FacetFilterBo> orginalIter = filterBos.iterator(); orginalIter.hasNext(); ) {
				tempBo = orginalIter.next();
				if ( tempBo!= null)  {
					if(!FacetTypeEnum.ATTR.equals(tempBo.getFacetType())){
						//如果是非attr类型的筛选项，如品牌，价格，发货地等，直接添加
						tempList.add(tempBo);
						orginalIter.remove();
					}else if(String.valueOf(tempBo.getFacetId()).equals(key)) {
						//或者是facetId和分类挂载的筛选项id一致，也添加
						tempList.add(tempBo);
						orginalIter.remove();
					}
				}
			}
		}
		searchBo.setAttrFacets(tempList);
	}
	/**
	 * 过滤掉只有一个属性值的属性项
	 * @param listBo
	 */
	private void removeOnlyOneValAttr(SearchBo searchBo) {
		List<FacetFilterBo> facetFilterBos = searchBo.getAttrFacets();
		if (facetFilterBos != null && facetFilterBos.size()>0) {
			for (Iterator<FacetFilterBo> boIter = facetFilterBos.iterator();boIter.hasNext();) {
				FacetFilterBo bo = boIter.next();
				List<FacetFilterValueBo> valueBos = bo.getFacetValues();
				if (FacetTypeEnum.ATTR.equals(bo.getFacetType()) && (valueBos == null || valueBos.size() <= 1)) {
					boIter.remove();
				}
			}
		}
	}
	/**
	 * 梳理各个属性项的“其他”属性值
	 * @param result
	 * @param listBo
	 * @param params
	 * @param attrIdNameMap
	 * @throws Exception
	 */
	private void mergeOtherAttr(SearchResult<GoodsSolrModel> result, SearchBo searchBo, SearchFatParam params, Map<String, AttrGroupJsonModel> attrIdNameMap) throws Exception{
		if (params ==null || params.getCatalogId()==null || searchBo == null || attrIdNameMap == null) {
			return;
		}
		List<FacetFilterBo> facetFilterBoList = searchBo.getAttrFacets();
		List<List<String>> selectedAttrValIds = params.getAttrValIdList();
		//已选择的过滤值
		StringBuilder filterStringBuilder = null;
		//属性项map
		Map<String, FacetFilterBo> facetFilterBoMap = new HashMap<String, FacetFilterBo>();
		//存放已经处理过的facet，用于最后添加那些只有“其他”属性值的属性项
		Set<String> attrIdCollect = new HashSet<String>();
		if (selectedAttrValIds != null && !selectedAttrValIds.isEmpty()) {
			filterStringBuilder = new StringBuilder();
			for (int i = 0; i < selectedAttrValIds.size(); i++) {
				if (selectedAttrValIds.get(i) == null || selectedAttrValIds.isEmpty()) {
					continue;
				}
				for (int j = 0; j < selectedAttrValIds.get(i).size(); j++) {
					filterStringBuilder.append(selectedAttrValIds.get(i).get(j)).append("|");
				}
				filterStringBuilder.setCharAt(filterStringBuilder.length()-1, ';');
			}
		}
		//查询分类下每项筛选项“其他”的数量
		List<Long> attrIds = new ArrayList<Long>();
		for (Iterator<String> attrIterator = attrIdNameMap.keySet().iterator();attrIterator.hasNext();) {
			String tempId = attrIterator.next();
			if (tempId != null) {
				attrIds.add(Long.valueOf(tempId.replaceFirst(GoodsIndexFieldEnum.ATTRS_PREFIX.fieldName(), "")));
			}
		}
		Map<String, Integer> attrId_CountMap = new HashMap<String, Integer>();
		SearchResult<GoodsSolrModel> otherAttrCountResult = goodsSolrService.findCurrentCatalogOtherAttrCount(params, attrIds);
		if (otherAttrCountResult != null) {
			Map<String, Integer> tempMap = otherAttrCountResult.getFacetQuery();
			if (tempMap != null) {
				for (Iterator<Map.Entry<String, Integer>> iterator = tempMap.entrySet().iterator(); iterator.hasNext();) {
					Map.Entry<String, Integer> entry = iterator.next();
					if (entry.getKey()!=null && entry.getKey().startsWith(GoodsIndexFieldEnum.ATTR_IDS.fieldName())) {
						attrId_CountMap.put(entry.getKey().replaceFirst("attrs:", "").trim(), entry.getValue());
					}
				}
			}
		}
		//检查并设置各属性项的“其他”筛选值（当前属性项未做筛选）
		if (facetFilterBoList != null) {
			for (Iterator<FacetFilterBo> boIter = facetFilterBoList.iterator();boIter.hasNext(); ) {
				int maxOrder = 0;
				FacetFilterBo facetFilterBo = boIter.next();
				facetFilterBoMap.put(facetFilterBo.getFacetId()+"", facetFilterBo);
				if (FacetTypeEnum.ATTR.equals(facetFilterBo.getFacetType())) {
					//存放分类下此属性项是否有被标记为“其他”的商品
					Integer num = attrId_CountMap.get(params.getCatalogId()+""+facetFilterBo.getFacetId())==null?0:attrId_CountMap.get(params.getCatalogId()+""+facetFilterBo.getFacetId());
					//此处删除已经处理过的经主查询facet到的分类属性筛选项
					//attrId_CountMap.remove(params.getCatalogId()+""+facetFilterBo.getFacetId());
					attrIdCollect.add(facetFilterBo.getFacetId()+"");
					if (num < 1) {
						//该分类筛选项下没有后期设置的“其他”值的商品，不需要强行设置“其他”筛选值
						continue;
					}
					List<FacetFilterValueBo> filterValueBos = facetFilterBo.getFacetValues();
					if(filterValueBos != null && filterValueBos.size()>0){
						for (Iterator<FacetFilterValueBo> filterValueBoIter = filterValueBos.iterator();filterValueBoIter.hasNext();) {
							FacetFilterValueBo filterValueBo = filterValueBoIter.next();
							//同时要删除已有的"其他"属性值，统一使用硬性添加的
							if ("其他".equals(filterValueBo.getName()) || "其它".equals(filterValueBo.getName())) {
								filterValueBoIter.remove();
								continue;
							}
							maxOrder = Math.max(maxOrder, filterValueBo.getShowOrder());
						}
						FacetFilterValueBo otherValBo = new FacetFilterValueBo();
						otherValBo.setFilter((filterStringBuilder==null?"":filterStringBuilder.toString())+facetFilterBo.getFacetId()+"");
						otherValBo.setId(String.valueOf(facetFilterBo.getFacetId()));
						otherValBo.setItemCount(num);
						otherValBo.setName("其他");
						filterValueBos.add(otherValBo);
					}
				}
			}
		}
		//检查是否筛选了“其他”属性值
		if (selectedAttrValIds != null && !selectedAttrValIds.isEmpty()) {
			List<String> singleAttrVals = null;
			for (int i = 0; i < selectedAttrValIds.size(); i++) {
				String attrId = null;
				String tempAttrVal = null;
				singleAttrVals = selectedAttrValIds.get(i);
				for (int j = 0; j < singleAttrVals.size(); j++) {
					tempAttrVal = singleAttrVals.get(j);
					//以7开头的19位字符串值，每个属性项只可能有一个“其他”
					if (tempAttrVal != null && tempAttrVal.startsWith("7") && tempAttrVal.trim().length()>=19) {
						attrId = tempAttrVal;
						break;
					}
				}
				//有进行“其他”筛选
				if (attrId != null) {
					AttrGroupJsonModel attrJsonModel = attrIdNameMap.get(GoodsIndexFieldEnum.ATTRS_PREFIX.fieldName()+attrId);
					
					//查找是否在facetAttrBo中存在
					if (facetFilterBoMap != null && !facetFilterBoMap.isEmpty()) {
						FacetFilterBo tempBo = facetFilterBoMap.get(attrId);
						//没在facetAttrList里面出现，就直接新增一个
						if (tempBo == null) {
							//此时是单选，但如果没有命中商品，那么就不用添加传到前端
							if (result.getTotalHits()<1) {
								continue;
							}
							tempBo = new FacetFilterBo();
							tempBo.setFacetFieldName(GoodsIndexFieldEnum.ATTRS_PREFIX.fieldName()+attrId);
							String display = attrIdNameMap.get(GoodsIndexFieldEnum.ATTRS_PREFIX.fieldName()+attrId).getName();
							if (display != null && display.trim().length()>4) {
								display = display.substring(0,4);
							}
							if (attrId.equals("7000000000000027249")) {
								display = "颜色";
							}else if(attrId.equals("7000000000000027250")){
								display = "尺码";
							}
							tempBo.setFacetDisplay(display);
							tempBo.setFacetId(Long.valueOf(attrId));
							tempBo.setFacetType(FacetTypeEnum.ATTR);
							tempBo.setShowOrder(attrJsonModel.getOrder());
							searchBo.addAttrFacetBoList(tempBo);
						}
						FacetFilterValueBo tempValueBo = new FacetFilterValueBo();
						tempValueBo.setFilter(filterStringBuilder==null?"":filterStringBuilder.toString());
						tempValueBo.setId(attrId);
						tempValueBo.setName("其他");
						tempValueBo.setItemCount(Long.valueOf(attrId_CountMap.get(params.getCatalogId()+""+attrId)));
						tempValueBo.setShowOrder(Integer.MAX_VALUE);
						List<FacetFilterValueBo> tempValueBoList = tempBo.getFacetValues();
						if(tempValueBoList == null){
							tempBo.addFacetValueBo(tempValueBo);
						}else {
							//多选下，加入“其他”
							boolean flag = false;
							for (int j = 0; j < tempValueBoList.size(); j++) {
								if (tempValueBoList.get(j) != null && tempValueBoList.get(j).getId().equals(attrId)) {
									flag = true;
									break;
								}
							}
							if (!flag) {
								tempValueBoList.add(tempValueBo);
							}
						}
					}else {
						//此时是单选，但如果没有命中商品，那么就不用添加传到前端
						if (result.getTotalHits()<1) {
							continue;
						}
						FacetFilterBo tempBo = new FacetFilterBo();
						tempBo.setFacetFieldName(GoodsIndexFieldEnum.ATTRS_PREFIX.fieldName()+attrId);
						String display = attrIdNameMap.get(GoodsIndexFieldEnum.ATTRS_PREFIX.fieldName()+attrId).getName();
						if (display != null && display.trim().length()>4) {
							display = display.substring(0,4);
						}
						if (attrId.equals("7000000000000027249")) {
							display = "颜色";
						}else if(attrId.equals("7000000000000027250")){
							display = "尺码";
						}
						tempBo.setFacetDisplay(display);
						tempBo.setFacetId(Long.valueOf(attrId));
						tempBo.setFacetType(FacetTypeEnum.ATTR);
						tempBo.setShowOrder(1);
						searchBo.addAttrFacetBoList(tempBo);
						FacetFilterValueBo tempValueBo = new FacetFilterValueBo();
						tempValueBo.setFilter(filterStringBuilder==null?"":filterStringBuilder.toString());
						tempValueBo.setId(attrId);
						tempValueBo.setName("其他");
						tempValueBo.setShowOrder(attrJsonModel.getOrder());
						tempValueBo.setItemCount(Long.valueOf(attrId_CountMap.get(params.getCatalogId()+""+attrId)));
						tempBo.addFacetValueBo(tempValueBo);
					}
				}
			}
		}
		//设置那些有新设置的“其他”属性值，但却没有出现在facet后的属性项值里面的属性项
		for (Iterator<Map.Entry<String, Integer>> entryIter = attrId_CountMap.entrySet().iterator();entryIter.hasNext();) {
			Map.Entry<String, Integer> entry = entryIter.next();
			String key = entry.getKey().replaceFirst("attrs:", "").replaceFirst(params.getCatalogId()+"", "");
			//只处理未被处理过的，但是通过查询，还有“其他”选项的商品
			if (attrIdCollect.contains(key)) {
				continue;
			}
			if (key != null && entry.getValue()>0){
				boolean notInFacet = true;
				if (searchBo.getAttrFacets() !=null) {
					for(Iterator<FacetFilterBo> iter= searchBo.getAttrFacets().iterator();iter.hasNext();) {
						FacetFilterBo bo = iter.next();
						if (bo != null && FacetTypeEnum.ATTR.equals(bo.getFacetType())) {
							if (key.equals(String.valueOf(bo.getFacetId()))) {
								notInFacet = false;
								break;
							}
						}
					}
				}
				if (notInFacet) {
					try {
						
						FacetFilterBo tempBo = new FacetFilterBo();
						tempBo.setFacetFieldName(GoodsIndexFieldEnum.ATTRS_PREFIX.fieldName()+key);
						String display = attrIdNameMap.get(GoodsIndexFieldEnum.ATTRS_PREFIX.fieldName()+key).getName();
						if (display != null && display.trim().length()>4) {
							display = display.substring(0,4);
						}
						if (key.equals("7000000000000027249")) {
							display = "颜色";
						}else if(key.equals("7000000000000027250")){
							display = "尺码";
						}
						tempBo.setFacetDisplay(display);
						tempBo.setFacetId(Long.valueOf(key));
						tempBo.setFacetType(FacetTypeEnum.ATTR);
						tempBo.setShowOrder(1);
						searchBo.addAttrFacetBoList(tempBo);
						FacetFilterValueBo tempValueBo = new FacetFilterValueBo();
						tempValueBo.setFilter(filterStringBuilder==null?"":filterStringBuilder.toString());
						tempValueBo.setId(key);
						tempValueBo.setName("其他");
						tempValueBo.setShowOrder(1);
						tempValueBo.setItemCount(entry.getValue());
						tempBo.addFacetValueBo(tempValueBo);
					} catch (Exception e) {
						LOGGER.error("设置新属性筛选项出现异常---------->",e);
					}
				}
			}
		}
					
	}
	/**
	 * 处理反查域
	 * @param facetFields
	 * @param searchBo
	 */
	private void dealFacetFields(List<FacetField> facetFields, GoodsIndexFieldEnum oClassPath, SearchFatParam fatParam, SearchBo searchBo) {
		Map<String, AttrGroupJsonModel> attrFacetFieldMap = null;
		if (null != fatParam.getCatalogId()) {
			attrFacetFieldMap = attrGroupService.getAttrGroupIdNameListWithInherit(fatParam.getCatalogId().longValue());
		} 
		
		FacetFilterBo facetFilterBo = null;
		for (FacetField f : facetFields) {
			
			// 品牌筛选
			if(GoodsIndexFieldEnum.BRAND_ID_NAME.fieldName().equals(f.getName())){
				facetFilterBo = facetFilterService.parseBrandFacetFilter(f);
				searchBo.addAttrFacetBoList(facetFilterBo);
			}
			
			// 运营分类
			else if (oClassPath.fieldName().equals(f.getName())) {
				List<CatalogBo> catalogBoList = catalogService.fetchCatalogBoTreeListForXiu(f, fatParam.getCatalogId());
				
				if (null != catalogBoList) {
					catalogService.removeHiddenCatalog(catalogBoList); // 去除屏蔽的分类
					searchBo.setCatalogs(catalogBoList);
				}
			} 
			
			// 发货地
			else if(GoodsIndexFieldEnum.SPACE_FLAG.fieldName().equals(f.getName())) {
				facetFilterBo = facetFilterService.parseDeliverFacetFilter(f);
				if (facetFilterBo != null && facetFilterBo.getFacetValues() != null && !facetFilterBo.getFacetValues().isEmpty()) {
					searchBo.addAttrFacetBoList(facetFilterBo);
				}
			}
			
			// 其他筛选
			else if (f.getName().indexOf(GoodsIndexFieldEnum.ATTRS_PREFIX.fieldName()) == 0) {
				facetFilterBo = facetFilterService.parseAttrFacetFilter(f, fatParam.getAttrValIdList(), attrFacetFieldMap);
				searchBo.addAttrFacetBoList(facetFilterBo);
			}
		}
	}
}
