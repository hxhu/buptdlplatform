package com.bupt.dlplatform.service;

import com.bupt.dlplatform.model.TTestsetEntity;
import com.bupt.dlplatform.vo.ResponseVO;
import com.bupt.dlplatform.vo.TestsetInputVO;
import com.bupt.dlplatform.vo.TestsetOutputVO;
import com.bupt.dlplatform.vo.TestsetTempVO;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

public interface TestsetInfoService {

    //测试集记录查询
    ResponseVO<List<TestsetOutputVO>> testsetRecord(TestsetInputVO request);


    // 上传检测集--数据库更新
    ResponseVO uploadTestsetComp( String userId,
                                  Date uploadTime,
                                  String testsetId,
                                  String testsetName,
                                  Double size,
                                  String pathName,
                                  String description);

    //删除检测集
    ResponseVO deleteTestset(TestsetInputVO request);

}
