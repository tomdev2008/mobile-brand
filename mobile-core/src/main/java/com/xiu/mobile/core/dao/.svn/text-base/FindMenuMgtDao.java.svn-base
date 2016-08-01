package com.xiu.mobile.core.dao;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.FindMenuMgt;

/**
 * 发现频道栏目管理Dao
 * @author coco.long
 * @time	2015-1-16
 */
public interface FindMenuMgtDao {

	//查询栏目列表
	public List<FindMenuMgt> getFindMenuList(Map map);
	
	//查询栏目数量
	public int getFindMenuCount(Map map);
	
	//查询栏目信息
	public FindMenuMgt getFindMenu(Map map);
	
	//新增栏目
	public int insert(FindMenuMgt findMenuMgt);
	//获取栏目ID
	public Long getFindMenuId();
	
	//修改栏目
	public int update(FindMenuMgt findMenuMgt);
	
	//删除栏目
	public int delete(Map map);
	
	//更新栏目状态
	public int updateStatus(Map map);
	
	//更新栏目版本号
	public int updateVersion(Map map);
}
