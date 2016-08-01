package com.xiu.mobile.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.common.lang.StringUtil;
import com.xiu.mobile.core.dao.DataBrandDao;
import com.xiu.mobile.core.model.DataBrandBo;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.util.GB2AlphaUtil;
import com.xiu.mobile.portal.common.util.ImageServiceConvertor;
import com.xiu.mobile.portal.model.DataBrandVo;
import com.xiu.mobile.portal.model.WellChosenBrandVo;
import com.xiu.mobile.portal.service.IMemcachedService;
import com.xiu.mobile.portal.service.IWellChosenBrandService;
/**
 * <p>
 * ************************************************************** 
 * @Description: 精选品牌业务逻辑层
 * @AUTHOR wangchengqun
 * @DATE 2014-6-5
 * ***************************************************************
 * </p>
 */
@Service("wellChosenBrandManager")
public class WellChosenBrandServiceImpl implements IWellChosenBrandService{
	private Logger logger = Logger.getLogger(WellChosenBrandServiceImpl.class);

	@Autowired
	private DataBrandDao dataBrandDao;
	@Autowired
	private IMemcachedService memcachedService;
	
	@Override
	public List<DataBrandBo> getWellChosenBrandList() throws Exception{
		return dataBrandDao.getWellChosenBrandList();
	}

