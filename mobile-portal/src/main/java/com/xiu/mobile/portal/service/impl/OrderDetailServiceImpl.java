package com.xiu.mobile.portal.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xiu.mobile.core.utils.DateUtil;
import com.xiu.mobile.portal.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.xiu.mobile.core.utils.ObjectUtil;
import com.xiu.mobile.core.utils.XiuUtil;
import com.xiu.mobile.portal.common.constants.OrderStatus;
import com.xiu.mobile.portal.common.constants.PayStatusConstant;
import com.xiu.mobile.portal.common.util.LogicUtil;
import com.xiu.mobile.portal.common.util.Tools;
import com.xiu.mobile.portal.constants.GlobalConstants;
import com.xiu.mobile.portal.dao.TopicActivityDao;
import com.xiu.mobile.portal.ei.EIOrderManager;
import com.xiu.mobile.portal.service.IGoodsService;
import com.xiu.mobile.portal.service.IOrderDetailService;
import com.xiu.mobile.portal.service.IOrderListService;
import com.xiu.mobile.portal.service.IOrderService;
import com.xiu.tc.common.orders.domain.PayOrderDO;
import com.xiu.tc.common.orders.domain.BizOrderDO;
import com.xiu.tc.common.orders.domain.DeliverAddressDO;
import com.xiu.tc.common.orders.domain.OrderDetailDO;
import com.xiu.tc.common.orders.domain.OrderInvoiceDO;
import com.xiu.tms.carry.result.PurchCarryInfo;
import com.xiu.tms.carry.service.PurchCarryInfoService;

/**
 * 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 订单详情
 * @AUTHOR : chenghong.ding@xiu.com 
 * @DATE :2014年5月22日 下午3:07:52
 * </p>
 ****************************************************************
 */
