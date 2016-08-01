package com.xiu.mobile.core.model;

import java.io.Serializable;

import com.xiu.mobile.core.model.BaseObject;

public class WapH5PageTemplate  extends BaseObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	
	private String name;
	
	private String templateIds;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTemplateIds() {
		return templateIds;
	}

	public void setTemplateIds(String templateIds) {
		this.templateIds = templateIds;
	}
	
}
