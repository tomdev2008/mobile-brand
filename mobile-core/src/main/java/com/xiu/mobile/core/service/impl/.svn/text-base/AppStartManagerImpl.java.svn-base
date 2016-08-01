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
import com.xiu.mobile.core.dao.AppStartManagerDao;
import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.core.model.AppChannel;
import com.xiu.mobile.core.model.AppStartManagerModel;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.model.TopicType;
import com.xiu.mobile.core.service.IAppStartManagerService;

@Transactional
@Service("appStartManagerService")
public class AppStartManagerImpl implements IAppStartManagerService {

	// 日志
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(AppStartManagerImpl.class);
	
	@Autowired
	private AppStartManagerDao appStartManagerDao;
		
	public AppStartManagerDao getAppStartManagerDao() {
		return appStartManagerDao;
	}

	public void setAppStartManagerDao(AppStartManagerDao appStartManagerDao) {
		this.appStartManagerDao = appStartManagerDao;
	}

	public List<AppStartManagerModel> getAppStartManagerList(Map map, Page<?> page) {
		List<AppStartManagerModel> resultList = new ArrayList<AppStartManagerModel>();
		page.setPageNo(page.getPageNo() == 0 ? 1 : page.getPageNo());
		int lineMax = page.getPageNo() * page.getPageSize()+1;
		int lineMin = (page.getPageNo()-1)*page.getPageSize()+1;

		map.put("lineMax", lineMax);
		map.put("lineMin", lineMin);
		
		try {
			//查询app启用页列表
			resultList = appStartManagerDao.getAppStartManagerList(map);
			
			//查询app启用页数量
			int count = appStartManagerDao.getAppStartManagerCount(map);
			page.setTotalCount(count);
		} catch(Throwable e) {
			LOGGER.error("查询app启动页列表异常！", e);
			throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_WAP_ERR, "查询app启动页列表异常！");
		}
		
		return resultList;
	}

	public AppStartManagerModel getAppStartManager(Map map) {
		return appStartManagerDao.getAppStartManager(map);
	}

	public int insert(AppStartManagerModel appStartManager) {
		return appStartManagerDao.insert(appStartManager);
	}

	public int update(AppStartManagerModel appStartManager) {
		return appStartManagerDao.update(appStartManager);
	}

	public int delete(Map map) {
		return appStartManagerDao.delete(map);
	}

	public int updateStatus(Map map) {
		return appStartManagerDao.updateStatus(map);
	}

	public int getStartPageCount(Map map) {
		return appStartManagerDao.getStartPageCount(map);
	}

	public List<AppChannel> getAppChannelList(Map map, Page<?> page) {
		List<AppChannel> resultList = new ArrayList<AppChannel>();
		page.setPageNo(page.getPageNo() == 0 ? 1 : page.getPageNo());
		int lineMax = page.getPageNo() * page.getPageSize()+1;
		int lineMin = (page.getPageNo()-1)*page.getPageSize()+1;

		map.put("lineMax", lineMax);
		map.put("lineMin", lineMin);
		
		//查询发行渠道列表
		resultList = appStartManagerDao.getAppChannelList(map);	
		
		//查询发行渠道数量
		int count = appStartManagerDao.getAppChannelCount(map);
		page.setTotalCount(count);
		
		return resultList;
	}
	
	public List<AppChannel> getAllAppChannelList(Map map) {
		List<AppChannel> resultList = new ArrayList<AppChannel>();
		
		//查询所有的发行渠道列表
		resultList = appStartManagerDao.getAllAppChannelList(map);	
				
		return resultList;
	}
	
	public AppChannel getAppChannel(Map map) {
		return appStartManagerDao.getAppChannel(map);
	}

	public int insertAppChannel(AppChannel appChannel) {
		return appStartManagerDao.addAppChannel(appChannel);
	}

	public int updateAppChannel(AppChannel appChannel) {
		return appStartManagerDao.updateAppChannel(appChannel);
	}

	public int updateAppChannelStatus(Map map) {
		return appStartManagerDao.updateAppChannelStatus(map);
	}
}
