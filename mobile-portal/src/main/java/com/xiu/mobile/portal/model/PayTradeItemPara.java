package com.xiu.mobile.portal.model;

import java.io.Serializable;


/**
 * 交易参数(X_PAY_TRADE_ITEM_PARA)实体<br>
 * @author    xiu <br>
 * @version   V2.00 2011-7-18 <br>
 * @see       [相关类/方法] <br>
 * @since     OpenXiu2.0 <br>
 */
public class PayTradeItemPara implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long itemParaId;// 交易参数标识;
    private Long  tradeItemId ;//交易项目标识
   
    private java.lang.String paraCode;// 参数编码;   
    private java.lang.String paraValue;// 参数值;
    public Long getTradeItemId() {
        return tradeItemId;
    }
    public void setTradeItemId(Long tradeItemId) {
        this.tradeItemId = tradeItemId;
    }
    public java.lang.String getParaCode() {
        return paraCode;
    }
    public void setParaCode(java.lang.String paraCode) {
        this.paraCode = paraCode;
    }
    public java.lang.String getParaValue() {
        return paraValue;
    }
    public void setParaValue(java.lang.String paraValue) {
        this.paraValue = paraValue;
    }
    public Long getItemParaId() {
        return itemParaId;
    }
    public void setItemParaId(Long itemParaId) {
		this.itemParaId = itemParaId;
	}
    
	@Override
    public String toString() {
        return String.format("PayTradeItemPara [itemParaId=%s, tradeItemId=%s, paraCode=%s, paraValue=%s]",
                itemParaId, tradeItemId, paraCode, paraValue);
    } 
}
