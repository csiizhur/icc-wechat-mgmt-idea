package com.iccspace.mapper;

import com.iccspace.controller.model.RentsAddModel;
import com.iccspace.controller.model.RentsEditModel;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface RentUsersMapper {
	@Select("select RENT_ID,NICKNAME,EXPECTSHOPSIZE_MIN,EXPECTSHOPSIZE_MAX,EXPECTRENTFEE_MIN,EXPECTRENTFEE_MAX,MOBILEPHONE," +
			"BUSINESSTYPE,CREATE_TIME from RENT_SHOPS rs left join USER u on u.id=rs.userid" +
			"where RELEASE_TYPE=2 and DELETED=0")
	@Results({
			//@Result(id = true, column = "id", property = "userId"),
			@Result(column = "RENT_ID", property = "rentId"),
			@Result(column = "NICKNAME", property = "nickName"),
			@Result(column = "EXPECTSHOPSIZE_MIN", property = "expectShopSizeMin"),
			@Result(column = "EXPECTSHOPSIZE_MAX", property = "expectShopSizeMax"),
			@Result(column = "EXPECTRENTFEE_MIN", property = "expectRentFeeMin"),
			@Result(column = "EXPECTRENTFEE_MAX", property = "expectRentFeeMax"),
			@Result(column = "MOBILEPHONE", property = "mobilePhone"),
			@Result(column = "BUSINESSTYPE", property = "businessType"),
			@Result(column = "CREATE_TIME", property = "releaseDate")
	})
	public List<Map<String,Object>> queryRentShopsList();

	@Select("select ID,RENT_ID,AUDIT_TIME,DESC,ADMIN_ID from ADMIN_AUDIT where RENT_ID=#{rentId}")
	@Results({
			@Result(column = "ID",property = "auditId"),
			@Result(column = "RENT_ID",property = "rentId"),
			@Result(column = "AUDIT_TIME",property = "auditTime"),
			@Result(column = "DESC",property = "desc"),
			@Result(column = "ADMIN_ID",property = "adminId"),

	})
	public List<Map<String,Object>> queryRentShopsAuditsListByRentId(String rentId);

	@Select("select RENT_ID,NICKNAME,EXPECTSHOPSIZE_MIN,EXPECTSHOPSIZE_MAX,EXPECTRENTFEE_MIN,EXPECTRENTFEE_MAX,MOBILEPHONE," +
			"BUSINESSTYPE,CREATE_TIME from RENT_SHOPS rs left join USER u on u.id=rs.userid" +
			"where RELEASE_TYPE=4 and DELETED=0 and rent_id=#{rentId}")
	@Results({
			//@Result(id = true, column = "id", property = "userId"),
			@Result(column = "RENT_ID", property = "rentId"),
			@Result(column = "NICKNAME", property = "nickName"),
			@Result(column = "EXPECTSHOPSIZE_MIN", property = "expectShopSizeMin"),
			@Result(column = "EXPECTSHOPSIZE_MAX", property = "expectShopSizeMax"),
			@Result(column = "EXPECTRENTFEE_MIN", property = "expectRentFeeMin"),
			@Result(column = "EXPECTRENTFEE_MAX", property = "expectRentFeeMax"),
			@Result(column = "MOBILEPHONE", property = "mobilePhone"),
			@Result(column = "BUSINESSTYPE", property = "businessType"),
			@Result(column = "CREATE_TIME", property = "releaseDate")
	})
	public Map<String,Object> queryRentShopsDetailById(String rentId);


	@Update("update RENT_SHOPS set create_time=#{releaseDate},expectshopsize_min=#{expectShopSizeMin},expectshopsize_max=#{expectShopSizeMax}," +
			"expectrentfee_min=#{expectRentFeeMin},expectrentfee_max=#{expectRentFeeMax},mobilephone=#{mobilePhone},businesstype=#{businessType}," +
			"address=#{expectAddress}")
	public int updateRentShops(RentsEditModel rentsEditModel);

	@Insert("insert into `wechat-server-db`.RENT_SHOPS (rent_id,address,businesstype,mobilephone,create_time," +
			"expectshopsize_min,expectshopsize_max,expectrentfee_min,expectrentfee_max,userid) "
			+ "values(#{rentId},#{expectAddress},#{businessType},#{mobilePhone},now()," +
			"#{expectShopSizeMin},#{expectShopSizeMax},#{expectRentFeeMin},#{expectRentFeeMax},#{userId})")
    //@SelectKey(statement="select replace(uuid(),'-','') from dual", keyProperty="rentId", before=true, resultType=String.class)
    public int insertRentShops(RentsAddModel rentsAddModel);
}
