/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-20 上午10:55:44 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.mobile.portal.model;

import java.io.Serializable;
import java.util.Date;

import com.xiu.mobile.core.utils.DateUtil;
import com.xiu.sales.common.card.dataobject.CardListOutParamDO;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(优惠券信息) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-20 上午10:55:44 
 * ***************************************************************
 * </p>
 */

public class CouponVo implements Serializable {
	
	/**
	 * 优惠券卡ID
	 */
	private String cardId;
	
	/**
	 * 用户ID
	 */
	private long userId;
	
	/** * 使用次数 */
	private int useTimes;
	
	/** * 使用状态 */
	private String useStatus;
	
	/**
	 * 类型
	 */
	private int cardType;
	
	/**
	 * 类型名称
	 */
	private String cardTypeName;
	
	/**
	 * 开始时间
	 */
	private String startTime;
	
	/**
	 * 结束时间
	 */
	private String endTime;
	
	/**
	 * 活动时间
	 */
	private String activateTime;
	
	/**
	 * 详情
	 */
	private String cardDetail;
	
	/**
	 * 状态
	 */
	private String status;
	
	/** * 优惠券类型字符串 * */
	private String cardRuleLists;
	
	/** * 优惠券使用时间 * */
	private String usedTime;
	
	/**
	 * 优惠券限额
	 */
	private long limitAmount;
	
	/**
	 * 客户使用规则限制(1 不限 2 受限)
	 */
	private int ruleUseLimit;
	
	/**
	 * 规则使用限制次数，0表示不限制
	 */
	private int ruleUseLimitTimes;
	
	/**
	 * 优惠券备注
	 */
	private String remark;
	
	// 优惠券类型
	private String couponType;
	
	// 使用条件
	private String useCondition;
	
	//使用终端(1 手机用户 2 电脑用户) 
	private String terminalUser;
	
	/**
	 * 无参构造器
	 */
	public CouponVo() {
	}
	
