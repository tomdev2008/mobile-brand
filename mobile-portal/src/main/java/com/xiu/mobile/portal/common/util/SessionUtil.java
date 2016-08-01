package com.xiu.mobile.portal.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import com.xiu.mobile.portal.common.config.XiuInterface;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.facade.utils.HttpUtil;
import com.xiu.mobile.portal.model.DeviceVo;
import com.xiu.mobile.portal.model.LoginResVo;

public class SessionUtil {
	
	private final static Logger logger = Logger.getLogger(SessionUtil.class);
	/**
	 * 写写cookie用
	 */
	private static final String LOGIN_TOKENID = "xiu.login.tokenId";
	private static final String LOGIN_USERID = "xiu.login.userId";
	private static final String LOGIN_TYPE = "xiu.login.type";
	private static final String LOGIN_USERNAME = "xiu.login.userName";
	private static final String REGISTER_FROM = "register_from";
	private static final String PRAISE_DEVICE_ID = "praise.device.id";
	private static final String SHOPPING_CART = "shopping.cart";
	private static final String RAIDER_SHOPPING_CART = "raider.shopping.cart";


	/**
	 * 获取登录用户信息 包含tokenId addressList couponList
	 * 
	 * @Title: getLoginInfo
	 * @param request
	 * @return Map<String,Object>
	 * @author: jack.jia
	 * @date: 2013-5-6上午11:10:30
	 */
	public static LoginResVo getLoginInfo(HttpServletRequest request) {

		String token_id = getCookie(request, LOGIN_TOKENID);
		if (null == token_id || "".equals(token_id)) {
			return null;
		}

		LoginResVo lrv = new LoginResVo();
		lrv.setLogonName(getCookie(request, LOGIN_USERNAME));
		lrv.setUserId(getCookie(request, LOGIN_USERID));
		lrv.setTokenId(getCookie(request, LOGIN_TOKENID));

		return lrv;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public static LoginResVo getUser(HttpServletRequest request) {
		return getLoginInfo(request);
	}
	
	
	/**
	 * 
	 * @Title: getDeviceInfo
	 * @Description: 从session获取设备信息
	 * @param request
	 * @return
	 * @throws
	 * @author: Hualong
	 * @date: 2013-5-6下午04:13:08
	 */
	public static void setDeviceInfo(HttpServletRequest request, DeviceVo deviceVo) {
		if (null != deviceVo) {
			deviceVo.setAppVersion(XiuInterface.appVersion);
			deviceVo.setDeviceType(XiuInterface.deviceType);
			deviceVo.setImei(XiuInterface.imei);
			deviceVo.setMarketingFlag(XiuInterface.marketingFlag);
			// 客户端信息,只能得到deviceSn, iosVersion
			String ua = request.getHeader("User-Agent");
			String deviceSn = "unknow";// 设备号
			String iosVersion = "unknow";
			if (ua != null) {
				if (ua.indexOf("iPhone") > 0) {
					deviceSn = "iPhone";
					for (int v = 0; v < 10; v++) {// 取得iphone型号
						if (ua.indexOf("iPhone OS " + v + "_") >= 0) {
							iosVersion = String.valueOf(v);
							break;
						}
					}
				} else if (ua.indexOf("Android") > 0) {
					deviceSn = "Android";
				} else if (ua.indexOf("MSIE") > 0) {
					deviceSn = "winPhone";
				}
			}
			deviceVo.setDeviceSn(deviceSn);
			deviceVo.setIosVersion(iosVersion);
			deviceVo.setIp(HttpUtil.getRemoteIpAddr(request));
			deviceVo.setUserId(getUserId(request));
		}
	}

	/**
	 * 
	 * @Title: getDeviceInfo
	 * @Description: 从Post提交中获取设备信息
	 * @param request
	 * @return
	 * @throws
	 * @author: Hualong
	 * @date: 2013-5-6下午04:13:08
	 */
	public static void setDeviceInfoByPost(HttpServletRequest request, DeviceVo deviceVo) {
		if (null != deviceVo) {
			deviceVo.setAppVersion(request.getParameter("appVersion"));
			deviceVo.setDeviceSn(request.getParameter("deviceSn"));
			deviceVo.setDeviceType((request.getParameter("deviceType")));
			deviceVo.setImei(request.getParameter("imei"));
			deviceVo.setIosVersion(request.getParameter("iosVersion"));
			deviceVo.setMarketingFlag(request.getParameter("marketingFlag"));
		}
	}

	/**
	 * 得到要登录后跳转的路径
	 * 
	 * @Title: getWurl
	 * @return Object
	 * @author: chengyuanhuan
	 * @date: 2013-5-9上午10:26:19
	 */

	public static String getWurl(HttpServletRequest request) {
		return (String) request.getSession().getAttribute(XiuConstant.WURL);
	}

	/**
	 * 设置登录cookie
	 * @param response
	 * @param lrv
	 */
	public static void addLoginCookie(HttpServletResponse response, LoginResVo lrv) {
		CookieUtil.getInstance().addCookie(response, LOGIN_TOKENID, lrv.getTokenId());
		CookieUtil.getInstance().addCookie(response, LOGIN_USERID, lrv.getUserId());
		CookieUtil.getInstance().addCookie(response, LOGIN_USERNAME, lrv.getLogonName());
	}

	/**
	 * 清除登录cookie
	 * @param response
	 */
	public static void deleteLoginCookie(HttpServletResponse response) {
		CookieUtil.getInstance().deleteCookie(response, LOGIN_TOKENID);
		CookieUtil.getInstance().deleteCookie(response, LOGIN_USERID);
		CookieUtil.getInstance().deleteCookie(response, LOGIN_USERNAME);
	}

	/**
	 * 获取xiu.com的cookie
	 * @param request
	 * @param cookieKey
	 * @return
	 */
	public static String getCookie(HttpServletRequest request, String cookieKey) {
		return CookieUtil.getInstance().getCookieValue(request, ".xiu.com", cookieKey);
	}

	/**
	 * 获取tokenId
	 * @param request
	 * @return
	 */
	public static String getTokenId(HttpServletRequest request) {
		return CookieUtil.getInstance().getCookieValue(request, ".xiu.com", LOGIN_TOKENID);
	}

	/**
	 * 获取userId
	 * @param request
	 * @return
	 */
	public static String getUserId(HttpServletRequest request) {
		return CookieUtil.getInstance().getCookieValue(request, ".xiu.com", LOGIN_USERID);
	}

	/**
	 * 获取UserName
	 * @param request
	 * @return
	 */
	public static String getUserName(HttpServletRequest request) {
		return CookieUtil.getInstance().getCookieValue(request, ".xiu.com", LOGIN_USERNAME);
	}
	/**
	 * 获取推荐注册
	 * @param request
	 * @return
	 */
	public static String getRegisterFrom(HttpServletRequest request) {
		return CookieUtil.getInstance().getCookieValue(request, ".xiu.com", REGISTER_FROM);
	}
	
	
	/**
	 * 设置点赞cookie
	 * @param response
	 * @param deviceId
	 */
	public static void addGoodPraiseCookie(HttpServletResponse response, String deviceId) {
		CookieUtil.getInstance().addCookie(response, PRAISE_DEVICE_ID, deviceId);
	}
	
	/**
	 * 获取点赞设备Id
	 * @param request
	 * @return
	 */
	public static String getPraiseDeviceId(HttpServletRequest request) {
		return CookieUtil.getInstance().getCookieValue(request, ".xiu.com", PRAISE_DEVICE_ID);
	}
	/**
	 * 设置登录类型
	 * @param response
	 * @param deviceId
	 */
	public static void addLoginTypeCookie(HttpServletResponse response, String loginType) {
		CookieUtil.getInstance().addCookie(response, LOGIN_TYPE, loginType);
	}
	
	/**
	 * 获取登录类型
	 * @param request
	 * @return
	 */
	public static String getLoginTypeCookie(HttpServletRequest request) {
		return CookieUtil.getInstance().getCookieValue(request, ".xiu.com", LOGIN_TYPE);
	}
	
	/**
	 * 获取购物车里商品信息
	 * @param request
	 * @return
	 */
	public static String getShoppingCartGoodsList(HttpServletRequest request) {
		return CookieUtil.getInstance().getCookieValue(request, ".xiu.com", SHOPPING_CART);
	}
	
	/**
	 * 获取夺宝购物车里商品信息
	 * @param request
	 * @return
	 */
	public static String getRaiderShoppingCartGoodsList(HttpServletRequest request) {
		return CookieUtil.getInstance().getCookieValue(request, ".xiu.com", RAIDER_SHOPPING_CART);
	}
	
	/***
	 * 添加购物车里商品信息
	 * @param request
	 * @param response
	 * @param goodsJson
	 */
	public static int getShoppingCartGoodsQuantityBySku(HttpServletRequest request,String goodsSku) {
		int quantity = 0;
		try {
			JSONArray jsonArray = new JSONArray();
			String goodsStr = CookieUtil.getInstance().getCookieValue(request, ".xiu.com", SHOPPING_CART);
			if (StringUtils.isNotBlank(goodsStr)) {
				jsonArray = JSONArray.fromObject(goodsStr);
			}
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				if (jsonObject.getString("goodsSku").equals(goodsSku)) {
					quantity = jsonObject.getInt("quantity");
					break;
				}
			}
		} catch (Exception e) {
			logger.error("商品添加到购物车异常", e);
		}
		return quantity;
	}
	
