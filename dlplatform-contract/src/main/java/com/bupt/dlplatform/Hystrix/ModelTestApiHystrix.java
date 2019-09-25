package com.bupt.dlplatform.Hystrix;

import com.bupt.dlplatform.consumer.ModelTestConsumer;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.model.TTestRecordEntity;
import com.bupt.dlplatform.vo.ResponseVO;
import com.bupt.dlplatform.vo.SearchTestInputVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Slf4j
@Component
public class ModelTestApiHystrix  implements ModelTestConsumer {

    private ResponseVO rtn = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

    @Override
    public ResponseVO<List<TTestRecordEntity>> searchTestRecord(SearchTestInputVO searchTestInputVO) {
        return rtn;
    }

}
