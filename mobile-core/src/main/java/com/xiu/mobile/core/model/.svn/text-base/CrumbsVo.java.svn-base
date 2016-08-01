package com.xiu.mobile.core.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(面包屑) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-11-18 上午10:25:07 
 * ***************************************************************
 * </p>
 */

public class CrumbsVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 分类ID
	 */
	private Integer catalogId;
	
	/**
	 * 分类名称
	 */
	private String catalogName;
	
	/**
	 * 级别
	 */
	private Integer level;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((catalogId == null) ? 0 : catalogId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CrumbsVo other = (CrumbsVo) obj;
		if (catalogId == null) {
			if (other.catalogId != null)
				return false;
		} else if (!catalogId.equals(other.catalogId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
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
	 * @return the catalogName
	 */
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
	 * @return the level
	 */
	public Integer getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}


}
