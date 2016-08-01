package com.xiu.show.robot.common.contants;

import com.xiu.show.core.common.util.ObjectUtil;
import com.xiu.show.robot.common.utils.EncryptUtils;
import com.xiu.show.robot.common.utils.ShowConfigUtil;

/**
 *************************************************************** 
 * <p>
 * 
 * @DESCRIPTION :
 * @AUTHOR : xiu@xiu.com
 * @DATE :2012-4-7 下午12:09:35
 *       </p>
 **************************************************************** 
 */
public final class ShowRobotConstants {
	/**
	 * 秀定时程序开关 在字典中的类型
	 */
	public final static Integer SHOW_BRUSH_OPEN_STATUS_TYPE_SIGN = 4;
	/**
	 * 秀定时程序开关 在字典中的子类型
	 */
	public final static Integer SHOW_BRUSH_OPEN_STATUS_SUB_TYPE_SIGN = 10001;
	/**
	 * 秀定时刷数据的分钟数
	 */
	public final static Integer SHOW_BRUSH_DATA_MINUTE_NUM = ObjectUtil.getInteger(ShowConfigUtil.getValue("show.robot.brush.data.minute.num"), 0);
	/**
	 * 秀刷数据在真实数据后的最小生成分钟数
	 */
	public final static Integer SHOW_BRUSH_DATA_AFTER_MINUTE_NUM_MIN = ObjectUtil.getInteger(ShowConfigUtil.getValue("show.robot.brush.data.after.min.minute.num"), 1);
	/**
	 * 秀刷数据在真实数据后的最大生成分钟数
	 */
	public final static Integer SHOW_BRUSH_DATA_AFTER_MINUTE_NUM_MAX = ObjectUtil.getInteger(ShowConfigUtil.getValue("show.robot.brush.data.after.max.minute.num"), 15);
	/**
	 * 秀刷数据在真实数据后的最大生成分钟数(推荐)
	 */
	public final static Integer SHOW_BRUSH_DATA_AFTER_RECOMMEND_MINUTE_NUM_MAX = ObjectUtil.getInteger(ShowConfigUtil.getValue("show.robot.brush.data.after.recommend.max.minute.num"), 180);
	/**
	 * 秀定时刷数据的登录用户名
	 */
	public final static String SHOW_BRUSH_DATA_LOGIN_USER_NAME =ShowConfigUtil.getValue("show.robot.brush.data.login.user.name");
	/**
	 * 秀定时刷数据的登录用户密码
	 */
	private  static String SHOW_BRUSH_DATA_LOGIN_USER_PWD =null;
	 //自己加密用，用于orderId,addresId,bankAccountId等
	public static String MPORTAL_AES_KEY_SELF=ShowConfigUtil.getValue("mportal.aes.key.self");
	// 和app、wap共享用,用作密码加密
	public static String MPORTAL_AES_KEY_SHARE=ShowConfigUtil.getValue("mportal.aes.key.share");
	// 代支付加密key
	public static String MPORTAL_AES_KEY_PAYFOR_OTHERS = ShowConfigUtil.getValue("mportal.aes.key.payforothers");
	
	public static String  getRobotUserPwd(){
		if(SHOW_BRUSH_DATA_LOGIN_USER_PWD==null){
			String pwdstr=ShowConfigUtil.getValue("show.robot.brush.data.login.user.pwd");
			String key=EncryptUtils.getAESKeyShare();
			try {
				SHOW_BRUSH_DATA_LOGIN_USER_PWD=EncryptUtils.encryptPassByAES(pwdstr, key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return SHOW_BRUSH_DATA_LOGIN_USER_PWD;
	}
	
    
}
