package com.xiu.mobile.portal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.portal.dao.SimpleTopicActivityDao;
import com.xiu.mobile.portal.model.SimpleTopicActivityVo;
import com.xiu.mobile.portal.service.ISimpleTopicActivityService;

@Service("simpleTopicActivityService")
public class SimpleTopicActivityServiceImpl implements ISimpleTopicActivityService{
	
	@Autowired
	private SimpleTopicActivityDao simpleTopicActivityDao;

	@Override
	public List<SimpleTopicActivityVo> getAll() {
		return simpleTopicActivityDao.selectAll();
	}

}
