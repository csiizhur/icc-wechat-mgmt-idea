package com.iccspace.mapper;

import com.iccspace.controller.model.PhotosAddModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

/**
 * Created by zhur on 2016/12/28.
 * @author:zhur
 * @description:
 * @date:create in 2016-12-28-11:46
 */
public interface ShopsPhotoMapper {

    @Insert("insert into SHOPS_PHOTOS_INFO(ID,SHOPSID,OSS_URL,DELETED,OSS_UPLOAD_FLG) " +
            "values(#{photoId},#{shopsId},#{ossUrl},0,1)")
    @SelectKey(statement = "select replace(uuid(),'-','') from dual",resultType = String.class,before = true,keyProperty = "photoId")
    public int insertShopsPhoto(PhotosAddModel photosAddModel);


    @Update("update SHOPS_PHOTOS_INFO set DELETED=1 where id=#{photoId}")
    public int updateShopsPhoto(String photoId);

    @Update("<script>" +
            "<foreach collection=\"photosId\" item=\"photoId\" index=\"index\" separator=\";\">" +
            "update SHOPS_PHOTOS_INFO" +
            "<set>" +
            "   deleted=1" +
            "</set>" +
            "where id=#{photoId}" +
            "</foreach>" +
            "</script>")
    public int updateBatchShopsPhotos(@Param("photosId") String[] photosId);
}
