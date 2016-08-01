package com.xiu.mobile.portal.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

public class PayOrderItem extends PayOrder implements Serializable {
    private static final long      serialVersionUID = -5318790068445539334L;
    private String                 channelCode; //固定值，支付中心定义
    private String                 protocolVersion;//固定值：2.0
    private String                 requestSeqCode;//固定值 如果没有
    private String                 tradeAmount;// 金额：分为单位
    private String                 tradeCuryType; //:CNY
    private long                   channelId;
    private String                 payFlowCode;
    private long                   tradeItemId;
    private Date                   payDate;
    private Date                   tradeBeginDate;
    private Date                   tradeEndDate;
    private String                 bankFlowCode;
    private String                 autoAccount;
    private String                 stateNow;
    private String                 notifyBankIp;
    private Date                   createdDate;                             //创建时间;
    private Date                   updatedDate;                             //更新时间;
    private String errorMsg; // 错误信息
	private String errorCode;// 错误编码
	private String ordIp;//客户ip
	private String payMentInfo;//已经加密的payment数据（apple支付）
    //特殊参数列表
    private List<PayTradeItemPara> specialParam;
    //特殊参数Map
    private Map<String, String>    specialMap;
    
    private String reqIp;//请求IP

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public String getRequestSeqCode() {
        return requestSeqCode;
    }

    public void setRequestSeqCode(String requestSeqCode) {
        this.requestSeqCode = requestSeqCode;
    }

    public String getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(String tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public String getTradeCuryType() {
        return tradeCuryType;
    }

    public void setTradeCuryType(String tradeCuryType) {
        this.tradeCuryType = tradeCuryType;
    }

    public long getChannelId() {
        return channelId;
    }

    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    public String getPayFlowCode() {
        return payFlowCode;
    }

    public void setPayFlowCode(String payFlowCode) {
        this.payFlowCode = payFlowCode;
    }

    public long getTradeItemId() {
        return tradeItemId;
    }

    public void setTradeItemId(long tradeItemId) {
        this.tradeItemId = tradeItemId;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Date getTradeBeginDate() {
        return tradeBeginDate;
    }

    public void setTradeBeginDate(Date tradeBeginDate) {
        this.tradeBeginDate = tradeBeginDate;
    }

    public Date getTradeEndDate() {
        return tradeEndDate;
    }

    public void setTradeEndDate(Date tradeEndDate) {
        this.tradeEndDate = tradeEndDate;
    }

    public String getStateNow() {
        return stateNow;
    }

    public void setStateNow(String stateNow) {
//        if (StringUtils.isEmpty(stateNow)) {
//            this.stateNow = BasicConstants.OPERATESTATE_DOING;
//        } else {
//            this.stateNow = stateNow;
//        }
    	this.stateNow = stateNow;
    }

    public List<PayTradeItemPara> getSpecialParam() {
        return specialParam;
    }

    public void setSpecialParam(List<PayTradeItemPara> specialParam) {
        this.specialParam = specialParam;
    }

    public Map<String, String> getSpecialMap() {
        if (specialParam == null)
            return null;
        if (specialMap != null)
            return specialMap;
        specialMap = new HashMap<String, String>();
        for (PayTradeItemPara param : specialParam) {
            String paraCode = param.getParaCode();
            String paraValue = param.getParaValue();
            specialMap.put(paraCode, paraValue);
        }
        return specialMap;
    }

    public String getBankFlowCode() {
        return bankFlowCode;
    }

    public void setBankFlowCode(String bankFlowCode) {
        this.bankFlowCode = bankFlowCode;
    }

    public String getAutoAccount() {
        return autoAccount;
    }

    public void setAutoAccount(String autoAccount) {
        this.autoAccount = autoAccount;
    }

    public String getNotifyBankIp() {
        return notifyBankIp;
    }

    public void setNotifyBankIp(String notifyBankIp) {
        this.notifyBankIp = notifyBankIp;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getReqIp() {
		return reqIp;
	}

	public void setReqIp(String reqIp) {
		this.reqIp = reqIp;
	}
	
	
	public String getOrdIp() {
		return ordIp;
	}

	public void setOrdIp(String ordIp) {
		this.ordIp = ordIp;
	}

	public String getPayMentInfo() {
		return payMentInfo;
	}

	public void setPayMentInfo(String payMentInfo) {
		this.payMentInfo = payMentInfo;
	}

	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
