package com.xiu.mobile.portal.ei.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.csc.facade.ProductServiceFacade;
import com.xiu.csc.facade.dto.OrderInfoDTO;
import com.xiu.csc.facade.dto.ProductServiceInfo;
import com.xiu.csc.facade.util.Result;
import com.xiu.mobile.portal.ei.EICSCManager;

/**
 * 求购CSC
 * @author coco.long
 * @time	2015-08-26
 */
@Service("eiCSCManager")
public class EICSCManagerImpl implements EICSCManager {
	
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(EICSCManagerImpl.class);

	@Autowired
	private ProductServiceFacade productServiceFacade;
	
	/**
	 * 添加求购订单
	 */
	public Map<String, Object> insertProductService(Map map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String productType = (String) map.get("productType");
		String brandName = (String) map.get("brandName");
		String sex = (String) map.get("sex");
		String productNumber = (String) map.get("productNumber");
		String referenceUrl = (String) map.get("referenceUrl");
		String productDesc = (String) map.get("productDesc");
		String mobile = (String) map.get("mobile");
		List<String> picList = (List<String>) map.get("picList");
		String userId = (String) map.get("userId");
		String terminal = (String) map.get("terminal");
		String goodsSn = (String) map.get("goodsSn");
		
		ProductServiceInfo productService = new ProductServiceInfo();
		productService.setSort(productType);	//产品分类
		productService.setBrand(brandName);		//品牌名称
		productService.setProductType(sex);		//适用性别
		productService.setProductNumber(productNumber);		//产品货号
		productService.setReferenceArdress(referenceUrl);	//参考网站
		productService.setProductDesc(productDesc);			//详细描述
		productService.setMobile(mobile);					//联系方式
		productService.setUserId(userId); 					//用户ID
		productService.setTerminal(terminal); 				//终端类型
		productService.setXiuId(goodsSn); 					//商品走秀码
		
		if(picList != null && picList.size() > 0) {
			//如果图片不为空
			for(int i = 0; i < picList.size(); i++) {
				if(i == 0) {
					productService.setPic1(picList.get(i));
				} else if(i == 1) {
					productService.setPic2(picList.get(i));
				} else if(i == 2) {
					productService.setPic3(picList.get(i));
				} else if(i == 3) {
					productService.setPic4(picList.get(i));
				} else if(i == 4) {
					productService.setPic5(picList.get(i));
				} else if(i == 5) {
					productService.setPic6(picList.get(i));
				} 
			}
		}
		
		try {
			Result result = productServiceFacade.insertProductService(productService);
			if(result != null) {
				if("1".equals(result.getSuccess())) {
					//成功
					resultMap.put("result", true);
				} else {
					resultMap.put("result", false);
					resultMap.put("errorCode", result.getErrorCode());
					resultMap.put("errorMessage", result.getErrorMessage());
				}
			} else {
				resultMap.put("result", false);
			}
		} catch(Exception e) {
			LOGGER.error("添加求购订单，错误信息:" + e.getMessage());
			resultMap.put("result", false);
		}
		
		return resultMap;
	}

	/**
	 * 查询用户订单列表
	 */
	public List<OrderInfoDTO> getUserOrdersByUserId(Long userId, int startRow, int endRow) {
		List<OrderInfoDTO> resultList = new ArrayList<OrderInfoDTO>();
		try {
			Result result = productServiceFacade.getUserOrdersByUserId(userId, startRow, endRow);
			
			if(result != null && "1".equals(result.getSuccess())) {
				resultList = (List<OrderInfoDTO>) result.getData();
			}
		} catch(Exception e) {
			LOGGER.error("查询用户求购订单失败，错误信息:" + e.getMessage());
		}
		
		return resultList;
	}
	
	/**
	 * 查询用户订单数量
	 */
	public int getUserOrderCount(Long userId) {
		int count = 0;
		try{
			Result result = productServiceFacade.countOrdersByUserId(userId);
			if(result != null && "1".equals(result.getSuccess())) {
				count = Integer.parseInt(result.getData().toString());
			}
		} catch(Exception e) {
			LOGGER.error("查询用户求购订单数量失败，错误信息:" + e.getMessage());
		}
		return count;
	}

	/**
	 * 根据订单ID查询订单详情
	 */
	public OrderInfoDTO getUsersOrderInfoByOrderId(Long orderId) {
		OrderInfoDTO orderInfo = null;
		
		try {
			Result result = productServiceFacade.getUsersOrderInfoByOrderId(orderId);
			if(result != null && "1".equals(result.getSuccess())) {
				List<OrderInfoDTO> orderList = (List<OrderInfoDTO>) result.getData();
				if(orderList != null && orderList.size() > 0) {
					orderInfo = orderList.get(0);
				}
			}
		} catch(Exception e) {
			LOGGER.error("根据订单ID查询求购订单详情失败，错误信息:" + e.getMessage());
		}
		return orderInfo;
	}

	/**
	 * 查询最新的订单
	 */
	public List<OrderInfoDTO> getUsersNearOrders() {
		List<OrderInfoDTO> resultList = new ArrayList<OrderInfoDTO>();
		
		try {
			Result result = productServiceFacade.getUsersNearOrders();
			
			if(result != null && "1".equals(result.getSuccess())) {
				resultList = (List<OrderInfoDTO>) result.getData();
			}
		} catch(Exception e) {
			LOGGER.error("查询最新求购订单失败，错误信息:" + e.getMessage());
		}
		
		return resultList;
	}

	/**
	 * 根据订单ID删除求购订单
	 */
	public Map<String, Object> deleteOrder(Long orderId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			Result result = productServiceFacade.deleteUsersOrderInfoByOrderId(orderId);
			
			if(result != null) {
				if("1".equals(result.getSuccess())) {
					//成功
					resultMap.put("result", true);
				} else {
					resultMap.put("result", false);
					resultMap.put("errorCode", result.getErrorCode());
					resultMap.put("errorMessage", result.getErrorMessage());
				}
			} else {
				resultMap.put("result", false);
			}
		} catch(Exception e) {
			LOGGER.error("根据订单ID删除求购订单失败，错误信息:" + e.getMessage());
		}
		
		return resultMap;
	}

}
