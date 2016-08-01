/**  
 * @Project: xiu
 * @Title: AddressUpdateOutParam.java
 * @Package org.lazicats.xiu.model.address
 * @Description: TODO
 * @author: yong
 * @date 2013-5-6 上午10:02:32
 * @Copyright: BroadenGate Software Services Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.xiu.mobile.portal.facade.model;

import com.xiu.mobile.portal.common.model.BaseBackDataVo;


/** 
 * @ClassName: AddressUpdateOutParam 
 * @Description: TODO
 * @author: yong
 * @date 2013-5-6 上午10:02:32
 *  
 */
public class AddressUpdateOutParam  extends BaseBackDataVo{
	
	/** 
	 * @Fields serialVersionUID : TODO
	 */ 
	private static final long serialVersionUID = 3387101433774588783L;

	private String tokenId;
	
	private AddressDataList  addressDataList;

	
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
	 * @return addressDataList 
	 */
	public AddressDataList getAddressDataList() {
		return addressDataList;
	}

	/**
	 * @param addressDataList the addressDataList to set
	 */
	public void setAddressDataList(AddressDataList addressDataList) {
		this.addressDataList = addressDataList;
	}
}
