package com.bupt.dlplatform.controller;

import com.bupt.dlplatform.service.AnalyzeInfoService;
import com.bupt.dlplatform.vo.AnalyzeInputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnalyzeController {
    @Autowired
    AnalyzeInfoService analyzeInfoService;

    //查询评估记录
    @PostMapping("/dlplatform/searchAnalyzeRecord")
    public ResponseVO searchAnalyzeRecord(@RequestBody @Validated AnalyzeInputVO request){
        return analyzeInfoService.searchAnalyzeRecord(request);
    }

}
