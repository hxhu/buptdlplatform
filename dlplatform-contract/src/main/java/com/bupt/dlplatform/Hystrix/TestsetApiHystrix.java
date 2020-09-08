package com.bupt.dlplatform.Hystrix;

import com.bupt.dlplatform.consumer.TestConsumer;
import com.bupt.dlplatform.consumer.TestsetConsumer;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.model.TTestsetEntity;
import com.bupt.dlplatform.model.TTestsetRecordEntity;
import com.bupt.dlplatform.vo.ResponseVO;
import com.bupt.dlplatform.vo.TestsetInputVO;
import com.bupt.dlplatform.vo.TestsetOutputVO;
import com.bupt.dlplatform.vo.TestsetTempVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class TestsetApiHystrix implements TestsetConsumer {

    private ResponseVO rtn = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

    @Override
    public ResponseVO<List<TestsetOutputVO>> searchTestset(TestsetInputVO testsetInputVO) {
        return rtn;
    }


    @Override
    public ResponseVO uploadTestsetComp(String userId, Date uploadTime, String testsetId, String testsetName, Double size,
                                        String pathName, String description){
        return rtn;
    }


    @Override
    public ResponseVO deleteTestset(TestsetInputVO request) {
        return rtn;
    }
}
