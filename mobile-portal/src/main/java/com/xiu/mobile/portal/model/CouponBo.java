package com.xiu.mobile.portal.model;

import java.io.Serializable;
import java.util.List;

import com.xiu.mobile.core.model.Page;

/**
 * 
 * <p>
 * ************************************************************** 
 * @Description: TODO(优惠劵BO 类) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-18 上午11:46:09 
 * ***************************************************************
 * </p>
 */
public class CouponBo implements Serializable{
    
    private static final long serialVersionUID = 1170217054264233606L;
    
    /**
     * 
     */
    private int actionType;
    
    /**
     * 优惠券列表
     */
    private List<CouponVo> coupons;
    
    /**
     * 分页信息
     */
    private Page<?> page;
    

	/**
	 * @return the actionType
	 */
	public int getActionType() {
		return actionType;
	}

	/**
	 * @param actionType the actionType to set
	 */
	public void setActionType(int actionType) {
		this.actionType = actionType;
	}

	/**
	 * @return the page
	 */
	public Page<?> getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(Page<?> page) {
		this.page = page;
	}

	/**
	 * @return the coupons
	 */
	public List<CouponVo> getCoupons() {
		return coupons;
	}

	/**
	 * @param coupons the coupons to set
	 */
	public void setCoupons(List<CouponVo> coupons) {
		this.coupons = coupons;
	}

}
