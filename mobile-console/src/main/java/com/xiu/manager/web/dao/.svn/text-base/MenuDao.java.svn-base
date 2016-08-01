package com.xiu.manager.web.dao;

import java.util.List;
import java.util.Map;

import com.xiu.manager.web.model.Menu;


/**
 * 系统菜单 2013.05.31
 * 
 * @author haijun.chen@xiu.com
 */
public interface MenuDao {
	
	/**
	 * 根据菜单ID 查询 菜单
	 * @param id
	 * @return
	 */
	Menu getMenu(Long id);

	/**
	 * 获得一级菜单(状态:启用)
	 * @return
	 */
	List<Menu> getOneMenuList();
	
	/**
	 * 根据菜单ID查找一级菜单
	 * @param menuId
	 * @return
	 */
	Menu getOneMenuListById(Long menuId);

	/**
	 * 获得二级菜单（状态：启用）
	 * @param parentId
	 * @return
	 */
	List<Menu> getTwoMenuList(Long parentId);
	
	/**
	 * 查询菜单(状态:启用、停用),分页
	 * @return
	 */
	List<Menu> getMenuListAll(Map<Object,Object> map);
	/**
	 * 获得总记录条数
	 * @param map
	 * @return
	 */
	public String getMenuTotalCount(Map<Object,Object> map);
	
	/**
	 * 根据菜单名称查询
	 * @param menuName
	 * @return
	 */
	Menu getMenuBymenuName(String menuName);
	
	/**
	 *  保存菜单
	 * @param menu
	 */
	int addMenu(Menu menu);
	
	/**
	 * 修改菜单
	 * @param menu
	 */
	int updateMenu(Menu menu);
	
	/**
	 * 修改菜单状态(status: 1 启用 , 0 停用)
	 * @param map
	 * @return
	 */
	int updateStatus(Map<Object,Object> map);
}
