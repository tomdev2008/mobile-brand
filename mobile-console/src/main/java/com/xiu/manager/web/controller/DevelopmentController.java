package com.xiu.manager.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiu.common.web.annotation.AuthRequired;
import com.xiu.manager.web.biz.DevelopmentService;

/**
 * @CLASS :com.xiu.recommend.web.controller.DevelopmentController
 * @DESCRIPTION :开发者选项
 * @AUTHOR :haijun.chen@xiu.com
 * @VERSION :v1.0
 * @DATE :2013-08-26
 */
@AuthRequired
@Controller
@RequestMapping(value = "/development")
public class DevelopmentController {

	// 日志
	private static final XLogger LOGGER = XLoggerFactory
			.getXLogger(DevelopmentController.class);

	@Autowired
	private DevelopmentService developmentService;

	/**
	 * 开发者选项
	 * 
	 * @param sql
	 *            自助SQL语句
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(String sql, Model model) {
		String error = "";// 信息
		sql = sql == null ? "" : sql.trim();
		List<Map<Object, Object>> listmap = new ArrayList<Map<Object, Object>>();
		try {
			if (!"".equals(sql)) {
				if ("select".equals(sql.substring(0, 6).toLowerCase())) {
					// 开发者选项 ----执行 select 语句
					listmap = developmentService.querySelectSQL(sql);
				} else {
					// 开发者选项 ----执行 insert、update 、delete 语句
					if (developmentService.queryInsertOrUpdateOrDeleteSQL(sql)) {
						error = "提示:操作成功！";
					} else {
						error = "提示:请检查您的SQL语句是否规范正确！";
					}
				}
			}
		} catch (Exception e) {
			error = "提示:操作异常！请检查您的SQL语句是否规范正确！";
			e.printStackTrace();
			LOGGER.error("自助输入SQL操作异常！========SQL：" + sql, e);
		}
		model.addAttribute("sql", sql);
		model.addAttribute("error", error);
		model.addAttribute("listmap", listmap);
		return "pages/development/list";
	}
}
