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

import com.xiu.mobile.wechat.console.dao.MenuCfgDao;
import com.xiu.mobile.wechat.console.model.MenuCfgVo;
import com.xiu.mobile.wechat.console.service.IMenuCfgService;
import com.xiu.mobile.wechat.core.constants.Page;

/**
 * 
 * 消息配置 服务接口实现类
 * 
 * @author wangzhenjiang
 * 
 */
@Transactional
@Service("menuCfgService")
public class MenuCfgServiceImpl implements IMenuCfgService {
	private static final Logger logger = LoggerFactory.getLogger(MenuCfgServiceImpl.class);

	@Resource
	private MenuCfgDao menuCfgDao;

	@Override
	public List<MenuCfgVo> getMenuCfgList(Map<String, Object> params, Page<?> page) {
		if (params == null) {
			params = new HashMap<String, Object>(10);
		}
		List<MenuCfgVo> lstMenuCfg = new ArrayList<MenuCfgVo>();
		try {
			int pageMax = page.getPageNo() * page.getPageSize();
			int pageMin = 1;
			if (page.getPageNo() != 1) {
				pageMin = (pageMax - (page.getPageNo() - 1) * page.getPageSize()) + 1;
			}
			params.put("pageMin", pageMin - 1);
			params.put("pageSize", page.getPageSize());
			params.put("pageMax", pageMax);

			lstMenuCfg = menuCfgDao.getMenuCfgListForPage(params);
			page.setTotalCount(lstMenuCfg.size());
		} catch (Throwable e) {
			logger.error("查询消息配置列表失败：", e);
		}
		return lstMenuCfg;
	}

	@Override
	public List<MenuCfgVo> getMenuCfgList(Map<String, Object> params) {
		if (params == null) {
			params = new HashMap<String, Object>(10);
		}
		List<MenuCfgVo> lstMenuCfg = new ArrayList<MenuCfgVo>();
		try {
			lstMenuCfg = menuCfgDao.getMenuCfgList(params);
		} catch (Throwable e) {
			logger.error("查询消息配置列表失败：", e);
		}
		return lstMenuCfg;
	}

	@Override
	public int saveMenuCfg(MenuCfgVo cfg) {
		int result = 1;
		try {
			result = menuCfgDao.saveMenuCfg(cfg) > 0 ? 0 : 1;
		} catch (Throwable e) {
			result = -1;
			logger.error("保存消息配置异常:", e);
		}
		return result;
	}

	@Override
	public int deleteMenuCfg(Long id) {
		int result = 1;
		try {
			result = menuCfgDao.deleteMenuCfg(id) > 0 ? 0 : 1;
		} catch (Throwable e) {
			result = -1;
			logger.error("删除消息配置异常:", e);
		}
		return result;
	}

	@Override
	public int deleteMenuCfgList(String[] ids) {
		int iCount = 0;
		for (String id : ids) {
			this.deleteMenuCfg(Long.valueOf(id));
			iCount++;
		}
		return iCount;
	}

	@Override
	public int updateMenuCfg(MenuCfgVo cfg) {
		int result = 1;
		try {
			result = menuCfgDao.updateMenuCfg(cfg) > 0 ? 0 : 1;
		} catch (Throwable e) {
			result = -1;
			logger.error("更新消息配置异常:", e);
		}
		return result;
	}

	@Override
	public MenuCfgVo getMenuCfg(Long id) {
		MenuCfgVo objMenuCfg = null;
		try {
			objMenuCfg = menuCfgDao.getMenuCfgById(id);
		} catch (Throwable e) {
			logger.error("根据ID查询消息配置异常:", e);
		}
		return objMenuCfg;
	}
}
