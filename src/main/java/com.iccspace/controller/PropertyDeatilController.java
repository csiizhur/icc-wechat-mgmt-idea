package com.iccspace.controller;

import com.iccspace.controller.model.PropertyDetailRequest;
import com.iccspace.controller.model.PropertyEditModel;
import com.iccspace.service.PropertyDetailService;
import com.iccspace.token.ResultMsg;
import com.iccspace.token.ResultStatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhur on 2016/12/29.
 *
 * @author:zhur
 * @description:
 * @date:create in 2016-12-29-17:42
 */
@RestController
@RequestMapping("property")
public class PropertyDeatilController {

    @Autowired
    private PropertyDetailService propertyDetailService;

    /**
     * property detail
     * @param propertyDetailRequest
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "detail",produces = "application/json;charset=UTF-8")
    public Object propertyDetails(@RequestBody PropertyDetailRequest propertyDetailRequest){

        ResultMsg resultMsg;
        String shopsId = propertyDetailRequest.getShopsId();
        String releaseUserId = propertyDetailRequest.getReleaseUserId();

        if(StringUtils.isEmpty(shopsId)){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_SHOPSID.getErrcode(),ResultStatusCode.INVALID_SHOPSID.getErrmsg(),null);
            return resultMsg;
        }

        resultMsg = propertyDetailService.propertyDetails(shopsId,releaseUserId);

        return resultMsg;

    }

    /**
     * property edit
     * @param propertyEditModel
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value="edit",consumes = "multipart/form-data")
    public Object propertyEdit(PropertyEditModel propertyEditModel){

        ResultMsg resultMsg;
        String shopsId = propertyEditModel.getShopsId();

        if(StringUtils.isEmpty(shopsId)){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_SHOPSID.getErrcode(),ResultStatusCode.INVALID_SHOPSID.getErrmsg(),null);
            return resultMsg;
        }
        resultMsg = propertyDetailService.propertyEdit(propertyEditModel);

        return resultMsg;
    }
}
