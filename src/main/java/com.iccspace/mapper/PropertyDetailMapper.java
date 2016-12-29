package com.iccspace.mapper;

import com.iccspace.config.EmptyStringIfNull;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.Map;

/**
 * Created by zhur on 2016/12/29.
 * 物业详细信息
 * @author:zhur
 * @description:
 * @date:create in 2016-12-29-16:31
 */
public interface PropertyDetailMapper {

    @Select("select rent_fee,release_type,shop_size,floor,renovation,address,sh.create_time,mobilephone,sh.deleted " +
            "from SHOPS_HISTORY sh join SHOPS s on sh.id=s.historyid " +
            "where sh.id=#{shopsId}")
    @Results({
            @Result(column = "rent_fee", property = "rentFee"),
            @Result(column = "release_type", property = "releaseType"),
            @Result(column = "shop_size", property = "shopSize"),
            @Result(column = "floor", property = "floor"),
            @Result(column = "renovation", property = "renovation"),
            @Result(column = "address", property = "propertyAddress"),
            @Result(column = "create_time", property = "releaseDate"),
            @Result(column = "mobilephone", property = "mobilePhone"),
            @Result(column = "deleted", property = "deleted",javaType = Integer.class)
    })
    public Map<String,Object> queryShopsDetail(String shopsId);

    @Select("select id,nickname,sex from USER where id=#{userId}")
    @Results({
            @Result(column = "id",property = "userId"),
            @Result(column = "nickname",property = "nickName"),
            @Result(column = "sex",property = "sex"),
    })
    public Map<String,Object> queryReleaseUserDetail(String userId);

    @Select("select id,audit_time,`desc` from ADMIN_AUDIT where shop_id=#{shopsId}")
    @Results({
            @Result(column = "id",property = "auditId"),
            @Result(column = "audit_time",property = "auditTime",typeHandler = EmptyStringIfNull.class,javaType = String.class,jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "desc",property = "desc")
    })
    public Map<String,Object> queryAuditsRecordDetail(String shopsId);

}
