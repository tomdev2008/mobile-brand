package com.xiu.wap.web.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.xiu.common.web.annotation.AuthRequired;
import com.xiu.common.web.utils.ExcelUtil;
import com.xiu.common.web.utils.StringUtil;
import com.xiu.manager.web.model.User;
import com.xiu.manager.web.utils.AdminSessionUtil;
import com.xiu.mobile.core.service.IFindGoodsService;

/**
 * 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 单品发现导入商品
 * @AUTHOR : chenghong.ding@xiu.com 
 * @DATE :2014年6月9日 下午2:17:43
 * </p>
 ****************************************************************
 */
@AuthRequired
@Controller
@RequestMapping(value = "/findgoods")
public class FindGoodsImportController {
	//日志
    private static final XLogger logger = XLoggerFactory.getXLogger(FindGoodsImportController.class);
    @Autowired
    private IFindGoodsService findGoodsService;
    
    @RequestMapping(value = "toimport", method = RequestMethod.GET)
    public String toimport(){
    	return "pages/findgoods/import";
    }
    
    @RequestMapping(value = "down", method = RequestMethod.GET)
    public String down(HttpServletRequest request,HttpServletResponse response,Model model){
    	String path = request.getSession().getServletContext().getRealPath("/");
    	path += "template/find-goods-template.xls";
    	path = path.replace("\\", "/");
    	logger.debug("模板文件路径：{}", new Object[]{path});
    	try {
			// path是指欲下载的文件的路径。
			File file = new File(path);
			// 取得文件名。
			String filename = file.getName();
			// 取得文件的后缀名。

			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.setHeader("Content-Type", "application/octet-stream");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes(), "utf-8"));
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = response.getOutputStream();
			
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			logger.error("下载单品导入模板出现异常",ex);
			return null;
		}
		return null;
    }
    
    @RequestMapping(value = "import_excel", method = RequestMethod.POST)
    public String import_excel(MultipartHttpServletRequest request,Model model){
    	try{
    		// 获取Excel文件流
			MultipartFile patch = request.getFile("dataFile");
			
			// 原始数据
			List<Map<Object, Object>> listMap = ExcelUtil.importStatements(patch.getInputStream());
			
			// 数据验证
			boolean flag = checkExcelData(listMap, model);
			if(!flag){
				// 返回页面，提示验证结果
				return "pages/findgoods/import";
			}
			
			// 去掉Excel表格中重复的goodsSn
			Map<String, Map<Object,Object>> data = removeRepetition(listMap);
			
			// 调用service接口导入
			User user = AdminSessionUtil.getUser(request);
			int count = findGoodsService.importGoods(data, user.getId());
			logger.debug("{}.import_excel 导入商品完成,共导入{}条",
					new Object[]{FindGoodsImportController.class,count});
    	}catch(Exception e){
    		logger.error("导入单品发现商品异常", e);
    	}
    	return "redirect:/findgoods/list";
    }
    
    /**
     * 去掉重复的goodsSn
     * @return
     */
    private Map<String,Map<Object,Object>> removeRepetition(List<Map<Object,Object>> data){
    	Map<String,Map<Object,Object>> result = new HashMap<String, Map<Object,Object>>();
    	if(null != data && !data.isEmpty()){
    		
    		// 转化---把goodsSn当key,map当value放入新的map中
    		Map<String,Map<Object,Object>> allData = new HashMap<String, Map<Object,Object>>();
    		for(Map<Object,Object> map : data){
    			String goodsSn = (String)map.get(0);  // excel中第一列为goodsSn
    			allData.put(goodsSn, map);
    		}
    		
    		// 用set去除重复的goodsSn
    		Set<Map<Object,Object>> dataPool = new HashSet<Map<Object,Object>>();
    		for(String key : allData.keySet()){
    			dataPool.add(allData.get(key));
    		}
    		
    		// 把set中的结果放入result中，然后返回
    		Iterator<Map<Object,Object>> it = dataPool.iterator();
    		while (it.hasNext()){
    			Map<Object,Object> m = it.next();
    			result.put((String)m.get(0),m);
    		}
    	}
    	return result;
    }
    
    /**
     * Excel数据校验
     * @param listMap
     * @param model
     * @return
     */
	private boolean checkExcelData(List<Map<Object, Object>> listMap, Model model) {
		boolean flag = true;
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date startDate = null;
		Date endDate = null;
		if (listMap != null && !listMap.isEmpty()) {
			List<String> snList = new ArrayList<String>();
			for (int i = 0; i < listMap.size(); i++) {
				Map<Object, Object> map = listMap.get(i);
				// 商品货号
				String goodsSn = (String) map.get(0);
				// 发现商品标题
				String title = (String) map.get(1);
				// 编辑语
				String content = (String) map.get(2);
				// 排序
				String sort = (String) map.get(3);
				// 开始时间
				String sDate = (String) map.get(4);
				// 结束时间
				String eDate = (String) map.get(5);
				//标签
				String label=(String) map.get(6);

				if (StringUtil.isEmpty(goodsSn)) {
					flag = false;
					model.addAttribute("error", "第" + (i + 1) + "行走秀码不能为空。");
					break;
				} else {
					if (goodsSn.length() > 10) {
						flag = false;
						model.addAttribute("error", "第" + (i + 1) + "行走秀码长度超过10个字符，请重填");
						break;
					}
				}

				if (StringUtil.isEmpty(title)) {
					flag = false;
					model.addAttribute("error", "第" + (i + 1) + "行商品发现名称不能为空。");
					break;
				} else {
					if (title.length() > 100) {
						map.put(1, title.substring(0, 100));
					}
				}

				if (StringUtil.isEmpty(content)) {
					flag = false;
					model.addAttribute("error", "第" + (i + 1) + "行编辑语不能为空。");
					break;
				} else {
//					if (content.length() < 4) {
//						flag = false;
//						model.addAttribute("error", "第" + (i + 1) + "行编辑语长度不能少于4个字，请重填");
//						break;
//					}

					if (content.length() > 200) {
						map.put(2, content.substring(0, 200));
					}
				}

				if (!StringUtil.isEmpty(sort)) {
					try {
						int order = Integer.parseInt(sort);
						if (order <= 0) {
							flag = false;
							model.addAttribute("error", "第" + (i + 1) + "行排序只能为正整数，请重填");
							break;
						}
					} catch (NumberFormatException e) {
						flag = false;
						logger.error("排序转化为数字时异常！", e);
						model.addAttribute("error", "第" + (i + 1) + "行排序只能为正整数，请重填");
						break;
					}
				} else {
					map.put(3, "0");
				}

				if (!StringUtil.isEmpty(sDate)) {
					try {
						startDate = sf.parse(sDate);
					} catch (ParseException e) {
						flag = false;
						logger.error("展示开始时间转换为日期时异常！", e);
						model.addAttribute("error", "第" + (i + 1) + "行展示开始时间格式不正确，请重填");
						break;
					}
				} else {
					flag = false;
					model.addAttribute("error", "第" + (i + 1) + "行展示开始时间不能为空，请重填");
					break;
				}

				if (!StringUtil.isEmpty(eDate)) {
					try {
						endDate = sf.parse(eDate);
					} catch (ParseException e) {
						flag = false;
						logger.error("展示结束时间转换为日期时异常！", e);
						model.addAttribute("error", "第" + (i + 1) + "行展示结束时间格式不正确，请重填");
						break;
					}
				} else {
					flag = false;
					model.addAttribute("error", "第" + (i + 1) + "行展示结束时间不能为空，请重填");
					break;
				}

				if (!sDate.equals(eDate)) {
					if (startDate.after(endDate)) {
						flag = false;
						model.addAttribute("error", "第" + (i + 1) + "行展示开始时间不能迟于展开结束时间，请重填");
						break;
					}
				}

				// goodsSn加入snList中
				snList.add(goodsSn);
			}

			if (flag) {
				if (!snList.isEmpty()) {
					// 筛选不存在的goodsSn
					List<String> goodsSnList = findGoodsService.checkGoodsSn(snList);
					
					if (goodsSnList != null && !goodsSnList.isEmpty()) {
						snList.removeAll(goodsSnList);
					}

					if (!snList.isEmpty()) {
						flag = false;
						model.addAttribute("error", "以下走秀码不存在:" + snList.toString());
					} else {
						String existGoodsSn = "";
						for(String goodsSn : goodsSnList) {
							if(findGoodsService.isFindGoodsExist(goodsSn)) {
								existGoodsSn = existGoodsSn + " 走秀码：" + goodsSn;
							}
						}
						
						if(!"".equals(existGoodsSn)) {
							flag = false;
							model.addAttribute("error", "以下商品已导入，" + existGoodsSn);
						}
					}
				}
			}
		} else {
			flag = false;
			model.addAttribute("error", "模板数据为空，请填写数据后重试！");
		}
		return flag;
	}
    
}
