package com.iccspace.controller;

import com.alibaba.fastjson.JSONArray;
import com.iccspace.controller.model.*;
import com.iccspace.core.Constants;
import com.iccspace.mapper.ShopsPhotoMapper;
import com.iccspace.service.ShopsHistoryService;
import com.iccspace.token.Audience;
import com.iccspace.token.JwtHelper;
import com.iccspace.token.ResultMsg;
import com.iccspace.token.ResultStatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private Audience audienceEntity;

    @Autowired
    private ShopsPhotoMapper shopsPhotoMapper;

    /**
     * cuzhu list
     * @param estatesType
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "cuzuList" ,produces = "application/json;charset=UTF-8")
    public Object cuzuShopsList(String estatesType){
        ResultMsg resultMsg;
        ShopsListRequest shopsListRequest = new ShopsListRequest();
        shopsListRequest.setReleaseType(Constants.CHU_ZU);

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
        if(StringUtils.isEmpty(shopsId)){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_SHOPSID.getErrcode(),ResultStatusCode.INVALID_SHOPSID.getErrmsg(),null);
            return resultMsg;
        }
        resultMsg=shopsHistoryService.photosList(shopsId);
        return resultMsg;
    }

    /**
     * upload photo
     * @param files
     * @return
     */
    @Transactional
    @RequestMapping(method = RequestMethod.POST,value = "photosAdd",consumes = "multipart/form-data")
    public Object propertyPhotosAdd(MultipartFile[] files,String shopsId){
        ResultMsg resultMsg;
        JSONArray jsonArray = new JSONArray();

        if(files.length==0){
            resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),"not upload photos",null);
            return resultMsg;
        }
        if(StringUtils.isEmpty(shopsId)){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_SHOPSID.getErrcode(),ResultStatusCode.INVALID_SHOPSID.getErrmsg(),null);
            return resultMsg;
        }
        if(files!=null && files.length>0){

            for(int i = 0;i<files.length;i++){
                MultipartFile file = files[i];

                Map map = new HashMap<String,String>();
                map = shopsHistoryService.photosAdd(file,shopsId);
                jsonArray.add(map);
            }
        }
        //thumbnail
        if(jsonArray.size()>0){
            Map m = (Map)jsonArray.get(0);
            ThumbnailUrlEditModel thumbnailUrlEditModel = new ThumbnailUrlEditModel(shopsId,m.get("oss_url").toString());
            shopsPhotoMapper.updateShopsThumbnailUrl(thumbnailUrlEditModel);
        }
        resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),ResultStatusCode.OK.getErrmsg(),jsonArray);
        return resultMsg;
    }

    /**
     *
     * @param files 添加的照片
     * @param photosId 删除照片的Id
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "photosEdit",consumes = "multipart/form-data")
    public Object propertyPhotosEdit(MultipartFile[] files,String [] photosId,String shopsId){
        ResultMsg resultMsg;
        if(files.length==0 && photosId==null){
            resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),"photo not edit and not upload new photos",null);
            return resultMsg;
        }
        if(StringUtils.isEmpty(shopsId)){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_SHOPSID.getErrcode(),ResultStatusCode.INVALID_SHOPSID.getErrmsg(),null);
            return resultMsg;
        }
        resultMsg = shopsHistoryService.photosEdit(files,photosId,shopsId);
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
        if(StringUtils.isEmpty(shopsId)){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_SHOPSID.getErrcode(),ResultStatusCode.INVALID_SHOPSID.getErrmsg(),null);
            return resultMsg;
        }
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
            return resultMsg;
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
        String shopsId = shopsEditModel.getShopsId();
        String estatesType = shopsEditModel.getEstatesType();
        String address = shopsEditModel.getShopsAddress();

        if(StringUtils.isEmpty(shopsId)){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_SHOPSID.getErrcode(),ResultStatusCode.INVALID_SHOPSID.getErrmsg(),null);
            return resultMsg;
        }
        if(StringUtils.isEmpty(estatesType)){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_ESTATESTYPE.getErrcode(),ResultStatusCode.INVALID_ESTATESTYPE.getErrmsg(),null);
            return resultMsg;
        }
        if(StringUtils.isEmpty(address)){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_ADDRESS.getErrcode(),ResultStatusCode.INVALID_ADDRESS.getErrmsg(),null);
            return resultMsg;
        }
        resultMsg = shopsHistoryService.shopsEdit(shopsEditModel);
        return resultMsg;
    }

    /**
     * shops add
     * @param shopsAddModel
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "shopsAdd",produces = "application/json;charset=UTF-8")
    public Object addShops(HttpServletRequest request,@RequestBody ShopsAddModel shopsAddModel){
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

        String auth = request.getHeader("Authorization");
        if ((auth != null) && (auth.length() > 7))
        {
            String HeadStr = auth.substring(0, 6).toLowerCase();
            if (HeadStr.compareTo("bearer") == 0)
            {
                auth = auth.substring(7, auth.length());
                Claims claims = JwtHelper.parseJWT(auth,audienceEntity.getBase64Secret());
                String adminId = claims.get("userid").toString();
                shopsAddModel.setAdminId(adminId);
            }
        }

        //时间转换
        if(StringUtils.isEmpty(releaseDate)){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_RELEASEDATE.getErrcode(),ResultStatusCode.INVALID_RELEASEDATE.getErrmsg(),null);
            return resultMsg;
        }
        if(StringUtils.isEmpty(mobilePhone)){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_MOBILEPHONE.getErrcode(),ResultStatusCode.INVALID_MOBILEPHONE.getErrmsg(),null);
            return resultMsg;
        }
        if(StringUtils.isEmpty(floor)){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_FLOOR.getErrcode(),ResultStatusCode.INVALID_FLOOR.getErrmsg(),null);
            return resultMsg;
        }
        if(StringUtils.isEmpty(shopsAddress)){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_ADDRESS.getErrcode(),ResultStatusCode.INVALID_ADDRESS.getErrmsg(),null);
            return resultMsg;
        }
        if(releaseType==null || releaseType==0){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_RELEASETYPE.getErrcode(),ResultStatusCode.INVALID_RELEASETYPE.getErrmsg(),null);
            return resultMsg;
        }
        if(StringUtils.isEmpty(estatesType)){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_ESTATESTYPE.getErrcode(),
                    ResultStatusCode.INVALID_ESTATESTYPE.getErrmsg(),null);
            return resultMsg;
        }
        resultMsg = shopsHistoryService.shopsAdd(shopsAddModel);
        return resultMsg;
    }

    /**
     * hezu List
     * @param estatesType
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "hezuList",produces = "application/json;charset=UTF-8")
    public Object hezuShopsList(String estatesType){
        ResultMsg resultMsg;
        ShopsListRequest shopsListRequest = new ShopsListRequest();
        if(StringUtils.isEmpty(estatesType)){
            shopsListRequest.setEstatesType(estatesType);
        }
        shopsListRequest.setReleaseType(Constants.HE_ZU);
        resultMsg = shopsHistoryService.shopsList(shopsListRequest);
        return resultMsg;
    }

    /**
     * zhuanzu List
     * @param estatesType
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "zhuanzuList",produces = "application/json;charset=UTF-8")
    public Object zhuanzuShopsList(String estatesType){
        ResultMsg resultMsg;
        ShopsListRequest shopsListRequest = new ShopsListRequest();
        shopsListRequest.setReleaseType(Constants.ZHUAN_ZU);
        if(StringUtils.isEmpty(estatesType)){
            shopsListRequest.setEstatesType(estatesType);
        }
        resultMsg = shopsHistoryService.shopsList(shopsListRequest);
        return resultMsg;
    }
}
