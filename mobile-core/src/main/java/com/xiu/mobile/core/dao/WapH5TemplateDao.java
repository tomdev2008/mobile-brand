package com.xiu.mobile.core.dao;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.WapH5Template;
import com.xiu.mobile.core.model.WapH5PageTemplate;

public interface WapH5TemplateDao {

	public void saveH5Template(WapH5Template template);
	
	public void updateH5Template(WapH5Template template);
	
	public WapH5Template queryWapH5TemplateById(long id);
	
	public WapH5Template queryWapH5TemplateByModuleId(Long moduleId);
	
	public List<WapH5Template> queryWapH5TemplateByPageId(Long pageId);
	
	public List<WapH5Template> queryWapH5TemplateListDesc(Map<String, Object> params);
	
	public Integer getWapH5TemplateListCount();
	
	public  List<WapH5Template>queryActveTemplateList();
	
	public void updatePageTemplate(WapH5PageTemplate template);
	
	public void insertPageTemplate(WapH5PageTemplate template);
	
	public List<WapH5PageTemplate> queryPageTemplateList();
	
	public Long  queryPageTemplateNameExist(String name);
	
	
}


