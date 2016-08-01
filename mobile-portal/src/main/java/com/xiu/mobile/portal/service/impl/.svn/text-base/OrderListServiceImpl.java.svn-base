package com.xiu.mobile.portal.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.xiu.common.command.result.Result;
import com.xiu.common.lang.PagingList;
import com.xiu.mobile.core.utils.XiuUtil;
import com.xiu.mobile.portal.common.constants.OrderStatus;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.util.LogicUtil;
import com.xiu.mobile.portal.common.util.Tools;
import com.xiu.mobile.portal.constants.GlobalConstants;
import com.xiu.mobile.portal.ei.EIOrderManager;
import com.xiu.mobile.portal.ei.EISysParamManager;
import com.xiu.mobile.portal.model.CommoSummaryOutParam;
import com.xiu.mobile.portal.model.OrderListInParam;
import com.xiu.mobile.portal.model.OrderListOutParam;
import com.xiu.mobile.portal.model.OrderStatisticsVo;
import com.xiu.mobile.portal.model.OrderSummaryOutParam;
import com.xiu.mobile.portal.model.OrderSummaryVo;
import com.xiu.mobile.portal.model.PageVo;
import com.xiu.mobile.portal.service.IGoodsService;
import com.xiu.mobile.portal.service.IOrderListService;
import com.xiu.mobile.portal.service.IOrderService;
import com.xiu.tc.common.orders.domain.BizOrderDO;
import com.xiu.tc.common.orders.domain.DeliverAddressDO;
import com.xiu.tc.common.orders.domain.OrderDetailDO;
import com.xiu.tc.common.orders.domain.OrderSysConfig;
import com.xiu.tc.common.orders.domain.PayOrderDO;
import com.xiu.tc.common.orders.domain.QueryDO;
import com.xiu.tc.common.statusenum.OrderEnum;
import com.xiu.tc.orders.condition.QueryOrderCondition;

/**
 * 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 订单列表
 * @AUTHOR : chenghong.ding@xiu.com  
 * @DATE :2014年5月19日 上午10:38:02
 * </p>
 ****************************************************************
 */
@Service("orderListService")
public class OrderListServiceImpl extends ComputeOrderStatus implements IOrderListService {
	
	private static final XLogger logger = XLoggerFactory.getXLogger(OrderListServiceImpl.class);
	
	@Autowired
	private EIOrderManager eiOrderManager;
	@Autowired
	private EISysParamManager eiSysParamManager;
	
	@Autowired
	private IGoodsService goodsService;
	@Autowired
	IOrderService orderService;
	
	@SuppressWarnings("unchecked")
	@Override
	public OrderListOutParam getOrderListOutParam(
			OrderListInParam orderListInParam) {
		
		String userId = orderListInParam.getUserId();
		Assert.notNull(userId, "用户userId不能为空.");
		// 返回结果
		OrderListOutParam listOut = new OrderListOutParam();
		
		QueryDO queryDO = new QueryDO();
		// 查询类型
		queryDO.setOrderCase(orderListInParam.getQueryType());
		queryDO.setCaseTimeRange("x");
		//封装页面属性
		PagingList<BizOrderDO> page = new PagingList<BizOrderDO>();
		page.setCurrentPage(orderListInParam.getPage());
		page.setPageSize(orderListInParam.getPageSize());
		
		if (logger.isDebugEnabled()) {
			logger.debug("invoke remote interface [tc] bizOrderQueryServive.queryOrderForJava");
			logger.debug("userId : " + orderListInParam.getUserId());
			logger.debug("queryDO : " + queryDO);
			logger.debug("page : " + page);
		}
		
		Result orderResult = eiOrderManager.queryOrderList(Long.parseLong(userId), queryDO, page);
		
		// 成功后组装数据成接口输出需要的map
		page = (PagingList<BizOrderDO>) orderResult.getModels().get("pagingList");
		
		listOut.setRetCode(GlobalConstants.RET_CODE_SUCESSS);
		listOut.setCurrentPage(page.getCurrentPage().toString());
		listOut.setTotalPage(Integer.toString(page.getTotalPage()));
		
		
		List<BizOrderDO> items = (List<BizOrderDO>) orderResult.getModels().get("bizOrderDO");
		List<OrderSummaryOutParam> orderList = new ArrayList<OrderSummaryOutParam>();
		//查询订单多笔支付配置
		OrderSysConfig sysConfig = eiOrderManager.queryOrderSysConfigInfo();
		
		for (BizOrderDO bizOrder : items) {
			orderList.add(this.assembleOrderList(bizOrder, sysConfig));
		}
		listOut.setOrderList(orderList);
		return listOut;
	}
	
