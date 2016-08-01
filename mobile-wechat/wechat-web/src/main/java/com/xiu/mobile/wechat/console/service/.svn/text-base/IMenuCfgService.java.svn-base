package com.xiu.mobile.wechat.console.service;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.wechat.console.model.MenuCfgVo;
import com.xiu.mobile.wechat.core.constants.Page;

/**
 * 
 * 菜单配置 服务接口
 * 
 * @author wangzhenjiang
 * 
 */
public interface IMenuCfgService {

	/**
	 * 根据参数查询菜单配置分页列表
	 * 
	 * @param map
	 * @param page
	 * @return List<MenuCfg>
	 */
	List<MenuCfgVo> getMenuCfgList(Map<String, Object> params, Page<?> page);

	/**
	 * 根据参数查询菜单配置列表
	 * 
	 * @param map
	 * @return List<MenuCfg>
	 */
	List<MenuCfgVo> getMenuCfgList(Map<String, Object> params);

	/**
	 * 保存菜单配置
	 * 
	 * @param cfg
	 * @return 0:success 1:failed -1:error
	 */
	int saveMenuCfg(MenuCfgVo cfg);

	/**
	 * 根据ID删除菜单配置
	 * 
	 * @param id
	 * @return 0:success 1:failed -1:error
	 */
	int deleteMenuCfg(Long id);

	/**
	 * 更新菜单配置
	 * 
	 * @param cfg
	 * @return 0:success 1:failed -1:error
	 */
	int updateMenuCfg(MenuCfgVo cfg);

	/**
	 * 根据ID查询菜单配置
	 * 
	 * @param id
	 * @return MenuCfg
	 */
	MenuCfgVo getMenuCfg(Long id);

	/**
	 * 根据ids 批量删除记录
	 * 
	 * @param ids
	 * @return 删除成功的条数 iCount
	 */
	int deleteMenuCfgList(String[] ids);

}
