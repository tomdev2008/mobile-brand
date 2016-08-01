package com.xiu.mobile.core.model;

import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.beans.Field;

public class XiuLabelInfoModel {
	
	@Field("id")
	private String id;     //主键id
	@Field("service_id")   
	private String serviceId;   //业务id
	@Field("name")
	private String name;     //名称
	@Field("type")
	private int type;     //分类（1、卖场 2、专题 3、H5）
	@Field("label_ids")
	private List<String> labelIds;     //标签id集合
	@Field("label_names")
	private List<String> labelNames;     //标签名称集合
	@Field("content")
	private String content;     // 内容（H5存放链接其他存放id）
	@Field("py")
	private List<String> py;     //拼音预留
	@Field("s_py")
	private List<String> sPy;     //拼音首字母预留
	@Field("sort_info")
	private Integer sortInfo;      //排序信息
	@Field("time_start")
	private Date timeStart;     //业务开始时间
	@Field("time_end")
	private Date timeEnd;     //业务结束时间
	@Field("create_time")
	private Date createTime;      //业务创建时间
	@Field("solr_create_date")
	private Date solrCreateDate;     //solr中的创建时间
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public List<String> getLabelIds() {
		return labelIds;
	}
	public void setLabelIds(List<String> labelIds) {
		this.labelIds = labelIds;
	}
	public List<String> getLabelNames() {
		return labelNames;
	}
	public void setLabelNames(List<String> labelNames) {
		this.labelNames = labelNames;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<String> getPy() {
		return py;
	}
	public void setPy(List<String> py) {
		this.py = py;
	}
	public List<String> getsPy() {
		return sPy;
	}
	public void setsPy(List<String> sPy) {
		this.sPy = sPy;
	}
	
	public Integer getSortInfo() {
		return sortInfo;
	}
	public void setSortInfo(Integer sortInfo) {
		this.sortInfo = sortInfo;
	}
	public Date getTimeStart() {
		return timeStart;
	}
	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}
	public Date getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getSolrCreateDate() {
		return solrCreateDate;
	}
	public void setSolrCreateDate(Date solrCreateDate) {
		this.solrCreateDate = solrCreateDate;
	}
}
