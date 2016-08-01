package com.xiu.show.web.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiu.show.core.model.Page;
import com.xiu.show.core.model.UserWhiteListModel;
import com.xiu.show.core.service.IShowUserWhiteListManagerService;
import com.xiu.show.web.controller.ShowCommentController;
import com.xiu.show.web.service.IUserWhiteListService;
@Transactional
@Service("userWhiteListService")
public class UserWhiteListServiceImpl implements IUserWhiteListService {
	 private static final XLogger LOGGER = XLoggerFactory.getXLogger(UserWhiteListServiceImpl.class);
	 @Autowired
	 private IShowUserWhiteListManagerService showUserWhiteListManagerService;
	 
	@Override
	public List<UserWhiteListModel> getUserWhiteList(int pageNo,int pageSize) {
		Page<?> page=new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		try{
			Map<String,Object> params=new HashMap<String,Object>();
			Map<String,Object> map=showUserWhiteListManagerService.getUserWhiteList(params,page);
			List<UserWhiteListModel> cs=(List<UserWhiteListModel>)map.get("model");
			return cs;
		}catch(Exception e){
			LOGGER.error("查询秀举报异常！",e);
		}
		
		return null;
	}

}
