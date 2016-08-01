package com.xiu.mobile.portal.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.common.command.result.Result;
import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.portal.constants.ErrConstants;
import com.xiu.mobile.portal.ei.EIShakeAndShakeManager;
import com.xiu.mobile.portal.model.ShakeWinDesc;
import com.xiu.mobile.portal.model.ShakeWinInfo;
import com.xiu.mobile.portal.service.IShakeAndShakeService;
import com.xiu.mobile.sales.dointerface.vo.PageView;
import com.xiu.mobile.sales.dointerface.vo.UserRockRewardVo;
import com.xiu.mobile.sales.dointerface.vo.UserRockingRewardResult;

/**
 * 
 * @ClassName
 * @Description 
 * @author chenlinyu
 * @date 2014年12月3日 下午2:39:14
 */
@Service("shakeAndShakeService")
public class ShakeAndShakeServiceImpl implements IShakeAndShakeService{
	private Logger logger = Logger.getLogger(ShakeAndShakeServiceImpl.class);
	@Autowired
	private EIShakeAndShakeManager eiShakeAndShakeManager;

	@Override
	public Map<String, Object> getUserCanShakeTime(String userId, String userName)throws Exception {
		return eiShakeAndShakeManager.getUserCanShakeTime(Long.valueOf(userId),userName);
	}

	@Override
	public Result mobileShake(String userId,String userName)throws Exception {
	    return  eiShakeAndShakeManager.mobileShake(Long.valueOf(userId),userName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageView<ShakeWinDesc> queryShakeWinList(Long userId,String currentPage,String pageSize)throws Exception {

		PageView<ShakeWinDesc> shakeWinDescPageView = null;

		List<ShakeWinDesc> list=new ArrayList<ShakeWinDesc>();
		// 获取用户中奖结果
		Result result=eiShakeAndShakeManager.queryShakeWinList(userId,currentPage,pageSize);
		
		Object pageViewObject=result.getModels().get("pageView");
		PageView<UserRockingRewardResult> pageView=null;
		
		if(pageViewObject!=null && pageViewObject instanceof PageView){
			pageView=(PageView<UserRockingRewardResult>)pageViewObject;

			shakeWinDescPageView = new PageView<ShakeWinDesc>(pageView.getPageSize(),pageView.getCurrentPage());
			
			List<UserRockingRewardResult> userRockingRewardResult= pageView.getRecords();
			if(userRockingRewardResult!=null && userRockingRewardResult.size()>0){
				for(UserRockingRewardResult userRockingReward:userRockingRewardResult ){
					// 返回中奖结果
					ShakeWinDesc shakeWinDesc=new ShakeWinDesc();
					shakeWinDesc.setUserName(userRockingReward.getUserName());
					shakeWinDesc.setWinDesc(userRockingReward.getRewardDesc());
					shakeWinDesc.setWinTime(userRockingReward.getRewardTime());
					logger.info(userRockingReward);
					list.add(shakeWinDesc);
				}
			}

			shakeWinDescPageView.setRecords(list);
			shakeWinDescPageView.setTotalRecord(pageView.getTotalRecord());
		}
		return shakeWinDescPageView;
	}

	@Override
	public Result getAnotherRockChance(Long userId, String userName) {
		return eiShakeAndShakeManager.getAnotherShakeChance(userId, userName);
	}
}
