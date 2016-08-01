package com.xiu.mobile.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.core.constants.ErrConstants;
import com.xiu.mobile.core.dao.FindGoodsDao;
import com.xiu.mobile.core.dao.GoodsDao;
import com.xiu.mobile.core.dao.LoveGoodsDao;
import com.xiu.mobile.core.dao.SubjectLabelDao;
import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.core.model.FindGoods;
import com.xiu.mobile.core.model.FindGoodsBo;
import com.xiu.mobile.core.model.FindGoodsVo;
import com.xiu.mobile.core.model.Goods;
import com.xiu.mobile.core.model.LabelCentre;
import com.xiu.mobile.core.model.LoveGoodsBo;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.service.IFindGoodsService;
import com.xiu.mobile.core.utils.DateUtil;
/**
 * <p>
 * ************************************************************** 
 * @Description: 发现商品业务逻辑层
 * @AUTHOR wangchengqun
 * @DATE 2014-6-4
 * ***************************************************************
 * </p>
 */
@Service("findGoodsService")
public class FindGoodsServiceImpl implements IFindGoodsService{

	private static final XLogger logger = XLoggerFactory.getXLogger(FindGoodsServiceImpl.class);
	
	@Autowired
	private FindGoodsDao findGoodsDao;
	@Autowired
	private GoodsDao goodsDao;
	@Autowired
	private LoveGoodsDao loveGoodsDao;
	@Autowired
	private SubjectLabelDao subjectLabelDao;
	
	@Override
	public boolean isFindGoodsExist(String goodsSn) {
		FindGoods goods=findGoodsDao.getFindGoodsBySn(goodsSn);
		if(goods==null){
			return false;
		}else{
			 if(goods.getEndDate().before(new Date())){//删除已经过期的重复数据
				 deleteFindGoods(goods.getId());
				 return false;	
			 }else{
				 return true;	
			 }
		}
	}
	
	@Override
	public FindGoods getFindGoodsBySn(String goodsSn) {
		return findGoodsDao.getFindGoodsBySn(goodsSn);
	}
	
	@Override
	public List<FindGoodsVo> getFindGoodsList(HashMap<String, Object> valMap) throws Exception{
		List<FindGoodsVo> findGoodsList = findGoodsDao.getFindGoodsList(valMap);
		return findGoodsList;
	}

	@Override
	public int getFindGoodsListCnt()throws Exception {
		return findGoodsDao.getFindGoodsListCount();
	}

	@Override
	public List<LoveGoodsBo> getLoveGoodsList(HashMap<String, Object> valMap)
			throws Exception {
		List<LoveGoodsBo> loveGoodsList=loveGoodsDao.getLoveGoodsList(valMap);
		return loveGoodsList;
	}

	@Override
	public int getLovedCountofGoods(String goodsSn) throws Exception {
		return loveGoodsDao.getLoveGoodsListCount(goodsSn);
	}

	@Override
	public int addLovedTheGoods(LoveGoodsBo loveGoods) throws Exception {
		return loveGoodsDao.addLovedTheGoods(loveGoods)>=0?0:-1;
	}

	@Override
	public List<FindGoodsBo> searchFindGoodsList(FindGoodsBo findGoodsBo, Page<?> page)
			throws Exception {
		try{
			return null;
		}catch(Exception e){
			throw ExceptionFactory.buildBaseException(ErrConstants.BusinessErrorCode.BIZ_FIND_GOODS_ERR, e);
		}
	}

