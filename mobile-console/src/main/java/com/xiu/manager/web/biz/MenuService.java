package com.xiu.manager.web.biz;

import java.util.List;
import java.util.Map;

import com.xiu.manager.web.model.Menu;
import com.xiu.manager.web.model.User;
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
public interface MenuService {
	/**
	 * 根据菜单ID,查询菜单(状态:启用)
	 * @param id
	 * @return
	 */
    public Menu getMenu(Long id);
    
    /**
     *加载可用的菜单 (状态:启用)
     */
    public List<Menu> getMenuList();
    
    /**
     *加载符合当前登录用户有权限的菜单 (状态:启用)
     */
    public List<Menu> getMenuListByUser(User user);
    
//    /**
//     * 查询菜单 (查询菜单 (状态:启用、停用),带分页
//     * @param map
//     * @return
//     */
//    public List<Menu> getMenuListAll(Map<Object,Object> map,Page<?> page);
//    
    /**
     * 查询一级菜单(状态:启用)
     * @return
     */
    public List<Menu> getOneMenuList();
    
    /**
     * 保存菜单
     * @param menu
     * @return
     */
    public int save(Menu menu);
    
   /**
    * 修改菜单
    * @param menu
    * @param oldmenuName
    * @return
    */
    public int update(Menu menu,String oldmenuName);
    
    /**
     * 修改菜单状态(status: 1 启用 , 0 停用)
     * @param map
     */
    public int updateStatus(Map<Object,Object> map);
    
}