	/**
	 * 将订单中心的订单对象组装成 订单OrderSummaryOutParam
	 * @param bizOrder
	 * @return
	 */
	private OrderSummaryOutParam assembleOrderList(BizOrderDO bizOrder, OrderSysConfig config){
		OrderSummaryOutParam outParam = new OrderSummaryOutParam();
		outParam.setOrderId(Integer.toString(bizOrder.getOrderId()));
		outParam.setOrderNo(bizOrder.getOrderCode());
		outParam.setTotalPrice(XiuUtil.getPriceDouble2Str(bizOrder.getPayAmount() / 100.0));
		// 订单金额（折后的）
		long orderAmount = bizOrder.getPayAmount() - bizOrder.getDiscountFee1() - bizOrder.getDiscountFee2() - bizOrder.getDiscountFee3();
		outParam.setPrice(XiuUtil.getPriceDouble2Str(orderAmount / 100.0));
		outParam.setWhen(DateFormatUtils.format(bizOrder.getGmtCreate(), "yyyy-MM-dd HH:mm:ss"));
		//旧的订单状态
		outParam.setStatus(Integer.toString(bizOrder.getOrderStatus()));
		//新的订单状态
		outParam.setNewStatus(bizOrder.getLogisticsStatus());
		//最新的订单显示状态
		outParam.setShowStatusCode(bizOrder.getShowStatusCode()==null?100:bizOrder.getShowStatusCode());
		outParam.setShowStatusName(bizOrder.getShowStatusName()==null?"待支付":bizOrder.getShowStatusName());
		
		Integer showStatusCode = outParam.getShowStatusCode();	//订单状态
		if(showStatusCode.intValue() == OrderStatus.NEW_ORDER_CLOSE.getCode()) {
			//如果是订单完结状态，显示为交易完成
			outParam.setShowStatusName(OrderStatus.NEW_ORDER_TRADE_CLOSE.getDesc());
		}
		//支付记录
		outParam.setPayOrderList(bizOrder.getPayOrderList());
		
		outParam.setPaymentMethod(Tools.getPayTypeDesc(bizOrder.getPayType()));
		outParam.setPayType(bizOrder.getPayType());
		outParam.setLpStatus(Integer.toString(bizOrder.getLpStatus()));
		// 订单支付状态代码
		outParam.setPayStatus(Integer.toString(bizOrder.getPayStatus()));
		// 订单支付状态描述
		outParam.setPayStatusDesc(OrderEnum.PayStatus.getValueByKey(bizOrder.getPayStatus()));
		//多笔支付 start
		outParam.setIsMutilPay(bizOrder.getIsMutilPay());
		outParam.setConfirmPaidFee(XiuUtil.getPriceDouble2Str(bizOrder.getConfirmPaidFee() / 100.0));
		outParam.setNotAmount(XiuUtil.getPriceDouble2Str(bizOrder.getNotAmount() / 100.0));
		outParam.setOrderPayConfig(orderService.getOrderPayConfig(outParam, config));
		outParam.setDelayTime(bizOrder.getDelayTime());
		//多笔支付 end
		
		DeliverAddressDO da = bizOrder.getDeliverAddressDO();
		if (null != da) {
			outParam.setReceiver(da.getFullName());
			outParam.setDeliver(Integer.toString(da.getPostType())); // 配送方式
			// 订单列表里循环获取城市信息太多 屏蔽
//			StringBuffer sb = new StringBuffer();
//			String province = eiSysParamManager.getAddressDescByCode(da.getProv());
//			String city = eiSysParamManager.getAddressDescByCode(da.getCity());
//			String area = eiSysParamManager.getAddressDescByCode(da.getArea());
//
//			sb.append(province).append(" ").append(city).append(" ")
//					.append(area).append(" ").append(da.getAddress());
//			outParam.setAddress(sb.toString());
			// 这里返回的区域为区域编码 已加上省市前缀
			if(da.getArea()!=null){
				outParam.setAddress(da.getArea().concat(" ").concat(da.getAddress()));
			}
		}
				
		// 以下组装订单中的商品列表字段
		List<CommoSummaryOutParam> commoSummaryOutParamList = new ArrayList<CommoSummaryOutParam>();
		List<OrderDetailDO> orderDetails = bizOrder.getOrderItemList();
		boolean useProductPackaging = false;
		for (OrderDetailDO orderDetail : orderDetails) {
			CommoSummaryOutParam o = new CommoSummaryOutParam();
			o.setGoodsSn(orderDetail.getItemCodes());
			o.setGoodsName(orderDetail.getItemName());
			o.setGoodsId(Long.toString(orderDetail.getItemId()));
			o.setGoodsAmount(Integer.toString(orderDetail.getQuantity()));
			o.setZsPrice(XiuUtil.getPriceDouble2Str(orderDetail.getOriginalPrice()/100.0)); //走秀价
			o.setSkuCode(orderDetail.getSkuCode());
			o.setDiscountPrice(XiuUtil.getPriceDouble2Str(orderDetail.getSharePrice() / 100.0));
			// 商品属性
			o.setGoodsProperties(orderDetail.getPropertyAlias());
			// 图片url
			String imgUrl = orderDetail.getPicturePath();
			o.setGoodsImg(Tools.assembleImgUrlForApp(imgUrl));
			commoSummaryOutParamList.add(o);
			
			String giftPackType = orderDetail.getGiftPackType();
			if(StringUtils.isNotBlank(giftPackType) && giftPackType.equals("1")) {
				//如果存在包装类型的商品，则认为使用了礼品包装服务
				useProductPackaging = true;
			}
		}
		outParam.setGoodsList(commoSummaryOutParamList);
		outParam.setUseProductPackaging(useProductPackaging); //是否使用包装服务
		return outParam;
	}

