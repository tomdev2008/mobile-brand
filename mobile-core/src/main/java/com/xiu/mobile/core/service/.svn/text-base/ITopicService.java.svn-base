package com.xiu.mobile.core.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.model.Topic;

/**
 * @author: 贾泽伟
 * @description:广告管理
 * @date: 2014-03-20
 */

public interface ITopicService {
	
	public List<Topic> getTopicList(Map<Object, Object> map,Page<?> page);
	
	public Topic getTopicByActivityId(BigDecimal activityId);
	
	public List<Map<String,Object>> getGoodsCountByActIds(List<BigDecimal> ids);
	
	public int update(Topic topic) ;
	
	public int insert(Topic topic) ;
	
	public void callSpSynTopicDataTask();
	
	public void callSpUpdateBrandVsTopicTask();
	
	//查询卖场集下的二级卖场
	public List<Topic> queryActivityBySet(Map map, Page<?> page);
	
	//查询二级卖场所属的卖场集
	public List<Topic> querySetByActivity(Map map, Page<?> page);
	
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
	
	//移出卖场集
	public int deleteActivitySet(Map map);
	
	//清空卖场集
	public int emptyActivitySet(Map map);
	
	//获取卖场活动Id序列
	public Long getCMSActivitySeq();
	
	//增加卖场所属分类
	public int insertTopicTypeRela(Map map);
	
	//删除卖场所属分类
	public int deleteTopicTypeRela(Map map);
	
	//查询要预览的卖场列表
	public List<Topic> getPreviewTopicList(Map map, Page<?> page);
	
	/**
	 * 批量导入修改卖场
	 * @param date
	 * @return
	 */
	public int batchUpdateImportTopics(Map<String, Map<Object,Object>> data,Long createUserId);
	
	
	/**
	 * 批量获取存在卖场活动id
	 * @param params
	 * @return
	 */
	public List<String> findExistTopicActivityId(Map params); 
	
	/**
	 * 获取适合同步到搜索的卖场数据总数
	 * @param params
	 * @return
	 */
	public Integer getSyncToSearchCount(Map params); 
	
	/**
	 * 获取适合同步到搜索的卖场数据
	 * @param params
	 * @return
	 */
	public List<Topic> getSyncToSearchList(Map params); 
	
}
