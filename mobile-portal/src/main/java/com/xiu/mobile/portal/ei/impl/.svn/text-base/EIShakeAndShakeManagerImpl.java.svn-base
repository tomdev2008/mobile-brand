package com.xiu.mobile.portal.ei.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.xiu.common.command.result.ResultSupport;
import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.portal.constants.ErrConstants;
import com.xiu.mobile.portal.ei.EIShakeAndShakeManager;
import com.xiu.mobile.sales.dointerface.serivce.MobileSalesServiceFacade;
import com.xiu.mobile.sales.dointerface.vo.ActivityGiveCouponRequest;
import com.xiu.mobile.sales.dointerface.vo.RockingQueryParames;
import com.xiu.common.command.result.Result;

/**
 * 
 * @ClassName
 * @Description
 * @author chenlinyu
 * @date 2014年12月3日 下午2:44:27
 */
@Service("eiShakeAndShakeManager")
public class EIShakeAndShakeManagerImpl implements EIShakeAndShakeManager {
	/**
	 * 用户进行手机摇一摇
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public Result mobileShake(Long userId, String userName) {
		Result result = null;

		ActivityGiveCouponRequest request = new ActivityGiveCouponRequest();
		request.setUserId(userId); // 用户ID
		request.setUserName(userName); // 用户名称
		String appTerminalTypeName = "mobile"; // 移动终端mobile pc
		request.setAppTerminalTypeName(appTerminalTypeName);
		String activityTypeName = "rockandrock"; // 摇一摇 register validateEmail rockandrock
		request.setActivityTypeName(activityTypeName);

		result = mSalesServiceFacade.userRockingGiveCoupon(request);
		try {
		} catch (Exception e) {
			logger.error("{}.mobileShake 调用MMKT接口查询用户摇奖情况出现异常  | message={}",
					new Object[] { mSalesServiceFacade.getClass(),
							e.getMessage() });
			throw ExceptionFactory.buildEIRuntimeException(
					ErrConstants.EIErrorCode.EI_MOBILE_SHAKE_ERR, e);
		}

		// 调用/摇奖不成功
		if (!"0".equals(result.getResultCode())) {
			if("250".equals(result.getResultCode()) && "251".equals(result.getResultCode()) && "252".equals(result.getResultCode())){
				String errorCode = result.getResultCode();
				Map<String, String> errorMsg = result.getErrorMessages();
				StringBuffer sf = new StringBuffer();
				if (null != errorMsg) {
					Iterator iter = errorMsg.entrySet().iterator();
					while (iter.hasNext()) {
						Map.Entry entry = (Map.Entry) iter.next();
						sf.append(entry.getValue());
						sf.append(" ");
					}
				}
				logger.error("{}.mobileShake 调用MMKT接口查询用户摇奖情况失败 | errCode={} | errMessage={}",
						new Object[] {mSalesServiceFacade.getClass().getName(),
								errorCode, sf.toString() });
				throw ExceptionFactory.buildEIBusinessException(
						ErrConstants.EIErrorCode.EI_MOBILE_SHAKE_FAILED_ERR,
						errorCode, sf.toString());
			}

		}
		logger.info("摇奖返回结果："+result.isSuccess() + "，resultCode: " + result.getResultCode());
		return result;
	}

	private static final XLogger logger = XLoggerFactory.getXLogger(EIShakeAndShakeManagerImpl.class);

	@Autowired
	private MobileSalesServiceFacade mSalesServiceFacade;

	/**
	 * 获取用户还可以摇一摇的次数
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public Map<String, Object> getUserCanShakeTime(Long userId, String userName) {
		String shakeTime;
		Result result = null;

		ActivityGiveCouponRequest request = new ActivityGiveCouponRequest();
		request.setUserId(userId); // 用户ID
		request.setUserName(userName); // 用户名称
		String appTerminalTypeName = "mobile"; // 移动终端mobile pc
		request.setAppTerminalTypeName(appTerminalTypeName);

		String activityTypeName = "rockandrock"; // 摇一摇 register validateEmail rockandrock
		request.setActivityTypeName(activityTypeName);

		try {
			result = mSalesServiceFacade.getUserCurrentDayAbleRockCount(request);

		} catch (Exception e) {
			logger.error("{}.getUserCanShakeTime 调用MMKT接口查询用户还剩余摇奖次数出现异常  | message={}",
					new Object[] { mSalesServiceFacade.getClass(),
							e.getMessage() });
			throw ExceptionFactory.buildEIRuntimeException(
					ErrConstants.EIErrorCode.EI_USER_SHAKETIME_ERR, e);
		}
		// 调用不成功
		if (!result.isSuccess()) {
			String errorCode = result.getResultCode();
			Map<String, String> errorMsg = result.getErrorMessages();
			StringBuffer sf = new StringBuffer();
			if (null != errorMsg) {
				Iterator iter = errorMsg.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					sf.append(entry.getValue());
					sf.append(" ");
				}
			}
			logger.error("{}.addRcvAddressInfo 调用MMKT接口查询用户还剩余摇奖次数失败 | errCode={} | errMessage={}",
					new Object[] {mSalesServiceFacade.getClass().getName(),
							errorCode, sf.toString() });
			throw ExceptionFactory.buildEIBusinessException(
					ErrConstants.EIErrorCode.EI_UUC_ADDRESS_ADD_FAILED_ERR,
					result.getResultCode(), sf.toString());
		}

		Map<String, Object> map = result.getModels();

		logger.info("剩余摇奖次数："+ map);


		return map;
	}

	/**
	 * 查询其他用户中奖结果
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public Result queryShakeWinList(Long userId, String currentPage,String pageSize) {
		Result result = null;
		RockingQueryParames parames=new RockingQueryParames();
		// 不为空且大于0则赋值，currentPage默认为1，pageSize默认为30		
		if(StringUtils.isNotEmpty(currentPage) && currentPage.matches("[0-9]+") && Integer.valueOf(currentPage)>0){
			parames.setCurrentPage(Integer.valueOf(currentPage));
		}					
		if(StringUtils.isNotEmpty(pageSize) && pageSize.matches("[0-9]+") && Integer.valueOf(pageSize)>0){		
			parames.setPageSize(Integer.valueOf(pageSize));
		}

		parames.setUserId(userId);
		parames.setActivityTypeName("rockandrock");
		parames.setAppTerminalTypeName("mobile");

		try {
			result=mSalesServiceFacade.queryUserRockRewardResult(parames);					
		} catch (Exception e) {
			logger.error("{}.queryShakeWinList 调用MMKT接口查询其他用户中奖结果出现异常  | message={}",
					new Object[] { mSalesServiceFacade.getClass(),
							e.getMessage() });
			throw ExceptionFactory.buildEIRuntimeException(
					ErrConstants.EIErrorCode.EI_OTHERUSER_SHAKE_ERR, e);
		}
		// 调用不成功
		if (!result.isSuccess()) {
			String errorCode = result.getResultCode();
			Map<String, String> errorMsg = result.getErrorMessages();
			StringBuffer sf = new StringBuffer();
			if (null != errorMsg) {
				Iterator iter = errorMsg.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					sf.append(entry.getValue());
					sf.append(" ");
				}
			}
			logger.error("{}.queryShakeWinList 调用MMKT接口查询用户中奖结果失败 | errCode={} | errMessage={}",
					new Object[] {mSalesServiceFacade.getClass().getName(),
							errorCode, sf.toString() });
			throw ExceptionFactory.buildEIBusinessException(
					ErrConstants.EIErrorCode.EI_OTHERUSER_SHAKE_FAILED_ERR,
					errorCode, sf.toString());
		}
		return result;
	}

	@Override
	public Result getAnotherShakeChance(Long userId, String userName) {

		ActivityGiveCouponRequest request = new ActivityGiveCouponRequest();
		request.setUserName(userName);
		request.setUserId(userId);
		request.setActivityTypeName("rockandrock");
		request.setAppTerminalTypeName("mobile");

		Result result;

		try{
			result = mSalesServiceFacade.getAnotherRockChance(request);
		}catch (Exception e){
			logger.error("调用摇一摇获取另一次机会发现异常！", e);
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_OTHERUSER_SHAKE_ERR, e);
		}
		return result;
	}
}
