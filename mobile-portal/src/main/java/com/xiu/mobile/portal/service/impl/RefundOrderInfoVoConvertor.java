package com.xiu.mobile.portal.service.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xiu.common.lang.PagingList;
import com.xiu.mobile.portal.common.util.RefundInfoUtil;
import com.xiu.mobile.portal.model.RefundContactVo;
import com.xiu.mobile.portal.model.RefundGoodsInfoVo;
import com.xiu.mobile.portal.model.RefundLogisticsVo;
import com.xiu.mobile.portal.model.RefundOrderInfoVo;
import com.xiu.tc.common.orders.domain.OrderDetailDO;
import com.xiu.tc.common.orders.domain.RefundItemFlowCompanyDO;
import com.xiu.tc.common.orders.domain.RefundItemFlowDO;
import com.xiu.tc.common.orders.domain.RefundItemFlowDetailDO;

/***
 * 商品列表信息转换
 * @author hejianxiong
 *
 */
@SuppressWarnings("serial")
public class RefundOrderInfoVoConvertor implements Serializable{

	/***
	 * 将用户退换货列表信息转换成mobile需要的数据格式
	 * @param pagingList
	 * @return
	 */
	public static List<RefundOrderInfoVo> getRefundOrderInfoVoList(PagingList<RefundItemFlowDO> pagingList){
		// 退换货列表响应信息
		List<RefundOrderInfoVo> refundOrderInfoVoList = new ArrayList<RefundOrderInfoVo>();
		List<RefundItemFlowDO> refundItemFlowDOList = pagingList.getItems();
		for (RefundItemFlowDO refundItemFlowDO : refundItemFlowDOList) {
			RefundOrderInfoVo refundOrderInfoVo = getRefundOrderInfoVo(refundItemFlowDO);
			refundOrderInfoVo.setContactVo(null); // 列表不需要联系人、图片、备注说明等信息展示
			refundOrderInfoVo.setProofImgURL(null);
			refundOrderInfoVo.setRefundReason(null);
			refundOrderInfoVo.setRemark(null);
			refundOrderInfoVoList.add(refundOrderInfoVo);
		}
		return refundOrderInfoVoList;
	}
	