	@Override
	public int delLovedTheGoods(HashMap<String, Object> valMap)
			throws Exception {
		int result = 0;
		result =loveGoodsDao.delLovedTheGoods(valMap)>=0?0:-1;
		return result;
	}
	
	
	@Override
	public int addFindGoods(FindGoods findGoods) {
		// 如果表中有正在进行或未开始的先把旧的设为被替换(replace = Y)
		List<String> sn = new ArrayList<String>();
		sn.add(findGoods.getGoodsSn());
		findGoodsDao.upateReplace(sn);
		long goodsId=findGoodsDao.findGoodsId();
		findGoods.setId(goodsId);
		int total=findGoodsDao.addFindGoods(findGoods);
		//添加标签
		String labelId=findGoods.getLabelId();
		if(labelId!=null && labelId!=""){
			String[] labelIdsArr=labelId.split(",");
			List<LabelCentre> lists=new ArrayList<LabelCentre>();
			for(int j=0;j<labelIdsArr.length;j++){
				LabelCentre label=new LabelCentre();
				label.setOrderSql(j+1);
				label.setLabelId(Long.parseLong(labelIdsArr[j]));
				label.setObjectId(findGoods.getId());
				label.setSubjectId(findGoods.getId());
				label.setCategory(2);
				lists.add(label);
			}
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("list", lists);
			subjectLabelDao.addLabelCentreList(params);
		}
		return total;
	}

	@Override
	public int updateFindGoods(FindGoods findGoods) {
		int i=findGoodsDao.updateFindGoods(findGoods);
		//删除标签
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("subjectId", findGoods.getId());
		map.put("category", 2);
		subjectLabelDao.deleteLabelCentreList(map);
		//添加标签
		String labelId=findGoods.getLabelId();
		if(StringUtils.isNotEmpty(labelId)){
			String[] labelIdsArr=labelId.split(",");
			List<LabelCentre> lists=new ArrayList<LabelCentre>();
			for(int j=0;j<labelIdsArr.length;j++){
				LabelCentre label=new LabelCentre();
				label.setOrderSql(j+1);
				label.setLabelId(Long.parseLong(labelIdsArr[j]));
				label.setObjectId(findGoods.getId());
				label.setSubjectId(findGoods.getId());
				label.setCategory(2);
				lists.add(label);
			}
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("list", lists);
			subjectLabelDao.addLabelCentreList(params);
		}
		return i;
	}

	@Override
	public int deleteFindGoods(long id) {
		return findGoodsDao.deleteFindGoods(id);
	}

	@Override
	public int batchDelete(List<Long> ids) {
		return findGoodsDao.batchDelete(ids);
	}

	@Override
	public FindGoods getFindGoods(long id) {
		FindGoods goods=findGoodsDao.getFindGoods(id);
		//根据Id去查询标签
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("subjectId", goods.getId());
		params.put("category", 2);
		List<LabelCentre> labelList=subjectLabelDao.findLabelBySubjectId(params);
		if(labelList.size()>0){
			goods.setLabelCentre(labelList);
		}
		return goods;
	}

