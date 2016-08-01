package com.xiu.mobile.portal.controller;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.mobile.core.exception.EIBusinessException;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.constants.OrderQueryTypeConstant;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.EncryptUtils;
import com.xiu.mobile.portal.common.util.ResponseUtil;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.ei.EIOrderManager;
import com.xiu.mobile.portal.model.CarryInfoVo;
import com.xiu.mobile.portal.model.OrderListInParam;
import com.xiu.mobile.portal.model.OrderListOutParam;
import com.xiu.mobile.portal.model.OrderSummaryOutParam;
import com.xiu.mobile.portal.model.OrderSummaryVo;
import com.xiu.mobile.portal.service.ILoginService;
import com.xiu.mobile.portal.service.IOrderDetailService;
import com.xiu.mobile.portal.service.IOrderListService;
import com.xiu.tc.common.orders.domain.BizOrderDO;

@Controller
@RequestMapping("/myorder")
public class MyOrderListController extends BaseController {
	
private Logger logger = Logger.getLogger(MyOrderListController.class);
	
	@Autowired
	private IOrderListService orderListService;
	@Autowired
	private ILoginService loginService;
	@Autowired
	private IOrderDetailService orderDetailService;
	@Autowired
	private EIOrderManager eiOrderManager;
	
	private int page = 1;
	private int pageSize = 300; //默认查300条

	// 供移动端接口使用
	private OrderListInParam getOrderListInParam(HttpServletRequest request,String tokenId, String queryType, int page, int pageSize) {
		OrderListInParam orderListInParam = new OrderListInParam();

		orderListInParam.setTokenId(tokenId);
		// 转换为查询全部订单（在全部订单中过滤各类订单）
		orderListInParam.setQueryType(queryType);
		orderListInParam.setPage(page);
		orderListInParam.setPageSize(pageSize);
		SessionUtil.setDeviceInfo(request, orderListInParam);

		return orderListInParam;
	}
 
