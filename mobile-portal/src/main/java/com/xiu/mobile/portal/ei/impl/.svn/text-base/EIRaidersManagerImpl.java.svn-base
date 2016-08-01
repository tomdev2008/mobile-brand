package com.xiu.mobile.portal.ei.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.common.command.result.Result;
import com.xiu.mobile.core.utils.ObjectUtil;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.ei.EIRaidersManager;
import com.xiu.mobile.portal.model.raiders.RaidersGoodVo;
import com.xiu.mobile.portal.model.raiders.RaidersOrderGoodVo;
import com.xiu.mobile.portal.model.raiders.RaidersParticipate;
import com.xiu.mobile.portal.model.raiders.RaidersParticipateNumberVo;
import com.xiu.mobile.portal.model.raiders.RaidersUserInfo;
import com.xiu.raiders.dointerface.service.XiuRaidersFacade;
import com.xiu.raiders.dointerface.vo.PayRaiderInfoOut;
import com.xiu.raiders.dointerface.vo.PayRaiderParamIn;
import com.xiu.raiders.dointerface.vo.RaiderShoppingCatGoodInfoVo;
import com.xiu.raiders.dointerface.vo.RaidersNumberVo;
import com.xiu.raiders.dointerface.vo.RaidersParticipateVo;
import com.xiu.raiders.model.AddRaiderShoppingCatParamsIn;
import com.xiu.raiders.model.DelRaiderShoppingCatParamsIn;
import com.xiu.raiders.model.GetMyRaiderNumberParamsIn;
import com.xiu.raiders.model.GetMyRaiderNumberParamsOut;
import com.xiu.raiders.model.GetRaiderGoodInfoParamesIn;
import com.xiu.raiders.model.GetRaiderGoodInfoParamesOut;
import com.xiu.raiders.model.GetRaiderShoppingCatParamsIn;
import com.xiu.raiders.model.GetRaiderShoppingCatParamsOut;
import com.xiu.raiders.model.GetVirtualAccountInfoOut;
import com.xiu.raiders.model.GoodQueryParams;
import com.xiu.raiders.model.RaidersOrderParamesIn;
import com.xiu.raiders.model.UpdateRaiderShoppingCatParamsIn;
/**
 * 
* @Description: TODO(购物车相关) 
* @author haidong.luo@xiu.com
* @date 2016年3月15日 下午6:34:23 
*
 */
@Service("eiraidersManager")
public class EIRaidersManagerImpl implements EIRaidersManager {
	private static final Logger logger = Logger.getLogger(EIRaidersManagerImpl.class);
	@Autowired
	private XiuRaidersFacade xiuRaidersFacade;
	
