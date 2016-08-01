package com.xiu.mobile.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiu.mobile.core.constants.ErrConstants;
import com.xiu.mobile.core.dao.TopicTypeDao;
import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.model.TopicType;
import com.xiu.mobile.core.service.ITopicTypeService;

/**
 * @description 卖场分类Service
 * @author coco.long
 * @time	2015-03-03
 */
@Transactional
@Service("topicTypeService")
public class TopicTypeServiceImpl implements ITopicTypeService {

	// 日志
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(TopicTypeServiceImpl.class);
				
	@Autowired
	private TopicTypeDao topicTypeDao;
	
	public TopicTypeDao getTopicTypeDao() {
		return topicTypeDao;
	}

	public void setTopicTypeDao(TopicTypeDao topicTypeDao) {
		this.topicTypeDao = topicTypeDao;
	}

	public List<TopicType> getTopicTypeList(Map map, Page<?> page) {
		List<TopicType> resultList = new ArrayList<TopicType>();
		page.setPageNo(page.getPageNo() == 0 ? 1 : page.getPageNo());
		int lineMax = page.getPageNo() * page.getPageSize()+1;
		int lineMin = (page.getPageNo()-1)*page.getPageSize()+1;

		map.put("lineMax", lineMax);
		map.put("lineMin", lineMin);
		
		try {
			//查询发现频道栏目列表
			resultList = topicTypeDao.getTopicTypeList(map);
			
			//查询发现频道栏目数量
			int count = topicTypeDao.getTopicTypeCount(map);
			page.setTotalCount(count);
			
		} catch(Throwable e) {
			LOGGER.error("查询卖场分类异常！", e);
			throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_WAP_ERR, "查询卖场分类异常！");
		}
		
		return resultList;
	}

	public TopicType getTopicType(Map map) {
		return topicTypeDao.getTopicType(map);
	}

	public int insert(TopicType TopicType) {
		return topicTypeDao.insert(TopicType);
	}

	public int update(TopicType TopicType) {
		return topicTypeDao.update(TopicType);
	}

	public int delete(Map map) {
		return topicTypeDao.delete(map);
	}

	public int updateStatus(Map map) {
		return topicTypeDao.updateStatus(map);
	}

	public List<TopicType> getAllTopicTypeList(Map map) {
		return topicTypeDao.getAllTopicTypeList(map);
	}

	public int queryActivityByTopicType(Map map) {
		return topicTypeDao.queryActivityByTopicType(map);
	}

	public List<TopicType> getTopicTypeAndActivityList(Map map) {
		return topicTypeDao.getTopicTypeAndActivityList(map);
	}
	
	public List<Long> getTopicTypeIdByNames(Map map) {
		return topicTypeDao.getTopicTypeIdByNames(map);
	}

	public void callSyncUpdateTopicTypeStatusTask() {
		try{
			topicTypeDao.updateBatchTopicTypeStatusByTime(null);
		}catch(Exception e){
			LOGGER.error("定时更新卖场分类状态异常:"+e.getMessage());
		}
	}
}
