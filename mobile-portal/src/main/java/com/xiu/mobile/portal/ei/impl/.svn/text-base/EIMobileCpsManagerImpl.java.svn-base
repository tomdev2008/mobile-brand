package com.xiu.mobile.portal.ei.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.common.command.result.Result;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.ei.EIMobileCpsManager;
import com.xiu.mobile.sales.dointerface.serivce.MobileCpsServiceFacade;
import com.xiu.mobile.sales.dointerface.vo.CpsServiceQueryParams;

/**
 * 个人礼包CPS
 * @author Administrator
 *
 */
@Service
public class EIMobileCpsManagerImpl implements EIMobileCpsManager {
	
	private static Logger logger = Logger.getLogger(EIMobileCpsManagerImpl.class);
	
	@Autowired
	private MobileCpsServiceFacade mobileCpsFacade;
	

	@Override
	public Map<String, Object> queryPersonCpsCenterInfo(Long xiuUserId, String userPhoneUrl) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		CpsServiceQueryParams param = new CpsServiceQueryParams();
		param.setXiuUserId(xiuUserId);
		param.setSharePhoneUrl(userPhoneUrl);
		
		try{
			Result result = mobileCpsFacade.queryUserCenter(param);
			if(result.isSuccess()){
				resultMap.put("result", true);
				resultMap.put("errorCode", "0");
				resultMap.put("errorMsg", "成功");
				resultMap.put("indexVO", result.getDefaultModel());
			}else{
				resultMap.put("result", false);
				Set<String> keySet = result.getErrorMessages().keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					resultMap.put("errorCode", key);
					resultMap.put("errorMsg", result.getErrorMessages().get(key));
				}
			}
			logger.info("获取个人礼包Cps首页信息返回结果：" + resultMap);
		}catch(Exception e){
			logger.error("获取个人礼包Cps首页信息异常", e);
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> shareCallback(Long xiuUserId, String mobile) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		CpsServiceQueryParams param = new CpsServiceQueryParams();
		param.setXiuUserId(xiuUserId);
		param.setMobile(mobile);
		try{
			Result result = mobileCpsFacade.shareCallback(param);
			if(result.isSuccess()){
				resultMap.put("result", true);
				resultMap.put("errorCode", "0");
				resultMap.put("errorMsg", "成功");
			}else{
				resultMap.put("result", false);
				Set<String> keySet = result.getErrorMessages().keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					resultMap.put("errorCode", key);
					resultMap.put("errorMsg", result.getErrorMessages().get(key));
				}
			}
			logger.info("用户分享礼包CPS回调返回结果：" + resultMap);
		}catch(Exception e){
			logger.error("用户分享礼包CPS回调异常", e);
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> queryRebateDetail(Long xiuUserId, String status,
			int page, int pageSize) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		CpsServiceQueryParams param = new CpsServiceQueryParams();
		param.setXiuUserId(xiuUserId);
		param.setPageNo(page);
		param.setPageSize(pageSize);
		param.setStatus(Long.parseLong(status));
		
		try{
			Result result = mobileCpsFacade.queryRebateDetail(param);
			if(result.isSuccess()){
				resultMap.put("result", true);
				resultMap.put("errorCode", "0");
				resultMap.put("errorMsg", "成功");
				resultMap.put("rebateDetail", result.getDefaultModel());
				resultMap.put("preRebateAmount", result.getModels().get("preIncome"));
				resultMap.put("rebateAmount", result.getModels().get("income"));
			}else{
				resultMap.put("result", false);
				Set<String> keySet = result.getErrorMessages().keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					resultMap.put("errorCode", key);
					resultMap.put("errorMsg", result.getErrorMessages().get(key));
				}
			}
			logger.info("查询返利明细返回结果：" + resultMap);
		}catch(Exception e){
			logger.error("查询返利明细异常", e);
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> queryGiftbagDetail(Long xiuUserId,
			String status, int page, int pageSize) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		CpsServiceQueryParams param = new CpsServiceQueryParams();
		param.setXiuUserId(xiuUserId);
		param.setPageNo(page);
		param.setPageSize(pageSize);
		param.setStatus(Long.parseLong(status));
		
		try{
			Result result = mobileCpsFacade.queryGiftBagDetail(param);
			if(result.isSuccess()){
				resultMap.put("result", true);
				resultMap.put("errorCode", "0");
				resultMap.put("errorMsg", "成功");
				resultMap.put("giftbagDetail", result.getDefaultModel());
			}else{
				resultMap.put("result", false);
				Set<String> keySet = result.getErrorMessages().keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					resultMap.put("errorCode", key);
					resultMap.put("errorMsg", result.getErrorMessages().get(key));
				}
			}
			logger.info("查询已领取礼包明细返回结果：" + resultMap);
		}catch(Exception e){
			logger.error("查询已领取礼包明细异常", e);
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return resultMap;
	}

}
