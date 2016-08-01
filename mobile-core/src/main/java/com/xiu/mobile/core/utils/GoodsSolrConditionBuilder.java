package com.xiu.mobile.core.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;

import com.xiu.mobile.core.constants.FacetPriceRangeQueryEnum;
import com.xiu.mobile.core.constants.GoodsIndexFieldEnum;
import com.xiu.mobile.core.constants.RequestInletEnum;
import com.xiu.mobile.core.constants.SearchSortOrderEnum;
import com.xiu.mobile.core.param.SearchFatParam;
import com.xiu.search.solrj.query.FacetAttrCondition;
import com.xiu.search.solrj.query.QueryFieldCondition;
import com.xiu.search.solrj.query.QueryFieldFacetCondition;
import com.xiu.search.solrj.query.QueryFieldSortCondition;

/**
 * <p>
 * ************************************************************** 
 * @Description: 搜索商品的solr条件builder类
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-12 上午10:39:34 
 * ***************************************************************
 * </p>
 */
public class GoodsSolrConditionBuilder {

	/**
	 * 构建搜索页面的主查询逻辑<br>
	 * 不带权重，只进行boolean查询
	 * TODO: 后期价格区间采用trie查询
	 * @param solrParams
	 * @return
	 */
	public static List<QueryFieldCondition> createMainCondition(SearchFatParam solrParams){
		List<QueryFieldCondition> mainConds = new ArrayList<QueryFieldCondition>();
		BooleanQuery mainBQ = new BooleanQuery();
		BooleanQuery tempBq = null;
		BooleanQuery brandBQ = null;
		BooleanQuery attrBQ = null;
		
		// 搜索关键字, 此部分仅搜索逻辑使用
		if (null != solrParams.getKeyWord()) {
			tempBq = new BooleanQuery();
			String keyword = XiuSearchStringUtils.escapeSolrMetacharactor(solrParams.getKeyWord());
			tempBq.add(new TermQuery(new Term(GoodsIndexFieldEnum.DEFAULT_SEARCH.fieldName(), "\"" + keyword + "\"~500")), Occur.MUST);
			tempBq.add(new TermQuery(new Term(GoodsIndexFieldEnum.BRAND_NAME.fieldName(), keyword)), Occur.SHOULD);
			tempBq.add(new TermQuery(new Term(GoodsIndexFieldEnum.BRAND_NAME_EN.fieldName(), keyword)), Occur.SHOULD);
			tempBq.add(new TermQuery(new Term(GoodsIndexFieldEnum.ITEM_TAGS.fieldName(), keyword)), Occur.SHOULD);
			mainBQ.add(tempBq, Occur.MUST);
		}
		
		// 走秀码条件
		if(null != solrParams.getGoodsSn()) {
			mainBQ.add(new TermQuery(new Term(GoodsIndexFieldEnum.PART_NUMBER.fieldName(),solrParams.getGoodsSn())), Occur.MUST);
		}
		
		// 拼装运营分类查询条件
		if(null != solrParams.getCatalogId()){
			mainBQ.add(new TermQuery(new Term(GoodsIndexFieldEnum.OCLASS_IDS.fieldName(),solrParams.getCatalogId().toString())), Occur.MUST);
		}
		
		// 品牌参数
		if(null != solrParams.getBrandIds() && solrParams.getBrandIds().length > 0) {
			brandBQ = new BooleanQuery();
			for(String brandId : solrParams.getBrandIds()){
				brandBQ.add(new TermQuery(new Term(GoodsIndexFieldEnum.BRAND_ID.fieldName(), brandId)), Occur.SHOULD);
			}
			mainBQ.add(brandBQ, Occur.MUST);
		}
		
		// 发货地参数
		if(null != solrParams.getDeliverIds() && solrParams.getDeliverIds().length > 0) {
			brandBQ = new BooleanQuery();
			for (String deliverId : solrParams.getDeliverIds()) {
				brandBQ.add(new TermQuery(new Term(GoodsIndexFieldEnum.SPACE_FLAG.fieldName(), deliverId)), Occur.SHOULD);
			}
			mainBQ.add(brandBQ, Occur.MUST);
		}
		
		// 属性项业务逻辑，支持多选
		if(null != solrParams.getAttrValIdList()) {
			attrBQ = new BooleanQuery();
			int num = 0;
			BooleanQuery tempQ;
			
			for(List<String> list : solrParams.getAttrValIdList()) {
				tempQ = new BooleanQuery();
				int i = 0; // 防止用户输入的数据中有空数据，用来计数
				
				for(String str : list) {
					if(str != null && !"".equals(str)) {
						num++;
						i++;
						//当筛选项值是筛选项ID如：7000000000000000073时，使用分类ID+属性筛选项ID组合字符串标识”其他“属性值
						if (solrParams.getCatalogId() !=null && str.length()==19 && Long.valueOf(str)>0) {
							str = solrParams.getCatalogId()+ str;
						}
						tempQ.add(new TermQuery(new Term(GoodsIndexFieldEnum.ATTR_IDS.fieldName(),
								XiuSearchStringUtils.escapeSolrMetacharactor(String.valueOf(str)))), Occur.SHOULD);
					}
				}
				
				if(i > 0) {
					attrBQ.add(tempQ, Occur.MUST);
				}
			}
			
			if(num > 0) {
				mainBQ.add(attrBQ, Occur.MUST);
			}
		} 
		
		// 价格范围
		if(null != solrParams.getPriceRangeEnum()) {
			mainBQ.add(solrParams.getPriceRangeEnum().getTermRangeQuery(), Occur.MUST);
		}
		
		// 自定义价格范围
		if(null != solrParams.getStartPrice() || null != solrParams.getEndPrice()) {
			String lowerTerm = null == solrParams.getStartPrice() ? "0" : String.valueOf(solrParams.getStartPrice());
			String upperTerm = null == solrParams.getEndPrice() ? "*" : String.valueOf(solrParams.getEndPrice());
			mainBQ.add(new TermRangeQuery(GoodsIndexFieldEnum.PRICE_FINAL.fieldName(), lowerTerm, upperTerm, true, true), Occur.MUST);
		}
			    	
		// 组合BQ
		QueryFieldCondition cond = new QueryFieldCondition(mainBQ);
		mainConds.add(cond);
		return mainConds;
	}
	