	/***
	 * 将用户退换货列表信息转换成mobile需要的数据格式
	 * @param pagingList
	 * @return
	 */
	public static RefundOrderInfoVo getRefundOrderInfoVo(RefundItemFlowDO refundItemFlowDO){
		RefundOrderInfoVo refundOrderInfoVo = new RefundOrderInfoVo();
		refundOrderInfoVo.setId(refundItemFlowDO.getId());
		refundOrderInfoVo.setCode(refundItemFlowDO.getCode());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (refundItemFlowDO.getGmtCreate()!=null) {
			refundOrderInfoVo.setApplyDate(dateFormat.format(refundItemFlowDO.getGmtCreate()));
		}else{
			refundOrderInfoVo.setApplyDate(dateFormat.format(new Date()));
		}
		if (refundItemFlowDO.getGmtHandle()!=null) {
			refundOrderInfoVo.setHandleDate(dateFormat.format(refundItemFlowDO.getGmtHandle()));
		}else{
			refundOrderInfoVo.setHandleDate(dateFormat.format(new Date()));
		}
		
		// 获取退换货商品订单详细信息
		List<RefundItemFlowDetailDO> refundItemFlowDetailDOList = refundItemFlowDO.getRefundItemFlowDetailDOList();
		if (refundItemFlowDetailDOList.size()>0) {
			RefundItemFlowDetailDO refundItemFlowDetailDO = refundItemFlowDetailDOList.get(0);
			OrderDetailDO orderDetailDO = refundItemFlowDetailDO.getOrderDetailDO();
			refundOrderInfoVo.setDetailId(refundItemFlowDetailDO.getId());
			refundOrderInfoVo.setHandleStatus(refundItemFlowDetailDO.getStatus());
			refundOrderInfoVo.setHandleStatusDesc(RefundInfoUtil.getHandleStatusDesc(refundItemFlowDetailDO.getStatus()));
			String handleProgress = RefundInfoUtil.getHandleProgressDesc(refundItemFlowDO.getStatus(),refundItemFlowDetailDO.getStatus(),refundItemFlowDetailDO.getType());
			refundOrderInfoVo.setHandleProgress(handleProgress);
			refundOrderInfoVo.setLogisticsStatus(orderDetailDO.getLogisticsStatus());
			refundOrderInfoVo.setOrderId(String.valueOf(refundItemFlowDO.getOrderId()));
			refundOrderInfoVo.setOrderDetailId(orderDetailDO.getOrderDetailId());
			refundOrderInfoVo.setOrderNo(refundItemFlowDO.getOrderCode());
			refundOrderInfoVo.setOrderStatus(refundItemFlowDO.getStatus());
			refundOrderInfoVo.setOrderStatusDesc(RefundInfoUtil.getOrderStatusDesc(refundItemFlowDO.getStatus(),refundItemFlowDetailDO.getStatus()));
			refundOrderInfoVo.setOrderType(String.valueOf(refundItemFlowDetailDO.getType()));
			refundOrderInfoVo.setRefundReason(refundItemFlowDetailDO.getBusinessReason());
			refundOrderInfoVo.setProofImgURL(refundItemFlowDetailDO.getProof());
			refundOrderInfoVo.setRemark(refundItemFlowDetailDO.getDescription());
			refundOrderInfoVo.setApplyStatus(RefundInfoUtil.getStatus(refundItemFlowDO.getStatus(),refundItemFlowDetailDO.getStatus()));
			// 计算订单总价
			int number = orderDetailDO.getQuantity();
			double price = (double)orderDetailDO.getSharePrice() / 100;
			double totalPrice = price * number;
			refundOrderInfoVo.setTotalPrice(String.valueOf(totalPrice));
			
			// 商品联系人信息
			RefundContactVo refundContactVo = new RefundContactVo();
			refundContactVo.setContactMobile(refundItemFlowDO.getContactMobile());
			refundContactVo.setContactName(refundItemFlowDO.getContactName());
			refundContactVo.setContactPhone(refundItemFlowDO.getContactPhone());
			refundContactVo.setReceiverAddress(refundItemFlowDO.getContactAddress());
			refundContactVo.setReceiverPostCode(refundItemFlowDO.getContactPostCode());
			refundOrderInfoVo.setContactVo(refundContactVo);
			
			// 商品信息
			RefundGoodsInfoVo refundGoodsInfoVo = new RefundGoodsInfoVo();
			refundGoodsInfoVo.setColor(orderDetailDO.getColor());
			refundGoodsInfoVo.setDiscountPrice(String.valueOf(price));
			// 商品单据数量获取订单详情数量
			refundGoodsInfoVo.setOrginalNumber(refundItemFlowDetailDO.getQuantity());
			refundGoodsInfoVo.setGoodsId(String.valueOf(orderDetailDO.getItemId()));
			refundGoodsInfoVo.setGoodsImg(orderDetailDO.getPicturePath());
			refundGoodsInfoVo.setGoodsName(orderDetailDO.getItemName());
			refundGoodsInfoVo.setGoodsSn(orderDetailDO.getItemCodes());
			refundGoodsInfoVo.setSize(orderDetailDO.getSize());
			refundGoodsInfoVo.setSkuCode(orderDetailDO.getSkuCode());
			refundGoodsInfoVo.setReturnNumber(orderDetailDO.getRefundItemQuantity());
			refundGoodsInfoVo.setReplaceNumber(orderDetailDO.getChangeItemQuantity());
			refundGoodsInfoVo.setTotalPrice(String.valueOf(totalPrice));
			refundGoodsInfoVo.setDeliverType(orderDetailDO.getDeliverType());
			// 商品信息
			refundOrderInfoVo.setGoodsInfoVo(refundGoodsInfoVo);
		}
		
		return refundOrderInfoVo;
	}
	
