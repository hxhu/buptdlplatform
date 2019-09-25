package com.bupt.dlplatform.controller;


import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.service.TestsetInfoService;
import com.bupt.dlplatform.util.FtpUtil;
import com.bupt.dlplatform.vo.ResponseVO;
import com.bupt.dlplatform.vo.TestsetInputVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

@RestController
public class TestsetController {

    @Autowired
    TestsetInfoService testsetInfoService;

    @Autowired
    FtpUtil ftpUtil;

    //查询测试集记录
    @PostMapping("/dlplatform/searchTestset")
    public ResponseVO searchTestsetRecord(@RequestBody @Validated TestsetInputVO request){
        return  testsetInfoService.testsetRecord(request);
    }

    @PostMapping("/dlplatform/uploadTestSet")
    public ResponseVO uploadTestSet(HttpServletRequest request){
        ResponseVO responseVO = new ResponseVO(ResponseCode.OK);
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = null;
        try {
            file = multipartRequest.getFile(multipartRequest.getFileNames().next());
        }catch (Exception e){
            responseVO.setData("文件上传失败");
        }
        try {
            if (file == null){
                responseVO.setCode(ResponseCode.OPERATE_ERROR.value());
                responseVO.setMsg(ResponseCode.OPERATE_ERROR.getDescription());
                responseVO.setData("文件上传失败");
                return responseVO;
            }
            InputStream inputStream = file.getInputStream();
            ftpUtil.uploadFile("","test",inputStream);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return  responseVO;
    }
}
