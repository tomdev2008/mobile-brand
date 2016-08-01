package com.xiu.manager.web.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiu.common.web.utils.EncryptUtils;
import com.xiu.manager.web.biz.UserService;
import com.xiu.manager.web.dao.UserDao;
import com.xiu.manager.web.dao.UserRoleDao;
import com.xiu.manager.web.model.User;
import com.xiu.manager.web.model.UserRole;
import com.xiu.mobile.core.constants.ErrConstants;
import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.core.model.Page;

/** 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 
 * @AUTHOR : haijun.chen@xiu.com 
 * @DATE :2013-06-05 下午2:44:41
 * </p>
 **************************************************************** 
 */
@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {

	//日志
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(UserServiceImpl.class);
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private UserRoleDao userRoleDao;
 
    /**
     * 根据用户ID查询
     */
    @Override
    public User getUser(Long id) {
    	try {
			return userDao.getUser(id);
	    } catch (Throwable e) {
			LOGGER.error("根据用户ID："+id+" 查询用户异常！",e);
			throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_COMMENT_ERR, "根据用户ID："+id+" 查询用户异常！");
		}
    }

    /**
     * 用户登录信息检验
     * @author haijun.chen@xiu.com
     * @param loginName
     * @param password
     */
	@Override
	public Map<Object, Object> check_loginUser(String loginName, String password) {
		Map<Object, Object> uMap = new HashMap<Object, Object>();
		try {
			User user = userDao.getUserByUsername(loginName);
			if (user != null) {
				if (password.equals(user.getPassword())) {
					uMap.put("sCode", "0");
					uMap.put("data", "提示:登录成功！");
					uMap.put("user", user);//用户信息
				} else {
					uMap.put("sCode", "2");
					uMap.put("data", "提示:密码错误！");
					uMap.put("user", null);
				}
			} else {
				uMap.put("sCode", "1");
				uMap.put("data", "提示:帐号不存在！");
				uMap.put("user", null);
			}
		} catch (Exception e) {
			uMap.put("sCode", "2");
			uMap.put("data", "提示:用户登录异常！");
			uMap.put("user", null);
			e.printStackTrace();
			LOGGER.error("用户登录异常！",e);
		}
		return uMap;
	}

	/**
	 * 查询用户(含分页)
	 */
	@Override
	public List<User> getUserListAll(Map<Object, Object> map,Page<?> page) {
		List<User> userlist=new ArrayList<User>();
		try {
			 int pageMax=page.getPageNo()*page.getPageSize();
			 int pageMin=1;
			 if(page.getPageNo()!=1){
				 pageMin=(pageMax-(page.getPageNo()-1)*page.getPageSize())+1;
			 }
			 map.put("pageMin", pageMin-1);
			 map.put("pageSize", page.getPageSize());
			 map.put("pageMax", pageMax);
			//获得分页列表
			 userlist= userDao.getUserListAll(map);
			 // 获取总记录数
		     Long totalCount = Long.parseLong(userDao.getUserTotalCount(map)); 
		     page.setTotalCount(Integer.valueOf(String.valueOf(totalCount)));
		    } catch (Throwable e) {
				LOGGER.error("查询用户异常！",e);
				throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_COMMENT_ERR, "查询用户异常！");
			}
		return userlist;
	}

	/**
	 * 修改用户的状态
	 */
	@Override
	public int updateUserStatus(Map<Object, Object> map) {
		int result=0;
		try {
			if(userDao.updateUserStatus(map)>0){
				result=0;
			}else{
				result=-1;
			}
	    } catch (Throwable e) {
	    	result=-1;
			LOGGER.error("修改用户的状态异常！",e);
			throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_COMMENT_ERR, "修改用户的状态异常！");
		}
		return result;
	}

	
	@Override
    public int updateUserVersion(Long id) {
        int result=0;
        try {
            if(userDao.updateUserVersion(id)>0){
                result=0;
            }else{
                result=-1;
            }
        } catch (Throwable e) {
            result=-1;
            LOGGER.error("修改用户的状态异常！",e);
            throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_COMMENT_ERR, "修改用户的状态异常！");
        }
        return result;
    }

	/**
	 * 添加用户--保存
	 */
	@Override
	public int save(User user, String user_role) {
		int result=0;
		try {
			//根据用户名称查询，判断用户名称是否已经存在
			User u=userDao.getUserByUsername(user.getUsername());
			if(u!=null){
				result=1;
			}else{
			    long user_id=userDao.getUserSEQ();
                user.setId(user_id);
				//获得用户的系列
				user.setCreateDt(new Date());
				if("".equals(user.getPassword())){//设置默认密码
					String password = EncryptUtils.getMD5("123456"); 
					user.setPassword(password);
				} else {
					String password = EncryptUtils.getMD5(user.getPassword()); 
					user.setPassword(password);
				}
				//保存用户
				userDao.addUser(user);
				//保存用户对应的的权限
				if(!"".equals(user_role)){
					if(user_role.indexOf(",")!=-1){
						String [] roleIds=user_role.split(",");
						if(roleIds.length>0){
							for(String roleId:roleIds){
								UserRole ur=new UserRole();
								ur.setUserId(user.getId());
								ur.setRoleId(Long.parseLong(roleId));
								ur.setCreateDt(new Date());
								userRoleDao.addUserRole(ur);
							}
						}
					}else{
						UserRole ur=new UserRole();
						ur.setUserId(user.getId());
						ur.setRoleId(Long.parseLong(user_role));
						ur.setCreateDt(new Date());
						userRoleDao.addUserRole(ur);
					}
				}
				result=0;
			}
		} catch (Throwable e) {
	    	result=-1;
			LOGGER.error("添加用户失败！",e);
			throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_COMMENT_ERR, "添加用户失败！");
		}
		return result;
	}

	/**
	 * 修改用户
	 */
	@Override
	public int update(User user, String user_role,
			String olduserName) {
		int result=0;
		try {
			//根据用户名称查询，判断用户名称是否已经存在
			User u=userDao.getUserByUsername(user.getUsername());
			if(u!=null&&!olduserName.equals(u.getUsername())){
				result=1;
			}else{
				//修改用户
				userDao.updateUser(user);
				//删除用户--角色对应的权限
				userRoleDao.deleteUserRole(user.getId());
				//修改用户对应的的权限
				if(!"".equals(user_role)){
					if(user_role.indexOf(",")!=-1){
						String [] roleIds=user_role.split(",");
						if(roleIds.length>0){
							for(String roleId:roleIds){
								UserRole ur=new UserRole();
								ur.setUserId(user.getId());
								ur.setRoleId(Long.parseLong(roleId));
								ur.setCreateDt(new Date());
								userRoleDao.addUserRole(ur);
							}
						}
					}else{
						UserRole ur=new UserRole();
						ur.setUserId(user.getId());
						ur.setRoleId(Long.parseLong(user_role));
						ur.setCreateDt(new Date());
						userRoleDao.addUserRole(ur);
					}
				}
				result=0;
			}
		} catch (Throwable e) {
	    	result=-1;
			LOGGER.error("修改用户失败！",e);
			throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_COMMENT_ERR, "修改用户失败！");
		}
		return result;
	}

	/**
	 * 用户修改密码
	 */
	@Override
	public int updatepwd(User user,String newpassword) {
		int result=0;
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("id", user.getId());
		map.put("newpassword",newpassword);
		try {
			User u=userDao.getUser(user.getId());
			if(u!=null){
				if(u.getPassword().equals(user.getPassword())){
					userDao.updateUserPwd(map);
					result=0;
				}else{
					result=1;
				}
			}
		} catch (Throwable e) {
	    	result=-1;
			LOGGER.error("修改用户密码失败！",e);
			throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_COMMENT_ERR, "修改用户密码失败！");
		}
		return result;
	}

	/**
	 * 查询用户
	 */
	@Override
	public List<User> getUserList() {
		try {
			return userDao.getUserList();
		} catch (Throwable e) {
			LOGGER.error("查询用户异常！",e);
			throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_COMMENT_ERR, "查询用户异常！");
		}
	}

	public int resetpwd(Map map) {
		return userDao.resetUserPwd(map);
	}

}
