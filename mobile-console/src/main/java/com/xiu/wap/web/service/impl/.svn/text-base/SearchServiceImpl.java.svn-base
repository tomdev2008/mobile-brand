package com.xiu.wap.web.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiu.mobile.core.constants.GlobalConstants;
import com.xiu.mobile.core.dao.SearchDao;
import com.xiu.mobile.core.model.ServiceSearchDateBo;
import com.xiu.mobile.core.model.Subject;
import com.xiu.mobile.core.model.Topic;
import com.xiu.mobile.core.model.WapH5List;
import com.xiu.mobile.core.service.ISubjectManagerService;
import com.xiu.mobile.core.service.ITopicService;
import com.xiu.mobile.core.service.IWapH5ListService;
import com.xiu.sales.common.tools.DateUtils;
import com.xiu.show.core.model.ShowModel;
import com.xiu.show.core.service.IShowManagerService;
import com.xiu.wap.web.service.ISearchService;

/**
 * 
* @Description: TODO(搜索横表) 
* @author haidong.luo@xiu.com
* @date 2016年5月12日 下午5:41:45 
*
 */
@Transactional
@Service("searchService") 
public class SearchServiceImpl implements ISearchService { 
	
	@Autowired
	private SearchDao searchDao;
	@Autowired
	private ITopicService topicService;
    @Autowired
    private IWapH5ListService wapH5ListService;
    @Autowired
    private ISubjectManagerService subjectManagerService;    
    @Autowired
    private IShowManagerService showManagerService;    

    
	@Override
	public Integer addDataToSearch(Integer type, Long objectId) {
		Integer status=0;//默认失败
		String serviceName="";//业务数据名称
        String serviceValue="";//业务数据值
        Date serviceCreateDate=null;
        Date startTime=null;
        Date endTime=null;
		if(type.equals(GlobalConstants.LABEL_TYPE_TOPIC)){//卖场
			BigDecimal activityId=BigDecimal.valueOf(objectId);
			Topic topic=topicService.getTopicByActivityId(activityId);
			if(topic!=null&&topic.getContentType()==1){
				serviceValue=activityId.toString();
				serviceName=topic.getName();
				if(topic.getIsEndCanBeSearch()==1){
					startTime=topic.getStart_time();
					endTime=topic.getEnd_time();
				}
			}
		}else if(type.equals(GlobalConstants.LABEL_TYPE_SUBJECT)){//专题
			Subject subject=subjectManagerService.getSubjectById(objectId);
			if(subject!=null&&subject.getSubjectId()!=null){
				serviceValue=subject.getSubjectId().toString();
				serviceName=subject.getName();
				startTime=subject.getStartTime();
				endTime=subject.getEndTime();
			}
		}else if(type.equals(GlobalConstants.LABEL_TYPE_H5)){//h5
			WapH5List h5=wapH5ListService.getWapH5ListById(objectId);
			if(h5!=null&&h5.getH5Url()!=null&&!h5.getH5Url().equals("")){
				serviceValue=h5.getH5Url();
				serviceName=h5.getTitle();
			}
		}
		// 检查开始时间和结束时间是否进行中
		  Date now=new Date();
			if((startTime!=null&&startTime.before(now))||(endTime!=null&&endTime.after(now))){//不是进行中
				status=1;
				return status;
			}
			//查询业务关联表是否已经存在
				Map getParams=new HashMap();
				getParams.put("serviceId", objectId);
				getParams.put("type", type);
				ServiceSearchDateBo labelServiceDateBo=searchDao.getSearchDate(getParams);
				String pinyin=converterToSpell(serviceName);
				String pinyinFirst=converterToFirstSpell(serviceName);
				if(labelServiceDateBo==null){
					//插入业务关联表
					labelServiceDateBo=new ServiceSearchDateBo();
					labelServiceDateBo.setServiceContent(serviceValue);
					labelServiceDateBo.setServiceCreateDate(serviceCreateDate);
					labelServiceDateBo.setServiceId(objectId);
					labelServiceDateBo.setServiceName(serviceName);
					labelServiceDateBo.setServiceSort(100);
					labelServiceDateBo.setPinYin(pinyin);
					labelServiceDateBo.setFirstPinYin(pinyinFirst);
					labelServiceDateBo.setStartTime(startTime);
					labelServiceDateBo.setType(type);
					labelServiceDateBo.setEndTime(endTime);
					searchDao.addSearchDate(labelServiceDateBo);
				}else{
					labelServiceDateBo.setServiceContent(serviceValue);
					labelServiceDateBo.setServiceCreateDate(serviceCreateDate);
					labelServiceDateBo.setServiceId(objectId);
					labelServiceDateBo.setServiceName(serviceName);
					labelServiceDateBo.setPinYin(pinyin);
					labelServiceDateBo.setFirstPinYin(pinyinFirst);
					labelServiceDateBo.setServiceSort(100);
					labelServiceDateBo.setStartTime(startTime);
					labelServiceDateBo.setType(type);
					labelServiceDateBo.setEndTime(endTime);
					searchDao.updateSearchDate(labelServiceDateBo);
				}
				status=1;
		
		return status;
	}
	
	
	public void syncDataToSearch() {
		//1 删除已经过期的
		Map deleteParams=new HashMap();
		Date now=new Date();
		deleteParams.put("endDate",now);
		Integer pageSize=200;
		searchDao.deleteOverTimeSearchDate(deleteParams);
		//2将符合条件的数据同步到搜索表
		   //2.1  同步卖场
		   syncTopicToSearch(now,pageSize);
		  //2.2同步专题
		   syncSubjectToSearch(now,pageSize);
		  //2.3同步H5
		   syncH5ToSearch(now,pageSize);
	}
	
