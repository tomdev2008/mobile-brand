package com.xiu.mobile.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xiu.mobile.core.model.AppPatchVO;

public interface AppPatchDao {
	
	public int queryIdBySeq();

	public int save(AppPatchVO AppPatchVO);

	public int update(AppPatchVO AppPatchVO);

	public List<AppPatchVO> getAppPatchVOList(Map<Object, Object> map);

	public int getAppPatchVOListCount(Map<Object, Object> map);
	
	public AppPatchVO queryAppPatchVOById(Long id);
	
	public void stopAlltypeAppPatch(@Param(value="type") String type);
	
	public AppPatchVO getAppPatchVOByConditionInfo(Map<Object, Object> map);
}
