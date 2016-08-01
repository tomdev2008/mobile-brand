package com.xiu.mobile.core.model;

import java.io.Serializable;

/**
 * IOS app热部署
 * @author Administrator
 *
 */
public class AppPatchVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public long id;	//版本编号
	
	public String name;	//版本名称
	
	public String type;	// appstore;appPro
	
	public String version;	//版本
	
	public Long needUpdate;	//1：是强制升级 0：否强制升级
	
	public Long status;	//0：停用 1：启用
	
	public String path;	//更新文件地址
	
	public String remark;	//备注
	
	public String createTime;	//发布时间
	
	public String md5Encode;	//MD5加密签名 先MD5，再RSA加密
	
	
	public Long jsVersion;	//JS版本，等于id
	

	public String getMd5Encode() {
		return md5Encode;
	}

	public void setMd5Encode(String md5Encode) {
		this.md5Encode = md5Encode;
	}

	public Long getJsVersion() {
		return jsVersion;
	}

	public void setJsVersion(Long jsVersion) {
		this.jsVersion = jsVersion;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Long getNeedUpdate() {
		return needUpdate;
	}

	public void setNeedUpdate(Long needUpdate) {
		this.needUpdate = needUpdate;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "AppPatchVO [id=" + id + ", name=" + name + ", type=" + type
				+ ", version=" + version + ", needUpdate=" + needUpdate
				+ ", status=" + status + ", path=" + path + ", remark="
				+ remark + ", createTime=" + createTime + ", md5Encode="
				+ md5Encode + ", jsVersion=" + jsVersion + "]";
	}
	
}
