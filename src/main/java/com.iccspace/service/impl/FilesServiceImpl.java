package com.iccspace.service.impl;

import com.iccspace.aliyun.FileUtil;
import com.iccspace.aliyun.OssClientUtil;
import com.iccspace.core.Constants;
import com.iccspace.service.FilesService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhur on 2016/12/26.
 * @author:zhur
 * @description:
 * @date:create in 2016-12-26-12:05
 */
@Service
public class FilesServiceImpl implements FilesService{

    /**
     * multipartFile upload server upload oss
     * @param multipartFile
     * @param url
     * @return
     */
    @Override
    public Map uploadShopsPhotos(MultipartFile multipartFile,String url) {
        String os_name = System.getProperty("os.name");
        String upload_path;
        if(os_name!=null && os_name.toLowerCase().indexOf("linux")>-1){
            upload_path = Constants.UPLOAD_PATH_LINUX;
        }else{
            upload_path = Constants.UPLOAD_PATH_WIN;

        }
        String originalName = multipartFile.getOriginalFilename();

        String fix = originalName.substring(originalName.lastIndexOf(".")+1);
        long fileName = Calendar.getInstance().getTimeInMillis();
        String uri = FileUtil.generateFolderPathByTime(fileName);
        String targetPath=upload_path+uri+"/"+fileName+"."+fix;
        File mk= new File(upload_path+ uri);
        File targetFile = new File(targetPath);

        if(!mk.exists()){
            mk.mkdirs();
        }

        String oss_url=null;
        try {
            multipartFile.transferTo(targetFile);

            OssClientUtil ossClientUtil = new OssClientUtil();
            String oss_photo_name = ossClientUtil.uploadPhoto(targetPath);
            oss_url = ossClientUtil.getPhotoUrl(oss_photo_name);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map map = new HashMap();

        map.put("local_url",targetPath);
        map.put("url",url+fileName+"."+fix);
        map.put("oss_url",oss_url);

        return map;
    }
}
