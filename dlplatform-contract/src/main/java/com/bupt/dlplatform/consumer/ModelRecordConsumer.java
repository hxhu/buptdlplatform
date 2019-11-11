package com.bupt.dlplatform.consumer;


import com.bupt.dlplatform.Hystrix.ModelRecordApiHystrix;
import com.bupt.dlplatform.model.TModelEntity;
import com.bupt.dlplatform.vo.ModelRecordInputVO;
import com.bupt.dlplatform.vo.ModelRecordOutputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "service-producer-dlplatform",fallback = ModelRecordApiHystrix.class)
public interface ModelRecordConsumer {

    @RequestMapping(value = "/dlplatform/searchModelRecord", method = RequestMethod.POST)
    ResponseVO<List<ModelRecordOutputVO>> searchModelRecord(ModelRecordInputVO modelRecordInputVO);


    @RequestMapping(value = "/dlplatform/deleteModel", method = RequestMethod.POST)
    ResponseVO deleteModel(ModelRecordInputVO modelRecordInputVO);

}
