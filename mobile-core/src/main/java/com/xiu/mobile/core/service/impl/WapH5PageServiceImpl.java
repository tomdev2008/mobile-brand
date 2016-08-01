package com.xiu.mobile.core.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiu.mobile.core.dao.WapH5ListDao;
import com.xiu.mobile.core.dao.WapH5ModuleDao;
import com.xiu.mobile.core.dao.WapH5TemplateDao;
import com.xiu.mobile.core.model.WapH5List;
import com.xiu.mobile.core.model.WapH5Module;
import com.xiu.mobile.core.model.WapH5ModuleData;
import com.xiu.mobile.core.model.WapH5Template;
import com.xiu.mobile.core.service.IWapH5ModuleService;
import com.xiu.mobile.core.service.IWapH5PageService;
import com.xiu.mobile.core.utils.ImageUtil;


@Transactional
@Service("wapH5PageService")
public class WapH5PageServiceImpl implements IWapH5PageService {
	
	private static Logger logger = Logger.getLogger(WapH5PageServiceImpl.class);
	
	@Autowired
	WapH5ListDao wapH5ListDao;
	@Autowired
	WapH5TemplateDao wapH5TemplateDao;
	@Autowired
	WapH5ModuleDao wapH5ModuleDao;
	@Autowired
	IWapH5ModuleService moduleService;

	@Override
	public WapH5List queryWapH5ListById(Long id) {
		WapH5List wapH5List = wapH5ListDao.getWapH5ListById(id);
		wapH5List.setPhoto(ImageUtil.getShowimageUrl(wapH5List.getPhoto()));
		wapH5List.setSharePhoto(ImageUtil.getShowimageUrl(wapH5List.getSharePhoto()));
		return wapH5List;
	}

