package com.xiu.mobile.portal.service.impl;

import com.xiu.commerce.hessian.model.Product;
import com.xiu.commerce.hessian.model.Sku;
import com.xiu.mobile.core.dao.LabelDao;
import com.xiu.mobile.core.model.FindGoodsVo;
import com.xiu.mobile.core.model.FindMenuMgt;
import com.xiu.mobile.core.model.FindSupportVersion;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.model.Label;
import com.xiu.mobile.portal.common.constants.GoodsConstant;
import com.xiu.mobile.portal.common.util.AppVersionUtils;
import com.xiu.mobile.portal.common.util.ImageServiceConvertor;
import com.xiu.mobile.portal.common.util.ImageUtil;
import com.xiu.mobile.portal.common.util.Tools;
import com.xiu.mobile.portal.constants.GlobalConstants;
import com.xiu.mobile.portal.dao.FindGoodsApiDao;
import com.xiu.mobile.portal.dao.FindMenuDao;
import com.xiu.mobile.portal.dao.SubjectDao;
import com.xiu.mobile.portal.ei.EIChannelInventoryManager;
import com.xiu.mobile.portal.model.AdvertisementVo;
import com.xiu.mobile.portal.model.AppVo;
import com.xiu.mobile.portal.model.SubjectVo;
import com.xiu.mobile.portal.service.IAdvService;
import com.xiu.mobile.portal.service.IGoodsService;
import com.xiu.mobile.portal.service.IIndexService;
import com.xiu.mobile.portal.service.ISupportVersionService;
import com.xiu.mobile.portal.service.ITopicActivityGoodsService;
import com.xiu.show.core.common.util.ObjectUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("indexService")
public class IndexServiceImpl implements IIndexService {

	@Autowired
    private IAdvService advService;
	
	@Autowired
	private FindMenuDao findMenuDao;
	
	@Autowired
	private FindGoodsApiDao findGoodsApiDao;
	
	@Autowired
	private SubjectDao subjectDao;
	
	@Autowired
	private ITopicActivityGoodsService topicActivityGoodsService;
	
	@Autowired
	private IGoodsService goodsManager;
	@Autowired
	private LabelDao subjectLabelDao;
	
	@Autowired
	private ISupportVersionService supportVersionService;
	
