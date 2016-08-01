package com.xiu.mobile.portal.dao;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.portal.model.QuestionGame;

/**
 * <p>
 * ************************************************************** 
 * @Description: 走秀游戏接口
 * @AUTHOR jianxiong.he@xiu.com
 * @DATE 2014-12-11 17:48:02 
 * ***************************************************************
 * </p>
 */
public interface QuestionGameDao {
	
	/***
	 * 通过Id查询问题信息
	 * @param id
	 * @return
	 */
	public QuestionGame getById(Long id);
	
	/***
	 * 通过相应参数查询相关问题列表
	 * @param params
	 * @return
	 */
	public List<QuestionGame> getListByParams(Map<String, Object> params);
}
