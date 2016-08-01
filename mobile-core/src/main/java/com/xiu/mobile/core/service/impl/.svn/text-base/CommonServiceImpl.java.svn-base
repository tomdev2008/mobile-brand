package com.xiu.mobile.core.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiu.mobile.core.dao.CommonDao;
import com.xiu.mobile.core.service.ICommonService;

/**
 * 公用Service
 * @author coco.long
 * @time	2015-3-9
 */
@Transactional
@Service("commonService")
public class CommonServiceImpl implements ICommonService {

	@Autowired
	private CommonDao commonDao;
	
	public CommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public int updateFindChannelVersion(Map map) {
		return commonDao.updateFindChannelVersion(map);
	}

}
