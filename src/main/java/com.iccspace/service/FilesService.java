package com.iccspace.service;

import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

/**
 * Created by zhur on 2016/12/26.
 * @author:zhur
 * @description:
 * @date:create in 2016-12-26-12:04
 */
public interface FilesService {

    //photo upload
    public Map uploadShopsPhotos(MultipartFile multipartFile, String url);
}
