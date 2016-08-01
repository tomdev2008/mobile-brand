package com.xiu.wap.web.service;


/**
 * 
* @Description: TODO(搜索横表) 
* @author haidong.luo@xiu.com
* @date 2016年5月12日 下午5:41:16 
*
 */
public interface ISearchService {


	/**
	 * 批量添加业务数据到搜索中
	 * 其中params中含有以下参数
	 *  type（标签类型(3:卖场 4:专题  5:H5)） 必填 String 类型
	 *  objectId 业务类型的ID
	 * @return 1成功 0失败 2参数不完整 3业务数据不存在
	 */
	public Integer addDataToSearch(Integer type,Long objectId);
	
	/**
	 * 定时同步业务数据到搜索表
	 */
	public  void syncDataToSearch();
	
}
