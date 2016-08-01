package com.xiu.mobile.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.portal.dao.QuestionGameDao;
import com.xiu.mobile.portal.model.QuestionGame;
import com.xiu.mobile.portal.service.IQuestionGameService;

@Service("questionGameService")
public class QuestionGameServiceImpl implements IQuestionGameService{
	
	@Autowired
	private QuestionGameDao questionGameDao;

	@Override
	public QuestionGame getById(Long id) {
		return questionGameDao.getById(id);
	}

	@Override
	public List<QuestionGame> getListByParams(Map<String, Object> params) {
		return questionGameDao.getListByParams(params);
	}

}
