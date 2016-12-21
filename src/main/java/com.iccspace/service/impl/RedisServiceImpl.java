package com.iccspace.service.impl;

import com.iccspace.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/12/21.
 */
@Service
public class RedisServiceImpl implements RedisService{

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Object queryRedisToken(String key) {
        Object o=stringRedisTemplate.opsForValue().get(key);
        return o;
    }
}
