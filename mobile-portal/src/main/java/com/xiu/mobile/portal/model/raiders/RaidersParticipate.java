package com.xiu.mobile.portal.model.raiders;

import java.util.List;

/**
 * 夺宝参与用户信息
 * @author Administrator
 *
 */
public class RaidersParticipate {
	
	
	private Long participateId; //参与Id
	
	private Long userId; //用户ID
	
	private Long raiderId; //夺宝活动ID
	
	private String userHeadPicUrl;//用户头像地址
	
	private String userName; //用户昵称
	
	private String goodName; //活动名称（商品名称）
	
	private String lotteryTime; //开奖时间
	
	private String goodSn; //商品走秀码
	
	private Long raiderMainId;//夺宝活动主表ID
	
	private String goodPicUrl; //商品图片保存路径
	
	private String brandName;//品牌名称
	
	private Integer totalNumber;//活动需总次数
	
	private Integer buyTotal;//购买总次数
	
	private Integer buyNumber;//购买次数
	
	private String raiderNumber; //夺宝号
	
	private String lotteryNumber; //开奖号
	
	private Integer status; //是否中奖  1中奖，不中奖0  
	
	private String raiderCode; //活动期号
	
	private String ratio; //购买比例

	private String IP; //ip地址
	
	private String IPAdr; //ip所在地区
	
	private String createDate; //购买时间
	
	private double goodPrice; //商品价格
	
	private List<RaidersParticipateNumberVo> participateNumber;//参与记录号码
	
	private List<RaidersParticipateNumberVo> friendList;
	
	private List<RaidersParticipateNumberVo> myRaidersList;
	
	public String getRaiderNumber() {
		return raiderNumber;
	}

	public void setRaiderNumber(String raiderNumber) {
		this.raiderNumber = raiderNumber;
	}

	public String getLotteryNumber() {
		return lotteryNumber;
	}

	public void setLotteryNumber(String lotteryNumber) {
		this.lotteryNumber = lotteryNumber;
	}

	public String getGoodSn() {
		return goodSn;
	}

	public void setGoodSn(String goodSn) {
		this.goodSn = goodSn;
	}

	public Long getRaiderMainId() {
		return raiderMainId;
	}

	public void setRaiderMainId(Long raiderMainId) {
		this.raiderMainId = raiderMainId;
	}

	public String getGoodPicUrl() {
		return goodPicUrl;
	}

	public void setGoodPicUrl(String goodPicUrl) {
		this.goodPicUrl = goodPicUrl;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Integer getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}

	public Integer getBuyNumber() {
		return buyNumber;
	}

	public void setBuyNumber(Integer buyNumber) {
		this.buyNumber = buyNumber;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRaiderId() {
		return raiderId;
	}

	public void setRaiderId(Long raiderId) {
		this.raiderId = raiderId;
	}

	public String getUserHeadPicUrl() {
		return userHeadPicUrl;
	}

	public void setUserHeadPicUrl(String userHeadPicUrl) {
		this.userHeadPicUrl = userHeadPicUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public String getLotteryTime() {
		return lotteryTime;
	}

	public void setLotteryTime(String lotteryTime) {
		this.lotteryTime = lotteryTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public String getRaiderCode() {
		return raiderCode;
	}

	public void setRaiderCode(String raiderCode) {
		this.raiderCode = raiderCode;
	}

	public String getRatio() {
		return ratio;
	}

	public void setRatio(String ratio) {
		this.ratio = ratio;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public double getGoodPrice() {
		return goodPrice;
	}

	public void setGoodPrice(double goodPrice) {
		this.goodPrice = goodPrice;
	}

	public Long getParticipateId() {
		return participateId;
	}

	public void setParticipateId(Long participateId) {
		this.participateId = participateId;
	}

	public List<RaidersParticipateNumberVo> getParticipateNumber() {
		return participateNumber;
	}

	public void setParticipateNumber(
			List<RaidersParticipateNumberVo> participateNumber) {
		this.participateNumber = participateNumber;
	}

	public String getIPAdr() {
		return IPAdr;
	}

	public void setIPAdr(String iPAdr) {
		IPAdr = iPAdr;
	}

	public Integer getBuyTotal() {
		return buyTotal;
	}

	public void setBuyTotal(Integer buyTotal) {
		this.buyTotal = buyTotal;
	}

	public List<RaidersParticipateNumberVo> getFriendList() {
		return friendList;
	}

	public void setFriendList(List<RaidersParticipateNumberVo> friendList) {
		this.friendList = friendList;
	}

	public List<RaidersParticipateNumberVo> getMyRaidersList() {
		return myRaidersList;
	}

	public void setMyRaidersList(List<RaidersParticipateNumberVo> myRaidersList) {
		this.myRaidersList = myRaidersList;
	}
	

}
