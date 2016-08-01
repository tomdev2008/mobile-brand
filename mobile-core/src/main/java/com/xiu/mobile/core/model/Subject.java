package com.xiu.mobile.core.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
* @Description: TODO(专题) 
* @author haidong.luo@xiu.com
* @date 2015年10月8日 下午2:50:59 
*
 */
public class Subject  implements Serializable{

	
	private static final long serialVersionUID = -3071990594153114036L;

	private Long subjectId;
	
	private String name;//名称
	
	private String title;//标题
	
	private Integer displayStytle;//1 横排 2竖排
	
	private String outPic;//专题首页显示图片
	
	private Date createTime;
	
	private Date startTime;
	
	private Date endTime;
	
	private Integer status;
	
	private String creatorName;
	
	private Date updateTime;
	
	private Integer orderSeq;
	
	private Integer commentNum;//评论数 
	
	private String labelId; //专题标签Id集
	
	private int isShow; //是否显示列表  1.显示 2.隐藏
	
	/**
	 * 2016-04-28 jesse.chen
	 */
	private Long clickCount;	//专题点击数
	private Long saveCount;	//专题收藏数
	private Long shareCount;	//专题分享数

	// 标签列表
	private List<SubjectItem> contentItemList;
	//专题标签
	private List<Label> labelList;
	//标签中间表
	private List<LabelCentre> labelCentre;//标签中间表
	
	
	
	public Long getClickCount() {
		return clickCount;
	}

	public void setClickCount(Long clickCount) {
		this.clickCount = clickCount;
	}

	public Long getSaveCount() {
		return saveCount;
	}

	public void setSaveCount(Long saveCount) {
		this.saveCount = saveCount;
	}

	public Long getShareCount() {
		return shareCount;
	}

	public void setShareCount(Long shareCount) {
		this.shareCount = shareCount;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getDisplayStytle() {
		return displayStytle;
	}

	public void setDisplayStytle(Integer displayStytle) {
		this.displayStytle = displayStytle;
	}

	public String getOutPic() {
		return outPic;
	}

	public void setOutPic(String outPic) {
		this.outPic = outPic;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public List<SubjectItem> getContentItemList() {
		return contentItemList;
	}

	public void setContentItemList(List<SubjectItem> contentItemList) {
		this.contentItemList = contentItemList;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(Integer orderSeq) {
		this.orderSeq = orderSeq;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public List<Label> getLabelList() {
		return labelList;
	}

	public void setLabelList(List<Label> labelList) {
		this.labelList = labelList;
	}

	public List<LabelCentre> getLabelCentre() {
		return labelCentre;
	}

	public void setLabelCentre(List<LabelCentre> labelCentre) {
		this.labelCentre = labelCentre;
	}

	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}

}
