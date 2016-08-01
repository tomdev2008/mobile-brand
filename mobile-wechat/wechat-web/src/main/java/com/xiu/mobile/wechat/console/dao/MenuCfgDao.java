package com.xiu.mobile.wechat.console.dao;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.wechat.console.model.MenuCfgVo;

/**
 * 微信菜单配置DAO接口
 * 
 * @author wangzhenjiang
 * 
 */
public interface MenuCfgDao {
	/**
	 * 保存菜单配置
	 * 
	 * @param cfg
	 * @return 1：success,0:failed
	 */
	int saveMenuCfg(MenuCfgVo cfg);

	/**
	 * 根据ID删除菜单配置
	 * 
	 * @param id
	 * @return 1：success,0:failed
	 */
	int deleteMenuCfg(Long id);

	/**
	 * 更新菜单配置
	 * 
	 * @param cfg
	 * @return 1：success,0:failed
	 */
	int updateMenuCfg(MenuCfgVo cfg);

	/**
	 * 根据ID 查询一条菜单配置记录
	 * 
	 * @param id
	 * @return MenuCfg
	 */
	MenuCfgVo getMenuCfgById(Long id);

	/**
	 * 根据参数查询菜单配置列表
	 * 
	 * @param params
	 *            Map<String, Object>
	 * @return List<MenuCfg>
	 */
	List<MenuCfgVo> getMenuCfgList(Map<String, Object> params);

	/**
	 * 根据参数查询菜单配置 分页列表
	 * 
	 * @param params
	 *            Map<String, Object>
	 * @return List<MenuCfg>
	 */
	List<MenuCfgVo> getMenuCfgListForPage(Map<String, Object> params);
}
