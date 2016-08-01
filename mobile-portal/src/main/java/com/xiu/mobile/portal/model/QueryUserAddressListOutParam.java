/**  
 * @Project: xiu
 * @Title: QueryUserAddressListOutParam.java
 * @Package org.lazicats.xiu.model.address
 * @Description: TODO
 * @author: yong
 * @date 2013-5-7 上午08:19:31
 * @Copyright: BroadenGate Software Services Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.xiu.mobile.portal.model;

import java.util.List;

import com.xiu.mobile.portal.common.model.BaseBackDataVo;

/** 
 * @ClassName: QueryUserAddressListOutParam 
 * @Description: TODO
 * @author: yong
 * @date 2013-5-7 上午08:19:31
 *  
 */
public class QueryUserAddressListOutParam  extends BaseBackDataVo{
	/** 
	 * @Fields serialVersionUID : TODO
	 */ 
	private static final long serialVersionUID = 1504345899385927955L;

	private String tokenId;
	
	private List<AddressVo> addressList;

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
	 * @return addressList 
	 */
	public List<AddressVo> getAddressList() {
		return addressList;
	}

	/**
	 * @param addressList the addressList to set
	 */
	public void setAddressList(List<AddressVo> addressList) {
		this.addressList = addressList;
	}
}
