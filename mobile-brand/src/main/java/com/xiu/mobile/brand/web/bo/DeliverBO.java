package com.xiu.mobile.brand.web.bo;

import com.xiu.mobile.brand.web.dao.model.DeliverModel;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(发货地业务逻辑BO) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2015-4-2 下午7:11:49 
 * ***************************************************************
 * </p>
 */

public class DeliverBO {

	private DeliverModel space;
	
	private Long count;
	
	/**
	 * @param space
	 * @param count
	 */
	public DeliverBO(DeliverModel space, Long count) {
		super();
		this.space = space;
		this.count = count;
	}

	/**
	 * @return the space
	 */
	public DeliverModel getSpace() {
		return space;
	}

	/**
	 * @param space the space to set
	 */
	public void setSpace(DeliverModel space) {
		this.space = space;
	}

	/**
	 * @return the count
	 */
	public Long getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(Long count) {
		this.count = count;
	}
}
