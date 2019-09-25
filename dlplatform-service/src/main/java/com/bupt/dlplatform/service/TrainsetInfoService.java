package com.bupt.dlplatform.service;

import com.bupt.dlplatform.model.TTrainsetEntity;
import com.bupt.dlplatform.vo.ResponseVO;
import com.bupt.dlplatform.vo.TrainsetInputVO;

public interface TrainsetInfoService {
    ResponseVO<TTrainsetEntity> trainsetRecord(TrainsetInputVO request);
}
