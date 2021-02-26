package com.bupt.dlplatform.controller;

import com.bupt.dlplatform.service.AnalyzeInfoService;
import com.bupt.dlplatform.vo.AnalyzeInputVO;
import com.bupt.dlplatform.vo.AnalyzeOutputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class AnalyzeController {
    @Autowired
    AnalyzeInfoService analyzeInfoService;


    /**
     * 查询评估记录
     * @param request
     * @return
     */
    @PostMapping("/dlplatform/searchAnalyzeRecord")
    public ResponseVO<List<AnalyzeOutputVO>>  searchAnalyzeRecord(@RequestBody @Validated AnalyzeInputVO request){
        return analyzeInfoService.searchAnalyzeRecord(request);
    }

    /**
     * 获取已有训练记录
     * @param request
     * @return
     */
    @PostMapping("/dlplatform/getTrainRecord")
    public ResponseVO<List<AnalyzeOutputVO>> getTrainRecord(@RequestBody @Validated AnalyzeInputVO request){
        return analyzeInfoService.getTrainRecord(request);
    }

    /**
     * 创建评估
     * @param request
     * @return
     */
    @PostMapping("/dlplatform/createAnalyze")
    public ResponseVO createAnalyze(@RequestBody @Validated AnalyzeInputVO request){
        return analyzeInfoService.createAnalyze(request);
    }

    /**
     * 查看评估结果
     * @param request
     * @return
     */
    @PostMapping("/dlplatform/searchAnalyzeResult")
    public ResponseVO searchAnalyzeResult(@RequestBody @Validated AnalyzeInputVO request){
        return analyzeInfoService.searchAnalyzeResult(request);
    }

    /**
     * 显示评估结果图
     * @param request
     */
    @PostMapping("/dlplatform/showAnalyzeResult")
    public ResponseVO showAnalyzeResult( @RequestBody @Validated AnalyzeInputVO request){
        return analyzeInfoService.showAnalyzeResult(request);

    }

    /**
     * 下载评估结果
     * @param request
     * @return
     */
    @PostMapping("/dlplatform/downAnalyzeResult")
    public ResponseVO downAnalyzeResult(@RequestBody @Validated AnalyzeInputVO request){
        return analyzeInfoService.downAnalyzeResult(request);
    }

    /**
     * 删除评估记录
     * @param request
     * @return
     */
    @PostMapping("/dlplatform/deleteAnalyze")
    public ResponseVO deleteAnalyze(@RequestBody @Validated AnalyzeInputVO request){
        return analyzeInfoService.deleteAnalyze(request);
    }


}
