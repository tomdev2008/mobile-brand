package com.xiu.mobile.core.service;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.TopicType;
import com.xiu.mobile.core.model.Page;

/**
 * @description:卖场分类Service
 * @AUTHOR :coco.long
 * @DATE :2015-03-03
 */
public interface ITopicTypeService {

	//查询分类列表
	public List<TopicType> getTopicTypeList(Map map,Page<?> page);
	
	//查询所有分类
	public List<TopicType> getAllTopicTypeList(Map map);
	
	//查询分类及卖场信息
	public List<TopicType> getTopicTypeAndActivityList(Map map);
	
	//查询分类信息
	public TopicType getTopicType(Map map);
	
	//新增分类
	public int insert(TopicType TopicType);
	
	//修改分类
	public int update(TopicType TopicType);
	
	//删除分类
	public int delete(Map map);
	
	//更新分类状态
	public int updateStatus(Map map);
	
	//查询分类下是否有进行中的卖场
	public int queryActivityByTopicType(Map map);
	
	/**
	 * 根据名称查询卖场类型ID
	 * @param map
	 * @return
	 */
	public List<Long> getTopicTypeIdByNames(Map map);
	/**
	 * 定时更新卖场分类状态
	 * @param map
	 */
	public void callSyncUpdateTopicTypeStatusTask();


}
