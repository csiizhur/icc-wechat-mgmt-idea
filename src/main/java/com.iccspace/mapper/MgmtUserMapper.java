package com.iccspace.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import com.iccspace.vo.User;



public interface MgmtUserMapper {
	@Select("select * from mgmt_user where username = #{userName} and password=#{password}")
	@Results({
		@Result(id = true, column = "id", property = "userId"),
		@Result(column = "username", property = "userName"),
		@Result(column = "headimage", property = "headImage")
	})
    public User queryMgmtUser(User user);
	@Select("select * from mgmt_user where username = #{userName}")
	@Results({
		@Result(id = true, column = "id", property = "userId"),
		@Result(column = "username", property = "userName"),
		@Result(column = "headimage", property = "headImage"),
		@Result(column ="password",property="password")
	})
	public User queryMgmtUserByUserName(String userName);
	
	@Insert("insert into `wechat-mgmt-db`.mgmt_user (id,username,password,headimage,create_time,create_userid,status) "
			+ "values(#{userId},#{userName},#{password},#{headImage},now(),'system',0)")  
    @SelectKey(statement="select replace(uuid(),'-','') from dual", keyProperty="userId", before=true, resultType=String.class)  
    public int insertMgmtUsers(User user);
}
