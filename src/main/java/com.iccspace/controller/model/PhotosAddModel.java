package com.iccspace.controller.model;

/**
 * Created by zhur on 2016/12/28.
 * @author:zhur
 * @description:
 * @date:create in 2016-12-28-11:53
 */
public class PhotosAddModel {

    private String photoId;
    private String shopsId;
    private String ossUrl;

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getShopsId() {
        return shopsId;
    }

    public void setShopsId(String shopsId) {
        this.shopsId = shopsId;
    }

    public String getOssUrl() {
        return ossUrl;
    }

    public void setOssUrl(String ossUrl) {
        this.ossUrl = ossUrl;
    }
}
