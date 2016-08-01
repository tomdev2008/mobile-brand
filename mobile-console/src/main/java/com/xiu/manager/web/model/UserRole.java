package com.xiu.manager.web.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.xiu.mobile.core.model.BaseObject;

/**
 * 用户--角色关系表 2013.06.04 
 * @author haijun.chen@xiu.com
 */
public class UserRole extends BaseObject implements Serializable {
	private static final long serialVersionUID = 5908372387868348561L;

	private Long id; // 主键ID
	private Long userId; // 用户ID
	private Long roleId; // 角色ID
	private Date createDt; // 创建时间
	private Integer version; // 版本号


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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
