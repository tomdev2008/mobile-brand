package com.xiu.mobile.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.common.lang.PagingList;
import com.xiu.mobile.portal.ei.EIGoodsRefundManager;
import com.xiu.mobile.portal.model.RefundApplyVo;
import com.xiu.mobile.portal.model.RefundLogisticsVo;
import com.xiu.mobile.portal.service.IGoodsRefundService;
import com.xiu.tc.common.orders.domain.RefundItemFlowCompanyDO;
import com.xiu.tc.common.orders.domain.RefundItemFlowDO;

@Service
public class GoodsRefundServiceImpl implements IGoodsRefundService{
	
	@Autowired
	private EIGoodsRefundManager eiGoodsRefundManager;

	@Override
	public RefundItemFlowDO getUserRefundGoodsListByOrderCode(String orderCode,long userId) {
		return eiGoodsRefundManager.getUserRefundGoodsListByOrderCode(orderCode, userId);
	}

	@Override
	public RefundItemFlowDO getUserGoodsRefundInfo(String orderCode,int orderDetailId, long userId) {
		return eiGoodsRefundManager.getUserGoodsRefundInfo(orderCode, orderDetailId, userId);
	}

	@Override
	public String saveRefundItemFlow(RefundApplyVo refundApplyVo) {
		return eiGoodsRefundManager.saveRefundItemFlow(refundApplyVo);
	}

	@Override
	public int saveRefundItemFlowCompany(RefundLogisticsVo refundLogisticsVo) {
		return eiGoodsRefundManager.saveRefundItemFlowCompany(refundLogisticsVo);
	}

	@Override
	public PagingList<RefundItemFlowDO> getUserRefundList(Map<String, Object> params) {
		return eiGoodsRefundManager.getUserRefundList(params);
	}

	@Override
	public RefundItemFlowDO getUserRefundInfoByCode(String code, long userId) {
		return eiGoodsRefundManager.getUserRefundInfoByCode(code, userId);
	}

	@Override
	public List<RefundItemFlowCompanyDO> getRefundLogisticsByRefundFlowId(int refundFlowId) {
		return eiGoodsRefundManager.getRefundLogisticsByRefundFlowId(refundFlowId);
	}

	@Override
	public boolean updateRefundItemFlow(RefundApplyVo refundApplyVo) {
		return eiGoodsRefundManager.updateRefundItemFlow(refundApplyVo);
	}

	@Override
	public boolean cancelRefundItemFlow(Map<String, String> params) {
		return eiGoodsRefundManager.cancelRefundItemFlow(params);
	}

	public List<RefundItemFlowDO> getOrderRefundList(long orderDetailId) {
		return eiGoodsRefundManager.getOrderRefundList(orderDetailId);
	}

}
