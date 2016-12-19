package com.iccspace.token;
/**
 * token返回结果类
 * @description
 * @author zhur
 * @date 2016年12月6日-上午9:19:35
 */
public class AccessToken {  
    private String access_token;  
    private String token_type;  
    private long expires_in;  
    public String getAccess_token() {  
        return access_token;  
    }  
    public void setAccess_token(String access_token) {  
        this.access_token = access_token;  
    }  
    public String getToken_type() {  
        return token_type;  
    }  
    public void setToken_type(String token_type) {  
        this.token_type = token_type;  
    }  
    public long getExpires_in() {  
        return expires_in;  
    }  
    public void setExpires_in(long expires_in) {  
        this.expires_in = expires_in;  
    }  
}