package com.xiu.mobile.brand.web.bo;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 筛选区BO
 * @author Leon
 *
 */
public class FacetFilterBo {

	public enum FacetTypeEnum{
		/**
		 * 品牌
		 */
		BRAND,
		/**
		 * 价格
		 */
		PRICE,
		/**
		 * 属性
		 */
		ATTR,
		/**
		 * 发货地
		 */
		DELIVER
	}
	
	/**
	 * 当前筛选的类别，品牌，价格，属性
	 */
	private FacetTypeEnum facetType;
	/**
	 * 维度ID，价格和商品不存在ID
	 */
	private long facetId;
	/**
	 * 维度索引字段名称
	 */
	private String facetFieldName;
	/**
	 * 用于展示的名称
	 */
	private String facetDisplay;
	/**
	 * 包含的属性值
	 */
	private List<FacetFilterValueBo> facetValues;
	
	private int showOrder;
	
	/**
	 * 添加当前属性项下面的属性值<br>
	 * 品牌，价格，属性项通用
	 * @param facetValueBo
	 */
	public void addFacetValueBo(FacetFilterValueBo facetValueBo){
		if(null == facetValues) {
			facetValues = new ArrayList<FacetFilterValueBo>();
		}
			
		facetValues.add(facetValueBo);
	}
	
	public FacetTypeEnum getFacetType() {
		return facetType;
	}
	public void setFacetType(FacetTypeEnum facetType) {
		this.facetType = facetType;
	}
	public long getFacetId() {
		return facetId;
	}
	public void setFacetId(long facetId) {
		this.facetId = facetId;
	}
	
	/**
	 * json转换过滤facetFieldName
	 * @return
	 */
	@JsonIgnore
	public String getFacetFieldName() {
		return facetFieldName;
	}
	public void setFacetFieldName(String facetName) {
		this.facetFieldName = facetName;
	}
	
	public String getFacetDisplay() {
		return facetDisplay;
	}
	
	public void setFacetDisplay(String facetDisplay) {
		this.facetDisplay = facetDisplay;
	}

	/**
	 * json转换过滤showOrder
	 * @return
	 */
	@JsonIgnore
	public int getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}

	/**
	 * @return the facetValues
	 */
	public List<FacetFilterValueBo> getFacetValues() {
		return facetValues;
	}

}
