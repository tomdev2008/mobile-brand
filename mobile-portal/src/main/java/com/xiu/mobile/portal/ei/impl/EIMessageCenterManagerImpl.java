package com.xiu.mobile.portal.ei.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xiu.common.command.result.Result;
import com.xiu.message.common.dointerface.XiuMessageFacade;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.ei.EIMessageCenterManager;

@Service("eIMessageCenterManager")
public class EIMessageCenterManagerImpl implements EIMessageCenterManager {
	private static Logger logger = Logger.getLogger(EIMessageCenterManagerImpl.class);

	@Resource(name="xiuMessageServiceFacade")
	private XiuMessageFacade xiuMessageFacade;
	
	@Override
	public Map<String, Object> queryUnReadMsgCount(Integer xiuUserId) {
		Map<String, Object> returnMap = new HashMap<String, Object>();		
		try{
			Result result = xiuMessageFacade.queryUnReadMsgCount(xiuUserId);
			if(result.isSuccess()){
				returnMap.put("result", true);
				returnMap.put("errorCode", "0");
				returnMap.put("errorMsg", "成功");
				returnMap.put("unReadQty", result.getDefaultModel());
			}else{
				returnMap.put("result", false);
				Set<String> keySet = result.getErrorMessages().keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					returnMap.put("errorCode", key);
					returnMap.put("errorMsg", result.getErrorMessages().get(key));
				}
			}
			logger.info("查询该用户下未读消息数返回结果：" + returnMap);
		}catch(Exception e){
			logger.error("查询该用户下未读消息数发生异常", e);
			returnMap.put("result", false);
			returnMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			returnMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> queryMsgCenterInfo(Integer xiuUserId) {
		Map<String, Object> returnMap = new HashMap<String, Object>();		
		try{
			Result result = xiuMessageFacade.queryMsgCenterInfo(xiuUserId);
			if(result.isSuccess()){
				returnMap.put("result", true);
				returnMap.put("errorCode", "0");
				returnMap.put("errorMsg", "成功");
				returnMap.put("messageCenter", JSON.parseArray(result.getDefaultModel().toString()));
			}else{
				returnMap.put("result", false);
				Set<String> keySet = result.getErrorMessages().keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					returnMap.put("errorCode", key);
					returnMap.put("errorMsg", result.getErrorMessages().get(key));
				}
			}
			logger.info("查询消息中心分类列表信息返回结果：" + returnMap);
		}catch(Exception e){
			logger.error("查询消息中心分类列表信息发生异常", e);
			returnMap.put("result", false);
			returnMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			returnMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> queryMsgListByCategory(Integer xiuUserId, Integer categoryId,int page, int pageSize) {
		Map<String, Object> returnMap = new HashMap<String, Object>();		
		try{
			Result result = xiuMessageFacade.queryMsgListByCategory(xiuUserId, categoryId,page,pageSize);
			if(result.isSuccess()){
				returnMap.put("result", true);
				returnMap.put("errorCode", "0");
				returnMap.put("errorMsg", "成功");
				returnMap.put("totalPage", result.getModels().get("totalPage"));
				returnMap.put("categoryName", result.getModels().get("categoryName"));
				returnMap.put("msgCategoryList", JSON.parseArray(result.getModels().get("msgCategoryList").toString()));
			}else{
				returnMap.put("result", false);
				Set<String> keySet = result.getErrorMessages().keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					returnMap.put("errorCode", key);
					returnMap.put("errorMsg", result.getErrorMessages().get(key));
				}
			}
			logger.info("查询某个分类下的消息列表返回结果：" + returnMap);
		}catch(Exception e){
			logger.error("查询某个分类下的消息列表发生异常", e);
			returnMap.put("result", false);
			returnMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			returnMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> queryMsgDetail(Integer xiuUserId,Integer categoryId, Integer msgId) {
		// TODO Auto-generated method stub
		Map<String, Object> returnMap = new HashMap<String, Object>();		
		try{
			Result result = xiuMessageFacade.queryMsgDetail(xiuUserId,categoryId,msgId);
			if(result.isSuccess()){
				returnMap.put("result", true);
				returnMap.put("errorCode", "0");
				returnMap.put("errorMsg", "成功");
				returnMap.put("msgDetail", JSON.parseObject(result.getDefaultModel().toString()));
			}else{
				returnMap.put("result", false);
				Set<String> keySet = result.getErrorMessages().keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					returnMap.put("errorCode", key);
					returnMap.put("errorMsg", result.getErrorMessages().get(key));
				}
			}
			logger.info("查询某条消息详情返回结果：" + returnMap);
		}catch(Exception e){
			logger.error("查询某条消息详情发生异常", e);
			returnMap.put("result", false);
			returnMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			returnMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return returnMap;
	}

	/**
	 * 更新消息已读
	 */
	@Override
	public Map<String, Object> readMsg(Integer xiuUserId, Integer categoryId, Integer msgId) {
		// TODO Auto-generated method stub
		Map<String, Object> returnMap = new HashMap<String, Object>();		
		try{
			Result result = xiuMessageFacade.readMsg(xiuUserId,categoryId,msgId);
			if(result.isSuccess()){
				returnMap.put("result", true);
				returnMap.put("errorCode", "0");
				returnMap.put("errorMsg", "成功");
			}else{
				returnMap.put("result", false);
				Set<String> keySet = result.getErrorMessages().keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					returnMap.put("errorCode", key);
					returnMap.put("errorMsg", result.getErrorMessages().get(key));
				}
			}
			logger.info("更新消息已读详情返回结果：" + returnMap);
		}catch(Exception e){
			logger.error("更新消息已读详情返回结果发生异常", e);
			returnMap.put("result", false);
			returnMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			returnMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> queryMsgCategory(Integer xiuUserId) {
		// TODO Auto-generated method stub
		Map<String, Object> returnMap = new HashMap<String, Object>();		
		try{
			Result result = xiuMessageFacade.queryMsgCategory(xiuUserId);
			logger.info("xiuUserId:"+xiuUserId+",查询消息分类结果:"+result.getModels().get("categoryList").toString());
			if(result.isSuccess()){
				returnMap.put("result", true);
				returnMap.put("errorCode", "0");
				returnMap.put("errorMsg", "成功");
				returnMap.put("categoryList", JSON.parseArray(result.getModels().get("categoryList").toString()));
			}else{
				returnMap.put("result", false);
				Set<String> keySet = result.getErrorMessages().keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					returnMap.put("errorCode", key);
					returnMap.put("errorMsg", result.getErrorMessages().get(key));
				}
			}
			logger.info("查询分类列表返回结果：" + returnMap);
		}catch(Exception e){
			logger.error("查询分类列表返回结果", e);
			returnMap.put("result", false);
			returnMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			returnMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return returnMap;
	}

	/**
	 * 更新用户分类状态
	 */
	@Override
	public Map<String, Object> updateMsgCategoryStatus(Integer xiuUserId,
			Integer categoryId,Integer status) {
		// TODO Auto-generated method stub
		Map<String, Object> returnMap = new HashMap<String, Object>();		
		try{
			Result result = xiuMessageFacade.updateMsgCategoryStatus(xiuUserId,categoryId,status);
			logger.info("xiuUserId:"+xiuUserId+"categoryId:"+categoryId+",更新消息分类结果:"+result);
			if(result.isSuccess()){
				returnMap.put("result", true);
				returnMap.put("errorCode", "0");
				returnMap.put("errorMsg", "成功");
				returnMap.put("categoryList", JSON.parseArray(result.getModels().get("categoryList").toString()));
			}else{
				returnMap.put("result", false);
				Set<String> keySet = result.getErrorMessages().keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					returnMap.put("errorCode", key);
					returnMap.put("errorMsg", result.getErrorMessages().get(key));
				}
			}
			logger.info("查询分类列表返回结果：" + returnMap);
		}catch(Exception e){
			logger.error("查询分类列表返回结果", e);
			returnMap.put("result", false);
			returnMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			returnMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return returnMap;
	}

	/**
	 * 清空用户所有消息
	 */
	@Override
	public Map<String, Object> cleanUserMsg(Integer xiuUserId) {
		// TODO Auto-generated method stub
		Map<String, Object> returnMap = new HashMap<String, Object>();		
		try{
			Result result = xiuMessageFacade.cleanUserMsg(xiuUserId);
			logger.info("xiuUserId:"+xiuUserId+",清空所有消息");
			if(result.isSuccess()){
				returnMap.put("result", true);
				returnMap.put("errorCode", "0");
				returnMap.put("errorMsg", "成功");
			}else{
				returnMap.put("result", false);
				Set<String> keySet = result.getErrorMessages().keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					returnMap.put("errorCode", key);
					returnMap.put("errorMsg", result.getErrorMessages().get(key));
				}
			}
			logger.info("清空所有消息：" + returnMap);
		}catch(Exception e){
			logger.error("清空所有消息", e);
			returnMap.put("result", false);
			returnMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			returnMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return returnMap;
	}
}
