package com.xiu.mobile.core.service;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.WapH5List;
import com.xiu.mobile.core.model.WapH5Module;
import com.xiu.mobile.core.model.WapH5ModuleData;

public interface IWapH5ModuleService {
	
	/**
	 * 查询下载组件模板的excel头
	 * @param moduleId
	 * @return
	 */
	public Map<String, Object> queryModuleTemplateByModuleId(Long moduleId);
	
	List<WapH5Module>  getTargetModuleList(long pageId);
	
	/**
	 * 通过pageId和rowIndex获取module
	 * @param pageId 页面id
	 * @param index  组件序号
	 * @return
	 */
	WapH5Module getTargetModuleByRowIndex(long pageId, Integer zIndex);
	 
	/**
	 * 添加组件
	 * @param module 组件信息
	 * @param backward 其他组件是否需要移动
	 */
	void addModule(WapH5Module module, boolean backward);
	
	/**
	 * 互换组件在页面的位置
	 * @param pageId
	 * @param currentIndex
	 * @return
	 */
	int exChangeModuleRowIndex(Map<String, Object> paramMap);
	
	/**
	 * 根据组件ID查询导入的数据
	 * @param moduleId
	 * @return
	 */
	public List<WapH5ModuleData> queryModuleDataByModuleId(Long moduleId);

	/**
	 * 导入组件模板数据
	 * @param moduleId
	 * @param listMap
	 */
	public String importModuleData(Long moduleId, List<?> listMap);
	
	/**
	 * 删除主件
	 */
	public boolean deletecomponents(WapH5Module module);

	public Map<String, Object> assembleModuleVeiw(Long moduleId);
	
	public List<WapH5Module> getModuleDataCount(Long pageId);

	/**
	 * 根据组件ID查询所属页面信息
	 * @param moduleId
	 * @return
	 */
	public WapH5List queryPageInfoByModuleId(Long moduleId);
	
	WapH5Module queryModuleInfoById(Long moduleId);
	
	/**
	 * 更新组件是否懒加载
	 * @param moduleId
	 * @param isLazyLoad
	 */
	void updateModuleLazyLoad(Long moduleId, String isLazyLoad);
	
}
