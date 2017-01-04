package com.iccspace.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iccspace.controller.model.AuditAddModel;
import com.iccspace.controller.model.RentsAddModel;
import com.iccspace.controller.model.RentsEditModel;
import com.iccspace.core.Constants;
import com.iccspace.mapper.AuditMapper;
import com.iccspace.mapper.RentUsersMapper;
import com.iccspace.service.RentUserService;
import com.iccspace.token.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2016/12/22.
 */
@Service
public class RentUserServiceImpl implements RentUserService {

    @Autowired
    private RentUsersMapper rentUsersMapper;

    @Autowired
    private AuditMapper auditMapper;

    @Override
    public ResultMsg rentsList(Integer pageNum) {
        ResultMsg resultMsg;
        PageHelper.startPage(pageNum,Constants.PAGE_SIZE);
        List<Map<String,Object>> list = rentUsersMapper.queryRentShopsList();
        PageInfo pageInfo = new PageInfo(list);
        resultMsg = new ResultMsg(Constants.OPERATOR_DB_SUCCESS,"rent list",pageInfo);

        return resultMsg;
    }

    @Override
    public ResultMsg auditsList(String rentId) {
        ResultMsg resultMsg;
        List<Map<String,Object>> list = rentUsersMapper.queryRentShopsAuditsListByRentId(rentId);
        resultMsg = new ResultMsg(Constants.OPERATOR_DB_SUCCESS,"",list);
        return resultMsg;
    }

    @Override
    public ResultMsg rentsDetail(String rentId){
        ResultMsg resultMsg;
        Map<String,Object> map = rentUsersMapper.queryRentShopsDetailById(rentId);
        resultMsg = new ResultMsg(Constants.OPERATOR_DB_SUCCESS,"",map);
        return resultMsg;
    }

    @Override
    public ResultMsg rentsEdit(RentsEditModel rentsEditModel) {
        ResultMsg resultMsg;
        int db= rentUsersMapper.updateRentShops(rentsEditModel);
        if(db!=Constants.AFFECT_DB_ROWS_1){
            resultMsg = new ResultMsg(Constants.OPERATOR_DB_ERROR,"update rents fail",null);
            return resultMsg;
        }
        return new ResultMsg(Constants.OPERATOR_DB_SUCCESS,"update rents success",null);
    }

    @Override
    public ResultMsg rentsAdd(RentsAddModel rentsAddModel) {
        ResultMsg resultMsg;
        UUID uuid = UUID.randomUUID();
        String rentId = uuid.toString().replace("-","");
        rentsAddModel.setRentId(rentId);
        int db = rentUsersMapper.insertRentShops(rentsAddModel);

        String desc = rentsAddModel.getDesc();
        if(!StringUtils.isEmpty(desc)){
            AuditAddModel auditAddModel = new AuditAddModel(rentsAddModel.getDesc(),rentsAddModel.getRentId(),rentsAddModel.getAdminId());
            auditAddModel.setAuditTime(rentsAddModel.getAuditTime());
            int db_audit = auditMapper.insertRentsAudit(auditAddModel);

            if(db_audit!=Constants.AFFECT_DB_ROWS_1){
                resultMsg = new ResultMsg(Constants.OPERATOR_DB_ERROR,"add audit fail",null);
                return resultMsg;
            }
        }

        if(db!=Constants.AFFECT_DB_ROWS_1){
            resultMsg = new ResultMsg(Constants.OPERATOR_DB_ERROR,"add rents fail",null);
            return resultMsg;
        }

        return new ResultMsg(Constants.OPERATOR_DB_SUCCESS,"add rents success",null);
    }
}
