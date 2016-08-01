package com.xiu.mobile.portal.model;

/**
 * 
 * @ClassName: ReceiverInfoVo
 * @Description: 收件人信息
 * @author: Hualong
 * @date 2013-5-3 04:06:12
 * 
 */
public class ReceiverInfoVo {

	private String receiver;// 收货人名称
	private String mobile;// 手机
	private String phone; // 电话
	private String postCode;
	private String province;// 省
	private String city;// 市
	private String area;// 区/县
	private String addressId; // 地址Id
	private String address;// 地址
	private String deliverTime;// 派送时间
	private Boolean idAuthorized;//身份认证

	public String getReceiver() {
		return this.receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDeliverTime() {
		return this.deliverTime;
	}

	public void setDeliverTime(String deliverTime) {
		this.deliverTime = deliverTime;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	@Override
	public String toString() {
		return "ReceiverInfoVo [receiver=" + receiver + ", mobile=" + mobile + ", phone=" + phone + ", postCode=" + postCode + ", province=" + province + ", city=" + city + ", area=" + area + ", addressId=" + addressId + ", address=" + address + ", deliverTime=" + deliverTime + "]";
	}

	public Boolean getIdAuthorized() {
		return idAuthorized;
	}

	public void setIdAuthorized(Boolean idAuthorized) {
		this.idAuthorized = idAuthorized;
	}

}