	/**
	 * 构建筛选区分组查询条件<br>
	 * 包含品牌、价格区间、筛选区
	 * @param brandFacetFlag 品牌筛选ID，false 不分组
	 * @param priceFacetFlag 是否需要价格区间筛选，false 不分组
	 * @param spaceFacetFlag 是否需要发货地筛选，false 不分组
	 * @param facetFields 属性项筛选数组，null 不分组
	 * @return
	 */
	public static List<QueryFieldFacetCondition> createFilterFacetCondition(
			boolean brandFacetFlag, boolean priceFacetFlag, boolean spaceFacetFlag, List<String> facetFields) {
		List<QueryFieldFacetCondition> facetConds = new ArrayList<QueryFieldFacetCondition>();
		QueryFieldFacetCondition facetCond = null;
		FacetAttrCondition facetAttrCond = null;
		
		// 品牌ID未选中，则进行品牌分组
		if (brandFacetFlag) {
			facetCond = new QueryFieldFacetCondition(GoodsIndexFieldEnum.BRAND_ID_NAME.fieldName());
			facetAttrCond = new FacetAttrCondition();
			facetAttrCond.setLimit(200);
			facetAttrCond.setMincount(1);
			facetCond.setFacetAttr(facetAttrCond);
			facetConds.add(facetCond);
		}
		
		// 价格区间未输入或选中，则进行价格区间分组true
		if (priceFacetFlag) {
			facetCond = new QueryFieldFacetCondition();
			for (FacetPriceRangeQueryEnum em : FacetPriceRangeQueryEnum.values()) {
				facetCond.addFacetQuery(em.getTermRangeQuery().toString());
			}
			facetCond.setFacetAttr(facetAttrCond);
			facetConds.add(facetCond);
		}
		
		// 发货地未选中，则进行发货地分组true
		if(spaceFacetFlag) {
			facetCond = new QueryFieldFacetCondition(GoodsIndexFieldEnum.SPACE_FLAG.fieldName());
			facetAttrCond = new FacetAttrCondition();
			facetAttrCond.setLimit(80);
			facetAttrCond.setMincount(1);
			facetCond.setFacetAttr(facetAttrCond);
			facetConds.add(facetCond);
		}
		
		// 如果需要其他筛选条件
		if (null != facetFields && facetFields.size() > 0) {
			facetCond = new QueryFieldFacetCondition(facetFields.toArray(new String[facetFields.size()]));
			facetAttrCond = new FacetAttrCondition();
			facetAttrCond.setLimit(80);
			facetAttrCond.setMincount(1);
			facetCond.setFacetAttr(facetAttrCond);
			facetConds.add(facetCond);
		}
		
		return facetConds;
	}
	