	@Override
	public Long saveOrUpadteWapH5List(WapH5List wapH5List) {
		
		if(wapH5List != null && wapH5List.getId() > 0){
			wapH5ListDao.updateWapH5List(wapH5List);
		}else{
			Long id = wapH5ListDao.findWapH5Id();
			wapH5List.setId(id);
			wapH5ListDao.addH5list(wapH5List);
		}
		Long h5Id=wapH5List.getId();
		return h5Id;
	}

//	@Override
//	public Map<String, Object> queryModuleTemplateByPageId(Long pageId) {
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		if(pageId == null){
//			return resultMap;
//		}
//		//根据页面ID查询该页面所包含的组件的模板数据规范
//		List<WapH5Template> templateList = wapH5TemplateDao.queryWapH5TemplateByPageId(pageId);
//		if(templateList == null || templateList.size() <= 0){
//			return resultMap;
//		}
//		//存储excel标题头
//		List<String[]> excelHeaders = new ArrayList<String[]>();
//		//存储sheetName
//		List<String> sheetNames = new ArrayList<String>();
//		for (Iterator<WapH5Template> iterator = templateList.iterator(); iterator.hasNext();) {
//			WapH5Template wapH5Template = iterator.next();
//			sheetNames.add(wapH5Template.getName() + "_" + wapH5Template.getId()); //注意，这个查出来的id是组件ID，不是模板ID
//			
//			JSONObject jsonObject = JSONObject.fromObject(wapH5Template.getDataTemplate());
//			JSONObject subJsonObject = JSONObject.fromObject(jsonObject.get("column"));
//			JSONArray showNameJsonArray = JSONArray.fromObject(subJsonObject.get("showname"));
//			
//			String[] excelHeader = null;
//			if(showNameJsonArray != null){
//				excelHeader = new String[showNameJsonArray.size()+1];
//				excelHeader[0] = "组:0";
//				for (int i=0; i<showNameJsonArray.size(); i++) {
//					String showName = (String) showNameJsonArray.get(i);
//					excelHeader[i] = showName + ":" + i;
//				}
//			}
//			excelHeaders.add(excelHeader);
//		}
//		resultMap.put("sheetNames", sheetNames);
//		resultMap.put("excelHeaders", excelHeaders);
//		
//		return resultMap;
//	}
	
	@Override
	public Map<String, Object> queryModuleTemplateByPageId(Long pageId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(pageId == null){
			return resultMap;
		}
		//根据页面ID查询该页面所包含的组件的模板数据规范
		List<WapH5Template> templateList = wapH5TemplateDao.queryWapH5TemplateByPageId(pageId);
		if(templateList == null || templateList.size() <= 0){
			return resultMap;
		}
		//目前所有模板的excel头都是一样的，所以取第0个就好
		WapH5Template wapH5Template = templateList.get(0);
			
		JSONObject jsonObject = JSONObject.fromObject(wapH5Template.getDataTemplate());
		JSONObject subJsonObject = JSONObject.fromObject(jsonObject.get("column"));
		JSONArray colnameJsonArray = JSONArray.fromObject(subJsonObject.get("colname"));
		JSONArray showNameJsonArray = JSONArray.fromObject(subJsonObject.get("showname"));
			
		//存储excel标题头
		String[] excelHeader = null;
		if(showNameJsonArray != null){
			excelHeader = new String[showNameJsonArray.size()+1];
			excelHeader[0] = "组:GroupIndex";
			for (int i=1; i<=showNameJsonArray.size(); i++) {
				String showName = (String) showNameJsonArray.get(i-1);
				excelHeader[i] = showName + ":" + colnameJsonArray.get(i-1);
			}
		}
		resultMap.put("excelHeader", excelHeader);
		
		return resultMap;
	}

	@Override
	public List<WapH5ModuleData> queryAllModuleDataByPageId(Long pageId) {
		List<WapH5Module> moduleList = wapH5ModuleDao.getTargetModuleList(pageId);
		if(moduleList == null || moduleList.size() <= 0){
			return null;
		}
//		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
//		for (Iterator<WapH5Module> iterator = moduleList.iterator(); iterator.hasNext();) {
//			WapH5Module wapH5Module = iterator.next();
//			
//			List<Map<String, Object>> moduleDataList = moduleService.queryModuleDataByModuleId(wapH5Module.getId());
//			if(moduleDataList == null){
//				moduleDataList = new ArrayList<Map<String,Object>>();
//			}
//			dataList.addAll(moduleDataList);
//		}
		List<WapH5ModuleData> dataList = new ArrayList<WapH5ModuleData>();
		for (Iterator<WapH5Module> iterator = moduleList.iterator(); iterator.hasNext();) {
			WapH5Module wapH5Module = iterator.next();
			
			List<WapH5ModuleData> moduleDataList = moduleService.queryModuleDataByModuleId(wapH5Module.getId());
			if(moduleDataList == null){
				moduleDataList = new ArrayList<WapH5ModuleData>();
			}
			dataList.addAll(moduleDataList);
		}
		return dataList;
	}

//	@Override
//	public String importAllModuleData(Long pageId,
//			Map<Long, Object> dataMap) {
//		
//		StringBuilder errorMsg = new StringBuilder();
//		List<WapH5Module> moduleList = wapH5ModuleDao.getTargetModuleList(pageId);
//		if(moduleList == null || moduleList.size() <= 0){
//			errorMsg.append("该页面当前没有选择任何组件");
//			return errorMsg.toString();
//		}
//		for (int i=0; i<moduleList.size(); i++) {
//			WapH5Module wapH5Module = moduleList.get(i);
//			List<Map<Object, Object>> moduleMap = (List<Map<Object, Object>>) dataMap.get(wapH5Module.getId());
//			if(moduleMap != null){
//				try{
//					if(moduleMap.size() > 0){
//						String importMsg = moduleService.importModuleData(wapH5Module.getId(), moduleMap);
//						if(!"导入成功".equals(importMsg)){
//							errorMsg.append("导入sheet"+i+"失败").append("; ");
//						}
//					}
//				}catch(Exception e){
//					logger.error("导入sheet"+i+"异常", e);
//					errorMsg.append("导入sheet"+i+"异常").append("; ");
//				}
//			}else{
//				errorMsg.append("sheet").append(i).append("已失效，请重新下载模板导入；");
//			}
//		}
//		return errorMsg.toString();
//	}
	
	@Override
	public String importAllModuleData(Long pageId,
			List<Map<Object, Object>> dataList) {
		
		StringBuilder errorMsg = new StringBuilder();
		List<WapH5Module> moduleList = wapH5ModuleDao.getTargetModuleList(pageId);
		if(moduleList == null || moduleList.size() <= 0){
			errorMsg.append("该页面当前没有选择任何组件");
			return errorMsg.toString();
		}
		//把数据按组件分组
		Map<String, List<Map<Object, Object>>> moduleDataMap = new HashMap<String, List<Map<Object,Object>>>();
		List<Map<Object, Object>> moduleDataList = null;
		for (int i=0; i<dataList.size(); i++) {
			Map<Object, Object> moduleData = dataList.get(i);
			//如果组名不为空，表示是新的一组
			String groupVal = (String) moduleData.get(0);
			if(StringUtils.isNotBlank(groupVal)){
				moduleDataList = new ArrayList<Map<Object,Object>>();
				moduleDataMap.put(groupVal, moduleDataList);
			}
			moduleDataList.add(moduleData);
		}
		//循环导入的每个组件的数据，校验是否存在与之对应的组件，存在则导入
		//由于要提示哪组导入不成功，所以外层循环是导入的数据，如果外层循环是moduleList，虽然效率好点，但不好提示
		for (Iterator<String> iterator = moduleDataMap.keySet().iterator(); iterator.hasNext();) {
			String groupVal = iterator.next();
			if(groupVal.contains("_")){
				long moduleId = Long.parseLong(groupVal.split("_")[1]);
				WapH5Module sameModule = null;
				for (int i=0; i<moduleList.size(); i++) {
					WapH5Module wapH5Module = moduleList.get(i);
					if(wapH5Module.getId() == moduleId){
						sameModule = wapH5Module;
						break;
					}
				}
				if(sameModule != null){
					List<Map<Object, Object>> moduleMap = (List<Map<Object, Object>>) moduleDataMap.get(groupVal);
					try{
						if(moduleMap.size() > 0){
							String importMsg = moduleService.importModuleData(sameModule.getId(), moduleMap);
							if(!"导入成功".equals(importMsg)){
								errorMsg.append("导入"+ groupVal +"失败").append("; ");
							}
						}
					}catch(Exception e){
						logger.error("导入"+ groupVal +"异常", e);
						errorMsg.append("导入组" + groupVal +"异常").append("; ");
					}
				}else{
					errorMsg.append(groupVal + "已失效，请重新下载模板导入").append("; ");
				}
			}else{
				errorMsg.append(groupVal + "已失效，请重新下载模板导入").append("; ");
			}
		}
		return errorMsg.toString();
	}

	@Override
	public Map<String, Object> assemblePageVeiw(Long pageId) {
		List<WapH5Module> moduleList = wapH5ModuleDao.getTargetModuleList(pageId);
		if(moduleList == null || moduleList.size() <= 0){
			return null;
		}
		List<List<List<WapH5ModuleData>>> pageData = new ArrayList<List<List<WapH5ModuleData>>>();
		List<String> templates = new ArrayList<String>();
		List<String> isLazyLoads = new ArrayList<String>();
		List<Long> moduleIds = new ArrayList<Long>();
		Set<String> jsUrlSet = new HashSet<String>(); 
		Set<String> cssUrlSet = new HashSet<String>(); 
		for (Iterator<WapH5Module> iterator = moduleList.iterator(); iterator.hasNext();) {
			WapH5Module wapH5Module = iterator.next();
			Map<String, Object> moduleMap = moduleService.assembleModuleVeiw(wapH5Module.getId());
			List<List<WapH5ModuleData>> moduleData = (List<List<WapH5ModuleData>>) moduleMap.get("dataList");
			if(moduleData == null){
				moduleData = new ArrayList<List<WapH5ModuleData>>();
			}
			pageData.add(moduleData);
			String template = (String) moduleMap.get("template");
			templates.add(template);
			
			String isLazyLoad = (String) moduleMap.get("isLazyLoad");
			isLazyLoads.add(isLazyLoad);
			
			Long moduleId = (Long) moduleMap.get("moduleId");
			moduleIds.add(moduleId);
			
			String jsUrl = (String) moduleMap.get("jsUrl");
			jsUrlSet.add(jsUrl);
			String cssUrl = (String) moduleMap.get("cssUrl");
			cssUrlSet.add(cssUrl);
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("pageData", pageData);
		resultMap.put("templates", templates);
		resultMap.put("isLazyLoads", isLazyLoads);
		resultMap.put("moduleIds", moduleIds);
		resultMap.put("jsUrlSet", jsUrlSet);
		resultMap.put("cssUrlSet", cssUrlSet);
		
		return resultMap;
	}
	
}
