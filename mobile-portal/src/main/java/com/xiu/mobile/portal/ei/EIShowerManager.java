package com.xiu.mobile.portal.ei;

import java.util.Map;


/** 
 * 
* @Description: TODO(秀客相关接口) 
* @author haidong.luo@xiu.com
* @date 2015年11月18日 下午4:13:22 
*
 */
public interface EIShowerManager {

	/**
	 * 禁止评论
	 * @param params
	 * @return
	 */
	public Map<String, Object> updateShowerForbidComment(Map params);
	
	/**
	 * 检查秀客管理员权限
	 * @param params
	 * @return
	 */
	public Boolean checkShowerCommentAuthority(Map params);
	
}
