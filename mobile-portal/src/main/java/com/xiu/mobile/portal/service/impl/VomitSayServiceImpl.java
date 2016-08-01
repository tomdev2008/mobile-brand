package com.xiu.mobile.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.portal.dao.VomitSayDao;
import com.xiu.mobile.portal.model.VomitSayVo;
import com.xiu.mobile.portal.service.IVomitSayService;

/**
 * 吐槽接口实现
 * @author wenxiaozhuo
 * @date 2014-4-15
 */
@Service("vomitSayService")
public class VomitSayServiceImpl implements IVomitSayService {
	@Autowired
	private VomitSayDao vomitsayDao;

	@Override
	public int saveVomitsay(VomitSayVo vomitSayVo) throws Exception {
		return vomitsayDao.insertVomitsay(vomitSayVo);
	}
}
