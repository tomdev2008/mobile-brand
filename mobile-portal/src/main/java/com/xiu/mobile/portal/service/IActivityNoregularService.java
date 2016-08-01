package com.xiu.mobile.portal.service;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.TopicType;
import com.xiu.mobile.portal.model.ActivityVo;
import com.xiu.mobile.portal.model.Topic;


/**
 *************************************************************** 
 * <p>
 * 
 * @DESCRIPTION :
 * @AUTHOR : Jack.jia@xiu.com
 * @DATE :2014-03-24 上午10:24:35
 *       </p>
 **************************************************************** 
 */
public interface IActivityNoregularService {
	public List<ActivityVo> queryActiveList(Map<String, Object> valMap);

	public List<ActivityVo> queryOutletsList(String saleType);

	public List<Topic> queryTopicList(Map<String, Object> valMap);

	public Topic getTopicByActId(String activityId);
	
	public int getTopicTotalAmount(Map<String, Object> valMap);
	
	
	/***
     * 查询顶层卖场列表数据
     * @param valMap
     * @return
     */
    List<Topic> getTopicList(Map<String, Object> valMap);
	
    /***
     * 获取顶层卖场数据总数
     * @param valMap
     * @return
     */
	int getTopicTotal(Map<String, Object> valMap);
	
	/**
	 * 查询顶层卖场列表数据 卖场分类新接口
	 * @param valMap
	 * @return
	 */
	List<Topic> getTopicListNew(Map<String, Object> valMap);
	
	/**
	 * 获取顶层卖场数据总数 卖场分类新接口
	 * @param valMap
	 * @return
	 */
	int getTopicTotalNew(Map<String, Object> valMap);
	
	/***
     * 查询卖场集下的卖场列表
     * @param valMap
     * @return
     */
    List<Topic> getTopicListBySetId(Map<String, Object> valMap);
	
    /***
     * 获取卖场集下的卖场总数
     * @param valMap
     * @return
     */
	int getTopicCountBySetId(Map<String, Object> valMap);
	
	/**
	 * 查询卖场分类信息
	 * @param map
	 * @return
	 */
	List<TopicType> getTopicTypeList(Map map);
}
