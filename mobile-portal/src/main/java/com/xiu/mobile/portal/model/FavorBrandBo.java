package com.xiu.mobile.portal.model;

import java.util.Date;

/** 
 * @ClassName:FavorBrandBo
 * @Description: TODO favor_brand
 * @author: wangchengqun
 * @date 2014-5-30
 *  
 */
public class FavorBrandBo {

	/**
	 * 收藏Id
	 */
	private Long id;
	/**
	 * 用户Id
	 */
	private Long userId;
	/**
	 * 品牌Id
	 */
	private Long brandId;
	/**
	 * 收藏时间
	 */
	private Date createDate;
	/**
	 * 收藏方式
	 */
	private int terminal;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getBrandId() {
		return brandId;
	}
	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getTerminal() {
		return terminal;
	}
	public void setTerminal(int terminal) {
		this.terminal = terminal;
	}
	
}
