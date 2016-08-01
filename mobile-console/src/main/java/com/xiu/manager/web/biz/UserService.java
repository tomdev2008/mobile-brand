package com.xiu.manager.web.biz;

import java.util.List;
import java.util.Map;

import com.xiu.manager.web.model.User;
import com.xiu.mobile.core.model.Page;


/** 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 
 * @AUTHOR : mike@xiu.com 
 * @DATE :2013-5-24 下午2:43:29
 * </p>
 **************************************************************** 
 */
public interface UserService {
    public User getUser(Long id);
    
    /**
     * 用户登录信息检验
     * @author haijun.chen@xiu.com
     * @param loginName
     * @param password
     */
    public Map<Object,Object> check_loginUser(String loginName, String password);
    
    /**
     * 查询用户(含分页)
     * @param map
     * @return
     */
    public List<User> getUserListAll(Map<Object,Object> map,Page<?> page);
    
    /**
     * 查询所有用户
     * @param map
     * @return
     */
    public List<User> getUserList();
    /**
     * 修改用户的状态
     * @param map
     * @return
     */
    public int updateUserStatus(Map<Object,Object> map);
    
    /**
     * 
    * @Description: 
    * @param id
    * @return
     */
    public int updateUserVersion(Long id);
    /**
     * 添加用户
     * @param user
     * @param user_role
     * @return
     */
    public int save(User user,String user_role);
    
    /**
     * 修改用户
     * @param user
     * @param user_role
     * @param olduserName
     * @return
     */
    public int update(User user,String user_role,String olduserName);
    
   /**
    * 用户修改密码
    * @param user
    * @param newpassword
    * @return
    */
    public int updatepwd(User user,String newpassword);
    
    /**
     * 重置用户密码
     * @param map
     * @return
     */
    public int resetpwd(Map map);
}
