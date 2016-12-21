package com.iccspace.token;
/**
 * 封装返回的结果
 * @description
 * @author zhur
 * @date 2016年12月6日-上午9:28:37
 */
public class ResultMsg {  
    private int errcode;  
    private String errmsg;  
    private Object resultdata;
      
    public ResultMsg(int ErrCode, String ErrMsg, Object resultData)
    {  
        this.errcode = ErrCode;  
        this.errmsg = ErrMsg;  
        this.resultdata = resultData;
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
    public void setResultdata(Object resultdata) {this.resultdata = resultdata;
    }  
}