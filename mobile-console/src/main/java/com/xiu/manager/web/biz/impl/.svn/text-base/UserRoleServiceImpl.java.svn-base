package com.xiu.manager.web.biz.impl;
import java.util.List;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiu.manager.web.biz.UserRoleService;
import com.xiu.manager.web.dao.UserRoleDao;
import com.xiu.manager.web.model.UserRole;
import com.xiu.mobile.core.constants.ErrConstants;
import com.xiu.mobile.core.exception.ExceptionFactory;

/** 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 
 * @AUTHOR : haijun.chen@xiu.com 
 * @DATE :2013-06-05
 * </p>
 **************************************************************** 
 */
@Transactional
@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {
	
	//日志
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(UserRoleServiceImpl.class);

    @Autowired
    private UserRoleDao userRoleDao;
	/**
	 * 根据用户Id查询
	 */
	@Override
	public List<UserRole> getUserRolebyuserId(Long userId) {
		try {
			return userRoleDao.getUserRolebyuserId(userId);
		} catch (Throwable e) {
			LOGGER.error("查询用户角色对应关系异常！",e);
			throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_COMMENT_ERR, "查询用户角色对应关系异常！");
		}
	}
}
