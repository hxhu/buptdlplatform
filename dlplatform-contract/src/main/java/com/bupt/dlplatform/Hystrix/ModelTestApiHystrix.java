package com.bupt.dlplatform.Hystrix;

import com.bupt.dlplatform.consumer.ModelTestConsumer;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.model.TTestRecordEntity;
import com.bupt.dlplatform.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@Component
public class ModelTestApiHystrix  implements ModelTestConsumer {

    private ResponseVO rtn = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

    @Override
    public ResponseVO<List<ModelTestOutputVO>> searchTestRecord(SearchTestInputVO searchTestInputVO) {
        return rtn;
    }

    @Override
    public ResponseVO getOptionNetwork(BaseInputVO baseInputVO) {
        return rtn;
    }

    @Override
    public  ResponseVO<List<OptionVO>> getOptionModel(ModelTestInputVO  modelTestInputVO) {
        return rtn;
    }

    @Override
    public  ResponseVO<List<OptionVO>> getOptionTestset(BaseInputVO baseInputVO) {
        return rtn;
    }

    @Override
    public ResponseVO getOptionTestLabel(ModelTestInputVO  modelTestInputVO) {
        return rtn;
    }

    @Override
    public ResponseVO addTestRecord(ModelTestInputVO  modelTestInputVO) {
        return rtn;
    }

    @Override
    public ResponseVO searchTestResult(ModelTestInputVO modelTestInputVO) {
        return rtn;
    }

    @Override
    public ResponseVO getResultLoc(TestResultInputVO request) {
        return rtn;
    }

    @Override
    public ResponseVO downloadResult(DownloadInputVO downloadInputVO) {
        return rtn;
    }

    @Override
    public ResponseVO deleteTestRecord(ModelTestInputVO modelTestInputVO) {
        return rtn;
    }


}
