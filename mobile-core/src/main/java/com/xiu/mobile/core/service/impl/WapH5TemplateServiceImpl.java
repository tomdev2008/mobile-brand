package com.xiu.mobile.core.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.xiu.mobile.core.dao.WapH5TemplateDao;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.model.WapH5List;
import com.xiu.mobile.core.model.WapH5PageTemplate;
import com.xiu.mobile.core.model.WapH5Template;
import com.xiu.mobile.core.service.IWapH5TemplateService;
import com.xiu.mobile.core.utils.ImageUtil;


@Transactional
@Service("wapH5TemplateService")
public class WapH5TemplateServiceImpl implements IWapH5TemplateService {

	@Autowired
	private WapH5TemplateDao wapH5TemplateDao;

	@Override
	public void saveOrUpdateH5Template(WapH5Template template) {
		//更新
		if(template != null && template.getId() != null){
			wapH5TemplateDao.updateH5Template(template);
		}else{//新增
			wapH5TemplateDao.saveH5Template(template);
		}
	}

	@Override
	public WapH5Template queryWapH5TemplateById(long id) {
		return wapH5TemplateDao.queryWapH5TemplateById(id);
	}

	@Override
	public Map<String, Object> queryWapH5TemplateListDesc(Map<String, Object> params,Page<?> page) {
		Map<String,Object> resultMap=new HashMap<String,Object>();//结果集
		List<WapH5Template> h5list=new ArrayList<WapH5Template>();
		Boolean resultStatus=false; //是否成功的状态
		params.put("pageMin", page.getFirstRecord());
		params.put("pageSize", page.getPageSize());
		params.put("pageMax", page.getEndRecord());
		h5list=wapH5TemplateDao.queryWapH5TemplateListDesc(params);
		int sise=h5list.size();
		for(int i=0;i<sise;i++){
			WapH5Template wapH5=h5list.get(i);
			wapH5.setThumbnailUrl(ImageUtil.getShowimageUrl(wapH5.getThumbnailUrl())); //图片地址
		}
		page.setTotalCount(Integer.valueOf(wapH5TemplateDao.getWapH5TemplateListCount()));//总条数
		//成功
		resultStatus=true;
		resultMap.put("status", resultStatus);
		resultMap.put("msc", "");
		resultMap.put("page", page);
		resultMap.put("resultInfo", h5list);
		return resultMap;
	}

	@Override
	public List<WapH5Template> queryActveTemplateList() {
		// TODO Auto-generated method stub
		return wapH5TemplateDao.queryActveTemplateList();
	}

	@Override
	public void updatePageTemplate(WapH5PageTemplate template) {
		
		wapH5TemplateDao.updatePageTemplate(template);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertPageTemplate(WapH5PageTemplate template) {
		// TODO Auto-generated method stub
		wapH5TemplateDao.insertPageTemplate(template);
	}

	@Override
	public List<WapH5PageTemplate> queryPageTemplateList() {
		// TODO Auto-generated method stub
		return wapH5TemplateDao.queryPageTemplateList();
	}

	@Override
	public Boolean queryPageTemplateNameExist(String name) {
		// TODO Auto-generated method stub
		Long count  = wapH5TemplateDao.queryPageTemplateNameExist(name);
		return count>0L;
	}


	
	
	
}
