package com.xiu.mobile.wechat.console.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 素材信息 Model类
 * 
 * @author wangzhenjiang
 * @table X_WECHAT_MATERIAL_CFG
 * 
 */
public class MaterialCfgVo implements Serializable {
	private static final long serialVersionUID = 4965340128832202620L;

	private Long id;

	/**
	 * 素材名称
	 */
	private String name;
	/**
	 * 图文消息类型
	 */
	private String type;
	/**
	 * 第一个item
	 * 
	 * 多条图文消息信息，默认第一个item为大图,注意，如果图文数超过10，则将会无响应
	 */
	private Long firstArticleId;
	/**
	 * 其它item
	 */
	private String otherArticleId;

	/**
	 * 图文列表
	 */
	private List<ArticleCfgVo> lstArticleCfg;

	/**
	 * 创建日期
	 */
	private Date createDate;
	/**
	 * 创建者ID
	 */
	private Long creatorId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public Long getFirstArticleId() {
		return firstArticleId;
	}

	public void setFirstArticleId(Long firstArticleId) {
		this.firstArticleId = firstArticleId;
	}

	public String getOtherArticleId() {
		return otherArticleId;
	}

	public void setOtherArticleId(String otherArticleId) {
		this.otherArticleId = otherArticleId;
	}

	public List<ArticleCfgVo> getLstArticleCfg() {
		return lstArticleCfg;
	}

	public void setLstArticleCfg(List<ArticleCfgVo> lstArticleCfg) {
		this.lstArticleCfg = lstArticleCfg;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
