package com.bupt.dlplatform.Hystrix;

import com.bupt.dlplatform.consumer.TestConsumer;
import com.bupt.dlplatform.consumer.TestsetConsumer;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.model.TTestsetEntity;
import com.bupt.dlplatform.model.TTestsetRecordEntity;
import com.bupt.dlplatform.vo.ResponseVO;
import com.bupt.dlplatform.vo.TestsetInputVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class TestsetApiHystrix implements TestsetConsumer {

    private ResponseVO rtn = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

    @Override
    public ResponseVO<List<TTestsetEntity>> searchTestset(TestsetInputVO testsetInputVO) {
        return rtn;
    }
}
