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
    @RequestMapping(method = RequestMethod.POST,value="sign",produces = "application/json;charset=UTF-8")
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
        ControllerMsg resultMsg=null;
        Admin user=adminMapper.queryAdminInfoByMobileAndPassword(admin.getMobile(),MyMD5Utils.getMD5(admin.getOldPassword()+"salt"));
        if(user==null){
            resultMsg = new ControllerMsg(ResultStatusCode.INVALID_PASSWORD.getErrcode(),
                    ResultStatusCode.INVALID_PASSWORD.getErrmsg(), null);
            return resultMsg;
        }
        if(admin.getOldPassword().equals(admin.getNewPassword())){
            resultMsg = new ControllerMsg(ResultStatusCode.INVALID_NEWPASSWORD.getErrcode(),ResultStatusCode.INVALID_NEWPASSWORD.getErrmsg(),null);
        }
        Admin adminUpdate=new Admin();
        adminUpdate.setAdminId(user.getAdminId());
        String md5Password=MyMD5Utils.getMD5(admin.getNewPassword()+"salt");
        adminUpdate.setPassword(md5Password);
        adminUpdate.setMobile(user.getMobile());
        int result=adminMapper.updateAdminPassword(adminUpdate);
        if(result==1){
            resultMsg = new ControllerMsg(ResultStatusCode.OK.getErrcode(), ResultStatusCode.OK.getErrmsg(),null);
        }
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
            return new ControllerMsg(ResultStatusCode.INVALID_REDIS_TOKEN.getErrcode(),ResultStatusCode.INVALID_REDIS_TOKEN.getErrmsg(),null);
        }
        ResultMsg msg=adminService.restPassword(adminEdit);
        return new ControllerMsg(msg.getErrcode(),msg.getErrmsg(),null);
    }

    /**
     * login out
     * @param admin
     * @return
     */
    @RequestMapping(method = RequestMethod.POST ,value="loginOut" ,produces = "application/json;charset=UTF-8")
    public Object adminLoginOut(@RequestBody Admin admin){
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
