package com.bupt.dlplatform.controller;


import com.bupt.dlplatform.config.SecretKeyConfig;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.model.common.TkGenerateParameter;
import com.bupt.dlplatform.service.TestsetInfoService;
import com.bupt.dlplatform.util.FtpUtil;
import com.bupt.dlplatform.util.TokenUtil;
import com.bupt.dlplatform.vo.ResponseVO;
import com.bupt.dlplatform.vo.TestsetInputVO;
import com.bupt.dlplatform.vo.TestsetOutputVO;
import com.bupt.dlplatform.vo.TestsetTempVO;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class TestsetController {

    @Autowired
    TestsetInfoService testsetInfoService;


    /**
     * 查询测试集记录
     * @param request
     * @return
     */
    @PostMapping("/dlplatform/searchTestset")
    public ResponseVO<List<TestsetOutputVO>> searchTestsetRecord(@RequestBody @Validated TestsetInputVO request){
        return  testsetInfoService.testsetRecord(request);
    }




    /**
     *
     * 上传检测集--数据库更新
     * 文件上传已在web部分完成
     * @param userId
     * @param uploadTime
     * @param testsetId
     * @param testsetName
     * @param size
     * @param pathName
     * @param description
     * @return
     */
    @PostMapping("/dlplatform/uploadComplete")
    public ResponseVO uploadTestSet(@RequestParam("userId") String userId,
                                    @RequestParam("uploadTime") Date uploadTime,
                                    @RequestParam("testsetId") String testsetId,
                                    @RequestParam("testsetname") String testsetName,
                                    @RequestParam("size") Double size,
                                    @RequestParam("pathName") String pathName,
                                    @RequestParam("Description") String description){

        return  testsetInfoService.uploadTestsetComp(userId,uploadTime,testsetId,
                                                    testsetName,size,
                                                    pathName, description);
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
