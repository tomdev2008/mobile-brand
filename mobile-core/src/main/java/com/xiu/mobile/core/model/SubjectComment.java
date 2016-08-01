package com.xiu.mobile.core.model;

import java.util.Date;

/**
 * 
* @Description: TODO(商品评论) 
* @author haidong.luo@xiu.com
* @date 2015年10月15日 下午8:09:46 
*
 */
public class SubjectComment {

	// 评论ID
	private Long id;
	
	// 发表评论用户ID
	private Long userId;
	
	// 所属专题ID
	private Long subjectId;
	
	// 专题名称
	private String subjectName;
	
	// 昵称
	private String petName;
	
	// 头像
	private String headPortrait;
	
	// 评论时间
	private Date commentDate;
	
	// 被评论用户ID
	private Long commentedUserId;
	
	// 被评论用户昵称
	private String commentedPetName;
	
	// 评论内容
	private String content;
	
	// 删除标示
	private Integer deleteFlag;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public String getHeadPortrait() {
		return headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}

	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	public Long getCommentedUserId() {
		return commentedUserId;
	}

	public void setCommentedUserId(Long commentedUserId) {
		this.commentedUserId = commentedUserId;
	}
	
	public String getCommentedPetName() {
		return commentedPetName;
	}

	public void setCommentedPetName(String commentedPetName) {
		this.commentedPetName = commentedPetName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

}
