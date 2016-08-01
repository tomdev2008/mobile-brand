package com.xiu.mobile.core.service;

import java.util.Map;

import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.model.SysParamsMgtVo;


/**
* @Description: TODO(系统参数) 
* @author haidong.luo@xiu.com
* @date 2016年6月21日 下午4:06:41 
*
 */
public interface ISysParamsService {
	
	public Map getSysParamsList(Map<Object, Object> map,Page<?> page);
	
	public Map save(Map params);
	
	public Map update(Map params);
	
	public int delete(SysParamsMgtVo sysParamsMgtVo);

	public SysParamsMgtVo getSysParamsById(Long id);
	
	
}