	@Override
	public WellChosenBrandVo getSortList(List<DataBrandBo> wellChosenBrandBoList)throws Exception {
		//按照enName首字符（中文，英文，数字）进行分组
		WellChosenBrandVo wcb=new WellChosenBrandVo();
		int len=wellChosenBrandBoList.size();
		GB2AlphaUtil gb2=new GB2AlphaUtil();
	 for(int i=0;i<len;i++){
		 if(wellChosenBrandBoList.get(i).getEnName()!=null){
		 String firstName=(wellChosenBrandBoList.get(i).getEnName()).substring(0, 1);
		 String name=gb2.String2Alpha(firstName);
		 String img=wellChosenBrandBoList.get(i).getAuthimgurl();
		
		 DataBrandVo dataBrandVo=new DataBrandVo();
		 dataBrandVo.setBrandId(wellChosenBrandBoList.get(i).getBrandId());
		 dataBrandVo.setBrandCode(wellChosenBrandBoList.get(i).getBrandCode());
		 dataBrandVo.setBrandName(wellChosenBrandBoList.get(i).getBrandName());
		 dataBrandVo.setEnName(wellChosenBrandBoList.get(i).getEnName());
		 String story = wellChosenBrandBoList.get(i).getStory();
		 if(StringUtil.isNotBlank(story)) {
			 if(story.length() <= 60) {
				 dataBrandVo.setStory(story);
			 } else {
				 dataBrandVo.setStory(story.substring(0, 60));
			 }
		 }
		 
		 if(null!=img&&!"".equals(img)){
				int index = img.lastIndexOf(".");
	        	int lastNum = Integer.parseInt(img.substring(index-1, index),16);
	        	dataBrandVo.setBrandImg(ImageServiceConvertor.replaceImageDomain(
	            		XiuConstant.MOBILE_IMAGE[lastNum%3]+"/UploadFiles/xiu/brand"+img));
			}else{
				dataBrandVo.setBrandImg("");
			}
		 if(name.equals("A")){
			 wcb.getaList().add(dataBrandVo);
		 }else if(name.equals("B")){
			 wcb.getbList().add(dataBrandVo);
		 }else if(name.equals("C")){
			 wcb.getcList().add(dataBrandVo);
		 }else if(name.equals("D")){
			 wcb.getdList().add(dataBrandVo);
		 }else if(name.equals("E")){
			 wcb.geteList().add(dataBrandVo);
		 }else if(name.equals("F")){
			 wcb.getfList().add(dataBrandVo);
		 }else if(name.equals("G")){
			 wcb.getgList().add(dataBrandVo);
		 }else if(name.equals("H")){
			 wcb.gethList().add(dataBrandVo);
		 }else if(name.equals("I")){
			 wcb.getiList().add(dataBrandVo);
		 }else if(name.equals("J")){
			 wcb.getjList().add(dataBrandVo);
		 }else if(name.equals("K")){
			 wcb.getkList().add(dataBrandVo);
		 }else if(name.equals("L")){
			 wcb.getlList().add(dataBrandVo);
		 }else if(name.equals("M")){
			 wcb.getmList().add(dataBrandVo);
		 }else if(name.equals("N")){
			 wcb.getnList().add(dataBrandVo);
		 }else if(name.equals("O")){
			 wcb.getoList().add(dataBrandVo);
		 }else if(name.equals("P")){
			 wcb.getpList().add(dataBrandVo);
		 }else if(name.equals("Q")){
			 wcb.getqList().add(dataBrandVo);
		 }else if(name.equals("R")){
			 wcb.getrList().add(dataBrandVo);
		 }else if(name.equals("S")){
			 wcb.getsList().add(dataBrandVo);
		 }else if(name.equals("T")){
			 wcb.gettList().add(dataBrandVo);
		 }else if(name.equals("U")){
			 wcb.getuList().add(dataBrandVo);
		 }else if(name.equals("V")){
			 wcb.getvList().add(dataBrandVo);
		 }else if(name.equals("W")){
			 wcb.getwList().add(dataBrandVo);
		 }else if(name.equals("X")){
			 wcb.getxList().add(dataBrandVo);
		 }else if(name.equals("Y")){
			 wcb.getyList().add(dataBrandVo);
		 }else if(name.equals("Z")){
			 wcb.getzList().add(dataBrandVo);
		 }else {
			 try{
				 int num=Integer.parseInt(name);
				 if(num>0&&num<10){
					 wcb.getNumList().add(dataBrandVo);
				 }
			 }catch(NumberFormatException e){
				 logger.info("按照brandName首字符"+name+"分组时首字母为特殊字符："+e.getMessage());
				 continue;
			 }
		 }
	}else{
		throw new Exception();
	}
	}
	 return wcb;
	}
	@Override
	public List<Object> getResultList(WellChosenBrandVo wcb)throws Exception{
		List<Object> wellChosenBrandList=new ArrayList<Object>();
		 String[] names=wcb.getNames();
		 List<Object> lists=wcb.getLists();
		 lists.add(wcb.getaList());
		 lists.add(wcb.getbList());
		 lists.add(wcb.getcList());
		 lists.add(wcb.getdList());
		 lists.add(wcb.geteList());
		 lists.add(wcb.getfList());
		 lists.add(wcb.getgList());
		 lists.add(wcb.gethList());
		 lists.add(wcb.getiList());
		 lists.add(wcb.getjList());
		 lists.add(wcb.getkList());
		 lists.add(wcb.getlList());
		 lists.add(wcb.getmList());
		 lists.add(wcb.getnList());
		 lists.add(wcb.getoList());
		 lists.add(wcb.getpList());
		 lists.add(wcb.getqList());
		 lists.add(wcb.getrList());
		 lists.add(wcb.getsList());
		 lists.add(wcb.gettList());
		 lists.add(wcb.getuList());
		 lists.add(wcb.getvList());
		 lists.add(wcb.getwList());
		 lists.add(wcb.getxList());
		 lists.add(wcb.getyList());
		 lists.add(wcb.getzList());
		 lists.add(wcb.getNumList());
		 for(int i=0;i<names.length;i++){
			 Map<String,Object> wellChosenBrandMap=new HashMap<String,Object>();
			 wellChosenBrandMap.put("name",names[i]);
			 wellChosenBrandMap.put("list", lists.get(i));
			 wellChosenBrandList.add(wellChosenBrandMap);
		 }
		return wellChosenBrandList;
	}
	@Override
	public List<Object> findAllBrands() throws Exception{
		//读缓存
		List<Object> allBrandList=memcachedService.findAllBrands();
		if(allBrandList==null || allBrandList.size()==0){
			//如果缓存中不存在，则查询数据库并添加至缓存
			List<DataBrandBo> list=dataBrandDao.findAllBrands();
			List<Object> wellChosenBrandList=new ArrayList<Object>();
			WellChosenBrandVo wcb=new WellChosenBrandVo();
			if(null!=list && list.size()>0){
				wcb=getSortList(list);
				wellChosenBrandList=getResultList(wcb);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(XiuConstant.BRAND_ALL, wellChosenBrandList);
				memcachedService.addAllBrands(map);
				return wellChosenBrandList;
			}
		}
		return allBrandList;
	}

}
