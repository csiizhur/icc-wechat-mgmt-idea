package com.iccspace.controller;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import cn.apiclub.captcha.gimpy.FishEyeGimpyRenderer;
import com.alibaba.fastjson.JSONObject;
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
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;
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
            msg=new ResultMsg(ResultStatusCode.INVALID_MOBILE.getErrcode(),
                    ResultStatusCode.INVALID_MOBILE.getErrmsg(),null);
            return msg;
        }
        if(StringUtils.isEmpty(pwd)){
            msg=new ResultMsg(ResultStatusCode.INVALID_PASSWORD_ISNULL.getErrcode(),
                    ResultStatusCode.INVALID_PASSWORD_ISNULL.getErrmsg(),null);
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
            msg= new ResultMsg(ResultStatusCode.INVALID_MOBILE.getErrcode(),ResultStatusCode.INVALID_MOBILE.getErrmsg(),null);
            return msg;
        }
        if(StringUtils.isEmpty(password)){
            msg= new ResultMsg(ResultStatusCode.INVALID_PASSWORD_ISNULL.getErrcode(),ResultStatusCode.INVALID_PASSWORD_ISNULL.getErrmsg(),null);
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
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_MOBILE.getErrcode(),ResultStatusCode.INVALID_MOBILE.getErrmsg(),null);
            return resultMsg;
        }
        if(StringUtils.isEmpty(oldPwd)){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_OLDPASSWORD.getErrcode(),ResultStatusCode.INVALID_OLDPASSWORD.getErrmsg(),null);
            return resultMsg;
        }
        if(StringUtils.isEmpty(newPwd)){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_NEWPASSWORD.getErrcode(),ResultStatusCode.INVALID_NEWPASSWORD.getErrmsg(),null);
            return resultMsg;
        }
        resultMsg=adminService.editAdminPassword(admin);
        return resultMsg;
    }

    /**
     * rest password need send vaild_code
     * @param adminId
     * @param mobile
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "sendVaildCode2",produces = "application/json;charset=UTF-8")
    public Object sendVaildCode(String adminId,String mobile){
        ResultMsg resultMsg;
        //模拟的校验码
        String vaild_code="is_code";
        //redis vaildcode {adminId_mobile:vaild_code}
        stringRedisTemplate.opsForValue().set(adminId+"_"+mobile,vaild_code,60,TimeUnit.SECONDS);
        JSONObject json = new JSONObject();
        json.put("vaildCode",vaild_code);
        json.put("expire",120);
        resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),ResultStatusCode.OK.getErrmsg(),json);
        return resultMsg;
    }
    private static int captchaExpires = 3*60; //超时时间3min
    private static int captchaW = 200;
    private static int captchaH = 60;

    /**
     * send vaild_code
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "sendVaildCode",produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getCaptcha(HttpServletResponse response){
        //生成验证码 没使用mobile作为redis的key
        String uuid = UUID.randomUUID().toString();
        Captcha captcha = new Captcha.Builder(captchaW, captchaH)
                .addText().addBackground(new GradiatedBackgroundProducer())
                .gimp(new FishEyeGimpyRenderer())
                .build();
        //将验证码以<key,value>形式缓存到redis
        stringRedisTemplate.opsForValue().set(uuid, captcha.getAnswer(), captchaExpires, TimeUnit.SECONDS);

        //将验证码key，及验证码的图片返回
        Cookie cookie = new Cookie("CaptchaCode",uuid);
        response.addCookie(cookie);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        try {
            ImageIO.write(captcha.getImage(), "png", bao);
            return bao.toByteArray();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * rest pwd
     * @param adminEdit
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "restPassword",produces = "application/json;charset=UTF-8")
    public Object restAdminPassword(@RequestBody AdminEdit adminEdit){

        String vaildcode = adminEdit.getVaildCode();
        String vaildvalue = adminEdit.getVaildValue();
        String mobile = adminEdit.getMobile();
        String password = adminEdit.getNewPassword();
        if(StringUtils.isEmpty(vaildcode)){
            return new ResultMsg(ResultStatusCode.INVALID_VAILDCODE.getErrcode(),ResultStatusCode.INVALID_VAILDCODE.getErrmsg(),null);
        }
        if(StringUtils.isEmpty(vaildvalue)){
            return new ResultMsg(ResultStatusCode.INVALID_VAILDCODE.getErrcode(),ResultStatusCode.INVALID_VAILDCODE.getErrmsg(),null);
        }
        if(StringUtils.isEmpty(mobile)){
            return new ResultMsg(ResultStatusCode.INVALID_MOBILE.getErrcode(),ResultStatusCode.INVALID_MOBILE.getErrmsg(),null);
        }
        if(StringUtils.isEmpty(password)){
            return new ResultMsg(ResultStatusCode.INVALID_NEWPASSWORD.getErrcode(),ResultStatusCode.INVALID_NEWPASSWORD.getErrmsg(),null);
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
            return new ResultMsg(ResultStatusCode.INVALID_TOKENOFKEY_ISNULL.getErrcode(),ResultStatusCode.INVALID_TOKENOFKEY_ISNULL.getErrmsg(),null);
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
