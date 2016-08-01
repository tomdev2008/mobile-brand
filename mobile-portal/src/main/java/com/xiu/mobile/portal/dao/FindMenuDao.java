package com.xiu.mobile.portal.dao;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.FindMenuMgt;
import com.xiu.mobile.core.model.FindSupportVersion;
import com.xiu.mobile.core.model.MobileCommonData;

public interface FindMenuDao {

	//查询发现频道栏目列表
	public List<FindMenuMgt> getFindMenuList(Map map);
	
	//查询发现频道栏目数量
	public int getFindMenuCount(Map map);
	
	//查询栏目信息
	public FindMenuMgt getFindMenuById(Map map);
	
	//获取发现频道版本号
	public MobileCommonData getFindChannelVersion(Map map);
	
	/**
	 * 获取首页引导栏数据
	 * @param map
	 * @return
	 */
	public List<FindMenuMgt> getFindMenuIndexList(Map map);
	/**
	 * 获取首页引导栏数据
	 * @param map
	 * @return
	 */
	public List<FindMenuMgt> getFindMenuListByBlock(Long block);
	/**
	 * 根据ID查询所以指定版本
	 */
	public List<FindSupportVersion> getSupportVersionList(Map<String,Object> findMenuId);
}
