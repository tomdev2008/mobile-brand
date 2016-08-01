package com.xiu.mobile.portal.service;

import java.util.List;

import com.xiu.mobile.portal.model.CarryInfoVo;
import com.xiu.mobile.portal.model.CommoSummaryVo;
import com.xiu.mobile.portal.model.OrderBaseInfoVo;
import com.xiu.mobile.portal.model.OrderDetailInParam;
import com.xiu.mobile.portal.model.OrderDetailOutParam;
import com.xiu.mobile.portal.model.OrderListInParam;
import com.xiu.mobile.portal.model.ReceiverInfoVo;


/**
 * 
 * @ClassName: IOrderDetailService
 * @Description: 查询订单详情
 * 
 */
public interface IOrderDetailService {

	/**
	 * 
	 * @Title: getOrderDetailOutParam
	 * @Description: 查询订单详情接口输出参数
	 * @param orderDetailInParam
	 * @return
	 * @throws
	 * @author: Hualong
	 * @date: 2013-5-3 03:46:39
	 */
	public OrderDetailOutParam getOrderDetailOutParam(OrderDetailInParam orderDetailInParam);

	/**
	 * 
	 * @Title: getOrderDetailBaseInfo
	 * @Description: 查询订单详情基本信息部分。
	 * @param orderDetailOutParam
	 * @return
	 * @throws
	 * @author: Hualong
	 * @date: 2013-5-3 03:46:54
	 */
	public OrderBaseInfoVo getOrderBaseInfo(OrderDetailOutParam orderDetailOutParam)throws Exception;

	/**
	 * 
	 * @Title: getReceiverInfo
	 * @Description:查询订单收货人信息
	 * @param orderDetailOutParam
	 * @return
	 * @throws
	 * @author: Hualong
	 * @date: 2013-5-3 04:08:13
	 */
	public ReceiverInfoVo getReceiverInfo(OrderDetailOutParam orderDetailOutParam);

	/**
	 * 
	 * @Title: getCommoSummaryList
	 * @Description: 查询订单商品摘要信息
	 * @param orderDetailOutParam
	 * @return
	 * @throws
	 * @author: Hualong
	 * @date: 2013-5-3 04:08:17
	 */
	public List<CommoSummaryVo> getCommoSummaryList(OrderDetailOutParam orderDetailOutParam);

	/**
	 * 
	 * @Title: getCarryInfo
	 * @Description: 查询物流信息
	 * @param orderId 订单Id
	 * @param skuCode 商品skuCode
	 * @return
	 * @throws
	 * @author: Hualong
	 * @date: 2014-4-24
	 */
	public List<CarryInfoVo> getCarryInfos(Integer orderId, String skuCode) throws Exception;
	/**
	 * 查询是否是当前用户的orderId
	 * 
	 */
	public boolean checkOrderId(OrderListInParam orderListInParam,Integer orderId);
}
