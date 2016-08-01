package com.xiu.mobile.brand.web.service;

import org.apache.solr.client.solrj.SolrServerException;

import com.xiu.mobile.brand.web.bo.ExclusiveProductBo;
import com.xiu.mobile.brand.web.controller.params.ExclusiveParam;

public interface IExclusiveService {

	ExclusiveProductBo getExclusiveProducts(ExclusiveParam handleRequestParam) throws SolrServerException, IllegalArgumentException;

}
