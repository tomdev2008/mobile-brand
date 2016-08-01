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
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiu.mobile.core.dao.WapH5ModuleDao;
import com.xiu.mobile.core.dao.WapH5TemplateDao;
import com.xiu.mobile.core.handler.h5.AbstractH5DataTypeHandler;
import com.xiu.mobile.core.model.WapH5List;
import com.xiu.mobile.core.model.WapH5Module;
import com.xiu.mobile.core.model.WapH5ModuleData;
import com.xiu.mobile.core.model.WapH5Template;
import com.xiu.mobile.core.service.IWapH5ModuleService;
import com.xiu.mobile.core.utils.ObjectUtil;
import com.xiu.mobile.core.constants.EnumContants.DataType;


@Transactional
@Service("wapH5ModuleService")
public class WapH5ModuleServiceImpl implements IWapH5ModuleService, ApplicationContextAware {
	
	@Autowired
	WapH5ModuleDao wapH5ModuleDao;
	@Autowired
	WapH5TemplateDao wapH5TemplateDao;
	
	private ApplicationContext applicationContext;

	@Override
	public Map<String, Object> queryModuleTemplateByModuleId(Long moduleId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//查询模板里定义的数据规范，里面有定义excel模板头
		WapH5Template wapH5Template = wapH5TemplateDao.queryWapH5TemplateByModuleId(moduleId);
		if(wapH5Template == null || StringUtils.isBlank(wapH5Template.getDataTemplate())){
			return resultMap;
		}
		JSONObject jsonObject = JSONObject.fromObject(wapH5Template.getDataTemplate());
		JSONObject subJsonObject = JSONObject.fromObject(jsonObject.get("column"));
		JSONArray colnameJsonArray = JSONArray.fromObject(subJsonObject.get("colname"));
		JSONArray showNameJsonArray = JSONArray.fromObject(subJsonObject.get("showname"));
		
		String[] excelHeader = null;
		if(showNameJsonArray != null){
			excelHeader = new String[showNameJsonArray.size()+1];
			excelHeader[0] = "组:GroupIndex";
			for (int i=1; i<=showNameJsonArray.size(); i++) {
				String showName = (String) showNameJsonArray.get(i-1);
				excelHeader[i] = showName + ":" + colnameJsonArray.get(i-1);
			}
		}
		//加上moduleId，是为了导入时，判断是否导入的不是这个组件的数据
		resultMap.put("name", wapH5Template.getName() + "_" + moduleId);
		resultMap.put("excelHeader", excelHeader);
		
		return resultMap;
	}

	@Override
	public List<WapH5Module> getTargetModuleList(long pageId) {
		return wapH5ModuleDao.getTargetModuleList(pageId);
	}

