package com.iccspace.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iccspace.aliyun.FileUtil;
import com.iccspace.aliyun.OssClientUtil;
import com.iccspace.controller.model.*;
import com.iccspace.core.Constants;
import com.iccspace.mapper.AuditMapper;
import com.iccspace.mapper.ShopsHistoryMapper;
import com.iccspace.mapper.ShopsPhotoMapper;
import com.iccspace.service.ShopsHistoryService;
import com.iccspace.token.ResultMsg;
import com.iccspace.token.ResultStatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.*;

/**
 * Created by Administrator on 2016/12/21.
 */
@Service
public class ShopsHistoryServiceImpl implements ShopsHistoryService{

    @Autowired
    private ShopsHistoryMapper shopsHistoryMapper;

    @Autowired
    private AuditMapper auditMapper;

    @Autowired
    private ShopsPhotoMapper shopsPhotoMapper;

    @Override
    public ResultMsg shopsList(ShopsListRequest shopsListRequest) {

        PageHelper.startPage(1,20);
        List<Map<String,Object>> list = shopsHistoryMapper.queryShopsHistoryList(shopsListRequest);
        PageInfo page= new PageInfo(list);
        ResultMsg resultMsg = new ResultMsg(Constants.OPERATOR_DB_SUCCESS,"shops list", JSONArray.toJSON(page));

        return resultMsg;
    }

    @Override
    public ResultMsg photosList(String shopsId){
        List<Map<String,Object>> list=shopsHistoryMapper.queryShopsPhotoListByShopsId(shopsId);
        ResultMsg resultMsg = new ResultMsg(Constants.OPERATOR_DB_SUCCESS,"photos list",list);
        return resultMsg;
    }

    @Override
    public ResultMsg photosEdit(MultipartFile[] files,String[] photosId,String shopsId){
        ResultMsg resultMsg;
        JSONArray jsonArray = new JSONArray();

        if(files!=null && files.length>0){

            for(int i = 0;i<files.length;i++) {
                Map map = new HashMap();

                MultipartFile file = files[i];
                String os_name = System.getProperty("os.name");
                String upload_path;
                if (os_name != null && os_name.toLowerCase().indexOf("linux") > -1) {
                    upload_path = Constants.UPLOAD_PATH_LINUX;
                } else {
                    upload_path = Constants.UPLOAD_PATH_WIN;

                }
                String originalName = file.getOriginalFilename();

                String fix = originalName.substring(originalName.lastIndexOf(".") + 1);
                long fileName = Calendar.getInstance().getTimeInMillis();
                String uri = FileUtil.generateFolderPathByTime(fileName);
                String targetPath = upload_path + uri + "/" + fileName + "." + fix;
                File mk = new File(upload_path + uri);
                File targetFile = new File(targetPath);

                if (!mk.exists()) {
                    mk.mkdirs();
                }

                String oss_url = null;
                try {
                    file.transferTo(targetFile);

                    OssClientUtil ossClientUtil = new OssClientUtil();
                    String oss_photo_name = ossClientUtil.uploadPhoto(targetPath);
                    oss_url = ossClientUtil.getPhotoUrl(oss_photo_name);

                    map.put("local_url",targetPath);
                    map.put("oss_url",oss_url);
                    jsonArray.add(map);

                    PhotosAddModel photosAddModel = new PhotosAddModel();
                    photosAddModel.setShopsId(shopsId);
                    photosAddModel.setOssUrl(oss_url);
                    shopsPhotoMapper.insertShopsPhoto(photosAddModel);

                }catch(MultipartException multipartException){
                    resultMsg = new ResultMsg(ResultStatusCode.MULTIPART_EXCEPTION.getErrcode(),ResultStatusCode.MULTIPART_EXCEPTION.getErrmsg(),null);
                    return resultMsg;
                }catch (Exception e) {
                    e.printStackTrace();
                    resultMsg = new ResultMsg(ResultStatusCode.SYSTEM_EXCEPTION.getErrcode(),ResultStatusCode.SYSTEM_EXCEPTION.getErrmsg(),null);
                    return resultMsg;
                }
            }
        }
        if(photosId.length>0){
            shopsPhotoMapper.updateBatchShopsPhotos(photosId);
        }
        resultMsg =new ResultMsg(ResultStatusCode.OK.getErrcode(),ResultStatusCode.OK.getErrmsg(),jsonArray);
        return resultMsg;
    }

