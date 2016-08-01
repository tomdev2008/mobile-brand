package com.xiu.mobile.wechat.console.service;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.wechat.console.model.MessageCfgVo;
import com.xiu.mobile.wechat.core.constants.Page;

/**
 * 
 * 消息配置 服务接口
 * 
 * @author wangzhenjiang
 * 
 */
public interface IMessageCfgService {

	/**
	 * 根据参数查询消息配置分页列表
	 * 
	 * @param map
	 * @param page
	 * @return List<MessageCfg>
	 */
	List<MessageCfgVo> getMessageCfgList(Map<String, Object> params, Page<?> page);

	/**
	 * 根据参数查询消息配置列表
	 * 
	 * @param map
	 * @return List<MessageCfg>
	 */
	List<MessageCfgVo> getMessageCfgList(Map<String, Object> params);

	/**
	 * 根据关键词模糊查询消息配置集合
	 * 
	 * @param keyword
	 * @return
	 */
	List<MessageCfgVo> getMessageCfgList(String keyword);

	/**
	 * 保存消息配置
	 * 
	 * @param cfg
	 * @return 0:success 1:failed -1:error
	 */
	int saveMessageCfg(MessageCfgVo cfg);

	/**
	 * 根据ID删除消息配置
	 * 
	 * @param id
	 * @return 0:success 1:failed -1:error
	 */
	int deleteMessageCfg(Long id);

	/**
	 * 更新消息配置
	 * 
	 * @param cfg
	 * @return 0:success 1:failed -1:error
	 */
	int updateMessageCfg(MessageCfgVo cfg);

	/**
	 * 根据ID查询消息配置
	 * 
	 * @param id
	 * @return MessageCfg
	 */
	MessageCfgVo getMessageCfg(Long id);

	/**
	 * 根据ids 批量删除记录
	 * 
	 * @param ids
	 * @return 删除成功的条数 iCount
	 */
	int deleteMessageCfgList(String[] ids);

}
