package com.xiu.mobile.core.param;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.xiu.mobile.core.constants.FacetPriceRangeQueryEnum;
import com.xiu.mobile.core.constants.RequestInletEnum;
import com.xiu.mobile.core.constants.SearchSortOrderEnum;
import com.xiu.mobile.core.model.Page;

/** 
 * <p>
 * ************************************************************** 
 * @Description: 品牌搜索页面的业务条件参数
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-5 下午3:46:24 
 * ***************************************************************
 * </p>
 */

public class SearchFatParam implements Serializable {

	private static final long serialVersionUID = 7292126968743290261L;
	
	/**
	 * 用户输入关键字
	 */
	private String keyWord;
	
	/**
	 * 品牌ID
	 */
	private String[] brandIds;
	
	/**
	 * 分类ID
	 */
	private Integer catalogId;
	
	/**
	 * 发货地ID
	 */
	private String[] deliverIds;
	
	/**
	 * 走秀码
	 */
	private String goodsSn;
	
	/**
	 * 属性值的ID,支持多选
	 */
	private List<List<String>> attrValIdList;
	
	/**
	 * 价格区间
	 */
	private FacetPriceRangeQueryEnum priceRangeEnum;
	
	/**
	 * 起始价格
	 */
	private Double startPrice;
	
	/**
	 * 结束价格
	 */
	private Double endPrice;
	
	/**
	 * 排序
	 */
	private SearchSortOrderEnum sort;
	
	/**
	 * 翻页
	 */
	private Page<?> page;
	
	/**
	 * 请求入口（排序使用）
	 */
	private RequestInletEnum requestInlet;
	
	/**
	 * 是否需要查询商品信息
	 * default true
	 */
	private boolean needGoodsFlag = true;
	
	/**
	 * 是否需要反查分类信息
	 * default true
	 */
	private boolean needCatalogFlag = true;
	
	/**
	 * 是否需要反查筛选项信息
	 * default true
	 */
	private boolean needAttrFlag = true;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * @return the keyWord
	 */
	public String getKeyWord() {
		return keyWord;
	}

	/**
	 * @param keyWord the keyWord to set
	 */
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	/**
	 * @return the attrValIdList
	 */
	public List<List<String>> getAttrValIdList() {
		return attrValIdList;
	}

	/**
	 * @param attrValIdList the attrValIdList to set
	 */
	public void setAttrValIdList(List<List<String>> attrValIdList) {
		this.attrValIdList = attrValIdList;
	}

	/**
	 * @return the priceRangeEnum
	 */
	public FacetPriceRangeQueryEnum getPriceRangeEnum() {
		return priceRangeEnum;
	}

	/**
	 * @param priceRangeEnum the priceRangeEnum to set
	 */
	public void setPriceRangeEnum(FacetPriceRangeQueryEnum priceRangeEnum) {
		this.priceRangeEnum = priceRangeEnum;
	}

	/**
	 * @return the startPrice
	 */
	public Double getStartPrice() {
		return startPrice;
	}

	/**
	 * @param startPrice the startPrice to set
	 */
	public void setStartPrice(Double startPrice) {
		this.startPrice = startPrice;
	}

	/**
	 * @return the endPrice
	 */
	public Double getEndPrice() {
		return endPrice;
	}

	/**
	 * @param endPrice the endPrice to set
	 */
	public void setEndPrice(Double endPrice) {
		this.endPrice = endPrice;
	}

	/**
	 * @return the sort
	 */
	public SearchSortOrderEnum getSort() {
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(SearchSortOrderEnum sort) {
		this.sort = sort;
	}

	/**
	 * @return the page
	 */
	public Page<?> getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(Page<?> page) {
		this.page = page;
	}

	/**
	 * @return the requestInlet
	 */
	public RequestInletEnum getRequestInlet() {
		return requestInlet;
	}

	/**
	 * @param requestInlet the requestInlet to set
	 */
	public void setRequestInlet(RequestInletEnum requestInlet) {
		this.requestInlet = requestInlet;
	}

	/**
	 * @return the needGoodsFlag
	 */
	public boolean isNeedGoodsFlag() {
		return needGoodsFlag;
	}

	/**
	 * @param needGoodsFlag the needGoodsFlag to set
	 */
	public void setNeedGoodsFlag(boolean needGoodsFlag) {
		this.needGoodsFlag = needGoodsFlag;
	}

	/**
	 * @return the needCatalogFlag
	 */
	public boolean isNeedCatalogFlag() {
		return needCatalogFlag;
	}

	/**
	 * @param needCatalogFlag the needCatalogFlag to set
	 */
	public void setNeedCatalogFlag(boolean needCatalogFlag) {
		this.needCatalogFlag = needCatalogFlag;
	}

	/**
	 * @return the needAttrFlag
	 */
	public boolean isNeedAttrFlag() {
		return needAttrFlag;
	}

	/**
	 * @param needAttrFlag the needAttrFlag to set
	 */
	public void setNeedAttrFlag(boolean needAttrFlag) {
		this.needAttrFlag = needAttrFlag;
	}

	/**
	 * @return the goodsSn
	 */
	public String getGoodsSn() {
		return goodsSn;
	}

	/**
	 * @param goodsSn the goodsSn to set
	 */
	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	/**
	 * @return the brandIds
	 */
	public String[] getBrandIds() {
		return brandIds;
	}

	/**
	 * @param brandIds the brandIds to set
	 */
	public void setBrandIds(String[] brandIds) {
		this.brandIds = brandIds;
	}

	/**
	 * @return the catalogId
	 */
	public Integer getCatalogId() {
		return catalogId;
	}

	/**
	 * @param catalogId the catalogId to set
	 */
	public void setCatalogId(Integer catalogId) {
		this.catalogId = catalogId;
	}

	/**
	 * @return the deliverIds
	 */
	public String[] getDeliverIds() {
		return deliverIds;
	}

	/**
	 * @param deliverIds the deliverIds to set
	 */
	public void setDeliverIds(String[] deliverIds) {
		this.deliverIds = deliverIds;
	}


}
