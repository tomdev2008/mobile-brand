package com.xiu.mobile.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xiu.mobile.core.model.WapH5List;
import com.xiu.mobile.core.model.WapH5Module;
import com.xiu.mobile.core.model.WapH5ModuleData;

public interface WapH5ModuleDao {

	List<WapH5Module> getTargetModuleList(long pageId);
	
	WapH5Module getTargetModuleByRowIndex(Map<String, Object> paramMap);

	void addModule(WapH5Module module);

	int moveRowIndex(Map<String, Object> paramMap);

	List<WapH5ModuleData> queryModuleDataByModuleId(Long moduleId);

	WapH5Module queryModuleInfoById(Long id);
	
	public int deleteById(Long id);
	
	public int deleteBypageId(Long id);
	
	public List<Long> getModuleListIds(@Param(value="pageIds")List<Long> pageIds);
	
	public int deleteModuleDataList(@Param(value="moduleIds")List<Long> moduleIds);
	
	public int deleteModuleList(@Param(value="pageIds")List<Long> pageIds);

	public int deleteModuleData(Long moduleId);

	void saveModuleData(WapH5ModuleData wapH5ModuleData);
	
	int updateRowIndex(WapH5Module module);
	
	public List<WapH5Module> getModuleDataCount(Long pageId);

	public List<String> queryStoreGoodsByStoreId(Long activityId);
	
	public WapH5List queryPageInfoByModuleId(Long moduleId);

	WapH5ModuleData queryModuleDataById(Long moduleDataId);

	void updateModuleLazyLoad(Map<String, Object> paramMap);

}
