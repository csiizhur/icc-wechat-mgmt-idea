package com.iccspace.vo;

/**
 * Created by Administrator on 2016/12/20.
 */
public class AdminEdit {

    private String mobile;
    private String oldPassword;
    private String newPassword;
    private String vaildCode;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getVaildCode() {
        return vaildCode;
    }

    public void setVaildCode(String vaildCode) {
        this.vaildCode = vaildCode;
    }
}
