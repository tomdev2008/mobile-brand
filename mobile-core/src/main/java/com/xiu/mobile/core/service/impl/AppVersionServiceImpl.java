/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-27 上午11:57:42 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.mobile.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiu.mobile.core.dao.AppVersionDao;
import com.xiu.mobile.core.model.AppDownloadModel;
import com.xiu.mobile.core.model.AppVersionModel;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.service.IAppVersionService;
import com.xiu.mobile.core.utils.DateUtil;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-27 上午11:57:42 
 * ***************************************************************
 * </p>
 */
@Transactional
@Service("appVersionService")
public class AppVersionServiceImpl implements IAppVersionService {
	
	@Autowired
	private AppVersionDao appVersionDao;
	
	@Override
	public AppVersionModel getAppVersionModelById(Long id) {
		return appVersionDao.getVersionById(id);
	}
	
	@Override
	public List<AppVersionModel> getAppVersionListByParam(String versionNo, String content, String sDate, String eDate, 
			Integer status, Page<?> page) {
		Map<String, Object> map = changeParams(versionNo, content, sDate, eDate, status, page);
		List<AppVersionModel> list = appVersionDao.getVersionListByParam(map);
		return list;
	}
	
	@Override
	public Integer getVersionCountByParam(String versionNo, String content, String sDate, String eDate, 
			Integer status) {
		Map<String, Object> map = changeParams(versionNo, content, sDate, eDate, status, null);
		return appVersionDao.getVersionCountByParam(map);
	}

	@Override
	public boolean saveOrUpdateAppVersion(AppVersionModel appVersionModel) {
		
		if(null == appVersionModel.getId()) { // 添加
			return appVersionDao.saveVersion(appVersionModel) > 0;
		} 
		else { // 修改
			return appVersionDao.updateVersion(appVersionModel) > 0;
		}
	}

	@Override
	public boolean deleteAppVersion(Long id) {
		return appVersionDao.deleteVersion(id) > 0;
	}

	@Override
	public boolean updateStatus(Long id, String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		return appVersionDao.updateStatus(map) > 0;
	}
	
	@Override
	public AppVersionModel getNewestAppVersion(int type) {
		List<AppVersionModel> appList = appVersionDao.getNewestAppVersion(type);
		
		if (null != appList && appList.size() > 0) {
			return appList.get(0);
		} else {
			return null;
		}

	}
	
	/**
	 * 封装参数
	 * @param versionNo
	 * @param content
	 * @param sDate
	 * @param eDate
	 * @param status
	 * @param page
	 * @return
	 */
	private Map<String, Object> changeParams(String versionNo, String content, String sDate, String eDate, 
			Integer status, Page<?> page) {

		Map<String, Object> map = new HashMap<String, Object>();
		
		if(StringUtils.isNotEmpty(versionNo)) {
			map.put("versionNo", versionNo);
		}
		
		if(StringUtils.isNotEmpty(content)) {
			map.put("content", content);
		}
		
		if(StringUtils.isNotEmpty(sDate)) {
			map.put("sDate", DateUtil.parseDate(sDate, "yyyy-MM-dd"));
		}
		
		if(StringUtils.isNotEmpty(eDate)) {
			map.put("eDate", DateUtil.parseDate(eDate, "yyyy-MM-dd"));
		}
		
		if(status!= null && status != -1) {
			map.put("status", status);
		}
		
		if(page != null) {
			// 分页参数
			page.setPageNo(page.getPageNo() > 0 ? page.getPageNo() : 1);
			
	        int pageMax = page.getPageNo() * page.getPageSize();
	        int pageMin = 1;
	        if (page.getPageNo() > 1) {
	            pageMin = (page.getPageNo() - 1) * page.getPageSize() + 1;
	        }
	        
			map.put("pageMin", pageMin);
			map.put("pageMax", pageMax);
		}
		
		return map;
	}
	/**
	 * 插入下载记录
	 * @param id
	 * @param status
	 * @return
	 */
	public int insertDownloadRecord(AppDownloadModel model){
		return appVersionDao.insertDownloadRecord(model);
		
	}
}
