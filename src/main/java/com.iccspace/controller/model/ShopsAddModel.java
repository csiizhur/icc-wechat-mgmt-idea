package com.iccspace.controller.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2016/12/22.
 */
public class ShopsAddModel implements Serializable{

    private static final long serialVersionUID = -4358002317537580709L;

    private String userId="iccspace_system";

    private String estatesType;
    private String releaseDate;
    private double shopSize;
    private String mobilePhone;
    private String floor;
    private String shopsAddress;
    private BigDecimal rentFee;
    private Integer releaseType;

    private String adminId;
    private String desc;
    private Timestamp auditTime;

    private String shopsId;
    private String baseSHopsId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAdminId() {
        return adminId;
    }

    public Timestamp getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Timestamp auditTime) {
        this.auditTime = auditTime;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getEstatesType() {
        return estatesType;
    }

    public void setEstatesType(String estatesType) {
        this.estatesType = estatesType;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getShopSize() {
        return shopSize;
    }

    public void setShopSize(double shopSize) {
        this.shopSize = shopSize;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getShopsAddress() {
        return shopsAddress;
    }

    public void setShopsAddress(String shopsAddress) {
        this.shopsAddress = shopsAddress;
    }

    public BigDecimal getRentFee() {
        return rentFee;
    }

    public void setRentFee(BigDecimal rentFee) {
        this.rentFee = rentFee;
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

    public String getShopsId() {
        return shopsId;
    }

    public void setShopsId(String shopsId) {
        this.shopsId = shopsId;
    }

    public String getBaseSHopsId() {
        return baseSHopsId;
    }

    public void setBaseSHopsId(String baseSHopsId) {
        this.baseSHopsId = baseSHopsId;
    }
}
