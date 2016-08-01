package com.xiu.mobile.portal.ei.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.xiu.common.command.result.Result;
import com.xiu.common.command.result.ResultSupport;
import com.xiu.common.lang.PagingList;
import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.core.utils.ObjectUtil;
import com.xiu.mobile.portal.common.constants.WCSErrorCode;
import com.xiu.mobile.portal.constants.ErrConstants;
import com.xiu.mobile.portal.ei.EIOrderManager;
import com.xiu.mobile.portal.model.OrderInvoiceVO;
import com.xiu.mobile.sales.dointerface.vo.FirstOpenRecordParamIn;
import com.xiu.sales.common.balance.dataobject.BalanceOrderInfoDO;
import com.xiu.sales.common.blacklist.dataobject.ItemBlackParamDO;
import com.xiu.sales.common.settle.ItemBlackScopeService;
import com.xiu.sales.common.settle.OrderSettleService;
import com.xiu.settlement.common.OrderSettlementHessianService;
import com.xiu.tc.common.orders.domain.BizOrderDO;
import com.xiu.tc.common.orders.domain.CancelDO;
import com.xiu.tc.common.orders.domain.InvoiceTypeDO;
import com.xiu.tc.common.orders.domain.OrderSysConfig;
import com.xiu.tc.common.orders.domain.PayOrderDO;
import com.xiu.tc.common.orders.domain.QueryDO;
import com.xiu.tc.orders.condition.QueryOrderCondition;
import com.xiu.tc.orders.condition.UpdatePayTypeForWapCondition;
import com.xiu.tc.orders.dointerface.BizOrderQueryServive;
import com.xiu.tc.orders.dointerface.BizOrderWriterService;

/** 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 外部接口-订单管理实现OSC
 * @AUTHOR : mike@xiu.com 
 * @DATE :2014-5-15 下午2:46:14
 * </p>
 **************************************************************** 
 */
@Service("eiOrderManager")
public class EIOrderManagerImpl implements EIOrderManager {
	
	private static final XLogger logger = XLoggerFactory.getXLogger(EIOrderManagerImpl.class);
	
