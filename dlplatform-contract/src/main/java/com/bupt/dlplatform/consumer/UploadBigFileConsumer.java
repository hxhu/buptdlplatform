package com.bupt.dlplatform.consumer;

import com.bupt.dlplatform.Hystrix.TrainsetApiHystrix;
import com.bupt.dlplatform.Hystrix.UploadBigFileApiHystrix;
import com.bupt.dlplatform.model.TFileEntity;
import com.bupt.dlplatform.vo.FileExistsVO;
import com.bupt.dlplatform.vo.ResponseVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;


@FeignClient(value = "service-producer-dlplatform",fallback = UploadBigFileApiHystrix.class)
public interface UploadBigFileConsumer {

//    @RequestMapping(value = "/dlplatform/file/exists",method = RequestMethod.GET)
//    FileExistsVO fileExists(String md5, Long size);

//    @RequestMapping(value = "/dlplatform/file/new",method = RequestMethod.POST)
//    TFileEntity uploadFile( TFileEntity file);
//
//    @RequestMapping(value = "/dlplatform/file/patch/upload",method = RequestMethod.POST)
//    ResponseVO filePatchExists(String name, Integer index, String parent, String md5, Long size);
//
//    @RequestMapping(value = "/dlplatform/file/patch/merge",method = RequestMethod.POST)
//    ResponseVO filePatchMerge(String parent, Long size);




}

