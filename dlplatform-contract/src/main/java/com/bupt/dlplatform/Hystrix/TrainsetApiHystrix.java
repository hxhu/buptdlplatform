package com.bupt.dlplatform.Hystrix;

import com.bupt.dlplatform.consumer.TrainsetConsumer;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.model.TTrainsetEntity;
import com.bupt.dlplatform.vo.ResponseVO;
import com.bupt.dlplatform.vo.TrainsetInputVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
public class TrainsetApiHystrix implements TrainsetConsumer {
    private ResponseVO rtn = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

    @Override
    public ResponseVO<List<TTrainsetEntity>> searchTrainset(TrainsetInputVO trainsetInputVO) {
        return rtn;
    }

    @Override
    public ResponseVO deleteTrainset(TrainsetInputVO request) {
        return rtn;
    }

}
