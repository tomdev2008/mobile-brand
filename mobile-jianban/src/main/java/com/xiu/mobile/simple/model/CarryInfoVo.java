package com.xiu.mobile.simple.model;

/**
 * 
 * @ClassName: CarryInfoVo
 * @Description: 物流信息
 * @author: wenxiaozhuo
 * @date 2013-4-2
 * 
 */
public class CarryInfoVo {

	// 订单详情Id
	private long orderDetailId;

	// 发货时间
	private String carryTime;

	// 发货状态
	private String status;

	// 发货地址
	private String address;

	// 物流详情
	private String detail;

	public long getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(long orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public String getCarryTime() {
		return carryTime;
	}

	public void setCarryTime(String carryTime) {
		this.carryTime = carryTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
}
