package com.xiu.manager.web.biz;

import java.util.List;
import java.util.Map;

import com.xiu.manager.web.model.Role;
import com.xiu.mobile.core.model.Page;

/** 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 
 * @AUTHOR : haijun.chen@xiu.com 
 * @DATE :2013-06-04
 * </p>
 **************************************************************** 
 */
public interface RoleService {
    
	/**
	 * 根据角色Id查询
	 * @param id
	 * @return
	 */
    public Role getRolebyId(Long id);
	
    /**
     * 查询角色 (查询角色 (状态:启用、停用)
     * @param map
     * @return
     */
    public List<Role> getRoleListAll(Map<Object,Object> map,Page<?> page);
    
    /**
     * 查询所有
     * @return
     */
    public List<Role> getRoleList();
    
    /**
     * 修改角色的状态
     * @param map
     * @return
     */
    public int updateRoleStatus(Map<Object,Object> map);
    
    /**
     * 添加--保存角色
     * @param role
     * @param role_menu
     * @return
     */
    public int save(Role role,String role_menu);
    
    /**
     * 角色--修改
     * @param role
     * @param role_menu
     * @param oldroleName
     * @return
     */
    public int update(Role role,String role_menu,String oldroleName);
    
}
