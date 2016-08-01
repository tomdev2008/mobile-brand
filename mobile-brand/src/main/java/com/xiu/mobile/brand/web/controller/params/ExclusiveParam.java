package com.xiu.mobile.brand.web.controller.params;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExclusiveParam {
	private String sex;
	private Map<String, List<String>> categorySizes;
	private List<String> styles;
	private String showDate;
	private Date searchDate;
	private int pageIndex;
	private int pageSize;
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Map<String, List<String>> getCategorySizes() {
		return categorySizes;
	}
	public void setCategorySizes(Map<String, List<String>> categorySizes) {
		this.categorySizes = categorySizes;
	}
	public List<String> getStyles() {
		return styles;
	}
	public void setStyles(List<String> styles) {
		this.styles = styles;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getShowDate() {
		return showDate;
	}
	public void setShowDate(String showDate) {
		this.showDate = showDate;
	}
	public Date getSearchDate() {
		return searchDate;
	}
	public void setSearchDate(Date searchDate) {
		this.searchDate = searchDate;
	}
	
}
