package com.xiu.mobile.wechat.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiu.mobile.wechat.core.constants.Page;
import com.xiu.mobile.wechat.web.dao.BindingCfgDao;
import com.xiu.mobile.wechat.web.model.BindingCfgVo;
import com.xiu.mobile.wechat.web.service.IBindingCfgService;

/**
 * 
 * 配置 服务接口实现类
 * 
 * @author wangzhenjiang
 * 
 */
@Transactional
@Service("bindingCfgService")
public class BindingCfgServiceImpl implements IBindingCfgService {
	private static final Logger logger = LoggerFactory.getLogger(BindingCfgServiceImpl.class);

	@Resource
	private BindingCfgDao bindingCfgDao;

	@Override
	public List<BindingCfgVo> getBindingCfgList(Map<String, Object> params, Page<?> page) {
		if (params == null) {
			params = new HashMap<String, Object>(10);
		}
		List<BindingCfgVo> lstBindingCfg = new ArrayList<BindingCfgVo>();
		try {
			int pageMax = page.getPageNo() * page.getPageSize();
			int pageMin = 1;
			if (page.getPageNo() != 1) {
				pageMin = (pageMax - (page.getPageNo() - 1) * page.getPageSize()) + 1;
			}
			params.put("pageMin", pageMin - 1);
			params.put("pageSize", page.getPageSize());
			params.put("pageMax", pageMax);

			lstBindingCfg = bindingCfgDao.getBindingCfgListForPage(params);
			page.setTotalCount(lstBindingCfg.size());
		} catch (Throwable e) {
			logger.error("查询配置列表失败：", e);
		}
		return lstBindingCfg;
	}

	@Override
	public List<BindingCfgVo> getBindingCfgList(Map<String, Object> params) {
		if (params == null) {
			params = new HashMap<String, Object>();
		}
		List<BindingCfgVo> lstBindingCfg = null;
		try {

			lstBindingCfg = bindingCfgDao.getBindingCfgList(params);
		} catch (Throwable e) {
			logger.error("查询配置列表失败：", e);
		}
		return lstBindingCfg;
	}

	@Override
	public int saveBindingCfg(BindingCfgVo cfg) {
		return bindingCfgDao.saveBindingCfg(cfg);
	}

	@Override
	public int deleteBindingCfgById(Long id) {
		return bindingCfgDao.deleteBindingCfgById(id);
	}

	@Override
	public int deleteBindingCfgByOpenId(String openId) {
		return bindingCfgDao.deleteBindingCfgByOpenId(openId);
	}

	@Override
	public int deleteBindingCfgByUnionId(String unionId) {
		return bindingCfgDao.deleteBindingCfgByUnionId(unionId);
	}
	
	@Override
	public int deleteBindingCfgList(List<Long> ids) {
		int iCount = 0;
		for (Long id : ids) {
			int i = this.deleteBindingCfgById(id);
			iCount += i;
		}
		return iCount;
	}

	@Override
	public int updateBindingCfg(BindingCfgVo cfg) {
		return bindingCfgDao.updateBindingCfg(cfg);
	}

	@Override
	public BindingCfgVo getBindingCfg(Long id) {
		return bindingCfgDao.getBindingCfgById(id);
	}

	@Override
	public BindingCfgVo getBindingCfg(String openId) {
		return bindingCfgDao.getBindingCfgByOpenId(openId);
	}

	@Override
	public BindingCfgVo getBindingCfgByUnionId(String unionId) {
		return bindingCfgDao.getBindingCfgByUnionId(unionId);
	}
	
	@Override
	public List<BindingCfgVo> queryRepeatingData(int count) {
		return bindingCfgDao.queryRepeatingData(count);
	}

	@Override
	public int saveOrUpdateBindingCfg(BindingCfgVo bindingCfgVo) {
		String openId = bindingCfgVo.getOpenId();
		BindingCfgVo oldBindingCfgVo = this.getBindingCfg(openId);
		if (null != oldBindingCfgVo) {
			bindingCfgVo.setId(oldBindingCfgVo.getId());
			return this.updateBindingCfg(bindingCfgVo);
		} else {
			return this.saveBindingCfg(bindingCfgVo);
		}
	}

}
