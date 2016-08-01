package com.xiu.mobile.core.dao;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.TopicType;

/**
 * @description 卖场分类Dao
 * @author coco.long
 * @time	2015-03-03
 */
public interface TopicTypeDao {
	
	//查询分类列表
	public List<TopicType> getTopicTypeList(Map map);
	
	//查询所有分类
	public List<TopicType> getAllTopicTypeList(Map map);
	
	//查询分类及卖场信息
	public List<TopicType> getTopicTypeAndActivityList(Map map);
	
	//查询分类数量
	public int getTopicTypeCount(Map map);
	
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
	 * 根据名称集合查询对应的id集合
	 * @param map
	 * @return
	 */
	public List<Long> getTopicTypeIdByNames(Map map);
	
	/**
	 * 根据时间批量更新分类状态
	 */
	public int updateBatchTopicTypeStatusByTime(Map map);
}
