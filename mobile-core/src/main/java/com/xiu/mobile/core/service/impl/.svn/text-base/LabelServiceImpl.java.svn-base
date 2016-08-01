package com.xiu.mobile.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiu.mobile.core.dao.LabelDao;
import com.xiu.mobile.core.model.Label;
import com.xiu.mobile.core.model.LabelCentre;
import com.xiu.mobile.core.model.LabelRelation;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.service.ILabelService;
import com.xiu.mobile.core.utils.ObjectUtil;
@Transactional
@Service("labelService")
public class LabelServiceImpl implements ILabelService { 
	@Autowired
	private LabelDao labelDao;
	

	@Override
	public Map<String, Object> getLabelList(Map<String, Object> params,
			Page<Label> page) {
		Map<String,Object> resultMap=new HashMap<String,Object>();//结果集
		List<Label> labelList=new ArrayList<Label>();
		int total=labelDao.getLabelCount(params);
		
		params.put("pageMin", page.getFirstRecord());
		params.put("pageMax", page.getEndRecord());
		labelList=labelDao.getLabelList(params);
		
		for (Label label:labelList) {
			if(label.getRelationLabelNum()>0){
				label.setRelationLabelNum(label.getRelationLabelNum());
			}
		}
		
		
		page.setTotalCount(total); //总条数
		resultMap.put("page", page);
		resultMap.put("labelList", labelList);
		return resultMap;
	}

	@Override
	public Map<String, Object> addSubjectLabel(Label label) {
		Map<String,Object> resultMap=new HashMap<String,Object>();
		Boolean isSuccess=false; //判断是否成功
		String msg="";
		//查询是否存在
		Long lableid=labelDao.getLabelIdByName(label.getTitle());
		if(lableid!=null&&lableid>0){
			isSuccess=false;
			msg="该标签已经存在";
			resultMap.put("status", isSuccess);
			resultMap.put("msg", msg);
			return resultMap;
		}
		
		int i=labelDao.addSubjectLabel(label);
		if(i>0){
			isSuccess=true;
		}
		List<LabelCentre> labelCentres=new ArrayList<LabelCentre>();
		LabelCentre lacentre=new LabelCentre();
		lacentre.setLabelId(36L);
		labelCentres.add(lacentre);
		resultMap.put("status", isSuccess);
		resultMap.put("msg", msg);
		return resultMap;
	}

	@Override
	public Label getLabelInfo(long labelId) {
		return labelDao.getLabelInfo(labelId);
	}

	@Override
	public Map<String, Object> updateLabel(Label label) {
		Map<String,Object> resultMap=new HashMap<String,Object>();
		Boolean isSuccess=false; //判断是否成功
		String msg="";
		//查询是否存在
		Long lableid=labelDao.getLabelIdByName(label.getTitle());
		if(lableid!=null&&lableid>0&&!lableid.equals(label.getLabelId())){
			isSuccess=false;
			msg="该标签已经存在";
			resultMap.put("status", isSuccess);
			resultMap.put("msg", msg);
			return resultMap;
		}
		
		int i=labelDao.updaetLabel(label);
		if(i>0){
			isSuccess=true;
		}
		resultMap.put("status", isSuccess);
		resultMap.put("msg", "");
		return resultMap;
	}
	
	@Override
	public int deleteLabel(Map params){
		Long labelId=ObjectUtil.getLong(params.get("labelId"), null);
		int deleteNum=0;
		if(labelId!=null){
			//删除标签也业务中间关联表
			labelDao.deleteLabelObjectByLabelId(labelId);
			//删除该标签
			 deleteNum= labelDao.deleteLabel(params);
		}
		return deleteNum;
	}

	public long findLabelIdByTitle(Map<String,Object> params){
		Label label=labelDao.findLabelIdByTitle(params);
		if(label!=null){
			return label.getLabelId();
		}
		return 0L;
	}

	@Override
	public Integer bindLableToSevice(Integer type,Long objectId,List<LabelCentre> labelCentres) {
		Integer status=0;//默认失败
		
		StringBuilder labelIdSb=new StringBuilder();//标签ID串
		StringBuilder labelNameSb=new StringBuilder();//标签名称串
		Integer typeI=ObjectUtil.getInteger(type, null);
		Long objectIdL=ObjectUtil.getLong(objectId, null);
		for (int i = 0; i < labelCentres.size(); i++) {
			LabelCentre lobject=labelCentres.get(i);
			Long labelIdL=ObjectUtil.getLong(lobject.getLabelId(),null);
			if(typeI==null||objectIdL==null||labelIdL==null){
				status=2;//参数不完整
				return status;
			}
			if(lobject.getLableOrderSeq()==null){
				lobject.setLableOrderSeq(i);
			}
			if(lobject.getObjectOrderSeq()==null){
				lobject.setObjectOrderSeq(100);
			}
			
			if(labelIdSb.toString().length()>0){
				labelIdSb.append(",");
				labelNameSb.append(",");
			}
			labelIdSb.append(labelIdL);
			//查询标签名称
			Label label=labelDao.getLabelInfo(labelIdL);
			labelNameSb.append(label.getTitle());
			lobject.setCategory(type);
			lobject.setObjectId(objectIdL);
		}
		//删除旧的关联数据
		Map delParams=new HashMap();
		delParams.put("objectId", objectId);
		delParams.put("type", typeI);
		labelDao.deleteBatchLabelObject(delParams);
		if(labelCentres!=null&&labelCentres.size()>0){
			//插入关联数据
			labelDao.addLabelObjectList(labelCentres);
		}
		status=1;
		return status;
	}
	

