package com.xiu.mobile.simple.dao;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.simple.model.ActivityVo;
import com.xiu.mobile.simple.model.Topic;

/**
 * <p>
 * ************************************************************** 
 * @Description: 每日专题数据层
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-14 下午4:42:35 
 * ***************************************************************
 * </p>
 */
public interface ActivityNoregularDao 
{
    List<ActivityVo> queryActiveList(Map<String, Object> valMap);
    
	List<Topic> queryTopicList(Map<String, Object> valMap);
	
	int getTopicTotalAmount(Map<String, Object> valMap);
	
    List<ActivityVo> queryOutletsList(String saleType);
    
    String queryTopicNameByActivityId(String activityId);
    
}