	@Override
	public List<FindGoods> searchFindGoodsList(FindGoods findGoods,
			Page<?> page){
		try{
			// 分页参数
            int pageMax = page.getPageNo() * page.getPageSize();
            int pageMin = 1;
            if (page.getPageNo() > 1) {
                pageMin = (page.getPageNo() - 1) * page.getPageSize() + 1;
            }
            findGoods.setPageMin(pageMin);
            findGoods.setPageMax(pageMax);
			int count = findGoodsDao.searchFindGoodsCount(findGoods);
			page.setTotalCount(count);
			List<FindGoods> goodsList = findGoodsDao.searchFindGoodsList(findGoods);
			List<Long> lists=new ArrayList<Long>();//标签处理
			for(FindGoods good:goodsList){
				lists.add(good.getId());
			}
			//查询标签
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("subjectId", lists);
			params.put("category", 2);//2表示单品
			Map<Long,List<LabelCentre>> resultMap=findLabelList(params);
			for(FindGoods good:goodsList){
				if(resultMap!=null){
					List<LabelCentre> labelList=(List<LabelCentre>)resultMap.get(good.getId());
					good.setLabelCentre(labelList);
				}
			}
			return goodsList;
		}catch(Exception e){
			throw ExceptionFactory.buildBaseException(ErrConstants.BusinessErrorCode.BIZ_FIND_GOODS_ERR, e);
		}
	}
	//批量查询标签
	private Map<Long,List<LabelCentre>> findLabelList(Map<String,Object> map){
		List<LabelCentre> list=subjectLabelDao.findLabelCentreList(map);
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
	@Override
	public List<FindGoods> findAll() {
		return findGoodsDao.findAll();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int importGoods(Map<String, Map<Object,Object>> data,Long createUserId) {
		int count = 0;
		int totalCount = data.size();
		long startTime = System.currentTimeMillis();
		try{
			logger.debug("=======================开始导入商品========去重后模板文件数据总数量：{} =================",new Object[]{totalCount});
			
			// 提取goodsSnList
			List<String> goodsSnList = new ArrayList<String>();
			Iterator itGoodsSn = data.entrySet().iterator();
			while(itGoodsSn.hasNext()){
				Map.Entry entry = (Map.Entry) itGoodsSn.next();
				String sn = (String) entry.getKey();
				goodsSnList.add(sn);
			}
			/*List<String> goodsSnList = goodsDao.checkGoodsSn(goodsSn);
			Map<String, Map<Object,Object>> realData = new HashMap<String, Map<Object,Object>>();
			if(goodsSnList !=null && !goodsSnList.isEmpty()){
				// 筛选goodsSn
				for(String sn : goodsSnList){
					realData.put(sn, data.get(sn));
				}
			}*/
			
			// 把表中正在进行或未开始的记录设为被替代(replace = Y)
			findGoodsDao.upateReplace(goodsSnList);
			
			// 开始导入
			Iterator it = data.entrySet().iterator();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				String sn = (String) entry.getKey();
				Map<Object, Object> map = (Map<Object, Object>) entry.getValue();
				
				// 发现商品标题
				String title = (String)map.get(1);
				// 编辑语
				String content = (String)map.get(2);
				// 排序
				String sort = (String)map.get(3);
				// 开始时间
				String sDate = (String)map.get(4);
				// 结束时间
				String eDate = (String)map.get(5);
				//标签
				String label=(String) map.get(6);
				
				// 插入数据
				// 查询商品信息
				Goods xiuGoods = goodsDao.getGoodsBySn(sn);
				String date = DateUtil.formatDate(xiuGoods.getCreateTime(), "yyyyMMdd");
				String imageUrl = "upload/goods" + date + "/" + 
						xiuGoods.getGoodsSn() + "/" + xiuGoods.getMainSku() + "/g1_162_216.jpg";
				//查询ID
				long goodsId=findGoodsDao.findGoodsId();
				FindGoods goods = new FindGoods();
				goods.setId(goodsId);
				goods.setContent(content);
				goods.setCreateBy(createUserId);
				goods.setCreateDate(Calendar.getInstance().getTime());
				goods.setGoodsImage(imageUrl);
				goods.setGoodsName(xiuGoods.getGoodsName());
				goods.setGoodsSn(sn);
				goods.setOrderSequence(Long.parseLong(sort));
				goods.setStartDate(sf.parse(sDate));
				goods.setEndDate(sf.parse(eDate));
				goods.setTitle(title);
				findGoodsDao.addFindGoods(goods);
				//添加标签
				if(StringUtils.isNotEmpty(label)){
					String[] labelIdsArr=label.split(",");
					List<LabelCentre> lists=new ArrayList<LabelCentre>();
					for(int j=0;j<labelIdsArr.length;j++){
						LabelCentre labelCentre=new LabelCentre();
						labelCentre.setLabelId(Long.parseLong(labelIdsArr[j]));
						labelCentre.setObjectId(goodsId);
						labelCentre.setSubjectId(goodsId);
						labelCentre.setCategory(2);//单品
						lists.add(labelCentre);
					}
					Map<String,Object> params=new HashMap<String,Object>();
					params.put("list", lists);
					subjectLabelDao.addLabelCentreList(params);
				}
				count++;
			}
		}catch(Exception e){
			logger.error("导入单品发现商品异常", e);
			throw ExceptionFactory.buildBaseException(ErrConstants.BusinessErrorCode.BIZ_FIND_GOODS_ERR, e);
		}
		long endTime = System.currentTimeMillis();
		logger.debug("=============== 导入商品完成 =========== 成功导入条数:" 
		+ count + " 花费时间:" + ((endTime - startTime)/1000) + " 秒 =====================");
		return count;
	}

	@Override
	public List<String> checkGoodsSn(List<String> goodsSnList) {
		return goodsDao.checkGoodsSn(goodsSnList);
	}
	
	

}
