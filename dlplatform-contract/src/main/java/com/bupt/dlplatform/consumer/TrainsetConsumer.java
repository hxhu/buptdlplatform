package com.bupt.dlplatform.consumer;


import com.bupt.dlplatform.Hystrix.TrainsetApiHystrix;
import com.bupt.dlplatform.model.TTrainsetEntity;
import com.bupt.dlplatform.vo.ResponseVO;
import com.bupt.dlplatform.vo.TrainsetInputVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "service-producer-dlplatform",fallback = TrainsetApiHystrix.class)
public interface TrainsetConsumer {

    @RequestMapping(value = "/dlplatform/searchTrainset", method = RequestMethod.POST)
    ResponseVO<List<TTrainsetEntity>> searchTrainset(TrainsetInputVO trainsetInputVO);

    @RequestMapping(value = "/dlplatform/deleteTrainset", method = RequestMethod.POST)
    ResponseVO deleteTrainset(TrainsetInputVO request);
}
