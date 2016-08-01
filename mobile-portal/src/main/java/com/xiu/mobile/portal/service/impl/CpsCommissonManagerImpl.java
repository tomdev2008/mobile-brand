package com.xiu.mobile.portal.service.impl;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.icu.text.SimpleDateFormat;
import com.xiu.mobile.cps.dointerface.query.CommissionsOrderQueryParamIn;
import com.xiu.mobile.cps.dointerface.query.CommissionsOrderQueryParamOut;
import com.xiu.mobile.cps.dointerface.vo.UserCommissionsAmountVo;
import com.xiu.mobile.cps.dointerface.vo.UserCommissionsOrderDetail;
import com.xiu.mobile.cps.dointerface.vo.UserCommissionsOrderInfo;
import com.xiu.mobile.portal.ei.EICpsCommissonManager;
import com.xiu.mobile.portal.service.ICpsCommissonManager;

@Service("cpsCommissonManager")
public class CpsCommissonManagerImpl implements ICpsCommissonManager{
	private static final Logger logger = Logger.getLogger(CpsCommissonManagerImpl.class);
	
	@Autowired
	private EICpsCommissonManager eiCpsCommissonManager;

	@Override
	public UserCommissionsAmountVo queryUserCommissionsAmount(Long userId) {
		// 金额相关数据订单系统以分为单位 除以100
		UserCommissionsAmountVo userCommissionsAmountVo = eiCpsCommissonManager.queryUserCommissionsAmount(userId);
		if (userCommissionsAmountVo!=null) {
			//返利总金额
			Double totalCommissionsAmount = userCommissionsAmountVo.getTotalCommissionsAmount();
			if (totalCommissionsAmount!=null) {
				userCommissionsAmountVo.setTotalCommissionsAmount(totalCommissionsAmount.doubleValue()/100);
			}
			//已返利金额
			Double pastCommissionsAmount = userCommissionsAmountVo.getPastCommissionsAmount();
			if (pastCommissionsAmount!=null) {
				userCommissionsAmountVo.setPastCommissionsAmount(pastCommissionsAmount.doubleValue()/100);
			}
			//待返利金额
			Double futureCommissionsAmount = userCommissionsAmountVo.getFutureCommissionsAmount();
			if (futureCommissionsAmount!=null) {
				userCommissionsAmountVo.setFutureCommissionsAmount(futureCommissionsAmount.doubleValue()/100);
			}
		}
		return userCommissionsAmountVo;
	}

	@Override
	public CommissionsOrderQueryParamOut queryUserCommissionsOrderList(Map<String, String> params) {
		CommissionsOrderQueryParamIn paramIn = new CommissionsOrderQueryParamIn();
		logger.info("用户返佣订单信息查询：params="+params);
		paramIn.setUserId(Long.parseLong(params.get("userId")));
		if (StringUtils.isNotBlank(params.get("commissionsStatus"))) {
			paramIn.setCommissionsStatus(Integer.parseInt(params.get("commissionsStatus")));
		}
		if (StringUtils.isNotBlank(params.get("entrisPerPage"))) {
			paramIn.setEntrisPerPage(Integer.parseInt(params.get("entrisPerPage")));
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (StringUtils.isNotBlank(params.get("maxOrderTime"))) {
				paramIn.setMaxOrderTime(dateFormat.parse(params.get("maxOrderTime")));
			}
			if (StringUtils.isNotBlank(params.get("minOrderTime"))) {
				paramIn.setMinOrderTime(dateFormat.parse(params.get("minOrderTime")));
			}
		} catch (ParseException e) {
			logger.error("用户返佣订单信息查询时间转换异常：e="+e);
			e.printStackTrace();
		}
		paramIn.setPageNo(Integer.parseInt(params.get("pageNo")));
		CommissionsOrderQueryParamOut commissionsOrderQueryParamOut = eiCpsCommissonManager.queryUserCommissionsOrderList(paramIn);
		List<UserCommissionsOrderInfo> userCommissionsOrderInfos = commissionsOrderQueryParamOut.getOrderInfos();
		for (UserCommissionsOrderInfo userCommissionsOrderInfo : userCommissionsOrderInfos) {
			//返利金额
			Double commissionsAmount = userCommissionsOrderInfo.getCommissionsAmount();
			if (commissionsAmount!=null) {
				userCommissionsOrderInfo.setCommissionsAmount(commissionsAmount.doubleValue()/100);
			}
		}
		return commissionsOrderQueryParamOut;
	}

	@Override
	public List<UserCommissionsOrderDetail> queryUserCommissionsOrderDetail(Long userId, Long orderCode) {
		List<UserCommissionsOrderDetail> userCommissionsOrderDetails = eiCpsCommissonManager.queryUserCommissionsOrderDetail(userId, orderCode);
		for (UserCommissionsOrderDetail userCommissionsOrderDetail : userCommissionsOrderDetails) {
			//最终价
			Double finalPrice = userCommissionsOrderDetail.getFinalPrice();
			//走秀价
			Double xiuPrice = userCommissionsOrderDetail.getXiuPrice();
			//返佣金额
			Double commissionsAmount = userCommissionsOrderDetail.getCommissionsAmount();
			if (finalPrice!=null) {
				userCommissionsOrderDetail.setFinalPrice(finalPrice.doubleValue()/100);
			}
			if (xiuPrice!=null) {
				userCommissionsOrderDetail.setXiuPrice(xiuPrice.doubleValue()/100);
			}
			if (commissionsAmount!=null) {
				userCommissionsOrderDetail.setCommissionsAmount(commissionsAmount.doubleValue()/100);
			}
		}
		return userCommissionsOrderDetails;
	}

	@Override
	public UserCommissionsOrderInfo queryUserCommissionsOrderInfo(Long orderCode) {
		UserCommissionsOrderInfo userCommissionsOrderInfo = eiCpsCommissonManager.queryUserCommissionsOrderInfo(orderCode);
		if (userCommissionsOrderInfo!=null) {
			//返利金额
			Double commissionsAmount = userCommissionsOrderInfo.getCommissionsAmount();
			if (commissionsAmount!=null) {
				userCommissionsOrderInfo.setCommissionsAmount(commissionsAmount.doubleValue()/100);
			}
		}
		return eiCpsCommissonManager.queryUserCommissionsOrderInfo(orderCode);
	}
	
}
