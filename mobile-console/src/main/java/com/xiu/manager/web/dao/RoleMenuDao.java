package com.xiu.manager.web.dao;
import java.util.List;

import com.xiu.manager.web.model.RoleMenu;

/**
 * 角色对应菜单  2013.06.05
 * @author haijun.chen@xiu.com
 */
public interface RoleMenuDao {
	
	/**
	 *  保存角色--菜单 对应权限
	 * @param menu
	 */
	void addRoleMenu(RoleMenu roleMenu);
	
	/**
	 * 根据角色ID查询
	 * @param roleId
	 * @return
	 */
	public List<RoleMenu> getRoleMenubyroleId(Long roleId);
	
	
	/**
	 * 根据角色Id删除 对应权限
	 * @param roleId
	 * @return
	 */
	public int deleteRoleMenu(Long roleId);
	
}
