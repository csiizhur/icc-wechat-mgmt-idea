package com.iccspace.controller;

import com.iccspace.controller.model.RentsAddModel;
import com.iccspace.controller.model.RentsEditModel;
import com.iccspace.service.RentUserService;
import com.iccspace.token.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2016/12/22.
 */
@RestController
@RequestMapping("rents")
public class RentUsersController {

    @Autowired
    private RentUserService rentUserService;

    /**
     * rent list
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "rentsList",produces = "application/json;charset=UTF-8")
    public Object rentShopsList(){
        ResultMsg resultMsg = rentUserService.rentsList();
        return resultMsg;
    }

    /**
     * audits list
     * @param rentId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "auditsList")
    public Object rentsAudisList(String rentId){
        ResultMsg resultMsg;
        if(StringUtils.isEmpty(rentId)){
            resultMsg = new ResultMsg(31,"",null);
            return resultMsg;
        }
        resultMsg = rentUserService.auditsList(rentId);
        return resultMsg;
    }

    /**
     * rents detail
     * @param rentId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "rentsDetail")
    public Object rentsDetail(String rentId){
        ResultMsg resultMsg;
        if(StringUtils.isEmpty(rentId)){
            resultMsg = new ResultMsg(35,"",null);
            return resultMsg;
        }
        resultMsg = rentUserService.rentsDetail(rentId);
        return resultMsg;
    }

    /**
     * rents edit
     * @param rentsEditModel
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "rentsEdit" ,produces = "application/json;charset=UTF-8")
    public Object rentsEdit(@RequestBody RentsEditModel rentsEditModel){
        ResultMsg resultMsg;
        String rentId = rentsEditModel.getRentId();

        if(StringUtils.isEmpty(rentId)){
            resultMsg = new ResultMsg(56,"",null);
            return resultMsg;
        }
        resultMsg = rentUserService.rentsEdit(rentsEditModel);
        return resultMsg;
    }

    /**
     * rents add
     * @param rentsAddModel
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "rentsAdd",produces = "application/json;charset=UTF-8")
    public Object rentsAdd(@RequestBody RentsAddModel rentsAddModel){
        ResultMsg resultMsg;
        resultMsg = rentUserService.rentsAdd(rentsAddModel);
        return resultMsg;
    }
}
