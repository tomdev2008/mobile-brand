package com.xiu.show.web.service;

import java.util.List;

import com.xiu.show.core.model.UserWhiteListModel;

public interface IUserWhiteListService {
	
	/**
	 * 查询白名单列表
	 */
	public List<UserWhiteListModel> getUserWhiteList(int page,int pageSize);

}
