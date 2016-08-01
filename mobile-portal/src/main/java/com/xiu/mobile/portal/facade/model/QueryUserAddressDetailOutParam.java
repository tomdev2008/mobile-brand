/**  
 * @Project: xiu
 * @Title: QueryUserAddressDetailOutParam.java
 * @Package org.lazicats.xiu.model.address
 * @Description: TODO
 * @author: yong
 * @date 2013-5-8 上午10:27:46
 * @Copyright: BroadenGate Software Services Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.xiu.mobile.portal.facade.model;

import com.xiu.mobile.portal.common.model.BaseBackDataVo;

/** 
 * @ClassName: QueryUserAddressDetailOutParam 
 * @Description: TODO
 * @author: yong
 * @date 2013-5-8 上午10:27:46
 *  
 */
public class QueryUserAddressDetailOutParam  extends BaseBackDataVo{
	
	/** 
	 * @Fields serialVersionUID : TODO
	 */ 
	private static final long serialVersionUID = 7071862666285404900L;

	private String tokenId;
	
	private AddressOutParam addressDetail;

	/** 
	 * @return tokenId 
	 */
	public String getTokenId() {
		return tokenId;
	}

	/**
	 * @param tokenId the tokenId to set
	 */
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	/** 
	 * @return addressDetail 
	 */
	public AddressOutParam getAddressDetail() {
		return addressDetail;
	}

	/**
	 * @param addressDetail the addressDetail to set
	 */
	public void setAddressDetail(AddressOutParam addressDetail) {
		this.addressDetail = addressDetail;
	}
}
