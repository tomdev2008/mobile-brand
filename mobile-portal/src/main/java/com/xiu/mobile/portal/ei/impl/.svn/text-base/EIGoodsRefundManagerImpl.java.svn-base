package com.xiu.mobile.portal.ei.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.common.command.result.Result;
import com.xiu.common.lang.PagingList;
import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.portal.dao.TopicActivityDao;
import com.xiu.mobile.portal.ei.EIGoodsRefundManager;
import com.xiu.mobile.portal.model.RefundApplyVo;
import com.xiu.mobile.portal.model.RefundGoodsInfoVo;
import com.xiu.mobile.portal.model.RefundLogisticsVo;
import com.xiu.mobile.portal.model.RefundOrderInfoVo;
import com.xiu.mobile.portal.service.impl.RefundOrderInfoVoConvertor;
import com.xiu.show.core.common.util.ObjectUtil;
import com.xiu.tc.common.orders.domain.OperationLogDO;
import com.xiu.tc.common.orders.domain.RefundItemFlowCompanyDO;
import com.xiu.tc.common.orders.domain.RefundItemFlowDO;
import com.xiu.tc.common.orders.domain.RefundItemFlowDetailDO;
import com.xiu.tc.orders.condition.RefundItemFlowCondition;
import com.xiu.tc.orders.dointerface.RefundItemFlowService;

@Service("eiGoodsRefundManager")
public class EIGoodsRefundManagerImpl implements EIGoodsRefundManager{
	
	private final static Logger logger = Logger.getLogger(EIGoodsRefundManagerImpl.class);
	
	@Autowired
	private RefundItemFlowService refundItemFlowService;
	
	@Autowired
	private TopicActivityDao topicActivityDao;

	@Override
	public RefundItemFlowDO getUserRefundGoodsListByOrderCode(String orderCode,long userId) {
		Result result = null;
		try {
			logger.info("查询orderCode="+orderCode+",userId="+userId+"用户退换货商品列表信息");
			result = refundItemFlowService.queryRefundItemFlowInfoByOrderCode(orderCode, userId);
		} catch (Exception e) {
			logger.error("根据订单编号查询可退换货商品列表异常：exception=",e);
			throw ExceptionFactory.buildEIRuntimeException("queryRefundItemFlowInfoByOrderCode.error",e,e.getMessage());
		}
		if (!result.isSuccess()) {
			logger.error("订单编号查询可退换货商品列表出错，errorMessage="+result.getError());
			throw ExceptionFactory.buildEIBusinessException("queryRefundItemFlowInfoByOrderCode.error",result.getResultCode(),result.getError()!=null?result.getError().toString():"未知错误");
		}
		
		return (RefundItemFlowDO)result.getDefaultModel();
	}

	@Override
	public RefundItemFlowDO getUserGoodsRefundInfo(String orderCode, int orderDetailId,long userId) {
		Result result = null;
		try {
			logger.info("查询orderCode="+orderCode+",orderDetailId"+orderDetailId+",userId="+userId+"用户退换货订单详细信息");
			result = refundItemFlowService.queryRefundItemFlowInfoByOrderDetailId(orderCode, orderDetailId, userId);
		} catch (Exception e) {
			logger.error("根据商品编号查询退换货信息异常：exception=",e);
			throw ExceptionFactory.buildEIRuntimeException("queryRefundItemFlowInfoByOrderDetailId.error",e,e.getMessage());
		}
		if (!result.isSuccess()) {
			logger.error("根据商品编号查询退换货信息出错，errorMessage="+result.getError());
			throw ExceptionFactory.buildEIBusinessException("queryRefundItemFlowInfoByOrderDetailId.error",result.getResultCode(),result.getError()!=null?result.getError().toString():"未知错误");
		}
		
		return (RefundItemFlowDO)result.getDefaultModel();
	}

