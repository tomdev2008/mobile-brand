package com.xiu.mobile.portal.service.impl;

import com.xiu.mobile.portal.common.constants.LpStatusConstant;
import com.xiu.mobile.portal.common.constants.OrderStatus;
import com.xiu.mobile.portal.common.constants.OrderStatusConstant;
import com.xiu.mobile.portal.common.constants.PayStatusConstant;
import com.xiu.mobile.portal.common.constants.PayTypeConstant;



public class ComputeOrderStatus {

	//mportal旧的订单状态组装
	public OrderStatus getOrderStatus(String payType, String payStatus, String lpStatus, String status) {

		// 订单状态：
		int iStatus = Integer.valueOf(status);
		// 物流状态
		int iLpStatus = Integer.valueOf(lpStatus);
		if (!PayTypeConstant.COD.equals(payType)) {
			// 支付状态：0-未到账 ,1-已到账,-1-已退款
			int iPayStatus = Integer.valueOf(payStatus);

			if (PayStatusConstant.NOT_ARRIVE_ACCOUNT == iPayStatus) {
				// 未审核、已审核、待复核 且 未到账 --> 待付款
				if (OrderStatusConstant.ORDER_NOT_CHECK == iStatus || OrderStatusConstant.ORDER_CHECKED == iStatus
						|| OrderStatusConstant.ORDER_NOT_RECHECK == iStatus) {
					return OrderStatus.DELAY_PAY;
				}
			}

			if (PayStatusConstant.ARRIVED_ACCOUNT == iPayStatus) {
				// 未审核、待复核 且 已到账 --> 待审核
				if (OrderStatusConstant.ORDER_NOT_CHECK == iStatus || OrderStatusConstant.ORDER_NOT_RECHECK == iStatus) {
					return OrderStatus.DELAY_CHECK;
				}
				// 已审核 且 已到账
				if (OrderStatusConstant.ORDER_CHECKED == iStatus) {
					if (LpStatusConstant.DELAY_DELIVE == iLpStatus || LpStatusConstant.TO_DELAY_DELIVE == iLpStatus) {
						return OrderStatus.DELAY_DELIVE;
					} else if (LpStatusConstant.PART_DELIVED == iLpStatus) {
						return OrderStatus.PART_DELIVERED;
					} else if (LpStatusConstant.DELIVED == iLpStatus) {
						return OrderStatus.DELIVERED;
					}
				}
			}
		} else {// COD 货到付款
			// 未审核 或者 待复核 --> 待审核
			if (OrderStatusConstant.ORDER_NOT_CHECK == iStatus || OrderStatusConstant.ORDER_NOT_RECHECK == iStatus) {
				return OrderStatus.DELAY_CHECK;
			}
			// 已审核 --> 备货中
			if (OrderStatusConstant.ORDER_CHECKED == iStatus) {
				if (LpStatusConstant.DELAY_DELIVE == iLpStatus || LpStatusConstant.TO_DELAY_DELIVE == iLpStatus) {
					return OrderStatus.DELAY_DELIVE;
				} else if (LpStatusConstant.PART_DELIVED == iLpStatus) {
					return OrderStatus.PART_DELIVERED;
				} else if (LpStatusConstant.DELIVED == iLpStatus) {
					return OrderStatus.DELIVERED;
				}
			}

		}

		// 其余通用状态
		switch (iStatus) {
		case OrderStatusConstant.ORDER_REPEALED:
			return OrderStatus.REPEALED;
		case OrderStatusConstant.ORDER_NOT_PASS:
			return OrderStatus.CHECK_NOT_PASS;
		case OrderStatusConstant.ORDER_TRADED:
			return OrderStatus.TRADED_COMPLETED;
		case OrderStatusConstant.ORDER_END:
			return OrderStatus.ORDER_COMPLETED;
		}
		//如果没有判断出订单类型，就默认订单审核不通过，防止返回为null的情况
		return OrderStatus.CHECK_NOT_PASS;
	}
	public OrderStatus getOrderStatus(String status){
		if(OrderStatus.ORDER_WAITING_VERIFY.getDesc().equals(status)){
			return OrderStatus.ORDER_WAITING_VERIFY;
		}else if(OrderStatus.ORDER_VERIFY_NOT_PASS.getDesc().equals(status)){
			return OrderStatus.ORDER_VERIFY_NOT_PASS;
		}else if(OrderStatus.ORDER_WAITING_PAY.getDesc().equals(status)){
			return OrderStatus.ORDER_WAITING_PAY;
		}else if(OrderStatus.ORDER_PACKAGEING.getDesc().equals(status)){
			return OrderStatus.ORDER_PACKAGEING;
		}else if(OrderStatus.ORDER_DELIVER_PART.getDesc().equals(status)){
			return OrderStatus.ORDER_DELIVER_PART;
		}else if(OrderStatus.ORDER_DELIVERED.getDesc().equals(status)){
			return OrderStatus.ORDER_DELIVERED;
		}else if(OrderStatus.ORDER_CANCEL.getDesc().equals(status)){
			return OrderStatus.ORDER_CANCEL;
		}else if(OrderStatus.ORDER_TRADE_CLOSE.getDesc().equals(status)){
			return OrderStatus.ORDER_TRADE_CLOSE;
		}else if(OrderStatus.ORDER_CLOSE.getDesc().equals(status)){
			return OrderStatus.ORDER_CLOSE;
		}else{
			return null;
		}
	}
}
