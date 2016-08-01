package com.xiu.mobile.portal.ei;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.xiu.csc.facade.dto.OrderInfoDTO;


/**
 * CSCManager
 * @author coco.long
 * @time	2015-08-24
 */
@Service("eiCSCManager")
public interface EICSCManager {

	//添加求购订单
	public Map<String, Object> insertProductService(Map map);
	
	//查询用户订单列表
	public List<OrderInfoDTO> getUserOrdersByUserId(Long userId, int startRow, int endRow);
	
	//查询用户订单数量
	public int getUserOrderCount(Long userId);
	
	//根据订单ID查询订单详情
	public OrderInfoDTO getUsersOrderInfoByOrderId(Long orderId);
	
	//查询最新的订单
	public List<OrderInfoDTO> getUsersNearOrders();
	
	//根据订单ID删除求购订单
	public Map<String, Object> deleteOrder(Long orderId);
}
