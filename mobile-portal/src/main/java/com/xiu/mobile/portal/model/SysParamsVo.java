package com.xiu.mobile.portal.model;

import java.io.Serializable;

import com.xiu.mobile.core.model.BaseObject;

/**
 * 
* @Description: TODO(系统参数) 
* @author haidong.luo@xiu.com
* @date 2016年6月16日 上午10:56:32 
*
 */
public class SysParamsVo  extends BaseObject implements Serializable {


	private static final long serialVersionUID = -2357293713712327086L;

	private Long id;
	
	private String paramKey;
	
	private String paramValue;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	
}
