package com.iccspace.controller.model;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/12/21.
 */
public class ShopsEditModel {

    private String shopsId;
    private String estatesType;
    private String floor;
    private BigDecimal rentFee;
    private String mobilePhone;

    private double shopSize;
    private String shopsAddress;

    public String getEstatesType() {
        return estatesType;
    }

    public void setEstatesType(String estatesType) {
        this.estatesType = estatesType;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public BigDecimal getRentFee() {
        return rentFee;
    }

    public void setRentFee(BigDecimal rentFee) {
        this.rentFee = rentFee;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public double getShopSize() {
        return shopSize;
    }

    public void setShopSize(double shopSize) {
        this.shopSize = shopSize;
    }

    public String getShopsAddress() {
        return shopsAddress;
    }

    public void setShopsAddress(String shopsAddress) {
        this.shopsAddress = shopsAddress;
    }

    public String getShopsId() {
        return shopsId;
    }

    public void setShopsId(String shopsId) {
        this.shopsId = shopsId;
    }
}
