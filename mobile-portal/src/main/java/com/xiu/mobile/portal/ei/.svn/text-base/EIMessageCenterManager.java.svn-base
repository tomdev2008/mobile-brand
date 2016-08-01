package com.xiu.mobile.portal.ei;

import java.util.Map;

import com.xiu.common.command.result.Result;

public interface EIMessageCenterManager {

	/**
	 * 查询该用户下未读消息数
	 * @param xiuUserId
	 * @return
	 */
	public Map<String, Object> queryUnReadMsgCount(Integer xiuUserId);
	
	/**
	 * 查询消息中心分类列表信息
	 * @param xiuUserId
	 * @return
	 */
	public Map<String, Object> queryMsgCenterInfo(Integer xiuUserId);
	
	/**
	 * 查询某个分类下的消息列表
	 * @param xiuUserId
	 * @param categoryId
	 * @return
	 */
	public Map<String, Object> queryMsgListByCategory(Integer xiuUserId, Integer categoryId,int page, int pageSize);
	
	/**
	 * 查询某条消息详情
	 * @param msgId
	 * @return
	 */
	public Map<String, Object> queryMsgDetail(Integer xiuUserId,Integer categoryId, Integer msgId);
	
	/**
	 * 推送的消息，用户点击后，更新为已读
	 * @param xiuUserId
	 * @param categoryId
	 * @param msgId
	 * @return
	 */
	public Map<String, Object> readMsg(Integer xiuUserId, Integer categoryId, Integer msgId);
	
	/**
	 * 查询用户消息分类情况
	 * @param xiuUserId
	 * @return
	 */
	public Map<String, Object> queryMsgCategory(Integer xiuUserId);
	
	/**
	 * 修改用户消息分类
	 * @param xiuUserId
	 * @return
	 */
	public Map<String, Object> updateMsgCategoryStatus(Integer xiuUserId,Integer categoryId,Integer status);
	
	/**
	 * 清空用户所有消息
	 * @param xiuUserId
	 * @return
	 */
	public Map<String, Object> cleanUserMsg(Integer xiuUserId);
}
