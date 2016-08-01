package com.xiu.mobile.brand.web.cache;

import java.util.List;

public interface ExclusiveConfigureCache {
	/**
	 * 获取性别对应的分类信息
	 * @param sex
	 * @return
	 */
	List<String> getSexCategory(String sex);
	/**
	 * 获取中性分类
	 * @return 中性分类ID集合
	 */
	List<String> getNormalSexCategory();
	/**
	 * 根据尺寸名称，获取对应的尺寸值
	 * @param sizeNames
	 * @return
	 */
	List<String> getSizeValues(List<String> sizeNames);
	/**
	 * 根据尺码分类对应的编码，获取尺寸对应的分类
	 * @param sizeCategoryNumber 尺码分类对应的编码
	 * @return
	 */
	List<String> getSizeCategories(String sex, String sizeCategoryNumber);
	/**
	 * 根据风格对应的性别及分类编号，获取目标值
	 * @param targetType
	 * @param styleNumber
	 * @return
	 */
	List<String> getStyleTargets(String sex, String styleNumber);
	/**
	 * 根据性别获取风格对应的类型
	 * @param sex
	 * @return
	 */
	String getStyleTargetType(String sex);
}
