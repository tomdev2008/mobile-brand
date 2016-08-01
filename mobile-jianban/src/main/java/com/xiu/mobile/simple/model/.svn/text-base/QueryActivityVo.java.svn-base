/**  
 * @Project: xiu
 * @Title: QueryActivityVo.java
 * @Package org.lazicats.xiu.topicActivity.model.vo
 * @Description: TODO
 * @author: chengyuanhuan
 * @date 2013-5-2 下午7:30:13
 * @Copyright: BroadenGate Software Services Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.xiu.mobile.simple.model;

import java.io.Serializable;

/**
 * 查询活动VO
 * 
 */
public class QueryActivityVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private int pageNum = 1;// 当前页
	private int pageSize = 20;// 每页大小,默认20
	private String activityType;// 活动类型 0:专题活动 1:名品特卖 2:奢华惠
	private String result;// 异步调用返回结果
	private String activityId;// 活动Id
	private String catId = "";// 分类Id
	private String subType; // 商品分类

	/**
	 * 0:默认
	 * 1：价格升序
	 * 2.价格降序
	 * 3：折扣升序
	 * 4.折扣降序
	 */
	private String order = "0";// 排序
	private String showType = "0"; // 0：大图 1:列表

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public String getSubType() {
		/*try {
			if (null != this.subType) {
				return new String(subType.getBytes("iso-8859-1"), "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/
		return this.subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

}
