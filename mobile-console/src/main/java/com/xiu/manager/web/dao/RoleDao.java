package com.xiu.manager.web.dao;

import java.util.List;
import java.util.Map;

import com.xiu.manager.web.model.Role;

/**
 * 角色管理 2013.06.04
 * @author haijun.chen@xiu.com
 */
public interface RoleDao {
	
	/**
	 * 根据角色ID查询
	 * @param id
	 * @return
	 */
	public Role getRoleById(Long id);
	
	/**
	 * 查询角色(状态:启用、停用)，带分页
	 * @return
	 */
	public List<Role> getRoleListAll(Map<Object,Object> map);
	/**
	 * 查询所有角色
	 * @return
	 */
	public List<Role> getRoleList();
	/**
	 * 总记录数量
	 * @param map
	 * @return
	 */
	public String getRoleTotalCount(Map<Object,Object> map);
	
	/**
	 * 修改角色的状态
	 * @param map
	 * @return
	 */
	public int updateRoleStatus(Map<Object,Object> map);
	
	/**
	 * 根据角色名称查询
	 * @param roleName
	 * @return
	 */
	public Role getRoleByroleName(String roleName);
	
	/**
	 * 保存角色
	 * @param role
	 */
	int addRole(Role role);
	
	/**
	 * 修改角色
	 * @param role
	 */
	int updateRole(Role role);
	
	/**
	 * 获得角色系列
	 * @return
	 */
	public long getRoleSEQ();
}
