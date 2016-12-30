package com.iccspace.mapper;

import com.iccspace.config.EmptyStringIfNull;
import com.iccspace.controller.model.PropertyEditModel;
import org.apache.ibatis.annotations.*;
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

    /**
     * 物业基础信息
     * @param shopsId
     * @return
     */
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

    /**
     * 发布用户
     * @param userId
     * @return
     */
    @Select("select id,nickname,sex from USER where id=#{userId}")
    @Results({
            @Result(column = "id",property = "userId"),
            @Result(column = "nickname",property = "nickName"),
            @Result(column = "sex",property = "sex"),
    })
    public Map<String,Object> queryReleaseUserDetail(String userId);

    /**
     * 带看记录
     * @param shopsId
     * @return
     */
    @Select("select id,audit_time,`desc` from ADMIN_AUDIT where shop_id=#{shopsId}")
    @Results({
            @Result(column = "id",property = "auditId"),
            @Result(column = "audit_time",property = "auditTime",typeHandler = EmptyStringIfNull.class,javaType = String.class,jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "desc",property = "desc")
    })
    public Map<String,Object> queryAuditsRecordDetail(String shopsId);


    /**
     * 备注
     * @param shopsId
     * @return
     */
    @Select("select id,description from REMARKS where deleted=0 and shop_id=#{shopsId}")
    @Results({
            @Result(column = "id",property = "remarkId"),
            @Result(column = "description",property = "description")
    })
    public Map<String,Object> queryRemarksRecordDetail(String shopsId);

    /**
     * 合同
     * @param shopsId
     * @return
     */
    @Select("select id,oss_url,description from CONTRACTS where deleted=0 and shop_id=#{shopsId}")
    @Results({
            @Result(column = "id",property = "contractId"),
            @Result(column = "oss_url",property = "contractOssUrl"),
            @Result(column = "description",property = "description")
    })
    public Map<String,Object> queryContractsRecordDetail(String shopsId);

    /**
     * 添加带看记录
     * @param propertyEditModel
     * @return
     */
    @Insert("insert into ADMIN_AUDIT(id,shop_id,audit_time,desc,admin_id,create_time) values(#{auditId},#{shopsId},#{auditTime},#{auditDesc},#{adminId},now())")
    @SelectKey(statement = "select replace(uuid(),'-','') from dual",before = true,resultType = String.class,keyProperty = "auditId")
    public int insertAuditRecord(PropertyEditModel propertyEditModel);

    /**
     * 添加合同文件
     * @param propertyEditModel
     * @return
     */
    @Insert("insert into CONTRACTS(id,shop_id,oss_url,create_time,admin_id) " +
            "values(#{contractId},#{shopsId},#{ossUrl},now(),#{adminId})")
    @SelectKey(statement = "select replace(uuid(),'-','') from dual",before = true,resultType = String.class,keyProperty = "contractId")
    public int insertContractRecord(PropertyEditModel propertyEditModel);

}
