package com.xiu.mobile.brand.web.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.schema.DateField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.brand.web.bo.ExclusiveProductBo;
import com.xiu.mobile.brand.web.cache.ExclusiveConfigureCache;
import com.xiu.mobile.brand.web.cache.impl.ExclusiveConfigureCacheImpl;
import com.xiu.mobile.brand.web.controller.params.ExclusiveParam;
import com.xiu.mobile.brand.web.service.IExclusiveService;
import com.xiu.mobile.brand.web.util.CommonUtil;
import com.xiu.mobile.brand.web.util.Page;
import com.xiu.mobile.core.constants.GoodsIndexFieldEnum;
import com.xiu.mobile.core.model.GoodsSolrModel;
import com.xiu.mobile.core.model.XiuSKUIndexModel;
import com.xiu.search.solrj.service.GenericSolrService;
import com.xiu.search.solrj.service.SearchResult;
@Service("exclusiveService")
public class ExclusiveServiceImpl implements IExclusiveService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExclusiveServiceImpl.class);
	
	//风格对应的类型
	private static final String BRAND = "brand";
	private static final String CATEGORY = "category";
	
	private ExclusiveConfigureCache exclusiveConfigureCache = ExclusiveConfigureCacheImpl.getInstance();
	@Autowired
	private GenericSolrService genericSolrService;
	@Override
	public ExclusiveProductBo getExclusiveProducts(ExclusiveParam exclusiveParam) throws SolrServerException, IllegalArgumentException {
			exclusiveParam = resolveSearchDate(exclusiveParam);
			SolrQuery solrQuery = parseExclusiveQuery(exclusiveParam);
			System.out.println(solrQuery);
			ExclusiveProductBo exclusiveProductBo = new ExclusiveProductBo();
			SearchResult<GoodsSolrModel> results = genericSolrService.findAll(GoodsSolrModel.class, solrQuery);
			List<GoodsSolrModel> goods = results.getBeanList();
			//目前的逻辑是过滤掉第一页之后的所有没有可售尺码在当前选择的尺码条件中的商品
			if (exclusiveParam.getPageIndex() >1) {
				filterGoodsWithoutSize(exclusiveParam,goods);
			}
			exclusiveProductBo.setGoods(CommonUtil.transformXiuItemBo(null,goods));
			exclusiveProductBo.setUpdateTime(exclusiveParam.getShowDate());
			Page page = new Page();
			page.setPageSize(exclusiveParam.getPageSize());
			page.setRecordCount((int)results.getTotalHits());
			page.setPageNo(exclusiveParam.getPageIndex());
			exclusiveProductBo.setPage(page);
			return exclusiveProductBo;
	}
	
	/**
	 * 过滤掉没有可售尺码在当前选择的尺码条件中的商品
	 * @param exclusiveParam
	 * @param goods
	 */
	private void filterGoodsWithoutSize(ExclusiveParam exclusiveParam, List<GoodsSolrModel> goods) {
		//商品集合不能为空
		if (goods == null || goods.isEmpty()) {
			return;
		}
		List<String> totalSizes = new ArrayList<String>();
		Map<String, List<String>> totalSizesMap = exclusiveParam.getCategorySizes();
		//分类尺寸集合不能为空
		if (totalSizesMap == null || totalSizesMap.isEmpty()) {
			return ;
		}
		for (Iterator<List<String>> iter = totalSizesMap.values().iterator();iter.hasNext();) {
			List<String> sizes = iter.next();
			if (sizes != null && !sizes.isEmpty()) {
				totalSizes.addAll(sizes);
			}
		}
		//尺寸总集合不能为空
		if (totalSizes.isEmpty()) {
			return ;
		}
		List<String> itemIdList = new ArrayList<String>();
		for (GoodsSolrModel good : goods) {
			itemIdList.add(good.getItemId());
		}
		SolrQuery solrQuery = new SolrQuery();
		SearchResult<XiuSKUIndexModel> searchResult = null;
		BooleanQuery query = new BooleanQuery();
		for(int i=0;i<itemIdList.size();i++){
			if (itemIdList.get(i) !=null && !"".equals(itemIdList.get(i))) {
				query.add(new TermQuery(new Term("itemId", itemIdList.get(i))),Occur.SHOULD);//并集
			}
		}
		solrQuery.setQuery(query.toString());
		solrQuery.setRows(Integer.MAX_VALUE);
		solrQuery.setFields("skuSize","itemId","skuQty");
		try {
			searchResult = genericSolrService.findAll(XiuSKUIndexModel.class, solrQuery);
			List<XiuSKUIndexModel> xiuskus = searchResult.getBeanList();
			Map<String, Set<String>> itemid_sku_map = new HashMap<String, Set<String>>();
			Map<String, Set<String>> all_itemid_sku_map = new HashMap<String, Set<String>>();
			if (xiuskus != null) {
				Set<String> sizeList = null;
				Set<String> itemAllsizeList = null;
				XiuSKUIndexModel model = null;
				for(int i=0;i<xiuskus.size();i++){
					model = xiuskus.get(i);
					itemAllsizeList = all_itemid_sku_map.get(model.getItemId());
					if (itemAllsizeList == null) {
						itemAllsizeList = new HashSet<String>();
						all_itemid_sku_map.put(model.getItemId(), itemAllsizeList);
					}
					itemAllsizeList.add(model.getSkuSize());
					//当库存大于0才记录
					if (model.getQty() != null && model.getQty()>0) {
						sizeList = itemid_sku_map.get(model.getItemId());
						if (sizeList == null) {
							sizeList = new HashSet<String>();
							itemid_sku_map.put(model.getItemId(), sizeList);
						}
						sizeList.add(model.getSkuSize());
					}
				}
			}
			List<String> waitToRemoveIds = new ArrayList<String>();
			//遍历商品所有尺码的MAP
			for (Iterator<Entry<String, Set<String>>> itemSizeIter = all_itemid_sku_map.entrySet().iterator();itemSizeIter.hasNext();) {
				Entry<String, Set<String>> entry = itemSizeIter.next();
				Set<String> sizes = entry.getValue();
				//如果查询的尺码包含商品的所有尺码中的某一个，如果商品的尺码都不在查询的尺码范围内，如US6等尺码，不做处理
				if (CollectionUtils.containsAny(totalSizes, sizes)) {
					Set<String> hasInventorySize = itemid_sku_map.get(entry.getKey());
					//如果商品没有可售尺码（此处指库存为0）
					if (hasInventorySize == null) {
						waitToRemoveIds.add(entry.getKey());
					}else {
						//如果有可售尺码，但是可售尺码不在查询的尺码范围内
						if (!CollectionUtils.containsAny(totalSizes, hasInventorySize)) {
							waitToRemoveIds.add(entry.getKey());
						}
					}
				}
			}
			Set<String> keySet = itemid_sku_map.keySet() == null?new HashSet<String>():itemid_sku_map.keySet();
			//中性分类,如手表等，不过滤
			List<String> category_normal = exclusiveConfigureCache.getNormalSexCategory();
			List<String> goodsCategories = null;
			for (Iterator<GoodsSolrModel> goodsIter = goods.iterator(); goodsIter.hasNext(); ) {
				GoodsSolrModel goodModel = goodsIter.next();
				goodsCategories = goodModel.getOclassIds()==null?null:Arrays.asList(goodModel.getOclassIds());
				if (goodsCategories != null) {
					//商品被挂在了中性运营分类上，那么不过滤这类商品
					if (CollectionUtils.containsAny(goodsCategories, category_normal)) {
						continue;
					}
				}
				if (waitToRemoveIds.contains(goodModel.getItemId()) || !keySet.contains(goodModel.getItemId())) {
					goodsIter.remove();
				}
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
			LOGGER.error("获取尺码异常：", e);
		}
	}
	private ExclusiveParam resolveSearchDate(ExclusiveParam exclusiveParam) {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		int DAY_OF_WEEK = calendar.get(Calendar.DAY_OF_WEEK);
		switch (DAY_OF_WEEK) {
		case Calendar.MONDAY:
			//星期六零点之前
			calendar.add(Calendar.DAY_OF_WEEK, -2);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE), 0, 0, 0);
			break;
		case Calendar.TUESDAY:
		case Calendar.WEDNESDAY:
		case Calendar.THURSDAY:
			//当天零点之前
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE), 0, 0, 0);
			break;
		case Calendar.FRIDAY:
			//星期四中午12点
			calendar.add(Calendar.DAY_OF_WEEK, -1);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE), 12, 0, 0);
			break;
		case Calendar.SATURDAY:
			//星期五零点之前
			calendar.add(Calendar.DAY_OF_WEEK, -1);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE), 0, 0, 0);
			break;
		case Calendar.SUNDAY:
			//星期五中午12点前
			calendar.add(Calendar.DAY_OF_WEEK, -2);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE), 12, 0, 0);
			break;
		default:
			break;
		}
		//System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
		exclusiveParam.setSearchDate(calendar.getTime());
		Calendar calendar2 = Calendar.getInstance(Locale.CHINA);
		calendar2.set(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH), calendar2.get(Calendar.DATE), 0, 0, 0);
		calendar2.add(Calendar.DATE, -1);
		exclusiveParam.setShowDate(new SimpleDateFormat("M月d日 24:00 更新").format(calendar2.getTime()));
		return exclusiveParam;
	}
	
	private SolrQuery parseExclusiveQuery(ExclusiveParam param) throws IllegalArgumentException{
		if (param == null || param.getSex() == null) {
			throw new IllegalArgumentException("请求参数错误");
		}
		final String STYLE_TARGET_TYPE = exclusiveConfigureCache.getStyleTargetType(param.getSex());
		//性别参数（男/女）
		List<String> category_sex = exclusiveConfigureCache.getSexCategory(param.getSex());
		//中性分类
		List<String> category_normal = exclusiveConfigureCache.getNormalSexCategory();
		Map<Set<String>, Set<String>> category_sizes = null;
		Set<String> category_styles = null;
		//风格参数
		if (param.getStyles() != null) {
			category_styles = new HashSet<String>();
			for (int i = 0; i < param.getStyles().size(); i++) {
				String style = param.getStyles().get(i);
				//选择了风格为分类，且风格为服装
				List<String> tempStyles = exclusiveConfigureCache.getStyleTargets(param.getSex(), style);
				if (CATEGORY.equalsIgnoreCase(STYLE_TARGET_TYPE) && style != null && ("201".equals(style) || style == "201")) {
					if (param.getCategorySizes() != null) {
						Set<String> category_size_temp = param.getCategorySizes().keySet();
						//是否选择了女上装/女下装
						if (category_size_temp.contains("2001") || category_size_temp.contains("2002")) {
							if (!category_size_temp.contains("2001")) {
								List<String> temSizeCats = exclusiveConfigureCache.getSizeCategories(param.getSex(), "2001");
								if (temSizeCats != null) {
									tempStyles.removeAll(temSizeCats);
								}
							}
							if (!category_size_temp.contains("2002")) {
								List<String> temSizeCats = exclusiveConfigureCache.getSizeCategories(param.getSex(), "2002");
								if (temSizeCats != null) {
									tempStyles.removeAll(temSizeCats);
								}
							}
							
						}
					}
				}
				if (tempStyles != null) {
					category_styles.addAll(tempStyles);
				}
			}
		}
		//尺码分类关系
		if (param.getCategorySizes() != null) {
			category_sizes = new HashMap<Set<String>,Set<String>>();
			for (Iterator<Map.Entry<String, List<String>>> iter = param.getCategorySizes().entrySet().iterator();iter.hasNext();) {
				Set<String> sizeCategories = new HashSet<String>();
				Set<String> sizes = new HashSet<String>();
				Entry<String, List<String>> entry = iter.next();
				String key = entry.getKey();
				List<String> sizeNames = entry.getValue();
				List<String> tempSizeCat = exclusiveConfigureCache.getSizeCategories(param.getSex(), key);
				if (tempSizeCat == null || tempSizeCat.isEmpty()) {
					continue;
				}
				sizeCategories.addAll(tempSizeCat);
				//为空时，表示尺码分类未选中尺码的情况
				if (sizeNames != null) {
					List<String> tempSizeVals = exclusiveConfigureCache.getSizeValues(sizeNames);
					if (tempSizeVals != null) {
						for (int i = 0; i < tempSizeVals.size(); i++) {
							if(null != tempSizeVals.get(i))							
								sizes.add(tempSizeVals.get(i));
						}
					}
				}
				category_sizes.put(sizeCategories, sizes);
			}
		}
		//如果有选中风格，并且风格指定的对象是分类的话
		if (category_styles != null && CATEGORY.equalsIgnoreCase(STYLE_TARGET_TYPE)) {
			//如果有选中尺码，那么：1-删除尺码分类中未在风格分类中指定的分类，2-删除风格分类中同时在尺码和风格分类中出现的分类
			if (category_sizes != null) {
				List<String> tempCatIds = new ArrayList<String>();
				for (Iterator<Set<String>> iter = category_sizes.keySet().iterator(); iter.hasNext(); ) {
					Set<String> sizeCatSet = iter.next();
					for (Iterator<String> categoryIter = sizeCatSet.iterator();categoryIter.hasNext(); ) {
						String cid = categoryIter.next();
						//删除1
						if (!category_styles.contains(cid)) {
							categoryIter.remove();
						}else {
							tempCatIds.add(cid);
						}
					}
				}
				//删除2
				if (category_styles != null) {
					category_styles.removeAll(tempCatIds);
				}
			}else {
				//如果没有选中尺码，不用处理
			}
			//男女分类无意义，使用风格中出现但在尺码中未出现的分类作为查询分类
			category_sex.clear();
			if (category_styles != null) {
				category_sex.addAll(category_styles);
			}
			category_styles = null;
			//中性分类也无意义
			category_normal = null;
		}else {
			//要么没有选中风格，要么选中的风格指定的是品牌，此时只需处理尺码与性别之间的分类关系
			if (category_sex != null && category_sizes != null) {
				for (Iterator<String> sexCatIter = category_sex.iterator();sexCatIter.hasNext();) {
					String categoryId = sexCatIter.next();
					for (Iterator<Set<String>> sizeCatKeyIter = category_sizes.keySet().iterator();sizeCatKeyIter.hasNext();) {
						Set<String> sizeCatSet = sizeCatKeyIter.next();
						if (sizeCatSet.contains(categoryId)) {
							sexCatIter.remove();
							break;
						}
					}
				}
			}
		}
		//如果风格指定的是分类，并且通用分类不为空，直接置空
		if (CATEGORY.equalsIgnoreCase(STYLE_TARGET_TYPE) && category_normal != null) {
			category_normal = null;
		}
 		return generateQuery(category_sex, category_sizes, category_styles, category_normal, param);
	}
	private SolrQuery generateQuery(List<String> category_sex, Map<Set<String>, Set<String>> category_sizes, 
			Set<String> category_styles, List<String> category_normal, ExclusiveParam param) {
		BooleanQuery mainQuery = new BooleanQuery();
		BooleanQuery categoryQuery = new BooleanQuery();
		//性别指定的分类
		if (category_sex != null && !category_sex.isEmpty()) {
			BooleanQuery sexQuery = new BooleanQuery();
			for (int i = 0; i < category_sex.size(); i++) {
				sexQuery.add(new TermQuery(new Term(GoodsIndexFieldEnum.OCLASS_IDS.fieldName(),category_sex.get(i))),Occur.SHOULD);
			}
			categoryQuery.add(sexQuery,Occur.SHOULD);
		}
		//尺码指定的分类
		if (category_sizes != null && !category_sizes.isEmpty()) {
			for (Iterator<Entry<Set<String>, Set<String>>> iter = category_sizes.entrySet().iterator();iter.hasNext();) {
				Entry<Set<String>, Set<String>> cats_sizes = iter.next();
				Set<String> cats = cats_sizes.getKey();
				Set<String> sizes = cats_sizes.getValue();
				//只有尺码分类没有尺码值的时候，表示前端未选中分类的尺码
				if ((cats ==null ||cats.isEmpty()) || (sizes == null || sizes.isEmpty())) {
					continue;
				}
				BooleanQuery sizeCatQuery = new BooleanQuery();
				BooleanQuery catQuery = new BooleanQuery();
				for (Iterator<String> catIter = cats.iterator();catIter.hasNext();) {
					catQuery.add(new TermQuery(new Term(GoodsIndexFieldEnum.OCLASS_IDS.fieldName(),catIter.next())),Occur.SHOULD);
				}
				sizeCatQuery.add(catQuery,Occur.MUST);
				BooleanQuery sizeQuery = new BooleanQuery();
				for (Iterator<String> sizeIter = sizes.iterator();sizeIter.hasNext();) {
					String sizeVal = sizeIter.next();
					if (sizeVal != null && !"".equals(sizeVal)) {
						sizeQuery.add(new TermQuery(new Term(GoodsIndexFieldEnum.ATTR_IDS.fieldName(),QueryParser.escape(sizeVal))),Occur.SHOULD);
					}
				}
				sizeCatQuery.add(sizeQuery,Occur.MUST);
				categoryQuery.add(sizeCatQuery,Occur.SHOULD);
			}
		}
		//中性分类
		if (category_normal != null && !category_normal.isEmpty()) {
			BooleanQuery normalQuery = new BooleanQuery();
			BooleanQuery normalSexQuery = new BooleanQuery();
			for (int i = 0; i < category_normal.size(); i++) {
				normalSexQuery.add(new TermQuery(new Term(GoodsIndexFieldEnum.OCLASS_IDS.fieldName(),category_normal.get(i))),Occur.SHOULD);
			}
			normalQuery.add(normalSexQuery, Occur.MUST);
			normalQuery.add(new TermQuery(new Term(GoodsIndexFieldEnum.ITEM_NAME.fieldName(), "man".equalsIgnoreCase(param.getSex())?"女":"男")),Occur.MUST_NOT);
			categoryQuery.add(normalQuery, Occur.SHOULD);
		}
		if (categoryQuery.getClauses().length>0) {
			mainQuery.add(categoryQuery, Occur.MUST);
		}
		//风格指定的品牌（当风格指定的是分类的时候，category_style=null)
		if (category_styles != null && !category_styles.isEmpty()) {
			BooleanQuery styleQuery = new BooleanQuery();
			for (Iterator<String> iterator = category_styles.iterator();iterator.hasNext();) {
				styleQuery.add(new TermQuery(new Term(GoodsIndexFieldEnum.BRAND_LEVEL.fieldName(), iterator.next())),Occur.SHOULD);
			}
			mainQuery.add(styleQuery, Occur.MUST);
		}
		mainQuery.add(new TermQuery(new Term(GoodsIndexFieldEnum.ONSALE_FLAG.fieldName(),"1")), Occur.MUST);
		SolrQuery solrQuery = new SolrQuery(mainQuery.toString());
		solrQuery.setFilterQueries(GoodsIndexFieldEnum.ONSALE_TIME.fieldName()+":[* TO "+new DateField().toExternal(param.getSearchDate()).replaceAll("\\.\\d+", "")+"]");
		solrQuery.setStart((param.getPageIndex()-1)*param.getPageSize());
		solrQuery.setRows(param.getPageSize());
		solrQuery.setSortField(GoodsIndexFieldEnum.ONSALE_TIME.fieldName(), ORDER.desc);
		return solrQuery;
	}
	public static void main(String[] args) {
		System.out.println(Integer.MAX_VALUE);
	}
}
