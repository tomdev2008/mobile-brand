package com.xiu.mobile.portal.ei;

import java.util.List;
import java.util.Map;

import com.xiu.common.lang.PagingList;
import com.xiu.mobile.portal.model.RefundApplyVo;
import com.xiu.mobile.portal.model.RefundLogisticsVo;
import com.xiu.tc.common.orders.domain.RefundItemFlowCompanyDO;
import com.xiu.tc.common.orders.domain.RefundItemFlowDO;

public interface EIGoodsRefundManager {
	
	/**
	 * 根据订单编号查询可退换货商品列表
	 * 
	 * @param orderCode 
	 * 					订单编号
	 * @param userId	
	 * 					用户Id
	 * @return
	 */
	RefundItemFlowDO getUserRefundGoodsListByOrderCode(String orderCode, long userId);
	
	/**
	 * 根据商品详情编号查询并构建退换货信息
	 *  
	 * @param orderCode
	 * 					订单编号
	 * @param orderDetailId
	 * 					订单详情Id
	 * @param userId
	 * 					用户Id
	 * @return
	 */
	RefundItemFlowDO getUserGoodsRefundInfo(String orderCode, int orderDetailId, long userId);
	
	/**
	 * 记录退换货申请单据
	 *  
	 * @param refundApplyVo 退换货信息
	 * @return 响应退换货流水号
	 */
	String saveRefundItemFlow(RefundApplyVo refundApplyVo);
	
	/**
	 * 记录返货物流信息
	 * 
	 * @param refundLogisticsVo
	 * @return
	 */
	int saveRefundItemFlowCompany(RefundLogisticsVo refundLogisticsVo);

	/***
	 * 根据条件查询退换列表
	 * @param params
	 * @return
	 */
	PagingList<RefundItemFlowDO> getUserRefundList(Map<String, Object> params);

	/**
	 * 查询申请单据CODE查询
	 * 
	 * @param code 
	 * 				退换货申请单据CODE
	 * @param userId
	 * 				用户Id
	 * @return
	 */
	RefundItemFlowDO getUserRefundInfoByCode(String code, long userId);

	/**
	 * 根据退换货Id查询返货物流信息
	 * 
	 * @param refundFlowId 退换货申请单据Id
	 * @return
	 */
	List<RefundItemFlowCompanyDO> getRefundLogisticsByRefundFlowId(int refundFlowId);

	/**
	 * 更新退换货单据
	 * 
	 * @param refundApplyVo
	 * @return
	 */
	boolean updateRefundItemFlow(RefundApplyVo refundApplyVo);
	
	/**
	 * 取消退换货单据
	 * 
	 * @param params
	 * @return
	 */
	boolean cancelRefundItemFlow(Map<String, String> params);
	
	/**
	 * 查询订单商品退换货列表
	 * @param orderDetailId
	 * @return
	 */
	List<RefundItemFlowDO> getOrderRefundList(long orderDetailId);

}
