package com.xiu.mobile.brand.web.cache.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.xiu.mobile.brand.web.cache.XiuAttrGroupInfoCache;
import com.xiu.mobile.brand.web.dao.XDataAttrDescDao;
import com.xiu.mobile.brand.web.dao.model.XDataAttrDesc;


public class XiuAttrGroupInfoCacheImpl extends XiuAttrGroupInfoCache {

	private final static Logger log = Logger.getLogger(XiuAttrGroupInfoCacheImpl.class);
	
	private static Map<String, String> cache = new HashMap<String, String>();
	
	@Override
	protected void init() {
		log.info("【XiuAttrGroupInfoCacheImpl】 系统启动时，开始加载属性项名称缓存数据: ");
		this.reloadAllAttrGroupName();
		this.reloadAttrGrouNameTimer();
		log.info("【XiuAttrGroupInfoCacheImpl】 系统启动时，完成加载属性项名称缓存数据");
	}

	@Override
	public String getAttrGrouNameByID(String id) {
		Map<String, String> tmpMap=cache;
		return tmpMap.get(id);
	}
	
	@Override
	protected int getCacheSize() {
		Map<String, String> tmpMap=cache;
		return tmpMap.size();
	}
    
	private Map<String, String> getAllAttrGroupName() {
		List<XDataAttrDesc> list = xDataAttrDescDao.getAllAttrGroupName();
		if(list==null || list.size()==0){
			return null;
		}
		Map<String, String> rstMap = new HashMap<String, String>();
		for(XDataAttrDesc d:list){
			rstMap.put(d.getId(), d.getName());
		}
		return rstMap;
	}
	/**
	 * @Description: 从数据库中加载所有属性项名称缓存   
	 * @return void
	 */
	private void reloadAllAttrGroupName(){
		
		Map<String, String> tmpMap = getAllAttrGroupName();
		if(tmpMap==null){
			log.error("=========================reloadAttrGrouName return NULL========================"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		}else {
			log.info("=========================reloadAttrGrouName return size:"+tmpMap.size()+"========================"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		}
		cache = tmpMap;
	}
	
	/**
	 * 定时器 
	 */
	private void reloadAttrGrouNameTimer(){
		Timer t=new Timer(true);
		TimerTask tk=new TimerTask() {
			@Override
			public void run() {
				reloadAllAttrGroupName();
				log.info("=========================reloadAttrGrouNameTimer========================"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			}
		};
		//4小时之后 ，每隔4小时执行一次
		t.scheduleAtFixedRate(tk, 4*60*60*1000, 4*60*60*1000);
	}
    @Autowired
	private XDataAttrDescDao xDataAttrDescDao;
}
