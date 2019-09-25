package com.bupt.dlplatform.controller;


import com.bupt.dlplatform.service.ModelRecordInfoService;
import com.bupt.dlplatform.vo.ModelRecordInputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModelRecordController {
    @Autowired
    ModelRecordInfoService modelRecordInfoService;

    //查询模型记录
    @PostMapping("/dlplatform/searchModelRecord")
    public ResponseVO searchTestRecord(@RequestBody @Validated ModelRecordInputVO request){
        return  modelRecordInfoService.modelRecord(request);

    }


}
