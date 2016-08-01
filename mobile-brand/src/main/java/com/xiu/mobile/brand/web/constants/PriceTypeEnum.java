/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-9-16 上午9:56:56 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.mobile.brand.web.constants;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(价格类型枚举类) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-9-16 上午9:56:56 
 * ***************************************************************
 * </p>
 */

public enum PriceTypeEnum {

	ACTIVITY(1),
	
	XIU(2), 
	
	PROMOTION(3), 
	
	MARKET(4);
	
	private int type;
	
	PriceTypeEnum(int type) {
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

}