	@Autowired
	private BizOrderWriterService bizOrderWriterService;
	@Autowired
	private BizOrderQueryServive bizOrderQueryServive;
	@Autowired
	private OrderSettleService orderSettleService;
	@Autowired
	private  ItemBlackScopeService itemBlackScopeService;
	@Autowired
	private OrderSettlementHessianService orderSettlementService;
	@Override
	public BizOrderDO createOrder(BizOrderDO order) {
		Result result = null;
		try {
			logger.info("调用BizOrderWriterService接口的BizOrderDO参数orderId："+order.getOrderId());
			result = this.bizOrderWriterService.submitOrderFromWCS(order);
		} catch (Exception e) {
			logger.error("{}.submitOrderFromWCS 创建订单异常 | message={}",
					new Object[]{bizOrderWriterService.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_TC_ORDER_CREATE_ERR, e);
		}
		if (!result.isSuccess()) {
			// 优先返回errorMessages中的错误信息
			if (result.getErrorMessages() != null && 
					!result.getErrorMessages().isEmpty()) {
				// 只返回一个错误提示
				for (String error : result.getErrorMessages().values()) {
					logger.error("{}.submitOrderFromWCS 创建订单失败 | errCode={} | errMessage={}",
							new Object[]{bizOrderWriterService.getClass(),
							result.getResultCode(),result.getErrorMessages()});
					throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_TC_ORDER_CREATE_BIZ_FAILED_ERR, 
							result.getResultCode(),
							error);
				}
			}
			// defaultModel中的错误
			//WCSErrorCode同官网一样的创建订单验证的详细的错误信息
			WCSErrorCode wcsError=new WCSErrorCode();
			String errorCode = (String) result.getDefaultModel();
			String errorMsg=wcsError.getErrorMsg(errorCode.toUpperCase());
			logger.error("{}.submitOrderFromWCS 前端TC处理失败 | errorCode={}",
					new Object[]{bizOrderWriterService.getClass(),errorCode});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_TC_ORDER_CREATE_BIZ_FAILED_ERR, errorCode,errorMsg);//"前端TC处理异常"
		}
		BizOrderDO bizOrderDO = (BizOrderDO) result.getModels().get("bizOrderDO");
		return bizOrderDO;
	}

	@Override
	public boolean cancelOrder(CancelDO cancelDO) {
		Result result = null;
		boolean flag = false;
		try{
			result = bizOrderWriterService.cancelOrderByUserForJava(cancelDO);
		}catch(Exception e){
			logger.error("{}.cancelOrderByUserForJava 调用tc接口撤销订单异常 | message={}",
					new Object[]{bizOrderWriterService.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_TC_ORDER_CANCEL_BIZ_ERR, e);
		}
		if(!result.isSuccess()){
			// 只返回一个错误提示
			for (String error : result.getErrorMessages().values()) {
				logger.error("{}.cancelOrderByUserForJava撤销订单失败 | errCode={} | errMessage={}",
						new Object[]{bizOrderWriterService.getClass(),
						result.getResultCode(),result.getErrorMessages()});
				throw ExceptionFactory.buildEIBusinessException(
						ErrConstants.EIErrorCode.EI_TC_ORDER_CANCEL_FAILED_ERR, 
						result.getResultCode(),
						error);
			}
		}else{
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean updatePayTypeByWap(UpdatePayTypeForWapCondition updatePayType) {
		boolean flag = false;
		if("WANLITONG_WAP".equals(updatePayType.getNewPayType())) {
			updatePayType.setNewPayType("PAWLTWAP"); //如果是万里通支付
		}
		try{
			flag = bizOrderWriterService.updatePayTypeByWap(updatePayType);
		}catch(Exception ex){
			logger.error("{}.updatePayTypeByWap 修改订单支付方式出现异常 | message={}",
					new Object[]{bizOrderWriterService.getClass(),ex.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_TC_ORDER_UPDATE_PAY_TYPE_BIZ_ERR, ex);
		}
		return flag;
	}

	@Override
	public Result orderBalanceService(BalanceOrderInfoDO boi) {
		Result result = null;
		try {
//			result = this.orderSettleService.orderBalanceService(boi);
			//结算系统的计算订单接口
			result = orderSettlementService.orderBalanceService(boi);
		} catch (Exception e) {
			logger.error("计算订单异常exception:",e);
			logger.error("{}.orderBalanceService 计算订单异常 | message={}",
					new Object[]{orderSettleService.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_SALES_BIZ_ERR, e);
		}
		if(!result.isSuccess()){
			logger.error("{}.orderBalanceService 计算订单失败 | errCode={} | errMessage={}",
					new Object[]{orderSettleService.getClass(),result.getResultCode(),result.getErrorMessages()});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_SALES_FAILED_ERR, result.getResultCode(),"订单中心计算订单失败");
		}
		return result;
	}

	@Override
	public int queryUserCountOrderNum(String userId) {
		Result result = null;
		try {
			result = this.bizOrderQueryServive.queryUserCountOrderNum(userId);
		} catch (Exception e) {
			logger.error("{}.queryUserCountOrderNum 查询用户订单数异常 | message={}",
					new Object[]{bizOrderQueryServive.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_TC_ORDER_COUNT_BIZ_ERR, e);
		}
		if (!result.isSuccess()) {
			logger.error("{}.queryUserCountOrderNum 查询用户订单数出错 | errCode={}",
					new Object[]{bizOrderQueryServive.getClass(),result.getResultCode()});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_TC_ORDER_COUNT_BIZ_ERR,result.getResultCode(),"订单中心查询用户订单数出错");
		}
		return (Integer)result.getDefaultModel();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BizOrderDO queryOrderAllInfo(long userId, int orderId) {
		Assert.notNull(orderId,"订单orderId不能为空.");
		Assert.notNull(userId,"订单userId不能为空.");
		if (logger.isDebugEnabled()) {
			logger.debug("invoke remote interface [tc] bizOrderQueryServive.queryOrderAllInfo");
			logger.debug("userId : " + userId);
			logger.debug("orderId : " + orderId);
		}
		Result result = new ResultSupport();
		result.setSuccess(false);
		try{
			result = this.bizOrderQueryServive.queryOrderAllInfo(userId, orderId);
		}catch(Exception e){
			logger.error("{}.queryOrderAllInfo 调用TC接口取用户订单信息异常 | message={}",
					new Object[]{bizOrderQueryServive.getClass().getName(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_TC_ORDER_BIZ_ERR, e);
		}
		if (!result.isSuccess()) {
			String errorCode =  result.getResultCode();
			Map<String,String> errorMsg = result.getErrorMessages();
			StringBuffer sf = new StringBuffer();
			if(null != errorMsg){
				Iterator iter = errorMsg.entrySet().iterator();
				while(iter.hasNext()){
					Map.Entry entry = (Map.Entry) iter.next();
					sf.append(entry.getValue());
					sf.append(" ");
				}
			}
			logger.error("{}.queryOrderAllInfo 调用tc接口查询订单详情失败 | errCode={} | errMessage={}",
					new Object[]{bizOrderQueryServive.getClass().getName(),errorCode,sf.toString()});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_TC_ORDER_FAILED_ERR, errorCode,
					"订单中心查询订单错误(订单不存在或不是该用户的订单)" + sf.toString());
		}
		BizOrderDO order = (BizOrderDO) result.getModels().get("bizOrderDO");
		return order;
	}

	@SuppressWarnings("rawtypes")
	@Override
	
	public Result queryOrderList(Long userId, QueryDO queryDO,
			PagingList<BizOrderDO> pageList) {
		Result result = null;
		try{
			result = this.bizOrderQueryServive.queryOrderForJava(userId, queryDO, pageList);
		}catch(Exception e){
			logger.error("{}.queryOrderForJava 调用TC接口取用户订单列表异常 | message={}",
					new Object[]{bizOrderQueryServive.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_TC_ORDER_LIST_BIZ_ERR, e);
		}
		// 查询失败
		if (!result.isSuccess()) {
			String errorCode = (String) result.getResultCode();
			Map<String,String> errorMsg = result.getErrorMessages();
			StringBuffer sf = new StringBuffer();
			if(null != errorMsg){
				Iterator iter = errorMsg.entrySet().iterator();
				while(iter.hasNext()){
					Map.Entry entry = (Map.Entry) iter.next();
					sf.append(entry.getValue());
					sf.append(" ");
				}
			}
			logger.error("{}.queryOrderForJava 调用TC接口取用户订单列表失败 | errCode={} | errMessage={}",
					new Object[]{bizOrderQueryServive.getClass(),errorCode,sf.toString()});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_TC_ORDER_LIST_FAILED_ERR, errorCode,"订单中心查询用户订单列表失败" + sf.toString());
		}
		return result;
	}
	@Override
	public ItemBlackParamDO itemBlackScope(ItemBlackParamDO itemBlackParamDO){
		ItemBlackParamDO item=new ItemBlackParamDO();
		try{
			item=itemBlackScopeService.itemBlackScope(itemBlackParamDO);
		}catch(Exception e){
			String sns="";
			if(itemBlackParamDO!=null&&itemBlackParamDO.getItemIds()!=null){
				for (String sn:itemBlackParamDO.getItemIds()) {
					sns=sns+","+sn;
				}
			}
			logger.error("{}.itemBlackScope 获取促销系统黑名单异常 | message={} ,SNs="+sns,
					new Object[]{itemBlackScopeService.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_SALES_BLACK_ERR, e);
			
		}
		return item;
		
	}
	@Override
	 public BizOrderDO queryOrderBaseInfo(int orderId){
		Result result = new ResultSupport();
		try{
	    result = bizOrderQueryServive.queryOrderBaseInfo(orderId);
	  }catch(Exception e){
			logger.error("{}.queryOrderBaseInfo 调用osc接口取用户订单信息异常 | message={}",
					new Object[]{bizOrderQueryServive.getClass().getName(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_TC_ORDER_BIZ_ERR, e);
		}
		if (!result.isSuccess()) {
			String errorCode =  result.getResultCode();
			Map<String,String> errorMsg = result.getErrorMessages();
			StringBuffer sf = new StringBuffer();
			if(null != errorMsg){
				Iterator iter = errorMsg.entrySet().iterator();
				while(iter.hasNext()){
					Map.Entry entry = (Map.Entry) iter.next();
					sf.append(entry.getValue());
					sf.append(" ");
				}
			}
			logger.error("{}.queryOrderBaseInfo 调用osc接口查询订单详情失败 | errCode={} | errMessage={}",
					new Object[]{bizOrderQueryServive.getClass().getName(),errorCode,sf.toString()});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_TC_ORDER_FAILED_ERR, errorCode,
					"订单中心查询订单错误(订单不存在或不是该用户的订单)" + sf.toString());
		}
		BizOrderDO order = (BizOrderDO) result.getDefaultModel();
		return order;
	    
	  }
	
	@SuppressWarnings("rawtypes")
	@Override
	
	public Result queryWaitingForCommentOrder(QueryOrderCondition condition, PagingList<BizOrderDO> pageList) {
		Result result = null;
		try{
			result = this.bizOrderQueryServive.queryWaitingForCommentOrder(condition, pageList);
		}catch(Exception e){
			logger.error("{}.queryWaitingForCommentOrder 调用TC接口取待评论订单异常 | message={}",
					new Object[]{bizOrderQueryServive.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_TC_WAIT_COMMENT_ORDER_ERR, e);
		}
		// 查询失败
		if (!result.isSuccess()) {
			String errorCode = (String) result.getResultCode();
			Map<String,String> errorMsg = result.getErrorMessages();
			StringBuffer sf = new StringBuffer();
			if(null != errorMsg){
				Iterator iter = errorMsg.entrySet().iterator();
				while(iter.hasNext()){
					Map.Entry entry = (Map.Entry) iter.next();
					sf.append(entry.getValue());
					sf.append(" ");
				}
			}
			logger.error("{}.queryWaitingForCommentOrder 调用TC接口取待评论订单失败| errCode={} | errMessage={}",
					new Object[]{bizOrderQueryServive.getClass(),errorCode,sf.toString()});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_TC_WAIT_COMMENT_ORDER_FAILED_ERR, errorCode,"调用TC接口取待评论订单失败" + sf.toString());
		}
		return result;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public Result updateTradeEndStatus(long orderId, long userId) {
		Result result = null;
		try{
			result = this.bizOrderWriterService.updateTradeEndStatus(orderId, userId);
		}catch(Exception e){
			logger.error("{}.updateTradeEndStatus 调用osc接口更新已发货订单为已完结状态异常 | message={}",
					new Object[]{bizOrderWriterService.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_TC_ORDER_END_ERR, e);
		}
		// 查询失败
		if (!result.isSuccess()) {
			String errorCode = (String) result.getResultCode();
			Map<String,String> errorMsg = result.getErrorMessages();
			StringBuffer sf = new StringBuffer();
			if(null != errorMsg){
				Iterator iter = errorMsg.entrySet().iterator();
				while(iter.hasNext()){
					Map.Entry entry = (Map.Entry) iter.next();
					sf.append(entry.getValue());
					sf.append(" ");
				}
			}
			logger.error("{}.updateTradeEndStatus 调用osc接口更新已发货订单为已完结状态失败| errCode={} | errMessage={}",
					new Object[]{bizOrderWriterService.getClass(),errorCode,sf.toString()});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_TC_ORDER_END_FAILED_ERR, errorCode,"调用osc接口更新已发货订单为已完结状态失败" + sf.toString());
		}
		return result;
	}

	public Result deleteOrder(long userId, int orderId) {
		Result result = null;
		try{
			result = this.bizOrderWriterService.deleteOrderWithLogic(userId, orderId);
		}catch(Exception e){
			logger.error("{}.deleteOrder 调用osc接口删除订单异常 | message={}",
					new Object[]{bizOrderWriterService.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_TC_ORDER_DELETE_ERR, e);
		}
		// 查询失败
		if (!result.isSuccess()) {
			String errorCode = (String) result.getResultCode();
			Map<String,String> errorMsg = result.getErrorMessages();
			StringBuffer sf = new StringBuffer();
			if(null != errorMsg){
				Iterator iter = errorMsg.entrySet().iterator();
				while(iter.hasNext()){
					Map.Entry entry = (Map.Entry) iter.next();
					sf.append(entry.getValue());
					sf.append(" ");
				}
			}
			logger.error("{}.deleteOrder 调用osc接口删除订单失败| errCode={} | errMessage={}",
					new Object[]{bizOrderWriterService.getClass(),errorCode,sf.toString()});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_TC_ORDER_DELETE_FAILED_ERR, errorCode,"调用osc接口删除订单失败" + sf.toString());
		}
		return result;
	}

	/**
	 * 查询用户购买商品数量
	 */
	public int queryUserBuyGoodsCount(long userId, String goodsSn, String beginDate, String endDate) {
		Assert.notNull(userId,"用户userId不能为空.");
		Assert.notNull(goodsSn,"商品goodsSn不能为空.");
		if (logger.isDebugEnabled()) {
			logger.debug("invoke remote interface [osc] bizOrderQueryServive.queryOrderDetailCount");
			logger.debug("userId : " + userId);
			logger.debug("goodsSn : " + goodsSn);
			logger.debug("beginDate : " + beginDate);
			logger.debug("endDate : " + endDate);
		}
		Result result = new ResultSupport();
		result.setSuccess(false);
		int counts = 0;
		try{
			result = this.bizOrderQueryServive.queryUserBuyGoodsCount(userId, goodsSn, beginDate, endDate);
		}catch(Exception e){
			logger.error("{}.queryOrderDetailCount 调用osc查询用户购买商品数量异常 | message={}",
					new Object[]{bizOrderQueryServive.getClass().getName(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_TC_QUERY_BUYGOODSNUM_FAILED_ERR, e);
		}
		if (!result.isSuccess()) {
			String errorCode =  result.getResultCode();
			Map<String,String> errorMsg = result.getErrorMessages();
			StringBuffer sf = new StringBuffer();
			if(null != errorMsg){
				Iterator iter = errorMsg.entrySet().iterator();
				while(iter.hasNext()){
					Map.Entry entry = (Map.Entry) iter.next();
					sf.append(entry.getValue());
					sf.append(" ");
				}
			}
			logger.error("{}.queryOrderDetailCount 调用osc查询用户购买商品数量失败 | errCode={} | errMessage={}",
					new Object[]{bizOrderQueryServive.getClass().getName(),errorCode,sf.toString()});
		} else {
			counts = (Integer) result.getModels().get("counts");
		}
		return counts;
	}

	
	/**
	 * 查询发票类型
	 */
	public List<OrderInvoiceVO> queryInvoiceType() {
		
		Result result = new ResultSupport();
		result.setSuccess(false);
		List<OrderInvoiceVO> orderIvoiceTypeDOs = new ArrayList<OrderInvoiceVO>();
		List<InvoiceTypeDO> invoiceTypeDOs = new ArrayList<InvoiceTypeDO>();
		try{
			result = this.bizOrderQueryServive.queryInvoiceType();
		}catch(Exception e){
			logger.error("{}.queryOrderDetailCount 调用osc查询发票异常 | message={}",
					new Object[]{bizOrderQueryServive.getClass().getName(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_TC_QUERY_INVOICE_FAILED_ERR, e);
		}
		if (!result.isSuccess()) {
			String errorCode =  result.getResultCode();
			Map<String,String> errorMsg = result.getErrorMessages();
			StringBuffer sf = new StringBuffer();
			if(null != errorMsg){
				Iterator iter = errorMsg.entrySet().iterator();
				while(iter.hasNext()){
					Map.Entry entry = (Map.Entry) iter.next();
					sf.append(entry.getValue());
					sf.append(" ");
				}
			}
			logger.error("{}.queryOrderDetailCount 调用osc查询发票失败 | errCode={} | errMessage={}",
					new Object[]{bizOrderQueryServive.getClass().getName(),errorCode,sf.toString()});
		} else {
			invoiceTypeDOs = (List<InvoiceTypeDO>) result.getModels().get("invoiceTypes");
			for (InvoiceTypeDO invoice:invoiceTypeDOs) {
				OrderInvoiceVO inv=new OrderInvoiceVO();
				inv.setInvoiceId(invoice.getInvoiceId());
				inv.setInvoiceName(invoice.getInvoiceName());
				orderIvoiceTypeDOs.add(inv);
			}
			
		}
		
		return orderIvoiceTypeDOs;
	}
	
	/**
	 * 查询用户购买的订单数量
	 */
	public int queryOrderDetailCount(long buyerId) {
		Assert.notNull(buyerId,"用户buyerId不能为空.");
		if (logger.isDebugEnabled()) {
			logger.debug("invoke remote interface [osc] bizOrderQueryServive.queryOrderDetailCount");
			logger.debug("buyerId : " + buyerId);
		}
		Result result = new ResultSupport();
		result.setSuccess(false);
		int counts = 0;
		try{
			result = this.bizOrderQueryServive.queryOrderDetailCount(buyerId);
		}catch(Exception e){
			logger.error("{}.queryOrderDetailCount 调用osc查询用户完成订单详情数量异常 | message={}",
					new Object[]{bizOrderQueryServive.getClass().getName(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_TC_ORDERDETAIL_LIST_FAILED_ERR, e);
		}
		if (!result.isSuccess()) {
			String errorCode =  result.getResultCode();
			Map<String,String> errorMsg = result.getErrorMessages();
			StringBuffer sf = new StringBuffer();
			if(null != errorMsg){
				Iterator iter = errorMsg.entrySet().iterator();
				while(iter.hasNext()){
					Map.Entry entry = (Map.Entry) iter.next();
					sf.append(entry.getValue());
					sf.append(" ");
				}
			}
			logger.error("{}.queryOrderDetailCount 调用osc查询用户完成订单详情数量失败 | errCode={} | errMessage={}",
					new Object[]{bizOrderQueryServive.getClass().getName(),errorCode,sf.toString()});
		} else {
			counts = (Integer) result.getModels().get("counts");
		}
		return counts;
	}
	
	/**
	 * 查询用户有效的订单数量
	 */
	public int queryValueOrderDetailCount(long buyerId) {
		Assert.notNull(buyerId,"用户buyerId不能为空.");
		if (logger.isDebugEnabled()) {
			logger.debug("invoke remote interface [osc] bizOrderQueryServive.queryValueOrderDetailCount");
			logger.debug("buyerId : " + buyerId);
		}
		Result result = new ResultSupport();
		result.setSuccess(false);
		int counts = 0;
		try{
			result = this.bizOrderQueryServive.queryValueOrderDetailCount(buyerId);
		}catch(Exception e){
			logger.error("{}.queryOrderDetailCount 调用osc查询用户有效订单详情数量异常 | message={}",
					new Object[]{bizOrderQueryServive.getClass().getName(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_TC_ORDERDETAIL_LIST_FAILED_ERR, e);
		}
		if (!result.isSuccess()) {
			String errorCode =  result.getResultCode();
			Map<String,String> errorMsg = result.getErrorMessages();
			StringBuffer sf = new StringBuffer();
			if(null != errorMsg){
				Iterator iter = errorMsg.entrySet().iterator();
				while(iter.hasNext()){
					Map.Entry entry = (Map.Entry) iter.next();
					sf.append(entry.getValue());
					sf.append(" ");
				}
			}
			logger.error("{}.queryValueOrderDetailCount 调用osc查询用户完成订单详情数量失败 | errCode={} | errMessage={}",
					new Object[]{bizOrderQueryServive.getClass().getName(),errorCode,sf.toString()});
		} else {
			counts = (Integer) result.getModels().get("counts");
		}
		return counts;
	}

	@Override
	public Result queryCanBeRefundOrderList(QueryOrderCondition condition,
			PagingList<BizOrderDO> pageList) {
		Result result = null;
		try{
			Long userId=ObjectUtil.getLong(condition.getBuyerId());
			result = this.bizOrderQueryServive.queryCanBeReRefundOrder(userId, pageList.getStartRow(), pageList.getEndRow());
		}catch(Exception e){
			logger.error("{}.queryWaitingForCommentOrder 调用TC接口取可申请售后服务订单异常 | message={}",
					new Object[]{bizOrderQueryServive.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_TC_QUERY_CAN_REFUND_ERR, e);
		}
		// 查询失败
		if (!result.isSuccess()) {
			String errorCode = (String) result.getResultCode();
			Map<String,String> errorMsg = result.getErrorMessages();
			StringBuffer sf = new StringBuffer();
			if(null != errorMsg){
				Iterator iter = errorMsg.entrySet().iterator();
				while(iter.hasNext()){
					Map.Entry entry = (Map.Entry) iter.next();
					sf.append(entry.getValue());
					sf.append(" ");
				}
			}
			logger.error("{}.queryWaitingForCommentOrder 调用TC接口取可申请售后服务订单失败| errCode={} | errMessage={}",
					new Object[]{bizOrderQueryServive.getClass(),errorCode,sf.toString()});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_TC_QUERY_CAN_REFUND_ERR, errorCode,"调用TC接口取可申请售后服务订单失败" + sf.toString());
		}
		return result;
	}

	@Override
	public int saveOrderPayRecord(PayOrderDO payOrderDO) {
		Result result = null;
		try {
			result = bizOrderWriterService.saveOrderPayRecord(payOrderDO);
		} catch (Exception e) {
			logger.error("{}.saveOrderPayRecord 调用TC接口保存订单支付记录异常 | message={}",
					new Object[]{bizOrderWriterService.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_TC_ORDER_SAVE_PAY_TYPE_BIZ_ERR, e);
		}
		if(result != null && result.isSuccess()){
			return (Integer) result.getDefaultModel();
		}
		return 0;
	}

	@Override
	public OrderSysConfig queryOrderSysConfigInfo() {
		Result result = null;
		try{
			result = this.bizOrderQueryServive.queryOrderSysConfigInfo();
		}catch(Exception e){
			logger.error("{}.queryOrderSysConfigInfo 调用osc接口删除订单异常 | message={}",
					new Object[]{bizOrderQueryServive.getClass(),e.getMessage()});
			return null;
		}
		// 查询失败
		if (result.isSuccess()) {
			return (OrderSysConfig)result.getDefaultModel();
		}
		return null;
	}

	@Override
	public List<PayOrderDO> queryUnPayedRecord(String orderId){
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("orderId", orderId);
		paramMap.put("status", "0");
		
		try{
			Result result = bizOrderQueryServive.queryUnPayedRecord(paramMap);
			if(result.isSuccess()){
				List<PayOrderDO> payOrderDOList = (List<PayOrderDO>) result.getDefaultModel();
				return payOrderDOList;
			}else{
				logger.error("查询订单未支付记录异常，orderId: " + orderId);
				throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_TC_QUERY_ORDER_PAY_TYPE_BIZ_ERR, "", "调用osc查询订单支付记录异常");
			}
		}catch(Exception e){
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_TC_QUERY_ORDER_PAY_TYPE_BIZ_ERR, e);
		}
	}
	
	
}
