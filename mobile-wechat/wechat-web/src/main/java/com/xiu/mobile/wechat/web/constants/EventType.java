package com.xiu.mobile.wechat.web.constants;

/**
 * 事件类型枚举类
 * 
 * @author wangzhenjiang
 * 
 */
public enum EventType {
	/**
	 * 订阅
	 */
	SUBSCRIBE("subscribe"),

	/**
	 * 取消订阅
	 */
	UNSUBSCRIBE("unsubscribe"),
	/**
	 * 自定义菜单点击事件
	 */
	CLICK("CLICK");

	private String value;

	EventType(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
