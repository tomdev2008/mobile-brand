package com.xiu.mobile.portal.model.raiders;
/**
 * 夺宝参与用户信息
 * @author Administrator
 *
 */
public class RaidersParticipateNumberVo {
	
	private Long userId; //用户ID
	
	private Long participateId; //参与Id

	private String participateNumber;//参与号码
	
	private Integer type;//类型1自己购买 2好友赠送  3已赠送给好友
	
	private Integer isLottery;//是否中奖 0否 1是  默认0没中奖

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getParticipateId() {
		return participateId;
	}

	public void setParticipateId(Long participateId) {
		this.participateId = participateId;
	}

	public String getParticipateNumber() {
		return participateNumber;
	}

	public void setParticipateNumber(String participateNumber) {
		this.participateNumber = participateNumber;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getIsLottery() {
		return isLottery;
	}

	public void setIsLottery(Integer isLottery) {
		this.isLottery = isLottery;
	}
	
	
	

}
