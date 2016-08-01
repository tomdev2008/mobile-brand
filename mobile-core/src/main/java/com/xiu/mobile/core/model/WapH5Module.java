package com.xiu.mobile.core.model;

import java.io.Serializable;
import java.util.Date;

import com.xiu.mobile.core.model.BaseObject;

public class WapH5Module  extends BaseObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	
	private long pageId;
	
	private long templateId;
	
	private int rowIndex;
	
	private String content;
	
	private Date createTime;
	
	private Date lastupdateTime;
	
	private String lastEditer;
	
	private String isLazyLoad; //该组件是否懒加载 0:否，1：是
	
	//扩展字段
	private long dataCount;
	
	private String thumbnailUrl;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPageId() {
		return pageId;
	}

	public void setPageId(long pageId) {
		this.pageId = pageId;
	}

	public long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public long getDataCount() {
		return dataCount;
	}

	public void setDataCount(long dataCount) {
		this.dataCount = dataCount;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	
	public String getIsLazyLoad() {
		return isLazyLoad;
	}

	public void setIsLazyLoad(String isLazyLoad) {
		this.isLazyLoad = isLazyLoad;
	}

	
}
