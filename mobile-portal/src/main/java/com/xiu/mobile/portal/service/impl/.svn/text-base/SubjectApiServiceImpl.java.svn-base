package com.xiu.mobile.portal.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.commerce.hessian.model.Product;
import com.xiu.mobile.core.constants.LinkType;
import com.xiu.mobile.core.dao.LabelDao;
import com.xiu.mobile.core.model.Label;
import com.xiu.mobile.core.model.LabelCentre;
import com.xiu.mobile.core.service.ISubjectManagerService;
import com.xiu.mobile.core.utils.DateUtil;
import com.xiu.mobile.core.utils.XiuUtil;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.constants.GoodsConstant;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.util.ImageServiceConvertor;
import com.xiu.mobile.portal.common.util.ImageUtil;
import com.xiu.mobile.portal.common.util.Tools;
import com.xiu.mobile.portal.dao.BusinessStatisticDao;
import com.xiu.mobile.portal.dao.ProductDao;
import com.xiu.mobile.portal.dao.SubjectDao;
import com.xiu.mobile.portal.ei.EIMbrandManager;
import com.xiu.mobile.portal.model.BusinessStatistic;
import com.xiu.mobile.portal.model.GoodItemVO;
import com.xiu.mobile.portal.model.GoodsVo;
import com.xiu.mobile.portal.model.SubjectItemVo;
import com.xiu.mobile.portal.model.SubjectVo;
import com.xiu.mobile.portal.service.IGoodsService;
import com.xiu.mobile.portal.service.ISubjectApiService;
import com.xiu.mobile.portal.service.ISubjectCommentService;
import com.xiu.mobile.portal.service.ITopicActivityGoodsService;
import com.xiu.show.core.model.Page;

@Service("subjectApiService")
public class SubjectApiServiceImpl implements ISubjectApiService{

	
	@Autowired
	private SubjectDao subjectDao;
	
	@Autowired
	private ITopicActivityGoodsService topicActivityGoodsService;
	
	@Autowired
	private IGoodsService goodsManager;
	
	@Autowired
	private ISubjectCommentService subjectCommentService;
	@Autowired
    private ISubjectManagerService subjectManagerService;
//	@Autowired
//	private LabelDao subjectLabelDao;
	@Autowired
	private LabelDao labelDao;
	@Autowired
	private BusinessStatisticDao businessStatisticDao;
	
	@Autowired
	private EIMbrandManager eiMbrandManager;
	
	@Autowired
	private ProductDao productDao;
	@Autowired
	private IGoodsService goodsService;
	
