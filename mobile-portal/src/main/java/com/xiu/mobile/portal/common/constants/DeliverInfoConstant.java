package com.xiu.mobile.portal.common.constants;

import java.util.HashMap;
import java.util.Map;

/***
 * 发货信息
 * @author hejianxiong
 *
 */
public class DeliverInfoConstant {

	public static Map<String, DeliverInfo> getDeliverInfoList(){
		Map<String, DeliverInfo> result = new HashMap<String, DeliverInfo>();
		result.put("100", new DeliverInfo(100, "参 考 价", "国内", "2-5", "China"));
		result.put("101", new DeliverInfo(101, "参 考 价", "国内", "2-5", "China"));
		result.put("102", new DeliverInfo(102, "参 考 价", "国内", "2-5", "China"));
		result.put("103", new DeliverInfo(103, "参 考 价", "国内", "2-5", "China"));
		result.put("201", new DeliverInfo(201, "韩国零售价", "韩国·首尔", "5-9", "Korea"));
		result.put("202", new DeliverInfo(202, "韩国零售价", "韩国·首尔", "5-9", "Korea"));
		result.put("203", new DeliverInfo(203, "韩国零售价", "韩国·首尔", "5-9", "Korea"));
		result.put("300", new DeliverInfo(300, "美国零售价", "美国·达拉斯", "7-14", "America"));
		result.put("301", new DeliverInfo(301, "美国零售价", "美国·达拉斯", "7-9", "America"));
		result.put("302", new DeliverInfo(302, "美国零售价", "美国·达拉斯", "7-14", "America"));
		result.put("303", new DeliverInfo(303, "美国零售价", "美国·达拉斯", "7-14", "America"));
		result.put("400", new DeliverInfo(400, "香港零售价", "香港", "4-7", "Hongkong"));
		result.put("401", new DeliverInfo(401, "香港零售价", "香港", "4-7", "Hongkong"));
		result.put("402", new DeliverInfo(402, "香港零售价", "香港", "4-7", "Hongkong"));
		result.put("403", new DeliverInfo(403, "香港零售价", "香港", "4-7", "Hongkong"));
		result.put("501", new DeliverInfo(501, "英国零售价", "英国·柏京", "7-9", "Britain"));
		result.put("502", new DeliverInfo(502, "英国零售价", "英国·伦敦", "7-14", "Britain"));
		result.put("503", new DeliverInfo(503, "英国零售价", "英国·伦敦", "7-14", "Britain"));
		result.put("504", new DeliverInfo(504, "英国零售价", "英国·巴克斯顿", "7-9", "Britain"));
		result.put("601", new DeliverInfo(601, "日本零售价", "日本·东京", "5-9", "Japan"));
		result.put("701", new DeliverInfo(701, "意大利零售价", "意大利·米兰", "7-9", "Italy"));
		result.put("702", new DeliverInfo(702, "意大利零售价", "意大利·米兰", "7-9", "Italy"));
		result.put("801", new DeliverInfo(801, "法国零售价", "法国·巴黎", "3-7", "France"));
		result.put("901", new DeliverInfo(901, "西班牙零售价", "西班牙.马德里", "7-14", "Spain"));
		result.put("1001", new DeliverInfo(1001, "瑞士零售价", "瑞士.苏黎世", "7-9", "Swit"));
		result.put("1101", new DeliverInfo(1101, "台湾零售价", "台湾", "7-9", "Taiwan"));
		result.put("1201", new DeliverInfo(1201, "德国零售价", "德国.莱比锡", "7-14", "Germany"));

		return result;
	}
	
}