    @Override
    public Map photosAdd(MultipartFile file,String shopsId){
        Map map = new HashMap();

        String os_name = System.getProperty("os.name");
        String upload_path;
        if(os_name!=null && os_name.toLowerCase().indexOf("linux")>-1){
            upload_path = Constants.UPLOAD_PATH_LINUX;
        }else{
            upload_path = Constants.UPLOAD_PATH_WIN;

        }
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
            map.put("message",ResultStatusCode.MULTIPART_EXCEPTION.getErrmsg());
            return map;
        } catch(Exception e) {
            e.printStackTrace();
            map.put("message",ResultStatusCode.SYSTEM_EXCEPTION.getErrmsg());
            return map;
        }
        PhotosAddModel photosAddModel = new PhotosAddModel();
        photosAddModel.setOssUrl(oss_url);
        photosAddModel.setShopsId(shopsId);

        int p_db = shopsPhotoMapper.insertShopsPhoto(photosAddModel);

        if(p_db!=Constants.AFFECT_DB_ROWS_1){
            map.put("code",Constants.OPERATOR_DB_ERROR);
            map.put("message",Constants.OPERATOR_DB_ERROR_MESSAGE);
            return map;
        }
        map.put("local_url",targetPath);

        map.put("oss_url",oss_url);

        return map;
    }

    @Override
    public ResultMsg auditsList(String shopsId){
        List<Map<String,Object>> list=shopsHistoryMapper.queryShopsAuditListByShopsId(shopsId);
        ResultMsg resultMsg = new ResultMsg(Constants.OPERATOR_DB_SUCCESS,"audits list",list);
        return resultMsg;
    }

    @Override
    public ResultMsg shopsDetail(String shopsId) {
        ResultMsg resultMsg;
        Map<String,Object> map = shopsHistoryMapper.queryShopsDetailByShopsId(shopsId);
        resultMsg = new ResultMsg(Constants.OPERATOR_DB_SUCCESS,"shops detail",map);
        return resultMsg;
    }

    @Override
    public ResultMsg shopsEdit(ShopsEditModel shopsEditModel) {
        ResultMsg resultMsg;
        int base_result = shopsHistoryMapper.updateBaseShops(shopsEditModel);
        int history_result = shopsHistoryMapper.updateHistoryShops(shopsEditModel);
        if(base_result!=Constants.AFFECT_DB_ROWS_1){
            resultMsg = new ResultMsg(Constants.OPERATOR_DB_ERROR,"update base error",null);
        }
        if(history_result!=Constants.AFFECT_DB_ROWS_1){
            resultMsg = new ResultMsg(Constants.OPERATOR_DB_ERROR,"update history error",null);
        }
        return new ResultMsg(Constants.OPERATOR_DB_SUCCESS,"edit success",null);
    }

    @Override
    public ResultMsg shopsAdd(ShopsAddModel shopsAddModel){
        ResultMsg resultMsg;

        String s = UUID.randomUUID().toString();
        //去掉“-”符号
        String baseShopsId = s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);

        UUID uuid = UUID.randomUUID();
        String shopsId = uuid.toString().replace("-","");

        shopsAddModel.setBaseSHopsId(baseShopsId);
        shopsAddModel.setShopsId(shopsId);

        int s_db=shopsHistoryMapper.insertBaseShops(shopsAddModel);
        int b_db=shopsHistoryMapper.insertHistoryShops(shopsAddModel);

        String desc = shopsAddModel.getDesc();
        if(!StringUtils.isEmpty(desc)){
            AuditAddModel auditAddModel = new AuditAddModel(shopsAddModel.getDesc(),shopsAddModel.getShopsId(),shopsAddModel.getAdminId());
            auditAddModel.setAuditTime(shopsAddModel.getAuditTime());
            int a_db=auditMapper.insertShopsAudit(auditAddModel);
            if(a_db!=Constants.AFFECT_DB_ROWS_1){
                resultMsg = new ResultMsg(Constants.OPERATOR_DB_ERROR,"insert audit error",null);
                return resultMsg;
            }
        }

        if(s_db!=Constants.AFFECT_DB_ROWS_1 && b_db!=Constants.AFFECT_DB_ROWS_1){
            resultMsg = new ResultMsg(Constants.OPERATOR_DB_ERROR,"insert shops error",null);
            return resultMsg;
        }
        return new ResultMsg(Constants.OPERATOR_DB_SUCCESS,"insert data success",null);
    }
}
