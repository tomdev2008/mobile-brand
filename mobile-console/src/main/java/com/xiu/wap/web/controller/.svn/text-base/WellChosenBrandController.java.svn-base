package com.xiu.wap.web.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.xiu.common.web.annotation.AuthRequired;
import com.xiu.common.web.contants.Constants;
import com.xiu.common.web.utils.JsonPackageWrapper;
import com.xiu.common.web.utils.UploadPicUtils;
import com.xiu.manager.web.model.User;
import com.xiu.manager.web.utils.AdminSessionUtil;
import com.xiu.mobile.core.model.DataBrandBo;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.model.WellChosenBrandBo;
import com.xiu.mobile.core.model.WellChosenBrandVo;
import com.xiu.mobile.core.service.IWellChosenBrandService;


/**
 * @DESCRIPTION :
 * @AUTHOR :wangchengqun
 * @VERSION :v1.0
 * @DATE :2014-06-09
 */
@AuthRequired
@Controller
@RequestMapping(value = "/wellChosenBrand")
public class WellChosenBrandController {
	// 日志
	private final Logger logger = Logger.getLogger(WellChosenBrandController.class);
	
	@Autowired
	private IWellChosenBrandService wellChosenBrandService;
	@Value("${upload_pic_path}")
	private String upload_pic_path;
	//查询
	@RequestMapping(value="list", method = RequestMethod.GET)
	public String getWellChosenBrandList(String brandCode,String brandName,String startDate,String endDate,
			@RequestParam(value="status",required=false,defaultValue="-1")int status,Page<?> page, Model model){
		List<WellChosenBrandVo> wellChosenBrandList=new ArrayList<WellChosenBrandVo>();
		Map<Object,Object> map=new HashMap<Object, Object>(); 
    	page.setPageNo(page.getPageNo() == 0 ? 1 : page.getPageNo());
    	  try {
              // 加载符合条件的所有启用的菜单
              map.put("brandCode", brandCode);
              map.put("brandName", brandName);
              map.put("startDate", startDate);
              map.put("endDate", endDate);
              map.put("status", status);
              wellChosenBrandList = wellChosenBrandService.getWellChosenBrandList(map, page);
          } catch (Exception e) {
              e.printStackTrace();
              logger.error("查询精选品牌异常！", e);
          }
    	  model.addAttribute("brandCode", brandCode);
    	  model.addAttribute("brandName", brandName);
    	  model.addAttribute("startDate", startDate);
    	  model.addAttribute("endDate", endDate);
    	  model.addAttribute("status", status);
          model.addAttribute("wellChosenBrandList", wellChosenBrandList);
          return "pages/wellChosenBrand/list";
    	
		
	}
	@RequestMapping(value="create", method = RequestMethod.GET)
	public String create(){
         return "pages/wellChosenBrand/create";
	}
	//添加页面模糊查询品牌名称
	@RequestMapping(value="toAdd", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String getBrandList(String brandName ,Model model){
		JsonPackageWrapper json = new JsonPackageWrapper();
		List<DataBrandBo> brandList =new ArrayList<DataBrandBo>();
		 try {
             
			 brandList = wellChosenBrandService.getBrandList(brandName);
			 if(null!=brandList&&brandList.size()>0){
				 json.setData(brandList);
				 json.setSmsg("查询品牌名称成功！");
			 }else{
				 json.setData(null);
				 json.setSmsg("查询品牌名称没有结果！");
			 }
			
         } catch (Exception e) {
             e.printStackTrace();
			 json.setData("查询品牌名称异常！");
             logger.error("查询品牌异常！", e);
         }
		 model.addAttribute("brandName",brandName);
		 model.addAttribute(Constants.JSON_MODEL__DATA, json);
         return "";
	}
	 private String upload(MultipartHttpServletRequest request)throws Exception{
		//图片检测
			MultipartFile patch = request.getFile("bannerPicFile");
			String fileName = patch.getOriginalFilename();
			if(null!=fileName && !"".equals(fileName)){
				fileName = UUID.randomUUID().toString()
						+ fileName.substring(fileName.lastIndexOf("."));
				String picPath = "/m/brand"
						+ new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + fileName;
				//上传
				String picFile = upload_pic_path.trim() + picPath;
				UploadPicUtils.isUploadPathExsit(picFile);
				patch.transferTo(new File(picFile));// 上传图片
				return picPath;
			}else{
				return "";
			}
	 }
	//添加
	@RequestMapping(value="add", method = RequestMethod.POST)
	public String addWellChosenBrand(MultipartHttpServletRequest request,HttpServletResponse response,WellChosenBrandBo wellChosenBrand ,Model model){
	        try {
	        	User user=AdminSessionUtil.getUser(request);
	        	wellChosenBrand.setCreateBy(user.getId());
	        	wellChosenBrand.setCreateDate(new Date());
	        	wellChosenBrand.setDeleted("N");
	        	wellChosenBrand.setReplaced("N");
	        	wellChosenBrand.setBannerPic(upload(request));
				 
	            int result = wellChosenBrandService.addWellChosenBrand(wellChosenBrand);
	            if (result == 0) {
	            	response.getWriter().print("<script>alert('success!') ; window.opener.location.reload(); self.close();</script> ");
	            } else if (result == 1) {
	            	response.getWriter().print("<script> alert('The brand already exists!') ;window.opener.location.reload(); self.close(); </script> ");
	            } else {
	            	response.getWriter().print("<script> alert('failed!') ;window.opener.location.reload(); self.close(); </script> ");
	            }
	        } catch (Exception e) {
	        	  logger.error("用户添加精选品牌失败！：", e);
	        	try {
					response.getWriter().print("<script> alert('failed!') ;window.opener.location.reload(); self.close();</script> ");
				} catch (IOException e1) {
					logger.error("打印异常！", e1);
				}
	          
	        }
	        return null;
	}
	//修改
	@RequestMapping(value="toUpdate", method = RequestMethod.GET)
	public String toUpdateWellChosenBrand(Long id, Model model){
		WellChosenBrandVo wellChosenBrandVo=new WellChosenBrandVo();
		 try {
			 wellChosenBrandVo = wellChosenBrandService.getWellChosenBrandVoList(id);
	        } catch (Exception e) {
	            e.printStackTrace();
	            logger.error("加载修改精选商品信息异常！", e);
	        }
	        model.addAttribute("wellChosenBrandVo", wellChosenBrandVo);
		return "pages/wellChosenBrand/edit";
	}
	@RequestMapping(value="update", method = RequestMethod.POST)
	public String updateWellChosenBrand(MultipartHttpServletRequest request,HttpServletResponse response,Long id,Long orderSequence, Model model){
	        try {
	        	String bannerPic=upload(request);
	            int result = wellChosenBrandService.updateWellChosenBrandByBrandId(id, orderSequence,bannerPic);
	            if (result == 0) {
	            	response.getWriter().print("<script> alert('success!') ;window.opener.location.reload(); self.close(); </script> ");
	            }  else {
	            	response.getWriter().print("<script> alert('failed!') ;window.opener.location.reload(); self.close(); </script> ");
	            }
	        } catch (Exception e) {
	            logger.error("用户修改精选品牌失败！", e);
	            try {
					response.getWriter().print("<script> alert('failed!') ;window.opener.location.reload(); self.close();</script> ");
				} catch (IOException e1) {
					logger.error("打印异常！", e1);
				}
	        }
	        return null;
	}
	//删除
	@RequestMapping(value="delete", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String deleteWellChosenBrand(Long id, Model model){
		 JsonPackageWrapper json = new JsonPackageWrapper();
	        try {
	            int result = wellChosenBrandService.deleteWellChosenBrandByBrandId(id);// 保存数据
	            if (result == 0) {
	                json.setScode(JsonPackageWrapper.S_OK);
	                json.setData("精选品牌删除成功！");
	            }  else {
	                json.setScode(JsonPackageWrapper.S_ERR);
	                json.setData("精选品牌删除失败！");
	            }
	        } catch (Exception e) {
	            json.setScode(JsonPackageWrapper.S_ERR);
	            json.setData("系统发生异常！");
	            logger.error("用户删除精选品牌失败！", e);
	        }
	        model.addAttribute(Constants.JSON_MODEL__DATA, json);
	        return "";
	}
	//批量删除
		@RequestMapping(value="deleteList", method = RequestMethod.GET, produces = "application/json", params = "format=json")
		public String deleteWellChosenBrands(String ids, Model model){
			 JsonPackageWrapper json = new JsonPackageWrapper();
		        try {
		        	String[] id=ids.split(",");
		        	List<Long> idList=new ArrayList<Long>();
		        	for(int i=0;i<id.length;i++){
		        		idList.add(Long.parseLong(id[i]));
		        	}
		            int result = wellChosenBrandService.deleteWellChosenBrandByIds(idList);// 保存数据
		            if (result == 0) {
		                json.setScode(JsonPackageWrapper.S_OK);
		                json.setData("精选品牌删除成功！");
		            }  else {
		                json.setScode(JsonPackageWrapper.S_ERR);
		                json.setData("精选品牌删除失败！");
		            }
		        } catch (Exception e) {
		            json.setScode(JsonPackageWrapper.S_ERR);
		            json.setData("系统发生异常！");
		            logger.error("用户删除精选品牌失败！", e);
		        }
		        model.addAttribute(Constants.JSON_MODEL__DATA, json);
		        return "";
		}
}
