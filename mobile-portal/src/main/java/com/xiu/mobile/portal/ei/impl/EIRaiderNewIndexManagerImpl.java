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
import com.xiu.mobile.portal.ei.EIRaiderNewIndexManager;
import com.xiu.raiders.dointerface.service.XiuRaidersFacade;
@Service
public class EIRaiderNewIndexManagerImpl implements EIRaiderNewIndexManager {
	private static final Logger logger = Logger.getLogger(EIRaiderNewIndexManagerImpl.class);
	
	@Autowired
	private XiuRaidersFacade xiuRaidersFacade;
	@Override
	public Map<String, Object> getShoesList(String raiderMainId) {
		Map<String,Object> resultMap=new HashMap<String,Object>();//结果集
		Result result = null;
		try{
			result=xiuRaidersFacade.getShoesList(raiderMainId);
			if(result!=null){
				if(result.isSuccess()){
					resultMap.put("status", true);
					resultMap.put("errorCode", "0");
					resultMap.put("errorMsg", "成功");
					resultMap.put("raidersList", result.getModels().get("raidersList"));
					resultMap.put("time", result.getModels().get("time"));
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
			logger.error("调用鞋子列表接口异常");
			resultMap.put("status", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return resultMap;
	}

}
