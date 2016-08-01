/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-9 上午10:57:38 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.mobile.brand.web.service.impl;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.brand.web.cache.XiuAttrGroupCache;
import com.xiu.mobile.brand.web.model.AttrGroupJsonModel;
import com.xiu.mobile.brand.web.service.IAttrGroupService;

/** 
 * <p>
 * ************************************************************** 
 * @Description: 筛选项业务逻辑实现类
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-9 上午10:57:38 
 * ***************************************************************
 * </p>
 */
@Service("attrGroupService")
public class AttrGroupServiceImpl implements IAttrGroupService {
	
	@Autowired
	private XiuAttrGroupCache xiuAttrGroupCache;

	@Override
	public Map<String, AttrGroupJsonModel> getAttrGroupIdNameListWithInherit(Long categoryId) {
		if(null == categoryId) {
			return Collections.emptyMap();
		}
		
		Map<String, AttrGroupJsonModel> map = xiuAttrGroupCache.selectByPrimaryKeyForJson(categoryId);
		return map;
	}

}
