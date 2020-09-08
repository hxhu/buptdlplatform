package com.bupt.dlplatform.service;

import com.bupt.dlplatform.model.TTrainRecordEntity;
import com.bupt.dlplatform.vo.BaseInputVO;
import com.bupt.dlplatform.vo.ModelTrainInputVO;
import com.bupt.dlplatform.vo.ModelTrainOutputVO;
import com.bupt.dlplatform.vo.ResponseVO;

import java.util.List;

public interface ModelTrainInfoService {

    //查询训练记录
    ResponseVO<List<ModelTrainOutputVO>> trainRecord(ModelTrainInputVO request);
    //查询训练情况
    ResponseVO<ModelTrainOutputVO> trainStatus(ModelTrainInputVO request);

    //模型训练--获取训练集名
    ResponseVO<List<ModelTrainOutputVO>> trainsetName(ModelTrainInputVO request);

    //模型训练--获取训练网络
    ResponseVO getTrainNetwork(ModelTrainInputVO request);

    //模型训练--提交基本信息
    ResponseVO<ModelTrainOutputVO> submitTrainMessage(ModelTrainInputVO request);

    //模型训练--下载参数文件
    ResponseVO<ModelTrainOutputVO> douwnloadParameter(ModelTrainInputVO request);

    //模型训练--上传参数文件
    ResponseVO submitParameter(ModelTrainInputVO request);

    //模型训练--开始训练
    ResponseVO startTrain(ModelTrainInputVO request);

}
