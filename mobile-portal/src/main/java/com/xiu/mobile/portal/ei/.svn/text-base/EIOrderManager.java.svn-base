package com.xiu.mobile.portal.ei;

import java.util.List;

import com.xiu.common.command.result.Result;
import com.xiu.common.lang.PagingList;
import com.xiu.mobile.portal.model.OrderInvoiceVO;
import com.xiu.sales.common.balance.dataobject.BalanceOrderInfoDO;
import com.xiu.sales.common.blacklist.dataobject.ItemBlackParamDO;
import com.xiu.tc.common.orders.domain.BizOrderDO;
import com.xiu.tc.common.orders.domain.CancelDO;
import com.xiu.tc.common.orders.domain.OrderSysConfig;
import com.xiu.tc.common.orders.domain.PayOrderDO;
import com.xiu.tc.common.orders.domain.QueryDO;
import com.xiu.tc.orders.condition.QueryOrderCondition;
import com.xiu.tc.orders.condition.UpdatePayTypeForWapCondition;


/** 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 外部接口-订单管理OSC
 * @AUTHOR : mike@xiu.com 
 * @DATE :2014-5-13 上午9:40:25
 * </p>
 **************************************************************** 
 */
public interface EIOrderManager {

	/**
	 * 创建订单
	 * @param order
	 * @return
	 */
	public BizOrderDO createOrder(BizOrderDO order);
	
	/**
	 * 撤销订单
	 * @param cancelDO
	 * @return
	 */
	public boolean cancelOrder(CancelDO cancelDO);
	
	/**
	 * 修改支付方式
	 * @param updatePayType
	 * @return
	 */
	public boolean updatePayTypeByWap(UpdatePayTypeForWapCondition updatePayType);
	
	/**
	 * 订单计算
	 * @param boi
	 * @return
	 */
	public Result orderBalanceService(BalanceOrderInfoDO boi);
	
	/**
	 * 计算用户的订单数
	 * @param userId
	 * @return
	 */
	public int queryUserCountOrderNum(String userId);
	
	/**
	 * 查询订单详情
	 * @param userId
	 * @param orderId
	 * @return
	 */
	public BizOrderDO queryOrderAllInfo(long userId,int orderId);
	
	/**
	 * 用户的订单列表
	 * @param userId
	 * @param queryDO
	 * @param pageList
	 * @return
	 */
	public Result queryOrderList(Long userId,QueryDO queryDO,PagingList<BizOrderDO> pageList);

	/**
	 * 获取促销系统的黑名单
	 * @param itemBlackParamDO
	 * @return
	 */
	public ItemBlackParamDO itemBlackScope(ItemBlackParamDO itemBlackParamDO);

	/**
	 * 根据orderId获取用户订单信息
	 * @param orderId
	 * @return
	 */
	public BizOrderDO queryOrderBaseInfo(int orderId);

	/**
	 * 查询待评论订单信息
	 * @param condition
	 * @param pageList
	 * @return
	 */
	public Result queryWaitingForCommentOrder(QueryOrderCondition condition,
			PagingList<BizOrderDO> pageList);
   
	/**
	 * 更新已发货订单为已完结状态
	 * @param orderId
	 * @param userId
	 * @return
	 */
	public Result updateTradeEndStatus(long orderId, long userId);
	
	/**
	 * 根据订单ID删除订单
	 * @param orderId
	 * @param userId
	 * @return
	 */
	public Result deleteOrder(long userId, int orderId);
	
	/**
	 * 查询用户购买商品数量
	 * @param userId
	 * @param goodsSn
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public int queryUserBuyGoodsCount(long userId, String goodsSn, String beginDate, String endDate);
	
	
	/**
	 *查询用户发票 
	 * @return
	 */
	public List<OrderInvoiceVO> queryInvoiceType();
	
	/**
	 * 查询用户完成的订单数量
	 * 订单状态：交易完结、订单完结
	 * @param buyerId
	 * @return
	 */
	int queryOrderDetailCount(long buyerId);
	
	/**
	 * 查询用户有效的订单数量
	 * 订单状态：已审核、交易完结、订单完结
	 * @param buyerId
	 * @return
	 */
	int queryValueOrderDetailCount(long buyerId);
	
	
	/**
	 * 查询可以申请售后服务器的订单列表
	 * @param condition
	 * @param pageList
	 * @return
	 */
	public Result queryCanBeRefundOrderList(QueryOrderCondition condition,
			PagingList<BizOrderDO> pageList);
	
	/**
	 * 保存订单支付记录
	 * @param payOrderDO
	 * @return
	 */
	public int saveOrderPayRecord(PayOrderDO payOrderDO);
	
	/**
	 * 查询未到账的支付记录
	 * @param orderId
	 * @return
	 */
	public List<PayOrderDO> queryUnPayedRecord(String orderId);

	/**
	 * 查询订单系统配置
	 * @return
	 */
	public OrderSysConfig queryOrderSysConfigInfo();
	
}
