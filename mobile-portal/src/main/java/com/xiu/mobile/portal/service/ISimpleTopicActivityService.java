package com.xiu.mobile.portal.service;

import java.util.List;

import com.xiu.mobile.portal.model.SimpleTopicActivityVo;

/***
 * 精选卖场
 * @author hejx
 *
 */
public interface ISimpleTopicActivityService {

	/***
	 * 查询所有精选卖场数据
	 * @return
	 */
	public List<SimpleTopicActivityVo> getAll();
	
}
