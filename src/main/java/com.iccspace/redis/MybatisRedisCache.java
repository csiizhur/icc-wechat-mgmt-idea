package com.iccspace.redis;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;


import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * MyBatis二级缓存(redis)
 */
public class MybatisRedisCache implements Cache {
    private static Logger logger = LoggerFactory.getLogger(MybatisRedisCache.class);

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
     
    private String id;

    private Jedis redisClient=createRedis(); //创建一个jedis连接


    public MybatisRedisCache(final String id) {  
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>MybatisRedisCache:id="+id);
        this.id = id;
    }

    /**
     * redis连接
     * @return
     */
    private static Jedis createRedis() {
        //从连接池获取redis连接
        Jedis jedis =RedisPool.getPool().getResource();
        return jedis;
    }

    public String getId() {
        return this.id;
    }
 
    public void putObject(Object key, Object value) {
        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>putObject:"+key+"="+value);
        byte[] keybyte=SerializableUtil.serialize(key);
        byte[]valuebyte=SerializableUtil.serialize(value);
        this.redisClient.set(keybyte, valuebyte);
        //RedisUtil.getJedis().set(SerializeUtil.serialize(key.toString()), SerializeUtil.serialize(value));
    }
 
    public Object getObject(Object key) {
        Object value = SerializableUtil.unserizlize(this.redisClient.get(SerializableUtil.serialize(key.toString())));
        //Object value = SerializeUtil.unserialize(RedisUtil.getJedis().get(SerializeUtil.serialize(key.toString())));
        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>getObject:"+key+"="+value);
        return value;
    }
 
    public Object removeObject(Object key) {
        return this.redisClient.expire(SerializableUtil.serialize(key.toString()),0);
        //return RedisUtil.getJedis().expire(SerializeUtil.serialize(key.toString()),0);
    }
 
    public void clear() {
        this.redisClient.flushDB();
        //RedisUtil.getJedis().flushDB();
    }
 
    public int getSize() {
        return Integer.valueOf(this.redisClient.dbSize().toString());
        //return Integer.valueOf(RedisUtil.getJedis().dbSize().toString());
    }
 
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }
     
}