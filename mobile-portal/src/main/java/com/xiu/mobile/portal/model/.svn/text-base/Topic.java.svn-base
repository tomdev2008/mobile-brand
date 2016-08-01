package com.xiu.mobile.portal.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: 贾泽伟
 * @description:专题卖场
 * @date: 2014-03-24
 */
public class Topic {

	private Long id;
	private BigDecimal activity_id;
	private String name;
	private Date start_time;
	private Date end_time;
	private String dateInfo;
	private Long order_seq;
	private String mobile_pic;
	private int content_type;// 卖场分类 1:促销 2:H5 3:特卖 4:卖场集

	/**
	 * 活动类型ID
	 */
	private Long topicTypeId;
	private String url; // 卖场地址
	private int displayStyle; // 展示方向 1:横向 2:竖向
	private String bannerPic; // 卖场集展示图片
	private String displayBannerPic; //卖场集图片是否显示
	private float discount; //折扣
	private String topicType; //卖场分类，多个分类以逗号分割
	private String title;	//标题
	private String description;	//描述
	private Date endTime;	//结束时间
	private Integer isShowCountDown; //是否显示卖场倒计时 1:是 0:否

	
	public int getContent_type() {
		return content_type;
	}

	public void setContent_type(int content_type) {
		this.content_type = content_type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMobile_pic() {
		return mobile_pic;
	}

	public void setMobile_pic(String mobile_pic) {
		this.mobile_pic = mobile_pic;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setActivity_id(BigDecimal activity_id) {
		this.activity_id = activity_id;
	}

	public void setOrder_seq(Long order_seq) {
		this.order_seq = order_seq;
	}

	public Long getId() {
		return id;
	}

	public BigDecimal getActivity_id() {
		return activity_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public Long getOrder_seq() {
		return order_seq;
	}

	public Long getTopicTypeId() {
		return topicTypeId;
	}

	public void setTopicTypeId(Long topicTypeId) {
		this.topicTypeId = topicTypeId;
	}

	public int getDisplayStyle() {
		return displayStyle;
	}

	public void setDisplayStyle(int displayStyle) {
		this.displayStyle = displayStyle;
	}

	public String getBannerPic() {
		return bannerPic;
	}

	public void setBannerPic(String bannerPic) {
		this.bannerPic = bannerPic;
	}

	public String getDisplayBannerPic() {
		return displayBannerPic;
	}

	public void setDisplayBannerPic(String displayBannerPic) {
		this.displayBannerPic = displayBannerPic;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}
	
	public String getTopicType() {
		return topicType;
	}

	public void setTopicType(String topicType) {
		this.topicType = topicType;
	}
	
	public String getDateInfo() {
		StringBuilder dateStr = new StringBuilder();
		if (this.getStart_time() != null && this.getEnd_time() != null) {
			// 如果是特卖卖场 卖场分类 1:促销 2:H5 3:特卖 4:卖场集
			if (this.getContent_type() == 3) {
				int diffDays = daysOfTwo(new Date(), this.getEnd_time());
				if (diffDays <= 0) {
					dateStr.append("今天截止");
				}else{
					dateStr.append("剩").append(diffDays).append("天");
				}
			} else {
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日");
				dateStr.append(dateFormat.format(this.getStart_time()));
				dateStr.append(" - ").append(dateFormat.format(this.getEnd_time()));
			}
		}
		return dateStr.toString();
	}
	
	public void setDateInfo(String dateInfo) {
		this.dateInfo = dateInfo;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getIsShowCountDown() {
		return isShowCountDown;
	}

	public void setIsShowCountDown(Integer isShowCountDown) {
		this.isShowCountDown = isShowCountDown;
	}

	/***
	 * 计算两个时间相差天数
	 * @param fDate
	 * @param oDate
	 * @return
	 */
	public int daysOfTwo(Date bDate, Date eDate) {
		// 格式化两个日期
        long time1 = bDate.getTime();               
        long time2 = eDate.getTime();   
        if(time2-time1 < 0) {
        	return 0;
        } else {
        	// 后一个时间天数减去前一个时间天数  除以时分秒毫秒得到天数
            long diffDays= (time2-time1)/(1000*3600*24);   
            // 天数相减获取值
            int days = new Long(diffDays).intValue() + 1;  //天数修正
            return days;
        }
	}

	@Override
	public String toString() {
		return "Topic [id=" + id + ", activity_id=" + activity_id + ", name=" + name + ", start_time=" + start_time + ", end_time=" + end_time + ", dateInfo=" + dateInfo + ", order_seq=" + order_seq + ", mobile_pic=" + mobile_pic + ", content_type=" + content_type + ", topicTypeId=" + topicTypeId
				+ ", url=" + url + ", displayStyle=" + displayStyle + ", bannerPic=" + bannerPic + ", displayBannerPic=" + displayBannerPic + ", discount=" + discount + ", topicType=" + topicType + "]";
	}


}
