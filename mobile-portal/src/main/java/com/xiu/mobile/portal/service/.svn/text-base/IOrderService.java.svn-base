package com.xiu.mobile.portal.service;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.PayForTemplet;
import com.xiu.mobile.portal.model.AddressListQueryInParam;
import com.xiu.mobile.portal.model.AddressVo;
import com.xiu.mobile.portal.model.CalculateOrderBo;
import com.xiu.mobile.portal.model.CancelOrderVO;
import com.xiu.mobile.portal.model.CheckRepeatedRespVo;
import com.xiu.mobile.portal.model.OrderDetailOutParam;
import com.xiu.mobile.portal.model.OrderGoodsVO;
import com.xiu.mobile.portal.model.OrderInvoiceVO;
import com.xiu.mobile.portal.model.OrderListInParam;
import com.xiu.mobile.portal.model.OrderPayConfig;
import com.xiu.mobile.portal.model.OrderReqVO;
import com.xiu.mobile.portal.model.OrderResVO;
import com.xiu.mobile.portal.model.OrderSummaryOutParam;
import com.xiu.mobile.portal.model.PayInfoVO;
import com.xiu.mobile.portal.model.PayMethodInParam;
import com.xiu.mobile.portal.model.PayReqVO;
import com.xiu.tc.common.orders.domain.OrderSysConfig;


public interface IOrderService {

	/**
	 * 该方法已废弃，请使用IAddressService接口中的方法
	 * @param addressListQuery
	 * @return
	 */
	AddressVo getOrderAddress(AddressListQueryInParam addressListQuery);

	/**
	 * 计算订单
	 * 
	 * @param request
	 * @param orderReqVO
	 * @return calculateOrderResVO
	 */
	CalculateOrderBo calcOrder(OrderReqVO orderReqVO) throws Exception;
	
	/**
	 * 查询订单包装商品
	 * @return
	 */
	OrderGoodsVO getOrderPackagingGoods();

	/**
	 * 创建订单
	 * 
	 * @param request
	 * @param orderReqVO
	 * @param activeId
	 * @param cpsFromId
	 * @return OrderResVO
	 */
	OrderResVO createOrder(OrderReqVO orderReqVO,String activeId,String cpsFromId) throws Exception;

	/**
	 * 支付
	 * 
	 * @param request
	 * @param orderResVO
	 * @return PayInfoVO
	 * @throws Exception 
	 */
	PayInfoVO pay(PayReqVO payReqVO) throws Exception;

	/**
	 * 撤单
	 * 
	 * @param cancelOrderVO
	 * @return Result
	 */
	public boolean cancelOrder(CancelOrderVO cancelOrderVO);
	
	/**
	 * 查看当前订单是否重复，防止重复提交订单
	 * @param orderListInParam
	 * @param skuCode
	 * @return boolean
	 */
	public CheckRepeatedRespVo checkIsRepeatedOrder(OrderListInParam orderListInParam, String skuCode, int quantity,
			String addressId,String orderAmount)throws Exception;
	
	
	/**
	 * 更新支付方式
	 * @param payMethodInParam
	 * @return
	 */
	public boolean updatePayMethod(PayMethodInParam payMethodInParam);
	

	/**
	 * 判断商品是否能使用优惠券
	 * @param goodsSn
	 * @return
	 * @throws Exception 
	 */
	public boolean canUserCoupon(String goodsSn) throws Exception;
	
	/**
	 * 判断商品是否能使用优惠券 批量
	 * @param goodsSn
	 * @return
	 * @throws Exception 
	 */
	public Map<String,Boolean> canUserCouponBatchs(String goodsSns) throws Exception;

	/**
	 * 根据orderId获得订单信息
	 * @param orderId
	 * @param payMedium
	 * @return
	 * @throws Exception 
	 */
	public PayReqVO queryOrderBaseInfo(int orderId, String payMedium) throws Exception;

	/**
	 * 更新已发货订单为已完结状态
	 * @param orderId
	 * @param userId
	 * @return
	 */
	public boolean updateTradeEndStatus(String orderId, String userId)throws Exception;

	/**
	 * 计算购物车商品价格和促销信息
	 * @param orderReqVO
	 * @return
	 * @throws Exception
	 */
	public CalculateOrderBo calcShoppingCartGoods(OrderReqVO orderReqVO)
			throws Exception;
	
	/**
	 * 根据订单ID删除订单
	 * @param orderId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean deleteOrder(String orderId, String userId) throws Exception;
	
	/**
	 * 查村找朋友代付模板列表
	 * @param map
	 * @return
	 */
	public List<PayForTemplet> getPayForTempletList(Map map);
	
	/**
	 * 查询找朋友代付模板数量
	 * @param map
	 * @return
	 */
	public int getPayForTempletCount(Map map);
	
	/**
	 * 查询用户购买商品数量
	 * @param map
	 * @return
	 */
	public int getUserBuyGoodsCount(Map map);
	
	/**
	 * 查询订单的限购信息
	 * @param map
	 * @return
	 */
	public Map getOrderLimitSaleInfo(Map map);
	
	/**
	 * 查询发票类型列表
	 * @param map
	 * @return
	 */
	public List<OrderInvoiceVO> getInvoiceTypeList(Map map);
	
	/**
	 * 根据订单金额获取订单支付配置(未生成订单前)
	 * @return
	 */
	public OrderPayConfig getOrderPayConfig(double orderAmount);
	
	/**
	 * 根据订单信息获取订单下次支付配置（生成订单后）
	 * @param orderDetailOutParam
	 * @return
	 */
	public OrderPayConfig getOrderPayConfig(OrderDetailOutParam orderDetailOutParam);
	
	/**
	 * 根据订单信息获取订单下次支付配置（生成订单后）
	 * @param isMutilPay
	 * @param notAmount
	 * @param sysConfig
	 * @return
	 */
	public OrderPayConfig getOrderPayConfig(OrderSummaryOutParam outParam, OrderSysConfig sysConfig);
}
