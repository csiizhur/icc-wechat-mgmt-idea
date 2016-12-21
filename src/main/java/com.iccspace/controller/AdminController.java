package com.iccspace.controller;

import com.iccspace.mapper.AdminMapper;
import com.iccspace.service.AdminService;
import com.iccspace.service.RedisService;
import com.iccspace.token.*;
import com.iccspace.vo.Admin;
import com.iccspace.vo.AdminEdit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/12/20.
 */
@RestController
@RequestMapping("admin")
public class AdminController {

    private Logger log= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private Audience audience;

    @Autowired
    private AdminService adminService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisService redisService;

    /**
     * account sign
     * @param admin
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value="signUpAccount",produces = "application/json;charset=UTF-8")
    public Object signAdminAccount(@RequestBody Admin admin){
        ResultMsg msg;
        String mobile=admin.getMobile();
        String pwd=admin.getPassword();
        if(StringUtils.isEmpty(mobile)){
            msg=new ResultMsg(ResultStatusCode.MOBILE_ISNULL.getErrcode(),
                    ResultStatusCode.MOBILE_ISNULL.getErrmsg(),null);
            return msg;
        }
        if(StringUtils.isEmpty(pwd)){
            msg=new ResultMsg(ResultStatusCode.PASSWORD_ISNULL.getErrcode(),
                    ResultStatusCode.PASSWORD_ISNULL.getErrmsg(),null);
            return msg;
        }
        msg=adminService.signAdminAccount(admin);
        return msg;
    }
    /**
     * json web token
     * @param admin
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "oauth/token",produces = "application/json;charset=UTF-8")
    public Object getAdminToken(@RequestBody Admin admin){
        ResultMsg msg;
        String mobile=admin.getMobile();
        String password=admin.getPassword();
        if(StringUtils.isEmpty(mobile)){
            msg= new ResultMsg(ResultStatusCode.MOBILE_ISNULL.getErrcode(),ResultStatusCode.MOBILE_ISNULL.getErrmsg(),null);
            return msg;
        }
        if(StringUtils.isEmpty(password)){
            msg= new ResultMsg(ResultStatusCode.PASSWORD_ISNULL.getErrcode(),ResultStatusCode.PASSWORD_ISNULL.getErrmsg(),null);
            return msg;
        }
        msg=adminService.adminOauthToken(admin);

        return msg;
    }

    /**
     * edit pwd
     * @param admin
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "editPassword",produces = "application/json;charset=UTF-8")
    public Object editAdminPassword(@RequestBody AdminEdit admin){
        ResultMsg resultMsg;
        String mobile=admin.getMobile();
        String oldPwd=admin.getOldPassword();
        String newPwd=admin.getNewPassword();
        if(StringUtils.isEmpty(mobile)){
            resultMsg = new ResultMsg(ResultStatusCode.MOBILE_ISNULL.getErrcode(),ResultStatusCode.MOBILE_ISNULL.getErrmsg(),null);
            return resultMsg;
        }
        if(StringUtils.isEmpty(oldPwd)){
            resultMsg = new ResultMsg(ResultStatusCode.OLDPASSWORD_ISNULL.getErrcode(),ResultStatusCode.OLDPASSWORD_ISNULL.getErrmsg(),null);
            return resultMsg;
        }
        if(StringUtils.isEmpty(newPwd)){
            resultMsg = new ResultMsg(ResultStatusCode.NEWPASSWORD_ISNULL.getErrcode(),ResultStatusCode.NEWPASSWORD_ISNULL.getErrmsg(),null);
            return resultMsg;
        }
        resultMsg=adminService.editAdminPassword(admin);
        return resultMsg;
    }

    /**
     * rest pwd
     * @param adminEdit
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "restPassword",produces = "application/json;charset=UTF-8")
    public Object restAdminPassword(@RequestBody AdminEdit adminEdit){
        Object o=redisService.queryRedisToken(adminEdit.getMobile());
        log.error("redis-->"+o);
        if(o==null){
            return new ResultMsg(ResultStatusCode.INVALID_REDIS_TOKEN.getErrcode(),ResultStatusCode.INVALID_REDIS_TOKEN.getErrmsg(),null);
        }
        ResultMsg msg=adminService.restPassword(adminEdit);
        return msg;
    }

    /**
     * login out
     * @param admin
     * @return
     */
    @RequestMapping(method = RequestMethod.POST ,value="loginOut" ,produces = "application/json;charset=UTF-8")
    public Object adminLoginOut(@RequestBody Admin admin){
        String tokenKey=admin.getMobile();
        if(StringUtils.isEmpty(tokenKey)){
            return new ResultMsg(ResultStatusCode.TOKENOFKEY_ISNULL.getErrcode(),ResultStatusCode.TOKENOFKEY_ISNULL.getErrmsg(),null);
        }
        return adminService.adminLoginOut(admin);
    }

    @RequestMapping(method = RequestMethod.GET,value = "putCache")
    public Admin putCache(){
        return adminService.findAdmin("13916176592","as");
    }
    @RequestMapping(method = RequestMethod.GET,value = "getCache")
    public Admin getCache(){
        return adminService.findAdmin("13916176592","as");
    }

}
