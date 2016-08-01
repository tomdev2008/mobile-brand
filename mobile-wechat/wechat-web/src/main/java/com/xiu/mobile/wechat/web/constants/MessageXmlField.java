package com.xiu.mobile.wechat.web.constants;

/**
 * 
 * 微信消息中XML标签 枚举类
 * 
 * @author wangzhenjiang
 * 
 */
public enum MessageXmlField {

	/**
	 * ToUserName标签
	 */
	TO_USER_NAME("ToUserName"),
	/**
	 * FromUserName标签
	 */
	FROM_USER_NAME("FromUserName"),
	/**
	 * CreateTime标签
	 */
	CREATE_TIME("CreateTime"),
	/**
	 * MsgType标签
	 */
	MSG_TYPE("MsgType"),
	/**
	 * Content标签
	 */
	CONTENT("Content"),
	/**
	 * Event标签
	 */
	EVENT("Event");

	private String value;

	MessageXmlField(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
