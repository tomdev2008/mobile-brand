package com.xiu.mobile.simple.common.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Response工具类
 * 
 * @author wangzhenjiang
 *
 */
public class ResponseUtil {

	private static final Logger logger = Logger.getLogger(ResponseUtil.class);


	/**
	 * 返回json字符串，支持跨域调用
	 * @param response
	 * @param jsonStr
	 * @throws IOException
	 */
	public static void outPrintResult(HttpServletResponse response, String jsonStr) {
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			out = response.getWriter();
			out.write(jsonStr);
		} catch (Exception e) {
			logger.error("返回结果时发生异常：" + e.getMessage());
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}
}
