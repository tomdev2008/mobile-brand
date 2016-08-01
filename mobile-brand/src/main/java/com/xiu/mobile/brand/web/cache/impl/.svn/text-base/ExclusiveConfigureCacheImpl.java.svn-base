package com.xiu.mobile.brand.web.cache.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.xiu.mobile.brand.web.cache.ExclusiveConfigureCache;

public class ExclusiveConfigureCacheImpl implements ExclusiveConfigureCache{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExclusiveConfigureCacheImpl.class);
	
	/**
	 * man/woman对应的分类集合
	 */
	private static volatile Map<String, List<String>> CATEGORY_SEX = new ConcurrentHashMap<String, List<String>>();
	/**
	 * 尺码名称与尺码值的对应MAP
	 */
	private static volatile Map<String, String> SIZE_VALUE = new ConcurrentHashMap<String, String>();
	/**
	 * 男/女分类和具体尺寸对应关系集合
	 */
	private static volatile Map<String, List<String>> CATEGORY_SIZE = new ConcurrentHashMap<String, List<String>>();
	/**
	 * 风格与目标对应集合
	 */
	private static volatile Map<String, List<String>> STYLE_SEX = new ConcurrentHashMap<String, List<String>>();
	/**
	 * 性别风格对应的ID目标类型
	 */
	private static volatile Map<String, String> SEX_STYLE_TARGET = new ConcurrentHashMap<String, String>();
	private static ExclusiveConfigureCacheImpl instance = null;
	private static Properties properties = new Properties();
	private void init(){
		InputStream is = null;
		try {
			LOGGER.info("loading exclusive.properties ......");
			is = new FileInputStream(new File(ExclusiveConfigureCacheImpl.class.getResource("/conf/exclusive.properties").getFile()));
			properties.load(is);
			is.close();
		} catch (IOException e) {
			LOGGER.error("loading exclusive.properties occurs an error !",e);
			e.printStackTrace();
		}finally{
			if (is != null) {
				is = null;
			}
		}
	}
	private ExclusiveConfigureCacheImpl(){
		init();
		handleConfigure();
	}
	public static synchronized ExclusiveConfigureCacheImpl getInstance(){
		if (instance == null) {
			LOGGER.debug("initializing ExclusiveConfigureCacheImpl instance .....");
			instance = new ExclusiveConfigureCacheImpl();
		}
		return instance;
	}
	private void handleConfigure(){
		if (properties == null) {
			return ;
		}
		synchronized (properties) {
			//临时分类-性别对应关系
			Map<String, List<String>> TEMP_CATEGORY_SEX = new ConcurrentHashMap<String, List<String>>();
			//临时尺码名称-值对应关系
			Map<String, String> TEMP_SIZE_VALUE = new ConcurrentHashMap<String, String>();
			//临时分类与尺码对应关系
			Map<String, List<String>> TEMP_CATEGORY_SIZE = new ConcurrentHashMap<String, List<String>>();
			//临时性别风格目标ID缓存
			Map<String, List<String>> TEMP_STYLE_SEX = new ConcurrentHashMap<String, List<String>>();
			//临时性别风格对应的目标类型
			Map<String, String> TEMP_SEX_STYLE_TARGET = new ConcurrentHashMap<String, String>();
			for (Iterator<Object> iterator = properties.keySet().iterator();iterator.hasNext();) {
				String key = String.valueOf(iterator.next());
				if (properties.get(key) == null || "".equals(properties.get(key))) {
					continue;
				}
				if(key.startsWith("category.sex.")){
					TEMP_CATEGORY_SEX.put(key, Arrays.asList(properties.getProperty(key).split(",")));
				}else if(key.startsWith("category.size.")){
					TEMP_CATEGORY_SIZE.put(key, Arrays.asList(properties.getProperty(key).split(",")));
				}else if(key.equalsIgnoreCase("style.man.target") || key.equalsIgnoreCase("style.woman.target")){
					TEMP_SEX_STYLE_TARGET.put(key, properties.getProperty(key));
				}else if(key.startsWith("style.man.") || key.startsWith("style.woman.")){
					TEMP_STYLE_SEX.put(key, Arrays.asList(properties.getProperty(key).split(",")));
				}else if (key.startsWith("system.size.")) {
					TEMP_SIZE_VALUE.put(key, properties.getProperty(key));
				}
			}
			CATEGORY_SEX = TEMP_CATEGORY_SEX;
			CATEGORY_SIZE = TEMP_CATEGORY_SIZE;
			SEX_STYLE_TARGET = TEMP_SEX_STYLE_TARGET;
			STYLE_SEX = TEMP_STYLE_SEX;
			SIZE_VALUE = TEMP_SIZE_VALUE;
			TEMP_CATEGORY_SEX = null;
			TEMP_CATEGORY_SIZE = null;
			TEMP_SEX_STYLE_TARGET = null;
			TEMP_STYLE_SEX = null;
			TEMP_SIZE_VALUE = null;
		}
		
	}
	@Override
	public List<String> getSexCategory(String sex) {
		List<String> temp = CATEGORY_SEX.get("category.sex."+sex);
		if (temp != null) {
			return new ArrayList<String>(temp);
		}
		return null;
	}
	@Override
	public List<String> getNormalSexCategory() {
		List<String> temp  = CATEGORY_SEX.get("category.sex.normal");
		if (temp != null) {
			return new ArrayList<String>(temp);
		}
		return null;
	}
	@Override
	public List<String> getSizeValues(List<String> sizeNames) {
		List<String> temList = new ArrayList<String>();
		for (String name:sizeNames) {
			temList.add(SIZE_VALUE.get("system.size."+name));
		}
		return temList;
	}
	@Override
	public List<String> getSizeCategories(String sex, String sizeCategoryNumber) {
		List<String> temp = CATEGORY_SIZE.get("category.size."+sex+"."+sizeCategoryNumber);
		if (temp !=null) {
			return new ArrayList<String>(temp);
		}
		return null;
	}
	@Override
	public List<String> getStyleTargets(String sex, String styleNumber) {
		List<String> temp = STYLE_SEX.get("style."+sex+"."+styleNumber);
		return temp ==null ?null : new ArrayList<String>(temp);
	}
	@Override
	public String getStyleTargetType(String sex) {
		return SEX_STYLE_TARGET.get("style."+sex+".target");
	}
}
