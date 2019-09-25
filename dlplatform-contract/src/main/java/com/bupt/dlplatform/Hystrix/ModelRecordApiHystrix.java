package com.bupt.dlplatform.Hystrix;

import com.bupt.dlplatform.consumer.ModelRecordConsumer;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.model.TModelEntity;
import com.bupt.dlplatform.vo.ModelRecordInputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Component
public class ModelRecordApiHystrix implements ModelRecordConsumer {
    private ResponseVO rtn = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

    @Override
    public ResponseVO<List<TModelEntity>> searchModelRecord(ModelRecordInputVO modelRecordInputVO) {
        return rtn;
    }


}