	@Autowired
	private EIChannelInventoryManager eiChannelInventoryManager;
	/**
	 * 获取首页显示数据信息
	 * @param params
	 * @return
	 */
	public Map<String,Object> getIndexInfo(Map<String,Object> params) {
		Boolean isSuccess=false;
		Map<String,Object> resultMap=new HashMap<String,Object>();
		List<AdvertisementVo> advs=null;//首页广告结果集
		List<AdvertisementVo> topAdvs=new ArrayList<AdvertisementVo>();//首页广告结果集
		List<AdvertisementVo> middelAdvs=new ArrayList<AdvertisementVo>();//首页广告结果集
//		List<AdvertisementVo> advs=null;//焦点图广告结果集
	    List<FindMenuMgt> findMenus=null;//首页引导图结果集
		List<SubjectVo> rowSubjectList=null;//竖专题（大）结果集
		List<SubjectVo> crossSubjectList=null;//横专题（小）结果集
		String dataType=(String)params.get("dataType");
//		if(dataType==null||dataType.equals("")||dataType.equals("0")){//如果dataType为空或者dataType为0则查询焦点图广告、首页引导图、专题
			//1.获取首页广告数据
			Map<String,Object> advparams=new HashMap<String,Object>();
			List<String> advCodes = new ArrayList<String>();
			advCodes.add( "IndexTop");
			advCodes.add( "IndexMiddle");
			advparams.put("codes", advCodes);
			advs=advService.getAdvListByTypes(advparams);
			for (AdvertisementVo adv:advs) {
				if(adv.getAdvPlaceCode().equals("IndexTop")){
					topAdvs.add(adv);
				}else if(adv.getAdvPlaceCode().equals("IndexMiddle")){
					middelAdvs.add(adv);
				}
				adv.setAdvPlaceCode(null);
			}

			AppVo appVo = (AppVo)params.get("appVo");

			//2.获取首页引导图数据
			Map<String,Object> findMenuParams=new HashMap<String,Object>();
		    findMenus=findMenuDao.getFindMenuIndexList(findMenuParams);

			/**是需要放开零钱夺宝*/
			boolean flag = appVo == null || ("iPhone".equalsIgnoreCase(appVo.getAppType())
					&& "3.6.0".compareTo(appVo.getAppVersion()) <= 0);

			//首页引导图片设置
			for(Iterator<FindMenuMgt> iter = findMenus.iterator(); iter.hasNext();){
				FindMenuMgt findMenuMgt = iter.next();

				findMenuMgt.setIconUrl(ImageUtil.getAdvImageUrl(findMenuMgt.getIconUrl()));
				if(!flag && "零钱夺宝".equals(findMenuMgt.getName())){
					iter.remove();
				}else if(flag && "分享返利".equals(findMenuMgt.getName())){
					iter.remove();
				}

			}

			//3.获取专题数据
			 Map<String,Object> subjectParams=new HashMap<String,Object>();
			 List<SubjectVo> subjects=subjectDao.getIndexSubjectList(subjectParams);
			 rowSubjectList= new ArrayList<SubjectVo>();//竖专题（大）
			 crossSubjectList= new ArrayList<SubjectVo>();//横专题（小）
			 int len=subjects.size();
			 for (int i = 0; i < len; i++) {
				 SubjectVo subject= subjects.get(i);
				 if(subject.getDisplayStytle()==1){
					 subject.setDisplayStytle(null);
					 rowSubjectList.add(subject);
				 }else {
					 subject.setDisplayStytle(null);
					 crossSubjectList.add(subject);
				 }
				 //专题图片设置
				 subjects.get(i).setOutPic(ImageUtil.getAdvImageUrl(subjects.get(i).getOutPic()));
			 }
			 //限制横专题的个数为2的倍数
			int size= crossSubjectList.size();
			int num=2;
			if(size%num>0){
				crossSubjectList.remove(size-1);
			}
			 
//		}
		
		//4.获取单品数据
//		String pageNumStr=(String)params.get("pageNum");
		int pageNum=1;
//		if(pageNumStr!=null&&!pageNumStr.equals("")){
//			pageNum=ObjectUtil.getInteger(pageNumStr, 1);
//		}
		Map<String,Object> goodsRecommendParams=new HashMap<String,Object>();
		Page page=new Page();
		page.setPageSize(10);//精选秀分页大小
		page.setPageNo(pageNum);
		
		int total =findGoodsApiDao.getFindGoodsListCount(goodsRecommendParams);//获取总数
		page.setTotalCount(total);
		goodsRecommendParams.put("lineMin", page.getFirstRecord());
		goodsRecommendParams.put("lineMax", page.getEndRecord());
		List<FindGoodsVo> findGoods=findGoodsApiDao.getFindGoodsList(goodsRecommendParams);
		int goodsSize=findGoods.size();
		StringBuffer goodsSnsSb=new StringBuffer();
		for (int i = 0; i < goodsSize; i++) {
			goodsSnsSb.append(findGoods.get(i).getGoodsSn());
			goodsSnsSb.append(",");
		}
		// ===========================封装产品相关信息==============================/
		List<Product> products = topicActivityGoodsService.batchLoadProducts(goodsSnsSb.toString());
		for(FindGoodsVo iitem: findGoods ){
			if(null!=products&&products.size()>0){
		     	for(Product product:products ){
					if(iitem.getGoodsSn().equals(product.getXiuSN())){
						if (StringUtils.isNotEmpty(product.getImgUrl())) {
							// 目前图片服务器前缀
							String goodsId =  iitem.getGoodsSn();
							// 商品图片
							String imgUrl = product.getImgUrl();
							// 为了适应镜像环境的端口号
							if (Tools.isEnableImageReplace()) { 
								if (null != imgUrl) {
									imgUrl = imgUrl.replace("pic.xiu.com", "pic.xiu.com:6080");
									imgUrl = imgUrl.replace("images.xiu.com", "images.xiu.com:6080");
								}
							} 
							imgUrl = ImageServiceConvertor.getGoodsDetail(imgUrl);
							iitem.setGoodsImg(imgUrl+"/g1_"+GoodsConstant.SLIDE_IMG_MAX);

							iitem.setGoodsId(product.getInnerID());
						}	
						
						// 获取商品价格
						Double price=goodsManager.getZxPrice(product);
						iitem.setGoodsPrice(price==null?0.00:price);
						//商品上下架
						int stateOnsale=Integer.parseInt(product.getOnSale());
						iitem.setGoodsStateOnsale(stateOnsale);
						//商品库存
						Integer stock=getProductStock(product.getSkus());
						iitem.setGoodsStock(stock);
					}
				}
			}else{
				iitem.setGoodsImg("");
				iitem.setGoodsId(Long.parseLong("0"));
				iitem.setGoodsPrice(0.00);
			}
		}
		
		isSuccess=true;
		Map<String,Object> findGoodsMolel=new HashMap<String,Object>();
		findGoodsMolel.put("goodsRecommendList", findGoods);
		findGoodsMolel.put("totalCount", total);
		findGoodsMolel.put("totalPage", page.getTotalPages());
		
		resultMap.put("status", isSuccess);
		resultMap.put("advList", topAdvs);
		resultMap.put("middelAdvList", middelAdvs);
		resultMap.put("indexGuideIconList", findMenus);
//		resultMap.put("rowSubjectList", rowSubjectList);
		resultMap.put("rowSubjectList", new ArrayList<SubjectVo>());
		resultMap.put("crossSubjectList", crossSubjectList);
		resultMap.put("goodsRecommendListModel", findGoodsMolel);
		return resultMap;
	}

