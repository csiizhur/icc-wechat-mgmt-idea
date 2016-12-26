package com.iccspace.service;

import com.iccspace.token.ResultMsg;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by zhur on 2016/12/26.
 * @author:zhur
 * @description:
 * @date:create in 2016-12-26-12:04
 * Administrator
 */
public interface FilesService {

    //photo upload
    public ResultMsg uploadShopsPhotos(MultipartFile multipartFile,String url);
}
