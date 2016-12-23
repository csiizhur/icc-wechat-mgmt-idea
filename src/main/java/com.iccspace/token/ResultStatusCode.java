package com.iccspace.token;
public enum ResultStatusCode {  
    OK(0, "OK"),  
    SYSTEM_ERR(30001, "System error"),
	INVALID_CLIENTID(30002, "Invalid clientid"),
	INVALID_PASSWORD(30003, "User name or password is incorrect"),
	INVALID_CAPTCHA(30004, "Invalid captcha or captcha overdue"),
	INVALID_TOKEN(30005, "Invalid token"),
	PERMISSION_DENIED(30006,"asdfgsf"),

    INVALID_MOBILE(30011,"Mobile is null"),
    INVALID_ADMINID(30011,"Invalid adminid"),
    INVALID_PASSWORD_ISNULL(30012,"Password is null"),
    INVALID_OLDPASSWORD(30013,"OldPassword is null"),
    INVALID_NEWPASSWORD(30014,"NewPassword is null"),
    INVALID_TOKENOFKEY_ISNULL(30015,"key of token is null"),
    INVALID_REDISKEY_ISNULL(30016,"redis key is null"),
    INVALID_MOBILE_ISSIGN(30017,"Mobile is sign in system"),
    INVALID_REDIS_VAILDCODE(30018,"Vaildcode is not in redis"),
    INVALID_VAILDCODE(30019,"Vaildcode is null"),
    INVALID_REDIS_TOKEN(30020,"Invalid redis token"),

    INVALID_SHOPSID(30021,"Invalid shopsid"),
    INVALID_UPLOAD_FILE(30022,"Invalid upload file"),
    INVALID_ESTATESTYPE(30023,"Invalid estates type"),
    INVALID_RELEASEDATE(30023,"Invalid release date"),
    INVALID_MOBILEPHONE(30023,"Invalid link mobilephone"),
    INVALID_FLOOR(30023,"Invalid floor"),
    INVALID_RELEASETYPE(30023,"Invalid release type"),

    INVALID_RENTID(30031,"Invalid rentid"),
    INVALID_NICKNAME(30032,"Invalid nickname"),
    INVALID_ADDRESS(30033,"Invalid address"),
    INVALID_SHOPSIZEMIN(30034,"Invalid min_shopsize"),
    INVALID_SHOPSIZEMAX(30035,"Invalid max_shopsize"),
    INVALID_RENTFEEMIN(30036,"Invalid min_rentfee"),
    INVALID_RENTFEEMAX(30037,"Invalid max_rentfee"),
    ;

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