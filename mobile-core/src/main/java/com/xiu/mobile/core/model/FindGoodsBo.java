package com.xiu.mobile.core.model;

import java.util.Date;
/** 
 * @ClassName:FindGoodsBo
 * @Description: TODO xiu_wap.find_goods
 * @author: wangchengqun
 * @date 2014-6-4
 *  
 */

public class FindGoodsBo {
	/**
	 * 发现Id
	 */
	private Long id;
	/**
	 * 走秀码
	 */
	private String goodsSn;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 推荐语
	 */
	private String content;
	/**
	 * 开始时间
	 */
	private Date startDate;
	/**
	 * 结束时间
	 */
	private Date endDate;
	/**
	 * 排序
	 */
	private Long orderSequence;
	/**
	 *添加时间
	 */
	private Date createDate;
	/**
	 * 添加人
	 */
	private Long createBy;
	/**
	 * 是否删除
	 */
	private String deleted;
	/**
	 * 是否替换
	 */
	private String replaced;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getGoodsSn() {
		return goodsSn;
	}
	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Long getOrderSequence() {
		return orderSequence;
	}
	public void setOrderSequence(Long orderSequence) {
		this.orderSequence = orderSequence;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getReplaced() {
		return replaced;
	}
	public void setReplaced(String replaced) {
		this.replaced = replaced;
	}
	
	

}
