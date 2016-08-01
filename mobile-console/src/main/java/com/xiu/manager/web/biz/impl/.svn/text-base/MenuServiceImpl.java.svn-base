package com.xiu.manager.web.biz.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.xiu.manager.web.biz.MenuService;
import com.xiu.manager.web.dao.MenuDao;
import com.xiu.manager.web.dao.RoleMenuDao;
import com.xiu.manager.web.dao.UserRoleDao;
import com.xiu.manager.web.model.Menu;
import com.xiu.manager.web.model.RoleMenu;
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
 * @DATE :2013-5-31 
 * </p>
 **************************************************************** 
 */
@Transactional
@Service("menuService")
public class MenuServiceImpl implements MenuService {
	
	//日志
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(MenuServiceImpl.class);

	@Autowired
	private MenuDao menuDao;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
	@Autowired
	private RoleMenuDao roleMenuDao;
	
	/**
	 * 根据菜单ID,查询菜单(状态:启用)
	 * @param id
	 * @return
	 */
	@Override
	public Menu getMenu(Long id) {
		try {
			return menuDao.getMenu(id);
		} catch (Throwable e) {
			LOGGER.error("根据菜单ID："+id+" 查询菜单异常！",e);
			throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_COMMENT_ERR, "根据菜单ID："+id+" 查询菜单异常！");
		}
	}

	/**
	 * 加载可用的菜单 (状态:启用)
	 */
	@Override
	public List<Menu> getMenuList() {
		List<Menu> menulist = new ArrayList<Menu>();
		try {
			// 查询所有符合条件的一级菜单目录
			List<Menu> mlist = menuDao.getOneMenuList();
			if (mlist != null && mlist.size() > 0) {
				for (Menu m : mlist) {
					// 根据一级菜单的ID，查找该菜单目录下面的子菜单
					List<Menu> mtlist = menuDao.getTwoMenuList(m.getId());
					if (mtlist != null && mtlist.size() > 0) {
						m.setMlist(mtlist);
					}
					menulist.add(m);
				}
			}
		} catch (Throwable e) {
			LOGGER.error("加载启用状态菜单失败！",e);
			throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_COMMENT_ERR, "加载启用状态菜单失败！！");
		}
		return menulist;
	}

