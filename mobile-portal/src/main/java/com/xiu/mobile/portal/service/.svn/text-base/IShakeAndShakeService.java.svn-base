package com.xiu.mobile.portal.service;

import com.xiu.common.command.result.Result;
import com.xiu.mobile.portal.model.ShakeWinDesc;
import com.xiu.mobile.portal.model.ShakeWinInfo;
import com.xiu.mobile.sales.dointerface.vo.PageView;

import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName
 * @Description 
 * @author chenlinyu
 * @date 2014年12月3日 下午12:13:22
 */
public interface IShakeAndShakeService {

	/**
	 * 进入摇一摇页面
	 * @return
	 */
	public Map<String, Object> getUserCanShakeTime(String userId, String userName)throws Exception;
	
	/**
	 * 手机摇一摇
	 * @return
	 */
	public Result mobileShake(String userId, String userName)throws Exception;
	
	/**
	 * 查询其他用户中奖信息
	 * @return
	 * @throws Exception 
	 */
	public PageView<ShakeWinDesc> queryShakeWinList(Long userId, String currentPage, String pageSize) throws Exception;

	/**
	 * 获取再一次摇一摇机会
	 * @param userId
	 * @param userName
     * @return
     */
	public Result getAnotherRockChance(Long userId, String userName);
}
