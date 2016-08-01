package com.xiu.mobile.core.dao;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.PayForTemplet;

/**
 * 找朋友代付模板管理DAO
 * @author coco.long
 * @time	2015-1-14
 */
public interface PayForTempletDAO {

	//查询代付模板列表
	public List<PayForTemplet> getPayForTempletList(Map map);
	
	//查询代付模板数量
	public int getPayForTempletCount(Map map);
	
	//查询代付模板
	public PayForTemplet getPayForTemplet(Map map);
	
	//添加代付模板
	public int insert(PayForTemplet payForTemplet);
	
	//更新代付模板
	public int update(PayForTemplet payForTemplet);
	
	//删除代付模板
	public int delete(Map map);
	
	//更改代付模板状态
	public int updateStatus(Map map);
	
}
