/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-17 下午5:20:47 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.mobile.portal.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.commerce.hessian.model.Product;
import com.xiu.commerce.hessian.model.ProductCommonParas;
import com.xiu.commerce.hessian.model.ProductSearchParas;
import com.xiu.common.command.result.Result;
import com.xiu.common.lang.StringUtil;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.utils.DateUtil;
import com.xiu.mobile.portal.constants.GlobalConstants;
import com.xiu.mobile.portal.ei.EIOrderManager;
import com.xiu.mobile.portal.ei.EIProductManager;
import com.xiu.mobile.portal.ei.EIPromotionManager;
import com.xiu.mobile.portal.model.CouponBo;
import com.xiu.mobile.portal.model.CouponVo;
import com.xiu.mobile.portal.model.OrderGoodsVO;
import com.xiu.mobile.portal.service.ICouponService;
import com.xiu.mobile.portal.service.IGoodsService;
import com.xiu.mobile.sales.dointerface.card.vo.CardInfoRequest;
import com.xiu.mobile.sales.dointerface.serivce.MobileSalesServiceFacade;
import com.xiu.sales.common.balance.dataobject.BalanceCardInfoDO;
import com.xiu.sales.common.balance.dataobject.BalanceGoodsInfoDO;
import com.xiu.sales.common.balance.dataobject.BalanceOrderInfoDO;
import com.xiu.sales.common.balance.dataobject.BalancePayInfoDO;
import com.xiu.sales.common.balance.dataobject.BalanceUserInfoDO;
import com.xiu.sales.common.card.dataobject.CardInputParamDO;
import com.xiu.sales.common.card.dataobject.CardListOutParamDO;
import com.xiu.sales.common.card.dataobject.CardOutParamDO;
import com.xiu.sales.common.card.dataobject.RuleGoodsRelationDO;
import com.xiu.sales.common.settle.OrderSettleService;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO 优惠卷业务实现类
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-17 下午5:20:47 
 * ***************************************************************
 * </p>
 */

@Service("couponService")
public class CouponServiceImpl implements ICouponService {
	
	private static final Logger LOG = Logger.getLogger(CouponServiceImpl.class);
	
	@Autowired
	private EIPromotionManager eiPromotionManager;
	@Autowired
	private EIOrderManager eiOrderManager;
	@Autowired
	private EIProductManager productManager;
	@Autowired
	private OrderSettleService orderSettleService;
	@Autowired
	private IGoodsService goodsService;
	@Autowired
	private MobileSalesServiceFacade mSalesServiceFacade;

	@Override
	public String activeCoupon(String cardCode, String activeCode, String userId,
			String loginName) {
		LOG.info("activeCoupon userId=" + userId + ", loginName=" + loginName 
				+ ", cardCode:" + cardCode + ", activeCode:" + activeCode);
		
		// 查询用户订单数
		int orderNum = eiOrderManager.queryUserCountOrderNum(userId);
		
		CardInputParamDO cardInputParamDO = getCardInputParamDO(userId, null, null);
		cardInputParamDO.setCardId(cardCode);
		cardInputParamDO.setCardPwd(activeCode);
		cardInputParamDO.setUserName(loginName);
        cardInputParamDO.setOrderNumber(String.valueOf(orderNum));

        String status = eiPromotionManager.activeCardBody(cardInputParamDO);
		return status;
	}
	