	@Override
	public OrderStatisticsVo getOrderStatistics(
			OrderListOutParam orderListOutParam)throws Exception {
		OrderStatisticsVo orderStatisticsVo = new OrderStatisticsVo();
		if (LogicUtil.isNotNull(orderListOutParam) && LogicUtil.isNotNullAndEmpty(orderListOutParam.getOrderList())) {
			// 全部订单
			orderStatisticsVo.setAllCount(String.valueOf(this.getAllOrderSummaryList(orderListOutParam).size()));
			// 已发货
			orderStatisticsVo.setDelivedCount(String.valueOf(this.getDelivedOrderSummaryList(orderListOutParam).size()));
			// 待付款
			orderStatisticsVo.setDelayPayCount(String.valueOf(this.getDelayPayOrderSummaryList(orderListOutParam).size()));
			// 待发货
			orderStatisticsVo.setDelayDeliveCount(String.valueOf(this.getDelayDeliveOrderSummaryList(orderListOutParam).size()));
		}
		return orderStatisticsVo;
	}

	@Override
	public List<OrderSummaryVo> getAllOrderSummaryList(
			OrderListOutParam allOrderList) throws Exception{
		List<OrderSummaryVo> orderSummaryList = new ArrayList<OrderSummaryVo>();

		if (LogicUtil.isNotNull(allOrderList) && LogicUtil.isNotNullAndEmpty(allOrderList.getOrderList())) {
			for (OrderSummaryOutParam orderSummaryOutParam : allOrderList.getOrderList()) {
				orderSummaryList.add(convert2OrderSummaryVo(orderSummaryOutParam));
			}
		}

		return orderSummaryList;
	}
	
