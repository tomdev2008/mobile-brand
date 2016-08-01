package com.xiu.mobile.core.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

public class Topic extends BaseObject implements Serializable {
	private static final XLogger LOGGER = XLoggerFactory
			.getXLogger(Topic.class);
	private static String dateFormat = "yyyy-MM-dd HH:mm:ss";

	/**
	 * @author: 贾泽伟
	 * @description:卖场维护
	 * @date: 2014-03-18
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private BigDecimal activity_id;
	private String name;
	private Date start_time;
	private Date end_time;
	private Long order_seq;
	private String mobile_pic;
	private String start_time_s;
	private String end_time_s;
	private Long goods_count = 0l;
	private String display;
	private String modified_by_mobile;
	private int display_col_num;
	private String status_s;

	/**
	 * 活动类型ID
	 */
	private Long topicTypeId;
	private int contentType; // 卖场分类 1:促销 2:H5 3:特卖 4:卖场集
	private String URL; // 卖场地址
	
	private int displayStyle;	//展示方向  1:横向 2:竖向
	private String bannerPic;	//卖场集展示图片
	private String displayBannerPic; //卖场集图片是否显示
	private float discount; //折扣
	private String topicType; //卖场分类
	private String dateInfo;
	private int topFlag; //是否置顶标识 1:是 0:否
	private String title; //标题
	private List<LabelCentre> labelCentre;//标签
	private Integer isShowCountDown; //是否显示卖场倒计时 1:是 0:否
	private Integer isEndCanBeSearch; //结束后是否被搜索 1:是 0:否

	public String getStatus_s() {
		return status_s;
	}

	public void setStatus_s(String status_s) {
		this.status_s = status_s;
	}

	public String getModified_by_mobile() {
		return modified_by_mobile;
	}

	public void setModified_by_mobile(String modified_by_mobile) {
		this.modified_by_mobile = modified_by_mobile;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public Long getGoods_count() {
		return goods_count;
	}

	public void setGoods_count(Long goods_count) {
		this.goods_count = goods_count;
	}

	public String getStart_time_s() {
		return start_time_s;
	}

	public void setStart_time_s(String start_time_s) {
		this.start_time_s = start_time_s;
		try {
			SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
			this.start_time = sf.parse(start_time_s);
		} catch (ParseException e) {
			LOGGER.error("start_time_s format wrong");
		}
	}

	public String getEnd_time_s() {
		return end_time_s;
	}

	public void setEnd_time_s(String end_time_s) {
		this.end_time_s = end_time_s;
		try {
			SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
			this.end_time = sf.parse(end_time_s);
		} catch (ParseException e) {
			LOGGER.error("end_time_s format wrong");
		}
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

	public int getDisplay_col_num() {
		return display_col_num;
	}

	public void setDisplay_col_num(int display_col_num) {
		this.display_col_num = display_col_num;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * @return the topicTypeId
	 */
	public Long getTopicTypeId() {
		return topicTypeId;
	}

	/**
	 * @param topicTypeId
	 *            the topicTypeId to set
	 */
	public void setTopicTypeId(Long topicTypeId) {
		this.topicTypeId = topicTypeId;
	}

	public int getContentType() {
		return contentType;
	}

	public void setContentType(int contentType) {
		this.contentType = contentType;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
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
	
	public int getTopFlag() {
		return topFlag;
	}

	public void setTopFlag(int topFlag) {
		this.topFlag = topFlag;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	
	public List<LabelCentre> getLabelCentre() {
		return labelCentre;
	}

	public void setLabelCentre(List<LabelCentre> labelCentre) {
		this.labelCentre = labelCentre;
	}

	public String getDateInfo() {
		StringBuilder dateStr = new StringBuilder();
		if (this.getStart_time() != null && this.getEnd_time() != null) {
			// 如果是特卖卖场 卖场分类 1:促销 2:H5 3:特卖 4:卖场集
			if (this.getContentType() == 3) {
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

	public Integer getIsShowCountDown() {
		return isShowCountDown;
	}

	public void setIsShowCountDown(Integer isShowCountDown) {
		this.isShowCountDown = isShowCountDown;
	}

	public Integer getIsEndCanBeSearch() {
		return isEndCanBeSearch;
	}

	public void setIsEndCanBeSearch(Integer isEndCanBeSearch) {
		this.isEndCanBeSearch = isEndCanBeSearch;
	}
	
	
}
