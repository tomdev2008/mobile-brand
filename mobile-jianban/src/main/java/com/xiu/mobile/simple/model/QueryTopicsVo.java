/**  
 * @Project: xiu
 * @Title: QueryActivityVo.java
 * @Package org.lazicats.xiu.topicActivity.model.vo
 * @Description: TODO
 * @author: chengyuanhuan
 * @date 2013-5-2 下午7:30:13
 * @Copyright: BroadenGate Software Services Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.xiu.mobile.simple.model;

import java.io.Serializable;

public class QueryTopicsVo implements Serializable {
	private static final long serialVersionUID = 1L;
	private  int pageNum;//当前页
   //1:每日促销   2:昨日上新  3:即将过期
    private  int dataType;//数据种类
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
