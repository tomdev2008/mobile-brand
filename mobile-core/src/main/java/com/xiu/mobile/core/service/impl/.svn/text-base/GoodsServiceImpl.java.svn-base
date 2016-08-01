package com.xiu.mobile.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.core.common.util.HttpUtils;
import com.xiu.mobile.core.constants.GlobalConstants;
import com.xiu.mobile.core.dao.GoodsDao;
import com.xiu.mobile.core.dao.WellChosenBrandDao;
import com.xiu.mobile.core.model.Goods;
import com.xiu.mobile.core.model.WellChosenBrandVo;
import com.xiu.mobile.core.service.IGoodsService;
import com.xiu.mobile.core.utils.XiuUtil;
import com.xiu.sales.common.tools.DateUtils;

@Service("goodsService")
public class GoodsServiceImpl implements IGoodsService {

	private static final XLogger logger = XLoggerFactory.getXLogger(GoodsServiceImpl.class);
	
	@Autowired
	private GoodsDao goodsDao;
	
	private static final String goodsPriceQueryUrl = "http://mbrand.xiu.com/query/price";
	
	@Autowired
	private WellChosenBrandDao wellChosenBrandDao;
	
	private boolean isAutoBrandCountPlanStock = true;
	
	@Override
	public Goods getGoodsBySn(String sn) {
		return goodsDao.getGoodsBySn(sn);
	}

	@Override
	public Goods getGoodsById(Long productId) {
		return goodsDao.getGoodsById(productId);
	}

	private String getTimeStr() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		return sf.format(now);
	}
	
	/**
	 * 商品降价定时任务
	 */
	public void callGoodsPriceReducedTask() {
		logger.info("商品降价提醒定时任务Begin，时间："+getTimeStr());
		//1.查询上架商品走秀码
		List<String> goodsSnList = goodsDao.getGoodsListOnSale(); 
		if(goodsSnList != null && goodsSnList.size() > 0) {
			//2.插入商品降价基础数据
			goodsDao.addGoodsReducedPriceAll();
			
			int size = goodsSnList.size();
			StringBuffer sb = new StringBuffer();
			for(int i = 0; i < size; i++) {
				sb.append(goodsSnList.get(i)).append(",");
				if(i != 0 && i%199 == 0) {
					sb.deleteCharAt(sb.length() - 1);
					//3.查询商品价格接口
					Map<String, Object> goodsData = queryGoodsPriceList(sb.toString());
					String goodsPrice = (String) goodsData.get("goodsPrice");
					if(StringUtils.isNotBlank(goodsPrice)) {
						//4.商品价格不为空，则插入商品最终价
						goodsDao.addGoodsFinalPrice(goodsData);
					}
					sb = new StringBuffer();
				}
			}
			if(sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
				//查询商品价格接口
				Map<String, Object> goodsData = queryGoodsPriceList(sb.toString());
				String goodsPrice = (String) goodsData.get("goodsPrice");
				if(StringUtils.isNotBlank(goodsPrice)) {
					//商品价格不为空，则插入商品最终价
					goodsDao.addGoodsFinalPrice(goodsData);
				}
			}
			
			//5.更新商品最终价
			goodsDao.updateGoodsFinalPrice();
			//6.更新商品差价
			goodsDao.updateGoodsDiffPirce();
			
			//7.查询用户关注商品降价信息，
			List userGoodsList = goodsDao.getUserGoodsReducedPrice();
			if(userGoodsList != null && userGoodsList.size() > 0) {
				//8.推送商品降价信息  待实现！！！
				
			}
			
		}
		logger.info("商品降价提醒定时任务End，时间："+getTimeStr());
	}

	public Map<String, Object> queryGoodsPriceList(String goodSns) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			List<String> goodsList = new ArrayList<String>();
			StringBuffer goodsPrice = new StringBuffer();
			Map<String, String> paraMap = new HashMap<String, String>();
			paraMap.put("prosn", goodSns);
			
			//查询商品价格接口
			String resultStr = HttpUtils.postMethod(goodsPriceQueryUrl, paraMap, "UTF-8");
			
			JSONObject json = JSONObject.fromObject(resultStr);
			JSONObject head = (JSONObject) json.get("head");
			
			String code = (String) head.get("code");
			if(code != null && code.equals("0")){
				JSONArray resultJsons = (JSONArray) json.get("data");
				int size = resultJsons.size();
				for (int i = 0; i< size; i++) {
					JSONObject jsonobj = (JSONObject) resultJsons.get(i);
					String productSn = (String) jsonobj.get("productSn");
					Double finalPrice = (Double) jsonobj.get("finalPrice");
					
					if(finalPrice != null) {
						goodsList.add(productSn);
						goodsPrice.append(productSn).append("#").append(XiuUtil.getPriceDoubleFormat(finalPrice)).append(",");
					}
				}
			}else{
				logger.error("查询商品价格接口异常：code=" + code + ",desc=" + (String) head.get("desc"));
			}
			if(goodsPrice.length() > 0) {
				goodsPrice.deleteCharAt(goodsPrice.length() - 1);
			}
			result.put("goodsList", goodsList);
			result.put("goodsPrice", goodsPrice.toString());
			
		} catch(Exception e){
			logger.error("查询商品价格接口异常，错误信息：" + e.getMessage());
		}
		
		return result;
	}

	/**
	 * 定时任务同步品牌在线商品数量
	 * @throws Exception 
	 */
	@Override
	public void callOnlineGoodsTask() throws Exception  {
		// TODO Auto-generated method stub
		logger.info("callOnlineGoodsTask定时任务同步品牌在线商品数量======开始:"+DateUtils.format(new Date(), DateUtils.DATE_FORMAT_YMD_HMS));
		int count=0;
		if(isAutoBrandCountPlanStock){
			isAutoBrandCountPlanStock=false;
			Map<Object, Object> map=new HashMap<Object, Object>();
			List<WellChosenBrandVo> wellChosenBrandVoList;
			try {
				map.put("pageMin", 0);
				map.put("pageMax", wellChosenBrandDao.getWellChosenBrandListCount(map));
				wellChosenBrandVoList = wellChosenBrandDao.getWellChosenBrandList(map);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				logger.info("查询商品信息异常",e1);
				return;
			}
			try{
				for( WellChosenBrandVo vo:wellChosenBrandVoList){
					// 查询商品数量
					try {
						int goodsNum=0;
						String url = GlobalConstants.GET_GOODSNUM_BY_BRAND + "?bId="+vo.getBrandId()+"&v=2.0";
						String httpResult = HttpUtils.getMethod(url, 10000, 15000);
						if(StringUtils.isNotBlank(httpResult)) {
							JSONObject  js=JSONObject.fromObject(httpResult);
							goodsNum = Integer.parseInt(js.getString("data"));
						}
						Map<String, Object> remap=new HashMap<String, Object>();
						remap.put("id", vo.getId());
						remap.put("onlineGoods", (long) goodsNum);
						remap.put("orderSequence", vo.getOrderSequence());
						wellChosenBrandDao.updateWellChosenBrandByBrandId(remap);
						count++;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						logger.info("查询商品数量异常,BrandId:"+vo.getBrandId(),e);
					}
				}
				}catch (Exception e){
					
				}finally{
				isAutoBrandCountPlanStock=true;
			}
		}
		logger.info("callOnlineGoodsTask定时任务同步品牌在线商品数量======结束:"+DateUtils.format(new Date(), DateUtils.DATE_FORMAT_YMD_HMS)+",本次同步数量:"+count);
	}
}
