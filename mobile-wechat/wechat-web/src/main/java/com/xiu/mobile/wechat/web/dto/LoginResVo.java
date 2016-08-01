package com.xiu.mobile.wechat.web.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("rawtypes")
public class LoginResVo implements Serializable {
	private static final long serialVersionUID = -5306049644576210488L;

	private String retCode; // 返回码
	private String tokenId;// tokenId;

	private List addressList;// 收货地址;
	private List couponList;// 优惠券;

	private String isConferCoupon;// 是否注册赠送了优惠券 Y：已赠送 N：无;
	private String errorMsg;// 错误返回信息
	private String logonName;// 登录名
	private String password;// 密码
	private String userId; // 用户Id

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public List getAddressList() {
		return addressList;
	}

	public void setAddressList(List addressList) {
		this.addressList = addressList;
	}

	public List getCouponList() {
		return couponList;
	}

	public void setCouponList(List couponList) {
		this.couponList = couponList;
	}

	public String getIsConferCoupon() {
		return isConferCoupon;
	}

	public void setIsConferCoupon(String isConferCoupon) {
		this.isConferCoupon = isConferCoupon;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getLogonName() {
		return logonName;
	}

	public void setLogonName(String logonName) {
		this.logonName = logonName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
