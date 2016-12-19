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
    private Object p2pdata;  
      
    public ResultMsg(int ErrCode, String ErrMsg, Object P2pData)  
    {  
        this.errcode = ErrCode;  
        this.errmsg = ErrMsg;  
        this.p2pdata = P2pData;  
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
    public Object getP2pdata() {  
        return p2pdata;  
    }  
    public void setP2pdata(Object p2pdata) {  
        this.p2pdata = p2pdata;  
    }  
}