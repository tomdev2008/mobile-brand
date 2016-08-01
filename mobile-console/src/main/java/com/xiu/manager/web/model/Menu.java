package com.xiu.manager.web.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.xiu.mobile.core.model.BaseObject;

/**
 * 系统菜单 2013.05.31 
 * @author haijun.chen@xiu.com
 */
public class Menu extends BaseObject implements Serializable {
	private static final long serialVersionUID = 5908372387868348561L;

	private Long id; // 菜单ID
	private String menuName; // 菜单名称
	private String menuUrl; // 菜单URL
	private Long parentId; // 上级菜单
	private List<Menu> mlist;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public List<Menu> getMlist() {
		return mlist;
	}

	public void setMlist(List<Menu> mlist) {
		this.mlist = mlist;
	}

}
