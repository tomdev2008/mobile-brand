package com.xiu.mobile.portal.dao;

import java.util.Map;

import com.xiu.mobile.core.model.AppStartManagerModel;
import com.xiu.mobile.portal.model.AppVo;

/**
 * APP更新处理
 * @author wenxiaozhuo
 * 2014-05-14
 */
public interface AppDao {

	/**
	 * 根据appType,更新以前数据的isLast属性，将其设为N
	 * @param appType
	 * @return
	 */
	public int updateAppIsLastByType(String appType);
	
	/**
	 * 插入app版本信息
	 * @param appVo
	 * @return
	 */
	public int insertApp(AppVo appVo);
	
	/**
	 * 根据appType查询最新版本信息
	 * @param appType
	 * @param appVersion
	 * @return
	 */
	public AppVo selectLatestAppByType(String appType);
	
	/**
	 * 获取最近的App启动页
	 * @param map
	 * @return
	 */
	public AppStartManagerModel getLatelyAppStartPage(Map map);
	
}
