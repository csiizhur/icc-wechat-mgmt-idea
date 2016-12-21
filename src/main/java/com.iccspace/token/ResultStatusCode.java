package com.iccspace.token;
public enum ResultStatusCode {  
    OK(0, "OK"),  
    SYSTEM_ERR(30001, "System error"),
	INVALID_CLIENTID(30002, "Invalid clientid"),
	INVALID_PASSWORD(30003, "User name or password is incorrect"),
	INVALID_CAPTCHA(30004, "Invalid captcha or captcha overdue"),
	INVALID_TOKEN(30005, "Invalid token"),
	PERMISSION_DENIED(30006,"asdfgsf"),
    MOBILE_ISNULL(30007,"Mobile is null"),
    PASSWORD_ISNULL(30008,"Password is null"),
    OLDPASSWORD_ISNULL(30008,"OldPassword is null"),
    NEWPASSWORD_ISNULL(30008,"NewPassword is null"),
    TOKENOFKEY_ISNULL(30008,"key of token is null"),
    REDISKEY_ISNULL(30008,"redis key is null"),
    MOBILE_ISSIGN(30009,"Mobile is sign in system"),
    REDIS_VAILDCODE(30011,"Vaildcode is not in redis"),
    VAILDCODE_ISNULL(30012,"Vaildcode is null"),
    INVALID_VAILDCODE(30013,"VaildCode is incorrect"),
    INVALID_NEWPASSWORD(30014,"Newpassword equals oldpassword"),
    INVALID_REDIS_TOKEN(30015,"Invalid redis token"),
    INVALID_SHOPSID(30015,"Invalid shopsid");

    private int errcode;
    private String errmsg;  
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
    private ResultStatusCode(int Errode, String ErrMsg)  
    {  
        this.errcode = Errode;  
        this.errmsg = ErrMsg;
    }  
}