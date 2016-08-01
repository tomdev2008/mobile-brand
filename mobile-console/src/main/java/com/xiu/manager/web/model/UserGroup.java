package com.xiu.manager.web.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.xiu.mobile.core.model.BaseObject;

/**
 * 用户组 2013.06.06 
 * @author haijun.chen@xiu.com
 */
public class UserGroup extends BaseObject implements Serializable {
	private static final long serialVersionUID = 5908372387868348561L;

	private Long id;              // 主键ID
	private String userGroupName; // 用户组名称
	private String userGroupDesc; // 用户组描述
	private Long status;          //标记(1 启用,0 停用)
	private Date createDt;        // 创建时间
	private Integer version;      // 版本号


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserGroupName() {
		return userGroupName;
	}

	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}

	public String getUserGroupDesc() {
		return userGroupDesc;
	}

	public void setUserGroupDesc(String userGroupDesc) {
		this.userGroupDesc = userGroupDesc;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
