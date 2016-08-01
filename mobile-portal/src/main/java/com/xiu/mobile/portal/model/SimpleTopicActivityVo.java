package com.xiu.mobile.portal.model;

import java.io.Serializable;
import java.util.Date;

/***
 * 精选卖场
 * @author hejx
 *
 */
public class SimpleTopicActivityVo implements Serializable {

	private Long id;
	private Long activityId;
	private String name;
	private Date startTime;
	private Date endTime;
	private int orderSeq;
	private String mobilePic;
	private String display;
	private String modifiedByMobile;
	private int displayColNum;
	private Long topicTypeId; // 卖场类型ID{1：中性；2：男士；3：女士}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(int orderSeq) {
		this.orderSeq = orderSeq;
	}

	public String getMobilePic() {
		return mobilePic;
	}

	public void setMobilePic(String mobilePic) {
		this.mobilePic = mobilePic;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getModifiedByMobile() {
		return modifiedByMobile;
	}

	public void setModifiedByMobile(String modifiedByMobile) {
		this.modifiedByMobile = modifiedByMobile;
	}

	public int getDisplayColNum() {
		return displayColNum;
	}

	public void setDisplayColNum(int displayColNum) {
		this.displayColNum = displayColNum;
	}

	public Long getTopicTypeId() {
		return topicTypeId;
	}

	public void setTopicTypeId(Long topicTypeId) {
		this.topicTypeId = topicTypeId;
	}

}
