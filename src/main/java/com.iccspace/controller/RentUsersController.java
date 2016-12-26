package com.iccspace.controller;

import com.iccspace.controller.model.RentsAddModel;
import com.iccspace.controller.model.RentsEditModel;
import com.iccspace.service.RentUserService;
import com.iccspace.token.Audience;
import com.iccspace.token.JwtHelper;
import com.iccspace.token.ResultMsg;
import com.iccspace.token.ResultStatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/12/22.
 */
@RestController
@RequestMapping("rents")
public class RentUsersController {

    @Autowired
    private RentUserService rentUserService;

    @Autowired
    private Audience audienceEntity;

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
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_RENTID.getErrcode(),ResultStatusCode.INVALID_RENTID.getErrmsg(),null);
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
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_RENTID.getErrcode(),ResultStatusCode.INVALID_RENTID.getErrmsg(),null);
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
        String address = rentsEditModel.getExpectAddress();
        String min_shopsize = rentsEditModel.getExpectShopSizeMin();
        String min_rentfee = rentsEditModel.getExpectRentFeeMin();
        if(StringUtils.isEmpty(rentId)){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_RENTID.getErrcode(),ResultStatusCode.INVALID_RENTID.getErrmsg(),null);
            return resultMsg;
        }
        if(StringUtils.isEmpty(address)){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_ADDRESS.getErrcode(),ResultStatusCode.INVALID_ADDRESS.getErrmsg(),null);
            return resultMsg;
        }
        if(StringUtils.isEmpty(min_rentfee)){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_RENTFEEMIN.getErrcode(),ResultStatusCode.INVALID_RENTFEEMIN.getErrmsg(),null);
            return resultMsg;
        }
        if(StringUtils.isEmpty(min_shopsize)){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_SHOPSIZEMIN.getErrcode(),ResultStatusCode.INVALID_SHOPSIZEMIN.getErrmsg(),null);
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
    public Object rentsAdd(@RequestBody RentsAddModel rentsAddModel, HttpServletRequest request){
        ResultMsg resultMsg;
        String nickName = rentsAddModel.getNickName();
        String expectShopSizeMin = rentsAddModel.getExpectShopsizeMin();
        String expectRentFeeMin = rentsAddModel.getExpectRentFeeMin();
        String expectAddress = rentsAddModel.getExpectAddress();

        //headers
        String auth = request.getHeader("Authorization");
        if(StringUtils.isEmpty(auth)){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_ADMINID.getErrcode(),ResultStatusCode.INVALID_ADMINID.getErrmsg(),null);
            return resultMsg;
        }
        if ((auth != null) && (auth.length() > 7))
        {
            String HeadStr = auth.substring(0, 6).toLowerCase();
            if (HeadStr.compareTo("bearer") == 0)
            {
                auth = auth.substring(7, auth.length());
                Claims claims = JwtHelper.parseJWT(auth,audienceEntity.getBase64Secret());
                String adminId = claims.get("userid").toString();
                rentsAddModel.setAdminId(adminId);
            }
        }

        /*if(StringUtils.isEmpty(nickName)){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_NICKNAME.getErrcode(),ResultStatusCode.INVALID_NICKNAME.getErrmsg(),null);
            return resultMsg;
        }*/
        if(StringUtils.isEmpty(expectAddress)){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_ADDRESS.getErrcode(),ResultStatusCode.INVALID_ADDRESS.getErrmsg(),null);
            return resultMsg;
        }
        if(StringUtils.isEmpty(expectRentFeeMin)){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_RENTFEEMIN.getErrcode(),ResultStatusCode.INVALID_RENTFEEMIN.getErrmsg(),null);
            return resultMsg;
        }
        if(StringUtils.isEmpty(expectShopSizeMin)){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_SHOPSIZEMIN.getErrcode(),ResultStatusCode.INVALID_SHOPSIZEMIN.getErrmsg(),null);
            return resultMsg;
        }
        resultMsg = rentUserService.rentsAdd(rentsAddModel);
        return resultMsg;
    }
}