	/**
	 *
	 * @param params
	 * @return
     */
	@Override
	public Map<String,Object> getNewIndexInfo(Map<String,Object> params) {
		Boolean isSuccess=false;
		Map<String,Object> resultMap=new HashMap<String,Object>();
		List<AdvertisementVo> advs=null;//首页广告结果集
		List<AdvertisementVo> topAdvs=new ArrayList<AdvertisementVo>();//首页广告结果集
		List<AdvertisementVo> middelAdvs=new ArrayList<AdvertisementVo>();//首页广告结果集
//		List<AdvertisementVo> advs=null;//焦点图广告结果集
		List<FindMenuMgt> findMenus=null;//首页引导图结果集
		List<SubjectVo> rowSubjectList=null;//竖专题（大）结果集
		List<SubjectVo> crossSubjectList=null;//横专题（小）结果集
		String dataType=(String)params.get("dataType");

		String seasonHotUrl = null;

		//1.获取首页广告数据
		Map<String,Object> advparams=new HashMap<String,Object>();
		List<String> advCodes = new ArrayList<String>();
		advCodes.add( "FinderTopAdv");
		advCodes.add( "IndexMiddle");
		advCodes.add( "FinderSeasonHotAdv");
		advparams.put("codes", advCodes);
		advs=advService.getAdvListByTypes(advparams);
		for (AdvertisementVo adv:advs) {
			if(adv.getAdvPlaceCode().equals("FinderTopAdv")){
				topAdvs.add(adv);
			}else if(adv.getAdvPlaceCode().equals("IndexMiddle")){
				middelAdvs.add(adv);
			}else if("FinderSeasonHotAdv".equals(adv.getAdvPlaceCode())){
				seasonHotUrl = adv.getHttpUrl();
			}

			adv.setAdvPlaceCode(null);
		}

		AppVo appVo = (AppVo)params.get("appVo");

		//2.获取首页引导图数据
		Map<String,Object> findMenuParams=new HashMap<String,Object>();
		findMenus=findMenuDao.getFindMenuIndexList(findMenuParams);
		findBaseSupportVersion(findMenus);
		AppVersionUtils.filterSupportVersionItems(findMenus,appVo);

		//首页引导图片设置
		for(Iterator<FindMenuMgt> iter = findMenus.iterator(); iter.hasNext();){
			FindMenuMgt findMenuMgt = iter.next();

			findMenuMgt.setIconUrl(ImageUtil.getAdvImageUrl(findMenuMgt.getIconUrl()));
		}

		//3.获取专题数据
		Map<String,Object> subjectParams=new HashMap<String,Object>();
		List<SubjectVo> subjects=subjectDao.getIndexSubjectList(subjectParams);
		rowSubjectList= new ArrayList<SubjectVo>();//竖专题（大）
		crossSubjectList= new ArrayList<SubjectVo>();//横专题（小）
		int len=subjects.size();
		for (int i = 0; i < len; i++) {
			SubjectVo subject= subjects.get(i);
			if(subject.getDisplayStytle()==1){
				subject.setDisplayStytle(null);
				rowSubjectList.add(subject);
			}else {
				subject.setDisplayStytle(null);
				crossSubjectList.add(subject);
			}
			//专题图片设置
			subjects.get(i).setOutPic(ImageUtil.getAdvImageUrl(subjects.get(i).getOutPic()));
		}
		//限制横专题的个数为2的倍数
		int size= crossSubjectList.size();
		int num=2;
		if(size%num>0){
			crossSubjectList.remove(size-1);
		}
		List<Map<String,Object>> recommendGoodsTags = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		int category=2;//专题
		List<Label> listLabel=subjectLabelDao.getSubjectLabelSecond(category);
		int indexFirstLabelId=0;//首页开始显示的发现好货标签ID
		if(listLabel.size()>0 && listLabel!=null){
			for(Label label:listLabel){
				if(indexFirstLabelId==0){
					indexFirstLabelId=ObjectUtil.getInteger(label.getLabelId(),0);
				}
				map = new HashMap<String, Object>();
				map.put("tagId", label.getLabelId());
				map.put("label", label.getTitle());
				recommendGoodsTags.add(map);
			}
		}
		//4.获取单品数据
//		String pageNumStr=(String)params.get("pageNum");
		int pageNum=1;
//		if(pageNumStr!=null&&!pageNumStr.equals("")){
//			pageNum=ObjectUtil.getInteger(pageNumStr, 1);
//		}
		Map<String,Object> goodsRecommendParams=new HashMap<String,Object>();
		Page page=new Page();
		page.setPageSize(10);//精选秀分页大小
		page.setPageNo(pageNum);
		goodsRecommendParams.put("tagId", indexFirstLabelId);
		int total =findGoodsApiDao.getFindGoodsListCount(goodsRecommendParams);//获取总数
		page.setTotalCount(total);
		goodsRecommendParams.put("lineMin", page.getFirstRecord());
		goodsRecommendParams.put("lineMax", page.getEndRecord());
		List<FindGoodsVo> findGoods=findGoodsApiDao.getFindGoodsList(goodsRecommendParams);
		int goodsSize=findGoods.size();
		StringBuffer goodsSnsSb=new StringBuffer();
		for (int i = 0; i < goodsSize; i++) {
			goodsSnsSb.append(findGoods.get(i).getGoodsSn());
			goodsSnsSb.append(",");
		}
		// ===========================封装产品相关信息==============================/
		List<Product> products = topicActivityGoodsService.batchLoadProducts(goodsSnsSb.toString());
		for(FindGoodsVo iitem: findGoods ){
			if(null!=products&&products.size()>0){
				for(Product product:products ){
					if(iitem.getGoodsSn().equals(product.getXiuSN())){
						if (StringUtils.isNotEmpty(product.getImgUrl())) {
							// 目前图片服务器前缀
							String goodsId =  iitem.getGoodsSn();
							// 商品图片
							String imgUrl = product.getImgUrl();
							// 为了适应镜像环境的端口号
							if (Tools.isEnableImageReplace()) {
								if (null != imgUrl) {
									imgUrl = imgUrl.replace("pic.xiu.com", "pic.xiu.com:6080");
									imgUrl = imgUrl.replace("images.xiu.com", "images.xiu.com:6080");
								}
							}
							imgUrl = ImageServiceConvertor.getGoodsDetail(imgUrl);
							iitem.setGoodsImg(imgUrl+"/g1_"+GoodsConstant.SLIDE_IMG_MAX);

							iitem.setGoodsId(product.getInnerID());
						}

						// 获取商品价格
						Double price=goodsManager.getZxPrice(product);
						iitem.setGoodsPrice(price==null?0.00:price);
						//商品上下架
						int stateOnsale=Integer.parseInt(product.getOnSale());
						iitem.setGoodsStateOnsale(stateOnsale);
						//商品库存
						Integer stock=getProductStock(product.getSkus());
						iitem.setGoodsStock(stock);
					}
				}
			}else{
				iitem.setGoodsImg("");
				iitem.setGoodsId(Long.parseLong("0"));
				iitem.setGoodsPrice(0.00);
			}
		}

		isSuccess=true;
		Map<String,Object> findGoodsMolel=new HashMap<String,Object>();
		findGoodsMolel.put("seasonHotUrl", seasonHotUrl);
		findGoodsMolel.put("recommendGoodsTags", recommendGoodsTags);
		findGoodsMolel.put("goodsRecommendList", findGoods);
		findGoodsMolel.put("totalCount", total);
		findGoodsMolel.put("totalPage", page.getTotalPages());

		resultMap.put("status", isSuccess);
		resultMap.put("advList", topAdvs);
		resultMap.put("middelAdvList", middelAdvs);
		resultMap.put("indexGuideIconList", findMenus);
//		resultMap.put("rowSubjectList", rowSubjectList);
		resultMap.put("rowSubjectList", new ArrayList<SubjectVo>());
		resultMap.put("crossSubjectList", crossSubjectList);
		resultMap.put("goodsRecommendListModel", findGoodsMolel);
		return resultMap;
	}
	//指定版本处理
	private void findBaseSupportVersion(List<FindMenuMgt> findMenus){
		//List<BaseSupportVersion> itemsList=new ArrayList<BaseSupportVersion>();
		//查询引导图是否需要展示
			List<Long> listFindId=new ArrayList<Long>();
			for(FindMenuMgt mgt:findMenus){
				if(mgt.getSupportVersion()!=null && mgt.getSupportVersion()==1){
					mgt.setIsShow(1);
					listFindId.add(mgt.getId());
				}else if(mgt.getSupportVersion()!=null && mgt.getSupportVersion()==0){
					mgt.setIsShow(0);
				}
			}
			//查询所有指定版本
			if(listFindId.size()==0){
				return ;
			}
			Map<String,Object> listMap=new HashMap<String, Object>();
			listMap.put("findMenuId", listFindId);
			listMap.put("type", 1);//1首页引导图
			Map<Long,List<FindSupportVersion>> tmp=supportVersionService.findVersion(listMap);
			for(FindMenuMgt mgt:findMenus){
				List<FindSupportVersion> list=tmp.get(mgt.getId());
				mgt.setListVersion(list);
				if(mgt.getSupportVersion()!=null && mgt.getSupportVersion()==1){
					mgt.setIsShow(1);
				}else if(mgt.getSupportVersion()!=null && mgt.getSupportVersion()==0){
					mgt.setIsShow(0);
				}
			}
	}
	// 处理sku信息
	private Integer getProductStock(Sku[] skus) {
		Integer stock=0;
		if (skus != null && skus.length > 0) {
			List<String> skuCodeList = new ArrayList<String>();
			for (Sku sku : skus) {
				skuCodeList.add(sku.getSkuSN());
			}
			// 批量获取SKU的库存信息
			Map<String, Integer> skuCodeMap = queryInventoryBySku(skuCodeList);
			for (Sku sku : skus) {
				if (skuCodeMap.containsKey(sku.getSkuSN()) && skuCodeMap.get(sku.getSkuSN()) != null) {
					// 添加sku的库存信息
					stock=stock+skuCodeMap.get(sku.getSkuSN());
				}
			}
		}

		return stock;
	}

	/**
	 * 查询库存信息
	 * @param sku
	 * @return
	 */
	public Map<String, Integer> queryInventoryBySku(List<String> skuCodeList) {
		try {
			return eiChannelInventoryManager.inventoryQueryBySkuCodeList(GlobalConstants.CHANNEL_ID, skuCodeList);
		} catch (Exception e) {
			return new HashMap<String, Integer>();
		}
	}
	
}
