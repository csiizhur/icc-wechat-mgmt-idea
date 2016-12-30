package com.iccspace.controller.model;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by zhur on 2016/12/30.
 *
 * @author:zhur
 * @description:
 * @date:create in 2016-12-30-10:39
 */
public class PropertyEditModel {

    private String contractId;
    private MultipartFile contractFile;
    private String ossUrl;

    private String shopsId;
    private String auditId;
    private String auditDesc;
    private Timestamp auditTime;
    private String adminId;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public MultipartFile getContractFile() {
        return contractFile;
    }

    public void setContractFile(MultipartFile contractFile) {
        this.contractFile = contractFile;
    }

    public String getOssUrl() {
        return ossUrl;
    }

    public void setOssUrl(String ossUrl) {
        this.ossUrl = ossUrl;
    }

    public String getShopsId() {
        return shopsId;
    }

    public void setShopsId(String shopsId) {
        this.shopsId = shopsId;
    }

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
    }

    public String getAuditDesc() {
        return auditDesc;
    }

    public void setAuditDesc(String auditDesc) {
        this.auditDesc = auditDesc;
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
}
