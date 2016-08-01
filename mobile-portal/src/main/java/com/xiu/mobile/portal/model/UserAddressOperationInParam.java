/**  
 * @Project: xiu
 * @Title: UserAddressOperationInParam.java
 * @Package org.lazicats.xiu.model.address
 * @Description: TODO
 * @author: yong
 * @date 2013-5-6 上午09:30:42
 * @Copyright: BroadenGate Software Services Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.xiu.mobile.portal.model;

/** 
 * @ClassName: UserAddressOperationInParam 
 * @Description: TODO
 * @author: yong
 * @date 2013-5-6 上午09:30:42
 *  
 */
public class UserAddressOperationInParam extends DeviceVo {
	
	/** 
	 * @Fields serialVersionUID : TODO
	 */ 
	private static final long serialVersionUID = 569295631712454368L;
	
	private String tokenId;
	
	private String bizType;

	private String addressId;
	
	private String rcverName;
	
	private String provinceCode;
	
	private String regionCode;
	
	private String cityCode;
	
	private String addressInfo;
	
	private String postCode;
	
	private String mobile;
	
	private String areaCode;
	
	private String phone;
	
	private String divCode;
	
	private String bookerName;
	
	private String bookerPhone;
	
	private String isMaster;
	
	private String idCard; // 身份证号码
	private String idHead; // 身份证正面
	private String idTails; // 身份证反面

	/** 身份证信息ID */
	private Long identityId;
	
	public Long getIdentityId() {
		return identityId;
	}

	public void setIdentityId(Long identityId) {
		this.identityId = identityId;
	}

	/** 
	 * @return addressId 
	 */
	public String getAddressId() {
		return addressId;
	}

	/**
	 * @param addressId the addressId to set
	 */
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	/** 
	 * @return rcverName 
	 */
	public String getRcverName() {
		return rcverName;
	}

	/**
	 * @param rcverName the rcverName to set
	 */
	public void setRcverName(String rcverName) {
		this.rcverName = rcverName;
	}

	/** 
	 * @return provinceCode 
	 */
	public String getProvinceCode() {
		return provinceCode;
	}

	/**
	 * @param provinceCode the provinceCode to set
	 */
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	/** 
	 * @return regionCode 
	 */
	public String getRegionCode() {
		return regionCode;
	}

	/**
	 * @param regionCode the regionCode to set
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	/** 
	 * @return cityCode 
	 */
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/** 
	 * @return addressInfo 
	 */
	public String getAddressInfo() {
		return addressInfo;
	}

	/**
	 * @param addressInfo the addressInfo to set
	 */
	public void setAddressInfo(String addressInfo) {
		this.addressInfo = addressInfo;
	}

	/** 
	 * @return postCode 
	 */
	public String getPostCode() {
		return postCode;
	}

	/**
	 * @param postCode the postCode to set
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	/** 
	 * @return mobile 
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/** 
	 * @return areaCode 
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * @param areaCode the areaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/** 
	 * @return phone 
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/** 
	 * @return divCode 
	 */
	public String getDivCode() {
		return divCode;
	}

	/**
	 * @param divCode the divCode to set
	 */
	public void setDivCode(String divCode) {
		this.divCode = divCode;
	}

	/** 
	 * @return bookerName 
	 */
	public String getBookerName() {
		return bookerName;
	}

	/**
	 * @param bookerName the bookerName to set
	 */
	public void setBookerName(String bookerName) {
		this.bookerName = bookerName;
	}

	/** 
	 * @return bookerPhone 
	 */
	public String getBookerPhone() {
		return bookerPhone;
	}

	/**
	 * @param bookerPhone the bookerPhone to set
	 */
	public void setBookerPhone(String bookerPhone) {
		this.bookerPhone = bookerPhone;
	}

	/** 
	 * @return isMaster 
	 */
	public String getIsMaster() {
		return isMaster;
	}

	/**
	 * @param isMaster the isMaster to set
	 */
	public void setIsMaster(String isMaster) {
		this.isMaster = isMaster;
	}

	/** 
	 * @return tokenId 
	 */
	public String getTokenId() {
		return tokenId;
	}

	/**
	 * @param tokenId the tokenId to set
	 */
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	/** 
	 * @return bizType 
	 */
	public String getBizType() {
		return bizType;
	}

	/**
	 * @param bizType the bizType to set
	 */
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getIdHead() {
		return idHead;
	}

	public void setIdHead(String idHead) {
		this.idHead = idHead;
	}

	public String getIdTails() {
		return idTails;
	}

	public void setIdTails(String idTails) {
		this.idTails = idTails;
	}

	@Override
	public String toString() {
		return "UserAddressOperationInParam [tokenId=" + tokenId + ", bizType="
				+ bizType + ", addressId=" + addressId + ", rcverName="
				+ rcverName + ", provinceCode=" + provinceCode
				+ ", regionCode=" + regionCode + ", cityCode=" + cityCode
				+ ", addressInfo=" + addressInfo + ", postCode=" + postCode
				+ ", mobile=" + mobile + ", areaCode=" + areaCode + ", phone="
				+ phone + ", divCode=" + divCode + ", bookerName=" + bookerName
				+ ", bookerPhone=" + bookerPhone + ", isMaster=" + isMaster
				+ ", idCard=" + idCard + ", idHead=" + idHead + ", idTails="
				+ idTails + ", identityId=" + identityId + "]";
	}
	
}