	//同步卖场
	public void syncTopicToSearch(Date now,Integer pageSize){
		   Map syncGetParams=new HashMap();
		   Integer pageNo=1;
		   syncGetParams.put("pageNo", pageNo);
		   syncGetParams.put("pageSize", pageSize);
//		   syncGetParams.put("lastUpdateDate", DateUtils.getDateOfParameterOnDay(now, -1));
		   Integer total= topicService.getSyncToSearchCount(syncGetParams);
		   Integer pageTotal=total/pageSize+1;
		   for (int i = 0; i < pageTotal; i++) {
//			   List<ServiceSearchDateBo> addSearchBos=new ArrayList<ServiceSearchDateBo>();
//			   List<ServiceSearchDateBo> updateSearchBos=new ArrayList<ServiceSearchDateBo>();
			   syncGetParams.put("pageNo", i+1);
			   List<Topic> topicResult=topicService.getSyncToSearchList(syncGetParams);
			   for (Topic topic:topicResult) {
				   Long activityId=topic.getActivity_id().longValue();
				   String serviceName=topic.getName();
				   Map getParams=new HashMap();
				   getParams.put("serviceId", activityId);
				   getParams.put("type", GlobalConstants.LABEL_TYPE_TOPIC);
				   ServiceSearchDateBo labelServiceDateBo=searchDao.getSearchDate(getParams);
				   String pinyin=converterToSpell(serviceName);
				   String pinyinFirst=converterToFirstSpell(serviceName);
				   if(labelServiceDateBo==null){
					   //插入业务关联表
					   labelServiceDateBo=new ServiceSearchDateBo();
				   }
					labelServiceDateBo.setServiceContent(activityId+"");
					labelServiceDateBo.setServiceCreateDate(null);
					labelServiceDateBo.setServiceId(activityId);
					labelServiceDateBo.setServiceName(serviceName);
					labelServiceDateBo.setServiceSort(100);
					labelServiceDateBo.setPinYin(pinyin);
					labelServiceDateBo.setFirstPinYin(pinyinFirst);
					labelServiceDateBo.setStartTime(topic.getStart_time());
					labelServiceDateBo.setType(GlobalConstants.LABEL_TYPE_TOPIC);
				    labelServiceDateBo.setEndTime(topic.getEnd_time());
				    if(labelServiceDateBo.getId()==null){
//				    	addSearchBos.add(labelServiceDateBo);
				    	searchDao.addSearchDate(labelServiceDateBo);
				    }else{
//				    	updateSearchBos.add(labelServiceDateBo);
				    	searchDao.updateSearchDate(labelServiceDateBo);
				    }
			   }
		   }
	}
	
