package com.xiu.mobile.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiu.mobile.core.constants.ErrConstants;
import com.xiu.mobile.core.dao.SysParamsDao;
import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.model.SysParamsMgtVo;
import com.xiu.mobile.core.service.ISysParamsService;
import com.xiu.mobile.core.utils.DateUtil;
import com.xiu.mobile.core.utils.ImageUtil;

/**
 * 
* @Description: TODO(系统参数管理) 
* @author haidong.luo@xiu.com
* @date 2016年6月21日 下午5:08:38 
*
 */
@Transactional
@Service("sysParamsMgtVoService")
public class SysParamsServiceImpl implements ISysParamsService {
	
	//日志
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(SysParamsServiceImpl.class);
	
    @Autowired
    private SysParamsDao sysParamsDao;
    
	@Override
	public Map getSysParamsList(Map<Object, Object> params,Page<?> page) {
		Map resultMap=new HashMap();//结果集
		Boolean resultStatus=false;
		List<SysParamsMgtVo> SysParamsMgtVolist=new ArrayList<SysParamsMgtVo>();

			params.put("pageMin",page.getFirstRecord());
			params.put("pageSize", page.getPageSize());
			params.put("pageMax", page.getEndRecord());

		//查询列表
		 SysParamsMgtVolist= sysParamsDao.getSysParamsMgtVoList(params);
	     page.setTotalCount(Integer.valueOf(sysParamsDao.getSysParamsMgtVoTotalCount(params)));
		//成功
		resultStatus=true;
		resultMap.put("page", page);
		resultMap.put("status", resultStatus);
		resultMap.put("msg","");
		resultMap.put("resultInfo", SysParamsMgtVolist);
		return resultMap;
	}

	@Override
	public Map save(Map params) {
		Map resultMap=new HashMap();//结果集
		boolean isSuccess =false;
		SysParamsMgtVo SysParamsMgtVo=(SysParamsMgtVo)params.get("model");
		//检查重复
		int num=sysParamsDao.isExsit(SysParamsMgtVo);
		if(num>0){
			resultMap.put("status", isSuccess);
			resultMap.put("msg", "该配置项已经存在!");
			return 	resultMap; 
		}
		//增加
		 int updateNum=sysParamsDao.save(SysParamsMgtVo);
		 if(updateNum>0){
			 isSuccess=true;
		 }
		 resultMap.put("status", isSuccess);
		 resultMap.put("msg", "");
		 return resultMap;
	}

	@Override
	public Map update(Map params) {
		Map resultMap=new HashMap();//结果集
		 boolean isSuccess =false;
		 SysParamsMgtVo adv=(SysParamsMgtVo)params.get("model");
		//检查重复
		int num=sysParamsDao.isExsit(adv);
		if(num>0){
			resultMap.put("status", isSuccess);
			resultMap.put("msg", "该配置项已经存在!");
			return 	resultMap; 
		}
		 
		 int updateNum=sysParamsDao.update(adv);
		 if(updateNum>0){
			 isSuccess=true;
		 }
		 resultMap.put("status", isSuccess);
		 return resultMap;
	}

	@Override
	public int delete(SysParamsMgtVo SysParamsMgtVo) {
		// TODO Auto-generated method stub
		 int result=0;
	        try {
	            if(sysParamsDao.delete(SysParamsMgtVo)>0){
	            	
	                result=0;
	            }else{
	                result=-1;
	            }
	        } catch (Throwable e) {
	            result=-1;
	            LOGGER.error("删除系统参数异常！",e);
	            throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_WAP_ERR, "删除系统参数异常！");
	        }
	        return result;
	}

	@Override
	public SysParamsMgtVo getSysParamsById(Long id) {
		// TODO Auto-generated method stub
		 	SysParamsMgtVo SysParamsMgtVo=null;
	        try {
	        	SysParamsMgtVo=sysParamsDao.getSysParamsMgtVoById(id);
	        } catch (Throwable e) {
	            LOGGER.error("根据系统参数ID查询系统参数异常！",e);
	            throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_WAP_ERR, "根据系统参数ID查询系统参数异常！");
	        }
	        return SysParamsMgtVo;
	}


}
