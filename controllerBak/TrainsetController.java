package com.bupt.dlplatform.controller;

import com.bupt.dlplatform.service.TrainsetInfoService;
import com.bupt.dlplatform.vo.ResponseVO;
import com.bupt.dlplatform.vo.TrainsetInputVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrainsetController {
    @Autowired
    TrainsetInfoService trainsetInfoService;

    /**
     * 查询训练集记录
     * @param request
     * @return
     */
    @PostMapping("/dlplatform/searchTrainset")
    public ResponseVO searchTrainsetRecord(@RequestBody @Validated TrainsetInputVO request){
        return  trainsetInfoService.trainsetRecord(request);

    }
    /*
     *
     * 从数据库中删除训练集
     *
     *  */
    @PostMapping("/dlplatform/deleteTrainset")
    public ResponseVO deleteTestset(@RequestBody @Validated TrainsetInputVO request){
        return trainsetInfoService.deleteTrainset(request);
    }
}
