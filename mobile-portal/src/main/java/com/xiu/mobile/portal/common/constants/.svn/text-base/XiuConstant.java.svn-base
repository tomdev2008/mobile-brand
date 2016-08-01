/**  
 * @Project: xiu
 * @Title: XiuConstant.java
 * @Package org.lazicats.xiu.common.constant
 * @Description: TODO
 * @author: chengyuanhuan
 * @date 2013-5-6 上午10:42:46
 * @Copyright: BroadenGate Software Services Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.xiu.mobile.portal.common.constants;

import java.io.Serializable;

import com.xiu.mobile.portal.common.util.ConfigUtil;

/**
 * xiu平台
 * @ClassName: XiuConstant 
 * @Description: TODO
 * @author: chengyuanhuan
 * @date 2013-5-6 上午10:42:55
 *
 */
public class XiuConstant implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static String ID = "id";// id
	public static String TOKENID = "tokenId";// tokenId
	public static String LOGIN_INFO = "loginInfo";// 登录人的一些信息，包含tokenId addressList couponList
	public static String USER_ID = "userId"; // 登录后的userId
	public static String USER_NAME = "userName"; // 登录后的userName
	public static String WURL = "wurl";// 页面url，用来记录历史浏览
	public static String ACTIVITY_MAP = "activityParamsMap";// 记录页面频道活动页面传来的参数，防止数据丢失
	public static String STATUS_INFO = "status_info";
	public static String RESULT_STATUS = "result_status";
	public static String PAGE_SIZE = "pageSize";
	public static String PAGE_NUM = "pageNum";
	public static String TOTAL_COUNT = "totalCount";
	public static String TOTAL_PAGE = "totalPage";

	public static String RETCODE_SUCCESS = "000";// 成功返回码

	public static String SAFE_CODE = ConfigUtil.getValue("safe_code");// 密钥 xiu 提供

	public static String G1_IMG = "/g1_150_150.jpg";// SKU 生成图片

	public static String XIU_INTERFACE_PREFIX = ConfigUtil.getValue("interface_url");// 接口URL前缀

	/**支付成功回调地址（旧版）*/
	public static String CALLBACK_URL_SUFFIX = "/order/paySuccess.shtml";

	/**支付成功回调地址*/
	public static String PAY_SUCCESS_CALLBACK_URL = ConfigUtil.getValue("pay.success.callback.url");
	/**支付成功上传身份证回调地址*/
	public static String PAY_SUCCESS_CALLBACK_UPLOAD_IDCARD_URL= ConfigUtil.getValue("pay.success.callback.upload.idcard.url");
	/**代支付成功回调地址*/
	public static String PAY_SUCCESS_CALLBACK_FOR_OTHER_URL= ConfigUtil.getValue("pay.success.callback.for.other.url");
	/**支付中心URL*/
	public static String REMOTE_URL_PAY = ConfigUtil.getValue("remote.url.pay");

	/**支付中心定义无线代码*/
	public static String PAY_MERCHANT_ID = ConfigUtil.getValue("pay.merchant.id");

	public static String DEVICE_SESSION = "device_info_session";// 设备信息在httpsession的缓存名字
	public static String BRANDURL = "http://list.xiu.com/";
	public static String BRANDURL1 = "http://list.xiu.com/";
	public static String BRANDURL2 = "http://brand.xiu.com/";
	public static String BRANDURL3 = "http://search.xiu.com/";
	public static String BRANDURL4 = "http://www.xiu.com/cx/";

	/**
	 * 订单媒体
	*/
	public static final String MEDIA_URL = "http://media.xiu.com/zs_media_from.php?act=query&user_id=";
	
	/**
	 * 用户等级说明页面
	 */
	public static final String USER_LEVEL_INFO_URL="http://m.xiu.com/fopageApp/LvState.html";

	public static String[] MOBILE_IMAGE = { "http://6.xiustatic.com", "http://7.xiustatic.com", "http://8.xiustatic.com"};
	public static int[] XIUSTATIC_NUMS = {6,7,8};

	// topic 首页每次条数
	public static int TOPIC_PAGE_SIZE = 20;
	
	//条数为10
	public static int PAGE_SIZE_10 = 10;
	
	//条数为20
	public static int PAGE_SIZE_20 = 20;
	
	//条数为40
	public static int PAGE_SIZE_40 = 40;

	// 订单每页数量
	public static int ORDER_PAGE_SIZE = 20;
	
	//hession批量请求最大数量
	public static final int HESSION_BATCH_MAX_NUMBER = 30;
	
	//.*表示其下所有均校验登录状态 如匹配winInternationalBrand*
	public static final String MOBILE_FILTER_URI = "winInternationalBrand.*,address,app,browseGoods.*,changePassword,"
			+ "coupon.*,favorBrand.*,favor,goods,cpsCommisson.*,myorder.*,order.*,payForOthers,goodsRefund.*,shakeAndShake.*,"
			+ "user.*,withDraw.*,giftcps.*,askBuy.*,subject,raiderIndex,raiderOrder.*,integral.*,raiderShoppingCat.*,credit.*,message.*";
	
	//Uri详细字符串
	//不满足以上的要求的其他过滤
	public static final String MOBILE_FILTER_URI_DETAIL = "app.rewardFirstLogin,changePassword.modify.password,goods.loadGoodsAndAddressInfo,"
			 + "goods.addAskBuyInfo,payForOthers.getPayForOthersURL,payForOthers.getPayForOthersURLAndTemplets,payForOthers.getPayForTemplets,"
			+ "address.initAddAddressRemote,address.saveAddressRemote,address.updateAddressRemote,address.getAddressDetailRemote,"
			+ "address.delAddressRemote,address.getAddressParamRemote,address.getAddressListRemote,address.setMasterAddressRemote,"
			+ "favor.addFavorGoods,favor.hasExistsFavorGoods,favor.getFavorGoodsList,favor.delFavorGoods,subject.addSubjectComment,subject.deleteSubjectComment,"
			+"raiderIndex.getMyRaidersIndex";	
	
	// 根据商品查询商品下的评论数量接口
	public static final String GET_COMMENTNUM_BY_PROD = ConfigUtil.getValue("remote.url.comment.countOfOneGoods");
	// 查询商品评论列表
	public static final String GET_COMMENT_BY_PROD = ConfigUtil.getValue("remote.url.comment.listOfOneGoods");
	// 根据商品ID查询商品的分类尺码信息
	public static final String GET_GOODS_CATEGORYSIZE_BY_GOODS = ConfigUtil.getValue("remote.url.mbrand.catelogsize");
	//图片上传地址
	public static final String MOBILE_PICTURE_UPLOADPATH = ConfigUtil.getValue("mobile.picture.uploadpath");
	//求购图片上传地址
	public static final String ASKBUY_PICTURE_UPLOADPATH = ConfigUtil.getValue("askbuy.picture.uploadpath");
	
	/**####缓存Key####*/
	/**所有省市区参数*/
	public static final String MOBILE_MEMCACHED_ALL_REGIONPARAM = "mportal.allregionparam";
	/**全部品牌参数*/
	public static final String BRAND_ALL="mportal.allbrand";
	/****卖场商品***/
	/**卖场*/
	public static final String MOBILE_TOPIC = "mportal.topic";
	/**卖场下的分类尺码信息*/
	public static final String MOBILE_TOPIC_CATEGORY = "catToSize";
	/**卖场下商品的分类、尺码*/
	public static final String MOBILE_TOPIC_GOODS = "catAndSizeOfGoods";
	
	/**发票类型缓存key*/
	public static final String MOBILE_INVOICE_TYPES_CACHE_KEY = "mportal.invoice.types";
	
	/**登录用户数据缓存key*/
	public static final String USER_DETAIL_CACHE_KEY = "user.detail.cache.key";
	
	/**商品数据缓存key*/
	public static final String CACHE_PRODUCT_CACHE_KEY = "cache.product.cache.key";
	
	/**
	 * 登录类型-账号登录
	 */
	public static final String LOGIN_TYPE_ACCOUNT = "account";
	/**
	 * 登录类型-第三方登录
	 */
	public static final String LOGIN_TYPE_FEDERATE = "federate";
	
	/***#######app内部跳转地址############ */
	public static final String[] XIU_APP_URL={
		"xiuApp://xiu.app.topicdetail/openwith?showTopicId=",//2, 话题  
		"xiuApp://xiu.app.showdetail/openwith?showid=",//3, 秀
		"xiuApp://xiu.app.topicgoodslist/openwith?id=",//4, 卖场  
		"xiuApp://xiu.app.topicgoodslist/openwith?id=",//5, 卖场集  
		"xiuApp://xiu.app.goodsdetail/openwith?id=",//6, 商品
		"xiuApp://xiu.app.mbrand.catgoodslist/openwith?id=",//7, 商品分类
		"xiuApp://xiu.app.subjectdetail/openwith?subjectId=",//8,专题
		"xiuApp://xiu.app.brandhomepage/openwith?brandId=",//9,品牌
		"xiuApp://xiu.app.querylabelshowlist/openwith?labelid=",//10,秀标签
		"xiuApp://xiu.app.queryshowcolletionlist/openwith?showcolletionid="//11,秀集合
	};
	public static final int[] XIU_APP_URL_TYPE={2,3,4,5,6,7,8,9,10,11};
	
	
	/**########评论功能#########################*/
	/*** 评论id api*/
	public static final String COMMENT_API_ID = "commentId";
	/*** 专题id api*/
	public static final String SUBJECT_ID = "subjectId";
	/*** 评论论id*/
	public static final String COMMENT_ID = "id";
	/*** 评论内容*/
	public static final String COMMENT_INFO = "content";
	/*** 被评论用户id 接口参数*/
	public static final String COMMENTED_API_TO_USER_INFO = "toUserId";
	/*** 被评论用户id*/
	public static final String COMMENTED_USER_INFO = "commentedUserId";
	/**  评论类型*/
	public static final String COMMENT_TYPE= "commentType";
	
	/**######常量######*/
	/***校验常量密串*/
	public static final String VERIFY_ENCRYPT_CONSTANT = "xiu!go!on@fighting##";
	/***校验常量编码*/
	public static final String VERIFY_ENCRYPT_CODE = "verifyEncryptCode";
	/**更多专题URL*/
	public static final String MORE_SUBJECT_URL = ConfigUtil.getValue("subject.more.url");
	/**标签专题列表URL*/
	public static final String SUBJECT_TAG_LIST_URL = ConfigUtil.getValue("subject.tag.list.url");
	
}
