package com.xiu.mobile.brand.web.cache.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.xiu.mobile.brand.web.cache.DeliverInfoCache;
import com.xiu.mobile.brand.web.dao.DeliverDao;
import com.xiu.mobile.brand.web.dao.model.DeliverModel;
import com.xiu.mobile.brand.web.util.ConfigUtil;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2015-4-2 下午6:02:24 
 * ***************************************************************
 * </p>
 */

public class DeliverInfoCacheImpl extends DeliverInfoCache {
	
	private static final Logger LOGGER = Logger.getLogger(DeliverInfoCacheImpl.class);
	
	private DeliverDao deliverDao;
	
	private volatile boolean isRunning;
	
	private Map<Integer, DeliverModel> map = new HashMap<Integer, DeliverModel>();

	@Override
	public void init() {
		this.reLoad();
		this.reloadTimer();
	}

	@Override
	public void reLoad() {
		if(isRunning) {
			return;
		}

		LOGGER.info("【SpaceInfoCacheImpl】 开始从数据库中读取发货地数据: ");
		List<DeliverModel> deliverModels = this.getSpaceInfoFromDB();
		if(deliverModels!=null){
			for(DeliverModel deliverModel : deliverModels) {
				map.put(deliverModel.getCode(), deliverModel);
			}
		}
		isRunning = false;
	}
	
	/**
	 * 从数据库中加载所有分类信息
	 * @return
	 */
	private List<DeliverModel> getSpaceInfoFromDB() {
		List<DeliverModel> deliverModels = null;
		try {
			deliverModels = deliverDao.getAllDeliverInfo();
			return deliverModels;
		} catch (Exception e) {
			LOGGER.error("【DeliverInfoCacheImpl】 从数据库中读取发货地数据出错: ", e);
			return null;
		}
	}
	
	/**
	 * 定时器
	 */
	private void reloadTimer() {
		Timer t = new Timer(true);
		TimerTask tk = new TimerTask() {
			@Override
			public void run() {
				reLoad();
			}
		};
		
		// 10秒之后 ，每隔N分钟执行一次
		long updateTime  = 60000 * Long.valueOf(ConfigUtil.getValue("catalog.update.time"));
		t.scheduleAtFixedRate(tk, 1000, updateTime);
	}

	@Override
	public DeliverModel getDeliverModel(Integer code) {
		return map.get(code);
	}

	/**
	 * @return the deliverDao
	 */
	public DeliverDao getDeliverDao() {
		return deliverDao;
	}

	/**
	 * @param deliverDao the deliverDao to set
	 */
	public void setDeliverDao(DeliverDao deliverDao) {
		this.deliverDao = deliverDao;
	}

}
