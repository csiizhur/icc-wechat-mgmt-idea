package com.iccspace.service;

import com.iccspace.controller.model.RentsAddModel;
import com.iccspace.controller.model.RentsEditModel;
import com.iccspace.token.ResultMsg;

/**
 * Created by Administrator on 2016/12/22.
 */
public interface RentUserService {

    //rents list
    public ResultMsg rentsList();

    //audits list
    public ResultMsg auditsList(String rentId);

    //rent detail
    public ResultMsg rentsDetail(String rentId);

    //rent update
    public ResultMsg rentsEdit(RentsEditModel rentsEditModel);

    //rent add
    public ResultMsg rentsAdd(RentsAddModel rentsAddModel);
}
