package com.bupt.dlplatform.service;

import com.bupt.dlplatform.model.TTestsetEntity;
import com.bupt.dlplatform.vo.ResponseVO;
import com.bupt.dlplatform.vo.TestsetInputVO;
import com.bupt.dlplatform.vo.TestsetOutputVO;
import com.bupt.dlplatform.vo.TestsetTempVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface TestsetInfoService {

    //测试集记录查询
    ResponseVO<List<TestsetOutputVO>> testsetRecord(TestsetInputVO request);

    //上传测试集文件
    ResponseVO uploadTestsetFile(TestsetTempVO testsetTempVO);

    //更新测试集记录和测试集表
    ResponseVO<TestsetOutputVO> addTestset(TestsetInputVO request);

    //删除检测集
    ResponseVO deleteTestset(TestsetInputVO request);

}
