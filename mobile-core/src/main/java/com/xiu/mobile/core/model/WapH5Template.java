package com.xiu.mobile.core.model;

import java.io.Serializable;
import java.util.Date;

import com.xiu.mobile.core.model.BaseObject;

public class WapH5Template  extends BaseObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String name;
	
	private String template;
	
	private String dataTemplate;
	
	private String thumbnailUrl;
	
	private String jsUrl;
	
	private String cssUrl;
	
	private Date createTime;
	
	private Date lastupdateTime;
	
	private String lastEditer;
	
	private int state;
	
	private String templateHandler;
	
	private int templateWeight;

	public int getTemplateWeight() {
		return templateWeight;
	}

	public void setTemplateWeight(int templateWeight) {
		this.templateWeight = templateWeight;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getDataTemplate() {
		return dataTemplate;
	}

	public void setDataTemplate(String dataTemplate) {
		this.dataTemplate = dataTemplate;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getJsUrl() {
		return jsUrl;
	}

	public void setJsUrl(String jsUrl) {
		this.jsUrl = jsUrl;
	}

	public String getCssUrl() {
		return cssUrl;
	}

	public void setCssUrl(String cssUrl) {
		this.cssUrl = cssUrl;
	}

	public Date getLastupdateTime() {
		return lastupdateTime;
	}

	public void setLastupdateTime(Date lastupdateTime) {
		this.lastupdateTime = lastupdateTime;
	}

	public String getLastEditer() {
		return lastEditer;
	}

	public void setLastEditer(String lastEditer) {
		this.lastEditer = lastEditer;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getTemplateHandler() {
		return templateHandler;
	}

	public void setTemplateHandler(String templateHandler) {
		this.templateHandler = templateHandler;
	}
	

}
