package com.iccspace.service;

import com.iccspace.controller.model.ShopsEditModel;
import com.iccspace.controller.model.ShopsListRequest;
import com.iccspace.token.ResultMsg;

/**
 * Created by Administrator on 2016/12/21.
 */
public interface ShopsHistoryService {

    //shops list
    public ResultMsg shopsList(ShopsListRequest shopsListRequest);
    //photos list
    public ResultMsg photosList(String shopsId);
    //audits list
    public ResultMsg auditsList(String shopsId);
    //detail
    public ResultMsg shopsDetail(String shopsId);
    //edit shops
    public ResultMsg shopsEdit(ShopsEditModel shopsEditModel);
}
