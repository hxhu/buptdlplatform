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

    //查看训练情况
    @PostMapping("/dlplatform/searchTrainStatus")
    public ResponseVO searchTrainStatus(@RequestBody @Validated ModelTrainInputVO request){
        return modelTrainInfoService.trainStatus(request);
    }

    //模型训练--获取训练集名
    @PostMapping("/dlplatform/getTrainsetName")
    public ResponseVO getTrainsetName(@RequestBody @Validated ModelTrainInputVO request){
        return modelTrainInfoService.trainsetName(request);
    }

    //模型训练--获取训练网络
    @PostMapping("/dlplatform/getTrainNetwork")
    public ResponseVO getTrainNetwork(@RequestBody @Validated ModelTrainInputVO request){
        return modelTrainInfoService.getTrainNetwork(request);
    }

    //模型训练--提交基本信息
    @PostMapping("/dlplatform/submitTrainMessage")
    public ResponseVO submitTrainMessage(@RequestBody @Validated ModelTrainInputVO request){
        return modelTrainInfoService.submitTrainMessage(request);
    }

    //模型训练--下载默认参数文件
    @PostMapping("/dlplatform/douwnloadParameter")
    public ResponseVO chooseParameter(@RequestBody @Validated ModelTrainInputVO request){
        return modelTrainInfoService.douwnloadParameter(request);
    }

    //模型训练--提交参数文件
    @PostMapping("/dlplatform/submitParameter")
    public ResponseVO submitParameter(@RequestBody @Validated ModelTrainInputVO request){
        return modelTrainInfoService.submitParameter(request);
    }

    //模型训练--开始训练
    @PostMapping("/dlplatform/startTrain")
    public ResponseVO startTrain(@RequestBody @Validated ModelTrainInputVO request){
        return modelTrainInfoService.startTrain(request);
    }

}
