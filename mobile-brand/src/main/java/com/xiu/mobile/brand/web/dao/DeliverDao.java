package com.xiu.mobile.brand.web.dao;

import java.util.List;

import com.xiu.mobile.brand.web.dao.model.DeliverModel;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(发货地) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2015-4-2 下午6:05:56 
 * ***************************************************************
 * </p>
 */

public interface DeliverDao {

	/**
	 * 获取所有发货地信息
	 * @return
	 */
	List<DeliverModel> getAllDeliverInfo();
	
}