	/**
	 * CardListOutParamDO 转换构造器
	 * @param cardListOutParamDO
	 */
	public CouponVo(CardListOutParamDO cardListOutParamDO) {
		this.cardId = cardListOutParamDO.getCardId();
		this.userId = cardListOutParamDO.getUserId();
		this.cardType = cardListOutParamDO.getCardType();
		this.cardTypeName = cardListOutParamDO.getCardTypeName();
		this.startTime = cardListOutParamDO.getRuleBeginTime();
		this.endTime = cardListOutParamDO.getEndTime();
		this.activateTime = cardListOutParamDO.getActivateTime();
		this.cardDetail = cardListOutParamDO.getCardDetail();
		this.status = cardListOutParamDO.getStatus();
		this.cardRuleLists = cardListOutParamDO.getCardRuleLists();
		this.limitAmount = cardListOutParamDO.getLimitAmount();
		this.ruleUseLimit = cardListOutParamDO.getRuleUseLimit();
		this.ruleUseLimitTimes = cardListOutParamDO.getRuleUseLimitTimes();
		this.usedTime = cardListOutParamDO.getUsedTime();
		String userStatus=cardListOutParamDO.getUseStatus();
		if( userStatus!=null&&userStatus.equals("未使用")){
			userStatus="可使用";
			if(startTime!=null){
				Date now =new Date();
				Date start=DateUtil.parseTime(startTime);
				if(start.after(now)){
					userStatus="未开始";
				}
			}
			
		}
		this.useStatus = userStatus;
		this.useTimes = cardListOutParamDO.getUseTimes();
		this.remark = cardListOutParamDO.getRemark();
		String terminalUser=cardListOutParamDO.getTerminalUser();
		if(terminalUser!=null&&!terminalUser.equals("")){
			if(terminalUser.equals("1,2")){//全平台
				terminalUser="0";
			}
		}
		this.terminalUser =terminalUser;
		// cardType类型说明：1为折扣券  2为代金券 3.赠品劵
		String desc = cardListOutParamDO.getCardRuleLists();
		desc=desc.replace("\n", "");
		cardListOutParamDO.setCardRuleLists(desc);
		if (cardListOutParamDO.getCardType() == 2) {
			String temp= desc.substring(desc.indexOf("减")+1);
			this.couponType = temp.substring(0,temp.indexOf("元"));
			this.useCondition = desc.substring(0, desc.indexOf("减")).concat("可使用");
		}else if(cardListOutParamDO.getCardType() == 1){
			this.couponType = desc.substring(desc.indexOf("打")+1,desc.indexOf("折"));
			this.useCondition = desc.substring(0, desc.indexOf("打")).concat("可使用");
		}else if(cardListOutParamDO.getCardType() == 3){
			//滿100.0元赠送:103091790001;103091790002;103091790003;103091790004;103091790005;
			desc=desc.substring(0,desc.indexOf("元")+1);
			this.couponType = "赠品";
			this.useCondition = desc+",可使用";
			this.cardRuleLists = desc+"赠送赠品";
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cardId == null) ? 0 : cardId.hashCode());
		result = prime * result + (int) (userId ^ (userId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CouponVo other = (CouponVo) obj;
		if (cardId == null) {
			if (other.cardId != null)
				return false;
		} else if (!cardId.equals(other.cardId))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	/**
	 * @return the cardId
	 */
	public String getCardId() {
		return cardId;
	}

	/**
	 * @param cardId the cardId to set
	 */
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @return the cardType
	 */
	public int getCardType() {
		return cardType;
	}

	/**
	 * @param cardType the cardType to set
	 */
	public void setCardType(int cardType) {
		this.cardType = cardType;
	}

	/**
	 * @return the cardTypeName
	 */
	public String getCardTypeName() {
		return cardTypeName;
	}

	/**
	 * @param cardTypeName the cardTypeName to set
	 */
	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the activateTime
	 */
	public String getActivateTime() {
		return activateTime;
	}

	/**
	 * @param activateTime the activateTime to set
	 */
	public void setActivateTime(String activateTime) {
		this.activateTime = activateTime;
	}

	/**
	 * @return the cardDetail
	 */
	public String getCardDetail() {
		return cardDetail;
	}

	/**
	 * @param cardDetail the cardDetail to set
	 */
	public void setCardDetail(String cardDetail) {
		this.cardDetail = cardDetail;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getUseTimes() {
		return useTimes;
	}

	public void setUseTimes(int useTimes) {
		this.useTimes = useTimes;
	}

	public String getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus;
	}

	public String getCardRuleLists() {
		return cardRuleLists;
	}

	public void setCardRuleLists(String cardRuleLists) {
		this.cardRuleLists = cardRuleLists;
	}

	public String getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(String usedTime) {
		this.usedTime = usedTime;
	}

	public long getLimitAmount() {
		return limitAmount;
	}

	public void setLimitAmount(long limitAmount) {
		this.limitAmount = limitAmount;
	}

	public int getRuleUseLimit() {
		return ruleUseLimit;
	}

	public void setRuleUseLimit(int ruleUseLimit) {
		this.ruleUseLimit = ruleUseLimit;
	}

	public int getRuleUseLimitTimes() {
		return ruleUseLimitTimes;
	}

	public void setRuleUseLimitTimes(int ruleUseLimitTimes) {
		this.ruleUseLimitTimes = ruleUseLimitTimes;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	public String getUseCondition() {
		return useCondition;
	}

	public void setUseCondition(String useCondition) {
		this.useCondition = useCondition;
	}

	public String getTerminalUser() {
		return terminalUser;
	}

	public void setTerminalUser(String terminalUser) {
		this.terminalUser = terminalUser;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@Override
	public String toString() {
		return "CouponVo [cardId=" + cardId + ", userId=" + userId + ", useTimes=" + useTimes + ", useStatus=" + useStatus 
				+ ", cardType=" + cardType + ", cardTypeName=" + cardTypeName + ", endTime=" + endTime + ", activateTime=" + activateTime 
				+ ", cardDetail=" + cardDetail + ", status=" + status+ ", cardRuleLists=" + cardRuleLists + ", usedTime=" + usedTime 
				+ ", limitAmount=" + limitAmount + ", ruleUseLimit=" + ruleUseLimit + ", ruleUseLimitTimes=" + ruleUseLimitTimes 
				+ ", remark=" + remark + ", couponType=" + couponType + ", useCondition=" + useCondition + "]";
	}


}
