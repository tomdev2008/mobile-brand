package com.xiu.mobile.portal.dao;

import java.util.Map;

import com.xiu.mobile.portal.model.ShowUserInfoVo;

/**
 * 秀客Dao
 * @author coco.long
 * @time	2015-08-13
 */
public interface ShowUserDao {

	/**
	 * 获取秀客信息
	 * @param userId
	 * @return
	 */
	public ShowUserInfoVo getShowUserInfo(String userId);
	
	/**
	 * 获取秀客用户是否管理员
	 * @param params
	 * @return
	 */
	public Integer getManagerAuthority(Map params);
	
	//查询用户秀客社区的秀数量
	public int getUserShowCount(String userId);
	
}
