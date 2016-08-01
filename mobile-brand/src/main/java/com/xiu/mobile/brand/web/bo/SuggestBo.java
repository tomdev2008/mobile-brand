package com.xiu.mobile.brand.web.bo;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(关键字联想Bo) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-10-15 下午12:27:48 
 * ***************************************************************
 * </p>
 */

public class SuggestBo {

	/**
	 * 用于展示的值
	 */
	private String display;
	/**
	 * 与输入内容匹配的值
	 */
	private String matchValue;
	/**
	 * 品牌ID（如果有）
	 */
	private Long brandId;
	/**
	 * 品牌名称（如果有）
	 */
	private String brandName;
	/**
	 * 运营分类的路径（如果有）
	 */
	private String catalogName;
	/**
	 * 运营分类的ID（如果有）
	 */
	private String catalogId;
	/**
	 * 关键字匹配的商品数量
	 * 近似值，不做精准统计
	 
	private int count;
	*/
	/**
	 * SuggestModel类型
	 * case 0 : 普通的suggest
	 * case 1 : 带oclass分类的suggest
	 * case 2  ： 联想结果为品牌
	 */
	private int type;
	/**
	 * @return the display
	 */
	public String getDisplay() {
		return display;
	}
	/**
	 * @param display the display to set
	 */
	public void setDisplay(String display) {
		this.display = display;
	}
	/**
	 * @return the matchValue
	 */
	public String getMatchValue() {
		return matchValue;
	}
	/**
	 * @param matchValue the matchValue to set
	 */
	public void setMatchValue(String matchValue) {
		this.matchValue = matchValue;
	}
	/**
	 * @return the brandId
	 */
	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	public Long getBrandId() {
		return brandId;
	}
	/**
	 * @param brandId the brandId to set
	 */
	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
	/**
	 * @return the brandName
	 */
	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	public String getBrandName() {
		return brandName;
	}
	/**
	 * @param brandName the brandName to set
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	/**
	 * @return the catalogName
	 */
	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	public String getCatalogName() {
		return catalogName;
	}
	/**
	 * @param catalogName the catalogName to set
	 */
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
	/**
	 * @return the catalogId
	 */
	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	public String getCatalogId() {
		return catalogId;
	}
	/**
	 * @param catalogId the catalogId to set
	 */
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
}
