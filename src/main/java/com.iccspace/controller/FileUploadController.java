package com.iccspace.controller;

import com.alibaba.fastjson.JSONArray;
import com.iccspace.core.Constants;
import com.iccspace.service.FilesService;
import com.iccspace.token.ResultMsg;
import com.iccspace.token.ResultStatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhur on 2016/12/28.
 * @author:zhur
 * @description:
 * @date:create in 2016-12-28-11:15
 */
@RestController
@RequestMapping("file")
public class FileUploadController {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private FilesService filesService;

    /***
     * 上传文件 用@RequestParam注解来指定表单上的file为MultipartFile
     *
     * @param file
     * @return
     */
    @RequestMapping("fileUpload")
    public String fileUpload(@RequestParam("file") MultipartFile file) {
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                // 文件保存路径
                String filePath = httpServletRequest.getSession().getServletContext().getRealPath("/") + "upload/"
                        + file.getOriginalFilename();
                // 转存文件
                file.transferTo(new File(filePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 重定向
        return "redirect:/list.html";
    }

    /**
     * batch file upload
     * @param files
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "filesUpload",consumes = "multipart/form-data")
    public Object filesUpload(@RequestParam("files") MultipartFile[] files) {

        ResultMsg resultMsg;
        JSONArray jsonArray = new JSONArray();

        String  url = httpServletRequest.getServerName()+":"+httpServletRequest.getServerPort()+httpServletRequest.getContextPath()+"/photo/";
        if(files!=null&&files.length>0){

            for(int i = 0;i<files.length;i++){
                MultipartFile file = files[i];

                Map map = new HashMap<String,String>();
                map = filesService.uploadShopsPhotos(file,url);
                jsonArray.add(map);
            }
        }

        resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),ResultStatusCode.OK.getErrmsg(),jsonArray);
        return resultMsg;
    }

    /**
     * //容器upload目录或磁盘目录
     * //String path = request.getSession().getServletContext().getRealPath("upload");
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
        if(multipartFile !=null) {
            if (multipartFile.isEmpty()) {
                resultMsg = new ResultMsg(ResultStatusCode.INVALID_UPLOAD_FILE.getErrcode(),
                        ResultStatusCode.INVALID_UPLOAD_FILE.getErrmsg(), null);
                return resultMsg;
            }
        }
        String  url = request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/photo/";

        Map map = filesService.uploadShopsPhotos(multipartFile,url);
        resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),ResultStatusCode.OK.getErrmsg(),map);
        return resultMsg;
    }

    /**
     * 单个文件上传到项目根路径下
     * String relativelyPath=System.getProperty("user.dir");
     * @param multipartFile
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "photosUpload2",consumes = "multipart/form-data")
    public Object uploadPhotos2(MultipartFile multipartFile,HttpServletRequest request){
        ResultMsg resultMsg;
        String relativelyPath=System.getProperty("user.dir");
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
        map.put("local_url", relativelyPath+File.separator+multipartFile.getOriginalFilename());
        //map.put("url", file);
        //Api
        String s=request.getContextPath().toString();
        //shops/photosUpload2
        String s1=request.getServletPath().toString();
        //Api
        String s2=request.getSession().getServletContext().getContextPath();
        //C:\Users\Administrator\AppData\Local\Temp\tomcat-docbase.4914921786617702531.8896
        String s3=request.getSession().getServletContext().getRealPath("/");
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
}
