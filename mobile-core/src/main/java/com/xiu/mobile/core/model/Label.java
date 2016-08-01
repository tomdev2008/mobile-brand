package com.xiu.mobile.core.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 标签
* @Description: TODO(标签) 
* @author haidong.luo@xiu.com
* @date 2016年5月4日 上午11:42:45 
*
 */
public class Label implements Serializable{
	

	private static final Long serialVersionUID = -5149406615124255435L;

	private Long labelId;//标签ID
	
	private String title; //标签名称
	
	private Integer display;//状态 1显示 2 隐藏
	
	private String operator; //操作人
	
	private Integer orderSeq; //排序 默认为0
	
	private Date createDate; //创建时间
	
	private Integer labelGroup; //标签组(1.热词 2.运营分类)
	
	private String imgUrl;//图片地址
	
	private Integer deleteFlag;//删除标识 (0未删除 1删除)
	
	private Integer status;//状态(1正常 0停用)
	
	private Integer relationLabelNum;//关联标签个数
	
	private Integer relationServiceNum;//关联业务数据个数
	
	private Integer labelFans;//标签粉丝数
	
	public Long getLabelId() {
		return labelId;
	}

	public void setLabelId(Long labelId) {
		this.labelId = labelId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getDisplay() {
		return display;
	}

	public void setDisplay(Integer display) {
		this.display = display;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Integer getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(Integer orderSeq) {
		this.orderSeq = orderSeq;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getLabelGroup() {
		return labelGroup;
	}

	public void setLabelGroup(Integer labelGroup) {
		this.labelGroup = labelGroup;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getRelationLabelNum() {
		return relationLabelNum;
	}

	public void setRelationLabelNum(Integer relationLabelNum) {
		this.relationLabelNum = relationLabelNum;
	}

	public Integer getRelationServiceNum() {
		return relationServiceNum;
	}

	public void setRelationServiceNum(Integer relationServiceNum) {
		this.relationServiceNum = relationServiceNum;
	}

	public Integer getLabelFans() {
		return labelFans;
	}

	public void setLabelFans(Integer labelFans) {
		this.labelFans = labelFans;
	}

}
