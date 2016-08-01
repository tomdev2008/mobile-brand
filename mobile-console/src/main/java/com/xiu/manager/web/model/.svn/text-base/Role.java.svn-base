package com.xiu.manager.web.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.xiu.mobile.core.model.BaseObject;

/**
 * 角色表 2013.06.04 
 * @author haijun.chen@xiu.com
 */
public class Role extends BaseObject implements Serializable {
	private static final long serialVersionUID = 5908372387868348561L;

	private Long id; // 角色ID
	private String roleName; // 角色名称
	private String roleDesc; // 角色描述
	private Date createDt; // 创建时间
	private Long status; // 状态: 1 启用 , 2作废
	private Integer version; // 版本号

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
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
