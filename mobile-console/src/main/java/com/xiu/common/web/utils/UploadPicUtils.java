package com.xiu.common.web.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.xiu.common.command.result.Result;
import com.xiu.common.command.result.ResultSupport;
import com.xiu.wap.web.controller.WapH5PageController;

/**
 *************************************************************** 
 * <p>
 * 
 * @DESCRIPTION :
 * @AUTHOR : wangmingjin
 * @DATE :2012-4-14 下午2:16:25
 *       </p>
 **************************************************************** 
 */
public class UploadPicUtils {

    // private static final XLogger LOGGER = XLoggerFactory.getXLogger(EIAddressManagerImpl.class);
	//日志
    private static final Logger logger = Logger.getLogger(WapH5PageController.class);

    /**
     * 判断下上传地址是否存在
     */
    public static void isUploadPathExsit(String fileName) {

        File picFile = new File(fileName);

        String picParentPath = picFile.getParent();

        File picParentFile = new File(picParentPath);

        if (!picParentFile.exists()) {
            picParentFile.mkdirs();
        }
    }

    /**
     * 用户删除凭证
     */
    public static void deleteFile(String fileUrl, String uploadPath) {
        String url = fileUrl;
        if (uploadPath.endsWith("/")) {
            url = uploadPath + url;

        } else {
            url = uploadPath + "/" + url;
        }
        File deleteFile = new File(url);
        if (deleteFile.exists()) {
            deleteFile.delete();
        }
    }

    /**
     * 用户删除凭证
     */
    public static String reSplice(final String fileUrl, final String proofs) {

        String proof = proofs;

        if (proof.endsWith(fileUrl)) {

            proof = proofs.replaceAll("(?:" + fileUrl + ")", "");
        } else {

            proof = proofs.replaceAll("(?:" + fileUrl + ";)", "");

        }

        return proof;
    }

    /**
     * @param upLoadFile
     * @throws Exception
     */
    public static String reFileName(String fileName, String remoteWritePath, Long userId) {
        StringBuilder sb = new StringBuilder();
        sb.append(DateUtil.formatDate(new Date(), "yyyyMMddHHmmss")).append(
                fileName.substring(fileName.lastIndexOf("."), fileName.length()));
        String wpath = remoteWritePath + "/"+userId+"/"+sb.toString();
        isUploadPathExsit(wpath);
        return sb.toString();

    }

    public static String array2String(String[] arry, String proof) {
        String proofs = proof;
        if (arry != null) {
            for (String str : arry) {
                proofs = proofs + ";" + str;
            }
            if(proofs.startsWith(";")){
                proofs = proofs.substring(1, proofs.length());
            }
            if(proofs.endsWith(";")){
                proofs = proofs.substring(0, proofs.length()-1);
            }
        }
   
        
        
        return proofs;
    }

    public static String array2StringProof(String proof, Long userId) {
        String arr[] = null;
        StringBuilder sb = new StringBuilder();
        arr = proof.split(";");
        int count = 0;
        for (String str : arr) {
            ++count;
            if (str.length() == 1) {
                sb.append(userId).append("/").append(str);
            } else if (count < arr.length) {
                sb.append(userId).append("/").append(str).append(";");
            } else {
                sb.append(userId).append("/").append(str);
            }
        }
        return sb.toString();
    }

    public static String[] string2Arr(String str) {

        String arr[] = null;
        if (StringUtil.isNotBlank(str)) {

            arr = str.split(";");
        }

        return arr;

    }
    
    /**
     * 上传指定大小图片到指定目录
     * @param patch	
     * @param picSize	文件限制大小
     * @param uploadUrl 文件上传到服务器的路径
     * @param picUrl	 文件上传后路径
     * @return
     */
    public static Result uploadPic(MultipartFile patch, Integer limitPicSize,String uploadUrl, String picUrl){
    	Result result = new ResultSupport();
    	Map<String, String> map = new HashMap<String, String>();
    	try {
    		if(patch != null){
    			if(limitPicSize != null){
					byte[] fileByts = patch.getBytes();
					
					if(fileByts.length > limitPicSize){
						result.setSuccess(false);
						result.setError("上传图片超过1M大小限制");
						return result;
				    }
    			}
				
				String fileName = patch.getOriginalFilename();
				if(StringUtils.isNotBlank(fileName)){
					fileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
					String picPatch = picUrl + fileName;
					String picFile = uploadUrl + picPatch;
					UploadPicUtils.isUploadPathExsit(picFile);
					try {
						patch.transferTo(new File(picFile));//上传图片
					} catch (IOException e) {
						logger.error("上传图片异常", e);
						result.setSuccess(false);
						result.setError("上传图片异常");
						return result;
					}
					result.setSuccess(true);
					map.put("picPatch", picPatch);
					map.put("fileName", fileName);
					result.setDefaultModel(map);
				}
    		}
		} catch (IOException e1) {
			logger.error("获取图片大小异常", e1);
		}
    	return result;
    }
    
    /**
     * 复制文件
     * @param filePath   源文件路径
     * @param savePath   复制到的路径
     * @return
     */
    public static Result copyFile(String filePath, String savePath){
    	Result result = new ResultSupport();

		UploadPicUtils.isUploadPathExsit(savePath);
    	File file = new File(filePath);
    	File saveFile = new File(savePath);
    	
    	FileInputStream fileIn = null;
    	FileOutputStream fileOut = null;
    	FileChannel inC = null;
    	FileChannel outC = null;
    	ByteBuffer bb = null;
    	
    	if(file.exists()){
    		try {
				fileIn = new FileInputStream(file);
	    		fileOut = new FileOutputStream(saveFile);
	    		inC = fileIn.getChannel();
	    		outC = fileOut.getChannel();
	    		bb = ByteBuffer.allocate(1024*1024*10);
	    		while(inC.read(bb) != -1){
	    			bb.flip();
	    			outC.write(bb);
	    			bb.clear();
	    		}
			} catch (FileNotFoundException e) {
				logger.error("复制文件异常",e);
			} catch (IOException e) {
				logger.error("复制文件异常",e);
			} finally {
			      try {
			          if(fileIn != null) {
			        	  inC.close();
			        	  fileIn.close();
			          }
			          if(fileOut != null) {
			        	  outC.close();
			        	  fileOut.close();
			          }
			        } catch(IOException e) {
			        	logger.error("复制文件异常",e);
			        }
			      }
    	}
    	
    	return result;
    }
    

}