	private OrderSummaryVo convert2OrderSummaryVo(OrderSummaryOutParam orderSummaryOutParam)throws Exception {
		OrderSummaryVo orderSummaryVo = new OrderSummaryVo();

		if (LogicUtil.isNotNull(orderSummaryOutParam)) {
			orderSummaryVo.setOrderId(orderSummaryOutParam.getOrderId());
			orderSummaryVo.setOrderNo(orderSummaryOutParam.getOrderNo());
			orderSummaryVo.setPrice(orderSummaryOutParam.getPrice());
			orderSummaryVo.setWhen(orderSummaryOutParam.getWhen());
			//是否禁止评论  0 不禁止  1 禁止
			SimpleDateFormat formatDate = new SimpleDateFormat(GlobalConstants.DATE_FROMAT);
			Date targetDate,orderDate;
			targetDate = formatDate.parse(GlobalConstants.DATE_2013);
	        orderDate = formatDate.parse(orderSummaryOutParam.getWhen());
			if (orderDate.before(targetDate)){
				orderSummaryVo.setForbidComment(1);
			}

			//mportal旧订单状态-旧
			OrderStatus ot = getOrderStatus(orderSummaryOutParam.getPayType(), orderSummaryOutParam.getPayStatus(),
					orderSummaryOutParam.getLpStatus(), orderSummaryOutParam.getStatus());
			orderSummaryVo.setStatusCode(ot.getCode());
			orderSummaryVo.setStatus(ot.getDesc());
			//从osc中获得订单状态-新
			OrderStatus newOs = getOrderStatus(orderSummaryOutParam.getNewStatus());
			orderSummaryVo.setNewStatusCode(newOs.getCode());
			orderSummaryVo.setNewStatus(newOs.getDesc());
			
			//从osc中获得订单显示状态描述	-最新
			orderSummaryVo.setShowStatusCode(orderSummaryOutParam.getShowStatusCode());
			orderSummaryVo.setShowStatusName(orderSummaryOutParam.getShowStatusName());
			
			orderSummaryVo.setPayType(orderSummaryOutParam.getPayType());
			orderSummaryVo.setLpStatus(orderSummaryOutParam.getLpStatus());
			
			//地址信息
			orderSummaryVo.setAddress(orderSummaryOutParam.getAddress());
			//送货方式
			orderSummaryVo.setDeliver(orderSummaryOutParam.getDeliver());
			//收货人
			orderSummaryVo.setReceiver(orderSummaryOutParam.getReceiver());
			
			if (null != orderSummaryOutParam.getGoodsList() && orderSummaryOutParam.getGoodsList().size() > 0) {
				//设置goodsId
				orderSummaryVo.setGoodsId(orderSummaryOutParam.getGoodsList().get(0).getGoodsId());  //?? 将被goodsList取代?
				orderSummaryVo.setSkuCode(orderSummaryOutParam.getGoodsList().get(0).getSkuCode());  //?? 将被goodsList取代?
				orderSummaryVo.setGoodsList(orderSummaryOutParam.getGoodsList());
				String goodsImg = orderSummaryOutParam.getGoodsList().get(0).getGoodsImg();
				if (StringUtils.isNotEmpty(goodsImg)) {
					// 目前图片服务器前缀
					int index = new Random().nextInt(XiuConstant.XIUSTATIC_NUMS.length);
					String imgServerUrl = "http://" + XiuConstant.XIUSTATIC_NUMS[index] + ".xiustatic.com/";
					
					String[] temp1 = goodsImg.split("com/")[1].split("_");
					String newImgUrl = imgServerUrl + temp1[0] + "/g1_66_88.jpg";
					orderSummaryVo.setImgUrl(newImgUrl);
				}
			}
			
			boolean useProductPackagingService = orderSummaryOutParam.getUseProductPackaging(); //礼品包装服务
			orderSummaryVo.setUseProductPackaging(useProductPackagingService);
			//多笔支付
			orderSummaryVo.setIsMutilPay(orderSummaryOutParam.getIsMutilPay());
			orderSummaryVo.setConfirmPaidFee(orderSummaryOutParam.getConfirmPaidFee());
			orderSummaryVo.setNotAmount(orderSummaryOutParam.getNotAmount());
			if(orderSummaryOutParam.getOrderPayConfig() != null){
				orderSummaryVo.setMinMutilPayAmount(orderSummaryOutParam.getOrderPayConfig().getMinMutilPayAmount());
			}
			//待付款订单列表
			if("0".equals(orderSummaryOutParam.getPayStatus()) && !"4".equals(orderSummaryOutParam.getStatus())){
				//计算订单离撤销剩余毫秒数
				if(orderSummaryOutParam.getDelayTime() != null){ //是否有设置延迟撤销
					orderSummaryVo.setLeft(orderSummaryOutParam.getDelayTime().getTime() - System.currentTimeMillis());
				}else{//否则30分钟内撤销
					orderSummaryVo.setLeft(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(orderSummaryVo.getWhen()).getTime()
							+ 30*60*1000 - System.currentTimeMillis());
				}
				//如果是多笔支付
				//则查询当前支付记录里是否有未到账的，有则继续让用户支付该笔
//				if(GlobalConstants.MUTIL_PAY_YES == orderSummaryOutParam.getIsMutilPay()){
//					List<PayOrderDO> payOrderList = orderSummaryOutParam.getPayOrderList();
//					if(payOrderList != null && payOrderList.size() > 0){
//						for (Iterator<PayOrderDO> iterator = payOrderList.iterator(); iterator
//								.hasNext();) {
//							PayOrderDO payOrderDO = iterator.next();
//							if(payOrderDO.getStatus() == 0
//									&& !"PAY_VIRTUAL".equals(payOrderDO.getPayTypeCat())
//									&& !"PAY_COD".equals(payOrderDO.getPayTypeCat())){
//								orderSummaryVo.setLimitPayAmount(XiuUtil.getPriceDouble2Str(payOrderDO.getNotAmount() / 100.0));
//								logger.info("当前有未到账支付记录，orderId: " + orderSummaryVo.getOrderId() + "，limitPayAmount: " + orderSummaryVo.getLimitPayAmount());
//								break;
//							}
//						}
//					}
//				}
			}
		}

		return orderSummaryVo;
	}
	
	

