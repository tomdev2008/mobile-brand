package com.xiu.mobile.core.model;

import java.io.Serializable;
/**
 * 发现频道支持版本表
 *
 */
public class FindSupportVersion implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long findMenuId;//发现频道ID
	
	private String appSource;//app名称
	
	private String appSystem;//系统
	
	private String startVersion;//开始版本
	
	private String lastVersion;//结束版本
	
	private String createTime;//创建时间
	
	private Integer type;//类型 1首页引导图 ，2广告

	public Long getFindMenuId() {
		return findMenuId;
	}

	public void setFindMenuId(Long findMenuId) {
		this.findMenuId = findMenuId;
	}

	public String getAppSource() {
		return appSource;
	}

	public void setAppSource(String appSource) {
		this.appSource = appSource;
	}

	public String getAppSystem() {
		return appSystem;
	}

	public void setAppSystem(String appSystem) {
		this.appSystem = appSystem;
	}

	public String getStartVersion() {
		return startVersion;
	}

	public void setStartVersion(String startVersion) {
		this.startVersion = startVersion;
	}

	public String getLastVersion() {
		return lastVersion;
	}

	public void setLastVersion(String lastVersion) {
		this.lastVersion = lastVersion;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	

}
