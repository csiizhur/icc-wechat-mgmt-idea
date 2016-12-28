package com.iccspace.controller.model;

/**
 * Created by zhur on 2016/12/28.
 * @author:zhur
 * @description:
 * @date:create in 2016-12-28-16:22
 */
public class ThumbnailUrlEditModel {

    private String shopsId;
    private String thumbnailUrl;

    public ThumbnailUrlEditModel(String shopsId, String thumbnailUrl) {
        this.shopsId = shopsId;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getShopsId() {
        return shopsId;
    }

    public void setShopsId(String shopsId) {
        this.shopsId = shopsId;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