	@Override
	public String saveRefundItemFlow(RefundApplyVo refundApplyVo) {
		Result result = null;
		try {
			logger.info("退换货申请数据：refundApplyVo="+refundApplyVo);
			RefundItemFlowDO refundItemFlowDO = getRefundItemFlowDOByApplyData(refundApplyVo);
			result = refundItemFlowService.saveRefundItemFlow(refundItemFlowDO, refundItemFlowDO.getBuyerId());
		} catch (Exception e) {
			logger.error("记录退换货申请单据异常：exception=",e);
			throw ExceptionFactory.buildEIRuntimeException("saveRefundItemFlow.error",e,e.getMessage());
		}
		if (!result.isSuccess()) {
			logger.error("记录退换货申请单据出错，errorMessage="+result.getError());
			throw ExceptionFactory.buildEIBusinessException("saveRefundItemFlow.error",result.getResultCode(),result.getError()!=null?result.getError().toString():"未知错误");
		}
		
		logger.info("退换货申请结果：status="+result.isSuccess());
		logger.info("退换货申请响应结果：result="+result.getDefaultModel());
		
		return result.getDefaultModel()!=null?result.getDefaultModel().toString():null;
	}

	@Override
	public int saveRefundItemFlowCompany(RefundLogisticsVo refundLogisticsVo) {
		Result result = null;
		try {
			logger.info("退换货记录返货物流信息：refundLogisticsVo="+refundLogisticsVo);
			RefundItemFlowCompanyDO refundItemFlowCompanyDO = new RefundItemFlowCompanyDO();
			refundItemFlowCompanyDO.setCompanyName(refundLogisticsVo.getCompanyName());
			refundItemFlowCompanyDO.setExpressOrderCode(refundLogisticsVo.getLogisticsCode());
			refundItemFlowCompanyDO.setGmtCreate(new Date());
			refundItemFlowCompanyDO.setRefundFlowId(Integer.parseInt(refundLogisticsVo.getRefundOrderId()));
			refundItemFlowCompanyDO.setOrderId(Integer.parseInt(refundLogisticsVo.getOrderId()));
			refundItemFlowCompanyDO.setPostFee(new Double(refundLogisticsVo.getPostFee()).longValue());
			result = refundItemFlowService.saveRefundItemFlowCompany(refundItemFlowCompanyDO);
		} catch (Exception e) {
			logger.error("记录返货物流信息异常：exception=",e);
			throw ExceptionFactory.buildEIRuntimeException("saveRefundItemFlowCompany.error",e,e.getMessage());
		}
		if (!result.isSuccess()) {
			logger.error("记录返货物流信息出错，errorMessage="+result.getError());
			throw ExceptionFactory.buildEIBusinessException("saveRefundItemFlowCompany.error",result.getResultCode(),result.getError()!=null?result.getError().toString():"未知错误");
		}
		
		logger.info("退换货申请返货物流信息结果：status="+result.isSuccess());
		logger.info("退换货申请返货物流信息响应结果：result="+result.getDefaultModel());
		
		return (Integer)result.getDefaultModel();
	}

	@Override
	public PagingList<RefundItemFlowDO> getUserRefundList(Map<String, Object> params) {
		Result result = null;
		try {
			logger.info("查询用户退换货列表，参数为params="+params);
			// 封装查询条件
			String userId = params.get("userId").toString();
			RefundItemFlowCondition condition = new RefundItemFlowCondition();
			condition.setBuyerId(Long.parseLong(userId));
			if (params.containsKey("status") && params.get("status")!=null) {
				// 单据状态 0:未受理 1:受理成功 2:受理失败 4:已取消
				condition.setStatus(params.get("status").toString());
			}
			
			// 分页参数查询
			PagingList<RefundItemFlowDO> pagingList = new PagingList<RefundItemFlowDO>();
			pagingList.setCurrentPage(Integer.parseInt(params.get("pageNo").toString()));
			pagingList.setPageSize(Integer.parseInt(params.get("pageSize").toString()));
			result = refundItemFlowService.queryRefundItemFlowList(condition, pagingList);
		} catch (Exception e) {
			logger.error("根据条件查询退换列表异常：exception=",e);
			throw ExceptionFactory.buildEIRuntimeException("queryRefundItemFlowList.error",e,e.getMessage());
		}
		if (!result.isSuccess()) {
			logger.error("根据条件查询退换列表出错，errorMessage="+result.getError());
			throw ExceptionFactory.buildEIBusinessException("queryRefundItemFlowList.error",result.getResultCode(),result.getError()!=null?result.getError().toString():"未知错误");
		}
		
		return (PagingList<RefundItemFlowDO>)result.getDefaultModel();
	}

