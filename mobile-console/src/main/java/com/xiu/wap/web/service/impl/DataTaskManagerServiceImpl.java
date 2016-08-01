package com.xiu.wap.web.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.common.web.thread.syncTopicFilter.SyncTopicFilterThread;
import com.xiu.common.web.thread.syncTopicFilter.SyncTopicFilterThreadTool;
import com.xiu.common.web.utils.DateUtil;
import com.xiu.mobile.core.constants.GlobalConstants;
import com.xiu.mobile.core.dao.BrowseGoodsMgtDao;
import com.xiu.mobile.core.dao.LabelDao;
import com.xiu.mobile.core.dao.TopicDao;
import com.xiu.mobile.core.service.ITopicFilterService;
import com.xiu.wap.web.service.IDataTaskManagerService;

@Service("dataTaskManagerService") 
public class DataTaskManagerServiceImpl implements IDataTaskManagerService{

    private static final XLogger LOGGER = XLoggerFactory.getXLogger(DataTaskManagerServiceImpl.class);
	
	@Autowired
	private BrowseGoodsMgtDao browsGoodDao;
	
	@Autowired
	private ITopicFilterService topicFilterService;
	
	@Autowired
	TopicDao topicDao;
	
	@Autowired
	LabelDao labelDao;
	/**
	 * 定时删除用户浏览器记录数据
	 */
	public void callDeleteUserBrowseDataTask() {
		//删除半年前的浏览记录
		Map params =new HashMap();
		Date date=DateUtil.getTimeDateByDays(new Date(), -180);//半年前
		Date endDate=DateUtil.getTimeDateByDays(new Date(), -182);//半年前加两天
		params.put("createDate", date);
		params.put("endDate", endDate);
		browsGoodDao.deleteBrowseGoodsBatchByTime(params);
	}
	
	public static void main(String[] args) {
		Date date=DateUtil.getTimeDateByDays(new Date(), -180);//半年前
		Date endDate=DateUtil.getTimeDateByDays(new Date(), -182);//半年前加两天
		System.out.println(date);
		System.out.println(endDate);
	}
	
	
	public void syncTopicFilterDataToCache() {
		LOGGER.info("同步卖场筛选数据到缓存-开始");
		//1 查询进行中的卖场
		Map map=new HashMap();
		Integer total=topicDao.getIngTopicCount(map);
		int pageSize=1000;
		int totalPage=total/pageSize+1;
		for (int i = 0; i <totalPage; i++) {
			int pageMin=i*pageSize;
			int pageMax=(i+1)*pageSize;
			if(pageMax>total){
				pageMax=total;
			}
			map.put("pageMax", pageMax);
			map.put("pageMin", pageMin);
			List<Long> topicIds=topicDao.getIngTopicIdByPage(map);
			SyncTopicFilterThread filterThread=new SyncTopicFilterThread(topicFilterService,topicIds);
			SyncTopicFilterThreadTool.getInstance().execute(filterThread);
		}
		LOGGER.info("同步卖场筛选数据到缓存-结束");
	}

	@Override
	public void syncLabelRelationDate() {
		//将老数据且未被改动的数据删除
		labelDao.deleteLabelRelationDate();
		//获取标签关联数据且批量插入
		List<Integer> types=new ArrayList<Integer>();//需要插入的关联标签分类
		types.add(GlobalConstants.LABEL_TYPE_SHOWER);
		
		Integer max=20;//每个标签关联数据的个数
		Map params=new HashMap();
		params.put("maxNum", max);
		for (Integer type:types) {
			params.put("type", type);
			labelDao.addLabelRelationDateByType(params);
		}
		
//		//删除有重复的数据（临时处理）
		Map delparams=new HashMap();
		labelDao.deleteBatchRepeatedLabelObject(delparams);
		
	}

	
}