	public Map<String,Object> getSubjectInfo(Map<String,Object> params) {
		Map<String,Object> resultMap=new HashMap<String,Object>();
		Boolean isSuccess=false;
		//1.查询详情
		SubjectVo subject=subjectDao.getSubjectInfo(params);
		
		if(subject==null){
			resultMap.put("status", isSuccess);
			resultMap.put("errorCode", ErrorCode.SubjectNotExists.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SubjectNotExists.getErrorMsg());
			return resultMap;
		}
		subject.setSaveCount((long)subjectDao.getUserSubjectListCount(params));
		//专题是否收藏
		if(params.get("xiuUserId")!=null && params.get("xiuUserId") !=""){
			params.put("userId", params.get("xiuUserId"));
			subject.setIsSave(subjectDao.getUserSubjectListCount(params) >0 ? 1L: 0L);
		}else{
			subject.setIsSave(0L);
		}
		
		/**
		 * 记录专题详情页点击情况
		 * jesse.chen 04-28
		 */
		
		BusinessStatistic bs=new BusinessStatistic();
		Map<String, Object> remap=new HashMap<String, Object>();
		remap.put("type", 1);
		remap.put("businessId", subject.getSubjectId());
		List<BusinessStatistic>  list=businessStatisticDao.queryBusinessStatisticByInfo(remap);
	    if(CollectionUtils.isNotEmpty(list)){
	    	bs=list.get(0);
	    	bs.setClickCount((bs.getClickCount() ==null ? 0:bs.getClickCount()) +1);
	    	businessStatisticDao.updateBusinessStatistic(bs);
	    }else{
	    	bs.setClickCount(1L);
	    	bs.setType(1L);
	    	bs.setBusinessId(subject.getSubjectId());
	    	businessStatisticDao.insertBusinessStatistic(bs);
	    }
	    
	    
	    
	    /*****************************/
		    Date startTime=subject.getStartTime();
		    if(startTime!=null){
		    	subject.setStartTime(null);
		    	subject.setPublishTime(DateUtil.formatDate(startTime, "yyyy年MM月dd日"));
		    }
			List<SubjectItemVo> siList=subject.getContentItemList();
			StringBuffer goodsSnsSb=new StringBuffer();//商品类型的id集字符串
			int size=siList.size();
			Boolean isFristImg=true;//是否第一张图
			subject.setSharePic(null);
			for (int i = 0; i < size; i++) {
				SubjectItemVo subjectItemVo=siList.get(i);
				//如果是图片类型则拼接完整图片url
				if(subjectItemVo.getContentItemType()==2){
					subjectItemVo.setContentItemData(ImageUtil.getAdvImageUrl(subjectItemVo.getContentItemData()));
					subjectItemVo.setWidth(subjectItemVo.getParam1());
					subjectItemVo.setHeight(subjectItemVo.getParam2());
					subjectItemVo.setParam1(null);
					subjectItemVo.setParam2(null);
					if(isFristImg){
						subject.setSharePic(subjectItemVo.getContentItemData());
						isFristImg=false;
					}
				}
				//如果是商品类型
				if(subjectItemVo.getContentItemType()==3){
					goodsSnsSb.append(subjectItemVo.getContentItemData());
					goodsSnsSb.append(",");
				}
			}
	
			// 2 对不同类型的数据进行封装
			if(!goodsSnsSb.toString().equals("")){
				List<Product> products = topicActivityGoodsService.batchLoadProducts(goodsSnsSb.toString());
				//获取商品价格
				List<GoodsVo> goodsVoList=new ArrayList<GoodsVo> ();
				eiMbrandManager.filterGoodsByMbrand(goodsSnsSb.toString(), goodsVoList);
			
				for(SubjectItemVo subjectItem: siList ){
					if(StringUtils.isNotEmpty(subjectItem.getLinkType()) && ! subjectItem.getLinkType().equals(LinkType.url.getCode())){
						//如果不是URL,则需要封装
						subjectItem.setContentItemLinkUrl(queryXiuAppUrlByType(subjectItem.getLinkType(),subjectItem.getLinkObject()));
					}
					//商品类型
					 if(subjectItem.getContentItemType()==3){
						if(null!=products&&products.size()>0){
					     	for(Product product:products ){
								if(subjectItem.getContentItemData().equals(product.getXiuSN())){
									if (StringUtils.isNotEmpty(product.getImgUrl())) {
										// 目前图片服务器前缀
										String imgUrl = product.getImgUrl();
										// 为了适应镜像环境的端口号
										if (Tools.isEnableImageReplace()) { 
											if (null != imgUrl) {
												imgUrl = imgUrl.replace("pic.xiu.com", "pic.xiu.com:6080");
												imgUrl = imgUrl.replace("images.xiu.com", "images.xiu.com:6080");
											}
										} 
										imgUrl = ImageServiceConvertor.getGoodsDetail(imgUrl);
										subjectItem.setGoodsImage(imgUrl+"/g1_"+GoodsConstant.SLIDE_IMG_MAX);
										
										subjectItem.setGoodsId(product.getInnerID());
										
										//封装商品信息
										GoodItemVO goodItemVO=new GoodItemVO ();
										goodItemVO.setGoodId(product.getInnerID());
										goodItemVO.setGoodName(product.getPrdName());
										goodItemVO.setBrand(product.getBrandName());
										//获取商品走秀价
										for(GoodsVo vo:goodsVoList){
											if(vo.getGoodsId().equals(goodItemVO.getGoodId().toString())){
												goodItemVO.setPrice(XiuUtil.getPriceDouble2Str(Double.valueOf(vo.getZsPrice())));
											}
										}
										subjectItem.setGoodItemVO(goodItemVO);
									}	
								}
							}
						}else{
							subjectItem.setGoodsImage("");
							subjectItem.setGoodsId(Long.parseLong("0"));
						}
					}
				}
			}
			
			//3.查询专题评论
			Map<String,Object> commentMap =new HashMap<String,Object>();
			commentMap.put("pageSize",10);
			commentMap.put("pageNum",1);
			commentMap.put("subjectId",params.get("subjectId"));
			
			Map<String,Object> commentResult=subjectCommentService.getSubjectCommentList(commentMap);
			Map<String,Object> subjectCommentData=new HashMap<String,Object>();
			subjectCommentData.put(XiuConstant.TOTAL_COUNT, commentResult.get(XiuConstant.TOTAL_COUNT));
			subjectCommentData.put(XiuConstant.TOTAL_PAGE, commentResult.get(XiuConstant.TOTAL_PAGE));
			subjectCommentData.put("subjectCommentList", commentResult.get("subjectCommentList"));

			//4.专题标签
			List<Map<String,Object>> subjectTagList = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String,Object> mapParam=new HashMap<String,Object>();
			mapParam.put("type", 4);//专题
			mapParam.put("objectId", params.get("subjectId"));
			List<Label> listLabel=labelDao.findLabelListByObjectIdAndType(mapParam);
			if(listLabel.size()>0 && listLabel!=null){
				for(Label label:listLabel){
					map = new HashMap<String, Object>();
					map.put("tagId", label.getLabelId());
					map.put("label", label.getTitle());
					Object[] array={label.getLabelId(),label.getTitle()};
					map.put("url",MessageFormat.format(XiuConstant.SUBJECT_TAG_LIST_URL, array) );
					subjectTagList.add(map);
				}
				subject.setSubjectTagList(subjectTagList);
			}
		isSuccess=true;
		resultMap.put("status", isSuccess);
		resultMap.put("model", subject);
		resultMap.put("subjectCommentData", subjectCommentData);
		return resultMap;
	}


