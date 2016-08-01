package com.xiu.mobile.wechat.facade.model;

import java.io.Serializable;

/**
 * 
 * @author wenxiaozhuo
 * @date   20140530
 * 视频消息
 *
 */
public class VideoInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *发送的视频的媒体ID 
	 */
	private String media_id;
	/**
	 * 视频消息的标题
	 */
	private String title;
	/**
	 * 视频消息的描述
	 */
	private String description;

	public VideoInfo() {
		super();
	}

	public VideoInfo(String media_id, String title, String description) {
		this.media_id = media_id;
		this.title = title;
		this.description = description;
	}

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
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

	@Override
	public String toString() {
		return "VideoInfo [media_id=" + media_id + ", title=" + title + ", description=" + description + "]";
	}

}
