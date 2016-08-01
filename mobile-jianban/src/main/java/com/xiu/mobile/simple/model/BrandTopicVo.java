package com.xiu.mobile.simple.model;

import java.util.Date;

/**
 * @author: wangchengqun
 * @description:品牌收藏对应的精选卖场
 * @date: 2014-06-17
 */
public class BrandTopicVo {
	/**
	 * 品牌主名称
	 */
	private String brandName;
	/**
	 * 专题Id
	 */
	private Long  activityId;
	/**
	 * 卖场名称
	 */
	private String name;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 卖场图片
	 */
	private String mobilePic;
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
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
	public String getMobilePic() {
		return mobilePic;
	}
	public void setMobilePic(String mobilePic) {
		this.mobilePic = mobilePic;
	}
	
}
