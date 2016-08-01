package com.xiu.manager.web.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.xiu.mobile.core.model.BaseObject;

/**
 * 角色--菜单关系表 2013.06.04 
 * @author haijun.chen@xiu.com
 */
public class RoleMenu extends BaseObject implements Serializable {
	private static final long serialVersionUID = 5908372387868348561L;

	private Long id; // 主键ID
	private Long roleId; // 角色ID
	private Long menuId; // 菜单ID
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

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
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
