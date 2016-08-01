package com.xiu.mobile.portal.common.constants;

/**
 * 订单状态显示枚举类
 * 
 * @author wangzhenjiang
 *
 */
public enum OrderStatus {
	//旧状态
	/**待审核*/
	DELAY_CHECK(1, "待审核"),
	/**已审核*/
	CHECKED(2, "已审核"),
	/**审核不通过*/
	CHECK_NOT_PASS(3, "审核不通过"),
	/**待付款*/
	DELAY_PAY(4, "待付款"),
	/**备货中*/
	DELAY_DELIVE(5, "备货中"),
	/**部分已发货*/
	PART_DELIVERED(6, "部分已发货"),
	/**已发货*/
	DELIVERED(7, "已发货"),
	/**已撤销*/
	REPEALED(8, "已撤销"),
	/**交易完结*/
	TRADED_COMPLETED(9, "交易完结"),
	/**订单完成*/
	ORDER_COMPLETED(10, "订单完成"),

	//新状态
	/**待审核*/
	ORDER_WAITING_VERIFY(1, "待审核"),
	/**已审核*/
	//CHECKED(2, "已审核"),
	/**审核不通过*/
	ORDER_VERIFY_NOT_PASS(3, "审核不通过"),
	/**待付款*/
	ORDER_WAITING_PAY(4, "待支付"),
	/**备货中*/
	ORDER_PACKAGEING(5, "备货中"),
	/**部分已发货*/
	ORDER_DELIVER_PART(6, "部分已发货"),
	/**已发货*/
	ORDER_DELIVERED(7, "已发货"),
	/**已撤销*/
	ORDER_CANCEL(8, "已撤销"),
	/**交易完结*/
	ORDER_TRADE_CLOSE(9, "交易完成"),
	/**订单完成*/
	ORDER_CLOSE(10, "订单完结"),
	
	//最新状态
	/**待审核*/
	NEW_ORDER_WAITING_VERIFY(200, "待审核"),
	/**已审核*/
	//CHECKED(2, "已审核"),
	/**审核不通过*/
	NEW_ORDER_VERIFY_NOT_PASS(-200, "审核不通过"),
	/**待付款*/
	NEW_ORDER_WAITING_PAY(100, "待支付"),
	/**备货中*/
	NEW_ORDER_PACKAGEING(300, "备货中"),
	/**部分已发货*/
	NEW_ORDER_DELIVER_PART(400, "部分已发货"),
	/**已发货*/
	NEW_ORDER_DELIVERED(500, "已发货"),
	/**已撤销*/
	NEW_ORDER_CANCEL(-100, "已撤销"),
	/**交易完结*/
	NEW_ORDER_TRADE_CLOSE(600, "交易完成"),
	/**订单完成*/
	NEW_ORDER_CLOSE(700, "订单完结");

	/**
	 * 订单状态代码
	 */
	private int code;
	/**
	 * 订单状态描述
	 */
	private String desc;

	private OrderStatus(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return this.code;
	}

	public String getDesc() {
		return this.desc;
	}
}
