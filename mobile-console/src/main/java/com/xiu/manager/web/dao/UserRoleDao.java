package com.xiu.manager.web.dao;

import java.util.List;

import com.xiu.manager.web.model.UserRole;

/**
 * 用户对应角色  2013.06.05
 * @author haijun.chen@xiu.com
 */
public interface UserRoleDao {
	
	/**
	 *  保存用户--角色 对应权限
	 * @param menu
	 */
	void addUserRole(UserRole userRole);
	
	/**
	 * 根据用户ID查询
	 * @param roleId
	 * @return
	 */
	public List<UserRole> getUserRolebyuserId(Long userId);
	
	/**
	 * 根据用户Id删除 对应的角色
	 * @param roleId
	 * @return
	 */
	public int deleteUserRole(Long userId);
}
