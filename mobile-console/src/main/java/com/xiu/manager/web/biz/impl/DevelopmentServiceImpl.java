package com.xiu.manager.web.biz.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.manager.web.biz.DevelopmentService;
import com.xiu.manager.web.dao.DevelopmentDao;
import com.xiu.mobile.core.constants.ErrConstants;
import com.xiu.mobile.core.exception.ExceptionFactory;


/**
 *************************************************************** 
 * <p>
 * 
 * @DESCRIPTION : 开发者选项
 * @AUTHOR : haijun.chen@xiu.com
 * @DATE :2013-08-26
 *       </p>
 **************************************************************** 
 */
@Service("developmentService")
public class DevelopmentServiceImpl implements DevelopmentService {

	// 日志
	private static final XLogger LOGGER = XLoggerFactory
			.getXLogger(DevelopmentServiceImpl.class);

	@Autowired
	private DevelopmentDao developmentDao;

	/**
	 * 选项 ----执行 insert、update 、delete 语句
	 */
	@Override
	public boolean queryInsertOrUpdateOrDeleteSQL(String sql) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		boolean isSucess = false;
		try {
			// 将整条SQL语句作为查询条件
			map.put("sql", sql);

			// insert
			if ("insert".equals(sql.substring(0, 6).toLowerCase())
					&& developmentDao.queryInsertSQL(map) > 0) {
				isSucess = true;
			}
			// update
			if ("update".equals(sql.substring(0, 6).toLowerCase())
					&& developmentDao.queryUpdateSQL(map) > 0) {
				return true;
			}
			// delete
			if ("delete".equals(sql.substring(0, 6).toLowerCase())
					&& developmentDao.queryDeleteSQL(map) > 0) {
				return true;
			}
		} catch (Throwable e) {
			LOGGER.error("自助输入SQL操作异常！========SQL：" + sql, e);
			throw ExceptionFactory.buildBusinessException(
					ErrConstants.BusinessErrorCode.BIZ_COMMENT_ERR,
					"自助输入SQL操作异常！========SQL：" + sql);
		}
		return isSucess;
	}

	/**
	 * 开发者选项 ----执行 select 语句
	 */
	@Override
	public List<Map<Object, Object>> querySelectSQL(String sql) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			map.put("sql", sql);
			List<Map<Object, Object>> listmap = developmentDao.querySelectSQL(map);
			if (listmap != null && listmap.size() > 0) {
               return this.getResult(listmap);
			}
		} catch (Throwable e) {
			LOGGER.error("自助输入SQL操作异常！========SQL：" + sql, e);
			throw ExceptionFactory.buildBusinessException(
					ErrConstants.BusinessErrorCode.BIZ_COMMENT_ERR,
					"自助输入SQL操作异常！========SQL：" + sql);
		}
		return null;
	}
	
	
	/**
	 * 获得最大的结果集
	 * 
	 * @param listmap
	 * @return
	 */
	public List<Map<Object, Object>> getResult(List<Map<Object, Object>> listmap) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		int status = 0;
		for (int i = 0; i < listmap.size(); i++) {
			if (i != 0 && map.get(status) < listmap.get(i).size()) {
				map.put(i, listmap.get(i).size());
				status = i;
			} else {
				map.put(i, listmap.get(i).size());
			}
		}
		return this.getResultMap(listmap, status);
	}

	/**
	 * 对查询的结果集进行转换
	 * @param listmap
	 * @param status
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map<Object, Object>> getResultMap(List<Map<Object, Object>> listmap, int status) {
		List<Map<Object,Object>> _listmap=new ArrayList<Map<Object,Object>>();
		Set keys = listmap.get(status).keySet();
		for (Map<Object, Object> mp : listmap) {
			Iterator  iter= keys.iterator();
			while (iter.hasNext()) {
				String key=""+iter.next();
				mp.put(key, mp.get(key)==null?"":mp.get(key));
			}
			_listmap.add(mp);
		}
		return _listmap;
	}

}
