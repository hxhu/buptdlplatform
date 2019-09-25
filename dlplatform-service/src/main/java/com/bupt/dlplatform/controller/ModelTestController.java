package com.bupt.dlplatform.controller;

import com.bupt.dlplatform.service.ModelTestInfoService;
import com.bupt.dlplatform.vo.ResponseVO;
import com.bupt.dlplatform.vo.SearchTestInputVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModelTestController {
    @Autowired
    ModelTestInfoService modelTestInfoService;

    //查询测试记录
    @PostMapping("/dlplatform/searchTestRecord")
    public ResponseVO searchTestRecord(@RequestBody @Validated SearchTestInputVO request){
        return  modelTestInfoService.testRecord(request);
    }

}
