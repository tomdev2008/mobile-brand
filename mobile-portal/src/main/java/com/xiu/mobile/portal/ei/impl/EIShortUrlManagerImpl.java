/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-22 下午3:34:50 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.mobile.portal.ei.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.xiu.mobile.core.utils.ObjectUtil;
import com.xiu.mobile.core.utils.XiuUtil;
import com.xiu.mobile.portal.common.util.HttpUtils;
import com.xiu.mobile.portal.ei.EIShortUrlManager;
import com.xiu.sales.common.itemsettle.dataobject.ItemSettleResultDO;

/** 
 * 
* @Description: TODO(生成短链接) 
* @author haidong.luo@xiu.com
* @date 2016年1月6日 下午2:20:39 
*
 */
@Service("eishortUrlManager")
public class EIShortUrlManagerImpl implements EIShortUrlManager {
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(EIShortUrlManagerImpl.class);
	
	private static final String apiUrl="http://dwz.cn/create.php";

	@Override
	public String getShortLinkByThirdParty(String url) {
        String shortURL = null;
        Map params=new HashMap();
        params.put("url", url);
        try {
			String str = HttpUtils.postMethod(apiUrl,params,"utf-8");
			JSONObject jsonObject=JSONObject.fromObject(str);
			Object tinyurl=jsonObject.get("tinyurl");
			if(tinyurl!=null&&tinyurl.toString().indexOf("dwz")>0){
				shortURL=tinyurl.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
 
        return shortURL;
	}

}
