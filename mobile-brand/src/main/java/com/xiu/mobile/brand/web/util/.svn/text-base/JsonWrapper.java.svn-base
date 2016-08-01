package com.xiu.mobile.brand.web.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.ser.BeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonWrapper {
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonWrapper.class);
	//定义可以过滤的对象
	public static final String CATALOG_BO_FILTER = "catalogBoFilter";
	private Head head = new Head(CodeEnum.SUCCESS);//默认success
	private Map<String,Object> data = new HashMap<String, Object>();
	private SimpleFilterProvider filters = null;
	public JsonWrapper(){
		super();
	}
	public Head getHead() {
		return head;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	public void setFilters(Map<String, String[]> simpleBeanProperties){
		Map<String, BeanPropertyFilter> tempMap = new HashMap<String, BeanPropertyFilter>();
		for (Iterator<String> keyIter = simpleBeanProperties.keySet().iterator(); keyIter.hasNext();) {
			tempMap.put(keyIter.next(), SimpleBeanPropertyFilter.serializeAllExcept(simpleBeanProperties.get(keyIter.next())));
		}
		filters = new SimpleFilterProvider(tempMap);
	}
	
	public void addFilters(Map<String, String[]> simpleBeanProperties){
		if (filters == null) {
			filters = new SimpleFilterProvider();
		}
		for (Iterator<String> keyIter = simpleBeanProperties.keySet().iterator(); keyIter.hasNext();) {
			String key = keyIter.next();
			filters.addFilter(key, SimpleBeanPropertyFilter.serializeAllExcept(simpleBeanProperties.get(key)));
		}
	}

	public String toJson(){
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
		mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
		if (filters != null) {
			mapper.setFilters(filters);
		}else {
			mapper.setFilters(new SimpleFilterProvider().setFailOnUnknownId(false));
		}
		
//		mapper.setSerializationInclusion(Inclusion.NON_NULL);
		Map<String, Object> tempMap = new LinkedHashMap<String, Object>();
		tempMap.put("head", this.head);
		for (Iterator<Map.Entry<String, Object>> iter = this.data.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<String, Object> entry = iter.next();
			tempMap.put(entry.getKey(), entry.getValue());
		}
		try {
			return mapper.writeValueAsString(tempMap);
		} catch (JsonGenerationException e) {
			LOGGER.error("jsonGenerationException---->", e);
			head = new Head(CodeEnum.DIGITA_ERROR);
		} catch (JsonMappingException e) {
			LOGGER.error("jsonMappingException---->", e);
			head = new Head(CodeEnum.DIGITA_ERROR);
		} catch (IOException e) {
			LOGGER.error("IOException---->", e);
			head = new Head(CodeEnum.DIGITA_ERROR);
		}
		try {
			return mapper.writeValueAsString(head);
		} catch (Exception e) {
			LOGGER.error("Exception---->", e);
			e.printStackTrace();
		}
		return "{}";
	};

	public String toJsonCallback(String callback) {
		StringBuilder sb = new StringBuilder();
		sb.append(callback).append("(").append(toJson()).append(")");
		return sb.toString();
	};
	
	public String render(String callback){
		if(StringUtils.isEmpty(callback)) {
			return toJson();
		} else {
			return toJsonCallback(callback);
		}
	}
	public String render(String callback,String[] properties){
		if(StringUtils.isEmpty(callback)) {
			return toJson();
		} else {
			return toJsonCallback(callback);
		}
	}
	public class Head{
		private String code;
		private String desc;
		public Head(){
			super();
		}
		public Head(CodeEnum codeEnum){
			this.code = codeEnum.code;
			this.desc = codeEnum.desc;
		}
		public Head(String code,String desc){
			this.code = code;
			this.desc = desc;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		
		public void setCodeEnum(CodeEnum codeEnum) {
			this.code = codeEnum.code;
			this.desc = codeEnum.desc;
		}
	}
	
	public enum CodeEnum{
		SUCCESS("0","SUCCESS"),
		LACK_PARAM("1001","请求参数缺失"),
		ILLEGAL_PARAM("1002","非法的参数"),
		CATA_NOT_FOUND("2001","不存在该分类"),
		BRAND_NOT_FOUND("2002","不存在该品牌"),
		NOT_FOUND("2003","数据不存在"),
		ERROR("3999","服务器端错误"),
		DIGITA_ERROR("4000","数据转换错误");
		private String code;
		private String desc;
		
		private CodeEnum(String code,String desc){
			this.code = code;
			this.desc = desc;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
	}
}
