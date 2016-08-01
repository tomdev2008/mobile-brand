package com.xiu.mobile.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiu.mobile.core.constants.ErrConstants;
import com.xiu.mobile.core.dao.PayForTempletDAO;
import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.model.PayForTemplet;
import com.xiu.mobile.core.service.IPayForTempletService;

/**
 * 找朋友代付模板管理Service
 * @author coco.long
 * @time	2015-1-14
 */
@Transactional
@Service("payForTempletService")
public class PayForTempletServiceImpl implements IPayForTempletService {

	// 日志
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(TopicServiceImpl.class);
	
	@Autowired
	private PayForTempletDAO payForTempletDao;
		
	public PayForTempletDAO getPayForTempletDao() {
		return payForTempletDao;
	}

	public void setPayForTempletDao(PayForTempletDAO payForTempletDao) {
		this.payForTempletDao = payForTempletDao;
	}

	public List<PayForTemplet> getPayForTempletList(Map map, Page<?> page) {
		List<PayForTemplet> resultList = new ArrayList<PayForTemplet>();
		page.setPageNo(page.getPageNo() == 0 ? 1 : page.getPageNo());
		int lineMax = page.getPageNo() * page.getPageSize()+1;
		int lineMin = (page.getPageNo()-1)*page.getPageSize()+1;
		
		map.put("lineMax", lineMax);
		map.put("lineMin", lineMin);
		try {
			//查询代付模板列表
			resultList = payForTempletDao.getPayForTempletList(map);
			
			//查询代付模板数量
			int counts = payForTempletDao.getPayForTempletCount(map);
			page.setTotalCount(counts); 
		} catch(Throwable e) {
			LOGGER.error("查询代付模板异常！", e);
			throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_WAP_ERR, "查询代付模板异常！");
		}
		return resultList;
	}

	public PayForTemplet getPayForTemplet(Map map) {
		return payForTempletDao.getPayForTemplet(map);
	}
	
	public int insert(PayForTemplet payForTemplet) {
		return payForTempletDao.insert(payForTemplet);
	}

	public int update(PayForTemplet payForTemplet) {
		return payForTempletDao.update(payForTemplet);
	}

	public int delete(Map map) {
		return payForTempletDao.delete(map);
	}

	public int updateStatus(Map map) {
		return payForTempletDao.updateStatus(map);
	}

}
