package com.xiu.mobile.wechat.web.dao;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.wechat.web.model.FeedbackCfgVo;

/**
 * 维权记录配置DAO接口
 * 
 * @author wangzhenjiang
 * 
 */
public interface FeedbackCfgDao {
	/**
	 * 保存配置
	 * 
	 * @param FeedbackCfgVo
	 * @return 1：success,0:failed
	 */
	int saveFeedbackCfg(FeedbackCfgVo cfg);

	/**
	 * 根据ID删除配置
	 * 
	 * @param id
	 * @return 1：success,0:failed
	 */
	int deleteFeedbackCfg(Long id);

	/**
	 * 更新配置
	 * 
	 * @param cfg
	 * @return 1：success,0:failed
	 */
	int updateFeedbackCfg(FeedbackCfgVo cfg);

	/**
	 * 根据ID 查询一条配置记录
	 * 
	 * @param id
	 * @return FeedbackCfg
	 */
	FeedbackCfgVo getFeedbackCfgById(Long id);
	
	/**
	 * 根据参数查询配置集合
	 * 
	 * @param params
	 *            Map<String, Object>
	 * @return List<FeedbackCfg>
	 */
	List<FeedbackCfgVo> getFeedbackCfgList(Map<String, Object> params);

	/**
	 * 根据参数查询配置 分页列表
	 * 
	 * @param params
	 *            Map<String, Object>
	 * @return List<FeedbackCfg>
	 */
	List<FeedbackCfgVo> getFeedbackCfgListForPage(Map<String, Object> params);
}