	@Override
	public void addModule(WapH5Module module, boolean backward) {
		if(backward){
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("pageId", module.getPageId());
			paramMap.put("currentIndex", module.getRowIndex());
			paramMap.put("direct", "2");
			//往页面添加组件时，先把当前以及之后的索引都往后移
			wapH5ModuleDao.moveRowIndex(paramMap);
		}
		wapH5ModuleDao.addModule(module);
	}
	
//	@Override
//	public List<Map<String, Object>> queryModuleDataByModuleId(Long moduleId) {
//		//查询组件模板数据
//		List<WapH5ModuleData> moduleDataList = wapH5ModuleDao.queryModuleDataByModuleId(moduleId);
//		if(moduleDataList == null || moduleDataList.size() <= 0){
//			return null;
//		}
//		//查询数据规范
//		WapH5Template wapH5Template = wapH5TemplateDao.queryWapH5TemplateByModuleId(moduleId);
//		JSONObject jsonObject = JSONObject.fromObject(wapH5Template.getDataTemplate());
//		JSONObject subJsonObject = JSONObject.fromObject(jsonObject.get("column"));
//		JSONArray colnameJsonArray = JSONArray.fromObject(subJsonObject.get("colname"));
//		//根据excel行位置进行数据分类
//		Map<Integer, List<WapH5ModuleData>> rowModuleDataMap = new HashMap<Integer, List<WapH5ModuleData>>();
//		for (Iterator<WapH5ModuleData> iterator = moduleDataList.iterator(); iterator
//				.hasNext();) {
//			WapH5ModuleData wapH5ModuleData = iterator.next();
//			List<WapH5ModuleData> moduleData = rowModuleDataMap.get(wapH5ModuleData.getExcelRow());
//			if(moduleData == null){
//				moduleData = new ArrayList<WapH5ModuleData>();
//			}
//			moduleData.add(wapH5ModuleData);
//			rowModuleDataMap.put(wapH5ModuleData.getExcelRow(), moduleData);
//		}
//		
//		List<Map<String, Object>> returnList = new ArrayList<Map<String,Object>>();
//		//每行数据按照数据规范填充列
//		for (Iterator<Integer> iterator = rowModuleDataMap.keySet().iterator(); iterator
//				.hasNext();) {
//			Integer excelRow = iterator.next();
//			//每一行上的列数据
//			List<WapH5ModuleData> moduleData = rowModuleDataMap.get(excelRow);
//			//存储excel每列上的值
//			Map<String, Object> dataMap = new HashMap<String, Object>();
//			//用来判断是否是一组新列
//			Set<String> columnSet = new HashSet<String>();
//			//先取出第一列对象
//			WapH5ModuleData wapH5ModuleData = moduleData.get(0);
//			//取出后移除掉
//			moduleData.remove(0);
//			//按照数据规范填充每列数据
//			for (int j=0; j<colnameJsonArray.size(); j++) {
//				String colname = (String) colnameJsonArray.get(j);
//				//先设默认值
//				dataMap.put(String.valueOf(j), "");
//				//是否是新的一组数据
//				boolean addSuccess = columnSet.add(colname);
//				if(!addSuccess){
//					//如果该excel行后面的列有数据，则填充，否则跳过，为默认空串
//					if(moduleData.size() > 0){
//						columnSet = new HashSet<String>();
//						//重新把数据再加一遍，否则会漏掉
//						columnSet.add(colname);
//						wapH5ModuleData = moduleData.get(0);
//						moduleData.remove(0);
//					}else{
//						continue;
//					}
//				}
//				//转换一下
//				if("DataType".equals(colname)){
//					wapH5ModuleData.setDataType(DataType.getName(wapH5ModuleData.getDataType()));
//				}
//				dataMap.put(String.valueOf(j), ObjectUtil.invokeGetMethod(wapH5ModuleData, colname));
//			}
//			returnList.add(dataMap);
//		}
//		
//		return returnList;
//	}
	
