package com.xiu.mobile.wechat.console.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiu.mobile.wechat.console.dao.MessageCfgDao;
import com.xiu.mobile.wechat.console.model.MessageCfgVo;
import com.xiu.mobile.wechat.console.service.IMessageCfgService;
import com.xiu.mobile.wechat.core.constants.Page;

/**
 * 
 * 消息配置 服务接口实现类
 * 
 * @author wangzhenjiang
 * 
 */
@Transactional
@Service("messageCfgService")
public class MessageCfgServiceImpl implements IMessageCfgService {
	private static final Logger logger = LoggerFactory.getLogger(MessageCfgServiceImpl.class);

	@Resource
	private MessageCfgDao messageCfgDao;

	@Override
	public List<MessageCfgVo> getMessageCfgList(Map<String, Object> params, Page<?> page) {
		if (params == null) {
			params = new HashMap<String, Object>(10);
		}
		List<MessageCfgVo> lstMessageCfg = new ArrayList<MessageCfgVo>();
		try {
			int pageMax = page.getPageNo() * page.getPageSize();
			int pageMin = 1;
			if (page.getPageNo() != 1) {
				pageMin = (pageMax - (page.getPageNo() - 1) * page.getPageSize()) + 1;
			}
			params.put("pageMin", pageMin - 1);
			params.put("pageSize", page.getPageSize());
			params.put("pageMax", pageMax);

			lstMessageCfg = messageCfgDao.getMessageCfgListForPage(params);
			page.setTotalCount(lstMessageCfg.size());
		} catch (Throwable e) {
			logger.error("查询消息配置列表失败：", e);
		}
		return lstMessageCfg;
	}

	@Override
	public List<MessageCfgVo> getMessageCfgList(Map<String, Object> params) {
		if (params == null) {
			params = new HashMap<String, Object>(10);
		}
		List<MessageCfgVo> lstMessageCfg = null;
		try {

			lstMessageCfg = messageCfgDao.getMessageCfgList(params);
		} catch (Throwable e) {
			logger.error("查询消息配置列表失败：", e);
		}
		return lstMessageCfg;
	}

	@Override
	public int saveMessageCfg(MessageCfgVo cfg) {
		int result = 1;
		try {
			result = messageCfgDao.saveMessageCfg(cfg) > 0 ? 0 : 1;
		} catch (Throwable e) {
			result = -1;
			logger.error("保存消息配置异常:", e);
		}
		return result;
	}

	@Override
	public int deleteMessageCfg(Long id) {
		int result = 1;
		try {
			result = messageCfgDao.deleteMessageCfg(id) > 0 ? 0 : 1;
		} catch (Throwable e) {
			result = -1;
			logger.error("删除消息配置异常:", e);
		}
		return result;
	}

	@Override
	public int deleteMessageCfgList(String[] ids) {
		int iCount = 0;
		for (String id : ids) {
			this.deleteMessageCfg(Long.valueOf(id));
			iCount++;
		}
		return iCount;
	}

	@Override
	public int updateMessageCfg(MessageCfgVo cfg) {
		int result = 1;
		try {
			result = messageCfgDao.updateMessageCfg(cfg) > 0 ? 0 : 1;
		} catch (Throwable e) {
			result = -1;
			logger.error("更新消息配置异常:", e);
		}
		return result;
	}

	@Override
	public MessageCfgVo getMessageCfg(Long id) {
		MessageCfgVo objMessageCfg = null;
		try {
			objMessageCfg = messageCfgDao.getMessageCfgById(id);
		} catch (Throwable e) {
			logger.error("根据ID查询消息配置异常:", e);
		}
		return objMessageCfg;
	}

	@Override
	public List<MessageCfgVo> getMessageCfgList(String keyword) {
		List<MessageCfgVo> lstMessageCfg = null;
		try {
			lstMessageCfg = messageCfgDao.getMessageCfgByKeyword(keyword);
		} catch (Throwable e) {
			logger.error("根据 关键词 查询消息配置异常:", e);
		}
		return lstMessageCfg;
	}
}
