/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-28 下午3:30:13 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.mobile.portal.ei;

import java.util.List;

import com.xiu.image.biz.dto.GoodsInfoDTO;
import com.xiu.image.biz.hessian.interfaces.SkuImagesPair;

/** 
 * <p>
 * ************************************************************** 
 * @Description: 图片
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-28 下午3:30:13 
 * ***************************************************************
 * </p>
 */

public interface EImageManager {
	
	/**
	 * 
	 * @param goodsDTO
	 * @return
	 */
	List<SkuImagesPair> checkImageExists(GoodsInfoDTO goodsDTO);

}
