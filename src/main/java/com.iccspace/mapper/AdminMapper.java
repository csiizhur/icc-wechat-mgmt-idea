package com.iccspace.mapper;

import com.iccspace.vo.Admin;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CachePut;

/**
 * Created by Administrator on 2016/12/20.
 */
//@CacheNamespace(implementation = (com.iccspace.redis.MybatisRedisCache.class))
public interface AdminMapper {

    @Insert("insert into ADMIN(id,name,mobile,password) values(#{adminId},#{userName},#{mobile},#{password})")
    @SelectKey(statement = "select replace(uuid(),'-','') from dual",keyProperty = "adminId",before = true,resultType = String.class)
    public int insertAdminAccount(Admin admin);

    @Select("select id,name,mobile,password from `wechat-server-db`.ADMIN where mobile=#{mobile}")
    @Results({
            @Result(id=true,column="id",property = "adminId"),
            @Result(column = "name",property = "userName"),
            @Result(column = "mobile",property = "mobile"),
            @Result(column = "password",property = "password"),
    })
    public Admin queryAdminInfoByMobile(String mobile);

    @Select("select id,name,mobile,password from `wechat-server-db`.ADMIN where mobile=#{mobile} and password=#{password}")
    @Results({
            @Result(id=true,column="id",property = "adminId"),
            @Result(column = "name",property = "userName"),
            @Result(column = "mobile",property = "mobile"),
            @Result(column = "password",property = "password"),
    })
    public Admin queryAdminInfoByMobileAndPassword(@Param("mobile") String mobile, @Param("password") String password);

    @Update("update ADMIN set password=#{password} where mobile=#{mobile} and id=#{adminId}")
    public int updateAdminPassword(Admin admin);
}
