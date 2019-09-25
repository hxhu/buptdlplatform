package com.bupt.dlplatform.service;

import com.bupt.dlplatform.mapper.TTestRecordRepository;
import com.bupt.dlplatform.model.TTestRecordEntity;
import com.bupt.dlplatform.vo.ResponseVO;
import com.bupt.dlplatform.vo.SearchTestInputVO;

import java.util.List;

public interface ModelTestInfoService {
    //查询测试记录
    ResponseVO<List<TTestRecordEntity>> testRecord(SearchTestInputVO request);

    //增加测试记录
    ResponseVO<TTestRecordEntity> addTestRecord(SearchTestInputVO request);

}
