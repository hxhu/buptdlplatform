package com.bupt.dlplatform.service;

import com.bupt.dlplatform.model.TTestsetEntity;
import com.bupt.dlplatform.vo.ResponseVO;
import com.bupt.dlplatform.vo.TestsetInputVO;

public interface TestsetInfoService {

    //测试集记录查询
    ResponseVO<TTestsetEntity> testsetRecord(TestsetInputVO request);

    //更新测试集记录和测试集表
    ResponseVO<TTestsetEntity> addTestset(TestsetInputVO request);

    //删除检测集
    ResponseVO<TTestsetEntity> deleteTestset(TestsetInputVO request);

}
