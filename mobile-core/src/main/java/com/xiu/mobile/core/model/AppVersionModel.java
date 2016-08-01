/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-27 上午11:04:18 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.mobile.core.model;

import java.util.Date;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(版本更新信息Model) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-27 上午11:04:18 
 * ***************************************************************
 * </p>
 */

public class AppVersionModel {
	
	/**
	 * ID
	 */
	private Long id;
	
	/**
	 * 版本名称
	 */
	private String name;
	
	/**
	 * 更新内容
	 */
	private String content;
	
	/**
	 * 发布时间
	 */
	private Date pubTime;
	
	/**
	 * 客户端类型
	 */
	private Integer type;
	
	/**
	 * 客户端下载链接地址
	 */
	private String url;
	
	/**
	 * 版本号
	 */
	private Long versionNo;
	
	/**
	 * 状态（启用|禁用）
	 */
	private Integer status; 
	
	/**
	 * 是否强制更新
	 */
	private Integer forcedUpdate;
	
	/**
	 * 强制更新版本号
	 */
	private Integer forcedBeforeVerno;
	

	/**
	 * 无参构造器
	 */
	public AppVersionModel() {
		super();
	}
	
	/**
	 * 完整构造器
	 * @param id
	 * @param name
	 * @param content
	 * @param pubTime
	 * @param type
	 * @param url
	 * @param versionNo
	 * @param status
	 * @param forcedUpdate
	 */
	public AppVersionModel(Long id, String name, String content, Date pubTime,
			Integer type, String url, Long versionNo, Integer status,
			Integer forcedUpdate, Integer forcedBeforeVerno) {
		super();
		this.id = id;
		this.name = name;
		this.content = content;
		this.pubTime = pubTime;
		this.type = type;
		this.url = url;
		this.versionNo = versionNo;
		this.status = status;
		this.forcedUpdate = forcedUpdate;
		this.forcedBeforeVerno = forcedBeforeVerno;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		AppVersionModel other = (AppVersionModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "AppVersionModel [id=" + id + ", name=" + name + ", content="
				+ content + ", pubTime=" + pubTime + ", type=" + type
				+ ", url=" + url + ", versionNo=" + versionNo + ", status="  
				+ status + ", forcedUpdate=" + forcedUpdate 
				+ ", forcedBeforeVerno=" + forcedBeforeVerno +"]";
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the pubTime
	 */
	public Date getPubTime() {
		return pubTime;
	}

	/**
	 * @param pubTime the pubTime to set
	 */
	public void setPubTime(Date pubTime) {
		this.pubTime = pubTime;
	}

	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the versionNo
	 */
	public Long getVersionNo() {
		return versionNo;
	}

	/**
	 * @param versionNo the versionNo to set
	 */
	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the forcedUpdate
	 */
	public Integer getForcedUpdate() {
		return forcedUpdate;
	}

	/**
	 * @param forcedUpdate the forcedUpdate to set
	 */
	public void setForcedUpdate(Integer forcedUpdate) {
		this.forcedUpdate = forcedUpdate;
	}
	
	public Integer getForcedBeforeVerno() {
		return forcedBeforeVerno;
	}

	public void setForcedBeforeVerno(Integer forcedBeforeVerno) {
		this.forcedBeforeVerno = forcedBeforeVerno;
	}

}
