package com.xiu.mobile.portal.model;

public class CommentBOResult {

	private String acpCoupon;
	private String percent;
	private CommentResult comment;
	private CommentProdResult commentProd;
	private CommentUserResult commentUser;
	
	public String getAcpCoupon() {
		return acpCoupon;
	}
	public void setAcpCoupon(String acpCoupon) {
		this.acpCoupon = acpCoupon;
	}
	public String getPercent() {
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
	public CommentResult getComment() {
		return comment;
	}
	public void setComment(CommentResult comment) {
		this.comment = comment;
	}
	public CommentProdResult getCommentProd() {
		return commentProd;
	}
	public void setCommentProd(CommentProdResult commentProd) {
		this.commentProd = commentProd;
	}
	public CommentUserResult getCommentUser() {
		return commentUser;
	}
	public void setCommentUser(CommentUserResult commentUser) {
		this.commentUser = commentUser;
	}
	
}
