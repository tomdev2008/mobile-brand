package com.xiu.mobile.portal.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiu.mobile.portal.controller.AppController;
import com.xiu.mobile.portal.dao.BrowseGoodsDao;
import com.xiu.mobile.portal.model.BrowseGoodsModel;
import com.xiu.mobile.portal.service.IBrowseGoodsService;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(商品浏览记录业务处理) 
 * @AUTHOR coco.long@xiu.com
 * @DATE 2014-12-19 
 * ***************************************************************
 * </p>
 */
@Transactional
@Service("browseGoodsService")
public class BrowseGoodsServiceImpl implements IBrowseGoodsService {
	private Logger logger = Logger.getLogger(AppController.class);
	
	@Autowired
	private BrowseGoodsDao browseGoodsDao;
	
	public int addBrowseGoods(Map map) {
		String goodsSns = map.get("goodsSns").toString();
		int returnValue = -1;
		if(goodsSns != null && goodsSns.indexOf(",") > -1) {
			//批量添加商品浏览记录
			String[] goodsSnArr = goodsSns.split(",");	//商品走秀码
			String browseTime = map.get("browseTime").toString();
			String[] browseTimeArr = browseTime.split(",");	//浏览时间
			
			List paraList = new ArrayList();
			for(int i = 0; i < goodsSnArr.length; i++) {
				Map objMap = new HashMap();
				objMap.put("goodsSn", goodsSnArr[i]);
				objMap.put("browseTime", browseTimeArr[i]);
				paraList.add(objMap);
			}
			map.put("list", paraList);
			
			returnValue = browseGoodsDao.addBrowseGoodsBatch(map); 
		} else {
			returnValue = browseGoodsDao.addBrowseGoods(map);
		}
		return returnValue;
	}

	public int deleteBrowseGoods(Map map) {
		String id = map.get("id").toString();
		int returnValue = -1;
		if(id != null && id.indexOf(",") > -1) {
			//批量删除商品浏览记录
			map.put("id", id.split(","));
			returnValue = browseGoodsDao.deleteBrowseGoodsBatch(map);
		} else {
			returnValue = browseGoodsDao.deleteBrowseGoods(map);
		}
		return returnValue;
	}
	
	public int deleteAllBrowseGoods(Map map) {
		return browseGoodsDao.deleteAllBrowseGoods(map);
	}

	public List<BrowseGoodsModel> getBrowseGoodsListByUserId(Map map) {
		return browseGoodsDao.getBrowseGoodsListByUserId(map);
	}

	public BrowseGoodsModel getBrowseGoodsById(Map map) {
		return browseGoodsDao.getBrowseGoodsById(map);
	}

	public int getBrowseGoodsTotalByUserId(Map map) {
		return browseGoodsDao.getBrowseGoodsTotalByUserId(map);
	}

	public int getBrowseGoodsCount(Map map) {
		return browseGoodsDao.getBrowseGoodsCount(map);
	}

}
