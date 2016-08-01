package com.xiu.mobile.core.model;

import java.util.Date;

/**
 * 发行渠道
 * @author coco.long
 * @time	2015-07-29
 */
public class AppChannel {

	//渠道ID
	private Long id;
	//渠道名称
	private String name;
	//渠道编码
	private String code;
	//排序
	private int orderSeq;
	//状态：1.启动 0.停用
	private int status;
	//创建时间
	private Date createDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(int orderSeq) {
		this.orderSeq = orderSeq;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