	@Override
	public List<OrderSummaryVo> getDelayPayOrderSummaryList(
			OrderListOutParam allOrderList)throws Exception {
		List<OrderSummaryVo> delayPayOrderList = new ArrayList<OrderSummaryVo>();

		if (LogicUtil.isNotNull(allOrderList) && LogicUtil.isNotNullAndEmpty(allOrderList.getOrderList())) {
			for (OrderSummaryOutParam orderSummaryOutParam : allOrderList.getOrderList()) {
				delayPayOrderList.add(this.convert2OrderSummaryVo(orderSummaryOutParam));
			}
		}

		return delayPayOrderList;
	}

	@Override
	public List<OrderSummaryVo> getDelayDeliveOrderSummaryList(
			OrderListOutParam allOrderList) throws Exception{
		List<OrderSummaryVo> delayDeliveOrderList = new ArrayList<OrderSummaryVo>();

		if (LogicUtil.isNotNull(allOrderList) && LogicUtil.isNotNullAndEmpty(allOrderList.getOrderList())) {
			for (OrderSummaryOutParam orderSummaryOutParam : allOrderList.getOrderList()) {
				delayDeliveOrderList.add(this.convert2OrderSummaryVo(orderSummaryOutParam));
				
			}
		}

		return delayDeliveOrderList;
	}

	@Override
	public List<OrderSummaryVo> getDelivedOrderSummaryList(
			OrderListOutParam allOrderList) throws Exception{
		List<OrderSummaryVo> delivedOrderList = new ArrayList<OrderSummaryVo>();
		
		if (LogicUtil.isNotNull(allOrderList) && LogicUtil.isNotNullAndEmpty(allOrderList.getOrderList())) {
			for (OrderSummaryOutParam orderSummaryOutParam : allOrderList.getOrderList()) {
				delivedOrderList.add(this.convert2OrderSummaryVo(orderSummaryOutParam));
				
			}
		}

		return delivedOrderList;
	}

	@Override
	public PageVo getPageVo(OrderListOutParam orderListOutParam) {
		PageVo pageVo = new PageVo();
		if (null != orderListOutParam) {
			pageVo.setTotalPage(orderListOutParam.getTotalPage());
			pageVo.setTotalItem(orderListOutParam.getTotalItem());
			pageVo.setCurrentPage(orderListOutParam.getCurrentPage());
		}
		return pageVo;
	}

	@Override
	public int getAllOrderListCount(OrderListOutParam allOrderListOutParam) {
		List<OrderSummaryOutParam> lstOrderSumaryVos =allOrderListOutParam.getOrderList();
		return null != lstOrderSumaryVos ? lstOrderSumaryVos.size() : 0;
	}

	@Override
	public int getDelayDeliveOrderListCount(
			OrderListOutParam allOrderListOutParam) {
		int cnt=0;
		if (LogicUtil.isNotNull(allOrderListOutParam) && LogicUtil.isNotNullAndEmpty(allOrderListOutParam.getOrderList())) {
			for (OrderSummaryOutParam orderSummaryOutParam : allOrderListOutParam.getOrderList()) {
				
				if ((orderSummaryOutParam.getShowStatusCode())==OrderStatus.NEW_ORDER_PACKAGEING.getCode()) {
					cnt++;
				}
			}
			return cnt;
		}else {
			return 0;
		}

	}

