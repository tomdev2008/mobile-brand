package com.xiu.manager.web.biz;

import java.util.List;
import java.util.Map;

import com.xiu.manager.web.model.UserGroup;
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
public interface UserGroupService {
    
	 /**
     * 查询用户组(分页)
     * @param map
     * @return
     */
    public List<UserGroup> getUserGroupListAll(Map<Object,Object> map,Page<?> page);
	
    /**
     * 保存用户组
     * @param usergroup
     * @param user_usergroup
     * @return
     */
    public int save(UserGroup usergroup,String user_usergroup);
    
    /**
     * 修改用户组的状态
     * @param map
     * @return
     */
    public int updateUserGroupStatus(Map<Object,Object> map);
    
    /**
     * 根据用户组ID查询用户组
     */
    public UserGroup getUserGroup(Long id);
    
    /**
     * 修改用户组
     * @param usergroup
     * @param user_usergroup
     * @param olduserGroupName
     * @return
     */
    public int update(UserGroup usergroup,String user_usergroup,String olduserGroupName);
   
    
}
