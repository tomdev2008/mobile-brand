package com.xiu.mobile.portal.model;

import java.io.Serializable;

/**
 * 激活 和验证优惠劵
 */
public class UseCouponVO extends DeviceVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4099263275373001017L;

	private String tokenId;

	private String cardId;

	private String cardPwd;

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getCardPwd() {
		return cardPwd;
	}

	public void setCardPwd(String cardPwd) {
		this.cardPwd = cardPwd;
	}

}
