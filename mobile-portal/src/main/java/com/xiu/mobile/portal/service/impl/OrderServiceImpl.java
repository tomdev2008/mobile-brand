package com.xiu.mobile.portal.service.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.xiu.commerce.hessian.model.Product;
import com.xiu.commerce.hessian.model.Sku;
import com.xiu.common.command.result.Result;
import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.core.model.PayForTemplet;
import com.xiu.mobile.core.utils.ObjectUtil;
import com.xiu.mobile.core.utils.XiuUtil;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.constants.OrderStatus;
import com.xiu.mobile.portal.common.constants.PayTypeConstant;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.util.EncryptUtils;
import com.xiu.mobile.portal.common.util.HttpUtils;
import com.xiu.mobile.portal.common.util.ImageServiceConvertor;
import com.xiu.mobile.portal.common.util.Tools;
import com.xiu.mobile.portal.constants.ErrConstants;
import com.xiu.mobile.portal.constants.GlobalConstants;
import com.xiu.mobile.portal.dao.PayForTempletDao;
import com.xiu.mobile.portal.ei.EIAddressManager;
import com.xiu.mobile.portal.ei.EIOrderManager;
import com.xiu.mobile.portal.ei.EIUUCManager;
import com.xiu.mobile.portal.model.AddressListQueryInParam;
import com.xiu.mobile.portal.model.AddressOutParam;
import com.xiu.mobile.portal.model.AddressVo;
import com.xiu.mobile.portal.model.CalculateOrderBo;
import com.xiu.mobile.portal.model.CancelOrderVO;
import com.xiu.mobile.portal.model.CheckRepeatedRespVo;
import com.xiu.mobile.portal.model.CommoSummaryOutParam;
import com.xiu.mobile.portal.model.GoodsDetailVo;
import com.xiu.mobile.portal.model.MktActInfoVo;
import com.xiu.mobile.portal.model.OrderDetailOutParam;
import com.xiu.mobile.portal.model.OrderGoodsVO;
import com.xiu.mobile.portal.model.OrderInvoiceVO;
import com.xiu.mobile.portal.model.OrderListInParam;
import com.xiu.mobile.portal.model.OrderListOutParam;
import com.xiu.mobile.portal.model.OrderPayConfig;
import com.xiu.mobile.portal.model.OrderReqVO;
import com.xiu.mobile.portal.model.OrderResVO;
import com.xiu.mobile.portal.model.OrderSummaryOutParam;
import com.xiu.mobile.portal.model.OrderSummaryVo;
import com.xiu.mobile.portal.model.PayInfoVO;
import com.xiu.mobile.portal.model.PayMethodInParam;
import com.xiu.mobile.portal.model.PayOrder;
import com.xiu.mobile.portal.model.PayOrderItem;
import com.xiu.mobile.portal.model.PayReqVO;
import com.xiu.mobile.portal.model.PayTradeItemPara;
import com.xiu.mobile.portal.model.QueryUserAddressDetailInParam;
import com.xiu.mobile.portal.model.QueryUserAddressListOutParam;
import com.xiu.mobile.portal.service.IAddressService;
import com.xiu.mobile.portal.service.IGoodsService;
import com.xiu.mobile.portal.service.IMemcachedService;
import com.xiu.mobile.portal.service.IOrderListService;
import com.xiu.mobile.portal.service.IOrderService;
import com.xiu.mobile.portal.service.IProductService;
import com.xiu.mobile.portal.service.IReceiverIdService;
import com.xiu.mobile.portal.service.IWithDrawService;
import com.xiu.sales.common.balance.dataobject.BalanceActivityInfoDO;
import com.xiu.sales.common.balance.dataobject.BalanceCardInfoDO;
import com.xiu.sales.common.balance.dataobject.BalanceGoodsInfoDO;
import com.xiu.sales.common.balance.dataobject.BalanceOrderInfoDO;
import com.xiu.sales.common.balance.dataobject.BalancePayInfoDO;
import com.xiu.sales.common.balance.dataobject.BalanceUserInfoDO;
import com.xiu.sales.common.balance.dataobject.PriceSettlementDO;
import com.xiu.sales.common.balance.dataobject.ProdSettlementDO;
import com.xiu.sales.common.blacklist.dataobject.ItemBlackParamDO;
import com.xiu.sales.common.card.dataobject.CardInputParamDO;
import com.xiu.sales.common.card.dataobject.CardOutParamDO;
import com.xiu.sales.common.card.dataobject.RuleGoodsRelationDO;
import com.xiu.sales.common.card.dointerface.CardService;
import com.xiu.sales.common.card.exception.LCException;
import com.xiu.tc.common.orders.domain.BizOrderDO;
import com.xiu.tc.common.orders.domain.CancelDO;
import com.xiu.tc.common.orders.domain.CpsOrderDO;
import com.xiu.tc.common.orders.domain.DeliverAddressDO;
import com.xiu.tc.common.orders.domain.MediaOrderDO;
import com.xiu.tc.common.orders.domain.OrderActiveDO;
import com.xiu.tc.common.orders.domain.OrderChannelDO;
import com.xiu.tc.common.orders.domain.OrderDetailDO;
import com.xiu.tc.common.orders.domain.OrderFromDO;
import com.xiu.tc.common.orders.domain.OrderSysConfig;
import com.xiu.tc.common.orders.domain.PayOrderDO;
import com.xiu.tc.common.orders.domain.ProductValidateDO;
import com.xiu.tc.orders.condition.UpdatePayTypeForWapCondition;
import com.xiu.uuc.facade.dto.RcvAddressDTO;
import com.xiu.uuc.facade.dto.UserBaseDTO;
import com.xiu.uuc.facade.dto.UserIdentityDTO;
import com.xiu.uuc.facade.dto.VirtualAcctItemDTO;

