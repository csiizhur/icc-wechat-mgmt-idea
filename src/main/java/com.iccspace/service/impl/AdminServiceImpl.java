package com.iccspace.service.impl;

import com.iccspace.controller.ControllerMsg;
import com.iccspace.core.Constants;
import com.iccspace.mapper.AdminMapper;
import com.iccspace.service.AdminService;
import com.iccspace.token.*;
import com.iccspace.vo.Admin;
import com.iccspace.vo.AdminEdit;
import org.apache.ibatis.annotations.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/12/20.
 */
@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private Audience audience;

    @Override
    public ResultMsg signAdminAccount(Admin admin){
        ResultMsg resultMsg;
        String mobile = admin.getMobile();
        Admin user=adminMapper.queryAdminInfoByMobile(mobile);
        if(user!=null){
            resultMsg=new ResultMsg(ResultStatusCode.MOBILE_ISSIGN.getErrcode(),
                    ResultStatusCode.MOBILE_ISSIGN.getErrmsg(),null);
        }
        int rows=adminMapper.insertAdminAccount(admin);
        if(rows>Constants.AFFECT_DB_ROWS_0){
            resultMsg=new ResultMsg(Constants.OPERATOR_DB_SUCCESS,"",null);
            return resultMsg;
        }else{
            resultMsg =new ResultMsg(Constants.OPERATOR_DB_ERROR,Constants.OPERATOR_DB_ERROR_MESSAGE,null);
            return resultMsg;
        }
    }

    @Override
    public ResultMsg adminOauthToken(Admin admin){
        ResultMsg resultMsg;
        try {

            Admin user = adminMapper.queryAdminInfoByMobile(admin.getMobile());
            if (user == null) {
                resultMsg = new ResultMsg(ResultStatusCode.INVALID_PASSWORD.getErrcode(),
                        ResultStatusCode.INVALID_PASSWORD.getErrmsg(), null);
                return resultMsg;
            } else {
                String md5Password = MyMD5Utils.getMD5(admin.getPassword() + "salt");

                if (md5Password.compareTo(user.getPassword().toString()) != 0) {
                    resultMsg = new ResultMsg(ResultStatusCode.INVALID_PASSWORD.getErrcode(),
                            ResultStatusCode.INVALID_PASSWORD.getErrmsg(), null);
                    return resultMsg;
                }
            }

            String accessToken = JwtHelper.createJWT2(user.getUserName(), String.valueOf(user.getAdminId()),
                    audience.getExpiresSecond() * 1000, audience.getBase64Secret());

            stringRedisTemplate.opsForValue().set(admin.getMobile(),accessToken,audience.getExpiresSecond(), TimeUnit.SECONDS);

            AccessToken accessTokenEntity = new AccessToken();
            accessTokenEntity.setAccess_token(accessToken);
            accessTokenEntity.setExpires_in(audience.getExpiresSecond());
            accessTokenEntity.setToken_type("bearer");
            resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(), ResultStatusCode.OK.getErrmsg(),accessTokenEntity);
            return resultMsg;

        } catch (Exception ex) {
            resultMsg = new ResultMsg(ResultStatusCode.SYSTEM_ERR.getErrcode(), ResultStatusCode.SYSTEM_ERR.getErrmsg(),null);
            return resultMsg;
        }
    }
    @Override
    public ResultMsg restPassword(AdminEdit adminEdit) {
        //从redis获取带时间的vildCode
        RedisConnectionFactory f=stringRedisTemplate.getConnectionFactory();
        RedisConnection c=f.getConnection();
        c.getClientName();
        //java.lang.IllegalArgumentException: non null key required
        if(StringUtils.isEmpty(adminEdit.getVaildCode())){
            return new ResultMsg(ResultStatusCode.VAILDCODE_ISNULL.getErrcode(),ResultStatusCode.VAILDCODE_ISNULL.getErrmsg(),null);
        }
        String redisVaildCode=stringRedisTemplate.opsForValue().get(adminEdit.getVaildCode());

        if(StringUtils.isEmpty(redisVaildCode)){
            return new ResultMsg(ResultStatusCode.REDIS_VAILDCODE.getErrcode(),ResultStatusCode.REDIS_VAILDCODE.getErrmsg(),null);
        }
        if(!adminEdit.getVaildCode().equals(redisVaildCode)){
            return new ResultMsg(ResultStatusCode.INVALID_VAILDCODE.getErrcode(),ResultStatusCode.INVALID_VAILDCODE.getErrmsg(),null);
        }
        Admin user= adminMapper.queryAdminInfoByMobile(adminEdit.getMobile());
        user.setPassword(adminEdit.getNewPassword());
        int result=adminMapper.updateAdminPassword(user);

        if(result> Constants.AFFECT_DB_ROWS_0){
            return new ResultMsg(Constants.OPERATOR_DB_SUCCESS,"",null);
        }else{
            return new ResultMsg(Constants.OPERATOR_DB_ERROR,Constants.OPERATOR_DB_ERROR_MESSAGE,null);
        }
    }

    @Override
    public ResultMsg adminLoginOut(Admin user){
        stringRedisTemplate.opsForValue().getOperations().delete("c");
        return new ResultMsg(222,"",null);
    }

    //jpa方式缓存 mybatis无效
    //@Cacheable(value = "usercache", key = "#id + '_' + #userName")
    @CachePut(key = "#mobile")
    public Admin findAdmin(String mobile,String userName){
        Admin admin=adminMapper.queryAdminInfoByMobile(mobile);
        return admin;
    }
}
