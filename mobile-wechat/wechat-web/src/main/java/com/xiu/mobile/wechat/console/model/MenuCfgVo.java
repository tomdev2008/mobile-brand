package com.xiu.mobile.wechat.console.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 菜单配置 Model类
 * 
 * @author wangzhenjiang
 * @table X_WECHAT_MENU_CFG
 * 
 */
public class MenuCfgVo implements Serializable {
	private static final long serialVersionUID = 8637558976576618957L;

	private Long id;
	/**
	 * 菜单名称
	 */
	private String name;

	/**
	 * 类型 click view
	 */
	private String type;
	/**
	 * 父菜单ID
	 */
	private Long parentId;

	/**
	 * 菜单的key
	 */
	private String key;

	/**
	 * 点击菜单响应消息内容
	 */
	private String content;

	/**
	 * 点击菜单跳转链接
	 */
	private String url;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
