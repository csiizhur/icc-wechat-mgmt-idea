package com.iccspace.mapper;

import com.iccspace.controller.model.AuditAddModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectKey;

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
}
