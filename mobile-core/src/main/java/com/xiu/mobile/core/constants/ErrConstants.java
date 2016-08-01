package com.xiu.mobile.core.constants;

import java.util.HashMap;
import java.util.Map;


/** 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 
 * @AUTHOR : xiu@xiu.com 
 * @DATE :2012-4-18 下午3:02:11
 * </p>
 **************************************************************** 
 */
public final class ErrConstants {
    public final static String GENERAL_UNKNOWN_ERR_MSG = "未知错误";
    
    public final static String GENERAL_COMM_ERR_MSG = "很抱歉，您刚才的操作出了点问题哦！";
    public final static String GENERAL_NOTFOUND_ERR_MSG = "您要访问的页面没有找到哦！";
    public final static String GENERAL_BADREQUEST_ERR_MSG = "请求中的语法错误！";
    public final static String GENERAL_NOTIMPLEMENTED_ERR_MSG = "没有实现相关的请求功能！";
    
    public class GeneralErrorCode {
        public final static String BIZ_PORTAL_ERR = "10000"; //portal系统业务异常
        
        public final static String BIZ_ADDRESS_LIMIT_FORBIDDEN = "10001"; //超过允许添加的地址条数
        public final static String BIZ_NOTRECORD_ERR = "10002"; //未找到对应记录
        public final static String BIZ_UNAUTHORIZED_ACCESS = "10003"; //非授权操作
        
        public final static String BIZ_VIRTALACCOUNT_ILLEGAL_STATE = "20001"; //无效状态
        public final static String BIZ_VIRTALACCOUNT_FREEZED = "20002"; //账户被冻结,不允许申请提现
        public final static String BIZ_VIRTALACCOUNT_WRONGMONEY= "20003"; //请输入正确金额！
        public final static String BIZ_BIRTALACCOUNT_WRONG_SMSCODE ="20004"; //您输入的验证码错误，请查看您的手机，输入正确的验证码！
        
    }
    
    //业务错误编码
    //3AABB
    //AA:00 商品 , 01 用户 , 02 订单
    // BB: 00 ~ 19 系统预留，20以上用于自定义
    public class BusinessErrorCode{
    	public final static String BIZ_PROD_STOCK_ERR = "30001"; //管理系统用户异常
    	public final static String BIZ_COMMENT_ERR = "10000001"; // comment系统业务异常
		public final static String BIZ_WAP_ERR = "10000002"; // wap系统业务异常
		public final static String BIZ_FIND_GOODS_ERR = "10000004"; // 单品发现业务异常
		public final static String BIZ_WELL_CHOSEN_BRAND_ERR = "10000005"; // 精选品牌业务异常
    }
    
    //EI的错误编码
    // 9AABB
    // AA: 00 - PORTAL, 01 - GOODS, 02 - PUC, 03 - UUC, 04 - CSP, 05 - SSO, 06 - RM, 07 - SMS, 08 - EMAIL
    // BB: 00 ~ 19 系统预留，20以上用于自定义
    //     01 - General Error, 02 - Biz Error
    //80开头的留给http status用.
    public class SYSErrorCode {
        //TRADE - 01
        public final static String SYS_GOODS_GENERAL_ERR = "90101";
        public final static String EI_TRADE_BIZ_ERR = "90102";      
        //PUC - 02
        public final static String EI_PUC_GENERAL_ERR = "90201";
        public final static String EI_PUC_BIZ_ERR = "90202";        
        //UUC - 03
        public final static String EI_UUC_GENERAL_ERR = "90301";
        public final static String EI_UUC_BIZ_ERR = "90302";        
        //CSP - 04
        public final static String EI_CSP_GENERAL_ERR = "90401";
        public final static String EI_CSP_BIZ_ERR = "90402";
        //SSO - 05
        public final static String EI_SSO_GENERAL_ERR = "90501";
        public final static String EI_SSO_BIZ_ERR = "90502";
        //RM - 06
        public final static String EI_RM_GENERAL_ERR = "90601";
        public final static String EI_RM_BIZ_ERR = "90602";
        
        //SMS - 07
        public final static String EI_SMS_GENERAL_ERR = "90701";
        public final static String EI_SMS_BIZ_ERR = "90702";
        //EMAIL - 08
        public final static String EI_EMAIL_GENERAL_ERR = "90801";
        public final static String EI_EMAIL_BIZ_ERR = "90802";
        //POINT - 09
        public final static String EI_POINT_GENERAL_ERR = "90901";
        public final static String EI_POINT_BIZ_ERR = "90902";
        
        //CSP - 10
        public final static String EI_XOP_GENERAL_ERR = "91001";
        public final static String EI_XOP_BIZ_ERR = "91002";
        
        //TODO PROMOTION - 11  
        public final static String EI_PROMOTION_GENERAL_ERR = "91101";
        public final static String EI_PROMOTION_BIZ_ERR = "91102";
        
        //COMMENT - 10
        public final static String EI_COMMENT_GENERAL_ERR = "91201";
        //渠道
		public static final String EI_INV_GENERAL_ERR = "91301";
		public static final String EI_INV_BIZ_QSUK_ERR = "91302"; //inventoryService.inventoryQueryBySkuCodeAndChannelCode

        
        
    }
    //80开头的留给http status用.
    public static class HttpErrorCode{
    	
    	//httpsatus
    	public final static String HTTP_INTERNAL_SERVER_ERROR="80500";
    	//错误且没信息
    	public final static String HTTP_NOMSG_SERVER_ERROR="80510";
    	//错误且脚本报错
    	public final static String HTTP_SCRIPT_ERROR_SERVER_ERROR="80511";
    	//错误且没有数据返回
    	public final static String HTTP_NO_DATA_SERVER_ERROR="80512";
    	
    	// 路径不存在
    	public final static String HTTP_NOTFOUND_ERROR="80404";
    	// 错误请求
    	public final static String HTTP_BADREQUEST_ERROR="80400";
    	// 未实现的功能
    	public final static String HTTP_NOTIMPLEMENTED_ERROR="80501";
    	// 非应用异常，可能是服务器自身异常
    	public final static String HTTP_NOT_APP_ERROR="80500#001";
    	
		public final static  Map<String,String> errorCodeMap = new HashMap<String,String>();
    	static {
    		errorCodeMap.put("HTTP_INTERNAL_SERVER_ERROR", HTTP_INTERNAL_SERVER_ERROR);
    		errorCodeMap.put("HTTP_NOMSG_SERVER_ERROR", HTTP_NOMSG_SERVER_ERROR);
    		errorCodeMap.put("HTTP_SCRIPT_ERROR_SERVER_ERROR", HTTP_SCRIPT_ERROR_SERVER_ERROR);
    		errorCodeMap.put("HTTP_NO_DATA_SERVER_ERROR", HTTP_NO_DATA_SERVER_ERROR);
    	}
    }
}

