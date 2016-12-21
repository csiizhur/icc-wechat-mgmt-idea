package com.iccspace.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.iccspace.controller.model.ShopsHistoryRequest;
import com.iccspace.core.Constants;
import com.iccspace.mapper.ShopsHistoryMapper;
import com.iccspace.service.ShopsHistoryService;
import com.iccspace.token.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/21.
 */
@Service
public class ShopsHistoryServiceImpl implements ShopsHistoryService{

    @Autowired
    private ShopsHistoryMapper shopsHistoryMapper;

    @Override
    public ResultMsg shopsList(ShopsHistoryRequest shopsHistoryRequest) {

        List<Map<String,Object>> list = shopsHistoryMapper.queryShopsHistoryList(shopsHistoryRequest);

        ResultMsg resultMsg = new ResultMsg(Constants.OPERATOR_DB_SUCCESS,"shops list", JSONArray.toJSON(list));

        return resultMsg;
    }

    @Override
    public ResultMsg photosList(String shopsId){
        List<Map<String,Object>> list=shopsHistoryMapper.queryShopsPhotoListByShopsId(shopsId);
        ResultMsg resultMsg = new ResultMsg(Constants.OPERATOR_DB_SUCCESS,"photos list",list);
        return resultMsg;
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
}
