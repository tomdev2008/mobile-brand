package com.xiu.mobile.portal.controller.raiders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.xiu.mobile.portal.model.raiders.RaidersGoodVo;
import com.xiu.mobile.portal.model.raiders.RaidersOrderGoodVo;
import com.xiu.mobile.portal.model.raiders.RaidersOrderVo;
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
@RequestMapping("/raiderOrder")
public class RaiderOrderController extends BaseController{
	private Logger logger=Logger.getLogger(RaiderOrderController.class);
	
	@Autowired
	private EIRaidersManager raidersManager;
	
	
	/**
	 * 计算订单
	 * @param request
	 * @param jsoncallback
	 * @param number
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value="/calcOrder",produces="text/html;charset=UTF-8")
	public String calcOrder(HttpServletRequest request,String jsoncallback,
			Integer number){
		// 存储返回结果值
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			/**夺宝活动ID*/
			String raiderIdStr = request.getParameter("raiderId");
			Long raiderId=ObjectUtil.getLong(raiderIdStr,null);
			/**购买数量*/
			int quantity = NumberUtils.toInt(request.getParameter("quantity"), 1);
			
			if(raiderId==null){
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, resultMap);
			}
			String userId = SessionUtil.getUserId(request);
			Map<String,Object> params =new HashMap<String,Object>();
			params.put("raiderId", raiderId);
			params.put("userId", userId);
			Map<String,Object> goodInfoMap = raidersManager.getRaiderGoodInfo(params);
			Boolean isSuccess=(Boolean)goodInfoMap.get("status");
			if (isSuccess) {
				RaidersGoodVo raidersGoodVo=(RaidersGoodVo)goodInfoMap.get("model");
				// 计算订单
				RaidersOrderVo calcOrderReqVO = new RaidersOrderVo();
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
				Integer totalPay=raidersGoodVo.getMinBuyNumber()*quantity;
				calcOrderReqVO.setTotalAmount(totalPay+"");
				calcOrderReqVO.setNeedAmount(totalPay+"'");
				
				//设置订单商品信息
				List<RaidersOrderGoodVo> orderGoods=new ArrayList<RaidersOrderGoodVo>();
				RaidersOrderGoodVo orderGood=new RaidersOrderGoodVo(raidersGoodVo);
				orderGood.setGoodsQuantity(quantity);
				orderGood.setGoodsAmt(ObjectUtil.getLong(totalPay));
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
				orderGoods.add(orderGood);
				calcOrderReqVO.setOrderGoods(orderGoods);
				//查询虚拟账号
				Map<String, Object> virtual=raidersManager.getVirtualAccountInfo(ObjectUtil.getLong(userId));
				 isSuccess=(Boolean )virtual.get("isSuccess");
				Long vtotalAmount=0l;
				if(isSuccess){
					vtotalAmount=(Long)virtual.get("num");
				}
				
				resultMap.put("result", true);
				Double vMount=XiuUtil.getPriceDoubleFormat(vtotalAmount+"");
				resultMap.put("vtotalAmount", XiuUtil.getPriceDouble2Str(vMount/100));
				resultMap.put("raidersOrder", calcOrderReqVO);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			} else {
				resultMap.put("result", false);
				resultMap.put("errorCode",goodInfoMap.get("errorCode") );
				resultMap.put("errorMsg",goodInfoMap.get("errorMsg"));
			}
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
	 * @param request
	 * @param jsoncallback
	 * @param number
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value="/toPay",produces="text/html;charset=UTF-8")
	public String toPay(HttpServletRequest request,String jsoncallback,
			Integer number){
		String raiderIdStr = request.getParameter("raiderId");
		String userIdStr = SessionUtil.getUserId(request);
		logger.info("进入-支付订单 活动id:"+raiderIdStr+",用户id:"+userIdStr);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		Map<String,Object> params=new HashMap<String,Object>();
		Long userId=ObjectUtil.getLong(userIdStr, null);
		Long raiderId=ObjectUtil.getLong(raiderIdStr,null);
		/**购买数量*/
		int quantity = NumberUtils.toInt(request.getParameter("quantity"), 1);
		
		if(raiderId==null){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		
		
		try{
			//1.查询商品剩余
			Map<String,Object> getInfoparams =new HashMap<String,Object>();
			getInfoparams.put("raiderId", raiderId);
			logger.info("支付订单-获取活动详情 begin 活动id:"+raiderIdStr+",用户id:"+userIdStr);
			Map<String,Object> goodInfoMap = raidersManager.getRaiderGoodInfo(getInfoparams);
			logger.info("支付订单-获取活动详情  end 活动id:"+raiderIdStr+",用户id:"+userIdStr);

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
				Integer showAmount=quantity;//展示价格(单位元)
				Integer orderAmount=showAmount;//支付价格(单位分)
				// 
				RaidersOrderParamesIn orderReqVO = new RaidersOrderParamesIn();
				orderReqVO.setTotalAmount(orderAmount+"");
				orderReqVO.setUserId(userId);
				
				//设置参与信息
				RaidersParticipateParamses rparticipate=new RaidersParticipateParamses();
				rparticipate.setRaiderId(raidersGoodVo.getRaiderId());
				rparticipate.setIp(HttpUtil.getRemoteIpAddr(request));
				rparticipate.setParticipateNum(quantity);
				
				List<RaidersParticipateParamses> rparticipates=new ArrayList<RaidersParticipateParamses>();
				rparticipates.add(rparticipate);
				orderReqVO.setOrderGoods(rparticipates);
				orderReqVO.setCreateType(1);//标示是单独下单

				
				Map<String,Object> createOrderParams=new HashMap<String,Object>();
				createOrderParams.put("orderReq", orderReqVO);
				logger.info("支付订单-生成参与订单 begin 活动id:"+raiderIdStr+",用户id:"+userIdStr);
				//调接口生成订单
				 Map<String,Object> createResultMap=raidersManager.createRaiderOrder(createOrderParams);
				logger.info("支付订单-生成参与订单 end 活动id:"+raiderIdStr+",用户id:"+userIdStr);

				 Boolean isCreaetSuccess=(Boolean)createResultMap.get("status");
				 if(isCreaetSuccess){
					 RaidersOrderParamesOut order=(RaidersOrderParamesOut)createResultMap.get("order");
					 //3.支付		
						logger.info("支付订单-生成支付订单 begin 活动id:"+raiderIdStr+",用户id:"+userIdStr);
		    			String payPlatform=request.getParameter("payPlatform");
		    			String payMedium=request.getParameter("payMedium");
						String isVirtualPayStr=request.getParameter("isVirtualPay");
						Integer isVirtualPay=ObjectUtil.getInteger(isVirtualPayStr,0);//是否虚拟支付
		    			params.put("userId", userId);
		    			params.put("payPlatform", payPlatform);
		    			params.put("payMedium", payMedium);
		    			params.put("orderAmount", orderAmount);
		    			params.put("orderCode", order.getOrderCode());
		    			params.put("orderId", order.getOrderId());
		    			params.put("isVirtualPay", isVirtualPay);

		    			Map<String,Object> result=raidersManager.payRaider(params);
		    			Boolean isSuccess=(Boolean)result.get("status");
						logger.info("支付订单-生成支付订单 end 活动id:"+raiderIdStr+",用户id:"+userIdStr);
		    			if(isSuccess){
		    				logger.info("支付成功："+result);
		    				resultMap.put("payInfo", result.get("url"));
		    				resultMap.put("isVirtualPayAll", result.get("isVirtualPayAll"));
		    				resultMap.put("submitNumber",showAmount );
		    				resultMap.put("payNumber",showAmount);
		    				resultMap.put("participateId",order.getParticipateIds());
		    				resultMap.put("result", true);
		    				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
		    				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
		    			}else{
		    				resultMap.put("result", false);
		    				resultMap.put("errorCode",result.get("errorCode") );
		    				resultMap.put("errorMsg",result.get("errorMsg"));
		    			}
				 }else{
		    			resultMap.put("result", false);
						resultMap.put("errorCode",goodInfoMap.get("errorCode") );
						resultMap.put("errorMsg",goodInfoMap.get("errorMsg"));
						return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		            }
				 
    	       
            }else{
    			resultMap.put("result", false);
				resultMap.put("errorCode",goodInfoMap.get("errorCode") );
				resultMap.put("errorMsg",goodInfoMap.get("errorMsg"));
				return JsonUtils.bean2jsonP(jsoncallback, resultMap);
            }
			
		}catch(Exception e){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("支付异常：" + e.getMessage(),e);
		}
		
		
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	
	
	
}
