package com.xiu.wap.web.service;

public interface MemcachedService {

	public boolean setCache(Object key, Object value);
	
	public Object getCache(Object key);
	
}
