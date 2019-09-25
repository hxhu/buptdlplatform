package com.bupt.dlplatform.service;

import com.bupt.dlplatform.model.TModelEntity;
import com.bupt.dlplatform.model.TModelRecordEntity;
import com.bupt.dlplatform.vo.ModelRecordInputVO;
import com.bupt.dlplatform.vo.ResponseVO;

import java.util.List;

public interface ModelRecordInfoService {

    //模型记录查询
    ResponseVO<List<TModelEntity>> modelRecord(ModelRecordInputVO request);
}