	@Override
	public RefundItemFlowDO getUserRefundInfoByCode(String code, long userId) {
		Result result = null;
		try {
			result = refundItemFlowService.queryRefundItemFlowByCode(code, userId);
		} catch (Exception e) {
			logger.error("申请单据CODE查询异常：exception=",e);
			throw ExceptionFactory.buildEIRuntimeException("queryRefundItemFlowByCode.error",e,e.getMessage());
		}
		if (!result.isSuccess()) {
			logger.error("申请单据CODE查询出错，errorMessage="+result.getError());
			throw ExceptionFactory.buildEIBusinessException("queryRefundItemFlowByCode.error",result.getResultCode(),result.getError()!=null?result.getError().toString():"未知错误");
		}
		
		return (RefundItemFlowDO)result.getDefaultModel();
	}

	@Override
	public List<RefundItemFlowCompanyDO> getRefundLogisticsByRefundFlowId(int refundFlowId) {
		Result result = null;
		try {
			result = refundItemFlowService.queryRefundItemFlowCompanyByRefundFlowId(refundFlowId);
		} catch (Exception e) {
			logger.error("根据退换货CODE查询返货物流信息异常：exception=",e);
			throw ExceptionFactory.buildEIRuntimeException("queryRefundItemFlowCompanyByRefundFlowId.error",e,e.getMessage());
		}
		if (!result.isSuccess()) {
			logger.error("根据退换货CODE查询返货物流信息出错，errorMessage="+result.getError());
			throw ExceptionFactory.buildEIBusinessException("queryRefundItemFlowCompanyByRefundFlowId.error",result.getResultCode(),result.getError()!=null?result.getError().toString():"未知错误");
		}
		
		List<RefundItemFlowCompanyDO> rs=(List<RefundItemFlowCompanyDO>)result.getDefaultModel();
		return rs;
	}

	@Override
	public boolean updateRefundItemFlow(RefundApplyVo refundApplyVo) {
		Result result = null;
		try {
			logger.info("退换货申请更新数据：refundApplyVo="+refundApplyVo);
			RefundItemFlowDO refundItemFlowDO = getRefundItemFlowDOByApplyData(refundApplyVo);
			result = refundItemFlowService.updateRefundItemFlow(refundItemFlowDO);
		} catch (Exception e) {
			logger.error("更新退换货单据异常：exception=",e);
			throw ExceptionFactory.buildEIRuntimeException("updateRefundItemFlow.error",e,e.getMessage());
		}
		if (!result.isSuccess()) {
			logger.error("更新退换货单据出错，errorMessage="+result.getError());
			throw ExceptionFactory.buildEIBusinessException("updateRefundItemFlow.error",result.getResultCode(),result.getError()!=null?result.getError().toString():"未知错误");
		}
		
		logger.info("退换货申请更新结果：status="+result.isSuccess());
		logger.info("退换货申请更新响应结果：result="+result.getDefaultModel());
		
		return result.isSuccess();
	}

