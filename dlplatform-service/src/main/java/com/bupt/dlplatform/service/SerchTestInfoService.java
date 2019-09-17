package com.bupt.dlplatform.service;

import com.bupt.dlplatform.mapper.TTestRecordRepository;
import com.bupt.dlplatform.vo.ResponseVO;
import com.bupt.dlplatform.vo.SearchTestInputVO;

public interface SerchTestInfoService {
    ResponseVO<TTestRecordRepository> testRecord(SearchTestInputVO request);

}
