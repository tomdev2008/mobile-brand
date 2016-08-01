package com.xiu.mobile.portal.ei;

import com.xiu.common.command.result.Result;

/**
 * @ClassName: EICPSManager
 * @Description: CPS管理
 * @author 贾泽伟
 * @date 2014-7-8 11:21:05
 * 
 */
public interface EICPSManager {
	
	/**
	 * 根据参数值解密S
	 * @param userId
	 * @return
	 */
	public Result queryCookiesInfo(String xiukcps, String cpscode);
	
}
