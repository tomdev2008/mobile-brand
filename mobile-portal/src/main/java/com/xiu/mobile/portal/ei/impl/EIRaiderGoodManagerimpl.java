package com.xiu.mobile.portal.ei.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.common.command.result.Result;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.ei.EIRaiderGoodManager;
import com.xiu.raiders.dointerface.service.XiuRaidersFacade;
import com.xiu.raiders.dointerface.vo.RaiderSharaParamVo;
import com.xiu.raiders.model.GoodQueryParams;
/**
 * 夺宝商品详情
 * @author Administrator
 *
 */
@Service
public class EIRaiderGoodManagerimpl implements EIRaiderGoodManager {
	private static final Logger logger = Logger.getLogger(EIRaiderGoodManagerimpl.class);

	@Autowired
	private XiuRaidersFacade xiuRaidersFacade;
	
	@Override
	public Map<String, Object> findParticipateUser(String raiderId) {
		Map<String,Object> resultMap=new HashMap<String,Object>();//结果集
		Result result = null;
		try{
			result=xiuRaidersFacade.findParticipateUser(Long.parseLong(raiderId));
			if(result!=null){
				if(result.isSuccess()){
					resultMap.put("status", true);
					resultMap.put("errorCode", "0");
					resultMap.put("errorMsg", "成功");
					resultMap.put("richUser", result.getModels().get("richUser"));
					resultMap.put("sofaUser", result.getModels().get("sofaUser"));
					resultMap.put("lastUser", result.getModels().get("lastUser"));
				}else{
					resultMap.put("status", false);
					Set<String> keySet = result.getErrorMessages().keySet();
					for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
						String key = iterator.next();
						resultMap.put("errorCode", key);
						resultMap.put("errorMsg", result.getErrorMessages().get(key));
					}
				}
			}
		}catch(Exception e){
			logger.error("调用他们都在玩接口异常");
			resultMap.put("status", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> findParticipateByRaiders(String raiderId,
			String page, String pageSize) {
		Map<String,Object> resultMap=new HashMap<String,Object>();//结果集
		Result result = null;
		GoodQueryParams params=new GoodQueryParams();
		params.setRaiderId(Long.parseLong(raiderId));
		params.setCurrentPage(Integer.valueOf(page));
		params.setPageSize(Integer.valueOf(pageSize));
		try{
			result=xiuRaidersFacade.findParticipateByRaiders(params);
			if(result!=null){
				if(result.isSuccess()){
					resultMap.put("status", true);
					resultMap.put("errorCode", "0");
					resultMap.put("errorMsg", "成功");
					resultMap.put("userList", result.getModels().get("userList"));
					resultMap.put("totalPage", result.getModels().get("totalPage"));
					resultMap.put("totalCount", result.getModels().get("totalCount"));
				}else{
					resultMap.put("status", false);
					Set<String> keySet = result.getErrorMessages().keySet();
					for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
						String key = iterator.next();
						resultMap.put("errorCode", key);
						resultMap.put("errorMsg", result.getErrorMessages().get(key));
					}
				}
			}
		}catch(Exception e){
			logger.error("调用本场活动参与的用户记录接口异常");
			resultMap.put("status", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> spreadSendRaiders(String raiderId,
			String participateId, boolean checkLogin, String xiuUserId,Integer personCount,String content) {
		Map<String,Object> resultMap=new HashMap<String,Object>();//结果集
		Result result = null;
		RaiderSharaParamVo params=new RaiderSharaParamVo();
		params.setParticipateId(Long.valueOf(participateId));
		params.setRaiderId(Long.valueOf(raiderId));
		params.setUserId(Long.valueOf(xiuUserId));
		params.setPersonCount(personCount);
		params.setContent(content);
		try{
			result=xiuRaidersFacade.spreadSendRaiders(params);
			if(result!=null){
				if(result.isSuccess()){
					resultMap.put("status", true);
					resultMap.put("errorCode", "0");
					resultMap.put("errorMsg", "成功");
					resultMap.put("url", result.getModels().get("url"));
					resultMap.put("packetsReceiveVo", result.getModels().get("packetsReceiveVo"));
					resultMap.put("personCount", result.getModels().get("personCount"));
				}else{
					resultMap.put("status", false);
					Set<String> keySet = result.getErrorMessages().keySet();
					for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
						String key = iterator.next();
						resultMap.put("errorCode", key);
						resultMap.put("errorMsg", result.getErrorMessages().get(key));
					}
				}
			}
		}catch(Exception e){
			logger.error("调用获取分享链接接口异常");
			resultMap.put("status", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> supportSendRaiders(String raiderId,
			String participateId, String code, String mobile,String ip,String ipAdr) {
		Map<String,Object> resultMap=new HashMap<String,Object>();//结果集
		Result result = null;
		RaiderSharaParamVo params=new RaiderSharaParamVo();
		params.setCode(code);
		params.setParticipateId(Long.valueOf(participateId));
		params.setRaiderId(Long.valueOf(raiderId));
		params.setIp(ip);
		params.setIpAdr(ipAdr);
		params.setMobile(mobile);
		try{
			result=xiuRaidersFacade.supportSendRaiders(params);
			if(result!=null){
				if(result.isSuccess()){
					resultMap.put("status", true);
					resultMap.put("errorCode", "0");
					resultMap.put("errorMsg", "成功");
					resultMap.put("count", result.getModels().get("count"));
					resultMap.put("raiderList", result.getModels().get("raiderList"));
					resultMap.put("packetChildren", result.getModels().get("packetChildren"));
				}else{
					resultMap.put("status", false);
					Set<String> keySet = result.getErrorMessages().keySet();
					for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
						String key = iterator.next();
						resultMap.put("errorCode", key);
						resultMap.put("errorMsg", result.getErrorMessages().get(key));
					}
				}
			}
		}catch(Exception e){
			logger.error("调用领取夺宝号接口异常");
			resultMap.put("status", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		
		return resultMap;
	}
	//分享详情
	@Override
	public Map<String, Object> accessRaidersSharaIndex(String raiderId,
			String participateId, String code) {
		Map<String,Object> resultMap=new HashMap<String,Object>();//结果集
		Result result = null;
		RaiderSharaParamVo params=new RaiderSharaParamVo();
		params.setRaiderId(Long.parseLong(raiderId));
		params.setParticipateId(Long.parseLong(participateId));
		params.setCode(code);
		try{
			result=xiuRaidersFacade.accessRaidersSharaIndex(params);
			if(result!=null){
				if(result.isSuccess()){
					SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					String time=format.format(new Date());
					resultMap.put("status", true);
					resultMap.put("errorCode", "0");
					resultMap.put("errorMsg", "成功");
					resultMap.put("shareRaiders", result.getModels().get("shareRaiders"));
					resultMap.put("packetChildren", result.getModels().get("packetChildren"));
					resultMap.put("time", time);
				}else{
					resultMap.put("status", false);
					Set<String> keySet = result.getErrorMessages().keySet();
					for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
						String key = iterator.next();
						resultMap.put("errorCode", key);
						resultMap.put("errorMsg", result.getErrorMessages().get(key));
					}
				}
			}
		}catch(Exception e){
			logger.error("调用分享详情接口异常");
			resultMap.put("status", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return resultMap;
	}
	//计算规则
	@Override
	public Map<String, Object> getLotteryRule(String raiderId) {
		Map<String,Object> resultMap=new HashMap<String,Object>();//结果集
		Result result = null;
		try{
			result=xiuRaidersFacade.getLotteryRule(Long.parseLong(raiderId));
			if(result!=null){
				if(result.isSuccess()){
					resultMap.put("status", true);
					resultMap.put("errorCode", "0");
					resultMap.put("errorMsg", "成功");
					resultMap.put("lotteryIndex", result.getModels().get("lotteryIndex"));
					resultMap.put("raiderList", result.getModels().get("raiderList"));
				}else{
					resultMap.put("status", false);
					Set<String> keySet = result.getErrorMessages().keySet();
					for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
						String key = iterator.next();
						resultMap.put("errorCode", key);
						resultMap.put("errorMsg", result.getErrorMessages().get(key));
					}
				}
			}
		}catch(Exception e){
			logger.error("调用计算规则接口异常");
			resultMap.put("status", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return resultMap;
	}
	@Override
	public Map<String,Object> getParticipateByOrderId(String orderId){
		Map<String,Object> resultMap=new HashMap<String,Object>();//结果集
		Result result = null;
		try{
			result=xiuRaidersFacade.getParticipateByOrderId(Long.parseLong(orderId));
			if(result!=null){
				if(result.isSuccess()){
					resultMap.put("status", true);
					resultMap.put("errorCode", "0");
					resultMap.put("errorMsg", "成功");
					resultMap.put("participateVO", result.getModels().get("participateVO"));
				}else{
					resultMap.put("status", false);
					Set<String> keySet = result.getErrorMessages().keySet();
					for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
						String key = iterator.next();
						resultMap.put("errorCode", key);
						resultMap.put("errorMsg", result.getErrorMessages().get(key));
					}
				}
			}
		}catch(Exception e){
			logger.error("调用根据orderId查询参与记录信息异常");
			resultMap.put("status", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> findSharaNum(String participateId) {
		Map<String,Object> resultMap=new HashMap<String,Object>();//结果集
		Result result = null;
		try{
			result=xiuRaidersFacade.findSharaNum(participateId);
			if(result!=null){
				if(result.isSuccess()){
					resultMap.put("status", true);
					resultMap.put("errorCode", "0");
					resultMap.put("errorMsg", "成功");
					resultMap.put("refund", result.getModels().get("refund"));
					resultMap.put("statusType", result.getModels().get("statusType"));
					resultMap.put("participateNumber", result.getModels().get("participateNumber"));
				}else{
					resultMap.put("status", false);
					Set<String> keySet = result.getErrorMessages().keySet();
					for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
						String key = iterator.next();
						resultMap.put("errorCode", key);
						resultMap.put("errorMsg", result.getErrorMessages().get(key));
					}
				}
			}
		}catch(Exception e){
			logger.error("调用根据orderId查询参与记录信息异常");
			resultMap.put("status", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return resultMap;
	}
}
