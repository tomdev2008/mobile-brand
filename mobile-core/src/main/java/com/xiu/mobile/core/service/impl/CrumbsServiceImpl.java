package com.xiu.mobile.core.service.impl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.core.model.CrumbsVo;
import com.xiu.mobile.core.model.GoodsSolrModel;
import com.xiu.mobile.core.service.ICrumbsService;
import com.xiu.mobile.core.service.IGoodsSolrService;
import com.xiu.mobile.core.param.SearchFatParam;
import com.xiu.search.solrj.service.SearchResult;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(面包屑业务逻辑实现类) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-11-18 上午10:06:53 
 * ***************************************************************
 * </p>
 */
@Service("crumbsService")
public class CrumbsServiceImpl implements ICrumbsService {

	@Autowired
	private IGoodsSolrService goodsSolrService;
	
	@Override
	public List<CrumbsVo> getCrumbsByGoodsSn(String goodsSn) {
		SearchFatParam solrParam = new SearchFatParam();
		solrParam.setGoodsSn(goodsSn);
		solrParam.setNeedAttrFlag(false);
		solrParam.setNeedCatalogFlag(false);
		
		SearchResult<GoodsSolrModel> result = goodsSolrService.search(solrParam);
		if(null != result && CollectionUtils.isNotEmpty(result.getBeanList())) {
			GoodsSolrModel item = result.getBeanList().get(0);
			String[] oclassPaths = item.getOclassPath();
			return parseOclassPaths(oclassPaths);
		}
		
		return null;
	}
	  
    /**
     * 处理运营分类路径 形如：4001:鞋子|4002:运动鞋|4003:阿迪达斯|4004:男鞋
     * 索引返回多个运营分类路径，取最长的路径
     * @param path
     * @return
     */
	private List<CrumbsVo> parseOclassPaths(String[] oclassPaths) {
		if (null == oclassPaths || oclassPaths.length == 0) {
			return null;
		}

		List<CrumbsVo> crumbsVos = new LinkedList<CrumbsVo>();
		
		int maxPathLength = 0; // 记录path的长度，用于取最长面包屑
		for (String path : oclassPaths) {

			// 运营分类父子之间使用“|”隔开
			List<String> oList = Arrays.asList(path.split("\\|"));
			if (CollectionUtils.isEmpty(oList) || maxPathLength >= oList.size()) {
				continue;
			}

			crumbsVos.clear();
			maxPathLength = oList.size();

			for (int i = 0; i < maxPathLength; i++) {
				List<String> oclass = Arrays.asList(oList.get(i).split(":"));
				CrumbsVo crumbs = new CrumbsVo();
				crumbs.setCatalogId(Integer.parseInt(oclass.get(0)));
				crumbs.setCatalogName(oclass.get(1));
				crumbs.setLevel(i + 1);
				crumbsVos.add(crumbs);
			}
		}

		return crumbsVos;
	}

}
