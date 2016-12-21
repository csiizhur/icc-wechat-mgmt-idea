package com.iccspace.vo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/20.
 */
public class Admin implements Serializable {

    private String adminId;
    private String userName;
    private String mobile;
    private String password;
    private String password_salt;
    private String status;

    public Admin() {
    }

    public Admin(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }

    public Admin(String mobile, String password, String password_salt) {
        this.mobile = mobile;
        this.password = password;
        this.password_salt = password_salt;
    }

    public Admin(String adminId, String name, String mobile, String password, String password_salt, String status) {
        this.adminId = adminId;
        this.userName = name;
        this.mobile = mobile;
        this.password = password;
        this.password_salt = password_salt;
        this.status = status;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_salt() {
        return password_salt;
    }

    public void setPassword_salt(String password_salt) {
        this.password_salt = password_salt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
