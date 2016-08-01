package com.xiu.mobile.core.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EnumContants {

	/**
	 * 数据类型
	 * @author Administrator
	 *
	 */
	public enum DataType{
		Url("0", "URL", "URL"),
		Topic("1", "话题", "topic"),
		Xiu("2", "秀", "show"),
		Sales("3", "卖场", "sales"),
		Store("4", "卖场集", "store"),
		Goods("5", "商品", "good"),
		GoodsCat("6", "商品分类", "goodCommodity"),
		Subject("7", "专题", "subject"),
		ModuleTitle("8", "组件标题", "moduletitle");
		
		private DataType(String key, String nameCn, String nameEn){
			this.key = key;
			this.nameCn = nameCn;
			this.nameEn = nameEn;
		}
		private String key;
		private String nameCn;
		private String nameEn;
		
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getNameCn() {
			return nameCn;
		}
		public void setNameCn(String nameCn) {
			this.nameCn = nameCn;
		}
		public String getNameEn() {
			return nameEn;
		}
		public void setNameEn(String nameEn) {
			this.nameEn = nameEn;
		}
		public static List<String> getDataTypeNameCnList(){
			List<String> nameCns = new ArrayList<String>();
			DataType[] dataTypes = DataType.values();
			for (int i = 0; i < dataTypes.length; i++) {
				nameCns.add(dataTypes[i].nameCn);
			}
			return nameCns;
		}
		
		public static Map<String, String> getKeyByNameCnMap(){
			Map<String, String> map = new HashMap<String, String>();
			for(DataType dataType : DataType.values()){
				map.put(dataType.nameCn, dataType.key);
			}
			return map;
		}
		
		public static Map<String, String> getNameEnMap(){
			Map<String, String> map = new HashMap<String, String>();
			for(DataType dataType : DataType.values()){
				map.put(dataType.key, dataType.nameEn);
			}
			return map;
		}
		
		public static Map<String, String> getNameCnMap(){
			Map<String, String> map = new HashMap<String, String>();
			for(DataType dataType : DataType.values()){
				map.put(dataType.key, dataType.nameCn);
			}
			return map;
		}
	}
}
