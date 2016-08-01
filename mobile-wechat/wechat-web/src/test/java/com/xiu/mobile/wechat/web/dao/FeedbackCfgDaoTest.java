package com.xiu.mobile.wechat.web.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.xiu.mobile.wechat.web.model.FeedbackCfgVo;

@ContextConfiguration(locations = {"classpath:applicationContext-web.xml"})
public class FeedbackCfgDaoTest extends AbstractJUnit4SpringContextTests {

	@Resource(name = "feedbackCfgDao")
	FeedbackCfgDao feedbackCfgDao;

	@Test
	public void testSelect() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("transId", "1217962501201406093403813278");
		params.put("feedbackId", "13199132596202774453");
		params.put("state", "N");
		List<FeedbackCfgVo> lst = feedbackCfgDao.getFeedbackCfgList(params);
		System.out.println(lst.get(0));
		
		
	}
}
