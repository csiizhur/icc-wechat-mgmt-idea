package com.iccspace.service.impl;

import com.iccspace.core.Constants;
import com.iccspace.service.FilesService;
import com.iccspace.token.ResultMsg;
import com.iccspace.token.ResultStatusCode;
import org.apache.ibatis.annotations.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhur on 2016/12/26.
 * @author:zhur
 * @description:
 * @date:create in 2016-12-26-12:05
 * Administrator
 */
@Service
public class FilesServiceImpl implements FilesService{

    @Override
    public ResultMsg uploadShopsPhotos(MultipartFile multipartFile,String url) {
        ResultMsg resultMsg;
        String os_name = System.getProperty("os.name");
        String upload_path;
        if(os_name!=null && os_name.toLowerCase().indexOf("linux")>-1){
            upload_path = Constants.UPLOAD_PATH_LINUX;
        }else{
            upload_path = Constants.UPLOAD_PATH_WIN;

        }
        String fileName = multipartFile.getOriginalFilename();
        String targetPath=upload_path+fileName;
        File mk= new File(upload_path);
        File targetFile = new File(targetPath);

        if(!mk.exists()){
            mk.mkdirs();
        }

        try {
            multipartFile.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map map = new HashMap();
        map.put("local_url",targetPath);

        map.put("url",url+fileName);

        resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),ResultStatusCode.OK.getErrmsg(),map);
        return resultMsg;
    }
}
