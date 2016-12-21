package com.iccspace.service;

import com.iccspace.controller.model.ShopsHistoryRequest;
import com.iccspace.token.ResultMsg;

/**
 * Created by Administrator on 2016/12/21.
 */
public interface ShopsHistoryService {

    //shops list
    public ResultMsg shopsList(ShopsHistoryRequest shopsHistoryRequest);
    //photos list
    public ResultMsg photosList(String shopsId);
    //audits list
    public ResultMsg auditsList(String shopsId);
    //detail
    public ResultMsg shopsDetail(String shopsId);
}
