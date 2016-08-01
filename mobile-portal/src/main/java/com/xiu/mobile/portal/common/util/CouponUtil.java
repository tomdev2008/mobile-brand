package com.xiu.mobile.portal.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 *************************************************************** 
 * <p>
 * 
 * @DESCRIPTION : 优惠券工具类
 * @AUTHOR : Liang Yongxu@xiu.com
 * @DATE :2012-4-26 上午10:26:49
 *       </p>
 **************************************************************** 
 */
public class CouponUtil {

	public static final Map<String, String> ACTIVE_RESULT;

	static {
		ACTIVE_RESULT = new HashMap<String, String>();
		ACTIVE_RESULT.put("0", "系统繁忙，请稍后再试！");
		ACTIVE_RESULT.put("11", "您输入的卡/券号激活成功");
		ACTIVE_RESULT.put("15", "订单可以使用此卡");
		ACTIVE_RESULT.put("16", "订单最低消费额");
		ACTIVE_RESULT.put("17", "优惠卷消费金额已超过了限制消费金额上限,不能使用");
		ACTIVE_RESULT.put("22", "您输入的卡/券号已过期");
		ACTIVE_RESULT.put("23", "您输入的卡/券号已失效");
		ACTIVE_RESULT.put("33", "您输入的卡/券号已超过规定使用人数");
		ACTIVE_RESULT.put("44", "您输入的卡/券号或密码错误");
		ACTIVE_RESULT.put("45", "系统商品异常,验证失败");
		ACTIVE_RESULT.put("46", "系统用户异常,验证失败");
		ACTIVE_RESULT.put("47", "系统卡号异常,验证失败");
		ACTIVE_RESULT.put("55", "您输入的卡/券号或密码错误");
		ACTIVE_RESULT.put("66", "您输入的卡/券号已被激活");
		ACTIVE_RESULT.put("77", "卡未激活");
		ACTIVE_RESULT.put("78", "您输入的卡/券号已失效");
		ACTIVE_RESULT.put("81", "订单不满足该优惠券使用条件，此优惠券不可用");
		ACTIVE_RESULT.put("82", "系统商品设置错误");
		ACTIVE_RESULT.put("88", "已超过限定使用次数");
		ACTIVE_RESULT.put("98", "卡活动尚未发布或优惠券未到生效日期，暂不能使用");
		ACTIVE_RESULT.put("99", "激活失败，卡尚未生效");
		ACTIVE_RESULT.put("-1", "用户未登入");
		ACTIVE_RESULT.put("-2", "请输入优惠券号");
		ACTIVE_RESULT.put("-3", "此优惠券无效，不能使用");
		ACTIVE_RESULT.put("-4", "订单信息缺失");
		ACTIVE_RESULT.put("-5", "订单商品信息缺失");
		ACTIVE_RESULT.put("-6", "订单不满足该优惠券使用条件，此优惠券不能使用");
		ACTIVE_RESULT.put("-7", "订单金额不满足使用该券，最小金额");
		ACTIVE_RESULT.put("-8", "订单金额超过500元方可使用优惠券");
		ACTIVE_RESULT.put("28", "此券为新用户券，您已是走秀会员，不支持使用");
		ACTIVE_RESULT.put("29", "您账户级别暂不支持使用，购买首单之后即可使用！");
		ACTIVE_RESULT.put("21", "优惠券激活失败，没找到优惠券相关信息或优惠券不支持该终端使用！");
		ACTIVE_RESULT.put("79", "优惠券已作废！");
		ACTIVE_RESULT.put("84", "优惠券无法使用，该优惠券只适用于Ebay平台商品");
		ACTIVE_RESULT.put("100", "超过优惠券激活截至时间");
		ACTIVE_RESULT.put("101", "同一个用户在一个规则下可激活优惠券张数已达到最大值");
	}

}
