package com.xiu.mobile.core.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.Topic;


/**
 * @author: 贾泽伟
 * @description:卖场维护
 * @date: 2014-03-18
 */
public interface TopicDao {
	
	public List<Topic> getTopicList(Map<Object, Object> map);
	
	public String getTopicTotalCount(Map<Object, Object> map);
	
	public Topic getTopicByActivityId(BigDecimal activityId);
	
	public List<Map<String,Object>> getGoodsCountByActIds(Map<String,Object> ids);
	
	public int update(Topic topic);
	
	public int insert(Topic topic);
	
	public void callSpSynTopicDataTask();

	public void callSpUpdateBrandVsTopicTask();
	
	//查询卖场集下的二级卖场
	public List<Topic> queryActivityBySet(Map map);
	
	//查询卖场集下的二级卖场数量
	public int getActivityCountsBySet(Map map);
	
	//查询二级卖场所属卖场集
	public List<Topic> querySetByActivity(Map map);

	//查询二级卖场所属卖场集数量
	public int getSetCountsByActivity(Map map);
	
	//查询卖场集下的二级卖场ID
	public String queryActivityIdBySet(Integer set_id);
	
	//批量查询卖场
	public List<Topic> queryActivityList(Map map);
	
	//查询卖场
	public Topic queryActivity(Map map);
	
	//查看卖场是否在卖场集下存在
	public int hasExistsActivitySet(Map map);
	
	//移入卖场集
	public int addActivitySet(Map map);
	
	//批量移入卖场集
	public int addActivitySetBatch(Map map);
	
	//移出卖场集
	public int deleteActivitySet(Map map);
	
	//批量移出卖场集
	public int deleteActivitySetBatch(Map map);
	
	//清空卖场集
	public int emptyActivitySet(Map map);
	
	//获取卖场活动Id序列
	public Long getCMSActivitySeq();
	
	//增加卖场所属分类
	public int insertTopicTypeRela(Map map);
	
	//删除卖场所属分类
	public int deleteTopicTypeRela(Map map);
	
	//查询要预览的卖场列表
	public List<Topic> getPreviewTopicList(Map map);
	
	//查询要预览的卖场列表数量
	public int getPreviewTopicCount(Map map);
	
	/**
	 * 获取存在的卖场活动id
	 * @param map
	 * @return
	 */
	public List<String> findExistTopicActivityId(Map map);
	
	
	//查询卖场下的商品ID列表
	List<String> getGoodsMgtListByTopicId(String activityId);
	
	
	//查询商品走秀码列表
	List<String> getGoodsSnMgtListByTopicAndGoodsIds(Map<String, Object> map);
	
	/**
	 * 分页获取进行中的卖场个数
	 * @param map
	 * @return
	 */
	public Integer getIngTopicCount(Map map);
	/**
	 * 分页获取进行中的卖场id
	 * @param map
	 * @return
	 */
	public List<Long> getIngTopicIdByPage(Map map);
	
	/**
	 * 获取同步到搜索的个数
	 * @param params
	 * @return
	 */
	public Integer getSyncToSearchCount(Map params);
	
	/**
	 * 获取同步到搜索的
	 * @param params
	 * @return
	 */
	public List<Topic> getSyncToSearchList(Map params);
}
