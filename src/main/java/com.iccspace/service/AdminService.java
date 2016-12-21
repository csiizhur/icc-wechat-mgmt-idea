package com.iccspace.service;

import com.iccspace.token.ResultMsg;
import com.iccspace.vo.Admin;
import com.iccspace.vo.AdminEdit;

/**
 * Created by Administrator on 2016/12/20.
 */
public interface AdminService {

    //注册
    public ResultMsg signAdminAccount(Admin admin);
    //认证
    public ResultMsg adminOauthToken(Admin admin);
    //edit pwd
    public ResultMsg editAdminPassword(AdminEdit admin);

    public ResultMsg restPassword(AdminEdit adminEdit);

    public ResultMsg adminLoginOut(Admin admin);

    public Admin findAdmin(String id,String userName);
}
