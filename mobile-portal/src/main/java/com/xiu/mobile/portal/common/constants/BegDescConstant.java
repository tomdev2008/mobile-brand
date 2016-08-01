package com.xiu.mobile.portal.common.constants;

import java.util.HashMap;
import java.util.Map;

public class BegDescConstant {
	
	
	//输入参数为null  天王盖地虎！
	public static final String  INPUT_PARAMES_IS_NULL="101";
	
	//必填属性校验失败  每一步都必须脚踏实地！
	public static final String REQUIRE_FIELD_VALIDATE_FAIL="102";
	
	//内部系统错误,300开始  代表的本系统的操作数据库异常     程序猿不在状态！
	public static final String SYSTEM_INNER_ERROR="300";

	//外部系统错误,400开始 ,调用外部接口      外星人来了，我们休息下！
	public static final String SYSTEM_OUTER_ERROR="400";

	/***
	 * 获取讨赏数据信息
	 * @return
	 */
	public static Map<String, String> getDescList(){
		
		// 用户讨赏提示信息集合
		Map<String, String> result = new HashMap<String, String>();	
		
		//成功	不做提示
		result.put("0", "");
		
		//输入参数为null  天王盖地虎！
		result.put("101", "天王盖地虎！");
		
		//必填属性校验失败  每一步都必须脚踏实地！
		result.put("102", "每一步都必须脚踏实地！");
		
		//活动ID必须是正整数  你别调皮哟！
		result.put("201", "你别调皮哟！");
		
		//数据被篡改了  你别调皮哟！
		result.put("103", "你别调皮哟！");
		
		//无效的微信活动,活动不存在   我们去火星了！
		result.put("202", "我们去火星了！");
		
		//活动不处于进行中 活动未开始！
		result.put("203", "活动未开始！");
		
		//该活动下没有可用的优惠劵 你的运气不好，都被抢光了哦！
		result.put("204", "你的运气不好，都被抢光了哦！");
		
		//活动传播人数达到受限人数  你来晚了哦！
		result.put("205", "你来晚了哦！");
		
		//活动传播人者不存在 他已经穿越了！
		result.put("206", "他的页面未加载完成就分享或微信未升级到6.1及以上版本，请帮忙提醒下他哦。");
		
		//活动下传播者配置不能获取优惠劵  神秘礼品与你无缘！
		result.put("207", "神秘礼品与你无缘！");
		
		//活动下传播者支持金额不够,不能送劵  还要继续加油哦！
		result.put("208", "还要继续加油哦！");
		
		//活动下传播者,已经送劵   哈，你已经领取过礼品了！
		result.put("209", "哈，你已经领取过礼品了！");
		
		//活动下传播者,送劵失败   系统开了个小差！
		result.put("210", "系统开了个小差！");
		
		//无效的微信活动类型   
		result.put("211", "无效的微信活动类型！");

		//内部系统错误,300开始  代表的本系统的操作数据库异常     程序猿不在状态！
		result.put("300", "程序猿不在状态！");

		//外部系统错误,400开始 ,调用外部接口      外星人来了，我们休息下！
		result.put("400", "外星人来了，我们休息下！");

		//微信活动传播者与支持者是同一个用户      不支持左右手互搏
		result.put("220", "不支持左右手互搏");

		//活动下支持者总人数达到限制数   来晚啦，参与人数已满！
		result.put("221", "来晚啦，参与人数已满！");

		//活动下支持者配置不能获取优惠劵  你走错门了！
		result.put("222", "你走错门了！");
		
		//活动支持者不存在    他不在地球！
		result.put("223", "他不在地球！");

		//活动下支持者已经送劵,不能再送  不要太贪心哦！
		result.put("224", "不要太贪心哦！");

		//活动下支持者等待支付中,还不能送劵   还差一步就能拿到礼品啦！
		result.put("225", "还差一步就能拿到礼品啦！");

		//活动下单人支持者人数达到限制数 人气好旺，你晚了一步！
		result.put("226", "人气好旺，你晚了一步！");

		//活动下支持者为传播者打赏已满,不能再打赏该传播者  人气好旺，你晚了一步！
		result.put("227", "人气好旺，你晚了一步！");
		
		//支持者对传播者重复打赏  土豪也不要乱花钱哦！
		result.put("228", "土豪也不要乱花钱哦！");
		
		return result;
	}
	
	/***
	 * 通过code获取message
	 * @param code
	 * @return
	 */
	public static String getMessageByCode(String code){
		Map<String, String> result = getDescList();
		if (result.containsKey(code)) {
			return result.get(code);
		}else {
			return "";
		}
		
	}
	
}