	@Override
	public Map<String,Object> findCommonLabelList() {
		Map<String,Object> resultMap=new HashMap<String,Object>();
		//查询新添加的前15个标签
		List<Label> newAddList=labelDao.findNewAddLabel();
		//查询 历史使用的标签
		List<Label> historyList=labelDao.findHistoryLabel();
		resultMap.put("newAddList", newAddList);
		resultMap.put("historyList", historyList);
		return resultMap;
	}

	
	
	@Override
	public Long getLabelIdByName(String labelName) {
		return labelDao.getLabelIdByName(labelName);
	}

	@Override
	public List<Label> findLabelListByObjectIdAndType(Long objectId,
			Integer type) {
		Map params=new HashMap();
		params.put("objectId", objectId);
		params.put("type", type);
		return labelDao.findLabelListByObjectIdAndType(params);
	}

	@Override
	public Map<Long,List<LabelCentre>> findLabelsByObjectIds(List<Long> objectIds,
			Integer type) {
		Map<Long,List<LabelCentre>> result=new HashMap<Long,List<LabelCentre>>();
		Map params=new HashMap();
		params.put("objectIds", objectIds);
		params.put("type", type);
		List<LabelCentre> labels= labelDao.findLabelsByObjectIds(params);
		
		for (LabelCentre label:labels) {
			if(result.get(label.getObjectId())==null){
				List<LabelCentre> lcs =new ArrayList<LabelCentre>();
				lcs.add(label);
				result.put(label.getObjectId(), lcs);
			}else{
				List<LabelCentre> lcs =result.get(label.getObjectId());
				lcs.add(label);
				result.put(label.getObjectId(), lcs);
			}
		}
		return result;
	}

	@Override
	public List<Long> getLabelIdByNames(Map typeNamesMap) {
		return labelDao.getLabelIdByNames(typeNamesMap);
	}

	/**
	 * 
	 */
	@Override
	public  Map<String, Object>  getLabelServiceDateList(Map params, Page<Label> page) {
		Map<String,Object> resultMap=new HashMap<String,Object>();//结果集
		List<LabelCentre> labelList=new ArrayList<LabelCentre>();
		Integer type=(Integer)params.get("type");
		Long labelId=(Long)params.get("labelId");
		Label label=labelDao.getLabelInfo(labelId);
		String labelName=label.getTitle();
		//获取总数
		int total=labelDao.getLabelServiceDateCount(params);
		params.put("pageMin", page.getFirstRecord());
		params.put("pageMax", page.getEndRecord());
		page.setTotalCount(total); //总条数
		//获取业务数据
		labelList=labelDao.getLabelServiceDateList(params);
		for (LabelCentre lc:labelList) {
			if(lc.getObjectName()!=null&&lc.getObjectName().length()>20){
				String oname=lc.getObjectName();
				lc.setObjectName(oname.substring(0,20));
			}
		}
		resultMap.put("page", page);
		resultMap.put("labelName", labelName);
		resultMap.put("labelCentreList", labelList);
		return resultMap;
	}

	@Override
	public Map<String, Object> getLabelRelationDateList(Map params,
			Page<Label> page) {
		Map<String,Object> resultMap=new HashMap<String,Object>();//结果集
		List<LabelRelation> labelList=new ArrayList<LabelRelation>();
		page.setPageSize(50);
		Integer type=(Integer)params.get("type");
		Long labelId=(Long)params.get("labelId");
		Label label=labelDao.getLabelInfo(labelId);
		String labelName=label.getTitle();
		//获取总数
		int total=labelDao.getLabelRelationDateCount(params);
		params.put("pageMin", page.getFirstRecord());
		params.put("pageMax", page.getEndRecord());
		page.setTotalCount(total); //总条数
		//获取业务数据
		labelList=labelDao.getLabelRelationDateList(params);
		resultMap.put("page", page);
		resultMap.put("labelName", labelName);
		resultMap.put("labelRelationList", labelList);
		return resultMap;
	}
	
	@Override
	public Boolean updateLabelServiceOrder(Map params) {
		Integer num=labelDao.updateLabelServiceOrder(params);
		if(num>0){
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public Boolean updateLabelRelationOrder(Map params) {
		Integer num=labelDao.updateLabelRelationOrder(params);
		if(num>0){
			return true;
		}else {
			return false;
		}
	}

	@Override
	public void syncLabelRelationDate(Map params) {
		//删除已经不存在的标签
		
		//批量更新标签关联数据
//		labelDao.syncLabelRelationDate(params);
	}

}
