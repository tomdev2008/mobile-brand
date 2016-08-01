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

import com.xiu.mobile.wechat.console.dao.ArticleCfgDao;
import com.xiu.mobile.wechat.console.model.ArticleCfgVo;
import com.xiu.mobile.wechat.console.service.IArticleCfgService;
import com.xiu.mobile.wechat.core.constants.Page;

/**
 * 
 * 图文配置 服务接口实现类
 * 
 * @author wangzhenjiang
 * 
 */
@Transactional
@Service("articleCfgService")
public class ArticleCfgServiceImpl implements IArticleCfgService {
	private static final Logger logger = LoggerFactory.getLogger(ArticleCfgServiceImpl.class);

	@Resource
	private ArticleCfgDao articleCfgDao;

	@Override
	public List<ArticleCfgVo> getArticleCfgList(Map<String, Object> params, Page<?> page) {
		if (params == null) {
			params = new HashMap<String, Object>(10);
		}
		List<ArticleCfgVo> lstArticleCfg = new ArrayList<ArticleCfgVo>();
		try {
			int pageMax = page.getPageNo() * page.getPageSize();
			int pageMin = 1;
			if (page.getPageNo() != 1) {
				pageMin = (pageMax - (page.getPageNo() - 1) * page.getPageSize()) + 1;
			}
			params.put("pageMin", pageMin - 1);
			params.put("pageSize", page.getPageSize());
			params.put("pageMax", pageMax);

			lstArticleCfg = articleCfgDao.getArticleCfgListForPage(params);
			page.setTotalCount(lstArticleCfg.size());
		} catch (Throwable e) {
			logger.error("查询图文配置列表失败：", e);
		}
		return lstArticleCfg;
	}

	@Override
	public List<ArticleCfgVo> getArticleCfgList(Map<String, Object> params) {
		if (params == null) {
			params = new HashMap<String, Object>(10);
		}
		List<ArticleCfgVo> lstArticleCfg = new ArrayList<ArticleCfgVo>(10);
		try {
			lstArticleCfg = articleCfgDao.getArticleCfgList(params);
		} catch (Throwable e) {
			logger.error("根据ID查询图文配置异常:", e);
		}
		return lstArticleCfg;
	}

	@Override
	public int saveArticleCfg(ArticleCfgVo cfg) {
		int result = 1;
		try {
			result = articleCfgDao.saveArticleCfg(cfg) > 0 ? 0 : 1;
		} catch (Throwable e) {
			result = -1;
			logger.error("保存图文配置异常:", e);
		}
		return result;
	}

	@Override
	public int deleteArticleCfg(Long id) {
		int result = 1;
		try {
			result = articleCfgDao.deleteArticleCfg(id) > 0 ? 0 : 1;
		} catch (Throwable e) {
			result = -1;
			logger.error("删除图文配置异常:", e);
		}
		return result;
	}

	@Override
	public int updateArticleCfg(ArticleCfgVo cfg) {
		int result = 1;
		try {
			result = articleCfgDao.updateArticleCfg(cfg) > 0 ? 0 : 1;
		} catch (Throwable e) {
			result = -1;
			logger.error("更新图文配置异常:", e);
		}
		return result;
	}

	@Override
	public ArticleCfgVo getArticleCfg(Long id) {
		ArticleCfgVo objArticleCfg = null;
		try {
			objArticleCfg = articleCfgDao.getArticleCfgById(id);
		} catch (Throwable e) {
			logger.error("根据ID查询图文配置异常:", e);
		}
		return objArticleCfg;
	}

	@Override
	public int deleteArticleCfgList(String[] ids) {
		int iCount = 0;
		for (String id : ids) {
			this.deleteArticleCfg(Long.valueOf(id));
			iCount++;
		}
		return iCount;
	}
}