	/**
	 * 获取订单列表
	 * queryType:0 	各订单个数
	 * queryType:1 	全部订单
	 * queryType:2 	待付款
	 * queryType:3 	待收货
	 * queryType:4 	撤销
	 * queryType:5 	待发货
	 * queryType:6  待评论
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/getOrderListRemote")
	public String getOrderList(String jsoncallback,HttpServletRequest request, HttpServletResponse response,
			String queryType, Integer page, Integer pageSize) {
		// 存储返回结果值
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		if(null != page){
			this.page = page;
		}
		if(null != pageSize){
			this.pageSize = pageSize;
		}
		
		try{
			// 检查登录
			String tokenId = SessionUtil.getTokenId(request);
			// 组装查询参数
			OrderListInParam orderListInParam = getOrderListInParam(request,tokenId, queryType, this.page, this.pageSize);
			
			if("6".equals(queryType)){
				List<OrderSummaryVo> waitCommentOrderList =this.orderListService.getWaitCommentOrderSummaryList(orderListInParam);
				// orderId加密(AES)
				for(OrderSummaryVo order:waitCommentOrderList){
					String aesKey=EncryptUtils.getAESKeySelf();
					String orderId=EncryptUtils.encryptOrderIdByAES(order.getOrderId(), aesKey);
					order.setOrderId(orderId);
				}
				
				model.put("result", true);
				model.put("errorCode", ErrorCode.Success.getErrorCode());
				model.put("errorMsg", ErrorCode.Success.getErrorMsg());
				model.put("listData", waitCommentOrderList);
				 
			}else{
				OrderListOutParam orderListOutParam = this.orderListService.getOrderListOutParam(orderListInParam);
				if (orderListOutParam.isSuccess()) {					
					// orderId加密(AES)
					for(OrderSummaryOutParam order:orderListOutParam.getOrderList()){
						String aesKey=EncryptUtils.getAESKeySelf();
						String orderId = EncryptUtils.encryptOrderIdByAES(order.getOrderId(), aesKey);
						order.setOrderId(orderId);
					}

					model.put("result", true);
					model.put("errorCode", ErrorCode.Success.getErrorCode());
					model.put("errorMsg", ErrorCode.Success.getErrorMsg());
					if (OrderQueryTypeConstant.KIND_OF_ORDER_COUNT.equals(queryType)) {
						model.put("allOrdersCount", orderListService.getAllOrderListCount(orderListOutParam));
						model.put("delivedOrdersCount", orderListService.getDelivedOrderListCount(orderListOutParam));
						model.put("delayPayOrdersCount", orderListService.getDelayPayOrderListCount(orderListOutParam));
						model.put("delayDelivedOrdersCount",orderListService.getDelayDeliveOrderListCount(orderListOutParam));
						OrderListInParam waitOrderListInParam =getOrderListInParam(request,tokenId, "6", this.page, this.pageSize);
						model.put("waitCommentOrdersCount", orderListService.getWaitCommentOrderListCount(waitOrderListInParam));
					} else if (OrderQueryTypeConstant.ALL_COUNT.equals(queryType)) {
						// 全部订单
						List<OrderSummaryVo> list =  orderListService.getAllOrderSummaryList(orderListOutParam);
						model.put("listData",list);
						
					} else if (OrderQueryTypeConstant.DELAY_PAY_COUNT.equals(queryType)) {
						// 待付款订单
						model.put("listData", orderListService.getDelayPayOrderSummaryList(orderListOutParam));
					} else if (OrderQueryTypeConstant.DELAY_DELIVE_COUNT.equals(queryType)) {
						// 待发货
						model.put("listData", orderListService.getDelayDeliveOrderSummaryList(orderListOutParam));
					} else if (OrderQueryTypeConstant.DELIVED_COUNT.equals(queryType)) {
						// 待收货
						model.put("listData", orderListService.getDelivedOrderSummaryList(orderListOutParam));
					} else {
						model.clear();
						model.put("result", false);
						model.put("errorCode", ErrorCode.ParamsError.getErrorCode());
						model.put("errorMsg", ErrorCode.ParamsError.getErrorMsg());
					}
				}
			}
		}catch(EIBusinessException e){
			model.put("result", false);
			model.put("errorCode", ErrorCode.GetOrderDataFailed.getErrorCode());
			model.put("errorMsg", ErrorCode.GetOrderDataFailed.getErrorMsg());
			logger.error("获取订单列表发生异常:" + e.getMessageWithSupportCode(),e);
		}catch(Exception ex){
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取订单列表发生异常：",ex);
		}
		ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback, model));
		return null;
	}
	
	/**
	 * 
	 * 物流详情
	 *  
	 */
	@ResponseBody
	@RequestMapping(value = "/getCarryInfoRemote")
	public String getCarryInfo(HttpServletRequest request,HttpServletResponse response,String jsoncallback,
			Integer page,Integer pageSize,String skuCode) {
		String oId=request.getParameter("orderId");
		// 存储返回结果值
		Map<String, Object> model = new LinkedHashMap<String, Object>();
//		if(null != page){
//			this.page = page;
//		}
//		if(null != pageSize){
//			this.pageSize = pageSize;
//		}
		try {
//			String tokenId = SessionUtil.getTokenId(request);
			//判断当前用户查询的订单是否是自己的订单
//			OrderListInParam orderListInParam = getOrderListInParam(request,tokenId, OrderQueryTypeConstant.ALL_COUNT, this.page, this.pageSize);
			// orderId解密(AES)
			String aesKey=EncryptUtils.getAESKeySelf();
			oId=EncryptUtils.decryptOrderIdByAES(oId, aesKey);
			Integer orderId = Integer.valueOf(oId);
			
			BizOrderDO order = eiOrderManager.queryOrderBaseInfo(orderId);
			//检查该用户查询的时候是自己订单的物流信息
	        if(null != order && order.getBuyerId().equals(Long.valueOf(SessionUtil.getUserId(request)))){
				List<CarryInfoVo> carryInfoVos = orderDetailService.getCarryInfos(orderId, skuCode);
				if (carryInfoVos != null) {
					model.put("result", true);
					model.put("errorCode", ErrorCode.Success.getErrorCode());
					model.put("errorMsg", ErrorCode.Success.getErrorMsg());
					model.put("carryInfos", carryInfoVos);
				} else {
					model.put("result", false);
					model.put("errorCode", ErrorCode.CarryInfoIsNull.getErrorCode());
					model.put("errorMsg", ErrorCode.CarryInfoIsNull.getErrorMsg());
				}
			}
		} catch(EIBusinessException e){
			model.put("result", false);
			model.put("errorCode", ErrorCode.CarryInfoNotBelongToUser.getErrorCode());
			model.put("errorMsg", ErrorCode.CarryInfoNotBelongToUser.getErrorMsg());
			logger.error("获取物流信息发生异常:" + e.getMessageWithSupportCode(),e);
		}catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取物流信息发生异常：" + e.getMessage(),e);
		}
		ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback, model));
		return null;
	}
		
}
