package com.xiu.mobile.portal.service;

/**
 * 敏感词service
 * @author coco.long
 * @time	2015-06-24
 */
public interface ISensitiveWordService {

	/**
	 * 检测敏感词是否存在
	 * @param content
	 * @return
	 */
	public boolean isSensitiveExists(String content);
}
