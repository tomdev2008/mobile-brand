package com.xiu.mobile.brand.web.cache.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.xiu.mobile.brand.web.cache.XiuBrandInfoCache;
import com.xiu.mobile.brand.web.dao.BrandDao;
import com.xiu.mobile.brand.web.dao.model.XBrandModel;
import com.xiu.mobile.brand.web.util.ConfigUtil;
import com.xiu.mobile.brand.web.util.Constants;

public class XiuBrandInfoCacheImpl extends XiuBrandInfoCache {

	private static final Logger LOGGER = Logger.getLogger(XiuBrandInfoCacheImpl.class);

	private static HashMap<Long, XBrandModel> brandCache = new HashMap<Long, XBrandModel>();
	private static HashMap<Long, XBrandModel> brandHasGoodsCache = new HashMap<Long, XBrandModel>();
	
	private BrandDao brandDao;
    
	//获取xiu.goods品牌ID
	public HashMap<Long, XBrandModel> getBrandIDs(){
		HashMap<Long, XBrandModel> AllbrandCache = new HashMap<Long, XBrandModel>();
		 List<XBrandModel> lists = brandDao.getBrandIDs();
		 LOGGER.info("获取xiu.goods品牌ID数量：=========="+lists.size()+"=============");
		 int count=0;
		 XBrandModel x=null;
		 if(CollectionUtils.isNotEmpty(lists)){
			 for(int i=0;i<lists.size();i++){
				  x = lists.get(i);
				  if(x!=null){
					  AllbrandCache.put(x.getBrandId(), x);
					  count++;
				  }
			 }
		 }
		 LOGGER.info("最终装入map品牌ID数量：=========="+count+"=============");
		return AllbrandCache;
	}
	
	
	@Override
	protected void init() {
		reloadTimer();
	}
	
	private void loadAllBrandData() {
		LOGGER.info("【XiuBrandInfoCacheImpl】 开始加载品牌缓存数据");
		HashMap<Long, XBrandModel> map = new HashMap<Long, XBrandModel>();
		List<XBrandModel> datas = brandDao.getAllBrandInfo();
		
		if(datas == null) {
			LOGGER.error("【XiuBrandInfoCacheImpl】 加载品牌缓存数据错误, datas is null.");
			return;
		}
	
		for(XBrandModel m : datas) {
			if(m != null && StringUtils.isNotBlank(m.getBannerPic())) {
				m.setBannerPic(Constants.BANNER_URL + m.getBannerPic());
			}
			if (m != null) {
				map.put(m.getBrandId(), m);
			}
		}
		
		LOGGER.info("【XiuBrandInfoCacheImpl】 加载品牌缓存数据的记录条数为: " + map.size());
		brandCache = map;
	}
	
	private void loadBrandHasGoodsData() {
		LOGGER.info("【XiuBrandInfoCacheImpl】 开始加载品牌直达功能对应的品牌缓存数据");
		HashMap<Long, XBrandModel> map = new HashMap<Long, XBrandModel>();
		List<XBrandModel> datas = brandDao.selectAllByShowFlag1AndHasGoods();
		
		if(datas == null) {
			LOGGER.error("【XiuBrandInfoCacheImpl】 加载品牌直达对应的品牌缓存数据错误, datas is null.");
			return;
		}
		
		for(XBrandModel m : datas) {
			if(m != null && StringUtils.isNotBlank(m.getBannerPic())) {
				m.setBannerPic(Constants.BANNER_URL + m.getBannerPic());
			}
			if (m != null) {
				map.put(m.getBrandId(), m);
			}
		}
		
		LOGGER.info("【XiuBrandInfoCacheImpl】 加载品牌直达功能对应的品牌缓存数据的记录条数为: " + map.size());
		brandHasGoodsCache = map;
	}
	
	/**
	 * 定时器
	 */
	private void reloadTimer(){
		Timer timer = new Timer(true);
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				
				try {
					loadAllBrandData();
				} catch (Exception e) {
					LOGGER.error("【XiuBrandInfoCacheImpl】 加载品牌缓存数据异常", e);
				}
				
				try {
					loadBrandHasGoodsData();
				} catch (Exception e) {
					LOGGER.error("【XiuBrandInfoCacheImpl】 加载品牌直达功能对应的品牌缓存数据", e);
				}
			}
		};
		
		long updateTime  = Long.valueOf(ConfigUtil.getValue("brand.update.time"));
		//10秒之后 ，每隔updateTime小时执行一次
		timer.scheduleAtFixedRate(timerTask, 10000, updateTime * 60 * 60 * 1000);
	}


	@Override
	public XBrandModel getBrandById(Long brandId) {
		if (brandCache != null) {
			return brandCache.get(brandId);
		}
			
		return null;
	}
	
	@Override
	public XBrandModel getBrandHasGoodsByName(String name) {
		for(XBrandModel brand : brandHasGoodsCache.values()) {
			if(name.equalsIgnoreCase(brand.getBrandName())
					|| name.equalsIgnoreCase(brand.getEnName())
					|| name.equalsIgnoreCase(brand.getMainName())){
				return brand;
			}
				
			// 处理品牌名中间为·
			if(brand.getBrandName() != null  
					&& name.equalsIgnoreCase(brand.getBrandName().replace("·", " "))) {
				return brand;
			}
		}
		return null;
	}

	/**
	 * @return the brandDao
	 */
	public BrandDao getBrandDao() {
		return brandDao;
	}

	/**
	 * @param brandDao the brandDao to set
	 */
	public void setBrandDao(BrandDao brandDao) {
		this.brandDao = brandDao;
	}

}
