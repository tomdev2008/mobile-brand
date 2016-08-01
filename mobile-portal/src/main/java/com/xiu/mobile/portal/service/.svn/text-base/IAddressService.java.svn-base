/**  
 * @Project: xiu
 * @Title: ILoginRegService.java
 * @Package org.lazicats.xiu.login.service
 * @Description: TODO
 * @version V1.0  
 */
package com.xiu.mobile.portal.service;

import java.util.Map;

import com.xiu.mobile.portal.common.model.BaseBackDataVo;
import com.xiu.mobile.portal.model.AddressAddOutParam;
import com.xiu.mobile.portal.model.AddressListQueryInParam;
import com.xiu.mobile.portal.model.AddressOutParam;
import com.xiu.mobile.portal.model.AddressUpdateOutParam;
import com.xiu.mobile.portal.model.AddressVo;
import com.xiu.mobile.portal.model.ProvinceRegionCityListOutParam;
import com.xiu.mobile.portal.model.QueryAddressParamInParam;
import com.xiu.mobile.portal.model.QueryUserAddressDetailInParam;
import com.xiu.mobile.portal.model.QueryUserAddressListOutParam;
import com.xiu.mobile.portal.model.UserAddressOperationInParam;

/**
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-14 上午9:33:20 
 * ***************************************************************
 * </p>
 */
public interface IAddressService {

	/**
	 * 新增地址
	 * @Title: addAddress 
	 * @Description: TODO
	 * @param addressInParam
	 * @param tokenId
	 * @return String 返回地址Id
	 * @throws Exception  
	 * @author: yong
	 * @date: 2013-5-6上午11:32:57
	 */
	AddressAddOutParam addAddress(UserAddressOperationInParam addressInParam) throws Exception;

	/**
	 * 修改地址
	 * @Title: updateAddress 
	 * @Description: TODO
	 * @param addressInParam
	 * @param tokenId
	 * @return
	 * @throws Exception  
	 * @author: yong
	 * @date: 2013-5-6上午11:33:08
	 */
	BaseBackDataVo updateAddress(UserAddressOperationInParam addressInParam) throws Exception;

	/**
	 * 得到要更新地址数据
	 * @Title: getUpdateAddress 
	 * @Description: TODO
	 * @param queryAddressParam
	 * @return
	 * @throws Exception  
	 * @author: yong
	 * @date: 2013-5-6上午11:33:18
	 */
	AddressUpdateOutParam getUpdateAddress(QueryAddressParamInParam queryAddressParam) throws Exception;

	/**
	 * 得到默认地址数据
	 * @Title: getMasterAddress 
	 * @Description: 如果没有默认收货地址，则按顺序返回第一条数据
	 * @param addressListQuery
	 * @return AddressVo
	 * @throws Exception  
	 * @author: zhenjiang.wang
	 * @date: 2014-03-17
	 */
	AddressVo getMasterAddress(AddressListQueryInParam addressListQuery) throws Exception;

	/**
	 * 删除地址数据
	 * @Title: delAddress 
	 * @Description: TODO
	 * @param addressOperation
	 * @return  
	 * @author: yong
	 * @date: 2013-5-6上午11:33:45
	 */
	BaseBackDataVo delAddress(UserAddressOperationInParam addressOperation);

	/**
	 * 显示某个地址信息
	 * @Title: getAddress 
	 * @Description: TODO
	 * @param addressDetail
	 * @return
	 * @throws Exception  
	 * @author: yong
	 * @date: 2013-5-6上午11:34:02
	 */
	AddressOutParam getAddress(QueryUserAddressDetailInParam addressDetail) throws Exception;

	/**
	 * 列表显示地址
	 * @Title: listAddress 
	 * @Description: TODO
	 * @param addressListQuery
	 * @return
	 * @throws Exception  
	 * @author: yong
	 * @date: 2013-5-6上午11:34:29
	 */
	QueryUserAddressListOutParam listAddress(AddressListQueryInParam addressListQuery) throws Exception;

	/**
	 * 设为默认地址
	 * @Title: setMaster 
	 * @Description: TODO
	 * @param addressOperation
	 * @return
	 * @throws Exception  
	 * @author: yong
	 * @date: 2013-5-6上午11:35:04
	 */
	BaseBackDataVo setMasterAddress(UserAddressOperationInParam addressOperation) throws Exception;

	/**
	 * 得到省市区数据(通过上级查下级数据)
	 * @Title: getAddressParam 
	 * @Description: TODO
	 * @param queryAddressParam
	 * @return
	 * @throws Exception  
	 * @author: yong
	 * @date: 2013-5-6上午11:35:19
	 */
	ProvinceRegionCityListOutParam getAddressParam(QueryAddressParamInParam queryAddressParam) throws Exception;
	
	/**
	 * 添加收货地址新方法（调用新的UUC用户身份证信息接口）
	 * @param addressInParam
	 * @return
	 * @throws Exception
	 * @time	2015-2-2
	 */
	AddressAddOutParam addAddressNew(UserAddressOperationInParam addressInParam) throws Exception;

	/**
	 * 获取更新收货地址信息（调用新的UUC用户身份证信息接口）
	 * @param queryAddressParam
	 * @return
	 * @throws Exception
	 * @time	2015-2-2
	 */
	AddressUpdateOutParam getUpdateAddressNew(QueryAddressParamInParam queryAddressParam) throws Exception;
	
	/**
	 * 列表显示地址（调用新的UUC用户身份证信息接口）
	 * @param addressListQuery
	 * @return
	 * @throws Exception
	 * @time	2015-2-2
	 */
	QueryUserAddressListOutParam listAddressNew(AddressListQueryInParam addressListQuery) throws Exception;
	
	/**
	 * 查询所有的地区参数信息：省、市、区、邮编
	 * @return
	 * @throws Exception
	 */
	ProvinceRegionCityListOutParam getAllRegionParamInfo() throws Exception;
	
	/**
	 * 查询所有的地区参数信息：省、市、区、邮编
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getRegionParamInfoByNames(String names) throws Exception;
}