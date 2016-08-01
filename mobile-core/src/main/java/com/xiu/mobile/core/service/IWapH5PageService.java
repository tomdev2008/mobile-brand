package com.xiu.mobile.core.service;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.WapH5List;
import com.xiu.mobile.core.model.WapH5ModuleData;


public interface IWapH5PageService {
	
	public WapH5List queryWapH5ListById(Long id);

	public Long saveOrUpadteWapH5List(WapH5List wapH5List);
	
	/**
	 * 查询下载组件模板的excel头
	 * @param moduleId
	 * @return
	 */
	public Map<String, Object> queryModuleTemplateByPageId(Long pageId);
	
	/**
	 * 根据页面ID查询该页面所有组件导入的数据
	 * @param pageId
	 * @return
	 */
	public List<WapH5ModuleData> queryAllModuleDataByPageId(Long pageId);

	/**
	 * 导入该页面所有组件数据
	 * @param pageId
	 * @param listMap
	 * @return
	 */
	public String importAllModuleData(Long pageId,
			List<Map<Object, Object>> dataList);
	
	/**
	 * 组装页面预览数据
	 * @param pageId
	 * @return
	 */
	public Map<String, Object> assemblePageVeiw(Long pageId);
	
}
