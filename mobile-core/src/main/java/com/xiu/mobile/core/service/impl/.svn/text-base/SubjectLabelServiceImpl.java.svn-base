package com.xiu.mobile.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiu.mobile.core.dao.SubjectLabelDao;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.model.SubjectLabel;
import com.xiu.mobile.core.service.ISubjectLabelService;
@Transactional
@Service("subjectLabelService")
public class SubjectLabelServiceImpl implements ISubjectLabelService {
	@Autowired
	private SubjectLabelDao subjectLabelDao;

	@Override
	public Map<String, Object> getLabelList(Map<String, Object> params,
			Page<SubjectLabel> page) {
		Map<String,Object> resultMap=new HashMap<String,Object>();//结果集
		List<SubjectLabel> labelList=new ArrayList<SubjectLabel>();
		params.put("pageMin", page.getFirstRecord());
		params.put("pageMax", page.getEndRecord());
		labelList=subjectLabelDao.getLabelList(params);
		int total=subjectLabelDao.getLabelCount(params);
		page.setTotalCount(total); //总条数
		resultMap.put("page", page);
		resultMap.put("labelList", labelList);
		return resultMap;
	}

	@Override
	public Map<String, Object> addSubjectLabel(SubjectLabel label) {
		Map<String,Object> resultMap=new HashMap<String,Object>();
		Boolean isSuccess=false; //判断是否成功
		int i=subjectLabelDao.addSubjectLabel(label);
		if(i>0){
			isSuccess=true;
		}
		resultMap.put("status", isSuccess);
		resultMap.put("msg", "");
		return resultMap;
	}

	@Override
	public SubjectLabel getLabelInfo(long labelId) {
		return subjectLabelDao.getLabelInfo(labelId);
	}

	@Override
	public Map<String, Object> updateLabel(SubjectLabel label) {
		Map<String,Object> resultMap=new HashMap<String,Object>();
		Boolean isSuccess=false; //判断是否成功
		int i=subjectLabelDao.updaetLabel(label);
		if(i>0){
			isSuccess=true;
		}
		resultMap.put("status", isSuccess);
		resultMap.put("msg", "");
		return resultMap;
	}
	
	@Override
	public int deleteLabel(long labelId){
		return subjectLabelDao.deleteLabel(labelId);
	}

	public long findLabelIdByTitle(Map<String,Object> params){
		SubjectLabel label=subjectLabelDao.findLabelIdByTitle(params);
		if(label!=null){
			return label.getLabelId();
		}
		return 0L;
	}
}
