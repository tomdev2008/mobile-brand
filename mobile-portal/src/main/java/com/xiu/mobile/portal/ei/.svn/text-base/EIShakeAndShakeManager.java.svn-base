package com.xiu.mobile.portal.ei;

import com.xiu.common.command.result.Result;

import java.util.Map;

/**
 * 
 * @ClassName
 * @Description 
 * @author chenlinyu
 * @date 2014年12月3日 下午2:45:29
 */
public interface EIShakeAndShakeManager {

	/**
	 * 进入摇一摇页面
	 * @return
	 */
	public Map<String, Object> getUserCanShakeTime(Long userId, String userName);
	
	/**
	 * 手机摇一摇
	 * @return
	 */
	public Result mobileShake(Long userId,String userName);
	
	/**
	 * 查询用户获奖信息
	 * @return
	 */
	public Result queryShakeWinList(Long userId,String currentPage,String pageSize);

	/**
	 * 用户获取再一次摇一摇机会
	 * @param userId
	 * @param userName
     * @return
     */
	public Result getAnotherShakeChance(Long userId, String userName);
}
