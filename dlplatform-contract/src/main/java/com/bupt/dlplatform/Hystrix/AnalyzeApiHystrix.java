package com.bupt.dlplatform.Hystrix;

import com.bupt.dlplatform.consumer.AnalyzeConsumer;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.model.TAnalyseRecordEntity;
import com.bupt.dlplatform.vo.AnalyzeInputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class AnalyzeApiHystrix implements AnalyzeConsumer {

    private ResponseVO rtn = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

    @Override
    public ResponseVO<List<TAnalyseRecordEntity>> searchAnalyzeRecord(AnalyzeInputVO analyzeInputVO) {
        return rtn;
    }

}
