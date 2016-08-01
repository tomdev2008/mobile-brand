package com.xiu.mobile.core.handler.h5.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.core.constants.EnumContants.DataType;
import com.xiu.mobile.core.dao.WapH5ModuleDao;
import com.xiu.mobile.core.handler.h5.AbstractH5DataTypeHandler;
import com.xiu.mobile.core.model.WapH5ModuleData;
import com.xiu.mobile.core.utils.ImageUtil;

/**
 * 按照excel导入的数据所在行分组list
 * @author Administrator
 *
 */
@Service("oneOnoneHandler")
public class OneOnOneHandler extends AbstractH5DataTypeHandler {
	
	private static Logger logger = Logger.getLogger(OneOnOneHandler.class);
	
	@Autowired
	WapH5ModuleDao wapH5ModuleDao;

//	@Override
//	public Map<String, Object> assembleModuleData(List<WapH5ModuleData> moduleDataList) {
//		Map<String, Object> returnMap = new HashMap<String, Object>();
//		
//		List<List<WapH5ModuleData>> dataLst = new ArrayList<List<WapH5ModuleData>>();
//		List<WapH5ModuleData> rowModuleDataList = new ArrayList<WapH5ModuleData>();
//		
//		for (int i=0; i<moduleDataList.size(); i++) {
//			WapH5ModuleData wapH5ModuleData = moduleDataList.get(i);
//			
//			if(i != 0 && moduleDataList.get(i-1).getExcelRow() != wapH5ModuleData.getExcelRow()){
//				dataLst.add(rowModuleDataList);
//				rowModuleDataList = new ArrayList<WapH5ModuleData>();
//			}
//			//如果是导入走秀码，则调接口查询商品信息
//			if(DataType.Goods.getKey().equals(wapH5ModuleData.getDataType())){
//				List<WapH5ModuleData> goodsInfoList = filterGoodsByMbrand(wapH5ModuleData.getDataId());
//				if(goodsInfoList != null && goodsInfoList.size() > 0){
//					BeanUtils.copyProperties(goodsInfoList.get(0), wapH5ModuleData);
//				}
//			}
//			rowModuleDataList.add(wapH5ModuleData);
//			if(i == moduleDataList.size() - 1){
//				dataLst.add(rowModuleDataList);
//			}
//		}
//		
//		returnMap.put("data", dataLst);
//		return returnMap;
//	}
	
	@Override
	public Map<String, Object> assembleModuleData(List<WapH5ModuleData> moduleDataList) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		List<List<WapH5ModuleData>> dataLst = new ArrayList<List<WapH5ModuleData>>();
		
		//根据position分组，套用模板freemarker。查询出来的数据按position排好序
		Set<Integer> rowSet = new HashSet<Integer>();
		List<WapH5ModuleData> rowData = null;
		List<WapH5ModuleData> rowModuleDataList = new ArrayList<WapH5ModuleData>();
		Map<String, String> dataTypeEns = DataType.getNameEnMap();
		for (int i=0; i<moduleDataList.size(); i++) {
			WapH5ModuleData wapH5ModuleData = moduleDataList.get(i);
			//处理下图片
			wapH5ModuleData.setImg(ImageUtil.getShowimageUrl(wapH5ModuleData.getImg()));
			
			String position = wapH5ModuleData.getPosition();
			if(StringUtils.isNotBlank(position) && position.contains("-")){
				Integer row = Integer.valueOf(position.split("-")[0]);
				boolean addSuccess = rowSet.add(row);
				if(addSuccess){
					if(i != 0){
						dataLst.add(rowData);
					}
					rowData = new ArrayList<WapH5ModuleData>();
				}
				//如果是导入走秀码，则调接口查询商品信息
				if(DataType.Goods.getKey().equals(wapH5ModuleData.getDataType())){
					List<WapH5ModuleData> goodsInfoList = filterGoodsByMbrand(wapH5ModuleData.getLink().trim());
					if(goodsInfoList != null && goodsInfoList.size() > 0){
						BeanUtils.copyProperties(goodsInfoList.get(0), wapH5ModuleData);
						//商品类型，处理下图片
						wapH5ModuleData.setImg(ImageUtil.getShowimageUrl(wapH5ModuleData.getImg()));
					}
				}
				rowData.add(wapH5ModuleData);
				
				if(i == moduleDataList.size() - 1){
					dataLst.add(rowData);
				}
			}else{
				//一期老数据
				if(i != 0 && moduleDataList.get(i-1).getExcelRow() != wapH5ModuleData.getExcelRow()){
					dataLst.add(rowModuleDataList);
					rowModuleDataList = new ArrayList<WapH5ModuleData>();
				}
				//如果是导入走秀码，则调接口查询商品信息
				if(DataType.Goods.getKey().equals(wapH5ModuleData.getDataType())){
					List<WapH5ModuleData> goodsInfoList = filterGoodsByMbrand(wapH5ModuleData.getDataId());
					if(goodsInfoList != null && goodsInfoList.size() > 0){
						WapH5ModuleData goodsInfo = goodsInfoList.get(0);
						goodsInfo.setExcelRow(wapH5ModuleData.getExcelRow());
						BeanUtils.copyProperties(goodsInfo, wapH5ModuleData);
						//商品类型，处理下图片
						wapH5ModuleData.setImg(ImageUtil.getShowimageUrl(wapH5ModuleData.getImg()));
					}
				}
				rowModuleDataList.add(wapH5ModuleData);
				if(i == moduleDataList.size() - 1){
					dataLst.add(rowModuleDataList);
				}
			}
			//数据类型转换，方便页面上用
			wapH5ModuleData.setDataType(dataTypeEns.get(wapH5ModuleData.getDataType()));
		}
		
//		for (int i=0; i<moduleDataList.size(); i++) {
//			WapH5ModuleData wapH5ModuleData = moduleDataList.get(i);
//			
//			if(i != 0 && moduleDataList.get(i-1).getExcelRow() != wapH5ModuleData.getExcelRow()){
//				dataLst.add(rowModuleDataList);
//				rowModuleDataList = new ArrayList<WapH5ModuleData>();
//			}
//			//如果是导入走秀码，则调接口查询商品信息
//			if(DataType.Goods.getKey().equals(wapH5ModuleData.getDataType())){
//				List<WapH5ModuleData> goodsInfoList = filterGoodsByMbrand(wapH5ModuleData.getDataId());
//				if(goodsInfoList != null && goodsInfoList.size() > 0){
//					BeanUtils.copyProperties(goodsInfoList.get(0), wapH5ModuleData);
//				}
//			}
//			rowModuleDataList.add(wapH5ModuleData);
//			if(i == moduleDataList.size() - 1){
//				dataLst.add(rowModuleDataList);
//			}
//		}
		
		returnMap.put("data", dataLst);
		return returnMap;
	}
	
}
