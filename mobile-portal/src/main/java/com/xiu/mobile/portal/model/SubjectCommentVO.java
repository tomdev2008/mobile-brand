package com.xiu.mobile.portal.model;

/**
 * 
* @Description: TODO(商品评论) 
* @author haidong.luo@xiu.com
* @date 2015年10月15日 下午8:10:17 
*
 */
public class SubjectCommentVO {

	// 评论ID
	private Long id;
	
	// 发表评论用户ID
	private Long userId;
	
	// 所属专题ID
	private Long subjectId;
	
	// 昵称
	private String petName;
	
	// 头像
	private String headPortrait;
	
	// 评论时间
	private String commentDate;
	
	// 被评论用户ID
	private Long commentedUserId;
	
	// 被评论用户昵称
	private String commentedPetName;
	
	// 评论内容
	private String content;
	

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

	public String getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(String commentDate) {
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

}
