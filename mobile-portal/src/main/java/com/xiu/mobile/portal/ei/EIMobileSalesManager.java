package com.xiu.mobile.portal.ei;

import java.util.Map;

import com.xiu.common.command.result.Result;
import com.xiu.mobile.sales.dointerface.activity.vo.WeiXinActivityJoinResponse;
import com.xiu.mobile.sales.dointerface.activity.vo.WeiXinActivityVirtualPayResponse;
import com.xiu.mobile.sales.dointerface.card.vo.CardInfoRequest;
import com.xiu.mobile.sales.dointerface.vo.PageView;
import com.xiu.mobile.sales.dointerface.vo.WeiXinActivityResponse;
import com.xiu.mobile.sales.dointerface.vo.WeiXinSpreadResponse;
import com.xiu.mobile.sales.dointerface.vo.WeiXinSpreadRewardResult;
import com.xiu.mobile.sales.dointerface.vo.WeiXinSupportResponse;


/**
 * @ClassName: EIMobileSalesManager
 * @Description: cps佣金管理
 * @author hejianxiong
 * @date 2014-7-8 11:21:05
 * 
 */
public interface EIMobileSalesManager {

	/***
	 * 第一次打开
	 * @param deviceId
	 * @param deviceOs
	 * @param source
	 * @param version
	 * @return
	 */
	boolean recordFirstOpen (String deviceId, String deviceOs,String source,String version,String idfa);
	
	/***
	 * 第一次登录
	 * @param deviceId
	 * @param deviceOs
	 * @param source
	 * @param version
	 * @param userId
	 * @param userName
	 * @return
	 */
	boolean awardFirstLogin (String deviceId, String deviceOs,String source,String version,String userId, String userName,String idfa);
	
	/**
	 * 用户注册送券
	 * 
	 * @return
	 */
	Result userRegisterGiveCoupon(String activityTypeName, String appTerminalTypeName, long userId, String userName);
	
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
	public Map<String, Object> supportSendReward(String activityId,
			String unionId,String code,String nickName,String supportUnionId,String supportSay,String supportPhoneUrl,String xiuUserName,String xiuUserId,String spreadUnionId, boolean check, String extstring2, Double extnumber1, String telephone);
	public Map<String, Object> supportSendRewardNew(String activityId,
			String unionId,String code,String nickName,String supportUnionId,String supportSay,String supportPhoneUrl,String xiuUserName,String xiuUserId,String spreadUnionId, boolean check, String extstring2, Double extnumber1, String telephone);
	
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
	
	/**
	 * 获取微信活动信息
	 * @param request
	 * @return
	 */
	public WeiXinActivityResponse getWeiXinActivitySupportInfo(String activityId,
			String unionId, String spreadUnionId, String code, Long xiuUserId, String xiuUserName, String nickName, String userPhoneUrl, boolean check, String extstring1, String extstring2, Double extnumber1);
	/**
	 * 获取下单发红包信息
	 * @param request
	 * @return
	 */
	public Map<String, Object> getOrderRedpacketParentInfo(String activityId,
			String unionId, String spreadUnionId, String code, Long xiuUserId, String xiuUserName, boolean check);
	
	/**
	 * 微信扫码获取优惠礼包
	 * @param activityId
	 * @param unionId
	 * @param spreadUnionId
	 * @param code
	 * @param xiuUserId
	 * @param xiuUserName
	 * @param nickName
	 * @param userPhoneUrl
	 * @return
	 */
	public WeiXinActivityResponse scanCodeWeiXinActivityPage(String activityId,
			String unionId, String spreadUnionId, String code, Long xiuUserId, String xiuUserName, String nickName, String userPhoneUrl);
	
	/**
	 * 微信卡券核销
	 * @param xiuUserId 
	 * @param xiuUserName
	 * @param unionId
	 * @param encryptCode
	 * @param cardId
	 * @param signature
	 * @return
	 */
	public Result consumeWechatCard(Long xiuUserId,String xiuUserName,
			String unionId,String encryptCode,String cardId,String signature) ;
	
	/**
	 * 设备专享送券
	 * @param xiuUserId
	 * @param xiuUserName
	 * @param deviceId
	 * @param operType
	 * @return
	 */
	public Map<String, Object> userCustomReward(Long xiuUserId,String xiuUserName,String deviceId,String operType, String platform);
	
	/**
	 * 设备送券状态
	 * @param xiuUserId
	 * @param deviceId
	 * @return
	 */
	public Map<String, Object> customRewardStatus(String xiuUserId, String deviceId);
	/**
	 * 获取分享红包链接
	 * @return
	 */
	public Map<String, Object> spreadSendReward(String activityCode, String spreadUnionId, Long xiuUserId, String xiuUserName, String userPhoneUrl, String code, boolean check);
	/**
	 * 下单发红包发现金获取分享红包链接
	 * @param activityCode
	 * @param spreadUnionId
	 * @param xiuUserId
	 * @param xiuUserName
	 * @param userPhoneUrl
	 * @param code
	 * @param check
	 * @return
	 */
	public Map<String, Object> spreadSendRewardNew(String activityCode, String spreadUnionId, Long xiuUserId, String xiuUserName, String userPhoneUrl, String code, boolean check,String mobile,String deviceId);
	/**
	 * 检验下单发红包是不是黑名单
	 * @param userId   用户Id
	 * @param mobile   手机号
	 * @param deviceId   设备号
	 * @return
	 */
	public Map<String,Object> spreadIsBlacklist(long userId,String mobile,String deviceId,String orderId);
	/**
	 * 下单发红包分享者黑名单处理
	 */
	public void findBlacklistByspread(String mobile,long userId,String deviceId);
	/**
	 * 下单发红包领取者黑名单处理
	 */
	public void findBlacklistBysupport(String mobile);
	/**
	 * 查询下单发红包的红包个数
	 */
	public Map<String,Object> getOrderRedPacketNum(long orderId);
	/**
	 * 根据优惠卷ID查询优惠代码
	 */
	public Map<String,Object> findGiftMemcached(Long userId,String cardCode);
	/**
	 * 积分兑换优惠卷激活
	 */
	public Map<String,Object> activateConponsOrCardCode(CardInfoRequest request);
	//积分兑换
	public Map<String,Object> integralChange(String userId, String userName,String cardCode);
	/**
	 * 积分兑奖记录
	 */
	public Map<String,Object> getIntegralChangeList(Long userId,String page,String pageSize);
}