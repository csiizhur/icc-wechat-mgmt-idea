package com.iccspace.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.iccspace.aliyun.FileUtil;
import com.iccspace.aliyun.OssClientUtil;
import com.iccspace.controller.model.PhotosAddModel;
import com.iccspace.controller.model.PropertyEditModel;
import com.iccspace.core.Constants;
import com.iccspace.mapper.PropertyDetailMapper;
import com.iccspace.service.PropertyDetailService;
import com.iccspace.token.ResultMsg;
import com.iccspace.token.ResultStatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhur on 2016/12/29.
 *
 * @author:zhur
 * @description:
 * @date:create in 2016-12-29-16:56
 */
@Service
public class PropertyDetailServiceImpl implements PropertyDetailService{

    @Autowired
    private PropertyDetailMapper propertyDetailMapper;

    /**
     * query detail
     * @param shopsId
     * @param releaseUserId
     * @return
     */
    @Override
    public ResultMsg propertyDetails(String shopsId, String releaseUserId) {
        ResultMsg resultMsg;

        JSONObject jsonObject =new JSONObject();

        Map<String,Object> propertyDetail = propertyDetailMapper.queryShopsDetail(shopsId);
        jsonObject.put("propertyDetail",propertyDetail);

        Map<String,Object> releaseUserDetail = propertyDetailMapper.queryReleaseUserDetail(releaseUserId);
        jsonObject.put("releaseUserDetail",releaseUserDetail);

        Map<String,Object> auditDetail = propertyDetailMapper.queryAuditsRecordDetail(shopsId);
        jsonObject.put("auditDetail",auditDetail);

        Map<String,Object> remarkDetail = propertyDetailMapper.queryRemarksRecordDetail(shopsId);
        jsonObject.put("remarkDetail",remarkDetail);

        Map<String,Object> contractDetail = propertyDetailMapper.queryContractsRecordDetail(shopsId);
        jsonObject.put("contractDetail",contractDetail);

        resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),ResultStatusCode.OK.getErrmsg(),jsonObject);
        return resultMsg;
    }

    /**
     * add contract  audit
     * @param propertyEditModel
     * @return
     */
    @Override
    public ResultMsg propertyEdit(PropertyEditModel propertyEditModel){
        ResultMsg resultMsg;
        Map map = new HashMap();

        String os_name = System.getProperty("os.name");
        String upload_path;
        if(os_name!=null && os_name.toLowerCase().indexOf("linux")>-1){
            upload_path = Constants.UPLOAD_PATH_LINUX;
        }else{
            upload_path = Constants.UPLOAD_PATH_WIN;

        }
        MultipartFile file = propertyEditModel.getContractFile();
        if(file!=null && file.getSize()>0){


            String originalName = file.getOriginalFilename();

            String fix = originalName.substring(originalName.lastIndexOf(".")+1);
            long fileName = Calendar.getInstance().getTimeInMillis();
            String uri = FileUtil.generateFolderPathByTime(fileName);
            String targetPath=upload_path+uri+"/"+fileName+"."+fix;
            File mk= new File(upload_path+ uri);
            File targetFile = new File(targetPath);

            if(!mk.exists()){
                mk.mkdirs();
            }

            String oss_url=null;
            try {
                file.transferTo(targetFile);

                OssClientUtil ossClientUtil = new OssClientUtil();
                String oss_photo_name = ossClientUtil.uploadPhoto(targetPath);
                oss_url = ossClientUtil.getPhotoUrl(oss_photo_name);
            } catch(MultipartException multipartException){
                resultMsg = new ResultMsg(ResultStatusCode.MULTIPART_EXCEPTION.getErrcode(),ResultStatusCode.MULTIPART_EXCEPTION.getErrmsg(),null);
                return resultMsg;
            } catch(Exception e) {
                e.printStackTrace();
                resultMsg = new ResultMsg(ResultStatusCode.SYSTEM_EXCEPTION.getErrcode(),ResultStatusCode.SYSTEM_EXCEPTION.getErrmsg(),null);
                return resultMsg;
            }

            propertyEditModel.setOssUrl(oss_url);
            map.put("local_url",targetPath);

            map.put("oss_url",oss_url);
        }

        int c_db = propertyDetailMapper.insertContractRecord(propertyEditModel);
        int r_db = propertyDetailMapper.insertAuditRecord(propertyEditModel);
        if(c_db!=Constants.AFFECT_DB_ROWS_1){
            resultMsg = new ResultMsg(Constants.OPERATOR_FILE_ERROR,Constants.OPERATOR_FILE_ERROR_MESSAGE,null);
            return resultMsg;
        }

        resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),ResultStatusCode.OK.getErrmsg(),map);

        return resultMsg;
    }
}
