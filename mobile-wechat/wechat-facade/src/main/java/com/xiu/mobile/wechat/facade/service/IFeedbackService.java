package com.xiu.mobile.wechat.facade.service;

import com.xiu.mobile.wechat.facade.model.NotifyResult;

/**
* 维权服务接口
* 
* @author wangzhenjiang
*
* @since  2014年6月17日
*/
public interface IFeedbackService {

	/**
	 * 标记客户的投诉处理状态
	 * <br><br>
	 * 当微信用户发起维权，客服与用户沟通完毕之后，需要调用本接口通知客户更新投诉处理状态。
	 * <br>
	 * @param openId
	 * @param feedbackId
	 * @return NotifyResult
	 */
	NotifyResult updateFeedbackStatus(String openId, String feedbackId);
}