	@Override
	public Map<String, Object> getLastTenLotteryUsers(Integer number) {
		Map<String,Object> resultMap=new HashMap<String,Object>();//结果集
		Result result = null;
		try{
			result=xiuRaidersFacade.getLastTenLotteryUsers(number);
			if(result!=null){
				if(result.isSuccess()){
					resultMap.put("status", true);
					resultMap.put("errorCode", "0");
					resultMap.put("errorMsg", "成功");
					resultMap.put("lotteryList", result.getModels().get("lotteryList"));
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
			logger.error("调用最近中奖用户信息异常");
			resultMap.put("status", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> findSoonLottery(String number1) {
		Map<String,Object> resultMap=new HashMap<String,Object>();//结果集
		Result result = null;
		try{
			result=xiuRaidersFacade.findSoonLottery(Integer.valueOf(number1));
			if(result!=null){
				if(result.isSuccess()){
					resultMap.put("status", true);
					resultMap.put("errorCode", "0");
					resultMap.put("errorMsg", "成功");
					resultMap.put("time", result.getModels().get("time"));
					resultMap.put("soonLotteryList", result.getModels().get("soonLotteryList"));
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
			logger.error("调用马上开奖信息异常");
			resultMap.put("status", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> getHotGood(String isHot,String page,String pageSize,Integer orderSeq,Integer sort) {
		Map<String,Object> resultMap=new HashMap<String,Object>();//结果集
		Result result = null;
		GoodQueryParams params=new GoodQueryParams();
		params.setIsHot(isHot);
		params.setCurrentPage(Integer.valueOf(page));//页码
		params.setPageSize(Integer.valueOf(pageSize)); //每页显示条数
		params.setOrderSeq(orderSeq);
		params.setSort(sort);
		try{
			result=xiuRaidersFacade.getHotGood(params);
			if(result!=null){
				if(result.isSuccess()){
					resultMap.put("status", true);
					resultMap.put("errorCode", "0");
					resultMap.put("errorMsg", "成功");
					resultMap.put("raidersList", result.getModels().get("raidersList"));
					resultMap.put("totalCount", result.getModels().get("totalCount"));
					resultMap.put("totalPage", result.getModels().get("totalPage"));
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
			logger.error("调用全部活动接口信息异常");
			resultMap.put("status", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> getMyRaidersIndex(String page, String pageSize,long userId,Integer isLottery) {
		Map<String,Object> resultMap=new HashMap<String,Object>();//结果集
		Result result = null;
		GoodQueryParams params=new GoodQueryParams();
		params.setUserId(userId);
		params.setPageSize(Integer.valueOf(pageSize));//每页显示的个数
		params.setCurrentPage(Integer.valueOf(page)); //页码
		params.setIsLottery(isLottery);
		try{
			result=xiuRaidersFacade.getMyRaidersIndex(params);
			if(result!=null){
				if(result.isSuccess()){
					RaidersUserInfo info=new RaidersUserInfo();
					String userName=(String)result.getModels().get("userName");
					String userHeadUrl=(String)result.getModels().get("userHeadUrl");
					info.setUserName(userName);
					info.setUserHeadUrl(userHeadUrl);
					info.setUserId(userId);
					resultMap.put("status", true);
					resultMap.put("errorCode", "0");
					resultMap.put("errorMsg", "成功");
					resultMap.put("time", result.getModels().get("time"));
					resultMap.put("raidersList", result.getModels().get("raidersList"));
					resultMap.put("totalCount", result.getModels().get("totalCount"));
					resultMap.put("totalPage", result.getModels().get("totalPage"));
					resultMap.put("userInfo", info);
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
			logger.error("调用我的夺宝记录信息异常");
			resultMap.put("status", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return resultMap;
	}
	public Map<String,Object> getRaidersBuyList(Long raiderId,Long userId,String isOther){
		Map<String,Object> resultMap=new HashMap<String,Object>();//结果集
		Result result = null;
		GoodQueryParams params=new GoodQueryParams();
		params.setUserId(userId);
		params.setRaiderId(raiderId);
		params.setIsHot(isOther);
		try{
			result=xiuRaidersFacade.getRaidersBuyList(params);
			if(result.isSuccess()){
				resultMap.put("status", true);
				resultMap.put("errorCode", "0");
				resultMap.put("errorMsg", "成功");
				resultMap.put("raidersList", result.getModels().get("raidersList"));
				resultMap.put("total", result.getModels().get("total"));
			}else{
				resultMap.put("status", false);
				Set<String> keySet = result.getErrorMessages().keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					resultMap.put("errorCode", key);
					resultMap.put("errorMsg", result.getErrorMessages().get(key));
				}
			}
		}catch(Exception e){
			logger.error("调用查询购买详情接口异常");
			resultMap.put("status", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return resultMap;
	}
	public Map<String,Object> findOtheRaidersIndex(String page,String pageSize,
			String userId,Integer isLottery){
		Map<String,Object> resultMap=new HashMap<String,Object>();//结果集
		Result result = null;
		GoodQueryParams params=new GoodQueryParams();
		params.setUserId(Long.parseLong(userId));
		params.setPageSize(Integer.valueOf(pageSize));//每页显示的个数
		params.setCurrentPage(Integer.valueOf(page)); //页码
		params.setIsLottery(isLottery);
		try{
			result=xiuRaidersFacade.getFindOtheRaidersIndex(params);
			if(result!=null){
				if(result.isSuccess()){
					resultMap.put("status", true);
					resultMap.put("errorCode", "0");
					resultMap.put("errorMsg", "成功");
					resultMap.put("time", result.getModels().get("time"));
					resultMap.put("raidersList", result.getModels().get("raidersList"));
					resultMap.put("totalCount", result.getModels().get("totalCount"));
					resultMap.put("totalPage", result.getModels().get("totalPage"));
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
			logger.error("调用他人的夺宝记录信息异常");
			resultMap.put("status", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return resultMap;
	}
	@Override
	public Map<String, Object> findRaiderSupperdList(String participateId) {
		Map<String,Object> resultMap=new HashMap<String,Object>();//结果集
		Result result = null;
		try{
			result=xiuRaidersFacade.findRaiderSupperdList(participateId);
			if(result!=null){
				if(result.isSuccess()){
					resultMap.put("status", true);
					resultMap.put("errorCode", "0");
					resultMap.put("errorMsg", "成功");
					resultMap.put("receiveList", result.getModels().get("receiveList"));
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
			logger.error("调用我的夺宝记录信息异常");
			resultMap.put("status", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return resultMap;
	}
	@Override
	public Map<String, Object> addRaiderLotteryAdd(String raiderId,
			String userId, String addId,String addressInfo,String realName,String addressPrefix,String mobile) {
		Map<String,Object> resultMap=new HashMap<String,Object>();//结果集
		Result result = null;
		GoodQueryParams params=new GoodQueryParams();
		params.setAddId(Long.valueOf(addId));
		params.setUserId(Long.valueOf(userId));
		params.setRaiderId(Long.valueOf(raiderId));
		params.setAddressInfo(addressInfo);
		params.setRealName(realName);
		params.setAddressPrefix(addressPrefix);
		params.setMobile(mobile);
		try{
			result=xiuRaidersFacade.addRaiderLotteryAdd(params);
			if(result!=null){
				if(result.isSuccess()){
					resultMap.put("status", true);
					resultMap.put("errorCode", "0");
					resultMap.put("errorMsg", "成功");
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
			logger.error("调用添加收货地址ID信息异常");
			resultMap.put("status", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return resultMap;
	}
	@Override
	public Map<String, Object> findPastRaider(Long raiderId, String page,
			String pageSize) {
		Map<String,Object> resultMap=new HashMap<String,Object>();//结果集
		Result result = null;
		GoodQueryParams params=new GoodQueryParams();
		params.setRaiderId(raiderId);
		params.setCurrentPage(Integer.valueOf(page));
		params.setPageSize(Integer.valueOf(pageSize));
		try{
			result=xiuRaidersFacade.findPastRaider(params);
			if(result!=null){
				if(result.isSuccess()){
					resultMap.put("status", true);
					resultMap.put("errorCode", "0");
					resultMap.put("errorMsg", "成功");
					resultMap.put("lotteryList", result.getModels().get("lotteryList"));
					resultMap.put("totalCount", result.getModels().get("totalCount"));
					resultMap.put("totalPage", result.getModels().get("totalPage"));
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
			logger.error("调用往期记录信息异常");
			resultMap.put("status", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> payRaider(Map<String,Object> params) {
		Map<String,Object> resultMap=new HashMap<String,Object>();
		Boolean isSuccess=false;
		Long userId=ObjectUtil.getLong(params.get("userId"),null);
		PayRaiderParamIn paramsIn =new PayRaiderParamIn();
		paramsIn.setUserId(ObjectUtil.getLong(userId));
		paramsIn.setLoginId(ObjectUtil.getLong(userId));
		paramsIn.setPayType((String)params.get("payPlatform"));//"AlipayWire");
		paramsIn.setPayMedium((String)params.get("payMedium"));
		paramsIn.setOrderAmount(params.get("orderAmount")+"");
		paramsIn.setOrderNo((String)params.get("orderCode"));
		paramsIn.setOrderId(params.get("orderId")+"");
		paramsIn.setIsVirtualPay(ObjectUtil.getInteger(params.get("isVirtualPay"),0));
		Result result=xiuRaidersFacade.payRaider(paramsIn);
		if(result==null){
			resultMap.put("code", "0");
			resultMap.put("code", "调用raider系统异常");
		}else if(result.isSuccess()){
			PayRaiderInfoOut vo=(PayRaiderInfoOut)result.getDefaultModel();
			resultMap.put("code", vo.getRetCode());
			resultMap.put("msg",vo.getErrorMsg());
			resultMap.put("url",vo.getPayInfo());
			resultMap.put("isVirtualPayAll",vo.getIsVirtualPayAll());
			isSuccess=true;
		}else{
			PayRaiderInfoOut vo=(PayRaiderInfoOut)result.getDefaultModel();
			resultMap.put("code", vo.getRetCode());
			resultMap.put("msg",vo.getErrorMsg());
		}
		
		resultMap.put("status", isSuccess);
		
		return resultMap;
	}

	/**
	 * 获取夺宝商品详情
	 */
	public Map<String, Object> getRaiderGoodInfo(Map<String,Object> params) {
		Long raiderId=(Long)params.get("raiderId");
		String userId=(String)params.get("userId");
		Long loginUserId=null;
		if(StringUtils.isNotEmpty(userId)){
			loginUserId=Long.parseLong(userId);
		}
		Map<String,Object> resultMap=new HashMap<String,Object>();
		Boolean isSuccess=false;

		GetRaiderGoodInfoParamesIn in=new GetRaiderGoodInfoParamesIn();
		in.setRaiderId(raiderId);
		in.setLoginUserId(loginUserId);
		Result result=xiuRaidersFacade.getRaiderGoodInfo(in);
		result.isSuccess();
		if(result==null||!result.isSuccess()){
			resultMap.put("code", "0");
			resultMap.put("code", "调用raider系统异常");
		}else {
			
			GetRaiderGoodInfoParamesOut info=(GetRaiderGoodInfoParamesOut)result.getDefaultModel();
			
			RaidersGoodVo good=new RaidersGoodVo();
			good.setBrandName(info.getBrandName());
			good.setHadBuyNumber(info.getHadBuyNumber());
			good.setBuyRatio(info.getRate()+"");
			good.setGoodName(info.getGoodsName());
			good.setGoodSn(info.getGoodsSn());
			good.setLessNumber(info.getTotalNumber()-info.getHadBuyNumber());
			good.setLotteryNumber(info.getLotteryNumber()+"");
			RaidersParticipateVo lottey=info.getLotteryParticipate();
			if(lottey!=null){
				RaidersParticipate lotteryParticipate=new RaidersParticipate();
				lotteryParticipate.setUserHeadPicUrl(lottey.getUserHeadPicUrl());
				lotteryParticipate.setUserName(lottey.getUserName());
				lotteryParticipate.setIP(lottey.getIp());
				lotteryParticipate.setIPAdr(lottey.getIpAddr());
				lotteryParticipate.setBuyNumber(lottey.getParticipateNum());
				lotteryParticipate.setUserId(lottey.getUserId());
				good.setLotteryParticipate(lotteryParticipate);
			}
			
			good.setLotteryTime(info.getLotteryTime());
			good.setMinBuyNumber(info.getMinBuyNumber());
			good.setRaiderCode(info.getRaiderCode());
			good.setRaiderId(info.getRaiderId());
			good.setSoldOutTime(info.getSoldOutTime());
			good.setStartTime(info.getStartTime());
			good.setTotalNumber(info.getTotalNumber());
			good.setStatus(info.getStatus());
			good.setGoodId(info.getGoodsId());
			good.setPictureList(info.getGoodPics());
			good.setNextRaiderId(info.getNextRaiderCode());
			good.setGoodsDetails(info.getGoodsDetails());
			good.setLessBuyPrice(info.getLessBuyPrice());
			good.setDefaultBuyPrice(info.getDefaultBuyPrice());
			good.setMaxBuyPrice(info.getMaxBuyPrice());
			if(userId!=null){
				GetMyRaiderNumberParamsIn mynumber=new GetMyRaiderNumberParamsIn();
				mynumber.setLoginUserId(ObjectUtil.getLong(params.get("userId")));
				mynumber.setRaiderId((Long)params.get("raiderId"));
				Result mynumberResult=xiuRaidersFacade.getMyRaiderNumber(mynumber);
				if(mynumberResult.isSuccess()){
					GetMyRaiderNumberParamsOut out=(GetMyRaiderNumberParamsOut)mynumberResult.getDefaultModel();
					List<RaidersParticipateNumberVo> numbers=new ArrayList<RaidersParticipateNumberVo>();
					List<RaidersNumberVo>  rnums=out.getRaidersNumberVos();
					for (RaidersNumberVo num:rnums) {
						RaidersParticipateNumberVo number=new RaidersParticipateNumberVo();
						number.setIsLottery(num.getIsLottery());
						number.setParticipateNumber(num.getRaiderNumber());
						number.setType(num.getType());
						numbers.add(number);
					}
					good.setParticipateNumberList(numbers);
				}
			}
		//	SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		//	String time=format.format(new Date());
			resultMap.put("time", info.getNowTime());//系统当前时间
			resultMap.put("model", good);
			
//			PayRaiderInfoOut vo=(PayRaiderInfoOut)result.getDefaultModel();
//			resultMap.put("code", vo.getRetCode());
//			resultMap.put("msg",vo.getErrorMsg());
//			resultMap.put("url",vo.getPayInfo());
			isSuccess=true;
		}
		
		resultMap.put("status", isSuccess);
		
		return resultMap;
	}

	
	public Map<String, Object> getMyRaiderNumber(Map<String,Object> params){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		Boolean isSuccess=true;
		GetMyRaiderNumberParamsIn order=new GetMyRaiderNumberParamsIn();
		order.setLoginUserId((Long)params.get("loginUserId"));
		order.setRaiderId((Long)params.get("raiderId"));
		//调接口生成订单
		Result result=xiuRaidersFacade.getMyRaiderNumber(order);
		resultMap.put("order", result.getDefaultModel());
		resultMap.put("status", isSuccess);
		return resultMap;
		
	}
	
	public Map<String, Object> createRaiderOrder(Map<String,Object> params) {
		Map<String,Object> resultMap=new HashMap<String,Object>();
		Boolean isSuccess=true;
		RaidersOrderParamesIn order=(RaidersOrderParamesIn)params.get("orderReq");
		//调接口生成订单
		Result result=xiuRaidersFacade.createRaiderOrder(order);
		resultMap.put("order", result.getDefaultModel());
		resultMap.put("status", isSuccess);
		return resultMap;
	}

	/**
	 * 加入购物车
	 * @param params
	 * @return
	 */
	public Map<String,Object> addRaiderShoppingCat(Map<String,Object> params){
		 Map<String,Object> resultMap=new HashMap<String,Object>();
		Boolean isSuccess=false;
		String msg="";
		Integer total=0;
		AddRaiderShoppingCatParamsIn in=new AddRaiderShoppingCatParamsIn();
		in.setQuantity((Integer)params.get("quantity"));
		in.setRaiderId((Long)params.get("raiderId"));
		in.setUserId((Long)params.get("userId"));
		Result result=xiuRaidersFacade.addRaiderShoppingCat(in);
		Integer status=(Integer)result.getModels().get("status");
		if(status==1){
			isSuccess=true;
			total=(Integer)result.getModels().get("num");
			resultMap.put("num", total);
		}else{
			msg=(String)result.getModels().get("msg");
		}
		resultMap.put("msg", msg);
		resultMap.put("isSuccess", isSuccess);
		return resultMap;
	}
	

	/**
	 * 修改购物车
	 * @param params
	 * @return
	 */
	public Map<String,Object> updateRaiderShoppingCat(Map<String,Object> params){
		 Map<String,Object> resultMap=new HashMap<String,Object>();
		Boolean isSuccess=false;
		UpdateRaiderShoppingCatParamsIn in=new UpdateRaiderShoppingCatParamsIn();
		in.setQuantity((Integer)params.get("quantity"));
		in.setRaiderId((Long)params.get("raiderId"));
		in.setUserId((Long)params.get("userId"));
		Result result=xiuRaidersFacade.updateRaiderShoppingCat(in);
		Integer status=(Integer)result.getModels().get("status");
		if(status==1){
			isSuccess=true;
		}
		resultMap.put("isSuccess", isSuccess);
		return resultMap;
	}

	@Override
	public List<RaidersOrderGoodVo> getShoppingCartGoodsList(
		Map<String, Object> params) {
		 Map<String,Object> resultMap=new HashMap<String,Object>();
		List<RaidersOrderGoodVo> rsgList=new ArrayList<RaidersOrderGoodVo>();
		Boolean isSuccess=false;
		
		GetRaiderShoppingCatParamsIn in=new GetRaiderShoppingCatParamsIn();
		in.setUserId((Long)params.get("userId"));
		Result result=xiuRaidersFacade.getRaiderShoppingCat(in);
		Integer status=(Integer)result.getModels().get("status");
		GetRaiderShoppingCatParamsOut out=(GetRaiderShoppingCatParamsOut)result.getModels().get("model");
		if(status==1){
			isSuccess=true;
			List<RaiderShoppingCatGoodInfoVo> catList=	out.getShoppingCatGoods();
			for (RaiderShoppingCatGoodInfoVo cat:catList) {
				RaidersOrderGoodVo good=new RaidersOrderGoodVo();
				good.setBrandName(cat.getBrandName());
				good.setBuyRatio(cat.getRate());
				good.setDefaultBuyPrice(cat.getDefaultBuyPrice());
				good.setGoodName(cat.getGoodsName());
				good.setGoodsQuantity(cat.getQuantity());
				good.setHadBuyNumber(cat.getHadBuyNumber());
				good.setLessNumber(ObjectUtil.getInteger(cat.getLessTotal(),0));
				good.setMaxBuyPrice(cat.getMaxBuyPrice());
				good.setMinBuyNumber(cat.getMinBuyNumber());
				good.setPictureList(cat.getGoodPics());
				good.setRaiderId(cat.getRaiderId());
				good.setStatus(cat.getStatus());
				good.setTotalNumber(cat.getTotalNumber());
				good.setLessBuyPrice(cat.getLessBuyPrice());
				
				good.setOldRaiderCode(cat.getOldRaiderCode());
				good.setOldRaiderId(cat.getOldRaiderId());
				rsgList.add(good);
			}
		}
		resultMap.put("isSuccess", isSuccess);
		return rsgList;
	}

	public Map<String, Object> delRaiderShoppingCat(Map<String, Object> params) {
		 Map<String,Object> resultMap=new HashMap<String,Object>();
			Boolean isSuccess=false;
			DelRaiderShoppingCatParamsIn in=new DelRaiderShoppingCatParamsIn();
			in.setRaiderIds((List)params.get("raiderIds"));
			in.setUserId((Long)params.get("userId"));
			Result result=xiuRaidersFacade.delRaiderShoppingCat(in);
			Integer status=(Integer)result.getModels().get("status");
			if(status==1){
				isSuccess=true;
			}
			resultMap.put("isSuccess", isSuccess);
			return resultMap;
	}

	public Map<String, Object> getRaiderShoppingCatNum(
			Map<String, Object> params) {
		   Map<String,Object> resultMap=new HashMap<String,Object>();
			Boolean isSuccess=false;
			Integer total=0;
			Long userId=(Long)params.get("userId");
			Result result=xiuRaidersFacade.getRaiderShoppingCatNum(userId);
			Integer status=(Integer)result.getModels().get("status");
			if(status==1){
				isSuccess=true;
				total=(Integer)result.getModels().get("num");
			}
			resultMap.put("num", total);
			resultMap.put("isSuccess", isSuccess);
			return resultMap;
	}
	
	
	public Map<String, Object> getVirtualAccountInfo(Long userId) {
		Map<String,Object> resultMap=new HashMap<String,Object>();
		Boolean isSuccess=false;
		Long total=0l ;
		GetVirtualAccountInfoOut out=new GetVirtualAccountInfoOut();
		Result result=xiuRaidersFacade.getVirtualAccountInfo(userId);
		Integer status=(Integer)result.getModels().get("status");
		if(status==1){
			isSuccess=true;
			out=(GetVirtualAccountInfoOut)result.getModels().get("model");
			total=out.getVtotalAmount();
		}
		resultMap.put("num", total);
		resultMap.put("isSuccess", isSuccess);
		return resultMap;
	}
	

}
