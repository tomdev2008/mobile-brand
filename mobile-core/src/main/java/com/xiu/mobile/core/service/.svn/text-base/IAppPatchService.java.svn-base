package com.xiu.mobile.core.service;

import java.util.Map;

import com.xiu.mobile.core.model.AppPatchVO;
import com.xiu.mobile.core.model.Page;

public interface IAppPatchService {
	
	public int queryIdBySeq();

	public int save(AppPatchVO vo);

	public int update(AppPatchVO vo);

	public Map<String,Object> getAppPatchVOListByCondition(Map<Object, Object> map,Page<?> page);

	
	public AppPatchVO queryAppPatchVOById(Long id);
	
	/**
	 * 判断指定类型是否存在启用的版本
	 * @return
	 */
	public boolean checkStatusAppPatchVO(Long id);
	
}
