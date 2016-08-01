package com.xiu.mobile.portal.common.constants;

import java.util.HashMap;
import java.util.Map;

/***
 * 优惠券常量属性值
 * @author hejianxiong   参考sale包对应常量说明参数值
 *
 */
public class CardCouponConstant {

	public static Map<String, Object> getCardCouponStatusList(){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("11", "卡激活成功且正常");
		result.put("22", "卡已过期");
		result.put("23", "卡失效,规则作废或冻结");
		result.put("24", "该订单已退券，不能再进行退券操作");
		result.put("25", "退券或补券失败");
		result.put("26", "退券或补券成功");
		result.put("27", "补券次数达到了设定最大值,不能再进行补券");
		result.put("30", "优惠券规则作废或冻结");
		result.put("31", "优惠券不支持该终端");
		result.put("33", "卡激活失败 卡使用的人数已经超过规定的人数");
		result.put("44", "密码错误");
		result.put("55", "卡号不存在");
		result.put("66", "卡已经被激活");
		result.put("77", "卡未被激活");
		result.put("78", "卡被冻结");
		result.put("88", "同批次的卡/券不能重复使用");
		result.put("99", "激活失败，卡尚未生效");
		result.put("98", "此卡/券尚未生效，暂不可使用");
		result.put("83", "订单商品属于活动平台，不可使用卡/券");
		result.put("80", "条件不滿足,订单无法使用优惠券");
		result.put("81", "购买的商品不可以使用此卡/券");
		result.put("85", "优惠券只适用于XIU官网");
		result.put("15", "卡可以使用");
		result.put("16", "订单最低消费额");
		result.put("17", "订单金额超过此卡/券使用金额上限");
		result.put("00", "系统错误");
		result.put("01", "请求参数为空");
		result.put("45", "未接收到商品参数");
		result.put("46", "没有接收到用户参数");
		result.put("47", "没有接收到卡号参数");
		result.put("82", "卡规则没有设定应用到商品 卡不可以使用");
		result.put("56", "接收到的卡号错误");
		result.put("-7", "订单金额小于此卡/券最低使用金额");
		result.put("-2", "卡券冻结状态");
		result.put("-1", "卡券作废状态");
		result.put("0", "卡券启用状态");
		result.put("1", "卡券已激活");
		result.put("2", "卡券已使用");
		result.put("-3", "此优惠券无效，不能使用");
		result.put("-4", "订单信息缺失");
		result.put("-5", "订单商品信息缺失");
		result.put("-6", "订单不满足该优惠券使用条件，此优惠券不能使用");
		result.put("-8", "订单金额超过500元方可使用优惠券");	
		result.put("84", "此卡/券只能用于ebay平台商品");	
		result.put("40", "没有此相关商品");	
		
		return result;
	}
	
}