	public Map activeCouponOrCardCode(String cardCode, String activeCode, String userId, String loginName) {
		LOG.info("activeCoupon userId=" + userId + ", loginName=" + loginName 
				+ ", cardCode:" + cardCode + ", activeCode:" + activeCode);
		Map resultMap = new HashMap();
		CardInfoRequest request = new CardInfoRequest();
		request.setCardId(cardCode);
		request.setCardPwd(activeCode);
		request.setTerminalUser("1");  //终端用户(1 手机用户 2 电脑用户)
		request.setUserId(Long.parseLong(userId));
		request.setUserName(loginName);
		
		Result result = mSalesServiceFacade.activateConponsOrCardCode(request);

		if(result != null) {
			resultMap.put("isSuccess", result.isSuccess());
			resultMap.put("resultCode", result.getResultCode());
			resultMap.put("errorMessages", result.getErrorMessages());
		} else {
			resultMap.put("isSuccess", false);
			resultMap.put("resultCode", "");
			resultMap.put("errorMessages", null);
		}
		
		return resultMap;
	}
	
	@Override
	public CouponBo findCouponBoByParam(String userId, Integer listValid, Page<?> page,
			Integer terminal,Integer orderField,Integer orderType,Integer isCanUseInt) {

		// 封装参数
		CardInputParamDO cardInputParamDO = getCardInputParamDO(userId, listValid, page);
		String terminalUser=null;
		if(terminal!=0){
			terminalUser=terminal+"";
			cardInputParamDO.setTerminalUser(terminalUser);
		}
		// 接收参数对象
		CardOutParamDO cardOutParamDo = null;
		List<CouponVo> couponList = new ArrayList<CouponVo>();
		
		// 如果listValid为1 则查询有效的数据信息
		if (1 == listValid) {
			// 接收数据
			cardOutParamDo = eiPromotionManager.findCoupons(cardInputParamDO);
			// 单页记录
			CardListOutParamDO[] cardListOuts = cardOutParamDo.getCardLists();
			couponList = new ArrayList<CouponVo>();
			int total=0;
			if (null != cardListOuts && cardListOuts.length > 0) {
				for (CardListOutParamDO cardListOutParamDO : cardListOuts) {
					CouponVo coupon = new CouponVo(cardListOutParamDO);
					boolean isCanUse=false;//是否过滤开始时间未到的优惠券
					if(isCanUseInt==1&&!StringUtil.isBlank(coupon.getStartTime())){
						Date start=DateUtil.parseTime(coupon.getStartTime());
						if(start.after(new Date())){
							isCanUse=true;
						}
					}
					if(!isCanUse){
						couponList.add(coupon);
						total++;
					}
				}
				page.setTotalCount(total);
			}
		}else{
			// 重新封装参数  查询已使用的优惠券  0(全部) 1(可使用) 2(已使用) 3(已过期) 4无效
			cardInputParamDO = getCardInputParamDO(userId, 4, page);
			// 接收数据
			cardOutParamDo = eiPromotionManager.findCoupons(cardInputParamDO);
			page.setTotalCount(cardOutParamDo.getCount());

			// 单页记录
			CardListOutParamDO[] cardListOuts = cardOutParamDo.getCardLists();
			// 定义一个couponVo set集合 过滤重复数据
			Set<CouponVo> couponVoSets = new HashSet<CouponVo>();
			couponList = new ArrayList<CouponVo>();
			if (null != cardListOuts && cardListOuts.length > 0) {
				for (CardListOutParamDO cardListOutParamDO : cardListOuts) {
					CouponVo coupon = new CouponVo(cardListOutParamDO);
					
					couponVoSets.add(coupon);
				}
			}
			
			
			// 放入到集合中
			couponList.addAll(couponVoSets);
		}

		final Integer orderFieldF=orderField;
		//排序
		  Collections.sort(couponList, new Comparator<CouponVo>() {
		        public int compare(CouponVo arg0, CouponVo arg1) {
		        	int compareValue=0;
		        	if(orderFieldF==0){//激活时间
		        		compareValue= arg1.getActivateTime().compareTo(arg0.getActivateTime());
		        	}else if(orderFieldF==1){//到期时间
		        		compareValue= arg1.getEndTime().compareTo(arg0.getEndTime());
			        }else if(orderFieldF==2){//失效时间
			        	String arg0UserTime=arg0.getUsedTime();
			        	String arg1UserTime=arg1.getUsedTime();
			        	if(arg0UserTime==null||arg0UserTime.length()<1){
			        		arg0UserTime=arg0.getEndTime();
			        	}
			        	if(arg1UserTime==null||arg1UserTime.length()<1){
			        		arg1UserTime=arg1.getEndTime();
			        	}
			        	compareValue=arg1UserTime.compareTo(arg0UserTime);
			        }
					return compareValue;
		        }
		    });
	         
		
		
		CouponBo couponBo = new CouponBo();
		couponBo.setCoupons(couponList);
		couponBo.setActionType(listValid);
		
		couponBo.setPage(page);
		return couponBo;
	}
	
