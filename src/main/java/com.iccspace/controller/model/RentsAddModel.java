package com.iccspace.controller.model;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2016/12/22.
 */
public class RentsAddModel {

    private String nickName;
    private String userId="iccspace_system";

    private Timestamp releaseDate;
    private String expectShopsizeMin;
    private String expectShopsizeMax;
    private String expectRentFeeMin;
    private String expectRentFeeMax;
    private String mobilePhone;
    private String businessType;
    private String expectAddress;
    private Integer releaseType;

    private String adminId;
    private String desc;
    private Timestamp auditTime;

    private String rentId;

    public String getNickName() {
        return nickName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Timestamp getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Timestamp auditTime) {
        this.auditTime = auditTime;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getRentId() {
        return rentId;
    }

    public void setRentId(String rentId) {
        this.rentId = rentId;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Timestamp getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Timestamp releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getExpectShopsizeMin() {
        return expectShopsizeMin;
    }

    public void setExpectShopsizeMin(String expectShopsizeMin) {
        this.expectShopsizeMin = expectShopsizeMin;
    }

    public String getExpectShopsizeMax() {
        return expectShopsizeMax;
    }

    public void setExpectShopsizeMax(String expectShopsizeMax) {
        this.expectShopsizeMax = expectShopsizeMax;
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

    public Integer getReleaseType() {
        return releaseType;
    }

    public void setReleaseType(Integer releaseType) {
        this.releaseType = releaseType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
