/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-27 上午11:57:27 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.mobile.core.service;

import java.util.List;

import com.xiu.mobile.core.model.AppDownloadModel;
import com.xiu.mobile.core.model.AppVersionModel;
import com.xiu.mobile.core.model.Page;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-27 上午11:57:27 
 * ***************************************************************
 * </p>
 */

public interface IAppVersionService {
	
	/**
	 * 通过ID获取版本更新信息
	 * @param id
	 * @return
	 */
	AppVersionModel getAppVersionModelById(Long id);
	
	/**
	 * 获取版本信息列表（多条件查询，分页）
	 * @return
	 */
	List<AppVersionModel> getAppVersionListByParam(String versionNo, String content, String sDate, String eDate, 
			Integer status, Page<?> page);
	
	/**
	 * 获取版本信息总数（多条件查询）
	 * @param param
	 * @return
	 */
	Integer getVersionCountByParam(String versionNo, String content, String sDate, String eDate, 
			Integer status);
	
	/**
	 * 保存/更新版本信息
	 * @param appVersionModel
	 * @return
	 */
	boolean saveOrUpdateAppVersion(AppVersionModel appVersionModel);
	
	/**
	 * 通过ID删除版本更新信息
	 * @param id
	 * @return
	 */
	boolean deleteAppVersion(Long id);
	
	/**
	 * 修改版本启动状态
	 * @param id
	 * @param status
	 * @return
	 */
	boolean updateStatus(Long id, String status);
	
	/**
	 * 获得最新App版本
	 * @param type
	 * @return
	 */
	AppVersionModel getNewestAppVersion(int type)throws Exception;
	/**
	 * 插入下载记录
	 * @param id
	 * @param status
	 * @return
	 */
	int insertDownloadRecord(AppDownloadModel model);

}
