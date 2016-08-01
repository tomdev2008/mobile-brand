package com.xiu.mobile.portal.controller.raiders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xiu.mobile.core.utils.ObjectUtil;
import com.xiu.mobile.core.utils.XiuUtil;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.controller.BaseController;
import com.xiu.mobile.portal.ei.EIRaidersManager;
import com.xiu.mobile.portal.facade.utils.HttpUtil;
import com.xiu.mobile.portal.model.LoginResVo;
import com.xiu.mobile.portal.model.raiders.RaidersGoodVo;
import com.xiu.mobile.portal.model.raiders.RaidersOrderGoodVo;
import com.xiu.mobile.portal.model.raiders.RaidersOrderVo;
import com.xiu.mobile.portal.service.ILoginService;
import com.xiu.raiders.model.RaidersOrderParamesIn;
import com.xiu.raiders.model.RaidersOrderParamesOut;
import com.xiu.raiders.model.RaidersParticipateParamses;

/**
 * 	
* @Description: TODO(夺宝订单) 
* @author haidong.luo@xiu.com
* @date 2016年1月9日 上午10:07:10 
*
 */
@Controller
@RequestMapping("/raiderShoppingCat")
public class RaiderShoppingCatController extends BaseController{
	private Logger logger=Logger.getLogger(RaiderShoppingCatController.class);
	
