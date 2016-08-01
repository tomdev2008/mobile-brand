package com.xiu.mobile.portal.service;

import com.xiu.mobile.portal.model.VomitSayVo;

/**
 * 吐槽接口
 * @author wenxiaozhuo
 * @date 2014-4-15
 */
public interface IVomitSayService {
	/**
	 * 保存吐槽内容
	 * @param vomitSayVo 吐槽对象
	 * @return int 保存成功与否
	 */
	public int saveVomitsay(VomitSayVo vomitSayVo) throws Exception;
}
