package com.bupt.dlplatform.service;

import com.bupt.dlplatform.mapper.TAnalyseRecordRepository;
import com.bupt.dlplatform.model.TAnalyseRecordEntity;
import com.bupt.dlplatform.vo.AnalyzeInputVO;
import com.bupt.dlplatform.vo.ResponseVO;

public interface AnalyzeInfoService {
    //评估记录查询
    ResponseVO<TAnalyseRecordEntity> searchAnalyzeRecord(AnalyzeInputVO request);

}
