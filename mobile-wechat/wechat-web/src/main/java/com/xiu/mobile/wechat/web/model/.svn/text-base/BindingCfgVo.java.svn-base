package com.xiu.mobile.wechat.web.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 账户绑定配置 Model类
 * 
 * @author wangzhenjiang
 * @table X_WECHAT_BINDING_CFG
 * 
 */
public class BindingCfgVo implements Serializable {
	private static final long serialVersionUID = -8380787401168964832L;

	private Long id;
	/** 走秀账号 */
	private String xiuAccount;
	/** 走秀账号密码 */
	private String xiuPassword;
	/** 用户UserId */
	private String userId;
	/** 微信OPENID */
	private String openId;
	/** 登录名 */
	private String logonName;
	/** 登陆类型：1-微信联合登陆，2-账号绑定 */
	private String xiuMode;
	/** 创建日期 */
	private Date createDate;
	/** 微信UnionId */
	private String unionId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getXiuAccount() {
		return xiuAccount;
	}

	public void setXiuAccount(String xiuAccount) {
		this.xiuAccount = xiuAccount;
	}

	public String getXiuPassword() {
		return xiuPassword;
	}

	public void setXiuPassword(String xiuPassword) {
		this.xiuPassword = xiuPassword;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLogonName() {
		return logonName;
	}

	public void setLogonName(String logonName) {
		this.logonName = logonName;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getXiuMode() {
		return xiuMode;
	}

	public void setXiuMode(String xiuMode) {
		this.xiuMode = xiuMode;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
