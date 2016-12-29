package com.iccspace.mapper;

import com.iccspace.controller.model.AuditAddModel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

/**
 * Created by Administrator on 2016/12/22.
 */
public interface AuditMapper {

    @Insert("inert into ADMIN_AUDIT(id,shop_id,audit_time,desc,create_time,admin_id) " +
            "values(#{auditId},#{shopsId},#{autitTime},#{desc},now(),#{adminId})")
    @SelectKey(statement = "select replace(uuid(),'-','') from dual" ,before = true,keyProperty = "auditId",resultType = String.class)
    public int insertShopsAudit(AuditAddModel auditAddModel);

    @Insert("inert into ADMIN_AUDIT(id,rent_id,audit_time,desc,create_time,admin_id) " +
            "values(#{auditId},#{rentId},#{autitTime},#{desc},now(),#{adminId})")
    @SelectKey(statement = "select replace(uuid(),'-','') from dual" ,before = true,keyProperty = "auditId",resultType = String.class)
    public int insertRentsAudit(AuditAddModel auditAddModel);




    @Select("select create_time from SHOPS_HISTORY where id='0180bfb0b06111e6b1e8507b9d158519'")
    @Results(
            @Result(column = "create_time",property = "createTime",javaType = String.class,jdbcType = JdbcType.TIMESTAMP)
    )
    public String queryTimestamp();

    @Select("select create_time from SHOPS_HISTORY where id='0180bfb0b06111e6b1e8507b9d158519'")
    @Results(
            @Result(column = "create_time",property = "createTime",javaType = String.class,jdbcType = JdbcType.DATE)
    )
    public String queryDate();
    @Select("select create_time from SHOPS_HISTORY where id='0180bfb0b06111e6b1e8507b9d158519'")
    @Results(
            @Result(column = "create_time",property = "createTime")
    )
    public String queryDateNotByMap();
}
