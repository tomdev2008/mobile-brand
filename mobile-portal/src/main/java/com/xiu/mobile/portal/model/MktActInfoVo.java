package com.xiu.mobile.portal.model;

import java.io.Serializable;

public class MktActInfoVo implements Serializable {
	/**
	 * 活动名称
	 */
	private String activityName;

	/**
	 * 活动类型 1打折 2赠品 3买减 4组合促销
	 */
	private String activityType;

	/**
	 * 活动Id
	 */
	private String activityId;
	/** 赠品列表 */
	private Object[] largess;
	/** 赠品价格 */
	private long largessMoney;
	/** 是否换购 1换购(加钱送) 0赠品 */
	private int largessFlag;
	/** 赠品数量 */
	private int largessNumber;
	/** 参加活动的商品列表 保存的为map<"goods_sn",> */
	private Object[] combinationId;

	
	public Object[] getCombinationId() {
		return combinationId;
	}

	public void setCombinationId(Object[] combinationId) {
		this.combinationId = combinationId;
	}

	public Object[] getLargess() {
		return largess;
	}

	public void setLargess(Object[] largess) {
		this.largess = largess;
	}

	public long getLargessMoney() {
		return largessMoney;
	}

	public void setLargessMoney(long largessMoney) {
		this.largessMoney = largessMoney;
	}

	public int getLargessFlag() {
		return largessFlag;
	}

	public void setLargessFlag(int largessFlag) {
		this.largessFlag = largessFlag;
	}

	public int getLargessNumber() {
		return largessNumber;
	}

	public void setLargessNumber(int largessNumber) {
		this.largessNumber = largessNumber;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	
}
