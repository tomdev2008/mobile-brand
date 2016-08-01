package com.xiu.mobile.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.core.model.FindSupportVersion;
import com.xiu.mobile.portal.dao.FindMenuDao;
import com.xiu.mobile.portal.service.ISupportVersionService;
/**
 * 版本
 * @author Administrator
 *
 */
@Service("supportVersionService")
public class SupportVersionServiceImpl implements ISupportVersionService {

	@Autowired
	private FindMenuDao findMenuDao;
	
	@Override
	public Map<Long, List<FindSupportVersion>> findVersion(
			Map<String, Object> params) {
		Map<Long,List<FindSupportVersion>> tmp=new HashMap<Long,List<FindSupportVersion>>();
		List<FindSupportVersion> listVersion=findMenuDao.getSupportVersionList(params);
		for(FindSupportVersion s:listVersion){
			List<FindSupportVersion> items=tmp.get(s.getFindMenuId());
			if(items==null){
				items=new ArrayList<FindSupportVersion>();
				tmp.put(s.getFindMenuId(), items);
			}
			items.add(s);
		}
		return tmp;
	}

}
