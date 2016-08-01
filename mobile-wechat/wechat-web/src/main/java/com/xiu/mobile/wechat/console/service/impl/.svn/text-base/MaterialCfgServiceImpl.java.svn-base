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

import com.xiu.mobile.wechat.console.dao.MaterialCfgDao;
import com.xiu.mobile.wechat.console.model.MaterialCfgVo;
import com.xiu.mobile.wechat.console.service.IMaterialCfgService;
import com.xiu.mobile.wechat.core.constants.Page;

/**
 * 
 * 素材配置 服务接口实现类
 * 
 * @author wangzhenjiang
 * 
 */
@Transactional
@Service("materialCfgService")
public class MaterialCfgServiceImpl implements IMaterialCfgService {
	private static final Logger logger = LoggerFactory.getLogger(MaterialCfgServiceImpl.class);

	@Resource
	private MaterialCfgDao materialCfgDao;

	@Override
	public List<MaterialCfgVo> getMaterialCfgList(Map<String, Object> params, Page<?> page) {
		if (params == null) {
			params = new HashMap<String, Object>(10);
		}
		List<MaterialCfgVo> lstMaterialCfg = new ArrayList<MaterialCfgVo>();
		try {
			int pageMax = page.getPageNo() * page.getPageSize();
			int pageMin = 1;
			if (page.getPageNo() != 1) {
				pageMin = (pageMax - (page.getPageNo() - 1) * page.getPageSize()) + 1;
			}
			params.put("pageMin", pageMin - 1);
			params.put("pageSize", page.getPageSize());
			params.put("pageMax", pageMax);

			lstMaterialCfg = materialCfgDao.getMaterialCfgListForPage(params);
			page.setTotalCount(lstMaterialCfg.size());
		} catch (Throwable e) {
			logger.error("查询素材配置列表失败：", e);
		}
		return lstMaterialCfg;
	}

	@Override
	public List<MaterialCfgVo> getMaterialCfgList(Map<String, Object> params) {
		if (params == null) {
			params = new HashMap<String, Object>(10);
		}
		List<MaterialCfgVo> lstMaterialCfg = new ArrayList<MaterialCfgVo>();
		try {
			lstMaterialCfg = materialCfgDao.getMaterialCfgList(params);
		} catch (Throwable e) {
			logger.error("查询素材配置列表失败：", e);
		}
		return lstMaterialCfg;
	}

	@Override
	public int saveMaterialCfg(MaterialCfgVo cfg) {
		int result = 1;
		try {
			result = materialCfgDao.saveMaterialCfg(cfg) > 0 ? 0 : 1;
		} catch (Throwable e) {
			result = -1;
			logger.error("保存素材配置异常:", e);
		}
		return result;
	}

	@Override
	public int deleteMaterialCfg(Long id) {
		int result = 1;
		try {
			result = materialCfgDao.deleteMaterialCfg(id) > 0 ? 0 : 1;
		} catch (Throwable e) {
			result = -1;
			logger.error("删除素材配置异常:", e);
		}
		return result;
	}

	@Override
	public int updateMaterialCfg(MaterialCfgVo cfg) {
		int result = 1;
		try {
			result = materialCfgDao.updateMaterialCfg(cfg) > 0 ? 0 : 1;
		} catch (Throwable e) {
			result = -1;
			logger.error("更新素材配置异常:", e);
		}
		return result;
	}

	@Override
	public MaterialCfgVo getMaterialCfg(Long id) {
		MaterialCfgVo objMaterialCfg = null;
		try {
			objMaterialCfg = materialCfgDao.getMaterialCfgById(id);
		} catch (Throwable e) {
			logger.error("根据ID查询素材配置异常:", e);
		}
		return objMaterialCfg;
	}

	@Override
	public int deleteMaterialCfgList(String[] ids) {
		int iCount = 0;
		for (String id : ids) {
			int result = this.deleteMaterialCfg(Long.valueOf(id));
			if (result == 0) {
				iCount++;
			}
		}
		return iCount;
	}

}
