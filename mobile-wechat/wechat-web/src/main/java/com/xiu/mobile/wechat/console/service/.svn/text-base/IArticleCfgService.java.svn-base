package com.xiu.mobile.wechat.console.service;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.wechat.console.model.ArticleCfgVo;
import com.xiu.mobile.wechat.core.constants.Page;

/**
 * 
 * 图文配置 服务接口
 * 
 * @author wangzhenjiang
 * 
 */
public interface IArticleCfgService {

	/**
	 * 根据参数查询图文配置分页列表
	 * 
	 * @param Map
	 *            <String, Object>
	 * @param page
	 * @return List<ArticleCfg>
	 */
	List<ArticleCfgVo> getArticleCfgList(Map<String, Object> params, Page<?> page);

	/**
	 * 根据参数查询图文配置集合
	 * 
	 * @param Map
	 *            <String, Object>
	 * @return List<ArticleCfg>
	 */
	List<ArticleCfgVo> getArticleCfgList(Map<String, Object> params);

	/**
	 * 保存图文配置
	 * 
	 * @param ArticleCfgVo
	 * @return 0:success 1:failed -1:error
	 */
	int saveArticleCfg(ArticleCfgVo cfg);

	/**
	 * 根据ID删除图文配置
	 * 
	 * @param id
	 * @return 0:success 1:failed -1:error
	 */
	int deleteArticleCfg(Long id);

	/**
	 * 更新图文配置
	 * 
	 * @param ArticleCfgVo
	 * @return 0:success 1:failed -1:error
	 */
	int updateArticleCfg(ArticleCfgVo cfg);

	/**
	 * 根据ID查询图文配置
	 * 
	 * @param id
	 * @return ArticleCfg
	 */
	ArticleCfgVo getArticleCfg(Long id);

	/**
	 * 根据ID批量删除图文配置
	 * 
	 * @param ids
	 * @return iCount
	 */
	int deleteArticleCfgList(String[] ids);

}
