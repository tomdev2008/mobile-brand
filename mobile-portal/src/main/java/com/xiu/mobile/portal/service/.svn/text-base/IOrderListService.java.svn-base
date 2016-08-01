package com.xiu.mobile.portal.service;

import java.util.List;

import com.xiu.common.lang.PagingList;
import com.xiu.mobile.portal.model.OrderListInParam;
import com.xiu.mobile.portal.model.OrderListOutParam;
import com.xiu.mobile.portal.model.OrderStatisticsVo;
import com.xiu.mobile.portal.model.OrderSummaryVo;
import com.xiu.mobile.portal.model.PageVo;
import com.xiu.tc.common.orders.domain.BizOrderDO;


/**
 * 
 * @ClassName: IOrderListService
 * @Description: 查询订单列表
 * 
 */
public interface IOrderListService {

	/**
	 * 查询订单列表接口输出参数
	 * 
	 * @param orderListInParams
	 * @author: Hualong
	 * @date: 2013-5-3 11:56:03
	 */
	public OrderListOutParam getOrderListOutParam(OrderListInParam orderListInParam);

	/**
	 * 各类订单数统计
	 * 
	 * @param orderListOutParam 全部订单查询输出参数
	 * @author: Hualong
	 * @date: 2013-5-3 03:40:10
	 */
	public OrderStatisticsVo getOrderStatistics(OrderListOutParam orderListOutParam)throws Exception;

	/**
	 * 查询所有订单摘要信息
	 * 
	 * @Title: getOrderSummaryList
	 * @param allOrderListOutParam
	 * @author: Hualong
	 * @date: 2013-5-3 03:40:50
	 */
	public List<OrderSummaryVo> getAllOrderSummaryList(OrderListOutParam allOrderListOutParam)throws Exception;

	/**
	 * 查询待付款订单摘要信息
	 * 
	 * @param allOrderListOutParam
	 * @author: Hualong
	 * @date: 2013-5-3 03:40:50
	 */
	public List<OrderSummaryVo> getDelayPayOrderSummaryList(OrderListOutParam allOrderListOutParam)throws Exception;

	/**
	 * 查询待发货的订单摘要列表
	 * 
	 * @Title: getDelayDeliveOrderSummaryList
	 * @param allOrderListOutParam 查询全部订单接口的输出参数
	 * @author: Hualong
	 * @date: 2013-5-6下午10:43:00
	 */
	public List<OrderSummaryVo> getDelayDeliveOrderSummaryList(OrderListOutParam allOrderListOutParam)throws Exception;

	/**
	 * 查询已发货订单摘要信息
	 * 
	 * @param allOrderListOutParam
	 * @author: Hualong
	 * @date: 2013-5-3 03:40:50
	 */
	public List<OrderSummaryVo> getDelivedOrderSummaryList(OrderListOutParam allOrderListOutParam)throws Exception;

	/**
	 * 订单分页信息
	 * 
	 * @param orderListOutParam
	 * @author: Hualong
	 * @date: 2013-5-3 03:41:03
	 */
	public PageVo getPageVo(OrderListOutParam orderListOutParam);

	/**
	 * 获取所有订单总数
	 * 
	 * @param allOrderListOutParam
	 */
	int getAllOrderListCount(OrderListOutParam allOrderListOutParam);

	/**
	 * 查询待发货的订单数
	 * 
	 * @param allOrderListOutParam 
	 */
	int getDelayDeliveOrderListCount(OrderListOutParam allOrderListOutParam);

	/**
	 * 查询待付款订单数
	 * 
	 * @param allOrderListOutParam
	 */
	int getDelayPayOrderListCount(OrderListOutParam allOrderListOutParam);

	/**
	 * 查询已发货订单摘要信息
	 * 
	 * @param allOrderListOutParam
	 */
	int getDelivedOrderListCount(OrderListOutParam allOrderListOutParam);

	/**
	 * 待评论
	 * @param orderListOutParam
	 * @return
	 */
	public List<OrderSummaryVo> getWaitCommentOrderSummaryList(
			OrderListInParam orderListInParam)throws Exception;

	/**
	 * 待评论数量
	 * @param orderListOutParam
	 * @return
	 */
	public int getWaitCommentOrderListCount(
			OrderListInParam orderListInParam)throws Exception;
	
	

	/**
	 * 能够申请售后服务的列表
	 * @param orderListInParam
	 * @return
	 * @throws Exception
	 */
	public List<OrderSummaryVo> getCanBeRefundOrderList(
			OrderListInParam orderListInParam,PagingList<BizOrderDO> page )throws Exception;
	

}
