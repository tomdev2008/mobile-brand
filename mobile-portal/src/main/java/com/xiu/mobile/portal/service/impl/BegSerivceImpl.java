package com.xiu.mobile.portal.service.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.portal.ei.EIMobileSalesManager;
import com.xiu.mobile.portal.service.IBegSerivce;
import com.xiu.mobile.sales.dointerface.activity.vo.WeiXinActivityJoinResponse;
import com.xiu.mobile.sales.dointerface.activity.vo.WeiXinActivityVirtualPayRequest;
import com.xiu.mobile.sales.dointerface.activity.vo.WeiXinActivityVirtualPayResponse;
import com.xiu.mobile.sales.dointerface.vo.PageView;
import com.xiu.mobile.sales.dointerface.vo.WeiXinActivityResponse;
import com.xiu.mobile.sales.dointerface.vo.WeiXinSpreadResponse;
import com.xiu.mobile.sales.dointerface.vo.WeiXinSpreadRewardResult;
import com.xiu.mobile.sales.dointerface.vo.WeiXinSupportResponse;

/***
 * 微信讨赏活动
 * @author hejianxiong
 * @date 2015-01-15 18:17:12
 */
@Service
public class BegSerivceImpl implements IBegSerivce {
	private final static Logger logger = Logger.getLogger(BegSerivceImpl.class);
	
	@Autowired
	private EIMobileSalesManager eiMobileSalesManager;

	@Override
	public WeiXinActivityResponse getWeiXinActivitySpreadAndSupportInfo(String activityId, String unionId, String code, String spreadUnionId) {
		return eiMobileSalesManager.getWeiXinActivitySpreadAndSupportInfo(activityId, unionId, code, spreadUnionId);
	}

	@Override
	public WeiXinSpreadResponse spreadSendReward(String activityId, String unionId, String code, String nickName, String userPhoneUrl, String xiuUserName, String xiuUserId) {
		return eiMobileSalesManager.spreadSendReward(activityId, unionId, code, nickName, userPhoneUrl, xiuUserName, xiuUserId);
	}

	@Override
	public boolean spreadUserGiveGift(String activityId, String unionId, String code) {
		return eiMobileSalesManager.spreadUserGiveGift(activityId, unionId, code);
	}

	@Override
	public Map<String, Object> supportSendReward(String activityId, String unionId, String code, String nickName, String supportUnionId, String supportSay, String supportPhoneUrl, String xiuUserName, String xiuUserId) {
		return eiMobileSalesManager.supportSendReward(activityId, unionId, code, nickName, supportUnionId, supportSay, supportPhoneUrl, xiuUserName, xiuUserId, null, true, null, null, null);
	}

	@Override
	public boolean supportUserGiveGift(String activityId, String unionId, String code, String supportUnionId) {
		return eiMobileSalesManager.supportUserGiveGift(activityId, unionId, code, supportUnionId);
	}

	@Override
	public PageView<WeiXinSpreadRewardResult> queryWeiXinSpreadRewardResult(String activityId, String activityType, int currentPage, int pageSize) {
		return eiMobileSalesManager.queryWeiXinSpreadRewardResult(activityId, activityType, currentPage, pageSize);
	}

	public WeiXinActivityVirtualPayResponse weiXinActivityVirtaulPayHomePage(String activityId, String unionId, String code, String spreadUnionId, String nickName, String supportSay, String supportPhoneUrl, String xiuUserName, String xiuUserId) {
		return eiMobileSalesManager.weiXinActivityVirtaulPayHomePage(activityId, unionId, code, spreadUnionId, nickName, supportSay, supportPhoneUrl, xiuUserName, xiuUserId);
	}
	
	public Map weiXinActivityVirtaulPayHomePageNew(String activityId, String unionId, String code, String spreadUnionId, String nickName, String supportSay, String supportPhoneUrl, String xiuUserName, String xiuUserId) {
		return eiMobileSalesManager.weiXinActivityVirtaulPayHomePageNew(activityId, unionId, code, spreadUnionId, nickName, supportSay, supportPhoneUrl, xiuUserName, xiuUserId);
	}

	public WeiXinActivityJoinResponse queryWeiXinJoinInfo(String activityId){
		return eiMobileSalesManager.queryWeiXinJoinInfo(activityId);
	};
	
}
