package com.iccspace.service;

import com.iccspace.controller.model.PropertyEditModel;
import com.iccspace.token.ResultMsg;

/**
 * Created by zhur on 2016/12/29.
 *
 * @author:zhur
 * @description:
 * @date:create in 2016-12-29-16:55
 */
public interface PropertyDetailService {

    //property detail
    public ResultMsg propertyDetails(String shopsId,String releaseUserId);

    //add contract audit
    public ResultMsg propertyEdit(PropertyEditModel propertyEditModel);
}
