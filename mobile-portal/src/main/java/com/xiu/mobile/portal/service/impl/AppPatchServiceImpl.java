package com.xiu.mobile.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.core.dao.AppPatchDao;
import com.xiu.mobile.core.model.AppPatchVO;
import com.xiu.mobile.portal.model.AppVo;
import com.xiu.mobile.portal.service.IAppPatchService;

@Service("appPatchServiceImpl")
public class AppPatchServiceImpl implements IAppPatchService {

	@Autowired
	private AppPatchDao appPatchDao;
	
	@Override
	public AppPatchVO getAppPatchVOByCondition(AppVo appVo ) {
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("type", appVo.getAppSource());
		map.put("version", appVo.getAppVersion());
		map.put("status", 1);
		AppPatchVO newVo= appPatchDao.getAppPatchVOByConditionInfo(map);
		if(newVo != null ){
			newVo.setJsVersion(newVo.getId());
		}
		return newVo;
	}

}