	public static QueryFieldFacetCondition createExtFacetCondition(List<String> facetFields){
		QueryFieldFacetCondition facetCond = null;
		if(null == facetFields || facetFields.size() == 0) {
			facetCond = new QueryFieldFacetCondition();
		} else {
			facetCond = new QueryFieldFacetCondition(facetFields.toArray(new String[facetFields.size()]));
		}
			
		FacetAttrCondition facetAttrCond = new FacetAttrCondition();
		facetAttrCond.setLimit(600);
		facetAttrCond.setMincount(1);
		facetCond.setFacetAttr(facetAttrCond);
		return facetCond;
	}
	
	/**
	 * 构建搜索页面的排序逻辑 <br>
	 * 上下架排序优先 <br>
	 * 1 - 价格升序 <br>
	 * 2 - 价格降序 <br>
	 * 3 - 折扣升序 <br>
	 * 4 - 折扣降序 <br>
	 * 5 - 总销量升序 <br>
	 * 6 - 总销量降序 <br>
	 * 7 - 上架时间升序 <br>
	 * 8 - 上架时间降序 <br>
	 * 9 - 得分降序/销售额降序 <br>
	 * 10 - 总销售额升序 <br>
	 * 11 - 总销售额降序 <br>
	 * 说明：当前总销售额和总销量默认存的一周的
	 * @return
	 */
	public static List<QueryFieldSortCondition> createSortCondition(SearchFatParam solrParams){
		List<QueryFieldSortCondition> sortConds = new ArrayList<QueryFieldSortCondition>();
		sortConds.add(new QueryFieldSortCondition(GoodsIndexFieldEnum.ONSALE_FLAG.fieldName(), ORDER.desc));
		SearchSortOrderEnum sort =  solrParams.getSort() == null ? SearchSortOrderEnum.SCORE_AMOUNT_DESC : solrParams.getSort();
		//默认得分降序/销售额降序
		switch (sort.getSortOrder()) {
		case 1:
			sortConds.add(new QueryFieldSortCondition(GoodsIndexFieldEnum.PRICE_FINAL.fieldName(), ORDER.asc));
			break;
			
		case 2:
			sortConds.add(new QueryFieldSortCondition(GoodsIndexFieldEnum.PRICE_FINAL.fieldName(), ORDER.desc));
			break;
			
		case 3:
			sortConds.add(new QueryFieldSortCondition(GoodsIndexFieldEnum.DISCOUNT.fieldName(), ORDER.asc));
			break;
			
		case 4:
			sortConds.add(new QueryFieldSortCondition(GoodsIndexFieldEnum.DISCOUNT.fieldName(), ORDER.desc));
			break;
			
		case 5:
			sortConds.add(new QueryFieldSortCondition(GoodsIndexFieldEnum.SALES_VOLUME.fieldName(), ORDER.asc));
			break;
			
		case 6:
			sortConds.add(new QueryFieldSortCondition(GoodsIndexFieldEnum.SALES_VOLUME.fieldName(), ORDER.desc));
			break;
			
		case 7:
			sortConds.add(new QueryFieldSortCondition(GoodsIndexFieldEnum.ONSALE_TIME.fieldName(), ORDER.asc));
			break;
			
		case 8:
			sortConds.add(new QueryFieldSortCondition(GoodsIndexFieldEnum.ONSALE_TIME.fieldName(), ORDER.desc));
			break;
			
		case 9:
			sortConds.add(new QueryFieldSortCondition(GoodsIndexFieldEnum.SCORE.fieldName(), ORDER.desc));
            sortConds.add(new QueryFieldSortCondition(GoodsIndexFieldEnum.SALES_AMOUNT.fieldName(), ORDER.desc));
            break;
        
		case 10:
			sortConds.add(new QueryFieldSortCondition(GoodsIndexFieldEnum.SALES_AMOUNT.fieldName(), ORDER.asc));
			break;
		
		case 11:
            if (RequestInletEnum.LIST_PAGE.equals(solrParams.getRequestInlet())) {
            	//分类列表页
                sortConds.add(new QueryFieldSortCondition(GoodsIndexFieldEnum.CATALOG_SCORE.fieldName() + solrParams.getCatalogId(), ORDER.desc));
            } else if (RequestInletEnum.BRAND_PAGE.equals(solrParams.getRequestInlet())) {
            	//品牌列表页
                sortConds.add(new QueryFieldSortCondition(GoodsIndexFieldEnum.BRAND_SCORE.fieldName(), ORDER.desc));
            }
            //搜索 列表页排序默认是销量倒序
            sortConds.add(new QueryFieldSortCondition(GoodsIndexFieldEnum.SALES_AMOUNT.fieldName(), ORDER.desc));
			break;
			
		default:
			sortConds.add(new QueryFieldSortCondition(GoodsIndexFieldEnum.SCORE.fieldName(), ORDER.desc));
			break;
		}
		
		return sortConds;
	}
	
}