	@Autowired
	private EIRaidersManager raidersManager;
	
	
	@Autowired
	private ILoginService loginService;
	
	
	/**
	 * 获取夺宝购物车详情
	 * @param jsoncallback
	 * @param request
	 * @param response
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value = "/getShoppingCartGoodsList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getShoppingCartGoodsList(String jsoncallback,HttpServletRequest request,HttpServletResponse response)  {
		// 存储返回结果值
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		List<RaidersOrderGoodVo> shoppingCartGoodsList = new ArrayList<RaidersOrderGoodVo>();
		HashMap<String, Object> sortMap = new HashMap<String, Object>();
		boolean loginStatus = true;
		try {
			String tokenId = SessionUtil.getTokenId(request);
			Map<String, Object> params = new LinkedHashMap<String, Object>();
			params.put("tokenId", tokenId);
			params.putAll(getDeviceParams(request));
			if (!StringUtils.isEmpty(tokenId) && loginService.checkOnlineStatus(params)) {
				// 已登录就查询用户购物车信息
				// 从cookie中获得userId
				LoginResVo loginResVo = SessionUtil.getUser(request);
				Long userId = Long.parseLong(loginResVo.getUserId());
				HashMap<String, Object> valMap = new HashMap<String, Object>();
				valMap.put("userId", userId);
				logger.info("登陆用户获取用户夺宝购物车列表数据start userId:"+userId);
				//##############待完善####
				shoppingCartGoodsList = raidersManager.getShoppingCartGoodsList(valMap);
				logger.info("登陆用户获取用户夺宝购物车列表数据end   userId:"+userId);
			} 
			Integer total=0;
			for (RaidersOrderGoodVo orderGood:shoppingCartGoodsList) {
				total=total+orderGood.getGoodsQuantity();
			}
			sortMap.put("totalAmt", total);
			if (null != shoppingCartGoodsList && shoppingCartGoodsList.size() > 0) {
				RaidersOrderVo calcOrderReqVO = new RaidersOrderVo();
				calcOrderReqVO.setOrderGoods(shoppingCartGoodsList);
				calcOrderReqVO.setTotalAmount(sortMap.get("totalAmt")+"");
				calcOrderReqVO.setNeedAmount(sortMap.get("totalAmt")+"");
				
				model.put("result", true);
				model.put("errorCode", ErrorCode.Success.getErrorCode());
				model.put("errorMsg", ErrorCode.Success.getErrorMsg());
				model.put("raidersOrder", calcOrderReqVO);
			} else {
				model.put("result", false);
				model.put("errorCode", ErrorCode.NoShoppingCartGoods.getErrorCode());
				model.put("errorMsg", ErrorCode.NoShoppingCartGoods.getErrorMsg());
			}
		} catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询夺宝购物车 商品信息列表时发生异常：" + e.getMessage(), e);
		}
		logger.info("查询夺宝购物车 商品信息返回结果：" + JSON.toJSONString(model));
		return JsonUtils.beanjsonP(jsoncallback, model);// beanjsonP2(当集合的key和value都一样的时候显示不了重复数据)
	}
	
	
	/**
	 * 加入购物车
	 */
	@RequestMapping(value = "/addRaiders", produces = "text/html;charset=UTF-8")
	public @ResponseBody String addGoods(String jsoncallback,HttpServletRequest request,HttpServletResponse response)  {
		// 存储返回结果值
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		Boolean isSuccess=false;
		try {
			String raiderIdStr = request.getParameter("raiderId");
			String userIdStr = SessionUtil.getUserId(request);
			logger.info("进入-加入夺宝购物车 活动id:"+raiderIdStr+",用户id:"+userIdStr);
			Long userId=ObjectUtil.getLong(userIdStr, null);
			Long raiderId=ObjectUtil.getLong(raiderIdStr,null);
			Integer num=0;
			
			if(raiderId==null){
				resultMap.put("result", isSuccess);
				resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, resultMap);
			}
			//购买数量
			int quantity = NumberUtils.toInt(request.getParameter("quantity"), 1);
			
			//1.查询商品剩余
			Map<String,Object> getInfoparams =new HashMap<String,Object>();
			getInfoparams.put("raiderId", raiderId);
			logger.info("加入夺宝购物车-获取活动详情 begin 活动id:"+raiderIdStr+",用户id:"+userIdStr);
			Map<String,Object> goodInfoMap = raidersManager.getRaiderGoodInfo(getInfoparams);
			logger.info("加入夺宝购物车-获取活动详情  end 活动id:"+raiderIdStr+",用户id:"+userIdStr);

			Boolean isGeInfoSuccess=(Boolean)goodInfoMap.get("status");
            if(isGeInfoSuccess){
				RaidersGoodVo raidersGoodVo=(RaidersGoodVo)goodInfoMap.get("model");
				//检查库存是否够
				Integer less=raidersGoodVo.getLessNumber();
				Integer minBuyNum=raidersGoodVo.getMinBuyNumber();
				if(quantity<minBuyNum){//强制数量变为最小购买数
					quantity=minBuyNum;
				}
				if(less<quantity){//如果库存不够则只能购买剩下的数量
					quantity=less;
				}
				if(quantity==0){
					resultMap.put("result", isSuccess);
					resultMap.put("errorCode",ErrorCode.RaiderNoStock.getErrorCode() ); 
					resultMap.put("errorMsg",ErrorCode.RaiderNoStock.getErrorMsg());
					return JsonUtils.bean2jsonP(jsoncallback, resultMap);
				}
				//加入购物车
				logger.info("加入夺宝购物车- begin 活动id:"+raiderIdStr+",用户id:"+userIdStr);
				Map addShoppingCatParam=new HashMap();
				addShoppingCatParam.put("userId", userId);
				addShoppingCatParam.put("quantity", quantity);
				addShoppingCatParam.put("raiderId", raiderId);
				Map result=raidersManager.addRaiderShoppingCat(addShoppingCatParam);
				isSuccess=(Boolean)result.get("isSuccess");
				if(isSuccess){
					num=(Integer)result.get("num");
					resultMap.put("num",num);
					logger.info("加入夺宝购物车-end 活动id:"+raiderIdStr+",用户id:"+userIdStr);
					resultMap.put("result", isSuccess);
					resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
					return JsonUtils.bean2jsonP(jsoncallback, resultMap);
				}else{
	            	resultMap.put("result", isSuccess);
					resultMap.put("errorCode","-1");
					resultMap.put("errorMsg",result.get("msg"));
					return JsonUtils.bean2jsonP(jsoncallback, resultMap);
				}
				
				
            }else{
            	resultMap.put("result", isSuccess);
				resultMap.put("errorCode",goodInfoMap.get("errorCode") );
				resultMap.put("errorMsg",goodInfoMap.get("errorMsg"));
				return JsonUtils.bean2jsonP(jsoncallback, resultMap);
            }
			
			
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("商品加入购物车异常：" + e.getMessage(), e);
		}
		logger.info("商品加入购物车返回结果：" + JSON.toJSONString(resultMap));
		return JsonUtils.beanjsonP(jsoncallback, resultMap);
	}
	
	/**
	 * 修改购物车
	 */
	@RequestMapping(value = "/updateRaiders", produces = "text/html;charset=UTF-8")
	public @ResponseBody String updateRaiders(String jsoncallback,HttpServletRequest request,HttpServletResponse response)  {
		// 存储返回结果值
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			String raiderIdStr = request.getParameter("raiderId");
			String userIdStr = SessionUtil.getUserId(request);
			logger.info("进入-修改夺宝购物车 活动id:"+raiderIdStr+",用户id:"+userIdStr);
			Long userId=ObjectUtil.getLong(userIdStr, null);
			Long raiderId=ObjectUtil.getLong(raiderIdStr,null);
			
			if(raiderId==null){
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, resultMap);
			}
			//购买数量
			int quantity = NumberUtils.toInt(request.getParameter("quantity"), 1);
			
			//1.查询商品剩余
			Map<String,Object> getInfoparams =new HashMap<String,Object>();
			getInfoparams.put("raiderId", raiderId);
			logger.info("修改夺宝购物车-获取活动详情 begin 活动id:"+raiderIdStr+",用户id:"+userIdStr);
			Map<String,Object> goodInfoMap = raidersManager.getRaiderGoodInfo(getInfoparams);
			logger.info("修改夺宝购物车-获取活动详情  end 活动id:"+raiderIdStr+",用户id:"+userIdStr);

			Boolean isGeInfoSuccess=(Boolean)goodInfoMap.get("status");
            if(isGeInfoSuccess){
				RaidersGoodVo raidersGoodVo=(RaidersGoodVo)goodInfoMap.get("model");
				//2.生成参与记录
				//检查库存是否够
				Integer less=raidersGoodVo.getLessNumber();
				Integer minBuyNum=raidersGoodVo.getMinBuyNumber();
				if(quantity<minBuyNum){//强制数量变为最小购买数
					quantity=minBuyNum;
				}
				if(less<quantity){//如果库存不够则只能购买剩下的数量
					quantity=less;
				}
				if(quantity==0){
					resultMap.put("result", false);
					resultMap.put("errorCode",ErrorCode.RaiderNoStock.getErrorCode() ); 
					resultMap.put("errorMsg",ErrorCode.RaiderNoStock.getErrorMsg());
					return JsonUtils.bean2jsonP(jsoncallback, resultMap);
				}
				//加入购物车
				logger.info("修改夺宝购物车- begin 活动id:"+raiderIdStr+",用户id:"+userIdStr);
				Map updateShoppingCatParam=new HashMap();
				updateShoppingCatParam.put("userId", userId);
				updateShoppingCatParam.put("quantity", quantity);
				updateShoppingCatParam.put("raiderId", raiderId);
				raidersManager.updateRaiderShoppingCat(updateShoppingCatParam);
				logger.info("修改夺宝购物车-end 活动id:"+raiderIdStr+",用户id:"+userIdStr);
				
	         	resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, resultMap);
				
            }else{
            	resultMap.put("result", false);
				resultMap.put("errorCode",goodInfoMap.get("errorCode") );
				resultMap.put("errorMsg",goodInfoMap.get("errorMsg"));
				return JsonUtils.bean2jsonP(jsoncallback, resultMap);
            }
			
			
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("商品修改购物车异常：" + e.getMessage(), e);
		}
		logger.info("商品修改购物车返回结果：" + JSON.toJSONString(resultMap));
		return JsonUtils.beanjsonP(jsoncallback, resultMap);
	}
	
	
	/**
	 * 删除购物车
	 */
	@RequestMapping(value = "/delRaiders", produces = "text/html;charset=UTF-8")
	public @ResponseBody String delRaiders(String jsoncallback,HttpServletRequest request,HttpServletResponse response)  {
		// 存储返回结果值
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			String raiderIdStr = request.getParameter("raiderIds");
			String userIdStr = SessionUtil.getUserId(request);
			logger.info("进入-删除夺宝购物车 活动id:"+raiderIdStr+",用户id:"+userIdStr);
			Long userId=ObjectUtil.getLong(userIdStr, null);
			
			if(raiderIdStr==null||userId==null){
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, resultMap);
			}
				//删除购物车
				logger.info("删除夺宝购物车- begin 活动id:"+raiderIdStr+",用户id:"+userIdStr);
				Map params=new HashMap();
				params.put("userId", userId);
				List<Long> raiderIdList=new ArrayList<Long>();
				String[] raiderIds=raiderIdStr.split(",");
				for (int i = 0; i < raiderIds.length; i++) {
					Long raiderId=ObjectUtil.getLong(raiderIds[i]);
					if(raiderId!=null){
						raiderIdList.add(raiderId);
					}
				}
				params.put("raiderIds", raiderIdList);
				raidersManager.delRaiderShoppingCat(params);
				logger.info("删除夺宝购物车-end 活动id:"+raiderIdStr+",用户id:"+userIdStr);
				
	         	resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, resultMap);
			
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("商品删除购物车异常：" + e.getMessage(), e);
		}
		logger.info("商品删除购物车返回结果：" + JSON.toJSONString(resultMap));
		return JsonUtils.beanjsonP(jsoncallback, resultMap);
	}
	
	/**
	 * 获取购物车个数
	 */
	@RequestMapping(value = "/getShoppingCatNum", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getShoppingCatNum(String jsoncallback,HttpServletRequest request,HttpServletResponse response)  {
		// 存储返回结果值
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		String userIdStr = SessionUtil.getUserId(request);
		logger.info("进入-查询夺宝购物车个数 用户id:"+userIdStr);
		Long userId=ObjectUtil.getLong(userIdStr, null);
		Integer num=0;

		if(userId==null){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		try {
				//删除购物车
				logger.info("删除夺宝购物车- begin 用户id:"+userIdStr);
				Map params=new HashMap();
				params.put("userId", userId);
				Map<String,Object> numMap=raidersManager.getRaiderShoppingCatNum(params);
				Boolean isSuccess=(Boolean)numMap.get("isSuccess");
				if(isSuccess){
					num=(Integer)numMap.get("num");
				}
				logger.info("删除夺宝购物车-end 用户id:"+userIdStr);
	         	resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
				resultMap.put("num", num);
				return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("商品加入购物车异常：" + e.getMessage(), e);
		}
		logger.info("商品加入购物车返回结果：" + JSON.toJSONString(resultMap));
		return JsonUtils.beanjsonP(jsoncallback, resultMap);
	}
	
	
	
//	/***
//	 * 同步夺宝购物车
//	 * @param jsoncallback
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping(value = "/syncRaiders", produces = "text/html;charset=UTF-8")
//	public @ResponseBody String synGoods(String jsoncallback,HttpServletRequest request,HttpServletResponse response)  {
//		// 存储返回结果值
//		Map<String, Object> model = new LinkedHashMap<String, Object>();
//		try {
//
//			// 未登录就取wap/app传过来的购物车信息 如果不传递则取cookie里的商品信息
//			// 商品信息的json串
//			String raiders = request.getParameter("raiders");
//			if (StringUtils.isEmpty(raiders)) {
//				raiders = SessionUtil.getRaiderShoppingCartGoodsList(request);
//			}
//			logger.info("获取传递商品列表参数为："+raiders);	
//			// 如果不传递
//			if (StringUtils.isEmpty(raiders) || raiders.equals("") || raiders.equals("[]")) {
//				model.put("result", true);
//				model.put("errorCode", ErrorCode.Success.getErrorCode());
//				model.put("errorMsg", ErrorCode.Success.getErrorMsg());
//				return JsonUtils.bean2jsonP(jsoncallback, model);
//			}
//			// 如果参数不为空 或者不是已[开头 已]结尾则认为不是一个合格的json参数
//			if ((!raiders.startsWith("[")) && (!raiders.endsWith("]"))) {
//				model.put("result", false);
//				model.put("errorCode", ErrorCode.ParamsError.getErrorCode());
//				model.put("errorMsg", ErrorCode.ParamsError.getErrorMsg());
//				return JsonUtils.bean2jsonP(jsoncallback, model);
//			}
//			String userIdStr = SessionUtil.getUserId(request);
//			Long userId=ObjectUtil.getLong(userIdStr, null);
//
//			// 转换成商品列表信息
//			JSONArray jsonArray = JSONArray.fromObject(raiders);
//			for (int i = 0; i < jsonArray.size(); i++) {
//				net.sf.json.JSONObject jsonObject = jsonArray.getJSONObject(i);
//				/** 夺宝活动Id */
//				String raiderIdStr = jsonObject.getString("raiderId");
//				Long raiderId=ObjectUtil.getLong(raiderIdStr,null);
//
//				/** 购买数量 */
//				int quantity = jsonObject.getInt("goodsQuantity");
//				//1.查询商品剩余
//				Map<String,Object> getInfoparams =new HashMap<String,Object>();
//				getInfoparams.put("raiderId", raiderId);
//				logger.info("加入夺宝购物车-获取活动详情 begin 活动id:"+raiderId+",用户id:"+userIdStr);
//				Map<String,Object> goodInfoMap = raidersManager.getRaiderGoodInfo(getInfoparams);
//				logger.info("加入夺宝购物车-获取活动详情  end 活动id:"+raiderId+",用户id:"+userIdStr);
//
//				Boolean isGeInfoSuccess=(Boolean)goodInfoMap.get("status");
//	            if(isGeInfoSuccess){
//					RaidersGoodVo raidersGoodVo=(RaidersGoodVo)goodInfoMap.get("model");
//					//2.生成参与记录
//					//检查库存是否够
//					Integer less=raidersGoodVo.getLessNumber();
//					Integer minBuyNum=raidersGoodVo.getMinBuyNumber();
//					if(quantity<minBuyNum){//强制数量变为最小购买数
//						quantity=minBuyNum;
//					}
//					if(less<quantity){//如果库存不够则只能购买剩下的数量
//						quantity=less;
//					}
//					if(quantity!=0){
//						//查询购物车是否有该活动
//						 //如果有则修改
//						
//						 //没有则新增
//						
//					}
//	            }
//			}
//			// 清空cookie里购物车的数据
//			SessionUtil.delRaiderShoppingCartGoods(response);
//			// 加入购物车成功
//			model.put("result", true);
//			model.put("errorCode", ErrorCode.Success.getErrorCode());
//			model.put("errorMsg", ErrorCode.Success.getErrorMsg());
//		} catch (Exception e) {
//			model.put("result", false);
//			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
//			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
//			logger.error("同步购物车商品异常：" + e.getMessage(), e);
//		}
//		logger.info("同步购物车商品返回结果：" + JSON.toJSONString(model));
//		return JsonUtils.beanjsonP(jsoncallback, model);
//	}
//	

	/**
	 * 订单确认价格重新计算
	 * @param jsoncallback
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/calOrder", produces = "text/html;charset=UTF-8")
	public @ResponseBody String calOrder(String jsoncallback,HttpServletRequest request,HttpServletResponse response)  {
		// 存储返回结果值
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			// 未登录就取wap/app传过来的购物车信息 如果不传递则取cookie里的商品信息
			// 商品信息的json串
			String raiders = request.getParameter("raiders");
			if (StringUtils.isEmpty(raiders)) {
				raiders = SessionUtil.getRaiderShoppingCartGoodsList(request);
			}
			logger.info("获取传递商品列表参数为："+raiders);	
			// 如果不传递
			if (StringUtils.isEmpty(raiders) || raiders.equals("") || raiders.equals("[]")) {
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, resultMap);
			}
			// 如果参数不为空 或者不是已[开头 已]结尾则认为不是一个合格的json参数
			if ((!raiders.startsWith("[")) && (!raiders.endsWith("]"))) {
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.ParamsError.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.ParamsError.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, resultMap);
			}
			String userIdStr = SessionUtil.getUserId(request);
			Long userId=ObjectUtil.getLong(userIdStr, null);

			// 转换成商品列表信息
			JSONArray jsonArray = JSONArray.fromObject(raiders);
			// 计算订单
			RaidersOrderVo calcOrderReqVO = new RaidersOrderVo();
			//商品列表
			List<RaidersOrderGoodVo> orderGoods=new ArrayList<RaidersOrderGoodVo>();
			Integer allTotalPay=0;//总价
			for (int i = 0; i < jsonArray.size(); i++) {
				net.sf.json.JSONObject jsonObject = jsonArray.getJSONObject(i);
				/** 夺宝活动Id */
				String raiderIdStr = jsonObject.getString("raiderId");
				Long raiderId=ObjectUtil.getLong(raiderIdStr,null);
				/** 购买数量 */
				int quantity = jsonObject.getInt("goodsQuantity");
				Map<String,Object> params =new HashMap<String,Object>();
				params.put("raiderId", raiderId);
				params.put("userId", userId+"");
				Map<String,Object> goodInfoMap = raidersManager.getRaiderGoodInfo(params);
				Boolean isSuccess=(Boolean)goodInfoMap.get("status");
				if (isSuccess) {
					RaidersGoodVo raidersGoodVo=(RaidersGoodVo)goodInfoMap.get("model");
					//计算价格
					//再次确认数量是否正确
					Integer less=raidersGoodVo.getLessNumber();
					Integer minBuyNum=raidersGoodVo.getMinBuyNumber();
					if(quantity<minBuyNum){//强制数量变为最小购买数
						quantity=minBuyNum;
					}
					if(less<quantity){//如果库存不够则只能购买剩下的数量
						quantity=less;
					}
					Integer totalPay=quantity;
					allTotalPay=allTotalPay+totalPay;
					
					//设置订单商品信息
					RaidersOrderGoodVo orderGood=new RaidersOrderGoodVo(raidersGoodVo);
					orderGood.setGoodsQuantity(quantity);
					orderGood.setGoodsAmt(ObjectUtil.getLong(totalPay,0l));
					//如果默认显示购买数大于库存。取库存
					if(raidersGoodVo.getDefaultBuyPrice()!=null && raidersGoodVo.getDefaultBuyPrice()>less){
						orderGood.setDefaultBuyPrice((long)less);
					}else{
						orderGood.setDefaultBuyPrice(raidersGoodVo.getDefaultBuyPrice());
					}
					//如果每期最多购买几个大于库存，取库存
					if(raidersGoodVo.getLessBuyPrice()!=null && raidersGoodVo.getLessBuyPrice()>less){
						orderGood.setLessBuyPrice((long)less);
					}else{
						orderGood.setLessBuyPrice(raidersGoodVo.getLessBuyPrice());
					}
					orderGood.setMaxBuyPrice(raidersGoodVo.getMaxBuyPrice());
					orderGood.setNextRaiderId(raidersGoodVo.getNextRaiderId());
					if(totalPay>0){
						orderGoods.add(orderGood);
					}
				} 
			}
			
			Map<String, Object> virtual=raidersManager.getVirtualAccountInfo(userId);
			Boolean isSuccess=(Boolean )virtual.get("isSuccess");
			Long vtotalAmount=0l;
			if(isSuccess){
				vtotalAmount=(Long)virtual.get("num");
			}
			
			calcOrderReqVO.setTotalAmount(allTotalPay+"");
			calcOrderReqVO.setNeedAmount(allTotalPay+"");
			calcOrderReqVO.setOrderGoods(orderGoods);
			
			resultMap.put("result", true);
			resultMap.put("raidersOrder", calcOrderReqVO);
			Double vMount=XiuUtil.getPriceDoubleFormat(vtotalAmount+"");
			resultMap.put("vtotalAmount", XiuUtil.getPriceDouble2Str(vMount/100));
			resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			

		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("计算订单时发生异常：" + e.getMessage(), e);
		}

		logger.info("计算订单返回结果：" + JSON.toJSONString(resultMap));
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	
	/**
	 * 支付订单
	 * @param jsoncallback
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toPay", produces = "text/html;charset=UTF-8")
	public @ResponseBody String toPay(String jsoncallback,HttpServletRequest request,HttpServletResponse response)  {
		// 存储返回结果值
		HashMap<String, Object> model = new HashMap<String, Object>();
		try {
			// 检查用户登陆状态
			if (!checkLogin(request)) {
				model.put("result", false);
				model.put("errorCode", ErrorCode.SessionTimeOut.getErrorCode());
				model.put("errorMsg", ErrorCode.SessionTimeOut.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, model);
			}

			String tokenId = SessionUtil.getTokenId(request);
			Map<String, Object> params = new LinkedHashMap<String, Object>();
			params.put("tokenId", tokenId);
			params.putAll(getDeviceParams(request));

			// 商品信息的json串
			String raiders = request.getParameter("raiders");
			if (StringUtils.isEmpty(raiders)) {
				raiders = SessionUtil.getRaiderShoppingCartGoodsList(request);
			}
			logger.info("获取传递商品列表参数为："+raiders);	
			// 如果不传递
			if (StringUtils.isEmpty(raiders) || raiders.equals("") || raiders.equals("[]")) {
				model.put("result", true);
				model.put("errorCode", ErrorCode.Success.getErrorCode());
				model.put("errorMsg", ErrorCode.Success.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, model);
			}
			// 如果参数不为空 或者不是已[开头 已]结尾则认为不是一个合格的json参数
			if ((!raiders.startsWith("[")) && (!raiders.endsWith("]"))) {
				model.put("result", false);
				model.put("errorCode", ErrorCode.ParamsError.getErrorCode());
				model.put("errorMsg", ErrorCode.ParamsError.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, model);
			}
			String userIdStr = SessionUtil.getUserId(request);
			Long userId=ObjectUtil.getLong(userIdStr, null);

			// 转换成商品列表信息
			JSONArray jsonArray = JSONArray.fromObject(raiders);
			// 计算订单
			Integer allTotalPay=0;
			//订单生成参数
			RaidersOrderParamesIn orderReqVO = new RaidersOrderParamesIn();
			orderReqVO.setUserId(userId);
			//参与记录列表
			List<RaidersParticipateParamses> rparticipates=new ArrayList<RaidersParticipateParamses>();
			for (int i = 0; i < jsonArray.size(); i++) {
				net.sf.json.JSONObject jsonObject = jsonArray.getJSONObject(i);
				/** 夺宝活动Id */
				String raiderIdStr = jsonObject.getString("raiderId");
				Long raiderId=ObjectUtil.getLong(raiderIdStr,null);
				/** 购买数量 */
				int quantity = jsonObject.getInt("goodsQuantity");
				//1.查询商品剩余
				Map<String,Object> getInfoparams =new HashMap<String,Object>();
				getInfoparams.put("raiderId", raiderId);
				logger.info("支付订单-获取活动详情 begin 活动id:"+raiderId+",用户id:"+userIdStr);
				Map<String,Object> goodInfoMap = raidersManager.getRaiderGoodInfo(getInfoparams);
				logger.info("支付订单-获取活动详情  end 活动id:"+raiderId+",用户id:"+userIdStr);

				Boolean isGeInfoSuccess=(Boolean)goodInfoMap.get("status");
	            if(isGeInfoSuccess){
					RaidersGoodVo raidersGoodVo=(RaidersGoodVo)goodInfoMap.get("model");
					//2.生成参与记录
					//检查库存是否够
					Integer less=raidersGoodVo.getLessNumber();
					Integer minBuyNum=raidersGoodVo.getMinBuyNumber();
					if(quantity<minBuyNum){//强制数量变为最小购买数
						quantity=minBuyNum;
					}
					if(less<quantity){//如果库存不够则只能购买剩下的数量
						quantity=less;
					}
					if(quantity!=0){//卖完则不加入订单列表
						Integer showAmount=quantity;//展示价格(单位元)
						Integer orderAmount=showAmount;//支付价格(单位分)
						
						allTotalPay=allTotalPay+orderAmount;//计算总价
						//设置参与信息
						RaidersParticipateParamses rparticipate=new RaidersParticipateParamses();
						rparticipate.setRaiderId(raidersGoodVo.getRaiderId());
						rparticipate.setIp(HttpUtil.getRemoteIpAddr(request));
						rparticipate.setParticipateNum(quantity);
						rparticipates.add(rparticipate);
					}
	            }else{
	    			model.put("result", false);
					model.put("errorCode",goodInfoMap.get("errorCode") );
					model.put("errorMsg",goodInfoMap.get("errorMsg"));
					return JsonUtils.bean2jsonP(jsoncallback, model);
	            }
			}
			
			if(rparticipates.size()==0){
					model.put("result", false);
					model.put("errorCode", ErrorCode.RaiderShoppingSaleOut.getErrorCode());
					model.put("errorMsg", ErrorCode.RaiderShoppingSaleOut.getErrorMsg());
					return JsonUtils.bean2jsonP(jsoncallback, model);
			}
			orderReqVO.setOrderGoods(rparticipates);
			orderReqVO.setTotalAmount(allTotalPay+"");
			orderReqVO.setCreateType(2);//标示是购物车下单
			
			Map<String,Object> createOrderParams=new HashMap<String,Object>();
			createOrderParams.put("orderReq", orderReqVO);
			logger.info("支付订单-生成参与订单 begin 用户id:"+userIdStr);
			//调接口生成订单
			 Map<String,Object> createResultMap=raidersManager.createRaiderOrder(createOrderParams);
			logger.info("支付订单-生成参与订单 end 用户id:"+userIdStr);

			 Boolean isCreaetSuccess=(Boolean)createResultMap.get("status");
			 if(isCreaetSuccess){
				 RaidersOrderParamesOut order=(RaidersOrderParamesOut)createResultMap.get("order");
				 //3.支付		
					logger.info("支付订单-生成支付订单 begin 用户id:"+userIdStr);
					String isVirtualPayStr=request.getParameter("isVirtualPay");
					Integer isVirtualPay=ObjectUtil.getInteger(isVirtualPayStr,0);//是否虚拟支付
	    			String payPlatform=request.getParameter("payPlatform");
	    			String payMedium=request.getParameter("payMedium");
	    			params.put("userId", userId);
	    			params.put("payPlatform", payPlatform);
	    			params.put("payMedium", payMedium);
	    			params.put("orderAmount", allTotalPay);
	    			params.put("orderCode", order.getOrderCode());
	    			params.put("orderId", order.getOrderId());
	    			params.put("isVirtualPay", isVirtualPay);
	    			Map<String,Object> result=raidersManager.payRaider(params);
	    			Boolean isSuccess=(Boolean)result.get("status");
					logger.info("支付订单-生成支付订单 end 用户id:"+userIdStr);
	    			if(isSuccess){
	    				logger.info("支付成功："+result);
	    				model.put("payInfo", result.get("url"));
	    				model.put("isVirtualPayAll", result.get("isVirtualPayAll"));
	    				model.put("submitNumber",allTotalPay);
	    				model.put("payNumber",allTotalPay);
	    				model.put("participateId",order.getParticipateIds());
	    				model.put("result", true);
	    				model.put("errorCode", ErrorCode.Success.getErrorCode());
	    				model.put("errorMsg", ErrorCode.Success.getErrorMsg());
	    			}else{
	    				model.put("result", false);
	    				model.put("errorCode",result.get("errorCode") );
	    				model.put("errorMsg",result.get("errorMsg"));
	    			}
			 }else{
 				model.put("result", false);
				model.put("errorCode", ErrorCode.RaiderCreateCartOrderFaile.getErrorCode());
				model.put("errorMsg", ErrorCode.RaiderCreateCartOrderFaile.getErrorMsg());
			 }
		} catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("订单确认发生异常：" + e.getMessage(), e);
		}
		logger.info("订单确认返回结果：" + JSON.toJSONString(model));
		return JsonUtils.beanjsonP(jsoncallback, model);// beanjsonP2(当集合的key和value都一样的时候显示不了重复数据)
	}
	
	
}