	/**
	 * 封装查询参数（）
	 * @param userId
	 * @param page
	 * @return
	 */
	private CardInputParamDO getCardInputParamDO(String userId, Integer listValid, Page<?> page) {
		CardInputParamDO paramDo = new CardInputParamDO();
		
		// 类型
		if (listValid != null) {
			paramDo.setListValid(listValid);
		} 
		
		// 分页查询
		if(page != null) {
			paramDo.setPageSize(page.getPageSize());
			paramDo.setCurrentPage(page.getPageNo());
		}
		
//		paramDo.setTerminalUser(GlobalConstants.TERMINAL_USER_TYPE);
		paramDo.setUserId(Long.valueOf(userId));
		paramDo.setChannelID(GlobalConstants.COUPON_CHANNEL_ID);

		return paramDo;
	}

	@Override
	public Map<String, Object> validateCardCoupon(String cardCode,long userId,String goodsSn, String channelID,int number) { // 商品数量暂时未做处理  后期修改
		//String[] goodsSnArr = goodsSnStr.split(",");
		LOG.info("优惠券验证开始：cardCode="+cardCode+",userId="+userId+",goodSnStr="+goodsSn+",channelId="+channelID);
		List<RuleGoodsRelationDO> goodsRelationDOList = new ArrayList<RuleGoodsRelationDO>();	
		Map<String, Object>	 result = new HashMap<String, Object>();
		double totalPrice = 0;
		// 获取产品相关数据信息
		ProductSearchParas searchParas = new ProductSearchParas();
		searchParas.setXiuSnList(goodsSn);
		searchParas.setPageStep(50);
		ProductCommonParas commonParas = new ProductCommonParas();
		commonParas.setStoreID(Integer.parseInt(GlobalConstants.STORE_ID));
		Map<String, Object> productResult = productManager.searchProduct(commonParas, searchParas);
		List<Product> products = (List<Product>) productResult.get("Products");
		if (products != null && products.size()>0) {
			// 封装对应的相关参数
			Product product = products.get(0);
			RuleGoodsRelationDO goodsRelationDO = new RuleGoodsRelationDO();
			// 走秀价
			Double goodsPrice = product.getPrdOfferPrice() * 100;
			totalPrice = totalPrice + goodsPrice * number;
			/*
			goodsRelationDO.setAmount(new Double(goodsPrice).longValue());
			goodsRelationDO.setBrandId(product.getBrandCode());
			goodsRelationDO.setBrandName(null);
			goodsRelationDO.setCardRuleExpDO(null);
			goodsRelationDO.setExpType(null);
			goodsRelationDO.setGoodsCatId(product.getBCatCode());
			goodsRelationDO.setGoodsCatName(product.getBCatName());
			goodsRelationDO.setGoodsId(""+product.getInnerID());
			goodsRelationDO.setGoodsName(product.getPrdName());
			goodsRelationDO.setPlatCode(null);
			goodsRelationDO.setAmount((new Double(totalPrice)).longValue());
			goodsRelationDO.setSerial(null);
			goodsRelationDO.setSupplierCode(null);
			*/
			goodsRelationDO.setBrandId(product.getBrandCode());
			goodsRelationDO.setGoodsCatId(product.getBCatCode());
			goodsRelationDO.setGoodsId(product.getXiuSN());
			//添加供应商编码
			goodsRelationDO.setPlatCode(StringUtil.isBlank(product.getSupplierCode())?"":product.getSupplierCode());
			goodsRelationDOList.add(goodsRelationDO);
		}else{
			// 提示没有此走秀码对应的产品
			result.put("result", false);
			result.put("status", "40");
			return result;
		}
		
		// 数组转换
		RuleGoodsRelationDO[] ruleGoodsRelationDOArr =new RuleGoodsRelationDO[goodsRelationDOList.size()];
		goodsRelationDOList.toArray(ruleGoodsRelationDOArr);

		try {
			Long totPrice=(new Double(totalPrice)).longValue(); 
			CardOutParamDO cardOutParamDO = eiPromotionManager.validateCardCoupon(cardCode, userId, ruleGoodsRelationDOArr, channelID, Long.toString(totPrice));
			LOG.info("优惠券验证结果cardOutParamDO"+cardOutParamDO);
			if (cardOutParamDO!=null) {
				if ("16".equalsIgnoreCase(cardOutParamDO.getStatus())) {
					if (Double.valueOf(cardOutParamDO.getLimitAmount()) > totalPrice) {
						result.put("result", false);
						result.put("status", "-7");
					}else{
						result.put("result", true);
						result.put("status", cardOutParamDO.getStatus());
						result.put("message", "验证成功");
					}
				}else if ("15".equalsIgnoreCase(cardOutParamDO.getStatus())) {
					Map<String, Object> validateResult = validateCoupon(cardCode, userId, goodsSn, channelID, number);
					if (Boolean.parseBoolean(validateResult.get("result").toString())) {
						result.put("result", true);
						result.put("status", "15");
						result.put("message", "验证成功");
					}else{
						result.put("result", false);
						result.put("status", "80");
					}
				}else{
					result.put("result", false);
					result.put("status", cardOutParamDO.getStatus());
					result.put("message", "验证失败！响应validateStatus="+cardOutParamDO.getStatus());
				}
			}else{
				result.put("result", false);
				result.put("status", "00");
			}
		} catch (Exception e) {
			LOG.error("优惠券验证异常:exception", e);
			result.put("result", false);
			result.put("status", "00");  // 优惠券响应列表00为系统错误
			result.put("message", e.getMessage());
		}
		return result;
	}

