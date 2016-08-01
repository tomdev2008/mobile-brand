package com.xiu.wap.web.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.danga.MemCached.MemCachedClient;
import com.xiu.wap.web.service.MemcachedService;

@Transactional()
@Service("memcachedService")
public class MemcachedServiceImpl implements MemcachedService{

	private Logger logger = Logger.getLogger(MemcachedServiceImpl.class);
	
	@Resource(name="memcachedClient")
	private MemCachedClient memcachedClient;

	@Override
	public boolean setCache(Object key, Object value) {
		logger.info(key + " --> " + value);
		return memcachedClient.set((String) key, value);
	}

	@Override
	public Object getCache(Object key) {
		
		return memcachedClient.get((String) key);
	}
	
	
}
