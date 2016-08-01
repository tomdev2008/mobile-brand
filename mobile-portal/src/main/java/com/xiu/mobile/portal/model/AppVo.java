package com.xiu.mobile.portal.model;
/**
 * 
 * @author wenxiaozhuo
 * @date	20140514
 *
 */
public class AppVo {
	
	/**
	 * appId
	 */
	private Long   appId;
	/**
	 * appUrl
	 */
	private String appUrl;
	/**
	 * 设备类型 1-IOS，2-Android，3-iPad
	 */
	private String appType;
	/**
	 * app版本号 4.0.0
	 */
	private String appVersion;
	/**
	 * app版本名称
	 */
	private String appVerName;
	/**
	 * app版本简介
	 */
	private String appSummary;
	/**
	 * 是否为最新版本
	 */
	private String isLast;
	
	/**
	 * 是否需要更新
	 */
	private boolean isUpdate;
	
	/**
	 * 上传时间
	 */
	private String createTime;
	/**
	 * 上传者
	 */
	private String creator;
	/**
	 * appSource
	 */
	private String appSource;
	/**
	 * 手机型号
	 */
	private String phoneModel;
	/**
	 * 设备ID
	 */
	private String deviceId;
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public String getAppUrl() {
		return appUrl;
	}
	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public String getAppVerName() {
		return appVerName;
	}
	public void setAppVerName(String appVerName) {
		this.appVerName = appVerName;
	}
	public String getAppSummary() {
		return appSummary;
	}
	public void setAppSummary(String appSummary) {
		this.appSummary = appSummary;
	}
	public String getIsLast() {
		return isLast;
	}
	public void setIsLast(String isLast) {
		this.isLast = isLast;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public boolean isUpdate() {
		return isUpdate;
	}
	public void setUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}
	public String getAppSource() {
		return appSource;
	}
	public void setAppSource(String appSource) {
		this.appSource = appSource;
	}
	public String getPhoneModel() {
		return phoneModel;
	}
	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	@Override
	public String toString() {
		return "AppVo [appId=" + appId + ", appUrl=" + appUrl + ", appType="
				+ appType + ", appVersion=" + appVersion + ", appVerName="
				+ appVerName + ", appSummary=" + appSummary + ", isLast="
				+ isLast + ", isUpdate=" + isUpdate + ", createTime="
				+ createTime + ", creator=" + creator + ", appSource="
				+ appSource + ", phoneModel=" + phoneModel + ", deviceId="
				+ deviceId + "]";
	}
}