@Service("orderService")
public class OrderServiceImpl extends ComputeOrderStatus implements
		IOrderService {
	
	private Logger logger = Logger.getLogger(OrderServiceImpl.class);
	@Autowired
	private IAddressService addressService;
	@Autowired
	private AddressVoConvertor addressVoConvertor;
	@Autowired
	private EIOrderManager eiOrderManager;
	@Autowired
	private EIAddressManager eiAddressManager;
	@Autowired
	private CardService cardService;
	@Autowired
	private IProductService productService;
	@Autowired
	private EIUUCManager eiuucManager;
	@Autowired
	private IOrderListService orderListService;
	@Autowired
	private IWithDrawService withDrawServiceImpl;
	@Autowired
	private IGoodsService goodsManager;
	@Autowired
	private IReceiverIdService receiverIdService;
	@Autowired
	private IMemcachedService memcachedService;
	
	@Autowired
	private PayForTempletDao payForTempletDao;
	final static String MERCHANT_ID = GlobalConstants.PAY_MERCHANT_ID;

	@Override
	public AddressVo getOrderAddress(AddressListQueryInParam addressListQuery) {
		try {
			QueryUserAddressListOutParam UserAddressLisParam = addressService.listAddress(addressListQuery);
			List<AddressVo> addressVos = UserAddressLisParam.getAddressList();
			if (null != addressVos && addressVos.size() > 0) {
				for (AddressVo addressVo : addressVos) {
					if ("Y".equals(addressVo.getIsMaster())) {
						return addressVo;
					}
				}
				return addressVos.get(0);
			}
		} catch (Exception e) {
			logger.error("获取订单地址失败", e);
			return null;
		}
		return null;
	}

	@Override
	public CalculateOrderBo calcOrder(OrderReqVO orderReqVO) throws Exception {
		try{
			CalculateOrderBo orderBo = new CalculateOrderBo();
			String channelId = GlobalConstants.CHANNEL_ID;
			
			//如果是礼品包装
			String useProductPackaging = orderReqVO.getUseProductPackaging();
			String goodsSn = orderReqVO.getGoodsSn();
			boolean supportPackaging = goodsManager.isProductSupportWrap(goodsSn);
			String packagingPrice = null;
			double packagingAmount = 0; //礼品包装金额
			if(supportPackaging) {
				packagingPrice = goodsManager.getProductPackagingPrice(); //礼品包装价格
			}
		    if(StringUtils.isNotBlank(useProductPackaging) && useProductPackaging.equals("1")) {
		    	//使用了礼品包装
		    	if(supportPackaging) {
		    		//如果支持礼品包装，则查询礼品包装价格
		    		if(StringUtils.isNotBlank(packagingPrice)) {
		    			packagingAmount = Double.parseDouble(packagingPrice);
		    		}
		    	}
		    }
		    orderBo.setSupportPackaging(supportPackaging); //是否支持礼品包装标识
		    orderBo.setPackagingPrice(packagingPrice); //礼品包装价格
			
			BalanceOrderInfoDO balanceInfo = this.calcOrderBySaleCenter(orderReqVO, channelId);
			BalancePayInfoDO payInfo = balanceInfo.getPayInfo().get(0);
			double amount = payInfo.getDiscountTotalAmount() / 100.0;		// 订单的金额（打折后的）==商品总价-优惠券钱
			double favourableAmount = payInfo.getActivityDiscountAmount() / 100.0;	// 订单优惠了多少钱 ==优惠券钱 
			double freight = payInfo.getActualPayShip() / 100;				//实际支付运费金额 ==运费
			/** 订单金额总计  商品总价==走秀价 */
			double goodsAmount=payInfo.getTotalAmount()/100.0;
			
			if (logger.isDebugEnabled()) {
				logger.debug("商品总价totalAmount : " + payInfo.getTotalAmount() / 100.00);
				logger.debug("用户最后要付的钱discountTotalAmount : " + payInfo.getDiscountTotalAmount() / 100.00);
				logger.debug("运费freight : " + payInfo.getActualPayShip() / 100.00);
			}
			long hxAmount=payInfo.getDiscountTotalAmount()+payInfo.getActualPayShip();
			//获得虚拟账户可用金额
			VirtualAcctItemDTO virtualAcct=withDrawServiceImpl.getVirtualAccountInfo(Long.parseLong(orderReqVO.getUserId()));
			//虚拟账户可用余额
			long unableDrawMoney=virtualAcct.getUnableDrawMoney();	// 可提现可用金额（可提现总金额-可提现冻结金额） 单位：分
			long unableNoDrawMoney=virtualAcct.getUnableNoDrawMoney();// 不可提现可用金额（不可提现总金额-不可提现冻结金额） 单位：分
			
			long vtotalAmount=unableDrawMoney+unableNoDrawMoney;
			//虚拟账户支付金额
			long vpayAmount=0;
		    //用户最后还需支付
			long totalAmount=0;
		    if("0".equals(orderReqVO.getIsVirtualPay())){
		    	//0不使用虚拟账户支付
		    	totalAmount=hxAmount;
		    }else{
		    	totalAmount=hxAmount-vtotalAmount;
			    if(totalAmount>0){
			    	vpayAmount=vtotalAmount;
			    }else{
			    	vpayAmount= hxAmount;
			    	totalAmount=0;
			    }
		    }
		    if(packagingAmount > 0) {
		    	List<OrderGoodsVO> goodsList = orderReqVO.getGoodsList();
		    	if(goodsList != null && goodsList.size() > 1) {
		    		goodsAmount = goodsAmount - packagingAmount;
			    	String goodsPriceStr = String.valueOf(goodsAmount);
	    			int len = goodsPriceStr.indexOf(".");
	    			if(len != -1 && goodsPriceStr.length() - len > 2) {
	    				//商品价格小数点处理
	    				BigDecimal priceValue = new BigDecimal(goodsPriceStr).setScale(2, BigDecimal.ROUND_HALF_UP);
	    				goodsPriceStr = priceValue.toString();
	    				goodsAmount = Double.parseDouble(goodsPriceStr);
	    			}
		    	} else {
		    		packagingAmount = 0;
		    	}
		    }
		    orderBo.setPackagingAmount(XiuUtil.getPriceDouble2Str(packagingAmount)); //礼品包装金额
		    
		    orderBo.setVtotalAmount(XiuUtil.getPriceDouble2Str(vtotalAmount/100.0));
		    orderBo.setVpayAmount(XiuUtil.getPriceDouble2Str(vpayAmount/100.0));
			orderBo.setTotalAmount(XiuUtil.getPriceDouble2Str(totalAmount/100.0));
			orderBo.setLeftAmount(XiuUtil.getPriceDouble2Str(totalAmount/100.0));
			orderBo.setAmount(XiuUtil.getPriceDouble2Str(amount));
			orderBo.setPromoAmount(XiuUtil.getPriceDouble2Str(favourableAmount));
			orderBo.setFreight(XiuUtil.getPriceDouble2Str(freight));
			orderBo.setGoodsAmount(XiuUtil.getPriceDouble2Str(goodsAmount));
			// 商品列表
			List<OrderGoodsVO> goodsList = new ArrayList<OrderGoodsVO>();
			List<BalanceGoodsInfoDO> goodsInfo = balanceInfo.getGoodsInfo();
			for (BalanceGoodsInfoDO g : goodsInfo) {
				OrderGoodsVO goodsVo = new OrderGoodsVO();
				goodsVo.setGoodsSn(g.getGoodsSn());
				goodsVo.setGoodsQuantity(g.getNumber());
				goodsVo.setZsPrice(Long.toString(g.getReMatchBasePrice()));
				goodsVo.setDiscountPrice(Long.toString(g.getDiscountPrice()));
				goodsVo.setProdSettlementDO(g.getProdSettlementDO());
				goodsVo.setGoodsAmt(g.getDiscountPrice()*g.getNumber());
				goodsList.add(goodsVo);
			}
			
			orderBo.setGoodsList(goodsList);
			//促销活动信息
			List<MktActInfoVo> activityItemList=new ArrayList<MktActInfoVo>();
			//免费送赠品
//			List<MktActInfoVo> freeActList=new ArrayList<MktActInfoVo>();
			//免费送赠品默认的一个赠品
//			String freeGoodsSn="";
			//优惠列表
			List<BalanceActivityInfoDO> balanceList= balanceInfo.getActivityInfo();
			List<String> activityIds=new ArrayList<String>();
			for(BalanceActivityInfoDO balance:balanceList){
				activityIds.add(Long.toString(balance.getActivityId()));
				MktActInfoVo mktActInfo=new MktActInfoVo();
				mktActInfo.setActivityId(Long.toString(balance.getActivityId()));
				mktActInfo.setActivityName(balance.getActivityName());
				mktActInfo.setActivityType(Integer.toString(balance.getActivityType()));
				mktActInfo.setLargess(balance.getLargess());
				mktActInfo.setLargessFlag(balance.getLargessFlag());
				mktActInfo.setLargessMoney(balance.getLargessMoney());
				mktActInfo.setCombinationId(balance.getCombinationId());
				mktActInfo.setLargessNumber(balance.getLargess_number());	
				
				activityItemList.add(mktActInfo);
				//免费送赠品
				/*if(2==balance.getActivityType()&&0==balance.getLargessFlag()){
					freeActList.add(mktActInfo);
					String firstLargess=(String)balance.getLargess()[0];
					freeGoodsSn+=firstLargess.substring(0,firstLargess.length()-4)+",";
				}*/
			}
			orderBo.setActivityList(activityIds);
			orderBo.setMktActInfoList(activityItemList);
		/*	List<GoodsDetailVo> freeMktGiftList=new ArrayList<GoodsDetailVo>();
			if(null!=freeActList&&freeActList.size()>0){
				//获得赠品详情
				freeMktGiftList= getMktGiftList(freeGoodsSn,"0",orderReqVO.getDeviceParams());
			}
				orderBo.setMktGiftList(freeMktGiftList);*/
			
			return orderBo;
		}catch(Exception e){
			logger.error("计算订单出现异常:exception",e);
			throw ExceptionFactory.buildEIRuntimeException(GlobalConstants.RET_CODE_OTHER_MSG,e);
		}
	}
	
	/**
	 * 查询订单包装商品
	 * @param price
	 * @return
	 */
	public OrderGoodsVO getOrderPackagingGoods() {
		OrderGoodsVO orderGoods = null;
		Map packagingMap = goodsManager.getProductPackagingGoods(); //查询包装商品
		String packagingGoodsSn = (String) packagingMap.get("goodsSn");
		if(StringUtils.isNotBlank(packagingGoodsSn)) {
			Map paraMap = new HashMap();
			paraMap.put("deviceType", "1");
			orderGoods = goodsManager.getOrderGoods(packagingGoodsSn, paraMap);
			if(orderGoods != null) {
				orderGoods.setGoodsQuantity(1); //包装商品数量
				orderGoods.setSkuCode((String)packagingMap.get("skuCode"));
				String packagingPrice = goodsManager.getProductPackagingPrice(); //礼品包装价格
				if(StringUtils.isNotBlank(packagingPrice)) {
					if(packagingPrice.indexOf(".") > -1) {
						packagingPrice = packagingPrice.substring(0, packagingPrice.indexOf("."));
					} 
					orderGoods.setZsPrice(packagingPrice);
					orderGoods.setDiscountPrice(packagingPrice);
				}
			}
		}
		return orderGoods;
	}
	 
	//获得赠品详情
	public List<GoodsDetailVo> getMktGiftList(String goodsSns,String giftPrice,Map<String,String> deviceParams){
		List<GoodsDetailVo> mktGiftList=new ArrayList<GoodsDetailVo>();
		String [] goodsSn=goodsSns.split(",");
		for(int i=0;i<goodsSn.length;i++){
			try {
				GoodsDetailVo goodsDetailVo = goodsManager.viewGoodsDetail(goodsSn[i],deviceParams);
				goodsDetailVo.setActivityPrice(giftPrice);
				mktGiftList.add(goodsDetailVo);
			} catch (Exception e) {
				logger.error("获得赠品详情出现异常:exception",e);
			}
		}
		return mktGiftList;
	}
	
	@Override
	public OrderResVO createOrder(OrderReqVO orderReqVO,String activeId,String cpsFromId) throws Exception {
		OrderResVO orderResVo = new OrderResVO();
		logger.info("创建订单信息：orderReqVO="+orderReqVO);
		logger.info("订单信息cps信息：cpsType="+orderReqVO.getCpsType()+",cpsId="+orderReqVO.getCpsId());
		BizOrderDO order = this.assembleBizOrderDO(orderReqVO);
		logger.info("初始化订单数据信息：bizOrderDO"+order);
		if (logger.isDebugEnabled()) {
			logger.debug("invoke remote interface [tc] : bizOrderWriterService.submitOrderFromWCS - 提交订单");
			logger.debug(order);
		}
		//活动Id
		List<OrderActiveDO> activeList =new ArrayList<OrderActiveDO>();
		if(!"".equals(activeId)&&null!=activeId){
			String[] activeIds=activeId.split(",");
			for(int i=0;i<activeIds.length;i++){
				OrderActiveDO orderActivedo=new OrderActiveDO();
				orderActivedo.setActiveId(Long.parseLong(activeIds[i]));//"13258"
				activeList.add(orderActivedo);
			}
			order.setActiveList(activeList);
		}
		//cps推广返利关联的用户Id
		//if(!"".equals(cpsFromId)&&null!=cpsFromId){
			order.setCpsFromId(cpsFromId);
		//}
		
		order.setCallSettlement(true);//是否调用结算接口true：调用结算false：调用促销
		
		BizOrderDO bizOrderDO = eiOrderManager.createOrder(order);
		orderResVo.setRetCode(GlobalConstants.RET_CODE_SUCESSS);
		orderResVo.setOrderNo(bizOrderDO.getOrderCode());
		orderResVo.setOrderAmount(XiuUtil.getPriceDouble2Str(bizOrderDO.getNotAmount() / 100.0));
		orderResVo.setOrderId(bizOrderDO.getOrderId());
		orderResVo.setPayType(bizOrderDO.getPayType());
		return orderResVo;
	}

	@Override
	public PayInfoVO pay(PayReqVO payReqVO)throws Exception {
		PayInfoVO payInfoVO = null;
		if (checkOrderInfo(payReqVO)) {
			// 获取支付信息
			if (PayTypeConstant.WECHAT.equals(payReqVO.getPayType()) 
					|| PayTypeConstant.ALIPAYWIRE.equals(payReqVO.getPayType())
					|| PayTypeConstant.ALIPAY_WIRE_APP.equals(payReqVO.getPayType())
					||PayTypeConstant.CHINAPAY_MOBILE_APP.equals(payReqVO.getPayType())
					||PayTypeConstant.CHINAPAY_MOBILE_WAP.equals(payReqVO.getPayType())
					||PayTypeConstant.WANLITONG_WAP.equals(payReqVO.getPayType())
					||PayTypeConstant.WECHAT_PRO.equals(payReqVO.getPayType())
					||PayTypeConstant.PAYEASE_APPLEPAY.equals(payReqVO.getPayType())
					) {
				payInfoVO = this.toPay(payReqVO);
				if(!GlobalConstants.RET_CODE_SUCESSS.equals(payInfoVO.getRetCode())){
					logger.error("支付出现错误 errorCode:" + payInfoVO.getRetCode() + 
							"payType:" + payReqVO.getPayType());
			
					throw ExceptionFactory.buildBusinessException(ErrConstants.GeneralErrorCode.BIZ_PAY_CENTER_BIZ_ERR, payInfoVO.getErrorMsg(),"调用支付中心出现错误");
				}
			}/* else {
				payInfoVO = toBlankPay(payReqVO);
			}*/
			if (payInfoVO != null && GlobalConstants.RET_CODE_SUCESSS.equals(payInfoVO.getRetCode())) {
				if ("app".equals(payReqVO.getPayMedium())) {
					// 编码，避免"&not"字符串在IO传输中被特殊处理导致乱码。
					payInfoVO.setPayInfo(URLEncoder.encode(payInfoVO.getPayInfo(), "utf-8"));
				}
			} else {
				if(payInfoVO == null) {
					logger.error("发起支付失败");
				} else {
					logger.error("发起支付失败 {errorCode：" + payInfoVO.getRetCode() + ", errorMsg:" + payInfoVO.getErrorMsg() + "}");
				}
			}
		}
		return payInfoVO;
	}
	
	/**
	 * 检查订单信息
	 * @param request 
	 * @param payReqVO
	 * @return
	 */
	private boolean checkOrderInfo(PayReqVO payReqVO) {
		Long userId = Long.valueOf(payReqVO.getUserId());
		int orderId = Integer.valueOf(payReqVO.getOrderId());
		BizOrderDO objBizOrderDO = eiOrderManager.queryOrderAllInfo(userId, orderId);
		if (objBizOrderDO.getOrderId() == orderId) {
			// 支付金额已经处理，单位为 分
			double nAmount = Double.valueOf(payReqVO.getOrderAmount())/100;//折后应付金额
			double oAmount = Double.valueOf(objBizOrderDO.getPayAmount()) / 100;//订单总金额
			double pAmount = Double.valueOf(objBizOrderDO.getNotAmount()) / 100;//应付金额（打折后）
			if (logger.isDebugEnabled()) {
				logger.debug("----------------检查支付金额----------------");
				logger.debug("订单ID: " + payReqVO.getOrderId());
				logger.debug("请求支付金额: " + nAmount);
				logger.debug("订单原始金额: " + oAmount);
				logger.debug("订单应付金额: "+pAmount);
			}
			return  Math.abs(nAmount - pAmount) < .0000001;
		}
		return false;
	}
	
	/**
	 * 银行卡支付
	 * @param request
	 * @param payReqVO
	 * @return InvocationResult
	 * @throws UnsupportedEncodingException
	 */
/*	private PayInfoVO toBlankPay(PayReqVO payReqVO) throws UnsupportedEncodingException {

		String orderId = payReqVO.getOrderId();
		String orderNo = payReqVO.getOrderNo();
		Double orderAmount = Double.valueOf(payReqVO.getOrderAmount());
		String payType = payReqVO.getPayType();
		PayOrder payOrder = new PayOrder();
		payOrder.setOrderId(orderId); // 订单ID
		payOrder.setOrderCode(orderNo); // 订单号
		payOrder.setTradeUserId(payReqVO.getUserId()); // 用户Id
		payOrder.setMerchantId(XiuConstant.PAY_MERCHANT_ID);
		payOrder.setTradeType("0");

		PayOrderItem item = new PayOrderItem();
		item.setChannelCode(payType); // 支付类型
		item.setProtocolVersion("2.0"); // 固定值：2.0
		item.setRequestSeqCode("1");
		item.setTradeAmount(Double.valueOf((orderAmount * 100)).intValue() + ""); // 单位为分
		item.setOrderCode(orderNo);
		item.setOrderId(orderId);

		List<PayTradeItemPara> specialParam = new ArrayList<PayTradeItemPara>();

		PayTradeItemPara itemPara = new PayTradeItemPara();
		itemPara.setParaCode("openType");
		itemPara.setParaValue(payReqVO.getPayMedium());
		specialParam.add(itemPara);

		// 添加回调参数 callBackUrl
		if ("web".equals(payReqVO.getPayMedium())) {
			itemPara = new PayTradeItemPara();
			itemPara.setParaCode("callBackUrl");
			itemPara.setParaValue(payReqVO.getCallbackUrl());
			specialParam.add(itemPara);
		}

		List<PayOrderItem> tradeList = new ArrayList<PayOrderItem>();
		tradeList.add(item);

		item.setSpecialParam(specialParam);
		payOrder.setTradeList(tradeList);

		String orderInfo = JSONObject.toJSONString(payOrder);
		logger.info("===============加密前sign：=============="+orderInfo + XiuConstant.PAY_SIGN_KEY);
		String sign = EncryptUtils.encryptByMD5(orderInfo + XiuConstant.PAY_SIGN_KEY);
		
		// 组装支付链接
		StringBuilder sbURL = new StringBuilder(XiuConstant.REMOTE_URL_PAY);
		sbURL.append("?sign=" + sign);
		sbURL.append("&orderInfo=" + orderInfo);

		String payUrl =sbURL.toString(); //URLEncoder.encode(sbURL.toString(), "UTF-8");
		logger.info("银行卡支付请求URL：" + payUrl);
		PayInfoVO payInfoVo = new PayInfoVO();
		payInfoVo.setRetCode(GlobalConstants.RET_CODE_SUCESSS);
		payInfoVo.setPayInfo(payUrl);
		return payInfoVo;
	}*/

	@Override
	public boolean cancelOrder(CancelOrderVO cancelOrderVO) {
		
		CancelDO cancel = new CancelDO();
		cancel.setIp(cancelOrderVO.getIp());
		cancel.setOrderId(cancelOrderVO.getOrderId());
		cancel.setReason(cancelOrderVO.getReason());
		cancel.setUserId(cancelOrderVO.getUserId());
		cancel.setUserName(cancelOrderVO.getUserName());
		boolean flag = eiOrderManager.cancelOrder(cancel);
		return flag;
	}

	@Override
	public boolean updatePayMethod(PayMethodInParam payMethodInParam) {
		Assert.notNull(payMethodInParam.getUserId());
		Assert.notNull(payMethodInParam.getOrderId());
		Assert.notNull(payMethodInParam.getPayType());
		Long userId = Long.parseLong(payMethodInParam.getUserId());
		int orderId = Integer.parseInt(payMethodInParam.getOrderId());
		
		// 组装成UpdatePayTypeForWapCondition对象，然后更新
		UpdatePayTypeForWapCondition updatePayType = new UpdatePayTypeForWapCondition();
		String newPayType = payMethodInParam.getPayType();
		updatePayType.setNewPayType(newPayType);
		updatePayType.setOrderId(orderId);
		updatePayType.setOperatorId(userId.intValue());
		updatePayType.setOperatorName(payMethodInParam.getLogonName());
		updatePayType.setOperatorIp(payMethodInParam.getIp());
		return eiOrderManager.updatePayTypeByWap(updatePayType);
	}
	
	@Override
	public CheckRepeatedRespVo checkIsRepeatedOrder(OrderListInParam orderListInParam, String skuCode, int quantity,
			String addressId,String orderAmount)throws Exception{
		OrderListOutParam orderListOutParam = orderListService.getOrderListOutParam(orderListInParam);
		List<OrderSummaryVo> orderlist = orderListService.getDelayPayOrderSummaryList(orderListOutParam);
		boolean flag = false;
		CheckRepeatedRespVo checkRepeatedRespVo = new CheckRepeatedRespVo();
		if (orderListOutParam.isSuccess()) {
			QueryUserAddressDetailInParam addrIn = new QueryUserAddressDetailInParam();
			addrIn.setAddressId(addressId);
			AddressOutParam addrOut = addressService.getAddress(addrIn);
			// 获取收货地址信息
			String address = addrOut.getCityCode().concat(" ").concat(addrOut.getAddressInfo());
			String receiver = addrOut.getRcverName();
			for (OrderSummaryVo orderSummaryVo : orderlist) {
				if(orderSummaryVo.getGoodsList().size() == 1){
					CommoSummaryOutParam goodsOut = orderSummaryVo.getGoodsList().get(0);
					String oldSkuCode = goodsOut.getSkuCode();
					String goodsAmount = goodsOut.getGoodsAmount();
					String oldAddress = orderSummaryVo.getAddress();
					String oldReceiver = orderSummaryVo.getReceiver();
					double oldOrderAmt=Double.parseDouble(orderSummaryVo.getPrice());
					if (StringUtils.isNotEmpty(oldSkuCode) && StringUtils.isNotEmpty(goodsAmount)&&
							 StringUtils.isNotEmpty(oldAddress)&&
							 StringUtils.isNotEmpty(oldReceiver)) {
						if(null==orderAmount||"".equals(orderAmount)){
							//兼容老版本
							flag =  oldSkuCode.equals(skuCode) && goodsAmount.equals(String.valueOf(quantity)) 
									&& oldAddress.equals(address) && receiver.equals(oldReceiver);
						}else{
							//考虑订单使用优惠券造成重复订单
							flag = oldSkuCode.equals(skuCode) && goodsAmount.equals(String.valueOf(quantity)) &&
									oldAddress.equals(address) && receiver.equals(oldReceiver) && oldOrderAmt == (Double.parseDouble(orderAmount));
						}
						if (flag) {
							checkRepeatedRespVo.setOrderNo(orderSummaryVo.getOrderNo());
							checkRepeatedRespVo.setOrderAmount(orderSummaryVo.getPrice());
							checkRepeatedRespVo.setOrderId(orderSummaryVo.getOrderId());
							checkRepeatedRespVo.setPayType(orderSummaryVo.getPayType());
							break;
						}
					}
				}
			}
		}
		checkRepeatedRespVo.setRepeated(flag);
		return checkRepeatedRespVo;
	}
	
	// 计算订单开始
	@SuppressWarnings("rawtypes")
	public BalanceOrderInfoDO calcOrderBySaleCenter(OrderReqVO orderReqVo,String channelId) throws Exception {
		BalanceOrderInfoDO boi = new BalanceOrderInfoDO();

		boi.setPayType("1");
		boi.setMobile(1); // 标识来自无线
				
		// 商品信息
		this.assembleGoodsList(boi, channelId, orderReqVo);
		
		// 用户信息
		this.assembleUserInfo(boi, orderReqVo.getUserId(), channelId);
				
		// 优惠券信息
		this.assembleCardInfo(boi, orderReqVo);
		//未登录状态下不添加支付信息
		if(null!=orderReqVo.getUserId()){
			// 支付信息
			this.assemblePayInfo(boi);
		}
		
		
		if (logger.isDebugEnabled()) {
			logger.debug("invoke remote interface [sale] : orderSettleService.orderBalanceService - 订单计算");
			logger.debug(boi);
		}
		
		Result result = eiOrderManager.orderBalanceService(boi);
		BalanceOrderInfoDO balanceOrderInfo = (BalanceOrderInfoDO) result.getDefaultModel();

		if (logger.isDebugEnabled()) {
			logger.debug("result.isSuccess(): " + result.isSuccess());
			logger.debug("result.getResultCode(): " + result.getResultCode());
			logger.debug("result.getDefaultModelKey(): " + result.getDefaultModelKey());
			logger.debug("result.getDefaultModel(): " + result.getDefaultModel());
			logger.debug("result.getError(): " + result.getError());
			logger.debug("result.getErrorMessages(): " + result.getErrorMessages());
			logger.debug("result.getModels(): " + result.getModels());
		}
		
		// 计算正常，但有一些注意项：比如优惠券没用
		if (result.getResultCode() != null && 
				"80".equals(result.getResultCode())) {
			// 返回码为80表示传入优惠券，但是并不适合
			logger.error("该订单不满足优惠券规则,优惠券无法使用，errorCode:" + result.getResultCode());
		}

		if (! result.isSuccess()) {
			// 计算错误的提示信息在这里
			List errors = balanceOrderInfo.getErrorInfo();
			if (errors != null && errors.size() > 0) {
				logger.error("该订单不满足优惠券规则,优惠券无法使用，errorInfo:" + balanceOrderInfo.getErrorInfo());				
			}
		}
		return balanceOrderInfo;
	}
	
	// 构造支付信息
	private void assemblePayInfo(BalanceOrderInfoDO boi) {
		List<BalancePayInfoDO> payInfo = new ArrayList<BalancePayInfoDO>();
		BalancePayInfoDO bpi = new BalancePayInfoDO();
		bpi.setPayId("COD");
		payInfo.add(bpi);
		boi.setPayInfo(payInfo);
	}

	
	// 将传入的商品列表组装成营销中心需要的商品列表对象
	// 此处不验证价格，下订单时会验证，验证价格
	private void assembleGoodsList(BalanceOrderInfoDO boi, String channelId,
			OrderReqVO orderReqVo) throws Exception{
		List<OrderGoodsVO> goodsList = orderReqVo.getGoodsList();
		if (goodsList == null || goodsList.isEmpty()) {
			logger.error("商品列表不能为空");
			throw ExceptionFactory.buildBusinessException(ErrConstants.GeneralErrorCode.BIZ_ORDER_GOODS_EMPTY);
		}
		// 重复goodsSn作为一条记录, 但是数量需要累加
		Map<String, BalanceGoodsInfoDO> goodsMap = new HashMap<String, BalanceGoodsInfoDO>();
		
		for (OrderGoodsVO goods : goodsList) {
			String goodsSn = goods.getGoodsSn();
			int quantity = goods.getGoodsQuantity();
			if (quantity <= 0) {
				logger.error("商品数量有错[" + quantity + "]");
				throw ExceptionFactory.buildBusinessException(ErrConstants.GeneralErrorCode.BIZ_ORDER_GOODS_EMPTY);
			}
						
			if (goodsMap.get(goodsSn) != null) {
				BalanceGoodsInfoDO bgi = goodsMap.get(goodsSn);
				bgi.setNumber(bgi.getNumber() + quantity);
				continue;
			}
			
			// 验证输入
			this.validateGoodsList(goods);
			
			BalanceGoodsInfoDO bgi = new BalanceGoodsInfoDO();
			bgi.setGoodsSn(goodsSn);
			bgi.setNumber(quantity);
			
			// 获取商品详情
			Product product = this.getProductInfo(goodsSn);
			if(product == null){
				throw ExceptionFactory.buildBusinessException(ErrConstants.GeneralErrorCode.BIZ_ORDER_GOODS_EMPTY);
			}
			
			// 判断商品是否上架
			if (! "1".equals(product.getOnSale())) {
				logger.error("商品【" + product.getPrdName() + "】已售罄");
				throw ExceptionFactory.buildBusinessException(ErrConstants.GeneralErrorCode.BIZ_ORDER_GOODS_SOLD_OUT);
			}

			// 判断库存
			String sku = goods.getSkuCode();
			int inventory = this.productService.queryInventoryBySku(sku);
			if (quantity > inventory) {
				if (logger.isDebugEnabled()) {
					logger.debug("商品【" + product.getPrdName() + "】库存不足, 需求:" + quantity + ", 库存:" + inventory);
				}
				logger.info("商品【" + product.getPrdName() + "】库存不足, 需求:" + quantity + ", 库存:" + inventory);
				throw ExceptionFactory.buildBusinessException(ErrConstants.GeneralErrorCode.BIZ_ORDER_GOODS_STOCK_OUT);
			}
			
			// 走秀价		
			Double goodsPrice = product.getPrdOfferPrice() * 100;
			bgi.setXiuPrice(goodsPrice.longValue());
		 
		
			
			// 活动价
			if (product.getPrdActivityPrice() != null) {
				Double activityPrice = product.getPrdActivityPrice() * 100;
				bgi.setActivityPrice(activityPrice.longValue());
				bgi.setActivityPriceType(product.getPrdActivityPriceType());
			} else {
				bgi.setActivityPrice(-100);
			}
									
			// 分类和品牌
			bgi.setCatId(product.getBCatCode());
			bgi.setBrandId(product.getBrandCode());
			
			// 来源
			bgi.setBookFrom(channelId);
			
			/*
			 * 平台商编码
			 * 2012-11-13
			 */
			bgi.setPlatformCode(product.getSupplierCode());
			
			goodsMap.put(goodsSn, bgi);
		}

		List<BalanceGoodsInfoDO> goodsInfo = new ArrayList<BalanceGoodsInfoDO>();
		
		for (Map.Entry<String, BalanceGoodsInfoDO> entry : goodsMap.entrySet()) {
			goodsInfo.add(entry.getValue());
		}
		boi.setGoodsInfo(goodsInfo);
	}
	
	// 组装用户信息
	private void assembleUserInfo(BalanceOrderInfoDO boi, 
			String userId, String channelId) throws Exception {
		List<BalanceUserInfoDO> buiInfo = new ArrayList<BalanceUserInfoDO>();
		BalanceUserInfoDO bui = new BalanceUserInfoDO();
		bui.setOrderNumber(this.queryUserOrderNum(userId));
		bui.setUserId(userId);
		bui.setChannelId(channelId);
		buiInfo.add(bui);
		
		boi.setUserInfo(buiInfo);
	}
	
	public String queryUserOrderNum(String userId) throws Exception {
		Integer count = eiOrderManager.queryUserCountOrderNum(userId);
		if(null != count){
			return count.toString();
		}
		return "0";
	}
	
	// 组装优惠券信息
	private void assembleCardInfo(BalanceOrderInfoDO boi, OrderReqVO orderReqVo) throws Exception {
		String couponNumber = orderReqVo.getCouponNumber();
		if (couponNumber != null && !couponNumber.isEmpty()) {

			List<BalanceCardInfoDO> cardInfo = new ArrayList<BalanceCardInfoDO>();

			couponNumber = couponNumber.trim();

			Long userId = Long.parseLong(orderReqVo.getUserId());
			// 验证优惠券
			this.validateCard(userId, couponNumber, boi.getGoodsInfo());
			// 组装优惠券对象
			BalanceCardInfoDO card = new BalanceCardInfoDO();
			card.setCardId(couponNumber);
			cardInfo.add(card);

			boi.setCardInfo(cardInfo);
		}
	}
	
	// 验证优惠券
	public void validateCard(Long userId, String cardId, 
			List<BalanceGoodsInfoDO> balanceGoodsInfo) throws Exception{
		
		if (cardId == null || cardId.isEmpty()) {
			logger.error("优惠券为空.");
			throw ExceptionFactory.buildBusinessException(ErrConstants.GeneralErrorCode.BIZ_ORDER_CARD_EMPTY);
		}
		
		CardInputParamDO par = new CardInputParamDO();
		par.setChannelID(GlobalConstants.COUPON_CHANNEL_ID);
		// 终端用户类型
		par.setTerminalUser(GlobalConstants.TERMINAL_USER_TYPE);
		// 用户Id
		par.setUserId(userId);
		// 卡号
		par.setCardId(cardId);

		if (balanceGoodsInfo != null && balanceGoodsInfo.size() > 0) {
			
			long amount = 0;
			int size = balanceGoodsInfo.size();
			// 组装商品列表
			RuleGoodsRelationDO[] relatedGoodses = new RuleGoodsRelationDO[size];
			for (int i = 0; i < size; i++) {
				BalanceGoodsInfoDO goods = balanceGoodsInfo.get(i);
				RuleGoodsRelationDO relatedGoods = new RuleGoodsRelationDO();
				relatedGoods.setBrandId(goods.getBrandId());
				relatedGoods.setGoodsCatId(goods.getCatId());
				relatedGoods.setGoodsId(goods.getGoodsSn());
				
				/*
				 * 平台商编码
				 * 2012-11-13
				 */
				relatedGoods.setPlatCode(goods.getPlatformCode());
				
				relatedGoodses[i] = relatedGoods;
				amount += goods.getXiuPrice();
			}
			
			par.setGoodsInfo(relatedGoodses);
			par.setAmount(String.valueOf(amount));
		}
				
		if (logger.isDebugEnabled()) {
			logger.debug("invoke remote interface [sale] : cardService.validateCardCondition - 验证优惠卷卡号");
			logger.debug(par);
		}
		
		CardOutParamDO  result = null;
		try {
			result = this.cardService.validateCard(par);
		} catch (LCException e) {
			logger.error("优惠卷验证接口异常,exception:", e);
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_SALES_CARD_VALIDATE_BIZ_ERR, e);
		}
		
		// 验证失败
		if (! "15".equals(result.getStatus())) {
			logger.error("营销中心处理异常, status:" + result.getStatus());
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_SALES_CARD_VALIDATE_FAILED_ERR, null, "营销中心优惠卷验证失败");
		}
	}

	private Product getProductInfo(String goodsSn) {
		Product product = this.productService.loadProduct(goodsSn);
		if (product == null) {
			logger.error("商品不存在");
			return null;
		}
		return product;
	}
	// 计算订单结束  

	// 创建订单开始
	// 将输入转化为接口需要的 BizOrderDO 对象
	private BizOrderDO assembleBizOrderDO(OrderReqVO orderReqVo) throws Exception {
		BizOrderDO order = new BizOrderDO();
		String channelId = GlobalConstants.CHANNEL_ID;
		order.setOrderStatus(1);  // 1-未审核
		order.setInstallmentFlag(0); // 不分期
		order.setTokenId(orderReqVo.getTokenId());
		order.setIp(orderReqVo.getIp());
		order.setBuyerMessage(orderReqVo.getMessage());
		order.setPayType(orderReqVo.getPaymentMethod());
		
		String orderSource = orderReqVo.getOrderSource();
		if(null == orderSource) orderSource = GlobalConstants.ORDER_FROM_CODE_MOBILE;
		order.setFromCodeId(orderSource);
		// 必须随便传个 orderCode
		order.setOrderCode("xxxxxxxxxxxxxx");
		// 渠道
		OrderChannelDO orderChannel = new OrderChannelDO();
		orderChannel.setChannelId(channelId);
		order.setOrderChannel(orderChannel);
		order.setStoreId(Integer.parseInt(channelId));
		order.setStoreName("xiu");

		// 一些用户相关的信息
		this.assembleBuyerInfo(order, Long.parseLong(orderReqVo.getUserId()));

		logger.info("计算订单信息：orderReqVo="+orderReqVo+",channelId="+channelId);
		// 计算订单
		BalanceOrderInfoDO balanceOrderInfo = this.calcOrderBySaleCenter(orderReqVo,channelId);
		logger.info("计算订单响应信息：balanceOrderInfo"+balanceOrderInfo);
		BalancePayInfoDO payInfo = balanceOrderInfo.getPayInfo().get(0);
		if (logger.isDebugEnabled()) {
			logger.debug("totalAmount : " + payInfo.getTotalAmount()/100.0);
			logger.debug("discountTotalAmount : " + payInfo.getDiscountTotalAmount()/100.0);
			logger.debug("freight : " + payInfo.getActualPayShip()/100.0);
		}
		
		// 商品总金额（走秀价总和）
		order.setItemAmount(payInfo.getTotalAmount());
		// 订单总金额（走秀价总额+运费）
		order.setPayAmount(payInfo.getTotalAmount() + payInfo.getActualPayShip());
		// 还应支付总金额
		order.setNotAmount(payInfo.getDiscountTotalAmount() + payInfo.getActualPayShip());
		// 实际支付金额(包含运费)
		order.setActualAmount(order.getNotAmount());
		//使用虚拟账户金额
		if(null!=orderReqVo.getIsVirtualPay()&&"1".equals(orderReqVo.getIsVirtualPay())){
			order.setBuyerId(Long.parseLong(orderReqVo.getUserId()));
			Double leftAmount=Double.parseDouble(orderReqVo.getLeftAmount())*100;
			order.setNotAmount((Long)leftAmount.longValue());
			order.setPageVirtualPay(orderReqVo.getVtotalAmount()); 
		}
		
		// 商品列表
		int quantity = this.assembleGoodsList(order, orderReqVo, balanceOrderInfo);
		order.setQuantity(quantity);  // 订单总商品数
		
		// 优惠券信息
		this.assembleCardInfo(order,orderReqVo);

		// 地址信息 物流信息
		this.assembleDeliverInfo(order, orderReqVo);
		
		// 发票信息
		this.assembleInvoiceInfo(order, orderReqVo, payInfo.getDiscountTotalAmount());
		
		// 活动信息 ??? by dingchenghong 20140519 18:37
		//this.assembleOrderActivity(order, balanceOrderInfo.getActivityInfo());
		
		// 订单支付方式
		this.assemblePayInfo(order, orderReqVo);
		
		// 订单来源信息
		this.assembleOrderFrom(order, orderReqVo);
		
		// 处理cps或media媒体信息
		this.assembleMediaAndCps(order, orderReqVo);
		
		return order;
	}
	
	// 组装订单来源
	// 增加记录设备号 2012-02-07 
	private void assembleOrderFrom(BizOrderDO order, OrderReqVO orderReqVo) {
		// 设置订单来源 ,默认为2
		// 1：PC端； 2：移动WAP端； 3：ANDROID应用端； 4：IPHONE应用端； 5：IPAD应用端；6：IPHONE特权版本；
		String orderSource = orderReqVo.getOrderSource();
		orderSource = StringUtils.isEmpty(orderSource) ? GlobalConstants.ORDER_FROM_CODE_MOBILE : orderSource;
		order.setFromCodeId(orderSource);

		// 订单特征值（features字段）：设备类型、营销标识、设备号
		// 最终的值应该类似于 deviceType:1;marketingFlag:2;deviceSn:xxxx
		String features = null;
		
		// 设备类型
		String deviceType = orderReqVo.getDeviceType();
		features = "deviceType:" + deviceType + ";";
		
		// 营销标识
		String marketingFlag = orderReqVo.getMarketingFlag();
		if (marketingFlag != null) {
			features += "marketingFlag:" + marketingFlag + ";";
		}
		
		// 设备号
		String deviceSn = orderReqVo.getDeviceSn();
		features += "deviceSn:" + deviceSn + ";";
		
		order.setFeatures(features);
				
		// 暂时保留
		OrderFromDO orderFrom = new OrderFromDO();
		orderFrom.setFromCode(GlobalConstants.ORDER_FROM_CODE_MOBILE);
		order.setOrderFromDO(orderFrom);
	}
	
	
	/**
	 * 组装商品列表
	 * @param order	订单对象
	 * @param input 客户端输入对象
	 * @param balanceOrderInfo 经营销中心计算后返回的订单信息
	 * @return
	 * @throws Exception
	 */
	private int assembleGoodsList(BizOrderDO order, OrderReqVO orderReqVo, BalanceOrderInfoDO balanceOrderInfo) throws Exception{
		List<OrderGoodsVO> goodsList = orderReqVo.getGoodsList();
		if (goodsList == null || goodsList.size() == 0) {
			logger.error("商品列表不能为空");
			throw ExceptionFactory.buildBusinessException(ErrConstants.GeneralErrorCode.BIZ_ORDER_GOODS_EMPTY);
		}
		
		// 总产品数量
		int allQuantity = 0;
		
		List<OrderDetailDO> orderItemList = new ArrayList<OrderDetailDO>();
		order.setOrderItemList(orderItemList);
	 
		 
		// 普通商品
		for (OrderGoodsVO goods : goodsList) {
			// 验证输入
			this.validateGoodsList(goods);
			
			int quantity = goods.getGoodsQuantity();
			
			String goodsSn = goods.getGoodsSn();

			OrderDetailDO orderDetail = new OrderDetailDO();
			orderDetail.setItemCodes(goodsSn);
			orderDetail.setSkuCode(goods.getSkuCode());
			
			orderDetail.setQuantity(quantity);
			orderDetail.setGiftPackType(goods.getGiftPackType()); //礼品包装类型
			
			//设置订单商品的来源  先取商品来源 无则取订单来源 否则取默认值
			String goodsFrom = goods.getGoodsSource();
			if (null == goodsFrom || "".equals(goodsFrom.trim())) {
				goodsFrom = orderReqVo.getGoodsFrom();
			}
			if (null == goodsFrom || "".equals(goodsFrom.trim())){
				goodsFrom = GlobalConstants.GOODS_FROM_DEFAULT;
			}
			orderDetail.setItemFrom(goodsFrom);
			allQuantity += quantity;
			
			// 商品价格通过营销中心计算后的结果给出
			this.assembleGoodsPrice(orderDetail, balanceOrderInfo.getGoodsInfo());
			
			// 组装商品的详情
			this.assembleGoodsDetail(orderDetail, false);
			
			// 礼品包装类型
			
			orderItemList.add(orderDetail);
		}
		return allQuantity;
	}
	
	/**
	 * 组装商品详情
	 * @param orderDetail 商品详情对象
	 * @param isGift      是否赠品
	 * @throws Exception
	 */
	private void assembleGoodsDetail(OrderDetailDO orderDetail, boolean isGift) throws Exception {
		String goodsSn = orderDetail.getItemCodes();
		// 获取商品详情
		Product product = this.productService.loadProduct(goodsSn);
		if (product == null) {
			throw ExceptionFactory.buildBusinessException(ErrConstants.GeneralErrorCode.BIZ_ORDER_GOODS_EMPTY);
		}
		
		orderDetail.setCatCode(product.getBCatCode());
		orderDetail.setBrandCode(product.getBrandCode());

		orderDetail.setItemName(product.getPrdName());
		orderDetail.setItemId(product.getInnerID());
		orderDetail.setBrandName(product.getBrandName());
		orderDetail.setCatName(product.getBCatName());
		
		orderDetail.setPicturePath(Tools.assembleImgUrlForTc(product.getMasterImgUrl()));
		//上一步拿到的图片是主图，这里需要替换成sku对应的图片
		String skuImg = orderDetail.getPicturePath();
		if(StringUtils.isNotBlank(skuImg)){
			skuImg = skuImg.replace(skuImg.substring(
					skuImg.indexOf(goodsSn)+goodsSn.length(), skuImg.lastIndexOf("/")), orderDetail.getSkuCode());
		}
		
		/*// 是赠品
		if (isGift) {
			orderDetail.setLargess(1);  //赠品
			orderDetail.setActivityId("");  //该赠品对应的活动ID
			
			Double zsPrice= product.getPrdOfferPrice() * 100;
			orderDetail.setOriginalPrice(zsPrice.longValue());
			orderDetail.setXiuPrice(orderDetail.getOriginalPrice());
		}*/
		
		// 商品的
		Sku[] skus = product.getSkus();
		for (Sku sku : skus) {
			if (sku.getSkuSN().equals(orderDetail.getSkuCode())) {
				orderDetail.setColor(sku.getColorValue());
				orderDetail.setSize(sku.getSizeValue());
			}
		}
		
		// 一些校验信息
		ProductValidateDO productValidateDO = new ProductValidateDO();
		productValidateDO.setLimitFlag(0);
		orderDetail.setProductValidateDO(productValidateDO);
		
		//发送方式：0:普通商品 ,1:直发, 2:全球速递, 3:香港速递     
		String deliverType = product.getGlobalFlag();
		if(StringUtils.isEmpty(deliverType)){//按照商品中心的定义，返回值可能为空，此时设置为普通商品
			deliverType = "0";
		}
		orderDetail.setDeliverType(Integer.parseInt(deliverType));
		
		/*
		 *  2012-09-17 新增字段supplierCode（供应商编码）
		 *  无逻辑，商品中心给什么值，就把值直接塞给订单中心
		 */
		orderDetail.setSupplierCode(product.getSupplierCode());
	}
	
	/**
	 * 依靠营销中心的返回的商品组装订单中商品价格
	 * @param orderDetail
	 * @param balanceGoodsInfo
	 */
	private void assembleGoodsPrice(OrderDetailDO orderDetail, List<BalanceGoodsInfoDO> balanceGoodsInfoList) {
		String goodsSn = orderDetail.getItemCodes();
		for (BalanceGoodsInfoDO bgi : balanceGoodsInfoList) {
			if (bgi.getGoodsSn().equals(goodsSn)) {				
				// 原价
				orderDetail.setOriginalPrice(bgi.getReMatchBasePrice());
				// 走秀价
				orderDetail.setXiuPrice(bgi.getXiuPrice());
				// 折后价
				orderDetail.setDiscountPrice(bgi.getDiscountPrice());
				// 优惠金额
				orderDetail.setFavorableAmount(bgi.getDiscountAmount());
				
				// 基准价 (价格统一项目添加)
				orderDetail.setBasePrice(bgi.getXiuPrice());
				// 活动价 (价格统一项目添加)
				orderDetail.setActivePrice(bgi.getActivityPrice());
				// 活动价格类型(价格统一项目添加)
				orderDetail.setPriceTag(NumberUtils.toInt(bgi.getActivityPriceType(), 0));
				
				//行邮报关信息
				ProdSettlementDO proSettlement=bgi.getProdSettlementDO();
//				PriceSettlementDO priceSettlement=new PriceSettlementDO();
				orderDetail.setHsCode(proSettlement.getHsCode());
				orderDetail.setCustomsCode(proSettlement.getCustomsCode());
				if(proSettlement.isCustoms()){
					orderDetail.setIsCustoms(1l);
					String activityPriceType =proSettlement.getActivityPriceType();
					if(null!=activityPriceType){
						if("0".equals(activityPriceType)){
							//反之就是促销价或走秀价，显示走秀价的价格组成
							PriceSettlementDO priceSettlement1=proSettlement.getXiuPrice();
							orderDetail.setRealCustomsTax(priceSettlement1.getRealCustomsTax());
							orderDetail.setTransportCost(priceSettlement1.getTransportCost());
							orderDetail.setDealPrice(priceSettlement1.getBasePrice());
							 
						}else{
							//非0就是用户下单支付的价钱是活动价，显示活动价的价格组成
							PriceSettlementDO priceSettlement=proSettlement.getActivityPrice();
							orderDetail.setRealCustomsTax(priceSettlement.getRealCustomsTax());
							orderDetail.setTransportCost(priceSettlement.getTransportCost());
							orderDetail.setDealPrice(priceSettlement.getBasePrice());
							
						}
				}
				}else{
					orderDetail.setIsCustoms(0l);
				}
				
				PriceSettlementDO priceSettlement2=proSettlement.getXiuPrice();
				orderDetail.setGoodsOriginalPrice(priceSettlement2.getBasePrice());
				 
				break;
			}
		}
	}
	
	// 验证商品列表字段
	private void validateGoodsList(OrderGoodsVO goods) throws Exception {
		if (null == goods.getGoodsSn()) {
			logger.error("缺少goodsSn字段");
			throw ExceptionFactory.buildBusinessException(ErrConstants.GeneralErrorCode.BIZ_ORDER_GOODS_SN_EMPTY);
		}
		
		if (null == goods.getSkuCode()) {
			logger.error("缺少skuCode字段");
			throw ExceptionFactory.buildBusinessException(ErrConstants.GeneralErrorCode.BIZ_ORDER_GOODS_SKU_EMPTY);
		}
		
		if (goods.getGoodsQuantity() <= 0) {
			throw ExceptionFactory.buildBusinessException(ErrConstants.GeneralErrorCode.BIZ_ORDER_GOODS_EMPTY);
		}
	}

	// 组装支付方式
	private void assemblePayInfo(BizOrderDO order, OrderReqVO orderReqVo) {
		order.setIsMutilPay(Integer.parseInt(orderReqVo.getIsMutilPay()));
		PayOrderDO orderPay = new PayOrderDO();
		List<PayOrderDO> payOrderList = new ArrayList<PayOrderDO>();
		String payType = orderReqVo.getPaymentMethod();
		String payTitle=Tools.getPayTypeDesc(payType);
		String payTypeCat=Tools.getPayTypeCat(payType);
		// 如果需支付金额为0，设置为支付宝，同时将支付状态设置为已支付
		// 否则可能引起不能出货
		if (order.getNotAmount() == 0) {
			if(null!=orderReqVo.getIsVirtualPay()&&"1".equals(orderReqVo.getIsVirtualPay())){
				payType = "VIRTUAL_CAN";
				payTitle="虚拟账户支付";
				payTypeCat="PAY_VIRTUAL";
				orderPay.setVirtual(1);// 是否使用虚拟账户支付0:没有使用；1：使用了虚拟账户
			}else{
				payType = orderReqVo.getPaymentMethod();
				payTitle=Tools.getPayTypeDesc(payType);
				payTypeCat=Tools.getPayTypeCat(payType);
			}
			order.setPayStatus(1);
		}
		orderPay.setPayType(payType);				
		orderPay.setPayTitle(payTitle);
		orderPay.setPayTypeCat(payTypeCat);
		//如果是多笔支付，则支付记录的支付金额为用户输入的金额
		if(GlobalConstants.MUTIL_PAY_YES == order.getIsMutilPay()
				&& StringUtils.isNotBlank(orderReqVo.getReqAmount())){
			orderPay.setNotAmount(XiuUtil.getPriceLongFormat100(orderReqVo.getReqAmount()));
		}
		
		payOrderList.add(orderPay);
		if(null!=orderReqVo.getIsVirtualPay()&&"1".equals(orderReqVo.getIsVirtualPay())&&order.getNotAmount()>0){
			PayOrderDO vorderPay = new PayOrderDO();
			vorderPay.setPayType("VIRTUAL_CAN");//KBC("VIRTUAL_CAN", "虚拟账户支付可提现")  ,BC("VIRTUAL_NO", "虚拟账户支付不可提现")
			vorderPay.setPayTitle("虚拟账户支付");
			vorderPay.setPayTypeCat("PAY_VIRTUAL");
			vorderPay.setVirtual(1);
			payOrderList.add(vorderPay);
		}
		order.setPayOrderList(payOrderList);
		order.setPayType(orderPay.getPayType());
		order.setPayTypeCat(orderPay.getPayTypeCat());
	}
	
	// 组装物流信息
	private void assembleDeliverInfo(BizOrderDO order, OrderReqVO orderReqVo) throws Exception{
		String addressId = orderReqVo.getAddressId();
		String userId=orderReqVo.getUserId();
		if (addressId == null) {
			logger.error("assembleDeliverInfo --> 缺少地址信息");
			throw ExceptionFactory.buildBusinessException(ErrConstants.GeneralErrorCode.Biz_ORDER_ADDRESS_EMPTY);
		}
		
		// 根据地址ID组装物流地址信息
		DeliverAddressDO deliverAddress = this.getOrderDeliverInfoByAddressId(Long.parseLong(addressId));
		
		// 送货偏好属性
		int deliverTime = 1;
		if (orderReqVo.getDeliverTime() != null) {
			deliverTime = NumberUtils.toInt(orderReqVo.getDeliverTime(), 1);
		}
		deliverAddress.setDeliveryTimePreference("deliverTime_0" + deliverTime);
		//创建订单时将用户身份证信息添加进去
		UserIdentityDTO userIdentityDto = receiverIdService.getUserIdentity(userId,addressId);
		deliverAddress.setAddressId(Long.parseLong(addressId));
		deliverAddress.setIdHeads(userIdentityDto.getIdHeads());
		deliverAddress.setIdNumber(userIdentityDto.getIdNumber());
		deliverAddress.setIdTails(userIdentityDto.getIdTails());
		order.setDeliverAddressDO(deliverAddress);
	}
	
	// 根据用户选择的地址ID从用户中心查询地址信息，转换为订单中心需要的物流对象
	private DeliverAddressDO getOrderDeliverInfoByAddressId(Long addressId) throws Exception {
		// 调用用户中心接口根据地址ID查询地址信息
		// 组装成TC需要的对象
		RcvAddressDTO rcvAddress = eiAddressManager.getRcvAddressInfoById(addressId);
		
		DeliverAddressDO deliverAddress = new  DeliverAddressDO();
		deliverAddress.setFullName(rcvAddress.getRcverName());
		deliverAddress.setPhone(rcvAddress.getPhone());
		deliverAddress.setMobile(rcvAddress.getMobile());
		deliverAddress.setAddress(rcvAddress.getAddressInfo());
		deliverAddress.setPostCode(rcvAddress.getPostCode());
		
		// 只需要传入Code即可
		deliverAddress.setProv(rcvAddress.getProvinceCode());
		deliverAddress.setCity(rcvAddress.getRegionCode());
		deliverAddress.setArea(rcvAddress.getCityCode());
		
		//校验用户收货地址是否有问题
		if(StringUtils.isNotBlank(deliverAddress.getCity())
				&& StringUtils.isNotBlank(deliverAddress.getArea())){
			
			//市和区一样，则把市的末两位变成00
			if(deliverAddress.getArea().equals(deliverAddress.getCity())){
				logger.error("用户收货地址市和区一样，deliverAddress：" + deliverAddress);
				String city = deliverAddress.getCity();
				if(city.length() > 2){
					city = city.substring(0, city.length() - 2);
					city += "00";
					deliverAddress.setCity(city);
				}
			}else if(deliverAddress.getArea().endsWith("00") 
					&& !deliverAddress.getCity().endsWith("00")){ //市和区反了
				logger.error("用户收货地址市和区反了，deliverAddress：" + deliverAddress);
				String city = deliverAddress.getCity();
				String area = deliverAddress.getArea();
				deliverAddress.setCity(area);
				deliverAddress.setArea(city);
			}
		}
		
		return deliverAddress;
	}
	
	// 组装用户的信息
	private void assembleBuyerInfo(BizOrderDO order, Long userId) throws Exception{
		UserBaseDTO user = eiuucManager.getUserBasicInfoByUserId(userId);
		order.setBuyerId(userId);
		order.setCreator(user.getCustName());
		order.setBuyerEmail(user.getEmail());
		order.setBuyerNick(user.getPetName() != null ? user.getPetName() : 
			(user.getMobile() != null ? user.getMobile() : user.getEmail()));
	}
	
	// 组装发票信息
	private void assembleInvoiceInfo(BizOrderDO order, OrderReqVO orderReqVo, long invoiceAmount) {
		Integer invoice=ObjectUtil.getInteger(orderReqVo.getInvoice(), null);
		if (invoice!=null&&invoice>0) {
			//取消发票-2016-01-11
//			OrderInvoiceDO orderInvoice = new OrderInvoiceDO();
//			orderInvoice.setInvoiceAmount(invoiceAmount);
//			orderInvoice.setInvoiceHead(orderReqVo.getInvoiceHeading());
//			orderInvoice.setInvoiceType(invoice);
//			orderInvoice.setContent(orderReqVo.getInvoiceContent());
//			order.setOrderInvoiceDO(orderInvoice);
		}
	}
	
	// 组装优惠卡信息
	private void assembleCardInfo(BizOrderDO order,OrderReqVO orderReqVo){
		String couponNumber = orderReqVo.getCouponNumber();
		if (couponNumber != null && ! couponNumber.isEmpty()) {
			// 暂时只支持一个优惠券
			// 此处不需要验证优惠券，订单计算的时候优惠券已经验证
			order.setCardCode(couponNumber);
		}
	}
	
	// 组装cps或媒体media信息 --- add by dingchenghong 2014-01-03
	private void assembleMediaAndCps(BizOrderDO order, OrderReqVO orderReqVo){
		int cpsType = orderReqVo.getCpsType();
		if(1 == cpsType){
			// 组装media信息
			assembleMediaInfo(order, orderReqVo);
		}else if(0 == cpsType){
			// 组装cps信息
			assembleCpsInfo(order, orderReqVo);
		}
	}
	
	// 组装cps信息 --- add by dingchenghong 2014-01-03
	private void assembleCpsInfo(BizOrderDO order, OrderReqVO orderReqVo){
		CpsOrderDO cps = new CpsOrderDO();
		String cpsId = orderReqVo.getCpsId();
		int cpsType = orderReqVo.getCpsType();
		cps.setCpsId(cpsId);
		cps.setCpsType(cpsType);
		cps.setA_id(orderReqVo.getA_id());
		cps.setU_id(orderReqVo.getU_id());
		cps.setW_id(orderReqVo.getW_id());
		cps.setBid(orderReqVo.getBid());
		cps.setUid(orderReqVo.getUid());
		cps.setAbid(orderReqVo.getAbid());
		cps.setCid(orderReqVo.getCid());
		order.setCpsOrderDO(cps);
	}
	
	// 组装媒体信息  --- add by dingchenghong 2014-01-03
	private void assembleMediaInfo(BizOrderDO order, OrderReqVO orderReqVo){
		MediaOrderDO media = new MediaOrderDO();
		media.setMediaId(orderReqVo.getMediaId());
		String adPosId = orderReqVo.getAdPosId();
		String mediaName = orderReqVo.getMediaName();
		media.setCpsType(orderReqVo.getCpsType());
		if(!StringUtils.isNotEmpty(adPosId) && !StringUtils.isNotEmpty(mediaName)){
			media.setAdPosId(adPosId);
			media.setMediaName(mediaName);
		}
		order.setMediaOrderDO(media);
	}
	// 创建订单结束
	
	// 支付相关 --- 开始
	public PayInfoVO toPay(PayReqVO payReqVo){
		
		this.processPayMedium(payReqVo);
		
		String payAmount = this.processPayRecord(payReqVo);
		payReqVo.setReqAmount(payAmount);
		
		this.processMutilPay(payReqVo);
		
		//是多笔支付则保存每次支付记录
//		if(payReqVo.getIsMutilPay() == GlobalConstants.MUTIL_PAY_YES){
//			this.saveOrderPayRecord(payReqVo);
//		}else{//不是则走老的，更新支付方式
//			// 更新支付的payType --- add by dingchenghong 2014-01-03
//			boolean flag = updatePayType(payReqVo);
//			logger.info("====== updatePayType result:" + flag);
//		}
		String result = this.requestPayCenter(payReqVo);
		logger.info("====== this.requestPayCenter result:" + result);
		if(null == result){
			PayInfoVO payInfoVo = new PayInfoVO();
			payInfoVo.setErrorMsg("调用支付中心出错");
			payInfoVo.setRetCode(ErrConstants.GeneralErrorCode.BIZ_PAY_CENTER_BIZ_ERR);
			return payInfoVo;
		}else{
				String type=payReqVo.getPayType();
				if((type.equals("WeChat")||type.equals("AlipayWire")||type.equals("ALIPAY_WIRE_APP"))&& result.contains("=") && result.contains("&")){
					PayInfoVO payInfoVo = new PayInfoVO();
					payInfoVo.setPayInfo(result);
					payInfoVo.setRetCode(GlobalConstants.RET_CODE_SUCESSS);
					return payInfoVo;
				}else if(type.equals("CHINAPAY_MOBILE_APP")||type.equals("CHINAPAY_MOBILE_WAP")
						||type.equals("WANLITONG_WAP")||type.equals("WeChat_Pro")||type.equals("PAYEASE_APPLEPAY")){
					PayInfoVO payInfoVo = new PayInfoVO();
					payInfoVo.setPayInfo(result);
					payInfoVo.setRetCode(GlobalConstants.RET_CODE_SUCESSS);
					return payInfoVo;
				}else {
					PayInfoVO payInfoVo = new PayInfoVO();
					payInfoVo.setErrorMsg(result);
					payInfoVo.setRetCode(GlobalConstants.RET_CODE_OTHER_MSG);
					return payInfoVo;
			}
			
		}
	}
	
	private String processPayRecord(PayReqVO payReqVo) {
//		String payAmount = null;
//		List<PayOrderDO> payOrderDOList = 
//			eiOrderManager.queryUnPayedRecord(payReqVo.getOrderId());
//		//如果存在未到账的支付记录，则还拿这一笔向支付中心发支付请求
//		if(payOrderDOList != null && payOrderDOList.size() > 0){
//			PayOrderDO payOrderDO = null;
//			for (Iterator<PayOrderDO> iterator = payOrderDOList.iterator(); iterator
//					.hasNext();) {
//				PayOrderDO unPayed = iterator.next();
//				if(!"PAY_VIRTUAL".equals(unPayed.getPayTypeCat())
//						&& !"PAY_COD".equals(unPayed.getPayTypeCat())){
//					payOrderDO = unPayed;
//					break;
//				}
//			}
//			if(payOrderDO != null){
//				payReqVo.setPayOrderId(payOrderDO.getPayOrderId());
//				//如果本次请求的支付方式与未到账的支付方式不一致，则更新
//				if(!payReqVo.getPayType().equals(payOrderDO.getPayType())){
//					// 更新支付的payType --- add by dingchenghong 2014-01-03
//					boolean flag = updatePayType(payReqVo);
//					logger.info("====== updatePayType orderId: " + payReqVo.getOrderId() + "，result:" + flag);
//				}
//				payAmount = XiuUtil.getPriceDouble2Str(payOrderDO.getNotAmount() / 100.0);
//				logger.info("当前存在未到账记录，orderId: " + payReqVo.getOrderId() + "，amount: " + payAmount);
//				return payAmount;
//			}else{
//				//否则新增一条支付记录
//				return this.saveOrderPayRecord(payReqVo);
//			}
//		}else{
//			//否则新增一条支付记录
//			return this.saveOrderPayRecord(payReqVo);
//		}
		
		if(StringUtils.isBlank(payReqVo.getReqAmount())){
			payReqVo.setReqAmount(XiuUtil.getPriceDouble2Str(Long.valueOf(payReqVo.getOrderAmount()) / 100.0));
		}
		
		return this.saveOrderPayRecord(payReqVo);
	}

	private String saveOrderPayRecord(PayReqVO payReqVo) {
		PayOrderDO orderPay = new PayOrderDO();
		String payType = payReqVo.getPayType();
		orderPay.setPayType(payType);				
		if("WANLITONG_WAP".equals(payType)) {
			orderPay.setPayType("PAWLTWAP"); //如果是万里通支付
		}
		String payTitle = Tools.getPayTypeDesc(payType);
		String payTypeCat = Tools.getPayTypeCat(payType);
		orderPay.setPayTitle(payTitle);
		orderPay.setPayTypeCat(payTypeCat);
		
		Double saveAmount = Double.valueOf(payReqVo.getReqAmount());
		Double orderNotAmount = XiuUtil.getPriceDoubleFormat(Double.valueOf(payReqVo.getOrderAmount())  / 100.0);
		//如果请求的金额比订单剩余金额多，则使用剩余金额支付
		if(saveAmount > orderNotAmount){
			saveAmount = orderNotAmount;
		}
		orderPay.setNotAmount(XiuUtil.getPriceLongFormat100(saveAmount.toString()));
		
		orderPay.setUserId(Long.parseLong(payReqVo.getUserId()));
		orderPay.setOrderId(Integer.parseInt(payReqVo.getOrderId()));
		
		int payOrderId = eiOrderManager.saveOrderPayRecord(orderPay);
		payReqVo.setPayOrderId(payOrderId);
		
		logger.info("新增支付记录成功，orderId: " + payReqVo.getOrderId() + "，payOrderId: " + payOrderId + "， amount: " + orderPay.getNotAmount());

		return saveAmount.toString();
	}

	// 更新payType --- add by dingchenghong 2014-01-03
	private boolean updatePayType(PayReqVO payReqVo){
		Assert.notNull(payReqVo.getUserId());
		Assert.notNull(payReqVo.getOrderId());
		Assert.notNull(payReqVo.getPayType());
		
		Long userId = Long.parseLong(payReqVo.getUserId());
		int orderId = Integer.parseInt(payReqVo.getOrderId());
		
		// 组装成UpdatePayTypeForWapCondition对象，然后更新
		UpdatePayTypeForWapCondition updatePayType = new UpdatePayTypeForWapCondition();
		String newPayType = payReqVo.getPayType();
		updatePayType.setNewPayType(newPayType);
		updatePayType.setOrderId(orderId);
		updatePayType.setOperatorId(userId.intValue());
		updatePayType.setOperatorName(payReqVo.getLogonName());
		updatePayType.setOperatorIp(payReqVo.getIp());
		return eiOrderManager.updatePayTypeByWap(updatePayType);
	}
	
	/**
	 *  支付媒介
	 * app - 通过内置支 sdk 支付， 默认
	 * web - 通过 web / wap 方式支付
	 */
	private void processPayMedium(PayReqVO payReqVo) {
		if( null == payReqVo.getPayMedium()){
			payReqVo.setPayMedium("app");
		}
	}
	
	/**
	 * 处理多次支付金额
	 * @param payReqVO
	 */
	private void processMutilPay(PayReqVO payReqVO){
		if(payReqVO.getIsMutilPay() != null 
				&& payReqVO.getIsMutilPay() == GlobalConstants.MUTIL_PAY_YES
				&& StringUtils.isNotBlank(payReqVO.getReqAmount())){
			payReqVO.setOrderAmount(XiuUtil.getPriceLongFormat100(payReqVO.getReqAmount()).toString());
		}
	}

	private String requestPayCenter(PayReqVO payReqVo){
		Map<String, String> params = this.assembleParamsMap(payReqVo);
		
		String payUrl = GlobalConstants.REMOTE_URL_PAY;
		if (logger.isInfoEnabled()) {
			logger.info("[invoke pay center] url: " + payUrl);
			logger.info("[invoke pay center] params: " + params);
		}
		
		String result = null;
		
		try {
			result = HttpUtils.postMethod(payUrl, params, "utf-8");
			logger.info("[invoke pay center] result: " + result);
		} catch (IOException e) {
			logger.error("调用支付中心异常 | payUrl=" + payUrl, e);
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.GeneralErrorCode.BIZ_PAY_CENTER_BIZ_ERR, null, "支付中心错误");
		}
			
		if (logger.isInfoEnabled()) {
			logger.info("[invoke pay center] return: " + result);
		}
		
		return result;
	}
	
	private String assemblePayJson(PayReqVO payReqVo) {
		PayOrder payOrder = this.assemblePayOrder(payReqVo);
		return this.toJson(payOrder);
	}
	
	private Map<String, String> assembleParamsMap(PayReqVO payReqVo) {
		Map<String, String> paramsMap = new LinkedHashMap<String, String>();
		String payJson = this.assemblePayJson(payReqVo);
		String sign = EncryptUtils.encryptByMD5(payJson + GlobalConstants.PAY_SIGN_KEY);
		paramsMap.put("sign", sign);
		paramsMap.put("orderInfo", payJson);
		logger.info("支付paramsMap"+paramsMap);
		logger.info("支付sign"+sign);
		logger.info("支付订单信息："+payJson);
		return paramsMap;
	}
	
	private String toJson(Object o) {
		StringWriter sw = new StringWriter();
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			mapper.writeValue(sw, o);
			return sw.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private PayOrder assemblePayOrder(PayReqVO payReqVo) {
		
		String orderId = payReqVo.getOrderId();
		String orderNo = payReqVo.getOrderNo();
		//Double orderAmount = Double.valueOf(ObjectUtils.toString(payReqVo.getOrderAmount()));
		String payType = payReqVo.getPayType();
		Date createDate=payReqVo.getCreateDate();
		PayOrder payOrder = new PayOrder();
		payOrder.setOrderId(orderId);			// 订单ID
		//传递给支付中心的orderCode规则为orderCode + "_" + x_tm_order_pay记录的主键
//		if(payReqVo.getIsMutilPay() == GlobalConstants.MUTIL_PAY_YES){
			payOrder.setOrderCode(orderNo + "_" + payReqVo.getPayOrderId());			// 订单号
//		}else{
//			payOrder.setOrderCode(orderNo);
//		}
		payOrder.setTradeUserId(payReqVo.getUserId());		// 用户Id
		payOrder.setMerchantId(GlobalConstants.PAY_MERCHANT_ID);
		payOrder.setTradeType("0");
		
		if("WANLITONG_WAP".equals(payType)) {
			payType = "PAWLTWAP"; //如果是万里通支付
		}
		
		PayOrderItem item = new PayOrderItem();
//		item.setChannelCode("AlipayWire");  // 固定值，支付中心定义 // 就是支付类型
		item.setChannelCode(payType);
		item.setProtocolVersion("2.0"); 	// 固定值：2.0
		item.setRequestSeqCode("1");
		item.setTradeType("0");
		//不折腾转错
		//item.setTradeAmount(Double.valueOf((orderAmount * 100)).intValue()+"");			// 单位为分
		item.setTradeAmount(Double.valueOf(payReqVo.getOrderAmount()).intValue()+"");			// 单位为分
		item.setOrderCode(orderNo + "_" + payReqVo.getPayOrderId());
		item.setOrderId(orderId);
		item.setCreatedDate(createDate);
//		item.setOrdIp(payReqVo.getOrdIp());
//		item.setPayMentInfo(payReqVo.getPayMentInfo());
		List<PayTradeItemPara> specialParam = new ArrayList<PayTradeItemPara>();
		item.setSpecialParam(specialParam);
		
		PayTradeItemPara itemPara = new PayTradeItemPara();
		if("PAWLTWAP".equals(payType)) {
			//如果是万里通支付
			itemPara.setParaCode("openType");
			itemPara.setParaValue("wap");
			specialParam.add(itemPara);
		} else {
			itemPara.setParaCode("openType");
			itemPara.setParaValue(payReqVo.getPayMedium());
			specialParam.add(itemPara);
		}
		
		//如果是applePay
		if(PayTypeConstant.PAYEASE_APPLEPAY.equals(payType)) {
			itemPara = new PayTradeItemPara();
			itemPara.setParaCode("payMentInfo");
			String payMent="";
			try {
				payMent = URLEncoder.encode(payReqVo.getPayMentInfo(), "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			itemPara.setParaValue(payMent);
			specialParam.add(itemPara);
			
			itemPara = new PayTradeItemPara();
			itemPara.setParaCode("ordIp");
			itemPara.setParaValue(payReqVo.getOrdIp());
			specialParam.add(itemPara);
		}

		
		// 添加回调参数 callBackUrl
		if ("web".equals(payReqVo.getPayMedium())) {
			String callbackUrl = payReqVo.getCallbackUrl();
			if (callbackUrl.isEmpty()) { // 默认url
				callbackUrl = GlobalConstants.PAY_SUCCESS_CALLBACK_URL;
			}
			
			itemPara = new PayTradeItemPara();
			itemPara.setParaCode("callBackUrl");
			itemPara.setParaValue(callbackUrl);
			
			specialParam.add(itemPara);
		}
		
		//如果是万里通支付，增加参数
		if("PAWLTWAP".equals(payType)) {
			itemPara = new PayTradeItemPara();
			itemPara.setParaCode("memberCode");	//万里通用户ID
			itemPara.setParaValue(payReqVo.getMemberID());
			specialParam.add(itemPara);
			
			itemPara = new PayTradeItemPara();
			itemPara.setParaCode("patk"); //万里通用户Token
			itemPara.setParaValue(payReqVo.getMemberToken());
			specialParam.add(itemPara);
		}
		
		List<PayOrderItem> tradeList = new ArrayList<PayOrderItem>();
		tradeList.add(item);
		payOrder.setTradeList(tradeList);
		return payOrder;
	}
	// 支付相关 --- 结束

	@Override
	public boolean canUserCoupon(String goodsSn) throws Exception{
		ItemBlackParamDO item = new ItemBlackParamDO();
		String itemIds[] = goodsSn.split(",");
		item.setItemIds(itemIds);
		item = eiOrderManager.itemBlackScope(item);
		// 如果商品信息中有不为黑名单商品的则可以使用优惠券
		if (null != item && null != item.getItemIds() && item.getItemIds().length >= itemIds.length) {
			return false;
		} else {
			return true;
		}
	}
	@Override
	public Map<String,Boolean> canUserCouponBatchs(String goodsSn) throws Exception{
		Map<String,Boolean> couponsMap=new HashMap<String,Boolean>();
		ItemBlackParamDO item = new ItemBlackParamDO();
		String itemIds[] = goodsSn.split(",");
		item.setItemIds(itemIds);
		for (String goodSn:itemIds) {
			couponsMap.put(goodSn,true);
		}
		item = eiOrderManager.itemBlackScope(item);
		// 如果商品信息中有为黑名单商品的则可以吧不能使用优惠券
		if (null != item && null != item.getItemIds() ) {
			String[] coupousItemIds=item.getItemIds();
			for (String str:coupousItemIds) {
				couponsMap.put(str, false);
			}
		}
		return couponsMap;
	}
	
	/**
	 * 根据orderId获得用户订单信息
	 */
	@Override
	 public PayReqVO queryOrderBaseInfo(int orderId,String payMedium)throws Exception{
		BizOrderDO orderDo = eiOrderManager.queryOrderBaseInfo(orderId);
		if (null != orderDo) {
			PayReqVO payReqVO = new PayReqVO();
			DeliverAddressDO orderAdr = orderDo.getDeliverAddressDO();
			payReqVO.setOrderNo(orderDo.getOrderCode());
			//得到的是分，这里不作处理，之前已导致542.8 ->542.9999问题
			//payReqVO.setOrderAmount(XiuUtil.getPriceDouble2Str(orderDo.getNotAmount() / 100.0));
			payReqVO.setOrderAmount(Long.toString(orderDo.getNotAmount()));
			payReqVO.setOrderId(Integer.toString(orderDo.getOrderId()));
			payReqVO.setPayMedium((StringUtils.isEmpty(payMedium) ? "web" : payMedium));
			payReqVO.setLoginId(orderDo.getBuyerId());
			payReqVO.setLogonName(orderDo.getBuyerNick());
			payReqVO.setPayStatus(orderDo.getPayStatus());
			payReqVO.setOrderStatus(orderDo.getShowStatusName());
			payReqVO.setOrderStatusCode(orderDo.getShowStatusCode());
			payReqVO.setCreateDate(orderDo.getGmtCreate());
			payReqVO.setIsMutilPay(orderDo.getIsMutilPay());
			payReqVO.setAddressId(orderAdr.getAddressId());
			payReqVO.setReceiveName(orderAdr.getFullName());
			payReqVO.setIdHeads(orderAdr.getIdHeads());
			payReqVO.setIdNumber(orderAdr.getIdNumber());
			payReqVO.setIdTails(orderAdr.getIdTails());
			
			// 订单商品列表
			List<OrderDetailDO> orderItemList = orderDo.getOrderItemList();
			String goodsSn = "";
			if (null != orderItemList && orderItemList.size() > 0) {
				for (int i = 0; i < orderItemList.size(); i++) {
					if (StringUtils.isNotBlank(goodsSn)) {
						goodsSn = goodsSn.concat(",").concat(orderItemList.get(i).getItemCodes());
					}else{
						goodsSn = goodsSn.concat(orderItemList.get(i).getItemCodes());
					}
				}
			}
			payReqVO.setGoodsSn(goodsSn);
			return payReqVO;
		}else{
			return null;
		}
		
	}

	@Override
	public boolean updateTradeEndStatus(String orderId, String userId) throws Exception{
		BizOrderDO orderDo= eiOrderManager.queryOrderBaseInfo(Integer.parseInt(orderId));
		if(null!=orderDo&&500==orderDo.getShowStatusCode()){//已发货
			Result result=eiOrderManager.updateTradeEndStatus(Long.parseLong(orderId), Long.parseLong(userId));
			boolean flag=result.isSuccess();
			if(flag){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
		
	}

	@Override
	public CalculateOrderBo calcShoppingCartGoods(OrderReqVO orderReqVO)
			throws Exception {
		try{
			CalculateOrderBo orderBo = new CalculateOrderBo();
			String channelId = GlobalConstants.CHANNEL_ID;
			BalanceOrderInfoDO balanceInfo = this.calcOrderBySaleCenter(orderReqVO, channelId);
			BalancePayInfoDO payInfo = balanceInfo.getPayInfo().get(0);
			double amount = payInfo.getDiscountTotalAmount() / 100.0;// 小计
			double favourableAmount = payInfo.getActivityDiscountAmount() / 100.0;	// 订单优惠了多少钱 ==优惠券钱 
			 
			orderBo.setAmount(XiuUtil.getPriceDouble2Str(amount)); 
			orderBo.setPromoAmount(XiuUtil.getPriceDouble2Str(favourableAmount));
			// 商品列表
			List<OrderGoodsVO> goodsList = new ArrayList<OrderGoodsVO>();
			List<BalanceGoodsInfoDO> goodsInfo = balanceInfo.getGoodsInfo();
			for (BalanceGoodsInfoDO g : goodsInfo) {
				OrderGoodsVO goodsVo = new OrderGoodsVO();
				goodsVo.setGoodsSn(g.getGoodsSn());
				goodsVo.setGoodsQuantity(g.getNumber());
				goodsVo.setZsPrice(Long.toString(g.getReMatchBasePrice()));
				goodsVo.setDiscountPrice(Long.toString(g.getDiscountPrice()));
				goodsVo.setProdSettlementDO(g.getProdSettlementDO());
				goodsVo.setGoodsAmt(g.getDiscountPrice()*g.getNumber());
				goodsList.add(goodsVo);
			}
			orderBo.setGoodsList(goodsList);
			//促销活动信息
			List<MktActInfoVo> activityItemList=new ArrayList<MktActInfoVo>();
		 
			//优惠列表
			List<BalanceActivityInfoDO> balanceList= balanceInfo.getActivityInfo();
		 
			for(BalanceActivityInfoDO balance:balanceList){
			 
				MktActInfoVo mktActInfo=new MktActInfoVo();
				mktActInfo.setActivityId(Long.toString(balance.getActivityId()));
				mktActInfo.setActivityName(balance.getActivityName());
				mktActInfo.setActivityType(Integer.toString(balance.getActivityType()));
				mktActInfo.setCombinationId(balance.getCombinationId());
				activityItemList.add(mktActInfo);
				 
			}
		 
			orderBo.setMktActInfoList(activityItemList);
		 
			return orderBo;
		}catch(Exception e){
			logger.error("计算购物车商品出现异常:exception",e);
			throw ExceptionFactory.buildEIRuntimeException(GlobalConstants.RET_CODE_OTHER_MSG,e);
		}
	
	}

	public boolean deleteOrder(String orderId, String userId) throws Exception {
		BizOrderDO orderDo= eiOrderManager.queryOrderBaseInfo(Integer.parseInt(orderId));
		//只允许删除订单状态为 已撤销、审核未通过、交易完成、订单完结的订单
		if(null!=orderDo&&(OrderStatus.NEW_ORDER_CANCEL.getCode()==orderDo.getShowStatusCode()
				||OrderStatus.NEW_ORDER_VERIFY_NOT_PASS.getCode()==orderDo.getShowStatusCode()
				||OrderStatus.NEW_ORDER_TRADE_CLOSE.getCode()==orderDo.getShowStatusCode()
				||OrderStatus.NEW_ORDER_CLOSE.getCode()==orderDo.getShowStatusCode())){
			Result result=eiOrderManager.deleteOrder(Long.parseLong(userId), Integer.parseInt(orderId));
			boolean flag=result.isSuccess();
			if(flag){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

	public List<PayForTemplet> getPayForTempletList(Map map) {
		List<PayForTemplet> resultList = payForTempletDao.getPayForTempletList(map);
		if(resultList != null && resultList.size() > 0) {
			for (PayForTemplet vo : resultList) {
				String picPath =  vo.getTempletPic();
	        	int index = 0;
	        	int lastNum = 0;
	        	if(StringUtils.isNotBlank(picPath)) {
	        		index = picPath.lastIndexOf(".");
	            	lastNum = Integer.parseInt(picPath.substring(index-1, index),16);
	                vo.setTempletPic(ImageServiceConvertor.replaceImageDomain(XiuConstant.MOBILE_IMAGE[lastNum%3]+ picPath)); //图片地址拼串
	        	}
			}
		}
		return resultList;
	}
	
	public int getPayForTempletCount(Map map) {
		return payForTempletDao.getPayForTempletCount(map);
	}

	/**
	 * 查询商品购买数量
	 */
	public int getUserBuyGoodsCount(Map map) {
		String userId = (String) map.get("userId");
		String goodsSn = (String) map.get("goodsSn");
		String beginDate = (String) map.get("beginDate");
		String endDate = (String) map.get("endDate");
		//查询商品购买数量
		int counts = eiOrderManager.queryUserBuyGoodsCount(Long.parseLong(userId), goodsSn, beginDate, endDate);
		return counts;
	}

	/**
	 * 查询订单的限购信息
	 */
	public Map getOrderLimitSaleInfo(Map map) {
		Map resultMap = new HashMap();
		String goodsSn = (String) map.get("goodsSn");
		int quantity = (Integer) map.get("quantity");
		int limitSaleNum = -1;
		
		Map limitMap = goodsManager.getGoodsLimitSaleInfo(goodsSn); //查询商品限购信息
		
		if(limitMap != null) {
			limitSaleNum = Integer.parseInt(limitMap.get("limitSaleNum").toString());
			if(limitSaleNum != -1 && limitSaleNum < quantity) {
				//是限购商品，且购买数量超过限购数量
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.OverGoodsLimitSaleNum.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.OverGoodsLimitSaleNum.getErrorMsg());
				return resultMap;
			}
			
			if(limitSaleNum != -1 && map.containsKey("userId")) {
				//限购商品
				if(limitMap.containsKey("lmtSaleStart") && limitMap.containsKey("lmtSaleEnd")) {
					//如果限购时间不为空，则查询此时间范围内用户已购买此商品的数量是否超过限购商品的数量，否则查询所有
					String beginDate = (String) limitMap.get("lmtSaleStart");
					String endDate = (String) limitMap.get("lmtSaleEnd");
					map.put("beginDate", beginDate);
					map.put("endDate", endDate);
				}
				
				//查询用户购买商品的数量
				int buyGoodsNum = getUserBuyGoodsCount(map);
				if(quantity > limitSaleNum - buyGoodsNum) {
					resultMap.put("result", false);
					resultMap.put("errorCode", ErrorCode.OverGoodsLimitSaleNum.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.OverGoodsLimitSaleNum.getErrorMsg());
					return resultMap;
				}
				
				limitSaleNum = limitSaleNum - buyGoodsNum; //更新限购数量
			}
		}
		
		resultMap.put("result", true);
		resultMap.put("limitSaleNum", limitSaleNum);
		return resultMap;
	}

	/**
	 * 查询发票类型列表
	 */
	public List<OrderInvoiceVO> getInvoiceTypeList(Map map) {
		//先查询缓存
		List<OrderInvoiceVO> is=memcachedService.getInvoiceTypesCache(XiuConstant.MOBILE_INVOICE_TYPES_CACHE_KEY);
		if(is==null){
			is=eiOrderManager.queryInvoiceType();
			Map cacheMap=new HashMap();
			cacheMap.put("key", XiuConstant.MOBILE_INVOICE_TYPES_CACHE_KEY);
			cacheMap.put("value",is);
			memcachedService.addInvoiceTypesCache(cacheMap);
		}
		return is;
	}

	@Override
	public OrderPayConfig getOrderPayConfig(double orderAmount) {
		
		OrderPayConfig payConfig = new OrderPayConfig();
		
		OrderSysConfig sysConfig = eiOrderManager.queryOrderSysConfigInfo();
		if(sysConfig != null){
			if(orderAmount >= sysConfig.getLowLimitOfMutilPayAmount()){
				payConfig.setSupportMutilPay(true);
				payConfig.setMinMutilPayAmount(sysConfig.getMinMutilPayAmount());
			}
		}
		
		return payConfig;
	}

	@Override
	public OrderPayConfig getOrderPayConfig(
			OrderDetailOutParam orderDetailOutParam) {
		
		if(orderDetailOutParam.getIsMutilPay() == GlobalConstants.MUTIL_PAY_NO){
			return null;
		}
		
		OrderSysConfig sysConfig = eiOrderManager.queryOrderSysConfigInfo();
		if(sysConfig != null){
			OrderPayConfig  payConfig = new OrderPayConfig();
			payConfig.setSupportMutilPay(true);
			payConfig.setMinMutilPayAmount(sysConfig.getMinMutilPayAmount());
			
			return payConfig;
		}
		return null;
	}

	@Override
	public OrderPayConfig getOrderPayConfig(OrderSummaryOutParam outParam, OrderSysConfig sysConfig) {
		
		if(outParam.getIsMutilPay() == GlobalConstants.MUTIL_PAY_NO || sysConfig == null){
			return null;
		}
		
		OrderPayConfig  payConfig = new OrderPayConfig();
		payConfig.setSupportMutilPay(true);
		payConfig.setMinMutilPayAmount(sysConfig.getMinMutilPayAmount());
		
		return payConfig;
	}

}
