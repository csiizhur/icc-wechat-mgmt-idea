package com.iccspace.controller.model;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2016/12/22.
 */
public class RentsEditModel {

    private String rentId;
    private Timestamp releaseDate;
    private String expectShopSizeMin;
    private String expectShopSizeMax;
    private String expectRentFeeMin;
    private String expectRentFeeMax;
    private String mobilePhone;
    private String businessType;
    private String expectAddress;

    private String desc;

    public String getRentId() {
        return rentId;
    }

    public void setRentId(String rentId) {
        this.rentId = rentId;
    }

    public Timestamp getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Timestamp releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getExpectShopSizeMin() {
        return expectShopSizeMin;
    }

    public void setExpectShopSizeMin(String expectShopSizeMin) {
        this.expectShopSizeMin = expectShopSizeMin;
    }

    public String getExpectShopSizeMax() {
        return expectShopSizeMax;
    }

    public void setExpectShopSizeMax(String expectShopSizeMax) {
        this.expectShopSizeMax = expectShopSizeMax;
    }

    public String getExpectRentFeeMin() {
        return expectRentFeeMin;
    }

    public void setExpectRentFeeMin(String expectRentFeeMin) {
        this.expectRentFeeMin = expectRentFeeMin;
    }

    public String getExpectRentFeeMax() {
        return expectRentFeeMax;
    }

    public void setExpectRentFeeMax(String expectRentFeeMax) {
        this.expectRentFeeMax = expectRentFeeMax;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getExpectAddress() {
        return expectAddress;
    }

    public void setExpectAddress(String expectAddress) {
        this.expectAddress = expectAddress;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
