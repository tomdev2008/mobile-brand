package com.xiu.mobile.wechat.web.service;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.wechat.core.constants.Page;
import com.xiu.mobile.wechat.web.model.BindingCfgVo;

/**
 * 
 * 用户绑定 服务接口
 * 
 * @author wangzhenjiang
 * 
 */
public interface IBindingCfgService {

	/**
	 * 根据参数查询配置分页列表
	 * 
	 * @param map
	 * @param page
	 * @return List<BindingCfg>
	 */
	List<BindingCfgVo> getBindingCfgList(Map<String, Object> params, Page<?> page);

	/**
	 * 根据参数查询配置列表
	 * 
	 * @param map
	 * @return List<BindingCfg>
	 */
	List<BindingCfgVo> getBindingCfgList(Map<String, Object> params);

	/**
	 * 保存配置
	 * 
	 * @param cfg
	 * @return 成功保存的记录数
	 */
	int saveBindingCfg(BindingCfgVo cfg);

	/**
	 * 根据ID删除配置
	 * 
	 * @param id
	 * @return 成功删除的记录数
	 */
	int deleteBindingCfgById(Long id);

	/**
	 * 根据OPEN ID删除配置
	 * 
	 * @param id
	 * @return 成功删除的记录数
	 */
	int deleteBindingCfgByOpenId(String openId);
	/**
	 * 根据Union ID删除配置
	 * 
	 * @param  unionId
	 * @return 成功删除的记录数
	 */
	int deleteBindingCfgByUnionId(String unionId) ;
	/**
	 * 更新配置
	 * 
	 * @param cfg
	 * @return 成功更新的记录数
	 */
	int updateBindingCfg(BindingCfgVo cfg);

	/**
	 * 根据ID查询配置
	 * 
	 * @param id
	 * @return BindingCfg
	 */
	BindingCfgVo getBindingCfg(Long id);

	/**
	 * 根据微信openId查询配置
	 * 
	 * @param String
	 * @return BindingCfg
	 */
	BindingCfgVo getBindingCfg(String openId);
	/**
	 * 根据微信unionId查询配置
	 * 
	 * @param String
	 * @return BindingCfg
	 */
	BindingCfgVo getBindingCfgByUnionId(String unionId);
	/**
	 * 根据ids 批量删除记录
	 * 
	 * @param ids
	 * @return 成功删除的记录数
	 */
	int deleteBindingCfgList(List<Long> ids);

	/**
	 * 查询重复数据
	 * 
	 * @param count
	 * @return List<BindingCfgVo>
	 */
	List<BindingCfgVo> queryRepeatingData(int count);

	/**
	 * 保存或者更新绑定记录
	 * 
	 * @param bindingCfgVo
	 * @return 成功的记录数
	 */
	int saveOrUpdateBindingCfg(BindingCfgVo bindingCfgVo);
}