	@Override
	public int getDelayPayOrderListCount(OrderListOutParam allOrderListOutParam) {
		int cnt=0;
		if (LogicUtil.isNotNull(allOrderListOutParam) && LogicUtil.isNotNullAndEmpty(allOrderListOutParam.getOrderList())) {
			for (OrderSummaryOutParam orderSummaryOutParam : allOrderListOutParam.getOrderList()) {
				
				if ((orderSummaryOutParam.getShowStatusCode())==OrderStatus.NEW_ORDER_WAITING_PAY.getCode()){
					cnt++;
				}
			}
			return cnt;
		}else {
			return 0;
		}
	}

	@Override
	public int getDelivedOrderListCount(OrderListOutParam allOrderListOutParam) {
		int cnt=0;
		if (LogicUtil.isNotNull(allOrderListOutParam) && LogicUtil.isNotNullAndEmpty(allOrderListOutParam.getOrderList())) {
			for (OrderSummaryOutParam orderSummaryOutParam : allOrderListOutParam.getOrderList()) {
				
				if ((orderSummaryOutParam.getShowStatusCode())==OrderStatus.NEW_ORDER_DELIVER_PART.getCode()||(orderSummaryOutParam.getShowStatusCode())==OrderStatus.NEW_ORDER_DELIVERED.getCode()) {
					cnt++;
				}
			}
			return cnt;
		}else {
			return 0;
		}
	}
	@Override
	public int getWaitCommentOrderListCount(OrderListInParam orderListInParam) throws Exception {
		List<OrderSummaryVo> waitCommentOrderList =this.getWaitCommentOrderSummaryList(orderListInParam);
		
		if (null!=waitCommentOrderList&&waitCommentOrderList.size()>0) {
			int cnt=waitCommentOrderList.size();
			return cnt;
		}else {
			return 0;
		}
	}
	@Override
	public  List<OrderSummaryVo> getWaitCommentOrderSummaryList(OrderListInParam orderListInParam) throws Exception{
		// 返回结果
		List<OrderSummaryVo> orderSummaryList = new ArrayList<OrderSummaryVo>();
		QueryOrderCondition condition = new QueryOrderCondition();
		// 查询类型
		condition.setBuyerId(orderListInParam.getUserId());
		 
		//封装页面属性
		PagingList<BizOrderDO> page = new PagingList<BizOrderDO>();
		page.setCurrentPage(orderListInParam.getPage());
		page.setPageSize(orderListInParam.getPageSize());
		Result orderResult=eiOrderManager.queryWaitingForCommentOrder(condition, page);

		// 成功后组装数据成接口输出需要的map
		page = (PagingList<BizOrderDO>) orderResult.getModels().get("pagingList");
		
		List<BizOrderDO> items =page.getItems();
		List<OrderSummaryOutParam> orderList = new ArrayList<OrderSummaryOutParam>();
		
		for (BizOrderDO bizOrder : items) {
			orderList.add(this.assembleOrderList(bizOrder, null));
		}
		
		if (LogicUtil.isNotNull(orderList) && LogicUtil.isNotNullAndEmpty(orderList)) {
			for (OrderSummaryOutParam orderSummaryOutParam : orderList) {
				orderSummaryList.add(this.convert2OrderSummaryVo(orderSummaryOutParam));
			}
		}
		return orderSummaryList;
	}

	public List<OrderSummaryVo> getCanBeRefundOrderList(
			OrderListInParam orderListInParam,PagingList<BizOrderDO> page ) throws Exception {
		        // 返回结果
				List<OrderSummaryVo> orderSummaryList = new ArrayList<OrderSummaryVo>();
				QueryOrderCondition condition = new QueryOrderCondition();
				// 查询类型
				condition.setBuyerId(orderListInParam.getUserId());
				 
				//封装页面属性
				Result orderResult=eiOrderManager.queryCanBeRefundOrderList(condition, page);

				// 成功后组装数据成接口输出需要的map
				page = (PagingList<BizOrderDO>) orderResult.getModels().get("pagingList");
				
				List<BizOrderDO> items =page.getItems();
				List<OrderSummaryOutParam> orderList = new ArrayList<OrderSummaryOutParam>();
				
				for (BizOrderDO bizOrder : items) {
					orderList.add(this.assembleRefundOrderList(bizOrder));
				}
				
				if (LogicUtil.isNotNull(orderList) && LogicUtil.isNotNullAndEmpty(orderList)) {
					for (OrderSummaryOutParam orderSummaryOutParam : orderList) {
						orderSummaryList.add(this.convert2RefundOrderSummaryVo(orderSummaryOutParam));
					}
				}
				return orderSummaryList;
	}
	
