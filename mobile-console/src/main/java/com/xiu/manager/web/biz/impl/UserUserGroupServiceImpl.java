package com.xiu.manager.web.biz.impl;

import java.util.List;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiu.manager.web.biz.UserUserGroupService;
import com.xiu.manager.web.dao.UserUserGroupDao;
import com.xiu.manager.web.model.UserUserGroup;
import com.xiu.mobile.core.constants.ErrConstants;
import com.xiu.mobile.core.exception.ExceptionFactory;

/** 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 
 * @AUTHOR : haijun.chen@xiu.com 
 * @DATE :2013-06-07
 * </p>
 **************************************************************** 
 */
@Transactional
@Service("userUserGroupService")
public class UserUserGroupServiceImpl implements UserUserGroupService {
	
	//日志
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(UserUserGroupServiceImpl.class);

    @Autowired
    private UserUserGroupDao userUserGroupDao;
	/**
	 * 根据用户组Id查询
	 */
	@Override
	public List<UserUserGroup> getUserUserGroupbyuserId(Long userGroupId) {
		try {
			return userUserGroupDao.getUserUserGroupByusergroupId(userGroupId);
		} catch (Throwable e) {
			LOGGER.error("查询用户--用户组对应关系异常！",e);
			throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_COMMENT_ERR, "查询用户--用户组对应关系异常！");
		}
	}
}
