package com.xiu.mobile.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiu.mobile.core.constants.ErrConstants;
import com.xiu.mobile.core.dao.CommonDao;
import com.xiu.mobile.core.dao.FindMenuMgtDao;
import com.xiu.mobile.core.dao.FindSupportVersionDao;
import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.core.model.FindMenuMgt;
import com.xiu.mobile.core.model.FindSupportVersion;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.service.IFindMenuMgtService;

/**
 * 发现频道栏目管理Service
 * @author coco.long
 * @time	2015-1-16
 */
@Transactional
@Service("findMenuMgtService")
public class FindMenuMgtServiceImpl implements IFindMenuMgtService {

	// 日志
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(TopicServiceImpl.class);
	
	@Autowired
	private FindMenuMgtDao findMenuMgtDao;
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private FindSupportVersionDao findSupportVersionDao;
	
	public FindMenuMgtDao getFindMenuMgtDao() {
		return findMenuMgtDao;
	}

	public void setFindMenuMgtDao(FindMenuMgtDao findMenuMgtDao) {
		this.findMenuMgtDao = findMenuMgtDao;
	}
	
	public CommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public List<FindMenuMgt> getFindMenuMgtList(Map<String,Object> map, Page<?> page) {
		List<FindMenuMgt> resultList = new ArrayList<FindMenuMgt>();
		page.setPageNo(page.getPageNo() == 0 ? 1 : page.getPageNo());
		int lineMax = page.getPageNo() * page.getPageSize()+1;
		int lineMin = (page.getPageNo()-1)*page.getPageSize()+1;

		map.put("lineMax", lineMax);
		map.put("lineMin", lineMin);
		
		try {
			//查询发现频道栏目列表
			resultList = findMenuMgtDao.getFindMenuList(map);
			
			//查询发现频道栏目数量
			int count = findMenuMgtDao.getFindMenuCount(map);
			page.setTotalCount(count);
			
		} catch(Throwable e) {
			LOGGER.error("查询发现频道栏目异常！", e);
			throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_WAP_ERR, "查询发现频道栏目异常！");
		}
		
		return resultList;
	}

	public FindMenuMgt getFindMenuMgt(Map<String,Object> map) {
		FindMenuMgt findMenuMgt=findMenuMgtDao.getFindMenu(map);
		if(findMenuMgt.getSupportVersion()!=null && findMenuMgt.getSupportVersion()==1){
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("type", 1);
			params.put("findMenuId", findMenuMgt.getId());
			List<FindSupportVersion> listVersion=findSupportVersionDao.getSupportVersionList(params);
			findMenuMgt.setListVersion(listVersion);
		}
		return findMenuMgt;
	}

	public int insert(FindMenuMgt findMenuMgt,String appSystem[],
			String appSource[],String startVersion[],String lastVersion[]) {
		int supportVersion=findMenuMgt.getSupportVersion();
		int j=0;
		if(findMenuMgt.getSupportVersion().equals(1) && appSystem==null){
			findMenuMgt.setSupportVersion(0);
		}
		Long id=findMenuMgtDao.getFindMenuId();
		findMenuMgt.setId(id);
		findMenuMgtDao.insert(findMenuMgt);
		//指定版本
		if(supportVersion==1 && appSystem.length>0){
			for(int i=0;i<appSystem.length;i++){
				FindSupportVersion version=new FindSupportVersion();
				version.setAppSystem(appSystem[i]);
				if(appSource.length>0 && StringUtils.isNotBlank(appSource[i])){
					version.setAppSource(appSource[i]);
				}
				if(lastVersion.length>0 && StringUtils.isNotBlank(lastVersion[i])){
					version.setLastVersion(lastVersion[i]);
				}
				if(startVersion.length>0 && StringUtils.isNotBlank(startVersion[i])){
					version.setStartVersion(startVersion[i]);
				}
				version.setFindMenuId(findMenuMgt.getId());
				version.setType(1);
				//保存版本信息
				findSupportVersionDao.addSupportVersionDao(version);
			}
		}
		j=1;
		return j;
	}

	public int update(FindMenuMgt findMenuMgt,String appSystem[],String appSource[],
			String startVersion[],String lastVersion[]) {
		int j=0;
		if(findMenuMgt.getSupportVersion().equals(1) && appSystem==null){
			findMenuMgt.setSupportVersion(0);
		}
		findMenuMgtDao.update(findMenuMgt);
		//删除指定版本
		findSupportVersionDao.deleteSupportVersion(findMenuMgt.getId());
		if(findMenuMgt.getSupportVersion()!=null && findMenuMgt.getSupportVersion()==1
				&& appSystem.length>0){
			for(int i=0;i<appSystem.length;i++){
				FindSupportVersion version=new FindSupportVersion();
				version.setAppSystem(appSystem[i]);
				if(appSource.length>0 && StringUtils.isNotBlank(appSource[i])){
					version.setAppSource(appSource[i]);
				}
				if(lastVersion.length>0 && StringUtils.isNotBlank(lastVersion[i])){
					version.setLastVersion(lastVersion[i]);
				}
				if(startVersion.length>0 && StringUtils.isNotBlank(startVersion[i])){
					version.setStartVersion(startVersion[i]);
				}
				version.setFindMenuId(findMenuMgt.getId());
				version.setType(1);
				//保存版本信息
				findSupportVersionDao.addSupportVersionDao(version);
			}
		}
		j=1;
		return j;
	}

	public int delete(Map<String,Object> map) {
		return findMenuMgtDao.delete(map);
	}

	public int updateStatus(Map<String,Object> map) {
		return findMenuMgtDao.updateStatus(map);
	}

	public int updateVersion(Map<String,Object> map) {
		int result = findMenuMgtDao.updateVersion(map); //更新栏目版本号
		if(result > 0) {
			result = commonDao.updateFindChannelVersion(map); //更新发现频道版本号
		}
		return result;
	}

}
