package com.xiu.show.web.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.common.web.utils.DateUtil;
import com.xiu.show.core.dao.ShowStatisticsManagerDao;
import com.xiu.show.core.dao.UserNewShowManagerDao;
import com.xiu.show.web.service.IShowerDataTaskManagerService;

@Service("showerDataTaskManagerService")
public class ShowerDataTaskManagerServiceImpl implements IShowerDataTaskManagerService	{

	@Autowired
	UserNewShowManagerDao userNewShowManagerDao;
	@Autowired
	ShowStatisticsManagerDao showStatisticsManagerDao;
	/**
	 * 定时删除用户浏览器记录数据
	 */
	public void callDeleteShowerDataTask() {
//		//删除半年前的浏览记录
		Map params =new HashMap();
		Date date=DateUtil.getTimeDateByDays(new Date(), -100);//半年前
		Date endDate=DateUtil.getTimeDateByDays(new Date(), -102);//半年前加2天
		params.put("createDate", date);
		params.put("endDate", endDate);
		
		//1.删除用户关注的人的秀关联数据
		userNewShowManagerDao.deleteUserNewShowByTime(params);
		//2.删除推荐数据
		showStatisticsManagerDao.deleteUserRecommendByTime(params);
	}

	
}
