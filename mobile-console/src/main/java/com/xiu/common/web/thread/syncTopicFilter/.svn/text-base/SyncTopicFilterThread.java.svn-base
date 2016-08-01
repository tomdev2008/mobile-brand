package com.xiu.common.web.thread.syncTopicFilter;

import java.util.List;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import com.xiu.mobile.core.service.ITopicFilterService;
import com.xiu.show.robot.common.contants.ShowRobotConstants;
import com.xiu.show.robot.common.utils.HttpUtil;
import com.xiu.show.robot.common.utils.ObjectUtil;

public class SyncTopicFilterThread extends Thread{

	// 日志
	private static final XLogger LOGGER = XLoggerFactory
			.getXLogger(SyncTopicFilterThread.class);
 	
	 public SyncTopicFilterThread(ITopicFilterService topicFilterService,List<Long> topicIds){
		 this.topicFilterService=topicFilterService;
		 this.topicIds=topicIds;
	 }
	 
	 private ITopicFilterService topicFilterService;
	 private List<Long> topicIds;
	 
	 public void run(){
			LOGGER.info("同步卖场筛选数据到缓存-异步处理-开始");
			//对每个卖场进行过滤数据缓存处理
			for (Long topicId:topicIds) {
				try{
					topicFilterService.addTopicGoodFilterCache(topicId+"");
				}catch(Exception e){
					LOGGER.error("同步卖场筛选数据到缓存-异常，topicId:"+topicId+",error:"+e.getMessage());
				}
			}
			LOGGER.info("同步卖场筛选数据到缓存-异步处理-结束");

	 }
			 
}
