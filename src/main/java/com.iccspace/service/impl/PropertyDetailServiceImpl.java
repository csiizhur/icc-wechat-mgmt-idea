package com.iccspace.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.iccspace.core.Constants;
import com.iccspace.mapper.PropertyDetailMapper;
import com.iccspace.service.PropertyDetailService;
import com.iccspace.token.ResultMsg;
import com.iccspace.token.ResultStatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Override
    public ResultMsg propertyDetails(String shopsId, String releaseUserId) {
        ResultMsg resultMsg;

        JSONArray jsonArray =new JSONArray();

        Map<String,Object> propertyDetail = propertyDetailMapper.queryShopsDetail(shopsId);
        if(!StringUtils.isEmpty(releaseUserId)){
            Map<String,Object> releaseUserDetail = propertyDetailMapper.queryReleaseUserDetail(releaseUserId);
            jsonArray.add(releaseUserDetail);
        }
        Map<String,Object> auditDetail = propertyDetailMapper.queryAuditsRecordDetail(shopsId);
        jsonArray.add(propertyDetail);
        jsonArray.add(auditDetail);
        if(propertyDetail.size()>0){
            Integer status = (Integer) propertyDetail.get("deleted");
            if(Constants.DELETED_1==status){
                resultMsg = new ResultMsg(ResultStatusCode.INVALID_PROPERTY_STATUS.getErrcode(),ResultStatusCode.INVALID_PROPERTY_STATUS.getErrmsg(),jsonArray);
                return resultMsg;
            }
        }
        resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),ResultStatusCode.OK.getErrmsg(),jsonArray);
        return resultMsg;
    }
}
