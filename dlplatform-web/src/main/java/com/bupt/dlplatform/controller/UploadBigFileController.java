package com.bupt.dlplatform.controller;

import com.bupt.dlplatform.consumer.UploadBigFileConsumer;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.model.TFileEntity;
import com.bupt.dlplatform.vo.FileExistsVO;
import com.bupt.dlplatform.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@Slf4j
public class UploadBigFileController {
    @Autowired
    UploadBigFileConsumer uploadBigFileConsumer;

     @RequestMapping(value = "/dlplatform/file/exists",method = RequestMethod.GET)
     public FileExistsVO fileExists(@RequestParam("md5")String md5, @RequestParam("size") Long size){
         FileExistsVO fileExistsVO=null;
         try{
             //fileExistsVO=uploadBigFileConsumer.fileExists(md5, size);

         }catch (Exception e){
             log.error("UploadBigFileController-exists 异常",e);
         }
         return fileExistsVO;

     }

    @RequestMapping(value = "/dlplatform/file/new",method = RequestMethod.POST)
    public TFileEntity uploadFile(@RequestBody TFileEntity file){
        TFileEntity tFileEntity=null;
        try{
            //tFileEntity=uploadBigFileConsumer.uploadFile(file);
        }catch (Exception e){
            log.error("UploadBigFileController-uploadFile 异常",e);
        }
        return tFileEntity;
    }

    @RequestMapping(value = "/dlplatform/file/patch/upload",method = RequestMethod.POST)
    public ResponseVO filePatchExists(@RequestParam("name") String name,
                                      @RequestParam("index") Integer index,
                                      @RequestParam("parent")String parent,
                                      @RequestParam("md5")String md5,
                                      @RequestParam("size")Long size,
                                      @RequestParam("patch")MultipartFile patch){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
//            ResponseVO res = uploadBigFileConsumer.filePatchExists(name, index, parent, md5, size);
//            if(res.getCode()== ResponseCode.OK.value()){
//                Object se = res.getData();
//                responseVO.setCode(ResponseCode.OK.value());
//                responseVO.setMsg(ResponseCode.OK.getDescription());
//                responseVO.setData(se);
//            }else {
//                responseVO.setCode(res.getCode());
//                responseVO.setMsg(res.getMsg());
//            }
        }catch (Exception e){
            responseVO.setCode(ResponseCode.SYSTEM_EXCEPTION.value());
            responseVO.setMsg(ResponseCode.SYSTEM_EXCEPTION.getDescription());
            log.error("UploadBigFileController-upload ---> 异常！", e);
        }
        return responseVO;
    }

    @RequestMapping(value = "/dlplatform/file/patch/merge",method = RequestMethod.POST)
    public ResponseVO filePatchMerge(@RequestParam("parent") String parent,
                                     @RequestParam("size") Long size){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
//            ResponseVO res = uploadBigFileConsumer.filePatchMerge(parent, size);
//            if(res.getCode()== ResponseCode.OK.value()){
//                Object se = res.getData();
//                responseVO.setCode(ResponseCode.OK.value());
//                responseVO.setMsg(ResponseCode.OK.getDescription());
//                responseVO.setData(se);
//            }else {
//                responseVO.setCode(res.getCode());
//                responseVO.setMsg(res.getMsg());
//            }
        }catch (Exception e){
            responseVO.setCode(ResponseCode.SYSTEM_EXCEPTION.value());
            responseVO.setMsg(ResponseCode.SYSTEM_EXCEPTION.getDescription());
            log.error("UploadBigFileController-filePatchMerge ---> 异常！", e);
        }
        return responseVO;
    }
}
