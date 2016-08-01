package com.xiu.mobile.portal.ei;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.portal.model.raiders.RaidersOrderGoodVo;

/**
 * 零钱夺宝首页
 * @author Administrator
 *
 */
public interface EIRaidersManager {
	/**
	 * 最近中奖用户
	 */
	public Map<String,Object> getLastTenLotteryUsers(Integer number);
	
	/**
	 * 马上开奖
	 * 1.开奖属于倒计时的商品
	 * 2.购买进度超过80%的商品
	 */
	public Map<String,Object> findSoonLottery(String number1);
	
	
	/**
	 * 支付夺宝
	 * @param params
	 * @return
	 */
	public Map<String,Object> payRaider(Map<String,Object> params);

	/**
	 * 全部活动
	 */
	public Map<String,Object> getHotGood(String isHot,String page,String pageSize,Integer orderSeq,Integer sort);
	/**
	 * 我的夺宝记录
	 */
	public Map<String,Object> getMyRaidersIndex(String page,String pageSize,long userId,Integer isLottery);
	/**
	 * 购买详情
	 */
	public Map<String,Object> getRaidersBuyList(Long raiderId,Long userId,String isOther);
	/**
	 * 他的夺宝记录
	 */
	public Map<String,Object> findOtheRaidersIndex(String page,String pageSize,String userId,Integer isLottery);
	/**
	 * 添加收货地址ID
	 */
	public Map<String,Object> addRaiderLotteryAdd(String raiderId,String userId,String addId,
			String addressInfo,String realName,String addressPrefix,String mobile);
	/**
	 * 根据participateId 查询领取用户记录
	 * @param participateId
	 * @return
	 */
	public Map<String,Object> findRaiderSupperdList(String participateId);
	
	/**
	 * 往期活动记录
	 */
	public Map<String,Object> findPastRaider(Long raiderId,String page,String pageSize);
	
	
	/**
	 * 获取夺宝商品详情
	 * @param params
	 * @return
	 */
	public Map<String,Object> getRaiderGoodInfo(Map<String,Object> params);
	
	
	/**
	 * 生成夺宝订单
	 * @param params
	 * @return
	 */
	public Map<String,Object> createRaiderOrder(Map<String,Object> params);
	
	/**
	 * 获取我的夺宝号码
	 * @param params
	 * @return
	 */
    public Map<String, Object> getMyRaiderNumber(Map<String,Object> params);
    
    /**
	 * 查询购物车
	 * @param params
	 * @return
	 */
	public List<RaidersOrderGoodVo> getShoppingCartGoodsList(Map<String,Object> params);
	
	/**
	 * 加入购物车
	 * @param params
	 * @return
	 */
	public Map<String,Object> addRaiderShoppingCat(Map<String,Object> params);
	
	/**
	 * 修改购物车
	 * @param params
	 * @return
	 */
	public Map<String,Object> updateRaiderShoppingCat(Map<String,Object> params);
	
	
	/**
	 * 删除购物车
	 * @param params
	 * @return
	 */
	public Map<String,Object> delRaiderShoppingCat(Map<String,Object> params);
	
	/**
	 * 查询购物车个数
	 * @param params
	 * @return
	 */
	public Map<String,Object> getRaiderShoppingCatNum(Map<String,Object> params);
	/**
	 * 查询虚拟账户
	 * @param userId
	 * @return
	 */
	public Map<String,Object> getVirtualAccountInfo(Long userId);
	
	
    

}
