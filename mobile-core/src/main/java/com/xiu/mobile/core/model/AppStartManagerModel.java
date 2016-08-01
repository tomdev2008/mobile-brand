package com.xiu.mobile.core.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:app启用页管理
 * @author: coco.long
 * @date: 2015-03-23
 */
public class AppStartManagerModel extends BaseObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long appType;
	
	private String channel;
	
	private Date startTime;
	
	private Long status;
	
	private Date createDate;
	
	private String pageUrl;
	
	private String pageUrlA;
	
	//生效时间字符串
	private String beginTime;
	
	//前台使用状态
	private String useStatus;
	
	//返回给前端图片地址
	private String url;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAppType() {
		return appType;
	}
	public void setAppType(Long appType) {
		this.appType = appType;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getUseStatus() {
		return useStatus;
	}
	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus;
	}
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public String getPageUrlA() {
		return pageUrlA;
	}
	public void setPageUrlA(String pageUrlA) {
		this.pageUrlA = pageUrlA;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
