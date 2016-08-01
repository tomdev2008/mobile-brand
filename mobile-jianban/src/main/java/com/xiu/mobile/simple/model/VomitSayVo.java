package com.xiu.mobile.simple.model;

/**
 * 吐槽对象
 * 
 * @author wenxiaozhuo
 * 
 */
public class VomitSayVo {
	private Long vomitId;// 吐槽ID
	private String vomitUsername;// 吐槽用户名
	private String vomitContact;// 联系方式
	private String vomitRmks;// 吐槽内容
	private String vomitTime;// 吐槽时间
	private String vomitDevInfo;// 设备信息
	private String appVerName;// app版本名称
	private String phoneBrand;// 手机品牌
	private String os;// 操作系统
	private String osVer;// 操作系统版本

	public Long getVomitId() {
		return vomitId;
	}

	public void setVomitId(Long vomitId) {
		this.vomitId = vomitId;
	}

	public String getVomitUsername() {
		return vomitUsername;
	}

	public void setVomitUsername(String vomitUsername) {
		this.vomitUsername = vomitUsername;
	}

	public String getVomitContact() {
		return vomitContact;
	}

	public void setVomitContact(String vomitContact) {
		this.vomitContact = vomitContact;
	}

	public String getVomitRmks() {
		return vomitRmks;
	}

	public void setVomitRmks(String vomitRmks) {
		this.vomitRmks = vomitRmks;
	}

	public String getVomitTime() {
		return vomitTime;
	}

	public void setVomitTime(String vomitTime) {
		this.vomitTime = vomitTime;
	}

	public String getVomitDevInfo() {
		return vomitDevInfo;
	}

	public void setVomitDevInfo(String vomitDevInfo) {
		this.vomitDevInfo = vomitDevInfo;
	}

	public String getAppVerName() {
		return appVerName;
	}

	public void setAppVerName(String appVerName) {
		this.appVerName = appVerName;
	}

	public String getPhoneBrand() {
		return phoneBrand;
	}

	public void setPhoneBrand(String phoneBrand) {
		this.phoneBrand = phoneBrand;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getOsVer() {
		return osVer;
	}

	public void setOsVer(String osVer) {
		this.osVer = osVer;
	}

}
