package com.xiu.mobile.portal.model;

import java.util.Date;
/** 
 * @ClassName: BookmarkIitemListVo 
 * @Description: TODO xiu_goods.iitemlist
 * @author: wangchengqun
 * @date 2014-5-23
 *  
 */
public class BookmarkIitemListVo {

	/**
	 * 
	 * 收藏夹Id
	 */
	private Long iitemlistId;
	/**
	 * 该字段只WCS操作
	 */
	private Long memberId;
	/**
	 * 收藏夹名称
	 */
	private String description;
	/**
	 * 无用
	 */
	private Long sequence;
	/**
	 * 收藏夹创建时间
	 */
	private Date lastupdate;
	/**
	 * 无用
	 */
	private Integer optcounter;
	/**
	 * SSO中的UserId一致
	 */
	private Long userId;

	public Long getIitemlistId() {
		return iitemlistId;
	}

	public void setIitemlistId(Long iitemlistId) {
		this.iitemlistId = iitemlistId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getSequence() {
		return sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public Date getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}

	public Integer getOptcounter() {
		return optcounter;
	}

	public void setOptcounter(Integer optcounter) {
		this.optcounter = optcounter;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
