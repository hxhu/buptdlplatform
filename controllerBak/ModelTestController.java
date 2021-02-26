package com.bupt.dlplatform.controller;

import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.service.ModelTestInfoService;
import com.bupt.dlplatform.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ModelTestController {
    @Autowired
    ModelTestInfoService modelTestInfoService;

    /**
     * 查询检测记录
     */
    @PostMapping("/dlplatform/searchTestRecord")
    public ResponseVO searchTestRecord(@RequestBody @Validated SearchTestInputVO request){
        return  modelTestInfoService.testRecord(request);
    }

    /**
     *
     * 点击"自定义测试"-->查询相关可选项信息
     */
    /*测试网络*/
    @PostMapping("/dlplatform/getOptionNetwork")
    public ResponseVO getOptionNetwork(@RequestBody @Validated BaseInputVO request){
        return modelTestInfoService.getOptionNetwork(request);
    }

    /*测试模型*/
    @PostMapping("/dlplatform/getOptionModel")
    public ResponseVO getOptionModel(@RequestBody @Validated ModelTestInputVO request){
        return modelTestInfoService.getOptionModel(request);
    }

    /*测试集*/
    @PostMapping("/dlplatform/getOptionTestset")
    public ResponseVO getOptionTestset(@RequestBody @Validated BaseInputVO request){
        return modelTestInfoService.getOptionTestset(request);
    }

    /*测试标签*/
    @PostMapping("/dlplatform/getOptionTestLabel")
    public ResponseVO getOptionTestLabel(@RequestBody @Validated ModelTestInputVO request){
        return modelTestInfoService.getOptionTestLabel(request);
    }

    /**
     *
     * 添加检测记录
     */
    @PostMapping("/dlplatform/addTestRecord")
    public ResponseVO addTestRecord(@RequestBody @Validated ModelTestInputVO request){
        return modelTestInfoService.addTestRecord(request);
    }

    /**
     * 添加检测结果记录
     */
    @PostMapping( "/dlplatform/getTestResult")
    public ResponseVO getTestResult(@RequestBody TestResultParamVO request){
        return modelTestInfoService.getTestResult(request);
    }


    /**
     * 查看检测结果
     */
    @PostMapping("/dlplatform/searchTestResult")
    public ResponseVO searchTestResult(@RequestBody @Validated ModelTestInputVO request){
        return modelTestInfoService.searchTestResult(request);
    }

    @PostMapping("/dlplatform/getResultLoc")
    public ResponseVO getResultLoc(@RequestBody @Validated TestResultInputVO request){
        return modelTestInfoService.getResultLoc(request);
    }


    /**
     * 下载检测结果
    */
    @PostMapping("/dlplatform/downloadResult")
    public ResponseVO downloadResult(@RequestBody @Validated DownloadInputVO request){
        return modelTestInfoService.downloadResult(request);
    }

    /**
     * 删除检测记录
     */
    @PostMapping("/dlplatform/deleteTestRecord")
    public ResponseVO deleteTestRecord(@RequestBody @Validated ModelTestInputVO request){
        return modelTestInfoService.deleteTestRecord(request);
    }

}
