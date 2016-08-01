/**  
 * @Project: xiu
 * @Title: AddressOutParam.java
 * @Package org.lazicats.xiu.model.address
 * @Description: TODO
 * @author: yong
 * @date 2013-5-6 上午09:30:42
 * @Copyright: BroadenGate Software Services Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.xiu.mobile.portal.model;

import com.xiu.mobile.portal.common.model.BaseBackDataVo;


/** 
 * @ClassName: AddressOutParam 
 * @Description: TODO
 * @author: yong
 * @date 2013-5-6 上午09:30:42
 *  
 */
public class AddressOutParam extends BaseBackDataVo{
	
	/** 
	 * @Fields serialVersionUID : TODO
	 */ 
	private static final long serialVersionUID = -1897238510146258284L;

	private String addressId;
	
	private String rcverName;
	
	private String provinceCode;
	//单个地址查询时_将省市县翻译过来的描述
	private String provinceCodeDesc;
	
	private String regionCode;
	//单个地址查询时_将省市县翻译过来的描述
	private String regionCodeDesc;
	
	private String cityCode;
	//单个地址查询时_将省市县翻译过来的描述
	private String cityCodeDesc;
	
	private String addressInfo;
	
	private String postCode;
	
	private String mobile;
	
	private String areaCode;
	
	private String phone;
	
	private String divCode;
	
	private String bookerName;
	
	private String bookerPhone;
	
	private String isMaster;
	
	//将省市县翻译过来的描述
	private String addressPrefix;
	//单个地址查询时_将省市县翻译过来的描述
	private String cityCodeRemark;
	
	private String idCard; // 身份证号码
	private String idHead; // 身份证正面
	private String idTails; // 身份证反面
	
	private String userId; // 用户Id
	private String customerId; // 客户Id

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
	 * @return addressPrefix 
	 */
	public String getAddressPrefix() {
		return addressPrefix;
	}

	/**
	 * @param addressPrefix the addressPrefix to set
	 */
	public void setAddressPrefix(String addressPrefix) {
		this.addressPrefix = addressPrefix;
	}

	/** 
	 * @return provinceCodeDesc 
	 */
	public String getProvinceCodeDesc() {
		return provinceCodeDesc;
	}

	/**
	 * @param provinceCodeDesc the provinceCodeDesc to set
	 */
	public void setProvinceCodeDesc(String provinceCodeDesc) {
		this.provinceCodeDesc = provinceCodeDesc;
	}

	/** 
	 * @return regionCodeDesc 
	 */
	public String getRegionCodeDesc() {
		return regionCodeDesc;
	}

	/**
	 * @param regionCodeDesc the regionCodeDesc to set
	 */
	public void setRegionCodeDesc(String regionCodeDesc) {
		this.regionCodeDesc = regionCodeDesc;
	}

	/** 
	 * @return cityCodeDesc 
	 */
	public String getCityCodeDesc() {
		return cityCodeDesc;
	}

	/**
	 * @param cityCodeDesc the cityCodeDesc to set
	 */
	public void setCityCodeDesc(String cityCodeDesc) {
		this.cityCodeDesc = cityCodeDesc;
	}

	/** 
	 * @return cityCodeRemark 
	 */
	public String getCityCodeRemark() {
		return cityCodeRemark;
	}

	/**
	 * @param cityCodeRemark the cityCodeRemark to set
	 */
	public void setCityCodeRemark(String cityCodeRemark) {
		this.cityCodeRemark = cityCodeRemark;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Override
	public String toString() {
		return "AddressOutParam [addressId=" + addressId + ", rcverName=" + rcverName + ", provinceCode=" + provinceCode 
				+ ", provinceCodeDesc=" + provinceCodeDesc + ", regionCode=" + regionCode + ", regionCodeDesc=" + regionCodeDesc 
				+ ", cityCode=" + cityCode + ", cityCodeDesc=" + cityCodeDesc
				+ ", addressInfo=" + addressInfo + ", postCode=" + postCode + ", mobile=" + mobile 
				+ ", areaCode=" + areaCode + ", phone=" + phone + ", divCode=" + divCode 
				+ ", bookerName=" + bookerName + ", bookerPhone=" + bookerPhone + ", isMaster=" + isMaster 
				+ ", addressPrefix=" + addressPrefix
				+ ", cityCodeRemark=" + cityCodeRemark + ", idCard=" + idCard + ", idHead=" + idHead + ", idTails=" + idTails 
				+ ", userId=" + userId + ", customerId=" + customerId + ", identityId=" + identityId
				+ "]";
	}
	
	
}
