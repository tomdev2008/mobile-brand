package com.xiu.manager.web.biz.impl;

import java.util.List;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiu.manager.web.biz.RoleMenuService;
import com.xiu.manager.web.dao.RoleMenuDao;
import com.xiu.manager.web.model.RoleMenu;
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
@Service("roleMenuService")
public class RoleMenuServiceImpl implements RoleMenuService {
	
	//日志
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(RoleMenuServiceImpl.class);

	@Autowired
	private RoleMenuDao roleMenuDao;
	/**
	 * 根据角色Id查询
	 */
	@Override
	public List<RoleMenu> getRoleMenubyroleId(Long roleId) {
		try {
			return roleMenuDao.getRoleMenubyroleId(roleId);
		} catch (Throwable e) {
			LOGGER.error("查询角色菜单对应权限异常！",e);
			throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_COMMENT_ERR, "查询角色菜单对应权限异常！");
		}
	}
}
