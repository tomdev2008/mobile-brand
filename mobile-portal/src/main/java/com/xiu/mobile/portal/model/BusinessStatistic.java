package com.xiu.mobile.portal.model;

import java.io.Serializable;

/**
 * 业务统计表
 * @author Administrator
 *
 */
public class BusinessStatistic implements Serializable{

	
	private static final long serialVersionUID = -3071990594153114036L;
	
	public Long id; //主键
	
	public Long type;	//业务类型  1：专题   2秀集合
	
	public Long businessId;	//业务主键
	
	public Long clickCount;	//点击次数
	
	public Long shareCount;	//分享次数
	
	public String modifyDate;	//更新时间
	

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	
	public Long getClickCount() {
		return clickCount;
	}

	public void setClickCount(Long clickCount) {
		this.clickCount = clickCount;
	}

	public Long getShareCount() {
		return shareCount;
	}

	public void setShareCount(Long shareCount) {
		this.shareCount = shareCount;
	}
}
