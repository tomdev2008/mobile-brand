package com.xiu.mobile.portal.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.core.utils.DateUtil;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.service.ICreditService;
import com.xiu.mobile.sales.dointerface.creditSign.CreditSignResponse;
import com.xiu.mobile.sales.dointerface.serivce.MobileSalesServiceFacade;

@Service("creditService")
public class CreditServiceImpl implements ICreditService{
	private static final Logger logger = Logger.getLogger(CreditServiceImpl.class);

	@Autowired
	private MobileSalesServiceFacade mSalesServiceFacade;
	
	/**
	 * 积分签到
	 */
	@Override
	public Map<String, Object> userCreditCenter(Long xiuUserId) {
		Map<String, Object> map=new HashMap<String, Object>();
		String paraLog = "积分签到用户："+xiuUserId+"：签到时间："+new Date();;
		try {
			logger.info(paraLog);
			CreditSignResponse result=mSalesServiceFacade.userCreditCenter(xiuUserId);
			logger.info("积分签到用户："+xiuUserId+",签到结果:"+result);
			map.put("result", result.getResult());
			map.put("errorCode", 0);
			map.put("creditToday", result.getCreditToday());
			map.put("unbrokenCount", result.getUnbrokenCount());
			map.put("totalCount", result.getTotalCount());
			map.put("totalCredit", result.getTotalCredit());
			map.put("canSupple", result.getCanSupple());
			map.put("suppleCredit", result.getSuppleCredit());
			map.put("guideUrl", result.getGuideUrl());
			map.put("creditList", result.getCreditUserDetailList());
			map.put("shareRemark", result.getShareRemark());
			map.put("shareUrl", result.getShareUrl());
			
			map.put("today", DateUtil.formatDate(new Date()));
		}catch(Exception e){
			logger.info(paraLog + ",exception ", e);
			map.put("result",false);
			map.put("errorCode", ErrorCode.SystemError.getErrorCode());
			map.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return map;
	}
	
	@Override
	public Map<String, Object> doCredit(Long xiuUserId,String creditOldStatus) {
		// TODO Auto-generated method stub
		Map<String, Object> remap=new HashMap<String, Object>();
		String paraLog = "积分补签用户："+xiuUserId+"：签到时间："+new Date()+",补签creditOldStatus："+creditOldStatus;
		logger.info(paraLog);
		CreditSignResponse result=mSalesServiceFacade.doCreditOld(xiuUserId,Long.valueOf(creditOldStatus));
		remap.put("result", result.getResult());
		remap.put("credit", result.getSuppleCredit());
		return remap;
	}

	 
}
