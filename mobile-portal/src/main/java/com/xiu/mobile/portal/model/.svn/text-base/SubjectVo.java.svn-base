package com.xiu.mobile.portal.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.LabelCentre;


public class SubjectVo  implements Serializable{

	private static final long serialVersionUID = -2357293713717427086L;

	
	private Long subjectId;
	
	private String name;//名称
	
	private String title;//
	
	private Integer displayStytle;//1 横排 2竖排
	
	private String outPic;//专题首页显示图片
	
	private Date createTime;
	
	private Date startTime;
	
	private Date endTime;
	
	private String publishTime;
	
	private String sharePic;//分享图
	
	/**
	 * 2016-04-28 jesse.chen
	 */
	private Long clickCount;	//专题点击数
	private Long saveCount;	//专题收藏数
	private Long shareCount;	//专题分享数
	
	private Long isSave;	//专题是否 收藏 1:是 0：否
	
	// 专题项列表
	private List<SubjectItemVo> contentItemList;

	private List<Map<String, Object>> subjectTagList;
	//标签中间表
	private List<LabelCentre> labelCentre;//标签中间表
	
	
	public Long getIsSave() {
		return isSave;
	}

	public void setIsSave(Long isSave) {
		this.isSave = isSave;
	}

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

	public List<SubjectItemVo> getContentItemList() {
		return contentItemList;
	}

	public void setContentItemList(List<SubjectItemVo> contentItemList) {
		this.contentItemList = contentItemList;
	}

	public String getSharePic() {
		return sharePic;
	}

	public void setSharePic(String sharePic) {
		this.sharePic = sharePic;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public List<LabelCentre> getLabelCentre() {
		return labelCentre;
	}

	public void setLabelCentre(List<LabelCentre> labelCentre) {
		this.labelCentre = labelCentre;
	}

	public List<Map<String, Object>> getSubjectTagList() {return subjectTagList;}

	public void setSubjectTagList(List<Map<String, Object>> subjectTagList) {this.subjectTagList = subjectTagList;}
}
