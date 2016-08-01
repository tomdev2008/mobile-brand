package com.xiu.manager.web.model;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.xiu.mobile.core.model.BaseObject;

/**
 *************************************************************** 
 * <p>
 * 
 * @DESCRIPTION : 用于存放用户登录验证的相关信息
 * @AUTHOR : xiu@xiu.com
 * @DATE :2012-4-3 上午11:44:16
 *       </p>
 **************************************************************** 
 */
public class UserAuthInfo extends BaseObject {
  
    public  static final long XIU_OFFICIAL = 11L; //渠道标识 官网
    private static final long serialVersionUID = -1542689354109286596L;
    
    private String ssoTokenId; // SSO证书id
    private Long ssoUserId; // SSO用户id
    private Long ssoCusId; // SSO客户id
    private String ssoCreateTime; // SSO用户创建时间

    private String loginName; // 登陆名
    private String loginFlag; // 是否是代理登录, 0或空:代表正常登录，1：代客登录
    private String clientIpAddress; // 客户访问IP地址

    private String unionFlag; // 是否通过联合登陆的标识
    private String unionPartnerId; // 来自联合登陆用户的id

	private Date regDate;	//用户注册时间

	private String mediaId; // 用户是用那个媒体跳转过来的
	private String userName; //昵称
    private String channelId;//渠道
    
    private String custType;//客户类型(01:走秀用户, 02:联盟用户)
    
    private String lastLoginDateTime;// 上次登录时间
 
    public String getLastLoginDateTime() {
		return lastLoginDateTime;
	}

	public void setLastLoginDateTime(String lastLoginDateTime) {
		this.lastLoginDateTime = lastLoginDateTime;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getSsoTokenId() {
        return ssoTokenId;
    }

    public void setSsoTokenId(String ssoTokenId) {
        this.ssoTokenId = ssoTokenId;
    }

    public Long getSsoUserId() {
		return ssoUserId;
	}

	public void setSsoUserId(Long ssoUserId) {
		this.ssoUserId = ssoUserId;
	}

	public Long getSsoCusId() {
		return ssoCusId;
	}

	public void setSsoCusId(Long ssoCusId) {
		this.ssoCusId = ssoCusId;
	}

	public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getSsoCreateTime() {
        return ssoCreateTime;
    }

    public void setSsoCreateTime(String ssoCreateTime) {
        this.ssoCreateTime = ssoCreateTime;
    }

    public String getLoginFlag() {
        return loginFlag;
    }

    public void setLoginFlag(String loginFlag) {
        this.loginFlag = loginFlag;
    }

    public String getClientIpAddress() {
        return clientIpAddress;
    }

    public void setClientIpAddress(String clientIpAddress) {
        this.clientIpAddress = clientIpAddress;
    }

    public String getUnionFlag() {
        return unionFlag;
    }

    public void setUnionFlag(String unionFlag) {
        this.unionFlag = unionFlag;
    }

    public String getUnionPartnerId() {
        return unionPartnerId;
    }

    public void setUnionPartnerId(String unionUserId) {
        this.unionPartnerId = unionUserId;
    }
    public Date getRegDate() {
		return this.regDate;
	}
    
    public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getMediaId() {
		return this.mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public void setUserName(String userName) {
		this.userName=userName;
	}
	
	public String getUserName() {
		return userName;
	}

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }


	

}
