package com.xiu.mobile.portal.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BindUserInfo implements Serializable {

	/**
	 * 联合登录Union ID
	 */
	private Long unionId;

	/**
	 * 绑定用户ID
	 */
	private Long userId;

	/**
	 * 联盟客户在联盟渠道中的标识
	 */
	private String partnerId;

	/**
	 * 联盟客户用户来源
	 */
	private String userSource;
	/**
	 * 第三方原名,如QQ网名"会飞的鱼"
	 */
	private String thirdName;

	/**
	 * 扩展信息
	 */
	private String extendInfo;

	public Long getUnionId() {
		return unionId;
	}

	public void setUnionId(Long unionId) {
		this.unionId = unionId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getUserSource() {
		return userSource;
	}

	public void setUserSource(String userSource) {
		this.userSource = userSource;
	}

	public String getExtendInfo() {
		return extendInfo;
	}

	public void setExtendInfo(String extendInfo) {
		this.extendInfo = extendInfo;
	}
	
	public String getThirdName() {
		return thirdName;
	}

	public void setThirdName(String thirdName) {
		this.thirdName = thirdName;
	}
	@Override
	public String toString() {
		return "BindUserInfo [unionId=" + unionId + ", userId=" + userId + ", partnerId=" + partnerId + ", userSource=" + userSource 
				+ ", extendInfo=" + extendInfo+ ", thirdName=" + thirdName + "]";
	}
	
}