	@Override
	public Map<String, Object> validateCoupon(String cardCode, long userId,String goodsSn, String channelID,int number) {
		List<BalanceGoodsInfoDO> balanceGoodsInfoDOList = new ArrayList<BalanceGoodsInfoDO>();
		BalanceGoodsInfoDO goods = new BalanceGoodsInfoDO();
		
		LOG.info("优惠券第二次验证开始");
		BalanceOrderInfoDO balanceOrderInfoDO = new BalanceOrderInfoDO();
		List<Product> products=goodsService.batchLoadProductsOld(goodsSn);
		if (products != null && products.size() > 0) {
			for (int i = 0; i < products.size(); i++) {
				Product product = products.get(i);
				goods.setGoodsId(product.getInnerID().toString());
				goods.setGoodsSn(product.getXiuSN());
				goods.setXiuPrice(product.getPrdOfferPrice().longValue()*100);
				goods.setActivityPrice(product.getPrdActivityPrice()==null?-100L:product.getPrdActivityPrice().longValue()*100);
				goods.setActivityPriceType(product.getPrdActivityPriceType());
				goods.setPlatformCode(StringUtil.isBlank(product.getSupplierCode())?"":product.getSupplierCode());
				goods.setNumber(number);
				goods.setCatId(product.getBCatCode());
				goods.setBrandId(product.getBrandCode());
				goods.setBookFrom("11");
				balanceGoodsInfoDOList.add(goods);
			}
		}
		
		// user信息封装
		List<BalanceUserInfoDO> userInfoList = new ArrayList<BalanceUserInfoDO>();
		BalanceUserInfoDO balanceUserInfoDO = new BalanceUserInfoDO();
		balanceUserInfoDO.setUserId(String.valueOf(userId));
		balanceUserInfoDO.setOrderNumber("0");
		userInfoList.add(balanceUserInfoDO);
		balanceOrderInfoDO.setUserInfo(userInfoList);
		
		// 支付信息封装
		List<BalancePayInfoDO> balancePayInfoDOs = new ArrayList<BalancePayInfoDO>();
		BalancePayInfoDO balancePayInfoDO = new BalancePayInfoDO();
		balancePayInfoDO.setPayId("PAY_ONLINE");
		balanceOrderInfoDO.setPayType("2");
		balanceOrderInfoDO.setSettlementType("2");
		balanceOrderInfoDO.setMobile(1); // 参数类型  1为手机 0为pc
		balancePayInfoDOs.add(balancePayInfoDO);
		balanceOrderInfoDO.setPayInfo(balancePayInfoDOs);
		
		// 优惠券信息封装
		List<BalanceCardInfoDO> balanceCardInfoDOs = new ArrayList<BalanceCardInfoDO>();
		BalanceCardInfoDO balanceCardInfoDO = new BalanceCardInfoDO();
		balanceCardInfoDO.setCardId(cardCode);
		balanceCardInfoDOs.add(balanceCardInfoDO);
		balanceOrderInfoDO.setCardInfo(balanceCardInfoDOs);
		
		// 计算订单 验证数据信息
		balanceOrderInfoDO.setGoodsInfo(balanceGoodsInfoDOList);
		LOG.info("优惠券第二次验证参数：balanceOrderInfoDO="+balanceOrderInfoDO);
		return eiPromotionManager.validateCoupon(balanceOrderInfoDO);
	}

