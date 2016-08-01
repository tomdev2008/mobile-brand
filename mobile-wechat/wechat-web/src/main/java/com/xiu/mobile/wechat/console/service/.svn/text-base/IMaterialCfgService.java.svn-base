package com.xiu.mobile.wechat.console.service;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.wechat.console.model.MaterialCfgVo;
import com.xiu.mobile.wechat.core.constants.Page;

/**
 * 
 * 素材配置 服务接口
 * 
 * @author wangzhenjiang
 * 
 */
public interface IMaterialCfgService {

	/**
	 * 根据参数查询素材配置分页列表
	 * 
	 * @param map
	 * @param page
	 * @return List<MaterialCfg>
	 */
	List<MaterialCfgVo> getMaterialCfgList(Map<String, Object> map, Page<?> page);

	/**
	 * 根据参数查询素材配置列表
	 * 
	 * @param map
	 * @return List<MaterialCfg>
	 */
	List<MaterialCfgVo> getMaterialCfgList(Map<String, Object> params);

	/**
	 * 保存素材配置
	 * 
	 * @param cfg
	 * @return 0:success 1:failed -1:error
	 */
	int saveMaterialCfg(MaterialCfgVo cfg);

	/**
	 * 根据ID删除素材配置
	 * 
	 * @param id
	 * @return 0:success 1:failed -1:error
	 */
	int deleteMaterialCfg(Long id);

	/**
	 * 更新素材配置
	 * 
	 * @param cfg
	 * @return 0:success 1:failed -1:error
	 */
	int updateMaterialCfg(MaterialCfgVo cfg);

	/**
	 * 根据ID查询素材配置
	 * 
	 * @param id
	 * @return
	 */
	MaterialCfgVo getMaterialCfg(Long id);

	/**
	 * 根据ids批量删除素材配置
	 * 
	 * @param ids
	 * @return iCount
	 */
	int deleteMaterialCfgList(String[] ids);

}