	/***
	 * 属性赋值
	 * @param applyVo
	 * @return
	 */
	public  RefundItemFlowDO getRefundItemFlowDOByApplyData(RefundApplyVo refundApplyVo){
		RefundItemFlowDO refundItemFlowDO = new RefundItemFlowDO();
		refundItemFlowDO.setId(refundApplyVo.getId());
		refundItemFlowDO.setCode(refundApplyVo.getCode());
		refundItemFlowDO.setOrderId(Integer.valueOf(refundApplyVo.getOrderId()));
		refundItemFlowDO.setOrderCode(refundApplyVo.getOrderCode());
		refundItemFlowDO.setBuyerId(refundApplyVo.getUserId());
		refundItemFlowDO.setBuyerName(refundApplyVo.getUsername());
		refundItemFlowDO.setContactName(refundApplyVo.getContactName());
		refundItemFlowDO.setContactPostCode(refundApplyVo.getContactPostCode());
		refundItemFlowDO.setContactPhone(refundApplyVo.getContactPhone());
		refundItemFlowDO.setContactMobile(refundApplyVo.getContactMobile());
		refundItemFlowDO.setContactAddress(refundApplyVo.getContactAddress());
		refundItemFlowDO.setApplicant(refundApplyVo.getUsername());
		refundItemFlowDO.setStatus(refundApplyVo.getStatus());
		
		// 退换货订单详细信息
		List<RefundItemFlowDetailDO> refundItemFlowDetailDOList = new ArrayList<RefundItemFlowDetailDO>();
		RefundItemFlowDetailDO refundItemFlowDetailDO = new RefundItemFlowDetailDO();
		refundItemFlowDetailDO.setId(refundApplyVo.getDetailId());
		refundItemFlowDetailDO.setOrderDetailId(refundApplyVo.getOrderDetailId());
		refundItemFlowDetailDO.setItemName(refundApplyVo.getGoodsName());
		refundItemFlowDetailDO.setType(refundApplyVo.getType());
		refundItemFlowDetailDO.setStatus(refundApplyVo.getStatus());
		refundItemFlowDetailDO.setQuantity(refundApplyVo.getNumber());
		refundItemFlowDetailDO.setBusinessReason(refundApplyVo.getReason());
		refundItemFlowDetailDO.setProof(refundApplyVo.getProofImgURL());
		refundItemFlowDetailDO.setDescription(refundApplyVo.getDescription());
		refundItemFlowDetailDOList.add(refundItemFlowDetailDO);
		refundItemFlowDO.setRefundItemFlowDetailDOList(refundItemFlowDetailDOList);
		
		// 操作用户信息
		OperationLogDO operationLogDO = new OperationLogDO();
		operationLogDO.setOperatorId(refundApplyVo.getUserId());
		operationLogDO.setOperatorName(refundApplyVo.getUsername());
		operationLogDO.setOperatorIp(refundApplyVo.getOperatorIp());
		refundItemFlowDO.setOperationLogDO(operationLogDO);
		
		return refundItemFlowDO;
	}

	@Override
	public boolean cancelRefundItemFlow(Map<String, String> params) {
		Result result = null;
		try {
			logger.info("退换货申请取消参数：params="+params);
			RefundItemFlowDO refundItemFlowDO = new RefundItemFlowDO();
			refundItemFlowDO.setBuyerId(Long.parseLong(params.get("userId")));
			refundItemFlowDO.setId(Integer.parseInt(params.get("id")));
			refundItemFlowDO.setCode(params.get("code"));
			refundItemFlowDO.setStatus(3);  // 状态为3 为取消用户订单
			// 操作用户信息
			OperationLogDO operationLogDO = new OperationLogDO();
			operationLogDO.setOperatorId(Long.parseLong(params.get("userId")));
			operationLogDO.setOperatorName(params.get("username"));
			operationLogDO.setOperatorIp(params.get("ip"));
			refundItemFlowDO.setOperationLogDO(operationLogDO);
			result = refundItemFlowService.updateRefundItemFlow(refundItemFlowDO);
		} catch (Exception e) {
			logger.error("取消退换货单据异常：exception=",e);
			throw ExceptionFactory.buildEIRuntimeException("updateRefundItemFlow.error",e,e.getMessage());
		}
		if (!result.isSuccess()) {
			logger.error("取消退换货单据出错，errorMessage="+result.getError());
			throw ExceptionFactory.buildEIBusinessException("updateRefundItemFlow.error",result.getResultCode(),result.getError()!=null?result.getError().toString():"未知错误");
		}
		
		logger.info("退换货申请取消结果：status="+result.isSuccess());
		logger.info("退换货申请取消响应结果：result="+result.getDefaultModel());
		return result.isSuccess();
	}

	public List<RefundItemFlowDO> getOrderRefundList(long orderDetailId) {
		Result result = null;
		try {
			logger.info("查询订单商品退换货列表，参数为orderDetailId="+orderDetailId);
			
			result = refundItemFlowService.queryRefundItemFlowListByOrderDetailId(orderDetailId);
		} catch (Exception e) {
			logger.error("根据订单商品详情ID查询退换列表异常：exception=",e);
			throw ExceptionFactory.buildEIRuntimeException("queryRefundItemFlowList.error",e,e.getMessage());
		}
		if (!result.isSuccess()) {
			logger.error("根据订单商品详情ID查询退换列表出错，errorMessage="+result.getError());
			throw ExceptionFactory.buildEIBusinessException("queryRefundItemFlowList.error",result.getResultCode(),result.getError()!=null?result.getError().toString():"未知错误");
		}
		
		return (List<RefundItemFlowDO>)result.getDefaultModel();
	}
}
