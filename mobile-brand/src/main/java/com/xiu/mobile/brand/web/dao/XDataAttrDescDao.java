package com.xiu.mobile.brand.web.dao;

import java.util.List;

import com.xiu.mobile.brand.web.dao.model.XDataAttrDesc;


public interface XDataAttrDescDao {

	/**
	 * @Description: 获取所有属性项名称
	 * @return    
	 * @return Map<String,String>
	 */
	public List<XDataAttrDesc> getAllAttrGroupName();
}
