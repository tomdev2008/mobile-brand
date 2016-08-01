package com.xiu.mobile.portal.common.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class UploadUtil {

	// 日志
	private static final XLogger LOGGER = XLoggerFactory
			.getXLogger(UploadUtil.class);
	@Value("${upload_pic_path}")
	private String upload_pic_path;

	public String update(MultipartHttpServletRequest request, String path) {
		try {
			// 图片检测
			MultipartFile patch = request.getFile("mobile_pic_f");

			String fileName = patch.getOriginalFilename();
			if (null == fileName || "".equals(fileName)) {
				return null;
			}

			fileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
			//String picPath = "/m/topic"	+ new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + fileName;
			String picFile = path	+ new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + fileName;
			String fullPicFile = upload_pic_path.trim() + picFile;
			//如果文件不存在则创建之
			createFile(fullPicFile);
			patch.transferTo(new File(fullPicFile));// 上传图片
			return picFile;

		} catch (Exception e) {
			LOGGER.error("更新专题活动异常！", e);
			return null;
		}
	}

	/**
	 * 如果文件不存在就创建
	 * @param fullFileName
	 */
	public static void createFile(String fullFileName) {
		File picFile = new File(fullFileName);
		String picParentPath = picFile.getParent();
		File picParentFile = new File(picParentPath);
		if (!picParentFile.exists()) {
			picParentFile.mkdirs();
		}
	}

	
	/**
	 * 获取图片分类名称：根据用户ID对5000取余
	 * @param userId
	 * @return
	 */
	public static String getImageSortName(String userId) {
		long result = 0;
		if(StringUtils.isNotBlank(userId)) {
			Long id = Long.parseLong(userId);
			result = id%5000;
		}
		return String.valueOf(result);
	}
}