@Service("orderDetailService")
public class OrderDetailServiceImpl extends ComputeOrderStatus implements
		IOrderDetailService {
	
	Logger logger = Logger.getLogger(OrderDetailServiceImpl.class);
	
	private final static long ORDER_CANCEL_TIME = 30 * 60 * 1000;

	@Autowired
	private PurchCarryInfoService purchCarryInfoService;
	@Autowired
	private EIOrderManager eiOrderManager;
	@Autowired
	private IOrderListService orderListService;
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IGoodsService goodsService;
	@Autowired
	private TopicActivityDao topicActivityDao;

	@Override
	public OrderDetailOutParam getOrderDetailOutParam(
			OrderDetailInParam orderDetailInParam) {
		OrderDetailOutParam orderDetail = new OrderDetailOutParam();
		int orderId = orderDetailInParam.getOrderId();
		String userId = orderDetailInParam.getUserId();
		Assert.notNull(orderId,"订单orderId不能为空.");
		Assert.notNull(userId,"订单userId不能为空.");
		Long userIdL = Long.parseLong(userId);
		BizOrderDO order = eiOrderManager.queryOrderAllInfo(userIdL, orderId);
        if(null != order){
        	orderDetail.setOrderId(Integer.toString(order.getOrderId()));
        	orderDetail.setOrderNo(order.getOrderCode());
        	orderDetail.setGoodsTotalPrice(XiuUtil.getPriceDouble2Str(order.getItemAmount() / 100.0));//商品金额
        	orderDetail.setTotalPrice(XiuUtil.getPriceDouble2Str((order.getPayAmount() / 100.0)));//订单总金额
        	orderDetail.setLogisticsCost(XiuUtil.getPriceDouble2Str((order.getPostFee() / 100.0)));//运费
        	//优惠金额
        	long promoAmount=order.getDiscountFee1() + order.getDiscountFee2() + order.getDiscountFee3();
        	orderDetail.setPromoAmount(XiuUtil.getPriceDouble2Str(promoAmount/ 100.0));
        	//虚拟支付金额
        	List<PayOrderDO> pl=order.getPayOrderList();
        	long vpayAmount=0;
        	String payType=order.getPayType();
        	int i=0;int j=0;
        	String iName="";
        	String jName="";
        	for(PayOrderDO payOrder:pl){
        		 if(null!=payOrder.getPayTypeCat()&&"PAY_VIRTUAL".equals(payOrder.getPayTypeCat())){
        			 
        			 if(null!=payOrder.getPayType()&&"VIRTUAL_CAN".equals(payOrder.getPayType())||"VIRTUAL_NO".equals(payOrder.getPayType())){
        				 long refundAmt=Long.parseLong(null==payOrder.getRefundFee()?"0":payOrder.getRefundFee());
        				 vpayAmount+=payOrder.getPayAmount()-refundAmt;//累计虚拟账户支付金额
        			 }
        			 ++i;
        			 iName=payOrder.getPayTypeCat();
        			 
        		 }else{
        			 ++j;
        			 jName=payOrder.getPayType();
        		 }
        	}
        	orderDetail.setVpayAmount(XiuUtil.getPriceDouble2Str(vpayAmount/100.0));
        	if(j>0){
        		payType=jName;
        	 }else if(j==0&&i>0){
        		 payType=iName;
        	 }
        	//需要支付金额
        	long orderAmount = order.getPayAmount() - promoAmount-vpayAmount;
        	orderDetail.setPrice(XiuUtil.getPriceDouble2Str(orderAmount / 100.0));
        	//还需要支付金额
        	orderDetail.setLeftAmount(XiuUtil.getPriceDouble2Str(order.getNotAmount() / 100.0));
        	//已支付金额
        	orderDetail.setConfirmPaidFee(XiuUtil.getPriceDouble2Str(order.getConfirmPaidFee() / 100.0));
        	
        	orderDetail.setWhen(DateFormatUtils.format(order.getGmtCreate(), "yyyy-MM-dd HH:mm:ss"));
        	//旧的订单状态
        	orderDetail.setStatus(Integer.toString(order.getOrderStatus()));
        	//新的订单状态
        	orderDetail.setNewStatus(order.getLogisticsStatus());
        	//最新的订单状态
        	orderDetail.setShowStatusCode(order.getShowStatusCode());
        	orderDetail.setShowStatusName(order.getShowStatusName());
        	
        	Integer showStatusCode = orderDetail.getShowStatusCode();	//订单状态
			if(showStatusCode.intValue() == OrderStatus.NEW_ORDER_CLOSE.getCode()) {
				//如果是订单完结状态，显示为交易完成
				orderDetail.setShowStatusName(OrderStatus.NEW_ORDER_TRADE_CLOSE.getDesc());
			}
			order.setDelayTime(order.getDelayTime());
			
			orderDetail.setLpStatus(String.valueOf(order.getLpStatus()));
        	
        	DeliverAddressDO da = order.getDeliverAddressDO();
        	orderDetail.setAddressId(da.getAddressId());
        	orderDetail.setReceiver(da.getFullName());
        	orderDetail.setProvince(da.getProvCode() != null ? da.getProvCode() : da.getProv());
        	orderDetail.setCity(da.getCityCode() != null ? da.getCityCode() : da.getCity());
        	orderDetail.setArea(da.getAreaCode() != null ? da.getAreaCode() : da.getArea());
        	orderDetail.setAddress(da.getAddress());
        	orderDetail.setMobile(da.getMobile());
        	orderDetail.setPostCode(da.getPostCode());
        	orderDetail.setPhone(da.getPhone());
        	orderDetail.setDeliver(Integer.toString(da.getPostType()));
        	orderDetail.setPaymentMethod(Tools.getPayTypeDesc(payType));
        	orderDetail.setPayType(payType);
        	// 订单支付状态代码
    		orderDetail.setPayStatus(Integer.toString(order.getPayStatus()));
    		orderDetail.setGoodsCount(order.getQuantity()); // 商品总数
    		// 以下组装订单中的商品列表字段
    		List<CommoSummaryOutParam> commoSummaryOutParamList = new ArrayList<CommoSummaryOutParam>();
    		List<OrderDetailDO> orderDetails = order.getOrderItemList();
    		boolean useProductPackaging = false; //使用礼品包装商品
    		long productPackagingPrice = 0;	//商品包装价格
    		for (OrderDetailDO od : orderDetails) {
				String giftPackType = od.getGiftPackType(); //礼品包装类型
				if(StringUtils.isNotBlank(giftPackType) && giftPackType.equals("1")) {
					//如果是礼品包装商品
					useProductPackaging = true;
					long goodsBuyPrice = od.getDiscountPrice(); //商品购买价
					productPackagingPrice = productPackagingPrice + goodsBuyPrice;
					continue; //如果是礼品包装商品，不添加到订单详情商品列表中
				}
				
    			CommoSummaryOutParam o = new CommoSummaryOutParam();
    			o.setGoodsId(Long.toString(od.getItemId()));
    			o.setGoodsSn(od.getItemCodes());
    			o.setGoodsName(od.getItemName());
    			o.setGoodsAmount(Integer.toString(od.getQuantity()));
    			o.setZsPrice(XiuUtil.getPriceDouble2Str(od.getOriginalPrice() / 100.0)); //走秀价
    			o.setPrice(XiuUtil.getPriceDouble2Str(od.getArtifactOriginalPrice()));
    			o.setDiscountPrice(XiuUtil.getPriceDouble2Str(od.getSharePrice() / 100.0));
    			// 商品属性
    			o.setGoodsProperties(od.getPropertyAlias());
    			// 图片url
    			String imgUrl = od.getPicturePath();
    			o.setGoodsImg(Tools.assembleImgUrlForApp(imgUrl));
    			o.setSkuCode(od.getSkuCode());
    			o.setRefundableQuantity(od.getRefundableQuantity());
    			o.setRefundRecord(od.getRefundRecord());
    			o.setDeliveryDate(od.getDeliveryDate());
    			o.setOrderDetailId(od.getOrderDetailId());
    			o.setDeliverType(od.getDeliverType());
    			commoSummaryOutParamList.add(o);
    		}
    		orderDetail.setGoodsList(commoSummaryOutParamList);
    		orderDetail.setUseProductPackaging(useProductPackaging);
    		productPackagingPrice = productPackagingPrice / 100;
    		orderDetail.setPackagingPrice(String.valueOf(productPackagingPrice));
    		
    		if(productPackagingPrice > Double.valueOf("0")) {
    			//如果商品包装价格大于0
    			double goodsTotalPrice =  Double.valueOf(orderDetail.getGoodsTotalPrice());
    			goodsTotalPrice = goodsTotalPrice - productPackagingPrice; //商品总价格减去商品包装价格
    			String goodsTotalPriceStr = String.valueOf(goodsTotalPrice);
    			int len = goodsTotalPriceStr.indexOf(".");
    			if(len != -1 && goodsTotalPriceStr.length() - len > 2) {
    				//商品价格小数点处理
    				BigDecimal priceValue = new BigDecimal(goodsTotalPriceStr).setScale(2, BigDecimal.ROUND_HALF_UP);
    				goodsTotalPriceStr = priceValue.toString();
    			}
    			orderDetail.setGoodsTotalPrice(goodsTotalPriceStr);
    			
    			//商品数量修改
    			int goodsCount = orderDetail.getGoodsCount();
    			goodsCount = goodsCount - 1;
    			orderDetail.setGoodsCount(goodsCount);
    		}
    		
    		
    		// 发票信息
    		OrderInvoiceDO invoice = order.getOrderInvoiceDO();
    		if(null != invoice){
    			orderDetail.setInvoice("1");
    			orderDetail.setInvoiceType(Integer.toString(invoice.getInvoiceType()));
    			orderDetail.setInvoiceHeading(invoice.getInvoiceHead());
    			orderDetail.setInvoiceContent(invoice.getContent());
    		}else{
    			orderDetail.setInvoice("0");
    		}
    		//订单是否为多次支付 订单
    		orderDetail.setIsMutilPay(order.getIsMutilPay());
    		orderDetail.setPayInfos(order.getPayOrderList());
        }
		orderDetail.setRetCode(GlobalConstants.RET_CODE_SUCESSS);
		return orderDetail;
	}

	@Override
	public OrderBaseInfoVo getOrderBaseInfo(
			OrderDetailOutParam orderDetailOutParam)throws Exception {
		return this.convert2OrderBaseInfoVo(orderDetailOutParam);
	}
	
	private OrderBaseInfoVo convert2OrderBaseInfoVo(OrderDetailOutParam orderDetailOutParam) throws Exception{
		OrderBaseInfoVo orderDetailVo = new OrderBaseInfoVo();

		if (LogicUtil.isNotNull(orderDetailOutParam)) {
			orderDetailVo.setOrderId(orderDetailOutParam.getOrderId());
			orderDetailVo.setOrderNo(orderDetailOutParam.getOrderNo());
			orderDetailVo.setWhen(orderDetailOutParam.getWhen());
			//mportal旧的订单状态
			OrderStatus ot = getOrderStatus(orderDetailOutParam.getPayType(), orderDetailOutParam.getPayStatus(),
					orderDetailOutParam.getLpStatus(), orderDetailOutParam.getStatus());
			orderDetailVo.setStatusCode(ot.getCode());
			orderDetailVo.setStatus(ot.getDesc());
			//新的订单状态
			OrderStatus newOs = getOrderStatus(orderDetailOutParam.getNewStatus());
			orderDetailVo.setNewStatusCode(newOs.getCode());
			orderDetailVo.setNewStatus(newOs.getDesc());
			
			//5(备货中)  6(部分已发货) 7(已发货) 9(交易完结) 10(订单完成)表示有物流信息
			if (newOs.getCode()==5 ||newOs.getCode()==6 || newOs.getCode()==7 || newOs.getCode()==9 || newOs.getCode()==10) {
				orderDetailVo.setHasLogisticsInfo(1);
			}else{
				orderDetailVo.setHasLogisticsInfo(0);
			}

			orderDetailVo.setIsMutilPay(orderDetailOutParam.getIsMutilPay());
			if(newOs.getCode() == OrderStatus.ORDER_WAITING_PAY.getCode()){//待支付訂單

				if(orderDetailOutParam.getDelayTime() != null){
					orderDetailVo.setLeft(orderDetailOutParam.getDelayTime().getTime() - System.currentTimeMillis());
				}else{
					orderDetailVo.setLeft(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(orderDetailOutParam.getWhen()).getTime()
							+ ORDER_CANCEL_TIME - System.currentTimeMillis());
				}
			}

			//最新的订单状态
			orderDetailVo.setShowStatusCode(orderDetailOutParam.getShowStatusCode());
			orderDetailVo.setShowStatusName(orderDetailOutParam.getShowStatusName());
			
			Integer showStatusCode = orderDetailVo.getShowStatusCode();	//订单状态
			if(showStatusCode.intValue() == OrderStatus.NEW_ORDER_CLOSE.getCode()) {
				//如果是订单完结状态，显示为交易完成
				orderDetailVo.setShowStatusName(OrderStatus.NEW_ORDER_TRADE_CLOSE.getDesc());
			}

			//300(备货中)  400(部分已发货) 500(已发货) 600(交易完结) 700(订单完成)表示有物流信息
			if (orderDetailOutParam.getShowStatusCode()==300 ||orderDetailOutParam.getShowStatusCode()==400 || orderDetailOutParam.getShowStatusCode()==500 || orderDetailOutParam.getShowStatusCode()==600 || orderDetailOutParam.getShowStatusCode()==700) {
				orderDetailVo.setHasLogisticsInfo(1);
			}else{
				orderDetailVo.setHasLogisticsInfo(0);
			}
			orderDetailVo.setVpayAmount(orderDetailOutParam.getVpayAmount());
			orderDetailVo.setPaymentMethod(this.getPaymentMethod(orderDetailOutParam.getPaymentMethod()));
			orderDetailVo.setPayStatus(this.getPayStatus(orderDetailOutParam.getPayStatus()));
			orderDetailVo.setPayType(orderDetailOutParam.getPayType());
			orderDetailVo.setGoodsTotalPrice(orderDetailOutParam.getGoodsTotalPrice());
			orderDetailVo.setOrderPrice(orderDetailOutParam.getTotalPrice());
			orderDetailVo.setPayPrice(orderDetailOutParam.getPrice());
			orderDetailVo.setLogisticsCost(orderDetailOutParam.getLogisticsCost());
			orderDetailVo.setGoodsCount(orderDetailOutParam.getGoodsCount());
			orderDetailVo.setPromoAmount(orderDetailOutParam.getPromoAmount());
			
			orderDetailVo.setUseProductPackaging(orderDetailOutParam.getUseProductPackaging()); //使用礼品包装服务
			orderDetailVo.setPackagingPrice(orderDetailOutParam.getPackagingPrice()); //商品包装价格
			orderDetailVo.setLpStatus(orderDetailOutParam.getLpStatus());
			
			orderDetailVo.setLeftPayAmount(orderDetailOutParam.getLeftAmount());
			orderDetailVo.setConfirmPaidFee(orderDetailOutParam.getConfirmPaidFee());
			
			if(GlobalConstants.MUTIL_PAY_YES == orderDetailOutParam.getIsMutilPay()){
				//多笔支付最小支付金额
				OrderPayConfig payConfig = orderService.getOrderPayConfig(orderDetailOutParam);
				if(payConfig != null){
					orderDetailVo.setMinMutilPayAmount(payConfig.getMinMutilPayAmount());
				}
				if(CollectionUtils.isNotEmpty(orderDetailOutParam.getPayInfos())){

					List<OrderPayInfo> list = new ArrayList<OrderPayInfo>(orderDetailOutParam.getPayInfos().size());

					for(PayOrderDO payOrderDO : orderDetailOutParam.getPayInfos()){
						//多笔支付，待支付订单，如果该订单有未到账支付记录，则继续让该用户支付该笔
//						if(payOrderDO.getStatus() == 0 
//								&& !"PAY_VIRTUAL".equals(payOrderDO.getPayTypeCat())
//								&& !"PAY_COD".equals(payOrderDO.getPayTypeCat())){
//							orderDetailVo.setLimitPayAmount(XiuUtil.getPriceDouble2Str(payOrderDO.getNotAmount() / 100.0));
//							
//							logger.info("当前有未到账支付记录，orderId: " + orderDetailVo.getOrderId() + "，limitPayAmount: " + orderDetailVo.getLimitPayAmount());
//						}
						//在详情页展示支付成功的记录
						if(payOrderDO.getStatus() == 1){
							OrderPayInfo orderPayInfo = new OrderPayInfo();
							
							orderPayInfo.setPayAmount(XiuUtil.getPriceDouble2Str(payOrderDO.getPayAmount() / 100.0));
							orderPayInfo.setPayType(Tools.getPayTypeDesc(payOrderDO.getPayType()));
							orderPayInfo.setPayTime(DateUtil.formatDate(payOrderDO.getGmtModified()));
							
							list.add(orderPayInfo);
						}
					}
					orderDetailVo.setOrderPayList(list);
				}
			}
		}
		return orderDetailVo;
	}

	private String getPaymentMethod(String paymentMethod) {
		if (null != paymentMethod) {
			return paymentMethod.replace("移动", "");
		}
		return "";
	}
	
	private String getPayStatus(String payStatus) {
		/**
		 * 未到账
		 */
		if (PayStatusConstant.NOT_ARRIVE_ACCOUNT == Integer.valueOf(payStatus)) {
			return PayStatusConstant.NOT_ARRIVE_ACCOUNT_MSG;
		}
		/**
		 * 已到账
		 */
		else if (PayStatusConstant.ARRIVED_ACCOUNT == Integer.valueOf(payStatus)) {
			return PayStatusConstant.ARRIVED_ACCOUNT_MSG;
		}
		/**
		 * 已退款
		 */
		else if (PayStatusConstant.REFUNDED == Integer.valueOf(payStatus)) {
			return PayStatusConstant.REFUNDED_MSG;
		} else {
			return "";
		}
	}
	
	@Override
	public ReceiverInfoVo getReceiverInfo(
			OrderDetailOutParam orderDetailOutParam) {
		return this.convert2ReceiverInfoVo(orderDetailOutParam);
	}
	
	private ReceiverInfoVo convert2ReceiverInfoVo(OrderDetailOutParam orderDetailOutParam) {
		ReceiverInfoVo receiverInfoVo = new ReceiverInfoVo();

		if (LogicUtil.isNotNull(orderDetailOutParam)) {
			receiverInfoVo.setReceiver(orderDetailOutParam.getReceiver());
			receiverInfoVo.setProvince(orderDetailOutParam.getProvince());
			receiverInfoVo.setCity(orderDetailOutParam.getCity());
			receiverInfoVo.setArea(orderDetailOutParam.getArea());
			receiverInfoVo.setAddressId(orderDetailOutParam.getAddressId() == null ? "" : orderDetailOutParam.getAddressId().toString());
			receiverInfoVo.setAddress(orderDetailOutParam.getAddress());
			receiverInfoVo.setMobile(orderDetailOutParam.getMobile());
			receiverInfoVo.setPhone(orderDetailOutParam.getPhone());
			receiverInfoVo.setPostCode(orderDetailOutParam.getPostCode());
		}

		return receiverInfoVo;
	}

	@Override
	public List<CommoSummaryVo> getCommoSummaryList(
			OrderDetailOutParam orderDetailOutParam) {
		List<CommoSummaryVo> commoSummaryList = null;

		if (LogicUtil.isNotNull(orderDetailOutParam) && LogicUtil.isNotNullAndEmpty(orderDetailOutParam.getGoodsList())) {
			commoSummaryList = new ArrayList<CommoSummaryVo>();
			for (CommoSummaryOutParam commoSummaryOutParam : orderDetailOutParam.getGoodsList()) {

				commoSummaryList.add(this.convert2CommoSummaryVo(commoSummaryOutParam));
			}
		}

		return commoSummaryList;
	}
	
	private CommoSummaryVo convert2CommoSummaryVo(CommoSummaryOutParam commoSummaryOutParam) {
		CommoSummaryVo commoSummaryVo = new CommoSummaryVo();
		if (LogicUtil.isNotNull(commoSummaryOutParam)) {
			// 商品编号
			commoSummaryVo.setGoodsSn(commoSummaryOutParam.getGoodsSn());
			commoSummaryVo.setGoodsName(commoSummaryOutParam.getGoodsName());
			commoSummaryVo.setGoodsAmount(commoSummaryOutParam.getGoodsAmount());
			commoSummaryVo.setDiscountPrice(commoSummaryOutParam.getDiscountPrice());
			commoSummaryVo.setSkuCode(commoSummaryOutParam.getSkuCode());
			commoSummaryVo.setGoodsId(commoSummaryOutParam.getGoodsId());
			
			//品牌名称
			Map brandMap = topicActivityDao.getBrandNameByGoods(commoSummaryOutParam.getGoodsId());
			if(brandMap != null) {
				String cnName = (String) brandMap.get("CN_NAME");
				String enName = (String) brandMap.get("EN_NAME");
				commoSummaryVo.setBrandCNName(cnName);
				commoSummaryVo.setBrandEnName(enName);
			}
			
			// 转换商品图片地址
			commoSummaryVo.setGoodsImg(commoSummaryOutParam.getGoodsImg() + "/g1_100_100.jpg");

			// 设置商品属性
			CommoPropOutParam commoPropOutParam = this.getCommoPropOutParam(commoSummaryOutParam);
			if (LogicUtil.isNotNull(commoPropOutParam)) {
				commoSummaryVo.setColor(commoPropOutParam.getColor());
				commoSummaryVo.setSize(commoPropOutParam.getSize());
			}

			// 商品可退换货数据整理   2014-08-29新增
			commoSummaryVo.setDeliverType(commoSummaryOutParam.getDeliverType());
			commoSummaryVo.setRefundableQuantity(commoSummaryOutParam.getRefundableQuantity());
			// 商品退换货记录 2015-04-14新增
			commoSummaryVo.setRefundRecord(commoSummaryOutParam.getRefundRecord());
			commoSummaryVo.setDeliveryDate(commoSummaryOutParam.getDeliveryDate());
			commoSummaryVo.setRefundFlag(false);
			commoSummaryVo.setOrderDetailId(commoSummaryOutParam.getOrderDetailId());
			// 判断商品是否可以退换货  条件为可退换数量大于0并且出库时间在30天以内
			if (commoSummaryOutParam.getRefundableQuantity()>0) {
				if (commoSummaryOutParam.getDeliveryDate()!=null) {
					int diffDay = daysOfTwo(commoSummaryOutParam.getDeliveryDate(), new Date());
					if (diffDay <= 30) {
						commoSummaryVo.setRefundFlag(true);
					}
				}
			}
			// 商品的小计
			double r = Double.valueOf(commoSummaryVo.getGoodsAmount()) * Double.valueOf(commoSummaryVo.getDiscountPrice());
			commoSummaryVo.setTotalAmount(String.valueOf(r));
		}

		return commoSummaryVo;
	}
	
	private CommoPropOutParam getCommoPropOutParam(CommoSummaryOutParam commoSummaryOutParam) {
		CommoPropOutParam cp = new CommoPropOutParam();
		String goodsProp = commoSummaryOutParam.getGoodsProperties();
		if (LogicUtil.isNotNull(goodsProp)) {

			Matcher m = Pattern.compile("颜色:([^;]*?);").matcher(goodsProp);
			if (m.find()) {
				cp.setColor(m.group(1));
			}
			m = Pattern.compile("尺寸:([^;]*?);").matcher(goodsProp);
			if (m.find()) {
				cp.setSize(m.group(1));
			}
		}

		return cp;
	}

	/**
	 * 获取物流信息
	 */
	@Override
	public List<CarryInfoVo> getCarryInfos(Integer orderId, String skuCode)
			throws Exception {
		List<PurchCarryInfo> purchCarryList = purchCarryInfoService.queryOrderCarryInfo(skuCode, orderId);
		List<CarryInfoVo> carryInfos = toCarryInfoVos(purchCarryList);
		return carryInfos;
	}
	
	public List<CarryInfoVo> toCarryInfoVos(List<PurchCarryInfo> purchCarryList) {
		List<CarryInfoVo> carryInfoVoList = null;
		if (purchCarryList != null) {
			carryInfoVoList = new ArrayList<CarryInfoVo>();
			for (PurchCarryInfo purchCarryInfo : purchCarryList) {
				CarryInfoVo carryInfoVo = new CarryInfoVo();
				carryInfoVo.setOrderDetailId(purchCarryInfo.getOrderDetailId());
				carryInfoVo.setCarryTime(purchCarryInfo.getCarryTime());
				carryInfoVo.setAddress(purchCarryInfo.getAddress());
				carryInfoVo.setStatus(purchCarryInfo.getStatus());
				carryInfoVo.setDetail(purchCarryInfo.getDetail());
				carryInfoVoList.add(carryInfoVo);
			}
		}

		return carryInfoVoList;
	}

	@Override
	public boolean checkOrderId(OrderListInParam orderListInParam,Integer orderId) {
		boolean flag=false;
		OrderListOutParam orderListOutParam = orderListService.getOrderListOutParam(orderListInParam);
		if (orderListOutParam!=null) {
			if (orderListOutParam.getOrderList()!=null&&orderListOutParam.getOrderList().size()>0) {
				List<OrderSummaryOutParam> orderList= orderListOutParam.getOrderList();
				for (OrderSummaryOutParam orderSummaryOutParam : orderList) {
					String value=String.valueOf(orderId);
					if(value.equals(orderSummaryOutParam.getOrderId())){
						flag=true;
						break;
					}
				}
			}
		}
		return flag;
	}
	/***
	 * 计算两个时间相差天数
	 * @param fDate
	 * @param oDate
	 * @return
	 */
	public int daysOfTwo(Date bDate, Date eDate) {
		// 格式化两个日期
        long time1 = bDate.getTime();               
        long time2 = eDate.getTime();   
        // 后一个时间天数减去前一个时间天数  除以时分秒毫秒得到天数
        long diffDays= (time2-time1)/(1000*3600*24);   
        // 天数相减获取值
        int days = new Long(diffDays).intValue();  
        
        return days;
	}

}
