package com.xiu.mobile.wechat.facade.utils;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.xiu.mobile.wechat.facade.constants.FacadeContants;
import com.xiu.mobile.wechat.facade.model.NotifyResult;

/**
* 消息通知JSON转换工具类
*
* @author wangzhenjiang
*
* @since  2014年5月20日
*/
public class JSONUtil {

	/**
	 * 将JSON转换为NotifyResult对象
	 * 
	 * @param json
	 * 
	 * @return NotifyResult
	 */
	public static NotifyResult convertToNotifyResult(String json) {
		NotifyResult result = new NotifyResult();
		if (StringUtils.isEmpty(json)) {
			return result;
		}
		JSONObject obj = JSONObject.fromObject(json);
		String errorCode = obj.getString("errcode");
		result.setSuccess("0".equals(errorCode));
		result.setErrorCode(errorCode);
		result.setErrorMsg(obj.getString("errmsg"));
		return result;
	}

	/**
	 * 组装模板基本属性
	 * <br><br>
	 * 返回结果{"value":"param","color":"#173177"}
	 * @param value
	 * @return json
	 */
	public static String assembleTemplateAttr(String value) {
		JSONObject param = new JSONObject();
		param.put("value", value);
		param.put("color", FacadeContants.TEMPLATE_FONT_COLOR);
		return param.toString();
	}
}
