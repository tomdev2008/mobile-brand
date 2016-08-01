package com.xiu.mobile.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiu.mobile.core.constants.GlobalConstants;
import com.xiu.mobile.core.dao.LabelDao;
import com.xiu.mobile.core.dao.WapH5ListDao;
import com.xiu.mobile.core.dao.WapH5ModuleDao;
import com.xiu.mobile.core.model.LabelCentre;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.model.Subject;
import com.xiu.mobile.core.model.Topic;
import com.xiu.mobile.core.model.WapH5List;
import com.xiu.mobile.core.service.IWapH5ListService;
import com.xiu.mobile.core.utils.ImageUtil;
import com.xiu.mobile.core.utils.ObjectUtil;
@Transactional
@Service("wapH5ListService")
public class WapH5ListServiceImpl implements IWapH5ListService {

	@Autowired
	private WapH5ListDao wapH5ListDao;
	@Autowired
	private WapH5ModuleDao wapH5ModuleDao;
    @Autowired
	private LabelDao labelDao;
    
	@Override
	public Map<String, Object> getH5list(Map<String, Object> params,
			Page<?> page) {
		Map<String,Object> resultMap=new HashMap<String,Object>();//结果集
		List<WapH5List> h5list=new ArrayList<WapH5List>();
		Boolean resultStatus=false; //是否成功的状态
		params.put("pageMin", page.getFirstRecord());
		params.put("pageSize", page.getPageSize());
		params.put("pageMax", page.getEndRecord());
		params.put("labelType",  GlobalConstants.LABEL_TYPE_H5);
		h5list=wapH5ListDao.getH5list(params);
		int sise=h5list.size();
		List<Long> h5Ids=new ArrayList<Long>();
		for(int i=0;i<sise;i++){
			WapH5List wapH5=h5list.get(i);
			wapH5.setPhoto(ImageUtil.getShowimageUrl(wapH5.getPhoto())); //图片地址
			h5Ids.add(wapH5.getId());
		}
		
		//查询标签
		Map<String,Object> labelMap=new HashMap<String,Object>();
		labelMap.put("objectIds", h5Ids);
		labelMap.put("type", GlobalConstants.LABEL_TYPE_H5);//卖场
		List<LabelCentre> results=null;
		if(h5Ids.size()>0){
			results=labelDao.findLabelsByObjectIds(labelMap);
		}
		for(WapH5List h5:h5list){
			if(results!=null){
				for (LabelCentre lc:results) {
					if(lc.getObjectId().equals(h5.getId())){
						List<LabelCentre> h5lc=h5.getLabelCentre();
						if(h5lc==null){
							h5lc=new  ArrayList<LabelCentre>();
						}
						h5lc.add(lc);
						h5.setLabelCentre(h5lc);
					}
				}
			}
		}
		
		page.setTotalCount(Integer.valueOf(wapH5ListDao.getH5ListCount(params)));//总条数
		//成功
		resultStatus=true;
		resultMap.put("status", resultStatus);
		resultMap.put("msc", "");
		resultMap.put("page", page);
		resultMap.put("resultInfo", h5list);
		return resultMap;
	}

	@Override
	public int addH5list(WapH5List h5list) {
		return wapH5ListDao.addH5list(h5list);
	}
	public Long findWapH5Id(){
		return wapH5ListDao.findWapH5Id();
	}
	
	@Override
	public WapH5List getWapH5ListById(Long id) {
		WapH5List wapH5=wapH5ListDao.getWapH5ListById(id);
		if(wapH5!=null){
			wapH5.setPhoto(ImageUtil.getShowimageUrl(wapH5.getPhoto()));
			wapH5.setSharePhoto(ImageUtil.getShowimageUrl(wapH5.getSharePhoto()));
		}
		return wapH5;
	}

	@Override
	public int updateWapH5List(WapH5List wapH5List) {
		int isSuccess=0; //检查是否成功   1添加成功 2 标题已经存在  0不成功
		//检查标题是否有重复
//		int total=wapH5ListDao.findWapH5ListTitil(wapH5List.getTitle());
//		if(total!=0){ //该标题已经存在
//			isSuccess=2;
//			return isSuccess;
//		}
		int i=wapH5ListDao.updateWapH5List(wapH5List);
		if(i>0){
			isSuccess=1;
		}
		return isSuccess;
	}

	@Override
	public int deleteWapH5List(List<Long> ids) {
		//查询type=3的所有pageId数据
		List<Long> pageIds = null;
		pageIds = wapH5ListDao.findWapH5ListTypeThree(ids);
		if(pageIds != null && pageIds.size() > 0){
			//根据pageId查询所有moduleId数据
			List<Long> moduleIds = null;
			moduleIds = wapH5ModuleDao.getModuleListIds(pageIds);
			if(null != moduleIds && moduleIds.size() != 0 ){				
				//根据moduleIds删除所有ModuleData表的数据
				wapH5ModuleDao.deleteModuleDataList(moduleIds);
				//删除Module表的数据
				wapH5ModuleDao.deleteModuleList(pageIds);
			}
		}
		return wapH5ListDao.deleteWapH5List(ids);
	}

	@Override
	public Map<String, Object> getWapH5List(Map<String, Object> params) {
		Map<String,Object> results=new HashMap<String,Object>();//结果集
		List<WapH5List> h5list=new ArrayList<WapH5List>();
		Boolean resultStatus=false; //是否成功的状态
		params.put("status", 1); //只显示展示的列表
		int total=wapH5ListDao.getH5ListCount(params); //总条数
		
		Page<WapH5List> page=new Page<WapH5List>();
		page.setPageSize(Integer.valueOf(params.get("pageSize").toString()));
		page.setPageNo(Integer.valueOf(params.get("pageNum").toString()));
		params.put("pageMin", page.getFirstRecord());
		params.put("pageMax", page.getEndRecord());
		h5list=wapH5ListDao.getH5list(params);
		if(h5list==null){
			results.put("status", resultStatus);
			results.put("errorCode","201");
			results.put("errorMsg", "没有H5列表");
			return results;
		}
		int size=h5list.size();
		page.setTotalCount(total);
		for(int i=0;i<size;i++){
			WapH5List wapH5=h5list.get(i);
			wapH5.setPhoto(ImageUtil.getShowimageUrl(wapH5.getPhoto())); //图片地址
		}
		resultStatus=true;//查询成功
		results.put("totalCount", total); //总条数
		results.put("totalPage", page.getTotalPages()); //总页数
		results.put("status", resultStatus);
		results.put("pageURL", "http://www.baidu.com"); //展示页面的url
		results.put("h5List", h5list);
		return results;
	}

	@Override
	public int findH5Url(String url) {
		return wapH5ListDao.findH5Url(url);
	}

	@Override
	public Integer getSyncToSearchCount(Map syncGetParams) {
		return wapH5ListDao.getSyncToSearchCount(syncGetParams);
	}

	@Override
	public List<WapH5List> getSyncToSearchList(Map params) {
		Map result =new HashMap();
		Integer page=ObjectUtil.getInteger(params.get("pageNo"), 1);
		Integer pageSize=ObjectUtil.getInteger(params.get("pageSize"), 100);
		int lineMax =page * pageSize+1;
		int lineMin = (page-1)*pageSize+1;
		params.put("pageMax", lineMax);
		params.put("pageMin", lineMin);
		List<WapH5List> topics=wapH5ListDao.getSyncToSearchList(params);
		return topics;
	}
}
