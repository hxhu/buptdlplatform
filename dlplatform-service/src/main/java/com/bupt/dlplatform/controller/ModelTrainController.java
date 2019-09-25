package com.bupt.dlplatform.controller;


import com.bupt.dlplatform.service.ModelTrainInfoService;
import com.bupt.dlplatform.vo.ModelTrainInputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModelTrainController {
    @Autowired
    ModelTrainInfoService modelTrainInfoService;

    //查询训练记录
    @PostMapping("/dlplatform/searchTrainRecord")
    public ResponseVO searchTestRecord(@RequestBody @Validated ModelTrainInputVO request){
        return  modelTrainInfoService.trainRecord(request);

    }


}