//	/**
//	 * 查询菜单 (状态:启用、停用)
//	 */
//	@Override
//	public List<Menu> getMenuListAll(Map<Object,Object> map,Page<?> page) {
//		List<Menu> mlist=new ArrayList<Menu>();
//		try {
//			 int pageMax=page.getPageNo()*page.getPageSize();
//			 int pageMin=1;
//			 if(page.getPageNo()!=1){
//				 pageMin=(pageMax-(page.getPageNo()-1)*page.getPageSize())+1;
//			 }
//			 map.put("pageMin", pageMin-1);
//			 map.put("pageSize", page.getPageSize());
//			 map.put("pageMax", pageMax);
//			//获得分页列表
//			 List<Menu> menulist=menuDao.getMenuListAll(map);
//			 // 获取总记录数
//			 Long totalCount = Long.parseLong(menuDao.getMenuTotalCount(map)); 
//		     page.setTotalCount(Integer.valueOf(String.valueOf(totalCount)));
//			
//			 if(menulist!=null&&menulist.size()>0){
//				for(Menu menu:menulist){
//					if(menu.getParentId()!=null){
//						Menu m=menuDao.getMenu(menu.getParentId());
//						if(m!=null){
//							menu.setParentName(m.getMenuName());
//						}
//					}
//					mlist.add(menu);
//				}
//			}
//		} catch (Throwable e) {
//			LOGGER.error("查询菜单失败！",e);
//			throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_COMMENT_ERR, "查询菜单失败！！");
//		}
//		return mlist;
//	}

	/**
	 * 查询一级菜单(状态:启用)
	 */
	@Override
	public List<Menu> getOneMenuList() {
		try {
			return menuDao.getOneMenuList();
		} catch (Throwable e) {
			LOGGER.error("查询一级菜单异常！",e);
			throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_COMMENT_ERR, "查询一级菜单异常！！");
		}
	}

	/**
	 * 保存菜单
	 */
	@Override
	public int save(Menu menu) {
		int result=0;
		try {
			//根据菜单名称查询，判断菜单名称是否已经存在
			Menu m=menuDao.getMenuBymenuName(menu.getMenuName());
			if(m!=null){
				result=1;
			}else{
				if(menuDao.addMenu(menu)>0){
					result=0;
				}else{
					result=-1;
				}
			}
		} catch (Throwable e) {
			result=-1;
			LOGGER.error("保存菜单异常！",e);
			throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_COMMENT_ERR, "保存菜单异常！");
		}
		return result;
	}

	 /**
     * 修改菜单状态(status: 1 启用 , 0 停用)
     * @param map
     */
	@Override
	public int updateStatus(Map<Object, Object> map) {
		int result=0;
		try {
			if(menuDao.updateStatus(map)>0){
				result=0;
			}else{
				result=-1;
			}
		} catch (Throwable e) {
			result=-1;
			LOGGER.error("修改菜单状态失败！",e);
			throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_COMMENT_ERR, "修改菜单状态失败！");
		}
		return result;
	}

	/**
	 * 修改菜单
	 */
	@Override
	public int update(Menu menu,String oldmenuName) {
		int result=0;
		try {
			//根据菜单名称查询，判断菜单名称是否已经存在
			Menu m=menuDao.getMenuBymenuName(menu.getMenuName());
			if(m!=null&&!oldmenuName.equals(m.getMenuName())){
				result=1;
			}else{
				if(menuDao.updateMenu(menu)>0){
					result=0;
				}else{
					result=-1;
				}
			}
		} catch (Throwable e) {
			result=-1;
			LOGGER.error("修改菜单异常！",e);
			throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_COMMENT_ERR, "修改菜单异常！");
		}
		return result;
	}

	/**
	 * 加载符合当前登录用户有权限的菜单 (状态:启用)
	 */
	@Override
	public List<Menu> getMenuListByUser(User user) {
		Assert.notNull(user);
		List<Menu> menulist = new ArrayList<Menu>();
		List<Map<Object,Object>> maplist=new ArrayList<Map<Object,Object>>();
		try {
			//根据用户Id,查找当前用户对应的角色
			List<UserRole> urlist=userRoleDao.getUserRolebyuserId(user.getId());
			if(urlist!=null&&urlist.size()>0){
				for(UserRole ur:urlist){
					//根据用户角色,查找用户对应的 权限菜单
					List<RoleMenu> rmlist=roleMenuDao.getRoleMenubyroleId(ur.getRoleId());
					if(rmlist!=null&&rmlist.size()>0){
						for(RoleMenu rm:rmlist){
							//过滤重复菜单数据
							Map<Object,Object> map=new HashMap<Object, Object>();
							map.put("menuId", rm.getMenuId());
							if(!maplist.contains(map)){
								maplist.add(map);
							}
						}
					}
				}
			}
			//循环菜单
			if(maplist!=null&&maplist.size()>0){
				for(Map<Object,Object> m:maplist){
					//根据菜单的ID,查找一级菜单
					Menu mu= menuDao.getOneMenuListById(Long.parseLong(m.get("menuId").toString()));
					if(mu!=null){
						// 根据一级菜单的ID，查找该菜单目录下面的子菜单
						List<Menu> mlist=new ArrayList<Menu>();
						List<Menu> mtlist = menuDao.getTwoMenuList(mu.getId());
						if (mtlist != null && mtlist.size() > 0) {
							for(Menu menu:mtlist){
								Map<Object,Object> mp=new HashMap<Object, Object>();
								mp.put("menuId", menu.getId());
								if(maplist.contains(mp)){
									mlist.add(menu);
								}
							}
							mu.setMlist(mlist);
						}
						menulist.add(mu);
					}
				}
			}
		} catch (Throwable e) {
		    LOGGER.error("加载菜单异常！",e);
			throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_COMMENT_ERR, "加载菜单异常！！");
		}
		return menulist;
	}
}
