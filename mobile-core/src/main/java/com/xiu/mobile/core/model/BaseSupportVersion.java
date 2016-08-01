package com.xiu.mobile.core.model;

import java.io.Serializable;
import java.util.List;
/**
 * 支持版本工具类
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public abstract class BaseSupportVersion implements Serializable{
	
	private Integer isShow;//是否显示 0是 1否
	
	private List<FindSupportVersion> listVersion;

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public List<FindSupportVersion> getListVersion() {
		return listVersion;
	}

	public void setListVersion(List<FindSupportVersion> listVersion) {
		this.listVersion = listVersion;
	}
	
	

}