	//同步专题
	public void syncSubjectToSearch(Date now,Integer pageSize){
		Map syncGetParams=new HashMap();
		Integer pageNo=1;
		syncGetParams.put("pageNo", pageNo);
		syncGetParams.put("pageSize", pageSize);
//		syncGetParams.put("lastUpdateDate", DateUtils.getDateOfParameterOnDay(now, -1));
		Integer total= subjectManagerService.getSyncToSearchCount(syncGetParams);
		Integer pageTotal=total/pageSize+1;
		for (int i = 0; i < pageTotal; i++) {
			   syncGetParams.put("pageNo", i+1);
			List<Subject> topicResult=subjectManagerService.getSyncToSearchList(syncGetParams);
			for (Subject subject:topicResult) {
				Long subjectId=subject.getSubjectId();
				String serviceName=subject.getName();
				Map getParams=new HashMap();
				getParams.put("serviceId", subjectId);
				getParams.put("type", GlobalConstants.LABEL_TYPE_SUBJECT);
				ServiceSearchDateBo labelServiceDateBo=searchDao.getSearchDate(getParams);
				String pinyin=converterToSpell(serviceName);
				String pinyinFirst=converterToFirstSpell(serviceName);
				if(labelServiceDateBo==null){
					//插入业务关联表
					labelServiceDateBo=new ServiceSearchDateBo();
				}
				labelServiceDateBo.setServiceContent(subjectId+"");
				labelServiceDateBo.setServiceCreateDate(subject.getCreateTime());
				labelServiceDateBo.setServiceCreateDate(null);
				labelServiceDateBo.setServiceId(subjectId);
				labelServiceDateBo.setServiceName(serviceName);
				labelServiceDateBo.setServiceSort(100);
				labelServiceDateBo.setPinYin(pinyin);
				labelServiceDateBo.setFirstPinYin(pinyinFirst);
				labelServiceDateBo.setStartTime(subject.getStartTime());
				labelServiceDateBo.setType(GlobalConstants.LABEL_TYPE_SUBJECT);
				labelServiceDateBo.setEndTime(subject.getEndTime());
				if(labelServiceDateBo.getId()==null){
//				    	addSearchBos.add(labelServiceDateBo);
					searchDao.addSearchDate(labelServiceDateBo);
				}else{
//				    	updateSearchBos.add(labelServiceDateBo);
					searchDao.updateSearchDate(labelServiceDateBo);
				}
			}
		}
	}
	//同步H5
	public void syncH5ToSearch(Date now,Integer pageSize){
		Map syncGetParams=new HashMap();
		Integer pageNo=1;
		syncGetParams.put("pageNo", pageNo);
		syncGetParams.put("pageSize", pageSize);
//		syncGetParams.put("lastUpdateDate", DateUtils.getDateOfParameterOnDay(now, -1));
		Integer total= wapH5ListService.getSyncToSearchCount(syncGetParams);
		Integer pageTotal=total/pageSize+1;
		for (int i = 0; i < pageTotal; i++) {
			syncGetParams.put("pageNo", i+1);
			List<WapH5List> topicResult=wapH5ListService.getSyncToSearchList(syncGetParams);
			for (WapH5List h5:topicResult) {
				Long h5Id=h5.getId();
				String serviceName=h5.getTitle();
				Map getParams=new HashMap();
				getParams.put("serviceId", h5Id);
				getParams.put("type", GlobalConstants.LABEL_TYPE_H5);
				ServiceSearchDateBo labelServiceDateBo=searchDao.getSearchDate(getParams);
				String pinyin=converterToSpell(serviceName);
				String pinyinFirst=converterToFirstSpell(serviceName);
				if(labelServiceDateBo==null){
					//插入业务关联表
					labelServiceDateBo=new ServiceSearchDateBo();
				}
				labelServiceDateBo.setServiceContent(h5Id+"");
				labelServiceDateBo.setServiceCreateDate(h5.getCreateTime());
				labelServiceDateBo.setServiceCreateDate(null);
				labelServiceDateBo.setServiceId(h5Id);
				labelServiceDateBo.setServiceName(serviceName);
				labelServiceDateBo.setServiceSort(100);
				labelServiceDateBo.setPinYin(pinyin);
				labelServiceDateBo.setFirstPinYin(pinyinFirst);
				labelServiceDateBo.setType(GlobalConstants.LABEL_TYPE_H5);
				if(labelServiceDateBo.getId()==null){
					searchDao.addSearchDate(labelServiceDateBo);
				}else{
					searchDao.updateSearchDate(labelServiceDateBo);
				}
			}
		}
	}
	
