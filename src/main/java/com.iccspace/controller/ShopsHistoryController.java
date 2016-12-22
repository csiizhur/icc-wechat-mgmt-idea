package com.iccspace.controller;

import com.iccspace.controller.model.ShopsAddModel;
import com.iccspace.controller.model.ShopsEditModel;
import com.iccspace.controller.model.ShopsListRequest;
import com.iccspace.service.ShopsHistoryService;
import com.iccspace.token.ResultMsg;
import com.iccspace.token.ResultStatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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
        ShopsListRequest shopsListRequest = new ShopsListRequest();
        shopsListRequest.setReleaseType(1);

        if(!StringUtils.isEmpty(estatesType)){
            shopsListRequest.setEstatesType(estatesType);
        }
        resultMsg = shopsHistoryService.shopsList(shopsListRequest);
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

    /**
     * shops edit
     * @param shopsEditModel
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "shopsEdit",produces = "application/json;charset=UTF-8")
    public Object editShops(@RequestBody ShopsEditModel shopsEditModel){
        ResultMsg resultMsg;
        resultMsg = shopsHistoryService.shopsEdit(shopsEditModel);
        return resultMsg;
    }

    /**
     * photos upload
     * @param multipartFile
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value="photosUpload",consumes = "multipart/form-data")
    public Object uploadPhotos(MultipartFile multipartFile, HttpServletRequest request){
        ResultMsg resultMsg;
        if(multipartFile.isEmpty()){
            resultMsg = new ResultMsg(3333,"",null);
           return  resultMsg;
        }
        String path = request.getSession().getServletContext().getRealPath("upload");
        String fileName = multipartFile.getOriginalFilename();
        File targetFile = new File(path,fileName);
        //目录不存在，则创建目录
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        //保存
        try {
            multipartFile.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map map = new HashMap();
        map.put("url",request.getContextPath()+"/upload"+fileName);
        resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),"",map);
        return resultMsg;
    }

    /**
     * shops add
     * @param shopsAddModel
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "shopsAdd",produces = "application/json;charset=UTF-8")
    public Object addShops(@RequestBody ShopsAddModel shopsAddModel){
        ResultMsg resultMsg;
        String estatesType = shopsAddModel.getEstatesType();
        String releaseDate = shopsAddModel.getReleaseDate();
        Double shopSize = shopsAddModel.getShopSize();
        String mobilePhone = shopsAddModel.getMobilePhone();
        String floor = shopsAddModel.getFloor();
        String shopsAddress = shopsAddModel.getShopsAddress();
        BigDecimal rentFee = shopsAddModel.getRentFee();
        Integer releaseType = shopsAddModel.getReleaseType();
        String desc = shopsAddModel.getDesc();

        //时间转换

        if(StringUtils.isEmpty(estatesType)){
            resultMsg = new ResultMsg(1,"",null);
            return resultMsg;
        }
        resultMsg = shopsHistoryService.shopsAdd(shopsAddModel);
        return resultMsg;
    }
}
