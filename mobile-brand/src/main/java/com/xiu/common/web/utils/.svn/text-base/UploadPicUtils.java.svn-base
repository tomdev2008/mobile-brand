package com.xiu.common.web.utils;

import java.io.File;
import java.util.Date;

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

}
