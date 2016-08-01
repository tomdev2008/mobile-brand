package com.xiu.mobile.wechat.web.dao;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.xiu.mobile.wechat.web.model.BindingCfgVo;

/**
* 类描述 
*
* @author wangzhenjiang
*
* @since  2014年6月23日
*/
@ContextConfiguration(locations = {"classpath:applicationContext-web.xml"})
public class BindingCfgDaoTest extends AbstractJUnit4SpringContextTests {

	@Resource(name = "bindingCfgDao")
	BindingCfgDao bindingCfgDao;

	@Test
	public void testSave() {
		BindingCfgVo  vo  = new BindingCfgVo();
		vo.setOpenId("test");
		vo.setXiuMode("1");
		int i = bindingCfgDao.saveBindingCfg(vo);
		Assert.assertEquals(i, 1);
	}

	@Test
	public void testDelete() {
		int i = bindingCfgDao.deleteBindingCfgByOpenId("XXXX");
		Assert.assertEquals(i, 0);
	}

}
