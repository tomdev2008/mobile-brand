/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-27 上午11:01:57 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.mobile.core.dao;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.AppDownloadModel;
import com.xiu.mobile.core.model.AppVersionModel;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(版本更新管理数据持久层) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-27 上午11:01:57 
 * ***************************************************************
 * </p>
 */

public interface AppVersionDao {
	
	/**
	 * 获得最新App版本
	 * @param type
	 * @return
	 */
	 List<AppVersionModel> getNewestAppVersion(int type);
	
	/**
	 * 通过ID查询版本更新信息
	 * @param id
	 * @return
	 */
	AppVersionModel getVersionById(Long id);

	/**
	 * 获取版本信息（多条件查询）
	 * @param versionNo
	 * @param coutent
	 * @param sDate
	 * @param eDate
	 * @param status
	 * @return
	 */
	List<AppVersionModel> getVersionListByParam(Map<String, Object> map);
	
	/**
	 * 获取版本信息总数（多条件）
	 * @return
	 */
	Integer getVersionCountByParam(Map<String, Object> map);
	
	/**
	 * 保存
	 * @param appVersionModel
	 * @return
	 */
	Integer saveVersion(AppVersionModel appVersionModel);
	
	/**
	 * 更新
	 * @param appVersionModel
	 * @return
	 */
	Integer updateVersion(AppVersionModel appVersionModel);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	Integer deleteVersion(Long id);
	
	/**
	 * 修改启用状态
	 * @param id
	 * @param status
	 * @return
	 */
	Integer updateStatus(Map<String, Object> map);
	
	/**
	 * 新增App下载记录
	 * @param id
	 * @param status
	 * @return
	 */
	Integer insertDownloadRecord(AppDownloadModel model);
}