	@Override
	public List<WapH5ModuleData> queryModuleDataByModuleId(Long moduleId) {
//		List<Map<String, Object>> returnList = new ArrayList<Map<String,Object>>();
		
//		JSONObject columnJsonObject = JSONObject.fromObject(jsonObject.get("column"));
//		JSONArray colnameJsonArray = JSONArray.fromObject(columnJsonObject.get("colname"));
		
		
		WapH5Module wapH5Module = wapH5ModuleDao.queryModuleInfoById(moduleId);
		
		//查询数据规范
		WapH5Template wapH5Template = wapH5TemplateDao.queryWapH5TemplateByModuleId(moduleId);
		JSONObject jsonObject = JSONObject.fromObject(wapH5Template.getDataTemplate());
		JSONObject rowJsonObject = JSONObject.fromObject(jsonObject.get("row"));
		JSONArray positionJsonArray = JSONArray.fromObject(rowJsonObject.get("position"));

		//查询组件模板数据
		List<WapH5ModuleData> moduleDataList = wapH5ModuleDao.queryModuleDataByModuleId(moduleId);
		if(moduleDataList == null || moduleDataList.size() <= 0){
			moduleDataList = new ArrayList<WapH5ModuleData>();
			
			for (int j = 0; j < positionJsonArray.size(); j++) {
				String position = positionJsonArray.getString(j);
				WapH5ModuleData wapH5ModuleData = new WapH5ModuleData();
				wapH5ModuleData.setPosition(position);
//				Map<String, Object> rowMap = new HashMap<String, Object>();
				if(j == 0){
//					rowMap.put("0", "组"+(wapH5Module.getRowIndex())+"_"+moduleId);
					wapH5ModuleData.setGroupIndex("组"+(wapH5Module.getRowIndex())+"_"+moduleId);
				}
//				rowMap.put("1", position);
//				returnList.add(rowMap);
				moduleDataList.add(wapH5ModuleData);
			}
			return moduleDataList;
		}
		
		Map<String, String> dataTypes = DataType.getNameCnMap();
		for (int i=0; i<moduleDataList.size(); i++) {
			WapH5ModuleData wapH5ModuleData = moduleDataList.get(i);
//			Map<String, Object> rowMap = new HashMap<String, Object>();
			//设置组名，同一组只在第一行显示
			if(i == 0){
//				rowMap.put("0", "组"+(wapH5Module.getRowIndex())+"_"+wapH5ModuleData.getModuleId());
				wapH5ModuleData.setGroupIndex("组"+(wapH5Module.getRowIndex())+"_"+wapH5ModuleData.getModuleId());
			}
			wapH5ModuleData.setDataType(dataTypes.get(wapH5ModuleData.getDataType()));
			//老数据
			if(StringUtils.isBlank(wapH5ModuleData.getPosition())){
				wapH5ModuleData.setPosition(positionJsonArray.getString(i));
			}
//			for (int j = 1; j <= colnameJsonArray.size(); j++) {
//				String colname = colnameJsonArray.getString(j-1);
//				rowMap.put(String.valueOf(j), ObjectUtil.invokeGetMethod(wapH5ModuleData, colname));
//				if("DataType".equals(colname)){
//					rowMap.put(String.valueOf(j), DataType.getName(wapH5ModuleData.getDataType()));
//				}
//			}
//			returnList.add(rowMap);
		}
		return moduleDataList;
	}

//	@Override
//	public String importModuleData(Long moduleId,
//			List<Map<Object, Object>> listMap) {
//		//获取该组件模板数据规范
//		WapH5Template wapH5Template = wapH5TemplateDao.queryWapH5TemplateByModuleId(moduleId);
//		if(wapH5Template == null || StringUtils.isBlank(wapH5Template.getDataTemplate())){
//			return "组件模板数据异常";
//		}
//		
//		JSONObject jsonObject = JSONObject.fromObject(wapH5Template.getDataTemplate());
//		JSONObject subJsonObject = JSONObject.fromObject(jsonObject.get("column"));
//		//数据规范
//		JSONArray colnameJsonArray = JSONArray.fromObject(subJsonObject.get("colname"));
//		//保存组件数据集合
//		List<WapH5ModuleData> moduleDataList = new ArrayList<WapH5ModuleData>();
//		//listMap格式：[{0=卖场, 1=11011, 2=http://www.baidu.com/}, {0=卖场, 1=11012, 2=http://www.sohu.com/, 3=卖场, 4=11013, 5=http://www.xiu.com/}]
//		//根据数据规范来创建数据对象
//		for (int i = 1; i <= listMap.size(); i++) {
//			Map<Object, Object> dataMap = listMap.get(i-1);
//			//用来判断是否需要创建新的WapH5ModuleData对象
//			Set<String> columnSet = new HashSet<String>();
//			WapH5ModuleData moduleData = new WapH5ModuleData();
//			
//			for (int j = 0; j < colnameJsonArray.size(); j++) {
//				String colname = colnameJsonArray.getString(j);
//				
//				boolean addSuccess = columnSet.add(colname);
//				//加不进去，表示是新的一组数据
//				if(!addSuccess){
//					moduleDataList.add(moduleData);
//					//如果新的一组数据的第一列为空，则表示这一组不填数据
//					if(dataMap.get(j) == null || "".equals((String) dataMap.get(j))){
//						break;
//					}
//					columnSet = new HashSet<String>();
//					//新建set, 重新把上面的colname再加一次，否则会漏掉
//					columnSet.add(colname);
//					moduleData = new WapH5ModuleData();
//				}
//				//调用对象的set方法，把值设置进去
//				ObjectUtil.invokeSetMethod(moduleData, colname, dataMap.get(j));
//				if("DataType".equals(colname)){
//					moduleData.setDataType(DataType.getKey(moduleData.getDataType()));
//				}
//				moduleData.setModuleId(moduleId);
//				//设置在excel里是哪一行，导出时需要用
//				moduleData.setExcelRow(i);
//				
//				if(j == colnameJsonArray.size() - 1){
//					moduleDataList.add(moduleData);
//				}
//			}
//		}
//		if(moduleDataList.size() > 0){
//			//先删除该组件的数据
//			wapH5ModuleDao.deleteModuleData(moduleId);
//			//保存新的数据
//			for (Iterator<WapH5ModuleData> iterator = moduleDataList.iterator(); iterator
//					.hasNext();) {
//				WapH5ModuleData wapH5ModuleData = iterator.next();
//				wapH5ModuleDao.saveModuleData(wapH5ModuleData);
//			}
//		}
//		return "导入成功";
//	}
	
