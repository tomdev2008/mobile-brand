package com.xiu.mobile.simple.dao;


import java.util.List;
import java.util.Map;

import com.xiu.mobile.simple.model.SimpleTopicActivityVo;
/**
 * @author: hejx
 * @description:精选卖场
 * @date: 2014-07-11
 */
public interface SimpleTopicActivityDao {

//	/***
//	 * 精选卖场分页列表数据查询
//	 * @param params
//	 * @return
//	 */
//	public List<SimpleTopicActivityVo> selectPageList(Map<String, Object> params);
	
	/***
	 * 精选卖场所有记录查询
	 * @return
	 */
	public List<SimpleTopicActivityVo> selectAll();
	
}
