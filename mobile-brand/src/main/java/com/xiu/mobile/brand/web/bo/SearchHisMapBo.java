package com.xiu.mobile.brand.web.bo;

public class SearchHisMapBo {
	/*
	 * 搜索历史中如果出现key的值应该转换为value
	 */
	public String key;
	
	/*
	 * value值
	 */
	public String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
