package com.xiu.mobile.wechat.facade.model;

import java.io.Serializable;

/**
 * 
 * @author wenxiaozhuo
 * @date   20140530
 * 文本消息
 *
 */
public class TextInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 文本消息内容
	 */
	private String content;

	public TextInfo() {
		super();
	}

	public TextInfo(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "TextInfo [content=" + content + "]";
	}

}
