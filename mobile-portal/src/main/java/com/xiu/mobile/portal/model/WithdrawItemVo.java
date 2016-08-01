package com.xiu.mobile.portal.model;

public class WithdrawItemVo {
	 private Long drawId;    //提现申请表Id        
	 private Double applyAmount; //申请返现金额
	 private String bankName; //开户银行
	 private String signName;//开户姓名
	 private String bankAccount; //开户账号
	 private String contactInfo;//联系方式 
	 private String gmtCreate;//申请时间
	 private Integer billStatus;//单据状态 :0:未审核,1:审核中,2:审核通过,3. 审核不通过,4:已返款 5:返款成功 6:返款失败 7:撤销
	 private String billStatusDesc;//单据状态 对应的文字描述信息 
	 private Long userId;//用户Id
	public Long getDrawId() {
		return drawId;
	}
	public void setDrawId(Long drawId) {
		this.drawId = drawId;
	}
	public Double getApplyAmount() {
		return applyAmount;
	}
	public void setApplyAmount(Double applyAmount) {
		this.applyAmount = applyAmount;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getSignName() {
		return signName;
	}
	public void setSignName(String signName) {
		this.signName = signName;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}
	public String getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Integer getBillStatus() {
		return billStatus;
	}
	public void setBillStatus(Integer billStatus) {
		this.billStatus = billStatus;
	}
	public String getBillStatusDesc() {
		return billStatusDesc;
	}
	public void setBillStatusDesc(String billStatusDesc) {
		this.billStatusDesc = billStatusDesc;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	 
}
