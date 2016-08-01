package com.xiu.show.robot.common.thread;

import java.util.List;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import com.xiu.show.robot.common.contants.ShowRobotConstants;
import com.xiu.show.robot.common.utils.HttpUtil;
import com.xiu.show.robot.common.utils.ObjectUtil;

public class HttpThread implements Runnable{

	// 日志
	private static final XLogger LOGGER = XLoggerFactory
			.getXLogger(HttpThread.class);
			 private String url;
			 private List<String> userIds;
			 private List<String> showIds;
			 public HttpThread(String url,List<String> showIds,List<String> userIds){
				 this.url=url;
				 this.userIds=userIds;
				 this.showIds=showIds;
			 }
			 public void run(){
//				 LOGGER.info("--run--");
//				 Integer randomNum=50;//随机范围数,单位毫秒
//				 if( userIds.size()>ShowRobotConstants.SHOW_BRUSH_DATA_MINUTE_NUM*10){//如果数目比较多，则缩小随机范围数
//					 randomNum=10;
//				 }
				 for (int i = 0; i < userIds.size(); i++) {
//					int sleepnum=ObjectUtil.getRandom(randomNum);
//					LOGGER.info("睡眠"+sleepnum+"毫秒后访问接口");
//					 try {
//						Thread.sleep(sleepnum);
//					} catch (InterruptedException e1) {
//						e1.printStackTrace();
//					}
					 HttpUtil.executeGet(url+showIds.get(i), userIds.get(i));
				 }
				 LOGGER.info("刷点赞"+userIds.size()+"个");
			 }
}
