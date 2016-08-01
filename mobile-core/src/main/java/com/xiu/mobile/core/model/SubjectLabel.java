package com.xiu.mobile.core.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 专题标签
 * @author Administrator
 *
 */
public class SubjectLabel implements Serializable{
	private static final long serialVersionUID = -1407013419574545320L;

	private long labelId;//标签ID
	
	private String title; //标签名称
	
	private int display;//状态 1显示 2 隐藏
	
	private String operator; //操作人
	
	private int orderSeq; //排序 默认为0
	
	private Date createDate; //创建时间
	
	private int category; //用途 1.专题标签 2.单品推荐标签

	public long getLabelId() {
		return labelId;
	}

	public void setLabelId(long labelId) {
		this.labelId = labelId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getDisplay() {
		return display;
	}

	public void setDisplay(int display) {
		this.display = display;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public int getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(int orderSeq) {
		this.orderSeq = orderSeq;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}
	
}
