package com.xiu.mobile.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiu.mobile.core.constants.ErrConstants;
import com.xiu.mobile.core.dao.DataBrandDao;
import com.xiu.mobile.core.dao.WellChosenBrandDao;
import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.core.model.DataBrandBo;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.model.WellChosenBrandBo;
import com.xiu.mobile.core.model.WellChosenBrandVo;
import com.xiu.mobile.core.service.IWellChosenBrandService;


@Transactional
@Service("wellChosenBrandService")
public class WellChosenBrandServiceImpl implements IWellChosenBrandService{
	// 日志
	private final Logger logger = Logger.getLogger(WellChosenBrandServiceImpl.class);
		
	@Autowired
	private WellChosenBrandDao wellChosenBrandDao;
	@Autowired
	private DataBrandDao dataBrandDao;
	@Override
	public int addWellChosenBrand(WellChosenBrandBo wellChosenBrand)throws Exception {
		int result=0;
		//根据brandId查询，判断品牌是否已经存在
		WellChosenBrandBo oldWellChosenBrand=wellChosenBrandDao.getWellChosenBrandByBrandId(wellChosenBrand.getBrandId());
		if(oldWellChosenBrand!=null){
			result=1;
		}else{
			if(wellChosenBrandDao.addWellChosenBrand(wellChosenBrand)>0){
				result=0;
			}else{
				result=-1;
			}
		}
		
		return result;
	}

	@Override
	public int updateWellChosenBrandByBrandId(Long id, Long orderSequence,String bannerPic)throws Exception {
		int result=0;
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("id", id);
		map.put("orderSequence", orderSequence);
		map.put("bannerPic", bannerPic);
		if(wellChosenBrandDao.updateWellChosenBrandByBrandId(map)>0){
			result=0;
		}else{
			result=-1;
		}
		System.out.println("修改参数："+id+"=="+orderSequence+"=="+bannerPic+"=="+result);
		return result;
	}

	@Override
	public int deleteWellChosenBrandByBrandId(Long id) throws Exception{
		int result=0;
		if(wellChosenBrandDao.deleteWellChosenBrandByBrandId(id)>0){
			result=0;
		}else{
			result=-1;
		}
		
		return result;
	}

	@Override
	public WellChosenBrandVo getWellChosenBrandVoList(Long id)throws Exception {
		List<WellChosenBrandVo> wellChosenBrandList=wellChosenBrandDao.getWellChosenBrandVoList(id);
		if(null!=wellChosenBrandList&&wellChosenBrandList.size()>0){
			return wellChosenBrandList.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<WellChosenBrandVo> getWellChosenBrandList(
			Map<Object, Object> map, Page<?> page) throws Exception{
		List<WellChosenBrandVo> wellChosenBrandVoList=new ArrayList<WellChosenBrandVo>();
		int pageMax = page.getPageNo() * page.getPageSize()+1;
		int pageMin = (page.getPageNo()-1)*page.getPageSize()+1;
		
		map.put("pageMax", pageMax);
		map.put("pageMin", pageMin);
		//获得分页列表
		 wellChosenBrandVoList=wellChosenBrandDao.getWellChosenBrandList(map);
		 // 获取总记录数
		 int totalCount = wellChosenBrandDao.getWellChosenBrandListCount(map); 
	     page.setTotalCount(Integer.valueOf(String.valueOf(totalCount)));
		if(null!=wellChosenBrandVoList&&wellChosenBrandVoList.size()>0){
			return wellChosenBrandVoList;
		}else{
			return new ArrayList<WellChosenBrandVo>();
		}
	}

	@Override
	public List<DataBrandBo> getBrandList(String brandName) throws Exception{
		List<DataBrandBo> brandList=new ArrayList<DataBrandBo>();
		brandList=dataBrandDao.getBrandList(brandName);
		if(null!=brandList&&brandList.size()>0){
			return brandList;
		}else{
			return null;
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int importWellChosenBrand(Map<String, Map<Object, Object>> data,
			Long createUserId) throws Exception{
		int count = 0;
		int totalCount = data.size();
		long startTime = System.currentTimeMillis();
		try{
			logger.info("=======================开始导入精选品牌====== 总数据量：" + totalCount);
			
			// 去查询商品中心，把不存在的brandCode去除
			List<String> brandCode = new ArrayList<String>();
			Iterator itBrandCode = data.entrySet().iterator();
			while(itBrandCode.hasNext()){
				Map.Entry entry = (Map.Entry) itBrandCode.next();
				String sn = (String) entry.getKey();
				brandCode.add(sn);
			}
			List<String> brandCodeList = dataBrandDao.checkBrandCode(brandCode);
			Map<String, Map<Object,Object>> realData = new HashMap<String, Map<Object,Object>>();
			if(brandCodeList !=null && !brandCodeList.isEmpty()){
				// 筛选brandCode
				for(String code : brandCodeList){
					realData.put(code, data.get(code));
				}
			}
			
			// 把表中正在进行或未开始的记录设为被替代(replace = Y)
			wellChosenBrandDao.updateReplace(brandCodeList);
			
			// 开始导入
			Iterator it = realData.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				// 品牌编码
				String code = (String) entry.getKey();
				Map<Object, Object> map = (Map<Object, Object>) entry.getValue();
				// 排序
				String orderSequence = map.get(1).toString();
				// 插入数据
				// 查询商品信息
				DataBrandBo dataBrand = dataBrandDao.getBrandByCode(code);
				WellChosenBrandBo wellChosenBrand = new WellChosenBrandBo();
				wellChosenBrand.setBrandCode(code);
				wellChosenBrand.setBrandId(dataBrand.getBrandId());
				wellChosenBrand.setCreateDate(new Date());
				wellChosenBrand.setCreateBy(createUserId);
				wellChosenBrand.setOrderSequence(Long.parseLong(orderSequence));
				wellChosenBrand.setDeleted("N");
				wellChosenBrand.setReplaced("N");
				wellChosenBrand.setBannerPic("");
				wellChosenBrandDao.addWellChosenBrand(wellChosenBrand);
				count++;
			}
		}catch(Exception e){
			logger.error("导入精选品牌异常", e);
			throw ExceptionFactory.buildBaseException(ErrConstants.BusinessErrorCode.BIZ_WELL_CHOSEN_BRAND_ERR, e);
		}
		long endTime = System.currentTimeMillis();
		logger.info("=============== 导入精选品牌完成 =========== 成功导入条数:" 
		+ count + " 花费时间:" + ((endTime - startTime)/1000) + " 秒");
		return count;
	}

	@Override
	public List<String> checkBrandCode(List<String> bcList) throws Exception{
		return dataBrandDao.checkBrandCode(bcList);
	}

	@Override
	public int deleteWellChosenBrandByIds(List<Long> ids) throws Exception{
		int result=0;
		if(wellChosenBrandDao.deleteWellChosenBrandByIds(ids)>0){
			result=0;
		}else{
			result=-1;
		}
		
		return result;
	}

}