	/**
	 * 将订单中心的订单对象组装成 订单OrderSummaryOutParam
	 * @param bizOrder
	 * @return
	 */
	private OrderSummaryOutParam assembleRefundOrderList(BizOrderDO bizOrder){
		OrderSummaryOutParam outParam = new OrderSummaryOutParam();
		outParam.setOrderId(Integer.toString(bizOrder.getOrderId()));
		outParam.setOrderNo(bizOrder.getOrderCode());
//		outParam.setTotalPrice(XiuUtil.getPriceDouble2Str(bizOrder.getPayAmount() / 100.0));
		// 订单金额（折后的）
//		long orderAmount = bizOrder.getPayAmount() - bizOrder.getDiscountFee1() - bizOrder.getDiscountFee2() - bizOrder.getDiscountFee3();
//		outParam.setPrice(XiuUtil.getPriceDouble2Str(orderAmount / 100.0));
//		outParam.setWhen(DateFormatUtils.format(bizOrder.getGmtCreate(), "yyyy-MM-dd HH:mm:ss"));
		//旧的订单状态
		outParam.setStatus(Integer.toString(bizOrder.getOrderStatus()));
		//新的订单状态
		outParam.setNewStatus(bizOrder.getLogisticsStatus());
		//最新的订单显示状态
		outParam.setShowStatusCode(bizOrder.getShowStatusCode()==null?100:bizOrder.getShowStatusCode());
		outParam.setShowStatusName(bizOrder.getShowStatusName()==null?"待支付":bizOrder.getShowStatusName());
		
		Integer showStatusCode = outParam.getShowStatusCode();	//订单状态
		if(showStatusCode.intValue() == OrderStatus.NEW_ORDER_CLOSE.getCode()) {
			//如果是订单完结状态，显示为交易完成
			outParam.setShowStatusName(OrderStatus.NEW_ORDER_TRADE_CLOSE.getDesc());
		}
		
//		outParam.setPaymentMethod(Tools.getPayTypeDesc(bizOrder.getPayType()));
//		outParam.setPayType(bizOrder.getPayType());
		outParam.setLpStatus(Integer.toString(bizOrder.getLpStatus()));
		// 订单支付状态代码
		outParam.setPayStatus(Integer.toString(bizOrder.getPayStatus()));
		// 订单支付状态描述
		outParam.setPayStatusDesc(OrderEnum.PayStatus.getValueByKey(bizOrder.getPayStatus()));
		
		
		
		// 以下组装订单中的商品列表字段
		List<CommoSummaryOutParam> commoSummaryOutParamList = new ArrayList<CommoSummaryOutParam>();
		List<OrderDetailDO> orderDetails = bizOrder.getOrderItemList();
		boolean useProductPackaging = false;
		for (OrderDetailDO orderDetail : orderDetails) {
			CommoSummaryOutParam o = new CommoSummaryOutParam();
			o.setGoodsSn(orderDetail.getItemCodes());
			o.setGoodsName(orderDetail.getItemName());
			o.setGoodsId(Long.toString(orderDetail.getItemId()));
			o.setGoodsAmount(Integer.toString(orderDetail.getQuantity()));
			o.setZsPrice(XiuUtil.getPriceDouble2Str(orderDetail.getOriginalPrice()/100.0)); //走秀价
			o.setOrderDetailId(orderDetail.getOrderDetailId());
//			o.setSkuCode(orderDetail.getSkuCode());
//			o.setDiscountPrice(XiuUtil.getPriceDouble2Str(orderDetail.getSharePrice() / 100.0));
			// 商品属性
			o.setGoodsProperties(orderDetail.getPropertyAlias());
			// 图片url
			String imgUrl = orderDetail.getPicturePath();
			o.setGoodsImg(Tools.assembleImgUrlForApp(imgUrl));
			o.setRefundableQuantity(orderDetail.getQuantity());
			commoSummaryOutParamList.add(o);
//			String giftPackType = orderDetail.getGiftPackType();
//			if(StringUtils.isNotBlank(giftPackType) && giftPackType.equals("1")) {
//				//如果存在包装类型的商品，则认为使用了礼品包装服务
//				useProductPackaging = true;
//			}
		}
		outParam.setGoodsList(commoSummaryOutParamList);
//		outParam.setUseProductPackaging(useProductPackaging); //是否使用包装服务
		return outParam;
	}

	
	private OrderSummaryVo convert2RefundOrderSummaryVo(OrderSummaryOutParam orderSummaryOutParam)throws Exception {
		OrderSummaryVo orderSummaryVo = new OrderSummaryVo();

		if (LogicUtil.isNotNull(orderSummaryOutParam)) {
			orderSummaryVo.setOrderId(orderSummaryOutParam.getOrderId());
			orderSummaryVo.setOrderNo(orderSummaryOutParam.getOrderNo());
//			orderSummaryVo.setPrice(orderSummaryOutParam.getPrice());
//			orderSummaryVo.setWhen(orderSummaryOutParam.getWhen());
			//是否禁止评论  0 不禁止  1 禁止
//			SimpleDateFormat formatDate = new SimpleDateFormat(GlobalConstants.DATE_FROMAT);
//			Date targetDate,orderDate;
//			targetDate = formatDate.parse(GlobalConstants.DATE_2013);
//	        orderDate = formatDate.parse(orderSummaryOutParam.getWhen());
//			if (orderDate.before(targetDate)){
//				orderSummaryVo.setForbidComment(1);
//			}

			//mportal旧订单状态-旧
			OrderStatus ot = getOrderStatus(orderSummaryOutParam.getPayType(), orderSummaryOutParam.getPayStatus(),
					orderSummaryOutParam.getLpStatus(), orderSummaryOutParam.getStatus());
			orderSummaryVo.setStatusCode(ot.getCode());
//			orderSummaryVo.setStatus(ot.getDesc());
			//从osc中获得订单状态-新
			OrderStatus newOs = getOrderStatus(orderSummaryOutParam.getNewStatus());
			orderSummaryVo.setNewStatusCode(newOs.getCode());
			orderSummaryVo.setNewStatus(newOs.getDesc());
			
			//从osc中获得订单显示状态描述	-最新
			orderSummaryVo.setShowStatusCode(orderSummaryOutParam.getShowStatusCode());
			orderSummaryVo.setShowStatusName(orderSummaryOutParam.getShowStatusName());
//			
//			orderSummaryVo.setPayType(orderSummaryOutParam.getPayType());
			orderSummaryVo.setLpStatus(orderSummaryOutParam.getLpStatus());
			
			//地址信息
//			orderSummaryVo.setAddress(orderSummaryOutParam.getAddress());
			//送货方式
//			orderSummaryVo.setDeliver(orderSummaryOutParam.getDeliver());
			//收货人
//			orderSummaryVo.setReceiver(orderSummaryOutParam.getReceiver());
			
			if (null != orderSummaryOutParam.getGoodsList() && orderSummaryOutParam.getGoodsList().size() > 0) {
				//设置goodsId
				orderSummaryVo.setGoodsId(orderSummaryOutParam.getGoodsList().get(0).getGoodsId());  //?? 将被goodsList取代?
//				orderSummaryVo.setSkuCode(orderSummaryOutParam.getGoodsList().get(0).getSkuCode());  //?? 将被goodsList取代?
				orderSummaryVo.setGoodsList(orderSummaryOutParam.getGoodsList());
				String goodsImg = orderSummaryOutParam.getGoodsList().get(0).getGoodsImg();
				if (StringUtils.isNotEmpty(goodsImg)) {
					// 目前图片服务器前缀
					int index = new Random().nextInt(XiuConstant.XIUSTATIC_NUMS.length);
					String imgServerUrl = "http://" + XiuConstant.XIUSTATIC_NUMS[index] + ".xiustatic.com/";
					
					String[] temp1 = goodsImg.split("com/")[1].split("_");
					String newImgUrl = imgServerUrl + temp1[0] + "/g1_66_88.jpg";
					orderSummaryVo.setImgUrl(newImgUrl);
				}
			}
			
//			boolean useProductPackagingService = orderSummaryOutParam.getUseProductPackaging(); //礼品包装服务
//			orderSummaryVo.setUseProductPackaging(useProsductPackagingService);
		}

		return orderSummaryVo;
	}
	
	

}
