package com.xiu.mobile.portal.model;

/**
 * 返回结果对象
 * @author coco.long
 * @time	2015-06-10
 */
public class ResultDO {

	// 标识
	private String scode;
	
	// 消息
	private String smsg;
	
	// 错误信息
	private String errorCode;
	
	// 数据对象
	private String data;
	
	// 返回结果head
	private HeadDO head;

	public String getScode() {
		return scode;
	}

	public void setScode(String scode) {
		this.scode = scode;
	}

	public String getSmsg() {
		return smsg;
	}

	public void setSmsg(String smsg) {
		this.smsg = smsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public HeadDO getHead() {
		return head;
	}

	public void setHead(HeadDO head) {
		this.head = head;
	}
	
}
