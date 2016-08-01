package com.xiu.mobile.portal.service;

import java.util.Map;

import com.xiu.mobile.core.model.AppStartManagerModel;
import com.xiu.mobile.portal.model.AppVo;

public interface IAppService {
	
	/**
	 * 插入app版本信息
	 * @param appVo
	 * @return
	 */
	public int addApp(AppVo appVo);
	
	/**
	 * 根据appType和appVersion判断其app是否为最新版本
	 * @param appType
	 * @param appVersion
	 * @return
	 */
	public boolean isNeedUpdate(String appType, String appVersion);

	/**
	 * 根据appType， 获得最新版本的信息
	 * @param appType
	 * @return
	 */
	public AppVo queryLatestAppByType(String appType);
	
	/**
	 * 获取最近的App启动页
	 * @param map
	 * @return
	 */
	public AppStartManagerModel getLatelyAppStartPage(Map map);

}
