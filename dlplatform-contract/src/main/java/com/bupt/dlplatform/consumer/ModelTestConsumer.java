package com.bupt.dlplatform.consumer;


import com.bupt.dlplatform.Hystrix.ModelTestApiHystrix;
import com.bupt.dlplatform.model.TTestRecordEntity;
import com.bupt.dlplatform.vo.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@FeignClient(value = "service-producer-dlplatform",fallback = ModelTestApiHystrix.class)
public interface ModelTestConsumer {
    /**
     * 查询检测记录
     */
    @RequestMapping(value = "/dlplatform/searchTestRecord", method = RequestMethod.POST)
    ResponseVO<List<ModelTestOutputVO>> searchTestRecord(SearchTestInputVO searchTestInputVO);
    //ResponseVO<List<TTestRecordEntity>> searchTestRecord(@RequestBody SearchTestInputVO searchTestInputVO);

    /**
     *
     * 点击"自定义测试"-->查询相关可选项信息
     */
    /*测试网络*/
    @RequestMapping(value = "/dlplatform/getOptionNetwork",method = RequestMethod.POST)
    ResponseVO getOptionNetwork(@RequestBody @Validated BaseInputVO baseInputVO);

    /*测试模型*/
    @RequestMapping(value = "/dlplatform/getOptionModel",method = RequestMethod.POST)
    ResponseVO<List<OptionVO>> getOptionModel(@RequestBody @Validated ModelTestInputVO modelTestInputVO);

    /*测试集*/
    @RequestMapping(value = "/dlplatform/getOptionTestset",method = RequestMethod.POST)
    ResponseVO<List<OptionVO>> getOptionTestset(@RequestBody @Validated BaseInputVO baseInputVO);

    /*测试标签*/
    @RequestMapping(value = "/dlplatform/getOptionTestLabel",method = RequestMethod.POST)
    ResponseVO getOptionTestLabel(@RequestBody @Validated ModelTestInputVO  modelTestInputVO);


    /**
     *
     * 添加检测记录
     */
    @RequestMapping(value = "/dlplatform/addTestRecord",method = RequestMethod.POST)
    ResponseVO addTestRecord(@RequestBody @Validated ModelTestInputVO modelTestInputVO);

    /**
     * 添加检测结果记录
     *
     */
    @RequestMapping(value = "/dlplatform/getTestResult",method = RequestMethod.POST)
    ResponseVO getTestResult(@RequestBody TestResultParamVO testResultParamVO);

    /**
     * 查看检测结果
     */
    @RequestMapping(value = "/dlplatform/searchTestResult",method = RequestMethod.POST)
    ResponseVO searchTestResult(@RequestBody @Validated ModelTestInputVO  modelTestInputVO);

    @RequestMapping(value = "/dlplatform/getResultLoc",method = RequestMethod.POST)
    ResponseVO getResultLoc(@RequestBody @Validated TestResultInputVO request);


    /**
     * 下载检测结果
     */
    @RequestMapping(value = "/dlplatform/downloadResult",method = RequestMethod.POST)
    ResponseVO downloadResult(@RequestBody @Validated DownloadInputVO downloadInputVO);

    /**
     * 删除检测记录
     */
    @RequestMapping(value = "/dlplatform/deleteTestRecord",method = RequestMethod.POST)
    ResponseVO deleteTestRecord(@RequestBody @Validated ModelTestInputVO  modelTestInputVO);



}

