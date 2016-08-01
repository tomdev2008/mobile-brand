package com.xiu.mobile.core.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiu.mobile.core.constants.GlobalConstants;
import com.xiu.mobile.core.constants.LinkType;
import com.xiu.mobile.core.dao.LabelDao;
import com.xiu.mobile.core.dao.SubjectLabelDao;
import com.xiu.mobile.core.dao.SubjectManagerDao;
import com.xiu.mobile.core.model.Goods;
import com.xiu.mobile.core.model.LabelCentre;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.model.Subject;
import com.xiu.mobile.core.model.SubjectComment;
import com.xiu.mobile.core.model.SubjectItem;
import com.xiu.mobile.core.model.SubjectLabel;
import com.xiu.mobile.core.service.IGoodsService;
import com.xiu.mobile.core.service.ISubjectManagerService;
import com.xiu.mobile.core.utils.DateUtil;
import com.xiu.mobile.core.utils.ImageUtil;
import com.xiu.mobile.core.utils.ObjectUtil;
@Transactional
@Service("subjectManagerService")
public class SubjectManagerServiceImpl implements ISubjectManagerService{

	
	@Autowired
	private SubjectManagerDao subjectDao;
	
    @Autowired
    private IGoodsService goodsService;
    @Autowired
	private LabelDao labelDao;
    
    @Autowired
    private SubjectLabelDao subjectLabelDao;

