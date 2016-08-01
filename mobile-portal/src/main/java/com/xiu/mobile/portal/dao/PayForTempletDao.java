package com.xiu.mobile.portal.dao;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.PayForTemplet;

/**
 * 找朋友代付模板
 * @author coco.long
 * @time	2015-1-19
 */
public interface PayForTempletDao {

	//查询找朋友代付模板列表
	public List<PayForTemplet> getPayForTempletList(Map map);
	
	//查询找朋友代付模板数量
	public int getPayForTempletCount(Map map);
}
