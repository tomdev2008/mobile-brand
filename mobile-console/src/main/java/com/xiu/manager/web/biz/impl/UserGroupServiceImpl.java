package com.xiu.manager.web.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiu.manager.web.biz.UserGroupService;
import com.xiu.manager.web.dao.UserGroupDao;
import com.xiu.manager.web.dao.UserUserGroupDao;
import com.xiu.manager.web.model.UserGroup;
import com.xiu.manager.web.model.UserUserGroup;
import com.xiu.mobile.core.constants.ErrConstants;
import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.core.model.Page;

/** 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 
 * @AUTHOR : haijun.chen@xiu.com 
 * @DATE :2013-06-06
 * </p>
 **************************************************************** 
 */
@Transactional
@Service("userGroupService")
public class UserGroupServiceImpl implements UserGroupService {
	
	//日志
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(UserGroupServiceImpl.class);

    @Autowired
    private UserGroupDao userGroupDao;
    
    @Autowired
    private UserUserGroupDao userUserGroupDao;
    
    /**
     * 查询用户组(分页)
     */
	@Override
	public List<UserGroup> getUserGroupListAll(Map<Object, Object> map,Page<?> page) {
		List<UserGroup> usergrouplist=new ArrayList<UserGroup>();
		try {
			 int pageMax=page.getPageNo()*page.getPageSize();
			 int pageMin=1;
			 if(page.getPageNo()!=1){
				 pageMin=(pageMax-(page.getPageNo()-1)*page.getPageSize())+1;
			 }
			 map.put("pageMin", pageMin);
			 map.put("pageMax", pageMax);
			 //获得分页列表
			 usergrouplist=userGroupDao.getUserGroupListAll(map);
			  // 获取总记录数
		     Long totalCount = Long.parseLong(userGroupDao.getUserGroupTotalCount(map)); 
		     page.setTotalCount(Integer.valueOf(String.valueOf(totalCount)));
		} catch (Throwable e) {
			LOGGER.error("查询用户组异常！",e);
			throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_COMMENT_ERR, "查询用户组异常！");
		}
		return usergrouplist;
	}

	/**
	 * 保存用户组
	 */
	@Override
	public int save(UserGroup userGroup, String user_usergroup) {
		int result=0;
		try {
			//根据用户组名称查询，判断用户组名称是否已经存在
			UserGroup ug=userGroupDao.getUserGroupByuserGroupName(userGroup.getUserGroupName());
			if(ug!=null){
				result=1;
			}else{
				//获得用户组的系列
				Long usergroup_id=userGroupDao.getUserGroupSEQ();
				userGroup.setId(usergroup_id);
				userGroup.setCreateDt(new Date());
				//保存用户组
				userGroupDao.addUserGroup(userGroup);
				//保存用户组下的用户对应关系
				if(!"".equals(user_usergroup)){
					if(user_usergroup.indexOf(",")!=-1){
						String [] userIds=user_usergroup.split(",");
						if(userIds.length>0){
							for(String userId:userIds){
								UserUserGroup uug=new UserUserGroup();
								uug.setCreateDt(new Date());
								uug.setUserId(Long.parseLong(userId));
								uug.setUserGroupId(usergroup_id);
								userUserGroupDao.addUserUserGroup(uug);
							}
						}
					}else{
						UserUserGroup uug=new UserUserGroup();
						uug.setCreateDt(new Date());
						uug.setUserId(Long.parseLong(user_usergroup));
						uug.setUserGroupId(usergroup_id);
						userUserGroupDao.addUserUserGroup(uug);
					}	
				}
				result=0;
			}
		} catch (Throwable e) {
			result=-1;
			LOGGER.error("添加用户组异常！",e);
			throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_COMMENT_ERR, "添加用户组异常！");
		}
		return result;
	}

	/**
	 * 修改用户组的状态
	 */
	@Override
	public int updateUserGroupStatus(Map<Object, Object> map) {
		int result=0;
		try {
			if(userGroupDao.updateUserGroupStatus(map)>0){
				result=0;
			}else{
				result=-1;
			}
		} catch (Throwable e) {
			result=-1;
			LOGGER.error("修改用户组的状态失败！",e);
			throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_COMMENT_ERR, "修改用户组的状态失败！");
		}
		return result;
	}

	/**
	 * 根据用户组ID查询用户组
	 */
	@Override
	public UserGroup getUserGroup(Long id) {
		try {
			return userGroupDao.getUserGroupById(id);
		} catch (Throwable e) {
			LOGGER.error("根据用户组ID："+id+" 查询用户失败！",e);
			throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_COMMENT_ERR, "根据用户组ID："+id+" 查询用户失败！");
		}
	}

	/**
	 * 修改用户组
	 */
	@Override
	public int update(UserGroup usergroup,
			String user_usergroup, String olduserGroupName) {
		int result=0;
		try {
			//根据用户组名称查询，判断用户组名称是否已经存在
			UserGroup ug=userGroupDao.getUserGroupByuserGroupName(usergroup.getUserGroupName());
			if(ug!=null&&!olduserGroupName.equals(ug.getUserGroupName())){
				result=1;
			}else{
				//修改用户组
				userGroupDao.updateUserGroup(usergroup);
				//删除用户--用户组的对应关系
				userUserGroupDao.deleteUserUserGroup(usergroup.getId());
				//修改用户组下的用户对应关系
				if(!"".equals(user_usergroup)){
					if(user_usergroup.indexOf(",")!=-1){
						String [] userIds=user_usergroup.split(",");
						if(userIds.length>0){
							for(String userId:userIds){
								UserUserGroup uug=new UserUserGroup();
								uug.setCreateDt(new Date());
								uug.setUserId(Long.parseLong(userId));
								uug.setUserGroupId(usergroup.getId());
								userUserGroupDao.addUserUserGroup(uug);
							}
						}
					}else{
						UserUserGroup uug=new UserUserGroup();
						uug.setCreateDt(new Date());
						uug.setUserId(Long.parseLong(user_usergroup));
						uug.setUserGroupId(usergroup.getId());
						userUserGroupDao.addUserUserGroup(uug);
					}	
				}
				result=0;
			}
		} catch (Throwable e) {
			result=-1;
			LOGGER.error("修改用户组异常！",e);
			throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_COMMENT_ERR, "修改用户组异常！");
		}
		return result;
	}

	
}
