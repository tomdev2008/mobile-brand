package com.xiu.mobile.portal.service;

import java.util.Map;

import com.xiu.mobile.sales.dointerface.activity.vo.WeiXinActivityJoinResponse;
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
public interface IBegSerivce {
	/***
	 * 进入微信活动页获取活动传播者讨赏和针对该传播者的支持者信息
	 * @param activityId 微信活动ID
	 * @param unionId 微信用户unionId
	 * @param code 防止篡改签名
	 * @param spreadUnionId 微信传播者UnionId
	 * @return
	 */
	public WeiXinActivityResponse getWeiXinActivitySpreadAndSupportInfo(String activityId, String unionId, String code, String spreadUnionId);
	
	/***
	 * 活动传播者发出讨赏接口
	 * @param activityId 微信活动ID
	 * @param unionId 微信用户unionId
	 * @param code 防止篡改签名
	 * @param nickName 微信传播者微信昵称
	 * @param userPhoneUrl 传播者图片地址
	 * @param xiuUserName 传播者走秀账号
	 * @param xiuUserId 传播者走秀用户ID
	 * @return
	 */
	public WeiXinSpreadResponse spreadSendReward(String activityId,String unionId,String code,String nickName,String userPhoneUrl,String xiuUserName,String xiuUserId);
	
	/***
	 * 微信活动传播者(讨满一定金额)后获取礼品
	 * @param activityId 微信活动ID
	 * @param unionId 微信传播者UnionId
	 * @param code 防止篡改签名
	 * @return
	 */
	public boolean spreadUserGiveGift(String activityId, String unionId, String code);
	
	/***
	 * 支持者为微信传播者打赏接口
	 * @param activityId 微信活动ID
	 * @param unionId 微信传播者UnionId
	 * @param code 防止篡改签名
	 * @param nickName 微信支持者微信昵称
	 * @param supportUnionId 微信支持者UnionId
	 * @param supportSay 支持者对传播者打赏是发表的说说
	 * @param supportPhoneUrl 支持者微信图像Url
	 * @param xiuUserName 支持者走秀账号
	 * @param xiuUserId 支持者走秀用户ID
	 * @return
	 */
	public Map<String, Object> supportSendReward(String activityId,String unionId,String code,String nickName,String supportUnionId,String supportSay,String supportPhoneUrl,String xiuUserName,String xiuUserId);
	
	/***
	 * 微信活动支持者为微信活动传播者打赏成功后获取礼品接口
	 * @param activityId 微信活动ID
	 * @param unionId 微信传播者UnionId
	 * @param code 防止篡改签名
	 * @param supportUnionId 微信支持者UnionId
	 * @return
	 */
	public boolean supportUserGiveGift(String activityId,String unionId,String code,String supportUnionId);
	
	/***
	 * 微信活动传播获奖名单
	 * @param activityId 微信活动ID
	 * @param activityType 微信活动类型
	 * @param currentPage 当前页(默认为1)
	 * @param pageSize 每页显示记录数(默认为30)
	 * @return
	 */
	public PageView<WeiXinSpreadRewardResult> queryWeiXinSpreadRewardResult(String activityId,String activityType,int currentPage,int pageSize);
	
	/**
	 * 进入微信活动页获取活动传播者讨赏和针对该传播者的支持者信息 新接口
	 * @param activityId 微信活动ID
	 * @param unionId 微信用户unionId
	 * @param code 防止篡改签名
	 * @param spreadUnionId 微信传播者UnionId
	 * @param nickName 微信支持者微信昵称
	 * @param supportSay 支持者对传播者打赏是发表的说说
	 * @param supportPhoneUrl 支持者微信图像Url
	 * @param xiuUserName 支持者走秀账号
	 * @param xiuUserId 支持者走秀用户ID
	 * @return
	 */
	public WeiXinActivityVirtualPayResponse weiXinActivityVirtaulPayHomePage(String activityId, String unionId, String code, String spreadUnionId, String nickName, String supportSay, String supportPhoneUrl, String xiuUserName, String xiuUserId);
	
	/**
	 * 进入微信活动页获取活动传播者讨赏和针对该传播者的支持者信息 新接口
	 * @param activityId 微信活动ID
	 * @param unionId 微信用户unionId
	 * @param code 防止篡改签名
	 * @param spreadUnionId 微信传播者UnionId
	 * @param nickName 微信支持者微信昵称
	 * @param supportSay 支持者对传播者打赏是发表的说说
	 * @param supportPhoneUrl 支持者微信图像Url
	 * @param xiuUserName 支持者走秀账号
	 * @param xiuUserId 支持者走秀用户ID
	 * @return
	 */
	public Map weiXinActivityVirtaulPayHomePageNew(String activityId, String unionId, String code, String spreadUnionId, String nickName, String supportSay, String supportPhoneUrl, String xiuUserName, String xiuUserId);
	
	
	/**
	 * 获取微信获取参与人数,与送出的年终奖信息
	 * @param activityId
	 * @return
	 */
	public WeiXinActivityJoinResponse queryWeiXinJoinInfo(String activityId);
}
