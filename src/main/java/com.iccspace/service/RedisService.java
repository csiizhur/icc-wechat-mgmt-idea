package com.iccspace.service;

import com.iccspace.token.ResultMsg;

/**
 * Created by Administrator on 2016/12/21.
 */
public interface RedisService {

    //redis put token
    public ResultMsg putRedisToken(String key, String value,Long expireTime);
    //redis get token
    public Object queryRedisToken(String key);
}
