package com.xiu.mobile.brand.web.service;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.brand.web.bo.LabelSearchBo;
import com.xiu.mobile.brand.web.bo.SearchHisMapBo;
import com.xiu.mobile.brand.web.bo.SuggestBo;
import com.xiu.mobile.brand.web.bo.TypeSuggestBo;
import com.xiu.mobile.brand.web.service.params.SuggestFatParam;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(关键字联想业务逻辑接口) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-10-14 下午4:23:51 
 * ***************************************************************
 * </p>
 */

public interface ISuggestService {

	List<SuggestBo> suggest(SuggestFatParam param);
	
	List<SearchHisMapBo> getSearchHistoryMap();
	
	/**
	 * 获取标签分类集合
	 * @param key 搜索关键字
	 * @param size 每个类别返回多少个
	 * @return
	 */
	Map<String,List<TypeSuggestBo>> getLabelList(String key,int size);
	
	/**
	 * 分页获取标签列表
	 * @param key 搜索关键字
	 * @param type 类别
	 * @param p 开始
	 * @param pSize  返回行数
	 * @return
	 */
	LabelSearchBo getLabelListByType(String key,String type,int p,int pSize);
}
