package com.iccspace.controller;

/**
 * controller消息
 * Created by Administrator on 2016/12/20.
 */
public class ControllerMsg {
    private int errcode;
    private String errmsg;
    private Object resultdata;

    public ControllerMsg(int errcode, String errmsg, Object resultdata) {
        this.errcode = errcode;
        this.errmsg = errmsg;
        this.resultdata = resultdata;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Object getResultdata() {
        return resultdata;
    }

    public void setResultdata(Object resultdata) {
        this.resultdata = resultdata;
    }
}
