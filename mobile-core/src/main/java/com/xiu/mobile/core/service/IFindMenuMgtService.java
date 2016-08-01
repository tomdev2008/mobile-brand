package com.xiu.mobile.core.service;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.FindMenuMgt;
import com.xiu.mobile.core.model.Page;

/**
 * 发现频道栏目Service
 * @author coco.long
 * @time	2015-1-16
 */
public interface IFindMenuMgtService {

	//查询栏目列表
	public List<FindMenuMgt> getFindMenuMgtList(Map<String,Object> map,Page<?> page);
	
	//查询栏目信息
	public FindMenuMgt getFindMenuMgt(Map<String,Object> map);
	
	//新增栏目
	public int insert(FindMenuMgt findMenuMgt,String appSystem[],String appSource[],String startVersion[],String lastVersion[]);
	
	//修改栏目
	public int update(FindMenuMgt findMenuMgt,String appSystem[],String appSource[],String startVersion[],String lastVersion[]);
	
	//删除栏目
	public int delete(Map<String,Object> map);
	
	//更新栏目状态
	public int updateStatus(Map<String,Object> map);
	
	//更新栏目版本号
	public int updateVersion(Map<String,Object> map);
}
