package com.iccspace.controller.code;

import com.alibaba.fastjson.JSONObject;
import com.iccspace.controller.code.DesUtil;
import com.iccspace.controller.code.SerializeUtil;
import com.iccspace.controller.code.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/12/24.
 */
@RestController
public class CodeController {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取验证码的token
     * @return
     */
    @RequestMapping(value="loginCode")
    @ResponseBody
    public String getCode(){
        PrintWriter out = null;
        JSONObject jsono = new JSONObject();
        try {
            //验证码工具类
            ValidateCode vCode = new ValidateCode(55,25,4,80);
            String randomCode = vCode.randomCode();
            String encCode = DesUtil.strEnc(randomCode+System.currentTimeMillis(), "1", "2", "3");
            //存储验证码字符串,过期时间为1分钟
            redisTemplate.opsForValue().set(encCode, randomCode);
            redisTemplate.expire(encCode, 1, TimeUnit.MINUTES);
            //存储验证码生成器,过期时间为1分钟
            redisTemplate.opsForValue().set(encCode+"ValidateCode", SerializeUtil.serialize(vCode));
            redisTemplate.expire(encCode+"ValidateCode", 1, TimeUnit.MINUTES);
            jsono.put("success", true);
            jsono.put("message", encCode);
        } catch (Exception e) {
            e.printStackTrace();
            jsono.put("success", true);
            jsono.put("message", "inner error.");
        } finally{
            if(out != null) {
                out.flush();
                out.close();
            }
        }
        return jsono.toString();
    }

    /**
     * 获取验证码图片
     * @param codeAuth
     * @param response
     */
    @RequestMapping(value="loginCodeImage")
    public void getCodeImage(String codeAuth, HttpServletResponse response){
        if(codeAuth == null) return;
        String randomCode = (String) redisTemplate.opsForValue().get(codeAuth);
        if(randomCode == null) return;
        ValidateCode vCode = (ValidateCode)SerializeUtil.unserialize((byte[])redisTemplate.opsForValue().get(codeAuth+"ValidateCode"));
        //产生图片
        vCode.createCode(randomCode);
        if(vCode == null) return;
        // 设置响应的类型格式为图片格式
        response.setContentType("image/jpeg");
        //禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        try {
            vCode.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