	@Override
	public Map<String, Object> validateCardCoupon(String cardCode, long userId,String channelID, List<OrderGoodsVO> goodsVOList) {
		LOG.info("优惠券验证开始：cardCode="+cardCode+",userId="+userId+",goodsVOList="+goodsVOList+",channelId="+channelID);
		StringBuilder goodsSnBuilder = new StringBuilder();
		Map<String, OrderGoodsVO> goodsMap = new HashMap<String, OrderGoodsVO>(); 
		for (OrderGoodsVO orderGoodsVO : goodsVOList) {
			goodsMap.put(orderGoodsVO.getGoodsSn(), orderGoodsVO);
			goodsSnBuilder.append(orderGoodsVO.getGoodsSn()).append(",");
		}
		String goodsSnStr = goodsSnBuilder.toString();
		LOG.info("多商品优惠券验证参数：cardCode="+cardCode+",userId="+userId+",goodsSnStr="+goodsSnStr+",channelId="+channelID);
		if (StringUtils.isNotBlank(goodsSnStr) && goodsSnStr.endsWith(",")) {
			goodsSnStr = goodsSnStr.substring(0, goodsSnStr.length()-1);
		}
		List<RuleGoodsRelationDO> goodsRelationDOList = new ArrayList<RuleGoodsRelationDO>();	
		Map<String, Object>	 result = new HashMap<String, Object>();
		double totalPrice = 0;
		// 获取产品相关数据信息
		ProductSearchParas searchParas = new ProductSearchParas();
		searchParas.setXiuSnList(goodsSnStr);
		searchParas.setPageStep(50);
		ProductCommonParas commonParas = new ProductCommonParas();
		commonParas.setStoreID(Integer.parseInt(GlobalConstants.STORE_ID));
		Map<String, Object> productResult = productManager.searchProduct(commonParas, searchParas);
		List<Product> products = (List<Product>) productResult.get("Products");
		if (products != null && products.size()>0) {
			for (Product product : products) {
				// 封装对应的相关参数
				RuleGoodsRelationDO goodsRelationDO = new RuleGoodsRelationDO();
				String goodsSn = product.getXiuSN();
				OrderGoodsVO goodsVO = goodsMap.get(goodsSn);
				// 走秀价
				Double goodsPrice = product.getPrdOfferPrice() * 100;
				totalPrice = totalPrice + goodsPrice * goodsVO.getGoodsQuantity();
				goodsRelationDO.setBrandId(product.getBrandCode());
				goodsRelationDO.setGoodsCatId(product.getBCatCode());
				goodsRelationDO.setGoodsId(product.getXiuSN());
				//添加供应商编码
				goodsRelationDO.setPlatCode(StringUtil.isBlank(product.getSupplierCode())?"":product.getSupplierCode());
				goodsRelationDOList.add(goodsRelationDO);
			}
		}else{
			// 提示没有此走秀码对应的产品
			result.put("result", false);
			result.put("status", "40");
			return result;
		}
		
		// 数组转换
		RuleGoodsRelationDO[] ruleGoodsRelationDOArr =new RuleGoodsRelationDO[goodsRelationDOList.size()];
		goodsRelationDOList.toArray(ruleGoodsRelationDOArr);
	
		try {
			Long totPrice=(new Double(totalPrice)).longValue(); 
			CardOutParamDO cardOutParamDO = eiPromotionManager.validateCardCoupon(cardCode, userId, ruleGoodsRelationDOArr, channelID, Long.toString(totPrice));
			LOG.info("优惠券验证结果cardOutParamDO"+cardOutParamDO);
			if (cardOutParamDO!=null) {
				if ("16".equalsIgnoreCase(cardOutParamDO.getStatus())) {
					if (Double.valueOf(cardOutParamDO.getLimitAmount()) > totalPrice) {
						result.put("result", false);
						result.put("status", "-7");
					}else{
						result.put("result", true);
						result.put("status", cardOutParamDO.getStatus());
						result.put("message", "验证成功");
					}
				}else if ("15".equalsIgnoreCase(cardOutParamDO.getStatus())) {
					Map<String, Object> validateResult = validateCoupon(cardCode, userId, channelID, goodsVOList);
					if (Boolean.parseBoolean(validateResult.get("result").toString())) {
						result.put("result", true);
						result.put("status", "15");
						result.put("message", "验证成功");
					}else{
						result.put("result", false);
						result.put("status", "80");
					}
				}else{
					result.put("result", false);
					result.put("status", cardOutParamDO.getStatus());
					result.put("message", "验证失败！响应validateStatus="+cardOutParamDO.getStatus());
				}
			}else{
				result.put("result", false);
				result.put("status", "00");
			}
		} catch (Exception e) {
			LOG.error("优惠券验证异常:exception", e);
			result.put("result", false);
			result.put("status", "00");  // 优惠券响应列表00为系统错误
			result.put("message", e.getMessage());
		}
		return result;
}

