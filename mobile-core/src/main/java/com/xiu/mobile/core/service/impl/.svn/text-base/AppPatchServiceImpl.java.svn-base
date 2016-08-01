package com.xiu.mobile.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiu.mobile.core.dao.AppPatchDao;
import com.xiu.mobile.core.model.AppPatchVO;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.service.IAppPatchService;

@Transactional
@Service("appPatchService")
public class AppPatchServiceImpl implements IAppPatchService{

	//日志
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(AppPatchServiceImpl.class);
	
	 @Autowired
    private AppPatchDao appPatchDao;
	 
	
	 
	@Override
	public int save(AppPatchVO vo) {
		//如果为启用，则禁用同类型所有的业务
		if(vo.getStatus().equals(1L)){
			appPatchDao.stopAlltypeAppPatch(vo.getType());
		}
		return appPatchDao.save(vo);
	}

	@Override
	public int update(AppPatchVO vo) {
		//如果是启用版本，则更新同类型的版本为禁用，
		if(vo.getStatus().equals(1L)){
			AppPatchVO old=queryAppPatchVOById(vo.getId());
			if(old!=null)
			appPatchDao.stopAlltypeAppPatch(old.getType());
		}
		return appPatchDao.update(vo);
	}

	@Override
	public AppPatchVO queryAppPatchVOById(Long id) {
		// TODO Auto-generated method stub
		return appPatchDao.queryAppPatchVOById(id);
	}

	/**
	 * 判断指定版本是否存在
	 */
	@Override
	public boolean checkStatusAppPatchVO(Long id) {
		// TODO Auto-generated method stub
		
		AppPatchVO vo= queryAppPatchVOById(id);
		Map<Object, Object> map=new HashMap<Object, Object> ();
		map.put("type", vo.getType());
		map.put("status", 1);
		map.put("pageMax", 3);
		map.put("pageMin", 1);
		List<AppPatchVO> appPatchList=appPatchDao.getAppPatchVOList(map);
		if(CollectionUtils.isNotEmpty(appPatchList)){
			return true;
		}
		return false;
	}

	@Override
	public Map<String,Object> getAppPatchVOListByCondition(Map<Object, Object> map,Page<?> page) {
		// TODO Auto-generated method stub
		Map<String,Object> resultMap=new HashMap<String,Object>();
		List<AppPatchVO> appPatchList=new ArrayList<AppPatchVO>();
		
		map.put("pageMin",page.getFirstRecord());
		map.put("pageSize", page.getPageSize());
		map.put("pageMax", page.getEndRecord());
		
		appPatchList=appPatchDao.getAppPatchVOList(map);
		
		page.setTotalCount(Integer.valueOf(appPatchDao.getAppPatchVOListCount(map)));
		//成功
		boolean resultStatus=true;
		
		resultMap.put("page", page);
		resultMap.put("status", resultStatus);
		resultMap.put("msg","");
		resultMap.put("resultInfo", appPatchList);
		return resultMap;
	}

	@Override
	public int queryIdBySeq() {
		// TODO Auto-generated method stub
		return appPatchDao.queryIdBySeq();
	}

}
