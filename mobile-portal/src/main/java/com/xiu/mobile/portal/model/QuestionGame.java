package com.xiu.mobile.portal.model;

import java.io.Serializable;

/***
 * 游戏问题
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("serial")
public class QuestionGame implements Serializable {

	private long id;
	private String name; // 名称
	private String imgURL; // 图片地址
	private int score; // 分数
	private int sex; // 性别 1为男 0为女
	private int questionNo; // 题号

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgURL() {
		return imgURL;
	}

	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getQuestionNo() {
		return questionNo;
	}

	public void setQuestionNo(int questionNo) {
		this.questionNo = questionNo;
	}

	@Override
	public String toString() {
		return "QuestionGame [id=" + id + ", name=" + name + ", imgURL=" + imgURL + ", score=" + score + ", sex=" + sex + ", questionNo=" + questionNo + "]";
	}

}
