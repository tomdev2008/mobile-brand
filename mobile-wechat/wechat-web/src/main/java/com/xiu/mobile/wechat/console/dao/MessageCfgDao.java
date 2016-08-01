package com.xiu.mobile.wechat.console.dao;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.wechat.console.model.MessageCfgVo;

/**
 * 微信消息配置DAO接口
 * 
 * @author wangzhenjiang
 * 
 */
public interface MessageCfgDao {
	/**
	 * 保存消息配置
	 * 
	 * @param cfg
	 * @return 1：success,0:failed
	 */
	int saveMessageCfg(MessageCfgVo cfg);

	/**
	 * 根据ID删除消息配置
	 * 
	 * @param id
	 * @return 1：success,0:failed
	 */
	int deleteMessageCfg(Long id);

	/**
	 * 更新消息配置
	 * 
	 * @param cfg
	 * @return 1：success,0:failed
	 */
	int updateMessageCfg(MessageCfgVo cfg);

	/**
	 * 根据ID 查询一条消息配置记录
	 * 
	 * @param id
	 * @return MessageCfg
	 */
	MessageCfgVo getMessageCfgById(Long id);

	/**
	 * 根据关键词模糊查询消息配置集合
	 * 
	 * @param keyword
	 * @return List<MessageCfg>
	 */
	List<MessageCfgVo> getMessageCfgByKeyword(String keyword);

	/**
	 * 根据参数查询消息配置集合
	 * 
	 * @param params
	 *            Map<String, Object>
	 * @return List<MessageCfg>
	 */
	List<MessageCfgVo> getMessageCfgList(Map<String, Object> params);

	/**
	 * 根据参数查询消息配置 分页列表
	 * 
	 * @param params
	 *            Map<String, Object>
	 * @return List<MessageCfg>
	 */
	List<MessageCfgVo> getMessageCfgListForPage(Map<String, Object> params);
}
