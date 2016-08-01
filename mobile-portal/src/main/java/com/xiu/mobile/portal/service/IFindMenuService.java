package com.xiu.mobile.portal.service;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.FindMenuMgt;
import com.xiu.mobile.core.model.MobileCommonData;

public interface IFindMenuService {

	//查询发现频道栏目列表
	public List<FindMenuMgt> getFindMenuList(Map map);
	
	//查询发现频道栏目数量
	public int getFindMenuCount(Map map);
	
	//根据栏目ID查询栏目信息
	public FindMenuMgt getFindMenuById(Map map);
	
	//获取发现频道版本号
	public MobileCommonData getFindChannelVersion(Map map);
	
	//按block块查询
	public List<FindMenuMgt> getFindMenuListByBlock( Long block);
}
