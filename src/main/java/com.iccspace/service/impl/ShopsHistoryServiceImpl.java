package com.iccspace.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.Constant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iccspace.controller.model.AuditAddModel;
import com.iccspace.controller.model.ShopsAddModel;
import com.iccspace.controller.model.ShopsEditModel;
import com.iccspace.controller.model.ShopsListRequest;
import com.iccspace.core.Constants;
import com.iccspace.mapper.AuditMapper;
import com.iccspace.mapper.ShopsHistoryMapper;
import com.iccspace.service.ShopsHistoryService;
import com.iccspace.token.ResultMsg;
import com.iccspace.token.ResultStatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2016/12/21.
 */
@Service
public class ShopsHistoryServiceImpl implements ShopsHistoryService{

    @Autowired
    private ShopsHistoryMapper shopsHistoryMapper;

    @Autowired
    private AuditMapper auditMapper;

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
    public ResultMsg photosUpload(MultipartFile multipartFile){
        ResultMsg resultMsg;
        return new ResultMsg(ResultStatusCode.OK.getErrcode(),ResultStatusCode.OK.getErrmsg(),null);
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

        AuditAddModel auditAddModel = new AuditAddModel(shopsAddModel.getDesc(),shopsAddModel.getShopsId(),"adminids");
        int a_db=auditMapper.insertShopsAudit(auditAddModel);

        if(s_db!=Constants.AFFECT_DB_ROWS_1 && b_db!=Constants.AFFECT_DB_ROWS_1){
            resultMsg = new ResultMsg(Constants.OPERATOR_DB_ERROR,"insert db error",null);
            return resultMsg;
        }
        return new ResultMsg(Constants.OPERATOR_DB_SUCCESS,"",null);
    }
}
