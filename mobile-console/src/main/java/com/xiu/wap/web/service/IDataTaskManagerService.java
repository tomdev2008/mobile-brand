package com.xiu.wap.web.service;

public abstract interface IDataTaskManagerService
{
	/**
	 * 定时删除用户浏览器记录数据
	 */
  public abstract void callDeleteUserBrowseDataTask();
	/**
	 * 定时刷新卖场过滤数据到缓存
	 */
  public abstract void syncTopicFilterDataToCache();
  
	/**
	 * 定时刷标签关联数据
	 */
  public abstract void syncLabelRelationDate();
}