	@Override
	public String importModuleData(Long moduleId,
			List<?> dataList) {
		//获取该组件模板数据规范
		WapH5Template wapH5Template = wapH5TemplateDao.queryWapH5TemplateByModuleId(moduleId);
		if(wapH5Template == null || StringUtils.isBlank(wapH5Template.getDataTemplate())){
			return "组件模板数据异常";
		}
		//保存组件数据集合
		List<WapH5ModuleData> moduleDataList = null;
		//两种修改方式：导入excel和在线编辑
		Object obj = dataList.get(0);
		if(obj instanceof Map){
			moduleDataList = this.importModuleDataByMap(moduleId, wapH5Template, dataList);
			if(moduleDataList == null){
				return "该组件已失效，请重新下载模板后导入";
			}
		}else if(obj instanceof WapH5ModuleData){
			moduleDataList = this.importModuleDataByObj(moduleId, wapH5Template, dataList);
		}
		
		if(moduleDataList != null && moduleDataList.size() > 0){
			//先删除该组件的数据
			wapH5ModuleDao.deleteModuleData(moduleId);
			//保存新的数据
			for (Iterator<WapH5ModuleData> iterator = moduleDataList.iterator(); iterator
					.hasNext();) {
				WapH5ModuleData wapH5ModuleData = iterator.next();
				wapH5ModuleDao.saveModuleData(wapH5ModuleData);
			}
		}
		return "导入成功";
	}

	private List<WapH5ModuleData> importModuleDataByObj(Long moduleId,
		WapH5Template wapH5Template, List<?> listData) {
		List<WapH5ModuleData> dataList = new ArrayList<WapH5ModuleData>();
		for (Iterator iterator = listData.iterator(); iterator.hasNext();) {
			WapH5ModuleData wapH5ModuleData = (WapH5ModuleData) iterator.next();
			dataList.add(wapH5ModuleData);
		}
		return dataList;
	}

	private List<WapH5ModuleData> importModuleDataByMap(Long moduleId, 
			WapH5Template wapH5Template, List<?> listMap) {
		//校验提交的数据是否是当前组件的数据
		String groupValue = (String) ((Map<Object, Object>) listMap.get(0)).get(0);
		if(StringUtils.isNotBlank(groupValue) && groupValue.contains("_")){
			long commitModuleId = Long.parseLong(groupValue.split("_")[1]);
			if(commitModuleId != moduleId){
				return null;
			}
		}else{
			return null;
		}
		
		JSONObject jsonObject = JSONObject.fromObject(wapH5Template.getDataTemplate());
		JSONObject subJsonObject = JSONObject.fromObject(jsonObject.get("column"));
		//数据规范
		JSONArray colnameJsonArray = JSONArray.fromObject(subJsonObject.get("colname"));
		//保存组件数据集合
		List<WapH5ModuleData> moduleDataList = new ArrayList<WapH5ModuleData>();
		//listMap格式：[{0=卖场, 1=11011, 2=http://www.baidu.com/}, {0=卖场, 1=11012, 2=http://www.sohu.com/, 3=卖场, 4=11013, 5=http://www.xiu.com/}]
		
		int moduleDataBlankCount = 0; //一组数据空白行数
		Map<String, String> dataTypes = DataType.getKeyByNameCnMap();
		
		for (int i = 1; i <= listMap.size(); i++) {
			Map<Object, Object> dataMap = (Map<Object, Object>) listMap.get(i-1);
			WapH5ModuleData moduleData = new WapH5ModuleData();
			
			int rowBlankCount = 0; //excel每行的列上是否空白
			//根据数据规范来创建数据对象
			for (int j = 0; j < colnameJsonArray.size(); j++) {
				String colname = colnameJsonArray.getString(j);
				
				String value = (String) dataMap.get(j+1);
				if(StringUtils.isBlank(value)){
					rowBlankCount++;
				}
				
				//调用对象的set方法，把值设置进去，这里map从key为1开始取，key为0是组名信息
				ObjectUtil.invokeSetMethod(moduleData, colname, value);
				if("DataType".equals(colname)){
					moduleData.setDataType(dataTypes.get(moduleData.getDataType()));
				}
				moduleData.setModuleId(moduleId);
				//设置在excel里是哪一行
				moduleData.setExcelRow(i);
			}
			//表示该行没数据
			if(rowBlankCount == colnameJsonArray.size() - 1){
				moduleDataBlankCount++;
			}
			moduleDataList.add(moduleData);
		}
		//该组件没有导入数据
		if(moduleDataBlankCount == listMap.size()){
			moduleDataList.removeAll(moduleDataList);
		}
		return moduleDataList;
	}
	
