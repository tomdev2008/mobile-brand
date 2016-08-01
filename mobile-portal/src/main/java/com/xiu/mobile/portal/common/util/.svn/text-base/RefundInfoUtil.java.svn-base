package com.xiu.mobile.portal.common.util;

public class RefundInfoUtil {

	
	/***
	 * 通过订单状态及处理状态获取单据显示数据
	 * @param orderStatus
	 * @param handleStatus
	 * #1待受理——受理状态：0:未受理
	 * #2受理中——受理状态：1:受理成功 and 审核状态：0:申请中
	 * #3申请成功——审核状态：1:申请成功 or 3:退货成功 or  5:换货成功 or 7:维修成功 or 10:换货沟通成功
	 * #4申请失败——受理状态：受理失败 or  审核状态：2:申请失败 or 4:退货失败 or 6:换货失败 or 8:维修失败
	 * #5已取消——受理状态：已取消 or 审核状态：9:已取消
	 * @return
	 */
	public static String getOrderStatusDesc(int orderStatus,int handleStatus) {
		String orderStatusDesc = "";
		if (orderStatus == 0) {
			orderStatusDesc = "待受理";
		}else if(orderStatus == 1 && handleStatus == 0){
			orderStatusDesc = "受理中";
		}else if(handleStatus == 1 || handleStatus == 3 || handleStatus == 5 || handleStatus == 7 || handleStatus == 10){
			orderStatusDesc = "申请成功";
		}else if(orderStatus == 2 || handleStatus == 2 || handleStatus == 4 || handleStatus == 6 || handleStatus == 8){
				orderStatusDesc = "申请失败";
		}else if(orderStatus == 3 || handleStatus == 9){
			orderStatusDesc = "已取消";
		}
		return orderStatusDesc;
	}
	
	/***
	 * 通过订单状态及处理状态获取单据显示数据
	 * @param orderStatus
	 * @param handleStatus
	 * #0待受理——受理状态：0:未受理
	 * #4受理中——受理状态：1:受理成功 and 审核状态：0:申请中
	 * #1申请成功——审核状态：1:申请成功 or 3:退货成功 or  5:换货成功 or 7:维修成功 or 10:换货沟通成功
	 * #2申请失败——受理状态：受理失败 or  审核状态：2:申请失败 or 4:退货失败 or 6:换货失败 or 8:维修失败
	 * #3已取消——受理状态：已取消 or 审核状态：9:已取消
	 * @return
	 */
	public static Integer getStatus(int orderStatus,int handleStatus) {
		Integer status = 0;
		if (orderStatus == 0) {
			status = 0;
		}else if(orderStatus == 1 && handleStatus == 0){
			status=4;//"受理中";
		}else if(handleStatus == 1 || handleStatus == 3 || handleStatus == 5 || handleStatus == 7 || handleStatus == 10){
			status=1;// "申请成功";
		}else if(orderStatus == 2 || handleStatus == 2 || handleStatus == 4 || handleStatus == 6 || handleStatus == 8){
			status=2;//"申请失败";
		}else if(orderStatus == 3 || handleStatus == 9){
			status=3;//"已取消";
		}
		return status;
	}

	/***
	 * 获取订单状态说明信息 处理状态 0:申请中 1:申请成功 2:申请失败 3:退货成功 4:退货失败 5:换货成功 6:换货失败 7:维修成功
	 * 8:维修失败 9:已取消
	 * 
	 * @param status
	 * @return
	 */
	public static String getHandleStatusDesc(int status) {
		switch (status) {
			case 0:
				return "申请中";
			case 1:
				return "申请成功";
			case 2:
				return "申请失败";
			case 3:
				return "退货成功";
			case 4:
				return "退货失败";
			case 5:
				return "换货成功";
			case 6:
				return "换货失败";
			case 7:
				return "维修成功";
			case 8:
				return "维修失败";
			case 9:
				return "已取消";
			case 10:
				return "换货沟通成功";
			default:
				return "未知状态";
		}
	}
	
	
	/***
	 * 通过订单状态、处理状态、订单类型获取订单新的处理进度提示信息
	 * @param orderStatus
	 * @param handleStatus
	 * @param orderType
	 * 新的处理进度提示如下：
	 * #1待受理：统一提示   您提交的退or换货申请待客服审核 
	 * #2受理中：统一提示   您提交的退or换货申请正在受理中
	 * #3申请成功：
	 * 若审核状态为申请成功，则提示  申请成功，请寄回商品并登记快递信息
	 * 若审核状态为退货/换货/维修成功，则提示  退货/换货/维修成功
	 * 若审核状态为换货沟通成功，则提示 换货成功
	 * #4申请失败：
	 * 若单据状态为受理失败或审核状态为申请失败，则提示  您提交的退or换货申请审核不通过
	 * 若审核状态为退货/换货/维修失败，则提示  退货/换货/维修失败
	 * #5已取消：统一提示   您提交的退or换货申请已取消
	 * @return
	 */
	public static String getHandleProgressDesc(int orderStatus,int handleStatus,int orderType) {
		String typeDesc = getOrderTypeDesc(orderType);
		String handleProgress = "";
		if (orderStatus == 0) {
			handleProgress = "您提交的".concat(typeDesc).concat("申请待客服审核");
		}else if(orderStatus == 1 && handleStatus == 0){
			handleProgress = "您提交的".concat(typeDesc).concat("申请正在受理中");
		}
		//成功
		else if(handleStatus == 1 || handleStatus == 3 || handleStatus == 5 || handleStatus == 7 || handleStatus == 10){
			if(handleStatus == 1){
				handleProgress = "申请成功，请寄回商品并登记快递信息";
			}else if(handleStatus == 3 || handleStatus == 5 || handleStatus == 7){
				handleProgress = typeDesc.concat("成功");
			}else if (handleStatus == 10) {
				handleProgress = "换货成功";
			}
		}
		//失败
		else if(orderStatus == 2 || handleStatus == 2 || handleStatus == 4 || handleStatus == 6 || handleStatus == 8){
			if (handleStatus == 2 || handleStatus == 9) {
				handleProgress = "您提交的".concat(typeDesc).concat("申请审核不通过");
			}else if (handleStatus == 4 || handleStatus == 6 || handleStatus == 8) {
				handleProgress = typeDesc.concat("失败");
			} 
		}else if(orderStatus == 3 || handleStatus == 9){
			handleProgress = "您提交的".concat(typeDesc).concat("申请已取消");
		}
		
		return handleProgress;
	}
	
	/***
	 * 通过订单状态获取退换货说明
	 * @param orderType
	 * 1:退货 2:换货 3:维修 
	 * @return
	 */
	public static String getOrderTypeDesc(int orderType) {
		switch (orderType) {
			case 1:
				return "退货";
			case 2:
				return "换货";
			case 3:
				return "维修 ";
			default:
				return "";
		}
	}

}
