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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.core.constants.EnumContants.DataType;
import com.xiu.mobile.core.dao.WapH5ModuleDao;
import com.xiu.mobile.core.handler.h5.AbstractH5DataTypeHandler;
import com.xiu.mobile.core.model.WapH5ModuleData;
import com.xiu.mobile.core.utils.ImageUtil;

/**
 * 根据导入的卖场ID查询卖场信息
 * @author Administrator
 *
 */
@Service("storeDataTypeHandler")
public class StoreDataTypeHandler extends AbstractH5DataTypeHandler {
	
	private static Logger logger = Logger.getLogger(StoreDataTypeHandler.class);
	
	@Autowired
	WapH5ModuleDao wapH5ModuleDao;
	
	@Override
	public Map<String, Object> assembleModuleData(List<WapH5ModuleData> moduleDataList) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		List<List<WapH5ModuleData>> dataLst = new ArrayList<List<WapH5ModuleData>>();
		
		Map<String, String> dataTypeEns = DataType.getNameEnMap();
		
		//根据position分组，套用模板freemarker。查询出来的数据按position排好序
		Set<Integer> rowSet = new HashSet<Integer>();
		List<WapH5ModuleData> rowData = null;
		Long salesId = null; //卖场ID
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
				rowData.add(wapH5ModuleData);
				//卖场比较特殊，展示是三行，但运营只要导入两行，一行标题，一行卖场ID，程序需要根据卖场ID把第三行的前15个商品查出来
				if(DataType.Sales.getKey().equals(wapH5ModuleData.getDataType())){
					salesId = Long.valueOf(wapH5ModuleData.getLink().trim());
				}
				if(i == moduleDataList.size() - 1){
					dataLst.add(rowData);
				}
			}else{
				//一期老数据
				rowData = new ArrayList<WapH5ModuleData>();
				rowData.add(wapH5ModuleData);
				dataLst.add(rowData);
				if(DataType.Sales.getKey().equals(wapH5ModuleData.getDataType())){
					salesId = Long.valueOf(wapH5ModuleData.getDataId());
				}
			}
			//数据类型转换，方便页面上用
			wapH5ModuleData.setDataType(dataTypeEns.get(wapH5ModuleData.getDataType()));
		}
		//根据卖场ID查询商品
		List<String> goodsSns = 
			wapH5ModuleDao.queryStoreGoodsByStoreId(salesId);
		
		if(goodsSns != null && goodsSns.size() > 0){
			StringBuilder builder = new StringBuilder();
			for (Iterator<String> iterator = goodsSns.iterator(); iterator.hasNext();) {
				String goodsSn = iterator.next();
				builder.append(goodsSn).append(",");
			}
			builder.deleteCharAt(builder.length() - 1);
			
			List<WapH5ModuleData> goodsList = filterGoodsByMbrand(builder.toString());
			
			if(goodsList != null && goodsList.size() > 0){
				for (Iterator<WapH5ModuleData> iterator = goodsList.iterator(); iterator
						.hasNext();) {
					WapH5ModuleData wapH5ModuleData = iterator.next();
					//数据类型转换，方便页面上用
					wapH5ModuleData.setDataType(dataTypeEns.get(wapH5ModuleData.getDataType()));
					//商品类型，处理下图片
					wapH5ModuleData.setImg(ImageUtil.getShowimageUrl(wapH5ModuleData.getImg()));
				}
			}
			dataLst.add(goodsList);
		}
		
		returnMap.put("data", dataLst);
		return returnMap;
	}
}
