package com.xiu.mobile.brand.web.service;

import java.util.List;

import com.xiu.mobile.brand.web.bo.MutilProductBo;

public interface ImutilProductService {
	
    List<MutilProductBo> queryBySn(String sn);
}
