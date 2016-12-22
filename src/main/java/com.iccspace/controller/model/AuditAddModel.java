package com.iccspace.controller.model;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2016/12/22.
 */
public class AuditAddModel {

    private String auditId;
    private String desc;
    private String shopsId;
    private String rentId;
    private String adminId;
    private Timestamp auditTime;

    public AuditAddModel(String desc, String shopsId, String adminId) {
        this.desc = desc;
        this.shopsId = shopsId;
        this.adminId = adminId;
    }

    public String getRentId() {
        return rentId;
    }

    public void setRentId(String rentId) {
        this.rentId = rentId;
    }

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
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

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public Timestamp getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Timestamp auditTime) {
        this.auditTime = auditTime;
    }
}