	/***
	 * 获取用户退换货商品列表信息
	 * @param pagingList
	 * @return
	 */
	public static List<RefundGoodsInfoVo> getRefundGoodsInfoVo(RefundItemFlowDO refundItemFlowDO){
		// 商品列表数据信息
		List<RefundGoodsInfoVo> refundGoodsInfoVoList = new ArrayList<RefundGoodsInfoVo>();
		// 获取退换货商品订单详细信息
		List<RefundItemFlowDetailDO> refundItemFlowDetailDOList = refundItemFlowDO.getRefundItemFlowDetailDOList();
		for (RefundItemFlowDetailDO refundItemFlowDetailDO : refundItemFlowDetailDOList) {
			OrderDetailDO orderDetailDO = refundItemFlowDetailDO.getOrderDetailDO();
			// 计算订单总价
			int number = orderDetailDO.getQuantity();
			double price = (double)orderDetailDO.getSharePrice() / 100;
			double totalPrice = price * number;
			
			// 商品信息
			RefundGoodsInfoVo refundGoodsInfoVo = new RefundGoodsInfoVo();
			refundGoodsInfoVo.setColor(orderDetailDO.getColor());
			refundGoodsInfoVo.setDiscountPrice(String.valueOf(orderDetailDO.getDiscountPrice()));
			refundGoodsInfoVo.setOrginalNumber(orderDetailDO.getOriginalQuantity());
			refundGoodsInfoVo.setGoodsId(String.valueOf(orderDetailDO.getItemId()));
			refundGoodsInfoVo.setGoodsImg(orderDetailDO.getPicturePath());
			refundGoodsInfoVo.setGoodsName(orderDetailDO.getItemName());
			refundGoodsInfoVo.setGoodsSn(orderDetailDO.getItemCodes());
			refundGoodsInfoVo.setSize(orderDetailDO.getSize());
			refundGoodsInfoVo.setSkuCode(orderDetailDO.getSkuCode());
			refundGoodsInfoVo.setReturnNumber(orderDetailDO.getRefundItemQuantity());
			refundGoodsInfoVo.setReplaceNumber(orderDetailDO.getRefundableQuantity());
			refundGoodsInfoVo.setTotalPrice(String.valueOf(totalPrice));
			// 商品信息
			refundGoodsInfoVoList.add(refundGoodsInfoVo);
		}
		
		return refundGoodsInfoVoList;
	}
	
	/***
	 * 获取用户退换货商品列表信息
	 * @param pagingList
	 * @return
	 */
	public static RefundLogisticsVo getRefundLogisticsVo(RefundItemFlowCompanyDO refundItemFlowCompanyDO){
		RefundLogisticsVo refundLogisticsVo = new RefundLogisticsVo();
		refundLogisticsVo.setCompanyName(refundItemFlowCompanyDO.getCompanyName());
		refundLogisticsVo.setLogisticsCode(refundItemFlowCompanyDO.getExpressOrderCode());
		refundLogisticsVo.setOrderId(String.valueOf(refundItemFlowCompanyDO.getOrderId()));
		refundLogisticsVo.setPostFee(new Long(refundItemFlowCompanyDO.getPostFee()).doubleValue());
		refundLogisticsVo.setRefundOrderId(String.valueOf(refundItemFlowCompanyDO.getRefundFlowId()));
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (refundItemFlowCompanyDO.getGmtCreate()!=null) {
			refundLogisticsVo.setCreateDate(dateFormat.format(refundItemFlowCompanyDO.getGmtCreate()));
		}else{
			refundLogisticsVo.setCreateDate(dateFormat.format(new Date()));
		}
		return refundLogisticsVo;
	}
	
	/***
	 * 将用户退换货列表信息转换成mobile需要的数据格式
	 * @param list
	 * @return
	 */
	public static List<RefundOrderInfoVo> getRefundOrderInfoList(List<RefundItemFlowDO> list){
		// 退换货列表响应信息
		List<RefundOrderInfoVo> refundOrderInfoVoList = new ArrayList<RefundOrderInfoVo>();
		for (RefundItemFlowDO refundItemFlowDO : list) {
			RefundOrderInfoVo refundOrderInfoVo = getRefundOrderInfoVo(refundItemFlowDO);
			refundOrderInfoVo.setContactVo(null); // 列表不需要联系人、图片、备注说明等信息展示
			refundOrderInfoVo.setProofImgURL(null);
			refundOrderInfoVo.setRefundReason(null);
			refundOrderInfoVo.setRemark(null);
			refundOrderInfoVoList.add(refundOrderInfoVo);
		}
		return refundOrderInfoVoList;
	}
}
