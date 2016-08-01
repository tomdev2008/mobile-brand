package com.xiu.mobile.wechat.facade.model;

import java.io.Serializable;

/**
 * 
 * @author wenxiaozhuo
 * @date   20140530
 * 音乐信息
 *
 */
public class MusicInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 音乐标题
	 */
	private String title;
	/**
	 * 音乐描述
	 */
	private String description;
	/**
	 * 音乐链接
	 */
	private String musicurl;
	/**
	 * 高品质音乐链接，wifi环境优先使用该链接播放音乐
	 */
	private String hqmusicurl;
	/**
	 * 缩略图的媒体ID
	 */
	private String thumb_media_id;

	public MusicInfo() {
		super();
	}

	public MusicInfo(String title, String description, String musicurl, String hqmusicurl, String thumb_media_id) {
		super();
		this.title = title;
		this.description = description;
		this.musicurl = musicurl;
		this.hqmusicurl = hqmusicurl;
		this.thumb_media_id = thumb_media_id;
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

	public String getMusicurl() {
		return musicurl;
	}

	public void setMusicurl(String musicurl) {
		this.musicurl = musicurl;
	}

	public String getHqmusicurl() {
		return hqmusicurl;
	}

	public void setHqmusicurl(String hqmusicurl) {
		this.hqmusicurl = hqmusicurl;
	}

	public String getThumb_media_id() {
		return thumb_media_id;
	}

	public void setThumb_media_id(String thumb_media_id) {
		this.thumb_media_id = thumb_media_id;
	}

	@Override
	public String toString() {
		return "MusicInfo [title=" + title + ", description=" + description + ", musicurl=" + musicurl + ", hqmusicurl=" + hqmusicurl
				+ ", thumb_media_id=" + thumb_media_id + "]";
	}

}