	 /** 
     * 汉字转换位汉语全拼，英文字符不变，特殊字符丢失 
     * 支持多音字，生成方式如（重当参:zhongdangcen,zhongdangcan,chongdangcen 
     * ,chongdangshen,zhongdangshen,chongdangcan） 
     *  
     * @param chines 
     *            汉字 
     * @return 拼音 
     */  
    public static String converterToSpell(String chines) {  
        StringBuffer pinyinName = new StringBuffer();  
        char[] nameChar = chines.toCharArray();  
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();  
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        for (int i = 0; i < nameChar.length; i++) {  
            if (nameChar[i] > 128) {  
                try {  
                    // 取得当前汉字的所有全拼  
                    String[] strs = PinyinHelper.toHanyuPinyinStringArray(  
                            nameChar[i], defaultFormat);  
                    if (strs != null) {  
                        for (int j = 0; j < strs.length; j++) {  
                            pinyinName.append(strs[j]);  
                            if (j != strs.length - 1) {  
                                pinyinName.append(",");  
                            }  
                        }  
                    }  
                } catch (BadHanyuPinyinOutputFormatCombination e) {  
                    e.printStackTrace();  
                }  
            } else {  
                pinyinName.append(nameChar[i]);  
            }  
            pinyinName.append(" ");  
        }  
//        System.out.println(pinyinName);
        // return pinyinName.toString();  
        return parseTheChineseByObject(discountTheChinese(pinyinName.toString()));  
    }  

    
    /** 
     * 汉字转换位汉语拼音首字母，英文字符不变，特殊字符丢失 支持多音字，生成方式如（长沙市长:cssc,zssz,zssc,cssz） 
     *  
     * @param chines 
     *            汉字 
     * @return 拼音 
     */  
    public static String converterToFirstSpell(String chines) {  
        StringBuffer pinyinName = new StringBuffer();  
        char[] nameChar = chines.toCharArray();  
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();  
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        for (int i = 0; i < nameChar.length; i++) {  
            if (nameChar[i] > 128) {  
                try {  
                    // 取得当前汉字的所有全拼  
                    String[] strs = PinyinHelper.toHanyuPinyinStringArray(  
                            nameChar[i], defaultFormat);  
                    if (strs != null) {  
                        for (int j = 0; j < strs.length; j++) {  
                            // 取首字母  
                            pinyinName.append(strs[j].charAt(0));  
                            if (j != strs.length - 1) {  
                                pinyinName.append(",");  
                            }  
                        }  
                    }  
                    // else {  
                    // pinyinName.append(nameChar[i]);  
                    // }  
                } catch (BadHanyuPinyinOutputFormatCombination e) {  
                    e.printStackTrace();  
                }  
            } else {  
                pinyinName.append(nameChar[i]);  
            }  
            pinyinName.append(" ");  
        }  
        // return pinyinName.toString();  
        return parseTheChineseByObject(discountTheChinese(pinyinName.toString()));  
    }  
    
    /** 
     * 去除多音字重复数据 
     *  
     * @param theStr 
     * @return 
     */  
    private static List<Map<String, Integer>> discountTheChinese(String theStr) {  
        // 去除重复拼音后的拼音列表  
        List<Map<String, Integer>> mapList = new ArrayList<Map<String, Integer>>();  
        // 用于处理每个字的多音字，去掉重复  
        Map<String, Integer> onlyOne = null;  
        String[] firsts = theStr.split(" ");  
        // 读出每个汉字的拼音  
        for (String str : firsts) {  
            onlyOne = new Hashtable<String, Integer>();  
            String[] china = str.split(",");  
            // 多音字处理  
            for (String s : china) {  
                Integer count = onlyOne.get(s);  
                if (count == null) {  
                    onlyOne.put(s, new Integer(1));  
                } else {  
                    onlyOne.remove(s);  
                    count++;  
                    onlyOne.put(s, count);  
                }  
            }  
            mapList.add(onlyOne);  
        }  
        return mapList;  
    }  
    
    
    
    
    /** 
     * 解析并组合拼音，对象合并方案(推荐使用) 
     *  
     * @return 
     */  
    private static String parseTheChineseByObject(  
            List<Map<String, Integer>> list) {  
        Map<String, Integer> first = null; // 用于统计每一次,集合组合数据  
        // 遍历每一组集合  
        for (int i = 0; i < list.size(); i++) {  
            // 每一组集合与上一次组合的Map  
            Map<String, Integer> temp = new Hashtable<String, Integer>();  
            // 第一次循环，first为空  
            if (first != null) {  
                // 取出上次组合与此次集合的字符，并保存  
                for (String s : first.keySet()) {  
                    for (String s1 : list.get(i).keySet()) {  
                        String str = s + s1;  
                        temp.put(str, 1);  
                    }  
                }  
                // 清理上一次组合数据  
                if (temp != null && temp.size() > 0) {  
                    first.clear();  
                }  
            } else {  
                for (String s : list.get(i).keySet()) {  
                    String str = s;  
                    temp.put(str, 1);  
                }  
            }  
            // 保存组合数据以便下次循环使用  
            if (temp != null && temp.size() > 0) {  
                first = temp;  
            }  
        }  
        String returnStr = "";  
        if (first != null) {  
            // 遍历取出组合字符串  
            for (String str : first.keySet()) {  
                returnStr += str ;  
//                returnStr += (str + ",");  
                break;
            }  
        }  
//        if (returnStr.length() > 0) {  
//            returnStr = returnStr.substring(0, returnStr.length() - 1);  
//        }  
        return returnStr;  
    }  
    

    
}
