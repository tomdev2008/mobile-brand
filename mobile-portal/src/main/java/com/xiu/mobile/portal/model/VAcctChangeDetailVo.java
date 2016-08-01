package com.xiu.mobile.portal.model;


public class VAcctChangeDetailVo {
	/** 账目变更序列号 */
	private Long accountChangeId;

	/** 账目ID */
	private Long acctItemId;
	/**
	 * 账目进出类型 01.进账 02出帐 03冻结 04解冻
	 */
	private String ioType;
	/** 交易时间 */
	private String createTime;
	/** 变动金额 */
	private Double ioAmount;

	/** 业务类型 */
	private String busiType = "";

	/**
	 * 业务类型 文字描述信息
	 */
	private String busiTypeDesc = "";
	/** 关联信息 导致帐目变化的业务流水code */

	private String rltCode;
	/** 帐户总额 变更操作后的 */
	private Double lastIoAmount;
	/**
	 * 金额类型 01 可提现 02不可提现 03积分
	 */
	private String acctTypeCode = "";

	/**
	 * 金额类型文字描述信息
	 */
	private String acctTypeCodeDesc = "";
	/**
	 * 账户ID
	 */
	private Long acctId;

	/**
	 * 用户ID
	 */
	private Long userId;

	public Long getAccountChangeId() {
		return accountChangeId;
	}

	public void setAccountChangeId(Long accountChangeId) {
		this.accountChangeId = accountChangeId;
	}

	public Long getAcctItemId() {
		return acctItemId;
	}

	public void setAcctItemId(Long acctItemId) {
		this.acctItemId = acctItemId;
	}

	public String getIoType() {
		return ioType;
	}

	public void setIoType(String ioType) {
		this.ioType = ioType;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Double getIoAmount() {
		return ioAmount;
	}

	public void setIoAmount(Double ioAmount) {
		this.ioAmount = ioAmount;
	}

	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public String getBusiTypeDesc() {
		return busiTypeDesc;
	}

	public void setBusiTypeDesc(String busiTypeDesc) {
		this.busiTypeDesc = busiTypeDesc;
	}

	public String getRltCode() {
		return rltCode;
	}

	public void setRltCode(String rltCode) {
		this.rltCode = rltCode;
	}

	public Double getLastIoAmount() {
		return lastIoAmount;
	}

	public void setLastIoAmount(Double lastIoAmount) {
		this.lastIoAmount = lastIoAmount;
	}

	public String getAcctTypeCode() {
		return acctTypeCode;
	}

	public void setAcctTypeCode(String acctTypeCode) {
		this.acctTypeCode = acctTypeCode;
	}

	public String getAcctTypeCodeDesc() {
		return acctTypeCodeDesc;
	}

	public void setAcctTypeCodeDesc(String acctTypeCodeDesc) {
		this.acctTypeCodeDesc = acctTypeCodeDesc;
	}

	public Long getAcctId() {
		return acctId;
	}

	public void setAcctId(Long acctId) {
		this.acctId = acctId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	

}
