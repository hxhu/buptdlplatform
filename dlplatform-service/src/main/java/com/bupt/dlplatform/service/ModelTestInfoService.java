package com.bupt.dlplatform.service;

import com.bupt.dlplatform.mapper.TTestRecordRepository;
import com.bupt.dlplatform.model.TTestRecordEntity;
import com.bupt.dlplatform.model.TTestResultEntity;
import com.bupt.dlplatform.vo.*;
import com.fasterxml.jackson.databind.ser.Serializers;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ModelTestInfoService {
    //查询测试记录
    ResponseVO<List<TTestRecordEntity>> testRecord(SearchTestInputVO request);

    //查询"自定义测试"所需相关选项

    //测试网络
    ResponseVO getOptionNetwork(BaseInputVO request);
    //测试模型
    ResponseVO getOptionModel(ModelTestInputVO request);
    //测试集
    ResponseVO getOptionTestset(BaseInputVO request);
    //测试标签
    ResponseVO getOptionTestLabel(ModelTestInputVO request);


    //增加测试记录
    ResponseVO addTestRecord(ModelTestInputVO request);

    //查看检测结果
    ResponseVO searchTestResult(ModelTestInputVO request);

    //显示检测结果图片
    void showTestResult(ModelTestInputVO request, HttpServletResponse response);

    //下载检测结果
    ResponseVO downloadResult(DownloadInputVO request);

    //删除检测记录
    ResponseVO deleteTestRecord(ModelTestInputVO request);

}
