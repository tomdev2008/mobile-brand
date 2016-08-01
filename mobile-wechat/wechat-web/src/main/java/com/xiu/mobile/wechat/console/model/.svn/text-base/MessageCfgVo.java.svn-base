package com.xiu.mobile.wechat.console.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 消息配置 Model类
 * 
 * @author wangzhenjiang
 * @table X_WECHAT_MESSAGE_CFG
 * 
 */
public class MessageCfgVo implements Serializable {
	private static final long serialVersionUID = 8321051669803436818L;

	private Long id;
	/**
	 * 关键词
	 */
	private String keyword;
	/**
	 * 响应消息类型： text-文本消息, image-图片消息, voice-语音消息, video-视频消息, music-音乐消息, news-图文消息;
	 */
	private String msgType;

	/**
	 * 响应消息内容 - 文本类型
	 */
	private String content;

	/**
	 * 关联素材ID
	 */
	private Long materialId;

	/**
	 * 关联素材名称
	 */
	private String materialName;

	/**
	 * 数据类型：keyword：关键词消息回复，default:消息自动回复，subscribe：被添加自动回复
	 */
	private String dataType;

	/**
	 * 创建日期
	 */
	private Date createDate;
	/**
	 * 创建者ID
	 */
	private Long creatorId;
	/**
	 * 创建者名称
	 */
	private String creatorName;
	/**
	 * 修改日期
	 */
	private Date modifyDate;
	/**
	 * 修改者ID
	 */
	private Long modifierId;
	/**
	 * 修改者名称
	 */
	private String modifierName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Long getModifierId() {
		return modifierId;
	}

	public void setModifierId(Long modifierId) {
		this.modifierId = modifierId;
	}

	public String getModifierName() {
		return modifierName;
	}

	public void setModifierName(String modifierName) {
		this.modifierName = modifierName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
