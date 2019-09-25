package com.bupt.dlplatform.controller;


import com.bupt.dlplatform.service.TestsetInfoService;
import com.bupt.dlplatform.vo.ResponseVO;
import com.bupt.dlplatform.vo.TestsetInputVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestsetController {

    @Autowired
    TestsetInfoService testsetInfoService;

    //查询测试集记录
    @PostMapping("/dlplatform/searchTestset")
    public ResponseVO searchTestsetRecord(@RequestBody @Validated TestsetInputVO request){
        return  testsetInfoService.testsetRecord(request);

    }
}
