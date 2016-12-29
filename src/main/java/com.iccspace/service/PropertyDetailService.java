package com.iccspace.service;

import com.iccspace.token.ResultMsg;

/**
 * Created by zhur on 2016/12/29.
 *
 * @author:zhur
 * @description:
 * @date:create in 2016-12-29-16:55
 */
public interface PropertyDetailService {

    public ResultMsg propertyDetails(String shopsId,String releaseUserId);
}
