package com.iccspace.controller.model;

/**
 * Created by Administrator on 2016/12/21.
 */
public class ShopsListRequest {
    private int releaseType;
    private String businessType;
    private String estatesType;
    private String buildType;

    private Integer pageNum;

    public int getReleaseType() {
        return releaseType;
    }

    public void setReleaseType(int releaseType) {
        this.releaseType = releaseType;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getEstatesType() {
        return estatesType;
    }

    public void setEstatesType(String estatesType) {
        this.estatesType = estatesType;
    }

    public String getBuildType() {
        return buildType;
    }

    public void setBuildType(String buildType) {
        this.buildType = buildType;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
}
