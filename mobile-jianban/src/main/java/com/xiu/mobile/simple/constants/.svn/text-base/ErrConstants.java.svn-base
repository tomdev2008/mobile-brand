package com.xiu.mobile.simple.constants;

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
        
        public final static String BIZ_ORDER_GOODS_EMPTY = "30001"; // 创建订单时商品为空
        public final static String BIZ_ORDER_GOODS_SN_EMPTY = "30002";   // 订单信息里商品goodsSN为空
        public final static String BIZ_ORDER_GOODS_SKU_EMPTY = "30003";  // 订单信息里商品sku为空
        public final static String BIZ_ORDER_GOODS_PAY_TYPE_ERROR = "30004"; // 订单信息里商品支付方式有误
        public final static String Biz_ORDER_ADDRESS_EMPTY = "30005";    // 收货地址为空
        public final static String BIZ_ORDER_GOODS_SOLD_OUT = "30006";   // 商品已售罄
        public final static String BIZ_ORDER_GOODS_STOCK_OUT = "30007";  // 商品库存不足
        public final static String BIZ_ORDER_CARD_EMPTY = "30008";       // 优惠券为空
        
        public final static String BIZ_PAY_CENTER_BIZ_ERR = "40001";     // 调用支付中心异常
        
        public final static String BIZ_RET_CODE_ERROR = "20005"; //密码解密错误
        public final static String BIZ_CODE_NO_GOODS_SN = "20006"; //商品SN不存在
    }
    //EI的错误编码
    // 9AABB
    // AA: 00 - PORTAL, 01 - Trade, 02 - PUC, 03 - UUC, 04 - CSP, 05 - SSO, 06 - RM, 07 - SMS, 08 - EMAIL
    // BB: 00 ~ 19 系统预留，20以上用于自定义
    //     01 - General Error, 02 - Biz Error
    //80开头的留给http status用.
    public class EIErrorCode {
        //TRADE - 01
        public final static String EI_TRADE_GENERAL_ERR = "90101";
        public final static String EI_TRADE_BIZ_ERR = "90102";      
        //PUC - 02
        public final static String EI_PUC_GENERAL_ERR = "90201";
        public final static String EI_PUC_BIZ_ERR = "90202";        
        //UUC - 03
        public final static String EI_UUC_GENERAL_ERR = "90301";
        public final static String EI_UUC_BIZ_ERR = "90302";                      // 获取用户信息异常
        public final static String EI_UUC_USER_INFO_ERR = "90303";                // 获取用户信息失败
        public final static String EI_UUC_ADDRESS_BIZ_ERR = "90304";              // 获取收货地址异常
        public final static String EI_UUC_ADDRESS_INFO_ERR = "90305";             // 获取收货地址失败
        public final static String EI_UUC_ADDRESS_ADD_BIZ_ERR = "90306";          // 添加收货地址异常
        public final static String EI_UUC_ADDRESS_ADD_FAILED_ERR = "90307";       // 添加收货地址失败
        public final static String EI_UUC_ADDRESS_MODIFY_BIZ_ERR = "90308";       // 修改收货地址异常
        public final static String EI_UUC_ADDRESS_MODIFY_FAILED_ERR = "90309";    // 修改收货地址失败
        public final static String EI_UUC_ADDRESS_DELETE_BIZ_ERR = "90310";       // 删除收货地址异常
        public final static String EI_UUC_ADDRESS_DELETE_FAILED_ERR = "90311";    // 删除收货地址失败
        public final static String EI_UUC_ADDRESS_LIST_BIZ_ERR = "90312";         // 获取用户收货地址列表异常
        public final static String EI_UUC_ADDRESS_LIST_FAILED_ERR = "90313";      // 获取用户收货地址列表失败
        
        public final static String EI_UUC_BIZ_REU_ERR = "90314"; // UserManageFacade.registerUser
        public final static String EI_UUC_BIZ_ILNE_ERR = "90315"; // UserManageFacade.isLogonNameExist
        //CSP - 04
        public final static String EI_CSP_GENERAL_ERR = "90401";
        public final static String EI_CSP_BIZ_ERR = "90402";
        public final static String EI_CSP_PRO_CITY_REGION_BIZ_ERR = "90403";      // 获取省市区异常
        public final static String EI_CSP_PRO_CITY_REGION_FAILED_ERR = "90404";   // 获取省市区失败
        //SSO - 05
        public final static String EI_SSO_GENERAL_ERR = "90501";
        public final static String EI_SSO_BIZ_ERR = "90502";
        public final static String EI_SSO_BIZ_LOGIN_ERR = "90503"; // SsoServer.login
        public final static String EI_SSO_BIZ_COS_ERR = "90504"; // SsoServer.checkOnlineState
        public final static String EI_SSO_BIZ_LOGOUT_ERR = "90504"; // SsoServer.logOut
        public final static String EI_SSO_BIZ_PROXYLOGIN_ERR = "90505"; // SsoServer.proxyLogin
        //RM - 06
        public final static String EI_RM_GENERAL_ERR = "90601";
        public final static String EI_RM_BIZ_ERR = "90602";
        
        //SMS - 07
        public final static String EI_SMS_GENERAL_ERR = "90701";
        public final static String EI_SMS_BIZ_ERR = "90702";
        public final static String EI_SMS_BIZ_SSM_ERR = "90703";  // ISMSHessianService.sendSimpleMessage
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
        public final static String EI_PROMOTION_BIZ_SCD_ERR = "91103"; // SendCardIntegService.sendCardByDeed
        public final static String EI_PROMOTION_BIZ_ISFXR_ERR = "91104" ;// itemSettleService.itemSettleForXmlRpc
        public final static String EI_PROMOTION_BIZ_ILSS_ERR = "91105"; // itemListSettleService.itemListSettleSrevice
        public final static String EI_PROMOTION_BIZ_ACB_ERR = "91106"; // cardService.activeCardBody
        public final static String EI_PROMOTION_BIZ_UCL_ERR = "91107"; // cardService.userCardList
        
        //COMMENT - 10
        public final static String EI_COMMENT_GENERAL_ERR = "91201";
        //渠道
		public static final String EI_INV_GENERAL_ERR = "91301";
		public static final String EI_INV_BIZ_QSUK_ERR = "91302"; //inventoryService.inventoryQueryBySkuCodeAndChannelCode
        
		//Search
        public static final String EI_SEARCH_GENERAL_ERR = "91401";
        
        // TC
        public static final String EI_TC_ORDER_CREATE_ERR = "91501";            // 创建订单异常
        public static final String EI_TC_ORDER_CREATE_BIZ_FAILED_ERR = "91502"; //创建订单失败
        public static final String EI_TC_ORDER_COUNT_BIZ_ERR = "91503";         // 查询用户订单数异常
        public static final String EI_TC_ORDER_BIZ_ERR = "91504";               // 查询用户订单信息异常
        public static final String EI_TC_ORDER_FAILED_ERR = "91505";            // 查询用户订单信息失败
        public static final String EI_TC_ORDER_LIST_BIZ_ERR = "91506";          // 查询用户订单列表异常
        public static final String EI_TC_ORDER_LIST_FAILED_ERR = "91507";       // 查询用户订单列表异常
        public static final String EI_TC_ORDER_CANCEL_BIZ_ERR = "91508";        // 撤销订单异常
        public static final String EI_TC_ORDER_CANCEL_FAILED_ERR = "91509";     // 撤销订单失败
        public static final String EI_TC_ORDER_UPDATE_PAY_TYPE_BIZ_ERR = "91510";//修改支付方式异常
        public static final String EI_TC_ORDER_UPDATE_PAY_TYPE_FAILED_ERR = "91511";//修改支付方式失败
        
        // SALES
        public static final String EI_SALES_CARD_VALIDATE_BIZ_ERR = "91601"; // 验证优惠券异常
        public static final String EI_SALES_CARD_VALIDATE_FAILED_ERR = "91602"; // 验证优惠券失败
        public static final String EI_SALES_BIZ_ERR = "91603";               // sales异常
        public static final String EI_SALES_FAILED_ERR = "91604";            // sales失败
        public static final String EI_SALES_BLACK_ERR = "91605";            // 获取促销系统黑名单失败
        //PROD商品中心
        public static final String EI_PROD_GENERAL_ERR = "91701";
        public static final String EI_PROD_BIZ_ERR = "91702";
        public static final String EI_PROD_GSA_BIZ_ERR = "91703"; // goodsCenterService.getSkuAttr
        public static final String EI_PROD_GPL_BIZ_ERR = "91704"; // goodsCenterService.getProductLight
        
        // IMAGE组图选择
        public static final String EI_IMAGE_GENERAL_ERR = "91801";
        public static final String EI_IMAGE_BIZ_CHECKIMAGE_ERR = "91702"; // oriImageCheckHessianService.checkImageExists
        
        
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

