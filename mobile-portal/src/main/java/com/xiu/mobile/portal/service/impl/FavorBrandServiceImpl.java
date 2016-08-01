package com.xiu.mobile.portal.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.core.dao.DataBrandDao;
import com.xiu.mobile.core.model.DataBrandBo;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.util.ImageServiceConvertor;
import com.xiu.mobile.portal.dao.FavorBrandDao;
import com.xiu.mobile.portal.model.BrandTopicVo;
import com.xiu.mobile.portal.model.FavorBrandBo;
import com.xiu.mobile.portal.service.IFavorBrandService;
/**
 * <p>
 * ************************************************************** 
 * @Description: 收藏品牌业务逻辑层
 * @AUTHOR wangchengqun
 * @DATE 2014-5-30
 * ***************************************************************
 * </p>
 */
@Service("favorBrandService")
public class FavorBrandServiceImpl implements IFavorBrandService{

	@Autowired
	private FavorBrandDao favorBrandDao;
	@Autowired
	private DataBrandDao dataBrandDao;
	
	@Override
	public int addFavorBrand(FavorBrandBo favorBrand) throws Exception{
		int result = 0;
		result =favorBrandDao.insertFavorBrand(favorBrand)>=0?0:-1;
		//把品牌秀放入到秀关注表中
		favorBrandDao.addNewShowByLabel(favorBrand);
		return result;
	}
	@Override
	public int delFavorBrand(HashMap<String, Object> valMap)throws Exception{
		int result = 0;
		result =favorBrandDao.deleteBrand(valMap)>=0?0:-1;
		//删除关注秀表中的信息
		favorBrandDao.deleteLabelConcern(valMap);
		return result;
	}
	@Override
	public List<FavorBrandBo> getBrandByUserIdAndBrandId(
			HashMap<String, Object> valMap)throws Exception {
		 List<FavorBrandBo> favorBrandList=favorBrandDao.getBrandByUserIdAndBrandId(valMap);
		return favorBrandList;
	}
	@Override
	public List<DataBrandBo> getBrandList(Map<String, Object> params)throws Exception{
		List<DataBrandBo> dataBrandList=dataBrandDao.getDataBrandByBrandId(params);
		return dataBrandList;
		
	}
	@Override
	public List<FavorBrandBo> getFavorBrand(HashMap<String, Object> valMap)throws Exception{
		 List<FavorBrandBo> favorBrandList=favorBrandDao.getBrandByPage(valMap);
		return favorBrandList;
	}
	@Override
	public int getFavorBrandCount(Long userId)throws Exception {
		return favorBrandDao.getBrandCount(userId);
	}


	@Override
	public List<BrandTopicVo> queryBrandTopicList(HashMap<String, Object> valMap)throws Exception {
		List<BrandTopicVo> brandTopicList=favorBrandDao.queryBrandTopicList(valMap);
		if(null!=brandTopicList&&brandTopicList.size()>0){
			for(BrandTopicVo top: brandTopicList){
				String picPath =  top.getMobilePic();
	        	int index = picPath.lastIndexOf(".");
	        	int lastNum = Integer.parseInt(picPath.substring(index-1, index),16);
	            top.setMobilePic(ImageServiceConvertor.replaceImageDomain(
	            		XiuConstant.MOBILE_IMAGE[lastNum%3]+ picPath));
				
			}
			return brandTopicList;
		}else {
			return null;
		}
	}

	@Override
	public int queryBrandTopicListCount(Long brandId) throws Exception{
		return favorBrandDao.queryBrandTopicListCount(brandId);
	}
	@Override
	public String queryActivityCount(Long brandId)throws Exception{
		List<BrandTopicVo> activityIds=favorBrandDao.queryActivityCount(brandId);
		if(null!=activityIds&&activityIds.size()>0){
			int cnt=activityIds.size();
			if(cnt==1){
				return "1,"+activityIds.get(0).getActivityId();
			}else{
				return cnt+",";
			}
		}else{
			return "0,";
		}
		
	}
	
	
	public int delBatchFavorBrand(HashMap<String, Object> valMap)
			throws Exception {
		int result = 0;
		result =favorBrandDao.deleteBatchBrand(valMap)>=0?0:-1;
		//批量删除关注表中的秀
		favorBrandDao.deleteBatchLabelConcern(valMap);
		return result;
	}
}
