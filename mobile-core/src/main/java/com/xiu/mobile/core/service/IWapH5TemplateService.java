package com.xiu.mobile.core.service;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.model.WapH5Template;
import com.xiu.mobile.core.model.WapH5PageTemplate;


public interface IWapH5TemplateService {
	
	public void saveOrUpdateH5Template(WapH5Template template);
	
	public WapH5Template queryWapH5TemplateById(long id);

	public Map<String, Object> queryWapH5TemplateListDesc(Map<String, Object> params,Page<?> pagel);

	
	public  List<WapH5Template> queryActveTemplateList();
	
	public void updatePageTemplate(WapH5PageTemplate template);
	
	public void insertPageTemplate(WapH5PageTemplate template);
	
	public List<WapH5PageTemplate> queryPageTemplateList();
	
	public Boolean queryPageTemplateNameExist(String name);
	
}