	@Override
	public boolean deletecomponents(WapH5Module module) {
		int resInt = 0;
		resInt = wapH5ModuleDao.deleteById(module.getId());
		if(resInt >= 1){
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("direct", 1);
			paramMap.put("pageId", module.getPageId());
			paramMap.put("currentIndex", module.getRowIndex());
			wapH5ModuleDao.moveRowIndex(paramMap);
			wapH5ModuleDao.deleteModuleData(module.getId());
		}
		if(resInt >= 1){
			return true;
		}else{
			return false;
		}
	}

	
	@Override
	public int exChangeModuleRowIndex(Map<String, Object> paramMap) {
		WapH5Module module = new WapH5Module();
		module.setId(Long.parseLong(paramMap.get("firstModuleId").toString()));
		module.setRowIndex(Integer.parseInt(paramMap.get("firstToIndex").toString()));
		int resInt = 0;
		resInt = wapH5ModuleDao.updateRowIndex(module);
		if(resInt >= 1){
			module.setId(Long.parseLong(paramMap.get("secondModuleId").toString()));
			module.setRowIndex(Integer.parseInt(paramMap.get("secondToIndex").toString()));
			resInt = wapH5ModuleDao.updateRowIndex(module);
		}
		return resInt;
	}

	@Override
	public Map<String, Object> assembleModuleVeiw(Long moduleId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		WapH5Template wapH5Template = wapH5TemplateDao.queryWapH5TemplateByModuleId(moduleId);
		
		WapH5Module wapH5Module = wapH5ModuleDao.queryModuleInfoById(moduleId);
		
		if(wapH5Template != null){
			String template = wapH5Template.getTemplate();
			resultMap.put("template", template);
			resultMap.put("jsUrl", wapH5Template.getJsUrl());
			resultMap.put("cssUrl", wapH5Template.getCssUrl());
			resultMap.put("isLazyLoad", wapH5Module.getIsLazyLoad());
			resultMap.put("moduleId", moduleId);
			
			JSONObject jsonObject = JSONObject.fromObject(wapH5Template.getDataTemplate());
			String index = jsonObject.getString("index");
			resultMap.put("index", index);
			
			//查询出该组件的数据，然后按照数据行分组
			List<WapH5ModuleData> moduleDataList = wapH5ModuleDao.queryModuleDataByModuleId(moduleId);
			if(moduleDataList != null && moduleDataList.size() > 0){
				
				AbstractH5DataTypeHandler dataTypeHandler = 
					(AbstractH5DataTypeHandler) applicationContext.getBean(wapH5Template.getTemplateHandler());
				
				Map<String, Object> dataMap = dataTypeHandler.assembleModuleData(moduleDataList);
				
				resultMap.put("dataList", dataMap.get("data"));
			}
		}
		return resultMap;
	}

	@Override
	public List<WapH5Module> getModuleDataCount(Long pageId) {
		return wapH5ModuleDao.getModuleDataCount(pageId);
	}

	@Override
	public WapH5List queryPageInfoByModuleId(Long moduleId) {
		return wapH5ModuleDao.queryPageInfoByModuleId(moduleId);
	}
	
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public WapH5Module queryModuleInfoById(Long moduleId) {
		return wapH5ModuleDao.queryModuleInfoById(moduleId);
	}

	@Override
	public void updateModuleLazyLoad(Long moduleId, String isLazyLoad) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("moduleId", moduleId);
		paramMap.put("isLazyLoad", isLazyLoad);
		
		wapH5ModuleDao.updateModuleLazyLoad(paramMap);
	}

	@Override
	public WapH5Module getTargetModuleByRowIndex(long pageId, Integer zIndex) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("pageId", pageId);
		map.put("rowIndex", zIndex);
		return wapH5ModuleDao.getTargetModuleByRowIndex(map);
	}
	
}

