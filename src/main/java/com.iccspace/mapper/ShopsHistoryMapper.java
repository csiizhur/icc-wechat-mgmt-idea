package com.iccspace.mapper;

import com.iccspace.controller.model.ShopsHistoryRequest;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/21.
 */
public interface ShopsHistoryMapper {

    //@SelectProvider
    @Select("<script>select sh.ID,sh.ESTATES_TYPE,s.ADDRESS,s.SHOP_SIZE,sh.RENT_FEE,sh.FLOOR,sh.CREATE_TIME,sh.MOBILEPHONE " +
            "from SHOPS s join SHOPS_HISTORY sh on s.historyid=sh.id " +
            "where sh.release_type=#{releaseType} and sh.deleted=0 <if test=\"estatesType !=null \"> and sh.estates_type=#{estatesType} </if></script>")
    @Results({
            @Result(column = "ID",property = "shopsId"),
            @Result(column = "ESTATES_TYPE",property = "estatesType"),
            @Result(column = "ADDRESS",property = "shopsAddress"),
            @Result(column = "SHOP_SIZE",property = "shopSize"),
            @Result(column = "RENT_FEE",property = "rentFee"),
            @Result(column = "FLOOR",property = "floor"),
            @Result(column = "CREATE_TIME",property = "releaseDate"),
            @Result(column = "MOBILEPHONE",property = "mobilePhone")
    })
    public List<Map<String,Object>> queryShopsHistoryList(ShopsHistoryRequest shopsHistoryRequest);

    @Select("select ID,OSS_URL from SHOPS_PHOTOS_INFO where SHOPSID=#{shopsId}")
    @Results({
            @Result(column = "ID",property = "photosId"),
            @Result(column = "OSS_URL",property = "ossUrl")
    })
    public List<Map<String,Object>> queryShopsPhotoListByShopsId(String shopsId);

    @Select("select ID,SHOP_ID,AUDIT_TIME,DESC,ADMIN_ID from ADMIN_AUDIT where SHOP_ID=#{shopsId}")
    @Results({
            @Result(column = "ID",property = "auditId"),
            @Result(column = "SHOP_ID",property = "shopsId"),
            @Result(column = "AUDIT_TIME",property = "auditTime"),
            @Result(column = "DESC",property = "desc"),
            @Result(column = "ADMIN_ID",property = "adminId"),

    })
    public List<Map<String,Object>> queryShopsAuditListByShopsId(String shopsId);

    @Select("select sh.ID,sh.estates_type,sh.create_time,s.shop_size,sh.mobilephone,sh.floor,s.address,sh.rent_fee " +
            "from SHOPS s join SHOPS_HISTORY sh on s.historyid=sh.id where sh.deleted=0")
    @Results({
            @Result(column = "ID",property = "shopsId"),
            @Result(column = "estates_type",property = "estatesType"),
            @Result(column = "create_time",property = "releaseDate"),
            @Result(column = "shop_size",property = "shopSize"),
            @Result(column = "mobilephone",property = "mobilePhone"),
            @Result(column = "floor",property = "floor"),
            @Result(column = "address",property = "shopsAddress"),
            @Result(column = "rent_fee",property = "rentFee")
    })
    public Map<String,Object> queryShopsDetailByShopsId(String shopsId);

    @Update("update SHOPS_HISTORY set estates_type=#{estatesType}," +
            "mobilephone=#{mobilePhone}," +
            "floor=#{floor}," +
            "rent_fee=#{rentFee}" +
            "where id=#{shopsId}")
    public int updateHistoryShops(String shopsId);

    @Update("update SHOPS set shop_size=#{shopSize}," +
            "address=#{shopsAddress}" +
            "where historyid=#{shopsId}")
    public int updateBaseShops(String shopsId);
}
