package com.bupt.dlplatform.Hystrix;

import com.bupt.dlplatform.consumer.ModelTrainConsumer;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.model.TTrainRecordEntity;
import com.bupt.dlplatform.vo.ModelTrainInputVO;
import com.bupt.dlplatform.vo.ModelTrainOutputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ModelTrainApiHystrix implements ModelTrainConsumer {

    private ResponseVO rtn = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

    @Override
    public ResponseVO<List<ModelTrainOutputVO>> searchTrainRecord(ModelTrainInputVO modelTrainInputVO) {
        return rtn;
    }

    @Override
    public ResponseVO<ModelTrainOutputVO> searchTrainStatus(ModelTrainInputVO modelTrainInputVO){

        return rtn;
    }

    @Override
    public ResponseVO getTrainsetName(ModelTrainInputVO modelTrainInputVO) {
        return rtn;
    }

    @Override
    public ResponseVO getTrainNetwork(ModelTrainInputVO modelTrainInputVO) {
        return rtn;
    }

    @Override
    public ResponseVO submitTrainMessage(ModelTrainInputVO modelTrainInputVO) {
        return rtn;
    }

    @Override
    public ResponseVO startTrain(ModelTrainInputVO modelTrainInputVO) {
        return rtn;
    }


}
