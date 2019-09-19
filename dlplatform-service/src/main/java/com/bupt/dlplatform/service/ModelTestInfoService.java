package com.bupt.dlplatform.service;

import com.bupt.dlplatform.mapper.TTestRecordRepository;
import com.bupt.dlplatform.model.TTestRecordEntity;
import com.bupt.dlplatform.vo.ResponseVO;
import com.bupt.dlplatform.vo.SearchTestInputVO;

public interface SearchTestInfoService {
    ResponseVO<TTestRecordEntity> testRecord(SearchTestInputVO request);

}