	public Map<String,Object> getSubjectList(Map<Object, Object> params, Page<?> page) {
				Map<String,Object> resultMap=new HashMap<String,Object>();//结果集
				Boolean resultStatus=false;
				List<Subject> subjectlist=new ArrayList<Subject>();

					Object startDate= params.get("startDate");
					Object endDate= params.get("endDate");
					if(startDate!=null&&!startDate.equals("")){
						params.put("startDate",DateUtil.parseTime(startDate.toString()));
					}
					if(endDate!=null&&!endDate.equals("")){
						params.put("endDate",DateUtil.parseTime(endDate.toString()));
					}
					params.put("pageMin",page.getFirstRecord());
					params.put("pageSize", page.getPageSize());
					params.put("pageMax", page.getEndRecord());
					params.put("labelType",  GlobalConstants.LABEL_TYPE_SUBJECT);

				//查询列表
				 subjectlist= subjectDao.getSubjectList(params);
				 int size=subjectlist.size();
				 Date now=new Date();
				 List<Long> lists=new ArrayList<Long>();//标签处理
				 for (int i = 0; i < size; i++) {
					 Subject subject=subjectlist.get(i);
					 if(now.after(subject.getStartTime())&&now.before(subject.getEndTime())){
						 subject.setStatus(1);//进行中
					 }else if(now.before(subject.getStartTime())){
						 subject.setStatus(0);//未开始
					 }else if(now.after(subject.getEndTime())){
						 subject.setStatus(2);//过期
					 }
					 subject.setOutPic(ImageUtil.getShowimageUrl(subject.getOutPic()));//图片地址处理
					 lists.add(subject.getSubjectId());
				 }
//				//查询标签
				 if(lists.size()>0){
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("objectIds", lists);
					map.put("type", GlobalConstants.LABEL_TYPE_SUBJECT);//表示专题
					List<LabelCentre> results=labelDao.findLabelsByObjectIds(map);
					for(Subject good:subjectlist){
						if(results!=null){
							for (LabelCentre lc:results) {
								if(lc.getObjectId().equals(good.getSubjectId())){
									 List<LabelCentre> goodlc=good.getLabelCentre();
									 if(goodlc==null){
										 goodlc=new  ArrayList<LabelCentre>();
									 }
									 goodlc.add(lc);
									good.setLabelCentre(goodlc);
								}
							}
						}
					}
				 }
			     page.setTotalCount(Integer.valueOf(subjectDao.getSubjectTotalCount(params)));
			     //白名单
			     
				//成功
				resultStatus=true;
				
				resultMap.put("page", page);
				resultMap.put("status", resultStatus);
				resultMap.put("msg","");
				resultMap.put("resultInfo", subjectlist);
				return resultMap;
			}
//	//批量查询标签
//		private Map<Long,List<LabelCentre>> findLabelList(Map<String,Object> map){
//			List<LabelCentre> list=labelDao.findLabelCentreList(map);
//			Map<Long, List<LabelCentre>> tmp = new HashMap<Long, List<LabelCentre>>();
//			for(LabelCentre label:list){
//				List<LabelCentre> items=tmp.get(label.getSubjectId());
//				if(items==null){
//					items=new ArrayList<LabelCentre>();
//					tmp.put(label.getSubjectId(), items);
//				}
//				items.add(label);
//			}
//			return tmp;
//		}
	public Map<String,Object> save(Map<String,Object> params) {
		Map<String,Object> resultMap=new HashMap<String,Object>();//结果集
		boolean isSuccess =false;
		Subject subject=(Subject)params.get("model");
		//检查名称是否重复
		//增加
		Long subjectId=subjectDao.getSubjectId();
		subject.setSubjectId(subjectId);
		 int updateNum=subjectDao.save(subject);
		 if(updateNum>0){
			 isSuccess=true;
		 }
		 //增加内容
		 params.put("subjectId", subjectId);
		 addSubjectItem(params);
//		//添加标签
//		String labelId=subject.getLabelId();
//		if(labelId!=null && labelId!=""){
//			String[] labelIdsArr=labelId.split(",");
//			List<LabelCentre> lists=new ArrayList<LabelCentre>();
//			for(int j=0;j<labelIdsArr.length;j++){
//				LabelCentre label=new LabelCentre();
//				label.setOrderSql(j+1);
//				label.setLabelId(Long.parseLong(labelIdsArr[j]));
//				label.setObjectId(subject.getSubjectId()); 
//				label.setCategory(1);
//				lists.add(label);
//			}
//			Map<String,Object> map=new HashMap<String,Object>();
//			map.put("list", lists);
//			labelDao.addLabelCentreList(map);
//		}
		 resultMap.put("subjectId", subjectId);
		 resultMap.put("status", isSuccess);
		 resultMap.put("msg", "");
		 return resultMap;
	}
	public int importSubject(List<Map<Object, Object>> listMap){
		//提取数据
		Map<String,Object> maps=getSubjectInfo(listMap);
		//保存专题
		Long subjectId=subjectDao.getSubjectId();
		Subject subject=(Subject)maps.get("subject");
		subject.setSubjectId(subjectId);
		int updateNum=subjectDao.save(subject);
		//保存标签
		List<Long> listLbael=(List<Long>)maps.get("list");
		List<LabelCentre> lists=new ArrayList<LabelCentre>();
		for(int i=0;i<listLbael.size();i++){
			LabelCentre label=new LabelCentre();
			label.setOrderSql(i+1);
			label.setLabelId(listLbael.get(i));
			label.setObjectId(subjectId);
			label.setCategory(1);
			lists.add(label);
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("list", lists);
		labelDao.addLabelCentreList(map);
		//保存内容
		List<SubjectItem> listItem=(List<SubjectItem>)maps.get("listItem");
		for(int i=0;i<listItem.size();i++){
			SubjectItem item=listItem.get(i);
			item.setSubjectId(subjectId);
			item.setOrderSeq(i+1);
			subjectDao.saveItem(item);
		}
		return 1;
	}
	public Map<String,Object> update(Map<String,Object> params) {
		Map<String,Object> resultMap=new HashMap<String,Object>();//结果集
		boolean isSuccess =false;
		Subject subject=(Subject)params.get("model");
		//检查名称是否重复
		//增加
		Long subjectId=subject.getSubjectId();
		 int updateNum=subjectDao.update(subject);
		 if(updateNum>0){
			 isSuccess=true;
		 }
		 //删除所有内容
		 subjectDao.deleteItem(subjectId);
		 //增加内容
		 params.put("subjectId", subjectId);
		 addSubjectItem(params);
//		//删除标签
//		Map<String,Object> map=new HashMap<String,Object>();
//		map.put("subjectId", subject.getSubjectId());
//		map.put("category", 1);
//		labelDao.deleteLabelCentreList(map);
//		//添加标签
//		String labelId=subject.getLabelId();
//		if(StringUtils.isNotEmpty(labelId)){
//			String[] labelIdsArr=labelId.split(",");
//			List<LabelCentre> lists=new ArrayList<LabelCentre>();
//			for(int j=0;j<labelIdsArr.length;j++){
//				LabelCentre label=new LabelCentre();
//				label.setOrderSql(j+1);
//				label.setLabelId(Long.parseLong(labelIdsArr[j]));
//				label.setObjectId(subject.getSubjectId());
//				label.setCategory(1);
//				lists.add(label);
//			}
//			Map<String,Object> mapHash=new HashMap<String,Object>();
//			mapHash.put("list", lists);
//			labelDao.addLabelCentreList(mapHash);
//		}
		 resultMap.put("status", isSuccess);
		 resultMap.put("msg", "");
		 return resultMap;
	} 
	 
	/**
	 * 增加专题内容数据
	 * @param params
	 */
	public void addSubjectItem(Map<String,Object> params){
		Long subjectId=(Long)params.get("subjectId");
		 String subjectItem[]=(String[])params.get("subjectItem");
		 String subjectItemType[]=(String[])params.get("subjectItemType");
		 String urlType[]=(String[])params.get("urlType");
		 String subjectUrl[]=(String[])params.get("subjectUrl");
		 for (int i = 0; i < subjectItem.length; i++) {
			 SubjectItem item=new SubjectItem();
			 String subjectItemStr=subjectItem[i];
			 String linkType="";
			 if(subjectUrl.length!=0){
				 linkType=subjectUrl[i];//链接或ID 
			 }
			 String type=urlType[i];
			 item.setSubjectId(subjectId);
			 item.setContentItemData(subjectItemStr);
			 Integer itemType=ObjectUtil.getInteger(subjectItemType[i],null);
			 item.setContentItemType(itemType);
			 item.setOrderSeq(i+1);
			 if(itemType!=null&&itemType==2){//图片类型
				 String imgUrl= subjectItemStr.substring(0,subjectItemStr.indexOf("?"));
				 item.setContentItemData(imgUrl);
				 item.setParam1(subjectItemStr.substring(subjectItemStr.indexOf("param1=")+7,subjectItemStr.indexOf("&param2")));
				 item.setParam2(subjectItemStr.substring(subjectItemStr.indexOf("param2=")+7));
			 }
			 if(!linkType.equals("") && linkType!=null){
				 item.setType(LinkType.getLinkTypeByDesc(type).getCode());
				 item.setLinkType(linkType);
			 }
			 if(StringUtils.isNotEmpty(subjectItemStr)){
				 subjectDao.saveItem(item); 
			 }
			 
		}
	}
	
	public int delete(Subject subject) {
		subjectDao.deleteItem(subject.getSubjectId());
		return subjectDao.delete(subject);
	}
	public int updateShow(Subject subject){
		return subjectDao.updateShow(subject);
	}
	public Subject getSubjectById(Long id) {
		Subject subject= subjectDao.getSubjectById(id);
		if(subject!=null){
			subject.setOutPic(ImageUtil.getShowimageUrl(subject.getOutPic()));
			List<SubjectItem> contentItemList= subject.getContentItemList();
			for (SubjectItem item:contentItemList) {
						if(item.getContentItemType()==2){
							item.setContentItemImg(ImageUtil.getShowimageUrl(item.getContentItemData()));
						}else if(item.getContentItemType()==3){
							Goods goods = goodsService.getGoodsBySn(item.getContentItemData());
							if(goods!=null){
								String date = DateUtil.formatDate(goods.getCreateTime(), "yyyyMMdd");
								String url="http://image4.zoshow.com/upload/goods" + date + "/" +
										goods.getGoodsSn() + "/" + goods.getMainSku() + "/g1_402_536.jpg";
								item.setGoodsImage(url);
							}
						}
				}
			//根据Id去查询标签
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("subjectId", subject.getSubjectId());
			params.put("category", 1);
			List<LabelCentre> labelList=labelDao.findLabelBySubjectId(params);
			if(labelList.size()>0){
				subject.setLabelCentre(labelList);
			}
		}
		return subject;
	}
	

	public int checkTimeByOrderSeq(Map<String,Object> params) {
		return subjectDao.checkTimeByOrderSeq(params);
	}

	public String getTimesByOrderSeq(Map<String,Object> params) {
		List<Subject> subjects=subjectDao.getTimesByOrderSeq(params);
		StringBuffer sb=new StringBuffer();
		int total=subjects.size();
		for (int i=0;i<total;i++) {
			Subject subject=subjects.get(i);
			if(i>0){
				sb.append("<br/>");
			}
			sb.append(DateUtil.formatDate(subject.getStartTime()));
			sb.append("到");
			sb.append(DateUtil.formatDate(subject.getEndTime()));
		}
		
		return sb.toString();
	}
	

	public Map<String,Object> getSubjectCommentList(Map<Object, Object> params, Page<?> page) {
		Map<String,Object> resultMap=new HashMap<String,Object>();//结果集
		Boolean resultStatus=false;
		List<SubjectComment> clist=new ArrayList<SubjectComment>();

		Object startDate= params.get("startDate");
		Object endDate= params.get("endDate");
		if(startDate!=null&&!startDate.equals("")){
			params.put("startDate",DateUtil.parseTime(startDate.toString()));
		}
		if(endDate!=null&&!endDate.equals("")){
			params.put("endDate",DateUtil.parseTime(endDate.toString()));
		}
		page.setPageSize(20);
        //获取总数
	    page.setTotalCount(Integer.valueOf(subjectDao.getSubjectCommentCount(params)));

		params.put("pageMin",page.getFirstRecord());
		params.put("pageSize", page.getPageSize());
		params.put("pageMax", page.getEndRecord());
		
		//查询列表
		clist= subjectDao.getSubjectCommentList(params);
		//成功
		resultStatus=true;
		
		resultMap.put("page", page);
		resultMap.put("status", resultStatus);
		resultMap.put("msg","");
		resultMap.put("resultInfo", clist);
		return resultMap;
	}
	
	public Integer deleteSubjectComment(Long commentId,Long userId,String userName,String resultType) {
		//删除评论
		Integer count= subjectDao.deleteSubjectComment(commentId);
		return count;
	}

	public Integer deleteBatchSubjectComment(Map<String,Object> params){
		//删除评论
		Integer num= subjectDao.deleteBatchSubjectComments(params);
		return num;
	}

	public SubjectComment getSubjectCommentInfo(Long commentId) {
		SubjectComment vo =subjectDao.getSubjectCommentById(commentId);
		return vo;
	}

	@Override
	public List<SubjectLabel> getSubjectLabel(int category) {
		return subjectLabelDao.getSubjectLabel(category);
	}

//提取专题excel表中的数据
	public Map<String,Object> getSubjectInfo(List<Map<Object, Object>> listMap){
		Map<String,Object> params=new HashMap<String,Object>();
		Subject subject=new Subject();
		//名称
		Map<Object, Object> map = listMap.get(0);
		String name=(String)map.get(2);
		subject.setName(name);
		//标题
		Map<Object, Object> map1 = listMap.get(1);
		String title=(String)map1.get(2);
		subject.setTitle(title);
		//类型
		Map<Object, Object> map2 = listMap.get(2);
		String displayStytle=(String)map2.get(2);
		if(displayStytle.equals("竖专题(大)")){
			subject.setDisplayStytle(ObjectUtil.getInteger("1", 1));
		}else if(displayStytle.equals("横专题(小)")){
			subject.setDisplayStytle(ObjectUtil.getInteger("2", 1));
		}
		//排序值
		Map<Object, Object> map3 = listMap.get(3);
		String orderSeq=(String)map3.get(2);
		if(orderSeq.equals("") || orderSeq==null){
    		orderSeq="100";
    	}
		subject.setOrderSeq(ObjectUtil.getInteger(orderSeq, 1));
		//专题图片
		Map<Object, Object> map4 = listMap.get(4);
		String out_pic=(String)map4.get(2);
		subject.setOutPic(out_pic);
		//开始时间
		Map<Object, Object> map5 = listMap.get(5);
		String beginTime=(String)map5.get(2);
		subject.setStartTime(DateUtil.parseTime(beginTime)); 
		//结束时间
    	Map<Object, Object> map6 = listMap.get(6);
		String endTime=(String)map6.get(2);
		subject.setEndTime(DateUtil.parseTime(endTime));
		//标签
		Map<Object, Object> map7 = listMap.get(7);
		String labelName=(String)map7.get(2);
		String[] labelIdsArr=labelName.split(",");
		List<Long> list=new ArrayList<Long>();
		for(int j=0;j<labelIdsArr.length;j++){
			Map<String,Object> maps=new HashMap<String,Object>();
			maps.put("title", labelIdsArr[j]);
			maps.put("category", 1);
			Long rabelId=labelDao.findLabelIdByTitle(maps).getLabelId();
			list.add(rabelId);
		}
		//内容
		List<SubjectItem> listItem=new ArrayList<SubjectItem>();
		for (int i = 9; i < listMap.size(); i++) {
			 SubjectItem item=new SubjectItem();
			Map<Object, Object> maps = listMap.get(i);
			 String subjectItemStr=(String)maps.get(2);//详情
			 String linkType=(String)maps.get(4);//链接地址
			 String type=(String)maps.get(3);//链接类型
			 String itemType=(String)maps.get(1);//图片or文字
			 Integer itemTypes=0;
			 if(itemType.equals("文字")){
				 itemTypes=1;
			 }else if(itemType.equals("图片")){
				 itemTypes=2;
			 }else{
				 itemTypes=3;
			 }
			 item.setContentItemData(subjectItemStr);
			 item.setContentItemType(itemTypes);
			 if(itemTypes!=null&&itemTypes==2){//图片类型
				 String path=ImageUtil.getShowImageAdd(subjectItemStr);
				 File file = new File(path);
				 BufferedImage buff=null;
				 try {
					buff = ImageIO.read(file);
				} catch (IOException e) {
					e.printStackTrace();
				} 
				 item.setParam1(String.valueOf(buff.getWidth()));
				 item.setParam2(String.valueOf(buff.getHeight()));
			 }
			 if(StringUtils.isNotBlank(linkType)){
				 item.setType(LinkType.getLinkTypeByDesc(type).getCode());
				 item.setLinkType(linkType);
			 }
			 listItem.add(item);
		}
		params.put("subject", subject);
		params.put("list", list);
		params.put("listItem", listItem);
		return params;
	}
	public int addSubjectComment(Map<String,Object> params){
		params.put("deleteFlag", 0);
		return subjectDao.addSubjectComment(params);
	}
	@Override
	public Integer getSyncToSearchCount(Map<String, Object> params) {
		return subjectDao.getSyncToSearchCount(params);
	}
	@Override
	public List<Subject> getSyncToSearchList(Map params) {
		Map result =new HashMap();
		Integer page=ObjectUtil.getInteger(params.get("pageNo"), 1);
		Integer pageSize=ObjectUtil.getInteger(params.get("pageSize"), 100);
		int lineMax =page * pageSize+1;
		int lineMin = (page-1)*pageSize+1;
		params.put("pageMax", lineMax);
		params.put("pageMin", lineMin);
		List<Subject> topics=subjectDao.getSyncToSearchList(params);
		return topics;
	}
}
