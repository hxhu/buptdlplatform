package com.bupt.dlplatform.service;

import com.bupt.dlplatform.model.TTrainRecordEntity;
import com.bupt.dlplatform.vo.ModelTrainInputVO;
import com.bupt.dlplatform.vo.ResponseVO;

import java.util.List;

public interface ModelTrainInfoService {

    //查询训练记录
    ResponseVO<List<TTrainRecordEntity>> trainRecord(ModelTrainInputVO request);



}
