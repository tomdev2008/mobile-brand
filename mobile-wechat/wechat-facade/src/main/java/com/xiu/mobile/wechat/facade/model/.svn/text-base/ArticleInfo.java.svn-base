package com.xiu.mobile.wechat.facade.model;

import java.io.Serializable;

/**
 * 
 * @author wenxiaozhuo
 * @date   20140530
 * 详细的图文消息
 *
 */
public class ArticleInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 点击后跳转的链接
	 */
	private String url;
	/**
	 * 图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80
	 */
	private String picurl;

	public ArticleInfo() {
		super();
	}

	public ArticleInfo(String title, String description, String url, String picurl) {
		super();
		this.title = title;
		this.description = description;
		this.url = url;
		this.picurl = picurl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	@Override
	public String toString() {
		return "ArticleInfo [title=" + title + ", description=" + description + ", url=" + url + ", picurl=" + picurl + "]";
	}

}
