package com.iccspace.service.impl;

import com.iccspace.core.Constants;
import com.iccspace.service.RedisService;
import com.iccspace.token.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/12/21.
 */
@Service
public class RedisServiceImpl implements RedisService{

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public ResultMsg putRedisToken(String key, String value,Long expireTime){
        ResultMsg resultMsg;
        stringRedisTemplate.opsForValue().set(key,value,expireTime, TimeUnit.SECONDS);
        resultMsg = new ResultMsg(Constants.OPERATOR_REDIS_SUCCESS,"operator redis success",null);
        return resultMsg;
    }
    @Override
    public Object queryRedisToken(String key) {
        Object o=stringRedisTemplate.opsForValue().get(key);
        return o;
    }
}
