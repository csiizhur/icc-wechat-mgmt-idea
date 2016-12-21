package com.iccspace.controller;

import com.iccspace.controller.model.ShopsHistoryRequest;
import com.iccspace.service.ShopsHistoryService;
import com.iccspace.token.ResultMsg;
import com.iccspace.token.ResultStatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2016/12/21.
 */
@RestController
@RequestMapping("shops")
public class ShopsHistoryController {

    @Autowired
    private ShopsHistoryService shopsHistoryService;

    /**
     * cuzhu list
     * @param estatesType
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "cuzuList" ,produces = "application/json;charset=UTF-8")
    public Object cuzuShopsList(String estatesType){
        ResultMsg resultMsg;
        ShopsHistoryRequest shopsHistoryRequest = new ShopsHistoryRequest();
        shopsHistoryRequest.setReleaseType(1);

        if(!StringUtils.isEmpty(estatesType)){
            shopsHistoryRequest.setEstatesType(estatesType);
        }
        resultMsg = shopsHistoryService.shopsList(shopsHistoryRequest);
        return resultMsg;
    }

    /**
     * photos list
     * @param shopsId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "photosList",produces = "application/json;charset=UTF-8")
    public Object propertyPhotosList(String shopsId){
        ResultMsg resultMsg;
        resultMsg=shopsHistoryService.photosList(shopsId);
        return resultMsg;
    }

    /**
     * audits list
     * @param shopsId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "auditsList",produces = "application/json;charset=UTF-8")
    public Object propertyAuditsList(String shopsId){
        ResultMsg resultMsg;
        resultMsg=shopsHistoryService.auditsList(shopsId);
        return resultMsg;
    }

    /**
     * shops deatil
     * @param shopsId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "shopsDetail",produces = "application/json;charset=UTF-8")
    public Object shopsDetail(String shopsId){
        ResultMsg resultMsg;
        if(StringUtils.isEmpty(shopsId)){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_SHOPSID.getErrcode(),ResultStatusCode.INVALID_SHOPSID.getErrmsg(),null);
        }
        resultMsg = shopsHistoryService.shopsDetail(shopsId);
        return resultMsg;
    }

    @RequestMapping(method = RequestMethod.POST,value = "shopsEdit")
    public Object editShops(String shopsId){

    }
}
