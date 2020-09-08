package com.bupt.dlplatform.service;

import com.bupt.dlplatform.model.TTrainsetEntity;
import com.bupt.dlplatform.vo.ResponseVO;
import com.bupt.dlplatform.vo.TrainsetInputVO;

public interface TrainsetInfoService {
    //训练集记录
    ResponseVO<TTrainsetEntity> trainsetRecord(TrainsetInputVO request);

    //删除训练集
    ResponseVO deleteTrainset(TrainsetInputVO request);
}
