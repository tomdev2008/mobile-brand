package com.xiu.manager.web.dao;

import java.util.List;
import java.util.Map;

import com.xiu.manager.web.model.User;


public interface UserDao {
    User getUser(Long id);

    User getUserByUsername(String username);

    int addUser(User user);

    int updateUser(User user);

    int deleteUser(Long id);
    
    /**
     * 查询用户(分页)
     * @param map
     * @return
     */
    public List<User> getUserListAll(Map<Object,Object> map);
    
    /**
     * 获得总记录条数
     * @param map
     * @return
     */
    public String getUserTotalCount(Map<Object,Object> map);
    
    /**
     * 查询所有用户
     * @return
     */
    public List<User> getUserList();
    
    /**
     * 修改用户的状态
     * @param map
     * @return
     */
    public int updateUserStatus(Map<Object,Object> map);
    
    public int updateUserVersion(Long id);
    /**
	 * 获得用户系列
	 * @return
	 */
	public long getUserSEQ();
	
	/**
	 * 修改用户密码
	 * @param map
	 * @return
	 */
	public int updateUserPwd(Map<Object,Object> map);
	
	/**
	 * 重置用户密码
	 * @param map
	 * @return
	 */
	public int resetUserPwd(Map map);
	
}
