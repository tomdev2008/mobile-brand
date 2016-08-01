package com.xiu.mobile.portal.ei;

import java.util.Map;

import com.xiu.maker.dointerface.dto.MakerUserDTO;
import com.xiu.maker.dointerface.dto.QueryUserDetailDTO;


public interface EIXiuMakerService {

	/**
	 * 根据创客id获取该创客所有有效资源
	 * @param xiuUserId
	 * @return
	 */
	public Map<String, Object> queryMakerResourceByUserId(Long xiuUserId);
	
	/**
	 * 获取创客信息
	 * @param xiuUserId
	 * @param resourceId
	 * @return
	 */
	public Map<String, Object> queryMakerUserInfo(Long xiuUserId); 
	/**
	 * 校验链接的有效性
	 * @param seriaNum
	 * @return
	 */
	public Map<String, Object> validSpreadUrl(String serialNum);
	
	/**
	 * 推广用户
	 * @param userResourceId
	 * @param seriaNum
	 * @param channel
	 * @param mobile
	 * @return
	 */
	public Map<String, Object> spreadUser(String serialNum, String channel, String mobile);
	
	/**
	 * 创客中心
	 * @param makerUserId
	 * @return
	 */
	public Map<String,Object> makerCenter(Long makerUserId);

	/**
	 * 会员明细
	 * @param queryDTO
	 * @return
	 */
	public Map<String, Object> querySpreadUserLst(QueryUserDetailDTO queryDTO);

	/**
	 * 会员详情
	 * @param parseLong
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> querySpreadUserDetail(long makerUserId, 
			long parseLong, String xiuIdentity, int pageNo, int pageSize);

	/**
	 * 收益明细列表
	 * @param makerUserId
	 * @param sortParameter
	 * @param sortRule
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> queryIncomeLst(Long makerUserId, String source,
			String startTime, String endTime, String sortParameter,
			String sortRule, int pageNo, int pageSize);

	/**
	 * 注册创客
	 * @param makerUserDTO
	 */
	public Map<String, Object> joinMaker(MakerUserDTO makerUserDTO);
	
	public Map<String, Object> inviteUser(long MakerUserId);

	/**
	 * 查询创客福利
	 * @param parseLong
	 * @return
	 */
	public Map<String, Object> queryMakerIncomeWelfare(long parseLong);
	
}
