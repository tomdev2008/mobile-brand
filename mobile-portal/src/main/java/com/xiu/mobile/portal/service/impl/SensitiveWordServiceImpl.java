package com.xiu.mobile.portal.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiu.mobile.portal.common.filter.SensitiveWordFilter;
import com.xiu.mobile.portal.service.ISensitiveWordService;

/**
 * 敏感词Service
 * @author coco.long
 * @time	2015-06-24
 */
@Transactional
@Service("sensitiveWordService")
public class SensitiveWordServiceImpl implements ISensitiveWordService {

	/**
	 * 检测敏感词是否存在
	 */
	public boolean isSensitiveExists(String content) {
		boolean result = SensitiveWordFilter.existsCharachter(content);
		return result;
	}

}
