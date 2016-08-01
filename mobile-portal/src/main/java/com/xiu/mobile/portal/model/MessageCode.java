package com.xiu.mobile.portal.model;
/**
 * @author: wenxiaozhuo
 * @description:验证码
 * @date: 2014-4-24
 */
public class MessageCode {
	
	private Long msgId;//验证码ID
	
	private String msgCode;//验证码
	
	private String timestamp;//时间戳
	
	private String msgPhone;//验证手机号
	
	private String timeFlag;//时间是否超时
	
	private String contentFlag;//内容是否失效

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getMsgPhone() {
		return msgPhone;
	}

	public void setMsgPhone(String msgPhone) {
		this.msgPhone = msgPhone;
	}

	public String getTimeFlag() {
		return timeFlag;
	}

	public void setTimeFlag(String timeFlag) {
		this.timeFlag = timeFlag;
	}

	public String getContentFlag() {
		return contentFlag;
	}

	public void setContentFlag(String contentFlag) {
		this.contentFlag = contentFlag;
	}

}
