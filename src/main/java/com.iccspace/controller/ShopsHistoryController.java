package com.iccspace.controller;

import com.iccspace.controller.model.ShopsAddModel;
import com.iccspace.controller.model.ShopsEditModel;
import com.iccspace.controller.model.ShopsListRequest;
import com.iccspace.core.Constants;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
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
     * "resultdata": {
     *      "local_url": "C:\\Users\\Administrator\\AppData\\Local\\Temp\\tomcat-docbase.2284832876424474442.8896\\upload/1481794283869.jpeg",
     *      "url": "/Api/upload/1481794283869.jpeg"
     *  }
     * photos upload
     * @param multipartFile
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value="photosUpload",consumes = "multipart/form-data")
    public Object uploadPhotos(MultipartFile multipartFile, HttpServletRequest request){
        ResultMsg resultMsg;
        if(multipartFile.isEmpty()){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_UPLOAD_FILE.getErrcode(),
                    ResultStatusCode.INVALID_UPLOAD_FILE.getErrmsg(),null);
           return  resultMsg;
        }
        String path = request.getSession().getServletContext().getRealPath("upload");
        //String path = "D://upload//";
        String fileName = multipartFile.getOriginalFilename();
        String f=path+"/"+fileName;
        File mk= new File(path);
        File targetFile = new File(f);
        System.getProperty("os");

        //目录不存在，则创建目录
        if(!mk.exists()){
            mk.mkdirs();
        }
        //保存
        try {
            multipartFile.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map map = new HashMap();
        map.put("local_url",f);
        map.put("url",request.getContextPath()+"/upload/"+fileName);
        resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),"",map);
        return resultMsg;
    }

    /**
     * 单个文件上传到项目根路径下
     * @param multipartFile
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "photosUpload2",consumes = "multipart/form-data")
    public Object uploadPhotos2(MultipartFile multipartFile){
        ResultMsg resultMsg;
        if(multipartFile.isEmpty()){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_UPLOAD_FILE.getErrcode(),ResultStatusCode.INVALID_UPLOAD_FILE.getErrmsg(),null);
            return  resultMsg;
        }
        try{
            File file = new File(multipartFile.getOriginalFilename());
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);

            bos.write(multipartFile.getBytes());
            bos.flush();
            bos.close();
        }catch(FileNotFoundException e){
            resultMsg = new ResultMsg(Constants.OPERATOR_FILE_ERROR,Constants.OPERATOR_FILE_ERROR_MESSAGE,null);
            return resultMsg;
        }catch(IOException e){
            resultMsg = new ResultMsg(Constants.OPERATOR_FILE_ERROR,Constants.OPERATOR_FILE_ERROR_MESSAGE,null);
            return resultMsg;
        }
        Map<String,Object> map = new HashMap<String,Object>();
        //map.put("url", file);
        return new ResultMsg(Constants.OPERATOR_DB_SUCCESS,"",map);
    }

    /**
     * 批量上传到工程根路径下
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "batch/photosUpload",consumes = "multipart/form-data")
    public Object batchUploadPhotos(HttpServletRequest httpServletRequest){

        ResultMsg resultMsg;

        List<MultipartFile> files = ((MultipartHttpServletRequest)httpServletRequest).getFiles("file");

        MultipartFile multipartFile = null;
        BufferedOutputStream bufferedOutputStream = null;
        for(int i=0;i<files.size();i++){
            multipartFile = files.get(i);
            if(multipartFile.isEmpty()){
                resultMsg = new ResultMsg(ResultStatusCode.INVALID_UPLOAD_FILE.getErrcode(),ResultStatusCode.INVALID_UPLOAD_FILE.getErrmsg(),null);
                return resultMsg;
            }
            try{
                byte[] bytes = multipartFile.getBytes();

                bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(multipartFile.getOriginalFilename())));

                bufferedOutputStream.write(bytes);
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
            }catch(FileNotFoundException e){
                resultMsg = new ResultMsg(Constants.OPERATOR_FILE_ERROR,Constants.OPERATOR_FILE_ERROR_MESSAGE,null);
                return resultMsg;
            }catch(IOException e){
                resultMsg = new ResultMsg(Constants.OPERATOR_FILE_ERROR,Constants.OPERATOR_FILE_ERROR_MESSAGE,null);
                return resultMsg;
            }
        }
        resultMsg = new ResultMsg(Constants.OPERATOR_FILE_SUCCESS,"",null);
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
