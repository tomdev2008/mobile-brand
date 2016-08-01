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

import com.xiu.manager.web.biz.RoleService;
import com.xiu.manager.web.dao.RoleDao;
import com.xiu.manager.web.dao.RoleMenuDao;
import com.xiu.manager.web.model.Role;
import com.xiu.manager.web.model.RoleMenu;
import com.xiu.mobile.core.constants.ErrConstants;
import com.xiu.mobile.core.exception.ExceptionFactory;
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
@Transactional
@Service("roleService")
public class RoleServiceImpl implements RoleService {
	
	//日志
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(RoleServiceImpl.class);

	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private RoleMenuDao roleMenuDao;

	/**
	 * 查询角色 (状态:启用、停用)
	 */
	@Override
	public List<Role> getRoleListAll(Map<Object,Object> map,Page<?> page) {
		List<Role> rolelist=new ArrayList<Role>();
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
			 rolelist=roleDao.getRoleListAll(map);
			  // 获取总记录数
		     Long totalCount = Long.parseLong(roleDao.getRoleTotalCount(map)); 
		     page.setTotalCount(Integer.valueOf(String.valueOf(totalCount)));
		} catch (Throwable e) {
			LOGGER.error("查询角色信息异常！",e);
			throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_COMMENT_ERR, "查询角色信息异常！");
		}
		return rolelist;
	}

	/**
	 * 修改角色的状态
	 */
	@Override
	public int updateRoleStatus(Map<Object, Object> map) {
		int result=0;
		try {
			if(roleDao.updateRoleStatus(map)>0){
				result=0;
			}else{
				result=-1;
			}
		} catch (Throwable e) {
			result=-1;
			LOGGER.error("修改角色状态失败！",e);
			throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_COMMENT_ERR, "修改角色状态失败！");
		}
		return result;
	}

	/**
	 * 添加--保存角色
	 */
	@Override
	public int save(Role role, String role_menu) {
		int result=0;
		try {
			//根据角色名称查询，判断角色名称是否已经存在
			Role r=roleDao.getRoleByroleName(role.getRoleName());
			if(r!=null){
				result=1;
			}else{
			    //获得角色的系列
                long role_id=roleDao.getRoleSEQ();
                role.setId(role_id);
				//保存角色
				roleDao.addRole(role);
				//保存角色对应菜单的权限
				if(!"".equals(role_menu)){
					if(role_menu.indexOf(",")!=-1){
						String [] menuIds=role_menu.split(",");
						if(menuIds.length>0){
							for(String menuId:menuIds){
								RoleMenu rm=new RoleMenu();
								rm.setMenuId(Long.parseLong(menuId));
								rm.setRoleId(role.getId());
								rm.setCreateDt(new Date());
								roleMenuDao.addRoleMenu(rm);
							}
						}
					}else{
						RoleMenu rm=new RoleMenu();
						rm.setMenuId(Long.parseLong(role_menu));
						rm.setRoleId(role.getId());
						rm.setCreateDt(new Date());
						roleMenuDao.addRoleMenu(rm);
					}
				}
				result=0;
			}
		} catch (Throwable e) {
			result=-1;
			LOGGER.error("添加角色失败！",e);
			throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_COMMENT_ERR, "添加角色失败！");
		}
		return result;
	}

	/**
	 * 根据角色Id查询
	 */
	@Override
	public Role getRolebyId(Long id) {
		try {
			return roleDao.getRoleById(id);
		} catch (Throwable e) {
			LOGGER.error("根据角色ID："+id+" 查询角色异常！",e);
			throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_COMMENT_ERR, "根据角色ID："+id+" 查询角色异常！");
		}
	}

	/**
	 * 角色--修改
	 */
	@Override
	public int update(Role role, String role_menu,
			String oldroleName) {
		int result=0;
		try {
			//根据角色名称查询，判断角色名称是否已经存在
			Role r=roleDao.getRoleByroleName(role.getRoleName());
			if(r!=null&&!oldroleName.equals(r.getRoleName())){
				result=1;
			}else{
				//修改角色
				roleDao.updateRole(role);
				//删除角色--菜单对应的权限
				roleMenuDao.deleteRoleMenu(role.getId());
				//修改角色对应菜单的权限
				if(!"".equals(role_menu)){
					if(role_menu.indexOf(",")!=-1){
						String [] menuIds=role_menu.split(",");
						if(menuIds.length>0){
							for(String menuId:menuIds){
								RoleMenu rm=new RoleMenu();
								rm.setMenuId(Long.parseLong(menuId));
								rm.setRoleId(role.getId());
								rm.setCreateDt(new Date());
								roleMenuDao.addRoleMenu(rm);
							}
						}
					}else{
						RoleMenu rm=new RoleMenu();
						rm.setMenuId(Long.parseLong(role_menu));
						rm.setRoleId(role.getId());
						rm.setCreateDt(new Date());
						roleMenuDao.addRoleMenu(rm);
					}
				}
				result=0;
			}
		} catch (Throwable e) {
			result=-1;
			LOGGER.error("修改角色失败！",e);
			throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_COMMENT_ERR, "修改角色失败！");
		}
		return result;
	}

	/**
	 * 查询所有
	 */
	@Override
	public List<Role> getRoleList() {
		try {
			return roleDao.getRoleList();
		} catch (Throwable e) {
			LOGGER.error("查询角色信息异常！",e);
			throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_COMMENT_ERR, "查询角色信息异常！");
		}
	}
}
