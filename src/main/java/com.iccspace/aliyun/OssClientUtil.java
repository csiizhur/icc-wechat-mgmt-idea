package com.iccspace.aliyun;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import java.io.*;
import java.net.URL;
import java.util.Date;

/**
 * Created by zhur on 2016/12/27.
 * @author:zhur
 * @description:
 * @date:create in 2016-12-27-16:12
 */
public class OssClientUtil {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String endPoint="http://oss-cn-shanghai.aliyuncs.com";
    private String accessKeyID="LTAI0Xo0GGlD35yY";
    private String accessKeySecret="73ImaOCKfGq2u4wqwko9nMyIoMl3k7";
    private String bucketName="test-iccspace";
    private String fileDir="";

    private OSSClient ossClient;

    public OssClientUtil(){
        ossClient = new OSSClient(endPoint,accessKeyID,accessKeySecret);
    }

    /**
     * oss init
     */
    public void init(){
        ossClient = new OSSClient(endPoint,accessKeyID,accessKeySecret);
    }
    /**
     * oss destory
     */
    public void destory(){
        ossClient.shutdown();
    }

    public String uploadPhoto(String localPath){
        File fileOnServer = new File(localPath);
        FileInputStream fin;
        String oss_url = null;
        try {
            fin = new FileInputStream(fileOnServer);
            String[] split = localPath.split("/");
            this.uploadPhoto2OSS(fin, split[split.length - 1]);
            oss_url=split[split.length - 1];
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("file not found");
        }
        return oss_url;
    }


    /**
     * 上传到OSS服务器 如果同名文件会覆盖服务器上的
     *
     * @param instream 文件流
     * @param fileName 文件名称 包括后缀名
     * @return 出错返回"" ,唯一MD5数字签名
     */
    public String uploadPhoto2OSS(InputStream instream, String fileName) {
        String ret = "";
        try {
            // 创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            // 上传文件
            PutObjectResult putResult = ossClient.putObject(bucketName, fileDir + fileName, instream, objectMetadata);
            ret = putResult.getETag();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * Description: 判断OSS服务文件上传时文件的contentType
     *
     * @param FilenameExtension 文件后缀
     * @return String
     */
    public static String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase("bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase("gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase("jpeg") || FilenameExtension.equalsIgnoreCase("jpg")
                || FilenameExtension.equalsIgnoreCase("png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equalsIgnoreCase("html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase("txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase("vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase("pptx") || FilenameExtension.equalsIgnoreCase("ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase("docx") || FilenameExtension.equalsIgnoreCase("doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase("xml")) {
            return "text/xml";
        }
        return "image/jpeg";
    }

    /**
     * 获得图片路径
     *
     * @param fileUrl
     * @return
     */
    public String getPhotoUrl(String fileUrl) {
        if (!StringUtils.isEmpty(fileUrl)) {
            String[] split = fileUrl.split("/");
            return this.getUrl(this.fileDir + split[split.length - 1]);
        }
        return null;
    }

    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public String getUrl(String key) {
        // 设置URL过期时间为10年 3600l* 1000*24*365*10
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }
}
