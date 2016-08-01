package com.xiu.mobile.portal.common.constants;
import java.util.HashMap;
import java.util.Map;
/**
 * 同官网一样的创建订单验证的详细提示信息
 * @author wangchengqun
 *
 */
public class WCSErrorCode{
	
	public WCSErrorCode() {
		super();
	}

	public String getErrorMsg(String errorCode) {
		Map<String, String> orderResultMap = new HashMap<String, String>();
		orderResultMap.put("SYSTEM_FAIL","系统发生了错误，请稍后重试。");
		orderResultMap.put("USER_VALIDATE_FAIL","用户校验失败，请登录后重试。");// 用户校验失败
		orderResultMap.put("ORDER_COUPON_VALIDATE","优惠券校验失败，请重新核对。");// 优惠券/卡校验失败
		orderResultMap.put("ORDER_VIRTUAL_ERROR","虚拟账户验证失败，请稍后重试。");

		orderResultMap.put("ORDER_PRODUCT_LIMIT","您购买的是限购商品，请修改数量后重新提交！");
		orderResultMap.put("ORDER_PRODUCT_PRICE","您选购的商品价格及优惠信息已做了调整，请重新核对。");
		orderResultMap.put("ORDER_PROMOTION_MATCH","您选购的商品价格及优惠信息已做了调整，请重新核对。");
		orderResultMap.put("ORDER_INVENTORY_LACKING","您选购的商品含有缺货商品，请移除后提交结算。");
		orderResultMap.put("ORDER_ACTIVITY_LIMIT","您选购的商品中含有超出了购买限制的商品，请返回购物袋修改购买数量，重新提交结算。");

		orderResultMap.put("ORDER_ACTIVITY_LIMIT_LACKING","您选购的商品中含有超出了购买限制的商品，请返回购物袋修改购买数量，重新提交结算。");
		orderResultMap.put("ORDER_ACTIVITY_UN_START","您选购的部分商品优惠信息已做了调整，请重新核对。");
		orderResultMap.put("ORDER_ACTIVITY_OVER","您选购的部分商品优惠信息已做了调整，请重新核对。");
		orderResultMap.put("ORDER_ACTIVITY_INVENTORY_LACKING","您选购的商品含有缺货商品，请移除后提交结算。");
		orderResultMap.put("ORDER_ACTIVITY_TOTAL_LIMIT_LACKING","您选购的商品中含有超出了购买限制的商品，请返回购物袋修改购买数量，重新提交结算。");
		orderResultMap.put("ORDER_PRODUCT_TOTALLIMIT","您选购的商品中含有超出了购买限制的商品，请返回购物袋修改购买数量，重新提交结算。");
		orderResultMap.put("ORDER_PRODUCT_NONSUPPORT_INSTALLMENT","您选购商品不支持分期，请重新选购商品。");
		orderResultMap.put("ORDER_PRODUCT_INSTALLMENT_ERR","您选购的分期商品已经不再享受分期付款活动，请重新选购商品。");
		orderResultMap.put("ORDER_PRODUCT_XIU_PRICE_NOT_MATCH","您选购的部分商品价格已做了调整，请重新核对。");
		orderResultMap.put("ORDER_PRODUCT_ONSALE_NOT_MATCH","您选购的商品含有下架商品，请移除后提交结算。");
		orderResultMap.put("ORDER_PRODUCT_PRESALE_NOT_MATCH","您选购商品的预售信息发生了变化，请重新核对");
		orderResultMap.put("ORDER_PRODUCT_INSTALLMENT_NOTMATCH","您选购的分期商品已经不再享受分期付款活动，请重新选购商品。");// 分期信息不匹配，不享受分期
		orderResultMap.put("ORDER_DETAIL_DATA_EMPTY_ERROR","您还没有选择商品，请重新选择商品。");

		orderResultMap.put("ORDER_VIRTUAL_MONEY_NOT_ENOUGH","您的虚拟账户余额不足以支付订单，请重新选择支付方式。");
		orderResultMap.put("ORDER_DETAIL_LESS_THAN_ZERO_ERROR","您的订单应付金额为0.00元，不能下单，请重新核对。");
		orderResultMap.put("ORDER_PRODUCT_INSTALLMENT_NET_ERR","系统发生了错误，请稍后重试。");// 分期信息查询-网络异常
		orderResultMap.put("ORDER_GIFT_MATCH","您选购的商品优惠信息已做了调整，请重新核对。");
		orderResultMap.put("ORDER_NORMAL_EXIST_KILL_GOODS","您选购的商品含有秒杀商品，需单独购买，请返回购物袋重新提交结算。");
		orderResultMap.put("ORDER_PRODUCT_INSTALLMENT_NUMBER_ERR","您选购分期商品只能购买一件，请返回购物袋修改购买数量，重新提交结算。");
		orderResultMap.put("ORDER_VIRTUAL_MONEY_CHANGE","您的虚拟账户余额发生了变化，请重新核对。");
		orderResultMap.put("ORDER_CHANNEL_PAY_NOT_MATCH","您的{0}积分余额发生了变化，请重新核对。");
		orderResultMap.put("ORDER_CHANNEL_PAY_NOT_ENOUGH","您的{0}积分余额发生了变化，请重新核对。");
		orderResultMap.put("ORDER_CHANNEL_NET_ERR","系统发生了错误，请稍后重试。");// 渠道积分网络异常。
		orderResultMap.put("ORDER_GIFT_NOT_MATCH","您选购的商品优惠信息已做了调整，请重新核对。");
		orderResultMap.put("ORDER_GIFT_NUMBER_NOT_MATCH","您选购的商品优惠信息已做了调整，请重新核对。");// 您使用的赠品数量大于订单可送数量，请重新核对
		orderResultMap.put("ORDER_INSTALLMENT_PRESALE","此商品是预售商品无法提交结算，请选购其他商品。");
		orderResultMap.put("ORDER_KILL_PRODUCT_COD","您选购的秒杀商品不支持货到付款 ，请重新核对。");
		orderResultMap.put("ORDER_KILL_PRODUCT_REMIT","您选购的秒杀商品不支持银行付款，请重新核对。");
		orderResultMap.put("ORDER_KILL_PRODUCT_INSTALLMENT","您选购的秒杀商品不支持分期付款，请重新核对。");

		orderResultMap.put("ORDER_BLACK_VALIDATE_FAILURE","同批次的卡/券不能重复使用。");
		orderResultMap.put("ORDER_PRODUCT_DETAIL_NOT_MATCH","您的订单中没有选购商品，请重新核对。");
		orderResultMap.put("ORDER_PRODUCT_DETAIL_QUANTITY_NOT_MATCH","您选购的商品信息已发生的变化，请重新核对。");
		orderResultMap.put("ORDER_PAYORDERLIST_DATA_EMPTY_ERROR","请选择支付方式，请重新核对。");
		
		orderResultMap.put("ORDER_PROMOTION_PAYTYPE_EMPTY","促销计算返回优惠支付方式信息为空");
//		orderResultMap.put("ORDER_PAY_COD_LITTLE","订单最终支付金额小于" + codMixAmount + "元，不支持货到付款支付，请重新选择支付方式。");
		return orderResultMap.get(errorCode);

	}
}