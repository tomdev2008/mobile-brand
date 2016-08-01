/**  
 * @Project: xiu
 * @Title: regResVo.java
 * @Package org.lazicats.xiu.model.login.vo
 * @Description: TODO
 * @author: chengyuanhuan
 * @date 2013-5-6 上午11:34:41
 * @Copyright: BroadenGate Software Services Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.xiu.mobile.portal.model;

import java.io.Serializable;
import java.util.List;

public class LoginResVo implements Serializable {
	/*
	 * "retCode":"000", "tokenId":"17d1715e1524c5fa558b2461a36b65a2",
	 * "addressList":[], "couponList":[], "isConferCoupon":"N"
	 */

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

	@Override
	public String toString() {
		return "LoginResVo [retCode=" + retCode + ", tokenId=" + tokenId + ", addressList=" + addressList 
				+ ", couponList=" + couponList + ", isConferCoupon=" + isConferCoupon + ", errorMsg=" + errorMsg
				+ ", logonName=" + logonName + ", password=" + password + ", userId=" + userId + "]";
	}
	
}
