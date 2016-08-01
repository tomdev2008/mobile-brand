package com.xiu.mobile.wechat.web.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public class FeedbackCfgVo implements Serializable {

	private static final long serialVersionUID = 2957709862507456621L;

	private Long id;
	/**
	 * 支付该笔订单的用户 ID
	 */
	private String openId;
	/**
	 * 公众号 ID
	 */
	private String appId;
	/**
	 * 通知类型:  request - 用户提交投诉,confirm - 用户确认消除投诉,reject - 用户拒绝消除投诉
	 */
	private String msgType;
	/**
	 * 投诉单号
	 */
	private String feedbackId;
	/**
	 * 交易订单号
	 */
	private String transId;
	/**
	 * 用户投诉原因
	 */
	private String reason;
	/**
	 * 用户希望解决方案
	 */
	private String solution;
	/**
	 * 备注信息+电话
	 */
	private String extinfo;
	/**
	 * 根据前文 paySign 所述的签名方式生成，参与签名的字段为： appid、appkey、timestamp、openid.
	 */
	private String appsignature;

	/**
	 * 用户上传图片URL
	 */
	private String[] picUrls;
	/**
	 * 处理状态 :  Y - 已回复，N - 未回复 
	 */
	private String state;
	/**
	 * 创建日期
	 */
	private Date createDate;
	/**
	 * 创建者ID
	 */
	private String creatorId;
	/**
	 * 创建者名称
	 */
	private String creatorName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(String feedbackId) {
		this.feedbackId = feedbackId;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public String getExtinfo() {
		return extinfo;
	}

	public void setExtinfo(String extinfo) {
		this.extinfo = extinfo;
	}

	public String getAppsignature() {
		return appsignature;
	}

	public void setAppsignature(String appsignature) {
		this.appsignature = appsignature;
	}

	public String[] getPicUrls() {
		return picUrls;
	}

	public void setPicUrls(String[] picUrls) {
		this.picUrls = picUrls;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
