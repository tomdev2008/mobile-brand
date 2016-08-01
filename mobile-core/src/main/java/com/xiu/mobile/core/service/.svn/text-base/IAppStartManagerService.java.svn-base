package com.xiu.mobile.core.service;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.AppChannel;
import com.xiu.mobile.core.model.AppStartManagerModel;
import com.xiu.mobile.core.model.Page;

/**
 * @description:卖场启动页管理Service
 * @AUTHOR :coco.long
 * @DATE :2015-03-24
 */
public interface IAppStartManagerService {

	/**
	 * 查询app启动页列表
	 * @param map
	 * @return
	 */
	public List<AppStartManagerModel> getAppStartManagerList(Map map, Page<?> page);
	
	/**
	 * 查询app启动页
	 * @param map
	 * @return
	 */
	public AppStartManagerModel getAppStartManager(Map map);
	
	/**
	 * 新增app启动页
	 * @param appStartManager
	 * @return
	 */
	public int insert(AppStartManagerModel appStartManager);
	
	/**
	 * 更新app启动页
	 * @param appStartManager
	 * @return
	 */
	public int update(AppStartManagerModel appStartManager);
	
	/**
	 * 删除app启动页
	 * @param map
	 * @return
	 */
	public int delete(Map map);
	
	/**
	 * 更新app启动页状态
	 * @param map
	 * @return
	 */
	public int updateStatus(Map map);
	
	/**
	 * 查询启动页数量
	 * @param map
	 * @return
	 */
	public int getStartPageCount(Map map);
	
	//查询发行渠道列表
	public List<AppChannel> getAppChannelList(Map map,Page<?> page);
	
	//查询所有的发行渠道列表
	public List<AppChannel> getAllAppChannelList(Map map);
	
	//查询发行渠道
	public AppChannel getAppChannel(Map map);
	
	//新增发行渠道
	public int insertAppChannel(AppChannel appChannel);
	
	//修改发行渠道
	public int updateAppChannel(AppChannel appChannel);
	
	//更新发行渠道状态
	public int updateAppChannelStatus(Map map);
}
