package com.bupt.dlplatform.controller;


import com.bupt.dlplatform.config.SecretKeyConfig;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.model.common.TkGenerateParameter;
import com.bupt.dlplatform.service.TestsetInfoService;
import com.bupt.dlplatform.util.FtpUtil;
import com.bupt.dlplatform.util.TokenUtil;
import com.bupt.dlplatform.vo.ResponseVO;
import com.bupt.dlplatform.vo.TestsetInputVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Date;

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

    /*
    * 上传测试集图片（一张）
    * Response.data:
    * 1)fileId
    * 2)fileSaveAddr
    * */
    @PostMapping("/dlplatform/uploadTestSet")
    public ResponseVO uploadTestSet(HttpServletRequest httprequest) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        //Token-->userId
        String token = httprequest.getHeader("Content-token");
        String userId;
        if (!StringUtils.isBlank(token)) {
            TkGenerateParameter tokenEntity = TokenUtil.getEntity(token, SecretKeyConfig.secretKeySave());
            if (tokenEntity != null) {
                userId=tokenEntity.getUserId();
            }
            else{
                responseVO.setData("Token 解析出错！");
                return responseVO;
            }
        }
        else{
            responseVO.setData("Token 解析出错！");
            return responseVO;
        }

        //转型为MultipartHttpServletRequest
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) httprequest;
        MultipartFile file = null;
        boolean res;
        try {
            //获得文件
            file = multipartRequest.getFile(multipartRequest.getFileNames().next());
        } catch (Exception e) {
            responseVO.setCode(ResponseCode.OPERATE_ERROR.value());
            responseVO.setMsg(ResponseCode.OPERATE_ERROR.getDescription());
            responseVO.setData("文件上传失败");
            return responseVO;
        }
        try {
            if (file == null) {
                responseVO.setCode(ResponseCode.OPERATE_ERROR.value());
                responseVO.setMsg(ResponseCode.OPERATE_ERROR.getDescription());
                responseVO.setData("文件上传失败");
                return responseVO;
            }
            //获得输入流
            InputStream inputStream = file.getInputStream();

            //生成文件ID:userID+上传时间戳+文件大小
            Long time =System.currentTimeMillis();
            Date uploadTime =new Date(time);
            String uploadTimeString = time.toString();
            Long size = file.getSize();
            Double sizeToDouble = size.doubleValue();
            String sizeString =size.toString();
            String fileId=userId+uploadTimeString+sizeString;

            //fileName
            String fileName=file.getName();

            //生成路径 userId/testset/uploadTimeString/
            String pathName=userId+"/testset/"+uploadTimeString+"/";
            res=ftpUtil.uploadFile(pathName, fileName, inputStream);
            if(res==true){
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());

                TestsetInputVO testsetInputVO=new TestsetInputVO(fileId,fileName,uploadTime,sizeToDouble,pathName,null,null);
                testsetInputVO.setUserId(userId);
                responseVO.setData(testsetInputVO);
            }
            else{
                responseVO.setCode(ResponseCode.OPERATE_ERROR.value());
                responseVO.setMsg(ResponseCode.OPERATE_ERROR.getDescription());
                responseVO.setData("文件上传失败");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return responseVO;
    }


    /**
     * 测试集上传记录写入数据库
     *
     * */
    @PostMapping("/dlplatform/addTestset")
    public ResponseVO addTestset(@RequestBody @Validated TestsetInputVO request){
        return  testsetInfoService.addTestset(request);
    }

    /*
     *
     * 从数据库中删除检测集
     *
     *  */
    @PostMapping("/dlplatform/deleteTestset")
    public ResponseVO deleteTestset(@RequestBody @Validated TestsetInputVO request){
        return testsetInfoService.deleteTestset(request);
    }

}
