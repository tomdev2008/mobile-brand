package com.xiu.mobile.wechat.web.service;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.wechat.core.constants.Page;
import com.xiu.mobile.wechat.web.model.FeedbackCfgVo;

/**
 * 
 * 维权 服务接口
 * 
 * @author wangzhenjiang
 * 
 */
public interface IFeedbackCfgService {

	/**
	 * 根据参数查询配置分页列表
	 * 
	 * @param map
	 * @param page
	 * @return List<FeedbackCfg>
	 */
	List<FeedbackCfgVo> getFeedbackCfgList(Map<String, Object> params, Page<?> page);

	/**
	 * 根据参数查询配置列表
	 * 
	 * @param map
	 * @return List<FeedbackCfg>
	 */
	List<FeedbackCfgVo> getFeedbackCfgList(Map<String, Object> params);

	/**
	 * 保存配置
	 * 
	 * @param cfg
	 * @return 0:success 1:failed -1:error
	 */
	int saveFeedbackCfg(FeedbackCfgVo cfg);

	/**
	 * 根据ID删除配置
	 * 
	 * @param id
	 * @return 0:success 1:failed -1:error
	 */
	int deleteFeedbackCfg(Long id);

	/**
	 * 更新配置
	 * 
	 * @param cfg
	 * @return 0:success 1:failed -1:error
	 */
	int updateFeedbackCfg(FeedbackCfgVo cfg);

	/**
	 * 根据ID查询配置
	 * 
	 * @param id
	 * @return FeedbackCfg
	 */
	FeedbackCfgVo getFeedbackCfg(Long id);

	/**
	 * 根据ids 批量删除记录
	 * 
	 * @param ids
	 * @return 删除成功的条数 iCount
	 */
	int deleteFeedbackCfgList(String[] ids);

	/**
	 * 将维权信息发送到客服系统
	 * 
	 * @param objFeedbackCfg
	 * @return 发送是否成功
	 */
	boolean sendToCustomSys(FeedbackCfgVo objFeedbackCfg) throws Exception;

	/**
	 * 更新配置
	 * 
	 * @param cfg
	 * @return 影响的数据记录
	 */
	int updateFeedbackCfgList(List<FeedbackCfgVo> lstVos);

}