	public static int getShoppingCartGoodsQuantityByGoodsSn(HttpServletRequest request,String goodsSn) {
		int quantity = 0;
		try {
			JSONArray jsonArray = new JSONArray();
			String goodsStr = CookieUtil.getInstance().getCookieValue(request, ".xiu.com", SHOPPING_CART);
			if (StringUtils.isNotBlank(goodsStr)) {
				jsonArray = JSONArray.fromObject(goodsStr);
			}
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				if (jsonObject.getString("goodsSn").equals(goodsSn)) {
					quantity = jsonObject.getInt("quantity");
				}
			}
		} catch (Exception e) {
			logger.error("商品添加到购物车异常", e);
		}
		return quantity;
	}
	
	/***
	 * 更新重置购物车商品信息
	 * @param request
	 * @param value
	 * @return
	 */
	public static void addShoppingCartGoods(HttpServletResponse response,String value) {
		CookieUtil.getInstance().addCookie(response, SHOPPING_CART, value);
	}
	
	/***
	 * 添加购物车里商品信息
	 * @param request
	 * @param response
	 * @param goodsJson
	 */
	public static boolean addShoppingCartGoods(HttpServletRequest request,HttpServletResponse response,JSONObject goodsJson) {
		try {
			boolean flag = false;
			JSONArray jsonArray = new JSONArray();
			String goodsStr = CookieUtil.getInstance().getCookieValue(request, ".xiu.com", SHOPPING_CART);
			if (StringUtils.isNotBlank(goodsStr)) {
				jsonArray = JSONArray.fromObject(goodsStr);
			}
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				if (jsonObject.getString("goodsSku").equals(goodsJson.getString("goodsSku"))) {
					int quantity = jsonObject.getInt("quantity");
					int number = NumberUtils.toInt(goodsJson.getString("quantity"), 1);
					int total = quantity + number;
					// 如果数量大于10 则重置为10
					if (total>10) {
						total = 10;
					}
					jsonObject.put("quantity", total);
					flag = true;
				}
			}
			if (!flag) {
				jsonArray.add(goodsJson);
			}
			CookieUtil.getInstance().addCookie(response, SHOPPING_CART, jsonArray.toString());
			return true;
		} catch (Exception e) {
			logger.error("商品添加到购物车异常", e);
			return false;
		}
	}
	
	/***
	 * 删除购物车里商品信息
	 * @param request
	 * @param response
	 * @param goodsJson
	 */
	public static boolean delShoppingCartGoods(HttpServletRequest request,HttpServletResponse response,String goodsSku) {
		try {
			JSONArray jsonArray = new JSONArray();
			String goodsStr = CookieUtil.getInstance().getCookieValue(request, ".xiu.com", SHOPPING_CART);
			if (StringUtils.isNotBlank(goodsStr)) {
				jsonArray = JSONArray.fromObject(goodsStr);
			}
			String[] goodsSkuArr = goodsSku.split(",");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				for (int j = 0; j < goodsSkuArr.length; j++) {
					String sku = goodsSkuArr[j];
					if (jsonObject.getString("goodsSku").equals(sku)) {
						jsonArray.remove(jsonObject);
					}
				}
			}
			CookieUtil.getInstance().addCookie(response, SHOPPING_CART, jsonArray.toString());
			return true;
		} catch (Exception e) {
			logger.error("购物车商品删除异常", e);
			return false;
		}
	}
	
	
	/***
	 * 清除购物车里cookie商品信息
	 * @param response
	 */
	public static void delShoppingCartGoods(HttpServletResponse response) {
		CookieUtil.getInstance().deleteCookie(response, SHOPPING_CART);
	}
	
	/***
	 * 清除购物车里cookie商品信息
	 * @param response
	 */
	public static void delRaiderShoppingCartGoods(HttpServletResponse response) {
		CookieUtil.getInstance().deleteCookie(response, RAIDER_SHOPPING_CART);
	}
}
