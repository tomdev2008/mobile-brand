package com.xiu.mobile.portal.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class UserPayPlatform implements Serializable {

	private String userId;
	private Date createDate;
	private Date updateDate;
	private String payPlatform;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPayPlatform() {
		return payPlatform;
	}

	public void setPayPlatform(String payPlatform) {
		this.payPlatform = payPlatform;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "UserPayPlatform [userId=" + userId + ", createDate=" + createDate + ", updateDate=" + updateDate 
				+ ", payPlatform=" + payPlatform + "]";
	}

	

}
