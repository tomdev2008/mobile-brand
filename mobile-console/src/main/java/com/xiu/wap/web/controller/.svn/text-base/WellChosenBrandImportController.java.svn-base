package com.xiu.wap.web.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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
import com.xiu.mobile.core.service.IWellChosenBrandService;

/**
 * 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 精选品牌导入品牌
 * @AUTHOR : wangchengqun
 * @DATE :2014年6月11日
 * </p>
 ****************************************************************
 */
@AuthRequired
@Controller
@RequestMapping(value = "/wellChosenBrand")
public class WellChosenBrandImportController {
	//日志
	private final XLogger logger = XLoggerFactory.getXLogger(WellChosenBrandImportController.class);
	@Autowired
	private IWellChosenBrandService wellChosenBrandService;
    
    @RequestMapping(value = "toimport", method = RequestMethod.GET)
    public String toimport(){
    	return "pages/wellChosenBrand/import";
    }
    
    @RequestMapping(value = "down", method = RequestMethod.GET)
    public String down(HttpServletRequest request,HttpServletResponse response,Model model){
    	String path = request.getSession().getServletContext().getRealPath("/");
    	path += "template/well-chosen-brand-template.xls";
    	path = path.replace("\\", "/");
    	System.out.println("==========================path is:" + path);
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
    public String import_excel(MultipartHttpServletRequest request,HttpServletResponse response,Model model){
    	try{
    		// 获取Excel文件流
			MultipartFile patch = request.getFile("dataFile");
			
			// 原始数据
			List<Map<Object, Object>> listMap = ExcelUtil.importStatements(patch.getInputStream());
			
			// 数据验证
			Map<Object, Object> resultFlag = checkExcelData(listMap, model);
			String flag=resultFlag.get("flag").toString();
			if(("false").equals(flag)){
				// 返回页面，提示验证结果
				return "pages/wellChosenBrand/import";
			}else if(("true").equals(flag)){
				listMap=(List<Map<Object, Object>>)resultFlag.get("listMap");
				// 去掉重复BrandCode
				Map<String, Map<Object,Object>> data = removeRepetition(listMap);
				
				// 调用service接口导入
				User user = AdminSessionUtil.getUser(request);
				int count = wellChosenBrandService.importWellChosenBrand(data, user.getId());
				logger.info("{}.import_excel 导入精选品牌完成,共导入{}条",new Object[]{WellChosenBrandImportController.class,count});
				response.getWriter().print("<script>alert('success!') ;location.href = '/wellChosenBrand/list';</script> ");
			}
		
			}catch(Exception e){
    		logger.error("导入精选品牌异常", e);
    		try {
				response.getWriter().print("<script> alert('failed!') ;location.href = '/wellChosenBrand/list';</script> ");
			} catch (IOException e1) {
				logger.error("打印异常！", e1);
			}
    	}
    	return null;
    }
    /**
     * 去掉重复的精选品牌
     * @return
     */
    private Map<String,Map<Object,Object>> removeRepetition(List<Map<Object,Object>> data){
    	Map<String,Map<Object,Object>> result = new HashMap<String, Map<Object,Object>>();
    	if(null != data && !data.isEmpty()){
    		
    		// 转化---把brandCode当key,map当value放入新的map中
    		Map<String,Map<Object,Object>> allData = new HashMap<String, Map<Object,Object>>();
    		for(Map<Object,Object> map : data){
    			String brandCode = (String)map.get(0);  // excel中第一列为brandCode
    			allData.put(brandCode, map);
    		}
    		
    		// 用set去除重复的brandCode
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
    
    private Map<Object, Object> checkExcelData(List<Map<Object, Object>> listMap,Model model)throws Exception{
    	Map<Object, Object> result=new HashMap<Object, Object>();
    	String flag="true";
    	if(listMap != null && !listMap.isEmpty()){
    		List<String> bcList = new ArrayList<String>();
    		for(int i = 0; i < listMap.size(); i++){
    			Map<Object,Object> map = listMap.get(i);
    			// 品牌编码
    			String brandCode = (String)map.get(0);
				// 排序
				String orderSequence = (String)map.get(1);
				if(StringUtil.isEmpty(brandCode)){
					flag= "false";
					model.addAttribute("error", "第 " + (i + 1) + " 行品牌编号不能为空。");
					break;
				}else{
					if(brandCode.length() > 256){
						flag= "false";
						model.addAttribute("error", "第 " + (i + 1) + " 行品牌编号长度超过256个字符，请重填");
						break;
					}
				}
				
				if(!StringUtil.isEmpty(orderSequence)){
					try{
						int order =Integer.parseInt(orderSequence);
						if(order <= 0||order>10000){
							map.put(1, 100);
						}
					}catch(NumberFormatException e){
						map.put(1, 100);
						logger.error("排序转化为数字时异常！", e);
					}
				}
				bcList.add(brandCode);
				
    		}
    		System.out.println("结果是："+flag);
    		if("true".equals(flag)){
    			// 筛选不存在的brandcode
    			if(!bcList.isEmpty()){
    				List<String> brandCodeList = wellChosenBrandService.checkBrandCode(bcList);
    				
    				if(brandCodeList != null && !brandCodeList.isEmpty()){
    					System.out.println("筛选不存在的brandcode:"+brandCodeList.size());
    					bcList.removeAll(brandCodeList);
    				}
    				
    				if(!bcList.isEmpty()){
    					flag = "false";
    					model.addAttribute("error", "以下品牌编码不存在:" + bcList.toString());
    				}
    			}
    		}
    	}else{
    		flag = "false";
    		model.addAttribute("error", "模板数据为空，请填写数据后重试！");
    	}
    	result.put("flag", flag);
    	result.put("listMap", listMap);
    	return result;
    }
    
}
