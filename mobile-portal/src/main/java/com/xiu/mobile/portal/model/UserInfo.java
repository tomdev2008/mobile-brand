package com.xiu.mobile.portal.model;

import java.util.Date;

public class UserInfo {
	/** 用户ID */
	private Long userId;
	/** 用户登录名 */
	private String logonName;
	/** 客户姓名 */
	private String custName;
	/** 客户昵称 */
	private String petName;
	/** 手机号码 */
	private String mobile;
	/** 电子邮箱 */
	private String email;
	/** 手机认证 0:未认证 1:认证通过 */
	private String mobileAuthenStatus;
	/** 邮箱认证 0:未认证 1:认证通过 */
	private String emailAuthenStatus;
	/** eBay用户协议是否同意 Y：同意 ，N：不同意,其它也表示不同意 */
	private String ebayUserAgreement;
	/** 生日 */
	private String birthday;
	/** 最后登录IP */
	private String lastLogonIp;
	/** 最后登录时间 */
	private Date lastLogonTime;
	/** 最后登录渠道标识 */
	private Integer lastLogonChannelId;
	/** 注册类型 01.Email 02.手机 03.呢称 */
	private String registerType;
	/** 注册时间 */
	private Date registerTime;
	/** 街道地址 */
	private String addressInfo;
	/** 性别 */
	private String sex;
	private String sexDesc;
	/** 头像（地址） */
	private String headPortrait;
	private String bigHeadPortrait; //高清头像
	private boolean isEmptyPassword;// 用户密码是否为空
	
	//是否是创客
	private boolean isMaker;
	
	//是否秀客达人
	private boolean isShowUserTalent; 
	
	//是否秀客前台管理员
	private boolean isShowManager; 
	
	//用户等级
	private String userLevel;
	
	//返现总金额
	private String totalAmount;
	
	//即将返现总金额
	private String notPayAmount;
	
	//用户等级url
	private String userLevelUrl;
	
	//手机绑定状态
	private Boolean mobileBindStatus=false;
	
	//微博绑定状态
	private Boolean weiboBindStatus=false;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLogonName() {
		return logonName;
	}

	public void setLogonName(String logonName) {
		this.logonName = logonName;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileAuthenStatus() {
		return mobileAuthenStatus;
	}

	public void setMobileAuthenStatus(String mobileAuthenStatus) {
		this.mobileAuthenStatus = mobileAuthenStatus;
	}

	public String getEmailAuthenStatus() {
		return emailAuthenStatus;
	}

	public void setEmailAuthenStatus(String emailAuthenStatus) {
		this.emailAuthenStatus = emailAuthenStatus;
	}

	public String getEbayUserAgreement() {
		return ebayUserAgreement;
	}

	public void setEbayUserAgreement(String ebayUserAgreement) {
		this.ebayUserAgreement = ebayUserAgreement;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getLastLogonIp() {
		return lastLogonIp;
	}

	public void setLastLogonIp(String lastLogonIp) {
		this.lastLogonIp = lastLogonIp;
	}

	public Date getLastLogonTime() {
		return lastLogonTime;
	}

	public void setLastLogonTime(Date lastLogonTime) {
		this.lastLogonTime = lastLogonTime;
	}

	public Integer getLastLogonChannelId() {
		return lastLogonChannelId;
	}

	public void setLastLogonChannelId(Integer lastLogonChannelId) {
		this.lastLogonChannelId = lastLogonChannelId;
	}

	public String getRegisterType() {
		return registerType;
	}

	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public String getAddressInfo() {
		return addressInfo;
	}

	public void setAddressInfo(String addressInfo) {
		this.addressInfo = addressInfo;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSexDesc() {
		return sexDesc;
	}

	public void setSexDesc(String sexDesc) {
		this.sexDesc = sexDesc;
	}

	public String getHeadPortrait() {
		return headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}
	
	public String getBigHeadPortrait() {
		return bigHeadPortrait;
	}

	public void setBigHeadPortrait(String bigHeadPortrait) {
		this.bigHeadPortrait = bigHeadPortrait;
	}

	public boolean getIsEmptyPassword() {
		return isEmptyPassword;
	}

	public void setIsEmptyPassword(boolean isEmptyPassword) {
		this.isEmptyPassword = isEmptyPassword;
	}
	
	public boolean getIsMaker() {
		return isMaker;
	}

	public void setIsMaker(boolean isMaker) {
		this.isMaker = isMaker;
	}
	
	public boolean getIsShowUserTalent() {
		return isShowUserTalent;
	}

	public void setIsShowUserTalent(boolean isShowUserTalent) {
		this.isShowUserTalent = isShowUserTalent;
	}
	
	

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public boolean getIsShowManager() {
		return isShowManager;
	}

	public void setIsShowManager(boolean isShowManager) {
		this.isShowManager = isShowManager;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getNotPayAmount() {
		return notPayAmount;
	}

	public void setNotPayAmount(String notPayAmount) {
		this.notPayAmount = notPayAmount;
	}
	
	public String getUserLevelUrl() {
		return userLevelUrl;
	}

	public void setUserLevelUrl(String userLevelUrl) {
		this.userLevelUrl = userLevelUrl;
	}

	public Boolean getMobileBindStatus() {
		return mobileBindStatus;
	}

	public void setMobileBindStatus(Boolean mobileBindStatus) {
		this.mobileBindStatus = mobileBindStatus;
	}

	public Boolean getWeiboBindStatus() {
		return weiboBindStatus;
	}

	public void setWeiboBindStatus(Boolean weiboBindStatus) {
		this.weiboBindStatus = weiboBindStatus;
	}

	@Override
	public String toString() {
		return "UserInfo [userId=" + userId + ", logonName=" + logonName + ", custName=" + custName + ", petName=" + petName 
				+ ", mobile=" + mobile + ", email=" + email + ", mobileAuthenStatus=" + mobileAuthenStatus + ", emailAuthenStatus=" 
				+ emailAuthenStatus + ", ebayUserAgreement=" + ebayUserAgreement + ", birthday=" + birthday + ", lastLogonIp=" + lastLogonIp 
				+ ", lastLogonTime=" + lastLogonTime + ", lastLogonChannelId=" + lastLogonChannelId + ", registerType=" + registerType 
				+ ", registerTime=" + registerTime + ", addressInfo=" + addressInfo + ", sex=" + sex
				+ ", sexDesc=" + sexDesc + ", headPortrait=" + headPortrait + ", isEmptyPassword=" + isEmptyPassword + "]";
	}

}