	@Override
	public Map<String, Object> validateCoupon(String cardCode, long userId,
			String channelID, List<OrderGoodsVO> goodsVOList) {
		List<BalanceGoodsInfoDO> balanceGoodsInfoDOList = new ArrayList<BalanceGoodsInfoDO>();
		
		LOG.info("商品优惠券使用规则验证开始：cardCode="+cardCode+",userId="+userId+",goodsVOList="+goodsVOList+",channelId="+channelID);
		StringBuilder goodsSnBuilder = new StringBuilder();
		Map<String, OrderGoodsVO> goodsMap = new HashMap<String, OrderGoodsVO>(); 
		for (OrderGoodsVO orderGoodsVO : goodsVOList) {
			goodsMap.put(orderGoodsVO.getGoodsSn(), orderGoodsVO);
			goodsSnBuilder.append(orderGoodsVO.getGoodsSn()).append(",");
		}
		String goodsSnStr = goodsSnBuilder.toString();
		LOG.info("多商品优惠券验证参数：cardCode="+cardCode+",userId="+userId+",goodsSnStr="+goodsSnStr+",channelId="+channelID);
		if (StringUtils.isNotBlank(goodsSnStr) && goodsSnStr.endsWith(",")) {
			goodsSnStr = goodsSnStr.substring(0, goodsSnStr.length()-1);
		}
		
		Map<String, Product> productMap = new HashMap<String, Product>();
		List<Product> products = goodsService.batchLoadProductsOld(goodsSnStr);
		if (products != null && products.size() > 0) {
			for (int i = 0; i < products.size(); i++) {
				Product product = products.get(i);
				productMap.put(product.getXiuSN(), product);
			}
		}
		
		for (OrderGoodsVO orderGoodsVO : goodsVOList) {
			String goodsSn = orderGoodsVO.getGoodsSn();
			Product product = productMap.get(goodsSn);
			if (product != null) {
				BalanceGoodsInfoDO goods = new BalanceGoodsInfoDO();
				goods.setGoodsId(product.getInnerID().toString());
				goods.setGoodsSn(product.getXiuSN());
				goods.setXiuPrice(product.getPrdOfferPrice().longValue() * 100);
				goods.setActivityPrice(product.getPrdActivityPrice() == null ? -100L : product.getPrdActivityPrice().longValue() * 100);
				goods.setActivityPriceType(product.getPrdActivityPriceType());
				goods.setPlatformCode(StringUtil.isBlank(product.getSupplierCode()) ? "" : product.getSupplierCode());
				goods.setNumber(orderGoodsVO.getGoodsQuantity());
				goods.setCatId(product.getBCatCode());
				goods.setBrandId(product.getBrandCode());
				goods.setBookFrom("11");
				balanceGoodsInfoDOList.add(goods);
			}
		}
		
		BalanceOrderInfoDO balanceOrderInfoDO = new BalanceOrderInfoDO();
		// user信息封装
		List<BalanceUserInfoDO> userInfoList = new ArrayList<BalanceUserInfoDO>();
		BalanceUserInfoDO balanceUserInfoDO = new BalanceUserInfoDO();
		balanceUserInfoDO.setUserId(String.valueOf(userId));
		balanceUserInfoDO.setOrderNumber("0");
		userInfoList.add(balanceUserInfoDO);
		balanceOrderInfoDO.setUserInfo(userInfoList);
		
		// 支付信息封装
		List<BalancePayInfoDO> balancePayInfoDOs = new ArrayList<BalancePayInfoDO>();
		BalancePayInfoDO balancePayInfoDO = new BalancePayInfoDO();
		balancePayInfoDO.setPayId("PAY_ONLINE");
		balanceOrderInfoDO.setPayType("2");
		balanceOrderInfoDO.setSettlementType("2");
		balanceOrderInfoDO.setMobile(1); // 参数类型  1为手机 0为pc
		balancePayInfoDOs.add(balancePayInfoDO);
		balanceOrderInfoDO.setPayInfo(balancePayInfoDOs);
		
		// 优惠券信息封装
		List<BalanceCardInfoDO> balanceCardInfoDOs = new ArrayList<BalanceCardInfoDO>();
		BalanceCardInfoDO balanceCardInfoDO = new BalanceCardInfoDO();
		balanceCardInfoDO.setCardId(cardCode);
		balanceCardInfoDOs.add(balanceCardInfoDO);
		balanceOrderInfoDO.setCardInfo(balanceCardInfoDOs);
		
		// 计算订单 验证数据信息
		balanceOrderInfoDO.setGoodsInfo(balanceGoodsInfoDOList);
		LOG.info("优惠券第二次验证参数：balanceOrderInfoDO="+balanceOrderInfoDO);
		return eiPromotionManager.validateCoupon(balanceOrderInfoDO);
	}

}