	@Override
	public Map<String,Object> getBigOrSmallSubjectListIndex(Map<String,Object> params) {
		Map<String,Object> result=new HashMap<String,Object>();
		List<SubjectVo> subjectList=new ArrayList<SubjectVo>();
		Boolean resultStatus=false; //是否成功的状态
		params.put("type", 4);//表示专题
		int total=subjectDao.getSubjectListCount(params); //查询总数
		Page<SubjectVo> page=new Page<SubjectVo>();
		page.setPageNo(Integer.valueOf(params.get("pageNum").toString()));
		page.setPageSize(Integer.valueOf(params.get("pageSize").toString()));
		params.put("pageMin", page.getFirstRecord());
		params.put("pageMax", page.getEndRecord());
		subjectList=subjectDao.getBigOrSmallSubjectListIndex(params);
		if(subjectList==null || subjectList.size()==0){
			result.put("status", resultStatus);
			result.put("errorCode",ErrorCode.SubjectNotLists.getErrorCode());
			result.put("errorMsg", ErrorCode.SubjectNotLists.getErrorMsg());
			return result;
		}
		int size=subjectList.size();
		page.setTotalCount(total);
		List<Long> lists=new ArrayList<Long>();//标签处理
		for(int i=0;i<size;i++){
			SubjectVo s=subjectList.get(i);
			s.setOutPic(ImageUtil.getAdvImageUrl(s.getOutPic()));
			//查询标签
			lists.add(s.getSubjectId());
		}
		//查询标签
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("objectIds", lists);
		map.put("type", 4);//表示专题
		Map<Long,List<LabelCentre>> results=findLabelList(map);
		for(SubjectVo good:subjectList){
			if(results!=null){
				List<LabelCentre> labelList=(List<LabelCentre>)results.get(good.getSubjectId());
				good.setLabelCentre(labelList);
			}
		}
		resultStatus=true;//查询成功
		result.put("totalCount", total); //总条数
		result.put("totalPage", page.getTotalPages()); //总页数
		result.put("status", resultStatus);
		result.put("listSubjiect", subjectList);
		return result;
	}
	//批量查询标签
	private Map<Long,List<LabelCentre>> findLabelList(Map<String,Object> map){
		List<LabelCentre> list=labelDao.findLabelsByObjectIds(map);
		Map<Long, List<LabelCentre>> tmp = new HashMap<Long, List<LabelCentre>>();
		for(LabelCentre label:list){
			List<LabelCentre> items=tmp.get(label.getObjectId());
			if(items==null){
				items=new ArrayList<LabelCentre>();
				tmp.put(label.getObjectId(), items);
			}
			items.add(label);
		}
		return tmp;
	}


	/**
	 * 查询用户收藏专题列表
	 */
	@Override
	public Map<String, Object> getUserCollectSubjectList(
			Map<String, Object> params) {
		Map<String,Object> result=new HashMap<String,Object>();
		List<SubjectVo> subjectList=new ArrayList<SubjectVo>();
		Boolean resultStatus=false; //是否成功的状态
		
		
		int total=subjectDao.getUserSubjectListCount(params); //查询总数
		Page<SubjectVo> page=new Page<SubjectVo>();
		page.setPageNo(Integer.valueOf(params.get("pageNum").toString()));
		page.setPageSize(Integer.valueOf(params.get("pageSize").toString()));
		params.put("pageMin", page.getFirstRecord());
		params.put("pageMax", page.getEndRecord());
		subjectList=subjectDao.getUserSubjectListList(params);
		
		/**
		 * 设置图片地址
		 */
		for(SubjectVo vo:subjectList){
			vo.setOutPic(ImageUtil.getAdvImageUrl(vo.getOutPic()));
		}
		
		resultStatus=true;//查询成功
		page.setTotalCount(total);
		result.put("totalCount", total); //总条数
		result.put("totalPage", page.getTotalPages()); //总页数
		result.put("status", resultStatus);
		result.put("listSubject", subjectList);
		return result;
	}

	
	/**
	 * 根据类型查询APP访问地址
	 * @return
	 */
	private  String queryXiuAppUrlByType(String type,String id){
		int retype=type.equals(LinkType.sales.getCode()) ? 4: type.equals(LinkType.store.getCode()) ? 5: type.equals(LinkType.goodCommodity.getCode()) ? 7: 
			type.equals(LinkType.brand.getCode()) ? 9: type.equals(LinkType.topic.getCode()) ? 2: type.equals(LinkType.show.getCode()) ? 3: type.equals(LinkType.good.getCode()) ? 6:
			type.equals(LinkType.subject.getCode()) ? 8: 0;
		return Tools.getXiuAppUrlByType(retype)+id;
	}
}
