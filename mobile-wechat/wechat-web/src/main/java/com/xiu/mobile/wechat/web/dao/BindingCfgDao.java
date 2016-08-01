package com.xiu.mobile.wechat.web.dao;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.wechat.web.model.BindingCfgVo;

/**
 * 账户绑定配置DAO接口
 * 
 * @author wangzhenjiang
 * 
 */
public interface BindingCfgDao {
	/**
	 * 保存配置
	 * 
	 * @param BindingCfgVo
	 * @return count 保存成功记录数
	 */
	int saveBindingCfg(BindingCfgVo cfg);

	/**
	 * 根据ID删除配置
	 * 
	 * @param id
	 * @return count 删除记录数
	 */
	int deleteBindingCfgById(Long id);

	/**
	 * 根据ID删除配置
	 * 
	 * @param openId
	 * @return count 删除记录数
	 */
	int deleteBindingCfgByOpenId(String openId);

	/**
	 * 根据ID删除配置
	 * 
	 * @param unionId
	 * @return count 删除记录数
	 */
	int deleteBindingCfgByUnionId(String unionId);
	
	
	/**
	 * 更新配置
	 * 
	 * @param cfg
	 * @return count 更新记录数
	 */
	int updateBindingCfg(BindingCfgVo cfg);

	/**
	 * 根据ID 查询一条配置记录
	 * 
	 * @param id
	 * @return BindingCfg
	 */
	BindingCfgVo getBindingCfgById(Long id);

	/**
	 * 根据微信openId 查询一条配置记录
	 * 
	 * @param openId
	 * @return BindingCfg
	 */
	BindingCfgVo getBindingCfgByOpenId(String openId);
	
	
	/**
	 * 根据微信unionId 查询一条配置记录
	 * 
	 * @param unionId
	 * @return BindingCfg
	 */
	BindingCfgVo getBindingCfgByUnionId(String unionId);
	

	/**
	 * 根据参数查询配置集合
	 * 
	 * @param params
	 *            Map<String, Object>
	 * @return List<BindingCfg>
	 */
	List<BindingCfgVo> getBindingCfgList(Map<String, Object> params);

	/**
	 * 根据参数查询配置 分页列表
	 * 
	 * @param params
	 *            Map<String, Object>
	 * @return List<BindingCfg>
	 */
	List<BindingCfgVo> getBindingCfgListForPage(Map<String, Object> params);

	/**
	 * 查询重复数据
	 * 
	 * @return List<BindingCfgVo> 
	 */
	List<BindingCfgVo> queryRepeatingData(int count);
}
